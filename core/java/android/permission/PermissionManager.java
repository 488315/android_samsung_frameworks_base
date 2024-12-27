package android.permission;

import android.Manifest;
import android.annotation.SystemApi;
import android.app.ActivityManager;
import android.app.ActivityThread;
import android.app.AppGlobals;
import android.app.AppOpsManager;
import android.app.IActivityManager;
import android.app.PropertyInvalidatedCache;
import android.companion.virtual.VirtualDevice;
import android.companion.virtual.VirtualDeviceManager;
import android.content.AttributionSource;
import android.content.Context;
import android.content.PermissionChecker;
import android.content.pm.IPackageManager;
import android.content.pm.PackageManager;
import android.content.pm.ParceledListSlice;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.content.pm.permission.SplitPermissionInfoParcelable;
import android.internal.modules.utils.build.SdkLevel;
import android.media.AudioManager;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.UserHandle;
import android.permission.IOnPermissionsChangeListener;
import android.permission.IPermissionManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Slog;
import com.android.internal.hidden_from_bootclasspath.android.permission.flags.Flags;
import com.android.internal.util.CollectionUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@SystemApi
/* loaded from: classes3.dex */
public final class PermissionManager {
    public static final String ACTION_REVIEW_PERMISSION_DECISIONS = "android.permission.action.REVIEW_PERMISSION_DECISIONS";
    public static final String CACHE_KEY_PACKAGE_INFO = "cache_key.package_info";
    public static final long CANNOT_INSTALL_WITH_BAD_PERMISSION_GROUPS = 146211400;
    public static final boolean DEBUG_DEVICE_PERMISSIONS = false;
    public static final boolean DEBUG_TRACE_GRANTS = false;
    public static final boolean DEBUG_TRACE_PERMISSION_UPDATES = false;
    public static final Set<String> DEVICE_AWARE_PERMISSIONS;
    private static final long EXEMPTED_INDICATOR_ROLE_UPDATE_FREQUENCY_MS = 15000;
    public static final int EXPLICIT_SET_FLAGS = 32823;

    @SystemApi
    public static final String EXTRA_PERMISSION_USAGES = "android.permission.extra.PERMISSION_USAGES";
    public static final String KILL_APP_REASON_GIDS_CHANGED = "permission grant or revoke changed gids";
    public static final String KILL_APP_REASON_PERMISSIONS_REVOKED = "permissions revoked";
    public static final String LOG_TAG_TRACE_GRANTS = "PermissionGrantTrace";
    public static final int PERMISSION_GRANTED = 0;
    public static final int PERMISSION_HARD_DENIED = 2;
    public static final int PERMISSION_SOFT_DENIED = 1;
    private static final String SYSTEM_PKG = "android";
    private static PropertyInvalidatedCache<PackageNamePermissionQuery, Integer> sPackageNamePermissionCache;
    private static final PropertyInvalidatedCache<PermissionQuery, Integer> sPermissionCache;
    private static volatile boolean sShouldWarnMissingActivityManager;
    private final Context mContext;
    private final LegacyPermissionManager mLegacyPermissionManager;
    private List<SplitPermissionInfo> mSplitPermissionInfos;
    private PermissionUsageHelper mUsageHelper;
    private static final String LOG_TAG = PermissionManager.class.getName();
    public static final boolean USE_ACCESS_CHECKING_SERVICE = SdkLevel.isAtLeastV();
    private static long sLastIndicatorUpdateTime = -1;
    private static final int[] EXEMPTED_ROLES = {17039411, 17039410, 17039412, 17039413, 17039414, 17039415};
    private static final String[] INDICATOR_EXEMPTED_PACKAGES = new String[EXEMPTED_ROLES.length];
    private final ArrayMap<PackageManager.OnPermissionsChangedListener, IOnPermissionsChangeListener> mPermissionListeners = new ArrayMap<>();
    private final IPackageManager mPackageManager = AppGlobals.getPackageManager();
    private final IPermissionManager mPermissionManager = IPermissionManager.Stub.asInterface(ServiceManager.getServiceOrThrow("permissionmgr"));

    @Retention(RetentionPolicy.SOURCE)
    public @interface PermissionResult {
    }

