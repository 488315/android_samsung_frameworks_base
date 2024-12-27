package com.android.systemui.communal.data.model;

import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
public final class DisabledReason {
    public static final /* synthetic */ EnumEntries $ENTRIES;
    public static final /* synthetic */ DisabledReason[] $VALUES;
    public static final DisabledReason DISABLED_REASON_DEVICE_POLICY;
    public static final DisabledReason DISABLED_REASON_FLAG;
    public static final DisabledReason DISABLED_REASON_INVALID_USER;
    public static final DisabledReason DISABLED_REASON_USER_SETTING;
    private final String loggingString;

    static {
        DisabledReason disabledReason = new DisabledReason("DISABLED_REASON_INVALID_USER", 0, "invalidUser");
        DISABLED_REASON_INVALID_USER = disabledReason;
        DisabledReason disabledReason2 = new DisabledReason("DISABLED_REASON_FLAG", 1, "flag");
        DISABLED_REASON_FLAG = disabledReason2;
        DisabledReason disabledReason3 = new DisabledReason("DISABLED_REASON_USER_SETTING", 2, "userSetting");
        DISABLED_REASON_USER_SETTING = disabledReason3;
        DisabledReason disabledReason4 = new DisabledReason("DISABLED_REASON_DEVICE_POLICY", 3, "devicePolicy");
        DISABLED_REASON_DEVICE_POLICY = disabledReason4;
        DisabledReason[] disabledReasonArr = {disabledReason, disabledReason2, disabledReason3, disabledReason4};
        $VALUES = disabledReasonArr;
        $ENTRIES = EnumEntriesKt.enumEntries(disabledReasonArr);
    }

    private DisabledReason(String str, int i, String str2) {
        this.loggingString = str2;
    }

    public static DisabledReason valueOf(String str) {
        return (DisabledReason) Enum.valueOf(DisabledReason.class, str);
    }

    public static DisabledReason[] values() {
        return (DisabledReason[]) $VALUES.clone();
    }

    public final String getLoggingString() {
        return this.loggingString;
    }
}
