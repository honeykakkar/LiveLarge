package com.su.honey.livelarge;

import com.firebase.client.Firebase;
import android.app.Application;
import android.content.Context;
//import android.support.multidex.*;

/**
 * Created by honey on 3/27/2016.
 */
public class FirebaseRVActivity extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //MultiDex.install(this);
    }
}
