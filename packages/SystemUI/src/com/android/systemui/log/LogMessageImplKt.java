package com.android.systemui.log;

import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public abstract class LogMessageImplKt {
    public static final Function1 DEFAULT_PRINTER = new Function1() { // from class: com.android.systemui.log.LogMessageImplKt$DEFAULT_PRINTER$1
        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            return "Unknown message: " + ((LogMessage) obj);
        }
    };
}
