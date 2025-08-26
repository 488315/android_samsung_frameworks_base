package com.android.compose.animation;

import android.view.View;
import android.view.ViewGroup;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.geometry.RoundRect;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.Outline;
import androidx.compose.ui.node.DrawModifierNodeKt;
import com.android.systemui.animation.TransitionAnimator;
import kotlin.Pair;
import kotlin.comparisons.ComparisonsKt___ComparisonsJvmKt;
import kotlin.math.MathKt__MathJVMKt;

/* loaded from: classes.dex */
public final class ExpandableControllerImpl$transitionController$1 implements TransitionAnimator.Controller {
    public final /* synthetic */ ExpandableControllerImpl this$0;
    public ViewGroup transitionContainer;
    public final int[] rootLocationOnScreen = {0, 0};
    public final boolean isLaunching = true;

    public ExpandableControllerImpl$transitionController$1(ExpandableControllerImpl expandableControllerImpl) {
        this.this$0 = expandableControllerImpl;
        this.transitionContainer = (ViewGroup) expandableControllerImpl.composeViewRoot.getRootView();
    }

    @Override // com.android.systemui.animation.TransitionAnimator.Controller
    public final TransitionAnimator.State createAnimatorState() {
        char c;
        char c2;
        char c3;
        float f;
        Pair pair;
        ExpandableControllerImpl expandableControllerImpl = this.this$0;
        Rect rect = (Rect) ((SnapshotMutableStateImpl) expandableControllerImpl.boundsInComposeViewRoot$delegate).getValue();
        float f2 = rect.right;
        float f3 = rect.left;
        float f4 = rect.bottom - rect.top;
        Size.Companion companion = Size.Companion;
        Outline outlineMo41createOutlinePq9zytI = expandableControllerImpl.shape.mo41createOutlinePq9zytI((Float.floatToRawIntBits(f2 - f3) << 32) | (Float.floatToRawIntBits(f4) & 4294967295L), expandableControllerImpl.layoutDirection, expandableControllerImpl.density);
        if (outlineMo41createOutlinePq9zytI instanceof Outline.Rectangle) {
            pair = new Pair(Float.valueOf(0.0f), Float.valueOf(0.0f));
            c3 = ' ';
            f = f3;
            c = 1;
            c2 = 0;
        } else {
            if (!(outlineMo41createOutlinePq9zytI instanceof Outline.Rounded)) {
                throw new IllegalStateException("ExpandableState only supports (rounded) rectangles at the moment.");
            }
            RoundRect roundRect = ((Outline.Rounded) outlineMo41createOutlinePq9zytI).roundRect;
            float fIntBitsToFloat = Float.intBitsToFloat((int) (roundRect.topLeftCornerRadius >> 32));
            float fIntBitsToFloat2 = Float.intBitsToFloat((int) (roundRect.topLeftCornerRadius & 4294967295L));
            long j = roundRect.topRightCornerRadius;
            c = 1;
            c2 = 0;
            float fMaxOf = ComparisonsKt___ComparisonsJvmKt.maxOf(fIntBitsToFloat, fIntBitsToFloat2, Float.intBitsToFloat((int) (j >> 32)), Float.intBitsToFloat((int) (j & 4294967295L)));
            long j2 = roundRect.bottomLeftCornerRadius;
            float fIntBitsToFloat3 = Float.intBitsToFloat((int) (j2 >> 32));
            float fIntBitsToFloat4 = Float.intBitsToFloat((int) (j2 & 4294967295L));
            long j3 = roundRect.bottomRightCornerRadius;
            c3 = ' ';
            f = f3;
            pair = new Pair(Float.valueOf(fMaxOf), Float.valueOf(ComparisonsKt___ComparisonsJvmKt.maxOf(fIntBitsToFloat3, fIntBitsToFloat4, Float.intBitsToFloat((int) (j3 >> 32)), Float.intBitsToFloat((int) (j3 & 4294967295L)))));
        }
        float fFloatValue = ((Number) pair.component1()).floatValue();
        float fFloatValue2 = ((Number) pair.component2()).floatValue();
        expandableControllerImpl.composeViewRoot.getLocationOnScreen(this.rootLocationOnScreen);
        Rect rect2 = (Rect) ((SnapshotMutableStateImpl) expandableControllerImpl.boundsInComposeViewRoot$delegate).getValue();
        long jFloatToRawIntBits = (Float.floatToRawIntBits(r0[c2] + rect2.left) << c3) | (Float.floatToRawIntBits(r0[c] + rect2.top) & 4294967295L);
        Offset.Companion companion2 = Offset.Companion;
        int i = (int) (jFloatToRawIntBits & 4294967295L);
        int iRoundToInt = MathKt__MathJVMKt.roundToInt(Float.intBitsToFloat(i));
        int iRoundToInt2 = MathKt__MathJVMKt.roundToInt(Float.intBitsToFloat(i) + f4);
        int i2 = (int) (jFloatToRawIntBits >> c3);
        return new TransitionAnimator.State(iRoundToInt, iRoundToInt2, MathKt__MathJVMKt.roundToInt(Float.intBitsToFloat(i2)), MathKt__MathJVMKt.roundToInt((rect.right - f) + Float.intBitsToFloat(i2)), fFloatValue, fFloatValue2);
    }

    @Override // com.android.systemui.animation.TransitionAnimator.Controller
    public final ViewGroup getTransitionContainer() {
        return this.transitionContainer;
    }

    @Override // com.android.systemui.animation.TransitionAnimator.Controller
    public final boolean isLaunching() {
        return this.isLaunching;
    }

    @Override // com.android.systemui.animation.TransitionAnimator.Controller
    public final void onTransitionAnimationEnd(boolean z) {
        ExpandableControllerImpl expandableControllerImpl = this.this$0;
        ((SnapshotMutableStateImpl) expandableControllerImpl.animatorState$delegate).setValue(null);
        DrawExpandableInOverlayNode drawExpandableInOverlayNode = expandableControllerImpl.currentNodeInOverlay;
        if (drawExpandableInOverlayNode != null) {
            DrawModifierNodeKt.invalidateDraw(drawExpandableInOverlayNode);
        }
    }

    @Override // com.android.systemui.animation.TransitionAnimator.Controller
    public final void onTransitionAnimationProgress(TransitionAnimator.State state, float f, float f2) {
        TransitionAnimator.State state2 = new TransitionAnimator.State(state.top, state.bottom, state.left, state.right, state.topCornerRadius, state.bottomCornerRadius);
        state2.visible = state.visible;
        ExpandableControllerImpl expandableControllerImpl = this.this$0;
        ((SnapshotMutableStateImpl) expandableControllerImpl.animatorState$delegate).setValue(state2);
        View view = (View) ((SnapshotMutableStateImpl) expandableControllerImpl.currentComposeViewInOverlay$delegate).getValue();
        if (view != null) {
            ExpandableKt.measureAndLayoutComposeViewInOverlay(view, state);
        }
        DrawExpandableInOverlayNode drawExpandableInOverlayNode = expandableControllerImpl.currentNodeInOverlay;
        if (drawExpandableInOverlayNode != null) {
            DrawModifierNodeKt.invalidateDraw(drawExpandableInOverlayNode);
        }
    }

    @Override // com.android.systemui.animation.TransitionAnimator.Controller
    public final void setTransitionContainer(ViewGroup viewGroup) {
        this.transitionContainer = viewGroup;
    }
}
