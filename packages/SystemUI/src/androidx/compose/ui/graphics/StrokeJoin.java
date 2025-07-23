package androidx.compose.ui.graphics;

import com.samsung.android.sdk.scs.ai.visual.c2pa.C2paManifestList;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class StrokeJoin {
    public final int value;
    public static final Companion Companion = new Companion(null);
    public static final int Round = 1;
    public static final int Bevel = 2;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    private /* synthetic */ StrokeJoin(int i) {
        this.value = i;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ StrokeJoin m499boximpl(int i) {
        return new StrokeJoin(i);
    }

    public final boolean equals(Object obj) {
        if (obj instanceof StrokeJoin) {
            return this.value == ((StrokeJoin) obj).value;
        }
        return false;
    }

    public final int hashCode() {
        return Integer.hashCode(this.value);
    }

    public final String toString() {
        int i = this.value;
        return i == 0 ? "Miter" : i == Round ? "Round" : i == Bevel ? "Bevel" : C2paManifestList.UNKNOWN_VALUE;
    }
}