    static {
        Set<String> emptySet;
        if (Flags.deviceAwarePermissionsEnabled()) {
            emptySet = Set.of(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO);
        } else {
            emptySet = Collections.emptySet();
        }
        DEVICE_AWARE_PERMISSIONS = emptySet;
        sShouldWarnMissingActivityManager = true;
        String str = CACHE_KEY_PACKAGE_INFO;
        sPermissionCache = new PropertyInvalidatedCache<PermissionQuery, Integer>(2048, str, "checkPermission") { // from class: android.permission.PermissionManager.1
            @Override // android.app.PropertyInvalidatedCache
            public Integer recompute(PermissionQuery query) {
                return Integer.valueOf(PermissionManager.checkPermissionUncached(query.permission, query.pid, query.uid, query.deviceId));
            }
        };
        sPackageNamePermissionCache = new PropertyInvalidatedCache<PackageNamePermissionQuery, Integer>(16, str, "checkPackageNamePermission") { // from class: android.permission.PermissionManager.2
            @Override // android.app.PropertyInvalidatedCache
            public Integer recompute(PackageNamePermissionQuery query) {
                return Integer.valueOf(PermissionManager.checkPackageNamePermissionUncached(query.permName, query.pkgName, query.persistentDeviceId, query.userId));
            }

            @Override // android.app.PropertyInvalidatedCache
            public boolean bypass(PackageNamePermissionQuery query) {
                return query.userId < 0;
            }
        };
    }

    public PermissionManager(Context context) throws ServiceManager.ServiceNotFoundException {
        this.mContext = context;
        this.mLegacyPermissionManager = (LegacyPermissionManager) context.getSystemService(LegacyPermissionManager.class);
    }

    public int checkPermissionForDataDelivery(String permission, AttributionSource attributionSource, String message) {
        return PermissionChecker.checkPermissionForDataDelivery(this.mContext, permission, -1, attributionSource, message);
    }

    public int checkPermissionForStartDataDelivery(String permission, AttributionSource attributionSource, String message) {
        return PermissionChecker.checkPermissionForDataDelivery(this.mContext, permission, -1, attributionSource, message, true);
    }

    public void finishDataDelivery(String permission, AttributionSource attributionSource) {
        PermissionChecker.finishDataDelivery(this.mContext, AppOpsManager.permissionToOp(permission), attributionSource);
    }

    public int checkPermissionForDataDeliveryFromDataSource(String permission, AttributionSource attributionSource, String message) {
        return PermissionChecker.checkPermissionForDataDeliveryFromDataSource(this.mContext, permission, -1, attributionSource, message);
    }

    public int checkPermissionForPreflight(String permission, AttributionSource attributionSource) {
        return PermissionChecker.checkPermissionForPreflight(this.mContext, permission, attributionSource);
    }

