package com.android.systemui.shade.display;

import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

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
