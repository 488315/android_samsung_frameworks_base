package com.android.systemui.screenshot;

import android.app.Notification;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import com.android.systemui.screenshot.ScreenshotController;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface ScreenshotViewProxy {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Factory {
        ScreenshotViewProxy getProxy(int i, Context context);
    }

    void addQuickShareChip(Notification.Action action);

    void announceForAccessibility(String str);

    void fadeForSharedTransition();

    View getScreenshotPreview();

    ViewGroup getView();

    boolean isAttachedToWindow();

    boolean isDismissing();

    void requestDismissal(ScreenshotEvent screenshotEvent);

    void reset();

    void setCallbacks(ScreenshotController.AnonymousClass3 anonymousClass3);

    void setChipIntents(ScreenshotController.SavedImageData savedImageData);

    void setPackageName(String str);

    void stopInputListening();

    void updateOrientation(WindowInsets windowInsets);
}
