package com.android.systemui.screenshot;

import android.R;
import android.animation.Animator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;
import android.view.Display;
import android.view.IScrollCaptureResponseListener;
import android.view.ScrollCaptureResponse;
import android.view.View;
import android.view.ViewRootImpl;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.window.WindowContext;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.policy.PhoneWindow;
import com.android.settingslib.applications.InterestingConfigChanges;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.screenshot.ActionExecutor;
import com.android.systemui.screenshot.ImageExporter;
import com.android.systemui.screenshot.ScreenshotActionsController;
import com.android.systemui.screenshot.ScreenshotNotificationsController;
import com.android.systemui.screenshot.ScreenshotShelfViewProxy;
import com.android.systemui.screenshot.TakeScreenshotService;
import com.android.systemui.screenshot.scroll.ScrollCaptureClient;
import com.android.systemui.screenshot.scroll.ScrollCaptureExecutor;
import com.android.systemui.screenshot.sep.ScreenCaptureHelper;
import com.android.systemui.screenshot.ui.ScreenshotShelfView;
import com.android.systemui.screenshot.ui.viewmodel.ActionButtonAppearance;
import com.google.common.util.concurrent.ListenableFuture;
import com.samsung.systemui.splugins.edgelightingplus.PluginEdgeLightingPlus;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import javax.inject.Provider;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class LegacyScreenshotController implements InteractiveScreenshotHandler {
    public final ActionExecutor mActionExecutor;
    public final ActionIntentCreator mActionIntentCreator;
    public final ScreenshotActionsController mActionsController;
    public final AnnouncementResolver mAnnouncementResolver;
    public final AssistContentRequester mAssistContentRequester;
    public boolean mAttachRequested;
    public final ExecutorService mBgExecutor;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final BroadcastSender mBroadcastSender;
    public final InterestingConfigChanges mConfigChanges;
    public final WindowContext mContext;
    public final AnonymousClass1 mCopyBroadcastReceiver;
    public TakeScreenshotService.RequestCallback mCurrentRequestCallback;
    public boolean mDetachRequested;
    public final Display mDisplay;
    public final ImageCapture mImageCapture;
    public final ImageExporter mImageExporter;
    public final Executor mMainExecutor;
    public final MessageContainerController mMessageContainerController;
    public final ScreenshotNotificationsController mNotificationsController;
    public String mPackageName = "";
    public Bitmap mScreenBitmap;
    public Animator mScreenshotAnimation;
    public final TimeoutHandler mScreenshotHandler;
    public final ScreenshotSoundController mScreenshotSoundController;
    public final ScrollCaptureExecutor mScrollCaptureExecutor;
    public final UiEventLogger mUiEventLogger;
    public final UserManager mUserManager;
    public final ScreenshotShelfViewProxy mViewProxy;
    public final PhoneWindow mWindow;
    public final WindowManager.LayoutParams mWindowLayoutParams;
    public final WindowManager mWindowManager;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.screenshot.LegacyScreenshotController$3, reason: invalid class name */
    public class AnonymousClass3 implements ViewRootImpl.ActivityConfigCallback {
        public final /* synthetic */ UserHandle val$owner;
        public final /* synthetic */ UUID val$requestId;

        public AnonymousClass3(UUID uuid, UserHandle userHandle) {
            this.val$requestId = uuid;
            this.val$owner = userHandle;
        }

        public final void onConfigurationChanged(Configuration configuration, int i) {
            LegacyScreenshotController legacyScreenshotController = LegacyScreenshotController.this;
            if (legacyScreenshotController.mConfigChanges.applyNewConfig(legacyScreenshotController.mContext.getResources())) {
                Iterator it = ((LinkedHashMap) LegacyScreenshotController.this.mActionsController.actionProviders).values().iterator();
                while (it.hasNext()) {
                    ((DefaultScreenshotActionsProvider) ((ScreenshotActionsProvider) it.next())).onScrollClick = null;
                }
                LegacyScreenshotController.this.mScreenshotHandler.postDelayed(new LegacyScreenshotController$$ExternalSyntheticLambda11(this, this.val$requestId, this.val$owner, 1), 150L);
                LegacyScreenshotController legacyScreenshotController2 = LegacyScreenshotController.this;
                ScreenshotShelfViewProxy screenshotShelfViewProxy = legacyScreenshotController2.mViewProxy;
                screenshotShelfViewProxy.view.updateInsets(legacyScreenshotController2.mWindowManager.getCurrentWindowMetrics().getWindowInsets());
                Animator animator = LegacyScreenshotController.this.mScreenshotAnimation;
                if (animator == null || !animator.isRunning()) {
                    return;
                }
                LegacyScreenshotController.this.mScreenshotAnimation.end();
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r11v7, types: [android.content.BroadcastReceiver, com.android.systemui.screenshot.LegacyScreenshotController$1] */
    public LegacyScreenshotController(Context context, WindowManager windowManager, FeatureFlags featureFlags, ScreenshotShelfViewProxy.Factory factory, ScreenshotSmartActions screenshotSmartActions, ScreenshotNotificationsController.Factory factory2, UiEventLogger uiEventLogger, ImageExporter imageExporter, ImageCapture imageCapture, Executor executor, ScrollCaptureExecutor scrollCaptureExecutor, TimeoutHandler timeoutHandler, BroadcastSender broadcastSender, BroadcastDispatcher broadcastDispatcher, ScreenshotNotificationSmartActionsProvider screenshotNotificationSmartActionsProvider, ScreenshotActionsController.Factory factory3, ActionExecutor.Factory factory4, UserManager userManager, AssistContentRequester assistContentRequester, MessageContainerController messageContainerController, Provider provider, AnnouncementResolver announcementResolver, ActionIntentCreator actionIntentCreator, Display display) {
        InterestingConfigChanges interestingConfigChanges = new InterestingConfigChanges(-2147474556);
        this.mConfigChanges = interestingConfigChanges;
        this.mNotificationsController = factory2.create(display.getDisplayId());
        this.mUiEventLogger = uiEventLogger;
        this.mImageExporter = imageExporter;
        this.mImageCapture = imageCapture;
        this.mMainExecutor = executor;
        this.mScrollCaptureExecutor = scrollCaptureExecutor;
        this.mBgExecutor = Executors.newSingleThreadExecutor();
        this.mBroadcastSender = broadcastSender;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mScreenshotHandler = timeoutHandler;
        timeoutHandler.mDefaultTimeout = PluginEdgeLightingPlus.VERSION;
        this.mDisplay = display;
        this.mWindowManager = windowManager;
        WindowContext createWindowContext = context.createDisplayContext(display).createWindowContext(2036, null);
        this.mContext = createWindowContext;
        this.mUserManager = userManager;
        this.mMessageContainerController = messageContainerController;
        this.mAssistContentRequester = assistContentRequester;
        this.mAnnouncementResolver = announcementResolver;
        this.mActionIntentCreator = actionIntentCreator;
        ScreenshotShelfViewProxy proxy = factory.getProxy(createWindowContext, display.getDisplayId());
        this.mViewProxy = proxy;
        timeoutHandler.mOnTimeout = new LegacyScreenshotController$$ExternalSyntheticLambda0(this, 0);
        WindowManager.LayoutParams floatingWindowParams = FloatingWindowUtil.getFloatingWindowParams();
        this.mWindowLayoutParams = floatingWindowParams;
        floatingWindowParams.setTitle("ScreenshotAnimation");
        PhoneWindow phoneWindow = new PhoneWindow(createWindowContext);
        phoneWindow.requestFeature(1);
        phoneWindow.requestFeature(13);
        phoneWindow.setBackgroundDrawableResource(R.color.transparent);
        this.mWindow = phoneWindow;
        phoneWindow.setWindowManager(windowManager, (IBinder) null, (String) null);
        interestingConfigChanges.applyNewConfig(context.getResources());
        ScreenshotShelfView screenshotShelfView = proxy.view;
        messageContainerController.setView(screenshotShelfView);
        proxy.callbacks = new ScreenshotShelfViewProxy.ScreenshotViewCallback() { // from class: com.android.systemui.screenshot.LegacyScreenshotController.2
            @Override // com.android.systemui.screenshot.ScreenshotShelfViewProxy.ScreenshotViewCallback
            public final void onDismiss() {
                LegacyScreenshotController.this.finishDismiss();
            }

            @Override // com.android.systemui.screenshot.ScreenshotShelfViewProxy.ScreenshotViewCallback
            public final void onTouchOutside() {
                LegacyScreenshotController.this.setWindowFocusable(false);
            }

            @Override // com.android.systemui.screenshot.ScreenshotShelfViewProxy.ScreenshotViewCallback
            public final void onUserInteraction() {
                LegacyScreenshotController.this.mScreenshotHandler.resetTimeout();
            }
        };
        phoneWindow.setContentView(screenshotShelfView);
        ActionExecutor create = factory4.create(phoneWindow, proxy, new Function0() { // from class: com.android.systemui.screenshot.LegacyScreenshotController$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                LegacyScreenshotController.this.finishDismiss();
                return Unit.INSTANCE;
            }
        });
        this.mActionExecutor = create;
        this.mActionsController = factory3.getController(create);
        if (display.getDisplayId() == 0) {
            this.mScreenshotSoundController = (ScreenshotSoundController) provider.get();
        } else {
            this.mScreenshotSoundController = null;
        }
        ?? r11 = new BroadcastReceiver() { // from class: com.android.systemui.screenshot.LegacyScreenshotController.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                if ("com.android.systemui.COPY".equals(intent.getAction())) {
                    LegacyScreenshotController.this.mViewProxy.requestDismissal(ScreenshotEvent.SCREENSHOT_DISMISSED_OTHER, null);
                }
            }
        };
        this.mCopyBroadcastReceiver = r11;
        broadcastDispatcher.registerReceiver(r11, new IntentFilter("com.android.systemui.COPY"), null, null, 4, "com.android.systemui.permission.SELF");
    }

    public final void finishDismiss() {
        Log.d("Screenshot", "finishDismiss");
        this.mActionsController.currentScreenshotId = null;
        this.mScrollCaptureExecutor.close();
        TakeScreenshotService.RequestCallback requestCallback = this.mCurrentRequestCallback;
        if (requestCallback != null) {
            Messenger messenger = ((TakeScreenshotService.RequestCallbackImpl) requestCallback).mReplyTo;
            boolean z = TakeScreenshotService.sConfigured;
            try {
                messenger.send(Message.obtain((Handler) null, 2));
            } catch (RemoteException e) {
                Log.d("Screenshot", "ignored remote exception", e);
            }
            this.mCurrentRequestCallback = null;
        }
        this.mViewProxy.reset();
        removeWindow();
        this.mScreenshotHandler.removeMessages(2);
    }

    /* JADX WARN: Code restructure failed: missing block: B:38:0x0175, code lost:
    
        if (java.lang.Math.abs((r5 / r6) - (r2.width() / r2.height())) < 0.1f) goto L46;
     */
    @Override // com.android.systemui.screenshot.ScreenshotHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void handleScreenshot(final com.android.systemui.screenshot.ScreenshotData r11, java.util.function.Consumer r12, com.android.systemui.screenshot.TakeScreenshotService.RequestCallback r13) {
        /*
            Method dump skipped, instructions count: 504
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.screenshot.LegacyScreenshotController.handleScreenshot(com.android.systemui.screenshot.ScreenshotData, java.util.function.Consumer, com.android.systemui.screenshot.TakeScreenshotService$RequestCallback):void");
    }

    @Override // com.android.systemui.screenshot.InteractiveScreenshotHandler
    public final boolean isAnimationRunning() {
        return false;
    }

    @Override // com.android.systemui.screenshot.InteractiveScreenshotHandler
    public final boolean isPendingSharedTransition() {
        return this.mActionExecutor.isPendingSharedTransition;
    }

    @Override // com.android.systemui.screenshot.InteractiveScreenshotHandler
    public final boolean isScreenshotSelectorViewVisible() {
        return false;
    }

    @Override // com.android.systemui.screenshot.InteractiveScreenshotHandler
    public final boolean isSnackBarShowing() {
        return false;
    }

    public final void logScreenshotResultStatus(Uri uri, UserHandle userHandle) {
        if (uri == null) {
            this.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_NOT_SAVED, 0, this.mPackageName);
            this.mNotificationsController.notifyScreenshotError(com.android.systemui.R.string.screenshot_failed_to_save_text);
        } else {
            this.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_SAVED, 0, this.mPackageName);
            if (this.mUserManager.isManagedProfile(userHandle.getIdentifier())) {
                this.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_SAVED_TO_WORK_PROFILE, 0, this.mPackageName);
            }
        }
    }

    @Override // com.android.systemui.screenshot.InteractiveScreenshotHandler
    public final void onDestroy() {
        removeWindow();
        ScreenshotSoundController screenshotSoundController = this.mScreenshotSoundController;
        if (screenshotSoundController != null) {
            ScreenshotSoundControllerImpl screenshotSoundControllerImpl = (ScreenshotSoundControllerImpl) screenshotSoundController;
            CoroutineTracingKt.launchTraced$default(screenshotSoundControllerImpl.coroutineScope, null, null, new ScreenshotSoundControllerImpl$releaseScreenshotSoundAsync$1(screenshotSoundControllerImpl, null), 7);
        }
        this.mBroadcastDispatcher.unregisterReceiver(this.mCopyBroadcastReceiver);
        this.mContext.release();
        this.mBgExecutor.shutdown();
    }

    @Override // com.android.systemui.screenshot.InteractiveScreenshotHandler
    public final void removeWindow() {
        View peekDecorView = this.mWindow.peekDecorView();
        if (peekDecorView != null && peekDecorView.isAttachedToWindow()) {
            this.mWindowManager.removeViewImmediate(peekDecorView);
            this.mDetachRequested = false;
        }
        if (this.mAttachRequested && !this.mDetachRequested) {
            this.mDetachRequested = true;
            withWindowAttached(new LegacyScreenshotController$$ExternalSyntheticLambda0(this, 2));
        }
        this.mViewProxy.stopInputListening();
    }

    @Override // com.android.systemui.screenshot.InteractiveScreenshotHandler
    public final void requestDismissal(ScreenshotEvent screenshotEvent) {
        this.mViewProxy.requestDismissal(screenshotEvent, null);
    }

    public final void requestScrollCapture(final UUID uuid, final UserHandle userHandle) {
        final int displayId = this.mDisplay.getDisplayId();
        IBinder windowToken = this.mWindow.getDecorView().getWindowToken();
        final Function1 function1 = new Function1() { // from class: com.android.systemui.screenshot.LegacyScreenshotController$$ExternalSyntheticLambda12
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                ScreenshotActionsProvider screenshotActionsProvider;
                UUID uuid2 = uuid;
                UserHandle userHandle2 = userHandle;
                ScrollCaptureResponse scrollCaptureResponse = (ScrollCaptureResponse) obj;
                LegacyScreenshotController legacyScreenshotController = LegacyScreenshotController.this;
                legacyScreenshotController.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_LONG_SCREENSHOT_IMPRESSION, 0, scrollCaptureResponse.getPackageName());
                LegacyScreenshotController$$ExternalSyntheticLambda15 legacyScreenshotController$$ExternalSyntheticLambda15 = new LegacyScreenshotController$$ExternalSyntheticLambda15(legacyScreenshotController, userHandle2, scrollCaptureResponse);
                ScreenshotActionsController screenshotActionsController = legacyScreenshotController.mActionsController;
                if (Intrinsics.areEqual(uuid2, screenshotActionsController.currentScreenshotId) && (screenshotActionsProvider = (ScreenshotActionsProvider) ((LinkedHashMap) screenshotActionsController.actionProviders).get(uuid2)) != null) {
                    DefaultScreenshotActionsProvider defaultScreenshotActionsProvider = (DefaultScreenshotActionsProvider) screenshotActionsProvider;
                    defaultScreenshotActionsProvider.onScrollClick = legacyScreenshotController$$ExternalSyntheticLambda15;
                    if (!defaultScreenshotActionsProvider.addedScrollChip) {
                        defaultScreenshotActionsProvider.actionsCallback.provideActionButton(new ActionButtonAppearance(AppCompatResources.getDrawable(com.android.systemui.R.drawable.ic_screenshot_scroll, defaultScreenshotActionsProvider.context), defaultScreenshotActionsProvider.context.getResources().getString(com.android.systemui.R.string.screenshot_scroll_label), defaultScreenshotActionsProvider.context.getResources().getString(com.android.systemui.R.string.screenshot_scroll_label), false, null, 24, null), new DefaultScreenshotActionsProvider$$ExternalSyntheticLambda0(defaultScreenshotActionsProvider, 3));
                        defaultScreenshotActionsProvider.addedScrollChip = true;
                    }
                }
                return Unit.INSTANCE;
            }
        };
        final ScrollCaptureExecutor scrollCaptureExecutor = this.mScrollCaptureExecutor;
        if (scrollCaptureExecutor.isLowRamDevice) {
            Log.d("ScrollCaptureExecutor", "Long screenshots not supported on this device");
            return;
        }
        final ScrollCaptureClient scrollCaptureClient = scrollCaptureExecutor.scrollCaptureClient;
        scrollCaptureClient.mHostWindowToken = windowToken;
        CallbackToFutureAdapter.SafeFuture safeFuture = scrollCaptureExecutor.lastScrollCaptureRequest;
        if (safeFuture != null) {
            safeFuture.cancel(true);
        }
        final CallbackToFutureAdapter.SafeFuture future = CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: com.android.systemui.screenshot.scroll.ScrollCaptureClient$$ExternalSyntheticLambda1
            @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
            public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                int i = displayId;
                ScrollCaptureClient scrollCaptureClient2 = ScrollCaptureClient.this;
                scrollCaptureClient2.getClass();
                try {
                    scrollCaptureClient2.mWindowManagerService.requestScrollCapture(i, scrollCaptureClient2.mHostWindowToken, -1, new IScrollCaptureResponseListener.Stub(scrollCaptureClient2, completer) { // from class: com.android.systemui.screenshot.scroll.ScrollCaptureClient.1
                        public final /* synthetic */ CallbackToFutureAdapter.Completer val$completer;

                        public AnonymousClass1(ScrollCaptureClient scrollCaptureClient22, CallbackToFutureAdapter.Completer completer2) {
                            this.val$completer = completer2;
                        }

                        public final void onScrollCaptureResponse(ScrollCaptureResponse scrollCaptureResponse) {
                            this.val$completer.set(scrollCaptureResponse);
                        }
                    });
                } catch (RemoteException e) {
                    completer2.setException(e);
                }
                return ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i, "ScrollCaptureClient#request(displayId=", ", taskId=-1)");
            }
        });
        future.delegate.addListener(new Runnable() { // from class: com.android.systemui.screenshot.scroll.ScrollCaptureExecutor$requestScrollCapture$scrollRequest$1$1
            @Override // java.lang.Runnable
            public final void run() {
                ScrollCaptureExecutor scrollCaptureExecutor2 = ScrollCaptureExecutor.this;
                ListenableFuture listenableFuture = future;
                listenableFuture.getClass();
                int i = ScrollCaptureExecutor.$r8$clinit;
                scrollCaptureExecutor2.getClass();
                ScrollCaptureResponse scrollCaptureResponse = null;
                try {
                    ScrollCaptureResponse scrollCaptureResponse2 = scrollCaptureExecutor2.lastScrollCaptureResponse;
                    if (scrollCaptureResponse2 != null) {
                        scrollCaptureResponse2.close();
                    }
                    scrollCaptureExecutor2.lastScrollCaptureResponse = null;
                    if (!listenableFuture.isCancelled()) {
                        Object obj = listenableFuture.get();
                        scrollCaptureExecutor2.lastScrollCaptureResponse = (ScrollCaptureResponse) obj;
                        ScrollCaptureResponse scrollCaptureResponse3 = (ScrollCaptureResponse) obj;
                        if (scrollCaptureResponse3.isConnected()) {
                            Log.d("ScrollCaptureExecutor", "ScrollCapture: connected to window [" + scrollCaptureResponse3.getWindowTitle() + "]");
                            scrollCaptureResponse = scrollCaptureResponse3;
                        } else {
                            Log.d("ScrollCaptureExecutor", "ScrollCapture: " + scrollCaptureResponse3.getDescription() + " [" + scrollCaptureResponse3.getWindowTitle() + "]");
                        }
                    }
                } catch (InterruptedException e) {
                    Log.e("ScrollCaptureExecutor", "requestScrollCapture interrupted", e);
                } catch (ExecutionException e2) {
                    Log.e("ScrollCaptureExecutor", "requestScrollCapture failed", e2);
                }
                if (scrollCaptureResponse != null) {
                    function1.mo779invoke(scrollCaptureResponse);
                }
            }
        }, scrollCaptureExecutor.mainExecutor);
        scrollCaptureExecutor.lastScrollCaptureRequest = future;
    }

    public final void saveScreenshotInBackground(final ScreenshotData screenshotData, UUID uuid, final TakeScreenshotExecutorImpl$sam$java_util_function_Consumer$0 takeScreenshotExecutorImpl$sam$java_util_function_Consumer$0, final Consumer consumer) {
        final CallbackToFutureAdapter.SafeFuture export = this.mImageExporter.export(this.mBgExecutor, uuid, screenshotData.bitmap, screenshotData.userHandle, this.mDisplay.getDisplayId());
        export.delegate.addListener(new Runnable() { // from class: com.android.systemui.screenshot.LegacyScreenshotController$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                LegacyScreenshotController legacyScreenshotController = LegacyScreenshotController.this;
                CallbackToFutureAdapter.SafeFuture safeFuture = export;
                ScreenshotData screenshotData2 = screenshotData;
                Consumer consumer2 = consumer;
                TakeScreenshotExecutorImpl$sam$java_util_function_Consumer$0 takeScreenshotExecutorImpl$sam$java_util_function_Consumer$02 = takeScreenshotExecutorImpl$sam$java_util_function_Consumer$0;
                legacyScreenshotController.getClass();
                try {
                    ImageExporter.Result result = (ImageExporter.Result) safeFuture.delegate.get();
                    Log.d("Screenshot", "Saved screenshot: " + result);
                    legacyScreenshotController.logScreenshotResultStatus(result.uri, screenshotData2.userHandle);
                    consumer2.accept(result);
                    takeScreenshotExecutorImpl$sam$java_util_function_Consumer$02.accept(result.uri);
                } catch (Exception e) {
                    Log.d("Screenshot", "Failed to store screenshot", e);
                    takeScreenshotExecutorImpl$sam$java_util_function_Consumer$02.accept(null);
                }
            }
        }, this.mMainExecutor);
    }

    public final void setWindowFocusable(boolean z) {
        View peekDecorView;
        WindowManager.LayoutParams layoutParams = this.mWindowLayoutParams;
        int i = layoutParams.flags;
        if (z) {
            layoutParams.flags = i & (-9);
        } else {
            layoutParams.flags = i | 8;
        }
        if (layoutParams.flags == i || (peekDecorView = this.mWindow.peekDecorView()) == null || !peekDecorView.isAttachedToWindow()) {
            return;
        }
        this.mWindowManager.updateViewLayout(peekDecorView, this.mWindowLayoutParams);
    }

    public final void withWindowAttached(final Runnable runnable) {
        final View decorView = this.mWindow.getDecorView();
        if (decorView.isAttachedToWindow()) {
            runnable.run();
        } else {
            decorView.getViewTreeObserver().addOnWindowAttachListener(new ViewTreeObserver.OnWindowAttachListener() { // from class: com.android.systemui.screenshot.LegacyScreenshotController.4
                @Override // android.view.ViewTreeObserver.OnWindowAttachListener
                public final void onWindowAttached() {
                    LegacyScreenshotController.this.mAttachRequested = false;
                    decorView.getViewTreeObserver().removeOnWindowAttachListener(this);
                    runnable.run();
                }

                @Override // android.view.ViewTreeObserver.OnWindowAttachListener
                public final void onWindowDetached() {
                }
            });
        }
    }

    @Override // com.android.systemui.screenshot.InteractiveScreenshotHandler
    public final void setScreenCaptureHelper(ScreenCaptureHelper screenCaptureHelper) {
    }

    @Override // com.android.systemui.screenshot.InteractiveScreenshotHandler
    public final void initSemScreenshotLayout() {
    }

    @Override // com.android.systemui.screenshot.InteractiveScreenshotHandler
    public final void setPartialScreenshotSelector(Bundle bundle, ScreenshotData screenshotData, TakeScreenshotExecutorImpl$sam$java_util_function_Consumer$0 takeScreenshotExecutorImpl$sam$java_util_function_Consumer$0, TakeScreenshotService.RequestCallback requestCallback) {
    }
}
