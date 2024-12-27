package com.android.server.wm;

import android.util.Slog;

import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class StartActivityFromRecentsInfo extends DexRestartAppInfo {
    @Override // com.android.server.wm.DexRestartAppInfo
    public final void startResult(
            int i, final ActivityTaskManagerService activityTaskManagerService) {
        if (this.mReusedTask == null) {
            Slog.w("DexController", "startResult: mReusedTask=null");
            return;
        }
        DisplayContent displayContent =
                activityTaskManagerService.mRootWindowContainer.getDisplayContent(i);
        if (displayContent == null) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    i, "startResult: failed, cannot find display#", "DexController");
            return;
        }
        this.mOptions.setLaunchDisplayId(i);
        displayContent.prepareAppTransition(1, 0);
        activityTaskManagerService.mH.post(
                new Runnable() { // from class:
                                 // com.android.server.wm.StartActivityFromRecentsInfo$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        StartActivityFromRecentsInfo startActivityFromRecentsInfo =
                                StartActivityFromRecentsInfo.this;
                        ActivityTaskManagerService activityTaskManagerService2 =
                                activityTaskManagerService;
                        startActivityFromRecentsInfo.getClass();
                        try {
                            activityTaskManagerService2.mDexController.mStartFromRecentInfo = true;
                            activityTaskManagerService2.startActivityFromRecents(
                                    startActivityFromRecentsInfo.mReusedTask.mTaskId,
                                    startActivityFromRecentsInfo.mOptions.toBundle());
                        } catch (IllegalArgumentException unused) {
                        } catch (Throwable th) {
                            activityTaskManagerService2.mDexController.mStartFromRecentInfo = false;
                            throw th;
                        }
                        activityTaskManagerService2.mDexController.mStartFromRecentInfo = false;
                    }
                });
    }

    public final String toString() {
        return "START_ACTIVITY_FROM_RECENTS_TYPE, launchTaskId : 0, processName : "
                + this.mProcessName
                + ", uid : "
                + this.mUid;
    }
}
