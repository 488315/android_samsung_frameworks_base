package com.android.systemui;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import com.android.systemui.process.ProcessWrapper;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
