package com.samsung.android.biometrics.app.setting.fingerprint;

import android.content.Context;
import android.hardware.display.IDisplayManager;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import android.util.SparseArray;

import com.samsung.android.biometrics.app.setting.DisplayStateManager;
import com.samsung.android.biometrics.app.setting.DisplayStateManager$$ExternalSyntheticLambda1;
import com.samsung.android.biometrics.app.setting.FpServiceProviderImpl;
import com.samsung.android.biometrics.app.setting.PowerServiceProvider;
import com.samsung.android.biometrics.app.setting.SysUiUdfpsOpticalManager$$ExternalSyntheticLambda0;
import com.samsung.android.biometrics.app.setting.Utils;
import com.samsung.android.biometrics.app.setting.fingerprint.HbmController.AnonymousClass1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
public class OpticalController
        implements Handler.Callback,
                HbmLockStateMonitor.Callback,
                HbmListener,
                AodStatusMonitor.Callback {
    static final int MAX_HBM_TIME_IN_SCREEN_OFF = 10000;
    static final int MSG_DELIVERY_HBM_OFF_EVENT = 3;
    static final int MSG_DELIVERY_TOUCH_DOWN_EVENT = 2;
    static final int MSG_TURN_OFF_HBM = 1;
    static final int TIME_DELAY_HBM_OFF = 300;
    static final int TOUCH_DELAY_TIME_DEFAULT = 34;
    public final AodStatusMonitor mAodStatusMonitor;
    public final Context mContext;
    public final DisplayStateManager mDisplayStateManager;
    public final FpServiceProvider mFpProvider;
    protected final Handler mH;
    public final HbmProvider mHbmProvider;
    public final UdfpsIconOptionMonitor mIconOptionMonitor;
    public boolean mIsDispatchedTouchDownEvent;
    public boolean mIsTouchDown;
    public PowerManager.WakeLock mPartialWakeLock;
    public final FingerprintSensorInfo mSensorInfo;
    protected SparseArray mMaskClients = new SparseArray(MSG_DELIVERY_HBM_OFF_EVENT);
    public final List mPendingActionsWhenTurnedOnHbm = new ArrayList();
    public final OpticalController$$ExternalSyntheticLambda2 mActionHandleTouchDown =
            new OpticalController$$ExternalSyntheticLambda2(this);
    public final SysUiUdfpsOpticalManager$$ExternalSyntheticLambda0 mActionTurnOnCalibrationLs =
            new SysUiUdfpsOpticalManager$$ExternalSyntheticLambda0(this);
    public final HbmLockStateMonitor mHbmLockStateMonitor = createHbmLockStateMonitor();
    public final HbmController mHbmController = createHbmController();
    public final DisplayConstraintHandler mDisplayConstraintHandler =
            createDisplayConstraintHandler();
    public final PowerServiceProvider mPsProvider = createPowerServiceProvider();

    /* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
    public final class MaskClient {
        public final boolean mIsKeyguard;
        public final boolean mIsMaskSA = true;
        public int mSessionId;

        public MaskClient(int i, boolean z) {
            this.mSessionId = i;
            this.mIsKeyguard = z;
        }
    }

    public OpticalController(
            Context context,
            FingerprintSensorInfo fingerprintSensorInfo,
            FpServiceProvider fpServiceProvider,
            DisplayBrightnessMonitor displayBrightnessMonitor,
            DisplayStateManager displayStateManager,
            UdfpsIconOptionMonitor udfpsIconOptionMonitor,
            AodStatusMonitor aodStatusMonitor) {
        this.mContext = context;
        this.mH = new Handler(context.getMainLooper(), this);
        this.mDisplayStateManager = displayStateManager;
        this.mSensorInfo = fingerprintSensorInfo;
        this.mFpProvider = fpServiceProvider;
        this.mIconOptionMonitor = udfpsIconOptionMonitor;
        this.mAodStatusMonitor = aodStatusMonitor;
        this.mHbmProvider = createHbmProvider(displayBrightnessMonitor);
    }

    public final void addMaskClient(MaskClient maskClient) {
        int i;
        HbmLockStateMonitor hbmLockStateMonitor;
        this.mMaskClients.put(maskClient.mSessionId, maskClient);
        if (!maskClient.mIsMaskSA && (hbmLockStateMonitor = this.mHbmLockStateMonitor) != null) {
            hbmLockStateMonitor.setHbmLockState(false, 4);
        }
        if (this.mMaskClients.size() == MSG_TURN_OFF_HBM) {
            DisplayConstraintHandler displayConstraintHandler = this.mDisplayConstraintHandler;
            displayConstraintHandler.getClass();
            if (!Utils.Config.FP_FEATURE_LOCAL_HBM) {
                displayConstraintHandler.mIsStarted = true;
            }
            HbmController hbmController = this.mHbmController;
            hbmController.getClass();
            HbmController.AnonymousClass1 anonymousClass1 = hbmController.new AnonymousClass1();
            HbmDisplayOnState.AnonymousClass1 anonymousClass12 = HbmDisplayOnState.sInstance;
            ((HbmDisplayOnState) anonymousClass12.get()).mProvider = anonymousClass1;
            HbmDisplayOffState.AnonymousClass1 anonymousClass13 = HbmDisplayOffState.sInstance;
            ((HbmDisplayOffState) anonymousClass13.get()).mProvider = anonymousClass1;
            ((HbmDisplayLimitState) HbmDisplayLimitState.sInstance.get()).mProvider =
                    anonymousClass1;
            DisplayStateManager displayStateManager = hbmController.mDisplayStateManager;
            if (displayStateManager.isOnState()) {
                hbmController.mState = (HbmDisplayOnState) anonymousClass12.get();
            } else {
                hbmController.mState = (HbmDisplayOffState) anonymousClass13.get();
            }
            Log.d("BSS_HbmController", "initState: ".concat(hbmController.mState.getTag()));
            displayStateManager.registerHbmListener(hbmController);
            displayStateManager.mBgHandler.post(
                    new DisplayStateManager$$ExternalSyntheticLambda1(
                            displayStateManager, hbmController, 0));
            if (Utils.Config.FEATURE_SUPPORT_DISPLAY_SEAMLESS_MODE) {
                displayStateManager.mRunnableReleaseRefreshRate = null;
                displayStateManager.mInjector.getClass();
                IDisplayManager asInterface =
                        IDisplayManager.Stub.asInterface(ServiceManager.getService("display"));
                if (asInterface != null) {
                    try {
                        if (Utils.getIntDb(
                                        displayStateManager.mInjector.mContext,
                                        "refresh_rate_mode",
                                        true,
                                        0)
                                == 0) {
                            if (displayStateManager.mPassiveModeToken == null) {
                                Log.i("BSS_DisplayStateManager", "acquirePassiveModeToken");
                                displayStateManager.mPassiveModeToken =
                                        asInterface.acquirePassiveModeToken(
                                                displayStateManager.mTokenForPassiveMode,
                                                "BSS_DisplayStateManager");
                            }
                            i = 60;
                        } else {
                            i = 120;
                        }
                        if (displayStateManager.mRefreshRateToken == null) {
                            Log.i(
                                    "BSS_DisplayStateManager",
                                    "acquireRefreshRateMinLimitToken : " + i);
                            displayStateManager.mRefreshRateToken =
                                    asInterface.acquireRefreshRateMinLimitToken(
                                            displayStateManager.mTokenForRefreshRate,
                                            i,
                                            "BSS_DisplayStateManager");
                        }
                    } catch (RemoteException unused) {
                        Log.w(
                                "BSS_DisplayStateManager",
                                "Error : acquireRefreshRateForSeamlessMode");
                    }
                }
            }
            this.mDisplayStateManager.registerHbmListener(this);
        }
        if (this.mDisplayStateManager.isOnState()) {
            if (Utils.Config.FP_FEATURE_LOCAL_HBM) {
                turnOnHbm$1();
                return;
            }
            HbmLockStateMonitor hbmLockStateMonitor2 = this.mHbmLockStateMonitor;
            if (hbmLockStateMonitor2 == null || hbmLockStateMonitor2.mHbmLockState != 0) {
                return;
            }
            this.mDisplayConstraintHandler.disableAllFunctions();
            turnOnHbm$1();
        }
    }

    public DisplayConstraintHandler createDisplayConstraintHandler() {
        Context context = this.mContext;
        return new DisplayConstraintHandler(
                context, this.mFpProvider, new DisplayConstraintHandler.Injector(context));
    }

    public HbmController createHbmController() {
        return new HbmController(this.mContext, this.mDisplayStateManager, this.mHbmProvider);
    }

    public HbmLockStateMonitor createHbmLockStateMonitor() {
        Context context = this.mContext;
        OpticalController$$ExternalSyntheticLambda0 opticalController$$ExternalSyntheticLambda0 =
                new OpticalController$$ExternalSyntheticLambda0(this);
        DisplayStateManager displayStateManager = this.mDisplayStateManager;
        Objects.requireNonNull(displayStateManager);
        return new HbmLockStateMonitor(
                context,
                context.getMainThreadHandler(),
                this,
                opticalController$$ExternalSyntheticLambda0,
                new OpticalController$$ExternalSyntheticLambda0(displayStateManager),
                new HbmLockStateMonitor.Injector());
    }

    public HbmProvider createHbmProvider(DisplayBrightnessMonitor displayBrightnessMonitor) {
        return new UdfpsMaskWindow(
                this.mContext,
                this.mSensorInfo,
                this.mDisplayStateManager,
                displayBrightnessMonitor);
    }

    public PowerServiceProvider createPowerServiceProvider() {
        return new PowerServiceProvider() { // from class:
                                            // com.samsung.android.biometrics.app.setting.fingerprint.OpticalController.1
            @Override // com.samsung.android.biometrics.app.setting.PowerServiceProvider
            public final void acquireWakeLock(long j) {
                OpticalController opticalController = OpticalController.this;
                if (opticalController.mPartialWakeLock == null) {
                    PowerManager.WakeLock newWakeLock =
                            ((PowerManager)
                                            opticalController.mContext.getSystemService(
                                                    PowerManager.class))
                                    .newWakeLock(
                                            OpticalController.MSG_TURN_OFF_HBM,
                                            "BSS_OpticalController:P");
                    opticalController.mPartialWakeLock = newWakeLock;
                    newWakeLock.setReferenceCounted(false);
                }
                opticalController.mPartialWakeLock.acquire(j);
            }

            @Override // com.samsung.android.biometrics.app.setting.PowerServiceProvider
            public final boolean isPowerSaveMode() {
                return ((PowerManager)
                                OpticalController.this.mContext.getSystemService(
                                        PowerManager.class))
                        .isPowerSaveMode();
            }

            @Override // com.samsung.android.biometrics.app.setting.PowerServiceProvider
            public final void releaseWakeLock() {
                PowerManager.WakeLock wakeLock = OpticalController.this.mPartialWakeLock;
                if (wakeLock != null) {
                    wakeLock.release();
                }
            }
        };
    }

    public final void deliverTouchEvent(int i) {
        if (i == MSG_DELIVERY_TOUCH_DOWN_EVENT) {
            this.mIsDispatchedTouchDownEvent = true;
        } else if (i == MSG_TURN_OFF_HBM) {
            if (!this.mIsDispatchedTouchDownEvent) {
                return;
            } else {
                this.mIsDispatchedTouchDownEvent = false;
            }
        }
        FpServiceProvider fpServiceProvider = this.mFpProvider;
        boolean isOnState = this.mDisplayStateManager.isOnState();
        int currentAlpha = this.mHbmProvider.getCurrentAlpha();
        FpServiceProviderImpl fpServiceProviderImpl = (FpServiceProviderImpl) fpServiceProvider;
        fpServiceProviderImpl.getClass();
        fpServiceProviderImpl.requestToFpSvc(
                9,
                i,
                i == MSG_DELIVERY_TOUCH_DOWN_EVENT
                        ? ((isOnState ? 1L : 0L) << 16) | currentAlpha
                        : 0L,
                null);
    }

    public void handleDisplayStateChanged(int i) {
        if (i == MSG_DELIVERY_TOUCH_DOWN_EVENT) {
            this.mH.removeMessages(MSG_TURN_OFF_HBM);
            this.mH.removeMessages(MSG_DELIVERY_HBM_OFF_EVENT);
        }
        if (this.mMaskClients.size() > 0) {
            this.mHbmController.mState.handleDisplayStateChanged(i);
            if (i == MSG_TURN_OFF_HBM && !this.mH.hasMessages(MSG_DELIVERY_HBM_OFF_EVENT)) {
                this.mH.sendEmptyMessageDelayed(MSG_DELIVERY_HBM_OFF_EVENT, 300L);
            }
        }
        DisplayConstraintHandler displayConstraintHandler = this.mDisplayConstraintHandler;
        if (!displayConstraintHandler.mIsStarted || i == 1001) {
            return;
        }
        displayConstraintHandler.disableAllFunctions();
    }

    @Override // android.os.Handler.Callback
    public final boolean handleMessage(Message message) {
        Log.i("BSS_OpticalController", Utils.getLogFormat(message));
        int i = message.what;
        if (i == MSG_TURN_OFF_HBM) {
            turnOffHbm$1();
        } else if (i != MSG_DELIVERY_TOUCH_DOWN_EVENT) {
            if (i == MSG_DELIVERY_HBM_OFF_EVENT
                    && this.mDisplayStateManager.isEnabledHbm()
                    && !this.mHbmProvider.isEnabledHbm()) {
                this.mHbmController.onHbmChanged(false);
            }
        } else if (this.mIsTouchDown) {
            deliverTouchEvent(MSG_DELIVERY_TOUCH_DOWN_EVENT);
        }
        return true;
    }

    public void handleOnTaskStackChanged() {
        HbmLockStateMonitor hbmLockStateMonitor;
        if (hasAuthMaskClient()
                || (hbmLockStateMonitor = this.mHbmLockStateMonitor) == null
                || !hbmLockStateMonitor.mIsScreenOn.getAsBoolean()) {
            return;
        }
        String[] strArr = hbmLockStateMonitor.mInjector.mPackageListWithNoMask;
        int length = strArr.length;
        for (int i = 0; i < length; i += MSG_TURN_OFF_HBM) {
            if (Utils.isForegroundTask(strArr[i])) {
                if (hbmLockStateMonitor.mIsAlphaMaskSaMode.getAsBoolean()) {
                    hbmLockStateMonitor.setHbmLockState(true, 4);
                    return;
                }
                return;
            }
        }
        hbmLockStateMonitor.setHbmLockState(false, 4);
    }

    public final void handleSingleTapEvent() {
        if (!hasAuthMaskClient() || this.mMaskClients.size() == 0 || this.mIsTouchDown) {
            return;
        }
        if (this.mPsProvider.isPowerSaveMode() && this.mAodStatusMonitor.mIsDisabledInPsm) {
            return;
        }
        turnOnHbm$1();
    }

    public final boolean hasAuthMaskClient() {
        for (int i = 0; i < this.mMaskClients.size(); i += MSG_TURN_OFF_HBM) {
            if (!((MaskClient) this.mMaskClients.valueAt(i)).mIsMaskSA) {
                return true;
            }
        }
        return false;
    }

    public final boolean hasMaskClient() {
        return this.mMaskClients.size() > 0;
    }

    public boolean isTablet() {
        return Utils.isTablet();
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.AodStatusMonitor.Callback
    public final void onAodStop() {
        if (this.mMaskClients.size() == 0) {
            return;
        }
        UdfpsIconOptionMonitor udfpsIconOptionMonitor = this.mIconOptionMonitor;
        if (!(udfpsIconOptionMonitor.mIconOptionWhenScreenOff == MSG_TURN_OFF_HBM
                        || udfpsIconOptionMonitor.isEnabledOnAod())
                || this.mDisplayStateManager.isOnState()) {
            return;
        }
        turnOffHbm$1();
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.HbmListener
    public final void onHbmChanged(boolean z) {
        this.mH.removeMessages(MSG_DELIVERY_HBM_OFF_EVENT);
        if (z) {
            Iterator it = ((ArrayList) this.mPendingActionsWhenTurnedOnHbm).iterator();
            while (it.hasNext()) {
                ((Runnable) it.next()).run();
            }
            ((ArrayList) this.mPendingActionsWhenTurnedOnHbm).clear();
        }
    }

    public final void onHbmLockState(boolean z) {
        if (this.mMaskClients.size() <= 0) {
            return;
        }
        if (z) {
            turnOffHbm$1();
        } else if (this.mDisplayStateManager.isOnState()) {
            this.mDisplayConstraintHandler.disableAllFunctions();
            turnOnHbm$1();
        }
    }

    public final void onTouchUp() {
        if (this.mIsTouchDown) {
            deliverTouchEvent(MSG_TURN_OFF_HBM);
            this.mSensorInfo.getClass();
            if (Utils.Config.FP_FEATURE_HW_LIGHT_SOURCE) {
                ((FpServiceProviderImpl) this.mFpProvider).requestToFpSvc(5, 0, 0L, null);
            } else {
                this.mHbmProvider.turnOffLightSource();
            }
            ((ArrayList) this.mPendingActionsWhenTurnedOnHbm).remove(this.mActionHandleTouchDown);
            this.mH.removeMessages(MSG_DELIVERY_TOUCH_DOWN_EVENT);
            this.mIsTouchDown = false;
        }
    }

    public void removeKeyguardMaskClientIfExist() {
        int i = 0;
        int i2 = 0;
        while (true) {
            if (i2 >= this.mMaskClients.size()) {
                break;
            }
            MaskClient maskClient = (MaskClient) this.mMaskClients.valueAt(i2);
            if (maskClient.mIsKeyguard) {
                i = maskClient.mSessionId;
                break;
            }
            i2 += MSG_TURN_OFF_HBM;
        }
        if (i != 0) {
            removeMaskClient(i);
        }
    }

    public final void removeMaskClient(int i) {
        this.mMaskClients.remove(i);
        if (!hasAuthMaskClient()) {
            this.mSensorInfo.getClass();
            if (Utils.Config.FP_FEATURE_HW_LIGHT_SOURCE) {
                ((FpServiceProviderImpl) this.mFpProvider).requestToFpSvc(5, 0, 0L, null);
            } else {
                this.mHbmProvider.turnOffLightSource();
            }
        }
        if (this.mMaskClients.size() == 0) {
            if (Utils.DEBUG) {
                Log.i("BSS_OpticalController", "No client using mask");
            }
            this.mHbmController.stop();
            ((ArrayList) this.mDisplayStateManager.mHbmListeners).remove(this);
            this.mDisplayConstraintHandler.stop();
        }
    }

    public void start() {
        this.mHbmProvider.initHbmProvider();
        HbmLockStateMonitor hbmLockStateMonitor = this.mHbmLockStateMonitor;
        if (hbmLockStateMonitor != null) {
            hbmLockStateMonitor.observe(true);
        }
        this.mAodStatusMonitor.addCallback(this);
    }

    public void stop() {
        this.mHbmProvider.destroyHbmProvider();
        HbmLockStateMonitor hbmLockStateMonitor = this.mHbmLockStateMonitor;
        if (hbmLockStateMonitor != null) {
            hbmLockStateMonitor.observe(false);
        }
        this.mHbmController.stop();
        this.mDisplayConstraintHandler.stop();
        ((ArrayList) this.mPendingActionsWhenTurnedOnHbm).clear();
        this.mAodStatusMonitor.removeCallback(this);
        this.mH.removeCallbacksAndMessages(null);
    }

    public final void turnOffHbm$1() {
        if (!this.mDisplayStateManager.isOnState()) {
            this.mPsProvider.acquireWakeLock(10000L);
        }
        HbmController hbmController = this.mHbmController;
        Log.d(
                "BSS_HbmController",
                "turnOffHbm: current state is ".concat(hbmController.mState.getTag()));
        hbmController.mHasHbmRequest = false;
        hbmController.mState.turnOffHbm();
        if (!this.mH.hasMessages(MSG_DELIVERY_HBM_OFF_EVENT)) {
            this.mH.sendEmptyMessageDelayed(MSG_DELIVERY_HBM_OFF_EVENT, 300L);
        }
        this.mH.removeMessages(MSG_TURN_OFF_HBM);
    }

    public final void turnOnHbm$1() {
        if (!this.mDisplayStateManager.isOnState()) {
            this.mPsProvider.acquireWakeLock(11000L);
        }
        HbmController hbmController = this.mHbmController;
        Log.d(
                "BSS_HbmController",
                "turnOnHbm: current state is ".concat(hbmController.mState.getTag()));
        hbmController.mHasHbmRequest = true;
        hbmController.mState.turnOnHbm();
        this.mH.removeMessages(MSG_TURN_OFF_HBM);
        this.mH.removeMessages(MSG_DELIVERY_HBM_OFF_EVENT);
        if (this.mDisplayStateManager.isOnState()) {
            return;
        }
        this.mH.sendEmptyMessageDelayed(MSG_TURN_OFF_HBM, 10000L);
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.AodStatusMonitor.Callback
    public final void onAodStart() {}
}
