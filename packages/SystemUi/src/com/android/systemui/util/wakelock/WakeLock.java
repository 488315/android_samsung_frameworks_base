package com.android.systemui.util.wakelock;

import android.content.Context;
import android.os.PowerManager;
import android.util.Log;
import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.AbstractC0866xb1ce8deb;
import com.android.systemui.LsRune;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import java.util.HashMap;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface WakeLock {
    public static final int DEFAULT_LEVELS_AND_FLAGS;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Builder {
        public final Context mContext;
        public final WakeLockLogger mLogger;
        public String mTag;
        public int mLevelsAndFlags = WakeLock.DEFAULT_LEVELS_AND_FLAGS;
        public long mMaxTimeout = 20000;

        public Builder(Context context, WakeLockLogger wakeLockLogger) {
            this.mContext = context;
            this.mLogger = wakeLockLogger;
        }

        public final WakeLock build() {
            String str = this.mTag;
            int i = this.mLevelsAndFlags;
            return WakeLock.wrap(WakeLock.createWakeLockInner(this.mContext, str, i), this.mLogger, this.mMaxTimeout);
        }
    }

    static {
        DEFAULT_LEVELS_AND_FLAGS = LsRune.AOD_SELF_POKE_DRAW_LOCK ? 128 : 1;
    }

    static WakeLock createPartial(Context context, WakeLockLogger wakeLockLogger, String str) {
        return wrap(createWakeLockInner(context, str, DEFAULT_LEVELS_AND_FLAGS), wakeLockLogger, 20000L);
    }

    static PowerManager.WakeLock createWakeLockInner(Context context, String str, int i) {
        return ((PowerManager) context.getSystemService(PowerManager.class)).newWakeLock(i, str);
    }

    static WakeLock wrap(final PowerManager.WakeLock wakeLock, final WakeLockLogger wakeLockLogger, final long j) {
        return new WakeLock() { // from class: com.android.systemui.util.wakelock.WakeLock.1
            public final HashMap mActiveClients = new HashMap();

            @Override // com.android.systemui.util.wakelock.WakeLock
            public final void acquire(String str) {
                HashMap hashMap = this.mActiveClients;
                hashMap.putIfAbsent(str, 0);
                int intValue = ((Integer) hashMap.get(str)).intValue() + 1;
                hashMap.put(str, Integer.valueOf(intValue));
                PowerManager.WakeLock wakeLock2 = wakeLock;
                WakeLockLogger wakeLockLogger2 = WakeLockLogger.this;
                if (wakeLockLogger2 != null) {
                    wakeLockLogger2.getClass();
                    LogLevel logLevel = LogLevel.DEBUG;
                    WakeLockLogger$logAcquire$2 wakeLockLogger$logAcquire$2 = new Function1() { // from class: com.android.systemui.util.wakelock.WakeLockLogger$logAcquire$2
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            LogMessage logMessage = (LogMessage) obj;
                            String str1 = logMessage.getStr1();
                            String str2 = logMessage.getStr2();
                            int int1 = logMessage.getInt1();
                            StringBuilder m87m = AbstractC0866xb1ce8deb.m87m("Acquire tag=", str1, " reason=", str2, " count=");
                            m87m.append(int1);
                            return m87m.toString();
                        }
                    };
                    LogBuffer logBuffer = wakeLockLogger2.buffer;
                    LogMessage obtain = logBuffer.obtain("WakeLock", logLevel, wakeLockLogger$logAcquire$2, null);
                    obtain.setStr1(wakeLock2.getTag());
                    obtain.setStr2(str);
                    obtain.setInt1(intValue);
                    logBuffer.commit(obtain);
                }
                wakeLock2.acquire(j);
            }

            @Override // com.android.systemui.util.wakelock.WakeLock
            public final void release(String str) {
                HashMap hashMap = this.mActiveClients;
                if (((Integer) hashMap.get(str)) == null) {
                    Log.wtf("WakeLock", KeyAttributes$$ExternalSyntheticOutline0.m21m("Releasing WakeLock with invalid reason: ", str), new Throwable());
                    return;
                }
                Integer valueOf = Integer.valueOf(r1.intValue() - 1);
                if (valueOf.intValue() == 0) {
                    hashMap.remove(str);
                } else {
                    hashMap.put(str, valueOf);
                }
                PowerManager.WakeLock wakeLock2 = wakeLock;
                WakeLockLogger wakeLockLogger2 = WakeLockLogger.this;
                if (wakeLockLogger2 != null) {
                    int intValue = valueOf.intValue();
                    wakeLockLogger2.getClass();
                    LogLevel logLevel = LogLevel.DEBUG;
                    WakeLockLogger$logRelease$2 wakeLockLogger$logRelease$2 = new Function1() { // from class: com.android.systemui.util.wakelock.WakeLockLogger$logRelease$2
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            LogMessage logMessage = (LogMessage) obj;
                            String str1 = logMessage.getStr1();
                            String str2 = logMessage.getStr2();
                            int int1 = logMessage.getInt1();
                            StringBuilder m87m = AbstractC0866xb1ce8deb.m87m("Release tag=", str1, " reason=", str2, " count=");
                            m87m.append(int1);
                            return m87m.toString();
                        }
                    };
                    LogBuffer logBuffer = wakeLockLogger2.buffer;
                    LogMessage obtain = logBuffer.obtain("WakeLock", logLevel, wakeLockLogger$logRelease$2, null);
                    obtain.setStr1(wakeLock2.getTag());
                    obtain.setStr2(str);
                    obtain.setInt1(intValue);
                    logBuffer.commit(obtain);
                }
                wakeLock2.release();
            }

            public final String toString() {
                return "active clients= " + this.mActiveClients;
            }

            @Override // com.android.systemui.util.wakelock.WakeLock
            public final WakeLock$$ExternalSyntheticLambda0 wrap(Runnable runnable) {
                acquire("wrap");
                return new WakeLock$$ExternalSyntheticLambda0(this, runnable);
            }
        };
    }

    void acquire(String str);

    void release(String str);

    WakeLock$$ExternalSyntheticLambda0 wrap(Runnable runnable);
}
