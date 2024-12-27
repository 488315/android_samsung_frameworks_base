package com.android.systemui.shade;

import java.util.function.BiConsumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
