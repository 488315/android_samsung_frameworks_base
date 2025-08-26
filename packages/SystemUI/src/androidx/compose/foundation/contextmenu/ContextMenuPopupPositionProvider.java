package androidx.compose.foundation.contextmenu;

import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntRect;
import androidx.compose.ui.unit.IntRectKt;
import androidx.compose.ui.unit.LayoutDirection;
import androidx.compose.ui.window.PopupPositionProvider;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class ContextMenuPopupPositionProvider implements PopupPositionProvider {
    public final long anchorPosition;
    public final Function2 onPositionCalculated;

    public /* synthetic */ ContextMenuPopupPositionProvider(long j, Function2 function2, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, function2);
    }

    @Override // androidx.compose.ui.window.PopupPositionProvider
    /* renamed from: calculatePosition-llwVHH4, reason: not valid java name */
    public final long mo49calculatePositionllwVHH4(IntRect intRect, long j, LayoutDirection layoutDirection, long j2) {
        int i = intRect.left;
        IntOffset.Companion companion = IntOffset.Companion;
        long j3 = this.anchorPosition;
        long jAlignPopupAxis = (ContextMenuPopupPositionProviderKt.alignPopupAxis(intRect.top + ((int) (j3 & 4294967295L)), (int) (j2 & 4294967295L), (int) (j & 4294967295L), true) & 4294967295L) | (ContextMenuPopupPositionProviderKt.alignPopupAxis(i + ((int) (j3 >> 32)), (int) (j2 >> 32), (int) (j >> 32), layoutDirection == LayoutDirection.Ltr) << 32);
        Function2 function2 = this.onPositionCalculated;
        if (function2 != null) {
            function2.invoke(IntOffset.m849boximpl(j3), IntRectKt.m860IntRectVbeCjmY(jAlignPopupAxis, j2));
        }
        return jAlignPopupAxis;
    }

    private ContextMenuPopupPositionProvider(long j, Function2 function2) {
        this.anchorPosition = j;
        this.onPositionCalculated = function2;
    }

    public /* synthetic */ ContextMenuPopupPositionProvider(long j, Function2 function2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, (i & 2) != 0 ? null : function2, null);
    }
}
