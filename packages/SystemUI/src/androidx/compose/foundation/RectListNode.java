package androidx.compose.foundation;

import android.graphics.Rect;
import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.layout.LayoutCoordinates;
import androidx.compose.ui.layout.LayoutCoordinatesKt;
import androidx.compose.ui.node.GlobalPositionAwareModifierNode;
import androidx.compose.ui.node.NodeCoordinator;
import kotlin.comparisons.ComparisonsKt___ComparisonsJvmKt;
import kotlin.jvm.functions.Function1;
import kotlin.math.MathKt__MathJVMKt;

/* loaded from: classes.dex */
public abstract class RectListNode extends Modifier.Node implements GlobalPositionAwareModifierNode {
    public Rect androidRect;
    public Function1 rect;

    public RectListNode(Function1 function1) {
        this.rect = function1;
    }

    public abstract MutableVector currentRects();

    @Override // androidx.compose.ui.Modifier.Node
    public final void onDetach() {
        MutableVector mutableVectorCurrentRects = currentRects();
        Rect rect = this.androidRect;
        if (rect != null) {
            mutableVectorCurrentRects.remove(rect);
        }
        updateRects(mutableVectorCurrentRects);
        this.androidRect = null;
    }

    @Override // androidx.compose.ui.node.GlobalPositionAwareModifierNode
    public final void onGloballyPositioned(NodeCoordinator nodeCoordinator) {
        Rect rect;
        Function1 function1 = this.rect;
        if (function1 == null) {
            androidx.compose.ui.geometry.Rect rectLocalBoundingBoxOf = LayoutCoordinatesKt.findRootCoordinates(nodeCoordinator).localBoundingBoxOf(nodeCoordinator, true);
            rect = new Rect(MathKt__MathJVMKt.roundToInt(rectLocalBoundingBoxOf.left), MathKt__MathJVMKt.roundToInt(rectLocalBoundingBoxOf.top), MathKt__MathJVMKt.roundToInt(rectLocalBoundingBoxOf.right), MathKt__MathJVMKt.roundToInt(rectLocalBoundingBoxOf.bottom));
        } else {
            androidx.compose.ui.geometry.Rect rect2 = (androidx.compose.ui.geometry.Rect) function1.mo781invoke(nodeCoordinator);
            LayoutCoordinates layoutCoordinatesFindRootCoordinates = LayoutCoordinatesKt.findRootCoordinates(nodeCoordinator);
            long jMo613localPositionOfR5De75A = layoutCoordinatesFindRootCoordinates.mo613localPositionOfR5De75A(nodeCoordinator, rect2.m411getTopLeftF1C5BW0());
            Offset.Companion companion = Offset.Companion;
            long jMo613localPositionOfR5De75A2 = layoutCoordinatesFindRootCoordinates.mo613localPositionOfR5De75A(nodeCoordinator, (Float.floatToRawIntBits(rect2.right) << 32) | (Float.floatToRawIntBits(rect2.top) & 4294967295L));
            long jMo613localPositionOfR5De75A3 = layoutCoordinatesFindRootCoordinates.mo613localPositionOfR5De75A(nodeCoordinator, (Float.floatToRawIntBits(rect2.bottom) & 4294967295L) | (Float.floatToRawIntBits(rect2.left) << 32));
            long jMo613localPositionOfR5De75A4 = layoutCoordinatesFindRootCoordinates.mo613localPositionOfR5De75A(nodeCoordinator, rect2.m408getBottomRightF1C5BW0());
            int i = (int) (jMo613localPositionOfR5De75A >> 32);
            float fIntBitsToFloat = Float.intBitsToFloat(i);
            int i2 = (int) (jMo613localPositionOfR5De75A2 >> 32);
            int i3 = (int) (jMo613localPositionOfR5De75A3 >> 32);
            int i4 = (int) (jMo613localPositionOfR5De75A4 >> 32);
            float[] fArr = {Float.intBitsToFloat(i2), Float.intBitsToFloat(i3), Float.intBitsToFloat(i4)};
            int i5 = 0;
            for (int i6 = 3; i5 < i6; i6 = 3) {
                fIntBitsToFloat = Math.min(fIntBitsToFloat, fArr[i5]);
                i5++;
            }
            int i7 = (int) (jMo613localPositionOfR5De75A & 4294967295L);
            float fIntBitsToFloat2 = Float.intBitsToFloat(i7);
            int i8 = (int) (jMo613localPositionOfR5De75A2 & 4294967295L);
            int i9 = (int) (jMo613localPositionOfR5De75A3 & 4294967295L);
            int i10 = (int) (jMo613localPositionOfR5De75A4 & 4294967295L);
            float[] fArr2 = {Float.intBitsToFloat(i8), Float.intBitsToFloat(i9), Float.intBitsToFloat(i10)};
            for (int i11 = 0; i11 < 3; i11++) {
                fIntBitsToFloat2 = Math.min(fIntBitsToFloat2, fArr2[i11]);
            }
            rect = new Rect(MathKt__MathJVMKt.roundToInt(fIntBitsToFloat), MathKt__MathJVMKt.roundToInt(fIntBitsToFloat2), MathKt__MathJVMKt.roundToInt(ComparisonsKt___ComparisonsJvmKt.maxOf(Float.intBitsToFloat(i), Float.intBitsToFloat(i2), Float.intBitsToFloat(i3), Float.intBitsToFloat(i4))), MathKt__MathJVMKt.roundToInt(ComparisonsKt___ComparisonsJvmKt.maxOf(Float.intBitsToFloat(i7), Float.intBitsToFloat(i8), Float.intBitsToFloat(i9), Float.intBitsToFloat(i10))));
        }
        MutableVector mutableVectorCurrentRects = currentRects();
        Object obj = this.androidRect;
        if (obj != null) {
            mutableVectorCurrentRects.remove(obj);
        }
        if (!rect.isEmpty()) {
            mutableVectorCurrentRects.add(rect);
        }
        updateRects(mutableVectorCurrentRects);
        this.androidRect = rect;
    }

    public abstract void updateRects(MutableVector mutableVector);
}
