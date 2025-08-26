package com.android.wm.shell.controlpanel;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import com.android.keyguard.ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0;
import com.samsung.android.rune.CoreRune;

/* loaded from: classes3.dex */
public class ControlPanelService extends Service {
    public boolean mIsNightMode;
    public final int mType = -1;
    public final ControlPanelServiceBinder mBinder = new ControlPanelServiceBinder(this);

    public class ControlPanelServiceBinder extends Binder {
        public ControlPanelServiceBinder(ControlPanelService controlPanelService) {
        }
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return this.mBinder;
    }

    @Override // android.app.Service, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        boolean zIsNightModeActive = configuration.isNightModeActive();
        if (this.mType != 2) {
            this.mIsNightMode = zIsNightModeActive;
        } else {
            if (this.mIsNightMode == zIsNightModeActive) {
                throw null;
            }
            throw null;
        }
    }

    @Override // android.app.Service
    public final void onCreate() {
        super.onCreate();
        boolean z = CoreRune.MW_SPLIT_FLEX_PANEL_MODE_SA_LOGGING;
        Log.d("ControlPanelService", "Floating icon is not available");
    }

    @Override // android.app.Service
    public final int onStartCommand(Intent intent, int i, int i2) {
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i2, "startId : ", "ControlPanelService");
        return 1;
    }
}
