package com.android.systemui.util.wakelock;

import android.content.Context;
import android.os.PowerManager;
import android.util.Log;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.systemui.Flags;
import com.android.systemui.LsRune;
import java.util.HashMap;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface WakeLock {
    public static final int DEFAULT_LEVELS_AND_FLAGS;
    public static final long DEFAULT_MAX_TIMEOUT = 20000;
    public static final String REASON_WRAP = "wrap";
    public static final String TAG = "WakeLock";

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.util.wakelock.WakeLock$1, reason: invalid class name */
    class AnonymousClass1 implements WakeLock {
        private final HashMap<String, Integer> mActiveClients = new HashMap<>();
        final /* synthetic */ PowerManager.WakeLock val$inner;
        final /* synthetic */ WakeLockLogger val$logger;
        final /* synthetic */ long val$maxTimeout;

        public AnonymousClass1(WakeLockLogger wakeLockLogger, PowerManager.WakeLock wakeLock, long j) {
            this.val$logger = wakeLockLogger;
            this.val$inner = wakeLock;
            this.val$maxTimeout = j;
        }

        @Override // com.android.systemui.util.wakelock.WakeLock
        public void acquire(String str) {
            this.mActiveClients.putIfAbsent(str, 0);
            int intValue = this.mActiveClients.get(str).intValue() + 1;
            this.mActiveClients.put(str, Integer.valueOf(intValue));
            WakeLockLogger wakeLockLogger = this.val$logger;
            if (wakeLockLogger != null) {
                wakeLockLogger.logAcquire(this.val$inner, str, intValue);
            }
            long j = this.val$maxTimeout;
            if (j == -1) {
                this.val$inner.acquire();
            } else {
                this.val$inner.acquire(j);
            }
        }

        @Override // com.android.systemui.util.wakelock.WakeLock
        public void release(String str) {
            Integer num = this.mActiveClients.get(str);
            if (num == null) {
                Log.wtf(WakeLock.TAG, AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Releasing WakeLock with invalid reason: ", str), new Throwable());
                return;
            }
            int intValue = num.intValue() - 1;
            Integer valueOf = Integer.valueOf(intValue);
            if (intValue == 0) {
                this.mActiveClients.remove(str);
            } else {
                this.mActiveClients.put(str, valueOf);
            }
            WakeLockLogger wakeLockLogger = this.val$logger;
            if (wakeLockLogger != null) {
                wakeLockLogger.logRelease(this.val$inner, str, intValue);
            }
            this.val$inner.release();
        }

        public String toString() {
            return "active clients= " + this.mActiveClients;
        }

        @Override // com.android.systemui.util.wakelock.WakeLock
        public Runnable wrap(Runnable runnable) {
            return WakeLock.wrapImpl(this, runnable);
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        Flags.FEATURE_FLAGS.getClass();
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
