package com.android.systemui.shared.clocks;

import androidx.core.graphics.PathParser$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
final class ClockRegistry$pluginListener$1$onPluginUnloaded$2 extends Lambda implements Function1 {
    public static final ClockRegistry$pluginListener$1$onPluginUnloaded$2 INSTANCE = new ClockRegistry$pluginListener$1$onPluginUnloaded$2();

    public ClockRegistry$pluginListener$1$onPluginUnloaded$2() {
        super(1);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        return PathParser$$ExternalSyntheticOutline0.m29m("Clock Id conflict on unload: ", ((LogMessage) obj).getStr1(), " is double registered");
    }
}
