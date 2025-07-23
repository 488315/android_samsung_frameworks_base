package androidx.compose.ui.modifier;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class EmptyMap extends ModifierLocalMap {
    public static final EmptyMap INSTANCE = new EmptyMap();

    private EmptyMap() {
        super(null);
    }

    @Override // androidx.compose.ui.modifier.ModifierLocalMap
    public final boolean contains$ui_release(ModifierLocal modifierLocal) {
        return false;
    }

    @Override // androidx.compose.ui.modifier.ModifierLocalMap
    public final Object get$ui_release(ProvidableModifierLocal providableModifierLocal) {
        throw new IllegalStateException("");
    }
}
