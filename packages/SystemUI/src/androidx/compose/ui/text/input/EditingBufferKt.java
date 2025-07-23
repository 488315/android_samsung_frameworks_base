package androidx.compose.ui.text.input;

import androidx.compose.ui.text.TextRange;
import androidx.compose.ui.text.TextRangeKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class EditingBufferKt {
    /* renamed from: updateRangeAfterDelete-pWDy79M, reason: not valid java name */
    public static final long m771updateRangeAfterDeletepWDy79M(long j, long j2) {
        int m748getLengthimpl;
        int m750getMinimpl = TextRange.m750getMinimpl(j);
        int m749getMaximpl = TextRange.m749getMaximpl(j);
        if ((TextRange.m750getMinimpl(j2) < TextRange.m749getMaximpl(j)) && (TextRange.m750getMinimpl(j) < TextRange.m749getMaximpl(j2))) {
            if ((TextRange.m750getMinimpl(j2) <= TextRange.m750getMinimpl(j)) && (TextRange.m749getMaximpl(j) <= TextRange.m749getMaximpl(j2))) {
                m750getMinimpl = TextRange.m750getMinimpl(j2);
                m749getMaximpl = m750getMinimpl;
            } else {
                if ((TextRange.m750getMinimpl(j) <= TextRange.m750getMinimpl(j2)) && (TextRange.m749getMaximpl(j2) <= TextRange.m749getMaximpl(j))) {
                    m748getLengthimpl = TextRange.m748getLengthimpl(j2);
                } else {
                    int m750getMinimpl2 = TextRange.m750getMinimpl(j2);
                    if (m750getMinimpl >= TextRange.m749getMaximpl(j2) || m750getMinimpl2 > m750getMinimpl) {
                        m749getMaximpl = TextRange.m750getMinimpl(j2);
                    } else {
                        m750getMinimpl = TextRange.m750getMinimpl(j2);
                        m748getLengthimpl = TextRange.m748getLengthimpl(j2);
                    }
                }
                m749getMaximpl -= m748getLengthimpl;
            }
        } else if (m749getMaximpl > TextRange.m750getMinimpl(j2)) {
            m750getMinimpl -= TextRange.m748getLengthimpl(j2);
            m748getLengthimpl = TextRange.m748getLengthimpl(j2);
            m749getMaximpl -= m748getLengthimpl;
        }
        return TextRangeKt.TextRange(m750getMinimpl, m749getMaximpl);
    }
}
