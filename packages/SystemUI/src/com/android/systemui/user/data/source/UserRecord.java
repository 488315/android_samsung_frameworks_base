package com.android.systemui.user.data.source;

import android.content.pm.UserInfo;
import android.graphics.Bitmap;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import com.android.settingslib.RestrictedLockUtils;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class UserRecord {
    public final RestrictedLockUtils.EnforcedAdmin enforcedAdmin;
    public final UserInfo info;
    public final boolean isAddSupervisedUser;
    public final boolean isAddUser;
    public final boolean isCurrent;
    public final boolean isGuest;
    public final boolean isManageUsers;
    public final boolean isRestricted;
    public final boolean isSwitchToEnabled;
    public final Bitmap picture;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public UserRecord() {
        this(null, null, false, false, false, false, false, false, null, false, 1023, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof UserRecord)) {
            return false;
        }
        UserRecord userRecord = (UserRecord) obj;
        return Intrinsics.areEqual(this.info, userRecord.info) && Intrinsics.areEqual(this.picture, userRecord.picture) && this.isGuest == userRecord.isGuest && this.isCurrent == userRecord.isCurrent && this.isAddUser == userRecord.isAddUser && this.isRestricted == userRecord.isRestricted && this.isSwitchToEnabled == userRecord.isSwitchToEnabled && this.isAddSupervisedUser == userRecord.isAddSupervisedUser && Intrinsics.areEqual(this.enforcedAdmin, userRecord.enforcedAdmin) && this.isManageUsers == userRecord.isManageUsers;
    }

    public final int hashCode() {
        UserInfo userInfo = this.info;
        int hashCode = (userInfo == null ? 0 : userInfo.hashCode()) * 31;
        Bitmap bitmap = this.picture;
        int m = TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m((hashCode + (bitmap == null ? 0 : bitmap.hashCode())) * 31, 31, this.isGuest), 31, this.isCurrent), 31, this.isAddUser), 31, this.isRestricted), 31, this.isSwitchToEnabled), 31, this.isAddSupervisedUser);
        RestrictedLockUtils.EnforcedAdmin enforcedAdmin = this.enforcedAdmin;
        return Boolean.hashCode(this.isManageUsers) + ((m + (enforcedAdmin != null ? enforcedAdmin.hashCode() : 0)) * 31);
    }

    public final int resolveId() {
        UserInfo userInfo;
        if (this.isGuest || (userInfo = this.info) == null) {
            return -10000;
        }
        return userInfo.id;
    }

    public final String toString() {
        UserInfo userInfo = this.info;
        Bitmap bitmap = this.picture;
        StringBuilder sb = new StringBuilder("UserRecord(info=");
        sb.append(userInfo);
        sb.append(", picture=");
        sb.append(bitmap);
        sb.append(", isGuest=");
        sb.append(this.isGuest);
        sb.append(", isCurrent=");
        sb.append(this.isCurrent);
        sb.append(", isAddUser=");
        sb.append(this.isAddUser);
        sb.append(", isRestricted=");
        sb.append(this.isRestricted);
        sb.append(", isSwitchToEnabled=");
        sb.append(this.isSwitchToEnabled);
        sb.append(", isAddSupervisedUser=");
        sb.append(this.isAddSupervisedUser);
        sb.append(", enforcedAdmin=");
        sb.append(this.enforcedAdmin);
        sb.append(", isManageUsers=");
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(sb, this.isManageUsers, ")");
    }

    public UserRecord(UserInfo userInfo, Bitmap bitmap, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, RestrictedLockUtils.EnforcedAdmin enforcedAdmin, boolean z7) {
        this.info = userInfo;
        this.picture = bitmap;
        this.isGuest = z;
        this.isCurrent = z2;
        this.isAddUser = z3;
        this.isRestricted = z4;
        this.isSwitchToEnabled = z5;
        this.isAddSupervisedUser = z6;
        this.enforcedAdmin = enforcedAdmin;
        this.isManageUsers = z7;
    }

    public /* synthetic */ UserRecord(UserInfo userInfo, Bitmap bitmap, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, RestrictedLockUtils.EnforcedAdmin enforcedAdmin, boolean z7, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : userInfo, (i & 2) != 0 ? null : bitmap, (i & 4) != 0 ? false : z, (i & 8) != 0 ? false : z2, (i & 16) != 0 ? false : z3, (i & 32) != 0 ? false : z4, (i & 64) != 0 ? false : z5, (i & 128) != 0 ? false : z6, (i & 256) != 0 ? null : enforcedAdmin, (i & 512) != 0 ? false : z7);
    }
}
