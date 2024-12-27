package com.android.internal.app;

import android.app.Activity;
import android.app.IUiModeManager;
import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;

public class DisableCarModeActivity extends Activity {
    private static final String TAG = "DisableCarModeActivity";

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            IUiModeManager uiModeManager =
                    IUiModeManager.Stub.asInterface(
                            ServiceManager.getService(Context.UI_MODE_SERVICE));
            uiModeManager.disableCarModeByCallingPackage(3, getOpPackageName());
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to disable car mode", e);
        }
        finish();
    }
}
