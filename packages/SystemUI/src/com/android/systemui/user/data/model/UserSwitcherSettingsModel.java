package com.android.systemui.user.data.model;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class UserSwitcherSettingsModel {
    public final boolean isAddUsersFromLockscreen;
    public final boolean isSimpleUserSwitcher;
    public final boolean isUserSwitcherEnabled;

    public UserSwitcherSettingsModel() {
        this(false, false, false, 7, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof UserSwitcherSettingsModel)) {
            return false;
        }
        UserSwitcherSettingsModel userSwitcherSettingsModel = (UserSwitcherSettingsModel) obj;
        return this.isSimpleUserSwitcher == userSwitcherSettingsModel.isSimpleUserSwitcher && this.isAddUsersFromLockscreen == userSwitcherSettingsModel.isAddUsersFromLockscreen && this.isUserSwitcherEnabled == userSwitcherSettingsModel.isUserSwitcherEnabled;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.isUserSwitcherEnabled) + TransitionData$$ExternalSyntheticOutline0.m(Boolean.hashCode(this.isSimpleUserSwitcher) * 31, 31, this.isAddUsersFromLockscreen);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("UserSwitcherSettingsModel(isSimpleUserSwitcher=");
        sb.append(this.isSimpleUserSwitcher);
        sb.append(", isAddUsersFromLockscreen=");
        sb.append(this.isAddUsersFromLockscreen);
        sb.append(", isUserSwitcherEnabled=");
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(sb, this.isUserSwitcherEnabled, ")");
    }

    public UserSwitcherSettingsModel(boolean z, boolean z2, boolean z3) {
        this.isSimpleUserSwitcher = z;
        this.isAddUsersFromLockscreen = z2;
        this.isUserSwitcherEnabled = z3;
    }

    public /* synthetic */ UserSwitcherSettingsModel(boolean z, boolean z2, boolean z3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? false : z, (i & 2) != 0 ? false : z2, (i & 4) != 0 ? false : z3);
    }
}
