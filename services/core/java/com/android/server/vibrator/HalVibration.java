package com.android.server.vibrator;

import android.os.CombinedVibration;
import android.os.IBinder;
import android.os.VibrationEffect;
import android.util.SparseArray;
import com.android.server.vibrator.Vibration;
import com.samsung.android.server.vibrator.CommonPatternInfo;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class HalVibration extends Vibration {
    public float mAdaptiveScale;
    public final CommonPatternInfo[] mCommonData;
    public final CountDownLatch mCompletionLatch;
    public volatile CombinedVibration mEffectToPlay;
    public final int[] mEngineData;
    public final SparseArray mFallbacks;
    public int mMagnitude;
    public final CombinedVibration mOriginalEffect;
    public int mScaleLevel;
    public Vibration.Status mStatus;
    public final long mTimeout;

    public HalVibration(IBinder iBinder, CombinedVibration combinedVibration, long j, int i, int[] iArr, CommonPatternInfo[] commonPatternInfoArr, Vibration.CallerInfo callerInfo) {
        this(iBinder, combinedVibration, callerInfo);
        this.mTimeout = j;
        this.mMagnitude = i;
        this.mEngineData = iArr;
        this.mCommonData = commonPatternInfoArr;
    }

    public HalVibration(IBinder iBinder, CombinedVibration combinedVibration, Vibration.CallerInfo callerInfo) {
        super(iBinder, callerInfo);
        this.mFallbacks = new SparseArray();
        this.mCompletionLatch = new CountDownLatch(1);
        this.mTimeout = -1L;
        this.mEngineData = null;
        this.mMagnitude = 0;
        this.mCommonData = null;
        this.mOriginalEffect = combinedVibration;
        this.mEffectToPlay = combinedVibration;
        this.mStatus = Vibration.Status.RUNNING;
        this.mScaleLevel = 0;
        this.mAdaptiveScale = 1.0f;
    }

    public final Vibration.DebugInfo getDebugInfo() {
        return new Vibration.DebugInfo(this.mStatus, this.stats, this.mEffectToPlay, Objects.equals(this.mOriginalEffect, this.mEffectToPlay) ? null : this.mOriginalEffect, this.mScaleLevel, this.mAdaptiveScale, this.callerInfo);
    }

    @Override // com.android.server.vibrator.Vibration
    public final boolean isRepeating() {
        return this.mOriginalEffect.getDuration() == Long.MAX_VALUE;
    }

    public final void resolveEffects(int i) {
        CombinedVibration transform = this.mEffectToPlay.transform(new HalVibration$$ExternalSyntheticLambda0(), Integer.valueOf(i));
        if (!Objects.equals(this.mEffectToPlay, transform)) {
            this.mEffectToPlay = transform;
        }
        for (int i2 = 0; i2 < this.mFallbacks.size(); i2++) {
            SparseArray sparseArray = this.mFallbacks;
            sparseArray.setValueAt(i2, ((VibrationEffect) sparseArray.valueAt(i2)).resolve(i));
        }
    }
}
