package com.android.systemui.log;

import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

public abstract class LogMessageImplKt {
    public static final Function1 DEFAULT_PRINTER = new Function1() { // from class: com.android.systemui.log.LogMessageImplKt$DEFAULT_PRINTER$1
        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            return "Unknown message: " + ((LogMessage) obj);
        }
    };
}
