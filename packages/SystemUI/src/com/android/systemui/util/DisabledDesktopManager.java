package com.android.systemui.util;

import android.os.Bundle;
import com.android.systemui.statusbar.phone.StatusBarSignalPolicy;
import com.android.systemui.statusbar.policy.SBluetoothControllerImpl;
import com.android.systemui.util.DesktopManager;
import com.samsung.android.desktopmode.SemDesktopModeState;

public class DisabledDesktopManager implements DesktopManager {
    @Override // com.android.systemui.util.DesktopManager
    public SemDesktopModeState getSemDesktopModeState() {
        return null;
    }

    @Override // com.android.systemui.util.DesktopManager
    public boolean isDesktopBarConnected() {
        return false;
    }

    @Override // com.android.systemui.util.DesktopManager
    public boolean isDesktopMode() {
        return false;
    }

    @Override // com.android.systemui.util.DesktopManager
    public boolean isDualView() {
        return false;
    }

    @Override // com.android.systemui.util.DesktopManager
    public boolean isStandalone() {
        return false;
    }

    @Override // com.android.systemui.util.DesktopManager
    public boolean isTouchpadEnabled() {
        return false;
    }

    @Override // com.android.systemui.util.DesktopManager
    public void notifyKeyguardLockout(boolean z) {
    }

    @Override // com.android.systemui.util.DesktopManager
    public void notifyKeyguardLockout(boolean z, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
    }

    @Override // com.android.systemui.util.DesktopManager
    public void destroy() {
    }

    @Override // com.android.systemui.util.DesktopManager
    public void notifyDismissKeyguard() {
    }

    @Override // com.android.systemui.util.DesktopManager
    public void notifyShowKeyguard() {
    }

    @Override // com.android.systemui.util.DesktopManager
    public void notifyOccluded(boolean z) {
    }

    @Override // com.android.systemui.util.DesktopManager
    public void notifyPrivacyItemsChanged(boolean z) {
    }

    @Override // com.android.systemui.util.DesktopManager
    public void notifyScreenState(boolean z) {
    }

    @Override // com.android.systemui.util.DesktopManager
    public void registerCallback(DesktopManager.Callback callback) {
    }

    @Override // com.android.systemui.util.DesktopManager
    public void removeDesktopStatusBarIconCallback(StatusBarSignalPolicy.DesktopCallback desktopCallback) {
    }

    @Override // com.android.systemui.util.DesktopManager
    public void setConnectedDeviceListForGroup(Bundle bundle) {
    }

    @Override // com.android.systemui.util.DesktopManager
    public void setDesktopBluetoothCallback(SBluetoothControllerImpl.BluetoothDesktopCallback bluetoothDesktopCallback) {
    }

    @Override // com.android.systemui.util.DesktopManager
    public void setDesktopStatusBarIconCallback(StatusBarSignalPolicy.DesktopCallback desktopCallback) {
    }

    @Override // com.android.systemui.util.DesktopManager
    public void unregisterCallback(DesktopManager.Callback callback) {
    }

    @Override // com.android.systemui.util.DesktopManager
    public void notifyTrustChanged(int i, boolean z) {
    }

    @Override // com.android.systemui.util.DesktopManager
    public void setAirplaneMode(boolean z, int i) {
    }

    @Override // com.android.systemui.util.DesktopManager
    public void setBtTetherIcon(boolean z, int i) {
    }

    @Override // com.android.systemui.util.DesktopManager
    public void setWifiIcon(boolean z, int i, int i2) {
    }

    @Override // com.android.systemui.util.DesktopManager
    public void notifyUpdateBouncerMessage(int i, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
    }

    @Override // com.android.systemui.util.DesktopManager
    public void setMPTCPIcon(boolean z, int i, int i2, int i3) {
    }

    @Override // com.android.systemui.util.DesktopManager
    public void setMobileIcon(boolean z, int i, int i2, int i3, int i4, boolean z2, int i5, int i6) {
    }
}
