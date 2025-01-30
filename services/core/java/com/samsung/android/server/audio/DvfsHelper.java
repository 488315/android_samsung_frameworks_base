package com.samsung.android.server.audio;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import com.samsung.android.audio.Rune;
import com.samsung.android.os.SemDvfsManager;

/* loaded from: classes2.dex */
public class DvfsHelper {
    public static DvfsHelper sInstance;
    public final Runnable boostChecker;
    public int mCPUBoostValueForVoIP;
    public SemDvfsManager mCpuStateLock;
    public final DvfsManagerFactory mDvfsManagerFactory;
    public Handler mHandler;
    public boolean mIsCPUBoostedForVoIP;
    public boolean mIsScreenOn;
    public int mRetryDelayMs;
    public SemDvfsManager mSemDvfsCpuMin;

    public interface DvfsManagerFactory {
        SemDvfsManager create(Context context, String str);
    }

    public class DefaultDvfsManagerFactory implements DvfsManagerFactory {
        @Override // com.samsung.android.server.audio.DvfsHelper.DvfsManagerFactory
        public SemDvfsManager create(Context context, String str) {
            return SemDvfsManager.createInstance(context, str);
        }
    }

    public DvfsHelper(Context context, DvfsManagerFactory dvfsManagerFactory) {
        this.mSemDvfsCpuMin = null;
        this.mCpuStateLock = null;
        this.mIsCPUBoostedForVoIP = false;
        this.mIsScreenOn = true;
        this.mRetryDelayMs = 28000;
        this.boostChecker = new Runnable() { // from class: com.samsung.android.server.audio.DvfsHelper$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                DvfsHelper.this.lambda$new$0();
            }
        };
        this.mDvfsManagerFactory = dvfsManagerFactory;
        init(context);
    }

    public void setRetryTime(int i) {
        this.mRetryDelayMs = i;
    }

    public DvfsHelper(Context context) {
        this(context, new DefaultDvfsManagerFactory());
    }

    public static DvfsHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DvfsHelper(context);
        }
        return sInstance;
    }

    public final void init(Context context) {
        SemDvfsManager create = this.mDvfsManagerFactory.create(context, "AUDIOSERVICE_VOIP_BOOST");
        this.mSemDvfsCpuMin = create;
        if (create != null) {
            create.setHint(3300);
        }
        HandlerThread handlerThread = new HandlerThread("AS.DvfsHelper");
        handlerThread.start();
        this.mHandler = new Handler(handlerThread.getLooper());
    }

    public void releaseCPUBoost() {
        if (this.mSemDvfsCpuMin != null) {
            Log.i("AS.DvfsHelper", "release() cpu min lock");
            this.mSemDvfsCpuMin.release();
        }
        if (!Rune.SEC_AUDIO_CPU_STATE_LOCK || this.mCpuStateLock == null) {
            return;
        }
        Log.i("AS.DvfsHelper", "releaseCPUBoost CPU state lock");
        this.mCpuStateLock.release();
    }

    public void acquireCPUBoost() {
        if (this.mSemDvfsCpuMin == null) {
            return;
        }
        Log.i("AS.DvfsHelper", "acquire() cpu min lock for audio VoIP");
        this.mSemDvfsCpuMin.acquire(30000);
        if (Rune.SEC_AUDIO_CPU_STATE_LOCK && this.mCpuStateLock != null) {
            Log.i("AS.DvfsHelper", "acquireCPUBoost CPU state lock");
            this.mCpuStateLock.acquire(30000);
        }
        if (this.mHandler.hasCallbacks(this.boostChecker)) {
            return;
        }
        this.mHandler.postDelayed(this.boostChecker, this.mRetryDelayMs);
    }

    public void startCPUBoostForVoIP(Context context) {
        this.mIsCPUBoostedForVoIP = true;
        if (this.mSemDvfsCpuMin == null) {
            Log.w("AS.DvfsHelper", "DvfsManager is null");
            return;
        }
        if (Rune.SEC_AUDIO_CPU_STATE_LOCK) {
            Log.i("AS.DvfsHelper", "initCPUBoost create cpu state lock");
            SemDvfsManager create = this.mDvfsManagerFactory.create(context, "AS.DvfsHelper");
            this.mCpuStateLock = create;
            if (create != null) {
                create.addResourceValue(268447744, create.getApproximateFrequency(this.mCPUBoostValueForVoIP, 268447744, 1));
            }
        }
        acquireCPUBoost();
    }

    public void stopCPUBoostForVoIP() {
        this.mIsCPUBoostedForVoIP = false;
        if (this.mHandler.hasCallbacks(this.boostChecker)) {
            return;
        }
        this.mHandler.post(this.boostChecker);
    }

    public int getCPUBoostValueForVoIP() {
        return this.mCPUBoostValueForVoIP;
    }

    public void setScreenOn(boolean z) {
        this.mIsScreenOn = z;
    }

    public boolean getIsScreenOn() {
        return this.mIsScreenOn;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        if (this.mIsCPUBoostedForVoIP) {
            acquireCPUBoost();
        } else {
            releaseCPUBoost();
        }
    }
}
