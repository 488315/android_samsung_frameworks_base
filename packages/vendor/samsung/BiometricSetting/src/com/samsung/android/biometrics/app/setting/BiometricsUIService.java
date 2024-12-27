package com.samsung.android.biometrics.app.setting;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.IBinder;
import android.util.Log;

import com.samsung.android.biometrics.app.setting.fingerprint.AodStatusMonitor;
import com.samsung.android.biometrics.app.setting.fingerprint.UdfpsIconOptionMonitor;
import com.samsung.android.biometrics.app.setting.fingerprint.UdfpsIconVisibilityNotifier;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
/* loaded from: classes.dex */
public class BiometricsUIService extends Service {
    public SysUiManager mSysUiManager;

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        if (Utils.DEBUG) {
            Log.d("BSS_BiometricsUIService", "onBind: " + intent);
        }
        if (this.mSysUiManager == null) {
            if (!Utils.Config.FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE) {
                this.mSysUiManager =
                        new SysUiManager(
                                this,
                                new DisplayStateManager(this),
                                new PowerServiceProviderImpl(this),
                                new TaskStackObserver(),
                                new FpServiceProviderImpl());
            } else if (Utils.Config.FP_FEATURE_SENSOR_IS_OPTICAL) {
                this.mSysUiManager = new SysUiUdfpsOpticalManager(this);
            } else {
                this.mSysUiManager =
                        new SysUiUdfpsManager(
                                this,
                                new DisplayStateManager(this),
                                new PowerServiceProviderImpl(this),
                                new TaskStackObserver(),
                                new FpServiceProviderImpl(),
                                new AodStatusMonitor(this),
                                new UdfpsIconOptionMonitor(this),
                                new UdfpsIconVisibilityNotifier(this));
            }
        }
        getMainThreadHandler()
                .post(
                        new Runnable() { // from class:
                                         // com.samsung.android.biometrics.app.setting.BiometricsUIService$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                BiometricsUIService biometricsUIService = BiometricsUIService.this;
                                if (biometricsUIService.mSysUiManager != null) {
                                    Log.i(
                                            "BSS_BiometricsUIService",
                                            "Init configuration : "
                                                    + biometricsUIService
                                                            .getResources()
                                                            .getConfiguration());
                                    biometricsUIService.mSysUiManager.init();
                                }
                            }
                        });
        return this.mSysUiManager.mSysUiServiceWrapper;
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
