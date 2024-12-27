package com.android.server.desktopmode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.os.Handler;
import android.os.PowerManager;
import android.os.UserHandle;
import android.util.IndentingPrintWriter;

import com.android.server.input.InputManagerService;

import com.samsung.android.cover.CoverManager;
import com.samsung.android.cover.CoverState;
import com.samsung.android.desktopmode.DesktopModeFeature;
import com.samsung.android.desktopmode.SemDesktopModeManager;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class CoverStateManager {
    public final Context mContext;
    public final CoverManager mCoverManager;
    public final Handler mHandler;
    public final InputManagerService mInputManagerService;
    public final boolean mIsNfcAuthSystemFeatureEnabled;
    public final SemDesktopModeManager mManager;
    public final PowerManager mPowerManager;
    public final IStateManager mStateManager;
    public final Object mLock = new Object();
    public boolean mCoverManagerDisabled = false;
    public boolean mDesktopDockConnected = false;
    public boolean mScreenMirroringDisabled = false;
    public boolean mAuthComplete = false;
    public int mCoverSupportState = -1;
    public CoverState mCoverState = new CoverState();
    public final AnonymousClass1 mCoverStateListener =
            new CoverManager.StateListener() { // from class:
                // com.android.server.desktopmode.CoverStateManager.1
                public final void onCoverStateChanged(CoverState coverState) {
                    boolean z = DesktopModeFeature.DEBUG;
                    if (z) {
                        Log.d("[DMS]CoverStateManager", "mCoverState=" + coverState);
                    }
                    synchronized (CoverStateManager.this.mLock) {
                        try {
                            CoverStateManager coverStateManager = CoverStateManager.this;
                            if (coverStateManager.mCoverManagerDisabled
                                    && coverState.attached
                                            == coverStateManager.mCoverState.attached) {
                                if (z) {
                                    Log.d(
                                            "[DMS]CoverStateManager",
                                            "onCoverStateChanged - mCoverManagerDisabled &&"
                                                + " state.attached == mCoverState.attached");
                                }
                            } else {
                                coverStateManager.mCoverState = coverState;
                                coverStateManager.updateCoverSupportStateLocked();
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }
            };
    public final AnonymousClass2 mStateListener =
            new StateManager
                    .StateListener() { // from class:
                                       // com.android.server.desktopmode.CoverStateManager.2
                @Override // com.android.server.desktopmode.StateManager.StateListener
                public final void onDockStateChanged(StateManager.InternalState internalState) {
                    synchronized (CoverStateManager.this.mLock) {
                        try {
                            if (CoverStateManager.this.mDesktopDockConnected
                                    != internalState.mDockState.isDexStation()) {
                                CoverStateManager.this.mDesktopDockConnected =
                                        internalState.mDockState.isDexStation();
                                CoverStateManager.this.updateCoverSupportStateLocked();
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                @Override // com.android.server.desktopmode.StateManager.StateListener
                public final void onDualModeStartLoadingScreen(boolean z) {}

                @Override // com.android.server.desktopmode.StateManager.StateListener
                public final void onDualModeStopLoadingScreen(boolean z) {}

                @Override // com.android.server.desktopmode.StateManager.StateListener
                public final void onExternalDisplayConnectionChanged(
                        StateManager.InternalState internalState) {
                    synchronized (CoverStateManager.this.mLock) {
                        CoverStateManager.this.updateCoverSupportStateLocked();
                    }
                }
            };
    public final AnonymousClass3 mBlocker =
            new SemDesktopModeManager
                    .DesktopModeBlocker() { // from class:
                                            // com.android.server.desktopmode.CoverStateManager.3
                public final String onBlocked() {
                    if (CoverStateManager.this.mAuthComplete) {
                        Log.w("[DMS]CoverStateManager", "onBlocked(), Unknown reason");
                        return null;
                    }
                    Log.i("[DMS]CoverStateManager", "onBlocked(), !mAuthComplete");
                    return null;
                }
            };

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Authenticator extends BroadcastReceiver {

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        /* renamed from: com.android.server.desktopmode.CoverStateManager$Authenticator$1, reason: invalid class name */
        public final class AnonymousClass1
                implements InputManagerService.DesktopModeServiceCallbacks {
            public AnonymousClass1() {}
        }

        public Authenticator() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (DesktopModeFeature.DEBUG) {
                Log.d("[DMS]CoverStateManager", "onReceive(), action=" + intent.getAction());
            }
            CoverStateManager.this.mHandler.removeCallbacksAndMessages(null);
            setAuthComplete();
            CoverStateManager coverStateManager = CoverStateManager.this;
            coverStateManager.mInputManagerService.mDesktopModeServiceCallbacks = null;
            coverStateManager.mContext.unregisterReceiver(this);
        }

        public final void setAuthComplete() {
            synchronized (CoverStateManager.this.mLock) {
                try {
                    CoverStateManager coverStateManager = CoverStateManager.this;
                    if (!coverStateManager.mAuthComplete) {
                        coverStateManager.mAuthComplete = true;
                        if (!CoverStateManager.m395$$Nest$minitializeCoverState(
                                coverStateManager)) {
                            ((StateManager) CoverStateManager.this.mStateManager)
                                    .notifyScheduleUpdateDesktopMode(true);
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* renamed from: -$$Nest$minitializeCoverState, reason: not valid java name */
    public static boolean m395$$Nest$minitializeCoverState(CoverStateManager coverStateManager) {
        boolean updateCoverSupportStateLocked;
        synchronized (coverStateManager.mLock) {
            try {
                CoverState coverState = coverStateManager.mCoverManager.getCoverState();
                if (coverState != null) {
                    coverStateManager.mCoverState = coverState;
                }
                coverStateManager.mDesktopDockConnected =
                        ((StateManager) coverStateManager.mStateManager)
                                .getState()
                                .mDockState
                                .isDexStation();
                updateCoverSupportStateLocked = coverStateManager.updateCoverSupportStateLocked();
                coverStateManager.mCoverManager.registerListener(
                        coverStateManager.mCoverStateListener);
                ((StateManager) coverStateManager.mStateManager)
                        .registerListener(coverStateManager.mStateListener);
            } catch (Throwable th) {
                throw th;
            }
        }
        return updateCoverSupportStateLocked;
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.server.desktopmode.CoverStateManager$1] */
    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.server.desktopmode.CoverStateManager$2] */
    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.server.desktopmode.CoverStateManager$3] */
    public CoverStateManager(
            Context context,
            IStateManager iStateManager,
            SemDesktopModeManager semDesktopModeManager,
            PowerManager powerManager,
            InputManagerService inputManagerService) {
        InputManagerService inputManagerService2;
        this.mContext = context;
        this.mStateManager = iStateManager;
        this.mCoverManager = new CoverManager(context);
        this.mManager = semDesktopModeManager;
        Handler handler = new Handler();
        this.mHandler = handler;
        final Authenticator authenticator = new Authenticator();
        if (this.mIsNfcAuthSystemFeatureEnabled
                && (inputManagerService2 = this.mInputManagerService) != null
                && inputManagerService2.mNative.getSwitchState(-1, -256, 27) == 1) {
            context.registerReceiverAsUser(
                    authenticator,
                    UserHandle.ALL,
                    new IntentFilter(
                            "com.samsung.android.intent.action.ACCESSORY_AUTHENTICATION_STOPPED"),
                    null,
                    null,
                    2);
            InputManagerService inputManagerService3 = this.mInputManagerService;
            if (inputManagerService3 != null) {
                inputManagerService3.mDesktopModeServiceCallbacks =
                        authenticator.new AnonymousClass1();
            }
            handler.removeCallbacksAndMessages(null);
            handler.postDelayed(
                    new Runnable() { // from class:
                                     // com.android.server.desktopmode.CoverStateManager$Authenticator$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            CoverStateManager.Authenticator authenticator2 =
                                    CoverStateManager.Authenticator.this;
                            authenticator2.getClass();
                            if (DesktopModeFeature.DEBUG) {
                                Log.d(
                                        "[DMS]CoverStateManager",
                                        "Cover auth timeout, mAuthComplete="
                                                + CoverStateManager.this.mAuthComplete);
                            }
                            if (CoverStateManager.this.mAuthComplete) {
                                return;
                            }
                            authenticator2.setAuthComplete();
                            CoverStateManager coverStateManager = CoverStateManager.this;
                            coverStateManager.mInputManagerService.mDesktopModeServiceCallbacks =
                                    null;
                            coverStateManager.mContext.unregisterReceiver(authenticator2);
                        }
                    },
                    20000L);
        } else {
            authenticator.setAuthComplete();
        }
        this.mPowerManager = powerManager;
        this.mInputManagerService = inputManagerService;
        this.mIsNfcAuthSystemFeatureEnabled =
                context.getPackageManager()
                        .hasSystemFeature("com.sec.feature.nfc_authentication_cover");
    }

    public static String coverSupportStateToString(int i) {
        return i != 1
                ? i != 2
                        ? i != 3
                                ? VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Unknown=")
                                : "COVER_SUPPORT_STATE_NONE"
                        : "COVER_SUPPORT_STATE_PARTIAL"
                : "COVER_SUPPORT_STATE_FULL";
    }

    public static boolean isFlipTypeCover(CoverState coverState) {
        int i = coverState.type;
        return i == 7
                || i == 0
                || i == 11
                || i == 8
                || i == 15
                || i == 1
                || i == 3
                || i == 6
                || coverState.friendsType == 2;
    }

    public final void disableCoverManager(boolean z) {
        synchronized (this.mLock) {
            try {
                if (this.mCoverManagerDisabled != z) {
                    if (DesktopModeFeature.DEBUG) {
                        Log.d(
                                "[DMS]CoverStateManager",
                                "disableCoverManager()=" + this.mCoverManagerDisabled + " -> " + z);
                    }
                    this.mCoverManagerDisabled = z;
                    this.mCoverManager.disableCoverManager(z);
                    if (!z) {
                        this.mCoverState = this.mCoverManager.getCoverState();
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void dump(IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("Current CoverStateManager state:");
        indentingPrintWriter.increaseIndent();
        synchronized (this.mLock) {
            indentingPrintWriter.println("mAuthComplete=" + this.mAuthComplete);
            indentingPrintWriter.println("mCoverManagerDisabled=" + this.mCoverManagerDisabled);
            indentingPrintWriter.println("mCoverState=" + this.mCoverState);
            indentingPrintWriter.println(
                    "mCoverSupportState=" + coverSupportStateToString(this.mCoverSupportState));
            indentingPrintWriter.println("mDesktopDockConnected=" + this.mDesktopDockConnected);
            indentingPrintWriter.println(
                    "mScreenMirroringDisabled=" + this.mScreenMirroringDisabled);
            indentingPrintWriter.println(
                    "mIsNfcAuthSystemFeatureEnabled=" + this.mIsNfcAuthSystemFeatureEnabled);
        }
        indentingPrintWriter.decreaseIndent();
    }

    public final boolean updateCoverSupportStateLocked() {
        boolean z;
        boolean z2;
        int i =
                !this.mAuthComplete
                        ? 3
                        : (!this.mCoverState.attached
                                        || ((StateManager) this.mStateManager)
                                                .getState()
                                                .isDexOnPcOrWirelessDexConnected()
                                        || DesktopModeFeature.IS_TABLET
                                        || !this.mDesktopDockConnected
                                        || this.mCoverState.switchState)
                                ? 1
                                : 2;
        if (this.mCoverSupportState != i) {
            this.mCoverSupportState = i;
            if (i == 3) {
                this.mManager.registerBlocker(this.mBlocker);
            } else {
                this.mManager.unregisterBlocker(this.mBlocker);
            }
            IStateManager iStateManager = this.mStateManager;
            CoverState coverState = this.mCoverState;
            int i2 = this.mCoverSupportState;
            StateManager stateManager = (StateManager) iStateManager;
            stateManager.getClass();
            if (DesktopModeFeature.DEBUG) {
                Log.d(
                        "[DMS]StateManager",
                        "setCoverState(coverState="
                                + coverState
                                + ", coverSupportState="
                                + i2
                                + ")");
            }
            synchronized (stateManager.mLock) {
                try {
                    if (stateManager.mInternalState.mCoverSupportState != i2) {
                        stateManager.mInternalState.mCoverSupportState = i2;
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    stateManager.mInternalState.mCoverState = coverState;
                    StateManager.InternalState copyInternalStateLocked =
                            stateManager.copyInternalStateLocked(stateManager.mInternalState);
                    if (z2) {
                        stateManager.mHandler.post(
                                new StateManager$$ExternalSyntheticLambda2(
                                        stateManager, copyInternalStateLocked, 2));
                    }
                } finally {
                }
            }
            z = true;
        } else {
            z = false;
        }
        if (this.mDesktopDockConnected && this.mCoverSupportState == 2) {
            this.mScreenMirroringDisabled = true;
            DesktopModeSettings.setSettings(
                    this.mContext.getContentResolver(), "mirroring_switch_disabled", true);
        } else if (this.mScreenMirroringDisabled) {
            this.mScreenMirroringDisabled = false;
            DesktopModeSettings.setSettings(
                    this.mContext.getContentResolver(), "mirroring_switch_disabled", false);
        }
        if (DesktopModeFeature.DEBUG) {
            Log.d(
                    "[DMS]CoverStateManager",
                    "updateCoverSupportState(), mCoverSupportState="
                            + coverSupportStateToString(this.mCoverSupportState)
                            + ", mCoverState="
                            + this.mCoverState
                            + ", mAuthComplete="
                            + this.mAuthComplete
                            + ", mDesktopDockConnected="
                            + this.mDesktopDockConnected);
        }
        return z;
    }
}
