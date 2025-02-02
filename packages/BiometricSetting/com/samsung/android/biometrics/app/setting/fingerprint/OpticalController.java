package com.samsung.android.biometrics.app.setting.fingerprint;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.util.Log;
import android.util.SparseArray;
import com.android.internal.annotations.VisibleForTesting;
import com.samsung.android.biometrics.app.setting.DisplayStateManager;
import com.samsung.android.biometrics.app.setting.PowerServiceProvider;
import com.samsung.android.biometrics.app.setting.SysUiClient;
import com.samsung.android.biometrics.app.setting.SysUiManager;
import com.samsung.android.biometrics.app.setting.Utils;
import com.samsung.android.biometrics.app.setting.fingerprint.AodStatusMonitor;
import com.samsung.android.biometrics.app.setting.fingerprint.DisplayConstraintHandler;
import com.samsung.android.biometrics.app.setting.fingerprint.HbmLockStateMonitor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.BooleanSupplier;

/* loaded from: classes.dex */
public class OpticalController implements Handler.Callback, HbmLockStateMonitor.Callback, HbmListener, AodStatusMonitor.Callback {

    @VisibleForTesting
    static final int MAX_HBM_TIME_IN_SCREEN_OFF = 10000;

    @VisibleForTesting
    static final int MSG_DELIVERY_HBM_OFF_EVENT = 3;

    @VisibleForTesting
    static final int MSG_DELIVERY_TOUCH_DOWN_EVENT = 2;

    @VisibleForTesting
    static final int MSG_TURN_OFF_HBM = 1;

    @VisibleForTesting
    static final int TIME_DELAY_HBM_OFF = 300;

    @VisibleForTesting
    static final int TIME_DELAY_HW_LIGHT_SOURCE_DRAW = 34;
    private final AodStatusMonitor mAodStatusMonitor;
    private final Context mContext;
    protected final DisplayStateManager mDisplayStateManager;
    protected final FpServiceProvider mFpProvider;

    /* renamed from: mH */
    @VisibleForTesting
    protected final Handler f28mH;
    private final HbmProvider mHbmProvider;
    private final UdfpsIconOptionMonitor mIconOptionMonitor;
    private boolean mIsDispatchedTouchDownEvent;
    private boolean mIsTouchDown;
    private PowerManager.WakeLock mPartialWakeLock;
    private final UdfpsInfo mSensorInfo;

    @VisibleForTesting
    protected SparseArray<MaskClient> mMaskClients = new SparseArray<>(MSG_DELIVERY_HBM_OFF_EVENT);
    final List<Runnable> mPendingActionsWhenTurnedOnHbm = new ArrayList();
    final OpticalController$$ExternalSyntheticLambda1 mActionHandleTouchDown = new OpticalController$$ExternalSyntheticLambda1(this, 0);
    final OpticalController$$ExternalSyntheticLambda1 mActionTurnOnCalibrationLs = new OpticalController$$ExternalSyntheticLambda1(this, MSG_TURN_OFF_HBM);
    private final HbmLockStateMonitor mHbmLockStateMonitor = createHbmLockStateMonitor();
    private final HbmController mHbmController = createHbmController();
    private final DisplayConstraintHandler mDisplayConstraintHandler = createDisplayConstraintHandler();
    private final PowerServiceProvider mPsProvider = createPowerServiceProvider();

    public static void $r8$lambda$ghgrl0VfKVTeKIFvHiccu8fkgPU(OpticalController opticalController) {
        if (opticalController.mIsTouchDown) {
            opticalController.mSensorInfo.getClass();
            boolean z = Utils.Config.FP_FEATURE_HW_LIGHT_SOURCE;
            if (z) {
                ((SysUiManager) opticalController.mFpProvider).turnOnHwLightSource();
            } else {
                opticalController.mHbmProvider.turnOnLightSource();
            }
            opticalController.mSensorInfo.getClass();
            if (z) {
                opticalController.f28mH.sendEmptyMessageDelayed(MSG_DELIVERY_TOUCH_DOWN_EVENT, 34L);
            } else if (opticalController.mIsTouchDown) {
                opticalController.deliverTouchEvent(MSG_DELIVERY_TOUCH_DOWN_EVENT);
            }
        }
    }

