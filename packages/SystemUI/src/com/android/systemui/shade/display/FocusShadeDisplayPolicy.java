package com.android.systemui.shade.display;

import com.android.systemui.display.data.repository.FocusedDisplayRepository;
import com.android.systemui.display.data.repository.FocusedDisplayRepositoryImpl;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class FocusShadeDisplayPolicy implements ShadeDisplayPolicy {
    public final FocusedDisplayRepository focusedDisplayRepository;

    public FocusShadeDisplayPolicy(FocusedDisplayRepository focusedDisplayRepository) {
        this.focusedDisplayRepository = focusedDisplayRepository;
    }

    @Override // com.android.systemui.shade.display.ShadeDisplayPolicy
    public final StateFlow getDisplayId() {
        FocusedDisplayRepositoryImpl focusedDisplayRepositoryImpl = (FocusedDisplayRepositoryImpl) this.focusedDisplayRepository;
        FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1 = focusedDisplayRepositoryImpl.focusedTask;
        SharingStarted.Companion.getClass();
        return FlowKt.stateIn(flowKt__TransformKt$onEach$$inlined$unsafeTransform$1, focusedDisplayRepositoryImpl.backgroundScope, SharingStarted.Companion.Eagerly, 0);
    }

    @Override // com.android.systemui.shade.display.ShadeDisplayPolicy
    public final String getName() {
        return "focused_display";
    }
}
