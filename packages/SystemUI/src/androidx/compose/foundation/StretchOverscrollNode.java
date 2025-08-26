package androidx.compose.foundation;

import android.graphics.Canvas;
import android.graphics.RecordingCanvas;
import android.graphics.RenderNode;
import android.widget.EdgeEffect;
import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.AndroidCanvas;
import androidx.compose.ui.graphics.AndroidCanvas_androidKt;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope$drawContext$1;
import androidx.compose.ui.graphics.layer.GraphicsLayer;
import androidx.compose.ui.node.DelegatableNode;
import androidx.compose.ui.node.DelegatingNode;
import androidx.compose.ui.node.DrawModifierNode;
import androidx.compose.ui.node.LayoutNodeDrawScope;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.IntSize;
import androidx.compose.ui.unit.LayoutDirection;
import kotlin.math.MathKt__MathJVMKt;

/* loaded from: classes.dex */
final class StretchOverscrollNode extends DelegatingNode implements DrawModifierNode {
    public RenderNode _renderNode;
    public final EdgeEffectWrapper edgeEffectWrapper;
    public final AndroidEdgeEffectOverscrollEffect overscrollEffect;

    public StretchOverscrollNode(DelegatableNode delegatableNode, AndroidEdgeEffectOverscrollEffect androidEdgeEffectOverscrollEffect, EdgeEffectWrapper edgeEffectWrapper) {
        this.overscrollEffect = androidEdgeEffectOverscrollEffect;
        this.edgeEffectWrapper = edgeEffectWrapper;
        delegate(delegatableNode);
    }

    public static boolean drawWithRotation(float f, EdgeEffect edgeEffect, Canvas canvas) {
        if (f == 0.0f) {
            return edgeEffect.draw(canvas);
        }
        int iSave = canvas.save();
        canvas.rotate(f);
        boolean zDraw = edgeEffect.draw(canvas);
        canvas.restoreToCount(iSave);
        return zDraw;
    }

