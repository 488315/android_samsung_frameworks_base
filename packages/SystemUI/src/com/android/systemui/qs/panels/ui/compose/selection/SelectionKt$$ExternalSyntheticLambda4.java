package com.android.systemui.qs.panels.ui.compose.selection;

import androidx.compose.animation.core.AnimationState;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class SelectionKt$$ExternalSyntheticLambda4 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ SelectionKt$$ExternalSyntheticLambda4(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                return Float.valueOf(((Number) ((AnimationState) this.f$0).getValue()).floatValue());
            default:
                ResizingState resizingState = (ResizingState) this.f$0;
                resizingState.getClass();
                float progress = resizingState.anchoredDraggableState.progress(QSDragAnchor.Icon, QSDragAnchor.Large);
                return Boolean.valueOf(progress == 0.0f || progress == 1.0f);
        }
    }
}
