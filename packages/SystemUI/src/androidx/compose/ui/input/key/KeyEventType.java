package androidx.compose.ui.input.key;

import com.samsung.android.sdk.scs.ai.visual.c2pa.C2paManifestList;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class KeyEventType {
    public final int value;
    public static final Companion Companion = new Companion(null);
    public static final int KeyUp = 1;
    public static final int KeyDown = 2;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final boolean equals(Object obj) {
        if (obj instanceof KeyEventType) {
            return this.value == ((KeyEventType) obj).value;
        }
        return false;
    }

    public final int hashCode() {
        return Integer.hashCode(this.value);
    }

    public final String toString() {
        int i = KeyUp;
        int i2 = this.value;
        return i2 == i ? "KeyUp" : i2 == KeyDown ? "KeyDown" : i2 == 0 ? C2paManifestList.UNKNOWN_VALUE : "Invalid";
    }
}
