package androidx.compose.ui.modifier;

import androidx.compose.ui.internal.InlineClassHelperKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class BackwardsCompatLocalMap extends ModifierLocalMap {
    public ModifierLocalProvider element;

    public BackwardsCompatLocalMap(ModifierLocalProvider<?> modifierLocalProvider) {
        super(null);
        this.element = modifierLocalProvider;
    }

    @Override // androidx.compose.ui.modifier.ModifierLocalMap
    public final boolean contains$ui_release(ModifierLocal modifierLocal) {
        return modifierLocal == this.element.getKey();
    }

    @Override // androidx.compose.ui.modifier.ModifierLocalMap
    public final Object get$ui_release(ProvidableModifierLocal providableModifierLocal) {
        if (providableModifierLocal != this.element.getKey()) {
            InlineClassHelperKt.throwIllegalStateException("Check failed.");
        }
        return this.element.getValue$1();
    }
}
