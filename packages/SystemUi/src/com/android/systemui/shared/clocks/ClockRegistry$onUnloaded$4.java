package com.android.systemui.shared.clocks;

import androidx.core.graphics.PathParser$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
final class ClockRegistry$onUnloaded$4 extends Lambda implements Function1 {
    public static final ClockRegistry$onUnloaded$4 INSTANCE = new ClockRegistry$onUnloaded$4();

    public ClockRegistry$onUnloaded$4() {
        super(1);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        return PathParser$$ExternalSyntheticOutline0.m29m("Current clock (", ((LogMessage) obj).getStr1(), ") was unloaded");
    }
}
