package com.android.systemui.user.shared.model;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class UserActionModel {
    public static final /* synthetic */ UserActionModel[] $VALUES;
    public static final UserActionModel ADD_SUPERVISED_USER;
    public static final UserActionModel ADD_USER;
    public static final UserActionModel ENTER_GUEST_MODE;
    public static final UserActionModel NAVIGATE_TO_USER_MANAGEMENT;

    static {
        UserActionModel userActionModel = new UserActionModel("ENTER_GUEST_MODE", 0);
        ENTER_GUEST_MODE = userActionModel;
        UserActionModel userActionModel2 = new UserActionModel("ADD_USER", 1);
        ADD_USER = userActionModel2;
        UserActionModel userActionModel3 = new UserActionModel("ADD_SUPERVISED_USER", 2);
        ADD_SUPERVISED_USER = userActionModel3;
        UserActionModel userActionModel4 = new UserActionModel("NAVIGATE_TO_USER_MANAGEMENT", 3);
        NAVIGATE_TO_USER_MANAGEMENT = userActionModel4;
        UserActionModel[] userActionModelArr = {userActionModel, userActionModel2, userActionModel3, userActionModel4};
        $VALUES = userActionModelArr;
        EnumEntriesKt.enumEntries(userActionModelArr);
    }

    private UserActionModel(String str, int i) {
    }

    public static UserActionModel valueOf(String str) {
        return (UserActionModel) Enum.valueOf(UserActionModel.class, str);
    }

    public static UserActionModel[] values() {
        return (UserActionModel[]) $VALUES.clone();
    }
}
