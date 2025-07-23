package android.app;

import android.Manifest;
import android.app.compat.CompatChanges;
import android.app.role.RoleManager;
import android.companion.virtual.VirtualDevice;
import android.companion.virtual.VirtualDeviceManager;
import android.compat.Compatibility;
import android.content.AttributionSource;
import android.content.Context;
import android.content.PermissionChecker;
import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.permission.PermissionManager;
import android.provider.DeviceConfig;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.SparseArray;
import com.android.internal.compat.CompatibilityChangeConfig;
import com.android.internal.compat.IPlatformCompat;
import com.android.internal.util.ArrayUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/* loaded from: classes.dex */
public abstract class ForegroundServiceTypePolicy {
    static final boolean DEBUG_FOREGROUND_SERVICE_TYPE_POLICY = false;
    private static final boolean DEFAULT_FGS_TYPE_FG_PERM_ENFORCEMENT_FLAG_VALUE = true;
    private static final String FGS_TYPE_FG_PERM_ENFORCEMENT_FLAG = "fgs_type_fg_perm_enforcement_flag";
    public static final long FGS_TYPE_PERMISSION_CHANGE_ID = 254662522;
    private static final String FGS_TYPE_PERM_ENFORCEMENT_FLAG_PREFIX = "fgs_type_perm_enforcement_flag_";
    public static final int FGS_TYPE_POLICY_CHECK_DEPRECATED = 2;
    public static final int FGS_TYPE_POLICY_CHECK_DISABLED = 3;
    public static final int FGS_TYPE_POLICY_CHECK_OK = 1;
    public static final int FGS_TYPE_POLICY_CHECK_PERMISSION_DENIED_ENFORCED = 5;
    public static final int FGS_TYPE_POLICY_CHECK_PERMISSION_DENIED_PERMISSIVE = 4;
    public static final int FGS_TYPE_POLICY_CHECK_UNKNOWN = 0;
    static final String TAG = "ForegroundServiceTypePolicy";
    public static final long FGS_TYPE_NONE_DEPRECATION_CHANGE_ID = 255042465;
    public static final long FGS_TYPE_NONE_DISABLED_CHANGE_ID = 255038118;
    public static final ForegroundServiceTypePolicyInfo FGS_TYPE_POLICY_MANIFEST = new ForegroundServiceTypePolicyInfo(-1, FGS_TYPE_NONE_DEPRECATION_CHANGE_ID, FGS_TYPE_NONE_DISABLED_CHANGE_ID, null, null, null, false, false);
    public static final ForegroundServiceTypePolicyInfo FGS_TYPE_POLICY_NONE = new ForegroundServiceTypePolicyInfo(0, FGS_TYPE_NONE_DEPRECATION_CHANGE_ID, FGS_TYPE_NONE_DISABLED_CHANGE_ID, null, null, null, false, false);
    public static final long FGS_TYPE_DATA_SYNC_DEPRECATION_CHANGE_ID = 255039210;
    public static final long FGS_TYPE_DATA_SYNC_DISABLED_CHANGE_ID = 255659651;
    private static final String FGS_TYPE_PERM_ENFORCEMENT_FLAG_DATA_SYNC = "fgs_type_perm_enforcement_flag_data_sync";
    public static final ForegroundServiceTypePolicyInfo FGS_TYPE_POLICY_DATA_SYNC = new ForegroundServiceTypePolicyInfo(1, FGS_TYPE_DATA_SYNC_DEPRECATION_CHANGE_ID, FGS_TYPE_DATA_SYNC_DISABLED_CHANGE_ID, new ForegroundServiceTypePermissions(new ForegroundServiceTypePermission[]{new RegularPermission(Manifest.permission.FOREGROUND_SERVICE_DATA_SYNC)}, true), null, FGS_TYPE_PERM_ENFORCEMENT_FLAG_DATA_SYNC, true, false);
    private static final String FGS_TYPE_PERM_ENFORCEMENT_FLAG_MEDIA_PLAYBACK = "fgs_type_perm_enforcement_flag_media_playback";
    public static final ForegroundServiceTypePolicyInfo FGS_TYPE_POLICY_MEDIA_PLAYBACK = new ForegroundServiceTypePolicyInfo(2, 0, 0, new ForegroundServiceTypePermissions(new ForegroundServiceTypePermission[]{new RegularPermission(Manifest.permission.FOREGROUND_SERVICE_MEDIA_PLAYBACK)}, true), null, FGS_TYPE_PERM_ENFORCEMENT_FLAG_MEDIA_PLAYBACK, true, false);
    private static final String FGS_TYPE_PERM_ENFORCEMENT_FLAG_PHONE_CALL = "fgs_type_perm_enforcement_flag_phone_call";
    public static final ForegroundServiceTypePolicyInfo FGS_TYPE_POLICY_PHONE_CALL = new ForegroundServiceTypePolicyInfo(4, 0, 0, new ForegroundServiceTypePermissions(new ForegroundServiceTypePermission[]{new RegularPermission(Manifest.permission.FOREGROUND_SERVICE_PHONE_CALL)}, true), new ForegroundServiceTypePermissions(new ForegroundServiceTypePermission[]{new RegularPermission(Manifest.permission.MANAGE_OWN_CALLS), new RolePermission("android.app.role.DIALER")}, false), FGS_TYPE_PERM_ENFORCEMENT_FLAG_PHONE_CALL, true, false);
    private static final String FGS_TYPE_PERM_ENFORCEMENT_FLAG_LOCATION = "fgs_type_perm_enforcement_flag_location";
    public static final ForegroundServiceTypePolicyInfo FGS_TYPE_POLICY_LOCATION = new ForegroundServiceTypePolicyInfo(8, 0, 0, new ForegroundServiceTypePermissions(new ForegroundServiceTypePermission[]{new RegularPermission(Manifest.permission.FOREGROUND_SERVICE_LOCATION)}, true), new ForegroundServiceTypePermissions(new ForegroundServiceTypePermission[]{new RegularPermission(Manifest.permission.ACCESS_COARSE_LOCATION), new RegularPermission(Manifest.permission.ACCESS_FINE_LOCATION)}, false), FGS_TYPE_PERM_ENFORCEMENT_FLAG_LOCATION, true, true);
    private static final String FGS_TYPE_PERM_ENFORCEMENT_FLAG_CONNECTED_DEVICE = "fgs_type_perm_enforcement_flag_connected_device";
    public static final ForegroundServiceTypePolicyInfo FGS_TYPE_POLICY_CONNECTED_DEVICE = new ForegroundServiceTypePolicyInfo(16, 0, 0, new ForegroundServiceTypePermissions(new ForegroundServiceTypePermission[]{new RegularPermission(Manifest.permission.FOREGROUND_SERVICE_CONNECTED_DEVICE)}, true), new ForegroundServiceTypePermissions(new ForegroundServiceTypePermission[]{new RegularPermission(Manifest.permission.BLUETOOTH_ADVERTISE), new RegularPermission(Manifest.permission.BLUETOOTH_CONNECT), new RegularPermission(Manifest.permission.BLUETOOTH_SCAN), new RegularPermission(Manifest.permission.CHANGE_NETWORK_STATE), new RegularPermission(Manifest.permission.CHANGE_WIFI_STATE), new RegularPermission(Manifest.permission.CHANGE_WIFI_MULTICAST_STATE), new RegularPermission(Manifest.permission.NFC), new RegularPermission(Manifest.permission.TRANSMIT_IR), new RegularPermission(Manifest.permission.UWB_RANGING), new RegularPermission(Manifest.permission.RANGING), new UsbDevicePermission(), new UsbAccessoryPermission()}, false), FGS_TYPE_PERM_ENFORCEMENT_FLAG_CONNECTED_DEVICE, true, false);
    private static final String FGS_TYPE_PERM_ENFORCEMENT_FLAG_MEDIA_PROJECTION = "fgs_type_perm_enforcement_flag_media_projection";
    public static final ForegroundServiceTypePolicyInfo FGS_TYPE_POLICY_MEDIA_PROJECTION = new ForegroundServiceTypePolicyInfo(32, 0, 0, new ForegroundServiceTypePermissions(new ForegroundServiceTypePermission[]{new RegularPermission(Manifest.permission.FOREGROUND_SERVICE_MEDIA_PROJECTION)}, true), new ForegroundServiceTypePermissions(new ForegroundServiceTypePermission[]{new RegularPermission(Manifest.permission.CAPTURE_VIDEO_OUTPUT), new AppOpPermission(46)}, false), FGS_TYPE_PERM_ENFORCEMENT_FLAG_MEDIA_PROJECTION, true, false);
    private static final String FGS_TYPE_PERM_ENFORCEMENT_FLAG_CAMERA = "fgs_type_perm_enforcement_flag_camera";
    public static final ForegroundServiceTypePolicyInfo FGS_TYPE_POLICY_CAMERA = new ForegroundServiceTypePolicyInfo(64, 0, 0, new ForegroundServiceTypePermissions(new ForegroundServiceTypePermission[]{new RegularPermission(Manifest.permission.FOREGROUND_SERVICE_CAMERA)}, true), new ForegroundServiceTypePermissions(new ForegroundServiceTypePermission[]{new RegularPermission(Manifest.permission.CAMERA), new RegularPermission(Manifest.permission.SYSTEM_CAMERA)}, false), FGS_TYPE_PERM_ENFORCEMENT_FLAG_CAMERA, true, true);
    private static final String FGS_TYPE_PERM_ENFORCEMENT_FLAG_MICROPHONE = "fgs_type_perm_enforcement_flag_microphone";
    public static final ForegroundServiceTypePolicyInfo FGS_TYPE_POLICY_MICROPHONE = new ForegroundServiceTypePolicyInfo(128, 0, 0, new ForegroundServiceTypePermissions(new ForegroundServiceTypePermission[]{new RegularPermission(Manifest.permission.FOREGROUND_SERVICE_MICROPHONE)}, true), new ForegroundServiceTypePermissions(new ForegroundServiceTypePermission[]{new RegularPermission(Manifest.permission.CAPTURE_AUDIO_HOTWORD), new RegularPermission(Manifest.permission.CAPTURE_AUDIO_OUTPUT), new RegularPermission(Manifest.permission.CAPTURE_MEDIA_OUTPUT), new RegularPermission(Manifest.permission.CAPTURE_TUNER_AUDIO_INPUT), new RegularPermission(Manifest.permission.CAPTURE_VOICE_COMMUNICATION_OUTPUT), new RegularPermission(Manifest.permission.RECORD_AUDIO)}, false), FGS_TYPE_PERM_ENFORCEMENT_FLAG_MICROPHONE, true, true);
    private static final String FGS_TYPE_PERM_ENFORCEMENT_FLAG_HEALTH = "fgs_type_perm_enforcement_flag_health";
    public static final ForegroundServiceTypePolicyInfo FGS_TYPE_POLICY_HEALTH = new ForegroundServiceTypePolicyInfo(256, 0, 0, new ForegroundServiceTypePermissions(new ForegroundServiceTypePermission[]{new RegularPermission(Manifest.permission.FOREGROUND_SERVICE_HEALTH)}, true), new ForegroundServiceTypePermissions(getAllowedHealthPermissions(), false), FGS_TYPE_PERM_ENFORCEMENT_FLAG_HEALTH, true, false);
    private static final String FGS_TYPE_PERM_ENFORCEMENT_FLAG_REMOTE_MESSAGING = "fgs_type_perm_enforcement_flag_remote_messaging";
    public static final ForegroundServiceTypePolicyInfo FGS_TYPE_POLICY_REMOTE_MESSAGING = new ForegroundServiceTypePolicyInfo(512, 0, 0, new ForegroundServiceTypePermissions(new ForegroundServiceTypePermission[]{new RegularPermission(Manifest.permission.FOREGROUND_SERVICE_REMOTE_MESSAGING)}, true), null, FGS_TYPE_PERM_ENFORCEMENT_FLAG_REMOTE_MESSAGING, true, false);
    private static final String FGS_TYPE_PERM_ENFORCEMENT_FLAG_SYSTEM_EXEMPTED = "fgs_type_perm_enforcement_flag_system_exempted";
    public static final ForegroundServiceTypePolicyInfo FGS_TYPE_POLICY_SYSTEM_EXEMPTED = new ForegroundServiceTypePolicyInfo(1024, 0, 0, new ForegroundServiceTypePermissions(new ForegroundServiceTypePermission[]{new RegularPermission(Manifest.permission.FOREGROUND_SERVICE_SYSTEM_EXEMPTED)}, true), new ForegroundServiceTypePermissions(new ForegroundServiceTypePermission[]{new RegularPermission(Manifest.permission.SCHEDULE_EXACT_ALARM), new RegularPermission(Manifest.permission.USE_EXACT_ALARM), new AppOpPermission(47)}, false), FGS_TYPE_PERM_ENFORCEMENT_FLAG_SYSTEM_EXEMPTED, true, false);
    public static final ForegroundServiceTypePolicyInfo FGS_TYPE_POLICY_SHORT_SERVICE = new ForegroundServiceTypePolicyInfo(2048, 0, 0, null, null, null, false, false);
    public static final ForegroundServiceTypePolicyInfo FGS_TYPE_POLICY_FILE_MANAGEMENT = new ForegroundServiceTypePolicyInfo(4096, 0, 0, new ForegroundServiceTypePermissions(new ForegroundServiceTypePermission[]{new RegularPermission(Manifest.permission.FOREGROUND_SERVICE_FILE_MANAGEMENT)}, true), null, null, false, false);
    public static final ForegroundServiceTypePolicyInfo FGS_TYPE_POLICY_MEDIA_PROCESSING = new ForegroundServiceTypePolicyInfo(8192, 0, 0, new ForegroundServiceTypePermissions(new ForegroundServiceTypePermission[]{new RegularPermission(Manifest.permission.FOREGROUND_SERVICE_MEDIA_PROCESSING)}, true), null, null, true, false);
    private static final String FGS_TYPE_PERM_ENFORCEMENT_FLAG_SPECIAL_USE = "fgs_type_perm_enforcement_flag_special_use";
    public static final ForegroundServiceTypePolicyInfo FGS_TYPE_POLICY_SPECIAL_USE = new ForegroundServiceTypePolicyInfo(1073741824, 0, 0, new ForegroundServiceTypePermissions(new ForegroundServiceTypePermission[]{new RegularPermission(Manifest.permission.FOREGROUND_SERVICE_SPECIAL_USE)}, true), null, FGS_TYPE_PERM_ENFORCEMENT_FLAG_SPECIAL_USE, true, false);
    private static ForegroundServiceTypePolicy sDefaultForegroundServiceTypePolicy = null;
    private static final Object sLock = new Object();

