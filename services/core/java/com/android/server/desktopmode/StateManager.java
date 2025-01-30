package com.android.server.desktopmode;

import android.os.Handler;
import android.util.ArrayMap;
import android.util.IndentingPrintWriter;
import com.android.server.ServiceThread;
import com.android.server.desktopmode.HardwareManager;
import com.samsung.android.cover.CoverState;
import com.samsung.android.desktopmode.DesktopModeFeature;
import com.samsung.android.desktopmode.SemDesktopModeState;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes2.dex */
public class StateManager implements IStateManager {
    public static final String TAG = "[DMS]" + StateManager.class.getSimpleName();
    public final Handler mHandler;
    public volatile InternalState mInternalState;
    public volatile State mState;
    public final Object mLock = new Object();
    public CopyOnWriteArrayList mStateListeners = new CopyOnWriteArrayList();

    public abstract class StateListener {
        public void onBootCompleted() {
        }

        public void onBootInitBlockerRegistered(boolean z) {
        }

        public void onConfigurationChanged(boolean z) {
        }

        public void onCoverSupportStateChanged(State state) {
        }

        public void onDesktopDisplayIdChanged(State state) {
        }

        public void onDesktopModeStateChanged(State state) {
        }

        public void onDisplayDisconnectionRequested(int i) {
        }

        public void onDockLowChargerPowerChanged(State state) {
        }

        public void onDockStateChanged(State state) {
        }

        public void onDualModeConfigurationChanged(boolean z) {
        }

        public void onDualModeSetDesktopMode(State state, boolean z) {
        }

        public void onDualModeSetDesktopModeInternal(boolean z) {
        }

        public void onDualModeStartLoadingScreen(boolean z) {
        }

        public void onDualModeStopLoadingScreen(boolean z) {
        }

        public void onEmergencyModeChanged(State state) {
        }

        public void onExternalDisplayConnectionChanged(State state) {
        }

        public void onExternalDisplayUpdated(State state) {
        }

        public void onForcedInternalScreenStateChanged(State state) {
        }

        public void onLauncherPackageReplaced(boolean z) {
        }

        public void onMouseConnectionChanged(State state) {
        }

        public void onNavBarGestureEnabled(State state) {
        }

        public void onPackageStateChanged(State state) {
        }

        public void onPogoKeyboardConnectionChanged(State state) {
        }

        public void onScheduleUpdateDesktopMode(boolean z) {
        }

        public void onSetDesktopMode(State state, boolean z) {
        }

        public void onSetDesktopModeInternal(boolean z) {
        }

        public void onSpenEnabled(State state) {
        }

        public void onStartLoadingScreen(boolean z) {
        }

        public void onStopLoadingScreen(boolean z) {
        }

        public void onTouchpadAvailabilityChanged(State state) {
        }

        public void onTouchpadEnabled(State state) {
        }

        public void onUserChanged(State state) {
        }

        public void onWiredChargingChanged(State state) {
        }
    }

    public StateManager(ServiceThread serviceThread) {
        InternalState internalState = new InternalState();
        this.mInternalState = internalState;
        this.mState = internalState;
        this.mHandler = new Handler(serviceThread.getLooper());
    }

    @Override // com.android.server.desktopmode.IStateManager
    public State getState() {
        State state;
        synchronized (this.mLock) {
            state = this.mState;
        }
        return state;
    }

