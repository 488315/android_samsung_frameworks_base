package android.app.admin;

import com.android.server.LocalServices;

/* loaded from: classes.dex */
public abstract class DeviceStateCache {
    public boolean hasAffiliationWithDevice(int i) {
        return false;
    }

    public abstract boolean isDeviceProvisioned();

    public abstract boolean isUserOrganizationManaged(int i);

    protected DeviceStateCache() {
    }

    public static DeviceStateCache getInstance() {
        DevicePolicyManagerInternal devicePolicyManagerInternal = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
        return devicePolicyManagerInternal != null ? devicePolicyManagerInternal.getDeviceStateCache() : EmptyDeviceStateCache.INSTANCE;
    }

    private static class EmptyDeviceStateCache extends DeviceStateCache {
        private static final EmptyDeviceStateCache INSTANCE = new EmptyDeviceStateCache();

        @Override // android.app.admin.DeviceStateCache
        public boolean isDeviceProvisioned() {
            return false;
        }

        @Override // android.app.admin.DeviceStateCache
        public boolean isUserOrganizationManaged(int i) {
            return false;
        }

        private EmptyDeviceStateCache() {
        }
    }
}
