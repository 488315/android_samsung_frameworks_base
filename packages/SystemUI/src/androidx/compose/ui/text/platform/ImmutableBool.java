package androidx.compose.ui.text.platform;

import androidx.compose.runtime.State;

/* loaded from: classes.dex */
final class ImmutableBool implements State<Boolean> {
    public final boolean value;

    public ImmutableBool(boolean z) {
        this.value = z;
    }

    @Override // androidx.compose.runtime.State
    public final Object getValue() {
        return Boolean.valueOf(this.value);
    }
}
