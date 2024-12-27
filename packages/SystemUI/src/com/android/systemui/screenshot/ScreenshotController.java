package com.android.systemui.screenshot;

import android.R;
import android.app.ActivityOptions;
import android.app.ExitTransitionCoordinator;
import android.app.Notification;
import android.app.Presentation;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;
import android.util.Pair;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.window.WindowContext;
import androidx.constraintlayout.widget.Guideline;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.policy.PhoneWindow;
import com.android.settingslib.applications.InterestingConfigChanges;
import com.android.systemui.Flags;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.screenshot.ActionExecutor;
import com.android.systemui.screenshot.ScreenshotActionsController;
import com.android.systemui.screenshot.ScreenshotNotificationsController;
import com.android.systemui.screenshot.ScreenshotViewProxy;
import com.android.systemui.screenshot.TakeScreenshotService;
import com.android.systemui.screenshot.scroll.ScrollCaptureExecutor;
import com.android.systemui.screenshot.sep.ScreenCaptureHelper;
import com.android.systemui.screenshot.sep.ScreenshotFeedbackController;
import com.android.systemui.screenshot.sep.ScreenshotSelectorView;
import com.android.systemui.screenshot.sep.ScreenshotUtils;
import com.android.systemui.screenshot.sep.ScreenshotViewUtils;
import com.android.systemui.screenshot.sep.SemImageCaptureImpl;
import com.android.systemui.screenshot.sep.SemScreenshotResult;
import com.android.systemui.screenshot.sep.SmartClipDataExtractor;
import com.android.systemui.screenshot.sep.SnackbarController;
import com.android.systemui.screenshot.sep.widget.SemScreenshotLayout;
import com.samsung.systemui.splugins.edgelightingplus.PluginEdgeLightingPlus;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import javax.inject.Provider;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.BuildersKt;

public final class ScreenshotController implements ScreenshotHandler {
    public static boolean mIsAnimationRunning;
    public static boolean mIsSnackBarShowing;
    public static final Object mShutterEffectLock = new Object();
    public final ActionExecutor mActionExecutor;
    public final ActionIntentExecutor mActionIntentExecutor;
    public final ScreenshotActionsController mActionsController;
    public final AnnouncementResolver mAnnouncementResolver;
    public final AssistContentRequester mAssistContentRequester;
    public boolean mAttachRequested;
    public final ExecutorService mBgExecutor;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final BroadcastSender mBroadcastSender;
    public final WindowContext mContext;
    public final AnonymousClass1 mCopyBroadcastReceiver;
    public TakeScreenshotService.RequestCallback mCurrentRequestCallback;
    public boolean mDetachRequested;
    public final Display mDisplay;
    public WindowManager mDisplayContextWindowManager;
    public final ScreenshotFeedbackController mFeedbackController;
    public final FeatureFlags mFlags;
    public final ImageExporter mImageExporter;
    public final Executor mMainExecutor;
    public final MessageContainerController mMessageContainerController;
    public final ScreenshotNotificationsController mNotificationsController;
    public List mNotifiedApps;
    public String mPackageName = "";
    public Presentation mPresentation;
    public SaveImageInBackgroundTask mSaveInBgTask;
    public Bitmap mScreenBitmap;
    public ScreenCaptureHelper mScreenCaptureHelper;
    public final ScreenshotDetectionController mScreenshotDetectionController;
    public final TimeoutHandler mScreenshotHandler;
    public final ScreenshotNotificationSmartActionsProvider mScreenshotNotificationSmartActionsProvider;
    public ScreenshotSelectorView mScreenshotSelectorView;
    public final ScreenshotSmartActions mScreenshotSmartActions;
    public final ScreenshotSoundController mScreenshotSoundController;
    public final ScrollCaptureExecutor mScrollCaptureExecutor;
    public SemScreenshotLayout mSemScreenshotLayout;
    public final SemImageCaptureImpl mSepImageCaptureImpl;
    public final UiEventLogger mUiEventLogger;
    public final UserManager mUserManager;
    public final ScreenshotViewProxy mViewProxy;
    public SmartClipDataExtractor.WebData mWebData;
    public final PhoneWindow mWindow;
    public final WindowManager.LayoutParams mWindowLayoutParams;
    public final WindowManager mWindowManager;

    /* renamed from: com.android.systemui.screenshot.ScreenshotController$2, reason: invalid class name */
    public final class AnonymousClass2 {
        public final /* synthetic */ ScreenshotData val$screenshot;

