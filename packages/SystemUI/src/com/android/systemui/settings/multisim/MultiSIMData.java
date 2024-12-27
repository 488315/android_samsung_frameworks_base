package com.android.systemui.settings.multisim;

import androidx.compose.animation.ChangeSize$$ExternalSyntheticOutline0;
import java.util.Arrays;

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
    public boolean isSatelliteMode = false;

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
        this.isSatelliteMode = multiSIMData.isSatelliteMode;
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
        return this.defaultVoiceSimId == multiSIMData.defaultVoiceSimId && this.defaultSmsSimId == multiSIMData.defaultSmsSimId && this.defaultDataSimId == multiSIMData.defaultDataSimId && this.airplaneMode == multiSIMData.airplaneMode && this.changingNetMode == multiSIMData.changingNetMode && this.isSRoaming == multiSIMData.isSRoaming && this.changingDataInternally == multiSIMData.changingDataInternally && this.isDataEnabled == multiSIMData.isDataEnabled && this.isRestrictionsForMmsUse == multiSIMData.isRestrictionsForMmsUse && this.isMultiSimReady == multiSIMData.isMultiSimReady && this.isCalling == multiSIMData.isCalling && this.isSecondaryUser == multiSIMData.isSecondaryUser && this.isSatelliteMode == multiSIMData.isSatelliteMode && Arrays.equals(this.simImageIdx, multiSIMData.simImageIdx) && Arrays.equals(this.simName, multiSIMData.simName) && Arrays.equals(this.isESimSlot, multiSIMData.isESimSlot) && Arrays.equals(this.carrierName, multiSIMData.carrierName) && Arrays.equals(this.phoneNumber, multiSIMData.phoneNumber);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("MultiSIMData{mDefaultVoiceSimId=");
        sb.append(this.defaultVoiceSimId);
        sb.append(", mDefaultSmsSimId=");
        sb.append(this.defaultSmsSimId);
        sb.append(", mDefaultDataSimId=");
        sb.append(this.defaultDataSimId);
        sb.append(", SimImageIdx=");
        sb.append(Arrays.toString(this.simImageIdx));
        sb.append(", SimName=");
        sb.append(Arrays.toString(this.simName));
        sb.append(", mAirplaneMode=");
        sb.append(this.airplaneMode);
        sb.append(", mChangNetModeDelaying=");
        sb.append(this.changingNetMode);
        sb.append(", mIsSRoaming=");
        sb.append(this.isSRoaming);
        sb.append(", mChangingDataInternally=");
        sb.append(this.changingDataInternally);
        sb.append(", mIsESimSlot=");
        sb.append(Arrays.toString(this.isESimSlot));
        sb.append(", carrierName=");
        sb.append(Arrays.toString(this.carrierName));
        sb.append(", phoneNumber=");
        sb.append(Arrays.toString(this.phoneNumber));
        sb.append(", isDataEnabled=");
        sb.append(this.isDataEnabled);
        sb.append(", isRestrictionsForMmsUse=");
        sb.append(this.isRestrictionsForMmsUse);
        sb.append(", isMultiSimReady=");
        sb.append(this.isMultiSimReady);
        sb.append(", isCalling=");
        sb.append(this.isCalling);
        sb.append(", isSecondaryUser=");
        sb.append(this.isSecondaryUser);
        sb.append(", isSatelliteMode=");
        return ChangeSize$$ExternalSyntheticOutline0.m(sb, this.isSatelliteMode, '}');
    }
}
