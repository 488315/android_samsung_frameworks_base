package com.android.systemui;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import com.android.systemui.process.ProcessWrapper;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class SystemUISecondaryUserService extends Service {
    public final ProcessWrapper mProcessWrapper;

    public SystemUISecondaryUserService(ProcessWrapper processWrapper) {
        this.mProcessWrapper = processWrapper;
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public final void onCreate() {
        super.onCreate();
        this.mProcessWrapper.getClass();
        if (!ProcessWrapper.isSystemUser()) {
            ((SystemUIApplication) getApplication()).startSecondaryUserServicesIfNeeded();
        } else {
            Log.w("SysUISecondaryService", "SecondaryServices started for System User. Stopping it.");
            stopSelf();
        }
    }
}
