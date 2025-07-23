package com.android.systemui.util.wakelock;

import android.content.Context;
import android.os.PowerManager;
import com.android.systemui.LsRune;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface WakeLock {
    public static final int DEFAULT_LEVELS_AND_FLAGS;
    public static final long DEFAULT_MAX_TIMEOUT = 20000;
    public static final String REASON_WRAP = "wrap";
    public static final String TAG = "WakeLock";

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Builder {
        public static final long NO_TIMEOUT = -1;
        private final Context mContext;
        private final WakeLockLogger mLogger;
        private String mTag;
        private int mLevelsAndFlags = WakeLock.DEFAULT_LEVELS_AND_FLAGS;
        private long mMaxTimeout = WakeLock.DEFAULT_MAX_TIMEOUT;

        public Builder(Context context, WakeLockLogger wakeLockLogger) {
            this.mContext = context;
            this.mLogger = wakeLockLogger;
        }

        public WakeLock build() {
            return WakeLock.createWakeLock(this.mContext, this.mLogger, this.mTag, this.mLevelsAndFlags, this.mMaxTimeout);
        }

        public Builder setLevelsAndFlags(int i) {
            this.mLevelsAndFlags = i;
            return this;
        }

        public Builder setMaxTimeout(long j) {
            this.mMaxTimeout = j;
            return this;
        }

        public Builder setTag(String str) {
            this.mTag = str;
            return this;
        }
    }

    static {
        DEFAULT_LEVELS_AND_FLAGS = LsRune.AOD_SELF_POKE_DRAW_LOCK ? 128 : 1;
    }

    static WakeLock createPartial(Context context, WakeLockLogger wakeLockLogger, String str) {
        return createPartial(context, wakeLockLogger, str, DEFAULT_MAX_TIMEOUT);
    }

    static WakeLock createWakeLock(Context context, WakeLockLogger wakeLockLogger, String str, int i, long j) {
        return wrap(createWakeLockInner(context, str, i), wakeLockLogger, j);
    }

    static PowerManager.WakeLock createWakeLockInner(Context context, String str, int i) {
        return ((PowerManager) context.getSystemService(PowerManager.class)).newWakeLock(i, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static /* synthetic */ void lambda$wrapImpl$0(Runnable runnable, WakeLock wakeLock) {
        try {
            runnable.run();
        } finally {
            wakeLock.release(REASON_WRAP);
        }
    }

    static WakeLock wrap(PowerManager.WakeLock wakeLock, WakeLockLogger wakeLockLogger, long j) {
        return new ClientTrackingWakeLock(wakeLock, wakeLockLogger, j);
    }

    static Runnable wrapImpl(final WakeLock wakeLock, final Runnable runnable) {
        wakeLock.acquire(REASON_WRAP);
        return new Runnable() { // from class: com.android.systemui.util.wakelock.WakeLock$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                WakeLock.lambda$wrapImpl$0(runnable, wakeLock);
            }
        };
    }

    void acquire(String str);

    void release(String str);

    Runnable wrap(Runnable runnable);

    static WakeLock createPartial(Context context, WakeLockLogger wakeLockLogger, String str, long j) {
        return wrap(createWakeLockInner(context, str, DEFAULT_LEVELS_AND_FLAGS), wakeLockLogger, j);
    }
}