    public PermissionInfo getPermissionInfo(String permissionName, int flags) {
        try {
            String packageName = this.mContext.getOpPackageName();
            return this.mPermissionManager.getPermissionInfo(permissionName, packageName, flags);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<PermissionInfo> queryPermissionsByGroup(String groupName, int flags) {
        try {
            ParceledListSlice<PermissionInfo> parceledList = this.mPermissionManager.queryPermissionsByGroup(groupName, flags);
            if (parceledList == null) {
                return null;
            }
            return parceledList.getList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean addPermission(PermissionInfo permissionInfo, boolean async) {
        try {
            return this.mPermissionManager.addPermission(permissionInfo, async);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void removePermission(String permissionName) {
        try {
            this.mPermissionManager.removePermission(permissionName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public PermissionGroupInfo getPermissionGroupInfo(String groupName, int flags) {
        try {
            return this.mPermissionManager.getPermissionGroupInfo(groupName, flags);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<PermissionGroupInfo> getAllPermissionGroups(int flags) {
        try {
            ParceledListSlice<PermissionGroupInfo> parceledList = this.mPermissionManager.getAllPermissionGroups(flags);
            if (parceledList == null) {
                return Collections.emptyList();
            }
            return parceledList.getList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isPermissionRevokedByPolicy(String packageName, String permissionName) {
        try {
            return this.mPermissionManager.isPermissionRevokedByPolicy(packageName, permissionName, this.mContext.getDeviceId(), this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static boolean shouldTraceGrant(String packageName, String permissionName, int userId) {
        return false;
    }

    public void grantRuntimePermission(String packageName, String permissionName, UserHandle user) {
        String persistentDeviceId = getPersistentDeviceId(this.mContext.getDeviceId());
        if (persistentDeviceId == null) {
            return;
        }
        grantRuntimePermissionInternal(packageName, permissionName, persistentDeviceId, user);
    }

    @SystemApi
    public void grantRuntimePermission(String packageName, String permissionName, String persistentDeviceId) {
        grantRuntimePermissionInternal(packageName, permissionName, persistentDeviceId, this.mContext.getUser());
    }

    private void grantRuntimePermissionInternal(String packageName, String permissionName, String persistentDeviceId, UserHandle user) {
        try {
            this.mPermissionManager.grantRuntimePermission(packageName, permissionName, persistentDeviceId, user.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void revokeRuntimePermission(String packageName, String permissionName, UserHandle user, String reason) {
        String persistentDeviceId = getPersistentDeviceId(this.mContext.getDeviceId());
        if (persistentDeviceId == null) {
            return;
        }
        revokeRuntimePermissionInternal(packageName, permissionName, persistentDeviceId, user, reason);
    }

    @SystemApi
    public void revokeRuntimePermission(String packageName, String permissionName, String persistentDeviceId, String reason) {
        revokeRuntimePermissionInternal(packageName, permissionName, persistentDeviceId, this.mContext.getUser(), reason);
    }

    private void revokeRuntimePermissionInternal(String packageName, String permissionName, String persistentDeviceId, UserHandle user, String reason) {
        try {
            this.mPermissionManager.revokeRuntimePermission(packageName, permissionName, persistentDeviceId, user.getIdentifier(), reason);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getPermissionFlags(String packageName, String permissionName, UserHandle user) {
        String persistentDeviceId = getPersistentDeviceId(this.mContext.getDeviceId());
        if (persistentDeviceId == null) {
            return 0;
        }
        return getPermissionFlagsInternal(packageName, permissionName, persistentDeviceId, user);
    }

    @SystemApi
    public int getPermissionFlags(String packageName, String permissionName, String persistentDeviceId) {
        return getPermissionFlagsInternal(packageName, permissionName, persistentDeviceId, this.mContext.getUser());
    }

    private int getPermissionFlagsInternal(String packageName, String permissionName, String persistentDeviceId, UserHandle user) {
        try {
            return this.mPermissionManager.getPermissionFlags(packageName, permissionName, persistentDeviceId, user.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void updatePermissionFlags(String packageName, String permissionName, int flagMask, int flagValues, UserHandle user) {
        String persistentDeviceId = getPersistentDeviceId(this.mContext.getDeviceId());
        if (persistentDeviceId == null) {
            return;
        }
        updatePermissionFlagsInternal(packageName, permissionName, flagMask, flagValues, persistentDeviceId, user);
    }

    @SystemApi
    public void updatePermissionFlags(String packageName, String permissionName, String persistentDeviceId, int flagMask, int flagValues) {
        updatePermissionFlagsInternal(packageName, permissionName, flagMask, flagValues, persistentDeviceId, this.mContext.getUser());
    }

    private void updatePermissionFlagsInternal(String packageName, String permissionName, int flagMask, int flagValues, String persistentDeviceId, UserHandle user) {
        try {
            boolean checkAdjustPolicyFlagPermission = this.mContext.getApplicationInfo().targetSdkVersion >= 29;
            this.mPermissionManager.updatePermissionFlags(packageName, permissionName, flagMask, flagValues, checkAdjustPolicyFlagPermission, persistentDeviceId, user.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Set<String> getAllowlistedRestrictedPermissions(String packageName, int allowlistFlag) {
        try {
            List<String> allowlist = this.mPermissionManager.getAllowlistedRestrictedPermissions(packageName, allowlistFlag, this.mContext.getUserId());
            if (allowlist == null) {
                return Collections.emptySet();
            }
            return new ArraySet(allowlist);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean addAllowlistedRestrictedPermission(String packageName, String permissionName, int allowlistFlags) {
        try {
            return this.mPermissionManager.addAllowlistedRestrictedPermission(packageName, permissionName, allowlistFlags, this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean removeAllowlistedRestrictedPermission(String packageName, String permissionName, int allowlistFlags) {
        try {
            return this.mPermissionManager.removeAllowlistedRestrictedPermission(packageName, permissionName, allowlistFlags, this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isAutoRevokeExempted(String packageName) {
        try {
            return this.mPermissionManager.isAutoRevokeExempted(packageName, this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setAutoRevokeExempted(String packageName, boolean exempted) {
        try {
            return this.mPermissionManager.setAutoRevokeExempted(packageName, exempted, this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean shouldShowRequestPermissionRationale(String permissionName) {
        try {
            String packageName = this.mContext.getPackageName();
            return this.mPermissionManager.shouldShowRequestPermissionRationale(packageName, permissionName, this.mContext.getDeviceId(), this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void addOnPermissionsChangeListener(PackageManager.OnPermissionsChangedListener listener) {
        synchronized (this.mPermissionListeners) {
            if (this.mPermissionListeners.get(listener) != null) {
                return;
            }
            OnPermissionsChangeListenerDelegate delegate = new OnPermissionsChangeListenerDelegate(listener, Looper.getMainLooper());
            try {
                this.mPermissionManager.addOnPermissionsChangeListener(delegate);
                this.mPermissionListeners.put(listener, delegate);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void removeOnPermissionsChangeListener(PackageManager.OnPermissionsChangedListener listener) {
        synchronized (this.mPermissionListeners) {
            IOnPermissionsChangeListener delegate = this.mPermissionListeners.get(listener);
            if (delegate != null) {
                try {
                    this.mPermissionManager.removeOnPermissionsChangeListener(delegate);
                    this.mPermissionListeners.remove(listener);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }

    @SystemApi
    public int getRuntimePermissionsVersion() {
        try {
            return this.mPackageManager.getRuntimePermissionsVersion(this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setRuntimePermissionsVersion(int version) {
        try {
            this.mPackageManager.setRuntimePermissionsVersion(version, this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<SplitPermissionInfo> getSplitPermissions() {
        if (this.mSplitPermissionInfos != null) {
            return this.mSplitPermissionInfos;
        }
        try {
            List<SplitPermissionInfoParcelable> parcelableList = ActivityThread.getPermissionManager().getSplitPermissions();
            this.mSplitPermissionInfos = splitPermissionInfoListToNonParcelableList(parcelableList);
            return this.mSplitPermissionInfos;
        } catch (RemoteException e) {
            Slog.e(LOG_TAG, "Error getting split permissions", e);
            return Collections.emptyList();
        }
    }

    public void initializeUsageHelper() {
        if (this.mUsageHelper == null) {
            this.mUsageHelper = new PermissionUsageHelper(this.mContext);
        }
    }

    public void tearDownUsageHelper() {
        if (this.mUsageHelper != null) {
            this.mUsageHelper.tearDown();
            this.mUsageHelper = null;
        }
    }

    public List<PermissionGroupUsage> getIndicatorAppOpUsageData() {
        return getIndicatorAppOpUsageData(new AudioManager().isMicrophoneMute());
    }

    public List<PermissionGroupUsage> getIndicatorAppOpUsageData(boolean micMuted) {
        initializeUsageHelper();
        boolean includeMicrophoneUsage = !micMuted;
        return this.mUsageHelper.getOpUsageDataByDevice(includeMicrophoneUsage, VirtualDeviceManager.PERSISTENT_DEVICE_ID_DEFAULT);
    }

    public static boolean shouldShowPackageForIndicatorCached(Context context, String packageName) {
        return !getIndicatorExemptedPackages(context).contains(packageName);
    }

    public static Set<String> getIndicatorExemptedPackages(Context context) {
        updateIndicatorExemptedPackages(context);
        ArraySet<String> pkgNames = new ArraySet<>();
        pkgNames.add("android");
        for (int i = 0; i < INDICATOR_EXEMPTED_PACKAGES.length; i++) {
            String exemptedPackage = INDICATOR_EXEMPTED_PACKAGES[i];
            if (exemptedPackage != null) {
                pkgNames.add(exemptedPackage);
            }
        }
        return pkgNames;
    }

    public static void updateIndicatorExemptedPackages(Context context) {
        long now = SystemClock.elapsedRealtime();
        if (sLastIndicatorUpdateTime == -1 || now - sLastIndicatorUpdateTime > EXEMPTED_INDICATOR_ROLE_UPDATE_FREQUENCY_MS) {
            sLastIndicatorUpdateTime = now;
            for (int i = 0; i < EXEMPTED_ROLES.length; i++) {
                INDICATOR_EXEMPTED_PACKAGES[i] = context.getString(EXEMPTED_ROLES[i]);
            }
        }
    }

    @SystemApi
    public Set<String> getAutoRevokeExemptionRequestedPackages() {
        try {
            return CollectionUtils.toSet(this.mPermissionManager.getAutoRevokeExemptionRequestedPackages(this.mContext.getUser().getIdentifier()));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public Set<String> getAutoRevokeExemptionGrantedPackages() {
        try {
            return CollectionUtils.toSet(this.mPermissionManager.getAutoRevokeExemptionGrantedPackages(this.mContext.getUser().getIdentifier()));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private List<SplitPermissionInfo> splitPermissionInfoListToNonParcelableList(List<SplitPermissionInfoParcelable> parcelableList) {
        int size = parcelableList.size();
        List<SplitPermissionInfo> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(new SplitPermissionInfo(parcelableList.get(i)));
        }
        return list;
    }

    public static List<SplitPermissionInfoParcelable> splitPermissionInfoListToParcelableList(List<SplitPermissionInfo> splitPermissionsList) {
        int size = splitPermissionsList.size();
        List<SplitPermissionInfoParcelable> outList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            SplitPermissionInfo info = splitPermissionsList.get(i);
            outList.add(new SplitPermissionInfoParcelable(info.getSplitPermission(), info.getNewPermissions(), info.getTargetSdk()));
        }
        return outList;
    }

    public static final class SplitPermissionInfo {
        private final SplitPermissionInfoParcelable mSplitPermissionInfoParcelable;

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            SplitPermissionInfo that = (SplitPermissionInfo) o;
            return this.mSplitPermissionInfoParcelable.equals(that.mSplitPermissionInfoParcelable);
        }

        public int hashCode() {
            return this.mSplitPermissionInfoParcelable.hashCode();
        }

        public String getSplitPermission() {
            return this.mSplitPermissionInfoParcelable.getSplitPermission();
        }

        public List<String> getNewPermissions() {
            return this.mSplitPermissionInfoParcelable.getNewPermissions();
        }

        public int getTargetSdk() {
            return this.mSplitPermissionInfoParcelable.getTargetSdk();
        }

        public SplitPermissionInfo(String splitPerm, List<String> newPerms, int targetSdk) {
            this(new SplitPermissionInfoParcelable(splitPerm, newPerms, targetSdk));
        }

        private SplitPermissionInfo(SplitPermissionInfoParcelable parcelable) {
            this.mSplitPermissionInfoParcelable = parcelable;
        }
    }

    @SystemApi
    @Deprecated
    public void startOneTimePermissionSession(String packageName, long timeoutMillis, int importanceToResetTimer, int importanceToKeepSessionAlive) {
        startOneTimePermissionSession(packageName, timeoutMillis, -1L, importanceToResetTimer, importanceToKeepSessionAlive);
    }

    @SystemApi
    public void startOneTimePermissionSession(String packageName, long timeoutMillis, long revokeAfterKilledDelayMillis, int importanceToResetTimer, int importanceToKeepSessionAlive) {
        try {
            this.mPermissionManager.startOneTimePermissionSession(packageName, this.mContext.getDeviceId(), this.mContext.getUserId(), timeoutMillis, revokeAfterKilledDelayMillis);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void stopOneTimePermissionSession(String packageName) {
        try {
            this.mPermissionManager.stopOneTimePermissionSession(packageName, this.mContext.getUserId());
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public int checkDeviceIdentifierAccess(String packageName, String message, String callingFeatureId, int pid, int uid) {
        return this.mLegacyPermissionManager.checkDeviceIdentifierAccess(packageName, message, callingFeatureId, pid, uid);
    }

    public AttributionSource registerAttributionSource(AttributionSource source) {
        try {
            if (Flags.serverSideAttributionRegistration()) {
                IBinder newToken = this.mPermissionManager.registerAttributionSource(source.asState());
                return source.withToken(newToken);
            }
            AttributionSource registeredSource = source.withToken(new Binder());
            this.mPermissionManager.registerAttributionSource(registeredSource.asState());
            return registeredSource;
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return source;
        }
    }

    public boolean isRegisteredAttributionSource(AttributionSource source) {
        try {
            return this.mPermissionManager.isRegisteredAttributionSource(source.asState());
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public int getRegisteredAttributionSourceCountForTest(int uid) {
        try {
            return this.mPermissionManager.getRegisteredAttributionSourceCount(uid);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return -1;
        }
    }

    public void revokePostNotificationPermissionWithoutKillForTest(String packageName, int userId) {
        try {
            this.mPermissionManager.revokePostNotificationPermissionWithoutKillForTest(packageName, userId);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int checkPermissionUncached(String permission, int pid, int uid, int deviceId) {
        IActivityManager am = ActivityManager.getService();
        if (am == null) {
            int appId = UserHandle.getAppId(uid);
            if (appId != 0 && appId != 1000) {
                Slog.w(LOG_TAG, "Missing ActivityManager; assuming " + uid + " does not hold " + permission);
                return -1;
            }
            if (sShouldWarnMissingActivityManager) {
                Slog.w(LOG_TAG, "Missing ActivityManager; assuming " + uid + " holds " + permission);
                sShouldWarnMissingActivityManager = false;
            }
            return 0;
        }
        try {
            sShouldWarnMissingActivityManager = true;
            return am.checkPermissionForDevice(permission, pid, uid, deviceId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private static final class PermissionQuery {
        final int deviceId;
        final String permission;
        final int pid;
        final int uid;

        PermissionQuery(String permission, int pid, int uid, int deviceId) {
            this.permission = permission;
            this.pid = pid;
            this.uid = uid;
            this.deviceId = deviceId;
        }

        public String toString() {
            return TextUtils.formatSimple("PermissionQuery(permission=\"%s\", pid=%d, uid=%d, deviceId=%d)", this.permission, Integer.valueOf(this.pid), Integer.valueOf(this.uid), Integer.valueOf(this.deviceId));
        }

        public int hashCode() {
            return Objects.hash(this.permission, Integer.valueOf(this.uid), Integer.valueOf(this.deviceId));
        }

        public boolean equals(Object rval) {
            if (rval == null) {
                return false;
            }
            try {
                PermissionQuery other = (PermissionQuery) rval;
                return this.uid == other.uid && this.deviceId == other.deviceId && Objects.equals(this.permission, other.permission);
            } catch (ClassCastException e) {
                return false;
            }
        }
    }

    public static int checkPermission(String permission, int pid, int uid, int deviceId) {
        return sPermissionCache.query(new PermissionQuery(permission, pid, uid, deviceId)).intValue();
    }

    @SystemApi
    public Map<String, PermissionState> getAllPermissionStates(String packageName, String persistentDeviceId) {
        try {
            return this.mPermissionManager.getAllPermissionStates(packageName, persistentDeviceId, this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static void disablePermissionCache() {
        sPermissionCache.disableLocal();
    }

    private static final class PackageNamePermissionQuery {
        final String permName;
        final String persistentDeviceId;
        final String pkgName;
        final int userId;

        PackageNamePermissionQuery(String permName, String pkgName, String persistentDeviceId, int userId) {
            this.permName = permName;
            this.pkgName = pkgName;
            this.persistentDeviceId = persistentDeviceId;
            this.userId = userId;
        }

        public String toString() {
            return TextUtils.formatSimple("PackageNamePermissionQuery(pkgName=\"%s\", permName=\"%s\", persistentDeviceId=%s, userId=%s\")", this.pkgName, this.permName, this.persistentDeviceId, Integer.valueOf(this.userId));
        }

        public int hashCode() {
            return Objects.hash(this.permName, this.pkgName, this.persistentDeviceId, Integer.valueOf(this.userId));
        }

        public boolean equals(Object rval) {
            if (rval == null) {
                return false;
            }
            try {
                PackageNamePermissionQuery other = (PackageNamePermissionQuery) rval;
                return Objects.equals(this.permName, other.permName) && Objects.equals(this.pkgName, other.pkgName) && Objects.equals(this.persistentDeviceId, other.persistentDeviceId) && this.userId == other.userId;
            } catch (ClassCastException e) {
                return false;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int checkPackageNamePermissionUncached(String permName, String pkgName, String persistentDeviceId, int userId) {
        try {
            return ActivityThread.getPermissionManager().checkPermission(pkgName, permName, persistentDeviceId, userId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int checkPackageNamePermission(String permName, String pkgName, int deviceId, int userId) {
        String persistentDeviceId = getPersistentDeviceId(deviceId);
        return sPackageNamePermissionCache.query(new PackageNamePermissionQuery(permName, pkgName, persistentDeviceId, userId)).intValue();
    }

    private String getPersistentDeviceId(int deviceId) {
        if (deviceId == 0) {
            return VirtualDeviceManager.PERSISTENT_DEVICE_ID_DEFAULT;
        }
        if (android.companion.virtual.flags.Flags.vdmPublicApis()) {
            VirtualDeviceManager virtualDeviceManager = (VirtualDeviceManager) this.mContext.getSystemService(VirtualDeviceManager.class);
            if (virtualDeviceManager == null) {
                return null;
            }
            VirtualDevice virtualDevice = virtualDeviceManager.getVirtualDevice(deviceId);
            if (virtualDevice == null) {
                Slog.e(LOG_TAG, "Virtual device is not found with device Id " + deviceId);
                return null;
            }
            String persistentDeviceId = virtualDevice.getPersistentDeviceId();
            if (persistentDeviceId == null) {
                Slog.e(LOG_TAG, "Cannot find persistent device Id for " + deviceId);
                return persistentDeviceId;
            }
            return persistentDeviceId;
        }
        Slog.e(LOG_TAG, "vdmPublicApis flag is not enabled when device Id " + deviceId + "is not default.");
        return null;
    }

    @SystemApi
    public int checkPermission(String permissionName, String packageName, String persistentDeviceId) {
        return sPackageNamePermissionCache.query(new PackageNamePermissionQuery(permissionName, packageName, persistentDeviceId, this.mContext.getUserId())).intValue();
    }

    public static void disablePackageNamePermissionCache() {
        sPackageNamePermissionCache.disableLocal();
    }

    private final class OnPermissionsChangeListenerDelegate extends IOnPermissionsChangeListener.Stub implements Handler.Callback {
        private static final int MSG_PERMISSIONS_CHANGED = 1;
        private final Handler mHandler;
        private final PackageManager.OnPermissionsChangedListener mListener;

        public OnPermissionsChangeListenerDelegate(PackageManager.OnPermissionsChangedListener listener, Looper looper) {
            this.mListener = listener;
            this.mHandler = new Handler(looper, this);
        }

        @Override // android.permission.IOnPermissionsChangeListener
        public void onPermissionsChanged(int uid, String persistentDeviceId) {
            this.mHandler.obtainMessage(1, uid, 0, persistentDeviceId).sendToTarget();
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    int uid = msg.arg1;
                    String persistentDeviceId = msg.obj.toString();
                    try {
                        this.mListener.onPermissionsChanged(uid, persistentDeviceId);
                        return true;
                    } catch (Exception e) {
                        Slog.i(PermissionManager.LOG_TAG, "Failed to notify listener", e);
                        return true;
                    }
                default:
                    return false;
            }
        }
    }

    @SystemApi
    public static final class PermissionState implements Parcelable {
        public static final Parcelable.Creator<PermissionState> CREATOR = new Parcelable.Creator<PermissionState>() { // from class: android.permission.PermissionManager.PermissionState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public PermissionState createFromParcel(Parcel source) {
                return new PermissionState(source);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public PermissionState[] newArray(int size) {
                return new PermissionState[size];
            }
        };
        private final int mFlags;
        private final boolean mGranted;

        public PermissionState(boolean granted, int flags) {
            this.mGranted = granted;
            this.mFlags = flags;
        }

        public boolean isGranted() {
            return this.mGranted;
        }

        public int getFlags() {
            return this.mFlags;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int flags) {
            parcel.writeBoolean(this.mGranted);
            parcel.writeInt(this.mFlags);
        }

        private PermissionState(Parcel parcel) {
            this(parcel.readBoolean(), parcel.readInt());
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            PermissionState that = (PermissionState) o;
            if (this.mGranted == that.mGranted && this.mFlags == that.mFlags) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(Boolean.valueOf(this.mGranted), Integer.valueOf(this.mFlags));
        }

        public String toString() {
            return "PermissionState{mGranted=" + this.mGranted + ", mFlags=" + this.mFlags + '}';
        }
    }
}
