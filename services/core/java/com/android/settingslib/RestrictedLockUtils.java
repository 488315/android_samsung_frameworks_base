package com.android.settingslib;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.UserHandle;

import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class RestrictedLockUtils {

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class EnforcedAdmin {
        public static final EnforcedAdmin MULTIPLE_ENFORCED_ADMIN;
        public ComponentName component;
        public String enforcedRestriction = null;
        public UserHandle user;

        static {
            EnforcedAdmin enforcedAdmin = new EnforcedAdmin();
            enforcedAdmin.component = null;
            enforcedAdmin.enforcedRestriction = null;
            enforcedAdmin.user = null;
            MULTIPLE_ENFORCED_ADMIN = enforcedAdmin;
        }

        public EnforcedAdmin(ComponentName componentName, UserHandle userHandle) {
            this.component = componentName;
            this.user = userHandle;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || EnforcedAdmin.class != obj.getClass()) {
                return false;
            }
            EnforcedAdmin enforcedAdmin = (EnforcedAdmin) obj;
            return Objects.equals(this.user, enforcedAdmin.user)
                    && Objects.equals(this.component, enforcedAdmin.component)
                    && Objects.equals(this.enforcedRestriction, enforcedAdmin.enforcedRestriction);
        }

        public final int hashCode() {
            return Objects.hash(this.component, this.enforcedRestriction, this.user);
        }

        public final String toString() {
            return "EnforcedAdmin{component="
                    + this.component
                    + ", enforcedRestriction='"
                    + this.enforcedRestriction
                    + ", user="
                    + this.user
                    + '}';
        }
    }

    public static EnforcedAdmin getProfileOrDeviceOwner(Context context, UserHandle userHandle) {
        DevicePolicyManager devicePolicyManager;
        ComponentName deviceOwnerComponentOnAnyUser;
        if (userHandle == null
                || (devicePolicyManager =
                                (DevicePolicyManager) context.getSystemService("device_policy"))
                        == null) {
            return null;
        }
        try {
            ComponentName profileOwner =
                    ((DevicePolicyManager)
                                    context.createPackageContextAsUser(
                                                    context.getPackageName(), 0, userHandle)
                                            .getSystemService(DevicePolicyManager.class))
                            .getProfileOwner();
            if (profileOwner != null) {
                return new EnforcedAdmin(profileOwner, userHandle);
            }
            if (!Objects.equals(devicePolicyManager.getDeviceOwnerUser(), userHandle)
                    || (deviceOwnerComponentOnAnyUser =
                                    devicePolicyManager.getDeviceOwnerComponentOnAnyUser())
                            == null) {
                return null;
            }
            return new EnforcedAdmin(deviceOwnerComponentOnAnyUser, userHandle);
        } catch (PackageManager.NameNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }
}
