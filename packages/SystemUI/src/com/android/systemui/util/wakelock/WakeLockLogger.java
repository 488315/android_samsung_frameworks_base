package com.android.systemui.util.wakelock;

import android.os.PowerManager;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class WakeLockLogger {
    public static final int $stable = 8;
    private final LogBuffer buffer;

    public WakeLockLogger(@WakeLockLog LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logAcquire$lambda$1(LogMessage logMessage) {
        String str1 = logMessage.getStr1();
        String str2 = logMessage.getStr2();
        int int1 = logMessage.getInt1();
        StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("Acquire tag=", str1, " reason=", str2, " count=");
        m.append(int1);
        return m.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logRelease$lambda$3(LogMessage logMessage) {
        String str1 = logMessage.getStr1();
        String str2 = logMessage.getStr2();
        int int1 = logMessage.getInt1();
        StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("Release tag=", str1, " reason=", str2, " count=");
        m.append(int1);
        return m.toString();
    }

    public final void logAcquire(PowerManager.WakeLock wakeLock, String str, int i) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain(WakeLock.TAG, LogLevel.DEBUG, new WakeLockLogger$$ExternalSyntheticLambda0(0), null);
        ((LogMessageImpl) obtain).str1 = wakeLock.getTag();
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str2 = str;
        logMessageImpl.int1 = i;
        logBuffer.commit(obtain);
    }

    public final void logRelease(PowerManager.WakeLock wakeLock, String str, int i) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain(WakeLock.TAG, LogLevel.DEBUG, new WakeLockLogger$$ExternalSyntheticLambda0(1), null);
        ((LogMessageImpl) obtain).str1 = wakeLock.getTag();
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str2 = str;
        logMessageImpl.int1 = i;
        logBuffer.commit(obtain);
    }
}
