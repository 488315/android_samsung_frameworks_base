package com.android.systemui.doze;

import android.os.Handler;
import android.os.PowerManager;
import android.util.Log;
import com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0;
import com.android.systemui.LsRune;
import com.android.systemui.aod.AODTouchModeManager;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.biometrics.UdfpsController;
import com.android.systemui.doze.DozeMachine;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.wakelock.SettableWakeLock;
import com.android.systemui.util.wakelock.WakeLock;
import dagger.Lazy;
import javax.inject.Provider;

public final class DozeScreenState implements DozeMachine.Part {
    public static final boolean DEBUG = DozeService.DEBUG;
    public AODTouchModeManager mAODTouchModeManager;
    public final AuthController mAuthController;
    public final AnonymousClass1 mAuthControllerCallback;
    public final DozeHost mDozeHost;
    public final DozeLog mDozeLog;
    public final DozeScreenBrightness mDozeScreenBrightness;
    public final DozeMachine.Service mDozeService;
    public PowerManager.WakeLock mDrawWakeLock;
    public final Handler mHandler;
    public boolean mIsExecutedClockTransition;
    public final DozeParameters mParameters;
    public Lazy mPluginAODManagerLazy;
    public PowerManager mPowerManager;
    public final SelectedUserInteractor mSelectedUserInteractor;
    public UdfpsController mUdfpsController;
    public final Provider mUdfpsControllerProvider;
    public final SettableWakeLock mWakeLock;
    public final DozeScreenState$$ExternalSyntheticLambda0 mApplyPendingScreenState = new DozeScreenState$$ExternalSyntheticLambda0(this, 0);
    public int mPendingScreenState = 0;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.systemui.biometrics.AuthController$Callback, com.android.systemui.doze.DozeScreenState$1] */
    public DozeScreenState(DozeMachine.Service service, Handler handler, DozeHost dozeHost, DozeParameters dozeParameters, WakeLock wakeLock, AuthController authController, Provider provider, DozeLog dozeLog, DozeScreenBrightness dozeScreenBrightness, SelectedUserInteractor selectedUserInteractor) {
        ?? r0 = new AuthController.Callback() { // from class: com.android.systemui.doze.DozeScreenState.1
            @Override // com.android.systemui.biometrics.AuthController.Callback
            public final void onAllAuthenticatorsRegistered(int i) {
                if (i == 2) {
                    boolean z = DozeScreenState.DEBUG;
                    DozeScreenState.this.updateUdfpsController();
                }
            }

            @Override // com.android.systemui.biometrics.AuthController.Callback
            public final void onEnrollmentsChanged(int i) {
                if (i == 2) {
                    boolean z = DozeScreenState.DEBUG;
                    DozeScreenState.this.updateUdfpsController();
                }
            }
        };
        this.mAuthControllerCallback = r0;
        this.mDozeService = service;
        this.mHandler = handler;
        this.mParameters = dozeParameters;
        this.mDozeHost = dozeHost;
        this.mWakeLock = new SettableWakeLock(wakeLock, "DozeScreenState");
        this.mAuthController = authController;
        this.mUdfpsControllerProvider = provider;
        this.mDozeLog = dozeLog;
        this.mDozeScreenBrightness = dozeScreenBrightness;
        this.mSelectedUserInteractor = selectedUserInteractor;
        updateUdfpsController();
        if (this.mUdfpsController == null) {
            authController.addCallback(r0);
        }
    }

    public final void applyScreenState(int i, boolean z) {
        if (i != 0) {
            Log.d("DozeScreenState", "applyScreenState(" + i + ", shouldWaitForTransitionToAodUi = " + z + ")");
            if (!LsRune.AOD_DOZE_AP_SLEEP ? i == 4 : i == 3) {
                if (this.mIsExecutedClockTransition) {
                    this.mIsExecutedClockTransition = false;
                } else {
                    try {
                        if (this.mDrawWakeLock == null) {
                            this.mDrawWakeLock = this.mPowerManager.newWakeLock(128, "DozeScreenState");
                        }
                        this.mDrawWakeLock.acquire(1000L);
                    } catch (Exception e) {
                        EmergencyButton$$ExternalSyntheticOutline0.m("applyDrawWakeLock exception = ", e, "DozeScreenState");
                    }
                }
            }
            this.mParameters.mAODParameters.mDozeUiState = i == 2 || i == 4 || (LsRune.AOD_DOZE_AP_SLEEP && i == 3);
            if (LsRune.AOD_FULLSCREEN) {
                ((PluginAODManager) this.mPluginAODManagerLazy.get()).updateRefreshRate(i);
            }
            this.mDozeService.setDozeScreenState(i, z);
            if (LsRune.AOD_TSP_CONTROL && (i == 4 || (LsRune.AOD_DOZE_AP_SLEEP && i == 3))) {
                this.mAODTouchModeManager.setTouchMode(0);
            }
            if (i == 3) {
                this.mDozeScreenBrightness.updateBrightnessAndReady(false);
            }
            this.mPendingScreenState = 0;
            this.mWakeLock.setAcquired(false);
        }
    }

    @Override // com.android.systemui.doze.DozeMachine.Part
    public final void destroy() {
        this.mAuthController.removeCallback(this.mAuthControllerCallback);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0027, code lost:
    
        if (r6.mControlScreenOffAnimation != false) goto L15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:5:0x0018, code lost:
    
        if (r6.getDisplayNeedsBlanking() != false) goto L12;
     */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0151  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0177  */
    @Override // com.android.systemui.doze.DozeMachine.Part
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void transitionTo(com.android.systemui.doze.DozeMachine.State r17, com.android.systemui.doze.DozeMachine.State r18) {
        /*
            Method dump skipped, instructions count: 458
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.doze.DozeScreenState.transitionTo(com.android.systemui.doze.DozeMachine$State, com.android.systemui.doze.DozeMachine$State):void");
    }

    public final void updateUdfpsController() {
        if (this.mAuthController.isUdfpsEnrolled(this.mSelectedUserInteractor.getSelectedUserId())) {
            this.mUdfpsController = (UdfpsController) this.mUdfpsControllerProvider.get();
        } else {
            this.mUdfpsController = null;
        }
    }
}
