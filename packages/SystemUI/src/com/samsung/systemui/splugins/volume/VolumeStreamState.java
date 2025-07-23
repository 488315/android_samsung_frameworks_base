package com.samsung.systemui.splugins.volume;

import androidx.viewpager.widget.ViewPager$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import java.util.HashMap;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class VolumeStreamState {
    public static final int $stable = 8;
    private int streamType;
    private HashMap<BooleanStateKey, Boolean> boolMap = new HashMap<>();
    private HashMap<IntegerStateKey, Integer> intMap = new HashMap<>();
    private HashMap<StringStateKey, String> stringMap = new HashMap<>();

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class BooleanStateKey {
        private static final /* synthetic */ EnumEntries $ENTRIES;
        private static final /* synthetic */ BooleanStateKey[] $VALUES;
        public static final BooleanStateKey DYNAMIC = new BooleanStateKey("DYNAMIC", 0);
        public static final BooleanStateKey MUTED = new BooleanStateKey("MUTED", 1);
        public static final BooleanStateKey MUTE_SUPPORT = new BooleanStateKey("MUTE_SUPPORT", 2);
        public static final BooleanStateKey ROUTED_TO_BT = new BooleanStateKey("ROUTED_TO_BT", 3);
        public static final BooleanStateKey ROUTED_TO_BUDS = new BooleanStateKey("ROUTED_TO_BUDS", 4);
        public static final BooleanStateKey ROUTED_TO_BUDS3 = new BooleanStateKey("ROUTED_TO_BUDS3", 5);
        public static final BooleanStateKey ROUTED_TO_APP_MIRRORING = new BooleanStateKey("ROUTED_TO_APP_MIRRORING", 6);
        public static final BooleanStateKey ROUTED_TO_REMOTE_SPEAKER = new BooleanStateKey("ROUTED_TO_REMOTE_SPEAKER", 7);
        public static final BooleanStateKey ROUTED_TO_HEADSET = new BooleanStateKey("ROUTED_TO_HEADSET", 8);
        public static final BooleanStateKey ROUTED_TO_HOME_MINI = new BooleanStateKey("ROUTED_TO_HOME_MINI", 9);
        public static final BooleanStateKey ROUTED_TO_MUSIC_FRAME = new BooleanStateKey("ROUTED_TO_MUSIC_FRAME", 10);
        public static final BooleanStateKey ROUTED_TO_HEARING_AID = new BooleanStateKey("ROUTED_TO_HEARING_AID", 11);
        public static final BooleanStateKey DISABLED_FIXED_SESSION = new BooleanStateKey("DISABLED_FIXED_SESSION", 12);

        private static final /* synthetic */ BooleanStateKey[] $values() {
            return new BooleanStateKey[]{DYNAMIC, MUTED, MUTE_SUPPORT, ROUTED_TO_BT, ROUTED_TO_BUDS, ROUTED_TO_BUDS3, ROUTED_TO_APP_MIRRORING, ROUTED_TO_REMOTE_SPEAKER, ROUTED_TO_HEADSET, ROUTED_TO_HOME_MINI, ROUTED_TO_MUSIC_FRAME, ROUTED_TO_HEARING_AID, DISABLED_FIXED_SESSION};
        }

        static {
            BooleanStateKey[] $values = $values();
            $VALUES = $values;
            $ENTRIES = EnumEntriesKt.enumEntries($values);
        }

        private BooleanStateKey(String str, int i) {
        }

        public static EnumEntries getEntries() {
            return $ENTRIES;
        }

        public static BooleanStateKey valueOf(String str) {
            return (BooleanStateKey) Enum.valueOf(BooleanStateKey.class, str);
        }

        public static BooleanStateKey[] values() {
            return (BooleanStateKey[]) $VALUES.clone();
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class IntegerStateKey {
        private static final /* synthetic */ EnumEntries $ENTRIES;
        private static final /* synthetic */ IntegerStateKey[] $VALUES;
        public static final IntegerStateKey LEVEL = new IntegerStateKey("LEVEL", 0);
        public static final IntegerStateKey MIN = new IntegerStateKey("MIN", 1);
        public static final IntegerStateKey MAX = new IntegerStateKey("MAX", 2);

        private static final /* synthetic */ IntegerStateKey[] $values() {
            return new IntegerStateKey[]{LEVEL, MIN, MAX};
        }

        static {
            IntegerStateKey[] $values = $values();
            $VALUES = $values;
            $ENTRIES = EnumEntriesKt.enumEntries($values);
        }

        private IntegerStateKey(String str, int i) {
        }

        public static EnumEntries getEntries() {
            return $ENTRIES;
        }

        public static IntegerStateKey valueOf(String str) {
            return (IntegerStateKey) Enum.valueOf(IntegerStateKey.class, str);
        }

        public static IntegerStateKey[] values() {
            return (IntegerStateKey[]) $VALUES.clone();
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class StringStateKey {
        private static final /* synthetic */ EnumEntries $ENTRIES;
        private static final /* synthetic */ StringStateKey[] $VALUES;
        public static final StringStateKey DUAL_BT_DEVICE_ADDRESS = new StringStateKey("DUAL_BT_DEVICE_ADDRESS", 0);
        public static final StringStateKey DUAL_BT_DEVICE_NAME = new StringStateKey("DUAL_BT_DEVICE_NAME", 1);
        public static final StringStateKey REMOTE_LABEL = new StringStateKey("REMOTE_LABEL", 2);
        public static final StringStateKey NAME_RES = new StringStateKey("NAME_RES", 3);

        private static final /* synthetic */ StringStateKey[] $values() {
            return new StringStateKey[]{DUAL_BT_DEVICE_ADDRESS, DUAL_BT_DEVICE_NAME, REMOTE_LABEL, NAME_RES};
        }

        static {
            StringStateKey[] $values = $values();
            $VALUES = $values;
            $ENTRIES = EnumEntriesKt.enumEntries($values);
        }

        private StringStateKey(String str, int i) {
        }

        public static EnumEntries getEntries() {
            return $ENTRIES;
        }

        public static StringStateKey valueOf(String str) {
            return (StringStateKey) Enum.valueOf(StringStateKey.class, str);
        }

        public static StringStateKey[] values() {
            return (StringStateKey[]) $VALUES.clone();
        }
    }

    public final String getDualBtDeviceAddress() {
        return getStringValue(StringStateKey.DUAL_BT_DEVICE_ADDRESS);
    }

    public final String getDualBtDeviceName() {
        return getStringValue(StringStateKey.DUAL_BT_DEVICE_NAME);
    }

    public final int getIntegerValue(IntegerStateKey integerStateKey) {
        Integer num = this.intMap.get(integerStateKey);
        if (num != null) {
            return num.intValue();
        }
        return -1;
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
        if (bool != null) {
            return bool.booleanValue();
        }
        return false;
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

    public final boolean isRoutedToMusicFrame() {
        return isEnabled(BooleanStateKey.ROUTED_TO_MUSIC_FRAME);
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
        StringBuilder m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("[stream=", i, "] isDynamic=", isDynamic, ", isMuted=");
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(m, isMuted, ", isMuteSupport=", isMuteSupport, ", isRoutedToBt=");
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(m, isRoutedToBt, ", isRoutedToBuds=", isRoutedToBuds, ", isRoutedToAppMirroring=");
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(m, isRoutedToAppMirroring, ", isRoutedToHeadset=", isRoutedToHeadset, ", isMuted=");
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(m, isMuted2, ", isMuteSupport=", isMuteSupport2, ", isRoutedToBt=");
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(m, isRoutedToBt2, ", isDisabledFixedSession=", isDisabledFixedSession, ", level=");
        ViewPager$$ExternalSyntheticOutline0.m(m, level, ", min=", min, ", max=");
        m.append(max);
        m.append(", dualBtDeviceAddress=");
        m.append(dualBtDeviceAddress);
        m.append(", dualBtDeviceName=");
        MoveResult$$ExternalSyntheticOutline0.m(m, dualBtDeviceName, ", remoteLabel=", remoteLabel, ", nameRes=");
        m.append(nameRes);
        return m.toString();
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Builder {
        public static final int $stable = 8;
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

        public final Builder isRoutedToMusicFrame(boolean z) {
            return setEnabled(BooleanStateKey.ROUTED_TO_MUSIC_FRAME, z);
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
