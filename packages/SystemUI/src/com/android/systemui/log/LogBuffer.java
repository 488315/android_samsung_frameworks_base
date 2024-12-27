package com.android.systemui.log;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.systemui.common.buffer.RingBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.log.core.MessageBuffer;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class LogBuffer implements MessageBuffer {
    public final RingBuffer buffer;
    public boolean frozen;
    public final LogcatEchoTracker logcatEchoTracker;
    public final int maxSize;
    public final String name;
    public final boolean systrace;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    public LogBuffer(String str, int i, LogcatEchoTracker logcatEchoTracker) {
        this(str, i, logcatEchoTracker, false, 8, null);
    }

    @Override // com.android.systemui.log.core.MessageBuffer
    public final synchronized void commit(LogMessage logMessage) {
        if (this.frozen || this.maxSize <= 0) {
            return;
        }
        echoToDesiredEndpoints(logMessage);
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0040  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0094  */
    /* JADX WARN: Removed duplicated region for block: B:27:? A[RETURN, SYNTHETIC] */
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
            boolean r7 = r7.systrace
            r5 = 4096(0x1000, double:2.0237E-320)
            if (r7 == 0) goto L2f
            boolean r7 = android.os.Trace.isTagEnabled(r5)
            if (r7 == 0) goto L2f
            goto L30
        L2f:
            r3 = r4
        L30:
            if (r0 != 0) goto L34
            if (r3 == 0) goto Lc3
        L34:
            kotlin.jvm.functions.Function1 r7 = r8.getMessagePrinter()
            java.lang.Object r7 = r7.invoke(r8)
            java.lang.String r7 = (java.lang.String) r7
            if (r0 == 0) goto L92
            com.android.systemui.log.core.LogLevel r0 = r8.getLevel()
            int[] r1 = com.android.systemui.log.LogBuffer.WhenMappings.$EnumSwitchMapping$0
            int r0 = r0.ordinal()
            r0 = r1[r0]
            switch(r0) {
                case 1: goto L8c;
                case 2: goto L80;
                case 3: goto L74;
                case 4: goto L68;
                case 5: goto L5c;
                case 6: goto L50;
                default: goto L4f;
            }
        L4f:
            goto L92
        L50:
            java.lang.String r0 = r8.getTag()
            java.lang.Throwable r1 = r8.getException()
            android.util.Log.wtf(r0, r7, r1)
            goto L92
        L5c:
            java.lang.String r0 = r8.getTag()
            java.lang.Throwable r1 = r8.getException()
            android.util.Log.e(r0, r7, r1)
            goto L92
        L68:
            java.lang.String r0 = r8.getTag()
            java.lang.Throwable r1 = r8.getException()
            android.util.Log.w(r0, r7, r1)
            goto L92
        L74:
            java.lang.String r0 = r8.getTag()
            java.lang.Throwable r1 = r8.getException()
            android.util.Log.i(r0, r7, r1)
            goto L92
        L80:
            java.lang.String r0 = r8.getTag()
            java.lang.Throwable r1 = r8.getException()
            android.util.Log.d(r0, r7, r1)
            goto L92
        L8c:
            r8.getTag()
            r8.getException()
        L92:
            if (r3 == 0) goto Lc3
            com.android.systemui.log.core.LogLevel r0 = r8.getLevel()
            java.lang.String r8 = r8.getTag()
            java.lang.String r0 = r0.getShortString()
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r2)
            java.lang.String r2 = " - "
            r1.append(r2)
            r1.append(r0)
            java.lang.String r0 = " "
            r1.append(r0)
            r1.append(r8)
            java.lang.String r8 = ": "
            java.lang.String r7 = androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0.m(r1, r8, r7)
            java.lang.String r8 = "UI Events"
            android.os.Trace.instantForTrack(r5, r8, r7)
        Lc3:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.log.LogBuffer.echoToDesiredEndpoints(com.android.systemui.log.core.LogMessage):void");
    }

    public final synchronized void freeze() {
        if (!this.frozen) {
            LogMessage obtain = obtain("LogBuffer", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.log.LogBuffer$freeze$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(((LogMessage) obj).getStr1(), " frozen");
                }
            }, null);
            ((LogMessageImpl) obtain).str1 = this.name;
            commit(obtain);
            this.frozen = true;
        }
    }

    public final void log(String str, LogLevel logLevel, String str2, Throwable th) {
        LogMessage obtain = obtain(str, logLevel, new Function1() { // from class: com.android.systemui.log.LogBuffer$log$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                String str1 = ((LogMessage) obj).getStr1();
                Intrinsics.checkNotNull(str1);
                return str1;
            }
        }, th);
        ((LogMessageImpl) obtain).str1 = str2;
        commit(obtain);
    }

    @Override // com.android.systemui.log.core.MessageBuffer
    public final synchronized LogMessage obtain(String str, LogLevel logLevel, Function1 function1, Throwable th) {
        if (this.frozen || this.maxSize <= 0) {
            return LogBufferKt.FROZEN_MESSAGE;
        }
        LogMessageImpl logMessageImpl = (LogMessageImpl) this.buffer.advance();
        logMessageImpl.reset(str, logLevel, System.currentTimeMillis(), function1, th);
        return logMessageImpl;
    }

    public final synchronized void unfreeze() {
        if (this.frozen) {
            this.frozen = false;
            LogMessage obtain = obtain("LogBuffer", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.log.LogBuffer$unfreeze$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(((LogMessage) obj).getStr1(), " unfrozen");
                }
            }, null);
            ((LogMessageImpl) obtain).str1 = this.name;
            commit(obtain);
        }
    }

    public LogBuffer(String str, int i, LogcatEchoTracker logcatEchoTracker, boolean z) {
        this.name = str;
        this.maxSize = i;
        this.logcatEchoTracker = logcatEchoTracker;
        this.systrace = z;
        this.buffer = new RingBuffer(i, new Function0() { // from class: com.android.systemui.log.LogBuffer$buffer$1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                LogMessageImpl.Factory.getClass();
                return LogMessageImpl.Factory.create();
            }
        });
    }

    public /* synthetic */ LogBuffer(String str, int i, LogcatEchoTracker logcatEchoTracker, boolean z, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, i, logcatEchoTracker, (i2 & 8) != 0 ? true : z);
    }
}
