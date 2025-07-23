package androidx.compose.ui.input.pointer;

import com.samsung.android.sdk.scs.ai.visual.c2pa.C2paManifestList;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PointerEventType {
    public final int value;
    public static final Companion Companion = new Companion(null);
    public static final int Press = 1;
    public static final int Release = 2;
    public static final int Move = 3;
    public static final int Enter = 4;
    public static final int Exit = 5;
    public static final int Scroll = 6;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final boolean equals(Object obj) {
        if (obj instanceof PointerEventType) {
            return this.value == ((PointerEventType) obj).value;
        }
        return false;
    }

    public final int hashCode() {
        return Integer.hashCode(this.value);
    }

    public final String toString() {
        int i = Press;
        int i2 = this.value;
        return i2 == i ? "Press" : i2 == Release ? "Release" : i2 == Move ? "Move" : i2 == Enter ? "Enter" : i2 == Exit ? "Exit" : i2 == Scroll ? "Scroll" : C2paManifestList.UNKNOWN_VALUE;
    }
}
