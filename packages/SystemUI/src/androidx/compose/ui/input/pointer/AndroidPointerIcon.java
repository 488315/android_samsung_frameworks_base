package androidx.compose.ui.input.pointer;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
