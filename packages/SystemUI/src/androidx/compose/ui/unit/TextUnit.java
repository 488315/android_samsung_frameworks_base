package androidx.compose.ui.unit;

import androidx.compose.ui.unit.TextUnitType;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class TextUnit {
    public static final Companion Companion = new Companion(null);
    public static final TextUnitType[] TextUnitTypes;
    public static final long Unspecified;
    public final long packedValue;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        TextUnitType.Companion companion = TextUnitType.Companion;
        companion.getClass();
        TextUnitType m873boximpl = TextUnitType.m873boximpl(0L);
        companion.getClass();
        TextUnitType m873boximpl2 = TextUnitType.m873boximpl(TextUnitType.Sp);
        companion.getClass();
        TextUnitTypes = new TextUnitType[]{m873boximpl, m873boximpl2, TextUnitType.m873boximpl(TextUnitType.Em)};
        Unspecified = TextUnitKt.pack(Float.NaN, 0L);
    }

    private /* synthetic */ TextUnit(long j) {
        this.packedValue = j;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ TextUnit m865boximpl(long j) {
        return new TextUnit(j);
    }

    /* renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m866equalsimpl0(long j, long j2) {
        return j == j2;
    }

    /* renamed from: getType-UIouoOA, reason: not valid java name */
    public static final long m867getTypeUIouoOA(long j) {
        return TextUnitTypes[(int) ((j & 1095216660480L) >>> 32)].type;
    }

    /* renamed from: getValue-impl, reason: not valid java name */
    public static final float m868getValueimpl(long j) {
        return Float.intBitsToFloat((int) (j & 4294967295L));
    }

    /* renamed from: isEm-impl, reason: not valid java name */
    public static final boolean m869isEmimpl(long j) {
        return (j & 1095216660480L) == 8589934592L;
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m870toStringimpl(long j) {
        long m867getTypeUIouoOA = m867getTypeUIouoOA(j);
        TextUnitType.Companion companion = TextUnitType.Companion;
        companion.getClass();
        if (TextUnitType.m874equalsimpl0(m867getTypeUIouoOA, 0L)) {
            return "Unspecified";
        }
        companion.getClass();
        if (TextUnitType.m874equalsimpl0(m867getTypeUIouoOA, TextUnitType.Sp)) {
            return m868getValueimpl(j) + ".sp";
        }
        companion.getClass();
        if (!TextUnitType.m874equalsimpl0(m867getTypeUIouoOA, TextUnitType.Em)) {
            return "Invalid";
        }
        return m868getValueimpl(j) + ".em";
    }

    public final boolean equals(Object obj) {
        if (obj instanceof TextUnit) {
            return this.packedValue == ((TextUnit) obj).packedValue;
        }
        return false;
    }

    public final int hashCode() {
        return Long.hashCode(this.packedValue);
    }

    public final String toString() {
        return m870toStringimpl(this.packedValue);
    }
}
