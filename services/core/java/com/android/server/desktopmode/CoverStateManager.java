package com.android.server.desktopmode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.PowerManager;
import android.os.SystemClock;
import android.os.UserHandle;
import android.util.IndentingPrintWriter;
import com.android.server.desktopmode.CoverStateManager;
import com.android.server.desktopmode.StateManager;
import com.android.server.input.InputManagerService;
import com.samsung.android.cover.CoverManager;
import com.samsung.android.cover.CoverState;
import com.samsung.android.desktopmode.DesktopModeFeature;
import com.samsung.android.desktopmode.SemDesktopModeManager;

/* loaded from: classes2.dex */
public class CoverStateManager {
    public static final String TAG = "[DMS]" + CoverStateManager.class.getSimpleName();
    public final Context mContext;
    public final CoverManager mCoverManager;
    public final InputManagerService mInputManagerService;
    public final boolean mIsNfcAuthSystemFeatureEnabled;
    public final SemDesktopModeManager mManager;
    public final PowerManager mPowerManager;
    public final IStateManager mStateManager;
    public final Object mLock = new Object();
    public boolean mBlockState = false;
    public boolean mCoverManagerDisabled = false;
    public boolean mDesktopDockConnected = false;
    public boolean mScreenMirroringDisabled = false;
    public boolean mAuthComplete = false;
    public int mCoverSupportState = -1;
    public CoverState mCoverState = new CoverState();
    public final CoverManager.StateListener mCoverStateListener = new CoverManager.StateListener() { // from class: com.android.server.desktopmode.CoverStateManager.1
        public void onCoverStateChanged(CoverState coverState) {
            if (DesktopModeFeature.DEBUG) {
                Log.m7d(CoverStateManager.TAG, "mCoverState=" + coverState);
            }
            synchronized (CoverStateManager.this.mLock) {
                if (CoverStateManager.this.mCoverManagerDisabled && coverState.attached == CoverStateManager.this.mCoverState.attached) {
                    if (DesktopModeFeature.DEBUG) {
                        Log.m7d(CoverStateManager.TAG, "onCoverStateChanged - mCoverManagerDisabled && state.attached == mCoverState.attached");
                    }
                } else {
                    CoverStateManager.this.mCoverState = coverState;
                    CoverStateManager.this.updateCoverSupportStateLocked();
                }
            }
        }
    };
    public final StateManager.StateListener mStateListener = new StateManager.StateListener() { // from class: com.android.server.desktopmode.CoverStateManager.2
        @Override // com.android.server.desktopmode.StateManager.StateListener
        public void onDualModeStartLoadingScreen(boolean z) {
        }

        @Override // com.android.server.desktopmode.StateManager.StateListener
        public void onDualModeStopLoadingScreen(boolean z) {
        }

        @Override // com.android.server.desktopmode.StateManager.StateListener
        public void onExternalDisplayConnectionChanged(State state) {
            synchronized (CoverStateManager.this.mLock) {
                CoverStateManager.this.updateCoverSupportStateLocked();
            }
        }

        @Override // com.android.server.desktopmode.StateManager.StateListener
        public void onDockStateChanged(State state) {
            synchronized (CoverStateManager.this.mLock) {
                if (CoverStateManager.this.mDesktopDockConnected != state.getDockState().isDexStation()) {
                    CoverStateManager.this.mDesktopDockConnected = state.getDockState().isDexStation();
                    CoverStateManager.this.updateCoverSupportStateLocked();
                }
            }
        }
    };
    public final SemDesktopModeManager.DesktopModeBlocker mBlocker = new SemDesktopModeManager.DesktopModeBlocker() { // from class: com.android.server.desktopmode.CoverStateManager.3
        public String onBlocked() {
            if (!CoverStateManager.this.mAuthComplete) {
                Log.m10i(CoverStateManager.TAG, "onBlocked(), !mAuthComplete");
                return null;
            }
            Log.m12w(CoverStateManager.TAG, "onBlocked(), Unknown reason");
            return null;
        }
    };
    public final Handler mHandler = new Handler();

    public class Authenticator extends BroadcastReceiver {
        public Authenticator() {
        }

