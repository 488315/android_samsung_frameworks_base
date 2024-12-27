package com.android.systemui.biometrics;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.TaskStackListener;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.SensorPrivacyManager;
import android.hardware.biometrics.BiometricStateListener;
import android.hardware.biometrics.IBiometricContextListener;
import android.hardware.biometrics.IBiometricSysuiReceiver;
import android.hardware.biometrics.PromptInfo;
import android.hardware.display.DisplayManager;
import android.hardware.face.FaceManager;
import android.hardware.face.FaceSensorPropertiesInternal;
import android.hardware.face.IFaceAuthenticatorsRegisteredCallback;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.hardware.fingerprint.IFingerprintAuthenticatorsRegisteredCallback;
import android.hardware.fingerprint.IUdfpsRefreshRateRequestCallback;
import android.os.Handler;
import android.os.RemoteException;
import android.os.UserManager;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.DisplayUtils;
import android.util.Log;
import android.util.RotationUtils;
import android.util.SparseBooleanArray;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.WindowManager;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.compose.runtime.snapshots.SnapshotStateObserver$$ExternalSyntheticOutline0;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.os.SomeArgs;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardStatusViewController$$ExternalSyntheticOutline0;
import com.android.systemui.CoreStartable;
import com.android.systemui.Flags;
import com.android.systemui.R;
import com.android.systemui.biometrics.AuthContainerView;
import com.android.systemui.biometrics.BiometricDisplayListener;
import com.android.systemui.biometrics.domain.interactor.LogContextInteractor;
import com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl;
import com.android.systemui.biometrics.shared.model.UdfpsOverlayParams;
import com.android.systemui.biometrics.ui.binder.Spaghetti;
import com.android.systemui.biometrics.ui.view.UdfpsTouchOverlay;
import com.android.systemui.biometrics.ui.viewmodel.PromptViewModel;
import com.android.systemui.deviceentry.shared.DeviceEntryUdfpsRefactor;
import com.android.systemui.doze.DozeReceiver;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.keyguard.data.repository.BiometricType;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.Execution;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.inject.Provider;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;

public final class AuthController implements CoreStartable, ConfigurationController.ConfigurationListener, CommandQueue.Callbacks, DozeReceiver {
    public final ActivityTaskManager mActivityTaskManager;
    public boolean mAllFingerprintAuthenticatorsRegistered;
    public final CoroutineScope mApplicationCoroutineScope;
    public final DelayableExecutor mBackgroundExecutor;
    final BroadcastReceiver mBroadcastReceiver;
    public final CommandQueue mCommandQueue;
    public final Context mContext;
    public final Provider mCredentialViewModelProvider;
    AuthDialog mCurrentDialog;
    public SomeArgs mCurrentDialogArgs;
    public final Display mDisplay;
    public final Execution mExecution;
    public final SparseBooleanArray mFaceEnrolledForUser;
    public final FaceManager mFaceManager;
    public final List mFaceProps;
    public final FingerprintManager mFingerprintManager;
    public Point mFingerprintSensorLocation;
    public List mFpProps;
    public final Handler mHandler;
    public final InteractionJankMonitor mInteractionJankMonitor;
    public final LockPatternUtils mLockPatternUtils;
    public final Lazy mLogContextInteractor;
    final BiometricDisplayListener mOrientationListener;
    public final AuthDialogPanelInteractionDetector mPanelInteractionDetector;
    public final Provider mPromptSelectorInteractor;
    public final Provider mPromptViewModelProvider;
    IBiometricSysuiReceiver mReceiver;
    public final SensorPrivacyManager mSensorPrivacyManager;
    public final SparseBooleanArray mSfpsEnrolledForUser;
    public List mSidefpsProps;
    public Rect mUdfpsBounds;
    public UdfpsController mUdfpsController;
    public final Provider mUdfpsControllerFactory;
    public final SparseBooleanArray mUdfpsEnrolledForUser;
    public final Lazy mUdfpsLogger;
    public UdfpsOverlayParams mUdfpsOverlayParams;
    public List mUdfpsProps;
    public IUdfpsRefreshRateRequestCallback mUdfpsRefreshRateRequestCallback;
    public final UdfpsUtils mUdfpsUtils;
    public final UserManager mUserManager;
    public final VibratorHelper mVibratorHelper;
    public final WakefulnessLifecycle mWakefulnessLifecycle;
    public final WindowManager mWindowManager;
    public Job mBiometricContextListenerJob = null;
    public float mScaleFactor = 1.0f;
    public final Set mCallbacks = new HashSet();
    public final Map mFpEnrolledForUser = new HashMap();
    public final DisplayInfo mCachedDisplayInfo = new DisplayInfo();
    final TaskStackListener mTaskStackListener = new TaskStackListener() { // from class: com.android.systemui.biometrics.AuthController.1
        public final void onTaskStackChanged() {
            if (AuthController.this.isOwnerInForeground()) {
                return;
            }
            AuthController authController = AuthController.this;
            authController.mHandler.post(new AuthController$$ExternalSyntheticLambda3(authController, 1));
        }
    };

