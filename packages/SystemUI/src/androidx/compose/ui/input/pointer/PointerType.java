package androidx.compose.ui.input.pointer;

import com.samsung.android.sdk.scs.ai.visual.c2pa.C2paManifestList;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PointerType {
    public final int value;
    public static final Companion Companion = new Companion(null);
    public static final int Touch = 1;
    public static final int Mouse = 2;
    public static final int Stylus = 3;
    public static final int Eraser = 4;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    private /* synthetic */ PointerType(int i) {
        this.value = i;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ PointerType m597boximpl(int i) {
        return new PointerType(i);
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m598toStringimpl(int i) {
        return i != 1 ? i != 2 ? i != 3 ? i != 4 ? C2paManifestList.UNKNOWN_VALUE : "Eraser" : "Stylus" : "Mouse" : "Touch";
    }

    public final boolean equals(Object obj) {
        if (obj instanceof PointerType) {
            return this.value == ((PointerType) obj).value;
        }
        return false;
    }

    public final int hashCode() {
        return Integer.hashCode(this.value);
    }

    public final String toString() {
        return m598toStringimpl(this.value);
    }
}
