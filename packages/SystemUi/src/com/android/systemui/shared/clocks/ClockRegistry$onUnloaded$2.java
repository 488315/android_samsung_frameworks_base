package com.android.systemui.shared.clocks;

import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
final class ClockRegistry$onUnloaded$2 extends Lambda implements Function1 {
    public static final ClockRegistry$onUnloaded$2 INSTANCE = new ClockRegistry$onUnloaded$2();

    public ClockRegistry$onUnloaded$2() {
        super(1);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        return KeyAttributes$$ExternalSyntheticOutline0.m21m("Unloaded ", ((LogMessage) obj).getStr1());
    }
}
