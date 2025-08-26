package androidx.compose.ui.input.pointer;

import androidx.activity.BackEventCompat$$ExternalSyntheticOutline0;

/* loaded from: classes.dex */
public final class AndroidPointerIconType implements PointerIcon {
    public final int type;

    public AndroidPointerIconType(int i) {
        this.type = i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return AndroidPointerIconType.class.equals(obj != null ? obj.getClass() : null) && this.type == ((AndroidPointerIconType) obj).type;
    }

    public final int hashCode() {
        return this.type;
    }

    public final String toString() {
        return BackEventCompat$$ExternalSyntheticOutline0.m(new StringBuilder("AndroidPointerIcon(type="), this.type, ')');
    }
}
