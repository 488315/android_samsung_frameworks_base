package androidx.compose.ui.unit;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class TextUnitType {
    public final long type;
    public static final Companion Companion = new Companion(null);
    public static final long Sp = 4294967296L;
    public static final long Em = 8589934592L;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    private /* synthetic */ TextUnitType(long j) {
        this.type = j;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ TextUnitType m873boximpl(long j) {
        return new TextUnitType(j);
    }

    /* renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m874equalsimpl0(long j, long j2) {
        return j == j2;
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m875toStringimpl(long j) {
        return m874equalsimpl0(j, 0L) ? "Unspecified" : m874equalsimpl0(j, Sp) ? "Sp" : m874equalsimpl0(j, Em) ? "Em" : "Invalid";
    }

    public final boolean equals(Object obj) {
        if (obj instanceof TextUnitType) {
            return this.type == ((TextUnitType) obj).type;
        }
        return false;
    }

    public final int hashCode() {
        return Long.hashCode(this.type);
    }

    public final String toString() {
        return m875toStringimpl(this.type);
    }
}
