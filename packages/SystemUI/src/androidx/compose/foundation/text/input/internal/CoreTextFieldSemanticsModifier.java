package androidx.compose.foundation.text.input.internal;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.LegacyTextFieldState;
import androidx.compose.foundation.text.selection.TextFieldSelectionManager;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.focus.FocusRequester;
import androidx.compose.ui.node.ModifierNodeElement;
import androidx.compose.ui.text.input.ImeOptions;
import androidx.compose.ui.text.input.OffsetMapping;
import androidx.compose.ui.text.input.TextFieldValue;
import androidx.compose.ui.text.input.TransformedText;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class CoreTextFieldSemanticsModifier extends ModifierNodeElement<CoreTextFieldSemanticsModifierNode> {
    public final boolean enabled;
    public final FocusRequester focusRequester;
    public final ImeOptions imeOptions;
    public final boolean isPassword;
    public final TextFieldSelectionManager manager;
    public final OffsetMapping offsetMapping;
    public final boolean readOnly;
    public final LegacyTextFieldState state;
    public final TransformedText transformedText;
    public final TextFieldValue value;

    public CoreTextFieldSemanticsModifier(TransformedText transformedText, TextFieldValue textFieldValue, LegacyTextFieldState legacyTextFieldState, boolean z, boolean z2, boolean z3, OffsetMapping offsetMapping, TextFieldSelectionManager textFieldSelectionManager, ImeOptions imeOptions, FocusRequester focusRequester) {
        this.transformedText = transformedText;
        this.value = textFieldValue;
        this.state = legacyTextFieldState;
        this.readOnly = z;
        this.enabled = z2;
        this.isPassword = z3;
        this.offsetMapping = offsetMapping;
        this.manager = textFieldSelectionManager;
        this.imeOptions = imeOptions;
        this.focusRequester = focusRequester;
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new CoreTextFieldSemanticsModifierNode(this.transformedText, this.value, this.state, this.readOnly, this.enabled, this.isPassword, this.offsetMapping, this.manager, this.imeOptions, this.focusRequester);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CoreTextFieldSemanticsModifier)) {
            return false;
        }
        CoreTextFieldSemanticsModifier coreTextFieldSemanticsModifier = (CoreTextFieldSemanticsModifier) obj;
        return Intrinsics.areEqual(this.transformedText, coreTextFieldSemanticsModifier.transformedText) && Intrinsics.areEqual(this.value, coreTextFieldSemanticsModifier.value) && Intrinsics.areEqual(this.state, coreTextFieldSemanticsModifier.state) && this.readOnly == coreTextFieldSemanticsModifier.readOnly && this.enabled == coreTextFieldSemanticsModifier.enabled && this.isPassword == coreTextFieldSemanticsModifier.isPassword && Intrinsics.areEqual(this.offsetMapping, coreTextFieldSemanticsModifier.offsetMapping) && Intrinsics.areEqual(this.manager, coreTextFieldSemanticsModifier.manager) && Intrinsics.areEqual(this.imeOptions, coreTextFieldSemanticsModifier.imeOptions) && Intrinsics.areEqual(this.focusRequester, coreTextFieldSemanticsModifier.focusRequester);
    }

    public final int hashCode() {
        return this.focusRequester.hashCode() + ((this.imeOptions.hashCode() + ((this.manager.hashCode() + ((this.offsetMapping.hashCode() + TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m((this.state.hashCode() + ((this.value.hashCode() + (this.transformedText.hashCode() * 31)) * 31)) * 31, 31, this.readOnly), 31, this.enabled), 31, this.isPassword)) * 31)) * 31)) * 31);
    }

    public final String toString() {
        return "CoreTextFieldSemanticsModifier(transformedText=" + this.transformedText + ", value=" + this.value + ", state=" + this.state + ", readOnly=" + this.readOnly + ", enabled=" + this.enabled + ", isPassword=" + this.isPassword + ", offsetMapping=" + this.offsetMapping + ", manager=" + this.manager + ", imeOptions=" + this.imeOptions + ", focusRequester=" + this.focusRequester + ')';
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0052, code lost:
    
        if (androidx.compose.ui.text.TextRange.m747getCollapsedimpl(r2.selection) != false) goto L22;
     */
    /* JADX WARN: Type inference failed for: r10v2, types: [androidx.compose.foundation.text.input.internal.CoreTextFieldSemanticsModifierNode$updateNodeSemantics$1, kotlin.jvm.internal.Lambda] */
    @Override // androidx.compose.ui.node.ModifierNodeElement
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void update(androidx.compose.ui.Modifier.Node r11) {
        /*
            r10 = this;
            androidx.compose.foundation.text.input.internal.CoreTextFieldSemanticsModifierNode r11 = (androidx.compose.foundation.text.input.internal.CoreTextFieldSemanticsModifierNode) r11
            boolean r0 = r11.enabled
            r1 = 0
            r2 = 1
            if (r0 == 0) goto Le
            boolean r3 = r11.readOnly
            if (r3 != 0) goto Le
            r3 = r2
            goto Lf
        Le:
            r3 = r1
        Lf:
            androidx.compose.ui.text.input.ImeOptions r4 = r11.imeOptions
            androidx.compose.foundation.text.selection.TextFieldSelectionManager r5 = r11.manager
            boolean r6 = r10.readOnly
            boolean r7 = r10.enabled
            if (r7 == 0) goto L1c
            if (r6 != 0) goto L1c
            r1 = r2
        L1c:
            androidx.compose.ui.text.input.TransformedText r2 = r10.transformedText
            r11.transformedText = r2
            androidx.compose.ui.text.input.TextFieldValue r2 = r10.value
            r11.value = r2
            androidx.compose.foundation.text.LegacyTextFieldState r8 = r10.state
            r11.state = r8
            r11.readOnly = r6
            r11.enabled = r7
            androidx.compose.ui.text.input.OffsetMapping r6 = r10.offsetMapping
            r11.offsetMapping = r6
            androidx.compose.foundation.text.selection.TextFieldSelectionManager r6 = r10.manager
            r11.manager = r6
            androidx.compose.ui.text.input.ImeOptions r8 = r10.imeOptions
            r11.imeOptions = r8
            androidx.compose.ui.focus.FocusRequester r9 = r10.focusRequester
            r11.focusRequester = r9
            if (r7 != r0) goto L54
            if (r1 != r3) goto L54
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r8, r4)
            if (r0 == 0) goto L54
            boolean r0 = r11.isPassword
            boolean r10 = r10.isPassword
            if (r10 != r0) goto L54
            long r0 = r2.selection
            boolean r10 = androidx.compose.ui.text.TextRange.m747getCollapsedimpl(r0)
            if (r10 != 0) goto L57
        L54:
            androidx.compose.ui.node.SemanticsModifierNodeKt.invalidateSemantics(r11)
        L57:
            boolean r10 = kotlin.jvm.internal.Intrinsics.areEqual(r6, r5)
            if (r10 != 0) goto L64
            androidx.compose.foundation.text.input.internal.CoreTextFieldSemanticsModifierNode$updateNodeSemantics$1 r10 = new androidx.compose.foundation.text.input.internal.CoreTextFieldSemanticsModifierNode$updateNodeSemantics$1
            r10.<init>()
            r6.requestAutofillAction = r10
        L64:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.input.internal.CoreTextFieldSemanticsModifier.update(androidx.compose.ui.Modifier$Node):void");
    }
}
