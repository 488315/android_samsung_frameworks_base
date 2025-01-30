package com.samsung.systemui.splugins.volume;

import androidx.picker.adapter.layoutmanager.AutoFitGridLayoutManager$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardFaceListenModel$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.appops.AppOpItem$$ExternalSyntheticOutline0;
import java.util.HashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class VolumeStreamState {
    private int streamType;
    private HashMap<BooleanStateKey, Boolean> boolMap = new HashMap<>();
    private HashMap<IntegerStateKey, Integer> intMap = new HashMap<>();
    private HashMap<StringStateKey, String> stringMap = new HashMap<>();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum BooleanStateKey {
        DYNAMIC,
        MUTED,
        MUTE_SUPPORT,
        ROUTED_TO_BT,
        ROUTED_TO_BUDS,
        ROUTED_TO_BUDS3,
        ROUTED_TO_APP_MIRRORING,
        ROUTED_TO_REMOTE_SPEAKER,
        ROUTED_TO_HEADSET,
        ROUTED_TO_HOME_MINI,
        ROUTED_TO_HEARING_AID,
        DISABLED_FIXED_SESSION
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum IntegerStateKey {
        LEVEL,
        MIN,
        MAX
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum StringStateKey {
        DUAL_BT_DEVICE_ADDRESS,
        DUAL_BT_DEVICE_NAME,
        REMOTE_LABEL,
        NAME_RES
    }

    public final String getDualBtDeviceAddress() {
        return getStringValue(StringStateKey.DUAL_BT_DEVICE_ADDRESS);
    }

    public final String getDualBtDeviceName() {
        return getStringValue(StringStateKey.DUAL_BT_DEVICE_NAME);
    }

    public final int getIntegerValue(IntegerStateKey integerStateKey) {
        Integer num = this.intMap.get(integerStateKey);
        if (num == null) {
            return -1;
        }
        return num.intValue();
    }

    public final int getLevel() {
        return getIntegerValue(IntegerStateKey.LEVEL);
    }

    public final int getMax() {
        return getIntegerValue(IntegerStateKey.MAX);
    }

    public final int getMin() {
        return getIntegerValue(IntegerStateKey.MIN);
    }

    public final String getNameRes() {
        return getStringValue(StringStateKey.NAME_RES);
    }

    public final String getRemoteLabel() {
        return getStringValue(StringStateKey.REMOTE_LABEL);
    }

    public final int getStreamType() {
        return this.streamType;
    }

    public final String getStringValue(StringStateKey stringStateKey) {
        String str = this.stringMap.get(stringStateKey);
        return str == null ? "" : str;
    }

    public final boolean isDisabledFixedSession() {
        return isEnabled(BooleanStateKey.DISABLED_FIXED_SESSION);
    }

    public final boolean isDynamic() {
        return isEnabled(BooleanStateKey.DYNAMIC);
    }

    public final boolean isEnabled(BooleanStateKey booleanStateKey) {
        Boolean bool = this.boolMap.get(booleanStateKey);
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    public final boolean isMuteSupport() {
        return isEnabled(BooleanStateKey.MUTE_SUPPORT);
    }

    public final boolean isMuted() {
        return isEnabled(BooleanStateKey.MUTED);
    }

    public final boolean isRoutedToAppMirroring() {
        return isEnabled(BooleanStateKey.ROUTED_TO_APP_MIRRORING);
    }

    public final boolean isRoutedToBt() {
        return isEnabled(BooleanStateKey.ROUTED_TO_BT);
    }

    public final boolean isRoutedToBuds() {
        return isEnabled(BooleanStateKey.ROUTED_TO_BUDS);
    }

    public final boolean isRoutedToBuds3() {
        return isEnabled(BooleanStateKey.ROUTED_TO_BUDS3);
    }

    public final boolean isRoutedToHeadset() {
        return isEnabled(BooleanStateKey.ROUTED_TO_HEADSET);
    }

    public final boolean isRoutedToHearingAid() {
        return isEnabled(BooleanStateKey.ROUTED_TO_HEARING_AID);
    }

    public final boolean isRoutedToHomeMini() {
        return isEnabled(BooleanStateKey.ROUTED_TO_HOME_MINI);
    }

    public final boolean isRoutedToRemoteSpeaker() {
        return isEnabled(BooleanStateKey.ROUTED_TO_REMOTE_SPEAKER);
    }

    public String toString() {
        int i = this.streamType;
        boolean isDynamic = isDynamic();
        boolean isMuted = isMuted();
        boolean isMuteSupport = isMuteSupport();
        boolean isRoutedToBt = isRoutedToBt();
        boolean isRoutedToBuds = isRoutedToBuds();
        boolean isRoutedToAppMirroring = isRoutedToAppMirroring();
        boolean isRoutedToHeadset = isRoutedToHeadset();
        boolean isMuted2 = isMuted();
        boolean isMuteSupport2 = isMuteSupport();
        boolean isRoutedToBt2 = isRoutedToBt();
        boolean isDisabledFixedSession = isDisabledFixedSession();
        int level = getLevel();
        int min = getMin();
        int max = getMax();
        String dualBtDeviceAddress = getDualBtDeviceAddress();
        String dualBtDeviceName = getDualBtDeviceName();
        String remoteLabel = getRemoteLabel();
        String nameRes = getNameRes();
        StringBuilder m76m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m76m("[stream=", i, "] isDynamic=", isDynamic, ", isMuted=");
        KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(m76m, isMuted, ", isMuteSupport=", isMuteSupport, ", isRoutedToBt=");
        KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(m76m, isRoutedToBt, ", isRoutedToBuds=", isRoutedToBuds, ", isRoutedToAppMirroring=");
        KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(m76m, isRoutedToAppMirroring, ", isRoutedToHeadset=", isRoutedToHeadset, ", isMuted=");
        KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(m76m, isMuted2, ", isMuteSupport=", isMuteSupport2, ", isRoutedToBt=");
        KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(m76m, isRoutedToBt2, ", isDisabledFixedSession=", isDisabledFixedSession, ", level=");
        AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m40m(m76m, level, ", min=", min, ", max=");
        m76m.append(max);
        m76m.append(", dualBtDeviceAddress=");
        m76m.append(dualBtDeviceAddress);
        m76m.append(", dualBtDeviceName=");
        AppOpItem$$ExternalSyntheticOutline0.m97m(m76m, dualBtDeviceName, ", remoteLabel=", remoteLabel, ", nameRes=");
        m76m.append(nameRes);
        return m76m.toString();
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static final class Builder {
        private VolumeStreamState volumeStreamState;

        public Builder() {
            this.volumeStreamState = new VolumeStreamState();
        }

        public final VolumeStreamState build() {
            return this.volumeStreamState;
        }

        public final Builder dualBtDeviceAddress(String str) {
            return setStringValue(StringStateKey.DUAL_BT_DEVICE_ADDRESS, str);
        }

        public final Builder dualBtDeviceName(String str) {
            return setStringValue(StringStateKey.DUAL_BT_DEVICE_NAME, str);
        }

        public final Builder isDisabledFixedSession(boolean z) {
            return setEnabled(BooleanStateKey.DISABLED_FIXED_SESSION, z);
        }

        public final Builder isDynamic(boolean z) {
            return setEnabled(BooleanStateKey.DYNAMIC, z);
        }

        public final Builder isMuteSupport(boolean z) {
            return setEnabled(BooleanStateKey.MUTE_SUPPORT, z);
        }

        public final Builder isMuted(boolean z) {
            return setEnabled(BooleanStateKey.MUTED, z);
        }

        public final Builder isRoutedToAppMirroring(boolean z) {
            return setEnabled(BooleanStateKey.ROUTED_TO_APP_MIRRORING, z);
        }

        public final Builder isRoutedToBt(boolean z) {
            return setEnabled(BooleanStateKey.ROUTED_TO_BT, z);
        }

        public final Builder isRoutedToBuds(boolean z) {
            return setEnabled(BooleanStateKey.ROUTED_TO_BUDS, z);
        }

        public final Builder isRoutedToBuds3(boolean z) {
            return setEnabled(BooleanStateKey.ROUTED_TO_BUDS3, z);
        }

        public final Builder isRoutedToHeadset(boolean z) {
            return setEnabled(BooleanStateKey.ROUTED_TO_HEADSET, z);
        }

        public final Builder isRoutedToHearingAid(boolean z) {
            return setEnabled(BooleanStateKey.ROUTED_TO_HEARING_AID, z);
        }

        public final Builder isRoutedToHomeMini(boolean z) {
            return setEnabled(BooleanStateKey.ROUTED_TO_HOME_MINI, z);
        }

        public final Builder isRoutedToRemoteSpeaker(boolean z) {
            return setEnabled(BooleanStateKey.ROUTED_TO_REMOTE_SPEAKER, z);
        }

        public final Builder level(int i) {
            return setIntegerValue(IntegerStateKey.LEVEL, i);
        }

        public final Builder max(int i) {
            return setIntegerValue(IntegerStateKey.MAX, i);
        }

        public final Builder min(int i) {
            return setIntegerValue(IntegerStateKey.MIN, i);
        }

        public final Builder nameRes(String str) {
            return setStringValue(StringStateKey.NAME_RES, str);
        }

        public final Builder remoteLabel(String str) {
            return setStringValue(StringStateKey.REMOTE_LABEL, str);
        }

        public final Builder setEnabled(BooleanStateKey booleanStateKey, boolean z) {
            this.volumeStreamState.boolMap.put(booleanStateKey, Boolean.valueOf(z));
            return this;
        }

        public final Builder setIntegerValue(IntegerStateKey integerStateKey, int i) {
            this.volumeStreamState.intMap.put(integerStateKey, Integer.valueOf(i));
            return this;
        }

        public final Builder setStreamType(int i) {
            this.volumeStreamState.streamType = i;
            return this;
        }

        public final Builder setStringValue(StringStateKey stringStateKey, String str) {
            this.volumeStreamState.stringMap.put(stringStateKey, str);
            return this;
        }

        public Builder(VolumeStreamState volumeStreamState) {
            VolumeStreamState volumeStreamState2 = new VolumeStreamState();
            volumeStreamState2.streamType = volumeStreamState.getStreamType();
            volumeStreamState2.boolMap = volumeStreamState.boolMap;
            volumeStreamState2.intMap = volumeStreamState.intMap;
            volumeStreamState2.stringMap = volumeStreamState.stringMap;
            this.volumeStreamState = volumeStreamState2;
        }
    }

    public static /* synthetic */ void getStreamType$annotations() {
    }
}
