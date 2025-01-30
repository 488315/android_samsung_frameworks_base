package com.android.systemui.doze;

import android.hardware.display.IDisplayManager;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.Log;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.LsRune;
import com.android.systemui.aod.AODTouchModeManager;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.biometrics.UdfpsController;
import com.android.systemui.doze.DozeMachine;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.DozeServiceHost;
import com.android.systemui.statusbar.phone.ScreenOffAnimation;
import com.android.systemui.statusbar.phone.ScrimState;
import com.android.systemui.subscreen.SubScreenManager;
import com.android.systemui.util.wakelock.SettableWakeLock;
import com.android.systemui.util.wakelock.WakeLock;
import com.samsung.android.hardware.display.IRefreshRateToken;
import com.sec.ims.settings.ImsProfile;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DozeScreenState implements DozeMachine.Part {
    public static final boolean DEBUG = DozeService.DEBUG;
    public AODTouchModeManager mAODTouchModeManager;
    public final AuthController mAuthController;
    public final C12441 mAuthControllerCallback;
    public final DozeHost mDozeHost;
    public final DozeLog mDozeLog;
    public final DozeScreenBrightness mDozeScreenBrightness;
    public final DozeMachine.Service mDozeService;
    public PowerManager.WakeLock mDrawWakeLock;
    public final Handler mHandler;
    public IDisplayManager mIDisplayManager;
    public boolean mIsExecutedClockTransition;
    public IRefreshRateToken mMaxRefreshRateToken;
    public final DozeParameters mParameters;
    public Lazy mPluginAODManagerLazy;
    public PowerManager mPowerManager;
    public SubScreenManager mSubScreenManager;
    public UdfpsController mUdfpsController;
    public final Provider mUdfpsControllerProvider;
    public final SettableWakeLock mWakeLock;
    public final DozeScreenState$$ExternalSyntheticLambda0 mApplyPendingScreenState = new DozeScreenState$$ExternalSyntheticLambda0(this, 0);
    public int mPendingScreenState = 0;
    public final IBinder mRefreshRateToken = new Binder();

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.systemui.biometrics.AuthController$Callback, com.android.systemui.doze.DozeScreenState$1] */
    public DozeScreenState(DozeMachine.Service service, Handler handler, DozeHost dozeHost, DozeParameters dozeParameters, WakeLock wakeLock, AuthController authController, Provider provider, DozeLog dozeLog, DozeScreenBrightness dozeScreenBrightness) {
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
        updateUdfpsController();
        if (this.mUdfpsController == null) {
            authController.addCallback(r0);
        }
    }

    public final void applyScreenState(int i, boolean z) {
        if (i != 0) {
            Log.d("DozeScreenState", "applyScreenState(" + i + ", shouldWaitForTransitionToAodUi = " + z + ")");
            if (i == 4) {
                if (this.mIsExecutedClockTransition) {
                    this.mIsExecutedClockTransition = false;
                } else {
                    try {
                        if (this.mDrawWakeLock == null) {
                            this.mDrawWakeLock = this.mPowerManager.newWakeLock(128, "DozeScreenState");
                        }
                        this.mDrawWakeLock.acquire(1000L);
                    } catch (Exception e) {
                        EmergencyButton$$ExternalSyntheticOutline0.m58m("applyDrawWakeLock exception = ", e, "DozeScreenState");
                    }
                }
            }
            this.mParameters.mAODParameters.mDozeUiState = i == 2 || i == 4;
            if (LsRune.AOD_FULLSCREEN) {
                updateRefreshRate(i);
            }
            this.mDozeService.setDozeScreenState(i, z);
            if (LsRune.SUBSCREEN_PLUGIN_DISCONNECT_WHEN_UNFOLDING && i == 4) {
                SubScreenManager subScreenManager = this.mSubScreenManager;
                ActionBarContextView$$ExternalSyntheticOutline0.m9m(new StringBuilder("runPendingPluginConnectRunnable mPendingPluginConnect="), subScreenManager.mPendingPluginConnect, "SubScreenManager");
                Runnable runnable = subScreenManager.mPendingPluginConnectRunnable;
                if (runnable != null) {
                    runnable.run();
                    Log.d("SubScreenManager", "clearPendingPluginConnectRunnable");
                    subScreenManager.mPendingPluginConnect = false;
                    subScreenManager.mPendingPluginConnectRunnable = null;
                }
            }
            if (LsRune.AOD_TSP_CONTROL && i == 4) {
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

    /* JADX WARN: Code restructure failed: missing block: B:117:0x0013, code lost:
    
        if (r3.mControlScreenOffAnimation != false) goto L11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:119:0x001a, code lost:
    
        if (r3.getDisplayNeedsBlanking() != false) goto L13;
     */
    /* JADX WARN: Removed duplicated region for block: B:104:0x00b3  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x00ac  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00b1  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00b6 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0168  */
    /* JADX WARN: Removed duplicated region for block: B:49:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00d9  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0116  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0124  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0133  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0161 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0158  */
    @Override // com.android.systemui.doze.DozeMachine.Part
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void transitionTo(DozeMachine.State state, DozeMachine.State state2) {
        int i;
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        UdfpsController udfpsController;
        boolean z5;
        boolean z6;
        int i2 = DozeMachine.AbstractC12401.$SwitchMap$com$android$systemui$doze$DozeMachine$State[state2.ordinal()];
        int i3 = 1;
        DozeParameters dozeParameters = this.mParameters;
        switch (i2) {
            case 1:
            case 3:
            case 11:
                i = 1;
                break;
            case 2:
            case 4:
            case 15:
                i = 4;
                break;
            case 5:
            case 7:
            case 8:
            case 12:
            case 13:
            case 14:
                i = 2;
                break;
            case 6:
                break;
            case 9:
            case 10:
                break;
            default:
                i = 0;
                break;
        }
        DozeServiceHost dozeServiceHost = (DozeServiceHost) this.mDozeHost;
        dozeServiceHost.mPendingScreenOffCallback = null;
        if (dozeServiceHost.mScrimController.mState == ScrimState.OFF) {
            ((CentralSurfacesImpl) dozeServiceHost.mCentralSurfaces).updateScrimController();
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
                updateRefreshRate(0);
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
            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("transitionTo INITIALIZED shouldWaitForTransitionToAodUi : ", z, "DozeScreenState");
        } else {
            z = false;
        }
        boolean hasCallbacks = handler.hasCallbacks(dozeScreenState$$ExternalSyntheticLambda0);
        if (state == DozeMachine.State.DOZE_PULSE_DONE) {
            if (state2 == DozeMachine.State.DOZE_AOD || state2 == DozeMachine.State.DOZE_AOD_DOCKED) {
                z2 = true;
                if (state != DozeMachine.State.DOZE_AOD_PAUSED || state == DozeMachine.State.DOZE) {
                    if (state2 != DozeMachine.State.DOZE_AOD || state2 == DozeMachine.State.DOZE_AOD_DOCKED) {
                        z3 = true;
                        boolean z7 = state == state4;
                        if (!hasCallbacks || z7 || z2 || z3) {
                            this.mPendingScreenState = i;
                            if (state2 == DozeMachine.State.DOZE_AOD) {
                                if (!(dozeParameters.mControlScreenOffAnimation && dozeParameters.mKeyguardVisible)) {
                                    List list = dozeParameters.mScreenOffAnimationController.animations;
                                    if (!(list instanceof Collection) || !((ArrayList) list).isEmpty()) {
                                        Iterator it = ((ArrayList) list).iterator();
                                        while (it.hasNext()) {
                                            if (((ScreenOffAnimation) it.next()).shouldDelayDisplayDozeTransition()) {
                                                z6 = true;
                                                if (!z6) {
                                                    z5 = false;
                                                    if (z5 && !z3) {
                                                        z4 = true;
                                                        boolean z8 = (state2 == DozeMachine.State.DOZE_AOD || (udfpsController = this.mUdfpsController) == null || !udfpsController.mOnFingerDown) ? false : true;
                                                        boolean z9 = DEBUG;
                                                        if (!hasCallbacks) {
                                                            if (z9) {
                                                                RecyclerView$$ExternalSyntheticOutline0.m46m(AbstractC0000x2c234b15.m1m("Display state changed to ", i, " delayed by "), z4 ? ImsProfile.DEFAULT_DEREG_TIMEOUT : 1, "DozeScreenState");
                                                            }
                                                            handler.post(dozeScreenState$$ExternalSyntheticLambda0);
                                                            if (state2 == DozeMachine.State.DOZE_TRANSITION_ENDED) {
                                                                this.mIsExecutedClockTransition = true;
                                                                handler.post(new DozeScreenState$$ExternalSyntheticLambda0(this, i3));
                                                            }
                                                        } else if (z9) {
                                                            ListPopupWindow$$ExternalSyntheticOutline0.m10m("Pending display state change to ", i, "DozeScreenState");
                                                        }
                                                        if (!z4 || z8) {
                                                            settableWakeLock.setAcquired(true);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    z6 = false;
                                    if (!z6) {
                                    }
                                }
                                z5 = true;
                                if (z5) {
                                    z4 = true;
                                    if (state2 == DozeMachine.State.DOZE_AOD) {
                                    }
                                    boolean z92 = DEBUG;
                                    if (!hasCallbacks) {
                                    }
                                    if (!z4) {
                                    }
                                    settableWakeLock.setAcquired(true);
                                }
                            }
                            z4 = false;
                            if (state2 == DozeMachine.State.DOZE_AOD) {
                            }
                            boolean z922 = DEBUG;
                            if (!hasCallbacks) {
                            }
                            if (!z4) {
                            }
                            settableWakeLock.setAcquired(true);
                        } else {
                            applyScreenState(i, z);
                            if (state2 == DozeMachine.State.DOZE_TRANSITION_ENDED) {
                                this.mIsExecutedClockTransition = true;
                                ((PluginAODManager) this.mPluginAODManagerLazy.get()).enableTouch(true);
                            }
                        }
                        if (z && state2 == DozeMachine.State.INITIALIZED) {
                            ((PluginAODManager) this.mPluginAODManagerLazy.get()).enableTouch(false);
                            ((PluginAODManager) this.mPluginAODManagerLazy.get()).mClockTransitionStarted = true;
                            return;
                        }
                        return;
                    }
                }
                z3 = false;
                if (state == state4) {
                }
                if (hasCallbacks) {
                }
                this.mPendingScreenState = i;
                if (state2 == DozeMachine.State.DOZE_AOD) {
                }
                z4 = false;
                if (state2 == DozeMachine.State.DOZE_AOD) {
                }
                boolean z9222 = DEBUG;
                if (!hasCallbacks) {
                }
                if (!z4) {
                }
                settableWakeLock.setAcquired(true);
                if (z) {
                    return;
                } else {
                    return;
                }
            }
        }
        z2 = false;
        if (state != DozeMachine.State.DOZE_AOD_PAUSED) {
        }
        if (state2 != DozeMachine.State.DOZE_AOD || state2 == DozeMachine.State.DOZE_AOD_DOCKED) {
        }
        z3 = false;
        if (state == state4) {
        }
        if (hasCallbacks) {
        }
        this.mPendingScreenState = i;
        if (state2 == DozeMachine.State.DOZE_AOD) {
        }
        z4 = false;
        if (state2 == DozeMachine.State.DOZE_AOD) {
        }
        boolean z92222 = DEBUG;
        if (!hasCallbacks) {
        }
        if (!z4) {
        }
        settableWakeLock.setAcquired(true);
        if (z) {
        }
    }

    public final void updateRefreshRate(int i) {
        if (LsRune.AOD_FULLSCREEN) {
            boolean z = i == 4;
            Log.i("DozeScreenState", "updateRefreshRate: displayState=" + i + " dozeSuspend=" + z);
            if (!z) {
                IRefreshRateToken iRefreshRateToken = this.mMaxRefreshRateToken;
                if (iRefreshRateToken != null) {
                    try {
                        iRefreshRateToken.release();
                        Log.d("DozeScreenState", "updateRefreshRate disabled");
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    this.mMaxRefreshRateToken = null;
                    return;
                }
                return;
            }
            if (this.mMaxRefreshRateToken == null) {
                if (this.mIDisplayManager == null) {
                    this.mIDisplayManager = IDisplayManager.Stub.asInterface(ServiceManager.getService("display"));
                }
                IDisplayManager iDisplayManager = this.mIDisplayManager;
                if (iDisplayManager != null) {
                    try {
                        this.mMaxRefreshRateToken = iDisplayManager.acquireRefreshRateMaxLimitToken(this.mRefreshRateToken, 30, "DozeScreenState");
                        Log.d("DozeScreenState", "updateRefreshRate enabled 30hz");
                    } catch (RemoteException e2) {
                        e2.printStackTrace();
                    }
                }
            }
            if (this.mMaxRefreshRateToken == null) {
                Log.w("DozeScreenState", "updateRefreshRate failed");
            }
        }
    }

    public final void updateUdfpsController() {
        if (this.mAuthController.isUdfpsEnrolled(KeyguardUpdateMonitor.getCurrentUser())) {
            this.mUdfpsController = (UdfpsController) this.mUdfpsControllerProvider.get();
        } else {
            this.mUdfpsController = null;
        }
    }
}
