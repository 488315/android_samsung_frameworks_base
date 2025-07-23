package androidx.compose.ui.window;

import android.graphics.Rect;
import kotlin.collections.CollectionsKt__CollectionsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class PopupLayoutHelperImpl29 extends PopupLayoutHelperImpl {
    @Override // androidx.compose.ui.window.PopupLayoutHelperImpl, androidx.compose.ui.window.PopupLayoutHelper
    public final void setGestureExclusionRects(PopupLayout popupLayout, int i, int i2) {
        popupLayout.setSystemGestureExclusionRects(CollectionsKt__CollectionsKt.mutableListOf(new Rect(0, 0, i, i2)));
    }
}
