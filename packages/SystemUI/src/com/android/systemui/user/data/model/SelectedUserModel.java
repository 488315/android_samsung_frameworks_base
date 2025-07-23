package com.android.systemui.user.data.model;

import android.content.pm.UserInfo;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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
