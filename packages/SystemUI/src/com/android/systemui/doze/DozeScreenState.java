package com.android.systemui.doze;

import android.os.Handler;
import android.os.PowerManager;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.LsRune;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.biometrics.UdfpsController;
import com.android.systemui.doze.DozeMachine;
import com.android.systemui.keyguard.KeyguardFoldController;
import com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl;
import com.android.systemui.keyguard.domain.interactor.DozeInteractor;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.DozeServiceHost;
import com.android.systemui.statusbar.phone.ScreenOffAnimation;
import com.android.systemui.statusbar.phone.ScrimState;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.wakelock.SettableWakeLock;
import com.android.systemui.util.wakelock.WakeLock;
import com.sec.ims.settings.ImsProfile;
import dagger.Lazy;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.inject.Provider;

/* loaded from: classes2.dex */
public class DozeScreenState implements DozeMachine.Part {
    public static final boolean DEBUG = DozeService.DEBUG;
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
    public final DozeScreenState$$ExternalSyntheticLambda0 mApplyPendingScreenState = new DozeScreenState$$ExternalSyntheticLambda0(this, 1);
    public int mPendingScreenState = 0;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.biometrics.AuthController$Callback, com.android.systemui.doze.DozeScreenState$1] */
    public DozeScreenState(KeyguardFoldController keyguardFoldController, DozeMachine.Service service, Handler handler, DozeHost dozeHost, DozeParameters dozeParameters, WakeLock wakeLock, AuthController authController, Provider provider, DozeLog dozeLog, DozeScreenBrightness dozeScreenBrightness, DozeInteractor dozeInteractor, SelectedUserInteractor selectedUserInteractor) {
        ?? r1 = new AuthController.Callback() { // from class: com.android.systemui.doze.DozeScreenState.1
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
        this.mAuthControllerCallback = r1;
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
            authController.addCallback(r1);
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
            AODParameters aODParameters = this.mParameters.mAODParameters;
            boolean z2 = true;
            boolean z3 = i == 2 || i == 4 || (LsRune.AOD_DOZE_AP_SLEEP && i == 3);
            if (((KeyguardViewMediatorHelperImpl) ((PluginAODManager) aODParameters.mPluginAODManagerLazy.get()).mKeyguardViewMediatorHelper).isScreenOn()) {
                Log.d("AODParameters", "setDozeUiState: Do not set dozeUiState when the screen is on");
                aODParameters.mDozeUiState = false;
            } else {
                aODParameters.mDozeUiState = z3;
            }
            if (LsRune.AOD_FULLSCREEN) {
                PluginAODManager pluginAODManager = (PluginAODManager) this.mPluginAODManagerLazy.get();
                if (!LsRune.AOD_DOZE_AP_SLEEP ? i != 4 : i != 3) {
                    z2 = false;
                }
                pluginAODManager.updateRefreshRate(z2);
            }
            this.mDozeService.setDozeScreenState(i, z);
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
    /* JADX WARN: Removed duplicated region for block: B:71:0x0110  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x011a  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x011f  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0014  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0145  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0150  */
    @Override // com.android.systemui.doze.DozeMachine.Part
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void transitionTo(DozeMachine.State state, DozeMachine.State state2) {
        boolean z;
        boolean z2;
        UdfpsController udfpsController;
        int iOrdinal = state2.ordinal();
        DozeParameters dozeParameters = this.mParameters;
        int i = 2;
        switch (iOrdinal) {
            case 0:
            case 1:
                if (!dozeParameters.mControlScreenOffAnimation) {
                    i = 1;
                    break;
                }
                break;
            case 2:
            case 3:
            case 10:
                break;
            case 4:
            case 11:
            case 13:
                if (!LsRune.AOD_DOZE_AP_SLEEP) {
                    i = 4;
                    break;
                } else {
                    i = 3;
                    break;
                }
            case 5:
                if (dozeParameters.getDisplayNeedsBlanking()) {
                }
                break;
            case 6:
            case 7:
            case 12:
            case 14:
            case 15:
                break;
            case 8:
            case 9:
            default:
                i = 0;
                break;
        }
        DozeServiceHost dozeServiceHost = (DozeServiceHost) this.mDozeHost;
        dozeServiceHost.getClass();
        dozeServiceHost.mHasPendingScreenOffCallbackChangeListener.onHasPendingScreenOffCallbackChanged(false);
        if (dozeServiceHost.mScrimController.mState == ScrimState.OFF) {
            dozeServiceHost.mCentralSurfaces.updateScrimController();
        }
        DozeMachine.State state3 = DozeMachine.State.FINISH;
        SettableWakeLock settableWakeLock = this.mWakeLock;
        DozeScreenState$$ExternalSyntheticLambda0 dozeScreenState$$ExternalSyntheticLambda0 = this.mApplyPendingScreenState;
        Handler handler = this.mHandler;
        if (state2 == state3) {
            this.mPendingScreenState = 0;
            handler.removeCallbacks(dozeScreenState$$ExternalSyntheticLambda0);
            applyScreenState(i, false);
            ((PluginAODManager) this.mPluginAODManagerLazy.get()).enableTouch(true);
            ((PluginAODManager) this.mPluginAODManagerLazy.get()).mClockTransitionStarted = false;
            if (LsRune.AOD_FULLSCREEN) {
                ((PluginAODManager) this.mPluginAODManagerLazy.get()).updateRefreshRate(false);
            }
            settableWakeLock.setAcquired(false);
            return;
        }
        if (i == 0) {
            return;
        }
        DozeMachine.State state4 = DozeMachine.State.INITIALIZED;
        if (state2 == state4) {
            z = dozeParameters.mControlScreenOffAnimation;
            EmergencyButtonController$$ExternalSyntheticOutline0.m("transitionTo INITIALIZED shouldWaitForTransitionToAodUi : ", "DozeScreenState", z);
        } else {
            z = false;
        }
        boolean zHasCallbacks = handler.hasCallbacks(dozeScreenState$$ExternalSyntheticLambda0);
        boolean z3 = state == DozeMachine.State.DOZE_PULSE_DONE && (state2 == DozeMachine.State.DOZE_AOD || state2 == DozeMachine.State.DOZE_AOD_DOCKED);
        boolean z4 = state == state4;
        if (zHasCallbacks || z4 || z3) {
            this.mPendingScreenState = i;
            if (state2 != DozeMachine.State.DOZE_AOD) {
                z2 = false;
                boolean z5 = state2 == DozeMachine.State.DOZE_AOD && (udfpsController = this.mUdfpsController) != null && udfpsController.mOnFingerDown;
                boolean z6 = DEBUG;
                if (zHasCallbacks) {
                    if (z6) {
                        RecyclerView$$ExternalSyntheticOutline0.m(z2 ? ImsProfile.DEFAULT_DEREG_TIMEOUT : 1, "DozeScreenState", MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Display state changed to ", " delayed by "));
                    }
                    handler.post(dozeScreenState$$ExternalSyntheticLambda0);
                    if (state2 == DozeMachine.State.DOZE_TRANSITION_ENDED) {
                        this.mIsExecutedClockTransition = true;
                        handler.post(new DozeScreenState$$ExternalSyntheticLambda0(this, 0));
                    }
                } else if (z6) {
                    ListPopupWindow$$ExternalSyntheticOutline0.m(i, "Pending display state change to ", "DozeScreenState");
                }
                if (!z2 || z5) {
                    settableWakeLock.setAcquired(true);
                }
            } else {
                if (((TransitionStep) dozeParameters.mTransitionInteractor.transitionState.$$delegate_0.getValue()).to != KeyguardState.AOD && (!dozeParameters.mControlScreenOffAnimation || !dozeParameters.mKeyguardVisible)) {
                    List list = dozeParameters.mScreenOffAnimationController.animations;
                    if (!(list instanceof Collection) || !list.isEmpty()) {
                        Iterator it = list.iterator();
                        while (it.hasNext()) {
                            if (((ScreenOffAnimation) it.next()).shouldDelayDisplayDozeTransition()) {
                            }
                        }
                    }
                    z2 = false;
                    if (state2 == DozeMachine.State.DOZE_AOD) {
                        boolean z62 = DEBUG;
                        if (zHasCallbacks) {
                        }
                        if (!z2) {
                            settableWakeLock.setAcquired(true);
                        }
                    }
                }
                z2 = true;
                if (state2 == DozeMachine.State.DOZE_AOD) {
                }
            }
        } else {
            applyScreenState(i, z);
            if (state2 == DozeMachine.State.DOZE_TRANSITION_ENDED) {
                this.mIsExecutedClockTransition = true;
                ((PluginAODManager) this.mPluginAODManagerLazy.get()).enableTouch(true);
            }
        }
        if (z && state2 == DozeMachine.State.INITIALIZED) {
            if (LsRune.SECURITY_FINGERPRINT_IN_DISPLAY) {
                ((PluginAODManager) this.mPluginAODManagerLazy.get()).mKeyguardUpdateMonitor.semSetScreenStatus();
            }
            ((PluginAODManager) this.mPluginAODManagerLazy.get()).enableTouch(false);
            ((PluginAODManager) this.mPluginAODManagerLazy.get()).mClockTransitionStarted = true;
        }
    }

    public final void updateUdfpsController() {
        if (this.mAuthController.isUdfpsEnrolled(this.mSelectedUserInteractor.getSelectedUserId())) {
            this.mUdfpsController = (UdfpsController) this.mUdfpsControllerProvider.get();
        } else {
            this.mUdfpsController = null;
        }
    }
}
