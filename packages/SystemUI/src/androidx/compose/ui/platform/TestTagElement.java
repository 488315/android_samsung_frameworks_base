package androidx.compose.ui.platform;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ModifierNodeElement;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class TestTagElement extends ModifierNodeElement<TestTagNode> {
    public final String tag;

    public TestTagElement(String str) {
        this.tag = str;
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new TestTagNode(this.tag);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TestTagElement)) {
            return false;
        }
        return Intrinsics.areEqual(this.tag, ((TestTagElement) obj).tag);
    }

    public final int hashCode() {
        return this.tag.hashCode();
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        ((TestTagNode) node).tag = this.tag;
    }
}