    @Retention(RetentionPolicy.SOURCE)
    public @interface ForegroundServicePolicyCheckCode {
    }

    public abstract int checkForegroundServiceTypePolicy(Context context, String str, int i, int i2, boolean z, ForegroundServiceTypePolicyInfo foregroundServiceTypePolicyInfo);

    public abstract ForegroundServiceTypePolicyInfo getForegroundServiceTypePolicyInfo(int i, int i2);

    public abstract void updatePermissionEnforcementFlagIfNecessary(String str);

    private static ForegroundServiceTypePermission[] getAllowedHealthPermissions() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new RegularPermission(Manifest.permission.ACTIVITY_RECOGNITION));
        arrayList.add(new RegularPermission(Manifest.permission.HIGH_SAMPLING_RATE_SENSORS));
        if (com.android.internal.hidden_from_bootclasspath.android.permission.flags.Flags.replaceBodySensorPermissionEnabled()) {
            arrayList.add(new RegularPermission("android.permission.health.READ_HEART_RATE"));
            arrayList.add(new RegularPermission("android.permission.health.READ_SKIN_TEMPERATURE"));
            arrayList.add(new RegularPermission("android.permission.health.READ_OXYGEN_SATURATION"));
        } else {
            arrayList.add(new RegularPermission(Manifest.permission.BODY_SENSORS));
        }
        return (ForegroundServiceTypePermission[]) arrayList.toArray(new ForegroundServiceTypePermission[arrayList.size()]);
    }

    public static ForegroundServiceTypePolicy getDefaultPolicy() {
        ForegroundServiceTypePolicy foregroundServiceTypePolicy;
        synchronized (sLock) {
            if (sDefaultForegroundServiceTypePolicy == null) {
                sDefaultForegroundServiceTypePolicy = new DefaultForegroundServiceTypePolicy();
            }
            foregroundServiceTypePolicy = sDefaultForegroundServiceTypePolicy;
        }
        return foregroundServiceTypePolicy;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isFgsTypeFgPermissionEnforcementEnabled() {
        return DeviceConfig.getBoolean("activity_manager", FGS_TYPE_FG_PERM_ENFORCEMENT_FLAG, true);
    }

    public static final class ForegroundServiceTypePolicyInfo {
        private static final long INVALID_CHANGE_ID = 0;
        final ForegroundServiceTypePermissions mAllOfPermissions;
        final ForegroundServiceTypePermissions mAnyOfPermissions;
        ForegroundServiceTypePermission mCustomPermission;
        final long mDeprecationChangeId;
        final long mDisabledChangeId;
        final boolean mForegroundOnlyPermission;
        final String mPermissionEnforcementFlag;
        final boolean mPermissionEnforcementFlagDefaultValue;
        volatile boolean mPermissionEnforcementFlagValue;
        final int mType;

        private static boolean isValidChangeId(long j) {
            return j != 0;
        }

        public ForegroundServiceTypePolicyInfo(int i, long j, long j2, ForegroundServiceTypePermissions foregroundServiceTypePermissions, ForegroundServiceTypePermissions foregroundServiceTypePermissions2, String str, boolean z, boolean z2) {
            this.mType = i;
            this.mDeprecationChangeId = j;
            this.mDisabledChangeId = j2;
            this.mAllOfPermissions = foregroundServiceTypePermissions;
            this.mAnyOfPermissions = foregroundServiceTypePermissions2;
            this.mPermissionEnforcementFlag = str;
            this.mPermissionEnforcementFlagDefaultValue = z;
            this.mPermissionEnforcementFlagValue = z;
            this.mForegroundOnlyPermission = z2;
        }

        public int getForegroundServiceType() {
            return this.mType;
        }

        public String toString() {
            StringBuilder permissionString = toPermissionString(new StringBuilder());
            permissionString.append("type=0x");
            permissionString.append(Integer.toHexString(this.mType));
            permissionString.append(" deprecationChangeId=");
            permissionString.append(this.mDeprecationChangeId);
            permissionString.append(" disabledChangeId=");
            permissionString.append(this.mDisabledChangeId);
            permissionString.append(" customPermission=");
            permissionString.append(this.mCustomPermission);
            return permissionString.toString();
        }

        public String toPermissionString() {
            return toPermissionString(new StringBuilder()).toString();
        }

        private StringBuilder toPermissionString(StringBuilder sb) {
            if (this.mAllOfPermissions != null) {
                sb.append("all of the permissions ");
                sb.append(this.mAllOfPermissions.toString());
                sb.append(' ');
            }
            if (this.mAnyOfPermissions != null) {
                sb.append("any of the permissions ");
                sb.append(this.mAnyOfPermissions.toString());
                sb.append(' ');
            }
            return sb;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updatePermissionEnforcementFlagIfNecessary(String str) {
            String str2 = this.mPermissionEnforcementFlag;
            if (str2 == null || !TextUtils.equals(str, str2)) {
                return;
            }
            this.mPermissionEnforcementFlagValue = DeviceConfig.getBoolean("activity_manager", this.mPermissionEnforcementFlag, this.mPermissionEnforcementFlagDefaultValue);
        }

        public void setCustomPermission(ForegroundServiceTypePermission foregroundServiceTypePermission) {
            this.mCustomPermission = foregroundServiceTypePermission;
        }

        public Optional<String[]> getRequiredAllOfPermissionsForTest(Context context) {
            ForegroundServiceTypePermissions foregroundServiceTypePermissions = this.mAllOfPermissions;
            if (foregroundServiceTypePermissions == null) {
                return Optional.empty();
            }
            return Optional.of(foregroundServiceTypePermissions.toStringArray(context));
        }

        public Optional<String[]> getRequiredAnyOfPermissionsForTest(Context context) {
            ForegroundServiceTypePermissions foregroundServiceTypePermissions = this.mAnyOfPermissions;
            if (foregroundServiceTypePermissions == null) {
                return Optional.empty();
            }
            return Optional.of(foregroundServiceTypePermissions.toStringArray(context));
        }

        public boolean isTypeDisabled(int i) {
            return isValidChangeId(this.mDisabledChangeId) && CompatChanges.isChangeEnabled(this.mDisabledChangeId, i);
        }

        public boolean hasForegroundOnlyPermission() {
            return this.mForegroundOnlyPermission;
        }

        public void setTypeDisabledForTest(boolean z, String str) throws RemoteException {
            overrideChangeIdForTest(this.mDisabledChangeId, z, str);
        }

        public void clearTypeDisabledForTest(String str) throws RemoteException {
            clearOverrideForTest(this.mDisabledChangeId, str);
        }

        boolean isTypeDeprecated(int i) {
            return isValidChangeId(this.mDeprecationChangeId) && CompatChanges.isChangeEnabled(this.mDeprecationChangeId, i);
        }

        private void overrideChangeIdForTest(long j, boolean z, String str) throws RemoteException {
            if (isValidChangeId(j)) {
                ArraySet arraySet = new ArraySet();
                ArraySet arraySet2 = new ArraySet();
                if (z) {
                    arraySet.add(Long.valueOf(j));
                } else {
                    arraySet2.add(Long.valueOf(j));
                }
                IPlatformCompat.Stub.asInterface(ServiceManager.getService(Context.PLATFORM_COMPAT_SERVICE)).setOverridesForTest(new CompatibilityChangeConfig(new Compatibility.ChangeConfig(arraySet, arraySet2)), str);
            }
        }

        private void clearOverrideForTest(long j, String str) throws RemoteException {
            IPlatformCompat.Stub.asInterface(ServiceManager.getService(Context.PLATFORM_COMPAT_SERVICE)).clearOverrideForTest(j, str);
        }

        public String getPermissionEnforcementFlagForTest() {
            return this.mPermissionEnforcementFlag;
        }
    }

    public static class ForegroundServiceTypePermissions {
        final boolean mAllOf;
        final ForegroundServiceTypePermission[] mPermissions;

        public ForegroundServiceTypePermissions(ForegroundServiceTypePermission[] foregroundServiceTypePermissionArr, boolean z) {
            this.mPermissions = foregroundServiceTypePermissionArr;
            this.mAllOf = z;
        }

        public int checkPermissions(Context context, int i, int i2, String str, boolean z) {
            if (this.mAllOf) {
                ForegroundServiceTypePermission[] foregroundServiceTypePermissionArr = this.mPermissions;
                int length = foregroundServiceTypePermissionArr.length;
                int i3 = 0;
                while (i3 < length) {
                    Context context2 = context;
                    int i4 = i;
                    int i5 = i2;
                    String str2 = str;
                    boolean z2 = z;
                    if (foregroundServiceTypePermissionArr[i3].checkPermission(context2, i4, i5, str2, z2) != 0) {
                        return -1;
                    }
                    i3++;
                    context = context2;
                    i = i4;
                    i2 = i5;
                    str = str2;
                    z = z2;
                }
                return 0;
            }
            for (ForegroundServiceTypePermission foregroundServiceTypePermission : this.mPermissions) {
                if (foregroundServiceTypePermission.checkPermission(context, i, i2, str, z) == 0) {
                    return 0;
                }
            }
            return -1;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("allOf=");
            sb.append(this.mAllOf);
            sb.append(" [");
            for (int i = 0; i < this.mPermissions.length; i++) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(this.mPermissions[i].toString());
            }
            sb.append(']');
            return sb.toString();
        }

        String[] toStringArray(Context context) {
            ArrayList<String> arrayList = new ArrayList<>();
            int i = 0;
            while (true) {
                ForegroundServiceTypePermission[] foregroundServiceTypePermissionArr = this.mPermissions;
                if (i < foregroundServiceTypePermissionArr.length) {
                    foregroundServiceTypePermissionArr[i].addToList(context, arrayList);
                    i++;
                } else {
                    return (String[]) arrayList.toArray(new String[arrayList.size()]);
                }
            }
        }
    }

    public static abstract class ForegroundServiceTypePermission {
        protected final String mName;

        public abstract int checkPermission(Context context, int i, int i2, String str, boolean z);

        public ForegroundServiceTypePermission(String str) {
            this.mName = str;
        }

        public String toString() {
            return this.mName;
        }

        void addToList(Context context, ArrayList<String> arrayList) {
            arrayList.add(this.mName);
        }
    }

    static class RegularPermission extends ForegroundServiceTypePermission {
        RegularPermission(String str) {
            super(str);
        }

        @Override // android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermission
        public int checkPermission(Context context, int i, int i2, String str, boolean z) {
            VirtualDeviceManager virtualDeviceManager;
            int checkPermission = checkPermission(context, this.mName, i, i2, str, z, 0);
            if (checkPermission != 0 && PermissionManager.DEVICE_AWARE_PERMISSIONS.contains(this.mName) && (virtualDeviceManager = (VirtualDeviceManager) context.getSystemService(VirtualDeviceManager.class)) != null) {
                List<VirtualDevice> virtualDevices = virtualDeviceManager.getVirtualDevices();
                int size = virtualDevices.size();
                for (int i3 = 0; i3 < size; i3++) {
                    int resolveDeviceIdForPermissionCheck = PermissionManager.resolveDeviceIdForPermissionCheck(context, virtualDevices.get(i3).getDeviceId(), this.mName);
                    if (resolveDeviceIdForPermissionCheck != 0 && (checkPermission = checkPermission(context, this.mName, i, i2, str, z, resolveDeviceIdForPermissionCheck)) == 0) {
                        return checkPermission;
                    }
                }
            }
            return checkPermission;
        }

        int checkPermission(Context context, String str, int i, int i2, String str2, boolean z, int i3) {
            AttributionSource attributionSource = new AttributionSource(i, str2, (String) null, i3);
            int checkPermissionForPreflight = PermissionChecker.checkPermissionForPreflight(context, str, attributionSource);
            if (checkPermissionForPreflight == 2) {
                return -1;
            }
            int permissionToOpCode = AppOpsManager.permissionToOpCode(str);
            if (permissionToOpCode == -1) {
                return checkPermissionForPreflight == 0 ? 0 : -1;
            }
            int unsafeCheckOpRawNoThrow = ((AppOpsManager) context.getSystemService(AppOpsManager.class)).unsafeCheckOpRawNoThrow(permissionToOpCode, attributionSource);
            if (unsafeCheckOpRawNoThrow == 0) {
                return 0;
            }
            if (unsafeCheckOpRawNoThrow == 1) {
                return (z && checkPermissionForPreflight == 1) ? 0 : -1;
            }
            if (unsafeCheckOpRawNoThrow == 3) {
                return checkPermissionForPreflight == 0 ? 0 : -1;
            }
            if (unsafeCheckOpRawNoThrow != 4) {
                return -1;
            }
            return (!ForegroundServiceTypePolicy.isFgsTypeFgPermissionEnforcementEnabled() || z) ? 0 : -1;
        }
    }

    static class AppOpPermission extends ForegroundServiceTypePermission {
        final int mOpCode;

        AppOpPermission(int i) {
            super(AppOpsManager.opToPublicName(i));
            this.mOpCode = i;
        }

        @Override // android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermission
        public int checkPermission(Context context, int i, int i2, String str, boolean z) {
            int unsafeCheckOpRawNoThrow = ((AppOpsManager) context.getSystemService(AppOpsManager.class)).unsafeCheckOpRawNoThrow(this.mOpCode, i, str);
            if (unsafeCheckOpRawNoThrow != 0) {
                return (z && unsafeCheckOpRawNoThrow == 4) ? 0 : -1;
            }
            return 0;
        }
    }

    static class RolePermission extends ForegroundServiceTypePermission {
        final String mRole;

        RolePermission(String str) {
            super(str);
            this.mRole = str;
        }

        @Override // android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermission
        public int checkPermission(Context context, int i, int i2, String str, boolean z) {
            List roleHoldersAsUser = ((RoleManager) context.getSystemService(RoleManager.class)).getRoleHoldersAsUser(this.mRole, UserHandle.getUserHandleForUid(i));
            return (roleHoldersAsUser == null || !roleHoldersAsUser.contains(str)) ? -1 : 0;
        }
    }

    static class UsbDevicePermission extends ForegroundServiceTypePermission {
        UsbDevicePermission() {
            super("USB Device");
        }

        @Override // android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermission
        public int checkPermission(Context context, int i, int i2, String str, boolean z) {
            UsbManager usbManager = (UsbManager) context.getSystemService(UsbManager.class);
            HashMap<String, UsbDevice> deviceList = usbManager.getDeviceList();
            if (ArrayUtils.isEmpty(deviceList)) {
                return -1;
            }
            Iterator<UsbDevice> it = deviceList.values().iterator();
            while (it.hasNext()) {
                if (usbManager.hasPermission(it.next(), str, i2, i)) {
                    return 0;
                }
            }
            return -1;
        }
    }

    static class UsbAccessoryPermission extends ForegroundServiceTypePermission {
        UsbAccessoryPermission() {
            super("USB Accessory");
        }

        @Override // android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermission
        public int checkPermission(Context context, int i, int i2, String str, boolean z) {
            UsbManager usbManager = (UsbManager) context.getSystemService(UsbManager.class);
            UsbAccessory[] accessoryList = usbManager.getAccessoryList();
            if (ArrayUtils.isEmpty(accessoryList)) {
                return -1;
            }
            for (UsbAccessory usbAccessory : accessoryList) {
                if (usbManager.hasPermission(usbAccessory, i2, i)) {
                    return 0;
                }
            }
            return -1;
        }
    }

    public static class DefaultForegroundServiceTypePolicy extends ForegroundServiceTypePolicy {
        private final SparseArray<ForegroundServiceTypePolicyInfo> mForegroundServiceTypePolicies;
        private final ArrayMap<String, ForegroundServiceTypePolicyInfo> mPermissionEnforcementToPolicyInfoMap;

        public DefaultForegroundServiceTypePolicy() {
            SparseArray<ForegroundServiceTypePolicyInfo> sparseArray = new SparseArray<>();
            this.mForegroundServiceTypePolicies = sparseArray;
            this.mPermissionEnforcementToPolicyInfoMap = new ArrayMap<>();
            sparseArray.put(-1, FGS_TYPE_POLICY_MANIFEST);
            sparseArray.put(0, FGS_TYPE_POLICY_NONE);
            sparseArray.put(1, FGS_TYPE_POLICY_DATA_SYNC);
            sparseArray.put(2, FGS_TYPE_POLICY_MEDIA_PLAYBACK);
            sparseArray.put(4, FGS_TYPE_POLICY_PHONE_CALL);
            sparseArray.put(8, FGS_TYPE_POLICY_LOCATION);
            sparseArray.put(16, FGS_TYPE_POLICY_CONNECTED_DEVICE);
            sparseArray.put(32, FGS_TYPE_POLICY_MEDIA_PROJECTION);
            sparseArray.put(64, FGS_TYPE_POLICY_CAMERA);
            sparseArray.put(128, FGS_TYPE_POLICY_MICROPHONE);
            sparseArray.put(256, FGS_TYPE_POLICY_HEALTH);
            sparseArray.put(512, FGS_TYPE_POLICY_REMOTE_MESSAGING);
            sparseArray.put(1024, FGS_TYPE_POLICY_SYSTEM_EXEMPTED);
            sparseArray.put(2048, FGS_TYPE_POLICY_SHORT_SERVICE);
            sparseArray.put(8192, FGS_TYPE_POLICY_MEDIA_PROCESSING);
            sparseArray.put(1073741824, FGS_TYPE_POLICY_SPECIAL_USE);
            int size = sparseArray.size();
            for (int i = 0; i < size; i++) {
                ForegroundServiceTypePolicyInfo valueAt = this.mForegroundServiceTypePolicies.valueAt(i);
                this.mPermissionEnforcementToPolicyInfoMap.put(valueAt.mPermissionEnforcementFlag, valueAt);
            }
        }

        @Override // android.app.ForegroundServiceTypePolicy
        public ForegroundServiceTypePolicyInfo getForegroundServiceTypePolicyInfo(int i, int i2) {
            ForegroundServiceTypePolicyInfo foregroundServiceTypePolicyInfo = this.mForegroundServiceTypePolicies.get(i);
            if (foregroundServiceTypePolicyInfo != null) {
                return foregroundServiceTypePolicyInfo;
            }
            ForegroundServiceTypePolicyInfo foregroundServiceTypePolicyInfo2 = this.mForegroundServiceTypePolicies.get(i2);
            if (foregroundServiceTypePolicyInfo2 != null) {
                return foregroundServiceTypePolicyInfo2;
            }
            throw new IllegalArgumentException("Invalid default fgs type " + i2);
        }

        @Override // android.app.ForegroundServiceTypePolicy
        public int checkForegroundServiceTypePolicy(Context context, String str, int i, int i2, boolean z, ForegroundServiceTypePolicyInfo foregroundServiceTypePolicyInfo) {
            Context context2;
            String str2;
            int i3;
            int i4;
            boolean z2;
            int i5;
            if (foregroundServiceTypePolicyInfo.isTypeDisabled(i)) {
                return 3;
            }
            if (foregroundServiceTypePolicyInfo.mAllOfPermissions != null) {
                context2 = context;
                str2 = str;
                i3 = i;
                i4 = i2;
                z2 = z;
                i5 = foregroundServiceTypePolicyInfo.mAllOfPermissions.checkPermissions(context2, i3, i4, str2, z2);
            } else {
                context2 = context;
                str2 = str;
                i3 = i;
                i4 = i2;
                z2 = z;
                i5 = 0;
            }
            if (i5 == 0) {
                if ((foregroundServiceTypePolicyInfo.mAnyOfPermissions == null || (i5 = foregroundServiceTypePolicyInfo.mAnyOfPermissions.checkPermissions(context2, i3, i4, str2, z2)) != 0) && foregroundServiceTypePolicyInfo.mCustomPermission != null) {
                    i5 = foregroundServiceTypePolicyInfo.mCustomPermission.checkPermission(context2, i3, i4, str2, z2);
                }
            }
            return i5 != 0 ? (foregroundServiceTypePolicyInfo.mPermissionEnforcementFlagValue && CompatChanges.isChangeEnabled(ForegroundServiceTypePolicy.FGS_TYPE_PERMISSION_CHANGE_ID, i3)) ? 5 : 4 : foregroundServiceTypePolicyInfo.isTypeDeprecated(i3) ? 2 : 1;
        }

        @Override // android.app.ForegroundServiceTypePolicy
        public void updatePermissionEnforcementFlagIfNecessary(String str) {
            ForegroundServiceTypePolicyInfo foregroundServiceTypePolicyInfo = this.mPermissionEnforcementToPolicyInfoMap.get(str);
            if (foregroundServiceTypePolicyInfo != null) {
                foregroundServiceTypePolicyInfo.updatePermissionEnforcementFlagIfNecessary(str);
            }
        }
    }
}
