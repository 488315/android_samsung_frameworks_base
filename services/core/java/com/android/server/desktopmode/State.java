package com.android.server.desktopmode;

import com.android.server.desktopmode.HardwareManager;
import com.samsung.android.cover.CoverState;
import com.samsung.android.desktopmode.SemDesktopModeState;
import java.util.Map;

/* loaded from: classes2.dex */
public interface State {
    DisplayInfo getConnectedDisplay();

    CoverState getCoverState();

    int getCurrentUserId();

    int getDesktopDisplayId();

    SemDesktopModeState getDesktopModeState();

    HardwareManager.DockState getDockState();

    Map getPackageState();

    DisplayInfo getPreviousConnectedDisplay();

    HardwareManager.DockState getPreviousDockState();

    boolean isCoverSupportStatePartial();

    boolean isDexOnPcConnected();

    boolean isDexOnPcOrWirelessDexConnected();

    boolean isDexStationConnectedWithFlipCover();

    boolean isDisplayResolutionUnsupported();

    boolean isDockLowChargerConnected();

    boolean isEmergencyModeEnabled();

    boolean isExternalDisplayConnected();

    boolean isForcedInternalScreenModeEnabled();

    boolean isHdmiConnected();

    boolean isModeChangeLocked();

    boolean isMouseConnected();

    boolean isNavBarGestureEnabled();

    boolean isPackagesAvailable();

    boolean isPogoKeyboardConnected();

    boolean isSpenEnabled();

    boolean isTouchpadAvailable();

    boolean isTouchpadEnabled();

    boolean isWiredCharging();

    boolean isWirelessDexConnected();
}
