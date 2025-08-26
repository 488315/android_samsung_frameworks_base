package com.android.internal.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.IPowerManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Slog;

/* loaded from: classes5.dex */
public class ShutdownActivity extends Activity {
    private static final String TAG = "ShutdownActivity";
    private boolean mConfirm;
    private boolean mReboot;

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) throws InterruptedException {
        super.onCreate(bundle);
        Intent intent = getIntent();
        this.mReboot = Intent.ACTION_REBOOT.equals(intent.getAction());
        this.mConfirm = intent.getBooleanExtra(Intent.EXTRA_KEY_CONFIRM, false);
        final String stringExtra = intent.getStringExtra(Intent.SEM_EXTRA_REBOOT_REASON);
        String str = TAG;
        Slog.i(TAG, "onCreate(): reason = " + stringExtra);
        Thread thread = new Thread(str) { // from class: com.android.internal.app.ShutdownActivity.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                IPowerManager iPowerManagerAsInterface = IPowerManager.Stub.asInterface(ServiceManager.getService("power"));
                try {
                    if (ShutdownActivity.this.mReboot) {
                        iPowerManagerAsInterface.reboot(ShutdownActivity.this.mConfirm, stringExtra, false);
                    } else {
                        iPowerManagerAsInterface.shutdown(ShutdownActivity.this.mConfirm, stringExtra, false);
                    }
                } catch (RemoteException unused) {
                }
            }
        };
        thread.start();
        finish();
        try {
            thread.join();
        } catch (InterruptedException unused) {
        }
    }
}
