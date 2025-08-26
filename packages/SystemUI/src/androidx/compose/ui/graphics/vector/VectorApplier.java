package androidx.compose.ui.graphics.vector;

import androidx.compose.runtime.AbstractApplier;
import java.util.ArrayList;

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
        GroupComponent groupComponentAsGroup = asGroup((VNode) this.current);
        groupComponentAsGroup.getClass();
        int i4 = 0;
        if (i > i2) {
            while (i4 < i3) {
                VNode vNode = (VNode) ((ArrayList) groupComponentAsGroup.children).get(i);
                ((ArrayList) groupComponentAsGroup.children).remove(i);
                ((ArrayList) groupComponentAsGroup.children).add(i2, vNode);
                i2++;
                i4++;
            }
        } else {
            while (i4 < i3) {
                VNode vNode2 = (VNode) ((ArrayList) groupComponentAsGroup.children).get(i);
                ((ArrayList) groupComponentAsGroup.children).remove(i);
                ((ArrayList) groupComponentAsGroup.children).add(i2 - 1, vNode2);
                i4++;
            }
        }
        groupComponentAsGroup.invalidate();
    }

    @Override // androidx.compose.runtime.AbstractApplier
    public final void onClear() {
        GroupComponent groupComponentAsGroup = asGroup((VNode) this.root);
        groupComponentAsGroup.remove(0, ((ArrayList) groupComponentAsGroup.children).size());
    }

    @Override // androidx.compose.runtime.Applier
    public final void remove(int i, int i2) {
        asGroup((VNode) this.current).remove(i, i2);
    }
}
