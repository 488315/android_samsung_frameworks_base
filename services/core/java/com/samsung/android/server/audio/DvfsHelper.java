package com.samsung.android.server.audio;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.android.server.KnoxCaptureInputFilter$$ExternalSyntheticOutline0;

import com.samsung.android.audio.Rune;
import com.samsung.android.os.SemDvfsManager;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class DvfsHelper {
    public static DvfsHelper sInstance;
    public final DvfsManagerFactory mDvfsManagerFactory;
    public final Handler mHandler;
    public final SemDvfsManager mSemDvfsCpuMin;
    public SemDvfsManager mCpuStateLock = null;
    public boolean mIsCPUBoostedForVoIP = false;
    public boolean mIsScreenOn = true;
    public int mRetryDelayMs = 28000;
    public final DvfsHelper$$ExternalSyntheticLambda0 boostChecker =
            new Runnable() { // from class:
                             // com.samsung.android.server.audio.DvfsHelper$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    DvfsHelper dvfsHelper = DvfsHelper.this;
                    if (dvfsHelper.mIsCPUBoostedForVoIP) {
                        dvfsHelper.acquireCPUBoost();
                        return;
                    }
                    if (dvfsHelper.mSemDvfsCpuMin != null) {
                        Log.i("AS.DvfsHelper", "release() cpu min lock");
                        dvfsHelper.mSemDvfsCpuMin.release();
                    }
                    if (!Rune.SEC_AUDIO_CPU_STATE_LOCK || dvfsHelper.mCpuStateLock == null) {
                        return;
                    }
                    Log.i("AS.DvfsHelper", "releaseCPUBoost CPU state lock");
                    dvfsHelper.mCpuStateLock.release();
                }
            };

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DefaultDvfsManagerFactory implements DvfsManagerFactory {}

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface DvfsManagerFactory {}

    /* JADX WARN: Type inference failed for: r0v4, types: [com.samsung.android.server.audio.DvfsHelper$$ExternalSyntheticLambda0] */
    public DvfsHelper(Context context, DvfsManagerFactory dvfsManagerFactory) {
        this.mSemDvfsCpuMin = null;
        this.mDvfsManagerFactory = dvfsManagerFactory;
        ((DefaultDvfsManagerFactory) dvfsManagerFactory).getClass();
        SemDvfsManager createInstance =
                SemDvfsManager.createInstance(context, "AUDIOSERVICE_VOIP_BOOST");
        this.mSemDvfsCpuMin = createInstance;
        if (createInstance != null) {
            createInstance.setHint(3300);
        }
        this.mHandler =
                new Handler(
                        KnoxCaptureInputFilter$$ExternalSyntheticOutline0.m("AS.DvfsHelper")
                                .getLooper());
    }

    public final void acquireCPUBoost() {
        if (this.mSemDvfsCpuMin == null) {
            return;
        }
        Log.i("AS.DvfsHelper", "acquire() cpu min lock for audio VoIP");
        this.mSemDvfsCpuMin.acquire(30000);
        if (Rune.SEC_AUDIO_CPU_STATE_LOCK && this.mCpuStateLock != null) {
            Log.i("AS.DvfsHelper", "acquireCPUBoost CPU state lock");
            this.mCpuStateLock.acquire(30000);
        }
        Handler handler = this.mHandler;
        DvfsHelper$$ExternalSyntheticLambda0 dvfsHelper$$ExternalSyntheticLambda0 =
                this.boostChecker;
        if (handler.hasCallbacks(dvfsHelper$$ExternalSyntheticLambda0)) {
            return;
        }
        this.mHandler.postDelayed(dvfsHelper$$ExternalSyntheticLambda0, this.mRetryDelayMs);
    }

    public void setRetryTime(int i) {
        this.mRetryDelayMs = i;
    }
}
