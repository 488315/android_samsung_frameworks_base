package com.android.server.sepunion.cover;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.android.server.LocalServices;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.android.server.wm.ActivityTaskManagerService;
import com.android.server.wm.ActivityTaskManagerService.SleepTokenAcquirerImpl;

import com.samsung.android.sepunion.Log;

public final class SleepTokenAcquirer {
    public final ActivityTaskManagerInternal mActivityTaskManagerInternal =
            (ActivityTaskManagerInternal)
                    LocalServices.getService(ActivityTaskManagerInternal.class);
    public ActivityTaskManagerService.SleepTokenAcquirerImpl mDefaultAcquirer;
    public final SleepTokenAcquirer$$ExternalSyntheticLambda0 mDefaultSleepToken;
    public final Handler mHandler;
    public boolean mIsCoverAppCovered;
    public final SleepTokenAcquirer$$ExternalSyntheticLambda0 mSleepTokenTask;
    public boolean mSwitchState;

    public SleepTokenAcquirer(Context context, Looper looper) {
        final int i = 0;
        this.mSleepTokenTask =
                new Runnable(
                        this) { // from class:
                                // com.android.server.sepunion.cover.SleepTokenAcquirer$$ExternalSyntheticLambda0
                    public final /* synthetic */ SleepTokenAcquirer f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        ActivityTaskManagerService.SleepTokenAcquirerImpl sleepTokenAcquirer;
                        int i2 = i;
                        SleepTokenAcquirer sleepTokenAcquirer2 = this.f$0;
                        switch (i2) {
                            case 0:
                                boolean z = sleepTokenAcquirer2.mSwitchState;
                                Handler handler = sleepTokenAcquirer2.mHandler;
                                SleepTokenAcquirer$$ExternalSyntheticLambda0
                                        sleepTokenAcquirer$$ExternalSyntheticLambda0 =
                                                sleepTokenAcquirer2.mDefaultSleepToken;
                                if (!z && !sleepTokenAcquirer2.mIsCoverAppCovered) {
                                    handler.removeCallbacks(
                                            sleepTokenAcquirer$$ExternalSyntheticLambda0);
                                    handler.postDelayed(
                                            sleepTokenAcquirer$$ExternalSyntheticLambda0, 200L);
                                    break;
                                } else if (!handler.hasCallbacks(
                                        sleepTokenAcquirer$$ExternalSyntheticLambda0)) {
                                    ActivityTaskManagerService.SleepTokenAcquirerImpl
                                            sleepTokenAcquirer3 =
                                                    sleepTokenAcquirer2.getSleepTokenAcquirer();
                                    if (sleepTokenAcquirer3 != null) {
                                        Log.d(
                                                "CoverManager_SleepTokenAcquirer",
                                                "releaseSleepToken 0");
                                        sleepTokenAcquirer3.release(0);
                                        break;
                                    }
                                } else {
                                    handler.removeCallbacks(
                                            sleepTokenAcquirer$$ExternalSyntheticLambda0);
                                    break;
                                }
                                break;
                            default:
                                if (!sleepTokenAcquirer2.mSwitchState
                                        && !sleepTokenAcquirer2.mIsCoverAppCovered
                                        && (sleepTokenAcquirer =
                                                        sleepTokenAcquirer2.getSleepTokenAcquirer())
                                                != null) {
                                    Log.d("CoverManager_SleepTokenAcquirer", "acquireSleepToken 0");
                                    sleepTokenAcquirer.acquire(0, false);
                                    break;
                                }
                                break;
                        }
                    }
                };
        final int i2 = 1;
        this.mDefaultSleepToken =
                new Runnable(
                        this) { // from class:
                                // com.android.server.sepunion.cover.SleepTokenAcquirer$$ExternalSyntheticLambda0
                    public final /* synthetic */ SleepTokenAcquirer f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        ActivityTaskManagerService.SleepTokenAcquirerImpl sleepTokenAcquirer;
                        int i22 = i2;
                        SleepTokenAcquirer sleepTokenAcquirer2 = this.f$0;
                        switch (i22) {
                            case 0:
                                boolean z = sleepTokenAcquirer2.mSwitchState;
                                Handler handler = sleepTokenAcquirer2.mHandler;
                                SleepTokenAcquirer$$ExternalSyntheticLambda0
                                        sleepTokenAcquirer$$ExternalSyntheticLambda0 =
                                                sleepTokenAcquirer2.mDefaultSleepToken;
                                if (!z && !sleepTokenAcquirer2.mIsCoverAppCovered) {
                                    handler.removeCallbacks(
                                            sleepTokenAcquirer$$ExternalSyntheticLambda0);
                                    handler.postDelayed(
                                            sleepTokenAcquirer$$ExternalSyntheticLambda0, 200L);
                                    break;
                                } else if (!handler.hasCallbacks(
                                        sleepTokenAcquirer$$ExternalSyntheticLambda0)) {
                                    ActivityTaskManagerService.SleepTokenAcquirerImpl
                                            sleepTokenAcquirer3 =
                                                    sleepTokenAcquirer2.getSleepTokenAcquirer();
                                    if (sleepTokenAcquirer3 != null) {
                                        Log.d(
                                                "CoverManager_SleepTokenAcquirer",
                                                "releaseSleepToken 0");
                                        sleepTokenAcquirer3.release(0);
                                        break;
                                    }
                                } else {
                                    handler.removeCallbacks(
                                            sleepTokenAcquirer$$ExternalSyntheticLambda0);
                                    break;
                                }
                                break;
                            default:
                                if (!sleepTokenAcquirer2.mSwitchState
                                        && !sleepTokenAcquirer2.mIsCoverAppCovered
                                        && (sleepTokenAcquirer =
                                                        sleepTokenAcquirer2.getSleepTokenAcquirer())
                                                != null) {
                                    Log.d("CoverManager_SleepTokenAcquirer", "acquireSleepToken 0");
                                    sleepTokenAcquirer.acquire(0, false);
                                    break;
                                }
                                break;
                        }
                    }
                };
        this.mHandler = new Handler(looper);
    }

    public final ActivityTaskManagerService.SleepTokenAcquirerImpl getSleepTokenAcquirer() {
        if (this.mDefaultAcquirer == null) {
            Log.d("CoverManager_SleepTokenAcquirer", "createSleepTokenAcquirer 0");
            this.mDefaultAcquirer =
                    ActivityTaskManagerService.this.new SleepTokenAcquirerImpl("cover");
        }
        return this.mDefaultAcquirer;
    }
}
