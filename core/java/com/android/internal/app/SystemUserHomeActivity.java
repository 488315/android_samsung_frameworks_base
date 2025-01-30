package com.android.internal.app;

import android.app.Activity;
import android.p009os.Bundle;
import android.util.Log;
import com.android.internal.C4337R;

/* loaded from: classes4.dex */
public class SystemUserHomeActivity extends Activity {
    private static final String TAG = "SystemUserHome";

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.m98i(TAG, "onCreate");
        setContentView(C4337R.layout.system_user_home);
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        Log.m98i(TAG, "onDestroy");
    }
}
