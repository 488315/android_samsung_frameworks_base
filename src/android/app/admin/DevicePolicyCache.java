package android.app.admin;

import com.android.server.LocalServices;
import java.util.Collections;
import java.util.Map;

/* loaded from: classes.dex */
public abstract class DevicePolicyCache {
    public abstract boolean canAdminGrantSensorsPermissions();

    public abstract int getContentProtectionPolicy(int i);

    public abstract Map<String, String> getLauncherShortcutOverrides();

    public abstract int getPasswordQuality(int i);

    public abstract int getPermissionPolicy(int i);

    public abstract boolean isScreenCaptureAllowed(int i);

    protected DevicePolicyCache() {
    }

    public static DevicePolicyCache getInstance() {
        DevicePolicyManagerInternal devicePolicyManagerInternal = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
        return devicePolicyManagerInternal != null ? devicePolicyManagerInternal.getDevicePolicyCache() : EmptyDevicePolicyCache.INSTANCE;
    }

    private static class EmptyDevicePolicyCache extends DevicePolicyCache {
        private static final EmptyDevicePolicyCache INSTANCE = new EmptyDevicePolicyCache();

        @Override // android.app.admin.DevicePolicyCache
        public boolean canAdminGrantSensorsPermissions() {
            return false;
        }

        @Override // android.app.admin.DevicePolicyCache
        public int getContentProtectionPolicy(int i) {
            return 1;
        }

        @Override // android.app.admin.DevicePolicyCache
        public int getPasswordQuality(int i) {
            return 0;
        }

        @Override // android.app.admin.DevicePolicyCache
        public int getPermissionPolicy(int i) {
            return 0;
        }

        @Override // android.app.admin.DevicePolicyCache
        public boolean isScreenCaptureAllowed(int i) {
            return true;
        }

        private EmptyDevicePolicyCache() {
        }

        @Override // android.app.admin.DevicePolicyCache
        public Map<String, String> getLauncherShortcutOverrides() {
            return Collections.EMPTY_MAP;
        }
    }
}
