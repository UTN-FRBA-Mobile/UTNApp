package mobile20171c.utnapp.Firebase;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import mobile20171c.utnapp.MainActivity;

public class FcmListenerService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage message){

        Map data = message.getData();

        if(data.get("cursoId") != null) {

            Log.d("FcmListenerService", "Mensaje recibido cursoId "+data.get("cursoId").toString());

            Intent intentCurso = new Intent(getApplicationContext(), MainActivity.class);
            intentCurso.putExtra("mensajesCurso", data.get("cursoId").toString());
            startActivity(intentCurso);
        }
    }

}
