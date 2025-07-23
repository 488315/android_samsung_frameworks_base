package androidx.compose.foundation.layout;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ModifierNodeElement;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class WrapContentElement extends ModifierNodeElement<WrapContentNode> {
    public static final Companion Companion = new Companion(null);
    public final Object align;
    public final Function2 alignmentCallback;
    public final Direction direction;
    public final boolean unbounded;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public WrapContentElement(Direction direction, boolean z, Function2 function2, Object obj, String str) {
        this.direction = direction;
        this.unbounded = z;
        this.alignmentCallback = function2;
        this.align = obj;
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new WrapContentNode(this.direction, this.unbounded, this.alignmentCallback);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || WrapContentElement.class != obj.getClass()) {
            return false;
        }
        WrapContentElement wrapContentElement = (WrapContentElement) obj;
        return this.direction == wrapContentElement.direction && this.unbounded == wrapContentElement.unbounded && Intrinsics.areEqual(this.align, wrapContentElement.align);
    }

    public final int hashCode() {
        return this.align.hashCode() + TransitionData$$ExternalSyntheticOutline0.m(this.direction.hashCode() * 31, 31, this.unbounded);
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        WrapContentNode wrapContentNode = (WrapContentNode) node;
        wrapContentNode.direction = this.direction;
        wrapContentNode.unbounded = this.unbounded;
        wrapContentNode.alignmentCallback = this.alignmentCallback;
    }
}