        public void initialize() {
            if (isAuthNeeded()) {
                CoverStateManager.this.mContext.registerReceiverAsUser(this, UserHandle.ALL, new IntentFilter("com.samsung.android.intent.action.ACCESSORY_AUTHENTICATION_STOPPED"), null, null);
                if (CoverStateManager.this.mInputManagerService != null) {
                    CoverStateManager.this.mInputManagerService.setDesktopModeServiceCallbacks(new InputManagerService.DesktopModeServiceCallbacks() { // from class: com.android.server.desktopmode.CoverStateManager.Authenticator.1
                        @Override // com.android.server.input.InputManagerService.DesktopModeServiceCallbacks
                        public void notifyUnverifiedCoverAttachChanged(long j, boolean z) {
                            if (DesktopModeFeature.DEBUG) {
                                Log.m7d(CoverStateManager.TAG, "notifyUnverifiedCoverAttachChanged, attached=" + z);
                            }
                            if (z) {
                                return;
                            }
                            CoverStateManager.this.mHandler.removeCallbacksAndMessages(null);
                            Authenticator.this.setAuthCompleteAndResetCallbacksReceiver();
                        }
                    });
                }
                CoverStateManager.this.mHandler.removeCallbacksAndMessages(null);
                CoverStateManager.this.mHandler.postDelayed(new Runnable() { // from class: com.android.server.desktopmode.CoverStateManager$Authenticator$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        CoverStateManager.Authenticator.this.lambda$initialize$0();
                    }
                }, 20000L);
                return;
            }
            setAuthComplete();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$initialize$0() {
            if (DesktopModeFeature.DEBUG) {
                Log.m7d(CoverStateManager.TAG, "Cover auth timeout, mAuthComplete=" + CoverStateManager.this.mAuthComplete);
            }
            if (CoverStateManager.this.mAuthComplete) {
                return;
            }
            setAuthCompleteAndResetCallbacksReceiver();
        }

        public boolean isAuthNeeded() {
            return CoverStateManager.this.mIsNfcAuthSystemFeatureEnabled && CoverStateManager.this.mInputManagerService != null && CoverStateManager.this.mInputManagerService.getSwitchState(-1, -256, 27) == 1;
        }

        public void setAuthComplete() {
            synchronized (CoverStateManager.this.mLock) {
                if (!CoverStateManager.this.mAuthComplete) {
                    CoverStateManager.this.mAuthComplete = true;
                    if (!CoverStateManager.this.initializeCoverState()) {
                        CoverStateManager.this.mStateManager.notifyScheduleUpdateDesktopMode(true);
                    }
                }
            }
        }

        public void setAuthCompleteAndResetCallbacksReceiver() {
            setAuthComplete();
            CoverStateManager.this.mInputManagerService.setDesktopModeServiceCallbacks(null);
            CoverStateManager.this.mContext.unregisterReceiver(this);
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (DesktopModeFeature.DEBUG) {
                Log.m7d(CoverStateManager.TAG, "onReceive(), action=" + intent.getAction());
            }
            CoverStateManager.this.mHandler.removeCallbacksAndMessages(null);
            setAuthCompleteAndResetCallbacksReceiver();
        }
    }

    public CoverStateManager(Context context, IStateManager iStateManager, SemDesktopModeManager semDesktopModeManager, PowerManager powerManager, InputManagerService inputManagerService) {
        this.mContext = context;
        this.mStateManager = iStateManager;
        this.mCoverManager = new CoverManager(context);
        this.mManager = semDesktopModeManager;
        new Authenticator().initialize();
        this.mPowerManager = powerManager;
        this.mInputManagerService = inputManagerService;
        this.mIsNfcAuthSystemFeatureEnabled = context.getPackageManager().hasSystemFeature("com.sec.feature.nfc_authentication_cover");
    }

    public void initialize() {
        synchronized (this.mLock) {
            this.mScreenMirroringDisabled = getSettingMirroringSwitchDisabled();
            updateCoverSupportStateLocked();
        }
    }

    public final boolean initializeCoverState() {
        boolean updateCoverSupportStateLocked;
        synchronized (this.mLock) {
            CoverState coverState = this.mCoverManager.getCoverState();
            if (coverState != null) {
                this.mCoverState = coverState;
            }
            this.mDesktopDockConnected = this.mStateManager.getState().getDockState().isDexStation();
            updateCoverSupportStateLocked = updateCoverSupportStateLocked();
            this.mCoverManager.registerListener(this.mCoverStateListener);
            this.mStateManager.registerListener(this.mStateListener);
        }
        return updateCoverSupportStateLocked;
    }

