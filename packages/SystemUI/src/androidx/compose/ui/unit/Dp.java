package androidx.compose.ui.unit;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class Dp implements Comparable<Dp> {
    public static final Companion Companion = new Companion(null);
    public static final float Infinity = Float.POSITIVE_INFINITY;
    public static final float Unspecified = Float.NaN;
    public final float value;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    private /* synthetic */ Dp(float f) {
        this.value = f;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ Dp m835boximpl(float f) {
        return new Dp(f);
    }

    /* renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m836equalsimpl0(float f, float f2) {
        return Float.compare(f, f2) == 0;
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m837toStringimpl(float f) {
        if (Float.isNaN(f)) {
            return "Dp.Unspecified";
        }
        return f + ".dp";
    }

    @Override // java.lang.Comparable
    public final int compareTo(Dp dp) {
        return Float.compare(this.value, dp.value);
    }

    public final boolean equals(Object obj) {
        return (obj instanceof Dp) && Float.compare(this.value, ((Dp) obj).value) == 0;
    }

    public final int hashCode() {
        return Float.hashCode(this.value);
    }

    public final String toString() {
        return m837toStringimpl(this.value);
    }
}
