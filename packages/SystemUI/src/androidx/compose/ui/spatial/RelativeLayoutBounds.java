package androidx.compose.ui.spatial;

import androidx.compose.ui.node.DelegatableNode;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class RelativeLayoutBounds {
    public final float[] viewToWindowMatrix;

    public /* synthetic */ RelativeLayoutBounds(long j, long j2, long j3, long j4, float[] fArr, DelegatableNode delegatableNode, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2, j3, j4, fArr, delegatableNode);
    }

    private RelativeLayoutBounds(long j, long j2, long j3, long j4, float[] fArr, DelegatableNode delegatableNode) {
        this.viewToWindowMatrix = fArr;
    }
}
