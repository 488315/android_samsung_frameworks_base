package androidx.compose.ui.node;

import androidx.compose.ui.node.LayoutNode;
import java.util.Comparator;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class LayoutNode$$ExternalSyntheticLambda0 implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        LayoutNode layoutNode = (LayoutNode) obj;
        LayoutNode layoutNode2 = (LayoutNode) obj2;
        LayoutNode.Companion companion = LayoutNode.Companion;
        float f = layoutNode.layoutDelegate.measurePassDelegate.zIndex;
        float f2 = layoutNode2.layoutDelegate.measurePassDelegate.zIndex;
        return f == f2 ? Intrinsics.compare(layoutNode.getPlaceOrder$ui_release(), layoutNode2.getPlaceOrder$ui_release()) : Float.compare(f, f2);
    }
}
