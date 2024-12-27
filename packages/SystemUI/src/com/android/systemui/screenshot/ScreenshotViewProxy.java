package com.android.systemui.screenshot;

import android.app.Notification;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import com.android.systemui.screenshot.ScreenshotController;

public interface ScreenshotViewProxy {

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
