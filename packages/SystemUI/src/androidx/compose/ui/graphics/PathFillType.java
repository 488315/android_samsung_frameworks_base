package androidx.compose.ui.graphics;

import com.samsung.android.sdk.scs.ai.visual.c2pa.C2paManifestList;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class PathFillType {
    public static final Companion Companion = new Companion(null);
    public static final int EvenOdd = 1;
    public final int value;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    private /* synthetic */ PathFillType(int i) {
        this.value = i;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ PathFillType m494boximpl(int i) {
        return new PathFillType(i);
    }

    public final boolean equals(Object obj) {
        if (obj instanceof PathFillType) {
            return this.value == ((PathFillType) obj).value;
        }
        return false;
    }

    public final int hashCode() {
        return Integer.hashCode(this.value);
    }

    public final String toString() {
        int i = this.value;
        return i == 0 ? "NonZero" : i == EvenOdd ? "EvenOdd" : C2paManifestList.UNKNOWN_VALUE;
    }
}
