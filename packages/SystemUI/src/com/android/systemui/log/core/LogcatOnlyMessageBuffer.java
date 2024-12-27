package com.android.systemui.log.core;

import android.util.Log;
import com.android.systemui.log.LogMessageImpl;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class LogcatOnlyMessageBuffer implements MessageBuffer {
    private boolean isObtained;
    private final LogMessageImpl singleMessage;
    private final LogLevel targetLogLevel;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final /* synthetic */ class WhenMappings {
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

    public LogcatOnlyMessageBuffer(LogLevel logLevel) {
        this.targetLogLevel = logLevel;
        LogMessageImpl.Factory.getClass();
        this.singleMessage = LogMessageImpl.Factory.create();
    }

    @Override // com.android.systemui.log.core.MessageBuffer
    public synchronized void commit(LogMessage logMessage) {
        try {
            if (!Intrinsics.areEqual(this.singleMessage, logMessage)) {
                throw new IllegalArgumentException("Message argument is not the expected message.");
            }
            if (!this.isObtained) {
                throw new UnsupportedOperationException("Message has not been obtained. Call order is incorrect.");
            }
            if (logMessage.getLevel().compareTo(this.targetLogLevel) >= 0) {
                String str = (String) logMessage.getMessagePrinter().invoke(logMessage);
                switch (WhenMappings.$EnumSwitchMapping$0[logMessage.getLevel().ordinal()]) {
                    case 1:
                        logMessage.getTag();
                        logMessage.getException();
                        break;
                    case 2:
                        Log.d(logMessage.getTag(), str, logMessage.getException());
                        break;
                    case 3:
                        Log.i(logMessage.getTag(), str, logMessage.getException());
                        break;
                    case 4:
                        Log.w(logMessage.getTag(), str, logMessage.getException());
                        break;
                    case 5:
                        Log.e(logMessage.getTag(), str, logMessage.getException());
                        break;
                    case 6:
                        Log.wtf(logMessage.getTag(), str, logMessage.getException());
                        break;
                }
            }
            this.isObtained = false;
        } catch (Throwable th) {
            throw th;
        }
    }

    public final LogLevel getTargetLogLevel() {
        return this.targetLogLevel;
    }

    @Override // com.android.systemui.log.core.MessageBuffer
    public synchronized LogMessage obtain(String str, LogLevel logLevel, Function1 function1, Throwable th) {
        if (this.isObtained) {
            throw new UnsupportedOperationException("Message has already been obtained. Call order is incorrect.");
        }
        this.singleMessage.reset(str, logLevel, System.currentTimeMillis(), function1, th);
        this.isObtained = true;
        return this.singleMessage;
    }
}
