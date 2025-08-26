package androidx.compose.ui.platform;

import android.content.Context;
import android.graphics.Canvas;

/* loaded from: classes.dex */
public final class ViewLayerContainer extends DrawChildContainer {
    public ViewLayerContainer(Context context) {
        super(context);
    }

    public final void dispatchGetDisplayList() {
    }

    @Override // androidx.compose.ui.platform.DrawChildContainer, android.view.ViewGroup, android.view.View
    public final void dispatchDraw(Canvas canvas) {
    }
}
