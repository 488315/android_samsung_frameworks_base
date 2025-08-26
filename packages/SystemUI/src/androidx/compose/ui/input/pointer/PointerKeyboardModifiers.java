package androidx.compose.ui.input.pointer;

import androidx.activity.BackEventCompat$$ExternalSyntheticOutline0;

/* loaded from: classes.dex */
public final class PointerKeyboardModifiers {
    public final int packedValue;

    private /* synthetic */ PointerKeyboardModifiers(int i) {
        this.packedValue = i;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ PointerKeyboardModifiers m598boximpl(int i) {
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