    /* renamed from: com.android.systemui.biometrics.AuthController$4, reason: invalid class name */
    public final class AnonymousClass4 extends BiometricStateListener {
        public AnonymousClass4() {
        }

        public final void onEnrollmentsChanged(int i, int i2, boolean z) {
            AuthController.this.mHandler.post(new AuthController$4$$ExternalSyntheticLambda0(this, i, i2, z));
        }
    }

    /* renamed from: com.android.systemui.biometrics.AuthController$5, reason: invalid class name */
    public final class AnonymousClass5 extends BiometricStateListener {
        public AnonymousClass5() {
        }

        public final void onEnrollmentsChanged(int i, int i2, boolean z) {
            AuthController.this.mHandler.post(new AuthController$4$$ExternalSyntheticLambda0(this, i, i2, z));
        }
    }

    /* renamed from: com.android.systemui.biometrics.AuthController$6, reason: invalid class name */
    public final class AnonymousClass6 extends IFingerprintAuthenticatorsRegisteredCallback.Stub {
        public AnonymousClass6() {
        }

        public final void onAllAuthenticatorsRegistered(List list) {
            AuthController.this.mHandler.post(new AuthController$6$$ExternalSyntheticLambda0(this, list));
        }
    }

    /* renamed from: com.android.systemui.biometrics.AuthController$7, reason: invalid class name */
    public final class AnonymousClass7 extends IFaceAuthenticatorsRegisteredCallback.Stub {
        public AnonymousClass7() {
        }

        public final void onAllAuthenticatorsRegistered(List list) {
            AuthController.this.mHandler.post(new AuthController$6$$ExternalSyntheticLambda0(this, list));
        }
    }

    public interface Callback {
        default void onEnrollmentsChanged(int i) {
        }

        default void onEnrollmentsChanged(BiometricType biometricType, int i, boolean z) {
        }

        default void onBiometricPromptDismissed() {
        }

        default void onBiometricPromptShown() {
        }

        default void onFingerprintLocationChanged() {
        }

        default void onAllAuthenticatorsRegistered(int i) {
        }

        default void onUdfpsLocationChanged(UdfpsOverlayParams udfpsOverlayParams) {
        }
    }

