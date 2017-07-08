package mobile20171c.utnapp;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import Helpers.ImageLoader;
import Helpers.UrlRequest;
import mobile20171c.utnapp.Modelo.Usuario;

import static android.app.Activity.RESULT_OK;

public class ConfiguracionFragment extends Fragment {

    private FirebaseAuth mAuth;

    private static final int REQUEST_PICK_IMAGE = 1000;
    private static final int REQUEST_TAKE_PHOTO = 1500;
    private static final int REQUEST_READ = 2000;
    private static final String SELECTED_PICTURE = "ImagenSeleccionada";
    private ProgressBar progressBar;

    String currentPhotoPath;
    private ImageView selectedImageView;

    String fotoURL;

    private Handler handler = new Handler();

    public ConfiguracionFragment() {
        // Required empty public constructor
    }

    public static ConfiguracionFragment newInstance() {
        return new ConfiguracionFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_configuracion, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FirebaseUser user = mAuth.getCurrentUser();
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        EditText etNombre = (EditText) view.findViewById(R.id.editTextNombre);

        etNombre.setText(user.getDisplayName());

        ImageButton btnGaleria = (ImageButton) view.findViewById(R.id.btnGaleria);
        ImageButton btnFoto = (ImageButton) view.findViewById(R.id.btnCamara);
        Button btnGuardar = (Button) view.findViewById(R.id.btnGuardar);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarCambiosEnPerfil();
            }
        });
        btnGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AbrirGaleria();
            }
        });
        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tomarFoto();
            }
        });

        selectedImageView = (ImageView) view.findViewById(R.id.imgUsr);

        FirebaseDatabase.getInstance()
                .getReference("usuarios")
                .child(user.getUid())
                .addListenerForSingleValueEvent(new UsrValueEventListener(view));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_TAKE_PHOTO:
                if (resultCode == getActivity().RESULT_OK) {
                    Intent resultData = new Intent();
                    resultData.putExtra(SELECTED_PICTURE, currentPhotoPath);
                    getActivity().setResult(getActivity().RESULT_OK, resultData);
                    cargarImagen();
                } else {
                    new File(currentPhotoPath).delete();
                }
                break;
            case REQUEST_PICK_IMAGE:
                if (resultCode == getActivity().RESULT_OK && data != null) {
                    saveImage(data.getData());
                    Intent resultData = new Intent();
                    resultData.putExtra(SELECTED_PICTURE, currentPhotoPath);
                    getActivity().setResult(getActivity().RESULT_OK, resultData);
                    cargarImagen();
                }
        }
    }

    private File crearArchivoDeImagen() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void cargarImagen() {

        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, null);
        selectedImageView.setImageBitmap(bitmap);
    }

    private void saveImage(Uri data) {
        try {
            InputStream input = getActivity().getContentResolver().openInputStream(data);
            assert input != null;
            File file = crearArchivoDeImagen();
            FileOutputStream output = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int len = input.read(buffer);
            while (len != -1) {
                output.write(buffer, 0, len);
                len = input.read(buffer);
            }
            output.close();
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void AbrirGaleria() {

        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this.getActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(getContext(), R.string.permisoRequerido, Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this.getActivity(), new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ);
            }
            return;
        }

        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        gallery.setType("image/*");
        startActivityForResult(gallery, REQUEST_PICK_IMAGE);
        fotoURL = null;
    }

    public static void grantPermissionsToUri(Context context, Intent intent, Uri uri) {
        List<ResolveInfo> resInfoList = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resInfoList) {
            String packageName = resolveInfo.activityInfo.packageName;
            context.grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
    }

    private void tomarFoto() {

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this.getActivity(), Manifest.permission.CAMERA)) {
                Toast.makeText(getContext(), R.string.permisoRequerido, Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_READ);
            }
            return;
        }

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = crearArchivoDeImagen();
            } catch (IOException ex) {
                Toast.makeText(getContext(), "Error al guardar la imagen", Toast.LENGTH_SHORT).show();
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this.getContext(), "mobile20171c.utnapp.fileprovider", photoFile);
                grantPermissionsToUri(this.getActivity(), takePictureIntent, photoURI);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
            fotoURL = null;
        }
    }

    private void guardarCambiosEnPerfil() {

        FirebaseUser user = mAuth.getCurrentUser();

        if((fotoURL == null || fotoURL.equals("")) && currentPhotoPath != null) {

            getView().findViewById(R.id.btnCamara).setEnabled(false);
            progressBar.setVisibility(View.VISIBLE);
            Toast.makeText(getContext(),R.string.guardandoPerfil,Toast.LENGTH_SHORT).show();


            Uri file = Uri.fromFile(new File(currentPhotoPath));
            String nombreArchivo = mAuth.getCurrentUser().getUid() + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            StorageReference storageRef = FirebaseStorage.getInstance().getReference();

            storageRef.child(nombreArchivo).putFile(file)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            @SuppressWarnings("VisibleForTests")Uri uri = taskSnapshot.getDownloadUrl();

                            FirebaseUser user = mAuth.getCurrentUser();

                            FirebaseDatabase.getInstance()
                                        .getReference()
                                        .child("usuarios")
                                        .child(user.getUid())
                                        .child("URLphoto")
                                        .setValue(uri.toString());


                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(getContext(),R.string.imagenSubidaOK,Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(getContext(),exception.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
        }

        FirebaseDatabase.getInstance()
                .getReference("usuarios")
                .child(user.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.exists()) {

                            FirebaseUser user = mAuth.getCurrentUser();

                            String nombre = ((EditText) getView().findViewById(R.id.editTextNombre)).getText().toString();

                            FirebaseDatabase.getInstance()
                                    .getReference("usuarios")
                                    .child(user.getUid())
                                    .child("nombre")
                                    .setValue(nombre);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("guardarCambiosEnPerfil", databaseError.toString());
                    }
                });
    }

    private void downloadFromUrl(String urlString) {
        URL url = null;
        try {
            fotoURL = urlString;
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            Toast.makeText(this.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        ImageLoader.instance.execute(UrlRequest.makeRequest(url, new UrlRequest.Listener() {
            @Override
            public void onReceivedBody(int responseCode, byte body[]) {
                final Bitmap bitmap = BitmapFactory.decodeByteArray(body, 0, body.length);
                if (bitmap != null) {
                    try {
                        File file = crearArchivoDeImagen();
                        FileOutputStream output = new FileOutputStream(file);
                        output.write(body);
                        output.close();
                        Intent resultData = new Intent();
                        resultData.putExtra(SELECTED_PICTURE, currentPhotoPath);
                        getActivity().setResult(RESULT_OK, resultData);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (bitmap != null) {
                            selectedImageView.setImageBitmap(bitmap);
                        }
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
            }

            @Override
            public void onError(final Exception e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
            }
        }));
    }

    private class UsrValueEventListener implements ValueEventListener {

        View view;

        public UsrValueEventListener(View view) {
            this.view = view;
        }

        @Override
        public void onDataChange(DataSnapshot snapshot) {
            if (snapshot.exists()) {

                Usuario usuario = snapshot.getValue(Usuario.class);

                if(usuario.URLphoto != null) {
                    downloadFromUrl(usuario.URLphoto);
                }

                ((EditText) view.findViewById(R.id.editTextNombre)).setText((usuario.nombre));
            } else {
                Toast.makeText(getContext(),R.string.errorUsuario,Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Log.e("UsrValueEventListener", databaseError.toString());
        }
    }

}
