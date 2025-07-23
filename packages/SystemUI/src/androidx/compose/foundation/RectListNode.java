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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        MutableVector currentRects = currentRects();
        Rect rect = this.androidRect;
        if (rect != null) {
            currentRects.remove(rect);
        }
        updateRects(currentRects);
        this.androidRect = null;
    }

    @Override // androidx.compose.ui.node.GlobalPositionAwareModifierNode
    public final void onGloballyPositioned(NodeCoordinator nodeCoordinator) {
        Rect rect;
        Function1 function1 = this.rect;
        if (function1 == null) {
            androidx.compose.ui.geometry.Rect localBoundingBoxOf = LayoutCoordinatesKt.findRootCoordinates(nodeCoordinator).localBoundingBoxOf(nodeCoordinator, true);
            rect = new Rect(MathKt__MathJVMKt.roundToInt(localBoundingBoxOf.left), MathKt__MathJVMKt.roundToInt(localBoundingBoxOf.top), MathKt__MathJVMKt.roundToInt(localBoundingBoxOf.right), MathKt__MathJVMKt.roundToInt(localBoundingBoxOf.bottom));
        } else {
            androidx.compose.ui.geometry.Rect rect2 = (androidx.compose.ui.geometry.Rect) function1.mo779invoke(nodeCoordinator);
            LayoutCoordinates findRootCoordinates = LayoutCoordinatesKt.findRootCoordinates(nodeCoordinator);
            long mo611localPositionOfR5De75A = findRootCoordinates.mo611localPositionOfR5De75A(nodeCoordinator, rect2.m409getTopLeftF1C5BW0());
            Offset.Companion companion = Offset.Companion;
            long mo611localPositionOfR5De75A2 = findRootCoordinates.mo611localPositionOfR5De75A(nodeCoordinator, (Float.floatToRawIntBits(rect2.right) << 32) | (Float.floatToRawIntBits(rect2.top) & 4294967295L));
            long mo611localPositionOfR5De75A3 = findRootCoordinates.mo611localPositionOfR5De75A(nodeCoordinator, (Float.floatToRawIntBits(rect2.bottom) & 4294967295L) | (Float.floatToRawIntBits(rect2.left) << 32));
            long mo611localPositionOfR5De75A4 = findRootCoordinates.mo611localPositionOfR5De75A(nodeCoordinator, rect2.m406getBottomRightF1C5BW0());
            int i = (int) (mo611localPositionOfR5De75A >> 32);
            float intBitsToFloat = Float.intBitsToFloat(i);
            int i2 = (int) (mo611localPositionOfR5De75A2 >> 32);
            int i3 = (int) (mo611localPositionOfR5De75A3 >> 32);
            int i4 = (int) (mo611localPositionOfR5De75A4 >> 32);
            float[] fArr = {Float.intBitsToFloat(i2), Float.intBitsToFloat(i3), Float.intBitsToFloat(i4)};
            int i5 = 0;
            for (int i6 = 3; i5 < i6; i6 = 3) {
                intBitsToFloat = Math.min(intBitsToFloat, fArr[i5]);
                i5++;
            }
            int i7 = (int) (mo611localPositionOfR5De75A & 4294967295L);
            float intBitsToFloat2 = Float.intBitsToFloat(i7);
            int i8 = (int) (mo611localPositionOfR5De75A2 & 4294967295L);
            int i9 = (int) (mo611localPositionOfR5De75A3 & 4294967295L);
            int i10 = (int) (mo611localPositionOfR5De75A4 & 4294967295L);
            float[] fArr2 = {Float.intBitsToFloat(i8), Float.intBitsToFloat(i9), Float.intBitsToFloat(i10)};
            for (int i11 = 0; i11 < 3; i11++) {
                intBitsToFloat2 = Math.min(intBitsToFloat2, fArr2[i11]);
            }
            rect = new Rect(MathKt__MathJVMKt.roundToInt(intBitsToFloat), MathKt__MathJVMKt.roundToInt(intBitsToFloat2), MathKt__MathJVMKt.roundToInt(ComparisonsKt___ComparisonsJvmKt.maxOf(Float.intBitsToFloat(i), Float.intBitsToFloat(i2), Float.intBitsToFloat(i3), Float.intBitsToFloat(i4))), MathKt__MathJVMKt.roundToInt(ComparisonsKt___ComparisonsJvmKt.maxOf(Float.intBitsToFloat(i7), Float.intBitsToFloat(i8), Float.intBitsToFloat(i9), Float.intBitsToFloat(i10))));
        }
        MutableVector currentRects = currentRects();
        Object obj = this.androidRect;
        if (obj != null) {
            currentRects.remove(obj);
        }
        if (!rect.isEmpty()) {
            currentRects.add(rect);
        }
        updateRects(currentRects);
        this.androidRect = rect;
    }

    public abstract void updateRects(MutableVector mutableVector);
}
