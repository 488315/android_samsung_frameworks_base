package androidx.compose.foundation;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNode;
import androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNodeImpl;
import androidx.compose.ui.node.ModifierNodeElement;
import androidx.compose.ui.node.SemanticsModifierNodeKt;
import androidx.compose.ui.semantics.Role;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
final class CombinedClickableElement extends ModifierNodeElement<CombinedClickableNode> {
    public final boolean enabled;
    public final boolean hapticFeedbackEnabled;
    public final IndicationNodeFactory indicationNodeFactory;
    public final MutableInteractionSource interactionSource;
    public final Function0 onClick;
    public final String onClickLabel;
    public final Function0 onDoubleClick;
    public final Function0 onLongClick;
    public final String onLongClickLabel;
    public final Role role;

    public /* synthetic */ CombinedClickableElement(MutableInteractionSource mutableInteractionSource, IndicationNodeFactory indicationNodeFactory, boolean z, String str, Role role, Function0 function0, String str2, Function0 function02, Function0 function03, boolean z2, DefaultConstructorMarker defaultConstructorMarker) {
        this(mutableInteractionSource, indicationNodeFactory, z, str, role, function0, str2, function02, function03, z2);
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new CombinedClickableNode(this.onClick, this.onLongClickLabel, this.onLongClick, this.onDoubleClick, this.hapticFeedbackEnabled, this.interactionSource, this.indicationNodeFactory, this.enabled, this.onClickLabel, this.role, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || CombinedClickableElement.class != obj.getClass()) {
            return false;
        }
        CombinedClickableElement combinedClickableElement = (CombinedClickableElement) obj;
        return Intrinsics.areEqual(this.interactionSource, combinedClickableElement.interactionSource) && Intrinsics.areEqual(this.indicationNodeFactory, combinedClickableElement.indicationNodeFactory) && this.enabled == combinedClickableElement.enabled && Intrinsics.areEqual(this.onClickLabel, combinedClickableElement.onClickLabel) && Intrinsics.areEqual(this.role, combinedClickableElement.role) && this.onClick == combinedClickableElement.onClick && Intrinsics.areEqual(this.onLongClickLabel, combinedClickableElement.onLongClickLabel) && this.onLongClick == combinedClickableElement.onLongClick && this.onDoubleClick == combinedClickableElement.onDoubleClick && this.hapticFeedbackEnabled == combinedClickableElement.hapticFeedbackEnabled;
    }

    public final int hashCode() {
        MutableInteractionSource mutableInteractionSource = this.interactionSource;
        int iHashCode = (mutableInteractionSource != null ? mutableInteractionSource.hashCode() : 0) * 31;
        IndicationNodeFactory indicationNodeFactory = this.indicationNodeFactory;
        int iM = TransitionData$$ExternalSyntheticOutline0.m((iHashCode + (indicationNodeFactory != null ? indicationNodeFactory.hashCode() : 0)) * 31, 31, this.enabled);
        String str = this.onClickLabel;
        int iHashCode2 = (iM + (str != null ? str.hashCode() : 0)) * 31;
        Role role = this.role;
        int iHashCode3 = (this.onClick.hashCode() + ((iHashCode2 + (role != null ? Integer.hashCode(role.value) : 0)) * 31)) * 31;
        String str2 = this.onLongClickLabel;
        int iHashCode4 = (iHashCode3 + (str2 != null ? str2.hashCode() : 0)) * 31;
        Function0 function0 = this.onLongClick;
        int iHashCode5 = (iHashCode4 + (function0 != null ? function0.hashCode() : 0)) * 31;
        Function0 function02 = this.onDoubleClick;
        return Boolean.hashCode(this.hapticFeedbackEnabled) + ((iHashCode5 + (function02 != null ? function02.hashCode() : 0)) * 31);
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        boolean z;
        SuspendingPointerInputModifierNode suspendingPointerInputModifierNode;
        CombinedClickableNode combinedClickableNode = (CombinedClickableNode) node;
        combinedClickableNode.hapticFeedbackEnabled = this.hapticFeedbackEnabled;
        String str = combinedClickableNode.onLongClickLabel;
        String str2 = this.onLongClickLabel;
        if (!Intrinsics.areEqual(str, str2)) {
            combinedClickableNode.onLongClickLabel = str2;
            SemanticsModifierNodeKt.invalidateSemantics(combinedClickableNode);
        }
        boolean z2 = combinedClickableNode.onLongClick == null;
        Function0 function0 = this.onLongClick;
        if (z2 != (function0 == null)) {
            combinedClickableNode.disposeInteractions();
            SemanticsModifierNodeKt.invalidateSemantics(combinedClickableNode);
            z = true;
        } else {
            z = false;
        }
        combinedClickableNode.onLongClick = function0;
        boolean z3 = combinedClickableNode.onDoubleClick == null;
        Function0 function02 = this.onDoubleClick;
        if (z3 != (function02 == null)) {
            z = true;
        }
        combinedClickableNode.onDoubleClick = function02;
        boolean z4 = combinedClickableNode.enabled;
        boolean z5 = this.enabled;
        if (z4 != z5) {
            z = true;
        }
        combinedClickableNode.m18updateCommonQzZPfjk(this.interactionSource, this.indicationNodeFactory, z5, this.onClickLabel, this.role, this.onClick);
        if (!z || (suspendingPointerInputModifierNode = combinedClickableNode.pointerInputNode) == null) {
            return;
        }
        ((SuspendingPointerInputModifierNodeImpl) suspendingPointerInputModifierNode).resetPointerInputHandler();
        Unit unit = Unit.INSTANCE;
    }

    private CombinedClickableElement(MutableInteractionSource mutableInteractionSource, IndicationNodeFactory indicationNodeFactory, boolean z, String str, Role role, Function0 function0, String str2, Function0 function02, Function0 function03, boolean z2) {
        this.interactionSource = mutableInteractionSource;
        this.indicationNodeFactory = indicationNodeFactory;
        this.enabled = z;
        this.onClickLabel = str;
        this.role = role;
        this.onClick = function0;
        this.onLongClickLabel = str2;
        this.onLongClick = function02;
        this.onDoubleClick = function03;
        this.hapticFeedbackEnabled = z2;
    }
}
