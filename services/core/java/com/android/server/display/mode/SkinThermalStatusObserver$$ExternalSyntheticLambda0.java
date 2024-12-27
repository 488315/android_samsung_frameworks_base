package com.android.server.display.mode;

import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class SkinThermalStatusObserver$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SkinThermalStatusObserver f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ SkinThermalStatusObserver$$ExternalSyntheticLambda0(SkinThermalStatusObserver skinThermalStatusObserver, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = skinThermalStatusObserver;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i;
        SparseArray sparseArray;
        switch (this.$r8$classId) {
            case 0:
                SkinThermalStatusObserver skinThermalStatusObserver = this.f$0;
                int i2 = this.f$1;
                synchronized (skinThermalStatusObserver.mThermalObserverLock) {
                    i = skinThermalStatusObserver.mStatus;
                    sparseArray = (SparseArray) skinThermalStatusObserver.mThermalThrottlingByDisplay.get(i2);
                }
                if (sparseArray == null) {
                    AnyMotionDetector$$ExternalSyntheticOutline0.m(i2, "Updating votes, display already removed, display=", "SkinThermalStatusObserver");
                    return;
                }
                if (skinThermalStatusObserver.mLoggingEnabled) {
                    StringBuilder m = ArrayUtils$$ExternalSyntheticOutline0.m(i, i2, "Updating votes for status=", ", display =", ", map=");
                    m.append(sparseArray);
                    Slog.d("SkinThermalStatusObserver", m.toString());
                }
                skinThermalStatusObserver.reportThrottlingIfNeeded(i2, i, sparseArray);
                return;
            default:
                SkinThermalStatusObserver skinThermalStatusObserver2 = this.f$0;
                skinThermalStatusObserver2.mVotesStorage.updateVote(this.f$1, 22, null);
                return;
        }
    }
}
