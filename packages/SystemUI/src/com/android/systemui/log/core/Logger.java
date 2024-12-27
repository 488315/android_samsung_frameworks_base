package com.android.systemui.log.core;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

public class Logger {
    private final MessageBuffer buffer;
    private final String tag;

    public Logger(MessageBuffer messageBuffer, String str) {
        this.buffer = messageBuffer;
        this.tag = str;
    }

    public static /* synthetic */ void d$default(Logger logger, Function1 function1, Throwable th, Function1 function12, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: d");
        }
        if ((i & 2) != 0) {
            th = null;
        }
        LogMessage obtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.DEBUG, function1, th);
        Logger$$ExternalSyntheticOutline0.m(function12, obtain, logger, obtain);
    }

    public static /* synthetic */ void e$default(Logger logger, Function1 function1, Throwable th, Function1 function12, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: e");
        }
        if ((i & 2) != 0) {
            th = null;
        }
        LogMessage obtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.ERROR, function1, th);
        Logger$$ExternalSyntheticOutline0.m(function12, obtain, logger, obtain);
    }

    public static /* synthetic */ void i$default(Logger logger, Function1 function1, Throwable th, Function1 function12, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: i");
        }
        if ((i & 2) != 0) {
            th = null;
        }
        LogMessage obtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.INFO, function1, th);
        Logger$$ExternalSyntheticOutline0.m(function12, obtain, logger, obtain);
    }

    public static /* synthetic */ void log$default(Logger logger, LogLevel logLevel, Function1 function1, Throwable th, Function1 function12, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: log");
        }
        if ((i & 4) != 0) {
            th = null;
        }
        LogMessage obtain = logger.getBuffer().obtain(logger.getTag(), logLevel, function1, th);
        Logger$$ExternalSyntheticOutline0.m(function12, obtain, logger, obtain);
    }

    public static /* synthetic */ void v$default(Logger logger, Function1 function1, Throwable th, Function1 function12, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: v");
        }
        if ((i & 2) != 0) {
            th = null;
        }
        LogMessage obtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.VERBOSE, function1, th);
        Logger$$ExternalSyntheticOutline0.m(function12, obtain, logger, obtain);
    }

    public static /* synthetic */ void w$default(Logger logger, Function1 function1, Throwable th, Function1 function12, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: w");
        }
        if ((i & 2) != 0) {
            th = null;
        }
        LogMessage obtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.WARNING, function1, th);
        Logger$$ExternalSyntheticOutline0.m(function12, obtain, logger, obtain);
    }

    public static /* synthetic */ void wtf$default(Logger logger, Function1 function1, Throwable th, Function1 function12, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: wtf");
        }
        if ((i & 2) != 0) {
            th = null;
        }
        LogMessage obtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.WTF, function1, th);
        Logger$$ExternalSyntheticOutline0.m(function12, obtain, logger, obtain);
    }

    public final void d(String str) {
        d$default(this, str, null, 2, null);
    }

    public final void e(String str) {
        e$default(this, str, null, 2, null);
    }

    public final MessageBuffer getBuffer() {
        return this.buffer;
    }

    public final String getTag() {
        return this.tag;
    }

    public final void i(String str) {
        i$default(this, str, null, 2, null);
    }

    public final void log(LogLevel logLevel, String str) {
        log$default(this, logLevel, str, null, 4, null);
    }

    public final void v(String str) {
        v$default(this, str, null, 2, null);
    }

    public final void w(String str) {
        w$default(this, str, null, 2, null);
    }

    public final void wtf(String str) {
        wtf$default(this, str, null, 2, null);
    }

    public final void d(Function1 function1, Throwable th, Function1 function12) {
        LogMessage obtain = getBuffer().obtain(getTag(), LogLevel.DEBUG, function1, th);
        Logger$$ExternalSyntheticOutline0.m(function12, obtain, this, obtain);
    }

    public final void e(Function1 function1, Throwable th, Function1 function12) {
        LogMessage obtain = getBuffer().obtain(getTag(), LogLevel.ERROR, function1, th);
        Logger$$ExternalSyntheticOutline0.m(function12, obtain, this, obtain);
    }

    public final void i(Function1 function1, Throwable th, Function1 function12) {
        LogMessage obtain = getBuffer().obtain(getTag(), LogLevel.INFO, function1, th);
        Logger$$ExternalSyntheticOutline0.m(function12, obtain, this, obtain);
    }

    public final void log(LogLevel logLevel, Function1 function1, Throwable th, Function1 function12) {
        LogMessage obtain = getBuffer().obtain(getTag(), logLevel, function1, th);
        Logger$$ExternalSyntheticOutline0.m(function12, obtain, this, obtain);
    }

    public final void v(Function1 function1, Throwable th, Function1 function12) {
        LogMessage obtain = getBuffer().obtain(getTag(), LogLevel.VERBOSE, function1, th);
        Logger$$ExternalSyntheticOutline0.m(function12, obtain, this, obtain);
    }

    public final void w(Function1 function1, Throwable th, Function1 function12) {
        LogMessage obtain = getBuffer().obtain(getTag(), LogLevel.WARNING, function1, th);
        Logger$$ExternalSyntheticOutline0.m(function12, obtain, this, obtain);
    }

    public final void wtf(Function1 function1, Throwable th, Function1 function12) {
        LogMessage obtain = getBuffer().obtain(getTag(), LogLevel.WTF, function1, th);
        Logger$$ExternalSyntheticOutline0.m(function12, obtain, this, obtain);
    }

    public static /* synthetic */ void log$default(Logger logger, LogLevel logLevel, String str, Throwable th, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: log");
        }
        if ((i & 4) != 0) {
            th = null;
        }
        logger.log(logLevel, str, th);
    }

    public final void log(LogLevel logLevel, String str, Throwable th) {
        LogMessage obtain = getBuffer().obtain(getTag(), logLevel, new Function1() { // from class: com.android.systemui.log.core.Logger$log$1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                Intrinsics.checkNotNull(str1);
                return str1;
            }
        }, th);
        obtain.setStr1(str);
        getBuffer().commit(obtain);
    }

    public static /* synthetic */ void d$default(Logger logger, String str, Throwable th, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: d");
        }
        if ((i & 2) != 0) {
            th = null;
        }
        logger.d(str, th);
    }

    public static /* synthetic */ void e$default(Logger logger, String str, Throwable th, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: e");
        }
        if ((i & 2) != 0) {
            th = null;
        }
        logger.e(str, th);
    }

    public static /* synthetic */ void i$default(Logger logger, String str, Throwable th, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: i");
        }
        if ((i & 2) != 0) {
            th = null;
        }
        logger.i(str, th);
    }

    public static /* synthetic */ void v$default(Logger logger, String str, Throwable th, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: v");
        }
        if ((i & 2) != 0) {
            th = null;
        }
        logger.v(str, th);
    }

    public static /* synthetic */ void w$default(Logger logger, String str, Throwable th, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: w");
        }
        if ((i & 2) != 0) {
            th = null;
        }
        logger.w(str, th);
    }

    public static /* synthetic */ void wtf$default(Logger logger, String str, Throwable th, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: wtf");
        }
        if ((i & 2) != 0) {
            th = null;
        }
        logger.wtf(str, th);
    }

    public final void d(String str, Throwable th) {
        log(LogLevel.DEBUG, str, th);
    }

    public final void e(String str, Throwable th) {
        log(LogLevel.ERROR, str, th);
    }

    public final void i(String str, Throwable th) {
        log(LogLevel.INFO, str, th);
    }

    public final void v(String str, Throwable th) {
        log(LogLevel.VERBOSE, str, th);
    }

    public final void w(String str, Throwable th) {
        log(LogLevel.WARNING, str, th);
    }

    public final void wtf(String str, Throwable th) {
        log(LogLevel.WTF, str, th);
    }

    public final void d(Function1 function1, Function1 function12) {
        LogMessage obtain = getBuffer().obtain(getTag(), LogLevel.DEBUG, function1, null);
        Logger$$ExternalSyntheticOutline0.m(function12, obtain, this, obtain);
    }

    public final void e(Function1 function1, Function1 function12) {
        LogMessage obtain = getBuffer().obtain(getTag(), LogLevel.ERROR, function1, null);
        Logger$$ExternalSyntheticOutline0.m(function12, obtain, this, obtain);
    }

    public final void i(Function1 function1, Function1 function12) {
        LogMessage obtain = getBuffer().obtain(getTag(), LogLevel.INFO, function1, null);
        Logger$$ExternalSyntheticOutline0.m(function12, obtain, this, obtain);
    }

    public final void v(Function1 function1, Function1 function12) {
        LogMessage obtain = getBuffer().obtain(getTag(), LogLevel.VERBOSE, function1, null);
        Logger$$ExternalSyntheticOutline0.m(function12, obtain, this, obtain);
    }

    public final void w(Function1 function1, Function1 function12) {
        LogMessage obtain = getBuffer().obtain(getTag(), LogLevel.WARNING, function1, null);
        Logger$$ExternalSyntheticOutline0.m(function12, obtain, this, obtain);
    }

    public final void wtf(Function1 function1, Function1 function12) {
        LogMessage obtain = getBuffer().obtain(getTag(), LogLevel.WTF, function1, null);
        Logger$$ExternalSyntheticOutline0.m(function12, obtain, this, obtain);
    }

    public final void log(LogLevel logLevel, Function1 function1, Function1 function12) {
        LogMessage obtain = getBuffer().obtain(getTag(), logLevel, function1, null);
        Logger$$ExternalSyntheticOutline0.m(function12, obtain, this, obtain);
    }
}
