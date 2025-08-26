package androidx.compose.ui.graphics;

import com.samsung.android.sdk.scs.ai.visual.c2pa.C2paManifestList;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class ImageBitmapConfig {
    public final int value;
    public static final Companion Companion = new Companion(null);
    public static final int Alpha8 = 1;
    public static final int Rgb565 = 2;
    public static final int F16 = 3;
    public static final int Gpu = 4;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    private /* synthetic */ ImageBitmapConfig(int i) {
        this.value = i;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ ImageBitmapConfig m480boximpl(int i) {
        return new ImageBitmapConfig(i);
    }

    public final boolean equals(Object obj) {
        if (obj instanceof ImageBitmapConfig) {
            return this.value == ((ImageBitmapConfig) obj).value;
        }
        return false;
    }

    public final int hashCode() {
        return Integer.hashCode(this.value);
    }

    public final String toString() {
        int i = this.value;
        return i == 0 ? "Argb8888" : i == Alpha8 ? "Alpha8" : i == Rgb565 ? "Rgb565" : i == F16 ? "F16" : i == Gpu ? "Gpu" : C2paManifestList.UNKNOWN_VALUE;
    }
}
