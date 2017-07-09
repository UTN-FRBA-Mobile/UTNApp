package mobile20171c.utnapp.Firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import mobile20171c.utnapp.MainActivity;
import mobile20171c.utnapp.R;

public class FcmListenerService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage message){

        Map data = message.getData();

        if(data.get("cursoId") != null) {

            Log.d("FcmListenerService", "Mensaje recibido cursoId "+data.get("cursoId").toString());

            Intent intentCurso = new Intent(getApplicationContext(), MainActivity.class);
            intentCurso.putExtra("mensajesCurso", data.get("cursoId").toString());
            intentCurso.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            PendingIntent resultIntent = PendingIntent.getActivity(this , 0, intentCurso, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder mNotificationBuilder = new NotificationCompat.Builder( this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(data.get("title").toString())
                    .setContentText(data.get("body").toString())
                    .setAutoCancel(true)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setContentIntent(resultIntent);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0, mNotificationBuilder.build());
        }
    }

}
