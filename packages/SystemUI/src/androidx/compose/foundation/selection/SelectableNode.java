package androidx.compose.foundation.selection;

import androidx.compose.foundation.ClickableNode;
import androidx.compose.foundation.IndicationNodeFactory;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.ui.semantics.Role;
import androidx.compose.ui.semantics.SemanticsProperties;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyKey;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.reflect.KProperty;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class SelectableNode extends ClickableNode {
    public boolean selected;

    public /* synthetic */ SelectableNode(boolean z, MutableInteractionSource mutableInteractionSource, IndicationNodeFactory indicationNodeFactory, boolean z2, Role role, Function0 function0, DefaultConstructorMarker defaultConstructorMarker) {
        this(z, mutableInteractionSource, indicationNodeFactory, z2, role, function0);
    }

    @Override // androidx.compose.foundation.AbstractClickableNode
    public final void applyAdditionalSemantics(SemanticsPropertyReceiver semanticsPropertyReceiver) {
        boolean z = this.selected;
        KProperty[] kPropertyArr = SemanticsPropertiesKt.$$delegatedProperties;
        SemanticsProperties.INSTANCE.getClass();
        SemanticsPropertyKey semanticsPropertyKey = SemanticsProperties.Selected;
        KProperty kProperty = SemanticsPropertiesKt.$$delegatedProperties[19];
        semanticsPropertyKey.setValue(semanticsPropertyReceiver, Boolean.valueOf(z));
    }

    private SelectableNode(boolean z, MutableInteractionSource mutableInteractionSource, IndicationNodeFactory indicationNodeFactory, boolean z2, Role role, Function0 function0) {
        super(mutableInteractionSource, indicationNodeFactory, z2, null, role, function0, null);
        this.selected = z;
    }
}
