package com.android.systemui.shared.clocks;

import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
final class ClockRegistry$onConnected$2 extends Lambda implements Function1 {
    public static final ClockRegistry$onConnected$2 INSTANCE = new ClockRegistry$onConnected$2();

    public ClockRegistry$onConnected$2() {
        super(1);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        return KeyAttributes$$ExternalSyntheticOutline0.m21m("Connected ", ((LogMessage) obj).getStr1());
    }
}
