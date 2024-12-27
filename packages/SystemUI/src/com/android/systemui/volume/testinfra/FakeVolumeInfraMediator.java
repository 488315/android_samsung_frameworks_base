package com.android.systemui.volume.testinfra;

import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.volume.util.SALoggingWrapper;
import com.samsung.systemui.splugins.volume.VolumeInfraMediator;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class FakeVolumeInfraMediator implements VolumeInfraMediator {
    public static final HashMap sConditionMap;
    public final LogWrapper logWrapper;
    public final VolumeInfraMediator volumeInfraMediator;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        sConditionMap = new HashMap();
    }

    public FakeVolumeInfraMediator(VolumeInfraMediator volumeInfraMediator, LogWrapper logWrapper) {
        this.volumeInfraMediator = volumeInfraMediator;
        this.logWrapper = logWrapper;
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final void disableSafeMediaVolume() {
        this.volumeInfraMediator.disableSafeMediaVolume();
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final Object get(VolumeInfraMediator.Values values, Object... objArr) {
        HashMap hashMap = sConditionMap;
        if (!hashMap.containsKey(values)) {
            return this.volumeInfraMediator.get(values, Arrays.copyOf(objArr, objArr.length));
        }
        Object obj = hashMap.get(values);
        Objects.toString(values);
        Objects.toString(obj);
        this.logWrapper.v("FakeVolumeInfraMediator");
        return obj;
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final String getActiveBtDeviceName() {
        return this.volumeInfraMediator.getActiveBtDeviceName();
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final String getAudioCastDeviceName() {
        return this.volumeInfraMediator.getAudioCastDeviceName();
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final void getBixbyServiceState() {
        this.volumeInfraMediator.getBixbyServiceState();
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final String getBtCallDeviceName() {
        return this.volumeInfraMediator.getBtCallDeviceName();
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final void getCaptionsComponentState(boolean z) {
        this.volumeInfraMediator.getCaptionsComponentState(z);
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final int getCutoutHeight() {
        return this.volumeInfraMediator.getCutoutHeight();
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final int getDevicesForStreamMusic() {
        return this.volumeInfraMediator.getDevicesForStreamMusic();
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final int getEarProtectLimit() {
        return this.volumeInfraMediator.getEarProtectLimit();
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final int getMultiSoundDevice() {
        return this.volumeInfraMediator.getMultiSoundDevice();
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final int getMusicFineVolume() {
        return this.volumeInfraMediator.getMusicFineVolume();
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final String getPinAppName(int i) {
        return this.volumeInfraMediator.getPinAppName(i);
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final int getPinDevice() {
        return this.volumeInfraMediator.getPinDevice();
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final String getPinDeviceName(int i) {
        return this.volumeInfraMediator.getPinDeviceName(i);
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final String getSmartViewDeviceName() {
        return this.volumeInfraMediator.getSmartViewDeviceName();
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final long getSystemTime() {
        return this.volumeInfraMediator.getSystemTime();
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final int getTimeoutControls() {
        return this.volumeInfraMediator.getTimeoutControls();
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final int getTimeoutControlsText() {
        return this.volumeInfraMediator.getTimeoutControlsText();
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final void initSound(int i) {
        this.volumeInfraMediator.initSound(i);
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isAllSoundOff() {
        return this.volumeInfraMediator.isAllSoundOff();
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isAodVolumePanel() {
        return this.volumeInfraMediator.isAodVolumePanel();
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isAudioMirroring() {
        return this.volumeInfraMediator.isAudioMirroring();
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isBixbyServiceForeground() {
        return this.volumeInfraMediator.isBixbyServiceForeground();
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isBixbyServiceOn() {
        return this.volumeInfraMediator.isBixbyServiceOn();
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isBleCallDeviceOn() {
        return this.volumeInfraMediator.isBleCallDeviceOn();
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isBluetoothScoOn() {
        return this.volumeInfraMediator.isBluetoothScoOn();
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isBudsTogetherEnabled() {
        return this.volumeInfraMediator.isBudsTogetherEnabled();
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isCaptionEnabled() {
        return this.volumeInfraMediator.isCaptionEnabled();
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isDensityOrFontChanged() {
        return this.volumeInfraMediator.isDensityOrFontChanged();
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isDexMode() {
        return this.volumeInfraMediator.isDexMode();
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isDisplayTypeChanged() {
        return this.volumeInfraMediator.isDisplayTypeChanged();
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isEnabled(VolumeInfraMediator.Conditions conditions, Object... objArr) {
        HashMap hashMap = sConditionMap;
        if (!hashMap.containsKey(conditions)) {
            return this.volumeInfraMediator.isEnabled(conditions, Arrays.copyOf(objArr, objArr.length));
        }
        boolean booleanValue = ((Boolean) hashMap.get(conditions)).booleanValue();
        Objects.toString(conditions);
        this.logWrapper.v("FakeVolumeInfraMediator");
        return booleanValue;
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isHasVibrator() {
        return this.volumeInfraMediator.isHasVibrator();
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isKeyguardState() {
        return this.volumeInfraMediator.isKeyguardState();
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isKioskModeEnabled() {
        return this.volumeInfraMediator.isKioskModeEnabled();
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isLcdOff() {
        return this.volumeInfraMediator.isLcdOff();
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isLeBroadcasting() {
        return this.volumeInfraMediator.isLeBroadcasting();
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isMediaDefault() {
        return this.volumeInfraMediator.isMediaDefault();
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isMultiSoundOn() {
        return this.volumeInfraMediator.isMultiSoundOn();
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isOrientationChanged() {
        return this.volumeInfraMediator.isOrientationChanged();
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isSafeMediaVolumeDeviceOn() {
        return this.volumeInfraMediator.isSafeMediaVolumeDeviceOn();
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isSafeMediaVolumePinDeviceOn() {
        return this.volumeInfraMediator.isSafeMediaVolumePinDeviceOn();
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isSetupWizardComplete() {
        return this.volumeInfraMediator.isSetupWizardComplete();
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isShadeLockedState() {
        return this.volumeInfraMediator.isShadeLockedState();
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isSmartView() {
        return this.volumeInfraMediator.isSmartView();
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isStandalone() {
        return this.volumeInfraMediator.isStandalone();
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isSupportTvVolumeSync() {
        return this.volumeInfraMediator.isSupportTvVolumeSync();
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isUserInCall() {
        return this.volumeInfraMediator.isUserInCall();
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isVoiceCapable() {
        return this.volumeInfraMediator.isVoiceCapable();
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isVolumeStarEnabled() {
        return this.volumeInfraMediator.isVolumeStarEnabled();
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isZenModeEnabled(int i) {
        return this.volumeInfraMediator.isZenModeEnabled(i);
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isZenModeNone(int i) {
        return this.volumeInfraMediator.isZenModeNone(i);
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isZenModePriorityOnly(int i) {
        return this.volumeInfraMediator.isZenModePriorityOnly(i);
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final void notifyVisible(boolean z) {
        this.volumeInfraMediator.notifyVisible(z);
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final void playSound() {
        this.volumeInfraMediator.playSound();
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final void sendEventLog(SALoggingWrapper.Event event) {
        this.volumeInfraMediator.sendEventLog(event);
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final void sendSystemDialogsCloseAction() {
        this.volumeInfraMediator.sendSystemDialogsCloseAction();
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final void setActiveStream(int i) {
        this.volumeInfraMediator.setActiveStream(i);
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final void setCaptionEnabled(boolean z) {
        this.volumeInfraMediator.setCaptionEnabled(z);
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final void setRingerMode(int i, boolean z) {
        this.volumeInfraMediator.setRingerMode(i, z);
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final void setSafeMediaVolume() {
        this.volumeInfraMediator.setSafeMediaVolume();
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final void setSafeVolumeDialogShowing(boolean z) {
        this.volumeInfraMediator.setSafeVolumeDialogShowing(z);
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final void setStreamVolume(int i, int i2) {
        this.volumeInfraMediator.setStreamVolume(i, i2);
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final void setStreamVolumeDualAudio(int i, int i2, String str) {
        this.volumeInfraMediator.setStreamVolumeDualAudio(i, i2, str);
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final void startDoNotDisturbActivity() {
        this.volumeInfraMediator.startDoNotDisturbActivity();
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final void startHearingEnhancementsActivity() {
        this.volumeInfraMediator.startHearingEnhancementsActivity();
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final void startLeBroadcastActivity() {
        this.volumeInfraMediator.startLeBroadcastActivity();
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final void startSettingsActivity() {
        this.volumeInfraMediator.startSettingsActivity();
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final void startVolumeSettingsActivity() {
        this.volumeInfraMediator.startVolumeSettingsActivity();
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final void toggleWifiDisplayMute() {
        this.volumeInfraMediator.toggleWifiDisplayMute();
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final void userActivity() {
        this.volumeInfraMediator.userActivity();
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final void playSound(int i) {
        this.volumeInfraMediator.playSound(i);
    }
}
