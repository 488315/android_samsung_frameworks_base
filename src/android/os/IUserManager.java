package android.os;

import android.content.IntentSender;
import android.content.pm.UserInfo;
import android.content.pm.UserProperties;
import android.graphics.Bitmap;
import android.os.IUserRestrictionsListener;
import android.os.UserManager;
import java.util.List;

/* loaded from: classes3.dex */
public interface IUserManager extends IInterface {

    public static class Default implements IUserManager {
        @Override // android.os.IUserManager
        public void addUserRestrictionsListener(IUserRestrictionsListener iUserRestrictionsListener) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.IUserManager
        public boolean canAddMoreManagedProfiles(int i, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public boolean canAddMoreProfilesToUser(String str, int i, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public boolean canAddMoreUsersOfType(String str) throws RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public boolean canAddPrivateProfile(int i) throws RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public boolean canHaveRestrictedProfile(int i) throws RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public void clearSeedAccountData(int i) throws RemoteException {
        }

        @Override // android.os.IUserManager
        public UserInfo createProfileForUserEvenWhenDisallowedWithThrow(String str, String str2, int i, int i2, String[] strArr) throws RemoteException {
            return null;
        }

        @Override // android.os.IUserManager
        public UserInfo createProfileForUserWithThrow(String str, String str2, int i, int i2, String[] strArr) throws RemoteException {
            return null;
        }

        @Override // android.os.IUserManager
        public UserInfo createRestrictedProfileWithThrow(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.os.IUserManager
        public UserHandle createUserWithAttributes(String str, String str2, int i, Bitmap bitmap, String str3, String str4, PersistableBundle persistableBundle) throws RemoteException {
            return null;
        }

        @Override // android.os.IUserManager
        public UserInfo createUserWithThrow(String str, String str2, int i) throws RemoteException {
            return null;
        }

        @Override // android.os.IUserManager
        public void evictCredentialEncryptionKey(int i) throws RemoteException {
        }

        @Override // android.os.IUserManager
        public Bundle getApplicationRestrictions(String str) throws RemoteException {
            return null;
        }

        @Override // android.os.IUserManager
        public Bundle getApplicationRestrictionsForUser(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.os.IUserManager
        public int getBootUser() throws RemoteException {
            return 0;
        }

        @Override // android.os.IUserManager
        public int getCommunalProfileId() throws RemoteException {
            return 0;
        }

        @Override // android.os.IUserManager
        public int getCredentialOwnerProfile(int i) throws RemoteException {
            return 0;
        }

        @Override // android.os.IUserManager
        public Bundle getDefaultGuestRestrictions() throws RemoteException {
            return null;
        }

        @Override // android.os.IUserManager
        public List<UserInfo> getGuestUsers() throws RemoteException {
            return null;
        }

        @Override // android.os.IUserManager
        public int getMainDisplayIdAssignedToUser(int i) throws RemoteException {
            return 0;
        }

        @Override // android.os.IUserManager
        public int getMainUserId() throws RemoteException {
            return 0;
        }

        @Override // android.os.IUserManager
        public String[] getPreInstallableSystemPackages(String str) throws RemoteException {
            return null;
        }

        @Override // android.os.IUserManager
        public int getPreviousFullUserToEnterForeground() throws RemoteException {
            return 0;
        }

        @Override // android.os.IUserManager
        public UserInfo getPrimaryUser() throws RemoteException {
            return null;
        }

        @Override // android.os.IUserManager
        public int getProfileAccessibilityLabelResId(int i) throws RemoteException {
            return 0;
        }

        @Override // android.os.IUserManager
        public int[] getProfileIds(int i, boolean z) throws RemoteException {
            return null;
        }

        @Override // android.os.IUserManager
        public int[] getProfileIdsExcludingHidden(int i, boolean z) throws RemoteException {
            return null;
        }

        @Override // android.os.IUserManager
        public int getProfileLabelResId(int i) throws RemoteException {
            return 0;
        }

        @Override // android.os.IUserManager
        public UserInfo getProfileParent(int i) throws RemoteException {
            return null;
        }

        @Override // android.os.IUserManager
        public int getProfileParentId(int i) throws RemoteException {
            return 0;
        }

        @Override // android.os.IUserManager
        public String getProfileType(int i) throws RemoteException {
            return null;
        }

        @Override // android.os.IUserManager
        public List<UserInfo> getProfiles(int i, boolean z) throws RemoteException {
            return null;
        }

        @Override // android.os.IUserManager
        public int getRemainingCreatableProfileCount(String str, int i) throws RemoteException {
            return 0;
        }

        @Override // android.os.IUserManager
        public int getRemainingCreatableUserCount(String str) throws RemoteException {
            return 0;
        }

        @Override // android.os.IUserManager
        public String getSeedAccountName(int i) throws RemoteException {
            return null;
        }

        @Override // android.os.IUserManager
        public PersistableBundle getSeedAccountOptions(int i) throws RemoteException {
            return null;
        }

        @Override // android.os.IUserManager
        public String getSeedAccountType(int i) throws RemoteException {
            return null;
        }

        @Override // android.os.IUserManager
        public String getUserAccount(int i) throws RemoteException {
            return null;
        }

        @Override // android.os.IUserManager
        public int getUserBadgeColorResId(int i) throws RemoteException {
            return 0;
        }

        @Override // android.os.IUserManager
        public int getUserBadgeDarkColorResId(int i) throws RemoteException {
            return 0;
        }

        @Override // android.os.IUserManager
        public int getUserBadgeLabelResId(int i) throws RemoteException {
            return 0;
        }

        @Override // android.os.IUserManager
        public int getUserBadgeNoBackgroundResId(int i) throws RemoteException {
            return 0;
        }

        @Override // android.os.IUserManager
        public int getUserBadgeResId(int i) throws RemoteException {
            return 0;
        }

        @Override // android.os.IUserManager
        public long getUserCreationTime(int i) throws RemoteException {
            return 0L;
        }

        @Override // android.os.IUserManager
        public int getUserHandle(int i) throws RemoteException {
            return 0;
        }

        @Override // android.os.IUserManager
        public ParcelFileDescriptor getUserIcon(int i) throws RemoteException {
            return null;
        }

        @Override // android.os.IUserManager
        public int getUserIconBadgeResId(int i) throws RemoteException {
            return 0;
        }

        @Override // android.os.IUserManager
        public UserInfo getUserInfo(int i) throws RemoteException {
            return null;
        }

        @Override // android.os.IUserManager
        public int getUserLogoutability(int i) throws RemoteException {
            return 0;
        }

        @Override // android.os.IUserManager
        public String getUserName() throws RemoteException {
            return null;
        }

        @Override // android.os.IUserManager
        public UserProperties getUserPropertiesCopy(int i) throws RemoteException {
            return null;
        }

        @Override // android.os.IUserManager
        public int getUserRestrictionSource(String str, int i) throws RemoteException {
            return 0;
        }

        @Override // android.os.IUserManager
        public List<UserManager.EnforcingUser> getUserRestrictionSources(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.os.IUserManager
        public Bundle getUserRestrictions(int i) throws RemoteException {
            return null;
        }

        @Override // android.os.IUserManager
        public int getUserSerialNumber(int i) throws RemoteException {
            return 0;
        }

        @Override // android.os.IUserManager
        public long getUserStartRealtime() throws RemoteException {
            return 0L;
        }

        @Override // android.os.IUserManager
        public int getUserStatusBarIconResId(int i) throws RemoteException {
            return 0;
        }

        @Override // android.os.IUserManager
        public int getUserSwitchability(int i) throws RemoteException {
            return 0;
        }

        @Override // android.os.IUserManager
        public long getUserUnlockRealtime() throws RemoteException {
            return 0L;
        }

        @Override // android.os.IUserManager
        public List<UserInfo> getUsers(boolean z, boolean z2, boolean z3) throws RemoteException {
            return null;
        }

        @Override // android.os.IUserManager
        public int[] getVisibleUsers() throws RemoteException {
            return null;
        }

        @Override // android.os.IUserManager
        public boolean hasBadge(int i) throws RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public boolean hasBaseUserRestriction(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public boolean hasRestrictedProfiles(int i) throws RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public boolean hasUserRestriction(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public boolean hasUserRestrictionOnAnyUser(String str) throws RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public boolean isAdminUser(int i) throws RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public boolean isDemoUser(int i) throws RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public boolean isForegroundUserAdmin() throws RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public boolean isHeadlessSystemUserMode() throws RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public boolean isPreCreated(int i) throws RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public boolean isQuietModeEnabled(int i) throws RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public boolean isRestricted(int i) throws RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public boolean isSameProfileGroup(int i, int i2) throws RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public boolean isSettingRestrictedForUser(String str, int i, String str2, int i2) throws RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public boolean isUserForeground(int i) throws RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public boolean isUserNameSet(int i) throws RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public boolean isUserOfType(int i, String str) throws RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public boolean isUserRunning(int i) throws RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public boolean isUserSwitcherEnabled(boolean z, int i) throws RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public boolean isUserTypeEnabled(String str) throws RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public boolean isUserUnlocked(int i) throws RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public boolean isUserUnlockingOrUnlocked(int i) throws RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public boolean isUserVisible(int i) throws RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public boolean markGuestForDeletion(int i) throws RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public UserInfo preCreateUserWithThrow(String str) throws RemoteException {
            return null;
        }

        @Override // android.os.IUserManager
        public boolean removeUser(int i) throws RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public boolean removeUserEvenWhenDisallowed(int i) throws RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public int removeUserWhenPossible(int i, boolean z) throws RemoteException {
            return 0;
        }

        @Override // android.os.IUserManager
        public boolean requestQuietModeEnabled(String str, boolean z, int i, IntentSender intentSender, int i2) throws RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public void revokeUserAdmin(int i) throws RemoteException {
        }

        @Override // android.os.IUserManager
        public void setApplicationRestrictions(String str, Bundle bundle, int i) throws RemoteException {
        }

        @Override // android.os.IUserManager
        public void setBootUser(int i) throws RemoteException {
        }

        @Override // android.os.IUserManager
        public void setDefaultGuestRestrictions(Bundle bundle) throws RemoteException {
        }

        @Override // android.os.IUserManager
        public void setSeedAccountData(int i, String str, String str2, PersistableBundle persistableBundle, boolean z) throws RemoteException {
        }

        @Override // android.os.IUserManager
        public void setUserAccount(int i, String str) throws RemoteException {
        }

        @Override // android.os.IUserManager
        public void setUserAdmin(int i) throws RemoteException {
        }

        @Override // android.os.IUserManager
        public void setUserEnabled(int i) throws RemoteException {
        }

        @Override // android.os.IUserManager
        public boolean setUserEphemeral(int i, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public void setUserIcon(int i, Bitmap bitmap) throws RemoteException {
        }

        @Override // android.os.IUserManager
        public void setUserName(int i, String str) throws RemoteException {
        }

        @Override // android.os.IUserManager
        public void setUserRestriction(String str, boolean z, int i) throws RemoteException {
        }

        @Override // android.os.IUserManager
        public boolean someUserHasAccount(String str, String str2) throws RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public boolean someUserHasSeedAccount(String str, String str2) throws RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public boolean updateUserInfo(int i, Bundle bundle) throws RemoteException {
            return false;
        }
    }

    void addUserRestrictionsListener(IUserRestrictionsListener iUserRestrictionsListener) throws RemoteException;

    boolean canAddMoreManagedProfiles(int i, boolean z) throws RemoteException;

    boolean canAddMoreProfilesToUser(String str, int i, boolean z) throws RemoteException;

    boolean canAddMoreUsersOfType(String str) throws RemoteException;

    boolean canAddPrivateProfile(int i) throws RemoteException;

    boolean canHaveRestrictedProfile(int i) throws RemoteException;

    void clearSeedAccountData(int i) throws RemoteException;

    UserInfo createProfileForUserEvenWhenDisallowedWithThrow(String str, String str2, int i, int i2, String[] strArr) throws RemoteException;

    UserInfo createProfileForUserWithThrow(String str, String str2, int i, int i2, String[] strArr) throws RemoteException;

    UserInfo createRestrictedProfileWithThrow(String str, int i) throws RemoteException;

    UserHandle createUserWithAttributes(String str, String str2, int i, Bitmap bitmap, String str3, String str4, PersistableBundle persistableBundle) throws RemoteException;

    UserInfo createUserWithThrow(String str, String str2, int i) throws RemoteException;

    void evictCredentialEncryptionKey(int i) throws RemoteException;

    Bundle getApplicationRestrictions(String str) throws RemoteException;

    Bundle getApplicationRestrictionsForUser(String str, int i) throws RemoteException;

    int getBootUser() throws RemoteException;

    int getCommunalProfileId() throws RemoteException;

    int getCredentialOwnerProfile(int i) throws RemoteException;

    Bundle getDefaultGuestRestrictions() throws RemoteException;

    List<UserInfo> getGuestUsers() throws RemoteException;

    int getMainDisplayIdAssignedToUser(int i) throws RemoteException;

    int getMainUserId() throws RemoteException;

    String[] getPreInstallableSystemPackages(String str) throws RemoteException;

    int getPreviousFullUserToEnterForeground() throws RemoteException;

    UserInfo getPrimaryUser() throws RemoteException;

    int getProfileAccessibilityLabelResId(int i) throws RemoteException;

    int[] getProfileIds(int i, boolean z) throws RemoteException;

    int[] getProfileIdsExcludingHidden(int i, boolean z) throws RemoteException;

    int getProfileLabelResId(int i) throws RemoteException;

    UserInfo getProfileParent(int i) throws RemoteException;

    int getProfileParentId(int i) throws RemoteException;

    String getProfileType(int i) throws RemoteException;

    List<UserInfo> getProfiles(int i, boolean z) throws RemoteException;

    int getRemainingCreatableProfileCount(String str, int i) throws RemoteException;

    int getRemainingCreatableUserCount(String str) throws RemoteException;

    String getSeedAccountName(int i) throws RemoteException;

    PersistableBundle getSeedAccountOptions(int i) throws RemoteException;

    String getSeedAccountType(int i) throws RemoteException;

    String getUserAccount(int i) throws RemoteException;

    int getUserBadgeColorResId(int i) throws RemoteException;

    int getUserBadgeDarkColorResId(int i) throws RemoteException;

    int getUserBadgeLabelResId(int i) throws RemoteException;

    int getUserBadgeNoBackgroundResId(int i) throws RemoteException;

    int getUserBadgeResId(int i) throws RemoteException;

    long getUserCreationTime(int i) throws RemoteException;

    int getUserHandle(int i) throws RemoteException;

    ParcelFileDescriptor getUserIcon(int i) throws RemoteException;

    int getUserIconBadgeResId(int i) throws RemoteException;

    UserInfo getUserInfo(int i) throws RemoteException;

    int getUserLogoutability(int i) throws RemoteException;

    String getUserName() throws RemoteException;

    UserProperties getUserPropertiesCopy(int i) throws RemoteException;

    int getUserRestrictionSource(String str, int i) throws RemoteException;

    List<UserManager.EnforcingUser> getUserRestrictionSources(String str, int i) throws RemoteException;

    Bundle getUserRestrictions(int i) throws RemoteException;

    int getUserSerialNumber(int i) throws RemoteException;

    long getUserStartRealtime() throws RemoteException;

    int getUserStatusBarIconResId(int i) throws RemoteException;

    int getUserSwitchability(int i) throws RemoteException;

    long getUserUnlockRealtime() throws RemoteException;

    List<UserInfo> getUsers(boolean z, boolean z2, boolean z3) throws RemoteException;

    int[] getVisibleUsers() throws RemoteException;

    boolean hasBadge(int i) throws RemoteException;

    boolean hasBaseUserRestriction(String str, int i) throws RemoteException;

    boolean hasRestrictedProfiles(int i) throws RemoteException;

    boolean hasUserRestriction(String str, int i) throws RemoteException;

    boolean hasUserRestrictionOnAnyUser(String str) throws RemoteException;

    boolean isAdminUser(int i) throws RemoteException;

    boolean isDemoUser(int i) throws RemoteException;

    boolean isForegroundUserAdmin() throws RemoteException;

    boolean isHeadlessSystemUserMode() throws RemoteException;

    boolean isPreCreated(int i) throws RemoteException;

    boolean isQuietModeEnabled(int i) throws RemoteException;

    boolean isRestricted(int i) throws RemoteException;

    boolean isSameProfileGroup(int i, int i2) throws RemoteException;

    boolean isSettingRestrictedForUser(String str, int i, String str2, int i2) throws RemoteException;

    boolean isUserForeground(int i) throws RemoteException;

    boolean isUserNameSet(int i) throws RemoteException;

    boolean isUserOfType(int i, String str) throws RemoteException;

    boolean isUserRunning(int i) throws RemoteException;

    boolean isUserSwitcherEnabled(boolean z, int i) throws RemoteException;

    boolean isUserTypeEnabled(String str) throws RemoteException;

    boolean isUserUnlocked(int i) throws RemoteException;

    boolean isUserUnlockingOrUnlocked(int i) throws RemoteException;

    boolean isUserVisible(int i) throws RemoteException;

    boolean markGuestForDeletion(int i) throws RemoteException;

    UserInfo preCreateUserWithThrow(String str) throws RemoteException;

    boolean removeUser(int i) throws RemoteException;

    boolean removeUserEvenWhenDisallowed(int i) throws RemoteException;

    int removeUserWhenPossible(int i, boolean z) throws RemoteException;

    boolean requestQuietModeEnabled(String str, boolean z, int i, IntentSender intentSender, int i2) throws RemoteException;

    void revokeUserAdmin(int i) throws RemoteException;

    void setApplicationRestrictions(String str, Bundle bundle, int i) throws RemoteException;

    void setBootUser(int i) throws RemoteException;

    void setDefaultGuestRestrictions(Bundle bundle) throws RemoteException;

    void setSeedAccountData(int i, String str, String str2, PersistableBundle persistableBundle, boolean z) throws RemoteException;

    void setUserAccount(int i, String str) throws RemoteException;

    void setUserAdmin(int i) throws RemoteException;

    void setUserEnabled(int i) throws RemoteException;

    boolean setUserEphemeral(int i, boolean z) throws RemoteException;

    void setUserIcon(int i, Bitmap bitmap) throws RemoteException;

    void setUserName(int i, String str) throws RemoteException;

    void setUserRestriction(String str, boolean z, int i) throws RemoteException;

    boolean someUserHasAccount(String str, String str2) throws RemoteException;

    boolean someUserHasSeedAccount(String str, String str2) throws RemoteException;

    boolean updateUserInfo(int i, Bundle bundle) throws RemoteException;

    public static abstract class Stub extends Binder implements IUserManager {
        public static final String DESCRIPTOR = "android.os.IUserManager";
        static final int TRANSACTION_addUserRestrictionsListener = 54;
        static final int TRANSACTION_canAddMoreManagedProfiles = 29;
        static final int TRANSACTION_canAddMoreProfilesToUser = 28;
        static final int TRANSACTION_canAddMoreUsersOfType = 25;
        static final int TRANSACTION_canAddPrivateProfile = 44;
        static final int TRANSACTION_canHaveRestrictedProfile = 43;
        static final int TRANSACTION_clearSeedAccountData = 70;
        static final int TRANSACTION_createProfileForUserEvenWhenDisallowedWithThrow = 77;
        static final int TRANSACTION_createProfileForUserWithThrow = 5;
        static final int TRANSACTION_createRestrictedProfileWithThrow = 6;
        static final int TRANSACTION_createUserWithAttributes = 65;
        static final int TRANSACTION_createUserWithThrow = 3;
        static final int TRANSACTION_evictCredentialEncryptionKey = 11;
        static final int TRANSACTION_getApplicationRestrictions = 57;
        static final int TRANSACTION_getApplicationRestrictionsForUser = 58;
        static final int TRANSACTION_getBootUser = 104;
        static final int TRANSACTION_getCommunalProfileId = 19;
        static final int TRANSACTION_getCredentialOwnerProfile = 1;
        static final int TRANSACTION_getDefaultGuestRestrictions = 60;
        static final int TRANSACTION_getGuestUsers = 63;
        static final int TRANSACTION_getMainDisplayIdAssignedToUser = 94;
        static final int TRANSACTION_getMainUserId = 18;
        static final int TRANSACTION_getPreInstallableSystemPackages = 7;
        static final int TRANSACTION_getPreviousFullUserToEnterForeground = 20;
        static final int TRANSACTION_getPrimaryUser = 17;
        static final int TRANSACTION_getProfileAccessibilityLabelResId = 88;
        static final int TRANSACTION_getProfileIds = 23;
        static final int TRANSACTION_getProfileIdsExcludingHidden = 105;
        static final int TRANSACTION_getProfileLabelResId = 87;
        static final int TRANSACTION_getProfileParent = 30;
        static final int TRANSACTION_getProfileParentId = 2;
        static final int TRANSACTION_getProfileType = 73;
        static final int TRANSACTION_getProfiles = 22;
        static final int TRANSACTION_getRemainingCreatableProfileCount = 27;
        static final int TRANSACTION_getRemainingCreatableUserCount = 26;
        static final int TRANSACTION_getSeedAccountName = 67;
        static final int TRANSACTION_getSeedAccountOptions = 69;
        static final int TRANSACTION_getSeedAccountType = 68;
        static final int TRANSACTION_getUserAccount = 36;
        static final int TRANSACTION_getUserBadgeColorResId = 83;
        static final int TRANSACTION_getUserBadgeDarkColorResId = 84;
        static final int TRANSACTION_getUserBadgeLabelResId = 82;
        static final int TRANSACTION_getUserBadgeNoBackgroundResId = 81;
        static final int TRANSACTION_getUserBadgeResId = 80;
        static final int TRANSACTION_getUserCreationTime = 38;
        static final int TRANSACTION_getUserHandle = 46;
        static final int TRANSACTION_getUserIcon = 16;
        static final int TRANSACTION_getUserIconBadgeResId = 79;
        static final int TRANSACTION_getUserInfo = 34;
        static final int TRANSACTION_getUserLogoutability = 41;
        static final int TRANSACTION_getUserName = 99;
        static final int TRANSACTION_getUserPropertiesCopy = 35;
        static final int TRANSACTION_getUserRestrictionSource = 47;
        static final int TRANSACTION_getUserRestrictionSources = 48;
        static final int TRANSACTION_getUserRestrictions = 49;
        static final int TRANSACTION_getUserSerialNumber = 45;
        static final int TRANSACTION_getUserStartRealtime = 100;
        static final int TRANSACTION_getUserStatusBarIconResId = 85;
        static final int TRANSACTION_getUserSwitchability = 39;
        static final int TRANSACTION_getUserUnlockRealtime = 101;
        static final int TRANSACTION_getUsers = 21;
        static final int TRANSACTION_getVisibleUsers = 93;
        static final int TRANSACTION_hasBadge = 86;
        static final int TRANSACTION_hasBaseUserRestriction = 50;
        static final int TRANSACTION_hasRestrictedProfiles = 97;
        static final int TRANSACTION_hasUserRestriction = 51;
        static final int TRANSACTION_hasUserRestrictionOnAnyUser = 52;
        static final int TRANSACTION_isAdminUser = 75;
        static final int TRANSACTION_isDemoUser = 74;
        static final int TRANSACTION_isForegroundUserAdmin = 95;
        static final int TRANSACTION_isHeadlessSystemUserMode = 32;
        static final int TRANSACTION_isPreCreated = 76;
        static final int TRANSACTION_isQuietModeEnabled = 64;
        static final int TRANSACTION_isRestricted = 42;
        static final int TRANSACTION_isSameProfileGroup = 31;
        static final int TRANSACTION_isSettingRestrictedForUser = 53;
        static final int TRANSACTION_isUserForeground = 91;
        static final int TRANSACTION_isUserNameSet = 96;
        static final int TRANSACTION_isUserOfType = 33;
        static final int TRANSACTION_isUserRunning = 90;
        static final int TRANSACTION_isUserSwitcherEnabled = 40;
        static final int TRANSACTION_isUserTypeEnabled = 24;
        static final int TRANSACTION_isUserUnlocked = 89;
        static final int TRANSACTION_isUserUnlockingOrUnlocked = 78;
        static final int TRANSACTION_isUserVisible = 92;
        static final int TRANSACTION_markGuestForDeletion = 62;
        static final int TRANSACTION_preCreateUserWithThrow = 4;
        static final int TRANSACTION_removeUser = 12;
        static final int TRANSACTION_removeUserEvenWhenDisallowed = 13;
        static final int TRANSACTION_removeUserWhenPossible = 61;
        static final int TRANSACTION_requestQuietModeEnabled = 98;
        static final int TRANSACTION_revokeUserAdmin = 10;
        static final int TRANSACTION_setApplicationRestrictions = 56;
        static final int TRANSACTION_setBootUser = 103;
        static final int TRANSACTION_setDefaultGuestRestrictions = 59;
        static final int TRANSACTION_setSeedAccountData = 66;
        static final int TRANSACTION_setUserAccount = 37;
        static final int TRANSACTION_setUserAdmin = 9;
        static final int TRANSACTION_setUserEnabled = 8;
        static final int TRANSACTION_setUserEphemeral = 102;
        static final int TRANSACTION_setUserIcon = 15;
        static final int TRANSACTION_setUserName = 14;
        static final int TRANSACTION_setUserRestriction = 55;
        static final int TRANSACTION_someUserHasAccount = 72;
        static final int TRANSACTION_someUserHasSeedAccount = 71;
        static final int TRANSACTION_updateUserInfo = 106;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 105;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IUserManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IUserManager)) {
                return (IUserManager) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getCredentialOwnerProfile";
                case 2:
                    return "getProfileParentId";
                case 3:
                    return "createUserWithThrow";
                case 4:
                    return "preCreateUserWithThrow";
                case 5:
                    return "createProfileForUserWithThrow";
                case 6:
                    return "createRestrictedProfileWithThrow";
                case 7:
                    return "getPreInstallableSystemPackages";
                case 8:
                    return "setUserEnabled";
                case 9:
                    return "setUserAdmin";
                case 10:
                    return "revokeUserAdmin";
                case 11:
                    return "evictCredentialEncryptionKey";
                case 12:
                    return "removeUser";
                case 13:
                    return "removeUserEvenWhenDisallowed";
                case 14:
                    return "setUserName";
                case 15:
                    return "setUserIcon";
                case 16:
                    return "getUserIcon";
                case 17:
                    return "getPrimaryUser";
                case 18:
                    return "getMainUserId";
                case 19:
                    return "getCommunalProfileId";
                case 20:
                    return "getPreviousFullUserToEnterForeground";
                case 21:
                    return "getUsers";
                case 22:
                    return "getProfiles";
                case 23:
                    return "getProfileIds";
                case 24:
                    return "isUserTypeEnabled";
                case 25:
                    return "canAddMoreUsersOfType";
                case 26:
                    return "getRemainingCreatableUserCount";
                case 27:
                    return "getRemainingCreatableProfileCount";
                case 28:
                    return "canAddMoreProfilesToUser";
                case 29:
                    return "canAddMoreManagedProfiles";
                case 30:
                    return "getProfileParent";
                case 31:
                    return "isSameProfileGroup";
                case 32:
                    return "isHeadlessSystemUserMode";
                case 33:
                    return "isUserOfType";
                case 34:
                    return "getUserInfo";
                case 35:
                    return "getUserPropertiesCopy";
                case 36:
                    return "getUserAccount";
                case 37:
                    return "setUserAccount";
                case 38:
                    return "getUserCreationTime";
                case 39:
                    return "getUserSwitchability";
                case 40:
                    return "isUserSwitcherEnabled";
                case 41:
                    return "getUserLogoutability";
                case 42:
                    return "isRestricted";
                case 43:
                    return "canHaveRestrictedProfile";
                case 44:
                    return "canAddPrivateProfile";
                case 45:
                    return "getUserSerialNumber";
                case 46:
                    return "getUserHandle";
                case 47:
                    return "getUserRestrictionSource";
                case 48:
                    return "getUserRestrictionSources";
                case 49:
                    return "getUserRestrictions";
                case 50:
                    return "hasBaseUserRestriction";
                case 51:
                    return "hasUserRestriction";
                case 52:
                    return "hasUserRestrictionOnAnyUser";
                case 53:
                    return "isSettingRestrictedForUser";
                case 54:
                    return "addUserRestrictionsListener";
                case 55:
                    return "setUserRestriction";
                case 56:
                    return "setApplicationRestrictions";
                case 57:
                    return "getApplicationRestrictions";
                case 58:
                    return "getApplicationRestrictionsForUser";
                case 59:
                    return "setDefaultGuestRestrictions";
                case 60:
                    return "getDefaultGuestRestrictions";
                case 61:
                    return "removeUserWhenPossible";
                case 62:
                    return "markGuestForDeletion";
                case 63:
                    return "getGuestUsers";
                case 64:
                    return "isQuietModeEnabled";
                case 65:
                    return "createUserWithAttributes";
                case 66:
                    return "setSeedAccountData";
                case 67:
                    return "getSeedAccountName";
                case 68:
                    return "getSeedAccountType";
                case 69:
                    return "getSeedAccountOptions";
                case 70:
                    return "clearSeedAccountData";
                case 71:
                    return "someUserHasSeedAccount";
                case 72:
                    return "someUserHasAccount";
                case 73:
                    return "getProfileType";
                case 74:
                    return "isDemoUser";
                case 75:
                    return "isAdminUser";
                case 76:
                    return "isPreCreated";
                case 77:
                    return "createProfileForUserEvenWhenDisallowedWithThrow";
                case 78:
                    return "isUserUnlockingOrUnlocked";
                case 79:
                    return "getUserIconBadgeResId";
                case 80:
                    return "getUserBadgeResId";
                case 81:
                    return "getUserBadgeNoBackgroundResId";
                case 82:
                    return "getUserBadgeLabelResId";
                case 83:
                    return "getUserBadgeColorResId";
                case 84:
                    return "getUserBadgeDarkColorResId";
                case 85:
                    return "getUserStatusBarIconResId";
                case 86:
                    return "hasBadge";
                case 87:
                    return "getProfileLabelResId";
                case 88:
                    return "getProfileAccessibilityLabelResId";
                case 89:
                    return "isUserUnlocked";
                case 90:
                    return "isUserRunning";
                case 91:
                    return "isUserForeground";
                case 92:
                    return "isUserVisible";
                case 93:
                    return "getVisibleUsers";
                case 94:
                    return "getMainDisplayIdAssignedToUser";
                case 95:
                    return "isForegroundUserAdmin";
                case 96:
                    return "isUserNameSet";
                case 97:
                    return "hasRestrictedProfiles";
                case 98:
                    return "requestQuietModeEnabled";
                case 99:
                    return "getUserName";
                case 100:
                    return "getUserStartRealtime";
                case 101:
                    return "getUserUnlockRealtime";
                case 102:
                    return "setUserEphemeral";
                case 103:
                    return "setBootUser";
                case 104:
                    return "getBootUser";
                case 105:
                    return "getProfileIdsExcludingHidden";
                case 106:
                    return "updateUserInfo";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int credentialOwnerProfile = getCredentialOwnerProfile(i3);
                    parcel2.writeNoException();
                    parcel2.writeInt(credentialOwnerProfile);
                    return true;
                case 2:
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int profileParentId = getProfileParentId(i4);
                    parcel2.writeNoException();
                    parcel2.writeInt(profileParentId);
                    return true;
                case 3:
                    String string = parcel.readString();
                    String string2 = parcel.readString();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    UserInfo userInfoCreateUserWithThrow = createUserWithThrow(string, string2, i5);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(userInfoCreateUserWithThrow, 1);
                    return true;
                case 4:
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    UserInfo userInfoPreCreateUserWithThrow = preCreateUserWithThrow(string3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(userInfoPreCreateUserWithThrow, 1);
                    return true;
                case 5:
                    String string4 = parcel.readString();
                    String string5 = parcel.readString();
                    int i6 = parcel.readInt();
                    int i7 = parcel.readInt();
                    String[] strArrCreateStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    UserInfo userInfoCreateProfileForUserWithThrow = createProfileForUserWithThrow(string4, string5, i6, i7, strArrCreateStringArray);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(userInfoCreateProfileForUserWithThrow, 1);
                    return true;
                case 6:
                    String string6 = parcel.readString();
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    UserInfo userInfoCreateRestrictedProfileWithThrow = createRestrictedProfileWithThrow(string6, i8);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(userInfoCreateRestrictedProfileWithThrow, 1);
                    return true;
                case 7:
                    String string7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String[] preInstallableSystemPackages = getPreInstallableSystemPackages(string7);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(preInstallableSystemPackages);
                    return true;
                case 8:
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setUserEnabled(i9);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setUserAdmin(i10);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    revokeUserAdmin(i11);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    evictCredentialEncryptionKey(i12);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zRemoveUser = removeUser(i13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveUser);
                    return true;
                case 13:
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zRemoveUserEvenWhenDisallowed = removeUserEvenWhenDisallowed(i14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveUserEvenWhenDisallowed);
                    return true;
                case 14:
                    int i15 = parcel.readInt();
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setUserName(i15, string8);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int i16 = parcel.readInt();
                    Bitmap bitmap = (Bitmap) parcel.readTypedObject(Bitmap.CREATOR);
                    parcel.enforceNoDataAvail();
                    setUserIcon(i16, bitmap);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParcelFileDescriptor userIcon = getUserIcon(i17);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(userIcon, 1);
                    return true;
                case 17:
                    UserInfo primaryUser = getPrimaryUser();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(primaryUser, 1);
                    return true;
                case 18:
                    int mainUserId = getMainUserId();
                    parcel2.writeNoException();
                    parcel2.writeInt(mainUserId);
                    return true;
                case 19:
                    int communalProfileId = getCommunalProfileId();
                    parcel2.writeNoException();
                    parcel2.writeInt(communalProfileId);
                    return true;
                case 20:
                    int previousFullUserToEnterForeground = getPreviousFullUserToEnterForeground();
                    parcel2.writeNoException();
                    parcel2.writeInt(previousFullUserToEnterForeground);
                    return true;
                case 21:
                    boolean z = parcel.readBoolean();
                    boolean z2 = parcel.readBoolean();
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    List<UserInfo> users = getUsers(z, z2, z3);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(users, 1);
                    return true;
                case 22:
                    int i18 = parcel.readInt();
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    List<UserInfo> profiles = getProfiles(i18, z4);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(profiles, 1);
                    return true;
                case 23:
                    int i19 = parcel.readInt();
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int[] profileIds = getProfileIds(i19, z5);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(profileIds);
                    return true;
                case 24:
                    String string9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsUserTypeEnabled = isUserTypeEnabled(string9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsUserTypeEnabled);
                    return true;
                case 25:
                    String string10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zCanAddMoreUsersOfType = canAddMoreUsersOfType(string10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCanAddMoreUsersOfType);
                    return true;
                case 26:
                    String string11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int remainingCreatableUserCount = getRemainingCreatableUserCount(string11);
                    parcel2.writeNoException();
                    parcel2.writeInt(remainingCreatableUserCount);
                    return true;
                case 27:
                    String string12 = parcel.readString();
                    int i20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int remainingCreatableProfileCount = getRemainingCreatableProfileCount(string12, i20);
                    parcel2.writeNoException();
                    parcel2.writeInt(remainingCreatableProfileCount);
                    return true;
                case 28:
                    String string13 = parcel.readString();
                    int i21 = parcel.readInt();
                    boolean z6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zCanAddMoreProfilesToUser = canAddMoreProfilesToUser(string13, i21, z6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCanAddMoreProfilesToUser);
                    return true;
                case 29:
                    int i22 = parcel.readInt();
                    boolean z7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zCanAddMoreManagedProfiles = canAddMoreManagedProfiles(i22, z7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCanAddMoreManagedProfiles);
                    return true;
                case 30:
                    int i23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    UserInfo profileParent = getProfileParent(i23);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(profileParent, 1);
                    return true;
                case 31:
                    int i24 = parcel.readInt();
                    int i25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsSameProfileGroup = isSameProfileGroup(i24, i25);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSameProfileGroup);
                    return true;
                case 32:
                    boolean zIsHeadlessSystemUserMode = isHeadlessSystemUserMode();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsHeadlessSystemUserMode);
                    return true;
                case 33:
                    int i26 = parcel.readInt();
                    String string14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsUserOfType = isUserOfType(i26, string14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsUserOfType);
                    return true;
                case 34:
                    int i27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    UserInfo userInfo = getUserInfo(i27);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(userInfo, 1);
                    return true;
                case 35:
                    int i28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    UserProperties userPropertiesCopy = getUserPropertiesCopy(i28);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(userPropertiesCopy, 1);
                    return true;
                case 36:
                    int i29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String userAccount = getUserAccount(i29);
                    parcel2.writeNoException();
                    parcel2.writeString(userAccount);
                    return true;
                case 37:
                    int i30 = parcel.readInt();
                    String string15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setUserAccount(i30, string15);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    int i31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long userCreationTime = getUserCreationTime(i31);
                    parcel2.writeNoException();
                    parcel2.writeLong(userCreationTime);
                    return true;
                case 39:
                    int i32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int userSwitchability = getUserSwitchability(i32);
                    parcel2.writeNoException();
                    parcel2.writeInt(userSwitchability);
                    return true;
                case 40:
                    boolean z8 = parcel.readBoolean();
                    int i33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsUserSwitcherEnabled = isUserSwitcherEnabled(z8, i33);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsUserSwitcherEnabled);
                    return true;
                case 41:
                    int i34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int userLogoutability = getUserLogoutability(i34);
                    parcel2.writeNoException();
                    parcel2.writeInt(userLogoutability);
                    return true;
                case 42:
                    int i35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsRestricted = isRestricted(i35);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsRestricted);
                    return true;
                case 43:
                    int i36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zCanHaveRestrictedProfile = canHaveRestrictedProfile(i36);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCanHaveRestrictedProfile);
                    return true;
                case 44:
                    int i37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zCanAddPrivateProfile = canAddPrivateProfile(i37);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCanAddPrivateProfile);
                    return true;
                case 45:
                    int i38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int userSerialNumber = getUserSerialNumber(i38);
                    parcel2.writeNoException();
                    parcel2.writeInt(userSerialNumber);
                    return true;
                case 46:
                    int i39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int userHandle = getUserHandle(i39);
                    parcel2.writeNoException();
                    parcel2.writeInt(userHandle);
                    return true;
                case 47:
                    String string16 = parcel.readString();
                    int i40 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int userRestrictionSource = getUserRestrictionSource(string16, i40);
                    parcel2.writeNoException();
                    parcel2.writeInt(userRestrictionSource);
                    return true;
                case 48:
                    String string17 = parcel.readString();
                    int i41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<UserManager.EnforcingUser> userRestrictionSources = getUserRestrictionSources(string17, i41);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(userRestrictionSources, 1);
                    return true;
                case 49:
                    int i42 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle userRestrictions = getUserRestrictions(i42);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(userRestrictions, 1);
                    return true;
                case 50:
                    String string18 = parcel.readString();
                    int i43 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zHasBaseUserRestriction = hasBaseUserRestriction(string18, i43);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasBaseUserRestriction);
                    return true;
                case 51:
                    String string19 = parcel.readString();
                    int i44 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zHasUserRestriction = hasUserRestriction(string19, i44);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasUserRestriction);
                    return true;
                case 52:
                    String string20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zHasUserRestrictionOnAnyUser = hasUserRestrictionOnAnyUser(string20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasUserRestrictionOnAnyUser);
                    return true;
                case 53:
                    String string21 = parcel.readString();
                    int i45 = parcel.readInt();
                    String string22 = parcel.readString();
                    int i46 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsSettingRestrictedForUser = isSettingRestrictedForUser(string21, i45, string22, i46);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSettingRestrictedForUser);
                    return true;
                case 54:
                    IUserRestrictionsListener iUserRestrictionsListenerAsInterface = IUserRestrictionsListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addUserRestrictionsListener(iUserRestrictionsListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 55:
                    String string23 = parcel.readString();
                    boolean z9 = parcel.readBoolean();
                    int i47 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setUserRestriction(string23, z9, i47);
                    parcel2.writeNoException();
                    return true;
                case 56:
                    String string24 = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int i48 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setApplicationRestrictions(string24, bundle, i48);
                    parcel2.writeNoException();
                    return true;
                case 57:
                    String string25 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Bundle applicationRestrictions = getApplicationRestrictions(string25);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(applicationRestrictions, 1);
                    return true;
                case 58:
                    String string26 = parcel.readString();
                    int i49 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle applicationRestrictionsForUser = getApplicationRestrictionsForUser(string26, i49);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(applicationRestrictionsForUser, 1);
                    return true;
                case 59:
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDefaultGuestRestrictions(bundle2);
                    parcel2.writeNoException();
                    return true;
                case 60:
                    Bundle defaultGuestRestrictions = getDefaultGuestRestrictions();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(defaultGuestRestrictions, 1);
                    return true;
                case 61:
                    int i50 = parcel.readInt();
                    boolean z10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int iRemoveUserWhenPossible = removeUserWhenPossible(i50, z10);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRemoveUserWhenPossible);
                    return true;
                case 62:
                    int i51 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zMarkGuestForDeletion = markGuestForDeletion(i51);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zMarkGuestForDeletion);
                    return true;
                case 63:
                    List<UserInfo> guestUsers = getGuestUsers();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(guestUsers, 1);
                    return true;
                case 64:
                    int i52 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsQuietModeEnabled = isQuietModeEnabled(i52);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsQuietModeEnabled);
                    return true;
                case 65:
                    String string27 = parcel.readString();
                    String string28 = parcel.readString();
                    int i53 = parcel.readInt();
                    Bitmap bitmap2 = (Bitmap) parcel.readTypedObject(Bitmap.CREATOR);
                    String string29 = parcel.readString();
                    String string30 = parcel.readString();
                    PersistableBundle persistableBundle = (PersistableBundle) parcel.readTypedObject(PersistableBundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    UserHandle userHandleCreateUserWithAttributes = createUserWithAttributes(string27, string28, i53, bitmap2, string29, string30, persistableBundle);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(userHandleCreateUserWithAttributes, 1);
                    return true;
                case 66:
                    int i54 = parcel.readInt();
                    String string31 = parcel.readString();
                    String string32 = parcel.readString();
                    PersistableBundle persistableBundle2 = (PersistableBundle) parcel.readTypedObject(PersistableBundle.CREATOR);
                    boolean z11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSeedAccountData(i54, string31, string32, persistableBundle2, z11);
                    parcel2.writeNoException();
                    return true;
                case 67:
                    int i55 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String seedAccountName = getSeedAccountName(i55);
                    parcel2.writeNoException();
                    parcel2.writeString(seedAccountName);
                    return true;
                case 68:
                    int i56 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String seedAccountType = getSeedAccountType(i56);
                    parcel2.writeNoException();
                    parcel2.writeString(seedAccountType);
                    return true;
                case 69:
                    int i57 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    PersistableBundle seedAccountOptions = getSeedAccountOptions(i57);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(seedAccountOptions, 1);
                    return true;
                case 70:
                    int i58 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearSeedAccountData(i58);
                    parcel2.writeNoException();
                    return true;
                case 71:
                    String string33 = parcel.readString();
                    String string34 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zSomeUserHasSeedAccount = someUserHasSeedAccount(string33, string34);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSomeUserHasSeedAccount);
                    return true;
                case 72:
                    String string35 = parcel.readString();
                    String string36 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zSomeUserHasAccount = someUserHasAccount(string35, string36);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSomeUserHasAccount);
                    return true;
                case 73:
                    int i59 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String profileType = getProfileType(i59);
                    parcel2.writeNoException();
                    parcel2.writeString(profileType);
                    return true;
                case 74:
                    int i60 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsDemoUser = isDemoUser(i60);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsDemoUser);
                    return true;
                case 75:
                    int i61 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsAdminUser = isAdminUser(i61);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAdminUser);
                    return true;
                case 76:
                    int i62 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsPreCreated = isPreCreated(i62);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsPreCreated);
                    return true;
                case 77:
                    String string37 = parcel.readString();
                    String string38 = parcel.readString();
                    int i63 = parcel.readInt();
                    int i64 = parcel.readInt();
                    String[] strArrCreateStringArray2 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    UserInfo userInfoCreateProfileForUserEvenWhenDisallowedWithThrow = createProfileForUserEvenWhenDisallowedWithThrow(string37, string38, i63, i64, strArrCreateStringArray2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(userInfoCreateProfileForUserEvenWhenDisallowedWithThrow, 1);
                    return true;
                case 78:
                    int i65 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsUserUnlockingOrUnlocked = isUserUnlockingOrUnlocked(i65);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsUserUnlockingOrUnlocked);
                    return true;
                case 79:
                    int i66 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int userIconBadgeResId = getUserIconBadgeResId(i66);
                    parcel2.writeNoException();
                    parcel2.writeInt(userIconBadgeResId);
                    return true;
                case 80:
                    int i67 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int userBadgeResId = getUserBadgeResId(i67);
                    parcel2.writeNoException();
                    parcel2.writeInt(userBadgeResId);
                    return true;
                case 81:
                    int i68 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int userBadgeNoBackgroundResId = getUserBadgeNoBackgroundResId(i68);
                    parcel2.writeNoException();
                    parcel2.writeInt(userBadgeNoBackgroundResId);
                    return true;
                case 82:
                    int i69 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int userBadgeLabelResId = getUserBadgeLabelResId(i69);
                    parcel2.writeNoException();
                    parcel2.writeInt(userBadgeLabelResId);
                    return true;
                case 83:
                    int i70 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int userBadgeColorResId = getUserBadgeColorResId(i70);
                    parcel2.writeNoException();
                    parcel2.writeInt(userBadgeColorResId);
                    return true;
                case 84:
                    int i71 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int userBadgeDarkColorResId = getUserBadgeDarkColorResId(i71);
                    parcel2.writeNoException();
                    parcel2.writeInt(userBadgeDarkColorResId);
                    return true;
                case 85:
                    int i72 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int userStatusBarIconResId = getUserStatusBarIconResId(i72);
                    parcel2.writeNoException();
                    parcel2.writeInt(userStatusBarIconResId);
                    return true;
                case 86:
                    int i73 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zHasBadge = hasBadge(i73);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasBadge);
                    return true;
                case 87:
                    int i74 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int profileLabelResId = getProfileLabelResId(i74);
                    parcel2.writeNoException();
                    parcel2.writeInt(profileLabelResId);
                    return true;
                case 88:
                    int i75 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int profileAccessibilityLabelResId = getProfileAccessibilityLabelResId(i75);
                    parcel2.writeNoException();
                    parcel2.writeInt(profileAccessibilityLabelResId);
                    return true;
                case 89:
                    int i76 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsUserUnlocked = isUserUnlocked(i76);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsUserUnlocked);
                    return true;
                case 90:
                    int i77 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsUserRunning = isUserRunning(i77);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsUserRunning);
                    return true;
                case 91:
                    int i78 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsUserForeground = isUserForeground(i78);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsUserForeground);
                    return true;
                case 92:
                    int i79 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsUserVisible = isUserVisible(i79);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsUserVisible);
                    return true;
                case 93:
                    int[] visibleUsers = getVisibleUsers();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(visibleUsers);
                    return true;
                case 94:
                    int i80 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int mainDisplayIdAssignedToUser = getMainDisplayIdAssignedToUser(i80);
                    parcel2.writeNoException();
                    parcel2.writeInt(mainDisplayIdAssignedToUser);
                    return true;
                case 95:
                    boolean zIsForegroundUserAdmin = isForegroundUserAdmin();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsForegroundUserAdmin);
                    return true;
                case 96:
                    int i81 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsUserNameSet = isUserNameSet(i81);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsUserNameSet);
                    return true;
                case 97:
                    int i82 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zHasRestrictedProfiles = hasRestrictedProfiles(i82);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasRestrictedProfiles);
                    return true;
                case 98:
                    String string39 = parcel.readString();
                    boolean z12 = parcel.readBoolean();
                    int i83 = parcel.readInt();
                    IntentSender intentSender = (IntentSender) parcel.readTypedObject(IntentSender.CREATOR);
                    int i84 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zRequestQuietModeEnabled = requestQuietModeEnabled(string39, z12, i83, intentSender, i84);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRequestQuietModeEnabled);
                    return true;
                case 99:
                    String userName = getUserName();
                    parcel2.writeNoException();
                    parcel2.writeString(userName);
                    return true;
                case 100:
                    long userStartRealtime = getUserStartRealtime();
                    parcel2.writeNoException();
                    parcel2.writeLong(userStartRealtime);
                    return true;
                case 101:
                    long userUnlockRealtime = getUserUnlockRealtime();
                    parcel2.writeNoException();
                    parcel2.writeLong(userUnlockRealtime);
                    return true;
                case 102:
                    int i85 = parcel.readInt();
                    boolean z13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean userEphemeral = setUserEphemeral(i85, z13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(userEphemeral);
                    return true;
                case 103:
                    int i86 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setBootUser(i86);
                    parcel2.writeNoException();
                    return true;
                case 104:
                    int bootUser = getBootUser();
                    parcel2.writeNoException();
                    parcel2.writeInt(bootUser);
                    return true;
                case 105:
                    int i87 = parcel.readInt();
                    boolean z14 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int[] profileIdsExcludingHidden = getProfileIdsExcludingHidden(i87, z14);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(profileIdsExcludingHidden);
                    return true;
                case 106:
                    int i88 = parcel.readInt();
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zUpdateUserInfo = updateUserInfo(i88, bundle3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zUpdateUserInfo);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IUserManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.os.IUserManager
            public int getCredentialOwnerProfile(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int getProfileParentId(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public UserInfo createUserWithThrow(String str, String str2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (UserInfo) parcelObtain2.readTypedObject(UserInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public UserInfo preCreateUserWithThrow(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (UserInfo) parcelObtain2.readTypedObject(UserInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public UserInfo createProfileForUserWithThrow(String str, String str2, int i, int i2, String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (UserInfo) parcelObtain2.readTypedObject(UserInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public UserInfo createRestrictedProfileWithThrow(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (UserInfo) parcelObtain2.readTypedObject(UserInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public String[] getPreInstallableSystemPackages(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public void setUserEnabled(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public void setUserAdmin(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public void revokeUserAdmin(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public void evictCredentialEncryptionKey(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean removeUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean removeUserEvenWhenDisallowed(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public void setUserName(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public void setUserIcon(int i, Bitmap bitmap) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(bitmap, 0);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public ParcelFileDescriptor getUserIcon(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParcelFileDescriptor) parcelObtain2.readTypedObject(ParcelFileDescriptor.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public UserInfo getPrimaryUser() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (UserInfo) parcelObtain2.readTypedObject(UserInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int getMainUserId() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int getCommunalProfileId() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int getPreviousFullUserToEnterForeground() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public List<UserInfo> getUsers(boolean z, boolean z2, boolean z3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeBoolean(z3);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(UserInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public List<UserInfo> getProfiles(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(UserInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int[] getProfileIds(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean isUserTypeEnabled(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean canAddMoreUsersOfType(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int getRemainingCreatableUserCount(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int getRemainingCreatableProfileCount(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean canAddMoreProfilesToUser(String str, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean canAddMoreManagedProfiles(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public UserInfo getProfileParent(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (UserInfo) parcelObtain2.readTypedObject(UserInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean isSameProfileGroup(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean isHeadlessSystemUserMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean isUserOfType(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public UserInfo getUserInfo(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (UserInfo) parcelObtain2.readTypedObject(UserInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public UserProperties getUserPropertiesCopy(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (UserProperties) parcelObtain2.readTypedObject(UserProperties.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public String getUserAccount(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public void setUserAccount(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public long getUserCreationTime(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int getUserSwitchability(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean isUserSwitcherEnabled(boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int getUserLogoutability(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean isRestricted(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean canHaveRestrictedProfile(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean canAddPrivateProfile(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int getUserSerialNumber(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int getUserHandle(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int getUserRestrictionSource(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public List<UserManager.EnforcingUser> getUserRestrictionSources(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(UserManager.EnforcingUser.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public Bundle getUserRestrictions(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(49, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean hasBaseUserRestriction(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(50, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean hasUserRestriction(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(51, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean hasUserRestrictionOnAnyUser(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(52, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean isSettingRestrictedForUser(String str, int i, String str2, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(53, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public void addUserRestrictionsListener(IUserRestrictionsListener iUserRestrictionsListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iUserRestrictionsListener);
                    this.mRemote.transact(54, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public void setUserRestriction(String str, boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(55, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public void setApplicationRestrictions(String str, Bundle bundle, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(56, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public Bundle getApplicationRestrictions(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(57, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public Bundle getApplicationRestrictionsForUser(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(58, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public void setDefaultGuestRestrictions(Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(59, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public Bundle getDefaultGuestRestrictions() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(60, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int removeUserWhenPossible(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(61, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean markGuestForDeletion(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(62, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public List<UserInfo> getGuestUsers() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(63, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(UserInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean isQuietModeEnabled(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(64, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public UserHandle createUserWithAttributes(String str, String str2, int i, Bitmap bitmap, String str3, String str4, PersistableBundle persistableBundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(bitmap, 0);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeString(str4);
                    parcelObtain.writeTypedObject(persistableBundle, 0);
                    this.mRemote.transact(65, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (UserHandle) parcelObtain2.readTypedObject(UserHandle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public void setSeedAccountData(int i, String str, String str2, PersistableBundle persistableBundle, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(persistableBundle, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(66, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public String getSeedAccountName(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(67, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public String getSeedAccountType(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(68, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public PersistableBundle getSeedAccountOptions(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(69, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (PersistableBundle) parcelObtain2.readTypedObject(PersistableBundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public void clearSeedAccountData(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(70, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean someUserHasSeedAccount(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(71, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean someUserHasAccount(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(72, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public String getProfileType(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(73, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean isDemoUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(74, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean isAdminUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(75, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean isPreCreated(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(76, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public UserInfo createProfileForUserEvenWhenDisallowedWithThrow(String str, String str2, int i, int i2, String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(77, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (UserInfo) parcelObtain2.readTypedObject(UserInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean isUserUnlockingOrUnlocked(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(78, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int getUserIconBadgeResId(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(79, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int getUserBadgeResId(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(80, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int getUserBadgeNoBackgroundResId(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(81, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int getUserBadgeLabelResId(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(82, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int getUserBadgeColorResId(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(83, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int getUserBadgeDarkColorResId(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(84, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int getUserStatusBarIconResId(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(85, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean hasBadge(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(86, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int getProfileLabelResId(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(87, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int getProfileAccessibilityLabelResId(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(88, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean isUserUnlocked(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(89, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean isUserRunning(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(90, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean isUserForeground(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(91, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean isUserVisible(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(92, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int[] getVisibleUsers() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(93, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int getMainDisplayIdAssignedToUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(94, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean isForegroundUserAdmin() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(95, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean isUserNameSet(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(96, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean hasRestrictedProfiles(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(97, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean requestQuietModeEnabled(String str, boolean z, int i, IntentSender intentSender, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(intentSender, 0);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(98, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public String getUserName() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(99, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public long getUserStartRealtime() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(100, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public long getUserUnlockRealtime() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(101, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean setUserEphemeral(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(102, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public void setBootUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(103, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int getBootUser() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(104, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int[] getProfileIdsExcludingHidden(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(105, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean updateUserInfo(int i, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(106, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
