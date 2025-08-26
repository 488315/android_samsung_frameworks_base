package com.android.compose.modifiers;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.node.ModifierNodeElement;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
final class BackgroundElement extends ModifierNodeElement<BackgroundNode> {
    public final Function0 alpha;
    public final Function0 color;
    public final Shape shape;

    public BackgroundElement(Function0 function0, Function0 function02, Shape shape, Function1 function1) {
        this.color = function0;
        this.alpha = function02;
        this.shape = shape;
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new BackgroundNode(this.color, this.alpha, this.shape);
    }

    public final boolean equals(Object obj) {
        BackgroundElement backgroundElement = obj instanceof BackgroundElement ? (BackgroundElement) obj : null;
        return backgroundElement != null && Intrinsics.areEqual(this.color, backgroundElement.color) && Intrinsics.areEqual(this.alpha, backgroundElement.alpha) && Intrinsics.areEqual(this.shape, backgroundElement.shape);
    }

    public final int hashCode() {
        return this.shape.hashCode() + ((this.alpha.hashCode() + (this.color.hashCode() * 31)) * 31);
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        BackgroundNode backgroundNode = (BackgroundNode) node;
        backgroundNode.color = this.color;
        backgroundNode.alpha = this.alpha;
        backgroundNode.shape = this.shape;
    }
}
