package com.android.internal.app;

import android.app.Activity;
import android.app.IUiModeManager;
import android.content.Context;
import android.p009os.Bundle;
import android.p009os.RemoteException;
import android.p009os.ServiceManager;
import android.util.Log;

/* loaded from: classes4.dex */
public class DisableCarModeActivity extends Activity {
    private static final String TAG = "DisableCarModeActivity";

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            IUiModeManager uiModeManager = IUiModeManager.Stub.asInterface(ServiceManager.getService(Context.UI_MODE_SERVICE));
            uiModeManager.disableCarModeByCallingPackage(3, getOpPackageName());
        } catch (RemoteException e) {
            Log.m97e(TAG, "Failed to disable car mode", e);
        }
        finish();
    }
}
