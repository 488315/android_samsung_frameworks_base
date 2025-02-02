package com.samsung.android.biometrics.app.setting;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.IBinder;
import android.util.Log;
import com.samsung.android.biometrics.app.setting.SysUiManager;

/* loaded from: classes.dex */
public class BiometricsUIService extends Service {
    private SysUiManager mSysUiManager;

    public static /* synthetic */ void $r8$lambda$atRg_Q6fnMSNEkydKgjCPnpYtXg(BiometricsUIService biometricsUIService) {
        if (biometricsUIService.mSysUiManager != null) {
            Log.i("BSS_BiometricsUIService", "Init configuration : " + biometricsUIService.getResources().getConfiguration());
            biometricsUIService.mSysUiManager.init();
        }
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        if (Utils.DEBUG) {
            Log.d("BSS_BiometricsUIService", "onBind: " + intent);
        }
        if (this.mSysUiManager == null) {
            this.mSysUiManager = new SysUiManager(this, new SysUiManager.Injector(this));
        }
        getMainThreadHandler().post(new Runnable() { // from class: com.samsung.android.biometrics.app.setting.BiometricsUIService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                BiometricsUIService.$r8$lambda$atRg_Q6fnMSNEkydKgjCPnpYtXg(BiometricsUIService.this);
            }
        });
        return this.mSysUiManager.getSysUiServiceWrapper();
    }

    @Override // android.app.Service, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        Log.i("BSS_BiometricsUIService", "onConfigurationChanged : " + configuration.orientation);
        SysUiManager sysUiManager = this.mSysUiManager;
        if (sysUiManager != null) {
            sysUiManager.onConfigurationChanged(configuration);
        } else {
            Log.w("BSS_BiometricsUIService", "onConfigurationChanged : SysUiManager is null");
        }
    }

    @Override // android.app.Service
    public final void onDestroy() {
        Log.d("BSS_BiometricsUIService", "onDestroy() called");
        SysUiManager sysUiManager = this.mSysUiManager;
        if (sysUiManager != null) {
            sysUiManager.destroy();
        }
        this.mSysUiManager = null;
        super.onDestroy();
    }
}
