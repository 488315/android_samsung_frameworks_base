package com.android.systemui.log.core;

import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract /* synthetic */ class Logger$$ExternalSyntheticOutline0 {
    public static void m(Function1 function1, LogMessage logMessage, Logger logger, LogMessage logMessage2) {
        function1.mo779invoke(logMessage);
        logger.getBuffer().commit(logMessage2);
    }
}
