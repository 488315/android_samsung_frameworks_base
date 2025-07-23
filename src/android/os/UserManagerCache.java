package android.os;

import android.content.pm.UserInfo;
import android.content.pm.UserProperties;
import android.os.IpcDataCache;
import android.os.UserManager;
import android.util.Pair;
import java.util.List;

/* loaded from: classes3.dex */
public class UserManagerCache {
    private static IpcDataCache<Integer, int[]> sEnabledProfileIds;
    private static IpcDataCache<Integer, int[]> sProfileIdsWithDisabled;
    private static IpcDataCache<UserHandle, UserHandle> sProfileParent;
    private static IpcDataCache<Integer, List<UserInfo>> sProfiles;
    private static IpcDataCache<Integer, UserInfo> sUserInfo;
    private static IpcDataCache<UserManager.QueryUserId, UserProperties> sUserPropertiesFromQuery;
    private static IpcDataCache<Pair<String, Integer>, Boolean> sUserRestrictionFromQuery;
    private static IpcDataCache<Integer, Boolean> sUserUnlocked;
    private static IpcDataCache<Integer, Boolean> sUserUnlockingOrUnlocked;
    private IpcDataCache<UserHandle, Boolean> mQuietModeEnabled;
    private IpcDataCache<Integer, Integer> mUserSerialNumber;
    private static final Object sUserUnlockedLock = new Object();
    private static final Object sUserUnlockingOrUnlockedLock = new Object();
    private static final Object sUserInfoLock = new Object();
    private static final Object sUserPropertiesFromQueryLock = new Object();
    private static final Object sUserRestrictionFromQueryLock = new Object();
    private static final Object sProfilesLock = new Object();
    private static final Object sProfileIdsWithDisabledLock = new Object();
    private static final Object sEnabledProfileIdsLock = new Object();
    private static final Object sProfileParentLock = new Object();
    private final Object mQuietModeEnabledLock = new Object();
    private final Object mUserSerialNumberLock = new Object();

    public static Boolean isUserUnlocked(IpcDataCache.RemoteCall<Integer, Boolean> remoteCall, IpcDataCache.BypassCall<Integer> bypassCall, Integer num) {
        IpcDataCache<Integer, Boolean> ipcDataCache = sUserUnlocked;
        if (ipcDataCache != null) {
            return ipcDataCache.query(num);
        }
        synchronized (sUserUnlockedLock) {
            if (sUserUnlocked == null) {
                sUserUnlocked = new IpcDataCache<>(new IpcDataCache.Config(32, "system_server", "is_user_unlocked", "UserUnlocked"), remoteCall, bypassCall);
            }
        }
        return sUserUnlocked.query(num);
    }

    public static Boolean isUserUnlocked(IpcDataCache.RemoteCall<Integer, Boolean> remoteCall, Integer num) {
        IpcDataCache<Integer, Boolean> ipcDataCache = sUserUnlocked;
        if (ipcDataCache != null) {
            return ipcDataCache.query(num);
        }
        synchronized (sUserUnlockedLock) {
            if (sUserUnlocked == null) {
                sUserUnlocked = new IpcDataCache<>(new IpcDataCache.Config(32, "system_server", "is_user_unlocked", "UserUnlocked"), remoteCall);
            }
        }
        return sUserUnlocked.query(num);
    }

    public static final void invalidateUserUnlocked() {
        IpcDataCache.invalidateCache("system_server", "is_user_unlocked");
    }

    public static Boolean isUserUnlockingOrUnlocked(IpcDataCache.RemoteCall<Integer, Boolean> remoteCall, IpcDataCache.BypassCall<Integer> bypassCall, Integer num) {
        IpcDataCache<Integer, Boolean> ipcDataCache = sUserUnlockingOrUnlocked;
        if (ipcDataCache != null) {
            return ipcDataCache.query(num);
        }
        synchronized (sUserUnlockingOrUnlockedLock) {
            if (sUserUnlockingOrUnlocked == null) {
                sUserUnlockingOrUnlocked = new IpcDataCache<>(new IpcDataCache.Config(32, "system_server", "is_user_unlocked", "UserUnlockingOrUnlocked"), remoteCall, bypassCall);
            }
        }
        return sUserUnlockingOrUnlocked.query(num);
    }

