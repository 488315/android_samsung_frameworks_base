package com.android.server.desktopmode;

import android.os.Handler;
import android.util.ArrayMap;
import android.view.Display;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.ServiceThread;
import com.android.server.desktopmode.HardwareManager;
import com.android.server.desktopmode.StateManager;
import com.samsung.android.cover.CoverState;
import com.samsung.android.desktopmode.DesktopModeFeature;
import com.samsung.android.desktopmode.SemDesktopModeState;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class StateManager implements IStateManager {
    public final Handler mHandler;
    public volatile InternalState mInternalState;
    public volatile InternalState mState;
    public final Object mLock = new Object();
    public final CopyOnWriteArrayList mStateListeners = new CopyOnWriteArrayList();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class InternalState {
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

        public final boolean isDexOnPcConnected() {
            DisplayInfo displayInfo = this.mConnectedDisplay;
            return displayInfo != null && displayInfo.mType == 1000;
        }

        public final boolean isDexOnPcOrWirelessDexConnected() {
            int i;
            DisplayInfo displayInfo = this.mConnectedDisplay;
            return displayInfo != null && ((i = displayInfo.mType) == 1000 || i == 1001);
        }

        public final boolean isDexStationConnectedWithFlipCover() {
            return this.mCoverSupportState == 2 && this.mDockState.isDexStation();
        }

        public final boolean isHdmiConnected() {
            DisplayInfo displayInfo = this.mConnectedDisplay;
            return displayInfo != null && displayInfo.mType == 2;
        }

        public final boolean isPackagesAvailable() {
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

        public final boolean isWirelessDexConnected() {
            DisplayInfo displayInfo = this.mConnectedDisplay;
            return displayInfo != null && displayInfo.mType == 1001;
        }

        public final String toString() {
            StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(128, "State{s");
            m.append(this.mSeq);
            m.append(" user");
            m.append(this.mCurrentUserId);
            if (this.mConnectedDisplay != null) {
                m.append(' ');
                m.append(this.mConnectedDisplay);
            }
            if (this.mPreviousConnectedDisplay != null) {
                m.append(" prev/");
                int i = this.mPreviousConnectedDisplay.mType;
                m.append(i != 1000 ? i != 1001 ? Display.typeToString(i) : "WIRELESS_DEX" : "DEX_ON_PC");
            }
            if (this.mIsPogoKeyboardConnected) {
                m.append(" pogoKeyboard");
            }
            if (this.mIsMouseConnected) {
                m.append(" mouse");
            }
            if (this.mCoverState.attached) {
                m.append(" cover.t");
                m.append(this.mCoverState.getType());
                m.append(".ft");
                m.append(this.mCoverState.getFriendsType());
                m.append('/');
                if (this.mCoverState.switchState) {
                    m.append("open");
                } else {
                    m.append("close");
                }
            }
            if (this.mCoverSupportState != 1) {
                m.append(' ');
                m.append(CoverStateManager.coverSupportStateToString(this.mCoverSupportState));
            }
            if (!this.mDesktopModeState.compareTo(2, 0)) {
                m.append(' ');
                m.append(this.mDesktopModeState);
            }
            if (!this.mDockState.isUndocked()) {
                m.append(' ');
                m.append(HardwareManager.DockState.dockTypeToString(this.mDockState.mType));
            }
            if (!this.mPreviousDockState.isUndocked()) {
                m.append(" prev/");
                m.append(HardwareManager.DockState.dockTypeToString(this.mPreviousDockState.mType));
            }
            if (this.mDesktopDisplayId != -1) {
                m.append(" desktopDisplay.");
                m.append(this.mDesktopDisplayId);
            }
            if (this.mForcedInternalScreenModeEnabled) {
                m.append(" forcedInternalScreenMode");
            }
            if (this.mDockLowChargerState == 1) {
                m.append(" dockLowCharger");
            }
            if (this.mModeChangeLocked) {
                m.append(" modeChangeLocked");
            }
            if (this.mIsWiredCharging) {
                m.append(" wiredCharging");
            }
            if (!isPackagesAvailable()) {
                m.append(" package=");
                m.append(this.mPackageState);
            }
            if (this.mTouchpadAvailable) {
                m.append(" touchpadAvailable");
            }
            if (this.mTouchpadEnabled) {
                m.append(" touchpadEnabled");
            }
            if (this.mDisplayResolutionUnsupported) {
                m.append(" displayResolutionUnsupported");
            }
            if (this.mSpenEnabled) {
                m.append(" spenEnabled");
            }
            if (this.mIsNavBarGestureEnabled) {
                m.append(" navBarGestureEnabled");
            }
            m.append('}');
            return m.toString();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class StateListener {
        public void onBootCompleted() {
        }

        public void onBootInitBlockerRegistered(boolean z) {
        }

        public void onDesktopModeStateChanged(InternalState internalState) {
        }

        public void onDisplayDisconnectionRequested(int i) {
        }

        public void onDockLowChargerPowerChanged(InternalState internalState) {
        }

        public void onDockStateChanged(InternalState internalState) {
        }

        public void onDualModeSetDesktopMode(boolean z) {
        }

        public void onDualModeSetDesktopModeInternal(boolean z) {
        }

        public void onDualModeStartLoadingScreen(boolean z) {
        }

        public void onDualModeStopLoadingScreen(boolean z) {
        }

        public void onEmergencyModeChanged() {
        }

        public void onExternalDisplayConnectionChanged(InternalState internalState) {
        }

        public void onExternalDisplayUpdated(InternalState internalState) {
        }

        public void onForcedInternalScreenStateChanged(InternalState internalState) {
        }

        public void onLauncherPackageReplaced(boolean z) {
        }

        public void onNavBarGestureEnabled(InternalState internalState) {
        }

        public void onPackageStateChanged(InternalState internalState) {
        }

        public void onPogoKeyboardConnectionChanged(InternalState internalState) {
        }

        public void onScheduleUpdateDesktopMode(boolean z) {
        }

        public void onSetDesktopMode(boolean z) {
        }

        public void onSetDesktopModeInternal(boolean z) {
        }

        public void onSpenEnabled(InternalState internalState) {
        }

        public void onStartLoadingScreen(boolean z) {
        }

        public void onStopLoadingScreen(boolean z) {
        }

        public void onTouchpadAvailabilityChanged(InternalState internalState) {
        }

        public void onTouchpadEnabled(InternalState internalState) {
        }

        public void onUserChanged(InternalState internalState) {
        }
    }

    public StateManager(ServiceThread serviceThread) {
        InternalState internalState = new InternalState();
        internalState.mSeq = 0;
        internalState.mForcedInternalScreenModeEnabled = false;
        internalState.mIsExternalDisplayConnected = false;
        internalState.mIsMouseConnected = false;
        internalState.mIsPogoKeyboardConnected = false;
        internalState.mIsWiredCharging = false;
        internalState.mEmergencyModeEnabled = false;
        internalState.mModeChangeLocked = false;
        internalState.mTouchpadAvailable = false;
        internalState.mTouchpadEnabled = false;
        internalState.mSpenEnabled = false;
        internalState.mDisplayResolutionUnsupported = false;
        internalState.mIsNavBarGestureEnabled = false;
        internalState.mDesktopDisplayId = -1;
        internalState.mCoverSupportState = 1;
        internalState.mCurrentUserId = -10000;
        internalState.mDockLowChargerState = -1;
        HardwareManager.DockState dockState = new HardwareManager.DockState();
        internalState.mDockState = dockState;
        internalState.mPreviousDockState = dockState;
        internalState.mCoverState = new CoverState();
        internalState.mPackageState = new ArrayMap();
        internalState.mDesktopModeState = new SemDesktopModeState();
        this.mInternalState = internalState;
        this.mState = internalState;
        this.mHandler = new Handler(serviceThread.getLooper());
    }

    public final InternalState copyInternalStateLocked(InternalState internalState) {
        internalState.mSeq++;
        InternalState internalState2 = new InternalState();
        internalState2.mSeq = 0;
        internalState2.mForcedInternalScreenModeEnabled = false;
        internalState2.mIsExternalDisplayConnected = false;
        internalState2.mIsMouseConnected = false;
        internalState2.mIsPogoKeyboardConnected = false;
        internalState2.mIsWiredCharging = false;
        internalState2.mEmergencyModeEnabled = false;
        internalState2.mModeChangeLocked = false;
        internalState2.mTouchpadAvailable = false;
        internalState2.mTouchpadEnabled = false;
        internalState2.mSpenEnabled = false;
        internalState2.mDisplayResolutionUnsupported = false;
        internalState2.mIsNavBarGestureEnabled = false;
        internalState2.mDesktopDisplayId = -1;
        internalState2.mCoverSupportState = 1;
        internalState2.mCurrentUserId = -10000;
        internalState2.mDockLowChargerState = -1;
        HardwareManager.DockState dockState = new HardwareManager.DockState();
        internalState2.mDockState = dockState;
        internalState2.mPreviousDockState = dockState;
        internalState2.mCoverState = new CoverState();
        internalState2.mPackageState = new ArrayMap();
        internalState2.mDesktopModeState = new SemDesktopModeState();
        internalState2.mSeq = internalState.mSeq;
        internalState2.mForcedInternalScreenModeEnabled = internalState.mForcedInternalScreenModeEnabled;
        internalState2.mIsExternalDisplayConnected = internalState.mIsExternalDisplayConnected;
        internalState2.mIsMouseConnected = internalState.mIsMouseConnected;
        internalState2.mIsPogoKeyboardConnected = internalState.mIsPogoKeyboardConnected;
        internalState2.mIsWiredCharging = internalState.mIsWiredCharging;
        internalState2.mDockLowChargerState = internalState.mDockLowChargerState;
        internalState2.mEmergencyModeEnabled = internalState.mEmergencyModeEnabled;
        internalState2.mModeChangeLocked = internalState.mModeChangeLocked;
        internalState2.mTouchpadAvailable = internalState.mTouchpadAvailable;
        internalState2.mTouchpadEnabled = internalState.mTouchpadEnabled;
        internalState2.mSpenEnabled = internalState.mSpenEnabled;
        internalState2.mDisplayResolutionUnsupported = internalState.mDisplayResolutionUnsupported;
        internalState2.mIsNavBarGestureEnabled = internalState.mIsNavBarGestureEnabled;
        internalState2.mDesktopDisplayId = internalState.mDesktopDisplayId;
        internalState2.mCoverSupportState = internalState.mCoverSupportState;
        internalState2.mCurrentUserId = internalState.mCurrentUserId;
        internalState2.mConnectedDisplay = internalState.mConnectedDisplay;
        internalState2.mPreviousConnectedDisplay = internalState.mPreviousConnectedDisplay;
        internalState2.mDockState = internalState.mDockState;
        internalState2.mPreviousDockState = internalState.mPreviousDockState;
        internalState2.mCoverState = internalState.mCoverState;
        internalState2.mPackageState = internalState.mPackageState;
        internalState2.mDesktopModeState = internalState.mDesktopModeState;
        this.mState = internalState2;
        if (DesktopModeFeature.DEBUG) {
            Log.v("[DMS]StateManager", internalState.toString());
        }
        return internalState2;
    }

    public final InternalState getState() {
        InternalState internalState;
        synchronized (this.mLock) {
            internalState = this.mState;
        }
        return internalState;
    }

    public final void notifyBootInitBlockerRegistered(final boolean z) {
        if (DesktopModeFeature.DEBUG) {
            Log.d("[DMS]StateManager", "notifyBootInitBlockerRegistered()");
        }
        synchronized (this.mLock) {
            this.mHandler.post(new Runnable() { // from class: com.android.server.desktopmode.StateManager$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    StateManager stateManager = StateManager.this;
                    boolean z2 = z;
                    Iterator it = stateManager.mStateListeners.iterator();
                    while (it.hasNext()) {
                        ((StateManager.StateListener) it.next()).onBootInitBlockerRegistered(z2);
                    }
                }
            });
        }
    }

    public final void notifyDisplayDisconnectionRequest(int i) {
        if (DesktopModeFeature.DEBUG) {
            Log.d("[DMS]StateManager", "notifyDisplayDisconnectionRequest(displayType=" + i + ")");
        }
        Iterator it = this.mStateListeners.iterator();
        while (it.hasNext()) {
            ((StateListener) it.next()).onDisplayDisconnectionRequested(i);
        }
    }

    public final void notifyLauncherPackageReplaced(boolean z) {
        if (DesktopModeFeature.DEBUG) {
            DesktopModeService$$ExternalSyntheticOutline0.m("notifyLauncherPackageReplaced(dataCleared=", ")", "[DMS]StateManager", z);
        }
        Iterator it = this.mStateListeners.iterator();
        while (it.hasNext()) {
            ((StateListener) it.next()).onLauncherPackageReplaced(z);
        }
    }

    public final void notifyScheduleUpdateDesktopMode(boolean z) {
        if (DesktopModeFeature.DEBUG) {
            DesktopModeService$$ExternalSyntheticOutline0.m("notifyScheduleUpdateDesktopMode(enter=", ")", "[DMS]StateManager", z);
        }
        Iterator it = this.mStateListeners.iterator();
        while (it.hasNext()) {
            ((StateListener) it.next()).onScheduleUpdateDesktopMode(z);
        }
    }

    public final void registerListener(StateListener stateListener) {
        if (DesktopModeFeature.DEBUG) {
            Log.d("[DMS]StateManager", "registerListener(StateListener=" + stateListener + ")");
        }
        this.mStateListeners.addIfAbsent(stateListener);
    }

    public final void setDesktopModeState(SemDesktopModeState semDesktopModeState) {
        if (DesktopModeFeature.DEBUG) {
            Log.d("[DMS]StateManager", "setDesktopModeState(state=" + semDesktopModeState + ")");
        }
        synchronized (this.mLock) {
            this.mInternalState.mDesktopModeState = semDesktopModeState;
            this.mHandler.post(new StateManager$$ExternalSyntheticLambda0(this, copyInternalStateLocked(this.mInternalState), 11));
        }
    }

    public final void setDockLowChargerState(int i) {
        if (DesktopModeFeature.DEBUG) {
            Log.d("[DMS]StateManager", "setDockLowChargerState(dockLowChargerState=" + i + ")");
        }
        synchronized (this.mLock) {
            try {
                if (this.mInternalState.mDockLowChargerState != i) {
                    this.mInternalState.mDockLowChargerState = i;
                    this.mHandler.post(new StateManager$$ExternalSyntheticLambda0(this, copyInternalStateLocked(this.mInternalState), 13));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setEmergencyModeEnabled(boolean z) {
        if (DesktopModeFeature.DEBUG) {
            DesktopModeService$$ExternalSyntheticOutline0.m("setEmergencyModeEnabled(enabled=", ")", "[DMS]StateManager", z);
        }
        synchronized (this.mLock) {
            try {
                if (this.mInternalState.mEmergencyModeEnabled != z) {
                    this.mInternalState.mEmergencyModeEnabled = z;
                    this.mHandler.post(new StateManager$$ExternalSyntheticLambda0(this, copyInternalStateLocked(this.mInternalState), 12));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setExternalDisplayConnected(boolean z, DisplayInfo displayInfo) {
        if (DesktopModeFeature.DEBUG) {
            Log.d("[DMS]StateManager", "setExternalDisplayConnected(connected=" + z + ", display=" + displayInfo + ")");
        }
        synchronized (this.mLock) {
            try {
                if (this.mInternalState.mIsExternalDisplayConnected != z) {
                    this.mInternalState.mIsExternalDisplayConnected = z;
                    this.mInternalState.mConnectedDisplay = displayInfo;
                    if (!z) {
                        this.mInternalState.mDisplayResolutionUnsupported = false;
                    }
                    this.mHandler.post(new StateManager$$ExternalSyntheticLambda0(this, copyInternalStateLocked(this.mInternalState), 2));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setExternalDisplayUpdated(DisplayInfo displayInfo) {
        if (DesktopModeFeature.DEBUG) {
            Log.d("[DMS]StateManager", "setExternalDisplayUpdated(display=" + displayInfo + ")");
        }
        synchronized (this.mLock) {
            try {
                if ((this.mInternalState.mConnectedDisplay == null ? -1 : this.mInternalState.mConnectedDisplay.mDisplayId) != displayInfo.mDisplayId) {
                    this.mInternalState.mPreviousConnectedDisplay = this.mInternalState.mConnectedDisplay;
                    this.mInternalState.mConnectedDisplay = displayInfo;
                    this.mHandler.post(new StateManager$$ExternalSyntheticLambda0(this, copyInternalStateLocked(this.mInternalState), 5));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setModeChangeLocked(boolean z) {
        if (DesktopModeFeature.DEBUG) {
            DesktopModeService$$ExternalSyntheticOutline0.m("setModeChangeLocked(locked=", ")", "[DMS]StateManager", z);
        }
        synchronized (this.mLock) {
            try {
                if (this.mInternalState.mModeChangeLocked != z) {
                    this.mInternalState.mModeChangeLocked = z;
                    copyInternalStateLocked(this.mInternalState);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setNavBarGestureEnabled(boolean z) {
        if (DesktopModeFeature.DEBUG) {
            DesktopModeService$$ExternalSyntheticOutline0.m("setNavBarGestureEnabled(enabled=", ")", "[DMS]StateManager", z);
        }
        synchronized (this.mLock) {
            try {
                if (this.mInternalState.mIsNavBarGestureEnabled != z) {
                    this.mInternalState.mIsNavBarGestureEnabled = z;
                    this.mHandler.post(new StateManager$$ExternalSyntheticLambda0(this, copyInternalStateLocked(this.mInternalState), 1));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setTouchpadEnabled(boolean z) {
        if (DesktopModeFeature.DEBUG) {
            DesktopModeService$$ExternalSyntheticOutline0.m("setTouchpadEnabled(enabled=", ")", "[DMS]StateManager", z);
        }
        synchronized (this.mLock) {
            try {
                if (this.mInternalState.mTouchpadEnabled != z) {
                    this.mInternalState.mTouchpadEnabled = z;
                    this.mHandler.post(new StateManager$$ExternalSyntheticLambda0(this, copyInternalStateLocked(this.mInternalState), 6));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setWiredCharging(boolean z) {
        if (DesktopModeFeature.DEBUG) {
            DesktopModeService$$ExternalSyntheticOutline0.m("setWiredCharging(isWiredCharging=", ")", "[DMS]StateManager", z);
        }
        synchronized (this.mLock) {
            try {
                if (this.mInternalState.mIsWiredCharging != z) {
                    this.mInternalState.mIsWiredCharging = z;
                    this.mHandler.post(new StateManager$$ExternalSyntheticLambda2(this, copyInternalStateLocked(this.mInternalState), 4));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void unregisterListener(StateListener stateListener) {
        if (DesktopModeFeature.DEBUG) {
            Log.d("[DMS]StateManager", "unregisterListener(StateListener=" + stateListener + ")");
        }
        this.mStateListeners.remove(stateListener);
    }
}
