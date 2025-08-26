package com.android.systemui.display.data.repository;

import kotlin.enums.EnumEntriesKt;

/* loaded from: classes2.dex */
public interface DeviceStateRepository {

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class DeviceState {
        public static final /* synthetic */ DeviceState[] $VALUES;
        public static final DeviceState CONCURRENT_DISPLAY;
        public static final DeviceState FOLDED;
        public static final DeviceState HALF_FOLDED;
        public static final DeviceState REAR_DISPLAY;
        public static final DeviceState REAR_DISPLAY_OUTER_DEFAULT;
        public static final DeviceState UNFOLDED;
        public static final DeviceState UNKNOWN;

        static {
            DeviceState deviceState = new DeviceState("FOLDED", 0);
            FOLDED = deviceState;
            DeviceState deviceState2 = new DeviceState("HALF_FOLDED", 1);
            HALF_FOLDED = deviceState2;
            DeviceState deviceState3 = new DeviceState("UNFOLDED", 2);
            UNFOLDED = deviceState3;
            DeviceState deviceState4 = new DeviceState("REAR_DISPLAY", 3);
            REAR_DISPLAY = deviceState4;
            DeviceState deviceState5 = new DeviceState("REAR_DISPLAY_OUTER_DEFAULT", 4);
            REAR_DISPLAY_OUTER_DEFAULT = deviceState5;
            DeviceState deviceState6 = new DeviceState("CONCURRENT_DISPLAY", 5);
            CONCURRENT_DISPLAY = deviceState6;
            DeviceState deviceState7 = new DeviceState("UNKNOWN", 6);
            UNKNOWN = deviceState7;
            DeviceState[] deviceStateArr = {deviceState, deviceState2, deviceState3, deviceState4, deviceState5, deviceState6, deviceState7};
            $VALUES = deviceStateArr;
            EnumEntriesKt.enumEntries(deviceStateArr);
        }

        private DeviceState(String str, int i) {
        }

        public static DeviceState valueOf(String str) {
            return (DeviceState) Enum.valueOf(DeviceState.class, str);
        }

        public static DeviceState[] values() {
            return (DeviceState[]) $VALUES.clone();
        }
    }
}
