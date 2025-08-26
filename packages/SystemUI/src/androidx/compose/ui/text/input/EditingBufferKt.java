package androidx.compose.ui.text.input;

import androidx.compose.ui.text.TextRange;
import androidx.compose.ui.text.TextRangeKt;

/* loaded from: classes.dex */
public abstract class EditingBufferKt {
    /* renamed from: updateRangeAfterDelete-pWDy79M, reason: not valid java name */
    public static final long m773updateRangeAfterDeletepWDy79M(long j, long j2) {
        int iM750getLengthimpl;
        int iM752getMinimpl = TextRange.m752getMinimpl(j);
        int iM751getMaximpl = TextRange.m751getMaximpl(j);
        if ((TextRange.m752getMinimpl(j2) < TextRange.m751getMaximpl(j)) && (TextRange.m752getMinimpl(j) < TextRange.m751getMaximpl(j2))) {
            if ((TextRange.m752getMinimpl(j2) <= TextRange.m752getMinimpl(j)) && (TextRange.m751getMaximpl(j) <= TextRange.m751getMaximpl(j2))) {
                iM752getMinimpl = TextRange.m752getMinimpl(j2);
                iM751getMaximpl = iM752getMinimpl;
            } else {
                if ((TextRange.m752getMinimpl(j) <= TextRange.m752getMinimpl(j2)) && (TextRange.m751getMaximpl(j2) <= TextRange.m751getMaximpl(j))) {
                    iM750getLengthimpl = TextRange.m750getLengthimpl(j2);
                } else {
                    int iM752getMinimpl2 = TextRange.m752getMinimpl(j2);
                    if (iM752getMinimpl >= TextRange.m751getMaximpl(j2) || iM752getMinimpl2 > iM752getMinimpl) {
                        iM751getMaximpl = TextRange.m752getMinimpl(j2);
                    } else {
                        iM752getMinimpl = TextRange.m752getMinimpl(j2);
                        iM750getLengthimpl = TextRange.m750getLengthimpl(j2);
                    }
                }
                iM751getMaximpl -= iM750getLengthimpl;
            }
        } else if (iM751getMaximpl > TextRange.m752getMinimpl(j2)) {
            iM752getMinimpl -= TextRange.m750getLengthimpl(j2);
            iM750getLengthimpl = TextRange.m750getLengthimpl(j2);
            iM751getMaximpl -= iM750getLengthimpl;
        }
        return TextRangeKt.TextRange(iM752getMinimpl, iM751getMaximpl);
    }
}
