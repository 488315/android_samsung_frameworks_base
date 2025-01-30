package com.android.systemui.screenshot;

import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.ExitTransitionCoordinator;
import android.app.Notification;
import android.app.Presentation;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.display.DisplayManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.Trace;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Pair;
import android.view.Display;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.IRemoteAnimationRunner;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.RemoteAnimationTarget;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.window.OnBackInvokedCallback;
import android.window.ScreenCapture;
import android.window.WindowContext;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.policy.PhoneWindow;
import com.android.settingslib.applications.InterestingConfigChanges;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.screenshot.DraggableConstraintLayout;
import com.android.systemui.screenshot.DraggableConstraintLayout.SwipeDismissHandler.C23291;
import com.android.systemui.screenshot.ScreenshotController;
import com.android.systemui.screenshot.ScreenshotController.C23414;
import com.android.systemui.screenshot.ScreenshotController.ScreenshotExitTransitionCallbacksSupplier;
import com.android.systemui.screenshot.ScreenshotController.ScreenshotExitTransitionCallbacksSupplier.C23451;
import com.android.systemui.screenshot.TakeScreenshotService;
import com.android.systemui.screenshot.WorkProfileMessageController;
import com.android.systemui.screenshot.sep.AliveShotImageUtils;
import com.android.systemui.screenshot.sep.EdmUtils;
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
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.util.Assert;
import com.samsung.android.app.SemMultiWindowManager;
import com.samsung.android.content.smartclip.SemRemoteAppDataExtractionManager;
import com.samsung.android.content.smartclip.SemSmartClipDataRepository;
import com.samsung.android.content.smartclip.SemSmartClipMetaTag;
import com.samsung.android.content.smartclip.SemSmartClipMetaTagArray;
import com.samsung.android.content.smartclip.SpenGestureManager;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.profile.ProfilePolicy;
import com.samsung.android.view.ScreenshotResult;
import com.samsung.systemui.splugins.edgelightingplus.PluginEdgeLightingPlus;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Supplier;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ScreenshotController {
    public static boolean isAnimationRunning;
    public static boolean isSnackBarShowing;
    public final ExecutorService mBgExecutor;
    public final BroadcastSender mBroadcastSender;
    public final CallbackToFutureAdapter.SafeFuture mCameraSound;
    public final WindowContext mContext;
    public final C23402 mCopyBroadcastReceiver;
    public TakeScreenshotService.RequestCallback mCurrentRequestCallback;
    public WindowManager mDisplayContextWindowManager;
    public final DisplayManager mDisplayManager;
    public final DisplayTracker mDisplayTracker;
    public final ScreenshotFeedbackController mFeedbackController;
    public final FeatureFlags mFlags;
    public final ImageExporter mImageExporter;
    public final Executor mMainExecutor;
    public final MessageContainerController mMessageContainerController;
    public final ScreenshotNotificationsController mNotificationsController;
    public List mNotifiedApps;
    public final ScreenshotController$$ExternalSyntheticLambda0 mOnBackInvokedCallback = new OnBackInvokedCallback() { // from class: com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda0
        @Override // android.window.OnBackInvokedCallback
        public final void onBackInvoked() {
            ScreenshotController screenshotController = ScreenshotController.this;
            screenshotController.getClass();
            screenshotController.dismissScreenshot(ScreenshotEvent.SCREENSHOT_DISMISSED_OTHER);
        }
    };
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
    public ScreenshotView mScreenshotView;
    public SemScreenshotLayout mSemScreenshotLayout;
    public final SemImageCaptureImpl mSepImageCaptureImpl;
    public final UiEventLogger mUiEventLogger;
    public final UserManager mUserManager;
    public SmartClipDataExtractor.WebData mWebData;
    public final PhoneWindow mWindow;
    public final WindowManager.LayoutParams mWindowLayoutParams;
    public final WindowManager mWindowManager;
    public static final C23361 SCREENSHOT_REMOTE_RUNNER = new IRemoteAnimationRunner.Stub() { // from class: com.android.systemui.screenshot.ScreenshotController.1
        public final void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
            try {
                iRemoteAnimationFinishedCallback.onAnimationFinished();
            } catch (RemoteException e) {
                C23361 c23361 = ScreenshotController.SCREENSHOT_REMOTE_RUNNER;
                Log.e("Screenshot", "Error finishing screenshot remote animation", e);
            }
        }

        public final void onAnimationCancelled() {
        }
    };
    public static final Object mShutterEffectLock = new Object();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.screenshot.ScreenshotController$4 */
    public final class C23414 {
        public final /* synthetic */ ScreenshotData val$screenshot;

        public C23414(ScreenshotData screenshotData) {
            this.val$screenshot = screenshotData;
        }

        /* JADX WARN: Removed duplicated region for block: B:12:0x0095  */
        /* JADX WARN: Removed duplicated region for block: B:38:0x011a A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:47:0x0114  */
        /* JADX WARN: Removed duplicated region for block: B:55:0x0085  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void finishAnimation() {
            WorkProfileMessageController.WorkProfileFirstRunData workProfileFirstRunData;
            Drawable drawable;
            CharSequence charSequence;
            final MessageContainerController messageContainerController = ScreenshotController.this.mMessageContainerController;
            ScreenshotData screenshotData = this.val$screenshot;
            messageContainerController.getClass();
            UserHandle userHandle = screenshotData.userHandle;
            final WorkProfileMessageController workProfileMessageController = messageContainerController.workProfileMessageController;
            PackageManager packageManager = workProfileMessageController.packageManager;
            if (userHandle == null || !workProfileMessageController.userManager.isManagedProfile(userHandle.getIdentifier()) || workProfileMessageController.context.getSharedPreferences("com.android.systemui.screenshot", 0).getBoolean("work_profile_first_run", false)) {
                workProfileFirstRunData = null;
            } else {
                ComponentName unflattenFromString = ComponentName.unflattenFromString(workProfileMessageController.context.getString(R.string.config_sceenshotWorkProfileFilesApp));
                if (unflattenFromString == null) {
                    workProfileFirstRunData = new WorkProfileMessageController.WorkProfileFirstRunData(workProfileMessageController.context.getString(R.string.screenshot_default_files_app_name), null);
                } else {
                    try {
                        ActivityInfo activityInfo = packageManager.getActivityInfo(unflattenFromString, PackageManager.ComponentInfoFlags.of(0L));
                        drawable = packageManager.getUserBadgedIcon(packageManager.getActivityIcon(unflattenFromString), userHandle);
                        try {
                            charSequence = activityInfo.loadLabel(packageManager);
                        } catch (PackageManager.NameNotFoundException unused) {
                            Log.w("WorkProfileMessageCtrl", "Component " + unflattenFromString + " not found");
                            charSequence = null;
                            if (charSequence == null) {
                            }
                            workProfileFirstRunData = new WorkProfileMessageController.WorkProfileFirstRunData(charSequence, drawable);
                            EmptyList emptyList = EmptyList.INSTANCE;
                            if (workProfileFirstRunData == null) {
                            }
                            synchronized (ScreenshotController.mShutterEffectLock) {
                            }
                        }
                    } catch (PackageManager.NameNotFoundException unused2) {
                        drawable = null;
                    }
                    if (charSequence == null) {
                        charSequence = workProfileMessageController.context.getString(R.string.screenshot_default_files_app_name);
                    }
                    workProfileFirstRunData = new WorkProfileMessageController.WorkProfileFirstRunData(charSequence, drawable);
                }
            }
            EmptyList emptyList2 = EmptyList.INSTANCE;
            if (workProfileFirstRunData == null) {
                ViewGroup viewGroup = messageContainerController.workProfileFirstRunView;
                if (viewGroup == null) {
                    viewGroup = null;
                }
                viewGroup.setVisibility(0);
                ViewGroup viewGroup2 = messageContainerController.detectionNoticeView;
                if (viewGroup2 == null) {
                    viewGroup2 = null;
                }
                viewGroup2.setVisibility(8);
                ViewGroup viewGroup3 = messageContainerController.workProfileFirstRunView;
                if (viewGroup3 == null) {
                    viewGroup3 = null;
                }
                final MessageContainerController$onScreenshotTaken$2 messageContainerController$onScreenshotTaken$2 = new MessageContainerController$onScreenshotTaken$2(messageContainerController);
                Drawable drawable2 = workProfileFirstRunData.icon;
                if (drawable2 != null) {
                    ((ImageView) viewGroup3.requireViewById(R.id.screenshot_message_icon)).setImageDrawable(drawable2);
                }
                ((TextView) viewGroup3.requireViewById(R.id.screenshot_message_content)).setText(viewGroup3.getContext().getString(R.string.screenshot_work_profile_notification, workProfileFirstRunData.appName));
                viewGroup3.requireViewById(R.id.message_dismiss_button).setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.screenshot.WorkProfileMessageController$populateView$1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        Function0.this.invoke();
                        WorkProfileMessageController workProfileMessageController2 = workProfileMessageController;
                        int i = WorkProfileMessageController.$r8$clinit;
                        SharedPreferences.Editor edit = workProfileMessageController2.context.getSharedPreferences("com.android.systemui.screenshot", 0).edit();
                        edit.putBoolean("work_profile_first_run", true);
                        edit.apply();
                    }
                });
                ViewGroup viewGroup4 = messageContainerController.container;
                if (viewGroup4 == null) {
                    viewGroup4 = null;
                }
                if (viewGroup4.getVisibility() != 0) {
                    ViewGroup viewGroup5 = messageContainerController.container;
                    if (viewGroup5 == null) {
                        viewGroup5 = null;
                    }
                    viewGroup5.setVisibility(0);
                    ViewGroup viewGroup6 = messageContainerController.container;
                    (viewGroup6 != null ? viewGroup6 : null).getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: com.android.systemui.screenshot.MessageContainerController$animateInMessageContainer$1
                        @Override // android.view.ViewTreeObserver.OnPreDrawListener
                        public final boolean onPreDraw() {
                            ViewGroup viewGroup7 = MessageContainerController.this.container;
                            if (viewGroup7 == null) {
                                viewGroup7 = null;
                            }
                            viewGroup7.getViewTreeObserver().removeOnPreDrawListener(this);
                            MessageContainerController.this.getAnimator(true).start();
                            return false;
                        }
                    });
                }
            } else {
                emptyList2.getClass();
            }
            synchronized (ScreenshotController.mShutterEffectLock) {
                ScreenshotController.isAnimationRunning = false;
                ScreenshotController.this.detachSemScreenshotLayoutToWindow();
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.screenshot.ScreenshotController$6 */
    public final class C23436 {
        public C23436() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface ActionsReadyListener {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class QuickShareData {
        public Notification.Action quickShareAction;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SaveImageInBackgroundData {
        public ScreenCaptureHelper captureHelper;
        public Consumer finisher;
        public Bitmap image;
        public ActionsReadyListener mActionsReadyListener;
        public ScreenshotController$$ExternalSyntheticLambda2 mQuickShareActionsReadyListener;
        public List notifiedApps;
        public UserHandle owner;
        public SmartClipDataExtractor.WebData webData;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SavedImageData {
        public Supplier editTransition;
        public UserHandle owner;
        public Notification.Action quickShareAction;
        public Supplier shareTransition;
        public List smartActions;
        public String subject;
        public Uri uri;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class ActionTransition {
            public Bundle bundle;
        }
    }

    /* renamed from: -$$Nest$mfinishDismiss, reason: not valid java name */
    public static void m1637$$Nest$mfinishDismiss(ScreenshotController screenshotController) {
        screenshotController.getClass();
        Log.d("Screenshot", "finishDismiss");
        TakeScreenshotService.RequestCallback requestCallback = screenshotController.mCurrentRequestCallback;
        if (requestCallback != null) {
            Messenger messenger = ((TakeScreenshotService.RequestCallbackImpl) requestCallback).mReplyTo;
            boolean z = TakeScreenshotService.sConfigured;
            try {
                messenger.send(Message.obtain((Handler) null, 2));
            } catch (RemoteException e) {
                Log.d("Screenshot", "ignored remote exception", e);
            }
            screenshotController.mCurrentRequestCallback = null;
        }
        screenshotController.mScreenshotView.reset();
        screenshotController.removeWindow();
        screenshotController.mScreenshotHandler.removeMessages(2);
    }

    /* JADX WARN: Type inference failed for: r1v35, types: [android.content.BroadcastReceiver, com.android.systemui.screenshot.ScreenshotController$2] */
    /* JADX WARN: Type inference failed for: r6v0, types: [com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda0] */
    public ScreenshotController(Context context, FeatureFlags featureFlags, ScreenshotSmartActions screenshotSmartActions, ScreenshotNotificationsController screenshotNotificationsController, ScrollCaptureClient scrollCaptureClient, UiEventLogger uiEventLogger, ImageExporter imageExporter, ImageCapture imageCapture, SemImageCaptureImpl semImageCaptureImpl, ScreenshotDetectionController screenshotDetectionController, Executor executor, ScrollCaptureController scrollCaptureController, LongScreenshotData longScreenshotData, ActivityManager activityManager, TimeoutHandler timeoutHandler, BroadcastSender broadcastSender, ScreenshotNotificationSmartActionsProvider screenshotNotificationSmartActionsProvider, ActionIntentExecutor actionIntentExecutor, UserManager userManager, AssistContentRequester assistContentRequester, MessageContainerController messageContainerController, DisplayTracker displayTracker) {
        InterestingConfigChanges interestingConfigChanges = new InterestingConfigChanges(-2147474556);
        this.mNotifiedApps = new ArrayList();
        this.mScreenshotSmartActions = screenshotSmartActions;
        this.mNotificationsController = screenshotNotificationsController;
        this.mUiEventLogger = uiEventLogger;
        this.mImageExporter = imageExporter;
        this.mMainExecutor = executor;
        activityManager.isLowRamDevice();
        this.mScreenshotNotificationSmartActionsProvider = screenshotNotificationSmartActionsProvider;
        this.mBgExecutor = Executors.newSingleThreadExecutor();
        this.mBroadcastSender = broadcastSender;
        this.mScreenshotHandler = timeoutHandler;
        timeoutHandler.mDefaultTimeout = PluginEdgeLightingPlus.VERSION;
        timeoutHandler.mOnTimeout = new Runnable() { // from class: com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                ScreenshotController screenshotController = ScreenshotController.this;
                screenshotController.getClass();
                screenshotController.dismissScreenshot(ScreenshotEvent.SCREENSHOT_INTERACTION_TIMEOUT);
            }
        };
        DisplayManager displayManager = (DisplayManager) context.getSystemService(DisplayManager.class);
        Objects.requireNonNull(displayManager);
        this.mDisplayManager = displayManager;
        this.mDisplayTracker = displayTracker;
        displayTracker.getClass();
        WindowContext createWindowContext = context.createDisplayContext(displayManager.getDisplay(0)).createWindowContext(2036, null);
        this.mContext = createWindowContext;
        WindowManager windowManager = (WindowManager) createWindowContext.getSystemService(WindowManager.class);
        this.mWindowManager = windowManager;
        this.mFlags = featureFlags;
        this.mUserManager = userManager;
        this.mMessageContainerController = messageContainerController;
        AccessibilityManager.getInstance(createWindowContext);
        WindowManager.LayoutParams floatingWindowParams = FloatingWindowUtil.getFloatingWindowParams();
        this.mWindowLayoutParams = floatingWindowParams;
        floatingWindowParams.setTitle("ScreenshotAnimation");
        PhoneWindow phoneWindow = new PhoneWindow(createWindowContext);
        phoneWindow.requestFeature(1);
        phoneWindow.requestFeature(13);
        phoneWindow.setBackgroundDrawableResource(android.R.color.transparent);
        this.mWindow = phoneWindow;
        phoneWindow.setWindowManager(windowManager, (IBinder) null, (String) null);
        interestingConfigChanges.applyNewConfig(context.getResources());
        ScreenshotView screenshotView = (ScreenshotView) LayoutInflater.from(createWindowContext).inflate(R.layout.screenshot, (ViewGroup) null);
        this.mScreenshotView = screenshotView;
        messageContainerController.getClass();
        messageContainerController.container = (ViewGroup) screenshotView.requireViewById(R.id.screenshot_message_container);
        messageContainerController.guideline = (Guideline) screenshotView.requireViewById(R.id.guideline);
        ViewGroup viewGroup = messageContainerController.container;
        messageContainerController.workProfileFirstRunView = (ViewGroup) (viewGroup == null ? null : viewGroup).requireViewById(R.id.work_profile_first_run);
        ViewGroup viewGroup2 = messageContainerController.container;
        messageContainerController.detectionNoticeView = (ViewGroup) (viewGroup2 == null ? null : viewGroup2).requireViewById(R.id.screenshot_detection_notice);
        ViewGroup viewGroup3 = messageContainerController.container;
        (viewGroup3 == null ? null : viewGroup3).setVisibility(8);
        Guideline guideline = messageContainerController.guideline;
        guideline = guideline == null ? null : guideline;
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) guideline.getLayoutParams();
        if (!guideline.mFilterRedundantCalls || layoutParams.guideEnd != 0) {
            layoutParams.guideEnd = 0;
            guideline.setLayoutParams(layoutParams);
        }
        ViewGroup viewGroup4 = messageContainerController.workProfileFirstRunView;
        (viewGroup4 == null ? null : viewGroup4).setVisibility(8);
        ViewGroup viewGroup5 = messageContainerController.detectionNoticeView;
        (viewGroup5 != null ? viewGroup5 : null).setVisibility(8);
        this.mScreenshotView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.systemui.screenshot.ScreenshotController.5
            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewAttachedToWindow(View view) {
                ScreenshotController.this.mScreenshotView.findOnBackInvokedDispatcher().registerOnBackInvokedCallback(0, ScreenshotController.this.mOnBackInvokedCallback);
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewDetachedFromWindow(View view) {
                ScreenshotController.this.mScreenshotView.findOnBackInvokedDispatcher().unregisterOnBackInvokedCallback(ScreenshotController.this.mOnBackInvokedCallback);
            }
        });
        ScreenshotView screenshotView2 = this.mScreenshotView;
        C23436 c23436 = new C23436();
        screenshotView2.mUiEventLogger = uiEventLogger;
        screenshotView2.mCallbacks = c23436;
        screenshotView2.mActionExecutor = actionIntentExecutor;
        screenshotView2.mFlags = featureFlags;
        ScreenshotView screenshotView3 = this.mScreenshotView;
        displayTracker.getClass();
        screenshotView3.mDefaultDisplay = 0;
        this.mScreenshotView.getClass();
        this.mScreenshotView.setOnKeyListener(new View.OnKeyListener() { // from class: com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda5
            @Override // android.view.View.OnKeyListener
            public final boolean onKey(View view, int i, KeyEvent keyEvent) {
                ScreenshotController screenshotController = ScreenshotController.this;
                screenshotController.getClass();
                if (i != 4 && i != 111) {
                    return false;
                }
                screenshotController.dismissScreenshot(ScreenshotEvent.SCREENSHOT_DISMISSED_OTHER);
                return true;
            }
        });
        this.mScreenshotView.getViewTreeObserver().addOnComputeInternalInsetsListener(this.mScreenshotView);
        phoneWindow.setContentView(this.mScreenshotView);
        this.mCameraSound = CallbackToFutureAdapter.getFuture(new ScreenshotController$$ExternalSyntheticLambda2(this, 3));
        isAnimationRunning = false;
        this.mSepImageCaptureImpl = semImageCaptureImpl;
        this.mScreenshotDetectionController = screenshotDetectionController;
        this.mFeedbackController = new ScreenshotFeedbackController(createWindowContext);
        ?? r1 = new BroadcastReceiver() { // from class: com.android.systemui.screenshot.ScreenshotController.2
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                if ("com.android.systemui.COPY".equals(intent.getAction())) {
                    ScreenshotController.this.dismissScreenshot(ScreenshotEvent.SCREENSHOT_DISMISSED_OTHER);
                }
            }
        };
        this.mCopyBroadcastReceiver = r1;
        createWindowContext.registerReceiver((BroadcastReceiver) r1, new IntentFilter("com.android.systemui.COPY"), "com.android.systemui.permission.SELF", (Handler) null, 4);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0044 A[Catch: IllegalStateException -> 0x00b9, all -> 0x00cf, TryCatch #0 {IllegalStateException -> 0x00b9, blocks: (B:6:0x0012, B:12:0x0044, B:14:0x0053, B:16:0x0057, B:23:0x009d), top: B:5:0x0012, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x009d A[Catch: IllegalStateException -> 0x00b9, all -> 0x00cf, TRY_LEAVE, TryCatch #0 {IllegalStateException -> 0x00b9, blocks: (B:6:0x0012, B:12:0x0044, B:14:0x0053, B:16:0x0057, B:23:0x009d), top: B:5:0x0012, outer: #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void attachSemScreenshotLayoutToWindow() {
        int i;
        boolean z;
        synchronized (mShutterEffectLock) {
            Log.i("Screenshot", "attachSemScreenshotLayoutToWindow");
            WindowManager.LayoutParams layoutParams = ScreenshotViewUtils.getLayoutParams(this.mScreenCaptureHelper);
            try {
                layoutParams.setTitle("ScreenshotAnimation");
                SemScreenshotLayout semScreenshotLayout = this.mSemScreenshotLayout;
                float f = this.mScreenCaptureHelper.screenDegrees;
                ImageView imageView = (ImageView) semScreenshotLayout.findViewById(R.id.white_bg);
                semScreenshotLayout.mScreenshotImageView = imageView;
                imageView.setVisibility(4);
                semScreenshotLayout.mScreenDegrees = f;
                i = this.mScreenCaptureHelper.builtInDisplayId;
            } catch (IllegalStateException e) {
                Log.e("Screenshot", "mScreenshotLayout " + this.mSemScreenshotLayout, e);
            }
            if (ScreenshotUtils.VALUE_SUB_DISPLAY_POLICY.contains("WATCHFACE")) {
                z = true;
                if (i == 1) {
                    if (z) {
                        this.mSemScreenshotLayout.addCaptureEffectViewInLayout(this.mScreenCaptureHelper);
                        WindowManager windowManager = (WindowManager) this.mScreenCaptureHelper.displayContext.getSystemService("window");
                        this.mDisplayContextWindowManager = windowManager;
                        windowManager.addView(this.mSemScreenshotLayout, layoutParams);
                    } else {
                        this.mSemScreenshotLayout.addCaptureEffectViewInLayout(this.mScreenCaptureHelper);
                        Display display = ScreenshotUtils.getDisplay(i, this.mContext);
                        if (display != null && this.mPresentation == null) {
                            Presentation presentation = new Presentation(this.mContext, display, 2132018368, VolteConstants.ErrorCode.REG_SUBSCRIBED);
                            this.mPresentation = presentation;
                            Window window = presentation.getWindow();
                            this.mScreenCaptureHelper.getClass();
                            window.addFlags(69207432);
                            window.getAttributes().layoutInDisplayCutoutMode = 3;
                            View decorView = window.getDecorView();
                            decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() | 1024 | 2);
                            window.addContentView(this.mSemScreenshotLayout, layoutParams);
                            window.setBackgroundDrawable(new ColorDrawable(0));
                            this.mPresentation.show();
                        }
                    }
                }
            }
            z = false;
            if (z) {
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

    public final void dismissScreenshot(ScreenshotEvent screenshotEvent) {
        ValueAnimator valueAnimator = this.mScreenshotView.mScreenshotStatic.mSwipeDismissHandler.mDismissAnimation;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            return;
        }
        this.mUiEventLogger.log(screenshotEvent, 0, this.mPackageName);
        this.mScreenshotHandler.removeMessages(2);
        DraggableConstraintLayout.SwipeDismissHandler swipeDismissHandler = this.mScreenshotView.mScreenshotStatic.mSwipeDismissHandler;
        ValueAnimator createSwipeDismissAnimation = swipeDismissHandler.createSwipeDismissAnimation(FloatingWindowUtil.dpToPx(swipeDismissHandler.mDisplayMetrics, 1.0f));
        swipeDismissHandler.mDismissAnimation = createSwipeDismissAnimation;
        createSwipeDismissAnimation.addListener(swipeDismissHandler.new C23291());
        swipeDismissHandler.mDismissAnimation.start();
    }

    /* JADX WARN: Can't wrap try/catch for region: R(35:188|(2:190|(36:194|195|(1:300)(1:199)|200|201|(3:203|204|205)|208|(1:210)(1:298)|(1:212)(1:297)|213|(1:215)|216|(1:218)(1:296)|219|(1:221)(1:295)|222|223|224|225|226|227|228|230|231|(1:233)(1:285)|234|235|(2:237|(1:239)(2:240|(2:242|(1:244)(1:245))))|246|247|(2:249|(2:251|(1:253)(1:254)))|(1:256)(1:281)|257|(1:259)|260|(2:262|(8:264|265|266|267|268|269|270|272)(1:279))(1:280)))(1:305)|304|201|(0)|208|(0)(0)|(0)(0)|213|(0)|216|(0)(0)|219|(0)(0)|222|223|224|225|226|227|228|230|231|(0)(0)|234|235|(0)|246|247|(0)|(0)(0)|257|(0)|260|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:284:0x01ff, code lost:
    
        android.util.Log.i(r4, "RemoteException is occurred.");
     */
    /* JADX WARN: Code restructure failed: missing block: B:287:0x01f9, code lost:
    
        r1 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:289:0x01fd, code lost:
    
        r1 = null;
        r13 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:292:0x01fc, code lost:
    
        r9 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:294:0x01fb, code lost:
    
        r3 = null;
     */
    /* JADX WARN: Removed duplicated region for block: B:105:0x041d  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x0478  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x04b4  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x04d1  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x050d  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x0528 A[Catch: SecurityException -> 0x0530, TRY_LEAVE, TryCatch #13 {SecurityException -> 0x0530, blocks: (B:128:0x051b, B:130:0x0528), top: B:127:0x051b }] */
    /* JADX WARN: Removed duplicated region for block: B:136:0x0571  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x05a0  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x05b3  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x04b8  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x047b  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x0420  */
    /* JADX WARN: Removed duplicated region for block: B:159:0x02be  */
    /* JADX WARN: Removed duplicated region for block: B:203:0x00f4  */
    /* JADX WARN: Removed duplicated region for block: B:210:0x0120  */
    /* JADX WARN: Removed duplicated region for block: B:212:0x0125  */
    /* JADX WARN: Removed duplicated region for block: B:215:0x012f  */
    /* JADX WARN: Removed duplicated region for block: B:218:0x0139  */
    /* JADX WARN: Removed duplicated region for block: B:221:0x0143  */
    /* JADX WARN: Removed duplicated region for block: B:233:0x0190  */
    /* JADX WARN: Removed duplicated region for block: B:237:0x01be A[Catch: RemoteException -> 0x01ff, TryCatch #4 {RemoteException -> 0x01ff, blocks: (B:235:0x0193, B:237:0x01be, B:239:0x01c8, B:240:0x01cf, B:242:0x01d5, B:244:0x01f2), top: B:234:0x0193 }] */
    /* JADX WARN: Removed duplicated region for block: B:249:0x020b  */
    /* JADX WARN: Removed duplicated region for block: B:256:0x0232  */
    /* JADX WARN: Removed duplicated region for block: B:259:0x0243  */
    /* JADX WARN: Removed duplicated region for block: B:262:0x0251  */
    /* JADX WARN: Removed duplicated region for block: B:280:0x0286  */
    /* JADX WARN: Removed duplicated region for block: B:281:0x0237  */
    /* JADX WARN: Removed duplicated region for block: B:285:0x0192  */
    /* JADX WARN: Removed duplicated region for block: B:295:0x0147  */
    /* JADX WARN: Removed duplicated region for block: B:296:0x013d  */
    /* JADX WARN: Removed duplicated region for block: B:297:0x0129  */
    /* JADX WARN: Removed duplicated region for block: B:298:0x0122  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x02b8  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0313  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x0400  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0406  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void handleScreenshot(final ScreenshotData screenshotData, TakeScreenshotService.RequestCallback requestCallback, Consumer consumer) {
        int i;
        int i2;
        Context context;
        int i3;
        boolean z;
        int i4;
        Bitmap bitmap;
        String str;
        Cursor query;
        Context context2;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        List list;
        final View decorView;
        UserHandle userHandle;
        SaveImageInBackgroundTask saveImageInBackgroundTask;
        String str2;
        boolean z6;
        String str3;
        boolean z7;
        final ScreenshotController screenshotController = this;
        Context context3 = screenshotController.mContext;
        Assert.isMainThread();
        screenshotController.mCurrentRequestCallback = requestCallback;
        TooltipPopup$$ExternalSyntheticOutline0.m13m(new StringBuilder("handleScreenshot: screenshot.getType()="), screenshotData.type, "Screenshot");
        int i5 = screenshotData.type;
        if (i5 == 3 && (screenshotData.disableCapture || screenshotData.secureLayer)) {
            Log.e("Screenshot", "handleScreenshot: disable screenshot on managed profile., disableCapture=" + screenshotData.disableCapture + ", secureLayer=" + screenshotData.secureLayer);
            screenshotController.showScreenshotErrorMessage(new SemScreenshotResult(null, screenshotData.disableCapture ? 64 : 32, null, null));
            TakeScreenshotService.RequestCallback requestCallback2 = screenshotController.mCurrentRequestCallback;
            if (requestCallback2 != null) {
                Messenger messenger = ((TakeScreenshotService.RequestCallbackImpl) requestCallback2).mReplyTo;
                boolean z8 = TakeScreenshotService.sConfigured;
                try {
                    messenger.send(Message.obtain(null, 1, null));
                } catch (RemoteException e) {
                    Log.d("Screenshot", "ignored remote exception", e);
                }
                try {
                    messenger.send(Message.obtain((Handler) null, 2));
                    return;
                } catch (RemoteException e2) {
                    Log.d("Screenshot", "ignored remote exception", e2);
                    return;
                }
            }
            return;
        }
        boolean z9 = false;
        if (i5 == 1 || i5 == 2 || i5 == 100 || i5 == 101) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            DisplayTracker displayTracker = screenshotController.mDisplayTracker;
            displayTracker.getClass();
            screenshotController.mDisplayManager.getDisplay(0).getRealMetrics(displayMetrics);
            Rect rect = new Rect(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
            displayTracker.getClass();
            ScreenCaptureHelper screenCaptureHelper = screenshotController.mScreenCaptureHelper;
            SemImageCaptureImpl semImageCaptureImpl = screenshotController.mSepImageCaptureImpl;
            Context context4 = semImageCaptureImpl.context;
            if (context4 == null) {
                Uri uri = AliveShotImageUtils.HANDLER_TRANSPARENCY_CONTENT_URI;
            } else if (AliveShotImageUtils.isEdgePanelPresent(context4) && (query = context4.getContentResolver().query(AliveShotImageUtils.HANDLER_TRANSPARENCY_CONTENT_URI, null, null, null, null)) != null) {
                try {
                    i = (query.getCount() == 0 || !query.moveToFirst()) ? 0 : query.getInt(0);
                    if (AliveShotImageUtils.isEdgePanelPresent(context4)) {
                        Log.i("Screenshot", "Hide edge panel");
                        AliveShotImageUtils.resetEdgeTransparency(100, context4);
                        try {
                            Thread.sleep(100L);
                        } catch (InterruptedException unused) {
                            Log.e("Screenshot", "InterruptedException occurred");
                        }
                    }
                    int i6 = screenCaptureHelper.builtInDisplayId;
                    i2 = screenCaptureHelper.screenCaptureType;
                    String str4 = ScreenshotUtils.VALUE_SUB_DISPLAY_POLICY;
                    context = context3;
                    if (Settings.System.getIntForUser(context4.getContentResolver(), "any_screen_running", 0, -2) != 1) {
                        i3 = 2015;
                    } else {
                        i3 = 2600;
                        z9 = true;
                    }
                    if (i2 == 100) {
                        i3 = 1;
                    }
                    int i7 = i3;
                    Rect screenshotRectToCapture = screenCaptureHelper.getScreenshotRectToCapture();
                    z = semImageCaptureImpl.useIdentityTransform;
                    int i8 = !z ? (int) screenCaptureHelper.screenNativeWidth : screenCaptureHelper.screenWidth;
                    int i9 = !z ? (int) screenCaptureHelper.screenNativeHeight : screenCaptureHelper.screenHeight;
                    StringBuilder m45m = GridLayoutManager$$ExternalSyntheticOutline0.m45m("semCaptureDisplay: takeScreenshot param, builtInDisplayId=", i6, ", targetWindowType=", i7, ", containTargetWindow=");
                    m45m.append(z9);
                    m45m.append(", rectToCapture=");
                    m45m.append(screenshotRectToCapture);
                    String sb = m45m.toString();
                    String str5 = semImageCaptureImpl.TAG;
                    Log.i(str5, sb);
                    ScreenshotResult takeScreenshotToTargetWindowFromCapture = semImageCaptureImpl.windowManager.takeScreenshotToTargetWindowFromCapture(i6, i7, z9, screenshotRectToCapture, i8, i9, semImageCaptureImpl.useIdentityTransform, false, true);
                    Bitmap bitmap2 = takeScreenshotToTargetWindowFromCapture.getCapturedBitmap();
                    i4 = takeScreenshotToTargetWindowFromCapture.getFailedReason();
                    String str6 = takeScreenshotToTargetWindowFromCapture.getSecuredWindowName();
                    String str7 = takeScreenshotToTargetWindowFromCapture.getTargetWindowName();
                    Log.i(str5, "semCaptureDisplay: takeScreenshot result, bitmapIsNull=" + (bitmap2 != null) + ", failedReason=" + i4 + ", resultSecuredWindowName=" + str6 + ", resultTargetWindowName=" + str7);
                    if (i4 == 64) {
                        if (semImageCaptureImpl.devicePolicyManager.getScreenCaptureDisabled(null, -1)) {
                            Log.i(str5, "semCaptureDisplay: screenshot disabled by dpm.");
                        } else if (EdmUtils.isScreenCaptureEnabled(context4)) {
                            ScreenCapture.CaptureArgs build = new ScreenCapture.CaptureArgs.Builder().setSourceCrop(rect).build();
                            ScreenCapture.SynchronousScreenCaptureListener createSyncCaptureListener = ScreenCapture.createSyncCaptureListener();
                            ((ImageCaptureImpl) semImageCaptureImpl).windowManager.captureDisplay(0, build, createSyncCaptureListener);
                            ScreenCapture.ScreenshotHardwareBuffer buffer = createSyncCaptureListener.getBuffer();
                            bitmap2 = buffer != null ? buffer.asBitmap() : null;
                        }
                    }
                    String str8 = str6;
                    Bitmap bitmap3 = bitmap2;
                    if (z) {
                        float f = screenCaptureHelper.screenDegrees;
                        if (f > 0.0f) {
                            Matrix matrix = new Matrix();
                            matrix.preRotate(f);
                            bitmap3 = bitmap3 != null ? Bitmap.createBitmap(bitmap3, 0, 0, bitmap3.getWidth(), bitmap3.getHeight(), matrix, false) : null;
                        }
                    }
                    SemScreenshotResult semScreenshotResult = new SemScreenshotResult(bitmap3 != null ? screenCaptureHelper.onPostScreenshot(bitmap3) : null, i4, str7, str8);
                    if (AliveShotImageUtils.isEdgePanelPresent(context4)) {
                        Log.i("Screenshot", "Show edge panel");
                        AliveShotImageUtils.resetEdgeTransparency(i, context4);
                    }
                    bitmap = semScreenshotResult.bitmap;
                    screenshotData.bitmap = bitmap;
                    if (bitmap == null) {
                        Log.e("Screenshot", "handleScreenshot: Screenshot bitmap was null");
                        showScreenshotErrorMessage(semScreenshotResult);
                        TakeScreenshotService.RequestCallback requestCallback3 = this.mCurrentRequestCallback;
                        if (requestCallback3 != null) {
                            Messenger messenger2 = ((TakeScreenshotService.RequestCallbackImpl) requestCallback3).mReplyTo;
                            boolean z10 = TakeScreenshotService.sConfigured;
                            try {
                                messenger2.send(Message.obtain(null, 1, null));
                                str = "ignored remote exception";
                            } catch (RemoteException e3) {
                                str = "ignored remote exception";
                                Log.d("Screenshot", str, e3);
                            }
                            try {
                                messenger2.send(Message.obtain((Handler) null, 2));
                                return;
                            } catch (RemoteException e4) {
                                Log.d("Screenshot", str, e4);
                                return;
                            }
                        }
                        return;
                    }
                    screenshotController = this;
                    screenshotData.screenBounds = rect;
                } finally {
                    query.close();
                }
            }
            i = 0;
            if (AliveShotImageUtils.isEdgePanelPresent(context4)) {
            }
            int i62 = screenCaptureHelper.builtInDisplayId;
            i2 = screenCaptureHelper.screenCaptureType;
            String str42 = ScreenshotUtils.VALUE_SUB_DISPLAY_POLICY;
            context = context3;
            if (Settings.System.getIntForUser(context4.getContentResolver(), "any_screen_running", 0, -2) != 1) {
            }
            if (i2 == 100) {
            }
            int i72 = i3;
            Rect screenshotRectToCapture2 = screenCaptureHelper.getScreenshotRectToCapture();
            z = semImageCaptureImpl.useIdentityTransform;
            int i82 = !z ? (int) screenCaptureHelper.screenNativeWidth : screenCaptureHelper.screenWidth;
            int i92 = !z ? (int) screenCaptureHelper.screenNativeHeight : screenCaptureHelper.screenHeight;
            StringBuilder m45m2 = GridLayoutManager$$ExternalSyntheticOutline0.m45m("semCaptureDisplay: takeScreenshot param, builtInDisplayId=", i62, ", targetWindowType=", i72, ", containTargetWindow=");
            m45m2.append(z9);
            m45m2.append(", rectToCapture=");
            m45m2.append(screenshotRectToCapture2);
            String sb2 = m45m2.toString();
            String str52 = semImageCaptureImpl.TAG;
            Log.i(str52, sb2);
            ScreenshotResult takeScreenshotToTargetWindowFromCapture2 = semImageCaptureImpl.windowManager.takeScreenshotToTargetWindowFromCapture(i62, i72, z9, screenshotRectToCapture2, i82, i92, semImageCaptureImpl.useIdentityTransform, false, true);
            Bitmap bitmap22 = takeScreenshotToTargetWindowFromCapture2.getCapturedBitmap();
            i4 = takeScreenshotToTargetWindowFromCapture2.getFailedReason();
            String str62 = takeScreenshotToTargetWindowFromCapture2.getSecuredWindowName();
            String str72 = takeScreenshotToTargetWindowFromCapture2.getTargetWindowName();
            if (bitmap22 != null) {
            }
            Log.i(str52, "semCaptureDisplay: takeScreenshot result, bitmapIsNull=" + (bitmap22 != null) + ", failedReason=" + i4 + ", resultSecuredWindowName=" + str62 + ", resultTargetWindowName=" + str72);
            if (i4 == 64) {
            }
            String str82 = str62;
            Bitmap bitmap32 = bitmap22;
            if (z) {
            }
            SemScreenshotResult semScreenshotResult2 = new SemScreenshotResult(bitmap32 != null ? screenCaptureHelper.onPostScreenshot(bitmap32) : null, i4, str72, str82);
            if (AliveShotImageUtils.isEdgePanelPresent(context4)) {
            }
            bitmap = semScreenshotResult2.bitmap;
            screenshotData.bitmap = bitmap;
            if (bitmap == null) {
            }
        } else {
            context = context3;
        }
        Trace.beginSection("ScreenshotController_getSmartClipWebData");
        screenshotController.mWebData = null;
        try {
            context2 = context;
        } catch (Exception e5) {
            e = e5;
            context2 = context;
        }
        try {
            new SemRemoteAppDataExtractionManager(context2);
            z2 = true;
        } catch (Exception e6) {
            e = e6;
            Log.e("Screenshot", "isSupportSmartClip, exxception occurred : " + e.toString());
            z2 = false;
            if (z2) {
            }
            z5 = true;
            z4 = false;
            if (z4 == z5) {
            }
            Trace.endSection();
            ScreenshotDetectionController screenshotDetectionController = screenshotController.mScreenshotDetectionController;
            screenshotDetectionController.getClass();
            if (screenshotData.source != 3) {
            }
            screenshotController.mNotifiedApps = list;
            screenshotController.mScreenBitmap = screenshotData.bitmap;
            String str9 = screenshotController.mPackageName;
            ComponentName componentName = screenshotData.topComponent;
            screenshotController.mPackageName = componentName != null ? "" : componentName.getPackageName();
            screenshotController.mBroadcastSender.sendBroadcast$1(new Intent("com.android.systemui.SCREENSHOT"));
            int i10 = context2.getResources().getConfiguration().orientation;
            screenshotController.mScreenBitmap.setHasAlpha(false);
            screenshotController.mScreenBitmap.prepareToDraw();
            final int i11 = 1;
            final Runnable runnable = new Runnable(screenshotController) { // from class: com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda3
                public final /* synthetic */ ScreenshotController f$0;

                {
                    this.f$0 = screenshotController;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    switch (i11) {
                        case 0:
                            ScreenshotController screenshotController2 = this.f$0;
                            ScreenshotData screenshotData2 = screenshotData;
                            screenshotController2.getClass();
                            synchronized (ScreenshotController.mShutterEffectLock) {
                                screenshotController2.mSemScreenshotLayout.mCallback = screenshotController2.new C23414(screenshotData2);
                                screenshotController2.mFeedbackController.semPlayCameraSound();
                                screenshotController2.mSemScreenshotLayout.startAnimation(screenshotController2.mScreenBitmap.getWidth(), screenshotController2.mScreenBitmap.getHeight());
                                ScreenshotController.isAnimationRunning = true;
                            }
                            return;
                        default:
                            ScreenshotController screenshotController3 = this.f$0;
                            ScreenshotData screenshotData3 = screenshotData;
                            screenshotController3.getClass();
                            boolean isManagedProfile = screenshotController3.mUserManager.isManagedProfile(screenshotData3.userHandle.getIdentifier());
                            WindowContext windowContext = screenshotController3.mContext;
                            if (isManagedProfile) {
                                screenshotController3.mScreenshotView.announceForAccessibility(windowContext.getResources().getString(R.string.screenshot_saving_work_profile_title));
                                return;
                            } else {
                                screenshotController3.mScreenshotView.announceForAccessibility(windowContext.getResources().getString(R.string.screenshot_saving_title));
                                return;
                            }
                    }
                }
            };
            PhoneWindow phoneWindow = screenshotController.mWindow;
            decorView = phoneWindow.getDecorView();
            if (decorView.isAttachedToWindow()) {
            }
            screenshotController.mScreenshotView.reset();
            if (screenshotController.mScreenshotView.isAttachedToWindow()) {
            }
            ScreenshotView screenshotView = screenshotController.mScreenshotView;
            screenshotView.mPackageName = screenshotController.mPackageName;
            screenshotView.updateOrientation(screenshotController.mWindowManager.getCurrentWindowMetrics().getWindowInsets());
            userHandle = screenshotData.userHandle;
            if (SemPersonaManager.isKnoxId(userHandle.getIdentifier())) {
            }
            ScreenshotController$$ExternalSyntheticLambda2 screenshotController$$ExternalSyntheticLambda2 = new ScreenshotController$$ExternalSyntheticLambda2(screenshotController, 1);
            ScreenshotController$$ExternalSyntheticLambda2 screenshotController$$ExternalSyntheticLambda22 = new ScreenshotController$$ExternalSyntheticLambda2(screenshotController, 2);
            SaveImageInBackgroundData saveImageInBackgroundData = new SaveImageInBackgroundData();
            saveImageInBackgroundData.image = screenshotController.mScreenBitmap;
            saveImageInBackgroundData.finisher = consumer;
            saveImageInBackgroundData.mActionsReadyListener = screenshotController$$ExternalSyntheticLambda2;
            saveImageInBackgroundData.mQuickShareActionsReadyListener = screenshotController$$ExternalSyntheticLambda22;
            saveImageInBackgroundData.owner = userHandle;
            saveImageInBackgroundData.captureHelper = screenshotController.mScreenCaptureHelper;
            saveImageInBackgroundData.webData = screenshotController.mWebData;
            saveImageInBackgroundData.notifiedApps = screenshotController.mNotifiedApps;
            saveImageInBackgroundTask = screenshotController.mSaveInBgTask;
            if (saveImageInBackgroundTask != null) {
            }
            SaveImageInBackgroundTask saveImageInBackgroundTask2 = new SaveImageInBackgroundTask(screenshotController.mContext, screenshotController.mFlags, screenshotController.mImageExporter, screenshotController.mScreenshotSmartActions, saveImageInBackgroundData, new Supplier() { // from class: com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda8
                @Override // java.util.function.Supplier
                public final Object get() {
                    ScreenshotController screenshotController2 = ScreenshotController.this;
                    Pair startSharedElementAnimation = ActivityOptions.startSharedElementAnimation(screenshotController2.mWindow, screenshotController2.new ScreenshotExitTransitionCallbacksSupplier(true).new C23451(), null, new Pair[]{Pair.create(screenshotController2.mScreenshotView.mScreenshotPreview, "screenshot_preview_image")});
                    ((ExitTransitionCoordinator) startSharedElementAnimation.second).startExit();
                    ScreenshotController.SavedImageData.ActionTransition actionTransition = new ScreenshotController.SavedImageData.ActionTransition();
                    actionTransition.bundle = ((ActivityOptions) startSharedElementAnimation.first).toBundle();
                    return actionTransition;
                }
            }, screenshotController.mScreenshotNotificationSmartActionsProvider);
            screenshotController.mSaveInBgTask = saveImageInBackgroundTask2;
            final int i12 = 0;
            saveImageInBackgroundTask2.execute(new Void[0]);
            if (screenshotController.mScreenCaptureHelper.isShowScreenshotAnimation()) {
            }
            phoneWindow.getDecorView().setOnApplyWindowInsetsListener(new ScreenshotController$$ExternalSyntheticLambda4());
            screenshotController.mScreenshotHandler.removeMessages(2);
        }
        if (z2) {
            Log.e("Screenshot", "canExtractWebData : SmartClip is not supported");
        } else {
            if (!(context2.getResources().getConfiguration().semDesktopModeEnabled == 1)) {
                int mode = new SemMultiWindowManager().getMode();
                ListPopupWindow$$ExternalSyntheticOutline0.m10m("isMultiWindowStyleAppExist : mode = ", mode, "Screenshot");
                if (mode == 0) {
                    z3 = false;
                } else {
                    if ((mode & 1) != 0) {
                        Log.i("Screenshot", "isMultiWindowStyleAppExist : MODE_FREEFORM");
                    }
                    if ((mode & 4) != 0) {
                        Log.i("Screenshot", "isMultiWindowStyleAppExist : MODE_PICTURE_IN_PICTURE");
                    }
                    if ((mode & 2) != 0) {
                        Log.i("Screenshot", "isMultiWindowStyleAppExist : MODE_SPLIT_SCREEN");
                    }
                    z3 = true;
                }
                z4 = true;
                if (!z3) {
                    z5 = true;
                    if (z4 == z5) {
                    }
                    Trace.endSection();
                    ScreenshotDetectionController screenshotDetectionController2 = screenshotController.mScreenshotDetectionController;
                    screenshotDetectionController2.getClass();
                    if (screenshotData.source != 3) {
                    }
                    screenshotController.mNotifiedApps = list;
                    screenshotController.mScreenBitmap = screenshotData.bitmap;
                    String str92 = screenshotController.mPackageName;
                    ComponentName componentName2 = screenshotData.topComponent;
                    screenshotController.mPackageName = componentName2 != null ? "" : componentName2.getPackageName();
                    screenshotController.mBroadcastSender.sendBroadcast$1(new Intent("com.android.systemui.SCREENSHOT"));
                    int i102 = context2.getResources().getConfiguration().orientation;
                    screenshotController.mScreenBitmap.setHasAlpha(false);
                    screenshotController.mScreenBitmap.prepareToDraw();
                    final int i112 = 1;
                    final Runnable runnable2 = new Runnable(screenshotController) { // from class: com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda3
                        public final /* synthetic */ ScreenshotController f$0;

                        {
                            this.f$0 = screenshotController;
                        }

                        @Override // java.lang.Runnable
                        public final void run() {
                            switch (i112) {
                                case 0:
                                    ScreenshotController screenshotController2 = this.f$0;
                                    ScreenshotData screenshotData2 = screenshotData;
                                    screenshotController2.getClass();
                                    synchronized (ScreenshotController.mShutterEffectLock) {
                                        screenshotController2.mSemScreenshotLayout.mCallback = screenshotController2.new C23414(screenshotData2);
                                        screenshotController2.mFeedbackController.semPlayCameraSound();
                                        screenshotController2.mSemScreenshotLayout.startAnimation(screenshotController2.mScreenBitmap.getWidth(), screenshotController2.mScreenBitmap.getHeight());
                                        ScreenshotController.isAnimationRunning = true;
                                    }
                                    return;
                                default:
                                    ScreenshotController screenshotController3 = this.f$0;
                                    ScreenshotData screenshotData3 = screenshotData;
                                    screenshotController3.getClass();
                                    boolean isManagedProfile = screenshotController3.mUserManager.isManagedProfile(screenshotData3.userHandle.getIdentifier());
                                    WindowContext windowContext = screenshotController3.mContext;
                                    if (isManagedProfile) {
                                        screenshotController3.mScreenshotView.announceForAccessibility(windowContext.getResources().getString(R.string.screenshot_saving_work_profile_title));
                                        return;
                                    } else {
                                        screenshotController3.mScreenshotView.announceForAccessibility(windowContext.getResources().getString(R.string.screenshot_saving_title));
                                        return;
                                    }
                            }
                        }
                    };
                    PhoneWindow phoneWindow2 = screenshotController.mWindow;
                    decorView = phoneWindow2.getDecorView();
                    if (decorView.isAttachedToWindow()) {
                    }
                    screenshotController.mScreenshotView.reset();
                    if (screenshotController.mScreenshotView.isAttachedToWindow()) {
                    }
                    ScreenshotView screenshotView2 = screenshotController.mScreenshotView;
                    screenshotView2.mPackageName = screenshotController.mPackageName;
                    screenshotView2.updateOrientation(screenshotController.mWindowManager.getCurrentWindowMetrics().getWindowInsets());
                    userHandle = screenshotData.userHandle;
                    if (SemPersonaManager.isKnoxId(userHandle.getIdentifier())) {
                        if (EnterpriseDeviceManager.getInstance(context2).getProfilePolicy().getRestriction(ProfilePolicy.RESTRICTION_PROPERTY_SCREENCAPTURE_SAVE_TO_OWNER)) {
                        }
                    }
                    ScreenshotController$$ExternalSyntheticLambda2 screenshotController$$ExternalSyntheticLambda23 = new ScreenshotController$$ExternalSyntheticLambda2(screenshotController, 1);
                    ScreenshotController$$ExternalSyntheticLambda2 screenshotController$$ExternalSyntheticLambda222 = new ScreenshotController$$ExternalSyntheticLambda2(screenshotController, 2);
                    SaveImageInBackgroundData saveImageInBackgroundData2 = new SaveImageInBackgroundData();
                    saveImageInBackgroundData2.image = screenshotController.mScreenBitmap;
                    saveImageInBackgroundData2.finisher = consumer;
                    saveImageInBackgroundData2.mActionsReadyListener = screenshotController$$ExternalSyntheticLambda23;
                    saveImageInBackgroundData2.mQuickShareActionsReadyListener = screenshotController$$ExternalSyntheticLambda222;
                    saveImageInBackgroundData2.owner = userHandle;
                    saveImageInBackgroundData2.captureHelper = screenshotController.mScreenCaptureHelper;
                    saveImageInBackgroundData2.webData = screenshotController.mWebData;
                    saveImageInBackgroundData2.notifiedApps = screenshotController.mNotifiedApps;
                    saveImageInBackgroundTask = screenshotController.mSaveInBgTask;
                    if (saveImageInBackgroundTask != null) {
                    }
                    SaveImageInBackgroundTask saveImageInBackgroundTask22 = new SaveImageInBackgroundTask(screenshotController.mContext, screenshotController.mFlags, screenshotController.mImageExporter, screenshotController.mScreenshotSmartActions, saveImageInBackgroundData2, new Supplier() { // from class: com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda8
                        @Override // java.util.function.Supplier
                        public final Object get() {
                            ScreenshotController screenshotController2 = ScreenshotController.this;
                            Pair startSharedElementAnimation = ActivityOptions.startSharedElementAnimation(screenshotController2.mWindow, screenshotController2.new ScreenshotExitTransitionCallbacksSupplier(true).new C23451(), null, new Pair[]{Pair.create(screenshotController2.mScreenshotView.mScreenshotPreview, "screenshot_preview_image")});
                            ((ExitTransitionCoordinator) startSharedElementAnimation.second).startExit();
                            ScreenshotController.SavedImageData.ActionTransition actionTransition = new ScreenshotController.SavedImageData.ActionTransition();
                            actionTransition.bundle = ((ActivityOptions) startSharedElementAnimation.first).toBundle();
                            return actionTransition;
                        }
                    }, screenshotController.mScreenshotNotificationSmartActionsProvider);
                    screenshotController.mSaveInBgTask = saveImageInBackgroundTask22;
                    final int i122 = 0;
                    saveImageInBackgroundTask22.execute(new Void[0]);
                    if (screenshotController.mScreenCaptureHelper.isShowScreenshotAnimation()) {
                    }
                    phoneWindow2.getDecorView().setOnApplyWindowInsetsListener(new ScreenshotController$$ExternalSyntheticLambda4());
                    screenshotController.mScreenshotHandler.removeMessages(2);
                }
                Log.e("Screenshot", "canExtractWebData : MultiWindow style app exists");
                z5 = true;
                z4 = false;
                if (z4 == z5) {
                    ScreenCaptureHelper screenCaptureHelper2 = screenshotController.mScreenCaptureHelper;
                    int i13 = screenCaptureHelper2.screenWidth / 2;
                    int i14 = screenCaptureHelper2.screenHeight / 2;
                    SmartClipDataExtractor.WebData webData = null;
                    SemSmartClipDataRepository smartClipDataByScreenRect = ((SpenGestureManager) context2.getSystemService("spengestureservice")).getSmartClipDataByScreenRect(new Rect(i13, i14, i13 + 1, i14 + 1), (IBinder) null, 1, 1);
                    if (smartClipDataByScreenRect == null) {
                        Log.e("Screenshot", "getWebData : Failed to extract the SmartClip data");
                    } else {
                        Rect contentRect = smartClipDataByScreenRect.getContentRect();
                        if (contentRect != null) {
                            Log.d("Screenshot", "getWebData : content Rect w=" + contentRect.width() + ", h=" + contentRect.height() + " " + contentRect);
                        }
                        SemSmartClipMetaTagArray metaTag = smartClipDataByScreenRect.getMetaTag("url");
                        if (metaTag.size() > 0) {
                            Iterator it = metaTag.iterator();
                            while (true) {
                                if (!it.hasNext()) {
                                    str2 = null;
                                    break;
                                }
                                str2 = ((SemSmartClipMetaTag) it.next()).getValue();
                                String lowerCase = str2.toLowerCase();
                                if (lowerCase.startsWith("http://") || lowerCase.startsWith("https://")) {
                                    break;
                                }
                            }
                            SmartClipDataExtractor.WebData webData2 = new SmartClipDataExtractor.WebData(str2, smartClipDataByScreenRect.getAppPackageName());
                            String str10 = webData2.mUrl;
                            if (str10 == null || (str3 = webData2.mAppPkgName) == null) {
                                Log.e("Screenshot", "isValidWebData : url or appPkgName is null");
                            } else {
                                String lowerCase2 = str10.toLowerCase();
                                if (lowerCase2.startsWith("http://") || lowerCase2.startsWith("https://")) {
                                    String[] strArr = SmartClipDataExtractor.mWhiteWebAppList;
                                    int i15 = 0;
                                    while (true) {
                                        if (i15 >= 3) {
                                            z7 = false;
                                            break;
                                        } else {
                                            if (strArr[i15].equals(str3)) {
                                                z7 = true;
                                                break;
                                            }
                                            i15++;
                                        }
                                    }
                                    if (z7) {
                                        z6 = true;
                                        if (z6) {
                                            Log.e("Screenshot", "getWebData : Invalid web data");
                                        } else {
                                            webData = webData2;
                                        }
                                    } else {
                                        Log.e("Screenshot", "isValidWebData : Not white app");
                                    }
                                } else {
                                    Log.e("Screenshot", "isValidWebData : Not valid url");
                                }
                            }
                            z6 = false;
                            if (z6) {
                            }
                        }
                    }
                    screenshotController.mWebData = webData;
                    if (webData != null) {
                        Log.i("Screenshot", "handleScreenshot: mWebData is extracted.");
                    }
                }
                Trace.endSection();
                ScreenshotDetectionController screenshotDetectionController22 = screenshotController.mScreenshotDetectionController;
                screenshotDetectionController22.getClass();
                if (screenshotData.source != 3) {
                    list = EmptyList.INSTANCE;
                } else {
                    List<ComponentName> notifyScreenshotListeners = screenshotDetectionController22.windowManager.notifyScreenshotListeners(0);
                    ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(notifyScreenshotListeners, 10));
                    for (ComponentName componentName3 : notifyScreenshotListeners) {
                        PackageManager.ComponentInfoFlags of = PackageManager.ComponentInfoFlags.of(512L);
                        PackageManager packageManager = screenshotDetectionController22.packageManager;
                        CharSequence loadLabel = packageManager.getActivityInfo(componentName3, of).loadLabel(packageManager);
                        if (loadLabel.length() == 0) {
                            loadLabel = packageManager.getActivityInfo(componentName3, PackageManager.ComponentInfoFlags.of(512L)).packageName;
                        }
                        arrayList.add(loadLabel);
                    }
                    list = arrayList;
                }
                screenshotController.mNotifiedApps = list;
                screenshotController.mScreenBitmap = screenshotData.bitmap;
                String str922 = screenshotController.mPackageName;
                ComponentName componentName22 = screenshotData.topComponent;
                screenshotController.mPackageName = componentName22 != null ? "" : componentName22.getPackageName();
                screenshotController.mBroadcastSender.sendBroadcast$1(new Intent("com.android.systemui.SCREENSHOT"));
                int i1022 = context2.getResources().getConfiguration().orientation;
                screenshotController.mScreenBitmap.setHasAlpha(false);
                screenshotController.mScreenBitmap.prepareToDraw();
                final int i1122 = 1;
                final Runnable runnable22 = new Runnable(screenshotController) { // from class: com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda3
                    public final /* synthetic */ ScreenshotController f$0;

                    {
                        this.f$0 = screenshotController;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (i1122) {
                            case 0:
                                ScreenshotController screenshotController2 = this.f$0;
                                ScreenshotData screenshotData2 = screenshotData;
                                screenshotController2.getClass();
                                synchronized (ScreenshotController.mShutterEffectLock) {
                                    screenshotController2.mSemScreenshotLayout.mCallback = screenshotController2.new C23414(screenshotData2);
                                    screenshotController2.mFeedbackController.semPlayCameraSound();
                                    screenshotController2.mSemScreenshotLayout.startAnimation(screenshotController2.mScreenBitmap.getWidth(), screenshotController2.mScreenBitmap.getHeight());
                                    ScreenshotController.isAnimationRunning = true;
                                }
                                return;
                            default:
                                ScreenshotController screenshotController3 = this.f$0;
                                ScreenshotData screenshotData3 = screenshotData;
                                screenshotController3.getClass();
                                boolean isManagedProfile = screenshotController3.mUserManager.isManagedProfile(screenshotData3.userHandle.getIdentifier());
                                WindowContext windowContext = screenshotController3.mContext;
                                if (isManagedProfile) {
                                    screenshotController3.mScreenshotView.announceForAccessibility(windowContext.getResources().getString(R.string.screenshot_saving_work_profile_title));
                                    return;
                                } else {
                                    screenshotController3.mScreenshotView.announceForAccessibility(windowContext.getResources().getString(R.string.screenshot_saving_title));
                                    return;
                                }
                        }
                    }
                };
                PhoneWindow phoneWindow22 = screenshotController.mWindow;
                decorView = phoneWindow22.getDecorView();
                if (decorView.isAttachedToWindow()) {
                    decorView.getViewTreeObserver().addOnWindowAttachListener(new ViewTreeObserver.OnWindowAttachListener() { // from class: com.android.systemui.screenshot.ScreenshotController.9
                        @Override // android.view.ViewTreeObserver.OnWindowAttachListener
                        public final void onWindowAttached() {
                            ScreenshotController screenshotController2 = ScreenshotController.this;
                            C23361 c23361 = ScreenshotController.SCREENSHOT_REMOTE_RUNNER;
                            screenshotController2.getClass();
                            decorView.getViewTreeObserver().removeOnWindowAttachListener(this);
                            runnable22.run();
                        }

                        @Override // android.view.ViewTreeObserver.OnWindowAttachListener
                        public final void onWindowDetached() {
                        }
                    });
                } else {
                    runnable22.run();
                }
                screenshotController.mScreenshotView.reset();
                if (screenshotController.mScreenshotView.isAttachedToWindow()) {
                    ValueAnimator valueAnimator = screenshotController.mScreenshotView.mScreenshotStatic.mSwipeDismissHandler.mDismissAnimation;
                    if (!(valueAnimator != null && valueAnimator.isRunning())) {
                        screenshotController.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_REENTERED, 0, str922);
                    }
                }
                ScreenshotView screenshotView22 = screenshotController.mScreenshotView;
                screenshotView22.mPackageName = screenshotController.mPackageName;
                screenshotView22.updateOrientation(screenshotController.mWindowManager.getCurrentWindowMetrics().getWindowInsets());
                userHandle = screenshotData.userHandle;
                if (SemPersonaManager.isKnoxId(userHandle.getIdentifier()) && !SemPersonaManager.isSecureFolderId(userHandle.getIdentifier())) {
                    try {
                        if (EnterpriseDeviceManager.getInstance(context2).getProfilePolicy().getRestriction(ProfilePolicy.RESTRICTION_PROPERTY_SCREENCAPTURE_SAVE_TO_OWNER)) {
                            Log.d("Screenshot", "Save to owner because of the security policy!");
                            userHandle = UserHandle.OWNER;
                        }
                    } catch (SecurityException e7) {
                        Log.e("Screenshot", "Exception: " + e7);
                    }
                }
                ScreenshotController$$ExternalSyntheticLambda2 screenshotController$$ExternalSyntheticLambda232 = new ScreenshotController$$ExternalSyntheticLambda2(screenshotController, 1);
                ScreenshotController$$ExternalSyntheticLambda2 screenshotController$$ExternalSyntheticLambda2222 = new ScreenshotController$$ExternalSyntheticLambda2(screenshotController, 2);
                SaveImageInBackgroundData saveImageInBackgroundData22 = new SaveImageInBackgroundData();
                saveImageInBackgroundData22.image = screenshotController.mScreenBitmap;
                saveImageInBackgroundData22.finisher = consumer;
                saveImageInBackgroundData22.mActionsReadyListener = screenshotController$$ExternalSyntheticLambda232;
                saveImageInBackgroundData22.mQuickShareActionsReadyListener = screenshotController$$ExternalSyntheticLambda2222;
                saveImageInBackgroundData22.owner = userHandle;
                saveImageInBackgroundData22.captureHelper = screenshotController.mScreenCaptureHelper;
                saveImageInBackgroundData22.webData = screenshotController.mWebData;
                saveImageInBackgroundData22.notifiedApps = screenshotController.mNotifiedApps;
                saveImageInBackgroundTask = screenshotController.mSaveInBgTask;
                if (saveImageInBackgroundTask != null) {
                    saveImageInBackgroundTask.mParams.mActionsReadyListener = new ScreenshotController$$ExternalSyntheticLambda2(screenshotController, 4);
                }
                SaveImageInBackgroundTask saveImageInBackgroundTask222 = new SaveImageInBackgroundTask(screenshotController.mContext, screenshotController.mFlags, screenshotController.mImageExporter, screenshotController.mScreenshotSmartActions, saveImageInBackgroundData22, new Supplier() { // from class: com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda8
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        ScreenshotController screenshotController2 = ScreenshotController.this;
                        Pair startSharedElementAnimation = ActivityOptions.startSharedElementAnimation(screenshotController2.mWindow, screenshotController2.new ScreenshotExitTransitionCallbacksSupplier(true).new C23451(), null, new Pair[]{Pair.create(screenshotController2.mScreenshotView.mScreenshotPreview, "screenshot_preview_image")});
                        ((ExitTransitionCoordinator) startSharedElementAnimation.second).startExit();
                        ScreenshotController.SavedImageData.ActionTransition actionTransition = new ScreenshotController.SavedImageData.ActionTransition();
                        actionTransition.bundle = ((ActivityOptions) startSharedElementAnimation.first).toBundle();
                        return actionTransition;
                    }
                }, screenshotController.mScreenshotNotificationSmartActionsProvider);
                screenshotController.mSaveInBgTask = saveImageInBackgroundTask222;
                final int i1222 = 0;
                saveImageInBackgroundTask222.execute(new Void[0]);
                if (screenshotController.mScreenCaptureHelper.isShowScreenshotAnimation()) {
                    screenshotController.mFeedbackController.semPlayCameraSound();
                } else {
                    attachSemScreenshotLayoutToWindow();
                    screenshotController.mSemScreenshotLayout.requestFocus();
                    screenshotController.mSemScreenshotLayout.post(new Runnable(screenshotController) { // from class: com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda3
                        public final /* synthetic */ ScreenshotController f$0;

                        {
                            this.f$0 = screenshotController;
                        }

                        @Override // java.lang.Runnable
                        public final void run() {
                            switch (i1222) {
                                case 0:
                                    ScreenshotController screenshotController2 = this.f$0;
                                    ScreenshotData screenshotData2 = screenshotData;
                                    screenshotController2.getClass();
                                    synchronized (ScreenshotController.mShutterEffectLock) {
                                        screenshotController2.mSemScreenshotLayout.mCallback = screenshotController2.new C23414(screenshotData2);
                                        screenshotController2.mFeedbackController.semPlayCameraSound();
                                        screenshotController2.mSemScreenshotLayout.startAnimation(screenshotController2.mScreenBitmap.getWidth(), screenshotController2.mScreenBitmap.getHeight());
                                        ScreenshotController.isAnimationRunning = true;
                                    }
                                    return;
                                default:
                                    ScreenshotController screenshotController3 = this.f$0;
                                    ScreenshotData screenshotData3 = screenshotData;
                                    screenshotController3.getClass();
                                    boolean isManagedProfile = screenshotController3.mUserManager.isManagedProfile(screenshotData3.userHandle.getIdentifier());
                                    WindowContext windowContext = screenshotController3.mContext;
                                    if (isManagedProfile) {
                                        screenshotController3.mScreenshotView.announceForAccessibility(windowContext.getResources().getString(R.string.screenshot_saving_work_profile_title));
                                        return;
                                    } else {
                                        screenshotController3.mScreenshotView.announceForAccessibility(windowContext.getResources().getString(R.string.screenshot_saving_title));
                                        return;
                                    }
                            }
                        }
                    });
                }
                phoneWindow22.getDecorView().setOnApplyWindowInsetsListener(new ScreenshotController$$ExternalSyntheticLambda4());
                screenshotController.mScreenshotHandler.removeMessages(2);
            }
            Log.e("Screenshot", "canExtractWebData : Desktop mode enabled");
        }
        z5 = true;
        z4 = false;
        if (z4 == z5) {
        }
        Trace.endSection();
        ScreenshotDetectionController screenshotDetectionController222 = screenshotController.mScreenshotDetectionController;
        screenshotDetectionController222.getClass();
        if (screenshotData.source != 3) {
        }
        screenshotController.mNotifiedApps = list;
        screenshotController.mScreenBitmap = screenshotData.bitmap;
        String str9222 = screenshotController.mPackageName;
        ComponentName componentName222 = screenshotData.topComponent;
        screenshotController.mPackageName = componentName222 != null ? "" : componentName222.getPackageName();
        screenshotController.mBroadcastSender.sendBroadcast$1(new Intent("com.android.systemui.SCREENSHOT"));
        int i10222 = context2.getResources().getConfiguration().orientation;
        screenshotController.mScreenBitmap.setHasAlpha(false);
        screenshotController.mScreenBitmap.prepareToDraw();
        final int i11222 = 1;
        final Runnable runnable222 = new Runnable(screenshotController) { // from class: com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda3
            public final /* synthetic */ ScreenshotController f$0;

            {
                this.f$0 = screenshotController;
            }

            @Override // java.lang.Runnable
            public final void run() {
                switch (i11222) {
                    case 0:
                        ScreenshotController screenshotController2 = this.f$0;
                        ScreenshotData screenshotData2 = screenshotData;
                        screenshotController2.getClass();
                        synchronized (ScreenshotController.mShutterEffectLock) {
                            screenshotController2.mSemScreenshotLayout.mCallback = screenshotController2.new C23414(screenshotData2);
                            screenshotController2.mFeedbackController.semPlayCameraSound();
                            screenshotController2.mSemScreenshotLayout.startAnimation(screenshotController2.mScreenBitmap.getWidth(), screenshotController2.mScreenBitmap.getHeight());
                            ScreenshotController.isAnimationRunning = true;
                        }
                        return;
                    default:
                        ScreenshotController screenshotController3 = this.f$0;
                        ScreenshotData screenshotData3 = screenshotData;
                        screenshotController3.getClass();
                        boolean isManagedProfile = screenshotController3.mUserManager.isManagedProfile(screenshotData3.userHandle.getIdentifier());
                        WindowContext windowContext = screenshotController3.mContext;
                        if (isManagedProfile) {
                            screenshotController3.mScreenshotView.announceForAccessibility(windowContext.getResources().getString(R.string.screenshot_saving_work_profile_title));
                            return;
                        } else {
                            screenshotController3.mScreenshotView.announceForAccessibility(windowContext.getResources().getString(R.string.screenshot_saving_title));
                            return;
                        }
                }
            }
        };
        PhoneWindow phoneWindow222 = screenshotController.mWindow;
        decorView = phoneWindow222.getDecorView();
        if (decorView.isAttachedToWindow()) {
        }
        screenshotController.mScreenshotView.reset();
        if (screenshotController.mScreenshotView.isAttachedToWindow()) {
        }
        ScreenshotView screenshotView222 = screenshotController.mScreenshotView;
        screenshotView222.mPackageName = screenshotController.mPackageName;
        screenshotView222.updateOrientation(screenshotController.mWindowManager.getCurrentWindowMetrics().getWindowInsets());
        userHandle = screenshotData.userHandle;
        if (SemPersonaManager.isKnoxId(userHandle.getIdentifier())) {
        }
        ScreenshotController$$ExternalSyntheticLambda2 screenshotController$$ExternalSyntheticLambda2322 = new ScreenshotController$$ExternalSyntheticLambda2(screenshotController, 1);
        ScreenshotController$$ExternalSyntheticLambda2 screenshotController$$ExternalSyntheticLambda22222 = new ScreenshotController$$ExternalSyntheticLambda2(screenshotController, 2);
        SaveImageInBackgroundData saveImageInBackgroundData222 = new SaveImageInBackgroundData();
        saveImageInBackgroundData222.image = screenshotController.mScreenBitmap;
        saveImageInBackgroundData222.finisher = consumer;
        saveImageInBackgroundData222.mActionsReadyListener = screenshotController$$ExternalSyntheticLambda2322;
        saveImageInBackgroundData222.mQuickShareActionsReadyListener = screenshotController$$ExternalSyntheticLambda22222;
        saveImageInBackgroundData222.owner = userHandle;
        saveImageInBackgroundData222.captureHelper = screenshotController.mScreenCaptureHelper;
        saveImageInBackgroundData222.webData = screenshotController.mWebData;
        saveImageInBackgroundData222.notifiedApps = screenshotController.mNotifiedApps;
        saveImageInBackgroundTask = screenshotController.mSaveInBgTask;
        if (saveImageInBackgroundTask != null) {
        }
        SaveImageInBackgroundTask saveImageInBackgroundTask2222 = new SaveImageInBackgroundTask(screenshotController.mContext, screenshotController.mFlags, screenshotController.mImageExporter, screenshotController.mScreenshotSmartActions, saveImageInBackgroundData222, new Supplier() { // from class: com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda8
            @Override // java.util.function.Supplier
            public final Object get() {
                ScreenshotController screenshotController2 = ScreenshotController.this;
                Pair startSharedElementAnimation = ActivityOptions.startSharedElementAnimation(screenshotController2.mWindow, screenshotController2.new ScreenshotExitTransitionCallbacksSupplier(true).new C23451(), null, new Pair[]{Pair.create(screenshotController2.mScreenshotView.mScreenshotPreview, "screenshot_preview_image")});
                ((ExitTransitionCoordinator) startSharedElementAnimation.second).startExit();
                ScreenshotController.SavedImageData.ActionTransition actionTransition = new ScreenshotController.SavedImageData.ActionTransition();
                actionTransition.bundle = ((ActivityOptions) startSharedElementAnimation.first).toBundle();
                return actionTransition;
            }
        }, screenshotController.mScreenshotNotificationSmartActionsProvider);
        screenshotController.mSaveInBgTask = saveImageInBackgroundTask2222;
        final int i12222 = 0;
        saveImageInBackgroundTask2222.execute(new Void[0]);
        if (screenshotController.mScreenCaptureHelper.isShowScreenshotAnimation()) {
        }
        phoneWindow222.getDecorView().setOnApplyWindowInsetsListener(new ScreenshotController$$ExternalSyntheticLambda4());
        screenshotController.mScreenshotHandler.removeMessages(2);
    }

    public final void logSuccessOnActionsReady(SavedImageData savedImageData) {
        Uri uri = savedImageData.uri;
        UiEventLogger uiEventLogger = this.mUiEventLogger;
        if (uri == null) {
            uiEventLogger.log(ScreenshotEvent.SCREENSHOT_NOT_SAVED, 0, this.mPackageName);
            this.mNotificationsController.notifyScreenshotError(R.string.screenshot_failed_to_save_text);
            return;
        }
        uiEventLogger.log(ScreenshotEvent.SCREENSHOT_SAVED, 0, this.mPackageName);
        if (this.mUserManager.isManagedProfile(savedImageData.owner.getIdentifier())) {
            uiEventLogger.log(ScreenshotEvent.SCREENSHOT_SAVED_TO_WORK_PROFILE, 0, this.mPackageName);
        }
    }

    public final void removeWindow() {
        View peekDecorView = this.mWindow.peekDecorView();
        if (peekDecorView != null && peekDecorView.isAttachedToWindow()) {
            this.mWindowManager.removeViewImmediate(peekDecorView);
        }
        ScreenshotView screenshotView = this.mScreenshotView;
        if (screenshotView != null) {
            screenshotView.stopInputListening();
        }
    }

    public final void showScreenshotErrorMessage(final SemScreenshotResult semScreenshotResult) {
        synchronized (mShutterEffectLock) {
            isSnackBarShowing = true;
        }
        attachSemScreenshotLayoutToWindow();
        Presentation presentation = this.mPresentation;
        final View decorView = presentation != null ? presentation.getWindow().getDecorView() : this.mSemScreenshotLayout;
        decorView.post(new Runnable() { // from class: com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                ScreenshotController screenshotController = ScreenshotController.this;
                View view = decorView;
                SemScreenshotResult semScreenshotResult2 = semScreenshotResult;
                screenshotController.getClass();
                synchronized (ScreenshotController.mShutterEffectLock) {
                    new SnackbarController(screenshotController.mContext, screenshotController.mScreenCaptureHelper.capturedDisplayId, new ScreenshotController$$ExternalSyntheticLambda2(screenshotController, 5)).showScreenshotError(view, semScreenshotResult2);
                }
            }
        });
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ScreenshotExitTransitionCallbacksSupplier implements Supplier {
        public final boolean mDismissOnHideSharedElements;

        public ScreenshotExitTransitionCallbacksSupplier(boolean z) {
            this.mDismissOnHideSharedElements = z;
        }

        @Override // java.util.function.Supplier
        public final Object get() {
            return new C23451();
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        /* renamed from: com.android.systemui.screenshot.ScreenshotController$ScreenshotExitTransitionCallbacksSupplier$1 */
        public final class C23451 implements ExitTransitionCoordinator.ExitTransitionCallbacks {
            public C23451() {
            }

            public final void hideSharedElements() {
                ScreenshotExitTransitionCallbacksSupplier screenshotExitTransitionCallbacksSupplier = ScreenshotExitTransitionCallbacksSupplier.this;
                if (screenshotExitTransitionCallbacksSupplier.mDismissOnHideSharedElements) {
                    ScreenshotController.m1637$$Nest$mfinishDismiss(ScreenshotController.this);
                }
            }

            public final boolean isReturnTransitionAllowed() {
                return false;
            }

            public final void onFinish() {
            }
        }
    }
}
