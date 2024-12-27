package com.android.systemui.display.data.repository;

import kotlin.enums.EnumEntriesKt;

public interface DeviceStateRepository {

    public final class DeviceState {
        public static final /* synthetic */ DeviceState[] $VALUES;
        public static final DeviceState CONCURRENT_DISPLAY;
        public static final DeviceState FOLDED;
        public static final DeviceState HALF_FOLDED;
        public static final DeviceState REAR_DISPLAY;
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
            DeviceState deviceState5 = new DeviceState("CONCURRENT_DISPLAY", 4);
            CONCURRENT_DISPLAY = deviceState5;
            DeviceState deviceState6 = new DeviceState("UNKNOWN", 5);
            UNKNOWN = deviceState6;
            DeviceState[] deviceStateArr = {deviceState, deviceState2, deviceState3, deviceState4, deviceState5, deviceState6};
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
