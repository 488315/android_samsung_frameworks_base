package com.android.systemui;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import com.android.systemui.process.ProcessWrapper;

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
