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
    public static final String CACHE_KEY_PACKAGE_INFO_CACHE;
    public static final String CACHE_KEY_PACKAGE_INFO_NOTIFY;
    public static final long CANNOT_INSTALL_WITH_BAD_PERMISSION_GROUPS = 146211400;
    public static final boolean DEBUG_DEVICE_PERMISSIONS = false;
    public static final boolean DEBUG_TRACE_GRANTS = false;
    public static final boolean DEBUG_TRACE_PERMISSION_UPDATES = false;
    public static final Set<String> DEVICE_AWARE_PERMISSIONS;
    private static final long EXEMPTED_INDICATOR_ROLE_UPDATE_FREQUENCY_MS = 15000;
    private static final int[] EXEMPTED_ROLES;
    public static final int EXPLICIT_SET_FLAGS = 32823;

    @SystemApi
    public static final String EXTRA_PERMISSION_USAGES = "android.permission.extra.PERMISSION_USAGES";
    private static final String[] INDICATOR_EXEMPTED_PACKAGES;
    public static final String KILL_APP_REASON_GIDS_CHANGED = "permission grant or revoke changed gids";
    public static final String KILL_APP_REASON_PERMISSIONS_REVOKED = "permissions revoked";
    private static final String LOG_TAG = "android.permission.PermissionManager";
    public static final String LOG_TAG_TRACE_GRANTS = "PermissionGrantTrace";
    public static final int PERMISSION_GRANTED = 0;
    public static final int PERMISSION_HARD_DENIED = 2;
    public static final int PERMISSION_SOFT_DENIED = 1;
    private static final String SYSTEM_PKG = "android";
    public static final boolean USE_ACCESS_CHECKING_SERVICE = SdkLevel.isAtLeastV();
    private static long sLastIndicatorUpdateTime = -1;
    private static PropertyInvalidatedCache<PackageNamePermissionQuery, Integer> sPackageNamePermissionCache;
    private static final PropertyInvalidatedCache<PermissionQuery, Integer> sPermissionCache;
    private static final PropertyInvalidatedCache<PermissionRequestStateQuery, Integer> sPermissionRequestStateCache;
    private static volatile boolean sShouldWarnMissingActivityManager;
    private final Context mContext;
    private final LegacyPermissionManager mLegacyPermissionManager;
    private List<SplitPermissionInfo> mSplitPermissionInfos;
    private PermissionUsageHelper mUsageHelper;
    private final ArrayMap<PackageManager.OnPermissionsChangedListener, IOnPermissionsChangeListener> mPermissionListeners = new ArrayMap<>();
    private final IPackageManager mPackageManager = AppGlobals.getPackageManager();
    private final IPermissionManager mPermissionManager = IPermissionManager.Stub.asInterface(ServiceManager.getServiceOrThrow("permissionmgr"));

    @Retention(RetentionPolicy.SOURCE)
    public @interface PermissionResult {
    }

    public static boolean shouldTraceGrant(String str, String str2, int i) {
        return false;
    }

    static {
        Set<String> set;
        int[] iArr = {17039411, 17039410, 17039412, 17039413, 17039414, 17039415};
        EXEMPTED_ROLES = iArr;
        INDICATOR_EXEMPTED_PACKAGES = new String[iArr.length];
        if (Flags.deviceAwarePermissionsEnabled()) {
            set = Set.of(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO);
        } else {
            set = Collections.EMPTY_SET;
        }
        DEVICE_AWARE_PERMISSIONS = set;
        sShouldWarnMissingActivityManager = true;
        CACHE_KEY_PACKAGE_INFO_NOTIFY = PropertyInvalidatedCache.createSystemCacheKey("package_info");
        String packageInfoCacheKey = getPackageInfoCacheKey();
        CACHE_KEY_PACKAGE_INFO_CACHE = packageInfoCacheKey;
        sPermissionCache = new PropertyInvalidatedCache<PermissionQuery, Integer>(2048, packageInfoCacheKey, "checkPermission") { // from class: android.permission.PermissionManager.1
            @Override // android.app.PropertyInvalidatedCache
            public Integer recompute(PermissionQuery permissionQuery) {
                return Integer.valueOf(PermissionManager.checkPermissionUncached(permissionQuery.permission, permissionQuery.pid, permissionQuery.uid, permissionQuery.deviceId));
            }
        };
        sPermissionRequestStateCache = new PropertyInvalidatedCache<PermissionRequestStateQuery, Integer>(512, packageInfoCacheKey, "getPermissionRequestState") { // from class: android.permission.PermissionManager.2
            @Override // android.app.PropertyInvalidatedCache
            public Integer recompute(PermissionRequestStateQuery permissionRequestStateQuery) {
                return Integer.valueOf(PermissionManager.getPermissionRequestStateUncached(permissionRequestStateQuery.mPackageName, permissionRequestStateQuery.mPermission, permissionRequestStateQuery.mDeviceId));
            }
        };
        sPackageNamePermissionCache = new PropertyInvalidatedCache<PackageNamePermissionQuery, Integer>(16, packageInfoCacheKey, "checkPackageNamePermission") { // from class: android.permission.PermissionManager.3
            @Override // android.app.PropertyInvalidatedCache
            public Integer recompute(PackageNamePermissionQuery packageNamePermissionQuery) {
                return Integer.valueOf(PermissionManager.checkPackageNamePermissionUncached(packageNamePermissionQuery.permName, packageNamePermissionQuery.pkgName, packageNamePermissionQuery.persistentDeviceId, packageNamePermissionQuery.userId));
            }

            @Override // android.app.PropertyInvalidatedCache
            public boolean bypass(PackageNamePermissionQuery packageNamePermissionQuery) {
                return packageNamePermissionQuery.userId < 0;
            }
        };
    }

    public PermissionManager(Context context) throws ServiceManager.ServiceNotFoundException {
        this.mContext = context;
        this.mLegacyPermissionManager = (LegacyPermissionManager) context.getSystemService(LegacyPermissionManager.class);
    }

    public int checkPermissionForDataDelivery(String str, AttributionSource attributionSource, String str2) {
        return PermissionChecker.checkPermissionForDataDelivery(this.mContext, str, -1, attributionSource, str2);
    }

    public int checkPermissionForStartDataDelivery(String str, AttributionSource attributionSource, String str2) {
        return PermissionChecker.checkPermissionForDataDelivery(this.mContext, str, -1, attributionSource, str2, true);
    }

    public void finishDataDelivery(String str, AttributionSource attributionSource) {
        PermissionChecker.finishDataDelivery(this.mContext, AppOpsManager.permissionToOp(str), attributionSource);
    }

    public int checkPermissionForDataDeliveryFromDataSource(String str, AttributionSource attributionSource, String str2) {
        return PermissionChecker.checkPermissionForDataDeliveryFromDataSource(this.mContext, str, -1, attributionSource, str2);
    }

    public int checkPermissionForPreflight(String str, AttributionSource attributionSource) {
        return PermissionChecker.checkPermissionForPreflight(this.mContext, str, attributionSource);
    }

    public PermissionInfo getPermissionInfo(String str, int i) {
        try {
            return this.mPermissionManager.getPermissionInfo(str, this.mContext.getOpPackageName(), i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<PermissionInfo> queryPermissionsByGroup(String str, int i) {
        try {
            ParceledListSlice queryPermissionsByGroup = this.mPermissionManager.queryPermissionsByGroup(str, i);
            if (queryPermissionsByGroup == null) {
                return null;
            }
            return queryPermissionsByGroup.getList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean addPermission(PermissionInfo permissionInfo, boolean z) {
        try {
            return this.mPermissionManager.addPermission(permissionInfo, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void removePermission(String str) {
        try {
            this.mPermissionManager.removePermission(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public PermissionGroupInfo getPermissionGroupInfo(String str, int i) {
        try {
            return this.mPermissionManager.getPermissionGroupInfo(str, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<PermissionGroupInfo> getAllPermissionGroups(int i) {
        try {
            ParceledListSlice allPermissionGroups = this.mPermissionManager.getAllPermissionGroups(i);
            if (allPermissionGroups == null) {
                return Collections.EMPTY_LIST;
            }
            return allPermissionGroups.getList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isPermissionRevokedByPolicy(String str, String str2) {
        try {
            return this.mPermissionManager.isPermissionRevokedByPolicy(str, str2, this.mContext.getDeviceId(), this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void grantRuntimePermission(String str, String str2, UserHandle userHandle) {
        String persistentDeviceId = getPersistentDeviceId(this.mContext.getDeviceId());
        if (persistentDeviceId == null) {
            return;
        }
        grantRuntimePermissionInternal(str, str2, persistentDeviceId, userHandle);
    }

    @SystemApi
    public void grantRuntimePermission(String str, String str2, String str3) {
        grantRuntimePermissionInternal(str, str2, str3, this.mContext.getUser());
    }

    private void grantRuntimePermissionInternal(String str, String str2, String str3, UserHandle userHandle) {
        try {
            this.mPermissionManager.grantRuntimePermission(str, str2, str3, userHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void revokeRuntimePermission(String str, String str2, UserHandle userHandle, String str3) {
        String persistentDeviceId = getPersistentDeviceId(this.mContext.getDeviceId());
        if (persistentDeviceId == null) {
            return;
        }
        revokeRuntimePermissionInternal(str, str2, persistentDeviceId, userHandle, str3);
    }

    @SystemApi
    public void revokeRuntimePermission(String str, String str2, String str3, String str4) {
        revokeRuntimePermissionInternal(str, str2, str3, this.mContext.getUser(), str4);
    }

    private void revokeRuntimePermissionInternal(String str, String str2, String str3, UserHandle userHandle, String str4) {
        try {
            this.mPermissionManager.revokeRuntimePermission(str, str2, str3, userHandle.getIdentifier(), str4);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getPermissionFlags(String str, String str2, UserHandle userHandle) {
        String persistentDeviceId = getPersistentDeviceId(this.mContext.getDeviceId());
        if (persistentDeviceId == null) {
            return 0;
        }
        return getPermissionFlagsInternal(str, str2, persistentDeviceId, userHandle);
    }

    @SystemApi
    public int getPermissionFlags(String str, String str2, String str3) {
        return getPermissionFlagsInternal(str, str2, str3, this.mContext.getUser());
    }

    private int getPermissionFlagsInternal(String str, String str2, String str3, UserHandle userHandle) {
        try {
            return this.mPermissionManager.getPermissionFlags(str, str2, str3, userHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void updatePermissionFlags(String str, String str2, int i, int i2, UserHandle userHandle) {
        String persistentDeviceId = getPersistentDeviceId(this.mContext.getDeviceId());
        if (persistentDeviceId == null) {
            return;
        }
        updatePermissionFlagsInternal(str, str2, i, i2, persistentDeviceId, userHandle);
    }

    @SystemApi
    public void updatePermissionFlags(String str, String str2, String str3, int i, int i2) {
        updatePermissionFlagsInternal(str, str2, i, i2, str3, this.mContext.getUser());
    }

    private void updatePermissionFlagsInternal(String str, String str2, int i, int i2, String str3, UserHandle userHandle) {
        try {
            this.mPermissionManager.updatePermissionFlags(str, str2, i, i2, this.mContext.getApplicationInfo().targetSdkVersion >= 29, str3, userHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Set<String> getAllowlistedRestrictedPermissions(String str, int i) {
        try {
            List<String> allowlistedRestrictedPermissions = this.mPermissionManager.getAllowlistedRestrictedPermissions(str, i, this.mContext.getUserId());
            if (allowlistedRestrictedPermissions == null) {
                return Collections.EMPTY_SET;
            }
            return new ArraySet(allowlistedRestrictedPermissions);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean addAllowlistedRestrictedPermission(String str, String str2, int i) {
        try {
            return this.mPermissionManager.addAllowlistedRestrictedPermission(str, str2, i, this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean removeAllowlistedRestrictedPermission(String str, String str2, int i) {
        try {
            return this.mPermissionManager.removeAllowlistedRestrictedPermission(str, str2, i, this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isAutoRevokeExempted(String str) {
        try {
            return this.mPermissionManager.isAutoRevokeExempted(str, this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setAutoRevokeExempted(String str, boolean z) {
        try {
            return this.mPermissionManager.setAutoRevokeExempted(str, z, this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean shouldShowRequestPermissionRationale(String str) {
        try {
            return this.mPermissionManager.shouldShowRequestPermissionRationale(this.mContext.getPackageName(), str, this.mContext.getDeviceId(), this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void addOnPermissionsChangeListener(PackageManager.OnPermissionsChangedListener onPermissionsChangedListener) {
        synchronized (this.mPermissionListeners) {
            if (this.mPermissionListeners.get(onPermissionsChangedListener) != null) {
                return;
            }
            OnPermissionsChangeListenerDelegate onPermissionsChangeListenerDelegate = new OnPermissionsChangeListenerDelegate(this, onPermissionsChangedListener, Looper.getMainLooper());
            try {
                this.mPermissionManager.addOnPermissionsChangeListener(onPermissionsChangeListenerDelegate);
                this.mPermissionListeners.put(onPermissionsChangedListener, onPermissionsChangeListenerDelegate);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void removeOnPermissionsChangeListener(PackageManager.OnPermissionsChangedListener onPermissionsChangedListener) {
        synchronized (this.mPermissionListeners) {
            IOnPermissionsChangeListener iOnPermissionsChangeListener = this.mPermissionListeners.get(onPermissionsChangedListener);
            if (iOnPermissionsChangeListener != null) {
                try {
                    this.mPermissionManager.removeOnPermissionsChangeListener(iOnPermissionsChangeListener);
                    this.mPermissionListeners.remove(onPermissionsChangedListener);
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
    public void setRuntimePermissionsVersion(int i) {
        try {
            this.mPackageManager.setRuntimePermissionsVersion(i, this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<SplitPermissionInfo> getSplitPermissions() {
        List<SplitPermissionInfo> list = this.mSplitPermissionInfos;
        if (list != null) {
            return list;
        }
        try {
            List<SplitPermissionInfo> splitPermissionInfoListToNonParcelableList = splitPermissionInfoListToNonParcelableList(ActivityThread.getPermissionManager().getSplitPermissions());
            this.mSplitPermissionInfos = splitPermissionInfoListToNonParcelableList;
            return splitPermissionInfoListToNonParcelableList;
        } catch (RemoteException e) {
            Slog.e(LOG_TAG, "Error getting split permissions", e);
            return Collections.EMPTY_LIST;
        }
    }

    public void initializeUsageHelper() {
        if (this.mUsageHelper == null) {
            this.mUsageHelper = new PermissionUsageHelper(this.mContext);
        }
    }

    public void tearDownUsageHelper() {
        PermissionUsageHelper permissionUsageHelper = this.mUsageHelper;
        if (permissionUsageHelper != null) {
            permissionUsageHelper.tearDown();
            this.mUsageHelper = null;
        }
    }

    public List<PermissionGroupUsage> getIndicatorAppOpUsageData() {
        return getIndicatorAppOpUsageData(new AudioManager().isMicrophoneMute());
    }

    public List<PermissionGroupUsage> getIndicatorAppOpUsageData(boolean z) {
        initializeUsageHelper();
        return this.mUsageHelper.getOpUsageDataByDevice(!z, VirtualDeviceManager.PERSISTENT_DEVICE_ID_DEFAULT);
    }

    public static boolean shouldShowPackageForIndicatorCached(Context context, String str) {
        return !getIndicatorExemptedPackages(context).contains(str);
    }

    public static Set<String> getIndicatorExemptedPackages(Context context) {
        updateIndicatorExemptedPackages(context);
        ArraySet arraySet = new ArraySet();
        arraySet.add("android");
        int i = 0;
        while (true) {
            String[] strArr = INDICATOR_EXEMPTED_PACKAGES;
            if (i >= strArr.length) {
                return arraySet;
            }
            String str = strArr[i];
            if (str != null) {
                arraySet.add(str);
            }
            i++;
        }
    }

    public static void updateIndicatorExemptedPackages(Context context) {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        long j = sLastIndicatorUpdateTime;
        if (j != -1 && elapsedRealtime - j <= EXEMPTED_INDICATOR_ROLE_UPDATE_FREQUENCY_MS) {
            return;
        }
        sLastIndicatorUpdateTime = elapsedRealtime;
        int i = 0;
        while (true) {
            int[] iArr = EXEMPTED_ROLES;
            if (i >= iArr.length) {
                return;
            }
            INDICATOR_EXEMPTED_PACKAGES[i] = context.getString(iArr[i]);
            i++;
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

    private List<SplitPermissionInfo> splitPermissionInfoListToNonParcelableList(List<SplitPermissionInfoParcelable> list) {
        int size = list.size();
        ArrayList arrayList = new ArrayList(size);
        for (int i = 0; i < size; i++) {
            arrayList.add(new SplitPermissionInfo(list.get(i)));
        }
        return arrayList;
    }

    public static List<SplitPermissionInfoParcelable> splitPermissionInfoListToParcelableList(List<SplitPermissionInfo> list) {
        int size = list.size();
        ArrayList arrayList = new ArrayList(size);
        for (int i = 0; i < size; i++) {
            SplitPermissionInfo splitPermissionInfo = list.get(i);
            arrayList.add(new SplitPermissionInfoParcelable(splitPermissionInfo.getSplitPermission(), splitPermissionInfo.getNewPermissions(), splitPermissionInfo.getTargetSdk()));
        }
        return arrayList;
    }

    public static final class SplitPermissionInfo {
        private final SplitPermissionInfoParcelable mSplitPermissionInfoParcelable;

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            return this.mSplitPermissionInfoParcelable.equals(((SplitPermissionInfo) obj).mSplitPermissionInfoParcelable);
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

        public SplitPermissionInfo(String str, List<String> list, int i) {
            this(new SplitPermissionInfoParcelable(str, list, i));
        }

        private SplitPermissionInfo(SplitPermissionInfoParcelable splitPermissionInfoParcelable) {
            this.mSplitPermissionInfoParcelable = splitPermissionInfoParcelable;
        }
    }

    @SystemApi
    @Deprecated
    public void startOneTimePermissionSession(String str, long j, int i, int i2) {
        startOneTimePermissionSession(str, j, -1L, i, i2);
    }

    @SystemApi
    public void startOneTimePermissionSession(String str, long j, long j2, int i, int i2) {
        try {
            this.mPermissionManager.startOneTimePermissionSession(str, this.mContext.getDeviceId(), this.mContext.getUserId(), j, j2);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void stopOneTimePermissionSession(String str) {
        try {
            this.mPermissionManager.stopOneTimePermissionSession(str, this.mContext.getUserId());
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public int checkDeviceIdentifierAccess(String str, String str2, String str3, int i, int i2) {
        return this.mLegacyPermissionManager.checkDeviceIdentifierAccess(str, str2, str3, i, i2);
    }

    public AttributionSource registerAttributionSource(AttributionSource attributionSource) {
        try {
            if (Flags.serverSideAttributionRegistration()) {
                return attributionSource.withToken(this.mPermissionManager.registerAttributionSource(attributionSource.asState()));
            }
            AttributionSource withToken = attributionSource.withToken(new Binder());
            this.mPermissionManager.registerAttributionSource(withToken.asState());
            return withToken;
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return attributionSource;
        }
    }

    public boolean isRegisteredAttributionSource(AttributionSource attributionSource) {
        try {
            return this.mPermissionManager.isRegisteredAttributionSource(attributionSource.asState());
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public int getRegisteredAttributionSourceCountForTest(int i) {
        try {
            return this.mPermissionManager.getRegisteredAttributionSourceCount(i);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return -1;
        }
    }

    public void revokePostNotificationPermissionWithoutKillForTest(String str, int i) {
        try {
            this.mPermissionManager.revokePostNotificationPermissionWithoutKillForTest(str, i);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int checkPermissionUncached(String str, int i, int i2, int i3) {
        int appId = UserHandle.getAppId(i2);
        if (appId == 0 || appId == 1000) {
            return 0;
        }
        IActivityManager service = ActivityManager.getService();
        if (service == null) {
            Slog.w(LOG_TAG, "Missing ActivityManager; assuming " + i2 + " does not hold " + str);
            return -1;
        }
        try {
            sShouldWarnMissingActivityManager = true;
            return service.checkPermissionForDevice(str, i, i2, i3);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getPermissionRequestStateUncached(String str, String str2, int i) {
        try {
            return AppGlobals.getPermissionManager().getPermissionRequestState(str, str2, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private static final class PermissionQuery {
        final int deviceId;
        final String permission;
        final int pid;
        final int uid;

        PermissionQuery(String str, int i, int i2, int i3) {
            this.permission = str;
            this.pid = i;
            this.uid = i2;
            this.deviceId = i3;
        }

        public String toString() {
            return TextUtils.formatSimple("PermissionQuery(permission=\"%s\", pid=%d, uid=%d, deviceId=%d)", this.permission, Integer.valueOf(this.pid), Integer.valueOf(this.uid), Integer.valueOf(this.deviceId));
        }

        public int hashCode() {
            return Objects.hash(this.permission, Integer.valueOf(this.uid), Integer.valueOf(this.deviceId));
        }

        public boolean equals(Object obj) {
            PermissionQuery permissionQuery;
            if (obj == null) {
                return false;
            }
            try {
                permissionQuery = (PermissionQuery) obj;
            } catch (ClassCastException unused) {
            }
            return this.uid == permissionQuery.uid && this.deviceId == permissionQuery.deviceId && Objects.equals(this.permission, permissionQuery.permission);
        }
    }

    private static final class PermissionRequestStateQuery {
        final int mDeviceId;
        final String mPackageName;
        final String mPermission;

        PermissionRequestStateQuery(String str, String str2, int i) {
            this.mPackageName = str;
            this.mPermission = str2;
            this.mDeviceId = i;
        }

        public String toString() {
            return TextUtils.formatSimple("PermissionRequestStateQuery(package=\"%s\", permission=\"%s\", deviceId=%d)", this.mPackageName, this.mPermission, Integer.valueOf(this.mDeviceId));
        }

        public int hashCode() {
            return Objects.hash(this.mPackageName, this.mPermission, Integer.valueOf(this.mDeviceId));
        }

        public boolean equals(Object obj) {
            PermissionRequestStateQuery permissionRequestStateQuery;
            if (obj == null) {
                return false;
            }
            try {
                permissionRequestStateQuery = (PermissionRequestStateQuery) obj;
            } catch (ClassCastException unused) {
            }
            return this.mDeviceId == permissionRequestStateQuery.mDeviceId && Objects.equals(this.mPackageName, permissionRequestStateQuery.mPackageName) && Objects.equals(this.mPermission, permissionRequestStateQuery.mPermission);
        }
    }

    private static String getPackageInfoCacheKey() {
        if (PropertyInvalidatedCache.separatePermissionNotificationsEnabled()) {
            return PropertyInvalidatedCache.createSystemCacheKey("package_info_cache");
        }
        return CACHE_KEY_PACKAGE_INFO_NOTIFY;
    }

    public static int checkPermission(String str, int i, int i2, int i3) {
        return sPermissionCache.query(new PermissionQuery(str, i, i2, i3)).intValue();
    }

    public int getPermissionRequestState(String str, String str2, int i) {
        return sPermissionRequestStateCache.query(new PermissionRequestStateQuery(str, str2, resolveDeviceIdForPermissionCheck(this.mContext, i, str2))).intValue();
    }

    @SystemApi
    public Map<String, PermissionState> getAllPermissionStates(String str, String str2) {
        try {
            return this.mPermissionManager.getAllPermissionStates(str, str2, this.mContext.getUserId());
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

        PackageNamePermissionQuery(String str, String str2, String str3, int i) {
            this.permName = str;
            this.pkgName = str2;
            this.persistentDeviceId = str3;
            this.userId = i;
        }

        public String toString() {
            return TextUtils.formatSimple("PackageNamePermissionQuery(pkgName=\"%s\", permName=\"%s\", persistentDeviceId=%s, userId=%s\")", this.pkgName, this.permName, this.persistentDeviceId, Integer.valueOf(this.userId));
        }

        public int hashCode() {
            return Objects.hash(this.permName, this.pkgName, this.persistentDeviceId, Integer.valueOf(this.userId));
        }

        public boolean equals(Object obj) {
            PackageNamePermissionQuery packageNamePermissionQuery;
            if (obj == null) {
                return false;
            }
            try {
                packageNamePermissionQuery = (PackageNamePermissionQuery) obj;
            } catch (ClassCastException unused) {
            }
            return Objects.equals(this.permName, packageNamePermissionQuery.permName) && Objects.equals(this.pkgName, packageNamePermissionQuery.pkgName) && Objects.equals(this.persistentDeviceId, packageNamePermissionQuery.persistentDeviceId) && this.userId == packageNamePermissionQuery.userId;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int checkPackageNamePermissionUncached(String str, String str2, String str3, int i) {
        try {
            return ActivityThread.getPermissionManager().checkPermission(str2, str, str3, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int checkPackageNamePermission(String str, String str2, int i, int i2) {
        return sPackageNamePermissionCache.query(new PackageNamePermissionQuery(str, str2, getPersistentDeviceId(resolveDeviceIdForPermissionCheck(this.mContext, i, str)), i2)).intValue();
    }

    public static int resolveDeviceIdForPermissionCheck(Context context, int i, String str) {
        if (i == 0 || !DEVICE_AWARE_PERMISSIONS.contains(str)) {
            return 0;
        }
        VirtualDeviceManager virtualDeviceManager = (VirtualDeviceManager) context.getSystemService(VirtualDeviceManager.class);
        if (virtualDeviceManager == null) {
            Slog.e(LOG_TAG, "VDM is not enabled when device id is not default. deviceId = " + i);
            return i;
        }
        VirtualDevice virtualDevice = virtualDeviceManager.getVirtualDevice(i);
        if (virtualDevice != null) {
            if ((!Objects.equals(str, Manifest.permission.RECORD_AUDIO) || virtualDevice.hasCustomAudioInputSupport()) && (!Objects.equals(str, Manifest.permission.CAMERA) || virtualDevice.hasCustomCameraSupport())) {
                return i;
            }
            return 0;
        }
        Slog.e(LOG_TAG, "virtualDevice is not found when device id is not default. deviceId = " + i);
        return i;
    }

    private String getPersistentDeviceId(int i) {
        if (i == 0) {
            return VirtualDeviceManager.PERSISTENT_DEVICE_ID_DEFAULT;
        }
        VirtualDeviceManager virtualDeviceManager = (VirtualDeviceManager) this.mContext.getSystemService(VirtualDeviceManager.class);
        if (virtualDeviceManager == null) {
            return null;
        }
        VirtualDevice virtualDevice = virtualDeviceManager.getVirtualDevice(i);
        if (virtualDevice == null) {
            Slog.e(LOG_TAG, "Virtual device is not found with device Id " + i);
            return null;
        }
        String persistentDeviceId = virtualDevice.getPersistentDeviceId();
        if (persistentDeviceId == null) {
            Slog.e(LOG_TAG, "Cannot find persistent device Id for " + i);
        }
        return persistentDeviceId;
    }

    @SystemApi
    public int checkPermission(String str, String str2, String str3) {
        return sPackageNamePermissionCache.query(new PackageNamePermissionQuery(str, str2, str3, this.mContext.getUserId())).intValue();
    }

    public static void disablePackageNamePermissionCache() {
        sPackageNamePermissionCache.disableLocal();
    }

    private final class OnPermissionsChangeListenerDelegate extends IOnPermissionsChangeListener.Stub implements Handler.Callback {
        private static final int MSG_PERMISSIONS_CHANGED = 1;
        private final Handler mHandler;
        private final PackageManager.OnPermissionsChangedListener mListener;

        public OnPermissionsChangeListenerDelegate(PermissionManager permissionManager, PackageManager.OnPermissionsChangedListener onPermissionsChangedListener, Looper looper) {
            this.mListener = onPermissionsChangedListener;
            this.mHandler = new Handler(looper, this);
        }

        @Override // android.permission.IOnPermissionsChangeListener
        public void onPermissionsChanged(int i, String str) {
            this.mHandler.obtainMessage(1, i, 0, str).sendToTarget();
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            if (message.what != 1) {
                return false;
            }
            try {
                this.mListener.onPermissionsChanged(message.arg1, message.obj.toString());
            } catch (Exception e) {
                Slog.i(PermissionManager.LOG_TAG, "Failed to notify listener", e);
            }
            return true;
        }
    }

    @SystemApi
    public static final class PermissionState implements Parcelable {
        public static final Parcelable.Creator<PermissionState> CREATOR = new Parcelable.Creator<PermissionState>() { // from class: android.permission.PermissionManager.PermissionState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public PermissionState createFromParcel(Parcel parcel) {
                return new PermissionState(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public PermissionState[] newArray(int i) {
                return new PermissionState[i];
            }
        };
        private final int mFlags;
        private final boolean mGranted;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public PermissionState(boolean z, int i) {
            this.mGranted = z;
            this.mFlags = i;
        }

        public boolean isGranted() {
            return this.mGranted;
        }

        public int getFlags() {
            return this.mFlags;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeBoolean(this.mGranted);
            parcel.writeInt(this.mFlags);
        }

        private PermissionState(Parcel parcel) {
            this(parcel.readBoolean(), parcel.readInt());
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && getClass() == obj.getClass()) {
                PermissionState permissionState = (PermissionState) obj;
                if (this.mGranted == permissionState.mGranted && this.mFlags == permissionState.mFlags) {
                    return true;
                }
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
