package androidx.compose.ui.graphics;

import com.samsung.android.sdk.scs.ai.visual.c2pa.C2paManifestList;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PaintingStyle {
    public static final Companion Companion = new Companion(null);
    public static final int Stroke = 1;
    public final int value;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final boolean equals(Object obj) {
        if (obj instanceof PaintingStyle) {
            return this.value == ((PaintingStyle) obj).value;
        }
        return false;
    }

    public final int hashCode() {
        return Integer.hashCode(this.value);
    }

    public final String toString() {
        int i = this.value;
        return i == 0 ? "Fill" : i == Stroke ? "Stroke" : C2paManifestList.UNKNOWN_VALUE;
    }
}
