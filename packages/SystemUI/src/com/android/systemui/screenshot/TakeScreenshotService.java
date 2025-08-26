package com.android.systemui.screenshot;

import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ResolveInfo;
import android.graphics.Rect;
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
import android.os.UserManager;
import android.os.storage.StorageManager;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.widget.Toast;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.util.ScreenshotRequest;
import com.android.keyguard.ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.aod.AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.screenshot.ScreenshotNotificationsController;
import com.android.systemui.screenshot.TakeScreenshotService;
import com.android.systemui.screenshot.sep.ScreenCaptureHelper;
import com.android.systemui.screenshot.sep.ScreenCaptureHelperForB5CoverScreen;
import com.android.systemui.screenshot.sep.ScreenCaptureHelperForFlex;
import com.android.systemui.screenshot.sep.ScreenCaptureHelperForLargeCoverScreen;
import com.android.systemui.screenshot.sep.ScreenCaptureHelperForPartial;
import com.android.systemui.screenshot.sep.ScreenCaptureHelperForWindow;
import com.android.systemui.screenshot.sep.ScreenshotErrorController;
import com.android.systemui.screenshot.sep.ScreenshotUtils;
import com.android.systemui.screenshot.sep.SemScreenCaptureHelperFactory;
import com.android.systemui.screenshot.sep.SemScreenshotSaLogging;
import com.android.systemui.screenshot.sep.TaskbarUtils;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.samsung.android.emergencymode.SemEmergencyManager;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.view.SemWindowManager;
import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* loaded from: classes2.dex */
public class TakeScreenshotService extends Service {
    public static boolean sConfigured = false;
    public final Executor mBgExecutor;
    public Bundle mBundle;
    public final Context mContext;
    public final ScreenshotNotificationsController mNotificationsController;
    public ScreenCaptureHelper mScreenCaptureHelper;
    public final ScreenshotErrorController mScreenshotErrorController;
    public final TakeScreenshotExecutor mTakeScreenshotExecutor;
    public final UiEventLogger mUiEventLogger;
    public final UserManager mUserManager;
    public final AnonymousClass1 mCloseSystemDialogs = new BroadcastReceiver() { // from class: com.android.systemui.screenshot.TakeScreenshotService.1
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            TakeScreenshotExecutorImpl takeScreenshotExecutorImpl;
            InteractiveScreenshotHandler interactiveScreenshotHandler;
            InteractiveScreenshotHandler interactiveScreenshotHandler2;
            if (!PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS.equals(intent.getAction()) || (interactiveScreenshotHandler = (takeScreenshotExecutorImpl = (TakeScreenshotExecutorImpl) TakeScreenshotService.this.mTakeScreenshotExecutor).screenshotController) == null || interactiveScreenshotHandler.isPendingSharedTransition() || (interactiveScreenshotHandler2 = takeScreenshotExecutorImpl.screenshotController) == null) {
                return;
            }
            interactiveScreenshotHandler2.requestDismissal(ScreenshotEvent.SCREENSHOT_DISMISSED_OTHER);
        }
    };
    public final Handler mHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() { // from class: com.android.systemui.screenshot.TakeScreenshotService$$ExternalSyntheticLambda0
        /* JADX WARN: Removed duplicated region for block: B:118:0x031a  */
        /* JADX WARN: Removed duplicated region for block: B:143:0x03d4  */
        /* JADX WARN: Removed duplicated region for block: B:71:0x01a9  */
        /* JADX WARN: Removed duplicated region for block: B:75:0x01ba  */
        @Override // android.os.Handler.Callback
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final boolean handleMessage(Message message) {
            ScreenCaptureHelper screenCaptureHelperForLargeCoverScreen;
            char c;
            boolean z;
            TakeScreenshotService takeScreenshotService = this.f$0;
            boolean z2 = TakeScreenshotService.sConfigured;
            final Messenger messenger = message.replyTo;
            Consumer consumer = new Consumer() { // from class: com.android.systemui.screenshot.TakeScreenshotService$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) throws RemoteException {
                    Messenger messenger2 = messenger;
                    Uri uri = (Uri) obj;
                    boolean z3 = TakeScreenshotService.sConfigured;
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
                screenCaptureHelperForLargeCoverScreen = i2 != 2 ? i2 != 100 ? i2 != 101 ? new ScreenCaptureHelper() : new ScreenCaptureHelperForFlex() : new ScreenCaptureHelperForWindow() : new ScreenCaptureHelperForPartial();
            } else {
                String str = SemSystemProperties.get("ro.product.device");
                screenCaptureHelperForLargeCoverScreen = (str != null && !str.isEmpty() && BasicRune$$ExternalSyntheticOutline0.m("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_SUBDISPLAY_POLICY", "LARGESCREEN") && i == 1 && str.startsWith("b7")) ? new ScreenCaptureHelperForLargeCoverScreen() : (BasicRune$$ExternalSyntheticOutline0.m("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_SUBDISPLAY_POLICY", "LARGESCREEN") && i == 1) ? new ScreenCaptureHelperForB5CoverScreen() : new ScreenCaptureHelper();
            }
            takeScreenshotService.mScreenCaptureHelper = screenCaptureHelperForLargeCoverScreen;
            Context context = takeScreenshotService.mContext;
            boolean z3 = message.arg1 > 0;
            boolean z4 = message.arg2 > 0;
            Bundle bundle = takeScreenshotService.mBundle;
            screenCaptureHelperForLargeCoverScreen.initializeCaptureType();
            screenCaptureHelperForLargeCoverScreen.mBundle = bundle;
            screenCaptureHelperForLargeCoverScreen.screenCaptureSweepDirection = bundle.getInt("sweepDirection", 1);
            screenCaptureHelperForLargeCoverScreen.capturedDisplayId = bundle.getInt("capturedDisplay", 0);
            screenCaptureHelperForLargeCoverScreen.screenCaptureOrigin = bundle.getInt("capturedOrigin", 1);
            screenCaptureHelperForLargeCoverScreen.safeInsetLeft = bundle.getInt("safeInsetLeft", 0);
            screenCaptureHelperForLargeCoverScreen.safeInsetTop = bundle.getInt("safeInsetTop", 0);
            screenCaptureHelperForLargeCoverScreen.safeInsetRight = bundle.getInt("safeInsetRight", 0);
            screenCaptureHelperForLargeCoverScreen.safeInsetBottom = bundle.getInt("safeInsetBottom", 0);
            screenCaptureHelperForLargeCoverScreen.captureSharedBundle = bundle.getBundle("captureSharedBundle");
            screenCaptureHelperForLargeCoverScreen.statusBarHeight = bundle.getInt("statusBarHeight", 0);
            screenCaptureHelperForLargeCoverScreen.navigationBarHeight = bundle.getInt("navigationBarHeight", 0);
            screenCaptureHelperForLargeCoverScreen.stackBounds = (Rect) bundle.getParcelable("stackBounds");
            screenCaptureHelperForLargeCoverScreen.isStatusBarVisible = z3;
            screenCaptureHelperForLargeCoverScreen.isNavigationBarVisible = z4;
            String str2 = ScreenCaptureHelper.TAG;
            if (context != null && Settings.Global.getInt(context.getContentResolver(), SettingsHelper.INDEX_NAVIGATION_BAR_GESTURE_WHILE_HIDDEN, 0) != 0) {
                boolean z5 = TaskbarUtils.FEATURE_SUPPORT_TASKBAR;
                String str3 = TaskbarUtils.TAG;
                if (z5 && Settings.Global.getInt(context.getContentResolver(), SettingsHelper.INDEX_TASK_BAR, 0) == 1) {
                    String topMostApplicationPackage = ScreenshotUtils.getTopMostApplicationPackage(context);
                    if (!Intrinsics.areEqual(topMostApplicationPackage, KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG) && !Intrinsics.areEqual(topMostApplicationPackage, "com.sec.android.app.desktoplauncher")) {
                        Intent intent = new Intent("android.intent.action.MAIN");
                        intent.addCategory("android.intent.category.HOME");
                        intent.setPackage(topMostApplicationPackage);
                        ResolveInfo resolveInfoResolveActivity = context.getPackageManager().resolveActivity(intent, 65536);
                        if (resolveInfoResolveActivity != null && resolveInfoResolveActivity.activityInfo != null) {
                            Log.i(str3, "taskbar visibility: false, launcher");
                        }
                    }
                    List visibleWindowInfoList = SemWindowManager.getInstance().getVisibleWindowInfoList();
                    if (visibleWindowInfoList != null) {
                        List<SemWindowManager.VisibleWindowInfo> list = visibleWindowInfoList;
                        if ((list instanceof Collection) && list.isEmpty()) {
                            z = false;
                            AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0.m("taskbar visibility: ", str3, z);
                            if (!z) {
                            }
                        } else {
                            for (SemWindowManager.VisibleWindowInfo visibleWindowInfo : list) {
                                if (StringsKt__StringsKt.contains(visibleWindowInfo.name, "TaskbarWindow", false) && Intrinsics.areEqual(visibleWindowInfo.packageName, "com.sec.android.app.launcher")) {
                                    z = true;
                                    break;
                                }
                            }
                            z = false;
                            AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0.m("taskbar visibility: ", str3, z);
                            if (!z) {
                                int identifier = context.getResources().getIdentifier("task_bar_height", "dimen", "android");
                                int dimensionPixelSize = identifier > 0 ? context.getResources().getDimensionPixelSize(identifier) : 0;
                                Log.i(TaskbarUtils.TAG, MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(dimensionPixelSize, "taskBarHeight: "));
                                int i3 = screenCaptureHelperForLargeCoverScreen.navigationBarHeight;
                                if (i3 >= dimensionPixelSize) {
                                    ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i3, "initialize : remove taskbar height from navigationBarHeight: ", str2);
                                    screenCaptureHelperForLargeCoverScreen.navigationBarHeight -= dimensionPixelSize;
                                }
                            }
                        }
                    }
                } else {
                    Log.i(str3, "taskbar visibility: false, disabled");
                }
                z = false;
                if (!z) {
                }
            }
            screenCaptureHelperForLargeCoverScreen.windowMode = 1;
            screenCaptureHelperForLargeCoverScreen.displayContext = context.createDisplayContext(ScreenshotUtils.getDisplay(screenCaptureHelperForLargeCoverScreen.capturedDisplayId, context));
            screenCaptureHelperForLargeCoverScreen.initializeScreenshotVariable();
            Log.i(str2, "initialize: " + screenCaptureHelperForLargeCoverScreen);
            ((TakeScreenshotExecutorImpl) takeScreenshotService.mTakeScreenshotExecutor).screenCaptureHelper = takeScreenshotService.mScreenCaptureHelper;
            if (!TakeScreenshotService.sConfigured) {
                SystemUIAnalytics.initSystemUIAnalyticsStates(takeScreenshotService.getApplication());
                TakeScreenshotService.sConfigured = true;
            }
            if (!takeScreenshotService.mUserManager.isUserUnlocked()) {
                Log.w("Screenshot", "Skipping screenshot because storage is locked!");
                takeScreenshotService.logFailedRequest(screenshotRequest);
                takeScreenshotService.mNotificationsController.notifyScreenshotError(R.string.screenshot_failed_to_save_user_locked_text);
                requestCallbackImpl.reportError();
                return true;
            }
            takeScreenshotService.mScreenshotErrorController.getClass();
            String str4 = SemSystemProperties.get("sys.shutdown.requested");
            if (str4.length() <= 0 || !(str4.startsWith("0") || str4.startsWith("1"))) {
                ScreenshotErrorController screenshotErrorController = takeScreenshotService.mScreenshotErrorController;
                Context context2 = screenshotErrorController.context;
                SemEmergencyManager.getInstance(context2);
                if (SemEmergencyManager.isEmergencyMode(context2)) {
                    Context context3 = screenshotErrorController.context;
                    if (context3 != null) {
                        Toast.makeText(new ContextThemeWrapper(context3, android.R.style.Theme.DeviceDefault.Light), context3.getString(R.string.cant_screenshot_in_ps, context3.getString(R.string.emergency_mode)), 0).show();
                    }
                } else {
                    ScreenshotErrorController screenshotErrorController2 = takeScreenshotService.mScreenshotErrorController;
                    screenshotErrorController2.getClass();
                    if (SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_UNPACK")) {
                        ScreenshotUtils.showToast(R.string.screenshot_failed_title, screenshotErrorController2.context);
                    } else {
                        ScreenshotErrorController screenshotErrorController3 = takeScreenshotService.mScreenshotErrorController;
                        if (((StorageManager) screenshotErrorController3.context.getSystemService("storage")).isUsbMassStorageEnabled()) {
                            Log.w("Screenshot", "getCapacityState: Usb mass storage is enabled.");
                        } else {
                            File externalStorageDirectory = Environment.getExternalStorageDirectory();
                            if (externalStorageDirectory == null) {
                                Log.w("Screenshot", "getCapacityState: an external storage directory is null.");
                            } else {
                                try {
                                    StatFs statFs = new StatFs(externalStorageDirectory.getPath());
                                    long blockSizeLong = statFs.getBlockSizeLong() * statFs.getAvailableBlocksLong();
                                    if (blockSizeLong < 2097152) {
                                        Log.w("Screenshot", "getCapacityState: availableSpace=" + blockSizeLong);
                                        c = (char) 0;
                                    } else {
                                        c = 1;
                                    }
                                } catch (IllegalArgumentException unused) {
                                    Log.w("Screenshot", "getCapacityState: IllegalArgumentException occurred.");
                                }
                                if (c != 1) {
                                    ScreenshotErrorController screenshotErrorController4 = takeScreenshotService.mScreenshotErrorController;
                                    Bundle bundle2 = takeScreenshotService.mBundle;
                                    int i4 = Settings.Global.getInt(screenshotErrorController4.context.getContentResolver(), "device_provisioned", 0);
                                    int i5 = bundle2.getInt("capturedOrigin", 1);
                                    if (i4 != 0 || i5 != 2) {
                                        Context context4 = takeScreenshotService.mContext;
                                        ScreenCaptureHelper screenCaptureHelper = takeScreenshotService.mScreenCaptureHelper;
                                        int i6 = screenCaptureHelper.screenCaptureType;
                                        int i7 = screenCaptureHelper.screenCaptureOrigin;
                                        if (SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_CONTEXTSERVICE_ENABLE_SURVEY_MODE")) {
                                            if (i6 == 1) {
                                                if (i7 == 1) {
                                                    SemScreenshotSaLogging.sendLogForUsabilityLogging(context4, "TPKE");
                                                    SystemUIAnalytics.sendEventLog("900", SystemUIAnalytics.EID_SCREEN_CAPTURE, SystemUIAnalytics.DT_SCREEN_CAPTURE_HW_KEY);
                                                } else if (i7 == 2) {
                                                    SemScreenshotSaLogging.sendLogForUsabilityLogging(context4, "TPPL");
                                                    SystemUIAnalytics.sendEventLog("900", SystemUIAnalytics.EID_SCREEN_CAPTURE, SystemUIAnalytics.DT_SCREEN_CAPTURE_PALM_SWIPE);
                                                } else if (i7 == 3) {
                                                    SemScreenshotSaLogging.sendLogForUsabilityLogging(context4, "TPQP");
                                                    SystemUIAnalytics.sendEventLog("900", SystemUIAnalytics.EID_SCREEN_CAPTURE, "Quick panel");
                                                } else if (i7 == 4) {
                                                    SemScreenshotSaLogging.sendLogForUsabilityLogging(context4, "KNFU");
                                                    SystemUIAnalytics.sendEventLog("900", SystemUIAnalytics.EID_SCREEN_CAPTURE, SystemUIAnalytics.DT_SCREEN_CAPTURE_DEX_MODE);
                                                }
                                            } else if (i6 == 2) {
                                                if (i7 == 4) {
                                                    SemScreenshotSaLogging.sendLogForUsabilityLogging(context4, "KNPA");
                                                    SystemUIAnalytics.sendEventLog("900", SystemUIAnalytics.EID_SCREEN_CAPTURE, SystemUIAnalytics.DT_SCREEN_CAPTURE_DEX_MODE);
                                                } else {
                                                    SemScreenshotSaLogging.sendLogForUsabilityLogging(context4, "TPPA");
                                                }
                                            } else if (i6 == 100) {
                                                SemScreenshotSaLogging.sendLogForUsabilityLogging(context4, "TPWI");
                                            }
                                        }
                                        Log.d("Screenshot", "Processing screenshot data");
                                        TakeScreenshotExecutorImpl takeScreenshotExecutorImpl = (TakeScreenshotExecutorImpl) takeScreenshotService.mTakeScreenshotExecutor;
                                        takeScreenshotExecutorImpl.getClass();
                                        CoroutineTracingKt.launchTraced$default(takeScreenshotExecutorImpl.mainScope, null, null, new TakeScreenshotExecutorImpl$executeScreenshotsAsync$1(takeScreenshotExecutorImpl, screenshotRequest, requestCallbackImpl, consumer, null), 7);
                                        return true;
                                    }
                                    Log.w("Screenshot", "isPalmScreenshotInSetupWizard: setUpWizardRunning=" + i4 + ", origin=" + i5);
                                } else if (c == 65535) {
                                    ScreenshotUtils.showToast(R.string.screenshot_failed_title, screenshotErrorController3.context);
                                } else if (c == 0) {
                                    ScreenshotUtils.showToast(R.string.screenshot_memory_full_msg, screenshotErrorController3.context);
                                }
                            }
                        }
                        c = 65535;
                        if (c != 1) {
                        }
                    }
                }
            } else {
                Log.w("Screenshot", "Device is in shutdown state");
            }
            takeScreenshotService.logFailedRequest(screenshotRequest);
            requestCallbackImpl.reportError();
            return true;
        }
    });

    public interface RequestCallback {
    }

    public class RequestCallbackImpl implements RequestCallback {
        public final Messenger mReplyTo;

        public RequestCallbackImpl(Messenger messenger) {
            this.mReplyTo = messenger;
        }

        public final void reportError() {
            Messenger messenger = this.mReplyTo;
            boolean z = TakeScreenshotService.sConfigured;
            try {
                messenger.send(Message.obtain(null, 1, null));
            } catch (RemoteException e) {
                Log.d("Screenshot", "ignored remote exception", e);
            }
            try {
                this.mReplyTo.send(Message.obtain((Handler) null, 2));
            } catch (RemoteException e2) {
                Log.d("Screenshot", "ignored remote exception", e2);
            }
        }
    }

    /* JADX WARN: Type inference failed for: r4v1, types: [com.android.systemui.screenshot.TakeScreenshotService$1] */
    public TakeScreenshotService(UserManager userManager, DevicePolicyManager devicePolicyManager, UiEventLogger uiEventLogger, ScreenshotNotificationsController.Factory factory, ScreenshotErrorController screenshotErrorController, Context context, Executor executor, TakeScreenshotExecutor takeScreenshotExecutor) {
        this.mUserManager = userManager;
        this.mUiEventLogger = uiEventLogger;
        this.mNotificationsController = factory.create(0);
        this.mContext = context;
        this.mBgExecutor = executor;
        this.mTakeScreenshotExecutor = takeScreenshotExecutor;
        this.mScreenshotErrorController = screenshotErrorController;
    }

    public final void logFailedRequest(ScreenshotRequest screenshotRequest) {
        ComponentName topComponent = screenshotRequest.getTopComponent();
        String packageName = topComponent == null ? "" : topComponent.getPackageName();
        this.mUiEventLogger.log(ScreenshotEvent.getScreenshotSource(screenshotRequest.getSource()), 0, packageName);
        this.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_CAPTURE_FAILED, 0, packageName);
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        registerReceiver(this.mCloseSystemDialogs, new IntentFilter(PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS), 2);
        return new Messenger(this.mHandler).getBinder();
    }

    @Override // android.app.Service
    public final void onDestroy() {
        super.onDestroy();
        TakeScreenshotExecutorImpl takeScreenshotExecutorImpl = (TakeScreenshotExecutorImpl) this.mTakeScreenshotExecutor;
        InteractiveScreenshotHandler interactiveScreenshotHandler = takeScreenshotExecutorImpl.screenshotController;
        if (interactiveScreenshotHandler != null) {
            interactiveScreenshotHandler.onDestroy();
        }
        takeScreenshotExecutorImpl.screenshotController = null;
    }

    @Override // android.app.Service
    public final boolean onUnbind(Intent intent) {
        InteractiveScreenshotHandler interactiveScreenshotHandler = ((TakeScreenshotExecutorImpl) this.mTakeScreenshotExecutor).screenshotController;
        if (interactiveScreenshotHandler != null) {
            interactiveScreenshotHandler.removeWindow();
        }
        unregisterReceiver(this.mCloseSystemDialogs);
        return false;
    }

    @Override // android.app.Service
    public final void onCreate() {
    }
}
