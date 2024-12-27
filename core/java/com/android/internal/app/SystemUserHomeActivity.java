package com.android.internal.app;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.android.internal.R;

public class SystemUserHomeActivity extends Activity {
    private static final String TAG = "SystemUserHome";

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        setContentView(R.layout.system_user_home);
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }
}
