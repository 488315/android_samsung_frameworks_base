package androidx.compose.ui.semantics;

import com.samsung.android.sdk.scs.ai.visual.c2pa.C2paManifestList;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class LiveRegionMode {
    public final int value;
    public static final Companion Companion = new Companion(null);
    public static final int Assertive = 1;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    private /* synthetic */ LiveRegionMode(int i) {
        this.value = i;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ LiveRegionMode m714boximpl(int i) {
        return new LiveRegionMode(i);
    }

    public final boolean equals(Object obj) {
        if (obj instanceof LiveRegionMode) {
            return this.value == ((LiveRegionMode) obj).value;
        }
        return false;
    }

    public final int hashCode() {
        return Integer.hashCode(this.value);
    }

    public final String toString() {
        int i = this.value;
        return i == 0 ? "Polite" : i == Assertive ? "Assertive" : C2paManifestList.UNKNOWN_VALUE;
    }
}
