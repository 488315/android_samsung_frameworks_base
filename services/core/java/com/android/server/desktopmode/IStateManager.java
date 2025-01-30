package com.android.server.desktopmode;

import android.util.IndentingPrintWriter;
import com.android.server.desktopmode.HardwareManager;
import com.android.server.desktopmode.StateManager;
import com.samsung.android.cover.CoverState;
import com.samsung.android.desktopmode.SemDesktopModeState;
import java.util.Map;

/* loaded from: classes2.dex */
public interface IStateManager {
    void dump(IndentingPrintWriter indentingPrintWriter);

    State getState();

    void notifyBootCompleted();

    void notifyBootInitBlockerRegistered(boolean z);

    void notifyDisplayDisconnectionRequest(int i);

    void notifyDualModeOnConfigurationChanged(boolean z);

    void notifyDualModeSetDesktopMode(State state, boolean z);

    void notifyDualModeSetDesktopModeInternal(boolean z);

    void notifyDualModeStartLoadingScreen(boolean z);

    void notifyDualModeStopLoadingScreen(boolean z);

    void notifyLauncherPackageReplaced(boolean z);

    void notifyOnConfigurationChanged(boolean z);

    void notifyScheduleUpdateDesktopMode(boolean z);

    void notifySetDesktopMode(State state, boolean z);

    void notifySetDesktopModeInternal(boolean z);

    void notifyStartLoadingScreen(boolean z);

    void notifyStopLoadingScreen(boolean z);

    void registerListener(StateManager.StateListener stateListener);

    void setCoverState(CoverState coverState, int i);

    void setCurrentUserId(int i);

    void setDesktopDisplayId(int i);

    void setDesktopModeState(SemDesktopModeState semDesktopModeState);

    void setDisplayResolutionUnsupported(boolean z);

    void setDockLowChargerState(int i);

    void setDockState(HardwareManager.DockState dockState);

    void setEmergencyModeEnabled(boolean z);

    void setExternalDisplayConnected(boolean z, DisplayInfo displayInfo);

    void setExternalDisplayUpdated(DisplayInfo displayInfo);

    void setForcedInternalScreenModeEnabled(boolean z);

    void setModeChangeLocked(boolean z);

    void setMouseConnected(boolean z);

    void setNavBarGestureEnabled(boolean z);

    void setPackageState(Map map);

    void setPogoKeyboardConnected(boolean z);

    void setSpenEnabled(boolean z);

    void setTouchpadAvailable(boolean z);

    void setTouchpadEnabled(boolean z);

    void setWiredCharging(boolean z);

    void unregisterListener(StateManager.StateListener stateListener);
}
