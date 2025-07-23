package androidx.compose.foundation.lazy.layout;

import androidx.compose.runtime.MutableState;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ObservableScopeInvalidator {
    public final MutableState state;

    /* renamed from: invalidateScope-impl, reason: not valid java name */
    public static final void m173invalidateScopeimpl(MutableState mutableState) {
        mutableState.setValue(Unit.INSTANCE);
    }

    public final boolean equals(Object obj) {
        if (obj instanceof ObservableScopeInvalidator) {
            return Intrinsics.areEqual(this.state, ((ObservableScopeInvalidator) obj).state);
        }
        return false;
    }

    public final int hashCode() {
        return this.state.hashCode();
    }

    public final String toString() {
        return "ObservableScopeInvalidator(state=" + this.state + ')';
    }
}
