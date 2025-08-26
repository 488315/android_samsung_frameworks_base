package androidx.compose.ui.input.pointer;

import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class AndroidPointerIcon implements PointerIcon {
    public final android.view.PointerIcon pointerIcon;

    public AndroidPointerIcon(android.view.PointerIcon pointerIcon) {
        this.pointerIcon = pointerIcon;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (AndroidPointerIcon.class.equals(obj != null ? obj.getClass() : null)) {
            return Intrinsics.areEqual(this.pointerIcon, ((AndroidPointerIcon) obj).pointerIcon);
        }
        return false;
    }

    public final int hashCode() {
        return this.pointerIcon.hashCode();
    }

    public final String toString() {
        return "AndroidPointerIcon(pointerIcon=" + this.pointerIcon + ')';
    }
}