    public static Boolean isUserUnlockingOrUnlocked(IpcDataCache.RemoteCall<Integer, Boolean> remoteCall, Integer num) {
        IpcDataCache<Integer, Boolean> ipcDataCache = sUserUnlockingOrUnlocked;
        if (ipcDataCache != null) {
            return ipcDataCache.query(num);
        }
        synchronized (sUserUnlockingOrUnlockedLock) {
            if (sUserUnlockingOrUnlocked == null) {
                sUserUnlockingOrUnlocked = new IpcDataCache<>(new IpcDataCache.Config(32, "system_server", "is_user_unlocked", "UserUnlockingOrUnlocked"), remoteCall);
            }
        }
        return sUserUnlockingOrUnlocked.query(num);
    }

    public static final void invalidateUserUnlockingOrUnlocked() {
        IpcDataCache.invalidateCache("system_server", "is_user_unlocked");
    }

    public static UserInfo getUserInfo(IpcDataCache.RemoteCall<Integer, UserInfo> remoteCall, IpcDataCache.BypassCall<Integer> bypassCall, Integer num) {
        IpcDataCache<Integer, UserInfo> ipcDataCache = sUserInfo;
        if (ipcDataCache != null) {
            return ipcDataCache.query(num);
        }
        synchronized (sUserInfoLock) {
            if (sUserInfo == null) {
                sUserInfo = new IpcDataCache<>(new IpcDataCache.Config(32, "system_server", "user_manager_user_data", "UserInfo"), remoteCall, bypassCall);
            }
        }
        return sUserInfo.query(num);
    }

    public static UserInfo getUserInfo(IpcDataCache.RemoteCall<Integer, UserInfo> remoteCall, Integer num) {
        IpcDataCache<Integer, UserInfo> ipcDataCache = sUserInfo;
        if (ipcDataCache != null) {
            return ipcDataCache.query(num);
        }
        synchronized (sUserInfoLock) {
            if (sUserInfo == null) {
                sUserInfo = new IpcDataCache<>(new IpcDataCache.Config(32, "system_server", "user_manager_user_data", "UserInfo"), remoteCall);
            }
        }
        return sUserInfo.query(num);
    }

    public static final void invalidateUserInfo() {
        IpcDataCache.invalidateCache("system_server", "user_manager_user_data");
    }

    public static UserProperties getUserPropertiesFromQuery(IpcDataCache.RemoteCall<UserManager.QueryUserId, UserProperties> remoteCall, IpcDataCache.BypassCall<UserManager.QueryUserId> bypassCall, UserManager.QueryUserId queryUserId) {
        IpcDataCache<UserManager.QueryUserId, UserProperties> ipcDataCache = sUserPropertiesFromQuery;
        if (ipcDataCache != null) {
            return ipcDataCache.query(queryUserId);
        }
        synchronized (sUserPropertiesFromQueryLock) {
            if (sUserPropertiesFromQuery == null) {
                sUserPropertiesFromQuery = new IpcDataCache<>(new IpcDataCache.Config(32, "system_server", "user_manager_user_properties_from_query", "UserPropertiesFromQuery"), remoteCall, bypassCall);
            }
        }
        return sUserPropertiesFromQuery.query(queryUserId);
    }

    public static UserProperties getUserPropertiesFromQuery(IpcDataCache.RemoteCall<UserManager.QueryUserId, UserProperties> remoteCall, UserManager.QueryUserId queryUserId) {
        IpcDataCache<UserManager.QueryUserId, UserProperties> ipcDataCache = sUserPropertiesFromQuery;
        if (ipcDataCache != null) {
            return ipcDataCache.query(queryUserId);
        }
        synchronized (sUserPropertiesFromQueryLock) {
            if (sUserPropertiesFromQuery == null) {
                sUserPropertiesFromQuery = new IpcDataCache<>(new IpcDataCache.Config(32, "system_server", "user_manager_user_properties_from_query", "UserPropertiesFromQuery"), remoteCall);
            }
        }
        return sUserPropertiesFromQuery.query(queryUserId);
    }

