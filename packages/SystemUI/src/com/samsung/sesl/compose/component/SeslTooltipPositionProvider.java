package com.samsung.sesl.compose.component;

import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntRect;
import androidx.compose.ui.unit.IntSize;
import androidx.compose.ui.unit.LayoutDirection;
import androidx.compose.ui.window.PopupPositionProvider;
import androidx.core.graphics.Insets;
import kotlin.jvm.functions.Function4;
import kotlin.ranges.RangesKt___RangesKt;

/* loaded from: classes4.dex */
public final class SeslTooltipPositionProvider implements PopupPositionProvider {
    public final Function4 delta;
    public final int offset;
    public final Insets popupInsets;

    public SeslTooltipPositionProvider(int i, Insets insets, Function4 function4) {
        this.offset = i;
        this.popupInsets = insets;
        this.delta = function4;
    }

    @Override // androidx.compose.ui.window.PopupPositionProvider
    /* renamed from: calculatePosition-llwVHH4 */
    public final long mo49calculatePositionllwVHH4(IntRect intRect, long j, LayoutDirection layoutDirection, long j2) {
        Insets insets = this.popupInsets;
        float f = insets.left;
        float f2 = insets.top;
        IntSize.Companion companion = IntSize.Companion;
        int i = (int) (j & 4294967295L);
        Rect rect = new Rect(f, f2, ((int) (j >> 32)) - insets.right, i - insets.bottom);
        int i2 = (int) (j2 & 4294967295L);
        int i3 = intRect.bottom + i2;
        int i4 = this.offset;
        boolean z = i3 + i4 <= i;
        long j3 = ((IntOffset) this.delta.invoke(intRect, IntSize.m861boximpl(j), layoutDirection, IntSize.m861boximpl(j2))).packedValue;
        int i5 = (int) (j3 & 4294967295L);
        int i6 = (int) (j2 >> 32);
        int iM858getCenternOccac = (((int) (intRect.m858getCenternOccac() >> 32)) - (i6 / 2)) + ((int) (j3 >> 32));
        int i7 = i5 + (z ? intRect.bottom + i4 : (intRect.top - i2) - i4);
        long jCoerceIn = (RangesKt___RangesKt.coerceIn(i7, (int) rect.top, (int) (rect.bottom - i2)) & 4294967295L) | (RangesKt___RangesKt.coerceIn(iM858getCenternOccac, (int) rect.left, (int) (rect.right - i6)) << 32);
        IntOffset.Companion companion2 = IntOffset.Companion;
        return jCoerceIn;
    }
}
