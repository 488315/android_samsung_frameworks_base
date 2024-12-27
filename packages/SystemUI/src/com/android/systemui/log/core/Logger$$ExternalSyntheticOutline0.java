package com.android.systemui.log.core;

import kotlin.jvm.functions.Function1;

public abstract /* synthetic */ class Logger$$ExternalSyntheticOutline0 {
    public static void m(Function1 function1, LogMessage logMessage, Logger logger, LogMessage logMessage2) {
        function1.invoke(logMessage);
        logger.getBuffer().commit(logMessage2);
    }
}