    public OpticalController(Context context, UdfpsInfo udfpsInfo, FpServiceProvider fpServiceProvider, DisplayBrightnessMonitor displayBrightnessMonitor, DisplayStateManager displayStateManager, UdfpsIconOptionMonitor udfpsIconOptionMonitor, AodStatusMonitor aodStatusMonitor) {
        this.mContext = context;
        this.f28mH = new Handler(context.getMainLooper(), this);
        this.mDisplayStateManager = displayStateManager;
        this.mSensorInfo = udfpsInfo;
        this.mFpProvider = fpServiceProvider;
        this.mIconOptionMonitor = udfpsIconOptionMonitor;
        this.mAodStatusMonitor = aodStatusMonitor;
        this.mHbmProvider = createHbmProvider(displayBrightnessMonitor);
    }

    private void deliverTouchEvent(int i) {
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
        ((SysUiManager) fpServiceProvider).deliverTouchEvent(i, isOnState ? 1 : 0, this.mHbmProvider.getCurrentAlpha());
    }

    private boolean hasAuthMaskClient() {
        for (int i = 0; i < this.mMaskClients.size(); i += MSG_TURN_OFF_HBM) {
            if (!this.mMaskClients.valueAt(i).mIsMaskSA) {
                return true;
            }
        }
        return false;
    }

    private void turnOffHbm() {
        if (!this.mDisplayStateManager.isOnState()) {
            this.mPsProvider.acquireWakeLock(10000L);
        }
        this.mHbmController.turnOffHbm();
        if (!this.f28mH.hasMessages(MSG_DELIVERY_HBM_OFF_EVENT)) {
            this.f28mH.sendEmptyMessageDelayed(MSG_DELIVERY_HBM_OFF_EVENT, 300L);
        }
        this.f28mH.removeMessages(MSG_TURN_OFF_HBM);
    }

    private void turnOnHbm() {
        if (!this.mDisplayStateManager.isOnState()) {
            this.mPsProvider.acquireWakeLock(11000L);
        }
        this.mHbmController.turnOnHbm();
        this.f28mH.removeMessages(MSG_TURN_OFF_HBM);
        this.f28mH.removeMessages(MSG_DELIVERY_HBM_OFF_EVENT);
        if (this.mDisplayStateManager.isOnState()) {
            return;
        }
        this.f28mH.sendEmptyMessageDelayed(MSG_TURN_OFF_HBM, 10000L);
    }

    public final void addMaskClient(MaskClient maskClient) {
        HbmLockStateMonitor hbmLockStateMonitor;
        this.mMaskClients.put(maskClient.mSessionId, maskClient);
        if (!maskClient.mIsMaskSA && (hbmLockStateMonitor = this.mHbmLockStateMonitor) != null) {
            hbmLockStateMonitor.setHbmLockState(false, 4);
        }
        if (this.mMaskClients.size() == MSG_TURN_OFF_HBM) {
            this.mDisplayConstraintHandler.start();
            this.mHbmController.start();
            this.mDisplayStateManager.registerHbmListener(this);
        }
        if (this.mDisplayStateManager.isOnState()) {
            if (Utils.Config.FP_FEATURE_LOCAL_HBM) {
                turnOnHbm();
                return;
            }
            HbmLockStateMonitor hbmLockStateMonitor2 = this.mHbmLockStateMonitor;
            if (hbmLockStateMonitor2 == null || hbmLockStateMonitor2.isLock()) {
                return;
            }
            this.mDisplayConstraintHandler.disableAllFunctions();
            turnOnHbm();
        }
    }

    @VisibleForTesting
    protected DisplayConstraintHandler createDisplayConstraintHandler() {
        Context context = this.mContext;
        return new DisplayConstraintHandler(context, this.mFpProvider, new DisplayConstraintHandler.Injector(context));
    }

    @VisibleForTesting
    protected HbmController createHbmController() {
        return new HbmController(this.mContext, this.mDisplayStateManager, this.mHbmProvider);
    }

