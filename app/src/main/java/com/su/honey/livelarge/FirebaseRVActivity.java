package com.su.honey.livelarge;

import com.firebase.client.Firebase;
import android.app.Application;
import android.widget.Toast;

/**
 * Created by honey on 3/27/2016.
 */
public class FirebaseRVActivity extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}