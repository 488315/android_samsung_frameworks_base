package com.android.internal.app;

import android.app.Activity;
import android.content.Intent;
import android.p009os.Bundle;
import android.p009os.IPowerManager;
import android.p009os.RemoteException;
import android.p009os.ServiceManager;
import android.util.Slog;

/* loaded from: classes4.dex */
public class ShutdownActivity extends Activity {
    private static final String TAG = "ShutdownActivity";
    private boolean mConfirm;
    private boolean mReboot;

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        this.mReboot = Intent.ACTION_REBOOT.equals(intent.getAction());
        this.mConfirm = intent.getBooleanExtra(Intent.EXTRA_KEY_CONFIRM, false);
        final String reason = intent.getStringExtra(Intent.SEM_EXTRA_REBOOT_REASON);
        String str = TAG;
        Slog.m117i(TAG, "onCreate(): reason = " + reason);
        Thread thr = new Thread(str) { // from class: com.android.internal.app.ShutdownActivity.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                IPowerManager pm = IPowerManager.Stub.asInterface(ServiceManager.getService("power"));
                try {
                    if (ShutdownActivity.this.mReboot) {
                        pm.reboot(ShutdownActivity.this.mConfirm, reason, false);
                    } else {
                        pm.shutdown(ShutdownActivity.this.mConfirm, reason, false);
                    }
                } catch (RemoteException e) {
                }
            }
        };
        thr.start();
        finish();
        try {
            thr.join();
        } catch (InterruptedException e) {
        }
    }
}
