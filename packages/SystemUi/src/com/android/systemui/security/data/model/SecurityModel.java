package com.android.systemui.security.data.model;

import android.app.admin.DeviceAdminInfo;
import android.app.admin.DevicePolicyManager;
import android.graphics.drawable.Drawable;
import android.os.UserHandle;
import com.android.systemui.statusbar.policy.SecurityController;
import com.android.systemui.statusbar.policy.SecurityControllerImpl;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SecurityModel {
    public static final Companion Companion = new Companion(null);
    public final Drawable deviceAdminIcon;
    public final String deviceOwnerOrganizationName;
    public final boolean hasCACertInCurrentUser;
    public final boolean hasCACertInWorkProfile;
    public final boolean hasWorkProfile;
    public final boolean isDeviceManaged;
    public final boolean isNetworkLoggingEnabled;
    public final boolean isParentalControlsEnabled;
    public final boolean isProfileOwnerOfOrganizationOwnedDevice;
    public final boolean isVpnBranded;
    public final boolean isWorkProfileOn;
    public final String primaryVpnName;
    public final String workProfileOrganizationName;
    public final String workProfileVpnName;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static Object create(SecurityController securityController, CoroutineDispatcher coroutineDispatcher, Continuation continuation) {
            return BuildersKt.withContext(coroutineDispatcher, new SecurityModel$Companion$create$2(securityController, null), continuation);
        }

        public final SecurityModel create(SecurityController securityController) {
            SecurityControllerImpl securityControllerImpl = (SecurityControllerImpl) securityController;
            DeviceAdminInfo deviceAdminInfo = securityControllerImpl.isParentalControlsEnabled() ? securityControllerImpl.getDeviceAdminInfo() : null;
            boolean isDeviceManaged = securityControllerImpl.isDeviceManaged();
            boolean hasWorkProfile = securityControllerImpl.hasWorkProfile();
            UserHandle of = UserHandle.of(securityControllerImpl.getWorkProfileUserId(securityControllerImpl.mCurrentUserId));
            boolean z = (of == null || securityControllerImpl.mUserManager.isQuietModeEnabled(of)) ? false : true;
            DevicePolicyManager devicePolicyManager = securityControllerImpl.mDevicePolicyManager;
            boolean isOrganizationOwnedDeviceWithManagedProfile = devicePolicyManager.isOrganizationOwnedDeviceWithManagedProfile();
            CharSequence deviceOwnerOrganizationName = devicePolicyManager.getDeviceOwnerOrganizationName();
            String obj = deviceOwnerOrganizationName != null ? deviceOwnerOrganizationName.toString() : null;
            int workProfileUserId = securityControllerImpl.getWorkProfileUserId(securityControllerImpl.mCurrentUserId);
            CharSequence organizationNameForUser = workProfileUserId == -10000 ? null : devicePolicyManager.getOrganizationNameForUser(workProfileUserId);
            return new SecurityModel(isDeviceManaged, hasWorkProfile, z, isOrganizationOwnedDeviceWithManagedProfile, obj, organizationNameForUser != null ? organizationNameForUser.toString() : null, devicePolicyManager.isNetworkLoggingEnabled(null), securityControllerImpl.isVpnBranded(), securityControllerImpl.getPrimaryVpnName(), securityControllerImpl.getWorkProfileVpnName(), securityControllerImpl.hasCACertInCurrentUser(), securityControllerImpl.hasCACertInWorkProfile(), securityControllerImpl.isParentalControlsEnabled(), deviceAdminInfo != null ? deviceAdminInfo.loadIcon(securityControllerImpl.mPackageManager) : null);
        }
    }

    public SecurityModel(boolean z, boolean z2, boolean z3, boolean z4, String str, String str2, boolean z5, boolean z6, String str3, String str4, boolean z7, boolean z8, boolean z9, Drawable drawable) {
        this.isDeviceManaged = z;
        this.hasWorkProfile = z2;
        this.isWorkProfileOn = z3;
        this.isProfileOwnerOfOrganizationOwnedDevice = z4;
        this.deviceOwnerOrganizationName = str;
        this.workProfileOrganizationName = str2;
        this.isNetworkLoggingEnabled = z5;
        this.isVpnBranded = z6;
        this.primaryVpnName = str3;
        this.workProfileVpnName = str4;
        this.hasCACertInCurrentUser = z7;
        this.hasCACertInWorkProfile = z8;
        this.isParentalControlsEnabled = z9;
        this.deviceAdminIcon = drawable;
    }

    public static final SecurityModel create(SecurityController securityController) {
        return Companion.create(securityController);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SecurityModel)) {
            return false;
        }
        SecurityModel securityModel = (SecurityModel) obj;
        return this.isDeviceManaged == securityModel.isDeviceManaged && this.hasWorkProfile == securityModel.hasWorkProfile && this.isWorkProfileOn == securityModel.isWorkProfileOn && this.isProfileOwnerOfOrganizationOwnedDevice == securityModel.isProfileOwnerOfOrganizationOwnedDevice && Intrinsics.areEqual(this.deviceOwnerOrganizationName, securityModel.deviceOwnerOrganizationName) && Intrinsics.areEqual(this.workProfileOrganizationName, securityModel.workProfileOrganizationName) && this.isNetworkLoggingEnabled == securityModel.isNetworkLoggingEnabled && this.isVpnBranded == securityModel.isVpnBranded && Intrinsics.areEqual(this.primaryVpnName, securityModel.primaryVpnName) && Intrinsics.areEqual(this.workProfileVpnName, securityModel.workProfileVpnName) && this.hasCACertInCurrentUser == securityModel.hasCACertInCurrentUser && this.hasCACertInWorkProfile == securityModel.hasCACertInWorkProfile && this.isParentalControlsEnabled == securityModel.isParentalControlsEnabled && Intrinsics.areEqual(this.deviceAdminIcon, securityModel.deviceAdminIcon);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        boolean z = this.isDeviceManaged;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        int i2 = i * 31;
        boolean z2 = this.hasWorkProfile;
        int i3 = z2;
        if (z2 != 0) {
            i3 = 1;
        }
        int i4 = (i2 + i3) * 31;
        boolean z3 = this.isWorkProfileOn;
        int i5 = z3;
        if (z3 != 0) {
            i5 = 1;
        }
        int i6 = (i4 + i5) * 31;
        boolean z4 = this.isProfileOwnerOfOrganizationOwnedDevice;
        int i7 = z4;
        if (z4 != 0) {
            i7 = 1;
        }
        int i8 = (i6 + i7) * 31;
        String str = this.deviceOwnerOrganizationName;
        int hashCode = (i8 + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.workProfileOrganizationName;
        int hashCode2 = (hashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        boolean z5 = this.isNetworkLoggingEnabled;
        int i9 = z5;
        if (z5 != 0) {
            i9 = 1;
        }
        int i10 = (hashCode2 + i9) * 31;
        boolean z6 = this.isVpnBranded;
        int i11 = z6;
        if (z6 != 0) {
            i11 = 1;
        }
        int i12 = (i10 + i11) * 31;
        String str3 = this.primaryVpnName;
        int hashCode3 = (i12 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.workProfileVpnName;
        int hashCode4 = (hashCode3 + (str4 == null ? 0 : str4.hashCode())) * 31;
        boolean z7 = this.hasCACertInCurrentUser;
        int i13 = z7;
        if (z7 != 0) {
            i13 = 1;
        }
        int i14 = (hashCode4 + i13) * 31;
        boolean z8 = this.hasCACertInWorkProfile;
        int i15 = z8;
        if (z8 != 0) {
            i15 = 1;
        }
        int i16 = (i14 + i15) * 31;
        boolean z9 = this.isParentalControlsEnabled;
        int i17 = (i16 + (z9 ? 1 : z9 ? 1 : 0)) * 31;
        Drawable drawable = this.deviceAdminIcon;
        return i17 + (drawable != null ? drawable.hashCode() : 0);
    }

    public final String toString() {
        return "SecurityModel(isDeviceManaged=" + this.isDeviceManaged + ", hasWorkProfile=" + this.hasWorkProfile + ", isWorkProfileOn=" + this.isWorkProfileOn + ", isProfileOwnerOfOrganizationOwnedDevice=" + this.isProfileOwnerOfOrganizationOwnedDevice + ", deviceOwnerOrganizationName=" + this.deviceOwnerOrganizationName + ", workProfileOrganizationName=" + this.workProfileOrganizationName + ", isNetworkLoggingEnabled=" + this.isNetworkLoggingEnabled + ", isVpnBranded=" + this.isVpnBranded + ", primaryVpnName=" + this.primaryVpnName + ", workProfileVpnName=" + this.workProfileVpnName + ", hasCACertInCurrentUser=" + this.hasCACertInCurrentUser + ", hasCACertInWorkProfile=" + this.hasCACertInWorkProfile + ", isParentalControlsEnabled=" + this.isParentalControlsEnabled + ", deviceAdminIcon=" + this.deviceAdminIcon + ")";
    }
}
