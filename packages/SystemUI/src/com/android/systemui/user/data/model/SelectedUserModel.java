package com.android.systemui.user.data.model;

import android.content.pm.UserInfo;
import kotlin.jvm.internal.Intrinsics;

public final class SelectedUserModel {
    public final SelectionStatus selectionStatus;
    public final UserInfo userInfo;

    public SelectedUserModel(UserInfo userInfo, SelectionStatus selectionStatus) {
        this.userInfo = userInfo;
        this.selectionStatus = selectionStatus;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SelectedUserModel)) {
            return false;
        }
        SelectedUserModel selectedUserModel = (SelectedUserModel) obj;
        return Intrinsics.areEqual(this.userInfo, selectedUserModel.userInfo) && this.selectionStatus == selectedUserModel.selectionStatus;
    }

    public final int hashCode() {
        return this.selectionStatus.hashCode() + (this.userInfo.hashCode() * 31);
    }

    public final String toString() {
        return "SelectedUserModel(userInfo=" + this.userInfo + ", selectionStatus=" + this.selectionStatus + ")";
    }
}