        public AnonymousClass2(ScreenshotData screenshotData) {
            this.val$screenshot = screenshotData;
        }

        public final void finishAnimation() {
            MessageContainerController messageContainerController = ScreenshotController.this.mMessageContainerController;
            ScreenshotData screenshotData = this.val$screenshot;
            messageContainerController.getClass();
            Flags.FEATURE_FLAGS.getClass();
            BuildersKt.launch$default(messageContainerController.mainScope, null, null, new MessageContainerController$onScreenshotTaken$1(messageContainerController, screenshotData, null), 3);
            synchronized (ScreenshotController.mShutterEffectLock) {
                ScreenshotController.mIsAnimationRunning = false;
                ScreenshotController.this.detachSemScreenshotLayoutToWindow();
            }
        }
    }

    /* renamed from: com.android.systemui.screenshot.ScreenshotController$3, reason: invalid class name */
    public final class AnonymousClass3 {
        public AnonymousClass3() {
        }

        public final void onAction(Intent intent, UserHandle userHandle, boolean z) {
            final ScreenshotController screenshotController = ScreenshotController.this;
            screenshotController.getClass();
            Pair startSharedElementAnimation = ActivityOptions.startSharedElementAnimation(screenshotController.mWindow, new ExitTransitionCoordinator.ExitTransitionCallbacks() { // from class: com.android.systemui.screenshot.ScreenshotController.7
                public final void hideSharedElements() {
                    ScreenshotController.this.finishDismiss();
                }

                public final boolean isReturnTransitionAllowed() {
                    return false;
                }

                public final void onFinish() {
                }
            }, null, new Pair[]{Pair.create(screenshotController.mViewProxy.getScreenshotPreview(), "screenshot_preview_image")});
            screenshotController.mActionIntentExecutor.launchIntentAsync(intent, userHandle, z, (ActivityOptions) startSharedElementAnimation.first, (ExitTransitionCoordinator) startSharedElementAnimation.second);
        }

        public final void onTouchOutside() {
            View peekDecorView;
            ScreenshotController screenshotController = ScreenshotController.this;
            WindowManager.LayoutParams layoutParams = screenshotController.mWindowLayoutParams;
            int i = layoutParams.flags;
            int i2 = i | 8;
            layoutParams.flags = i2;
            if (i2 == i || (peekDecorView = screenshotController.mWindow.peekDecorView()) == null || !peekDecorView.isAttachedToWindow()) {
                return;
            }
            screenshotController.mWindowManager.updateViewLayout(peekDecorView, screenshotController.mWindowLayoutParams);
        }
    }

    public interface Factory {
        ScreenshotController create(Display display, boolean z);
    }

    public final class QuickShareData {
        public Notification.Action quickShareAction;
    }

    public final class SaveImageInBackgroundData {
        public ScreenCaptureHelper captureHelper;
        public int displayId;
        public Consumer finisher;
        public Bitmap image;
        public ScreenshotController$$ExternalSyntheticLambda2 mActionsReadyListener;
        public ScreenshotController$$ExternalSyntheticLambda2 mQuickShareActionsReadyListener;
        public List notifiedApps;
        public UserHandle owner;
        public SmartClipDataExtractor.WebData webData;
    }

