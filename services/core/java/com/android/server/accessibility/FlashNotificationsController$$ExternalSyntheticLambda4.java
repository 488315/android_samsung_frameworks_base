package com.android.server.accessibility;

import android.util.Log;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.android.internal.util.FrameworkStatsLog;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

import java.util.function.BiConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class FlashNotificationsController$$ExternalSyntheticLambda4
        implements BiConsumer {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        FlashNotificationsController flashNotificationsController =
                (FlashNotificationsController) obj;
        switch (this.$r8$classId) {
            case 0:
                flashNotificationsController.startFlashNotification(
                        (FlashNotificationsController.FlashNotification) obj2);
                break;
            default:
                int intValue = ((Integer) obj2).intValue();
                flashNotificationsController.getClass();
                Log.d("FlashNotifController", "showScreenNotificationOverlayViewMainThread");
                WindowManager.LayoutParams layoutParams =
                        new WindowManager.LayoutParams(
                                -1,
                                -1,
                                2015,
                                FrameworkStatsLog
                                        .APP_BACKGROUND_RESTRICTIONS_INFO__EXEMPTION_REASON__REASON_LOCATION_PROVIDER,
                                -3);
                layoutParams.privateFlags |= 16;
                layoutParams.layoutInDisplayCutoutMode = 1;
                layoutParams.inputFeatures |= 1;
                if (flashNotificationsController.mScreenFlashNotificationOverlayView == null) {
                    FrameLayout frameLayout =
                            new FrameLayout(flashNotificationsController.mContext);
                    frameLayout.setBackgroundColor(intValue);
                    frameLayout.setAlpha(FullScreenMagnificationGestureHandler.MAX_SCALE);
                    flashNotificationsController.mScreenFlashNotificationOverlayView = frameLayout;
                    ((WindowManager)
                                    flashNotificationsController.mContext.getSystemService(
                                            WindowManager.class))
                            .addView(
                                    flashNotificationsController
                                            .mScreenFlashNotificationOverlayView,
                                    layoutParams);
                    FlashNotificationsController.fadeScreenNotificationOverlayViewMainThread(
                            flashNotificationsController.mScreenFlashNotificationOverlayView, true);
                }
                WindowManager coverDisplayWindowManager =
                        flashNotificationsController.getCoverDisplayWindowManager();
                if (coverDisplayWindowManager != null
                        && flashNotificationsController.mCoverScreenNotificationOverlayView
                                == null) {
                    FrameLayout frameLayout2 =
                            new FrameLayout(flashNotificationsController.mContext);
                    frameLayout2.setBackgroundColor(intValue);
                    frameLayout2.setAlpha(FullScreenMagnificationGestureHandler.MAX_SCALE);
                    flashNotificationsController.mCoverScreenNotificationOverlayView = frameLayout2;
                    coverDisplayWindowManager.addView(frameLayout2, layoutParams);
                    FlashNotificationsController.fadeScreenNotificationOverlayViewMainThread(
                            flashNotificationsController.mCoverScreenNotificationOverlayView, true);
                    break;
                }
                break;
        }
    }
}
