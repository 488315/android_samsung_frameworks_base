package androidx.compose.ui.graphics;

import com.samsung.android.sdk.scs.ai.visual.c2pa.C2paManifestList;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class PathOperation {
    public final int value;
    public static final Companion Companion = new Companion(null);
    public static final int Intersect = 1;
    public static final int Union = 2;
    public static final int Xor = 3;
    public static final int ReverseDifference = 4;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final boolean equals(Object obj) {
        if (obj instanceof PathOperation) {
            return this.value == ((PathOperation) obj).value;
        }
        return false;
    }

    public final int hashCode() {
        return Integer.hashCode(this.value);
    }

    public final String toString() {
        int i = this.value;
        return i == 0 ? "Difference" : i == Intersect ? "Intersect" : i == Union ? "Union" : i == Xor ? "Xor" : i == ReverseDifference ? "ReverseDifference" : C2paManifestList.UNKNOWN_VALUE;
    }
}
