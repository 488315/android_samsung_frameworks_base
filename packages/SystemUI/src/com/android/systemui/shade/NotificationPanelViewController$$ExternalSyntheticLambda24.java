package com.android.systemui.shade;

import java.util.function.BiConsumer;

public final /* synthetic */ class NotificationPanelViewController$$ExternalSyntheticLambda24 implements BiConsumer {
    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        NotificationPanelView notificationPanelView = (NotificationPanelView) obj;
        int floatValue = (int) ((Float) obj2).floatValue();
        notificationPanelView.mCurrentPanelAlpha = floatValue;
        notificationPanelView.mAlphaPaint.setARGB(floatValue, 255, 255, 255);
        notificationPanelView.invalidate();
    }
}
