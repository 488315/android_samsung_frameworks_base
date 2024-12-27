package com.android.systemui.biometrics.domain.interactor;

import kotlin.enums.EnumEntriesKt;

final class UserType {
    public static final /* synthetic */ UserType[] $VALUES;
    public static final UserType MANAGED_PROFILE;
    public static final UserType PRIMARY;
    public static final UserType SECONDARY;

    static {
        UserType userType = new UserType("PRIMARY", 0);
        PRIMARY = userType;
        UserType userType2 = new UserType("MANAGED_PROFILE", 1);
        MANAGED_PROFILE = userType2;
        UserType userType3 = new UserType("SECONDARY", 2);
        SECONDARY = userType3;
        UserType[] userTypeArr = {userType, userType2, userType3};
        $VALUES = userTypeArr;
        EnumEntriesKt.enumEntries(userTypeArr);
    }

    private UserType(String str, int i) {
    }

    public static UserType valueOf(String str) {
        return (UserType) Enum.valueOf(UserType.class, str);
    }

    public static UserType[] values() {
        return (UserType[]) $VALUES.clone();
    }
}
