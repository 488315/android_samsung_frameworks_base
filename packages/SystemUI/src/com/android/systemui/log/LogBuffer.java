package com.android.systemui.log;

import android.os.Trace;
import android.util.Log;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import com.android.systemui.common.buffer.RingBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.log.core.MessageBuffer;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class LogBuffer implements MessageBuffer {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final RingBuffer buffer;
    public boolean frozen;
    public final LogcatEchoTracker logcatEchoTracker;
    public final int maxSize;
    public final String name;
    public final boolean systrace;
    public final String systraceTrackName;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[LogLevel.values().length];
            try {
                iArr[LogLevel.VERBOSE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[LogLevel.DEBUG.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[LogLevel.INFO.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[LogLevel.WARNING.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[LogLevel.ERROR.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[LogLevel.WTF.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        new Companion(null);
    }

    public LogBuffer(String str, int i, LogcatEchoTracker logcatEchoTracker) {
        this(str, i, logcatEchoTracker, false, null, 24, null);
    }

    public static void log$default(LogBuffer logBuffer, String str, LogLevel logLevel, String str2) {
        LogMessage logMessageObtain = logBuffer.obtain(str, logLevel, new LogBuffer$$ExternalSyntheticLambda0(0), null);
        ((LogMessageImpl) logMessageObtain).str1 = str2;
        logBuffer.commit(logMessageObtain);
    }

    @Override // com.android.systemui.log.core.MessageBuffer
    public final synchronized void commit(LogMessage logMessage) {
        if (!this.frozen && this.maxSize > 0) {
            echoToDesiredEndpoints(logMessage);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void echoToDesiredEndpoints(LogMessage logMessage) {
        boolean z;
        LogLevel level = logMessage.getLevel();
        LogcatEchoTracker logcatEchoTracker = this.logcatEchoTracker;
        String str = this.name;
        if (!logcatEchoTracker.isBufferLoggable(level, str)) {
            z = logcatEchoTracker.isTagLoggable(logMessage.getLevel(), logMessage.getTag());
        }
        boolean z2 = this.systrace && Trace.isTagEnabled(4096L);
        if (z || z2) {
            String str2 = (String) logMessage.getMessagePrinter().mo781invoke(logMessage);
            if (z) {
                switch (WhenMappings.$EnumSwitchMapping$0[logMessage.getLevel().ordinal()]) {
                    case 1:
                        logMessage.getTag();
                        logMessage.getException();
                        break;
                    case 2:
                        Log.d(logMessage.getTag(), str2, logMessage.getException());
                        break;
                    case 3:
                        Log.i(logMessage.getTag(), str2, logMessage.getException());
                        break;
                    case 4:
                        Log.w(logMessage.getTag(), str2, logMessage.getException());
                        break;
                    case 5:
                        Log.e(logMessage.getTag(), str2, logMessage.getException());
                        break;
                    case 6:
                        Log.wtf(logMessage.getTag(), str2, logMessage.getException());
                        break;
                    default:
                        throw new NoWhenBranchMatchedException();
                }
            }
            if (z2) {
                LogLevel level2 = logMessage.getLevel();
                String tag = logMessage.getTag();
                if (Trace.isEnabled()) {
                    String shortString = level2.getShortString();
                    StringBuilder sb = new StringBuilder();
                    sb.append(str);
                    sb.append(" - ");
                    sb.append(shortString);
                    sb.append(" ");
                    sb.append(tag);
                    Trace.instantForTrack(4096L, this.systraceTrackName, TransitionKt$$ExternalSyntheticOutline0.m(sb, ": ", str2));
                }
            }
        }
    }

    public final synchronized void freeze() {
        if (!this.frozen) {
            LogMessage logMessageObtain = obtain("LogBuffer", LogLevel.DEBUG, new LogBuffer$$ExternalSyntheticLambda0(2), null);
            ((LogMessageImpl) logMessageObtain).str1 = this.name;
            commit(logMessageObtain);
            this.frozen = true;
        }
    }

    @Override // com.android.systemui.log.core.MessageBuffer
    public final synchronized LogMessage obtain(String str, LogLevel logLevel, Function1 function1, Throwable th) {
        if (!(!this.frozen && this.maxSize > 0)) {
            return LogBufferKt.FROZEN_MESSAGE;
        }
        LogMessageImpl logMessageImpl = (LogMessageImpl) this.buffer.advance();
        logMessageImpl.reset(str, logLevel, System.currentTimeMillis(), function1, th);
        return logMessageImpl;
    }

    public final synchronized void unfreeze() {
        if (this.frozen) {
            this.frozen = false;
            LogMessage logMessageObtain = obtain("LogBuffer", LogLevel.DEBUG, new LogBuffer$$ExternalSyntheticLambda0(1), null);
            ((LogMessageImpl) logMessageObtain).str1 = this.name;
            commit(logMessageObtain);
        }
    }

    public LogBuffer(String str, int i, LogcatEchoTracker logcatEchoTracker, boolean z) {
        this(str, i, logcatEchoTracker, z, null, 16, null);
    }

    public LogBuffer(String str, int i, LogcatEchoTracker logcatEchoTracker, boolean z, String str2) {
        this.name = str;
        this.maxSize = i;
        this.logcatEchoTracker = logcatEchoTracker;
        this.systrace = z;
        this.systraceTrackName = str2;
        this.buffer = new RingBuffer(i, new LogBuffer$$ExternalSyntheticLambda1());
    }

    public /* synthetic */ LogBuffer(String str, int i, LogcatEchoTracker logcatEchoTracker, boolean z, String str2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, i, logcatEchoTracker, (i2 & 8) != 0 ? true : z, (i2 & 16) != 0 ? "UI Events" : str2);
    }
}