    public final class SavedImageData {
        public UserHandle owner;
        public Notification.Action quickShareAction;
        public List smartActions;
        public String subject;
        public Uri uri;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v30, types: [android.content.BroadcastReceiver, com.android.systemui.screenshot.ScreenshotController$1] */
    public ScreenshotController(Context context, WindowManager windowManager, FeatureFlags featureFlags, ScreenshotViewProxy.Factory factory, ScreenshotSmartActions screenshotSmartActions, ScreenshotNotificationsController.Factory factory2, UiEventLogger uiEventLogger, ImageExporter imageExporter, ImageCapture imageCapture, SemImageCaptureImpl semImageCaptureImpl, ScreenshotDetectionController screenshotDetectionController, Executor executor, ScrollCaptureExecutor scrollCaptureExecutor, TimeoutHandler timeoutHandler, BroadcastSender broadcastSender, BroadcastDispatcher broadcastDispatcher, ScreenshotNotificationSmartActionsProvider screenshotNotificationSmartActionsProvider, ScreenshotActionsController.Factory factory3, ActionIntentExecutor actionIntentExecutor, ActionExecutor.Factory factory4, UserManager userManager, AssistContentRequester assistContentRequester, MessageContainerController messageContainerController, Provider provider, AnnouncementResolver announcementResolver, Display display, boolean z) {
        InterestingConfigChanges interestingConfigChanges = new InterestingConfigChanges(-2147474556);
        this.mNotifiedApps = new ArrayList();
        this.mScreenshotSmartActions = screenshotSmartActions;
        this.mNotificationsController = factory2.create(display.getDisplayId());
        this.mUiEventLogger = uiEventLogger;
        this.mImageExporter = imageExporter;
        this.mMainExecutor = executor;
        this.mScrollCaptureExecutor = scrollCaptureExecutor;
        this.mScreenshotNotificationSmartActionsProvider = screenshotNotificationSmartActionsProvider;
        this.mBgExecutor = Executors.newSingleThreadExecutor();
        this.mBroadcastSender = broadcastSender;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mScreenshotHandler = timeoutHandler;
        timeoutHandler.mDefaultTimeout = PluginEdgeLightingPlus.VERSION;
        this.mDisplay = display;
        this.mWindowManager = windowManager;
        WindowContext createWindowContext = context.createDisplayContext(display).createWindowContext(2036, null);
        this.mContext = createWindowContext;
        this.mFlags = featureFlags;
        this.mActionIntentExecutor = actionIntentExecutor;
        this.mUserManager = userManager;
        this.mMessageContainerController = messageContainerController;
        this.mAssistContentRequester = assistContentRequester;
        this.mAnnouncementResolver = announcementResolver;
        ScreenshotViewProxy proxy = factory.getProxy(display.getDisplayId(), createWindowContext);
        this.mViewProxy = proxy;
        timeoutHandler.mOnTimeout = new ScreenshotController$$ExternalSyntheticLambda0(this, 0);
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
        ViewGroup view = proxy.getView();
        messageContainerController.getClass();
        messageContainerController.container = (ViewGroup) view.requireViewById(com.android.systemui.R.id.screenshot_message_container);
        messageContainerController.guideline = (Guideline) view.requireViewById(com.android.systemui.R.id.guideline);
        ViewGroup viewGroup = messageContainerController.container;
        messageContainerController.workProfileFirstRunView = (ViewGroup) (viewGroup == null ? null : viewGroup).requireViewById(com.android.systemui.R.id.work_profile_first_run);
        ViewGroup viewGroup2 = messageContainerController.container;
        messageContainerController.detectionNoticeView = (ViewGroup) (viewGroup2 == null ? null : viewGroup2).requireViewById(com.android.systemui.R.id.screenshot_detection_notice);
        ViewGroup viewGroup3 = messageContainerController.container;
        (viewGroup3 == null ? null : viewGroup3).setVisibility(8);
        Guideline guideline = messageContainerController.guideline;
        (guideline == null ? null : guideline).setGuidelineEnd(0);
        ViewGroup viewGroup4 = messageContainerController.workProfileFirstRunView;
        (viewGroup4 == null ? null : viewGroup4).setVisibility(8);
        ViewGroup viewGroup5 = messageContainerController.detectionNoticeView;
        (viewGroup5 == null ? null : viewGroup5).setVisibility(8);
        proxy.setCallbacks(new AnonymousClass3());
        phoneWindow.setContentView(proxy.getView());
        ActionExecutor create = factory4.create(phoneWindow, proxy, new Function0() { // from class: com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ScreenshotController.this.finishDismiss();
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
        this.mSepImageCaptureImpl = semImageCaptureImpl;
        this.mFeedbackController = new ScreenshotFeedbackController(createWindowContext);
        this.mScreenshotDetectionController = screenshotDetectionController;
        mIsAnimationRunning = false;
        ?? r1 = new BroadcastReceiver() { // from class: com.android.systemui.screenshot.ScreenshotController.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                if ("com.android.systemui.COPY".equals(intent.getAction())) {
                    ScreenshotController.this.mViewProxy.requestDismissal(ScreenshotEvent.SCREENSHOT_DISMISSED_OTHER);
                }
            }
        };
        this.mCopyBroadcastReceiver = r1;
        broadcastDispatcher.registerReceiver(r1, new IntentFilter("com.android.systemui.COPY"), null, null, 4, "com.android.systemui.permission.SELF");
    }

