package androidx.compose.ui.spatial;

import androidx.compose.ui.node.DelegatableNode;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
