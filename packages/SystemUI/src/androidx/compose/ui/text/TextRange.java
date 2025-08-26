package androidx.compose.ui.text;

import androidx.activity.BackEventCompat$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class TextRange {
    public static final Companion Companion = new Companion(null);
    public static final long Zero = TextRangeKt.TextRange(0, 0);
    public final long packedValue;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    private /* synthetic */ TextRange(long j) {
        this.packedValue = j;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ TextRange m747boximpl(long j) {
        return new TextRange(j);
    }

    /* renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m748equalsimpl0(long j, long j2) {
        return j == j2;
    }

    /* renamed from: getCollapsed-impl, reason: not valid java name */
    public static final boolean m749getCollapsedimpl(long j) {
        return ((int) (j >> 32)) == ((int) (j & 4294967295L));
    }

    /* renamed from: getLength-impl, reason: not valid java name */
    public static final int m750getLengthimpl(long j) {
        return m751getMaximpl(j) - m752getMinimpl(j);
    }

    /* renamed from: getMax-impl, reason: not valid java name */
    public static final int m751getMaximpl(long j) {
        return Math.max((int) (j >> 32), (int) (j & 4294967295L));
    }

    /* renamed from: getMin-impl, reason: not valid java name */
    public static final int m752getMinimpl(long j) {
        return Math.min((int) (j >> 32), (int) (j & 4294967295L));
    }

    /* renamed from: getReversed-impl, reason: not valid java name */
    public static final boolean m753getReversedimpl(long j) {
        return ((int) (j >> 32)) > ((int) (j & 4294967295L));
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m754toStringimpl(long j) {
        StringBuilder sb = new StringBuilder("TextRange(");
        sb.append((int) (j >> 32));
        sb.append(", ");
        return BackEventCompat$$ExternalSyntheticOutline0.m(sb, (int) (j & 4294967295L), ')');
    }

    public final boolean equals(Object obj) {
        if (obj instanceof TextRange) {
            return this.packedValue == ((TextRange) obj).packedValue;
        }
        return false;
    }

    public final int hashCode() {
        return Long.hashCode(this.packedValue);
    }

    public final String toString() {
        return m754toStringimpl(this.packedValue);
    }
}
