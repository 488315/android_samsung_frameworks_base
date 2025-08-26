package com.android.systemui.screenshot;

import android.R;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.assist.AssistContent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Insets;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Process;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.util.Log;
import android.util.MathUtils;
import android.view.Display;
import android.view.IScrollCaptureResponseListener;
import android.view.ScrollCaptureResponse;
import android.view.View;
import android.view.ViewGroup;
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
import com.android.systemui.broadcast.BroadcastSender$$ExternalSyntheticLambda0;
import com.android.systemui.dagger.DaggerReferenceGlobalRootComponent;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.log.DebugLogger;
import com.android.systemui.screenshot.ActionExecutor;
import com.android.systemui.screenshot.AssistContentRequester;
import com.android.systemui.screenshot.ImageExporter;
import com.android.systemui.screenshot.ScreenshotActionsController;
import com.android.systemui.screenshot.ScreenshotActionsController.ActionsCallback;
import com.android.systemui.screenshot.ScreenshotNotificationsController;
import com.android.systemui.screenshot.ScreenshotShelfViewProxy;
import com.android.systemui.screenshot.TakeScreenshotService;
import com.android.systemui.screenshot.scroll.ScrollCaptureClient;
import com.android.systemui.screenshot.scroll.ScrollCaptureExecutor;
import com.android.systemui.screenshot.sep.ScreenCaptureHelper;
import com.android.systemui.screenshot.ui.ScreenshotAnimationController;
import com.android.systemui.screenshot.ui.ScreenshotShelfView;
import com.android.systemui.screenshot.ui.viewmodel.ActionButtonAppearance;
import com.android.systemui.screenshot.ui.viewmodel.AnimationState;
import com.android.systemui.screenshot.ui.viewmodel.ScreenshotViewModel;
import com.android.systemui.util.Assert;
import com.android.systemui.util.SettingsHelper;
import com.google.common.util.concurrent.ListenableFuture;
import com.samsung.systemui.splugins.edgelightingplus.PluginEdgeLightingPlus;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
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
import kotlin.jvm.internal.Reflection;

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

    /* renamed from: com.android.systemui.screenshot.LegacyScreenshotController$3, reason: invalid class name */
    public class AnonymousClass3 implements ViewRootImpl.ActivityConfigCallback {
        public final /* synthetic */ UserHandle val$owner;
        public final /* synthetic */ UUID val$requestId;

        public AnonymousClass3(UUID uuid, UserHandle userHandle) {
            this.val$requestId = uuid;
            this.val$owner = userHandle;
        }

        public final void onConfigurationChanged(Configuration configuration, int i) throws Resources.NotFoundException {
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
        WindowContext windowContextCreateWindowContext = context.createDisplayContext(display).createWindowContext(2036, null);
        this.mContext = windowContextCreateWindowContext;
        this.mUserManager = userManager;
        this.mMessageContainerController = messageContainerController;
        this.mAssistContentRequester = assistContentRequester;
        this.mAnnouncementResolver = announcementResolver;
        this.mActionIntentCreator = actionIntentCreator;
        ScreenshotShelfViewProxy proxy = factory.getProxy(windowContextCreateWindowContext, display.getDisplayId());
        this.mViewProxy = proxy;
        timeoutHandler.mOnTimeout = new LegacyScreenshotController$$ExternalSyntheticLambda0(this, 0);
        WindowManager.LayoutParams floatingWindowParams = FloatingWindowUtil.getFloatingWindowParams();
        this.mWindowLayoutParams = floatingWindowParams;
        floatingWindowParams.setTitle("ScreenshotAnimation");
        PhoneWindow phoneWindow = new PhoneWindow(windowContextCreateWindowContext);
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
            public final void onDismiss() throws RemoteException {
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
        ActionExecutor actionExecutorCreate = factory4.create(phoneWindow, proxy, new Function0() { // from class: com.android.systemui.screenshot.LegacyScreenshotController$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() throws RemoteException {
                this.f$0.finishDismiss();
                return Unit.INSTANCE;
            }
        });
        this.mActionExecutor = actionExecutorCreate;
        this.mActionsController = factory3.getController(actionExecutorCreate);
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

    public final void finishDismiss() throws RemoteException {
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

    /* JADX WARN: Code restructure failed: missing block: B:42:0x0175, code lost:
    
        if (java.lang.Math.abs((r5 / r6) - (r2.width() / r2.height())) < 0.1f) goto L46;
     */
    @Override // com.android.systemui.screenshot.ScreenshotHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void handleScreenshot(final ScreenshotData screenshotData, Consumer consumer, TakeScreenshotService.RequestCallback requestCallback) {
        Assert.isMainThread();
        this.mCurrentRequestCallback = requestCallback;
        Bitmap bitmap = screenshotData.bitmap;
        if (bitmap == null) {
            Log.e("Screenshot", "handleScreenshot: Screenshot bitmap was null");
            this.mNotificationsController.notifyScreenshotError(com.android.systemui.R.string.screenshot_failed_to_capture_text);
            TakeScreenshotService.RequestCallback requestCallback2 = this.mCurrentRequestCallback;
            if (requestCallback2 != null) {
                ((TakeScreenshotService.RequestCallbackImpl) requestCallback2).reportError();
                return;
            }
            return;
        }
        this.mScreenBitmap = bitmap;
        String str = this.mPackageName;
        this.mPackageName = screenshotData.getPackageNameString();
        final boolean z = false;
        if (Settings.Secure.getInt(this.mContext.createContextAsUser(Process.myUserHandle(), 0).getContentResolver(), SettingsHelper.INDEX_USER_SETUP_COMPLETE, 0) != 1) {
            Log.w("Screenshot", "User setup not complete, displaying toast only");
            ScreenshotSoundController screenshotSoundController = this.mScreenshotSoundController;
            if (screenshotSoundController != null) {
                ScreenshotSoundControllerImpl screenshotSoundControllerImpl = (ScreenshotSoundControllerImpl) screenshotSoundController;
                CoroutineTracingKt.launchTraced$default(screenshotSoundControllerImpl.coroutineScope, null, null, new ScreenshotSoundControllerImpl$playScreenshotSoundAsync$1(screenshotSoundControllerImpl, null), 7);
            }
            saveScreenshotInBackground(screenshotData, UUID.randomUUID(), (TakeScreenshotExecutorImpl$sam$java_util_function_Consumer$0) consumer, new LegacyScreenshotController$$ExternalSyntheticLambda10(this, 0));
            return;
        }
        Intent intent = new Intent("com.android.systemui.SCREENSHOT");
        BroadcastSender broadcastSender = this.mBroadcastSender;
        broadcastSender.getClass();
        broadcastSender.sendInBackground(String.valueOf(intent), new BroadcastSender$$ExternalSyntheticLambda0(broadcastSender, intent, 0));
        int i = this.mContext.getResources().getConfiguration().orientation;
        this.mScreenBitmap.setHasAlpha(false);
        this.mScreenBitmap.prepareToDraw();
        withWindowAttached(new LegacyScreenshotController$$ExternalSyntheticLambda9(this, screenshotData, 0));
        final ScreenshotShelfViewProxy screenshotShelfViewProxy = this.mViewProxy;
        screenshotShelfViewProxy.reset();
        ScreenshotShelfView screenshotShelfView = screenshotShelfViewProxy.view;
        if (screenshotShelfView.isAttachedToWindow() && !screenshotShelfViewProxy.isDismissing) {
            this.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_REENTERED, 0, str);
        }
        screenshotShelfViewProxy.packageName = this.mPackageName;
        ScreenshotActionsController screenshotActionsController = this.mActionsController;
        screenshotActionsController.getClass();
        final UUID uuidRandomUUID = UUID.randomUUID();
        screenshotActionsController.currentScreenshotId = uuidRandomUUID;
        Map map = screenshotActionsController.actionProviders;
        uuidRandomUUID.getClass();
        map.put(uuidRandomUUID, ((DaggerReferenceGlobalRootComponent.ReferenceSysUIComponentImpl.SwitchingProvider.AnonymousClass144) screenshotActionsController.actionsProviderFactory).create(uuidRandomUUID, screenshotData, screenshotActionsController.actionExecutor, screenshotActionsController.new ActionsCallback(uuidRandomUUID)));
        saveScreenshotInBackground(screenshotData, uuidRandomUUID, (TakeScreenshotExecutorImpl$sam$java_util_function_Consumer$0) consumer, new Consumer() { // from class: com.android.systemui.screenshot.LegacyScreenshotController$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                LegacyScreenshotController legacyScreenshotController = this.f$0;
                ScreenshotData screenshotData2 = screenshotData;
                UUID uuid = uuidRandomUUID;
                ImageExporter.Result result = (ImageExporter.Result) obj;
                legacyScreenshotController.getClass();
                if (result.uri != null) {
                    legacyScreenshotController.mActionsController.setCompletedScreenshot(uuid, new ScreenshotSavedResult(result.uri, screenshotData2.userHandle, result.timestamp));
                }
            }
        });
        int i2 = screenshotData.taskId;
        if (i2 >= 0) {
            AssistContentRequester.Callback callback = new AssistContentRequester.Callback() { // from class: com.android.systemui.screenshot.LegacyScreenshotController$$ExternalSyntheticLambda4
                @Override // com.android.systemui.screenshot.AssistContentRequester.Callback
                public final void onAssistContentAvailable(AssistContent assistContent) {
                    ScreenshotActionsProvider screenshotActionsProvider = (ScreenshotActionsProvider) ((LinkedHashMap) this.f$0.mActionsController.actionProviders).get(uuidRandomUUID);
                    if (screenshotActionsProvider != null) {
                        ((DefaultScreenshotActionsProvider) screenshotActionsProvider).webUri = assistContent != null ? assistContent.getWebUri() : null;
                    }
                }
            };
            AssistContentRequester assistContentRequester = this.mAssistContentRequester;
            assistContentRequester.mSystemInteractionExecutor.execute(new AssistContentRequester$$ExternalSyntheticLambda0(assistContentRequester, callback, i2));
        } else {
            ScreenshotActionsProvider screenshotActionsProvider = (ScreenshotActionsProvider) ((LinkedHashMap) screenshotActionsController.actionProviders).get(uuidRandomUUID);
            if (screenshotActionsProvider != null) {
                ((DefaultScreenshotActionsProvider) screenshotActionsProvider).webUri = null;
            }
        }
        setWindowFocusable(true);
        screenshotShelfView.requestFocus();
        int i3 = screenshotData.type;
        if (i3 != 3) {
            withWindowAttached(new LegacyScreenshotController$$ExternalSyntheticLambda11(this, uuidRandomUUID, screenshotData.userHandle, 0));
        }
        View decorView = this.mWindow.getDecorView();
        if (!decorView.isAttachedToWindow() && !this.mAttachRequested) {
            this.mAttachRequested = true;
            this.mWindowManager.addView(decorView, this.mWindowLayoutParams);
            decorView.requestApplyInsets();
            ViewGroup viewGroup = (ViewGroup) decorView.requireViewById(R.id.content);
            viewGroup.setClipChildren(false);
            viewGroup.setClipToPadding(false);
        }
        final Rect rect = screenshotData.originalScreenBounds;
        if (i3 != 3) {
            z = true;
        } else {
            if (rect != null) {
                Bitmap bitmap2 = screenshotData.bitmap;
                Insets insets = screenshotData.originalInsets;
                int width = (bitmap2.getWidth() - insets.left) - insets.right;
                int height = (bitmap2.getHeight() - insets.top) - insets.bottom;
                if (height != 0) {
                    if (width != 0) {
                        if (bitmap2.getWidth() != 0) {
                            if (bitmap2.getHeight() != 0) {
                            }
                        }
                    }
                }
            }
            rect = new Rect(0, 0, screenshotData.bitmap.getWidth(), screenshotData.bitmap.getHeight());
            z = true;
        }
        final Runnable runnable = new Runnable() { // from class: com.android.systemui.screenshot.LegacyScreenshotController$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                LegacyScreenshotController legacyScreenshotController = this.f$0;
                Rect rect2 = rect;
                boolean z2 = z;
                ScreenshotData screenshotData2 = screenshotData;
                legacyScreenshotController.getClass();
                LegacyScreenshotController$$ExternalSyntheticLambda9 legacyScreenshotController$$ExternalSyntheticLambda9 = new LegacyScreenshotController$$ExternalSyntheticLambda9(legacyScreenshotController, screenshotData2, 1);
                Animator animator = legacyScreenshotController.mScreenshotAnimation;
                if (animator != null && animator.isRunning()) {
                    legacyScreenshotController.mScreenshotAnimation.cancel();
                }
                final ScreenshotShelfViewProxy screenshotShelfViewProxy2 = legacyScreenshotController.mViewProxy;
                screenshotShelfViewProxy2.getClass();
                final ScreenshotShelfViewProxy$$ExternalSyntheticLambda1 screenshotShelfViewProxy$$ExternalSyntheticLambda1 = new ScreenshotShelfViewProxy$$ExternalSyntheticLambda1(screenshotShelfViewProxy2, 1);
                final ScreenshotAnimationController screenshotAnimationController = screenshotShelfViewProxy2.animationController;
                screenshotAnimationController.getClass();
                AnimatorSet animatorSet = new AnimatorSet();
                ScreenshotShelfView screenshotShelfView2 = screenshotAnimationController.view;
                screenshotShelfView2.setAlpha(1.0f);
                screenshotShelfView2.setTranslationX(0.0f);
                Rect rect3 = new Rect();
                screenshotAnimationController.screenshotPreview.getHitRect(rect3);
                final float fWidth = rect2.width() / rect3.width();
                final float fHeight = rect2.height() / rect3.height();
                final PointF pointF = new PointF(rect2.exactCenterX(), rect2.exactCenterY());
                final PointF pointF2 = new PointF(rect3.exactCenterX(), rect3.exactCenterY());
                ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(pointF.y, pointF2.y);
                valueAnimatorOfFloat.setDuration(500L);
                valueAnimatorOfFloat.setInterpolator(screenshotAnimationController.fastOutSlowIn);
                valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.screenshot.ui.ScreenshotAnimationController$getPreviewAnimator$1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                        screenshotAnimationController.screenshotPreview.setY(fFloatValue - (r2.getHeight() / 2.0f));
                    }
                });
                ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(0.0f, 1.0f);
                valueAnimatorOfFloat2.setDuration(234L);
                valueAnimatorOfFloat2.setInterpolator(screenshotAnimationController.fastOutSlowIn);
                valueAnimatorOfFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.screenshot.ui.ScreenshotAnimationController$getPreviewAnimator$2
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        float animatedFraction = valueAnimator.getAnimatedFraction();
                        screenshotAnimationController.screenshotPreview.setScaleX(MathUtils.lerp(fWidth, 1.0f, animatedFraction));
                        screenshotAnimationController.screenshotPreview.setScaleY(MathUtils.lerp(fHeight, 1.0f, animatedFraction));
                        screenshotAnimationController.screenshotPreview.setX(MathUtils.lerp(pointF.x, pointF2.x, animatedFraction) - (screenshotAnimationController.screenshotPreview.getWidth() / 2.0f));
                    }
                });
                AnimatorSet animatorSet2 = new AnimatorSet();
                animatorSet2.play(valueAnimatorOfFloat2).with(valueAnimatorOfFloat);
                animatorSet2.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.screenshot.ui.ScreenshotAnimationController$getPreviewAnimator$$inlined$doOnEnd$1
                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator2) {
                        screenshotAnimationController.screenshotPreview.setScaleX(1.0f);
                        screenshotAnimationController.screenshotPreview.setScaleY(1.0f);
                        screenshotAnimationController.screenshotPreview.setX(pointF2.x - (r4.getWidth() / 2.0f));
                        screenshotAnimationController.screenshotPreview.setY(pointF2.y - (r4.getHeight() / 2.0f));
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationCancel(Animator animator2) {
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationRepeat(Animator animator2) {
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationStart(Animator animator2) {
                    }
                });
                animatorSet2.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.screenshot.ui.ScreenshotAnimationController$getPreviewAnimator$$inlined$doOnStart$1
                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationStart(Animator animator2) {
                        screenshotAnimationController.screenshotPreview.setVisibility(0);
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationCancel(Animator animator2) {
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator2) {
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationRepeat(Animator animator2) {
                    }
                });
                if (z2) {
                    ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(screenshotAnimationController.flashView, "alpha", 0.0f, 1.0f);
                    objectAnimatorOfFloat.setDuration(133L);
                    objectAnimatorOfFloat.setInterpolator(screenshotAnimationController.fastOutSlowIn);
                    ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(screenshotAnimationController.flashView, "alpha", 1.0f, 0.0f);
                    objectAnimatorOfFloat2.setDuration(217L);
                    objectAnimatorOfFloat2.setInterpolator(screenshotAnimationController.fastOutSlowIn);
                    objectAnimatorOfFloat.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.screenshot.ui.ScreenshotAnimationController$getEntranceAnimation$$inlined$doOnStart$1
                        @Override // android.animation.Animator.AnimatorListener
                        public final void onAnimationStart(Animator animator2) {
                            screenshotAnimationController.flashView.setVisibility(0);
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public final void onAnimationCancel(Animator animator2) {
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public final void onAnimationEnd(Animator animator2) {
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public final void onAnimationRepeat(Animator animator2) {
                        }
                    });
                    objectAnimatorOfFloat2.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.screenshot.ui.ScreenshotAnimationController$getEntranceAnimation$$inlined$doOnEnd$1
                        @Override // android.animation.Animator.AnimatorListener
                        public final void onAnimationEnd(Animator animator2) {
                            screenshotAnimationController.flashView.setVisibility(8);
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public final void onAnimationCancel(Animator animator2) {
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public final void onAnimationRepeat(Animator animator2) {
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public final void onAnimationStart(Animator animator2) {
                        }
                    });
                    animatorSet.play(objectAnimatorOfFloat2).after(objectAnimatorOfFloat);
                    animatorSet.play(animatorSet2).with(objectAnimatorOfFloat2);
                    animatorSet.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.screenshot.ui.ScreenshotAnimationController$getEntranceAnimation$$inlined$doOnStart$2
                        @Override // android.animation.Animator.AnimatorListener
                        public final void onAnimationStart(Animator animator2) {
                            screenshotAnimationController.screenshotPreview.setVisibility(4);
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public final void onAnimationCancel(Animator animator2) {
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public final void onAnimationEnd(Animator animator2) {
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public final void onAnimationRepeat(Animator animator2) {
                        }
                    });
                }
                float height2 = screenshotShelfView2.getHeight() - screenshotAnimationController.actionContainer.getTop();
                ValueAnimator valueAnimatorOfFloat3 = ValueAnimator.ofFloat(height2, 0.0f);
                valueAnimatorOfFloat3.setDuration(500L);
                valueAnimatorOfFloat3.setInterpolator(screenshotAnimationController.fastOutSlowIn);
                valueAnimatorOfFloat3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.screenshot.ui.ScreenshotAnimationController$getActionsAnimator$1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        screenshotAnimationController.actionContainer.setTranslationY(((Float) valueAnimator.getAnimatedValue()).floatValue());
                    }
                });
                screenshotAnimationController.actionContainer.setTranslationY(height2);
                animatorSet.play(valueAnimatorOfFloat3).with(animatorSet2);
                ValueAnimator valueAnimatorOfFloat4 = ValueAnimator.ofFloat(0.0f);
                valueAnimatorOfFloat4.setDuration(0L);
                valueAnimatorOfFloat4.setStartDelay(200L);
                valueAnimatorOfFloat4.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.screenshot.ui.ScreenshotAnimationController$getEntranceAnimation$lambda$6$$inlined$doOnEnd$1
                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator2) {
                        screenshotShelfViewProxy$$ExternalSyntheticLambda1.invoke();
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationCancel(Animator animator2) {
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationRepeat(Animator animator2) {
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationStart(Animator animator2) {
                    }
                });
                animatorSet.play(valueAnimatorOfFloat4).with(valueAnimatorOfFloat3);
                ValueAnimator valueAnimatorOfFloat5 = ValueAnimator.ofFloat(0.0f, 1.0f);
                valueAnimatorOfFloat5.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.screenshot.ui.ScreenshotAnimationController$getEntranceAnimation$4
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        Iterator it = screenshotAnimationController.staticUI.iterator();
                        while (it.hasNext()) {
                            ((View) it.next()).setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                        }
                    }
                });
                animatorSet.play(valueAnimatorOfFloat5).after(animatorSet2);
                animatorSet.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.screenshot.ui.ScreenshotAnimationController$getEntranceAnimation$$inlined$doOnStart$3
                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationStart(Animator animator2) {
                        ScreenshotViewModel screenshotViewModel = screenshotAnimationController.viewModel;
                        screenshotViewModel._isAnimating.updateState(null, Boolean.TRUE);
                        Iterator it = screenshotAnimationController.staticUI.iterator();
                        while (it.hasNext()) {
                            ((View) it.next()).setAlpha(0.0f);
                        }
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationCancel(Animator animator2) {
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator2) {
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationRepeat(Animator animator2) {
                    }
                });
                animatorSet.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.screenshot.ui.ScreenshotAnimationController$getEntranceAnimation$$inlined$doOnEnd$2
                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator2) {
                        ScreenshotViewModel screenshotViewModel = screenshotAnimationController.viewModel;
                        screenshotViewModel._isAnimating.updateState(null, Boolean.FALSE);
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationCancel(Animator animator2) {
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationRepeat(Animator animator2) {
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationStart(Animator animator2) {
                    }
                });
                screenshotAnimationController.animator = animatorSet;
                animatorSet.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.screenshot.ScreenshotShelfViewProxy$createScreenshotDropInAnimation$$inlined$doOnStart$1
                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationStart(Animator animator2) {
                        screenshotShelfViewProxy2.thumbnailObserver.getClass();
                        ScreenshotViewModel screenshotViewModel = screenshotShelfViewProxy2.viewModel;
                        screenshotViewModel._animationState.setValue(AnimationState.ENTRANCE_STARTED);
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationCancel(Animator animator2) {
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator2) {
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationRepeat(Animator animator2) {
                    }
                });
                animatorSet.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.screenshot.ScreenshotShelfViewProxy$createScreenshotDropInAnimation$$inlined$doOnEnd$1
                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator2) {
                        ScreenshotShelfViewProxy.ScreenshotViewCallback screenshotViewCallback = screenshotShelfViewProxy2.callbacks;
                        if (screenshotViewCallback != null) {
                            screenshotViewCallback.onUserInteraction();
                        }
                        screenshotShelfViewProxy2.thumbnailObserver.getClass();
                        ScreenshotViewModel screenshotViewModel = screenshotShelfViewProxy2.viewModel;
                        screenshotViewModel._animationState.setValue(AnimationState.ENTRANCE_COMPLETE);
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationCancel(Animator animator2) {
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationRepeat(Animator animator2) {
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationStart(Animator animator2) {
                    }
                });
                legacyScreenshotController.mScreenshotAnimation = animatorSet;
                animatorSet.addListener(new AnimatorListenerAdapter(legacyScreenshotController, legacyScreenshotController$$ExternalSyntheticLambda9) { // from class: com.android.systemui.screenshot.LegacyScreenshotController.5
                    public final /* synthetic */ Runnable val$onAnimationComplete;

                    {
                        this.val$onAnimationComplete = legacyScreenshotController$$ExternalSyntheticLambda9;
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator2) {
                        super.onAnimationEnd(animator2);
                        this.val$onAnimationComplete.run();
                    }
                });
                ScreenshotSoundController screenshotSoundController2 = legacyScreenshotController.mScreenshotSoundController;
                if (screenshotSoundController2 != null) {
                    ScreenshotSoundControllerImpl screenshotSoundControllerImpl2 = (ScreenshotSoundControllerImpl) screenshotSoundController2;
                    CoroutineTracingKt.launchTraced$default(screenshotSoundControllerImpl2.coroutineScope, null, null, new ScreenshotSoundControllerImpl$playScreenshotSoundAsync$1(screenshotSoundControllerImpl2, null), 7);
                }
                legacyScreenshotController.mScreenshotAnimation.start();
            }
        };
        screenshotShelfView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: com.android.systemui.screenshot.ScreenshotShelfViewProxy$prepareEntranceAnimation$1
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public final boolean onPreDraw() {
                DebugLogger debugLogger = DebugLogger.INSTANCE;
                Reflection.getOrCreateKotlinClass(ScreenshotShelfViewProxy$prepareEntranceAnimation$1.class).getSimpleName();
                screenshotShelfViewProxy.view.getViewTreeObserver().removeOnPreDrawListener(this);
                runnable.run();
                return true;
            }
        });
        Bitmap bitmap3 = screenshotData.bitmap;
        ScreenshotViewModel screenshotViewModel = screenshotShelfViewProxy.viewModel;
        screenshotViewModel._preview.setValue(bitmap3);
        Drawable drawable = AppCompatResources.getDrawable(com.android.systemui.R.drawable.overlay_badge_background, screenshotShelfViewProxy.context);
        UserHandle userHandle = screenshotData.userHandle;
        if (drawable != null && userHandle != null) {
            screenshotViewModel._badge.setValue(screenshotShelfViewProxy.context.getPackageManager().getUserBadgedIcon(drawable, userHandle));
        }
        this.mWindow.getDecorView().setOnApplyWindowInsetsListener(new LegacyScreenshotController$$ExternalSyntheticLambda6());
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
        View viewPeekDecorView = this.mWindow.peekDecorView();
        if (viewPeekDecorView != null && viewPeekDecorView.isAttachedToWindow()) {
            this.mWindowManager.removeViewImmediate(viewPeekDecorView);
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
            public final Object mo781invoke(Object obj) {
                ScreenshotActionsProvider screenshotActionsProvider;
                UUID uuid2 = uuid;
                UserHandle userHandle2 = userHandle;
                ScrollCaptureResponse scrollCaptureResponse = (ScrollCaptureResponse) obj;
                LegacyScreenshotController legacyScreenshotController = this.f$0;
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
                ScrollCaptureClient scrollCaptureClient2 = scrollCaptureClient;
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
            public final void run() throws ExecutionException, InterruptedException {
                ScrollCaptureExecutor scrollCaptureExecutor2 = scrollCaptureExecutor;
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
                    function1.mo781invoke(scrollCaptureResponse);
                }
            }
        }, scrollCaptureExecutor.mainExecutor);
        scrollCaptureExecutor.lastScrollCaptureRequest = future;
    }

    public final void saveScreenshotInBackground(final ScreenshotData screenshotData, UUID uuid, final TakeScreenshotExecutorImpl$sam$java_util_function_Consumer$0 takeScreenshotExecutorImpl$sam$java_util_function_Consumer$0, final Consumer consumer) {
        final CallbackToFutureAdapter.SafeFuture safeFutureExport = this.mImageExporter.export(this.mBgExecutor, uuid, screenshotData.bitmap, screenshotData.userHandle, this.mDisplay.getDisplayId());
        safeFutureExport.delegate.addListener(new Runnable() { // from class: com.android.systemui.screenshot.LegacyScreenshotController$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                LegacyScreenshotController legacyScreenshotController = this.f$0;
                CallbackToFutureAdapter.SafeFuture safeFuture = safeFutureExport;
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
        View viewPeekDecorView;
        WindowManager.LayoutParams layoutParams = this.mWindowLayoutParams;
        int i = layoutParams.flags;
        if (z) {
            layoutParams.flags = i & (-9);
        } else {
            layoutParams.flags = i | 8;
        }
        if (layoutParams.flags == i || (viewPeekDecorView = this.mWindow.peekDecorView()) == null || !viewPeekDecorView.isAttachedToWindow()) {
            return;
        }
        this.mWindowManager.updateViewLayout(viewPeekDecorView, this.mWindowLayoutParams);
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
