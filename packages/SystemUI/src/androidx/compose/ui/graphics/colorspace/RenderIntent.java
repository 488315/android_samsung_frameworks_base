package androidx.compose.ui.graphics.colorspace;

import com.samsung.android.sdk.scs.ai.visual.c2pa.C2paManifestList;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class RenderIntent {
    public final int value;
    public static final Companion Companion = new Companion(null);
    public static final int Relative = 1;
    public static final int Saturation = 2;
    public static final int Absolute = 3;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final boolean equals(Object obj) {
        if (obj instanceof RenderIntent) {
            return this.value == ((RenderIntent) obj).value;
        }
        return false;
    }

    public final int hashCode() {
        return Integer.hashCode(this.value);
    }

    public final String toString() {
        int i = this.value;
        return i == 0 ? "Perceptual" : i == Relative ? "Relative" : i == Saturation ? "Saturation" : i == Absolute ? "Absolute" : C2paManifestList.UNKNOWN_VALUE;
    }
}
