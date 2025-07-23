package com.android.compose.animation.scene;

import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.ApproachLayoutModifierNode;
import androidx.compose.ui.layout.ApproachMeasureScope;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.LayoutAwareModifierNode;
import androidx.compose.ui.unit.IntSize;
import com.android.compose.animation.scene.content.state.TransitionState;
import com.android.compose.ui.util.MathHelpersKt;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class LayoutNode extends Modifier.Node implements ApproachLayoutModifierNode, LayoutAwareModifierNode {
    public SceneTransitionLayoutImpl layoutImpl;
    public TransitionState transitionState;

    public LayoutNode(SceneTransitionLayoutImpl sceneTransitionLayoutImpl, TransitionState transitionState) {
        this.layoutImpl = sceneTransitionLayoutImpl;
        this.transitionState = transitionState;
    }

    @Override // androidx.compose.ui.layout.ApproachLayoutModifierNode
    /* renamed from: approachMeasure-3p2s80s */
    public final MeasureResult mo603approachMeasure3p2s80s(ApproachMeasureScope approachMeasureScope, Measurable measurable, long j) {
        int i;
        int i2;
        MeasureResult layout$1;
        final Placeable mo608measureBRTryo0 = measurable.mo608measureBRTryo0(j);
        TransitionState transitionState = this.transitionState;
        TransitionState.Transition.ChangeScene changeScene = transitionState instanceof TransitionState.Transition.ChangeScene ? (TransitionState.Transition.ChangeScene) transitionState : null;
        if (changeScene == null) {
            i = mo608measureBRTryo0.width;
            i2 = mo608measureBRTryo0.height;
        } else {
            long j2 = ((IntSize) ((SnapshotMutableStateImpl) this.layoutImpl.scene$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(changeScene.fromScene).targetSize$delegate).getValue()).packedValue;
            long j3 = ((IntSize) ((SnapshotMutableStateImpl) this.layoutImpl.scene$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(changeScene.toScene).targetSize$delegate).getValue()).packedValue;
            Element.Companion.getClass();
            long j4 = Element.SizeUnspecified;
            if (IntSize.m861equalsimpl0(j2, j4)) {
                throw new IllegalStateException("fromSize is unspecified ");
            }
            if (IntSize.m861equalsimpl0(j3, j4)) {
                throw new IllegalStateException("toSize is unspecified");
            }
            if (IntSize.m861equalsimpl0(j2, j3)) {
                i = (int) (j2 >> 32);
                i2 = (int) (j2 & 4294967295L);
            } else {
                long m940lerpe0twbBA = MathHelpersKt.m940lerpe0twbBA(j2, j3, changeScene.getProgress());
                i = (int) (m940lerpe0twbBA >> 32);
                i2 = 0;
                if (i < 0) {
                    i = 0;
                }
                int i3 = (int) (m940lerpe0twbBA & 4294967295L);
                if (i3 >= 0) {
                    i2 = i3;
                }
            }
        }
        layout$1 = approachMeasureScope.layout$1(i, i2, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: com.android.compose.animation.scene.LayoutNode$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                ((Placeable.PlacementScope) obj).place(Placeable.this, 0, 0, 0.0f);
                return Unit.INSTANCE;
            }
        });
        return layout$1;
    }

    @Override // androidx.compose.ui.layout.ApproachLayoutModifierNode
    /* renamed from: isMeasurementApproachInProgress-ozmzZPI */
    public final boolean mo604isMeasurementApproachInProgressozmzZPI(long j) {
        return this.transitionState instanceof TransitionState.Transition.ChangeScene;
    }

    @Override // androidx.compose.ui.node.LayoutAwareModifierNode
    /* renamed from: onRemeasured-ozmzZPI */
    public final void mo49onRemeasuredozmzZPI(long j) {
        this.layoutImpl.lastSize = j;
    }
}
