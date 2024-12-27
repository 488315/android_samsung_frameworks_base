package com.android.systemui.deviceentry.shared.model;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class DeviceEntryRestrictionReason {
    public static final /* synthetic */ DeviceEntryRestrictionReason[] $VALUES;
    public static final DeviceEntryRestrictionReason AdaptiveAuthRequest;
    public static final DeviceEntryRestrictionReason BouncerLockedOut;
    public static final DeviceEntryRestrictionReason DeviceNotUnlockedSinceMainlineUpdate;
    public static final DeviceEntryRestrictionReason DeviceNotUnlockedSinceReboot;
    public static final DeviceEntryRestrictionReason NonStrongBiometricsSecurityTimeout;
    public static final DeviceEntryRestrictionReason NonStrongFaceLockedOut;
    public static final DeviceEntryRestrictionReason PolicyLockdown;
    public static final DeviceEntryRestrictionReason SecurityTimeout;
    public static final DeviceEntryRestrictionReason StrongBiometricsLockedOut;
    public static final DeviceEntryRestrictionReason TrustAgentDisabled;
    public static final DeviceEntryRestrictionReason UnattendedUpdate;
    public static final DeviceEntryRestrictionReason UserLockdown;

    static {
        DeviceEntryRestrictionReason deviceEntryRestrictionReason = new DeviceEntryRestrictionReason("UserLockdown", 0);
        UserLockdown = deviceEntryRestrictionReason;
        DeviceEntryRestrictionReason deviceEntryRestrictionReason2 = new DeviceEntryRestrictionReason("DeviceNotUnlockedSinceReboot", 1);
        DeviceNotUnlockedSinceReboot = deviceEntryRestrictionReason2;
        DeviceEntryRestrictionReason deviceEntryRestrictionReason3 = new DeviceEntryRestrictionReason("DeviceNotUnlockedSinceMainlineUpdate", 2);
        DeviceNotUnlockedSinceMainlineUpdate = deviceEntryRestrictionReason3;
        DeviceEntryRestrictionReason deviceEntryRestrictionReason4 = new DeviceEntryRestrictionReason("PolicyLockdown", 3);
        PolicyLockdown = deviceEntryRestrictionReason4;
        DeviceEntryRestrictionReason deviceEntryRestrictionReason5 = new DeviceEntryRestrictionReason("UnattendedUpdate", 4);
        UnattendedUpdate = deviceEntryRestrictionReason5;
        DeviceEntryRestrictionReason deviceEntryRestrictionReason6 = new DeviceEntryRestrictionReason("SecurityTimeout", 5);
        SecurityTimeout = deviceEntryRestrictionReason6;
        DeviceEntryRestrictionReason deviceEntryRestrictionReason7 = new DeviceEntryRestrictionReason("StrongBiometricsLockedOut", 6);
        StrongBiometricsLockedOut = deviceEntryRestrictionReason7;
        DeviceEntryRestrictionReason deviceEntryRestrictionReason8 = new DeviceEntryRestrictionReason("NonStrongFaceLockedOut", 7);
        NonStrongFaceLockedOut = deviceEntryRestrictionReason8;
        DeviceEntryRestrictionReason deviceEntryRestrictionReason9 = new DeviceEntryRestrictionReason("NonStrongBiometricsSecurityTimeout", 8);
        NonStrongBiometricsSecurityTimeout = deviceEntryRestrictionReason9;
        DeviceEntryRestrictionReason deviceEntryRestrictionReason10 = new DeviceEntryRestrictionReason("TrustAgentDisabled", 9);
        TrustAgentDisabled = deviceEntryRestrictionReason10;
        DeviceEntryRestrictionReason deviceEntryRestrictionReason11 = new DeviceEntryRestrictionReason("AdaptiveAuthRequest", 10);
        AdaptiveAuthRequest = deviceEntryRestrictionReason11;
        DeviceEntryRestrictionReason deviceEntryRestrictionReason12 = new DeviceEntryRestrictionReason("BouncerLockedOut", 11);
        BouncerLockedOut = deviceEntryRestrictionReason12;
        DeviceEntryRestrictionReason[] deviceEntryRestrictionReasonArr = {deviceEntryRestrictionReason, deviceEntryRestrictionReason2, deviceEntryRestrictionReason3, deviceEntryRestrictionReason4, deviceEntryRestrictionReason5, deviceEntryRestrictionReason6, deviceEntryRestrictionReason7, deviceEntryRestrictionReason8, deviceEntryRestrictionReason9, deviceEntryRestrictionReason10, deviceEntryRestrictionReason11, deviceEntryRestrictionReason12};
        $VALUES = deviceEntryRestrictionReasonArr;
        EnumEntriesKt.enumEntries(deviceEntryRestrictionReasonArr);
    }

    private DeviceEntryRestrictionReason(String str, int i) {
    }

    public static DeviceEntryRestrictionReason valueOf(String str) {
        return (DeviceEntryRestrictionReason) Enum.valueOf(DeviceEntryRestrictionReason.class, str);
    }

    public static DeviceEntryRestrictionReason[] values() {
        return (DeviceEntryRestrictionReason[]) $VALUES.clone();
    }
}
