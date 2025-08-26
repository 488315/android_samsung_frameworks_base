package androidx.compose.ui.graphics;

import com.samsung.android.sdk.scs.ai.visual.c2pa.C2paManifestList;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class StrokeCap {
    public static final Companion Companion = new Companion(null);
    public static final int Round = 1;
    public static final int Square = 2;
    public final int value;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    private /* synthetic */ StrokeCap(int i) {
        this.value = i;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ StrokeCap m500boximpl(int i) {
        return new StrokeCap(i);
    }

    public final boolean equals(Object obj) {
        if (obj instanceof StrokeCap) {
            return this.value == ((StrokeCap) obj).value;
        }
        return false;
    }

    public final int hashCode() {
        return Integer.hashCode(this.value);
    }

    public final String toString() {
        int i = this.value;
        return i == 0 ? "Butt" : i == Round ? "Round" : i == Square ? "Square" : C2paManifestList.UNKNOWN_VALUE;
    }
}
