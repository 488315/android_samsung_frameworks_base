package com.android.systemui.screenshot;

import android.R;
import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.widget.Toast;
import androidx.compose.foundation.text.HeightInLinesModifierKt$$ExternalSyntheticOutline0;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.util.ScreenshotRequest;
import com.android.systemui.BasicRune$$ExternalSyntheticOutline0;
import com.android.systemui.Flags;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.screenshot.ScreenshotNotificationsController;
import com.android.systemui.screenshot.TakeScreenshotService;
import com.android.systemui.screenshot.sep.ScreenCaptureHelper;
import com.android.systemui.screenshot.sep.ScreenCaptureHelperForB5CoverScreen;
import com.android.systemui.screenshot.sep.ScreenCaptureHelperForFlex;
import com.android.systemui.screenshot.sep.ScreenCaptureHelperForPartial;
import com.android.systemui.screenshot.sep.ScreenCaptureHelperForWindow;
import com.android.systemui.screenshot.sep.ScreenshotErrorController;
import com.android.systemui.screenshot.sep.ScreenshotUtils;
import com.android.systemui.screenshot.sep.SemScreenCaptureHelperFactory;
import com.android.systemui.screenshot.sep.SemScreenshotSaLogging;
import com.android.systemui.util.SystemUIAnalytics;
import com.samsung.android.emergencymode.SemEmergencyManager;
import com.samsung.android.feature.SemFloatingFeature;
import java.io.File;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import kotlinx.coroutines.BuildersKt;

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
            ScreenshotController screenshotController;
            ScreenshotController screenshotController2;
            if (!PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS.equals(intent.getAction()) || (screenshotController = (takeScreenshotExecutorImpl = (TakeScreenshotExecutorImpl) TakeScreenshotService.this.mTakeScreenshotExecutor).screenshotController) == null) {
                return;
            }
            Flags.screenshotShelfUi2();
            if (screenshotController.mActionExecutor.isPendingSharedTransition || (screenshotController2 = takeScreenshotExecutorImpl.screenshotController) == null) {
                return;
            }
            screenshotController2.mViewProxy.requestDismissal(ScreenshotEvent.SCREENSHOT_DISMISSED_OTHER);
        }
    };
    public final Handler mHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() { // from class: com.android.systemui.screenshot.TakeScreenshotService$$ExternalSyntheticLambda0
        @Override // android.os.Handler.Callback
        public final boolean handleMessage(Message message) {
            TakeScreenshotService takeScreenshotService = TakeScreenshotService.this;
            boolean z = TakeScreenshotService.sConfigured;
            takeScreenshotService.getClass();
            final Messenger messenger = message.replyTo;
            Consumer consumer = new Consumer() { // from class: com.android.systemui.screenshot.TakeScreenshotService$$ExternalSyntheticLambda1
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
            ScreenCaptureHelper screenCaptureHelper = i2 != 1 ? i2 != 2 ? i2 != 100 ? i2 != 101 ? new ScreenCaptureHelper() : new ScreenCaptureHelperForFlex() : new ScreenCaptureHelperForWindow() : new ScreenCaptureHelperForPartial() : (BasicRune$$ExternalSyntheticOutline0.m("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_SUBDISPLAY_POLICY", "LARGESCREEN") && i == 1) ? new ScreenCaptureHelperForB5CoverScreen() : new ScreenCaptureHelper();
            takeScreenshotService.mScreenCaptureHelper = screenCaptureHelper;
            Context context = takeScreenshotService.mContext;
            boolean z2 = message.arg1 > 0;
            boolean z3 = message.arg2 > 0;
            Bundle bundle = takeScreenshotService.mBundle;
            screenCaptureHelper.initializeCaptureType();
            screenCaptureHelper.mBundle = bundle;
            screenCaptureHelper.screenCaptureSweepDirection = bundle.getInt("sweepDirection", 1);
            screenCaptureHelper.capturedDisplayId = bundle.getInt("capturedDisplay", 0);
            screenCaptureHelper.screenCaptureOrigin = bundle.getInt("capturedOrigin", 1);
            screenCaptureHelper.safeInsetLeft = bundle.getInt("safeInsetLeft", 0);
            screenCaptureHelper.safeInsetTop = bundle.getInt("safeInsetTop", 0);
            screenCaptureHelper.safeInsetRight = bundle.getInt("safeInsetRight", 0);
            screenCaptureHelper.safeInsetBottom = bundle.getInt("safeInsetBottom", 0);
            screenCaptureHelper.captureSharedBundle = bundle.getBundle("captureSharedBundle");
            screenCaptureHelper.statusBarHeight = bundle.getInt("statusBarHeight", 0);
            screenCaptureHelper.navigationBarHeight = bundle.getInt("navigationBarHeight", 0);
            screenCaptureHelper.stackBounds = (Rect) bundle.getParcelable("stackBounds");
            screenCaptureHelper.isStatusBarVisible = z2;
            screenCaptureHelper.isNavigationBarVisible = z3;
            screenCaptureHelper.windowMode = 1;
            screenCaptureHelper.displayContext = context.createDisplayContext(ScreenshotUtils.getDisplay(screenCaptureHelper.capturedDisplayId, context));
            screenCaptureHelper.initializeScreenshotVariable();
            Log.i(ScreenCaptureHelper.TAG, "initialize: " + screenCaptureHelper);
            ((TakeScreenshotExecutorImpl) takeScreenshotService.mTakeScreenshotExecutor).screenCaptureHelper = takeScreenshotService.mScreenCaptureHelper;
            if (!TakeScreenshotService.sConfigured) {
                SystemUIAnalytics.initSystemUIAnalyticsStates(takeScreenshotService.getApplication());
                TakeScreenshotService.sConfigured = true;
            }
            if (takeScreenshotService.mUserManager.isUserUnlocked()) {
                ScreenshotErrorController screenshotErrorController = takeScreenshotService.mScreenshotErrorController;
                screenshotErrorController.getClass();
                String str = SemSystemProperties.get("sys.shutdown.requested");
                if (str.length() <= 0 || !(str.startsWith("0") || str.startsWith("1"))) {
                    Context context2 = takeScreenshotService.mScreenshotErrorController.context;
                    SemEmergencyManager.getInstance(context2);
                    if (!SemEmergencyManager.isEmergencyMode(context2)) {
                        ScreenshotErrorController screenshotErrorController2 = takeScreenshotService.mScreenshotErrorController;
                        screenshotErrorController2.getClass();
                        if (SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_UNPACK")) {
                            Context context3 = screenshotErrorController2.context;
                            if (context3 != null) {
                                Toast.makeText(new ContextThemeWrapper(context3, R.style.Theme.DeviceDefault.Light), com.android.systemui.R.string.screenshot_failed_title, 0).show();
                            }
                        } else {
                            ScreenshotErrorController screenshotErrorController3 = takeScreenshotService.mScreenshotErrorController;
                            boolean isUsbMassStorageEnabled = ((StorageManager) screenshotErrorController3.context.getSystemService("storage")).isUsbMassStorageEnabled();
                            String str2 = screenshotErrorController3.TAG;
                            if (isUsbMassStorageEnabled) {
                                Log.w(str2, "getCapacityState: Usb mass storage is enabled.");
                            } else {
                                File externalStorageDirectory = Environment.getExternalStorageDirectory();
                                if (externalStorageDirectory == null) {
                                    Log.w(str2, "getCapacityState: an external storage directory is null.");
                                } else {
                                    try {
                                        StatFs statFs = new StatFs(externalStorageDirectory.getPath());
                                        long blockSizeLong = statFs.getBlockSizeLong() * statFs.getAvailableBlocksLong();
                                        if (blockSizeLong < 2097152) {
                                            Log.w(str2, "getCapacityState: availableSpace=" + blockSizeLong);
                                        } else {
                                            ScreenshotErrorController screenshotErrorController4 = takeScreenshotService.mScreenshotErrorController;
                                            Bundle bundle2 = takeScreenshotService.mBundle;
                                            int i3 = Settings.Global.getInt(screenshotErrorController4.context.getContentResolver(), "device_provisioned", 0);
                                            int i4 = bundle2.getInt("capturedOrigin", 1);
                                            if (i3 == 0 && i4 == 2) {
                                                Log.w(screenshotErrorController4.TAG, HeightInLinesModifierKt$$ExternalSyntheticOutline0.m(i3, i4, "isPalmScreenshotInSetupWizard: setUpWizardRunning=", ", origin="));
                                            } else {
                                                Context context4 = takeScreenshotService.mContext;
                                                ScreenCaptureHelper screenCaptureHelper2 = takeScreenshotService.mScreenCaptureHelper;
                                                int i5 = screenCaptureHelper2.screenCaptureType;
                                                int i6 = screenCaptureHelper2.screenCaptureOrigin;
                                                if (SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_CONTEXTSERVICE_ENABLE_SURVEY_MODE")) {
                                                    if (i5 == 1) {
                                                        if (i6 == 1) {
                                                            SemScreenshotSaLogging.sendLogForUsabilityLogging(context4, "TPKE");
                                                            SystemUIAnalytics.sendEventLog("900", SystemUIAnalytics.EID_SCREEN_CAPTURE, SystemUIAnalytics.DT_SCREEN_CAPTURE_HW_KEY);
                                                        } else if (i6 == 2) {
                                                            SemScreenshotSaLogging.sendLogForUsabilityLogging(context4, "TPPL");
                                                            SystemUIAnalytics.sendEventLog("900", SystemUIAnalytics.EID_SCREEN_CAPTURE, SystemUIAnalytics.DT_SCREEN_CAPTURE_PALM_SWIPE);
                                                        } else if (i6 == 3) {
                                                            SemScreenshotSaLogging.sendLogForUsabilityLogging(context4, "TPQP");
                                                            SystemUIAnalytics.sendEventLog("900", SystemUIAnalytics.EID_SCREEN_CAPTURE, "Quick panel");
                                                        } else if (i6 == 4) {
                                                            SemScreenshotSaLogging.sendLogForUsabilityLogging(context4, "KNFU");
                                                            SystemUIAnalytics.sendEventLog("900", SystemUIAnalytics.EID_SCREEN_CAPTURE, SystemUIAnalytics.DT_SCREEN_CAPTURE_DEX_MODE);
                                                        }
                                                    } else if (i5 == 2) {
                                                        if (i6 == 4) {
                                                            SemScreenshotSaLogging.sendLogForUsabilityLogging(context4, "KNPA");
                                                            SystemUIAnalytics.sendEventLog("900", SystemUIAnalytics.EID_SCREEN_CAPTURE, SystemUIAnalytics.DT_SCREEN_CAPTURE_DEX_MODE);
                                                        } else {
                                                            SemScreenshotSaLogging.sendLogForUsabilityLogging(context4, "TPPA");
                                                        }
                                                    } else if (i5 == 100) {
                                                        SemScreenshotSaLogging.sendLogForUsabilityLogging(context4, "TPWI");
                                                    }
                                                }
                                                Log.d("Screenshot", "Processing screenshot data");
                                                TakeScreenshotExecutorImpl takeScreenshotExecutorImpl = (TakeScreenshotExecutorImpl) takeScreenshotService.mTakeScreenshotExecutor;
                                                takeScreenshotExecutorImpl.getClass();
                                                BuildersKt.launch$default(takeScreenshotExecutorImpl.mainScope, null, null, new TakeScreenshotExecutorImpl$executeScreenshotsAsync$1(takeScreenshotExecutorImpl, screenshotRequest, requestCallbackImpl, consumer, null), 3);
                                            }
                                        }
                                    } catch (IllegalArgumentException unused) {
                                        Log.w(str2, "getCapacityState: IllegalArgumentException occurred.");
                                    }
                                }
                            }
                        }
                    }
                } else {
                    Log.w(screenshotErrorController.TAG, "Device is in shutdown state");
                }
                takeScreenshotService.logFailedRequest(screenshotRequest);
                requestCallbackImpl.reportError();
            } else {
                Log.w("Screenshot", "Skipping screenshot because storage is locked!");
                takeScreenshotService.logFailedRequest(screenshotRequest);
                takeScreenshotService.mNotificationsController.notifyScreenshotError(com.android.systemui.R.string.screenshot_failed_to_save_user_locked_text);
                requestCallbackImpl.reportError();
            }
            return true;
        }
    });

    public interface RequestCallback {
    }

    public final class RequestCallbackImpl implements RequestCallback {
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
        ScreenshotController screenshotController = takeScreenshotExecutorImpl.screenshotController;
        if (screenshotController != null) {
            SaveImageInBackgroundTask saveImageInBackgroundTask = screenshotController.mSaveInBgTask;
            if (saveImageInBackgroundTask != null) {
                saveImageInBackgroundTask.mParams.mActionsReadyListener = new ScreenshotController$$ExternalSyntheticLambda2(screenshotController, 0);
            }
            screenshotController.removeWindow();
            ScreenshotSoundController screenshotSoundController = screenshotController.mScreenshotSoundController;
            if (screenshotSoundController != null) {
                ScreenshotSoundControllerImpl screenshotSoundControllerImpl = (ScreenshotSoundControllerImpl) screenshotSoundController;
                BuildersKt.launch$default(screenshotSoundControllerImpl.coroutineScope, null, null, new ScreenshotSoundControllerImpl$releaseScreenshotSoundAsync$1(screenshotSoundControllerImpl, null), 3);
            }
            screenshotController.mBroadcastDispatcher.unregisterReceiver(screenshotController.mCopyBroadcastReceiver);
            screenshotController.mContext.release();
            screenshotController.mBgExecutor.shutdown();
        }
        takeScreenshotExecutorImpl.screenshotController = null;
    }

    @Override // android.app.Service
    public final boolean onUnbind(Intent intent) {
        ScreenshotController screenshotController = ((TakeScreenshotExecutorImpl) this.mTakeScreenshotExecutor).screenshotController;
        if (screenshotController != null) {
            screenshotController.removeWindow();
        }
        unregisterReceiver(this.mCloseSystemDialogs);
        return false;
    }

    @Override // android.app.Service
    public final void onCreate() {
    }
}
