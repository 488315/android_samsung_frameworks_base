package com.android.systemui.log;

import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class LogMessageImplKt {
    public static final Function1 DEFAULT_PRINTER = new Function1() { // from class: com.android.systemui.log.LogMessageImplKt$DEFAULT_PRINTER$1
        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            return "Unknown message: " + ((LogMessage) obj);
        }
    };
}
