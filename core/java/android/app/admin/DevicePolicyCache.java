package android.app.admin;

import com.android.server.LocalServices;
import java.util.Collections;
import java.util.Map;

/* loaded from: classes.dex */
public abstract class DevicePolicyCache {
    public abstract boolean canAdminGrantSensorsPermissions();

    public abstract Map<String, String> getLauncherShortcutOverrides();

    public abstract int getPasswordQuality(int i);

    public abstract int getPermissionPolicy(int i);

    public abstract boolean isScreenCaptureAllowed(int i);

    protected DevicePolicyCache() {
    }

    public static DevicePolicyCache getInstance() {
        DevicePolicyManagerInternal dpmi = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
        return dpmi != null ? dpmi.getDevicePolicyCache() : EmptyDevicePolicyCache.INSTANCE;
    }

    private static class EmptyDevicePolicyCache extends DevicePolicyCache {
        private static final EmptyDevicePolicyCache INSTANCE = new EmptyDevicePolicyCache();

        private EmptyDevicePolicyCache() {
        }

        @Override // android.app.admin.DevicePolicyCache
        public boolean isScreenCaptureAllowed(int userHandle) {
            return true;
        }

        @Override // android.app.admin.DevicePolicyCache
        public int getPasswordQuality(int userHandle) {
            return 0;
        }

        @Override // android.app.admin.DevicePolicyCache
        public int getPermissionPolicy(int userHandle) {
            return 0;
        }

        @Override // android.app.admin.DevicePolicyCache
        public boolean canAdminGrantSensorsPermissions() {
            return false;
        }

        @Override // android.app.admin.DevicePolicyCache
        public Map<String, String> getLauncherShortcutOverrides() {
            return Collections.EMPTY_MAP;
        }
    }
}
