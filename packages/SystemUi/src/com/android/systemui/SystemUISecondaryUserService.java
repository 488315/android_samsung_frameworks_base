package com.android.systemui;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class SystemUISecondaryUserService extends Service {
    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public final void onCreate() {
        super.onCreate();
        ((SystemUIApplication) getApplication()).startSecondaryUserServicesIfNeeded();
    }
}
