package com.android.systemui.util;

import android.os.Bundle;
import com.android.systemui.statusbar.phone.StatusBarSignalPolicy;
import com.android.systemui.statusbar.policy.SBluetoothControllerImpl;
import com.samsung.android.desktopmode.SemDesktopModeState;

public interface DesktopManager {
    void destroy();

    SemDesktopModeState getSemDesktopModeState();

    boolean isDesktopBarConnected();

    boolean isDesktopMode();

    boolean isDualView();

    boolean isStandalone();

    boolean isTouchpadEnabled();

    void notifyDismissKeyguard();

    void notifyKeyguardLockout(boolean z);

    void notifyKeyguardLockout(boolean z, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3);

    void notifyOccluded(boolean z);

    void notifyPrivacyItemsChanged(boolean z);

    void notifyScreenState(boolean z);

    void notifyShowKeyguard();

    void notifyTrustChanged(int i, boolean z);

    void notifyUpdateBouncerMessage(int i, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3);

    void registerCallback(Callback callback);

    void removeDesktopStatusBarIconCallback(StatusBarSignalPolicy.DesktopCallback desktopCallback);

    void setAirplaneMode(boolean z, int i);

    void setBtTetherIcon(boolean z, int i);

    void setConnectedDeviceListForGroup(Bundle bundle);

    void setDesktopBluetoothCallback(SBluetoothControllerImpl.BluetoothDesktopCallback bluetoothDesktopCallback);

    void setDesktopStatusBarIconCallback(StatusBarSignalPolicy.DesktopCallback desktopCallback);

    void setMPTCPIcon(boolean z, int i, int i2, int i3);

    void setMobileIcon(boolean z, int i, int i2, int i3, int i4, boolean z2, int i5, int i6);

    void setWifiIcon(boolean z, int i, int i2);

    void unregisterCallback(Callback callback);

    public interface Callback {
        default void onPrivacyItemStateRequested() {
        }

        default void onServiceConnected() {
        }

        default void onServiceDisconnected() {
        }

        default void onDesktopModeStateChanged(SemDesktopModeState semDesktopModeState) {
        }
    }
}
