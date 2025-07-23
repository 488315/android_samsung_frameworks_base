package com.android.systemui.common.ui.compose;

import androidx.compose.foundation.pager.PagerState;
import androidx.compose.ui.geometry.CornerRadius;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import com.samsung.android.knox.custom.IKnoxCustomManager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class PagerDotsKt {
    /* JADX WARN: Code restructure failed: missing block: B:58:0x00ca, code lost:
    
        if (r7 == androidx.compose.runtime.Composer.Companion.Empty) goto L57;
     */
    /* renamed from: PagerDots-LLiWm1Q, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void m1073PagerDotsLLiWm1Q(final androidx.compose.foundation.pager.PagerState r20, final long r21, final long r23, final androidx.compose.ui.Modifier r25, float r26, float r27, androidx.compose.runtime.Composer r28, final int r29) {
        /*
            Method dump skipped, instructions count: 704
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.common.ui.compose.PagerDotsKt.m1073PagerDotsLLiWm1Q(androidx.compose.foundation.pager.PagerState, long, long, androidx.compose.ui.Modifier, float, float, androidx.compose.runtime.Composer, int):void");
    }

    public static final void PagerDots_LLiWm1Q$drawDoubleRect(DrawScope drawScope, float f, float f2, float f3, long j, boolean z, float f4) {
        float f5;
        if (z) {
            f5 = drawScope.mo57toPx0680j_4(f) - drawScope.mo57toPx0680j_4(f4);
        } else {
            f5 = -(drawScope.mo57toPx0680j_4(f2) + drawScope.mo57toPx0680j_4(f));
        }
        long floatToRawIntBits = (Float.floatToRawIntBits(f5) << 32) | (Float.floatToRawIntBits(0.0f) & 4294967295L);
        Offset.Companion companion = Offset.Companion;
        float mo57toPx0680j_4 = drawScope.mo57toPx0680j_4(f4);
        float mo57toPx0680j_42 = drawScope.mo57toPx0680j_4(f);
        long floatToRawIntBits2 = (Float.floatToRawIntBits(mo57toPx0680j_42) & 4294967295L) | (Float.floatToRawIntBits(mo57toPx0680j_4) << 32);
        Size.Companion companion2 = Size.Companion;
        long floatToRawIntBits3 = (4294967295L & Float.floatToRawIntBits(r5)) | (Float.floatToRawIntBits(drawScope.mo57toPx0680j_4(f3)) << 32);
        CornerRadius.Companion companion3 = CornerRadius.Companion;
        DrawScope.m541drawRoundRectuAw5IA$default(drawScope, j, floatToRawIntBits, floatToRawIntBits2, floatToRawIntBits3, null, 0.0f, IKnoxCustomManager.Stub.TRANSACTION_getFavoriteApp);
    }

    public static final boolean isOverscrolling(PagerState pagerState) {
        float currentPageOffsetFraction = pagerState.getCurrentPageOffsetFraction() + pagerState.getCurrentPage();
        return currentPageOffsetFraction < 0.0f || currentPageOffsetFraction > ((float) (pagerState.getPageCount() - 1));
    }
}
