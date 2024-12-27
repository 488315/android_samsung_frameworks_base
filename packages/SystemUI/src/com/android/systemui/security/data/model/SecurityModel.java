package com.android.systemui.security.data.model;

import android.app.admin.DeviceAdminInfo;
import android.graphics.drawable.Drawable;
import android.os.UserHandle;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.policy.SecurityController;
import com.android.systemui.statusbar.policy.SecurityControllerImpl;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public static Object create(SecurityController securityController, CoroutineDispatcher coroutineDispatcher, Continuation continuation) {
            return BuildersKt.withContext(coroutineDispatcher, new SecurityModel$Companion$create$2(securityController, null), continuation);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final SecurityModel create(SecurityController securityController) {
            SecurityControllerImpl securityControllerImpl = (SecurityControllerImpl) securityController;
            DeviceAdminInfo deviceAdminInfo = securityControllerImpl.isParentalControlsEnabled() ? securityControllerImpl.getDeviceAdminInfo() : null;
            boolean isDeviceManaged = securityControllerImpl.mDevicePolicyManager.isDeviceManaged();
            boolean hasWorkProfile$1 = securityControllerImpl.hasWorkProfile$1();
            UserHandle of = UserHandle.of(securityControllerImpl.getWorkProfileUserId$1(securityControllerImpl.mCurrentUserId));
            boolean z = (of == null || securityControllerImpl.mUserManager.isQuietModeEnabled(of)) ? false : true;
            boolean isOrganizationOwnedDeviceWithManagedProfile = securityControllerImpl.mDevicePolicyManager.isOrganizationOwnedDeviceWithManagedProfile();
            CharSequence deviceOwnerOrganizationName = securityControllerImpl.mDevicePolicyManager.getDeviceOwnerOrganizationName();
            String obj = deviceOwnerOrganizationName != null ? deviceOwnerOrganizationName.toString() : null;
            int workProfileUserId$1 = securityControllerImpl.getWorkProfileUserId$1(securityControllerImpl.mCurrentUserId);
            CharSequence organizationNameForUser = workProfileUserId$1 == -10000 ? null : securityControllerImpl.mDevicePolicyManager.getOrganizationNameForUser(workProfileUserId$1);
            return new SecurityModel(isDeviceManaged, hasWorkProfile$1, z, isOrganizationOwnedDeviceWithManagedProfile, obj, organizationNameForUser != null ? organizationNameForUser.toString() : null, securityControllerImpl.mDevicePolicyManager.isNetworkLoggingEnabled(null), securityControllerImpl.isVpnBranded(), securityControllerImpl.getPrimaryVpnName(), securityControllerImpl.getWorkProfileVpnName(), securityControllerImpl.hasCACertInCurrentUser(), securityControllerImpl.hasCACertInWorkProfile(), securityControllerImpl.isParentalControlsEnabled(), deviceAdminInfo != null ? deviceAdminInfo.loadIcon(securityControllerImpl.mPackageManager) : null);
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

    public final int hashCode() {
        int m = TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(Boolean.hashCode(this.isDeviceManaged) * 31, 31, this.hasWorkProfile), 31, this.isWorkProfileOn), 31, this.isProfileOwnerOfOrganizationOwnedDevice);
        String str = this.deviceOwnerOrganizationName;
        int hashCode = (m + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.workProfileOrganizationName;
        int m2 = TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m((hashCode + (str2 == null ? 0 : str2.hashCode())) * 31, 31, this.isNetworkLoggingEnabled), 31, this.isVpnBranded);
        String str3 = this.primaryVpnName;
        int hashCode2 = (m2 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.workProfileVpnName;
        int m3 = TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m((hashCode2 + (str4 == null ? 0 : str4.hashCode())) * 31, 31, this.hasCACertInCurrentUser), 31, this.hasCACertInWorkProfile), 31, this.isParentalControlsEnabled);
        Drawable drawable = this.deviceAdminIcon;
        return m3 + (drawable != null ? drawable.hashCode() : 0);
    }

    public final String toString() {
        return "SecurityModel(isDeviceManaged=" + this.isDeviceManaged + ", hasWorkProfile=" + this.hasWorkProfile + ", isWorkProfileOn=" + this.isWorkProfileOn + ", isProfileOwnerOfOrganizationOwnedDevice=" + this.isProfileOwnerOfOrganizationOwnedDevice + ", deviceOwnerOrganizationName=" + this.deviceOwnerOrganizationName + ", workProfileOrganizationName=" + this.workProfileOrganizationName + ", isNetworkLoggingEnabled=" + this.isNetworkLoggingEnabled + ", isVpnBranded=" + this.isVpnBranded + ", primaryVpnName=" + this.primaryVpnName + ", workProfileVpnName=" + this.workProfileVpnName + ", hasCACertInCurrentUser=" + this.hasCACertInCurrentUser + ", hasCACertInWorkProfile=" + this.hasCACertInWorkProfile + ", isParentalControlsEnabled=" + this.isParentalControlsEnabled + ", deviceAdminIcon=" + this.deviceAdminIcon + ")";
    }
}
