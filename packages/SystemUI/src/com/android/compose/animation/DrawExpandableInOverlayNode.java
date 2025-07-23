package com.android.compose.animation;

import androidx.compose.foundation.BorderStroke;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope$drawContext$1;
import androidx.compose.ui.graphics.layer.GraphicsLayer;
import androidx.compose.ui.graphics.layer.GraphicsLayerKt;
import androidx.compose.ui.node.DrawModifierNode;
import androidx.compose.ui.node.LayoutNodeDrawScope;
import androidx.compose.ui.platform.ComposeView;
import com.android.systemui.animation.TransitionAnimator;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class DrawExpandableInOverlayNode extends Modifier.Node implements DrawModifierNode {
    public int[] composeViewLocationOnScreen;
    public GraphicsLayer contentGraphicsLayer;
    public ExpandableControllerImpl controller;

    public DrawExpandableInOverlayNode(ComposeView composeView, ExpandableControllerImpl expandableControllerImpl, GraphicsLayer graphicsLayer) {
        this.contentGraphicsLayer = graphicsLayer;
        this.controller = expandableControllerImpl;
        this.composeViewLocationOnScreen = composeView.getLocationOnScreen();
    }

    @Override // androidx.compose.ui.node.DrawModifierNode
    public final void draw(LayoutNodeDrawScope layoutNodeDrawScope) {
        TransitionAnimator.State state = (TransitionAnimator.State) ((SnapshotMutableStateImpl) this.controller.animatorState$delegate).getValue();
        if (state != null) {
            if (!state.visible) {
                state = null;
            }
            if (state == null) {
                return;
            }
            float f = state.top;
            int[] iArr = this.composeViewLocationOnScreen;
            float f2 = f - iArr[1];
            float f3 = state.left - iArr[0];
            CanvasDrawScope canvasDrawScope = layoutNodeDrawScope.canvasDrawScope;
            canvasDrawScope.drawContext.transform.translate(f3, f2);
            try {
                long j = ((Color) this.controller.color.invoke()).value;
                BorderStroke borderStroke = this.controller.borderStroke;
                float width = state.getWidth();
                float height = state.getHeight();
                long floatToRawIntBits = (Float.floatToRawIntBits(height) & 4294967295L) | (Float.floatToRawIntBits(width) << 32);
                Size.Companion companion = Size.Companion;
                ExpandableKt.m912drawBackgroundHilfTbk(layoutNodeDrawScope, state, j, borderStroke, floatToRawIntBits);
                long m408getSizeNHjbRc = ((Rect) ((SnapshotMutableStateImpl) this.controller.boundsInComposeViewRoot$delegate).getValue()).m408getSizeNHjbRc();
                float intBitsToFloat = Float.intBitsToFloat((int) (m408getSizeNHjbRc >> 32));
                float intBitsToFloat2 = Float.intBitsToFloat((int) (m408getSizeNHjbRc & 4294967295L));
                float min = Math.min(state.getWidth() / intBitsToFloat, state.getHeight() / intBitsToFloat2);
                float height2 = state.getHeight() / 2.0f;
                long floatToRawIntBits2 = (Float.floatToRawIntBits(height2) & 4294967295L) | (Float.floatToRawIntBits(state.getWidth() / 2.0f) << 32);
                Offset.Companion companion2 = Offset.Companion;
                CanvasDrawScope$drawContext$1 canvasDrawScope$drawContext$1 = canvasDrawScope.drawContext;
                long m526getSizeNHjbRc = canvasDrawScope$drawContext$1.m526getSizeNHjbRc();
                canvasDrawScope$drawContext$1.getCanvas().save();
                try {
                    canvasDrawScope$drawContext$1.transform.m530scale0AR0LA0(min, min, floatToRawIntBits2);
                    float width2 = (state.getWidth() - intBitsToFloat) / 2.0f;
                    float height3 = (state.getHeight() - intBitsToFloat2) / 2.0f;
                    canvasDrawScope.drawContext.transform.translate(width2, height3);
                    try {
                        GraphicsLayerKt.drawLayer(layoutNodeDrawScope, this.contentGraphicsLayer);
                    } finally {
                        canvasDrawScope.drawContext.transform.translate(-width2, -height3);
                    }
                } finally {
                    canvasDrawScope$drawContext$1.getCanvas().restore();
                    canvasDrawScope$drawContext$1.m527setSizeuvyYCjk(m526getSizeNHjbRc);
                }
            } finally {
                canvasDrawScope.drawContext.transform.translate(-f3, -f2);
            }
        }
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onAttach() {
        this.controller.currentNodeInOverlay = this;
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onDetach() {
        if (Intrinsics.areEqual(this.controller.currentNodeInOverlay, this)) {
            this.controller.currentNodeInOverlay = null;
        }
    }
}
