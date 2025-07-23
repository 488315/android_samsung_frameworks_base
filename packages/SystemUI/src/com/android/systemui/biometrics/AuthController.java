package com.android.systemui.biometrics;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.KeyguardManager;
import android.app.TaskStackListener;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
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
import android.os.UserHandle;
import android.os.UserManager;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.DisplayUtils;
import android.util.Log;
import android.util.RotationUtils;
import android.util.SparseBooleanArray;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.WindowManager;
import androidx.compose.runtime.snapshots.SnapshotStateObserver$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.os.SomeArgs;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.ClockEventController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.CoreStartable;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.accessibility.MagnificationImpl$$ExternalSyntheticOutline0;
import com.android.systemui.biometrics.AuthContainerView;
import com.android.systemui.biometrics.BiometricDisplayListener;
import com.android.systemui.biometrics.domain.interactor.LogContextInteractor;
import com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl;
import com.android.systemui.biometrics.plugins.AuthContextPlugins;
import com.android.systemui.biometrics.shared.model.PromptKind;
import com.android.systemui.biometrics.shared.model.UdfpsOverlayParams;
import com.android.systemui.biometrics.ui.binder.Spaghetti;
import com.android.systemui.biometrics.ui.view.UdfpsTouchOverlay;
import com.android.systemui.biometrics.ui.viewmodel.PromptViewModel;
import com.android.systemui.doze.DozeReceiver;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.keyguard.data.repository.BiometricType;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.Execution;
import com.android.systemui.utils.windowmanager.WindowManagerProvider;
import com.android.systemui.utils.windowmanager.WindowManagerProviderImpl;
import com.android.systemui.utils.windowmanager.WindowManagerUtils;
import com.google.android.msdl.domain.MSDLPlayer;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import javax.inject.Provider;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class AuthController implements CoreStartable, ConfigurationController.ConfigurationListener, CommandQueue.Callbacks, DozeReceiver {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ActivityTaskManager mActivityTaskManager;
    public boolean mAllFingerprintAuthenticatorsRegistered;
    public final CoroutineScope mApplicationCoroutineScope;
    public final DelayableExecutor mBackgroundExecutor;
    final BroadcastReceiver mBroadcastReceiver;
    public final CommandQueue mCommandQueue;
    public final Context mContext;
    public final Provider mCredentialViewModelProvider;
    AuthContainerView mCurrentDialog;
    public SomeArgs mCurrentDialogArgs;
    public final Display mDisplay;
    public final DisplayManager mDisplayManager;
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
    public final MSDLPlayer mMSDLPlayer;
    final BiometricDisplayListener mOrientationListener;
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
    public final WindowManagerProvider mWindowManagerProvider;
    public StandaloneCoroutine mBiometricContextListenerJob = null;
    public float mScaleFactor = 1.0f;
    public final Set mCallbacks = new HashSet();
    public final Map mFpEnrolledForUser = new HashMap();
    public final DisplayInfo mCachedDisplayInfo = new DisplayInfo();
    final TaskStackListener mTaskStackListener = new AnonymousClass1();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.biometrics.AuthController$1, reason: invalid class name */
    public class AnonymousClass1 extends TaskStackListener {
        public static final /* synthetic */ int $r8$clinit = 0;

        public AnonymousClass1() {
        }

        public final void onTaskStackChanged() {
            AuthController authController = AuthController.this;
            int i = AuthController.$r8$clinit;
            if (authController.isOwnerInBackground()) {
                AuthController authController2 = AuthController.this;
                authController2.mHandler.post(new AuthController$$ExternalSyntheticLambda3(authController2, 1));
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.biometrics.AuthController$4, reason: invalid class name */
    public class AnonymousClass4 extends BiometricStateListener {
        public AnonymousClass4() {
        }

        public final void onEnrollmentsChanged(int i, int i2, boolean z) {
            AuthController.this.mHandler.post(new AuthController$4$$ExternalSyntheticLambda0(this, i, i2, z));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.biometrics.AuthController$5, reason: invalid class name */
    public class AnonymousClass5 extends BiometricStateListener {
        public AnonymousClass5() {
        }

        public final void onEnrollmentsChanged(int i, int i2, boolean z) {
            AuthController.this.mHandler.post(new AuthController$4$$ExternalSyntheticLambda0(this, i, i2, z));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.biometrics.AuthController$6, reason: invalid class name */
    public class AnonymousClass6 extends IFingerprintAuthenticatorsRegisteredCallback.Stub {
        public AnonymousClass6() {
        }

        public final void onAllAuthenticatorsRegistered(List list) {
            AuthController.this.mHandler.post(new AuthController$6$$ExternalSyntheticLambda0(this, list));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.biometrics.AuthController$7, reason: invalid class name */
    public class AnonymousClass7 extends IFaceAuthenticatorsRegisteredCallback.Stub {
        public AnonymousClass7() {
        }

        public final void onAllAuthenticatorsRegistered(List list) {
            AuthController.this.mHandler.post(new AuthController$6$$ExternalSyntheticLambda0(this, list));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    public static void m1017$$Nest$mhandleEnrollmentsChanged(AuthController authController, int i, int i2, int i3, boolean z) {
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

    public AuthController(Context context, CoroutineScope coroutineScope, Execution execution, CommandQueue commandQueue, ActivityTaskManager activityTaskManager, WindowManager windowManager, FingerprintManager fingerprintManager, FaceManager faceManager, Optional<AuthContextPlugins> optional, Provider provider, DisplayManager displayManager, WakefulnessLifecycle wakefulnessLifecycle, UserManager userManager, LockPatternUtils lockPatternUtils, Lazy lazy, Lazy lazy2, Provider provider2, Provider provider3, Provider provider4, InteractionJankMonitor interactionJankMonitor, Handler handler, DelayableExecutor delayableExecutor, UdfpsUtils udfpsUtils, VibratorHelper vibratorHelper, KeyguardManager keyguardManager, MSDLPlayer mSDLPlayer, WindowManagerProvider windowManagerProvider) {
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.biometrics.AuthController.2
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                if (PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS.equals(intent.getAction())) {
                    String stringExtra = intent.getStringExtra("reason");
                    if (stringExtra == null) {
                        stringExtra = "unknown";
                    }
                    AuthController authController = AuthController.this;
                    int i = AuthController.$r8$clinit;
                    authController.closeDialog(3, stringExtra);
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
        this.mDisplayManager = displayManager;
        this.mWindowManager = windowManager;
        this.mInteractionJankMonitor = interactionJankMonitor;
        this.mUdfpsEnrolledForUser = new SparseBooleanArray();
        this.mSfpsEnrolledForUser = new SparseBooleanArray();
        this.mFaceEnrolledForUser = new SparseBooleanArray();
        this.mUdfpsUtils = udfpsUtils;
        this.mApplicationCoroutineScope = coroutineScope;
        this.mVibratorHelper = vibratorHelper;
        this.mMSDLPlayer = mSDLPlayer;
        this.mLogContextInteractor = lazy2;
        this.mPromptSelectorInteractor = provider2;
        this.mPromptViewModelProvider = provider4;
        this.mCredentialViewModelProvider = provider3;
        keyguardManager.addKeyguardLockedStateListener(context.getMainExecutor(), new KeyguardManager.KeyguardLockedStateListener() { // from class: com.android.systemui.biometrics.AuthController$$ExternalSyntheticLambda0
            @Override // android.app.KeyguardManager.KeyguardLockedStateListener
            public final void onKeyguardLockedStateChanged(boolean z) {
                AuthController authController = AuthController.this;
                int i = AuthController.$r8$clinit;
                if (z) {
                    authController.closeDialog(3, "Device lock");
                } else {
                    authController.getClass();
                }
            }
        });
        this.mOrientationListener = new BiometricDisplayListener(context, displayManager, handler, BiometricDisplayListener.SensorType.Generic.INSTANCE, new Function0() { // from class: com.android.systemui.biometrics.AuthController$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i = AuthController.$r8$clinit;
                AuthController.this.updateSensorLocations();
                return Unit.INSTANCE;
            }
        });
        this.mWakefulnessLifecycle = wakefulnessLifecycle;
        this.mFaceProps = faceManager != null ? faceManager.getSensorPropertiesInternal() : null;
        this.mDisplay = context.getDisplay();
        updateSensorLocations();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS);
        context.registerReceiver(broadcastReceiver, intentFilter, 2);
        this.mSensorPrivacyManager = (SensorPrivacyManager) context.getSystemService(SensorPrivacyManager.class);
        this.mWindowManagerProvider = windowManagerProvider;
    }

    public final void addCallback(Callback callback) {
        this.mCallbacks.add(callback);
    }

    public final void closeDialog(int i, String str) {
        if (isShowing()) {
            Log.i("AuthController", "Close BP, reason :".concat(str));
            this.mCurrentDialog.animateAway(0, false);
            this.mCurrentDialog = null;
            Iterator it = ((HashSet) this.mCallbacks).iterator();
            while (it.hasNext()) {
                ((Callback) it.next()).onBiometricPromptDismissed();
            }
            try {
                IBiometricSysuiReceiver iBiometricSysuiReceiver = this.mReceiver;
                if (iBiometricSysuiReceiver != null) {
                    iBiometricSysuiReceiver.onDialogDismissed(i, (byte[]) null);
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
            udfpsController.getClass();
        }
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        AuthContainerView authContainerView = this.mCurrentDialog;
        printWriter.println("  mCachedDisplayInfo=" + this.mCachedDisplayInfo);
        StringBuilder m = MagnificationImpl$$ExternalSyntheticOutline0.m(new StringBuilder("  mScaleFactor="), this.mScaleFactor, printWriter, "  fingerprintSensorLocationInNaturalOrientation=");
        m.append(getFingerprintSensorLocationInNaturalOrientation());
        printWriter.println(m.toString());
        printWriter.println("  fingerprintSensorLocation=" + this.mFingerprintSensorLocation);
        printWriter.println("  udfpsBounds=" + this.mUdfpsBounds);
        printWriter.println("  allFingerprintAuthenticatorsRegistered=" + this.mAllFingerprintAuthenticatorsRegistered);
        printWriter.println("  currentDialog=" + authContainerView);
        if (authContainerView != null) {
            printWriter.println("    isAttachedToWindow=" + authContainerView.isAttachedToWindow());
            StringBuilder m2 = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("    containerState="), authContainerView.mContainerState, printWriter, "    pendingCallbackReason=");
            m2.append(authContainerView.mPendingCallbackReason);
            printWriter.println(m2.toString());
            KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("    config exist="), authContainerView.mConfig != null, printWriter);
            if (authContainerView.mConfig != null) {
                KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("    config.sensorIds exist="), authContainerView.mConfig.mSensorIds != null, printWriter);
            }
        }
    }

    public final IBiometricSysuiReceiver getCurrentReceiver(long j) {
        AuthContainerView authContainerView = this.mCurrentDialog;
        if (authContainerView == null) {
            Log.w("AuthController", "shouldNotifyReceiver: dialog already gone");
            return null;
        }
        if (j != authContainerView.mConfig.mRequestId) {
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

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void hideAuthenticationDialog(long j) {
        Log.d("AuthController", "hideAuthenticationDialog: " + this.mCurrentDialog);
        AuthContainerView authContainerView = this.mCurrentDialog;
        if (authContainerView == null) {
            Log.d("AuthController", "dialog already gone");
            return;
        }
        if (j != authContainerView.mConfig.mRequestId) {
            StringBuilder m = SnapshotStateObserver$$ExternalSyntheticOutline0.m("ignore - ids do not match: ", j, " current: ");
            m.append(this.mCurrentDialog.mConfig.mRequestId);
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

    public final boolean isOwnerInBackground() {
        AuthContainerView authContainerView = this.mCurrentDialog;
        boolean z = false;
        if (authContainerView != null) {
            AuthContainerView.Config config = authContainerView.mConfig;
            String str = config.mOpPackageName;
            String classNameIfItIsConfirmDeviceCredentialActivity = config.mPromptInfo.getClassNameIfItIsConfirmDeviceCredentialActivity();
            ActivityTaskManager activityTaskManager = this.mActivityTaskManager;
            Context context = this.mContext;
            int i = Utils.$r8$clinit;
            List tasks = activityTaskManager.getTasks(Integer.MAX_VALUE);
            if (tasks == null || tasks.isEmpty()) {
                Log.w("SysUIBiometricUtils", "No running tasks reported");
            } else {
                ComponentName componentName = ((ActivityManager.RunningTaskInfo) tasks.get(0)).topActivity;
                boolean z2 = context.checkCallingOrSelfPermission("android.permission.USE_BIOMETRIC_INTERNAL") == 0 && "android".equals(str);
                componentName.getClass();
                boolean areEqual = Intrinsics.areEqual(componentName.getPackageName(), str);
                boolean z3 = classNameIfItIsConfirmDeviceCredentialActivity != null;
                if ((!z2 && !areEqual) || (z3 && !Intrinsics.areEqual(componentName.getClassName(), classNameIfItIsConfirmDeviceCredentialActivity))) {
                    z = true;
                }
            }
            if (z) {
                MotionLayout$$ExternalSyntheticOutline0.m("Evicting client due to top activity is not : ", str, "AuthController");
            }
        }
        return z;
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
        AuthContainerView authContainerView = this.mCurrentDialog;
        if (authContainerView == null) {
            Log.w("AuthController", "onBiometricAuthenticated callback but dialog gone");
            return;
        }
        Spaghetti spaghetti = authContainerView.mBiometricView;
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
        AuthContainerView authContainerView = this.mCurrentDialog;
        if (authContainerView == null) {
            Log.w("AuthController", "onBiometricError callback but dialog is gone");
            return;
        }
        if (Utils.isDeviceCredentialAllowed(authContainerView.mConfig.mPromptInfo) && z2) {
            Log.d("AuthController", "onBiometricError, lockout");
            Spaghetti spaghetti = this.mCurrentDialog.mBiometricView;
            if (spaghetti == null) {
                Log.e("AuthContainerView", "animateToCredentialUI(): mBiometricView is null");
                return;
            }
            spaghetti.viewModel.onSwitchToCredential();
            Spaghetti.Callback callback = spaghetti.legacyCallback;
            if (callback != null) {
                ((AuthContainerView.BiometricCallback) callback).onUseDeviceCredential();
                return;
            }
            return;
        }
        String str = "";
        if (!z) {
            if (i == 2) {
                str = FingerprintManager.getErrorString(this.mContext, i2, i3);
            } else if (i == 8) {
                str = FaceManager.getErrorString(this.mContext, i2, i3);
            }
            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("onBiometricError, hard error: ", str, "AuthController");
            Spaghetti spaghetti2 = this.mCurrentDialog.mBiometricView;
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
                    i4 = i == 8 ? android.R.string.mediasize_chinese_prc_7 : android.R.string.mediasize_iso_a3;
                    str = this.mContext.getString(i4);
                }
            }
            i4 = android.R.string.config_displayWhiteBalanceColorTemperatureSensorName;
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
                    AuthContainerView authContainerView2 = authController.mCurrentDialog;
                    String string = authController.mContext.getString(android.R.string.lockscreen_transport_pause_description);
                    if (authContainerView2.mBiometricView == null) {
                        Log.e("AuthContainerView", "onAuthenticationFailed(): mBiometricView is null");
                        return;
                    }
                    ((HashSet) authContainerView2.mFailedModalities).add(Integer.valueOf(i6));
                    authContainerView2.mBiometricView.onAuthenticationFailed(i6, string);
                }
            }, 500L);
            return;
        }
        AuthContainerView authContainerView2 = this.mCurrentDialog;
        if (authContainerView2.mBiometricView == null) {
            Log.e("AuthContainerView", "onAuthenticationFailed(): mBiometricView is null");
        } else {
            ((HashSet) authContainerView2.mFailedModalities).add(Integer.valueOf(i));
            authContainerView2.mBiometricView.onAuthenticationFailed(i, str);
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void onBiometricHelp(int i, String str) {
        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("onBiometricHelp: ", str, "AuthController");
        AuthContainerView authContainerView = this.mCurrentDialog;
        if (authContainerView == null) {
            Log.w("AuthController", "onBiometricHelp callback but dialog gone");
            return;
        }
        Spaghetti spaghetti = authContainerView.mBiometricView;
        if (spaghetti != null) {
            spaghetti.onHelp(i, str);
        } else {
            Log.e("AuthContainerView", "onHelp(): mBiometricView is null");
        }
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onConfigChanged(Configuration configuration) {
        updateSensorLocations();
        AuthContainerView authContainerView = this.mCurrentDialog;
        if (authContainerView != null) {
            PromptViewModel promptViewModel = authContainerView.mPromptViewModel;
            authContainerView.dismissWithoutCallback();
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

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void setBiometricContextListener(IBiometricContextListener iBiometricContextListener) {
        StandaloneCoroutine standaloneCoroutine = this.mBiometricContextListenerJob;
        if (standaloneCoroutine != null) {
            standaloneCoroutine.cancel(null);
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
        WindowManager windowManager;
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
        AuthContainerView authContainerView = new AuthContainerView(config, this.mApplicationCoroutineScope, this.mFpProps, this.mFaceProps, this.mWakefulnessLifecycle, userManager, null, lockPatternUtils, this.mInteractionJankMonitor, this.mPromptSelectorInteractor, promptViewModel, this.mCredentialViewModelProvider, this.mBackgroundExecutor, this.mVibratorHelper, this.mMSDLPlayer);
        StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "userId: ", " mCurrentDialog: ");
        m.append(this.mCurrentDialog);
        m.append(" newDialog: ");
        m.append(authContainerView);
        Log.d("AuthController", m.toString());
        AuthContainerView authContainerView2 = this.mCurrentDialog;
        if (authContainerView2 != null) {
            authContainerView2.dismissWithoutCallback();
        }
        this.mReceiver = (IBiometricSysuiReceiver) someArgs.arg2;
        Iterator it = ((HashSet) this.mCallbacks).iterator();
        while (it.hasNext()) {
            ((Callback) it.next()).onBiometricPromptShown();
        }
        this.mCurrentDialog = authContainerView;
        if (!promptInfo.isAllowBackgroundAuthentication() && isOwnerInBackground()) {
            this.mExecution.assertIsMainThread();
            closeDialog(3, "owner not in foreground");
            return;
        }
        if (this.mUserManager.isVisibleBackgroundUsersSupported()) {
            UserManager userManager2 = (UserManager) this.mContext.createContextAsUser(UserHandle.of(i), 0).getSystemService(UserManager.class);
            if (userManager2 == null) {
                ClockEventController$$ExternalSyntheticOutline0.m(i, "unable to get UserManager for user=", "AuthController");
            } else if (userManager2.isUserVisible()) {
                int mainDisplayIdAssignedToUser = userManager2.getMainDisplayIdAssignedToUser();
                if (mainDisplayIdAssignedToUser == -1) {
                    ClockEventController$$ExternalSyntheticOutline0.m(i, "unable to get display assigned to user=", "AuthController");
                } else {
                    Display display = this.mDisplayManager.getDisplay(mainDisplayIdAssignedToUser);
                    if (display == null) {
                        ClockEventController$$ExternalSyntheticOutline0.m(i, "unable to get Display for user=", "AuthController");
                    } else {
                        Context createDisplayContext = this.mContext.createDisplayContext(display);
                        ((WindowManagerProviderImpl) this.mWindowManagerProvider).getClass();
                        windowManager = WindowManagerUtils.getWindowManager(createDisplayContext);
                    }
                }
            } else {
                windowManager = this.mWindowManager;
            }
            windowManager = null;
        } else {
            windowManager = this.mWindowManager;
        }
        if (windowManager == null) {
            closeDialog(9, "unable to get WM instance for user");
        } else {
            AuthContainerView authContainerView3 = this.mCurrentDialog;
            windowManager.addView(authContainerView3, AuthContainerView.getLayoutParams(authContainerView3.mWindowToken, authContainerView3.mConfig.mPromptInfo.getTitle(), ((PromptKind) authContainerView3.mPromptViewModel.promptKind.$$delegate_0.getValue()).isCredential()));
        }
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
        Iterator it = ((HashSet) this.mCallbacks).iterator();
        while (it.hasNext()) {
            ((Callback) it.next()).onFingerprintLocationChanged();
        }
    }

    public final void updateUdfpsLocation() {
        int naturalHeight;
        int naturalWidth;
        int i;
        int i2;
        int i3;
        int i4;
        if (this.mUdfpsController != null) {
            int i5 = 0;
            FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal = (FingerprintSensorPropertiesInternal) this.mUdfpsProps.get(0);
            Rect rect = this.mUdfpsBounds;
            UdfpsOverlayParams udfpsOverlayParams = this.mUdfpsOverlayParams;
            Rect rect2 = fingerprintSensorPropertiesInternal.getLocation().getRect();
            this.mUdfpsBounds = rect2;
            rect2.scale(this.mScaleFactor);
            int inDisplayFingerprintHeight = DeviceState.getInDisplayFingerprintHeight();
            int inDisplayFingerprintImageSize = DeviceState.getInDisplayFingerprintImageSize();
            int naturalWidth2 = this.mCachedDisplayInfo.getNaturalWidth() / 2;
            int i6 = inDisplayFingerprintImageSize / 2;
            String str = LsRune.VALUE_SUB_DISPLAY_POLICY;
            DisplayInfo displayInfo = this.mCachedDisplayInfo;
            int i7 = displayInfo.rotation;
            if (i7 == 0) {
                i3 = displayInfo.getNaturalHeight() / 2;
                naturalHeight = this.mCachedDisplayInfo.getNaturalWidth();
                naturalWidth = this.mCachedDisplayInfo.getNaturalHeight();
                i = naturalWidth2 - i6;
                i2 = this.mCachedDisplayInfo.getNaturalHeight() - inDisplayFingerprintHeight;
                inDisplayFingerprintHeight = naturalWidth2 + i6;
                i4 = inDisplayFingerprintImageSize + i2;
            } else if (i7 == 2) {
                int naturalWidth3 = displayInfo.getNaturalWidth();
                naturalWidth = this.mCachedDisplayInfo.getNaturalHeight() / 2;
                i = naturalWidth2 - i6;
                i2 = inDisplayFingerprintHeight - inDisplayFingerprintImageSize;
                i4 = inDisplayFingerprintHeight;
                inDisplayFingerprintHeight = naturalWidth2 + i6;
                naturalHeight = naturalWidth3;
                i3 = 0;
            } else if (i7 == 1) {
                int naturalHeight2 = displayInfo.getNaturalHeight() / 2;
                naturalHeight = this.mCachedDisplayInfo.getNaturalHeight();
                naturalWidth = this.mCachedDisplayInfo.getNaturalWidth();
                i = this.mCachedDisplayInfo.getNaturalHeight() - inDisplayFingerprintHeight;
                i2 = naturalWidth2 - i6;
                inDisplayFingerprintHeight = i + inDisplayFingerprintImageSize;
                i4 = naturalWidth2 + i6;
                i3 = 0;
                i5 = naturalHeight2;
            } else {
                naturalHeight = displayInfo.getNaturalHeight() / 2;
                naturalWidth = this.mCachedDisplayInfo.getNaturalWidth();
                i = inDisplayFingerprintHeight - inDisplayFingerprintImageSize;
                i2 = naturalWidth2 - i6;
                i3 = 0;
                i4 = naturalWidth2 + i6;
            }
            this.mUdfpsBounds = new Rect(i, i2, inDisplayFingerprintHeight, i4);
            UdfpsOverlayParams udfpsOverlayParams2 = new UdfpsOverlayParams(this.mUdfpsBounds, new Rect(i5, i3, naturalHeight, naturalWidth), this.mCachedDisplayInfo.getNaturalWidth(), this.mCachedDisplayInfo.getNaturalHeight(), this.mScaleFactor, this.mCachedDisplayInfo.rotation, fingerprintSensorPropertiesInternal.sensorType);
            this.mUdfpsOverlayParams = udfpsOverlayParams2;
            UdfpsController udfpsController = this.mUdfpsController;
            if (udfpsController.mSensorProps.sensorId != fingerprintSensorPropertiesInternal.sensorId) {
                udfpsController.mSensorProps = fingerprintSensorPropertiesInternal;
                Log.w("UdfpsController", "updateUdfpsParams | sensorId has changed");
            }
            if (!udfpsController.mOverlayParams.equals(udfpsOverlayParams2)) {
                udfpsController.mOverlayParams = udfpsOverlayParams2;
                UdfpsControllerOverlay udfpsControllerOverlay = udfpsController.mOverlay;
                if (udfpsControllerOverlay != null && udfpsControllerOverlay.requestReason == 4) {
                    udfpsControllerOverlay.overlayParams = udfpsOverlayParams2;
                    udfpsControllerOverlay.sensorBounds = udfpsOverlayParams2.sensorBounds;
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
