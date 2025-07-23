package androidx.compose.ui.modifier;

import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.ui.internal.InlineClassHelperKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SingleLocalMap extends ModifierLocalMap {
    public final ModifierLocal key;
    public final MutableState value$delegate;

    public SingleLocalMap(ModifierLocal<?> modifierLocal) {
        super(null);
        this.key = modifierLocal;
        this.value$delegate = SnapshotStateKt.mutableStateOf$default(null);
    }

    @Override // androidx.compose.ui.modifier.ModifierLocalMap
    public final boolean contains$ui_release(ModifierLocal modifierLocal) {
        return modifierLocal == this.key;
    }

    @Override // androidx.compose.ui.modifier.ModifierLocalMap
    public final Object get$ui_release(ProvidableModifierLocal providableModifierLocal) {
        if (providableModifierLocal != this.key) {
            InlineClassHelperKt.throwIllegalStateException("Check failed.");
        }
        Object value = ((SnapshotMutableStateImpl) this.value$delegate).getValue();
        if (value == null) {
            return null;
        }
        return value;
    }
}
