package androidx.compose.ui.unit;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class TextUnitType {
    public final long type;
    public static final Companion Companion = new Companion(null);
    public static final long Sp = 4294967296L;
    public static final long Em = 8589934592L;

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
    public static final /* synthetic */ TextUnitType m875boximpl(long j) {
        return new TextUnitType(j);
    }

    /* renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m876equalsimpl0(long j, long j2) {
        return j == j2;
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m877toStringimpl(long j) {
        return m876equalsimpl0(j, 0L) ? "Unspecified" : m876equalsimpl0(j, Sp) ? "Sp" : m876equalsimpl0(j, Em) ? "Em" : "Invalid";
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
        return m877toStringimpl(this.type);
    }
}
