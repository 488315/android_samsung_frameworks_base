package androidx.compose.ui.semantics;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ModifierNodeElement;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AppendedSemanticsElement extends ModifierNodeElement<CoreSemanticsModifierNode> implements SemanticsModifier {
    public final boolean mergeDescendants;
    public final Function1 properties;

    public AppendedSemanticsElement(boolean z, Function1 function1) {
        this.mergeDescendants = z;
        this.properties = function1;
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new CoreSemanticsModifierNode(this.mergeDescendants, false, this.properties);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AppendedSemanticsElement)) {
            return false;
        }
        AppendedSemanticsElement appendedSemanticsElement = (AppendedSemanticsElement) obj;
        return this.mergeDescendants == appendedSemanticsElement.mergeDescendants && this.properties == appendedSemanticsElement.properties;
    }

    @Override // androidx.compose.ui.semantics.SemanticsModifier
    public final SemanticsConfiguration getSemanticsConfiguration() {
        SemanticsConfiguration semanticsConfiguration = new SemanticsConfiguration();
        semanticsConfiguration.isMergingSemanticsOfDescendants = this.mergeDescendants;
        this.properties.mo779invoke(semanticsConfiguration);
        return semanticsConfiguration;
    }

    public final int hashCode() {
        return this.properties.hashCode() + (Boolean.hashCode(this.mergeDescendants) * 31);
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        CoreSemanticsModifierNode coreSemanticsModifierNode = (CoreSemanticsModifierNode) node;
        coreSemanticsModifierNode.mergeDescendants = this.mergeDescendants;
        coreSemanticsModifierNode.properties = this.properties;
    }
}
