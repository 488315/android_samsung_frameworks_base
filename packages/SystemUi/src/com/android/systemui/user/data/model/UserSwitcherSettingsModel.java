package com.android.systemui.user.data.model;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        boolean z = this.isSimpleUserSwitcher;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        int i2 = i * 31;
        boolean z2 = this.isAddUsersFromLockscreen;
        int i3 = z2;
        if (z2 != 0) {
            i3 = 1;
        }
        int i4 = (i2 + i3) * 31;
        boolean z3 = this.isUserSwitcherEnabled;
        return i4 + (z3 ? 1 : z3 ? 1 : 0);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("UserSwitcherSettingsModel(isSimpleUserSwitcher=");
        sb.append(this.isSimpleUserSwitcher);
        sb.append(", isAddUsersFromLockscreen=");
        sb.append(this.isAddUsersFromLockscreen);
        sb.append(", isUserSwitcherEnabled=");
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m6m(sb, this.isUserSwitcherEnabled, ")");
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