    public final boolean updateCoverSupportStateLocked() {
        int i;
        boolean z;
        if (this.mAuthComplete) {
            i = (!this.mCoverState.attached || this.mStateManager.getState().isDexOnPcOrWirelessDexConnected() || DesktopModeFeature.IS_TABLET || !this.mDesktopDockConnected || this.mCoverState.switchState) ? 1 : 2;
        } else {
            i = 3;
        }
        if (this.mCoverSupportState != i) {
            this.mCoverSupportState = i;
            if (i == 3) {
                this.mManager.registerBlocker(this.mBlocker);
            } else {
                this.mManager.unregisterBlocker(this.mBlocker);
            }
            this.mStateManager.setCoverState(this.mCoverState, this.mCoverSupportState);
            z = true;
        } else {
            z = false;
        }
        if (this.mDesktopDockConnected && this.mCoverSupportState == 2) {
            setMirroringSwitchDisabled(true);
        } else if (this.mScreenMirroringDisabled) {
            setMirroringSwitchDisabled(false);
        }
        if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "updateCoverSupportState(), mCoverSupportState=" + coverSupportStateToString(this.mCoverSupportState) + ", mCoverState=" + this.mCoverState + ", mAuthComplete=" + this.mAuthComplete + ", mDesktopDockConnected=" + this.mDesktopDockConnected);
        }
        return z;
    }

    public final void setMirroringSwitchDisabled(boolean z) {
        this.mScreenMirroringDisabled = z;
        DesktopModeSettings.setSettings(this.mContext.getContentResolver(), "mirroring_switch_disabled", z);
    }

    public final boolean getSettingMirroringSwitchDisabled() {
        return DesktopModeSettings.getSettings(this.mContext.getContentResolver(), "mirroring_switch_disabled", false);
    }

    public void disableCoverManager(boolean z) {
        synchronized (this.mLock) {
            if (this.mCoverManagerDisabled != z) {
                if (DesktopModeFeature.DEBUG) {
                    Log.m7d(TAG, "disableCoverManager()=" + this.mCoverManagerDisabled + " -> " + z);
                }
                this.mCoverManagerDisabled = z;
                this.mCoverManager.disableCoverManager(z);
                if (!z) {
                    this.mCoverState = this.mCoverManager.getCoverState();
                }
            }
        }
    }

    public final void goToSleep() {
        this.mPowerManager.goToSleep(SystemClock.uptimeMillis());
    }

    public boolean goToSleepIfFlipTypeCoverClosed() {
        CoverState coverState = this.mCoverState;
        if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "goToSleepIfFlipTypeCoverClosed(), coverState=" + coverState);
        }
        if (!coverState.attached || !isFlipTypeCover(coverState) || isCoverUiTypeCover(coverState) || coverState.switchState) {
            return false;
        }
        goToSleep();
        return true;
    }

    public static boolean isFlipTypeCover(CoverState coverState) {
        int i = coverState.type;
        return i == 7 || i == 0 || i == 11 || i == 8 || i == 15 || i == 1 || i == 3 || i == 6 || coverState.friendsType == 2;
    }

    public static boolean isCoverUiTypeCover(CoverState coverState) {
        int i = coverState.type;
        return i == 1 || i == 3 || i == 6 || i == 8 || i == 15;
    }

    public boolean isFlipTypeCoverClosed() {
        CoverState coverState = this.mCoverManager.getCoverState();
        return coverState != null && coverState.getAttachState() && isFlipTypeCover(coverState) && !coverState.getSwitchState();
    }

    public static String coverSupportStateToString(int i) {
        if (i == 1) {
            return "COVER_SUPPORT_STATE_FULL";
        }
        if (i == 2) {
            return "COVER_SUPPORT_STATE_PARTIAL";
        }
        if (i == 3) {
            return "COVER_SUPPORT_STATE_NONE";
        }
        return "Unknown=" + i;
    }

    public void dump(IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("Current " + CoverStateManager.class.getSimpleName() + " state:");
        indentingPrintWriter.increaseIndent();
        synchronized (this.mLock) {
            indentingPrintWriter.println("mAuthComplete=" + this.mAuthComplete);
            indentingPrintWriter.println("mCoverManagerDisabled=" + this.mCoverManagerDisabled);
            indentingPrintWriter.println("mCoverState=" + this.mCoverState);
            indentingPrintWriter.println("mCoverSupportState=" + coverSupportStateToString(this.mCoverSupportState));
            indentingPrintWriter.println("mDesktopDockConnected=" + this.mDesktopDockConnected);
            indentingPrintWriter.println("mScreenMirroringDisabled=" + this.mScreenMirroringDisabled);
            indentingPrintWriter.println("mIsNfcAuthSystemFeatureEnabled=" + this.mIsNfcAuthSystemFeatureEnabled);
        }
        indentingPrintWriter.decreaseIndent();
    }
}
