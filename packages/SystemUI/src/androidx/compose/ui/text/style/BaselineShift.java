package androidx.compose.ui.text.style;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class BaselineShift {
    public static final Companion Companion = new Companion(null);
    public final float multiplier;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    private /* synthetic */ BaselineShift(float f) {
        this.multiplier = f;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ BaselineShift m791boximpl(float f) {
        return new BaselineShift(f);
    }

    public final boolean equals(Object obj) {
        if (obj instanceof BaselineShift) {
            return Float.compare(this.multiplier, ((BaselineShift) obj).multiplier) == 0;
        }
        return false;
    }

    public final int hashCode() {
        return Float.hashCode(this.multiplier);
    }

    public final String toString() {
        return "BaselineShift(multiplier=" + this.multiplier + ')';
    }
}
