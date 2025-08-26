package androidx.compose.ui.unit;

import androidx.compose.ui.unit.TextUnitType;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class TextUnit {
    public static final Companion Companion = new Companion(null);
    public static final TextUnitType[] TextUnitTypes;
    public static final long Unspecified;
    public final long packedValue;

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
        TextUnitType textUnitTypeM875boximpl = TextUnitType.m875boximpl(0L);
        companion.getClass();
        TextUnitType textUnitTypeM875boximpl2 = TextUnitType.m875boximpl(TextUnitType.Sp);
        companion.getClass();
        TextUnitTypes = new TextUnitType[]{textUnitTypeM875boximpl, textUnitTypeM875boximpl2, TextUnitType.m875boximpl(TextUnitType.Em)};
        Unspecified = TextUnitKt.pack(Float.NaN, 0L);
    }

    private /* synthetic */ TextUnit(long j) {
        this.packedValue = j;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ TextUnit m867boximpl(long j) {
        return new TextUnit(j);
    }

    /* renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m868equalsimpl0(long j, long j2) {
        return j == j2;
    }

    /* renamed from: getType-UIouoOA, reason: not valid java name */
    public static final long m869getTypeUIouoOA(long j) {
        return TextUnitTypes[(int) ((j & 1095216660480L) >>> 32)].type;
    }

    /* renamed from: getValue-impl, reason: not valid java name */
    public static final float m870getValueimpl(long j) {
        return Float.intBitsToFloat((int) (j & 4294967295L));
    }

    /* renamed from: isEm-impl, reason: not valid java name */
    public static final boolean m871isEmimpl(long j) {
        return (j & 1095216660480L) == 8589934592L;
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m872toStringimpl(long j) {
        long jM869getTypeUIouoOA = m869getTypeUIouoOA(j);
        TextUnitType.Companion companion = TextUnitType.Companion;
        companion.getClass();
        if (TextUnitType.m876equalsimpl0(jM869getTypeUIouoOA, 0L)) {
            return "Unspecified";
        }
        companion.getClass();
        if (TextUnitType.m876equalsimpl0(jM869getTypeUIouoOA, TextUnitType.Sp)) {
            return m870getValueimpl(j) + ".sp";
        }
        companion.getClass();
        if (!TextUnitType.m876equalsimpl0(jM869getTypeUIouoOA, TextUnitType.Em)) {
            return "Invalid";
        }
        return m870getValueimpl(j) + ".em";
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
        return m872toStringimpl(this.packedValue);
    }
}