    /* renamed from: -$$Nest$mhandleEnrollmentsChanged, reason: not valid java name */
    public static void m889$$Nest$mhandleEnrollmentsChanged(AuthController authController, int i, int i2, int i3, boolean z) {
        authController.mExecution.assertIsMainThread();
        Log.d("AuthController", "handleEnrollmentsChanged, userId: " + i2 + ", sensorId: " + i3 + ", hasEnrollments: " + z);
        BiometricType biometricType = BiometricType.UNKNOWN;
        List list = authController.mFpProps;
        if (list != null) {
            Iterator it = list.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal = (FingerprintSensorPropertiesInternal) it.next();
                if (fingerprintSensorPropertiesInternal.sensorId == i3) {
                    ((HashMap) authController.mFpEnrolledForUser).put(Integer.valueOf(i2), Boolean.valueOf(z));
                    if (fingerprintSensorPropertiesInternal.isAnyUdfpsType()) {
                        biometricType = BiometricType.UNDER_DISPLAY_FINGERPRINT;
                        authController.mUdfpsEnrolledForUser.put(i2, z);
                    } else if (fingerprintSensorPropertiesInternal.isAnySidefpsType()) {
                        biometricType = BiometricType.SIDE_FINGERPRINT;
                        authController.mSfpsEnrolledForUser.put(i2, z);
                    } else if (fingerprintSensorPropertiesInternal.sensorType == 1) {
                        biometricType = BiometricType.REAR_FINGERPRINT;
                    }
                }
            }
        }
        List list2 = authController.mFaceProps;
        if (list2 == null) {
            Log.d("AuthController", "handleEnrollmentsChanged, mFaceProps is null");
        } else {
            Iterator it2 = list2.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                if (((FaceSensorPropertiesInternal) it2.next()).sensorId == i3) {
                    authController.mFaceEnrolledForUser.put(i2, z);
                    biometricType = BiometricType.FACE;
                    break;
                }
            }
        }
        Iterator it3 = ((HashSet) authController.mCallbacks).iterator();
        while (it3.hasNext()) {
            Callback callback = (Callback) it3.next();
            callback.onEnrollmentsChanged(i);
            callback.onEnrollmentsChanged(biometricType, i2, z);
        }
    }

    public AuthController(Context context, CoroutineScope coroutineScope, Execution execution, CommandQueue commandQueue, ActivityTaskManager activityTaskManager, WindowManager windowManager, FingerprintManager fingerprintManager, FaceManager faceManager, Provider provider, DisplayManager displayManager, WakefulnessLifecycle wakefulnessLifecycle, AuthDialogPanelInteractionDetector authDialogPanelInteractionDetector, UserManager userManager, LockPatternUtils lockPatternUtils, Lazy lazy, Lazy lazy2, Provider provider2, Provider provider3, Provider provider4, InteractionJankMonitor interactionJankMonitor, Handler handler, DelayableExecutor delayableExecutor, UdfpsUtils udfpsUtils, VibratorHelper vibratorHelper) {
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.biometrics.AuthController.2
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                if (PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS.equals(intent.getAction())) {
                    String stringExtra = intent.getStringExtra("reason");
                    if (stringExtra == null) {
                        stringExtra = "unknown";
                    }
                    AuthController authController = AuthController.this;
                    if (authController.isShowing()) {
                        Log.i("AuthController", "Close BP, reason :".concat(stringExtra));
                        ((AuthContainerView) authController.mCurrentDialog).animateAway(0, false);
                        authController.mCurrentDialog = null;
                        Iterator it = ((HashSet) authController.mCallbacks).iterator();
                        while (it.hasNext()) {
                            ((Callback) it.next()).onBiometricPromptDismissed();
                        }
                        try {
                            IBiometricSysuiReceiver iBiometricSysuiReceiver = authController.mReceiver;
                            if (iBiometricSysuiReceiver != null) {
                                iBiometricSysuiReceiver.onDialogDismissed(3, (byte[]) null);
                                authController.mReceiver = null;
                            }
                        } catch (RemoteException e) {
                            Log.e("AuthController", "Remote exception", e);
                        }
                    }
                }
            }
        };
        this.mBroadcastReceiver = broadcastReceiver;
        this.mContext = context;
        this.mExecution = execution;
        this.mUserManager = userManager;
        this.mLockPatternUtils = lockPatternUtils;
        this.mHandler = handler;
        this.mBackgroundExecutor = delayableExecutor;
        this.mCommandQueue = commandQueue;
        this.mActivityTaskManager = activityTaskManager;
        this.mFingerprintManager = fingerprintManager;
        this.mFaceManager = faceManager;
        this.mUdfpsControllerFactory = provider;
        this.mUdfpsLogger = lazy;
        this.mWindowManager = windowManager;
        this.mInteractionJankMonitor = interactionJankMonitor;
        this.mUdfpsEnrolledForUser = new SparseBooleanArray();
        this.mSfpsEnrolledForUser = new SparseBooleanArray();
        this.mFaceEnrolledForUser = new SparseBooleanArray();
        this.mUdfpsUtils = udfpsUtils;
        this.mApplicationCoroutineScope = coroutineScope;
        this.mVibratorHelper = vibratorHelper;
        this.mLogContextInteractor = lazy2;
        this.mPromptSelectorInteractor = provider2;
        this.mPromptViewModelProvider = provider4;
        this.mCredentialViewModelProvider = provider3;
        this.mOrientationListener = new BiometricDisplayListener(context, displayManager, handler, BiometricDisplayListener.SensorType.Generic.INSTANCE, new Function0() { // from class: com.android.systemui.biometrics.AuthController$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                AuthController authController = AuthController.this;
                authController.updateSensorLocations();
                if (authController.mCurrentDialog != null) {
                    Flags.constraintBp();
                }
                return Unit.INSTANCE;
            }
        });
        this.mWakefulnessLifecycle = wakefulnessLifecycle;
        this.mPanelInteractionDetector = authDialogPanelInteractionDetector;
        this.mFaceProps = faceManager != null ? faceManager.getSensorPropertiesInternal() : null;
        this.mDisplay = context.getDisplay();
        updateSensorLocations();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS);
        context.registerReceiver(broadcastReceiver, intentFilter, 2);
        this.mSensorPrivacyManager = (SensorPrivacyManager) context.getSystemService(SensorPrivacyManager.class);
    }

    public final void addCallback(Callback callback) {
        this.mCallbacks.add(callback);
    }

    public final void cancelIfOwnerIsNotInForeground() {
        this.mExecution.assertIsMainThread();
        AuthDialog authDialog = this.mCurrentDialog;
        if (authDialog != null) {
            try {
                ((AuthContainerView) authDialog).dismissWithoutCallback(true);
                this.mCurrentDialog = null;
                Iterator it = ((HashSet) this.mCallbacks).iterator();
                while (it.hasNext()) {
                    ((Callback) it.next()).onBiometricPromptDismissed();
                }
                IBiometricSysuiReceiver iBiometricSysuiReceiver = this.mReceiver;
                if (iBiometricSysuiReceiver != null) {
                    iBiometricSysuiReceiver.onDialogDismissed(3, (byte[]) null);
                    this.mReceiver = null;
                }
            } catch (RemoteException e) {
                Log.e("AuthController", "Remote exception", e);
            }
        }
    }

    @Override // com.android.systemui.doze.DozeReceiver
    public final void dozeTimeTick() {
        UdfpsController udfpsController = this.mUdfpsController;
        if (udfpsController != null) {
            udfpsController.dozeTimeTick();
        }
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        AuthDialog authDialog = this.mCurrentDialog;
        printWriter.println("  mCachedDisplayInfo=" + this.mCachedDisplayInfo);
        StringBuilder m = KeyguardStatusViewController$$ExternalSyntheticOutline0.m(new StringBuilder("  mScaleFactor="), this.mScaleFactor, printWriter, "  fingerprintSensorLocationInNaturalOrientation=");
        m.append(getFingerprintSensorLocationInNaturalOrientation());
        printWriter.println(m.toString());
        printWriter.println("  fingerprintSensorLocation=" + this.mFingerprintSensorLocation);
        printWriter.println("  udfpsBounds=" + this.mUdfpsBounds);
        printWriter.println("  allFingerprintAuthenticatorsRegistered=" + this.mAllFingerprintAuthenticatorsRegistered);
        printWriter.println("  currentDialog=" + authDialog);
        if (authDialog != null) {
            ((AuthContainerView) authDialog).dump(printWriter, strArr);
        }
    }

    public final IBiometricSysuiReceiver getCurrentReceiver(long j) {
        AuthDialog authDialog = this.mCurrentDialog;
        if (authDialog == null) {
            Log.w("AuthController", "shouldNotifyReceiver: dialog already gone");
            return null;
        }
        if (j != ((AuthContainerView) authDialog).mConfig.mRequestId) {
            Log.w("AuthController", "shouldNotifyReceiver: requestId doesn't match");
            return null;
        }
        if (this.mReceiver == null) {
            Log.w("AuthController", "getCurrentReceiver: Receiver is null");
        }
        return this.mReceiver;
    }

    public final Point getFingerprintSensorLocationInNaturalOrientation() {
        if (getUdfpsLocation() != null) {
            return getUdfpsLocation();
        }
        int naturalWidth = this.mCachedDisplayInfo.getNaturalWidth() / 2;
        try {
            naturalWidth = this.mContext.getResources().getDimensionPixelSize(R.dimen.physical_fingerprint_sensor_center_screen_location_x);
        } catch (Resources.NotFoundException unused) {
        }
        return new Point((int) (naturalWidth * this.mScaleFactor), (int) (this.mContext.getResources().getDimensionPixelSize(R.dimen.physical_fingerprint_sensor_center_screen_location_y) * this.mScaleFactor));
    }

    public final Point getUdfpsLocation() {
        if (this.mUdfpsController == null || this.mUdfpsBounds == null) {
            return null;
        }
        return new Point(this.mUdfpsBounds.centerX(), this.mUdfpsBounds.centerY());
    }

    public final float getUdfpsRadius() {
        Rect rect;
        if (this.mUdfpsController == null || (rect = this.mUdfpsBounds) == null) {
            return -1.0f;
        }
        return rect.height() / 2.0f;
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void hideAuthenticationDialog(long j) {
        Log.d("AuthController", "hideAuthenticationDialog: " + this.mCurrentDialog);
        AuthDialog authDialog = this.mCurrentDialog;
        if (authDialog == null) {
            Log.d("AuthController", "dialog already gone");
            return;
        }
        AuthContainerView authContainerView = (AuthContainerView) authDialog;
        if (j != authContainerView.mConfig.mRequestId) {
            StringBuilder m = SnapshotStateObserver$$ExternalSyntheticOutline0.m("ignore - ids do not match: ", j, " current: ");
            m.append(((AuthContainerView) this.mCurrentDialog).mConfig.mRequestId);
            Log.w("AuthController", m.toString());
        } else {
            authContainerView.animateAway(0, false);
            Iterator it = ((HashSet) this.mCallbacks).iterator();
            while (it.hasNext()) {
                ((Callback) it.next()).onBiometricPromptDismissed();
            }
            this.mCurrentDialog = null;
        }
    }

    public final boolean isOwnerInForeground() {
        AuthDialog authDialog = this.mCurrentDialog;
        if (authDialog != null) {
            String str = ((AuthContainerView) authDialog).mConfig.mOpPackageName;
            List tasks = this.mActivityTaskManager.getTasks(1);
            if (!tasks.isEmpty()) {
                String packageName = ((ActivityManager.RunningTaskInfo) tasks.get(0)).topActivity.getPackageName();
                if (!packageName.contentEquals(str)) {
                    Context context = this.mContext;
                    int i = Utils.$r8$clinit;
                    if (context.checkCallingOrSelfPermission("android.permission.USE_BIOMETRIC_INTERNAL") != 0 || !"android".equals(str)) {
                        Log.w("AuthController", "Evicting client due to: ".concat(packageName));
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public final boolean isShowing() {
        return this.mCurrentDialog != null;
    }

    public final boolean isUdfpsEnrolled(int i) {
        if (this.mUdfpsController == null) {
            return false;
        }
        return this.mUdfpsEnrolledForUser.get(i);
    }

    public final boolean isUdfpsSupported() {
        List list = this.mUdfpsProps;
        return (list == null || list.isEmpty()) ? false : true;
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void onBiometricAuthenticated(int i) {
        Log.d("AuthController", "onBiometricAuthenticated: ");
        AuthDialog authDialog = this.mCurrentDialog;
        if (authDialog == null) {
            Log.w("AuthController", "onBiometricAuthenticated callback but dialog gone");
            return;
        }
        Spaghetti spaghetti = ((AuthContainerView) authDialog).mBiometricView;
        if (spaghetti != null) {
            spaghetti.onAuthenticationSucceeded(i);
        } else {
            Log.e("AuthContainerView", "onAuthenticationSucceeded(): mBiometricView is null");
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void onBiometricError(final int i, int i2, int i3) {
        int i4;
        Log.d("AuthController", String.format("onBiometricError(%d, %d, %d)", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3)));
        boolean z = true;
        boolean z2 = i2 == 7 || i2 == 9;
        boolean z3 = i2 == 1 && this.mSensorPrivacyManager.isSensorPrivacyEnabled(1, 2);
        if (i2 != 100 && i2 != 3 && i2 != 16 && !z3) {
            z = false;
        }
        AuthDialog authDialog = this.mCurrentDialog;
        if (authDialog == null) {
            Log.w("AuthController", "onBiometricError callback but dialog is gone");
            return;
        }
        if (Utils.isDeviceCredentialAllowed(((AuthContainerView) authDialog).mConfig.mPromptInfo) && z2) {
            Log.d("AuthController", "onBiometricError, lockout");
            Spaghetti spaghetti = ((AuthContainerView) this.mCurrentDialog).mBiometricView;
            if (spaghetti != null) {
                spaghetti.startTransitionToCredentialUI();
                return;
            } else {
                Log.e("AuthContainerView", "animateToCredentialUI(): mBiometricView is null");
                return;
            }
        }
        String str = "";
        if (!z) {
            if (i == 2) {
                str = FingerprintManager.getErrorString(this.mContext, i2, i3);
            } else if (i == 8) {
                str = FaceManager.getErrorString(this.mContext, i2, i3);
            }
            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("onBiometricError, hard error: ", str, "AuthController");
            Spaghetti spaghetti2 = ((AuthContainerView) this.mCurrentDialog).mBiometricView;
            if (spaghetti2 != null) {
                spaghetti2.onError(i, str);
                return;
            } else {
                Log.e("AuthContainerView", "onError(): mBiometricView is null");
                return;
            }
        }
        if (i2 == 100 || i2 == 3) {
            int i5 = this.mCurrentDialogArgs.argi1;
            if (this.mFaceProps != null ? this.mFaceEnrolledForUser.get(i5) : false) {
                if (((Boolean) ((HashMap) this.mFpEnrolledForUser).getOrDefault(Integer.valueOf(i5), Boolean.FALSE)).booleanValue()) {
                    i4 = i == 8 ? android.R.string.lockscreen_sound_on_label : android.R.string.lockscreen_transport_play_description;
                    str = this.mContext.getString(i4);
                }
            }
            i4 = android.R.string.config_defaultSupervisionProfileOwnerComponent;
            str = this.mContext.getString(i4);
        } else if (i == 2) {
            str = FingerprintManager.getErrorString(this.mContext, i2, i3);
        } else if (i == 8) {
            str = FaceManager.getErrorString(this.mContext, i2, i3);
        }
        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("onBiometricError, soft error: ", str, "AuthController");
        if (z3) {
            this.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.biometrics.AuthController$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    AuthController authController = AuthController.this;
                    int i6 = i;
                    AuthDialog authDialog2 = authController.mCurrentDialog;
                    String string = authController.mContext.getString(android.R.string.lockscreen_access_pattern_cell_added_verbose);
                    AuthContainerView authContainerView = (AuthContainerView) authDialog2;
                    if (authContainerView.mBiometricView == null) {
                        Log.e("AuthContainerView", "onAuthenticationFailed(): mBiometricView is null");
                        return;
                    }
                    ((HashSet) authContainerView.mFailedModalities).add(Integer.valueOf(i6));
                    authContainerView.mBiometricView.onAuthenticationFailed(i6, string);
                }
            }, 500L);
            return;
        }
        AuthContainerView authContainerView = (AuthContainerView) this.mCurrentDialog;
        if (authContainerView.mBiometricView == null) {
            Log.e("AuthContainerView", "onAuthenticationFailed(): mBiometricView is null");
        } else {
            ((HashSet) authContainerView.mFailedModalities).add(Integer.valueOf(i));
            authContainerView.mBiometricView.onAuthenticationFailed(i, str);
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void onBiometricHelp(int i, String str) {
        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("onBiometricHelp: ", str, "AuthController");
        AuthDialog authDialog = this.mCurrentDialog;
        if (authDialog == null) {
            Log.w("AuthController", "onBiometricHelp callback but dialog gone");
            return;
        }
        Spaghetti spaghetti = ((AuthContainerView) authDialog).mBiometricView;
        if (spaghetti != null) {
            spaghetti.onHelp(i, str);
        } else {
            Log.e("AuthContainerView", "onHelp(): mBiometricView is null");
        }
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onConfigChanged(Configuration configuration) {
        updateSensorLocations();
        AuthDialog authDialog = this.mCurrentDialog;
        if (authDialog != null) {
            PromptViewModel promptViewModel = ((AuthContainerView) authDialog).mPromptViewModel;
            ((AuthContainerView) authDialog).dismissWithoutCallback(false);
            this.mCurrentDialog = null;
            showDialog(this.mCurrentDialogArgs, true, promptViewModel);
        }
    }

    public final void removeCallback(Callback callback) {
        ((HashSet) this.mCallbacks).remove(callback);
    }

    public Point rotateToCurrentOrientation(Point point, DisplayInfo displayInfo) {
        RotationUtils.rotatePoint(point, displayInfo.rotation, displayInfo.getNaturalWidth(), displayInfo.getNaturalHeight());
        return point;
    }

    public final void sendResultAndCleanUp(int i, byte[] bArr) {
        IBiometricSysuiReceiver iBiometricSysuiReceiver = this.mReceiver;
        if (iBiometricSysuiReceiver == null) {
            Log.e("AuthController", "sendResultAndCleanUp: Receiver is null");
            return;
        }
        try {
            iBiometricSysuiReceiver.onDialogDismissed(i, bArr);
        } catch (RemoteException e) {
            Log.w("AuthController", "Remote exception", e);
        }
        ListPopupWindow$$ExternalSyntheticOutline0.m(i, "onDialogDismissed: ", "AuthController");
        if (this.mCurrentDialog == null) {
            Log.w("AuthController", "Dialog already dismissed");
        }
        Iterator it = ((HashSet) this.mCallbacks).iterator();
        while (it.hasNext()) {
            ((Callback) it.next()).onBiometricPromptDismissed();
        }
        this.mReceiver = null;
        this.mCurrentDialog = null;
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void setBiometricContextListener(IBiometricContextListener iBiometricContextListener) {
        Job job = this.mBiometricContextListenerJob;
        if (job != null) {
            job.cancel(null);
        }
        this.mBiometricContextListenerJob = ((LogContextInteractorImpl) ((LogContextInteractor) this.mLogContextInteractor.get())).addBiometricContextListener(iBiometricContextListener);
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void setUdfpsRefreshRateCallback(IUdfpsRefreshRateRequestCallback iUdfpsRefreshRateRequestCallback) {
        this.mUdfpsRefreshRateRequestCallback = iUdfpsRefreshRateRequestCallback;
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void showAuthenticationDialog(PromptInfo promptInfo, IBiometricSysuiReceiver iBiometricSysuiReceiver, int[] iArr, boolean z, boolean z2, int i, long j, String str, long j2) {
        int authenticators = promptInfo.getAuthenticators();
        StringBuilder sb = new StringBuilder();
        boolean z3 = false;
        for (int i2 : iArr) {
            sb.append(i2);
            sb.append(" ");
        }
        StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(authenticators, "showAuthenticationDialog, authenticators: ", ", sensorIds: ");
        m.append(sb.toString());
        m.append(", credentialAllowed: ");
        m.append(z);
        m.append(", requireConfirmation: ");
        m.append(z2);
        m.append(", operationId: ");
        m.append(j);
        m.append(", requestId: ");
        m.append(j2);
        Log.d("AuthController", m.toString());
        SomeArgs obtain = SomeArgs.obtain();
        obtain.arg1 = promptInfo;
        obtain.arg2 = iBiometricSysuiReceiver;
        obtain.arg3 = iArr;
        obtain.arg4 = Boolean.valueOf(z);
        obtain.arg5 = Boolean.valueOf(z2);
        obtain.argi1 = i;
        obtain.arg6 = str;
        obtain.argl1 = j;
        obtain.argl2 = j2;
        if (this.mCurrentDialog != null) {
            Log.w("AuthController", "mCurrentDialog: " + this.mCurrentDialog);
            z3 = true;
        }
        showDialog(obtain, z3, (PromptViewModel) this.mPromptViewModelProvider.get());
    }

    public final void showDialog(SomeArgs someArgs, boolean z, PromptViewModel promptViewModel) {
        this.mCurrentDialogArgs = someArgs;
        PromptInfo promptInfo = (PromptInfo) someArgs.arg1;
        int[] iArr = (int[]) someArgs.arg3;
        ((Boolean) someArgs.arg4).getClass();
        boolean booleanValue = ((Boolean) someArgs.arg5).booleanValue();
        int i = someArgs.argi1;
        String str = (String) someArgs.arg6;
        long j = someArgs.argl1;
        long j2 = someArgs.argl2;
        UserManager userManager = this.mUserManager;
        LockPatternUtils lockPatternUtils = this.mLockPatternUtils;
        AuthContainerView.Config config = new AuthContainerView.Config();
        config.mContext = this.mContext;
        config.mCallback = this;
        config.mPromptInfo = promptInfo;
        config.mRequireConfirmation = booleanValue;
        config.mUserId = i;
        config.mOpPackageName = str;
        config.mSkipIntro = z;
        config.mOperationId = j;
        config.mRequestId = j2;
        config.mSensorIds = iArr;
        new Object(this) { // from class: com.android.systemui.biometrics.AuthController$$ExternalSyntheticLambda1
            public final /* synthetic */ AuthController f$0;
        };
        AuthContainerView authContainerView = new AuthContainerView(config, this.mApplicationCoroutineScope, this.mFpProps, this.mFaceProps, this.mWakefulnessLifecycle, this.mPanelInteractionDetector, userManager, lockPatternUtils, this.mInteractionJankMonitor, this.mPromptSelectorInteractor, promptViewModel, this.mCredentialViewModelProvider, this.mBackgroundExecutor, this.mVibratorHelper);
        StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "userId: ", " mCurrentDialog: ");
        m.append(this.mCurrentDialog);
        m.append(" newDialog: ");
        m.append(authContainerView);
        Log.d("AuthController", m.toString());
        AuthDialog authDialog = this.mCurrentDialog;
        if (authDialog != null) {
            ((AuthContainerView) authDialog).dismissWithoutCallback(false);
        }
        this.mReceiver = (IBiometricSysuiReceiver) someArgs.arg2;
        Iterator it = ((HashSet) this.mCallbacks).iterator();
        while (it.hasNext()) {
            ((Callback) it.next()).onBiometricPromptShown();
        }
        this.mCurrentDialog = authContainerView;
        if (!promptInfo.isAllowBackgroundAuthentication() && !isOwnerInForeground()) {
            cancelIfOwnerIsNotInForeground();
            return;
        }
        AuthContainerView authContainerView2 = (AuthContainerView) this.mCurrentDialog;
        this.mWindowManager.addView(authContainerView2, AuthContainerView.getLayoutParams(authContainerView2.mWindowToken, authContainerView2.mConfig.mPromptInfo.getTitle()));
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        this.mCommandQueue.addCallback((CommandQueue.Callbacks) this);
        FingerprintManager fingerprintManager = this.mFingerprintManager;
        if (fingerprintManager != null) {
            fingerprintManager.addAuthenticatorsRegisteredCallback(new AnonymousClass6());
        }
        FaceManager faceManager = this.mFaceManager;
        if (faceManager != null) {
            faceManager.addAuthenticatorsRegisteredCallback(new AnonymousClass7());
        }
        this.mActivityTaskManager.registerTaskStackListener(this.mTaskStackListener);
        BiometricDisplayListener biometricDisplayListener = this.mOrientationListener;
        Display display = biometricDisplayListener.context.getDisplay();
        if (display != null) {
            display.getDisplayInfo(biometricDisplayListener.cachedDisplayInfo);
        }
        biometricDisplayListener.displayManager.registerDisplayListener(biometricDisplayListener, biometricDisplayListener.handler, 4L);
        updateSensorLocations();
    }

    public final void updateSensorLocations() {
        this.mDisplay.getDisplayInfo(this.mCachedDisplayInfo);
        DisplayInfo displayInfo = this.mCachedDisplayInfo;
        this.mUdfpsUtils.getClass();
        Display.Mode maximumResolutionDisplayMode = DisplayUtils.getMaximumResolutionDisplayMode(displayInfo.supportedModes);
        float physicalPixelDisplaySizeRatio = DisplayUtils.getPhysicalPixelDisplaySizeRatio(maximumResolutionDisplayMode.getPhysicalWidth(), maximumResolutionDisplayMode.getPhysicalHeight(), displayInfo.getNaturalWidth(), displayInfo.getNaturalHeight());
        if (physicalPixelDisplaySizeRatio == Float.POSITIVE_INFINITY) {
            physicalPixelDisplaySizeRatio = 1.0f;
        }
        this.mScaleFactor = physicalPixelDisplaySizeRatio;
        updateUdfpsLocation();
        if (this.mFpProps == null) {
            this.mFingerprintSensorLocation = null;
        } else {
            this.mFingerprintSensorLocation = rotateToCurrentOrientation(getFingerprintSensorLocationInNaturalOrientation(), this.mCachedDisplayInfo);
        }
        Iterator it = this.mCallbacks.iterator();
        while (it.hasNext()) {
            ((Callback) it.next()).onFingerprintLocationChanged();
        }
    }

    public final void updateUdfpsLocation() {
        if (this.mUdfpsController != null) {
            FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal = (FingerprintSensorPropertiesInternal) this.mUdfpsProps.get(0);
            Rect rect = this.mUdfpsBounds;
            UdfpsOverlayParams udfpsOverlayParams = this.mUdfpsOverlayParams;
            Rect rect2 = fingerprintSensorPropertiesInternal.getLocation().getRect();
            this.mUdfpsBounds = rect2;
            rect2.scale(this.mScaleFactor);
            UdfpsOverlayParams udfpsOverlayParams2 = new UdfpsOverlayParams(this.mUdfpsBounds, new Rect(0, this.mCachedDisplayInfo.getNaturalHeight() / 2, this.mCachedDisplayInfo.getNaturalWidth(), this.mCachedDisplayInfo.getNaturalHeight()), this.mCachedDisplayInfo.getNaturalWidth(), this.mCachedDisplayInfo.getNaturalHeight(), this.mScaleFactor, this.mCachedDisplayInfo.rotation, fingerprintSensorPropertiesInternal.sensorType);
            this.mUdfpsOverlayParams = udfpsOverlayParams2;
            UdfpsController udfpsController = this.mUdfpsController;
            if (udfpsController.mSensorProps.sensorId != fingerprintSensorPropertiesInternal.sensorId) {
                udfpsController.mSensorProps = fingerprintSensorPropertiesInternal;
                Log.w("UdfpsController", "updateUdfpsParams | sensorId has changed");
            }
            if (!udfpsController.mOverlayParams.equals(udfpsOverlayParams2)) {
                udfpsController.mOverlayParams = udfpsOverlayParams2;
                DeviceEntryUdfpsRefactor deviceEntryUdfpsRefactor = DeviceEntryUdfpsRefactor.INSTANCE;
                Flags.deviceEntryUdfpsRefactor();
                UdfpsControllerOverlay udfpsControllerOverlay = udfpsController.mOverlay;
                if (udfpsControllerOverlay != null && udfpsControllerOverlay.requestReason == 4) {
                    UdfpsOverlayParams udfpsOverlayParams3 = udfpsController.mOverlayParams;
                    RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                    Flags.deviceEntryUdfpsRefactor();
                    udfpsControllerOverlay.overlayParams = udfpsOverlayParams3;
                    udfpsControllerOverlay.sensorBounds = udfpsOverlayParams3.sensorBounds;
                    Flags.deviceEntryUdfpsRefactor();
                    UdfpsTouchOverlay udfpsTouchOverlay = udfpsControllerOverlay.overlayTouchView;
                    if (udfpsTouchOverlay != null && udfpsControllerOverlay.addViewRunnable == null) {
                        WindowManager windowManager = udfpsControllerOverlay.windowManager;
                        WindowManager.LayoutParams layoutParams = udfpsControllerOverlay.coreLayoutParams;
                        udfpsControllerOverlay.updateDimensions(layoutParams, null);
                        windowManager.updateViewLayout(udfpsTouchOverlay, layoutParams);
                    }
                } else if (udfpsControllerOverlay != null) {
                    udfpsController.hideUdfpsOverlay();
                    udfpsController.showUdfpsOverlay(udfpsControllerOverlay);
                }
            }
            if (Objects.equals(rect, this.mUdfpsBounds) && Objects.equals(udfpsOverlayParams, this.mUdfpsOverlayParams)) {
                return;
            }
            Iterator it = ((HashSet) this.mCallbacks).iterator();
            while (it.hasNext()) {
                ((Callback) it.next()).onUdfpsLocationChanged(this.mUdfpsOverlayParams);
            }
        }
    }
}
