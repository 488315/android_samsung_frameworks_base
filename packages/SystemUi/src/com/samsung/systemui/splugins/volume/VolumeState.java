package com.samsung.systemui.splugins.volume;

import java.util.HashMap;
import java.util.List;
import kotlin.collections.EmptyList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class VolumeState {
    private List<VolumeStreamState> streamStates = EmptyList.INSTANCE;
    private HashMap<BooleanStateKey, Boolean> boolMap = new HashMap<>();
    private HashMap<IntegerStateKey, Integer> intMap = new HashMap<>();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum BooleanStateKey {
        FIXED_SCO_VOLUME,
        IS_DUAL_AUDIO,
        IS_FROM_KEY,
        DISALLOW_RINGER,
        DISALLOW_SYSTEM,
        DISALLOW_MEDIA,
        REMOTE_MIC,
        IS_AOD_VOLUME_PANEL,
        IS_LE_BROADCASTING
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum IntegerStateKey {
        ACTIVE_STREAM,
        RINGER_MODE_INTERNAL,
        ZEN_MODE,
        BROADCAST_MODE
    }

    public final int getActiveStream() {
        return getIntegerValue(IntegerStateKey.ACTIVE_STREAM);
    }

    public final int getBroadcastMode() {
        return getIntegerValue(IntegerStateKey.BROADCAST_MODE);
    }

    public final int getIntegerValue(IntegerStateKey integerStateKey) {
        Integer num = this.intMap.get(integerStateKey);
        if (num == null) {
            return -1;
        }
        return num.intValue();
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
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static final class Builder {
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