    public static final void invalidateUserPropertiesFromQuery() {
        IpcDataCache.invalidateCache("system_server", "user_manager_user_properties_from_query");
    }

    public static Boolean getUserRestrictionFromQuery(IpcDataCache.RemoteCall<Pair<String, Integer>, Boolean> remoteCall, IpcDataCache.BypassCall<Pair<String, Integer>> bypassCall, Pair<String, Integer> pair) {
        IpcDataCache<Pair<String, Integer>, Boolean> ipcDataCache = sUserRestrictionFromQuery;
        if (ipcDataCache != null) {
            return ipcDataCache.query(pair);
        }
        synchronized (sUserRestrictionFromQueryLock) {
            if (sUserRestrictionFromQuery == null) {
                sUserRestrictionFromQuery = new IpcDataCache<>(new IpcDataCache.Config(32, "system_server", "user_manager_user_restriction_from_query", "UserRestrictionFromQuery"), remoteCall, bypassCall);
            }
        }
        return sUserRestrictionFromQuery.query(pair);
    }

    public static Boolean getUserRestrictionFromQuery(IpcDataCache.RemoteCall<Pair<String, Integer>, Boolean> remoteCall, Pair<String, Integer> pair) {
        IpcDataCache<Pair<String, Integer>, Boolean> ipcDataCache = sUserRestrictionFromQuery;
        if (ipcDataCache != null) {
            return ipcDataCache.query(pair);
        }
        synchronized (sUserRestrictionFromQueryLock) {
            if (sUserRestrictionFromQuery == null) {
                sUserRestrictionFromQuery = new IpcDataCache<>(new IpcDataCache.Config(32, "system_server", "user_manager_user_restriction_from_query", "UserRestrictionFromQuery"), remoteCall);
            }
        }
        return sUserRestrictionFromQuery.query(pair);
    }

    public static final void invalidateUserRestrictionFromQuery() {
        IpcDataCache.invalidateCache("system_server", "user_manager_user_restriction_from_query");
    }

    public static List<UserInfo> getProfiles(IpcDataCache.RemoteCall<Integer, List<UserInfo>> remoteCall, IpcDataCache.BypassCall<Integer> bypassCall, Integer num) {
        IpcDataCache<Integer, List<UserInfo>> ipcDataCache = sProfiles;
        if (ipcDataCache != null) {
            return ipcDataCache.query(num);
        }
        synchronized (sProfilesLock) {
            if (sProfiles == null) {
                sProfiles = new IpcDataCache<>(new IpcDataCache.Config(32, "system_server", "user_manager_user_data", "Profiles"), remoteCall, bypassCall);
            }
        }
        return sProfiles.query(num);
    }

    public static List<UserInfo> getProfiles(IpcDataCache.RemoteCall<Integer, List<UserInfo>> remoteCall, Integer num) {
        IpcDataCache<Integer, List<UserInfo>> ipcDataCache = sProfiles;
        if (ipcDataCache != null) {
            return ipcDataCache.query(num);
        }
        synchronized (sProfilesLock) {
            if (sProfiles == null) {
                sProfiles = new IpcDataCache<>(new IpcDataCache.Config(32, "system_server", "user_manager_user_data", "Profiles"), remoteCall);
            }
        }
        return sProfiles.query(num);
    }

    public static final void invalidateProfiles() {
        IpcDataCache.invalidateCache("system_server", "user_manager_user_data");
    }

