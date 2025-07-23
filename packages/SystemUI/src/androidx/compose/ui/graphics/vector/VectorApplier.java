package androidx.compose.ui.graphics.vector;

import androidx.compose.runtime.AbstractApplier;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class VectorApplier extends AbstractApplier<VNode> {
    public VectorApplier(VNode vNode) {
        super(vNode);
    }

    public static GroupComponent asGroup(VNode vNode) {
        if (vNode instanceof GroupComponent) {
            return (GroupComponent) vNode;
        }
        throw new IllegalStateException("Cannot only insert VNode into Group");
    }

    @Override // androidx.compose.runtime.Applier
    public final void insertBottomUp(int i, Object obj) {
        asGroup((VNode) this.current).insertAt(i, (VNode) obj);
    }

    @Override // androidx.compose.runtime.Applier
    public final /* bridge */ /* synthetic */ void insertTopDown(int i, Object obj) {
    }

    @Override // androidx.compose.runtime.Applier
    public final void move(int i, int i2, int i3) {
        GroupComponent asGroup = asGroup((VNode) this.current);
        asGroup.getClass();
        int i4 = 0;
        if (i > i2) {
            while (i4 < i3) {
                VNode vNode = (VNode) ((ArrayList) asGroup.children).get(i);
                ((ArrayList) asGroup.children).remove(i);
                ((ArrayList) asGroup.children).add(i2, vNode);
                i2++;
                i4++;
            }
        } else {
            while (i4 < i3) {
                VNode vNode2 = (VNode) ((ArrayList) asGroup.children).get(i);
                ((ArrayList) asGroup.children).remove(i);
                ((ArrayList) asGroup.children).add(i2 - 1, vNode2);
                i4++;
            }
        }
        asGroup.invalidate();
    }

    @Override // androidx.compose.runtime.AbstractApplier
    public final void onClear() {
        GroupComponent asGroup = asGroup((VNode) this.root);
        asGroup.remove(0, ((ArrayList) asGroup.children).size());
    }

    @Override // androidx.compose.runtime.Applier
    public final void remove(int i, int i2) {
        asGroup((VNode) this.current).remove(i, i2);
    }
}
