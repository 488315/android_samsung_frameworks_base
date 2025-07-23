package com.android.systemui.log;

import com.android.systemui.common.buffer.RingBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.log.core.MessageBuffer;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        LogMessage obtain = logBuffer.obtain(str, logLevel, new LogBuffer$$ExternalSyntheticLambda0(0), null);
        ((LogMessageImpl) obtain).str1 = str2;
        logBuffer.commit(obtain);
    }

    @Override // com.android.systemui.log.core.MessageBuffer
    public final synchronized void commit(LogMessage logMessage) {
        if (!this.frozen && this.maxSize > 0) {
            echoToDesiredEndpoints(logMessage);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0040  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0099  */
    /* JADX WARN: Removed duplicated region for block: B:32:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void echoToDesiredEndpoints(com.android.systemui.log.core.LogMessage r8) {
        /*
            r7 = this;
            com.android.systemui.log.core.LogLevel r0 = r8.getLevel()
            com.android.systemui.log.LogcatEchoTracker r1 = r7.logcatEchoTracker
            java.lang.String r2 = r7.name
            boolean r0 = r1.isBufferLoggable(r0, r2)
            r3 = 1
            r4 = 0
            if (r0 != 0) goto L21
            java.lang.String r0 = r8.getTag()
            com.android.systemui.log.core.LogLevel r5 = r8.getLevel()
            boolean r0 = r1.isTagLoggable(r5, r0)
            if (r0 == 0) goto L1f
            goto L21
        L1f:
            r0 = r4
            goto L22
        L21:
            r0 = r3
        L22:
            boolean r1 = r7.systrace
            r5 = 4096(0x1000, double:2.0237E-320)
            if (r1 == 0) goto L2f
            boolean r1 = android.os.Trace.isTagEnabled(r5)
            if (r1 == 0) goto L2f
            goto L30
        L2f:
            r3 = r4
        L30:
            if (r0 != 0) goto L34
            if (r3 == 0) goto Lcf
        L34:
            kotlin.jvm.functions.Function1 r1 = r8.getMessagePrinter()
            java.lang.Object r1 = r1.mo779invoke(r8)
            java.lang.String r1 = (java.lang.String) r1
            if (r0 == 0) goto L97
            com.android.systemui.log.core.LogLevel r0 = r8.getLevel()
            int[] r4 = com.android.systemui.log.LogBuffer.WhenMappings.$EnumSwitchMapping$0
            int r0 = r0.ordinal()
            r0 = r4[r0]
            switch(r0) {
                case 1: goto L91;
                case 2: goto L85;
                case 3: goto L79;
                case 4: goto L6d;
                case 5: goto L61;
                case 6: goto L55;
                default: goto L4f;
            }
        L4f:
            kotlin.NoWhenBranchMatchedException r7 = new kotlin.NoWhenBranchMatchedException
            r7.<init>()
            throw r7
        L55:
            java.lang.String r0 = r8.getTag()
            java.lang.Throwable r4 = r8.getException()
            android.util.Log.wtf(r0, r1, r4)
            goto L97
        L61:
            java.lang.String r0 = r8.getTag()
            java.lang.Throwable r4 = r8.getException()
            android.util.Log.e(r0, r1, r4)
            goto L97
        L6d:
            java.lang.String r0 = r8.getTag()
            java.lang.Throwable r4 = r8.getException()
            android.util.Log.w(r0, r1, r4)
            goto L97
        L79:
            java.lang.String r0 = r8.getTag()
            java.lang.Throwable r4 = r8.getException()
            android.util.Log.i(r0, r1, r4)
            goto L97
        L85:
            java.lang.String r0 = r8.getTag()
            java.lang.Throwable r4 = r8.getException()
            android.util.Log.d(r0, r1, r4)
            goto L97
        L91:
            r8.getTag()
            r8.getException()
        L97:
            if (r3 == 0) goto Lcf
            com.android.systemui.log.core.LogLevel r0 = r8.getLevel()
            java.lang.String r8 = r8.getTag()
            boolean r3 = android.os.Trace.isEnabled()
            if (r3 != 0) goto La8
            goto Lcf
        La8:
            java.lang.String r0 = r0.getShortString()
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r2)
            java.lang.String r2 = " - "
            r3.append(r2)
            r3.append(r0)
            java.lang.String r0 = " "
            r3.append(r0)
            r3.append(r8)
            java.lang.String r8 = ": "
            java.lang.String r8 = androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0.m(r3, r8, r1)
            java.lang.String r7 = r7.systraceTrackName
            android.os.Trace.instantForTrack(r5, r7, r8)
        Lcf:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.log.LogBuffer.echoToDesiredEndpoints(com.android.systemui.log.core.LogMessage):void");
    }

    public final synchronized void freeze() {
        if (!this.frozen) {
            LogMessage obtain = obtain("LogBuffer", LogLevel.DEBUG, new LogBuffer$$ExternalSyntheticLambda0(2), null);
            ((LogMessageImpl) obtain).str1 = this.name;
            commit(obtain);
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
            LogMessage obtain = obtain("LogBuffer", LogLevel.DEBUG, new LogBuffer$$ExternalSyntheticLambda0(1), null);
            ((LogMessageImpl) obtain).str1 = this.name;
            commit(obtain);
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