    public static int[] getProfileIdsWithDisabled(IpcDataCache.RemoteCall<Integer, int[]> remoteCall, IpcDataCache.BypassCall<Integer> bypassCall, Integer num) {
        IpcDataCache<Integer, int[]> ipcDataCache = sProfileIdsWithDisabled;
        if (ipcDataCache != null) {
            return ipcDataCache.query(num);
        }
        synchronized (sProfileIdsWithDisabledLock) {
            if (sProfileIdsWithDisabled == null) {
                sProfileIdsWithDisabled = new IpcDataCache<>(new IpcDataCache.Config(32, "system_server", "user_manager_users", "ProfileIdsWithDisabled"), remoteCall, bypassCall);
            }
        }
        return sProfileIdsWithDisabled.query(num);
    }

    public static int[] getProfileIdsWithDisabled(IpcDataCache.RemoteCall<Integer, int[]> remoteCall, Integer num) {
        IpcDataCache<Integer, int[]> ipcDataCache = sProfileIdsWithDisabled;
        if (ipcDataCache != null) {
            return ipcDataCache.query(num);
        }
        synchronized (sProfileIdsWithDisabledLock) {
            if (sProfileIdsWithDisabled == null) {
                sProfileIdsWithDisabled = new IpcDataCache<>(new IpcDataCache.Config(32, "system_server", "user_manager_users", "ProfileIdsWithDisabled"), remoteCall);
            }
        }
        return sProfileIdsWithDisabled.query(num);
    }

    public static final void invalidateProfileIdsWithDisabled() {
        IpcDataCache.invalidateCache("system_server", "user_manager_users");
    }

    public static int[] getEnabledProfileIds(IpcDataCache.RemoteCall<Integer, int[]> remoteCall, IpcDataCache.BypassCall<Integer> bypassCall, Integer num) {
        IpcDataCache<Integer, int[]> ipcDataCache = sEnabledProfileIds;
        if (ipcDataCache != null) {
            return ipcDataCache.query(num);
        }
        synchronized (sEnabledProfileIdsLock) {
            if (sEnabledProfileIds == null) {
                sEnabledProfileIds = new IpcDataCache<>(new IpcDataCache.Config(32, "system_server", "user_manager_users_enabled", "EnabledProfileIds"), remoteCall, bypassCall);
            }
        }
        return sEnabledProfileIds.query(num);
    }

    public static int[] getEnabledProfileIds(IpcDataCache.RemoteCall<Integer, int[]> remoteCall, Integer num) {
        IpcDataCache<Integer, int[]> ipcDataCache = sEnabledProfileIds;
        if (ipcDataCache != null) {
            return ipcDataCache.query(num);
        }
        synchronized (sEnabledProfileIdsLock) {
            if (sEnabledProfileIds == null) {
                sEnabledProfileIds = new IpcDataCache<>(new IpcDataCache.Config(32, "system_server", "user_manager_users_enabled", "EnabledProfileIds"), remoteCall);
            }
        }
        return sEnabledProfileIds.query(num);
    }

    public static final void invalidateEnabledProfileIds() {
        IpcDataCache.invalidateCache("system_server", "user_manager_users_enabled");
    }

    public static UserHandle getProfileParent(IpcDataCache.RemoteCall<UserHandle, UserHandle> remoteCall, IpcDataCache.BypassCall<UserHandle> bypassCall, UserHandle userHandle) {
        IpcDataCache<UserHandle, UserHandle> ipcDataCache = sProfileParent;
        if (ipcDataCache != null) {
            return ipcDataCache.query(userHandle);
        }
        synchronized (sProfileParentLock) {
            if (sProfileParent == null) {
                sProfileParent = new IpcDataCache<>(new IpcDataCache.Config(32, "system_server", "user_manager_users", "ProfileParent"), remoteCall, bypassCall);
            }
        }
        return sProfileParent.query(userHandle);
    }

    public static UserHandle getProfileParent(IpcDataCache.RemoteCall<UserHandle, UserHandle> remoteCall, UserHandle userHandle) {
        IpcDataCache<UserHandle, UserHandle> ipcDataCache = sProfileParent;
        if (ipcDataCache != null) {
            return ipcDataCache.query(userHandle);
        }
        synchronized (sProfileParentLock) {
            if (sProfileParent == null) {
                sProfileParent = new IpcDataCache<>(new IpcDataCache.Config(32, "system_server", "user_manager_users", "ProfileParent"), remoteCall);
            }
        }
        return sProfileParent.query(userHandle);
    }

