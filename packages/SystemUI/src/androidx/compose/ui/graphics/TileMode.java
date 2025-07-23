package androidx.compose.ui.graphics;

import com.samsung.android.sdk.scs.ai.visual.c2pa.C2paManifestList;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class TileMode {
    public final int value;
    public static final Companion Companion = new Companion(null);
    public static final int Repeated = 1;
    public static final int Mirror = 2;
    public static final int Decal = 3;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m500toStringimpl(int i) {
        return i == 0 ? "Clamp" : i == Repeated ? "Repeated" : i == Mirror ? "Mirror" : i == Decal ? "Decal" : C2paManifestList.UNKNOWN_VALUE;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof TileMode) {
            return this.value == ((TileMode) obj).value;
        }
        return false;
    }

    public final int hashCode() {
        return Integer.hashCode(this.value);
    }

    public final String toString() {
        return m500toStringimpl(this.value);
    }
}
