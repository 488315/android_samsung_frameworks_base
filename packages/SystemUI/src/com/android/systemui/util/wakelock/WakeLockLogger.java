package com.android.systemui.util.wakelock;

import android.os.PowerManager;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class WakeLockLogger {
    public static final int $stable = 8;
    private final LogBuffer buffer;

    public WakeLockLogger(@WakeLockLog LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    public final void logAcquire(PowerManager.WakeLock wakeLock, String str, int i) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain(WakeLock.TAG, LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.util.wakelock.WakeLockLogger$logAcquire$2
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                String str2 = logMessage.getStr2();
                int int1 = logMessage.getInt1();
                StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("Acquire tag=", str1, " reason=", str2, " count=");
                m.append(int1);
                return m.toString();
            }
        }, null);
        ((LogMessageImpl) obtain).str1 = wakeLock.getTag();
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str2 = str;
        logMessageImpl.int1 = i;
        logBuffer.commit(obtain);
    }

    public final void logRelease(PowerManager.WakeLock wakeLock, String str, int i) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain(WakeLock.TAG, LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.util.wakelock.WakeLockLogger$logRelease$2
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                String str2 = logMessage.getStr2();
                int int1 = logMessage.getInt1();
                StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("Release tag=", str1, " reason=", str2, " count=");
                m.append(int1);
                return m.toString();
            }
        }, null);
        ((LogMessageImpl) obtain).str1 = wakeLock.getTag();
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str2 = str;
        logMessageImpl.int1 = i;
        logBuffer.commit(obtain);
    }
}
