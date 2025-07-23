package androidx.compose.ui.platform;

import android.content.Context;
import android.graphics.Canvas;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
