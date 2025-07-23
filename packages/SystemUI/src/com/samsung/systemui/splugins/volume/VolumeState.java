package com.samsung.systemui.splugins.volume;

import java.util.HashMap;
import java.util.List;
import kotlin.collections.EmptyList;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class VolumeState {
    public static final int $stable = 8;
    private List<VolumeStreamState> streamStates = EmptyList.INSTANCE;
    private HashMap<BooleanStateKey, Boolean> boolMap = new HashMap<>();
    private HashMap<IntegerStateKey, Integer> intMap = new HashMap<>();

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class BooleanStateKey {
        private static final /* synthetic */ EnumEntries $ENTRIES;
        private static final /* synthetic */ BooleanStateKey[] $VALUES;
        public static final BooleanStateKey FIXED_SCO_VOLUME = new BooleanStateKey("FIXED_SCO_VOLUME", 0);
        public static final BooleanStateKey IS_DUAL_AUDIO = new BooleanStateKey("IS_DUAL_AUDIO", 1);
        public static final BooleanStateKey IS_FROM_KEY = new BooleanStateKey("IS_FROM_KEY", 2);
        public static final BooleanStateKey DISALLOW_RINGER = new BooleanStateKey("DISALLOW_RINGER", 3);
        public static final BooleanStateKey DISALLOW_SYSTEM = new BooleanStateKey("DISALLOW_SYSTEM", 4);
        public static final BooleanStateKey DISALLOW_MEDIA = new BooleanStateKey("DISALLOW_MEDIA", 5);
        public static final BooleanStateKey REMOTE_MIC = new BooleanStateKey("REMOTE_MIC", 6);
        public static final BooleanStateKey IS_AOD_VOLUME_PANEL = new BooleanStateKey("IS_AOD_VOLUME_PANEL", 7);
        public static final BooleanStateKey IS_LE_BROADCASTING = new BooleanStateKey("IS_LE_BROADCASTING", 8);

        private static final /* synthetic */ BooleanStateKey[] $values() {
            return new BooleanStateKey[]{FIXED_SCO_VOLUME, IS_DUAL_AUDIO, IS_FROM_KEY, DISALLOW_RINGER, DISALLOW_SYSTEM, DISALLOW_MEDIA, REMOTE_MIC, IS_AOD_VOLUME_PANEL, IS_LE_BROADCASTING};
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
        public static final IntegerStateKey ACTIVE_STREAM = new IntegerStateKey("ACTIVE_STREAM", 0);
        public static final IntegerStateKey RINGER_MODE_INTERNAL = new IntegerStateKey("RINGER_MODE_INTERNAL", 1);
        public static final IntegerStateKey ZEN_MODE = new IntegerStateKey("ZEN_MODE", 2);
        public static final IntegerStateKey BROADCAST_MODE = new IntegerStateKey("BROADCAST_MODE", 3);

        private static final /* synthetic */ IntegerStateKey[] $values() {
            return new IntegerStateKey[]{ACTIVE_STREAM, RINGER_MODE_INTERNAL, ZEN_MODE, BROADCAST_MODE};
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

    public final int getActiveStream() {
        return getIntegerValue(IntegerStateKey.ACTIVE_STREAM);
    }

    public final int getBroadcastMode() {
        return getIntegerValue(IntegerStateKey.BROADCAST_MODE);
    }

    public final int getIntegerValue(IntegerStateKey integerStateKey) {
        Integer num = this.intMap.get(integerStateKey);
        if (num != null) {
            return num.intValue();
        }
        return -1;
    }

    public final int getRingerModeInternal() {
        return getIntegerValue(IntegerStateKey.RINGER_MODE_INTERNAL);
    }

    public final List<VolumeStreamState> getStreamStates() {
        return this.streamStates;
    }

    public final int getZenMode() {
        return getIntegerValue(IntegerStateKey.ZEN_MODE);
    }

    public final boolean isAodVolumePanel() {
        return isEnabled(BooleanStateKey.IS_AOD_VOLUME_PANEL);
    }

    public final boolean isDisallowMedia() {
        return isEnabled(BooleanStateKey.DISALLOW_MEDIA);
    }

    public final boolean isDisallowRinger() {
        return isEnabled(BooleanStateKey.DISALLOW_RINGER);
    }

    public final boolean isDisallowSystem() {
        return isEnabled(BooleanStateKey.DISALLOW_SYSTEM);
    }

    public final boolean isDualAudio() {
        return isEnabled(BooleanStateKey.IS_DUAL_AUDIO);
    }

    public final boolean isEnabled(BooleanStateKey booleanStateKey) {
        Boolean bool = this.boolMap.get(booleanStateKey);
        if (bool != null) {
            return bool.booleanValue();
        }
        return false;
    }

    public final boolean isFixedScoVolume() {
        return isEnabled(BooleanStateKey.FIXED_SCO_VOLUME);
    }

    public final boolean isFromKey() {
        return isEnabled(BooleanStateKey.IS_FROM_KEY);
    }

    public final boolean isLeBroadcasting() {
        return isEnabled(BooleanStateKey.IS_LE_BROADCASTING);
    }

    public final boolean isRemoteMic() {
        return isEnabled(BooleanStateKey.REMOTE_MIC);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Builder {
        public static final int $stable = 8;
        private VolumeState volumeState;

        public Builder() {
            this.volumeState = new VolumeState();
        }

        public final Builder activeStream(int i) {
            return setIntegerValue(IntegerStateKey.ACTIVE_STREAM, i);
        }

        public final Builder broadcastMode(int i) {
            return setIntegerValue(IntegerStateKey.BROADCAST_MODE, i);
        }

        public final VolumeState build() {
            return this.volumeState;
        }

        public final Builder disallowMedia(boolean z) {
            return setEnabled(BooleanStateKey.DISALLOW_MEDIA, z);
        }

        public final Builder disallowRinger(boolean z) {
            return setEnabled(BooleanStateKey.DISALLOW_RINGER, z);
        }

        public final Builder disallowSystem(boolean z) {
            return setEnabled(BooleanStateKey.DISALLOW_SYSTEM, z);
        }

        public final Builder fixedScoVolume(boolean z) {
            return setEnabled(BooleanStateKey.FIXED_SCO_VOLUME, z);
        }

        public final Builder isAodVolumePanel(boolean z) {
            return setEnabled(BooleanStateKey.IS_AOD_VOLUME_PANEL, z);
        }

        public final Builder isDualAudio(boolean z) {
            return setEnabled(BooleanStateKey.IS_DUAL_AUDIO, z);
        }

        public final Builder isFromKey(boolean z) {
            return setEnabled(BooleanStateKey.IS_FROM_KEY, z);
        }

        public final Builder isLeBroadcasting(boolean z) {
            return setEnabled(BooleanStateKey.IS_LE_BROADCASTING, z);
        }

        public final Builder remoteMic(boolean z) {
            return setEnabled(BooleanStateKey.REMOTE_MIC, z);
        }

        public final Builder ringerModeInternal(int i) {
            return setIntegerValue(IntegerStateKey.RINGER_MODE_INTERNAL, i);
        }

        public final Builder setEnabled(BooleanStateKey booleanStateKey, boolean z) {
            this.volumeState.boolMap.put(booleanStateKey, Boolean.valueOf(z));
            return this;
        }

        public final Builder setIntegerValue(IntegerStateKey integerStateKey, int i) {
            this.volumeState.intMap.put(integerStateKey, Integer.valueOf(i));
            return this;
        }

        public final Builder setStreamStates(List<VolumeStreamState> list) {
            this.volumeState.streamStates = list;
            return this;
        }

        public final Builder zenMode(int i) {
            return setIntegerValue(IntegerStateKey.ZEN_MODE, i);
        }

        public Builder(VolumeState volumeState) {
            VolumeState volumeState2 = new VolumeState();
            volumeState2.streamStates = volumeState.getStreamStates();
            volumeState2.boolMap = volumeState.boolMap;
            volumeState2.intMap = volumeState.intMap;
            this.volumeState = volumeState2;
        }
    }

    public static /* synthetic */ void getRingerModeInternal$annotations() {
    }

    public static /* synthetic */ void getStreamStates$annotations() {
    }
}
