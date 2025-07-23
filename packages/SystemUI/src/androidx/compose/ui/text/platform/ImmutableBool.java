package androidx.compose.ui.text.platform;

import androidx.compose.runtime.State;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