    /* JADX WARN: Removed duplicated region for block: B:129:0x028b A[PHI: r18
      0x028b: PHI (r18v2 boolean) = (r18v1 boolean), (r18v11 boolean) binds: [B:116:0x024a, B:124:0x0264] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // androidx.compose.ui.node.DrawModifierNode
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void draw(LayoutNodeDrawScope layoutNodeDrawScope) {
        long j;
        char c;
        boolean z;
        boolean zDrawWithRotation;
        CanvasDrawScope canvasDrawScope;
        float f;
        float f2;
        CanvasDrawScope canvasDrawScope2 = layoutNodeDrawScope.canvasDrawScope;
        long jMo547getSizeNHjbRc = canvasDrawScope2.mo547getSizeNHjbRc();
        AndroidEdgeEffectOverscrollEffect androidEdgeEffectOverscrollEffect = this.overscrollEffect;
        long j2 = androidEdgeEffectOverscrollEffect.containerSize;
        Size.Companion.getClass();
        boolean zM416equalsimpl0 = Size.m416equalsimpl0(j2, 0L);
        boolean zM416equalsimpl02 = Size.m416equalsimpl0(jMo547getSizeNHjbRc, androidEdgeEffectOverscrollEffect.containerSize);
        androidEdgeEffectOverscrollEffect.containerSize = jMo547getSizeNHjbRc;
        if (!zM416equalsimpl02) {
            long jRoundToInt = (MathKt__MathJVMKt.roundToInt(Float.intBitsToFloat((int) (jMo547getSizeNHjbRc & 4294967295L))) & 4294967295L) | (MathKt__MathJVMKt.roundToInt(Float.intBitsToFloat((int) (jMo547getSizeNHjbRc >> 32))) << 32);
            IntSize.Companion companion = IntSize.Companion;
            EdgeEffectWrapper edgeEffectWrapper = androidEdgeEffectOverscrollEffect.edgeEffectWrapper;
            edgeEffectWrapper.size = jRoundToInt;
            EdgeEffect edgeEffect = edgeEffectWrapper.topEffect;
            if (edgeEffect != null) {
                edgeEffect.setSize((int) (jRoundToInt >> 32), (int) (jRoundToInt & 4294967295L));
            }
            EdgeEffect edgeEffect2 = edgeEffectWrapper.bottomEffect;
            if (edgeEffect2 != null) {
                edgeEffect2.setSize((int) (jRoundToInt >> 32), (int) (jRoundToInt & 4294967295L));
            }
            EdgeEffect edgeEffect3 = edgeEffectWrapper.leftEffect;
            if (edgeEffect3 != null) {
                edgeEffect3.setSize((int) (jRoundToInt & 4294967295L), (int) (jRoundToInt >> 32));
            }
            EdgeEffect edgeEffect4 = edgeEffectWrapper.rightEffect;
            if (edgeEffect4 != null) {
                edgeEffect4.setSize((int) (jRoundToInt & 4294967295L), (int) (jRoundToInt >> 32));
            }
            EdgeEffect edgeEffect5 = edgeEffectWrapper.topEffectNegation;
            if (edgeEffect5 != null) {
                edgeEffect5.setSize((int) (jRoundToInt >> 32), (int) (jRoundToInt & 4294967295L));
            }
            EdgeEffect edgeEffect6 = edgeEffectWrapper.bottomEffectNegation;
            if (edgeEffect6 != null) {
                edgeEffect6.setSize((int) (jRoundToInt >> 32), (int) (jRoundToInt & 4294967295L));
            }
            EdgeEffect edgeEffect7 = edgeEffectWrapper.leftEffectNegation;
            if (edgeEffect7 != null) {
                edgeEffect7.setSize((int) (jRoundToInt & 4294967295L), (int) (jRoundToInt >> 32));
            }
            EdgeEffect edgeEffect8 = edgeEffectWrapper.rightEffectNegation;
            if (edgeEffect8 != null) {
                edgeEffect8.setSize((int) (jRoundToInt & 4294967295L), (int) (jRoundToInt >> 32));
            }
        }
        if (!zM416equalsimpl0 && !zM416equalsimpl02) {
            androidEdgeEffectOverscrollEffect.animateToReleaseIfNeeded();
        }
        androidx.compose.ui.graphics.Canvas canvas = canvasDrawScope2.drawContext.getCanvas();
        Canvas canvas2 = AndroidCanvas_androidKt.EmptyCanvas;
        Canvas canvas3 = ((AndroidCanvas) canvas).internalCanvas;
        ((SnapshotMutableStateImpl) androidEdgeEffectOverscrollEffect.redrawSignal).getValue();
        if (Size.m420isEmptyimpl(canvasDrawScope2.mo547getSizeNHjbRc())) {
            layoutNodeDrawScope.drawContent();
            return;
        }
        boolean zIsHardwareAccelerated = canvas3.isHardwareAccelerated();
        EdgeEffectWrapper edgeEffectWrapper2 = this.edgeEffectWrapper;
        if (!zIsHardwareAccelerated) {
            EdgeEffect edgeEffect9 = edgeEffectWrapper2.topEffect;
            if (edgeEffect9 != null) {
                edgeEffect9.finish();
            }
            EdgeEffect edgeEffect10 = edgeEffectWrapper2.bottomEffect;
            if (edgeEffect10 != null) {
                edgeEffect10.finish();
            }
            EdgeEffect edgeEffect11 = edgeEffectWrapper2.leftEffect;
            if (edgeEffect11 != null) {
                edgeEffect11.finish();
            }
            EdgeEffect edgeEffect12 = edgeEffectWrapper2.rightEffect;
            if (edgeEffect12 != null) {
                edgeEffect12.finish();
            }
            EdgeEffect edgeEffect13 = edgeEffectWrapper2.topEffectNegation;
            if (edgeEffect13 != null) {
                edgeEffect13.finish();
            }
            EdgeEffect edgeEffect14 = edgeEffectWrapper2.bottomEffectNegation;
            if (edgeEffect14 != null) {
                edgeEffect14.finish();
            }
            EdgeEffect edgeEffect15 = edgeEffectWrapper2.leftEffectNegation;
            if (edgeEffect15 != null) {
                edgeEffect15.finish();
            }
            EdgeEffect edgeEffect16 = edgeEffectWrapper2.rightEffectNegation;
            if (edgeEffect16 != null) {
                edgeEffect16.finish();
            }
            layoutNodeDrawScope.drawContent();
            return;
        }
        float fMo58toPx0680j_4 = layoutNodeDrawScope.mo58toPx0680j_4(ClipScrollableContainerKt.MaxSupportedElevation);
        boolean z2 = EdgeEffectWrapper.isAnimating(edgeEffectWrapper2.topEffect) || EdgeEffectWrapper.isStretched(edgeEffectWrapper2.topEffectNegation) || EdgeEffectWrapper.isAnimating(edgeEffectWrapper2.bottomEffect) || EdgeEffectWrapper.isStretched(edgeEffectWrapper2.bottomEffectNegation);
        boolean z3 = EdgeEffectWrapper.isAnimating(edgeEffectWrapper2.leftEffect) || EdgeEffectWrapper.isStretched(edgeEffectWrapper2.leftEffectNegation) || EdgeEffectWrapper.isAnimating(edgeEffectWrapper2.rightEffect) || EdgeEffectWrapper.isStretched(edgeEffectWrapper2.rightEffectNegation);
        if (z2 && z3) {
            j = 4294967295L;
            getRenderNode().setPosition(0, 0, canvas3.getWidth(), canvas3.getHeight());
        } else {
            j = 4294967295L;
            if (z2) {
                getRenderNode().setPosition(0, 0, (MathKt__MathJVMKt.roundToInt(fMo58toPx0680j_4) * 2) + canvas3.getWidth(), canvas3.getHeight());
            } else {
                if (!z3) {
                    layoutNodeDrawScope.drawContent();
                    return;
                }
                getRenderNode().setPosition(0, 0, canvas3.getWidth(), (MathKt__MathJVMKt.roundToInt(fMo58toPx0680j_4) * 2) + canvas3.getHeight());
            }
        }
        RecordingCanvas recordingCanvasBeginRecording = getRenderNode().beginRecording();
        if (EdgeEffectWrapper.isStretched(edgeEffectWrapper2.leftEffectNegation)) {
            EdgeEffect edgeEffectCreateEdgeEffect = edgeEffectWrapper2.leftEffectNegation;
            if (edgeEffectCreateEdgeEffect == null) {
                edgeEffectCreateEdgeEffect = edgeEffectWrapper2.createEdgeEffect(Orientation.Horizontal);
                edgeEffectWrapper2.leftEffectNegation = edgeEffectCreateEdgeEffect;
            }
            drawWithRotation(90.0f, edgeEffectCreateEdgeEffect, recordingCanvasBeginRecording);
            edgeEffectCreateEdgeEffect.finish();
        }
        if (EdgeEffectWrapper.isAnimating(edgeEffectWrapper2.leftEffect)) {
            EdgeEffect orCreateLeftEffect = edgeEffectWrapper2.getOrCreateLeftEffect();
            zDrawWithRotation = drawWithRotation(270.0f, orCreateLeftEffect, recordingCanvasBeginRecording);
            c = ' ';
            if (EdgeEffectWrapper.isStretched(edgeEffectWrapper2.leftEffect)) {
                z = z3;
                float fIntBitsToFloat = Float.intBitsToFloat((int) (androidEdgeEffectOverscrollEffect.m21displacementF1C5BW0$foundation_release() & j));
                EdgeEffectCompat edgeEffectCompat = EdgeEffectCompat.INSTANCE;
                EdgeEffect edgeEffectCreateEdgeEffect2 = edgeEffectWrapper2.leftEffectNegation;
                if (edgeEffectCreateEdgeEffect2 == null) {
                    edgeEffectCreateEdgeEffect2 = edgeEffectWrapper2.createEdgeEffect(Orientation.Horizontal);
                    edgeEffectWrapper2.leftEffectNegation = edgeEffectCreateEdgeEffect2;
                }
                edgeEffectCompat.getClass();
                EdgeEffectCompat.onPullDistanceCompat(edgeEffectCreateEdgeEffect2, EdgeEffectCompat.getDistanceCompat(orCreateLeftEffect), 1 - fIntBitsToFloat);
            } else {
                z = z3;
            }
        } else {
            c = ' ';
            z = z3;
            zDrawWithRotation = false;
        }
        if (EdgeEffectWrapper.isStretched(edgeEffectWrapper2.topEffectNegation)) {
            EdgeEffect edgeEffectCreateEdgeEffect3 = edgeEffectWrapper2.topEffectNegation;
            if (edgeEffectCreateEdgeEffect3 == null) {
                edgeEffectCreateEdgeEffect3 = edgeEffectWrapper2.createEdgeEffect(Orientation.Vertical);
                edgeEffectWrapper2.topEffectNegation = edgeEffectCreateEdgeEffect3;
            }
            drawWithRotation(180.0f, edgeEffectCreateEdgeEffect3, recordingCanvasBeginRecording);
            edgeEffectCreateEdgeEffect3.finish();
        }
        if (EdgeEffectWrapper.isAnimating(edgeEffectWrapper2.topEffect)) {
            EdgeEffect orCreateTopEffect = edgeEffectWrapper2.getOrCreateTopEffect();
            zDrawWithRotation = drawWithRotation(0.0f, orCreateTopEffect, recordingCanvasBeginRecording) || zDrawWithRotation;
            if (EdgeEffectWrapper.isStretched(edgeEffectWrapper2.topEffect)) {
                canvasDrawScope = canvasDrawScope2;
                float fIntBitsToFloat2 = Float.intBitsToFloat((int) (androidEdgeEffectOverscrollEffect.m21displacementF1C5BW0$foundation_release() >> c));
                EdgeEffectCompat edgeEffectCompat2 = EdgeEffectCompat.INSTANCE;
                EdgeEffect edgeEffectCreateEdgeEffect4 = edgeEffectWrapper2.topEffectNegation;
                if (edgeEffectCreateEdgeEffect4 == null) {
                    edgeEffectCreateEdgeEffect4 = edgeEffectWrapper2.createEdgeEffect(Orientation.Vertical);
                    edgeEffectWrapper2.topEffectNegation = edgeEffectCreateEdgeEffect4;
                }
                edgeEffectCompat2.getClass();
                EdgeEffectCompat.onPullDistanceCompat(edgeEffectCreateEdgeEffect4, EdgeEffectCompat.getDistanceCompat(orCreateTopEffect), fIntBitsToFloat2);
            } else {
                canvasDrawScope = canvasDrawScope2;
            }
        }
        if (EdgeEffectWrapper.isStretched(edgeEffectWrapper2.rightEffectNegation)) {
            EdgeEffect edgeEffectCreateEdgeEffect5 = edgeEffectWrapper2.rightEffectNegation;
            if (edgeEffectCreateEdgeEffect5 == null) {
                edgeEffectCreateEdgeEffect5 = edgeEffectWrapper2.createEdgeEffect(Orientation.Horizontal);
                edgeEffectWrapper2.rightEffectNegation = edgeEffectCreateEdgeEffect5;
            }
            drawWithRotation(270.0f, edgeEffectCreateEdgeEffect5, recordingCanvasBeginRecording);
            edgeEffectCreateEdgeEffect5.finish();
        }
        if (EdgeEffectWrapper.isAnimating(edgeEffectWrapper2.rightEffect)) {
            EdgeEffect orCreateRightEffect = edgeEffectWrapper2.getOrCreateRightEffect();
            zDrawWithRotation = drawWithRotation(90.0f, orCreateRightEffect, recordingCanvasBeginRecording) || zDrawWithRotation;
            if (EdgeEffectWrapper.isStretched(edgeEffectWrapper2.rightEffect)) {
                float fIntBitsToFloat3 = Float.intBitsToFloat((int) (androidEdgeEffectOverscrollEffect.m21displacementF1C5BW0$foundation_release() & j));
                EdgeEffectCompat edgeEffectCompat3 = EdgeEffectCompat.INSTANCE;
                EdgeEffect edgeEffectCreateEdgeEffect6 = edgeEffectWrapper2.rightEffectNegation;
                if (edgeEffectCreateEdgeEffect6 == null) {
                    edgeEffectCreateEdgeEffect6 = edgeEffectWrapper2.createEdgeEffect(Orientation.Horizontal);
                    edgeEffectWrapper2.rightEffectNegation = edgeEffectCreateEdgeEffect6;
                }
                edgeEffectCompat3.getClass();
                EdgeEffectCompat.onPullDistanceCompat(edgeEffectCreateEdgeEffect6, EdgeEffectCompat.getDistanceCompat(orCreateRightEffect), fIntBitsToFloat3);
            }
        }
        if (EdgeEffectWrapper.isStretched(edgeEffectWrapper2.bottomEffectNegation)) {
            EdgeEffect edgeEffectCreateEdgeEffect7 = edgeEffectWrapper2.bottomEffectNegation;
            if (edgeEffectCreateEdgeEffect7 == null) {
                edgeEffectCreateEdgeEffect7 = edgeEffectWrapper2.createEdgeEffect(Orientation.Vertical);
                edgeEffectWrapper2.bottomEffectNegation = edgeEffectCreateEdgeEffect7;
            }
            drawWithRotation(0.0f, edgeEffectCreateEdgeEffect7, recordingCanvasBeginRecording);
            edgeEffectCreateEdgeEffect7.finish();
        }
        if (EdgeEffectWrapper.isAnimating(edgeEffectWrapper2.bottomEffect)) {
            EdgeEffect orCreateBottomEffect = edgeEffectWrapper2.getOrCreateBottomEffect();
            boolean z4 = drawWithRotation(180.0f, orCreateBottomEffect, recordingCanvasBeginRecording) || zDrawWithRotation;
            if (EdgeEffectWrapper.isStretched(edgeEffectWrapper2.bottomEffect)) {
                float fIntBitsToFloat4 = Float.intBitsToFloat((int) (androidEdgeEffectOverscrollEffect.m21displacementF1C5BW0$foundation_release() >> c));
                EdgeEffectCompat edgeEffectCompat4 = EdgeEffectCompat.INSTANCE;
                EdgeEffect edgeEffectCreateEdgeEffect8 = edgeEffectWrapper2.bottomEffectNegation;
                if (edgeEffectCreateEdgeEffect8 == null) {
                    edgeEffectCreateEdgeEffect8 = edgeEffectWrapper2.createEdgeEffect(Orientation.Vertical);
                    edgeEffectWrapper2.bottomEffectNegation = edgeEffectCreateEdgeEffect8;
                }
                edgeEffectCompat4.getClass();
                EdgeEffectCompat.onPullDistanceCompat(edgeEffectCreateEdgeEffect8, EdgeEffectCompat.getDistanceCompat(orCreateBottomEffect), 1 - fIntBitsToFloat4);
            }
            zDrawWithRotation = z4;
        }
        if (zDrawWithRotation) {
            androidEdgeEffectOverscrollEffect.invalidateOverscroll$foundation_release();
        }
        float f3 = z ? 0.0f : fMo58toPx0680j_4;
        if (z2) {
            fMo58toPx0680j_4 = 0.0f;
        }
        LayoutDirection layoutDirection = layoutNodeDrawScope.getLayoutDirection();
        AndroidCanvas androidCanvas = new AndroidCanvas();
        androidCanvas.internalCanvas = recordingCanvasBeginRecording;
        long jMo547getSizeNHjbRc2 = canvasDrawScope.mo547getSizeNHjbRc();
        Density density = canvasDrawScope.drawContext.getDensity();
        LayoutDirection layoutDirection2 = canvasDrawScope.drawContext.getLayoutDirection();
        androidx.compose.ui.graphics.Canvas canvas4 = canvasDrawScope.drawContext.getCanvas();
        long jM528getSizeNHjbRc = canvasDrawScope.drawContext.m528getSizeNHjbRc();
        CanvasDrawScope$drawContext$1 canvasDrawScope$drawContext$1 = canvasDrawScope.drawContext;
        GraphicsLayer graphicsLayer = canvasDrawScope$drawContext$1.graphicsLayer;
        canvasDrawScope$drawContext$1.setDensity(layoutNodeDrawScope);
        canvasDrawScope$drawContext$1.setLayoutDirection(layoutDirection);
        canvasDrawScope$drawContext$1.setCanvas(androidCanvas);
        canvasDrawScope$drawContext$1.m529setSizeuvyYCjk(jMo547getSizeNHjbRc2);
        canvasDrawScope$drawContext$1.graphicsLayer = null;
        androidCanvas.save();
        try {
            canvasDrawScope.drawContext.transform.translate(f3, fMo58toPx0680j_4);
            try {
                layoutNodeDrawScope.drawContent();
                androidCanvas.restore();
                CanvasDrawScope$drawContext$1 canvasDrawScope$drawContext$12 = canvasDrawScope.drawContext;
                canvasDrawScope$drawContext$12.setDensity(density);
                canvasDrawScope$drawContext$12.setLayoutDirection(layoutDirection2);
                canvasDrawScope$drawContext$12.setCanvas(canvas4);
                canvasDrawScope$drawContext$12.m529setSizeuvyYCjk(jM528getSizeNHjbRc);
                canvasDrawScope$drawContext$12.graphicsLayer = graphicsLayer;
                getRenderNode().endRecording();
                int iSave = canvas3.save();
                canvas3.translate(f, f2);
                canvas3.drawRenderNode(getRenderNode());
                canvas3.restoreToCount(iSave);
            } finally {
                canvasDrawScope.drawContext.transform.translate(-f3, -fMo58toPx0680j_4);
            }
        } catch (Throwable th) {
            androidCanvas.restore();
            CanvasDrawScope$drawContext$1 canvasDrawScope$drawContext$13 = canvasDrawScope.drawContext;
            canvasDrawScope$drawContext$13.setDensity(density);
            canvasDrawScope$drawContext$13.setLayoutDirection(layoutDirection2);
            canvasDrawScope$drawContext$13.setCanvas(canvas4);
            canvasDrawScope$drawContext$13.m529setSizeuvyYCjk(jM528getSizeNHjbRc);
            canvasDrawScope$drawContext$13.graphicsLayer = graphicsLayer;
            throw th;
        }
    }

    public final RenderNode getRenderNode() {
        RenderNode renderNode = this._renderNode;
        if (renderNode != null) {
            return renderNode;
        }
        RenderNode renderNode2 = new RenderNode("AndroidEdgeEffectOverscrollEffect");
        this._renderNode = renderNode2;
        return renderNode2;
    }
}
