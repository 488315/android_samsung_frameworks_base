package com.android.systemui.log.core;

import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public abstract /* synthetic */ class Logger$$ExternalSyntheticOutline0 {
    public static void m(Function1 function1, LogMessage logMessage, Logger logger, LogMessage logMessage2) {
        function1.invoke(logMessage);
        logger.getBuffer().commit(logMessage2);
    }
}
