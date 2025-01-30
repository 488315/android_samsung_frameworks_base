package com.android.systemui.user.data.source;

import android.content.pm.UserInfo;
import android.graphics.Bitmap;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import com.android.settingslib.RestrictedLockUtils;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        UserInfo userInfo = this.info;
        int hashCode = (userInfo == null ? 0 : userInfo.hashCode()) * 31;
        Bitmap bitmap = this.picture;
        int hashCode2 = (hashCode + (bitmap == null ? 0 : bitmap.hashCode())) * 31;
        boolean z = this.isGuest;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        int i2 = (hashCode2 + i) * 31;
        boolean z2 = this.isCurrent;
        int i3 = z2;
        if (z2 != 0) {
            i3 = 1;
        }
        int i4 = (i2 + i3) * 31;
        boolean z3 = this.isAddUser;
        int i5 = z3;
        if (z3 != 0) {
            i5 = 1;
        }
        int i6 = (i4 + i5) * 31;
        boolean z4 = this.isRestricted;
        int i7 = z4;
        if (z4 != 0) {
            i7 = 1;
        }
        int i8 = (i6 + i7) * 31;
        boolean z5 = this.isSwitchToEnabled;
        int i9 = z5;
        if (z5 != 0) {
            i9 = 1;
        }
        int i10 = (i8 + i9) * 31;
        boolean z6 = this.isAddSupervisedUser;
        int i11 = z6;
        if (z6 != 0) {
            i11 = 1;
        }
        int i12 = (i10 + i11) * 31;
        RestrictedLockUtils.EnforcedAdmin enforcedAdmin = this.enforcedAdmin;
        int hashCode3 = (i12 + (enforcedAdmin != null ? enforcedAdmin.hashCode() : 0)) * 31;
        boolean z7 = this.isManageUsers;
        return hashCode3 + (z7 ? 1 : z7 ? 1 : 0);
    }

    public final int resolveId() {
        UserInfo userInfo;
        if (this.isGuest || (userInfo = this.info) == null) {
            return -10000;
        }
        return userInfo.id;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("UserRecord(info=");
        sb.append(this.info);
        sb.append(", picture=");
        sb.append(this.picture);
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
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m6m(sb, this.isManageUsers, ")");
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
