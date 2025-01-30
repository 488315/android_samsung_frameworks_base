package com.android.server.appprelauncher;

import android.app.ActivityOptions;
import android.app.ApplicationExitInfo;
import android.content.Context;
import android.content.Intent;
import android.util.Slog;
import com.android.server.SpegService;
import com.android.server.SystemService;
import com.android.server.am.ActivityManagerService;
import com.samsung.android.rune.CoreRune;

/* loaded from: classes.dex */
public class AppPrelaunchManagerService extends SystemService {
    public AppPrelaunchService mService;

    public AppPrelaunchManagerService(Context context) {
        super(context);
        this.mService = null;
    }

    public void initPrelauncher(SpegService spegService, ActivityManagerService activityManagerService) {
        if (!CoreRune.SYSFW_APP_PREL) {
            Slog.i("PREL", "Prelaunch service is disabled");
            return;
        }
        AppPrelaunchService appPrelaunchService = new AppPrelaunchService(getContext(), spegService, activityManagerService);
        this.mService = appPrelaunchService;
        if (appPrelaunchService.initCoreServices()) {
            this.mService.registerBroadcastReceiver();
            this.mService.bootCompletedBroadcastReceiver();
            publishBinderService("prelauncher", this.mService);
        } else {
            Slog.e("PREL", "Failed to start AppPrelaunchService");
            this.mService = null;
        }
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        Slog.i("PREL", "Manager service started");
    }

    public int startTrackIntent(Intent intent, int i) {
        AppPrelaunchService appPrelaunchService = this.mService;
        if (appPrelaunchService == null) {
            return 0;
        }
        return appPrelaunchService.mIntentTracker.startTrackIntent(intent, i);
    }

    public void stopTrackIntent(int i) {
        AppPrelaunchService appPrelaunchService = this.mService;
        if (appPrelaunchService == null) {
            return;
        }
        appPrelaunchService.mIntentTracker.stopTrackIntent(i);
    }

    public void setStartExecutionComplete(String str, int i) {
        AppPrelaunchService appPrelaunchService = this.mService;
        if (appPrelaunchService == null) {
            return;
        }
        appPrelaunchService.setStartExecutionComplete(str, i);
    }

    public boolean isAppPrelaunched(int i) {
        AppPrelaunchService appPrelaunchService = this.mService;
        if (appPrelaunchService == null) {
            return false;
        }
        return appPrelaunchService.isAppPrelaunched(i);
    }

    public boolean isAppPrelaunched(String str, int i) {
        AppPrelaunchService appPrelaunchService = this.mService;
        if (appPrelaunchService == null) {
            return false;
        }
        return appPrelaunchService.isAppPrelaunched(str, i);
    }

    public int handleActivityExecution(Intent intent, int i, int i2, ActivityOptions activityOptions) {
        AppPrelaunchService appPrelaunchService = this.mService;
        if (appPrelaunchService == null) {
            return -96;
        }
        return appPrelaunchService.handleActivityExecution(intent, i, i2, activityOptions);
    }

    public void setSetupWizardFinished(boolean z) {
        AppPrelaunchService appPrelaunchService = this.mService;
        if (appPrelaunchService == null) {
            return;
        }
        appPrelaunchService.setSetupWizardFinished(z);
    }

    public void setSmartSwitchState(boolean z) {
        AppPrelaunchService appPrelaunchService = this.mService;
        if (appPrelaunchService == null) {
            return;
        }
        appPrelaunchService.setSmartSwitchState(z);
    }

    public void handlePrelaunchedAppDied(ApplicationExitInfo applicationExitInfo) {
        AppPrelaunchService appPrelaunchService = this.mService;
        if (appPrelaunchService == null) {
            return;
        }
        appPrelaunchService.handlePrelaunchedAppDied(applicationExitInfo);
    }

    public void stopPrelaunch(int i, String str) {
        AppPrelaunchService appPrelaunchService = this.mService;
        if (appPrelaunchService == null) {
            return;
        }
        appPrelaunchService.stopPrelaunch(i, str);
    }

    public void setPidForPrelaunchedAppAsync(int i, int i2) {
        AppPrelaunchService appPrelaunchService = this.mService;
        if (appPrelaunchService == null) {
            return;
        }
        appPrelaunchService.setPidForPrelaunchedAppAsync(i, i2);
    }

    public void setTaskProcessedForPrelaunchedAppAsync(int i) {
        AppPrelaunchService appPrelaunchService = this.mService;
        if (appPrelaunchService == null) {
            return;
        }
        appPrelaunchService.setTaskProcessedForPrelaunchedAppAsync(i);
    }
}
