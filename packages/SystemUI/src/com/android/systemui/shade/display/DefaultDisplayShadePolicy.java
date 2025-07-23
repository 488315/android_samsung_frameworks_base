package com.android.systemui.shade.display;

import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DefaultDisplayShadePolicy implements ShadeDisplayPolicy {
    public final StateFlowImpl displayId = StateFlowKt.MutableStateFlow(0);

    @Override // com.android.systemui.shade.display.ShadeDisplayPolicy
    public final StateFlow getDisplayId() {
        return this.displayId;
    }

    @Override // com.android.systemui.shade.display.ShadeDisplayPolicy
    public final String getName() {
        return "default_display";
    }
}
