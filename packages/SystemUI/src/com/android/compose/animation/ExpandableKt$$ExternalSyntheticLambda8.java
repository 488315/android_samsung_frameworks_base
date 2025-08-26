package com.android.compose.animation;

import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.drawscope.ContentDrawScope;
import androidx.compose.ui.graphics.layer.GraphicsLayer;
import androidx.compose.ui.node.LayoutNodeDrawScope;
import androidx.compose.ui.platform.ComposeView;
import com.android.systemui.animation.TransitionAnimator;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public final /* synthetic */ class ExpandableKt$$ExternalSyntheticLambda8 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ExpandableControllerImpl f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ ExpandableKt$$ExternalSyntheticLambda8(ExpandableControllerImpl expandableControllerImpl, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = expandableControllerImpl;
        this.f$1 = obj;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                Modifier.Companion companion = Modifier.Companion;
                DrawExpandableInOverlayElement drawExpandableInOverlayElement = new DrawExpandableInOverlayElement((ComposeView) obj, this.f$0, (GraphicsLayer) this.f$1);
                companion.getClass();
                return drawExpandableInOverlayElement;
            default:
                ContentDrawScope contentDrawScope = (ContentDrawScope) obj;
                ExpandableControllerImpl expandableControllerImpl = this.f$0;
                TransitionAnimator.State state = (TransitionAnimator.State) ((SnapshotMutableStateImpl) expandableControllerImpl.animatorState$delegate).getValue();
                if (state == null) {
                    return Unit.INSTANCE;
                }
                if (!state.visible) {
                    return Unit.INSTANCE;
                }
                long j = ((Color) ((Function0) this.f$1).invoke()).value;
                LayoutNodeDrawScope layoutNodeDrawScope = (LayoutNodeDrawScope) contentDrawScope;
                ExpandableKt.m914drawBackgroundHilfTbk(contentDrawScope, state, j, expandableControllerImpl.borderStroke, layoutNodeDrawScope.canvasDrawScope.mo547getSizeNHjbRc());
                layoutNodeDrawScope.drawContent();
                return Unit.INSTANCE;
        }
    }
}
