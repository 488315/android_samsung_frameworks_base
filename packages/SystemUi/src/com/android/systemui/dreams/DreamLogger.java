package com.android.systemui.dreams;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DreamLogger {
    public final LogBuffer buffer;

    public DreamLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    /* renamed from: d */
    public final void m132d(final String str) {
        LogLevel logLevel = LogLevel.DEBUG;
        Function1 function1 = new Function1() { // from class: com.android.systemui.dreams.DreamLogger$d$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return str;
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("DreamOverlayAnimationsController", logLevel, function1, null);
        obtain.setStr1(str);
        logBuffer.commit(obtain);
    }
}