    @VisibleForTesting
    protected HbmLockStateMonitor createHbmLockStateMonitor() {
        Context context = this.mContext;
        final int i = 0;
        BooleanSupplier booleanSupplier = new BooleanSupplier() { // from class: com.samsung.android.biometrics.app.setting.fingerprint.OpticalController$$ExternalSyntheticLambda0
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                switch (i) {
                    case 0:
                        OpticalController opticalController = (OpticalController) this;
                        for (int i2 = 0; i2 < opticalController.mMaskClients.size(); i2++) {
                            if (opticalController.mMaskClients.valueAt(i2).mIsMaskSA) {
                                return true;
                            }
                        }
                        return false;
                    default:
                        return ((DisplayStateManager) this).isOnState();
                }
            }
        };
        final DisplayStateManager displayStateManager = this.mDisplayStateManager;
        Objects.requireNonNull(displayStateManager);
        final int i2 = MSG_TURN_OFF_HBM;
        return new HbmLockStateMonitor(context, context.getMainThreadHandler(), this, booleanSupplier, new BooleanSupplier() { // from class: com.samsung.android.biometrics.app.setting.fingerprint.OpticalController$$ExternalSyntheticLambda0
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                switch (i2) {
                    case 0:
                        OpticalController opticalController = (OpticalController) displayStateManager;
                        for (int i22 = 0; i22 < opticalController.mMaskClients.size(); i22++) {
                            if (opticalController.mMaskClients.valueAt(i22).mIsMaskSA) {
                                return true;
                            }
                        }
                        return false;
                    default:
                        return ((DisplayStateManager) displayStateManager).isOnState();
                }
            }
        }, new HbmLockStateMonitor.Injector());
    }

    @VisibleForTesting
    protected HbmProvider createHbmProvider(DisplayBrightnessMonitor displayBrightnessMonitor) {
        return new UdfpsMaskWindow(this.mContext, this.mSensorInfo, this.mDisplayStateManager, displayBrightnessMonitor);
    }

    @VisibleForTesting
    protected PowerServiceProvider createPowerServiceProvider() {
        return new PowerServiceProvider() { // from class: com.samsung.android.biometrics.app.setting.fingerprint.OpticalController.1
            @Override // com.samsung.android.biometrics.app.setting.PowerServiceProvider
            public final void acquireWakeLock(long j) {
                OpticalController opticalController = OpticalController.this;
                if (opticalController.mPartialWakeLock == null) {
                    opticalController.mPartialWakeLock = ((PowerManager) opticalController.mContext.getSystemService(PowerManager.class)).newWakeLock(OpticalController.MSG_TURN_OFF_HBM, "BSS_OpticalController:P");
                    opticalController.mPartialWakeLock.setReferenceCounted(false);
                }
                opticalController.mPartialWakeLock.acquire(j);
            }

            @Override // com.samsung.android.biometrics.app.setting.PowerServiceProvider
            public final boolean isPowerSaveMode() {
                return ((PowerManager) OpticalController.this.mContext.getSystemService(PowerManager.class)).isPowerSaveMode();
            }

            @Override // com.samsung.android.biometrics.app.setting.PowerServiceProvider
            public final void releaseWakeLock() {
                OpticalController opticalController = OpticalController.this;
                if (opticalController.mPartialWakeLock != null) {
                    opticalController.mPartialWakeLock.release();
                }
            }
        };
    }

    public final void handleAuthenticationSucceeded(int i) {
        this.mIsDispatchedTouchDownEvent = false;
        if (i != 0) {
            removeMaskClient(i);
            if (!this.mDisplayStateManager.isOnState()) {
                removeKeyguardMaskClientIfExist();
            } else {
                boolean z = Utils.Config.FEATURE_FACE_HAL;
                removeKeyguardMaskClientIfExist();
            }
        }
    }

    public final void handleCaptureCompleted() {
        this.mHbmProvider.turnOffLightSource();
    }

    public final void handleConfigurationChanged(Configuration configuration) {
        this.mHbmProvider.onConfigurationInfoChanged();
    }

    public void handleDisplayStateChanged(int i) {
        if (i == MSG_DELIVERY_TOUCH_DOWN_EVENT) {
            this.f28mH.removeMessages(MSG_TURN_OFF_HBM);
            this.f28mH.removeMessages(MSG_DELIVERY_HBM_OFF_EVENT);
        }
        if (this.mMaskClients.size() > 0) {
            this.mHbmController.mState.handleDisplayStateChanged(i);
            if (i == MSG_TURN_OFF_HBM && !this.f28mH.hasMessages(MSG_DELIVERY_HBM_OFF_EVENT)) {
                this.f28mH.sendEmptyMessageDelayed(MSG_DELIVERY_HBM_OFF_EVENT, 300L);
            }
        }
        this.mDisplayConstraintHandler.handleDisplayStateChanged(i);
    }

    @Override // android.os.Handler.Callback
    public final boolean handleMessage(Message message) {
        Log.i("BSS_OpticalController", Utils.getLogFormat(message));
        int i = message.what;
        if (i == MSG_TURN_OFF_HBM) {
            turnOffHbm();
        } else if (i != MSG_DELIVERY_TOUCH_DOWN_EVENT) {
            if (i == MSG_DELIVERY_HBM_OFF_EVENT && this.mDisplayStateManager.isEnabledHbm() && !this.mHbmProvider.isEnabledHbm()) {
                this.mHbmController.onHbmChanged(false);
            }
        } else if (this.mIsTouchDown) {
            deliverTouchEvent(MSG_DELIVERY_TOUCH_DOWN_EVENT);
        }
        return true;
    }

    public void handleOnTaskStackChanged() {
        HbmLockStateMonitor hbmLockStateMonitor;
        if (hasAuthMaskClient() || (hbmLockStateMonitor = this.mHbmLockStateMonitor) == null) {
            return;
        }
        hbmLockStateMonitor.handleOnTaskStackListener();
    }

    public final void handleRotationInfoChanged(int i) {
        this.mHbmProvider.onRotationChanged();
    }

    public final void handleSingleTapEvent() {
        if (!hasAuthMaskClient() || this.mMaskClients.size() == 0 || this.mIsTouchDown) {
            return;
        }
        if (this.mPsProvider.isPowerSaveMode() && this.mAodStatusMonitor.isDisabledInPSM()) {
            return;
        }
        turnOnHbm();
    }

    public final boolean hasMaskClient() {
        return this.mMaskClients.size() > 0;
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.AodStatusMonitor.Callback
    public final void onAodStop() {
        if (this.mMaskClients.size() == 0) {
            return;
        }
        UdfpsIconOptionMonitor udfpsIconOptionMonitor = this.mIconOptionMonitor;
        if (((udfpsIconOptionMonitor.isEnabledTapToShow() || udfpsIconOptionMonitor.isEnabledOnAod()) ? false : true) || this.mDisplayStateManager.isOnState()) {
            return;
        }
        turnOffHbm();
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.HbmListener
    public final void onHbmChanged(boolean z) {
        this.f28mH.removeMessages(MSG_DELIVERY_HBM_OFF_EVENT);
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
            turnOffHbm();
        } else if (this.mDisplayStateManager.isOnState()) {
            this.mDisplayConstraintHandler.disableAllFunctions();
            turnOnHbm();
        }
    }

    public final void onTouchDown() {
        if (!hasAuthMaskClient() || this.mIsTouchDown || this.mDisplayStateManager.getDisplayState() == 1001) {
            return;
        }
        if (this.mDisplayStateManager.isOnState() || !this.mDisplayStateManager.isLimitedDisplayInProgress()) {
            this.mIsTouchDown = true;
            turnOnHbm();
            if (this.mDisplayStateManager.isEnabledHbm()) {
                this.mActionHandleTouchDown.run();
            } else {
                ((ArrayList) this.mPendingActionsWhenTurnedOnHbm).add(this.mActionHandleTouchDown);
            }
            ((SysUiManager) this.mFpProvider).acquireDVFS();
        }
    }

    public final void onTouchUp() {
        if (this.mIsTouchDown) {
            deliverTouchEvent(MSG_TURN_OFF_HBM);
            this.mSensorInfo.getClass();
            if (Utils.Config.FP_FEATURE_HW_LIGHT_SOURCE) {
                ((SysUiManager) this.mFpProvider).turnOffHwLightSource();
            } else {
                this.mHbmProvider.turnOffLightSource();
            }
            ((ArrayList) this.mPendingActionsWhenTurnedOnHbm).remove(this.mActionHandleTouchDown);
            this.f28mH.removeMessages(MSG_DELIVERY_TOUCH_DOWN_EVENT);
            this.mIsTouchDown = false;
        }
    }

    @VisibleForTesting
    void removeKeyguardMaskClientIfExist() {
        int i = 0;
        int i2 = 0;
        while (true) {
            if (i2 >= this.mMaskClients.size()) {
                break;
            }
            MaskClient valueAt = this.mMaskClients.valueAt(i2);
            if (valueAt.mIsKeyguard) {
                i = valueAt.mSessionId;
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
                ((SysUiManager) this.mFpProvider).turnOffHwLightSource();
            } else {
                this.mHbmProvider.turnOffLightSource();
            }
        }
        if (this.mMaskClients.size() == 0) {
            if (Utils.DEBUG) {
                Log.i("BSS_OpticalController", "No client using mask");
            }
            this.mHbmController.stop();
            this.mDisplayStateManager.unregisterHbmListener(this);
            this.mDisplayConstraintHandler.stop();
        }
    }

    public void start() {
        this.mHbmProvider.initHbmProvider();
        HbmLockStateMonitor hbmLockStateMonitor = this.mHbmLockStateMonitor;
        if (hbmLockStateMonitor != null) {
            hbmLockStateMonitor.start();
        }
        this.mAodStatusMonitor.addCallback(this);
    }

    public void stop() {
        this.mHbmProvider.destroyHbmProvider();
        HbmLockStateMonitor hbmLockStateMonitor = this.mHbmLockStateMonitor;
        if (hbmLockStateMonitor != null) {
            hbmLockStateMonitor.stop();
        }
        this.mHbmController.stop();
        this.mDisplayConstraintHandler.stop();
        ((ArrayList) this.mPendingActionsWhenTurnedOnHbm).clear();
        this.mAodStatusMonitor.removeCallback(this);
        this.f28mH.removeCallbacksAndMessages(null);
    }

    public final void turnOffCalibrationLightSource() {
        this.mSensorInfo.getClass();
        if (Utils.Config.FP_FEATURE_HW_LIGHT_SOURCE) {
            ((SysUiManager) this.mFpProvider).turnOffHwLightSource();
        } else {
            this.mHbmProvider.turnOffCalibrationLightSource();
        }
        ((ArrayList) this.mPendingActionsWhenTurnedOnHbm).remove(this.mActionTurnOnCalibrationLs);
    }

    public final void turnOnCalibrationLightSource() {
        if (this.mDisplayStateManager.isEnabledHbm()) {
            this.mSensorInfo.getClass();
            if (Utils.Config.FP_FEATURE_HW_LIGHT_SOURCE) {
                ((SysUiManager) this.mFpProvider).turnOnHwLightSource();
                return;
            } else {
                this.mHbmProvider.turnOnCalibrationLightSource();
                return;
            }
        }
        if (((ArrayList) this.mPendingActionsWhenTurnedOnHbm).contains(this.mActionTurnOnCalibrationLs)) {
            return;
        }
        ((ArrayList) this.mPendingActionsWhenTurnedOnHbm).add(this.mActionTurnOnCalibrationLs);
    }

    public static class MaskClient {
        public boolean mIsKeyguard;
        public boolean mIsMaskSA;
        public int mSessionId;

        public MaskClient(SysUiClient sysUiClient) {
            this.mSessionId = sysUiClient.getSessionId();
        }

        public MaskClient(int i, boolean z) {
            this.mSessionId = i;
            this.mIsKeyguard = z;
            this.mIsMaskSA = true;
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.AodStatusMonitor.Callback
    public final void onAodStart() {
    }
}