    public final void attachSemScreenshotLayoutToWindow() {
        synchronized (mShutterEffectLock) {
            try {
                Log.i("Screenshot", "attachSemScreenshotLayoutToWindow");
                WindowManager.LayoutParams layoutParams = ScreenshotViewUtils.getLayoutParams(this.mScreenCaptureHelper);
                try {
                    layoutParams.setTitle("ScreenshotAnimation");
                    SemScreenshotLayout semScreenshotLayout = this.mSemScreenshotLayout;
                    float f = this.mScreenCaptureHelper.screenDegrees;
                    ImageView imageView = (ImageView) semScreenshotLayout.findViewById(com.android.systemui.R.id.white_bg);
                    semScreenshotLayout.mScreenshotImageView = imageView;
                    imageView.setVisibility(4);
                    semScreenshotLayout.mScreenDegrees = f;
                    int i = this.mScreenCaptureHelper.builtInDisplayId;
                    if (ScreenshotUtils.isSubDisplayCapture(i)) {
                        this.mSemScreenshotLayout.addCaptureEffectViewInLayout(this.mScreenCaptureHelper);
                        Display display = ScreenshotUtils.getDisplay(i, this.mContext);
                        if (display != null) {
                            Presentation presentation = new Presentation(this.mContext, display, 2132018520, VolteConstants.ErrorCode.REG_SUBSCRIBED);
                            this.mPresentation = presentation;
                            Window window = presentation.getWindow();
                            this.mScreenCaptureHelper.getClass();
                            window.addFlags(69207432);
                            window.getAttributes().layoutInDisplayCutoutMode = 3;
                            View decorView = window.getDecorView();
                            decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() | 1026);
                            window.addContentView(this.mSemScreenshotLayout, layoutParams);
                            window.setBackgroundDrawable(new ColorDrawable(0));
                            this.mPresentation.show();
                        }
                    } else {
                        this.mSemScreenshotLayout.addCaptureEffectViewInLayout(this.mScreenCaptureHelper);
                        WindowManager windowManager = (WindowManager) this.mScreenCaptureHelper.displayContext.getSystemService("window");
                        this.mDisplayContextWindowManager = windowManager;
                        windowManager.addView(this.mSemScreenshotLayout, layoutParams);
                    }
                } catch (IllegalStateException e) {
                    Log.e("Screenshot", "mScreenshotLayout " + this.mSemScreenshotLayout, e);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void detachSemScreenshotLayoutToWindow() {
        Log.i("Screenshot", "detachSemScreenshotLayoutToWindow");
        Presentation presentation = this.mPresentation;
        if (presentation == null) {
            if (this.mSemScreenshotLayout.isAttachedToWindow()) {
                this.mDisplayContextWindowManager.removeViewImmediate(this.mSemScreenshotLayout);
            }
        } else {
            presentation.dismiss();
            this.mPresentation = null;
            if (this.mSemScreenshotLayout.getParent() != null) {
                ((ViewGroup) this.mSemScreenshotLayout.getParent()).removeView(this.mSemScreenshotLayout);
            }
        }
    }

    public final void finishDismiss() {
        Log.d("Screenshot", "finishDismiss");
        this.mActionsController.getClass();
        this.mScrollCaptureExecutor.getClass();
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

    /* JADX WARN: Can't wrap try/catch for region: R(29:123|(2:125|(30:129|130|(1:208)(1:134)|135|136|(3:138|139|140)|143|(4:145|(2:147|(2:149|150))|205|150)(1:206)|151|(1:153)|154|155|156|157|158|160|161|162|163|(1:165)(1:195)|166|167|(2:169|(1:171)(2:172|(1:174)))|175|176|(1:178)(1:191)|179|(1:181)(1:190)|182|(2:184|(2:186|187)(1:188))(1:189)))(1:213)|212|136|(0)|143|(0)(0)|151|(0)|154|155|156|157|158|160|161|162|163|(0)(0)|166|167|(0)|175|176|(0)(0)|179|(0)(0)|182|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:194:0x022f, code lost:
    
        android.util.Log.i(r7, "RemoteException is occurred.");
     */
    /* JADX WARN: Code restructure failed: missing block: B:197:0x0224, code lost:
    
        r1 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:200:0x0226, code lost:
    
        r1 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:201:0x0227, code lost:
    
        r4 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:202:0x0229, code lost:
    
        r3 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:204:0x022b, code lost:
    
        r3 = 0;
        r1 = null;
        r2 = null;
     */
    /* JADX WARN: Removed duplicated region for block: B:138:0x00ea  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x011c  */
    /* JADX WARN: Removed duplicated region for block: B:153:0x018c  */
    /* JADX WARN: Removed duplicated region for block: B:165:0x01d8  */
    /* JADX WARN: Removed duplicated region for block: B:169:0x0206 A[Catch: RemoteException -> 0x022f, TryCatch #1 {RemoteException -> 0x022f, blocks: (B:167:0x01db, B:169:0x0206, B:171:0x0210, B:172:0x0217, B:174:0x021f), top: B:166:0x01db }] */
    /* JADX WARN: Removed duplicated region for block: B:178:0x023c  */
    /* JADX WARN: Removed duplicated region for block: B:181:0x024f  */
    /* JADX WARN: Removed duplicated region for block: B:184:0x0266  */
    /* JADX WARN: Removed duplicated region for block: B:189:0x027a  */
    /* JADX WARN: Removed duplicated region for block: B:190:0x025c  */
    /* JADX WARN: Removed duplicated region for block: B:191:0x0241  */
    /* JADX WARN: Removed duplicated region for block: B:195:0x01da  */
    /* JADX WARN: Removed duplicated region for block: B:206:0x0182  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x03d3  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0454  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x04c7  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x04ef  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0502  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0458  */
    @Override // com.android.systemui.screenshot.ScreenshotHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void handleScreenshot(com.android.systemui.screenshot.ScreenshotData r29, java.util.function.Consumer r30, com.android.systemui.screenshot.TakeScreenshotService.RequestCallback r31) {
        /*
            Method dump skipped, instructions count: 1305
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.screenshot.ScreenshotController.handleScreenshot(com.android.systemui.screenshot.ScreenshotData, java.util.function.Consumer, com.android.systemui.screenshot.TakeScreenshotService$RequestCallback):void");
    }

    public final void logSuccessOnActionsReady(SavedImageData savedImageData) {
        Uri uri = savedImageData.uri;
        UserHandle userHandle = savedImageData.owner;
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

    public final void removeWindow() {
        View peekDecorView = this.mWindow.peekDecorView();
        if (peekDecorView != null && peekDecorView.isAttachedToWindow()) {
            this.mWindowManager.removeViewImmediate(peekDecorView);
            this.mDetachRequested = false;
        }
        if (this.mAttachRequested && !this.mDetachRequested) {
            this.mDetachRequested = true;
            ScreenshotController$$ExternalSyntheticLambda0 screenshotController$$ExternalSyntheticLambda0 = new ScreenshotController$$ExternalSyntheticLambda0(this, 1);
            View decorView = this.mWindow.getDecorView();
            if (decorView.isAttachedToWindow()) {
                screenshotController$$ExternalSyntheticLambda0.run();
            } else {
                decorView.getViewTreeObserver().addOnWindowAttachListener(new AnonymousClass5(decorView, screenshotController$$ExternalSyntheticLambda0));
            }
        }
        this.mViewProxy.stopInputListening();
    }

    public final void showScreenshotErrorMessage(final SemScreenshotResult semScreenshotResult) {
        synchronized (mShutterEffectLock) {
            mIsSnackBarShowing = true;
        }
        attachSemScreenshotLayoutToWindow();
        Presentation presentation = this.mPresentation;
        final View decorView = presentation != null ? presentation.getWindow().getDecorView() : this.mSemScreenshotLayout;
        decorView.post(new Runnable() { // from class: com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                ScreenshotController screenshotController = ScreenshotController.this;
                View view = decorView;
                SemScreenshotResult semScreenshotResult2 = semScreenshotResult;
                screenshotController.getClass();
                synchronized (ScreenshotController.mShutterEffectLock) {
                    new SnackbarController(screenshotController.mContext, screenshotController.mScreenCaptureHelper.capturedDisplayId, new ScreenshotController$$ExternalSyntheticLambda2(screenshotController, 1)).showScreenshotError(view, semScreenshotResult2);
                }
            }
        });
    }

    /* renamed from: com.android.systemui.screenshot.ScreenshotController$5, reason: invalid class name */
    public final class AnonymousClass5 implements ViewTreeObserver.OnWindowAttachListener {
        public final /* synthetic */ Runnable val$action;
        public final /* synthetic */ View val$decorView;

        public AnonymousClass5(View view, Runnable runnable) {
            this.val$decorView = view;
            this.val$action = runnable;
        }

        @Override // android.view.ViewTreeObserver.OnWindowAttachListener
        public final void onWindowAttached() {
            ScreenshotController.this.mAttachRequested = false;
            this.val$decorView.getViewTreeObserver().removeOnWindowAttachListener(this);
            this.val$action.run();
        }

        @Override // android.view.ViewTreeObserver.OnWindowAttachListener
        public final void onWindowDetached() {
        }
    }
}
