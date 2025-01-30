package com.android.systemui.screenshot;

import android.app.ActivityManager;
import android.content.Context;
import android.os.UserManager;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.screenshot.sep.SemImageCaptureImpl;
import com.android.systemui.settings.DisplayTracker;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ScreenshotController_Factory implements Provider {
    public final Provider actionExecutorProvider;
    public final Provider activityManagerProvider;
    public final Provider assistContentRequesterProvider;
    public final Provider broadcastSenderProvider;
    public final Provider contextProvider;
    public final Provider displayTrackerProvider;
    public final Provider flagsProvider;
    public final Provider imageCaptureProvider;
    public final Provider imageExporterProvider;
    public final Provider longScreenshotHolderProvider;
    public final Provider mainExecutorProvider;
    public final Provider messageContainerControllerProvider;
    public final Provider screenshotDetectionControllerProvider;
    public final Provider screenshotNotificationSmartActionsProvider;
    public final Provider screenshotNotificationsControllerProvider;
    public final Provider screenshotSmartActionsProvider;
    public final Provider scrollCaptureClientProvider;
    public final Provider scrollCaptureControllerProvider;
    public final Provider sepImageCaptureProvider;
    public final Provider timeoutHandlerProvider;
    public final Provider uiEventLoggerProvider;
    public final Provider userManagerProvider;

    public ScreenshotController_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16, Provider provider17, Provider provider18, Provider provider19, Provider provider20, Provider provider21, Provider provider22) {
        this.contextProvider = provider;
        this.flagsProvider = provider2;
        this.screenshotSmartActionsProvider = provider3;
        this.screenshotNotificationsControllerProvider = provider4;
        this.scrollCaptureClientProvider = provider5;
        this.uiEventLoggerProvider = provider6;
        this.imageExporterProvider = provider7;
        this.imageCaptureProvider = provider8;
        this.sepImageCaptureProvider = provider9;
        this.screenshotDetectionControllerProvider = provider10;
        this.mainExecutorProvider = provider11;
        this.scrollCaptureControllerProvider = provider12;
        this.longScreenshotHolderProvider = provider13;
        this.activityManagerProvider = provider14;
        this.timeoutHandlerProvider = provider15;
        this.broadcastSenderProvider = provider16;
        this.screenshotNotificationSmartActionsProvider = provider17;
        this.actionExecutorProvider = provider18;
        this.userManagerProvider = provider19;
        this.assistContentRequesterProvider = provider20;
        this.messageContainerControllerProvider = provider21;
        this.displayTrackerProvider = provider22;
    }

    public static ScreenshotController newInstance(Context context, FeatureFlags featureFlags, ScreenshotSmartActions screenshotSmartActions, ScreenshotNotificationsController screenshotNotificationsController, ScrollCaptureClient scrollCaptureClient, UiEventLogger uiEventLogger, ImageExporter imageExporter, ImageCapture imageCapture, SemImageCaptureImpl semImageCaptureImpl, ScreenshotDetectionController screenshotDetectionController, Executor executor, ScrollCaptureController scrollCaptureController, LongScreenshotData longScreenshotData, ActivityManager activityManager, TimeoutHandler timeoutHandler, BroadcastSender broadcastSender, ScreenshotNotificationSmartActionsProvider screenshotNotificationSmartActionsProvider, ActionIntentExecutor actionIntentExecutor, UserManager userManager, AssistContentRequester assistContentRequester, MessageContainerController messageContainerController, DisplayTracker displayTracker) {
        return new ScreenshotController(context, featureFlags, screenshotSmartActions, screenshotNotificationsController, scrollCaptureClient, uiEventLogger, imageExporter, imageCapture, semImageCaptureImpl, screenshotDetectionController, executor, scrollCaptureController, longScreenshotData, activityManager, timeoutHandler, broadcastSender, screenshotNotificationSmartActionsProvider, actionIntentExecutor, userManager, assistContentRequester, messageContainerController, displayTracker);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return newInstance((Context) this.contextProvider.get(), (FeatureFlags) this.flagsProvider.get(), (ScreenshotSmartActions) this.screenshotSmartActionsProvider.get(), (ScreenshotNotificationsController) this.screenshotNotificationsControllerProvider.get(), (ScrollCaptureClient) this.scrollCaptureClientProvider.get(), (UiEventLogger) this.uiEventLoggerProvider.get(), (ImageExporter) this.imageExporterProvider.get(), (ImageCapture) this.imageCaptureProvider.get(), (SemImageCaptureImpl) this.sepImageCaptureProvider.get(), (ScreenshotDetectionController) this.screenshotDetectionControllerProvider.get(), (Executor) this.mainExecutorProvider.get(), (ScrollCaptureController) this.scrollCaptureControllerProvider.get(), (LongScreenshotData) this.longScreenshotHolderProvider.get(), (ActivityManager) this.activityManagerProvider.get(), (TimeoutHandler) this.timeoutHandlerProvider.get(), (BroadcastSender) this.broadcastSenderProvider.get(), (ScreenshotNotificationSmartActionsProvider) this.screenshotNotificationSmartActionsProvider.get(), (ActionIntentExecutor) this.actionExecutorProvider.get(), (UserManager) this.userManagerProvider.get(), (AssistContentRequester) this.assistContentRequesterProvider.get(), (MessageContainerController) this.messageContainerControllerProvider.get(), (DisplayTracker) this.displayTrackerProvider.get());
    }
}
