package androidx.compose.foundation;

import android.graphics.Rect;
import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.node.DelegatableNode_androidKt;
import java.util.List;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class ExcludeFromSystemGestureNode extends RectListNode {
    public ExcludeFromSystemGestureNode(Function1 function1) {
        super(function1);
    }

    @Override // androidx.compose.foundation.RectListNode
    public final MutableVector currentRects() {
        MutableVector mutableVector = new MutableVector(new Rect[16], 0);
        mutableVector.addAll(mutableVector.size, (List) DelegatableNode_androidKt.requireView(this).getSystemGestureExclusionRects());
        return mutableVector;
    }

    @Override // androidx.compose.foundation.RectListNode
    public final void updateRects(MutableVector mutableVector) {
        DelegatableNode_androidKt.requireView(this).setSystemGestureExclusionRects(mutableVector.asMutableList());
    }
}
