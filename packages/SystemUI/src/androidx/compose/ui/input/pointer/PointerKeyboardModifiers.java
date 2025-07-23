package androidx.compose.ui.input.pointer;

import androidx.activity.BackEventCompat$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PointerKeyboardModifiers {
    public final int packedValue;

    private /* synthetic */ PointerKeyboardModifiers(int i) {
        this.packedValue = i;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ PointerKeyboardModifiers m596boximpl(int i) {
        return new PointerKeyboardModifiers(i);
    }

    public final boolean equals(Object obj) {
        if (obj instanceof PointerKeyboardModifiers) {
            return this.packedValue == ((PointerKeyboardModifiers) obj).packedValue;
        }
        return false;
    }

    public final int hashCode() {
        return Integer.hashCode(this.packedValue);
    }

    public final String toString() {
        return BackEventCompat$$ExternalSyntheticOutline0.m(new StringBuilder("PointerKeyboardModifiers(packedValue="), this.packedValue, ')');
    }
}
