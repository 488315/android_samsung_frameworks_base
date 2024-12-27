package com.android.systemui.keyguard.shared.model;

import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class DevicePosture {
    public static final /* synthetic */ DevicePosture[] $VALUES;
    public static final DevicePosture CLOSED;
    public static final Companion Companion;
    public static final DevicePosture FLIPPED;
    public static final DevicePosture HALF_OPENED;
    public static final DevicePosture OPENED;
    public static final DevicePosture UNKNOWN;

    public final class Companion {
        private Companion() {
        }

        public static DevicePosture toPosture(int i) {
            return i != 0 ? i != 1 ? i != 2 ? i != 3 ? i != 4 ? DevicePosture.UNKNOWN : DevicePosture.FLIPPED : DevicePosture.OPENED : DevicePosture.HALF_OPENED : DevicePosture.CLOSED : DevicePosture.UNKNOWN;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        DevicePosture devicePosture = new DevicePosture("UNKNOWN", 0);
        UNKNOWN = devicePosture;
        DevicePosture devicePosture2 = new DevicePosture("CLOSED", 1);
        CLOSED = devicePosture2;
        DevicePosture devicePosture3 = new DevicePosture("HALF_OPENED", 2);
        HALF_OPENED = devicePosture3;
        DevicePosture devicePosture4 = new DevicePosture("OPENED", 3);
        OPENED = devicePosture4;
        DevicePosture devicePosture5 = new DevicePosture("FLIPPED", 4);
        FLIPPED = devicePosture5;
        DevicePosture[] devicePostureArr = {devicePosture, devicePosture2, devicePosture3, devicePosture4, devicePosture5};
        $VALUES = devicePostureArr;
        EnumEntriesKt.enumEntries(devicePostureArr);
        Companion = new Companion(null);
    }

    private DevicePosture(String str, int i) {
    }

    public static DevicePosture valueOf(String str) {
        return (DevicePosture) Enum.valueOf(DevicePosture.class, str);
    }

    public static DevicePosture[] values() {
        return (DevicePosture[]) $VALUES.clone();
    }
}
