package com.android.systemui.settings.multisim;

import java.util.Arrays;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class MultiSIMData {
    public int defaultVoiceSimId = 0;
    public int defaultSmsSimId = 0;
    public int defaultDataSimId = 0;
    public int[] simImageIdx = {0, 1};
    public String[] simName = {"SIM 1", "SIM 2"};
    public boolean airplaneMode = false;
    public boolean changingNetMode = false;
    public boolean isSRoaming = false;
    public boolean changingDataInternally = false;
    public boolean[] isESimSlot = new boolean[2];
    public String[] carrierName = new String[2];
    public String[] phoneNumber = new String[2];
    public boolean isDataEnabled = false;
    public boolean isRestrictionsForMmsUse = false;
    public boolean isMultiSimReady = false;
    public boolean isCalling = false;
    public boolean isSecondaryUser = false;

    public final void copyFrom(MultiSIMData multiSIMData) {
        this.defaultVoiceSimId = multiSIMData.defaultVoiceSimId;
        this.defaultSmsSimId = multiSIMData.defaultSmsSimId;
        this.defaultDataSimId = multiSIMData.defaultDataSimId;
        this.airplaneMode = multiSIMData.airplaneMode;
        this.changingNetMode = multiSIMData.changingNetMode;
        this.isSRoaming = multiSIMData.isSRoaming;
        this.changingDataInternally = multiSIMData.changingDataInternally;
        this.isDataEnabled = multiSIMData.isDataEnabled;
        this.isRestrictionsForMmsUse = multiSIMData.isRestrictionsForMmsUse;
        this.isMultiSimReady = multiSIMData.isMultiSimReady;
        this.isCalling = multiSIMData.isCalling;
        this.isSecondaryUser = multiSIMData.isSecondaryUser;
        this.simImageIdx = Arrays.copyOf(multiSIMData.simImageIdx, 2);
        this.simName = (String[]) Arrays.copyOf(multiSIMData.simName, 2);
        this.isESimSlot = Arrays.copyOf(multiSIMData.isESimSlot, 2);
        this.carrierName = (String[]) Arrays.copyOf(multiSIMData.carrierName, 2);
        this.phoneNumber = (String[]) Arrays.copyOf(multiSIMData.phoneNumber, 2);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MultiSIMData)) {
            return false;
        }
        MultiSIMData multiSIMData = (MultiSIMData) obj;
        return this.defaultVoiceSimId == multiSIMData.defaultVoiceSimId && this.defaultSmsSimId == multiSIMData.defaultSmsSimId && this.defaultDataSimId == multiSIMData.defaultDataSimId && this.airplaneMode == multiSIMData.airplaneMode && this.changingNetMode == multiSIMData.changingNetMode && this.isSRoaming == multiSIMData.isSRoaming && this.changingDataInternally == multiSIMData.changingDataInternally && this.isDataEnabled == multiSIMData.isDataEnabled && this.isRestrictionsForMmsUse == multiSIMData.isRestrictionsForMmsUse && this.isMultiSimReady == multiSIMData.isMultiSimReady && this.isCalling == multiSIMData.isCalling && this.isSecondaryUser == multiSIMData.isSecondaryUser && Arrays.equals(this.simImageIdx, multiSIMData.simImageIdx) && Arrays.equals(this.simName, multiSIMData.simName) && Arrays.equals(this.isESimSlot, multiSIMData.isESimSlot) && Arrays.equals(this.carrierName, multiSIMData.carrierName) && Arrays.equals(this.phoneNumber, multiSIMData.phoneNumber);
    }

    public final String toString() {
        return "MultiSIMData{mDefaultVoiceSimId=" + this.defaultVoiceSimId + ", mDefaultSmsSimId=" + this.defaultSmsSimId + ", mDefaultDataSimId=" + this.defaultDataSimId + ", SimImageIdx=" + Arrays.toString(this.simImageIdx) + ", SimName=" + Arrays.toString(this.simName) + ", mAirplaneMode=" + this.airplaneMode + ", mChangNetModeDelaying=" + this.changingNetMode + ", mIsSRoaming=" + this.isSRoaming + ", mChangingDataInternally=" + this.changingDataInternally + ", mIsESimSlot=" + Arrays.toString(this.isESimSlot) + ", carrierName=" + Arrays.toString(this.carrierName) + ", phoneNumber=" + Arrays.toString(this.phoneNumber) + ", isDataEnabled=" + this.isDataEnabled + ", isRestrictionsForMmsUse=" + this.isRestrictionsForMmsUse + ", isMultiSimReady=" + this.isMultiSimReady + ", isCalling=" + this.isCalling + ", isSecondaryUser=" + this.isSecondaryUser + '}';
    }
}