    @Override // com.android.server.desktopmode.IStateManager
    public void setExternalDisplayConnected(boolean z, DisplayInfo displayInfo) {
        if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "setExternalDisplayConnected(connected=" + z + ", display=" + displayInfo + ")");
        }
        synchronized (this.mLock) {
            if (this.mInternalState.mIsExternalDisplayConnected != z) {
                this.mInternalState.mIsExternalDisplayConnected = z;
                this.mInternalState.mConnectedDisplay = displayInfo;
                if (!z) {
                    this.mInternalState.mDisplayResolutionUnsupported = false;
                }
                final State copyInternalStateLocked = copyInternalStateLocked(this.mInternalState);
                this.mHandler.post(new Runnable() { // from class: com.android.server.desktopmode.StateManager$$ExternalSyntheticLambda15
                    @Override // java.lang.Runnable
                    public final void run() {
                        StateManager.this.lambda$setExternalDisplayConnected$0(copyInternalStateLocked);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setExternalDisplayConnected$0(State state) {
        Iterator it = this.mStateListeners.iterator();
        while (it.hasNext()) {
            ((StateListener) it.next()).onExternalDisplayConnectionChanged(state);
        }
    }

    @Override // com.android.server.desktopmode.IStateManager
    public void setExternalDisplayUpdated(DisplayInfo displayInfo) {
        if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "setExternalDisplayUpdated(display=" + displayInfo + ")");
        }
        synchronized (this.mLock) {
            if ((this.mInternalState.mConnectedDisplay == null ? -1 : this.mInternalState.mConnectedDisplay.getDisplayId()) != displayInfo.getDisplayId()) {
                this.mInternalState.mPreviousConnectedDisplay = this.mInternalState.mConnectedDisplay;
                this.mInternalState.mConnectedDisplay = displayInfo;
                final State copyInternalStateLocked = copyInternalStateLocked(this.mInternalState);
                this.mHandler.post(new Runnable() { // from class: com.android.server.desktopmode.StateManager$$ExternalSyntheticLambda14
                    @Override // java.lang.Runnable
                    public final void run() {
                        StateManager.this.lambda$setExternalDisplayUpdated$1(copyInternalStateLocked);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setExternalDisplayUpdated$1(State state) {
        Iterator it = this.mStateListeners.iterator();
        while (it.hasNext()) {
            ((StateListener) it.next()).onExternalDisplayUpdated(state);
        }
    }

    @Override // com.android.server.desktopmode.IStateManager
    public void setDesktopDisplayId(int i) {
        if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "setDesktopDisplayId(displayId=" + i + ")");
        }
        synchronized (this.mLock) {
            if (this.mInternalState.mDesktopDisplayId != i) {
                this.mInternalState.mDesktopDisplayId = i;
                final State copyInternalStateLocked = copyInternalStateLocked(this.mInternalState);
                this.mHandler.post(new Runnable() { // from class: com.android.server.desktopmode.StateManager$$ExternalSyntheticLambda17
                    @Override // java.lang.Runnable
                    public final void run() {
                        StateManager.this.lambda$setDesktopDisplayId$2(copyInternalStateLocked);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setDesktopDisplayId$2(State state) {
        Iterator it = this.mStateListeners.iterator();
        while (it.hasNext()) {
            ((StateListener) it.next()).onDesktopDisplayIdChanged(state);
        }
    }

    @Override // com.android.server.desktopmode.IStateManager
    public void setMouseConnected(boolean z) {
        if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "setMouseConnected(connected=" + z + ")");
        }
        synchronized (this.mLock) {
            if (this.mInternalState.mIsMouseConnected != z) {
                this.mInternalState.mIsMouseConnected = z;
                final State copyInternalStateLocked = copyInternalStateLocked(this.mInternalState);
                this.mHandler.post(new Runnable() { // from class: com.android.server.desktopmode.StateManager$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        StateManager.this.lambda$setMouseConnected$3(copyInternalStateLocked);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setMouseConnected$3(State state) {
        Iterator it = this.mStateListeners.iterator();
        while (it.hasNext()) {
            ((StateListener) it.next()).onMouseConnectionChanged(state);
        }
    }

    @Override // com.android.server.desktopmode.IStateManager
    public void setPogoKeyboardConnected(boolean z) {
        if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "setPogoKeyboardConnected(state=" + z + ")");
        }
        synchronized (this.mLock) {
            if (this.mInternalState.mIsPogoKeyboardConnected != z) {
                this.mInternalState.mIsPogoKeyboardConnected = z;
                final State copyInternalStateLocked = copyInternalStateLocked(this.mInternalState);
                this.mHandler.post(new Runnable() { // from class: com.android.server.desktopmode.StateManager$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        StateManager.this.lambda$setPogoKeyboardConnected$4(copyInternalStateLocked);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setPogoKeyboardConnected$4(State state) {
        Iterator it = this.mStateListeners.iterator();
        while (it.hasNext()) {
            ((StateListener) it.next()).onPogoKeyboardConnectionChanged(state);
        }
    }

    @Override // com.android.server.desktopmode.IStateManager
    public void setWiredCharging(boolean z) {
        if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "setWiredCharging(isWiredCharging=" + z + ")");
        }
        synchronized (this.mLock) {
            if (this.mInternalState.mIsWiredCharging != z) {
                this.mInternalState.mIsWiredCharging = z;
                final State copyInternalStateLocked = copyInternalStateLocked(this.mInternalState);
                this.mHandler.post(new Runnable() { // from class: com.android.server.desktopmode.StateManager$$ExternalSyntheticLambda10
                    @Override // java.lang.Runnable
                    public final void run() {
                        StateManager.this.lambda$setWiredCharging$5(copyInternalStateLocked);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setWiredCharging$5(State state) {
        Iterator it = this.mStateListeners.iterator();
        while (it.hasNext()) {
            ((StateListener) it.next()).onWiredChargingChanged(state);
        }
    }

    @Override // com.android.server.desktopmode.IStateManager
    public void setDockState(HardwareManager.DockState dockState) {
        if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "setDockState(dockState=" + dockState + ")");
        }
        synchronized (this.mLock) {
            this.mInternalState.mPreviousDockState = this.mInternalState.mDockState;
            this.mInternalState.mDockState = dockState;
            final State copyInternalStateLocked = copyInternalStateLocked(this.mInternalState);
            this.mHandler.post(new Runnable() { // from class: com.android.server.desktopmode.StateManager$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    StateManager.this.lambda$setDockState$6(copyInternalStateLocked);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setDockState$6(State state) {
        Iterator it = this.mStateListeners.iterator();
        while (it.hasNext()) {
            ((StateListener) it.next()).onDockStateChanged(state);
        }
    }

    @Override // com.android.server.desktopmode.IStateManager
    public void setForcedInternalScreenModeEnabled(boolean z) {
        if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "setForcedInternalScreenModeEnabled(enabled=" + z + ")");
        }
        synchronized (this.mLock) {
            if (this.mInternalState.mForcedInternalScreenModeEnabled != z) {
                this.mInternalState.mForcedInternalScreenModeEnabled = z;
                final State copyInternalStateLocked = copyInternalStateLocked(this.mInternalState);
                this.mHandler.post(new Runnable() { // from class: com.android.server.desktopmode.StateManager$$ExternalSyntheticLambda7
                    @Override // java.lang.Runnable
                    public final void run() {
                        StateManager.this.lambda$setForcedInternalScreenModeEnabled$7(copyInternalStateLocked);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setForcedInternalScreenModeEnabled$7(State state) {
        Iterator it = this.mStateListeners.iterator();
        while (it.hasNext()) {
            ((StateListener) it.next()).onForcedInternalScreenStateChanged(state);
        }
    }

    @Override // com.android.server.desktopmode.IStateManager
    public void setDockLowChargerState(int i) {
        if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "setDockLowChargerState(dockLowChargerState=" + i + ")");
        }
        synchronized (this.mLock) {
            if (this.mInternalState.mDockLowChargerState != i) {
                this.mInternalState.mDockLowChargerState = i;
                final State copyInternalStateLocked = copyInternalStateLocked(this.mInternalState);
                this.mHandler.post(new Runnable() { // from class: com.android.server.desktopmode.StateManager$$ExternalSyntheticLambda9
                    @Override // java.lang.Runnable
                    public final void run() {
                        StateManager.this.lambda$setDockLowChargerState$8(copyInternalStateLocked);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setDockLowChargerState$8(State state) {
        Iterator it = this.mStateListeners.iterator();
        while (it.hasNext()) {
            ((StateListener) it.next()).onDockLowChargerPowerChanged(state);
        }
    }

    @Override // com.android.server.desktopmode.IStateManager
    public void setCoverState(CoverState coverState, int i) {
        boolean z;
        if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "setCoverState(coverState=" + coverState + ", coverSupportState=" + i + ")");
        }
        synchronized (this.mLock) {
            if (this.mInternalState.mCoverSupportState != i) {
                this.mInternalState.mCoverSupportState = i;
                z = true;
            } else {
                z = false;
            }
            this.mInternalState.mCoverState = coverState;
            final State copyInternalStateLocked = copyInternalStateLocked(this.mInternalState);
            if (z) {
                this.mHandler.post(new Runnable() { // from class: com.android.server.desktopmode.StateManager$$ExternalSyntheticLambda18
                    @Override // java.lang.Runnable
                    public final void run() {
                        StateManager.this.lambda$setCoverState$9(copyInternalStateLocked);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setCoverState$9(State state) {
        Iterator it = this.mStateListeners.iterator();
        while (it.hasNext()) {
            ((StateListener) it.next()).onCoverSupportStateChanged(state);
        }
    }

    @Override // com.android.server.desktopmode.IStateManager
    public void setCurrentUserId(int i) {
        if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "setCurrentUserId(userId=" + i + ")");
        }
        synchronized (this.mLock) {
            if (this.mInternalState.mCurrentUserId != i) {
                this.mInternalState.mCurrentUserId = i;
                final State copyInternalStateLocked = copyInternalStateLocked(this.mInternalState);
                this.mHandler.post(new Runnable() { // from class: com.android.server.desktopmode.StateManager$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() {
                        StateManager.this.lambda$setCurrentUserId$10(copyInternalStateLocked);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setCurrentUserId$10(State state) {
        Iterator it = this.mStateListeners.iterator();
        while (it.hasNext()) {
            ((StateListener) it.next()).onUserChanged(state);
        }
    }

    @Override // com.android.server.desktopmode.IStateManager
    public void setPackageState(Map map) {
        if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "setPackageState(packageState=" + map + ")");
        }
        synchronized (this.mLock) {
            this.mInternalState.mPackageState = map;
            final State copyInternalStateLocked = copyInternalStateLocked(this.mInternalState);
            this.mHandler.post(new Runnable() { // from class: com.android.server.desktopmode.StateManager$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    StateManager.this.lambda$setPackageState$11(copyInternalStateLocked);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setPackageState$11(State state) {
        Iterator it = this.mStateListeners.iterator();
        while (it.hasNext()) {
            ((StateListener) it.next()).onPackageStateChanged(state);
        }
    }

    @Override // com.android.server.desktopmode.IStateManager
    public void setEmergencyModeEnabled(boolean z) {
        if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "setEmergencyModeEnabled(enabled=" + z + ")");
        }
        synchronized (this.mLock) {
            if (this.mInternalState.mEmergencyModeEnabled != z) {
                this.mInternalState.mEmergencyModeEnabled = z;
                final State copyInternalStateLocked = copyInternalStateLocked(this.mInternalState);
                this.mHandler.post(new Runnable() { // from class: com.android.server.desktopmode.StateManager$$ExternalSyntheticLambda11
                    @Override // java.lang.Runnable
                    public final void run() {
                        StateManager.this.lambda$setEmergencyModeEnabled$12(copyInternalStateLocked);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setEmergencyModeEnabled$12(State state) {
        Iterator it = this.mStateListeners.iterator();
        while (it.hasNext()) {
            ((StateListener) it.next()).onEmergencyModeChanged(state);
        }
    }

    @Override // com.android.server.desktopmode.IStateManager
    public void setDesktopModeState(SemDesktopModeState semDesktopModeState) {
        if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "setDesktopModeState(state=" + semDesktopModeState + ")");
        }
        synchronized (this.mLock) {
            this.mInternalState.mDesktopModeState = semDesktopModeState;
            final State copyInternalStateLocked = copyInternalStateLocked(this.mInternalState);
            this.mHandler.post(new Runnable() { // from class: com.android.server.desktopmode.StateManager$$ExternalSyntheticLambda12
                @Override // java.lang.Runnable
                public final void run() {
                    StateManager.this.lambda$setDesktopModeState$13(copyInternalStateLocked);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setDesktopModeState$13(State state) {
        Iterator it = this.mStateListeners.iterator();
        while (it.hasNext()) {
            ((StateListener) it.next()).onDesktopModeStateChanged(state);
        }
    }

    @Override // com.android.server.desktopmode.IStateManager
    public void setModeChangeLocked(boolean z) {
        if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "setModeChangeLocked(locked=" + z + ")");
        }
        synchronized (this.mLock) {
            if (this.mInternalState.mModeChangeLocked != z) {
                this.mInternalState.mModeChangeLocked = z;
                copyInternalStateLocked(this.mInternalState);
            }
        }
    }

    @Override // com.android.server.desktopmode.IStateManager
    public void setTouchpadAvailable(boolean z) {
        if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "setTouchpadAvailable(available=" + z + ")");
        }
        synchronized (this.mLock) {
            if (this.mInternalState.mTouchpadAvailable != z) {
                this.mInternalState.mTouchpadAvailable = z;
                final State copyInternalStateLocked = copyInternalStateLocked(this.mInternalState);
                this.mHandler.post(new Runnable() { // from class: com.android.server.desktopmode.StateManager$$ExternalSyntheticLambda19
                    @Override // java.lang.Runnable
                    public final void run() {
                        StateManager.this.lambda$setTouchpadAvailable$14(copyInternalStateLocked);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setTouchpadAvailable$14(State state) {
        Iterator it = this.mStateListeners.iterator();
        while (it.hasNext()) {
            ((StateListener) it.next()).onTouchpadAvailabilityChanged(state);
        }
    }

    @Override // com.android.server.desktopmode.IStateManager
    public void setTouchpadEnabled(boolean z) {
        if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "setTouchpadEnabled(enabled=" + z + ")");
        }
        synchronized (this.mLock) {
            if (this.mInternalState.mTouchpadEnabled != z) {
                this.mInternalState.mTouchpadEnabled = z;
                final State copyInternalStateLocked = copyInternalStateLocked(this.mInternalState);
                this.mHandler.post(new Runnable() { // from class: com.android.server.desktopmode.StateManager$$ExternalSyntheticLambda13
                    @Override // java.lang.Runnable
                    public final void run() {
                        StateManager.this.lambda$setTouchpadEnabled$15(copyInternalStateLocked);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setTouchpadEnabled$15(State state) {
        Iterator it = this.mStateListeners.iterator();
        while (it.hasNext()) {
            ((StateListener) it.next()).onTouchpadEnabled(state);
        }
    }

    @Override // com.android.server.desktopmode.IStateManager
    public void setSpenEnabled(boolean z) {
        if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "setSpenEnabled(enabled=" + z + ")");
        }
        synchronized (this.mLock) {
            if (this.mInternalState.mSpenEnabled != z) {
                this.mInternalState.mSpenEnabled = z;
                final State copyInternalStateLocked = copyInternalStateLocked(this.mInternalState);
                this.mHandler.post(new Runnable() { // from class: com.android.server.desktopmode.StateManager$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        StateManager.this.lambda$setSpenEnabled$16(copyInternalStateLocked);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setSpenEnabled$16(State state) {
        Iterator it = this.mStateListeners.iterator();
        while (it.hasNext()) {
            ((StateListener) it.next()).onSpenEnabled(state);
        }
    }

    @Override // com.android.server.desktopmode.IStateManager
    public void setDisplayResolutionUnsupported(boolean z) {
        if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "setDisplayResolutionUnsupported(unsupported=" + z + ")");
        }
        synchronized (this.mLock) {
            if (this.mInternalState.mDisplayResolutionUnsupported != z) {
                this.mInternalState.mDisplayResolutionUnsupported = z;
                copyInternalStateLocked(this.mInternalState);
            }
        }
    }

    @Override // com.android.server.desktopmode.IStateManager
    public void setNavBarGestureEnabled(boolean z) {
        if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "setNavBarGestureEnabled(enabled=" + z + ")");
        }
        synchronized (this.mLock) {
            if (this.mInternalState.mIsNavBarGestureEnabled != z) {
                this.mInternalState.mIsNavBarGestureEnabled = z;
                final State copyInternalStateLocked = copyInternalStateLocked(this.mInternalState);
                this.mHandler.post(new Runnable() { // from class: com.android.server.desktopmode.StateManager$$ExternalSyntheticLambda16
                    @Override // java.lang.Runnable
                    public final void run() {
                        StateManager.this.lambda$setNavBarGestureEnabled$17(copyInternalStateLocked);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setNavBarGestureEnabled$17(State state) {
        Iterator it = this.mStateListeners.iterator();
        while (it.hasNext()) {
            ((StateListener) it.next()).onNavBarGestureEnabled(state);
        }
    }

    public final State copyInternalStateLocked(InternalState internalState) {
        internalState.mSeq++;
        InternalState internalState2 = new InternalState(internalState);
        this.mState = internalState2;
        if (DesktopModeFeature.DEBUG) {
            Log.m11v(TAG, internalState.toString());
        }
        return internalState2;
    }

    @Override // com.android.server.desktopmode.IStateManager
    public void notifyBootCompleted() {
        if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "notifyBootCompleted()");
        }
        this.mHandler.post(new Runnable() { // from class: com.android.server.desktopmode.StateManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                StateManager.this.lambda$notifyBootCompleted$18();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyBootCompleted$18() {
        Iterator it = this.mStateListeners.iterator();
        while (it.hasNext()) {
            ((StateListener) it.next()).onBootCompleted();
        }
    }

    @Override // com.android.server.desktopmode.IStateManager
    public void notifyBootInitBlockerRegistered(final boolean z) {
        if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "notifyBootInitBlockerRegistered()");
        }
        this.mHandler.post(new Runnable() { // from class: com.android.server.desktopmode.StateManager$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                StateManager.this.lambda$notifyBootInitBlockerRegistered$19(z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyBootInitBlockerRegistered$19(boolean z) {
        Iterator it = this.mStateListeners.iterator();
        while (it.hasNext()) {
            ((StateListener) it.next()).onBootInitBlockerRegistered(z);
        }
    }

    @Override // com.android.server.desktopmode.IStateManager
    public void notifyLauncherPackageReplaced(boolean z) {
        if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "notifyLauncherPackageReplaced(dataCleared=" + z + ")");
        }
        Iterator it = this.mStateListeners.iterator();
        while (it.hasNext()) {
            ((StateListener) it.next()).onLauncherPackageReplaced(z);
        }
    }

    @Override // com.android.server.desktopmode.IStateManager
    public void notifySetDesktopMode(State state, boolean z) {
        if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "notifySetDesktopMode(enter=" + z + ")");
        }
        Iterator it = this.mStateListeners.iterator();
        while (it.hasNext()) {
            ((StateListener) it.next()).onSetDesktopMode(state, z);
        }
    }

    @Override // com.android.server.desktopmode.IStateManager
    public void notifyStartLoadingScreen(boolean z) {
        if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "notifyStartLoadingScreen(enter=" + z + ")");
        }
        Iterator it = this.mStateListeners.iterator();
        while (it.hasNext()) {
            ((StateListener) it.next()).onStartLoadingScreen(z);
        }
    }

    @Override // com.android.server.desktopmode.IStateManager
    public void notifySetDesktopModeInternal(boolean z) {
        if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "notifySetDesktopModeInternal(enter=" + z + ")");
        }
        Iterator it = this.mStateListeners.iterator();
        while (it.hasNext()) {
            ((StateListener) it.next()).onSetDesktopModeInternal(z);
        }
    }

    @Override // com.android.server.desktopmode.IStateManager
    public void notifyOnConfigurationChanged(boolean z) {
        if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "notifyOnConfigurationChanged(enter=" + z + ")");
        }
        Iterator it = this.mStateListeners.iterator();
        while (it.hasNext()) {
            ((StateListener) it.next()).onConfigurationChanged(z);
        }
    }

    @Override // com.android.server.desktopmode.IStateManager
    public void notifyStopLoadingScreen(boolean z) {
        if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "notifyStopLoadingScreen(enter=" + z + ")");
        }
        Iterator it = this.mStateListeners.iterator();
        while (it.hasNext()) {
            ((StateListener) it.next()).onStopLoadingScreen(z);
        }
    }

    @Override // com.android.server.desktopmode.IStateManager
    public void notifyDualModeSetDesktopMode(State state, boolean z) {
        if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "notifyDualSetDesktopMode(enter=" + z + ")");
        }
        Iterator it = this.mStateListeners.iterator();
        while (it.hasNext()) {
            ((StateListener) it.next()).onDualModeSetDesktopMode(state, z);
        }
    }

    @Override // com.android.server.desktopmode.IStateManager
    public void notifyDualModeStartLoadingScreen(boolean z) {
        if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "notifyDualStartLoadingScreen(enter=" + z + ")");
        }
        Iterator it = this.mStateListeners.iterator();
        while (it.hasNext()) {
            ((StateListener) it.next()).onDualModeStartLoadingScreen(z);
        }
    }

    @Override // com.android.server.desktopmode.IStateManager
    public void notifyDualModeSetDesktopModeInternal(boolean z) {
        if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "notifyDualModeSetDesktopModeInternal(enter=" + z + ")");
        }
        Iterator it = this.mStateListeners.iterator();
        while (it.hasNext()) {
            ((StateListener) it.next()).onDualModeSetDesktopModeInternal(z);
        }
    }

    @Override // com.android.server.desktopmode.IStateManager
    public void notifyDualModeOnConfigurationChanged(boolean z) {
        if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "notifyDualOnConfigurationChanged(enter=" + z + ")");
        }
        Iterator it = this.mStateListeners.iterator();
        while (it.hasNext()) {
            ((StateListener) it.next()).onDualModeConfigurationChanged(z);
        }
    }

    @Override // com.android.server.desktopmode.IStateManager
    public void notifyDualModeStopLoadingScreen(boolean z) {
        if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "notifyDualStopLoadingScreen(enter=" + z + ")");
        }
        Iterator it = this.mStateListeners.iterator();
        while (it.hasNext()) {
            ((StateListener) it.next()).onDualModeStopLoadingScreen(z);
        }
    }

    @Override // com.android.server.desktopmode.IStateManager
    public void notifyScheduleUpdateDesktopMode(boolean z) {
        if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "notifyScheduleUpdateDesktopMode(enter=" + z + ")");
        }
        Iterator it = this.mStateListeners.iterator();
        while (it.hasNext()) {
            ((StateListener) it.next()).onScheduleUpdateDesktopMode(z);
        }
    }

    @Override // com.android.server.desktopmode.IStateManager
    public void notifyDisplayDisconnectionRequest(int i) {
        if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "notifyDisplayDisconnectionRequest(displayType=" + i + ")");
        }
        Iterator it = this.mStateListeners.iterator();
        while (it.hasNext()) {
            ((StateListener) it.next()).onDisplayDisconnectionRequested(i);
        }
    }

    @Override // com.android.server.desktopmode.IStateManager
    public void registerListener(StateListener stateListener) {
        if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "registerListener(StateListener=" + stateListener + ")");
        }
        this.mStateListeners.addIfAbsent(stateListener);
    }

    @Override // com.android.server.desktopmode.IStateManager
    public void unregisterListener(StateListener stateListener) {
        if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "unregisterListener(StateListener=" + stateListener + ")");
        }
        this.mStateListeners.remove(stateListener);
    }

    @Override // com.android.server.desktopmode.IStateManager
    public void dump(IndentingPrintWriter indentingPrintWriter) {
        synchronized (this.mLock) {
            indentingPrintWriter.println("Current " + StateManager.class.getSimpleName() + " state:");
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.println("mState=" + this.mState);
            indentingPrintWriter.println("mStateListeners=" + this.mStateListeners);
            indentingPrintWriter.decreaseIndent();
        }
    }

    public class InternalState implements State {
        public DisplayInfo mConnectedDisplay;
        public CoverState mCoverState;
        public int mCoverSupportState;
        public int mCurrentUserId;
        public int mDesktopDisplayId;
        public SemDesktopModeState mDesktopModeState;
        public boolean mDisplayResolutionUnsupported;
        public int mDockLowChargerState;
        public HardwareManager.DockState mDockState;
        public boolean mEmergencyModeEnabled;
        public boolean mForcedInternalScreenModeEnabled;
        public boolean mIsExternalDisplayConnected;
        public boolean mIsMouseConnected;
        public boolean mIsNavBarGestureEnabled;
        public boolean mIsPogoKeyboardConnected;
        public boolean mIsWiredCharging;
        public boolean mModeChangeLocked;
        public Map mPackageState;
        public DisplayInfo mPreviousConnectedDisplay;
        public HardwareManager.DockState mPreviousDockState;
        public int mSeq;
        public boolean mSpenEnabled;
        public boolean mTouchpadAvailable;
        public boolean mTouchpadEnabled;

        public InternalState() {
            this.mSeq = 0;
            this.mForcedInternalScreenModeEnabled = false;
            this.mIsExternalDisplayConnected = false;
            this.mIsMouseConnected = false;
            this.mIsPogoKeyboardConnected = false;
            this.mIsWiredCharging = false;
            this.mEmergencyModeEnabled = false;
            this.mModeChangeLocked = false;
            this.mTouchpadAvailable = false;
            this.mTouchpadEnabled = false;
            this.mSpenEnabled = false;
            this.mDisplayResolutionUnsupported = false;
            this.mIsNavBarGestureEnabled = false;
            this.mDesktopDisplayId = -1;
            this.mCoverSupportState = 1;
            this.mCurrentUserId = -10000;
            this.mDockLowChargerState = -1;
            HardwareManager.DockState dockState = new HardwareManager.DockState();
            this.mDockState = dockState;
            this.mPreviousDockState = dockState;
            this.mCoverState = new CoverState();
            this.mPackageState = new ArrayMap();
            this.mDesktopModeState = new SemDesktopModeState();
        }

        public InternalState(InternalState internalState) {
            this.mSeq = 0;
            this.mForcedInternalScreenModeEnabled = false;
            this.mIsExternalDisplayConnected = false;
            this.mIsMouseConnected = false;
            this.mIsPogoKeyboardConnected = false;
            this.mIsWiredCharging = false;
            this.mEmergencyModeEnabled = false;
            this.mModeChangeLocked = false;
            this.mTouchpadAvailable = false;
            this.mTouchpadEnabled = false;
            this.mSpenEnabled = false;
            this.mDisplayResolutionUnsupported = false;
            this.mIsNavBarGestureEnabled = false;
            this.mDesktopDisplayId = -1;
            this.mCoverSupportState = 1;
            this.mCurrentUserId = -10000;
            this.mDockLowChargerState = -1;
            HardwareManager.DockState dockState = new HardwareManager.DockState();
            this.mDockState = dockState;
            this.mPreviousDockState = dockState;
            this.mCoverState = new CoverState();
            this.mPackageState = new ArrayMap();
            this.mDesktopModeState = new SemDesktopModeState();
            this.mSeq = internalState.mSeq;
            this.mForcedInternalScreenModeEnabled = internalState.mForcedInternalScreenModeEnabled;
            this.mIsExternalDisplayConnected = internalState.mIsExternalDisplayConnected;
            this.mIsMouseConnected = internalState.mIsMouseConnected;
            this.mIsPogoKeyboardConnected = internalState.mIsPogoKeyboardConnected;
            this.mIsWiredCharging = internalState.mIsWiredCharging;
            this.mDockLowChargerState = internalState.mDockLowChargerState;
            this.mEmergencyModeEnabled = internalState.mEmergencyModeEnabled;
            this.mModeChangeLocked = internalState.mModeChangeLocked;
            this.mTouchpadAvailable = internalState.mTouchpadAvailable;
            this.mTouchpadEnabled = internalState.mTouchpadEnabled;
            this.mSpenEnabled = internalState.mSpenEnabled;
            this.mDisplayResolutionUnsupported = internalState.mDisplayResolutionUnsupported;
            this.mIsNavBarGestureEnabled = internalState.mIsNavBarGestureEnabled;
            this.mDesktopDisplayId = internalState.mDesktopDisplayId;
            this.mCoverSupportState = internalState.mCoverSupportState;
            this.mCurrentUserId = internalState.mCurrentUserId;
            this.mConnectedDisplay = internalState.mConnectedDisplay;
            this.mPreviousConnectedDisplay = internalState.mPreviousConnectedDisplay;
            this.mDockState = internalState.mDockState;
            this.mPreviousDockState = internalState.mPreviousDockState;
            this.mCoverState = internalState.mCoverState;
            this.mPackageState = internalState.mPackageState;
            this.mDesktopModeState = internalState.mDesktopModeState;
        }

        @Override // com.android.server.desktopmode.State
        public boolean isForcedInternalScreenModeEnabled() {
            return this.mForcedInternalScreenModeEnabled;
        }

        @Override // com.android.server.desktopmode.State
        public boolean isExternalDisplayConnected() {
            return this.mIsExternalDisplayConnected;
        }

        @Override // com.android.server.desktopmode.State
        public boolean isMouseConnected() {
            return this.mIsMouseConnected;
        }

        @Override // com.android.server.desktopmode.State
        public boolean isPogoKeyboardConnected() {
            return this.mIsPogoKeyboardConnected;
        }

        @Override // com.android.server.desktopmode.State
        public boolean isWiredCharging() {
            return this.mIsWiredCharging;
        }

        @Override // com.android.server.desktopmode.State
        public boolean isDockLowChargerConnected() {
            return this.mDockLowChargerState == 1;
        }

        @Override // com.android.server.desktopmode.State
        public boolean isCoverSupportStatePartial() {
            return this.mCoverSupportState == 2;
        }

        @Override // com.android.server.desktopmode.State
        public boolean isEmergencyModeEnabled() {
            return this.mEmergencyModeEnabled;
        }

        @Override // com.android.server.desktopmode.State
        public boolean isPackagesAvailable() {
            if (this.mPackageState.isEmpty()) {
                return false;
            }
            Iterator it = this.mPackageState.values().iterator();
            while (it.hasNext()) {
                if (!((Boolean) it.next()).booleanValue()) {
                    return false;
                }
            }
            return true;
        }

        @Override // com.android.server.desktopmode.State
        public boolean isModeChangeLocked() {
            return this.mModeChangeLocked;
        }

        @Override // com.android.server.desktopmode.State
        public boolean isTouchpadAvailable() {
            return this.mTouchpadAvailable;
        }

        @Override // com.android.server.desktopmode.State
        public boolean isTouchpadEnabled() {
            return this.mTouchpadEnabled;
        }

        @Override // com.android.server.desktopmode.State
        public boolean isSpenEnabled() {
            return this.mSpenEnabled;
        }

        @Override // com.android.server.desktopmode.State
        public boolean isDexStationConnectedWithFlipCover() {
            return isCoverSupportStatePartial() && this.mDockState.isDexStation();
        }

        @Override // com.android.server.desktopmode.State
        public boolean isDexOnPcConnected() {
            DisplayInfo displayInfo = this.mConnectedDisplay;
            return displayInfo != null && displayInfo.getType() == 1000;
        }

        @Override // com.android.server.desktopmode.State
        public boolean isWirelessDexConnected() {
            DisplayInfo displayInfo = this.mConnectedDisplay;
            return displayInfo != null && displayInfo.getType() == 1001;
        }

        @Override // com.android.server.desktopmode.State
        public boolean isDexOnPcOrWirelessDexConnected() {
            DisplayInfo displayInfo = this.mConnectedDisplay;
            return displayInfo != null && (displayInfo.getType() == 1000 || this.mConnectedDisplay.getType() == 1001);
        }

        @Override // com.android.server.desktopmode.State
        public boolean isHdmiConnected() {
            DisplayInfo displayInfo = this.mConnectedDisplay;
            return displayInfo != null && displayInfo.getType() == 2;
        }

        @Override // com.android.server.desktopmode.State
        public boolean isDisplayResolutionUnsupported() {
            return this.mDisplayResolutionUnsupported;
        }

        @Override // com.android.server.desktopmode.State
        public boolean isNavBarGestureEnabled() {
            return this.mIsNavBarGestureEnabled;
        }

        @Override // com.android.server.desktopmode.State
        public int getDesktopDisplayId() {
            return this.mDesktopDisplayId;
        }

        @Override // com.android.server.desktopmode.State
        public int getCurrentUserId() {
            return this.mCurrentUserId;
        }

        @Override // com.android.server.desktopmode.State
        public DisplayInfo getConnectedDisplay() {
            return this.mConnectedDisplay;
        }

        @Override // com.android.server.desktopmode.State
        public DisplayInfo getPreviousConnectedDisplay() {
            return this.mPreviousConnectedDisplay;
        }

        @Override // com.android.server.desktopmode.State
        public HardwareManager.DockState getDockState() {
            return this.mDockState;
        }

        @Override // com.android.server.desktopmode.State
        public HardwareManager.DockState getPreviousDockState() {
            return this.mPreviousDockState;
        }

        @Override // com.android.server.desktopmode.State
        public CoverState getCoverState() {
            return this.mCoverState;
        }

        @Override // com.android.server.desktopmode.State
        public Map getPackageState() {
            return this.mPackageState;
        }

        @Override // com.android.server.desktopmode.State
        public SemDesktopModeState getDesktopModeState() {
            return this.mDesktopModeState;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(128);
            sb.append("State{s");
            sb.append(this.mSeq);
            sb.append(" user");
            sb.append(this.mCurrentUserId);
            if (this.mConnectedDisplay != null) {
                sb.append(' ');
                sb.append(this.mConnectedDisplay);
            }
            if (this.mPreviousConnectedDisplay != null) {
                sb.append(" prev/");
                sb.append(DisplayInfo.typeToString(this.mPreviousConnectedDisplay.getType()));
            }
            if (this.mIsPogoKeyboardConnected) {
                sb.append(" pogoKeyboard");
            }
            if (this.mIsMouseConnected) {
                sb.append(" mouse");
            }
            if (this.mCoverState.attached) {
                sb.append(" cover.t");
                sb.append(this.mCoverState.getType());
                sb.append(".ft");
                sb.append(this.mCoverState.getFriendsType());
                sb.append('/');
                if (this.mCoverState.switchState) {
                    sb.append("open");
                } else {
                    sb.append("close");
                }
            }
            if (this.mCoverSupportState != 1) {
                sb.append(' ');
                sb.append(CoverStateManager.coverSupportStateToString(this.mCoverSupportState));
            }
            if (!this.mDesktopModeState.compareTo(2, 0)) {
                sb.append(' ');
                sb.append(this.mDesktopModeState);
            }
            if (!this.mDockState.isUndocked()) {
                sb.append(' ');
                sb.append(HardwareManager.DockState.dockTypeToString(this.mDockState.getType()));
            }
            if (!this.mPreviousDockState.isUndocked()) {
                sb.append(" prev/");
                sb.append(HardwareManager.DockState.dockTypeToString(this.mPreviousDockState.getType()));
            }
            if (this.mDesktopDisplayId != -1) {
                sb.append(" desktopDisplay.");
                sb.append(this.mDesktopDisplayId);
            }
            if (this.mForcedInternalScreenModeEnabled) {
                sb.append(" forcedInternalScreenMode");
            }
            if (this.mDockLowChargerState == 1) {
                sb.append(" dockLowCharger");
            }
            if (this.mModeChangeLocked) {
                sb.append(" modeChangeLocked");
            }
            if (this.mIsWiredCharging) {
                sb.append(" wiredCharging");
            }
            if (!isPackagesAvailable()) {
                sb.append(" package=");
                sb.append(this.mPackageState);
            }
            if (this.mTouchpadAvailable) {
                sb.append(" touchpadAvailable");
            }
            if (this.mTouchpadEnabled) {
                sb.append(" touchpadEnabled");
            }
            if (this.mDisplayResolutionUnsupported) {
                sb.append(" displayResolutionUnsupported");
            }
            if (this.mSpenEnabled) {
                sb.append(" spenEnabled");
            }
            if (this.mIsNavBarGestureEnabled) {
                sb.append(" navBarGestureEnabled");
            }
            sb.append('}');
            return sb.toString();
        }
    }
}
