package com.android.systemui.screenshot;

import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.SemSystemProperties;
import android.os.StatFs;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.storage.StorageManager;
import android.provider.Settings;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.window.WindowContext;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.util.ScreenshotRequest;
import com.android.systemui.AbstractC0970x34f4116a;
import com.android.systemui.R;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.screenshot.TakeScreenshotService;
import com.android.systemui.screenshot.sep.ScreenCaptureHelper;
import com.android.systemui.screenshot.sep.ScreenCaptureHelperForB5CoverScreen;
import com.android.systemui.screenshot.sep.ScreenCaptureHelperForFlex;
import com.android.systemui.screenshot.sep.ScreenCaptureHelperForPartial;
import com.android.systemui.screenshot.sep.ScreenCaptureHelperForWindow;
import com.android.systemui.screenshot.sep.ScreenshotErrorController;
import com.android.systemui.screenshot.sep.ScreenshotSelectorView;
import com.android.systemui.screenshot.sep.ScreenshotUtils;
import com.android.systemui.screenshot.sep.SemScreenCaptureHelperFactory;
import com.android.systemui.screenshot.sep.SemScreenshotSaLogging;
import com.android.systemui.screenshot.sep.widget.SemScreenshotLayout;
import com.android.systemui.util.SystemUIAnalytics;
import com.samsung.android.emergencymode.SemEmergencyManager;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.feature.SemFloatingFeature;
import com.sec.ims.configuration.DATA;
import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;
import kotlinx.coroutines.BuildersKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class TakeScreenshotService extends Service {
    public static boolean sConfigured = false;
    public final Executor mBgExecutor;
    public Bundle mBundle;
    public final Context mContext;
    public final ScreenshotNotificationsController mNotificationsController;
    public final RequestProcessor mProcessor;
    public ScreenCaptureHelper mScreenCaptureHelper;
    public final ScreenshotController mScreenshot;
    public final ScreenshotErrorController mScreenshotErrorController;
    public final UiEventLogger mUiEventLogger;
    public final UserManager mUserManager;
    public final C23511 mCloseSystemDialogs = new BroadcastReceiver() { // from class: com.android.systemui.screenshot.TakeScreenshotService.1
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            ScreenshotController screenshotController;
            if (!"android.intent.action.CLOSE_SYSTEM_DIALOGS".equals(intent.getAction()) || (screenshotController = TakeScreenshotService.this.mScreenshot) == null || screenshotController.mScreenshotView.mPendingSharedTransition) {
                return;
            }
            screenshotController.dismissScreenshot(ScreenshotEvent.SCREENSHOT_DISMISSED_OTHER);
        }
    };
    public final Handler mHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() { // from class: com.android.systemui.screenshot.TakeScreenshotService$$ExternalSyntheticLambda1
        @Override // android.os.Handler.Callback
        public final boolean handleMessage(Message message) {
            ScreenCaptureHelper screenCaptureHelperForB5CoverScreen;
            TakeScreenshotService takeScreenshotService = TakeScreenshotService.this;
            boolean z = TakeScreenshotService.sConfigured;
            takeScreenshotService.getClass();
            final Messenger messenger = message.replyTo;
            Consumer<Uri> consumer = new Consumer() { // from class: com.android.systemui.screenshot.TakeScreenshotService$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    Messenger messenger2 = messenger;
                    Uri uri = (Uri) obj;
                    boolean z2 = TakeScreenshotService.sConfigured;
                    try {
                        messenger2.send(Message.obtain(null, 1, uri));
                    } catch (RemoteException e) {
                        Log.d("Screenshot", "ignored remote exception", e);
                    }
                }
            };
            TakeScreenshotService.RequestCallbackImpl requestCallbackImpl = new TakeScreenshotService.RequestCallbackImpl(messenger);
            ScreenshotRequest screenshotRequest = (ScreenshotRequest) message.obj;
            Bundle data = message.getData();
            takeScreenshotService.mBundle = data;
            int i = data.getInt("capturedDisplay", 0);
            SemScreenCaptureHelperFactory semScreenCaptureHelperFactory = SemScreenCaptureHelperFactory.INSTANCE;
            int i2 = message.what;
            semScreenCaptureHelperFactory.getClass();
            if (i2 != 1) {
                screenCaptureHelperForB5CoverScreen = i2 != 2 ? i2 != 100 ? i2 != 101 ? new ScreenCaptureHelper() : new ScreenCaptureHelperForFlex() : new ScreenCaptureHelperForWindow() : new ScreenCaptureHelperForPartial();
            } else {
                screenCaptureHelperForB5CoverScreen = ScreenshotUtils.VALUE_SUB_DISPLAY_POLICY.contains("LARGESCREEN") && i == 1 ? new ScreenCaptureHelperForB5CoverScreen() : new ScreenCaptureHelper();
            }
            takeScreenshotService.mScreenCaptureHelper = screenCaptureHelperForB5CoverScreen;
            Context context = takeScreenshotService.mContext;
            boolean z2 = message.arg1 > 0;
            boolean z3 = message.arg2 > 0;
            Bundle bundle = takeScreenshotService.mBundle;
            screenCaptureHelperForB5CoverScreen.initializeCaptureType();
            screenCaptureHelperForB5CoverScreen.mBundle = bundle;
            screenCaptureHelperForB5CoverScreen.screenCaptureSweepDirection = bundle.getInt("sweepDirection", 1);
            screenCaptureHelperForB5CoverScreen.capturedDisplayId = bundle.getInt("capturedDisplay", 0);
            screenCaptureHelperForB5CoverScreen.screenCaptureOrigin = bundle.getInt("capturedOrigin", 1);
            screenCaptureHelperForB5CoverScreen.safeInsetLeft = bundle.getInt("safeInsetLeft", 0);
            screenCaptureHelperForB5CoverScreen.safeInsetTop = bundle.getInt("safeInsetTop", 0);
            screenCaptureHelperForB5CoverScreen.safeInsetRight = bundle.getInt("safeInsetRight", 0);
            screenCaptureHelperForB5CoverScreen.safeInsetBottom = bundle.getInt("safeInsetBottom", 0);
            screenCaptureHelperForB5CoverScreen.captureSharedBundle = bundle.getBundle("captureSharedBundle");
            screenCaptureHelperForB5CoverScreen.statusBarHeight = bundle.getInt("statusBarHeight", 0);
            screenCaptureHelperForB5CoverScreen.navigationBarHeight = bundle.getInt("navigationBarHeight", 0);
            screenCaptureHelperForB5CoverScreen.stackBounds = (Rect) bundle.getParcelable("stackBounds");
            screenCaptureHelperForB5CoverScreen.isStatusBarVisible = z2;
            screenCaptureHelperForB5CoverScreen.isNavigationBarVisible = z3;
            screenCaptureHelperForB5CoverScreen.windowMode = 1;
            screenCaptureHelperForB5CoverScreen.displayContext = context.createDisplayContext(ScreenshotUtils.getDisplay(screenCaptureHelperForB5CoverScreen.capturedDisplayId, context));
            screenCaptureHelperForB5CoverScreen.initializeScreenshotVariable();
            Log.i(ScreenCaptureHelper.TAG, "initialize: " + screenCaptureHelperForB5CoverScreen);
            if (!TakeScreenshotService.sConfigured) {
                SystemUIAnalytics.initSystemUIAnalyticsStates(takeScreenshotService.getApplication());
                TakeScreenshotService.sConfigured = true;
            }
            takeScreenshotService.handleRequest(screenshotRequest, consumer, requestCallbackImpl);
            return true;
        }
    });

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface RequestCallback {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class RequestCallbackImpl implements RequestCallback {
        public final Messenger mReplyTo;

        public RequestCallbackImpl(Messenger messenger) {
            this.mReplyTo = messenger;
        }
    }

    /* JADX WARN: Type inference failed for: r4v1, types: [com.android.systemui.screenshot.TakeScreenshotService$1] */
    public TakeScreenshotService(ScreenshotController screenshotController, UserManager userManager, DevicePolicyManager devicePolicyManager, UiEventLogger uiEventLogger, ScreenshotNotificationsController screenshotNotificationsController, Context context, ScreenshotErrorController screenshotErrorController, Executor executor, FeatureFlags featureFlags, RequestProcessor requestProcessor) {
        this.mScreenshot = screenshotController;
        this.mUserManager = userManager;
        this.mUiEventLogger = uiEventLogger;
        this.mNotificationsController = screenshotNotificationsController;
        this.mContext = context;
        this.mBgExecutor = executor;
        this.mProcessor = requestProcessor;
        this.mScreenshotErrorController = screenshotErrorController;
    }

    /* JADX WARN: Code restructure failed: missing block: B:173:0x00fb, code lost:
    
        r7 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:179:0x00f9, code lost:
    
        if (android.provider.Settings.System.getInt(r0.getContentResolver(), "enable_reserve_max_mode", 0) != 0) goto L59;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00e1, code lost:
    
        if (android.provider.Settings.Secure.getInt(r0.getContentResolver(), "enable_reserve_max_mode", 0) != 0) goto L59;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00fd, code lost:
    
        r7 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x0231, code lost:
    
        if (r8 != false) goto L177;
     */
    /* JADX WARN: Removed duplicated region for block: B:155:0x01b2  */
    /* JADX WARN: Removed duplicated region for block: B:172:0x0129  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0122  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x01b0  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x01c4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void handleRequest(ScreenshotRequest screenshotRequest, final Consumer<Uri> consumer, final RequestCallback requestCallback) {
        boolean z;
        boolean z2;
        boolean z3;
        char c;
        boolean z4;
        boolean z5;
        boolean z6;
        boolean z7;
        boolean z8;
        boolean z9;
        boolean z10;
        if (!this.mUserManager.isUserUnlocked()) {
            Log.w("Screenshot", "Skipping screenshot because storage is locked!");
            logFailedRequest(screenshotRequest);
            this.mNotificationsController.notifyScreenshotError(R.string.screenshot_failed_to_save_user_locked_text);
            Messenger messenger = ((RequestCallbackImpl) requestCallback).mReplyTo;
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
        ScreenshotErrorController screenshotErrorController = this.mScreenshotErrorController;
        screenshotErrorController.getClass();
        String str = SemSystemProperties.get("sys.shutdown.requested");
        boolean z11 = false;
        if ((str.length() > 0) && (str.startsWith(DATA.DM_FIELD_INDEX.PCSCF_DOMAIN) || str.startsWith("1"))) {
            Log.w(screenshotErrorController.TAG, "Device is in shutdown state");
            z = true;
        } else {
            z = false;
        }
        if (!z) {
            ScreenshotErrorController screenshotErrorController2 = this.mScreenshotErrorController;
            screenshotErrorController2.getClass();
            String str2 = ScreenshotUtils.VALUE_SUB_DISPLAY_POLICY;
            Context context = screenshotErrorController2.context;
            SemEmergencyManager.getInstance(context);
            if (SemEmergencyManager.isEmergencyMode(context)) {
                String string = SemCscFeature.getInstance().getString("CscFeature_Common_ConfigYuva", "");
                if (string != null && string.contains("powerplanning") && string.contains("reserve")) {
                    String string2 = SemCscFeature.getInstance().getString("CscFeature_Common_ConfigYuva", "");
                    if (string2 != null && string2.contains("downloadable_spowerplanning")) {
                        z9 = Settings.Secure.getInt(context.getContentResolver(), "reserve_battery_on", 0) != 0;
                    } else {
                        z9 = Settings.System.getInt(context.getContentResolver(), "reserve_battery_on", 0) != 0;
                    }
                    Log.i("Screenshot", "isReserveBatteryOn : " + z9 + " isEnableReserveMaxMode : " + z10);
                    if (z9 && z10) {
                        z8 = true;
                        if (z8) {
                            ScreenshotUtils.showToast(context, R.string.emergency_mode);
                        } else {
                            ScreenshotUtils.showToast(context, R.string.reserve_battery_mode);
                        }
                        z2 = true;
                    }
                }
                z8 = false;
                if (z8) {
                }
                z2 = true;
            } else {
                z2 = false;
            }
            if (!z2) {
                ScreenshotErrorController screenshotErrorController3 = this.mScreenshotErrorController;
                screenshotErrorController3.getClass();
                if (SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_UNPACK")) {
                    ScreenshotUtils.showToast(R.string.screenshot_failed_title, screenshotErrorController3.context);
                    z3 = true;
                } else {
                    z3 = false;
                }
                if (!z3) {
                    ScreenshotErrorController screenshotErrorController4 = this.mScreenshotErrorController;
                    Context context2 = screenshotErrorController4.context;
                    boolean isUsbMassStorageEnabled = ((StorageManager) context2.getSystemService("storage")).isUsbMassStorageEnabled();
                    String str3 = screenshotErrorController4.TAG;
                    if (isUsbMassStorageEnabled) {
                        Log.w(str3, "getCapacityState: Usb mass storage is enabled.");
                    } else {
                        File externalStorageDirectory = Environment.getExternalStorageDirectory();
                        if (externalStorageDirectory == null) {
                            Log.w(str3, "getCapacityState: an external storage directory is null.");
                        } else {
                            try {
                                StatFs statFs = new StatFs(externalStorageDirectory.getPath());
                                long blockSizeLong = statFs.getBlockSizeLong() * statFs.getAvailableBlocksLong();
                                if (blockSizeLong < 2097152) {
                                    Log.w(str3, "getCapacityState: availableSpace=" + blockSizeLong);
                                    c = (char) 0;
                                } else {
                                    c = 1;
                                }
                            } catch (IllegalArgumentException unused) {
                                Log.w(str3, "getCapacityState: IllegalArgumentException occurred.");
                            }
                            if (c != 1) {
                                z4 = true;
                            } else {
                                if (c == 65535) {
                                    ScreenshotUtils.showToast(R.string.screenshot_failed_title, context2);
                                } else if (c == 0) {
                                    ScreenshotUtils.showToast(R.string.screenshot_memory_full_msg, context2);
                                }
                                z4 = false;
                            }
                            if (z4) {
                                ScreenshotErrorController screenshotErrorController5 = this.mScreenshotErrorController;
                                Bundle bundle = this.mBundle;
                                int i = Settings.Global.getInt(screenshotErrorController5.context.getContentResolver(), "device_provisioned", 0);
                                int i2 = bundle.getInt("capturedOrigin", 1);
                                if (i == 0 && i2 == 2) {
                                    Log.w(screenshotErrorController5.TAG, AbstractC0970x34f4116a.m94m("isPalmScreenshotInSetupWizard: setUpWizardRunning=", i, ", origin=", i2));
                                    z5 = true;
                                } else {
                                    z5 = false;
                                }
                                if (!z5) {
                                    this.mScreenshot.getClass();
                                    Object obj = ScreenshotController.mShutterEffectLock;
                                    synchronized (obj) {
                                        if (ScreenshotController.isSnackBarShowing) {
                                            Log.w("Screenshot", "handleRequest: isSnackBarShowing");
                                        }
                                        z6 = ScreenshotController.isSnackBarShowing;
                                    }
                                    if (!z6) {
                                        this.mScreenshot.getClass();
                                        synchronized (obj) {
                                            if (ScreenshotController.isAnimationRunning) {
                                                Log.w("Screenshot", "handleRequest: isAnimationRunning");
                                            }
                                            z7 = ScreenshotController.isAnimationRunning;
                                        }
                                        if (!z7) {
                                            if (screenshotRequest.getType() == 2) {
                                                ScreenshotSelectorView screenshotSelectorView = this.mScreenshot.mScreenshotSelectorView;
                                                if (screenshotSelectorView != null && screenshotSelectorView.getVisibility() == 0) {
                                                    z11 = true;
                                                }
                                            }
                                            Context context3 = this.mContext;
                                            ScreenCaptureHelper screenCaptureHelper = this.mScreenCaptureHelper;
                                            int i3 = screenCaptureHelper.screenCaptureType;
                                            int i4 = screenCaptureHelper.screenCaptureOrigin;
                                            if (SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_CONTEXTSERVICE_ENABLE_SURVEY_MODE")) {
                                                if (i3 == 1) {
                                                    if (i4 == 1) {
                                                        SemScreenshotSaLogging.sendLogForUsabilityLogging(context3, "TPKE");
                                                        SystemUIAnalytics.sendEventLog(DATA.DM_FIELD_INDEX.SHOW_REG_INFO_TO_SETTING_APP, "9001", "HW key");
                                                    } else if (i4 == 2) {
                                                        SemScreenshotSaLogging.sendLogForUsabilityLogging(context3, "TPPL");
                                                        SystemUIAnalytics.sendEventLog(DATA.DM_FIELD_INDEX.SHOW_REG_INFO_TO_SETTING_APP, "9001", "Palm swipe");
                                                    } else if (i4 == 3) {
                                                        SemScreenshotSaLogging.sendLogForUsabilityLogging(context3, "TPQP");
                                                        SystemUIAnalytics.sendEventLog(DATA.DM_FIELD_INDEX.SHOW_REG_INFO_TO_SETTING_APP, "9001", "Quick panel");
                                                    } else if (i4 == 4) {
                                                        SemScreenshotSaLogging.sendLogForUsabilityLogging(context3, "KNFU");
                                                        SystemUIAnalytics.sendEventLog(DATA.DM_FIELD_INDEX.SHOW_REG_INFO_TO_SETTING_APP, "9001", "Dex mode");
                                                    }
                                                } else if (i3 == 2) {
                                                    if (i4 == 4) {
                                                        SemScreenshotSaLogging.sendLogForUsabilityLogging(context3, "KNPA");
                                                        SystemUIAnalytics.sendEventLog(DATA.DM_FIELD_INDEX.SHOW_REG_INFO_TO_SETTING_APP, "9001", "Dex mode");
                                                    } else {
                                                        SemScreenshotSaLogging.sendLogForUsabilityLogging(context3, "TPPA");
                                                    }
                                                } else if (i3 == 100) {
                                                    SemScreenshotSaLogging.sendLogForUsabilityLogging(context3, "TPWI");
                                                }
                                            }
                                            Log.d("Screenshot", "Processing screenshot data");
                                            ScreenshotData.Companion.getClass();
                                            ScreenshotData screenshotData = new ScreenshotData(screenshotRequest.getType(), screenshotRequest.getSource(), screenshotRequest.getUserId() >= 0 ? UserHandle.of(screenshotRequest.getUserId()) : null, screenshotRequest.getTopComponent(), screenshotRequest.getBoundsInScreen(), screenshotRequest.getTaskId(), screenshotRequest.getInsets(), screenshotRequest.getBitmap(), null, 0, false, false, 3840, null);
                                            ScreenshotController screenshotController = this.mScreenshot;
                                            ScreenCaptureHelper screenCaptureHelper2 = this.mScreenCaptureHelper;
                                            screenshotController.mScreenCaptureHelper = screenCaptureHelper2;
                                            if (screenshotController.mSemScreenshotLayout != null) {
                                                Log.i("Screenshot", "initSemScreenshotLayout: SemScreenshotLayout is already initialized.");
                                            } else {
                                                SemScreenshotLayout semScreenshotLayout = (SemScreenshotLayout) ((LayoutInflater) new ContextThemeWrapper(screenCaptureHelper2.displayContext, 2132018368).getSystemService("layout_inflater")).inflate(R.layout.layout_sem_screenshot, (ViewGroup) null);
                                                screenshotController.mSemScreenshotLayout = semScreenshotLayout;
                                                ScreenshotSelectorView screenshotSelectorView2 = (ScreenshotSelectorView) semScreenshotLayout.findViewById(R.id.global_screenshot_selector);
                                                screenshotController.mScreenshotSelectorView = screenshotSelectorView2;
                                                screenshotSelectorView2.setFocusable(true);
                                                screenshotController.mScreenshotSelectorView.setFocusableInTouchMode(true);
                                            }
                                            screenshotData.displayId = this.mScreenCaptureHelper.builtInDisplayId;
                                            try {
                                                RequestProcessor requestProcessor = this.mProcessor;
                                                BuildersKt.launch$default(requestProcessor.mainScope, null, null, new RequestProcessor$processAsync$2(requestProcessor, screenshotData, new Consumer() { // from class: com.android.systemui.screenshot.TakeScreenshotService$$ExternalSyntheticLambda0
                                                    @Override // java.util.function.Consumer
                                                    public final void accept(Object obj2) {
                                                        TakeScreenshotService takeScreenshotService = TakeScreenshotService.this;
                                                        Consumer consumer2 = consumer;
                                                        TakeScreenshotService.RequestCallback requestCallback2 = requestCallback;
                                                        ScreenshotData screenshotData2 = (ScreenshotData) obj2;
                                                        UiEventLogger uiEventLogger = takeScreenshotService.mUiEventLogger;
                                                        ScreenshotEvent screenshotSource = ScreenshotEvent.getScreenshotSource(screenshotData2.source);
                                                        ComponentName componentName = screenshotData2.topComponent;
                                                        uiEventLogger.log(screenshotSource, 0, componentName == null ? "" : componentName.getPackageName());
                                                        Log.d("Screenshot", "Screenshot request: " + screenshotData2);
                                                        if (screenshotData2.type != 2) {
                                                            takeScreenshotService.mScreenshot.handleScreenshot(screenshotData2, requestCallback2, consumer2);
                                                            return;
                                                        }
                                                        ScreenshotController screenshotController2 = takeScreenshotService.mScreenshot;
                                                        Bundle bundle2 = takeScreenshotService.mBundle;
                                                        ScreenshotSelectorView screenshotSelectorView3 = screenshotController2.mScreenshotSelectorView;
                                                        if (screenshotSelectorView3 == null || !screenshotSelectorView3.isAttachedToWindow()) {
                                                            WindowManager windowManager = (WindowManager) screenshotController2.mScreenCaptureHelper.displayContext.getSystemService("window");
                                                            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 0, 0, 2036, 84411648, -3);
                                                            layoutParams.layoutInDisplayCutoutMode = 1;
                                                            layoutParams.setFitInsetsTypes(0);
                                                            layoutParams.setTitle("ScreenshotSelectorView");
                                                            ImageView imageView = screenshotController2.mSemScreenshotLayout.mScreenshotImageView;
                                                            if (imageView != null) {
                                                                imageView.setImageBitmap(null);
                                                                imageView.setVisibility(8);
                                                            }
                                                            if (screenshotController2.mSemScreenshotLayout.getWindowToken() != null) {
                                                                Log.i("Screenshot", "setPartialScreenshotSelector semScreenshot view window token is not null");
                                                                return;
                                                            }
                                                            try {
                                                                windowManager.addView(screenshotController2.mSemScreenshotLayout, layoutParams);
                                                                screenshotController2.mScreenshotSelectorView.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.screenshot.ScreenshotController.13
                                                                    public final /* synthetic */ Bundle val$bundle;
                                                                    public final /* synthetic */ Consumer val$finisher;
                                                                    public final /* synthetic */ TakeScreenshotService.RequestCallback val$requestCallback;
                                                                    public final /* synthetic */ ScreenshotData val$screenshotData;

                                                                    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                                                                    /* renamed from: com.android.systemui.screenshot.ScreenshotController$13$1 */
                                                                    public final class AnonymousClass1 implements Runnable {
                                                                        public final /* synthetic */ Rect val$rect;

                                                                        public AnonymousClass1(Rect rect) {
                                                                            r2 = rect;
                                                                        }

                                                                        @Override // java.lang.Runnable
                                                                        public final void run() {
                                                                            r2.putParcelable("rect", r2);
                                                                            ViewOnTouchListenerC233713 viewOnTouchListenerC233713 = ViewOnTouchListenerC233713.this;
                                                                            ScreenshotController.this.handleScreenshot(r3, r5, r4);
                                                                        }
                                                                    }

                                                                    public ViewOnTouchListenerC233713(Bundle bundle22, ScreenshotData screenshotData22, Consumer consumer22, TakeScreenshotService.RequestCallback requestCallback22) {
                                                                        r2 = bundle22;
                                                                        r3 = screenshotData22;
                                                                        r4 = consumer22;
                                                                        r5 = requestCallback22;
                                                                    }

                                                                    @Override // android.view.View.OnTouchListener
                                                                    public final boolean onTouch(View view, MotionEvent motionEvent) {
                                                                        ScreenshotSelectorView screenshotSelectorView4 = (ScreenshotSelectorView) view;
                                                                        int action = motionEvent.getAction();
                                                                        if (action == 0) {
                                                                            int x = (int) motionEvent.getX();
                                                                            int y = (int) motionEvent.getY();
                                                                            screenshotSelectorView4.getClass();
                                                                            screenshotSelectorView4.mStartPoint = new Point(x, y);
                                                                            screenshotSelectorView4.mSelectionRect = new Rect(x, y, x, y);
                                                                            return true;
                                                                        }
                                                                        if (action == 1) {
                                                                            screenshotSelectorView4.setVisibility(8);
                                                                            ScreenshotController screenshotController3 = ScreenshotController.this;
                                                                            screenshotController3.mWindowManager.removeView(screenshotController3.mSemScreenshotLayout);
                                                                            Rect rect = screenshotSelectorView4.mSelectionRect;
                                                                            if (rect != null && rect.width() != 0 && rect.height() != 0) {
                                                                                ScreenshotController.this.mSemScreenshotLayout.post(new Runnable() { // from class: com.android.systemui.screenshot.ScreenshotController.13.1
                                                                                    public final /* synthetic */ Rect val$rect;

                                                                                    public AnonymousClass1(Rect rect2) {
                                                                                        r2 = rect2;
                                                                                    }

                                                                                    @Override // java.lang.Runnable
                                                                                    public final void run() {
                                                                                        r2.putParcelable("rect", r2);
                                                                                        ViewOnTouchListenerC233713 viewOnTouchListenerC233713 = ViewOnTouchListenerC233713.this;
                                                                                        ScreenshotController.this.handleScreenshot(r3, r5, r4);
                                                                                    }
                                                                                });
                                                                            }
                                                                            screenshotSelectorView4.mStartPoint = null;
                                                                            screenshotSelectorView4.mSelectionRect = null;
                                                                            return true;
                                                                        }
                                                                        if (action != 2) {
                                                                            return false;
                                                                        }
                                                                        int x2 = (int) motionEvent.getX();
                                                                        int y2 = (int) motionEvent.getY();
                                                                        Rect rect2 = screenshotSelectorView4.mSelectionRect;
                                                                        if (rect2 != null) {
                                                                            rect2.left = Math.min(screenshotSelectorView4.mStartPoint.x, x2);
                                                                            screenshotSelectorView4.mSelectionRect.right = Math.max(screenshotSelectorView4.mStartPoint.x, x2);
                                                                            screenshotSelectorView4.mSelectionRect.top = Math.min(screenshotSelectorView4.mStartPoint.y, y2);
                                                                            screenshotSelectorView4.mSelectionRect.bottom = Math.max(screenshotSelectorView4.mStartPoint.y, y2);
                                                                            screenshotSelectorView4.invalidate();
                                                                        }
                                                                        return true;
                                                                    }
                                                                });
                                                                screenshotController2.mScreenshotSelectorView.setOnKeyListener(new View.OnKeyListener() { // from class: com.android.systemui.screenshot.ScreenshotController.14
                                                                    public ViewOnKeyListenerC233814() {
                                                                    }

                                                                    @Override // android.view.View.OnKeyListener
                                                                    public final boolean onKey(View view, int i5, KeyEvent keyEvent) {
                                                                        if (i5 != 111 || keyEvent.getAction() != 1) {
                                                                            return false;
                                                                        }
                                                                        view.setVisibility(8);
                                                                        ScreenshotController screenshotController3 = ScreenshotController.this;
                                                                        screenshotController3.mWindowManager.removeView(screenshotController3.mSemScreenshotLayout);
                                                                        return true;
                                                                    }
                                                                });
                                                                screenshotController2.mSemScreenshotLayout.post(new Runnable() { // from class: com.android.systemui.screenshot.ScreenshotController.15
                                                                    public RunnableC233915() {
                                                                    }

                                                                    @Override // java.lang.Runnable
                                                                    public final void run() {
                                                                        ScreenshotController.this.mScreenshotSelectorView.setVisibility(0);
                                                                        ScreenshotController.this.mScreenshotSelectorView.requestFocus();
                                                                    }
                                                                });
                                                            } catch (IllegalStateException e3) {
                                                                Log.e("Screenshot", "setPartialScreenshotSelector " + e3);
                                                            }
                                                        }
                                                    }
                                                }, null), 3);
                                                return;
                                            } catch (IllegalStateException e3) {
                                                Log.e("Screenshot", "Failed to process screenshot request!", e3);
                                                logFailedRequest(screenshotRequest);
                                                this.mNotificationsController.notifyScreenshotError(R.string.screenshot_failed_to_capture_text);
                                                Messenger messenger2 = ((RequestCallbackImpl) requestCallback).mReplyTo;
                                                try {
                                                    messenger2.send(Message.obtain(null, 1, null));
                                                } catch (RemoteException e4) {
                                                    Log.d("Screenshot", "ignored remote exception", e4);
                                                }
                                                try {
                                                    messenger2.send(Message.obtain((Handler) null, 2));
                                                    return;
                                                } catch (RemoteException e5) {
                                                    Log.d("Screenshot", "ignored remote exception", e5);
                                                    return;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    c = 65535;
                    if (c != 1) {
                    }
                    if (z4) {
                    }
                }
            }
        }
        logFailedRequest(screenshotRequest);
        Messenger messenger3 = ((RequestCallbackImpl) requestCallback).mReplyTo;
        try {
            messenger3.send(Message.obtain(null, 1, null));
        } catch (RemoteException e6) {
            Log.d("Screenshot", "ignored remote exception", e6);
        }
        try {
            messenger3.send(Message.obtain((Handler) null, 2));
        } catch (RemoteException e7) {
            Log.d("Screenshot", "ignored remote exception", e7);
        }
    }

    public final void logFailedRequest(ScreenshotRequest screenshotRequest) {
        ComponentName topComponent = screenshotRequest.getTopComponent();
        String packageName = topComponent == null ? "" : topComponent.getPackageName();
        this.mUiEventLogger.log(ScreenshotEvent.getScreenshotSource(screenshotRequest.getSource()), 0, packageName);
        this.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_CAPTURE_FAILED, 0, packageName);
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        registerReceiver(this.mCloseSystemDialogs, new IntentFilter("android.intent.action.CLOSE_SYSTEM_DIALOGS"), 2);
        return new Messenger(this.mHandler).getBinder();
    }

    @Override // android.app.Service
    public final void onDestroy() {
        super.onDestroy();
        ScreenshotController screenshotController = this.mScreenshot;
        SaveImageInBackgroundTask saveImageInBackgroundTask = screenshotController.mSaveInBgTask;
        if (saveImageInBackgroundTask != null) {
            saveImageInBackgroundTask.mParams.mActionsReadyListener = new ScreenshotController$$ExternalSyntheticLambda2(screenshotController, 0);
        }
        screenshotController.removeWindow();
        CallbackToFutureAdapter.SafeFuture safeFuture = screenshotController.mCameraSound;
        try {
            MediaPlayer mediaPlayer = (MediaPlayer) safeFuture.get(1L, TimeUnit.SECONDS);
            if (mediaPlayer != null) {
                mediaPlayer.release();
            }
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            safeFuture.cancel(true);
            Log.w("Screenshot", "Error releasing shutter sound", e);
        }
        WindowContext windowContext = screenshotController.mContext;
        windowContext.unregisterReceiver(screenshotController.mCopyBroadcastReceiver);
        windowContext.release();
        screenshotController.mBgExecutor.shutdownNow();
    }

    @Override // android.app.Service
    public final boolean onUnbind(Intent intent) {
        this.mScreenshot.removeWindow();
        unregisterReceiver(this.mCloseSystemDialogs);
        return false;
    }

    @Override // android.app.Service
    public final void onCreate() {
    }
}
