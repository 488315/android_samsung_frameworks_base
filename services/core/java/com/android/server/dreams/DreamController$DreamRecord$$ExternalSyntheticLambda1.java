package com.android.server.dreams;

import android.os.SystemClock;
import android.util.Slog;
import com.android.server.dreams.DreamController;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class DreamController$DreamRecord$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DreamController.DreamRecord f$0;

    public /* synthetic */ DreamController$DreamRecord$$ExternalSyntheticLambda1(DreamController.DreamRecord dreamRecord, int i) {
        this.$r8$classId = i;
        this.f$0 = dreamRecord;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        DreamController.DreamRecord dreamRecord = this.f$0;
        switch (i) {
            case 0:
                DreamController dreamController = dreamRecord.this$0;
                if (dreamController.mCurrentDream == dreamRecord) {
                    dreamController.stopPreviousDreams();
                    break;
                }
                break;
            case 1:
                dreamRecord.releaseWakeLockIfNeeded();
                break;
            case 2:
                if (dreamRecord.mBound && !dreamRecord.mConnected) {
                    Slog.w("DreamController", "Bound dream did not connect in the time allotted");
                    dreamRecord.this$0.stopDream(true, "slow to connect");
                    break;
                }
                break;
            case 3:
                dreamRecord.getClass();
                Slog.w("DreamController", "Stubborn dream did not finish itself in the time allotted");
                dreamRecord.this$0.stopDream(true, "slow to finish");
                dreamRecord.mStopReason = null;
                break;
            case 4:
                dreamRecord.mService = null;
                DreamController dreamController2 = dreamRecord.this$0;
                if (dreamController2.mCurrentDream == dreamRecord) {
                    if (dreamController2.mResetScreenTimeoutOnUnexpectedDreamExit) {
                        Slog.i("DreamController", "Resetting screen timeout");
                        dreamController2.mPowerManager.userActivity(SystemClock.uptimeMillis(), 0, 1);
                    }
                    dreamRecord.this$0.stopDream(true, "service disconnected");
                    break;
                }
                break;
            default:
                dreamRecord.mService = null;
                DreamController dreamController3 = dreamRecord.this$0;
                if (dreamController3.mCurrentDream == dreamRecord) {
                    if (dreamController3.mResetScreenTimeoutOnUnexpectedDreamExit) {
                        Slog.i("DreamController", "Resetting screen timeout");
                        dreamController3.mPowerManager.userActivity(SystemClock.uptimeMillis(), 0, 1);
                    }
                    dreamRecord.this$0.stopDream(true, "binder died");
                    break;
                }
                break;
        }
    }
}
