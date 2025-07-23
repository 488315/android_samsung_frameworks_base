package androidx.compose.foundation.contextmenu;

import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntRect;
import androidx.compose.ui.unit.IntRectKt;
import androidx.compose.ui.unit.LayoutDirection;
import androidx.compose.ui.window.PopupPositionProvider;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ContextMenuPopupPositionProvider implements PopupPositionProvider {
    public final long anchorPosition;
    public final Function2 onPositionCalculated;

    public /* synthetic */ ContextMenuPopupPositionProvider(long j, Function2 function2, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, function2);
    }

    @Override // androidx.compose.ui.window.PopupPositionProvider
    /* renamed from: calculatePosition-llwVHH4, reason: not valid java name */
    public final long mo48calculatePositionllwVHH4(IntRect intRect, long j, LayoutDirection layoutDirection, long j2) {
        int i = intRect.left;
        IntOffset.Companion companion = IntOffset.Companion;
        long j3 = this.anchorPosition;
        long alignPopupAxis = (ContextMenuPopupPositionProviderKt.alignPopupAxis(intRect.top + ((int) (j3 & 4294967295L)), (int) (j2 & 4294967295L), (int) (j & 4294967295L), true) & 4294967295L) | (ContextMenuPopupPositionProviderKt.alignPopupAxis(i + ((int) (j3 >> 32)), (int) (j2 >> 32), (int) (j >> 32), layoutDirection == LayoutDirection.Ltr) << 32);
        Function2 function2 = this.onPositionCalculated;
        if (function2 != null) {
            function2.invoke(IntOffset.m847boximpl(j3), IntRectKt.m858IntRectVbeCjmY(alignPopupAxis, j2));
        }
        return alignPopupAxis;
    }

    private ContextMenuPopupPositionProvider(long j, Function2 function2) {
        this.anchorPosition = j;
        this.onPositionCalculated = function2;
    }

    public /* synthetic */ ContextMenuPopupPositionProvider(long j, Function2 function2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, (i & 2) != 0 ? null : function2, null);
    }
}