    public static final void invalidateProfileParent() {
        IpcDataCache.invalidateCache("system_server", "user_manager_users");
    }

    public Boolean isQuietModeEnabled(IpcDataCache.RemoteCall<UserHandle, Boolean> remoteCall, IpcDataCache.BypassCall<UserHandle> bypassCall, UserHandle userHandle) {
        IpcDataCache<UserHandle, Boolean> ipcDataCache = this.mQuietModeEnabled;
        if (ipcDataCache != null) {
            return ipcDataCache.query(userHandle);
        }
        synchronized (this.mQuietModeEnabledLock) {
            if (this.mQuietModeEnabled == null) {
                this.mQuietModeEnabled = new IpcDataCache<>(new IpcDataCache.Config(32, "system_server", "user_manager_quiet_mode_enabled", "QuietModeEnabled"), remoteCall, bypassCall);
            }
        }
        return this.mQuietModeEnabled.query(userHandle);
    }

    public Boolean isQuietModeEnabled(IpcDataCache.RemoteCall<UserHandle, Boolean> remoteCall, UserHandle userHandle) {
        IpcDataCache<UserHandle, Boolean> ipcDataCache = this.mQuietModeEnabled;
        if (ipcDataCache != null) {
            return ipcDataCache.query(userHandle);
        }
        synchronized (this.mQuietModeEnabledLock) {
            if (this.mQuietModeEnabled == null) {
                this.mQuietModeEnabled = new IpcDataCache<>(new IpcDataCache.Config(32, "system_server", "user_manager_quiet_mode_enabled", "QuietModeEnabled"), remoteCall);
            }
        }
        return this.mQuietModeEnabled.query(userHandle);
    }

    public static final void invalidateQuietModeEnabled() {
        IpcDataCache.invalidateCache("system_server", "user_manager_quiet_mode_enabled");
    }

    public Integer getUserSerialNumber(IpcDataCache.RemoteCall<Integer, Integer> remoteCall, IpcDataCache.BypassCall<Integer> bypassCall, Integer num) {
        IpcDataCache<Integer, Integer> ipcDataCache = this.mUserSerialNumber;
        if (ipcDataCache != null) {
            return ipcDataCache.query(num);
        }
        synchronized (this.mUserSerialNumberLock) {
            if (this.mUserSerialNumber == null) {
                this.mUserSerialNumber = new IpcDataCache<>(new IpcDataCache.Config(32, "system_server", "user_manager_users", "UserSerialNumber"), remoteCall, bypassCall);
            }
        }
        return this.mUserSerialNumber.query(num);
    }

    public Integer getUserSerialNumber(IpcDataCache.RemoteCall<Integer, Integer> remoteCall, Integer num) {
        IpcDataCache<Integer, Integer> ipcDataCache = this.mUserSerialNumber;
        if (ipcDataCache != null) {
            return ipcDataCache.query(num);
        }
        synchronized (this.mUserSerialNumberLock) {
            if (this.mUserSerialNumber == null) {
                this.mUserSerialNumber = new IpcDataCache<>(new IpcDataCache.Config(32, "system_server", "user_manager_users", "UserSerialNumber"), remoteCall);
            }
        }
        return this.mUserSerialNumber.query(num);
    }

    public static final void invalidateUserSerialNumber() {
        IpcDataCache.invalidateCache("system_server", "user_manager_users");
    }

    public static void initCache() {
        invalidateUserUnlocked();
        invalidateUserUnlockingOrUnlocked();
        invalidateUserInfo();
        invalidateUserPropertiesFromQuery();
        invalidateUserRestrictionFromQuery();
        invalidateProfiles();
        invalidateProfileIdsWithDisabled();
        invalidateEnabledProfileIds();
        invalidateProfileParent();
        invalidateQuietModeEnabled();
        invalidateUserSerialNumber();
    }
}
