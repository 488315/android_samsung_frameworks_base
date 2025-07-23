package androidx.compose.foundation.layout;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ModifierNodeElement;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class FillElement extends ModifierNodeElement<FillNode> {
    public static final Companion Companion = new Companion(null);
    public final Direction direction;
    public final float fraction;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public FillElement(Direction direction, float f, String str) {
        this.direction = direction;
        this.fraction = f;
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new FillNode(this.direction, this.fraction);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FillElement)) {
            return false;
        }
        FillElement fillElement = (FillElement) obj;
        return this.direction == fillElement.direction && this.fraction == fillElement.fraction;
    }

    public final int hashCode() {
        return Float.hashCode(this.fraction) + (this.direction.hashCode() * 31);
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        FillNode fillNode = (FillNode) node;
        fillNode.direction = this.direction;
        fillNode.fraction = this.fraction;
    }
}
