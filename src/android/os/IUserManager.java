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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IUserManager)) {
                return (IUserManager) queryLocalInterface;
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
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int credentialOwnerProfile = getCredentialOwnerProfile(readInt);
                    parcel2.writeNoException();
                    parcel2.writeInt(credentialOwnerProfile);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int profileParentId = getProfileParentId(readInt2);
                    parcel2.writeNoException();
                    parcel2.writeInt(profileParentId);
                    return true;
                case 3:
                    String readString = parcel.readString();
                    String readString2 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    UserInfo createUserWithThrow = createUserWithThrow(readString, readString2, readInt3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(createUserWithThrow, 1);
                    return true;
                case 4:
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    UserInfo preCreateUserWithThrow = preCreateUserWithThrow(readString3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(preCreateUserWithThrow, 1);
                    return true;
                case 5:
                    String readString4 = parcel.readString();
                    String readString5 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    String[] createStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    UserInfo createProfileForUserWithThrow = createProfileForUserWithThrow(readString4, readString5, readInt4, readInt5, createStringArray);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(createProfileForUserWithThrow, 1);
                    return true;
                case 6:
                    String readString6 = parcel.readString();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    UserInfo createRestrictedProfileWithThrow = createRestrictedProfileWithThrow(readString6, readInt6);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(createRestrictedProfileWithThrow, 1);
                    return true;
                case 7:
                    String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String[] preInstallableSystemPackages = getPreInstallableSystemPackages(readString7);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(preInstallableSystemPackages);
                    return true;
                case 8:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setUserEnabled(readInt7);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setUserAdmin(readInt8);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    revokeUserAdmin(readInt9);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    evictCredentialEncryptionKey(readInt10);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean removeUser = removeUser(readInt11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeUser);
                    return true;
                case 13:
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean removeUserEvenWhenDisallowed = removeUserEvenWhenDisallowed(readInt12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeUserEvenWhenDisallowed);
                    return true;
                case 14:
                    int readInt13 = parcel.readInt();
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setUserName(readInt13, readString8);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int readInt14 = parcel.readInt();
                    Bitmap bitmap = (Bitmap) parcel.readTypedObject(Bitmap.CREATOR);
                    parcel.enforceNoDataAvail();
                    setUserIcon(readInt14, bitmap);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParcelFileDescriptor userIcon = getUserIcon(readInt15);
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
                    boolean readBoolean = parcel.readBoolean();
                    boolean readBoolean2 = parcel.readBoolean();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    List<UserInfo> users = getUsers(readBoolean, readBoolean2, readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(users, 1);
                    return true;
                case 22:
                    int readInt16 = parcel.readInt();
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    List<UserInfo> profiles = getProfiles(readInt16, readBoolean4);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(profiles, 1);
                    return true;
                case 23:
                    int readInt17 = parcel.readInt();
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int[] profileIds = getProfileIds(readInt17, readBoolean5);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(profileIds);
                    return true;
                case 24:
                    String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isUserTypeEnabled = isUserTypeEnabled(readString9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUserTypeEnabled);
                    return true;
                case 25:
                    String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean canAddMoreUsersOfType = canAddMoreUsersOfType(readString10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canAddMoreUsersOfType);
                    return true;
                case 26:
                    String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int remainingCreatableUserCount = getRemainingCreatableUserCount(readString11);
                    parcel2.writeNoException();
                    parcel2.writeInt(remainingCreatableUserCount);
                    return true;
                case 27:
                    String readString12 = parcel.readString();
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int remainingCreatableProfileCount = getRemainingCreatableProfileCount(readString12, readInt18);
                    parcel2.writeNoException();
                    parcel2.writeInt(remainingCreatableProfileCount);
                    return true;
                case 28:
                    String readString13 = parcel.readString();
                    int readInt19 = parcel.readInt();
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean canAddMoreProfilesToUser = canAddMoreProfilesToUser(readString13, readInt19, readBoolean6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canAddMoreProfilesToUser);
                    return true;
                case 29:
                    int readInt20 = parcel.readInt();
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean canAddMoreManagedProfiles = canAddMoreManagedProfiles(readInt20, readBoolean7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canAddMoreManagedProfiles);
                    return true;
                case 30:
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    UserInfo profileParent = getProfileParent(readInt21);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(profileParent, 1);
                    return true;
                case 31:
                    int readInt22 = parcel.readInt();
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isSameProfileGroup = isSameProfileGroup(readInt22, readInt23);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSameProfileGroup);
                    return true;
                case 32:
                    boolean isHeadlessSystemUserMode = isHeadlessSystemUserMode();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isHeadlessSystemUserMode);
                    return true;
                case 33:
                    int readInt24 = parcel.readInt();
                    String readString14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isUserOfType = isUserOfType(readInt24, readString14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUserOfType);
                    return true;
                case 34:
                    int readInt25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    UserInfo userInfo = getUserInfo(readInt25);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(userInfo, 1);
                    return true;
                case 35:
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    UserProperties userPropertiesCopy = getUserPropertiesCopy(readInt26);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(userPropertiesCopy, 1);
                    return true;
                case 36:
                    int readInt27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String userAccount = getUserAccount(readInt27);
                    parcel2.writeNoException();
                    parcel2.writeString(userAccount);
                    return true;
                case 37:
                    int readInt28 = parcel.readInt();
                    String readString15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setUserAccount(readInt28, readString15);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    int readInt29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long userCreationTime = getUserCreationTime(readInt29);
                    parcel2.writeNoException();
                    parcel2.writeLong(userCreationTime);
                    return true;
                case 39:
                    int readInt30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int userSwitchability = getUserSwitchability(readInt30);
                    parcel2.writeNoException();
                    parcel2.writeInt(userSwitchability);
                    return true;
                case 40:
                    boolean readBoolean8 = parcel.readBoolean();
                    int readInt31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isUserSwitcherEnabled = isUserSwitcherEnabled(readBoolean8, readInt31);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUserSwitcherEnabled);
                    return true;
                case 41:
                    int readInt32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int userLogoutability = getUserLogoutability(readInt32);
                    parcel2.writeNoException();
                    parcel2.writeInt(userLogoutability);
                    return true;
                case 42:
                    int readInt33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isRestricted = isRestricted(readInt33);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isRestricted);
                    return true;
                case 43:
                    int readInt34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean canHaveRestrictedProfile = canHaveRestrictedProfile(readInt34);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canHaveRestrictedProfile);
                    return true;
                case 44:
                    int readInt35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean canAddPrivateProfile = canAddPrivateProfile(readInt35);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canAddPrivateProfile);
                    return true;
                case 45:
                    int readInt36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int userSerialNumber = getUserSerialNumber(readInt36);
                    parcel2.writeNoException();
                    parcel2.writeInt(userSerialNumber);
                    return true;
                case 46:
                    int readInt37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int userHandle = getUserHandle(readInt37);
                    parcel2.writeNoException();
                    parcel2.writeInt(userHandle);
                    return true;
                case 47:
                    String readString16 = parcel.readString();
                    int readInt38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int userRestrictionSource = getUserRestrictionSource(readString16, readInt38);
                    parcel2.writeNoException();
                    parcel2.writeInt(userRestrictionSource);
                    return true;
                case 48:
                    String readString17 = parcel.readString();
                    int readInt39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<UserManager.EnforcingUser> userRestrictionSources = getUserRestrictionSources(readString17, readInt39);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(userRestrictionSources, 1);
                    return true;
                case 49:
                    int readInt40 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle userRestrictions = getUserRestrictions(readInt40);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(userRestrictions, 1);
                    return true;
                case 50:
                    String readString18 = parcel.readString();
                    int readInt41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean hasBaseUserRestriction = hasBaseUserRestriction(readString18, readInt41);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasBaseUserRestriction);
                    return true;
                case 51:
                    String readString19 = parcel.readString();
                    int readInt42 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean hasUserRestriction = hasUserRestriction(readString19, readInt42);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasUserRestriction);
                    return true;
                case 52:
                    String readString20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean hasUserRestrictionOnAnyUser = hasUserRestrictionOnAnyUser(readString20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasUserRestrictionOnAnyUser);
                    return true;
                case 53:
                    String readString21 = parcel.readString();
                    int readInt43 = parcel.readInt();
                    String readString22 = parcel.readString();
                    int readInt44 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isSettingRestrictedForUser = isSettingRestrictedForUser(readString21, readInt43, readString22, readInt44);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSettingRestrictedForUser);
                    return true;
                case 54:
                    IUserRestrictionsListener asInterface = IUserRestrictionsListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addUserRestrictionsListener(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 55:
                    String readString23 = parcel.readString();
                    boolean readBoolean9 = parcel.readBoolean();
                    int readInt45 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setUserRestriction(readString23, readBoolean9, readInt45);
                    parcel2.writeNoException();
                    return true;
                case 56:
                    String readString24 = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int readInt46 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setApplicationRestrictions(readString24, bundle, readInt46);
                    parcel2.writeNoException();
                    return true;
                case 57:
                    String readString25 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Bundle applicationRestrictions = getApplicationRestrictions(readString25);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(applicationRestrictions, 1);
                    return true;
                case 58:
                    String readString26 = parcel.readString();
                    int readInt47 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle applicationRestrictionsForUser = getApplicationRestrictionsForUser(readString26, readInt47);
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
                    int readInt48 = parcel.readInt();
                    boolean readBoolean10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int removeUserWhenPossible = removeUserWhenPossible(readInt48, readBoolean10);
                    parcel2.writeNoException();
                    parcel2.writeInt(removeUserWhenPossible);
                    return true;
                case 62:
                    int readInt49 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean markGuestForDeletion = markGuestForDeletion(readInt49);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(markGuestForDeletion);
                    return true;
                case 63:
                    List<UserInfo> guestUsers = getGuestUsers();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(guestUsers, 1);
                    return true;
                case 64:
                    int readInt50 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isQuietModeEnabled = isQuietModeEnabled(readInt50);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isQuietModeEnabled);
                    return true;
                case 65:
                    String readString27 = parcel.readString();
                    String readString28 = parcel.readString();
                    int readInt51 = parcel.readInt();
                    Bitmap bitmap2 = (Bitmap) parcel.readTypedObject(Bitmap.CREATOR);
                    String readString29 = parcel.readString();
                    String readString30 = parcel.readString();
                    PersistableBundle persistableBundle = (PersistableBundle) parcel.readTypedObject(PersistableBundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    UserHandle createUserWithAttributes = createUserWithAttributes(readString27, readString28, readInt51, bitmap2, readString29, readString30, persistableBundle);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(createUserWithAttributes, 1);
                    return true;
                case 66:
                    int readInt52 = parcel.readInt();
                    String readString31 = parcel.readString();
                    String readString32 = parcel.readString();
                    PersistableBundle persistableBundle2 = (PersistableBundle) parcel.readTypedObject(PersistableBundle.CREATOR);
                    boolean readBoolean11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSeedAccountData(readInt52, readString31, readString32, persistableBundle2, readBoolean11);
                    parcel2.writeNoException();
                    return true;
                case 67:
                    int readInt53 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String seedAccountName = getSeedAccountName(readInt53);
                    parcel2.writeNoException();
                    parcel2.writeString(seedAccountName);
                    return true;
                case 68:
                    int readInt54 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String seedAccountType = getSeedAccountType(readInt54);
                    parcel2.writeNoException();
                    parcel2.writeString(seedAccountType);
                    return true;
                case 69:
                    int readInt55 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    PersistableBundle seedAccountOptions = getSeedAccountOptions(readInt55);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(seedAccountOptions, 1);
                    return true;
                case 70:
                    int readInt56 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearSeedAccountData(readInt56);
                    parcel2.writeNoException();
                    return true;
                case 71:
                    String readString33 = parcel.readString();
                    String readString34 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean someUserHasSeedAccount = someUserHasSeedAccount(readString33, readString34);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(someUserHasSeedAccount);
                    return true;
                case 72:
                    String readString35 = parcel.readString();
                    String readString36 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean someUserHasAccount = someUserHasAccount(readString35, readString36);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(someUserHasAccount);
                    return true;
                case 73:
                    int readInt57 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String profileType = getProfileType(readInt57);
                    parcel2.writeNoException();
                    parcel2.writeString(profileType);
                    return true;
                case 74:
                    int readInt58 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isDemoUser = isDemoUser(readInt58);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDemoUser);
                    return true;
                case 75:
                    int readInt59 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isAdminUser = isAdminUser(readInt59);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAdminUser);
                    return true;
                case 76:
                    int readInt60 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isPreCreated = isPreCreated(readInt60);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPreCreated);
                    return true;
                case 77:
                    String readString37 = parcel.readString();
                    String readString38 = parcel.readString();
                    int readInt61 = parcel.readInt();
                    int readInt62 = parcel.readInt();
                    String[] createStringArray2 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    UserInfo createProfileForUserEvenWhenDisallowedWithThrow = createProfileForUserEvenWhenDisallowedWithThrow(readString37, readString38, readInt61, readInt62, createStringArray2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(createProfileForUserEvenWhenDisallowedWithThrow, 1);
                    return true;
                case 78:
                    int readInt63 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isUserUnlockingOrUnlocked = isUserUnlockingOrUnlocked(readInt63);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUserUnlockingOrUnlocked);
                    return true;
                case 79:
                    int readInt64 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int userIconBadgeResId = getUserIconBadgeResId(readInt64);
                    parcel2.writeNoException();
                    parcel2.writeInt(userIconBadgeResId);
                    return true;
                case 80:
                    int readInt65 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int userBadgeResId = getUserBadgeResId(readInt65);
                    parcel2.writeNoException();
                    parcel2.writeInt(userBadgeResId);
                    return true;
                case 81:
                    int readInt66 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int userBadgeNoBackgroundResId = getUserBadgeNoBackgroundResId(readInt66);
                    parcel2.writeNoException();
                    parcel2.writeInt(userBadgeNoBackgroundResId);
                    return true;
                case 82:
                    int readInt67 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int userBadgeLabelResId = getUserBadgeLabelResId(readInt67);
                    parcel2.writeNoException();
                    parcel2.writeInt(userBadgeLabelResId);
                    return true;
                case 83:
                    int readInt68 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int userBadgeColorResId = getUserBadgeColorResId(readInt68);
                    parcel2.writeNoException();
                    parcel2.writeInt(userBadgeColorResId);
                    return true;
                case 84:
                    int readInt69 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int userBadgeDarkColorResId = getUserBadgeDarkColorResId(readInt69);
                    parcel2.writeNoException();
                    parcel2.writeInt(userBadgeDarkColorResId);
                    return true;
                case 85:
                    int readInt70 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int userStatusBarIconResId = getUserStatusBarIconResId(readInt70);
                    parcel2.writeNoException();
                    parcel2.writeInt(userStatusBarIconResId);
                    return true;
                case 86:
                    int readInt71 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean hasBadge = hasBadge(readInt71);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasBadge);
                    return true;
                case 87:
                    int readInt72 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int profileLabelResId = getProfileLabelResId(readInt72);
                    parcel2.writeNoException();
                    parcel2.writeInt(profileLabelResId);
                    return true;
                case 88:
                    int readInt73 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int profileAccessibilityLabelResId = getProfileAccessibilityLabelResId(readInt73);
                    parcel2.writeNoException();
                    parcel2.writeInt(profileAccessibilityLabelResId);
                    return true;
                case 89:
                    int readInt74 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isUserUnlocked = isUserUnlocked(readInt74);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUserUnlocked);
                    return true;
                case 90:
                    int readInt75 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isUserRunning = isUserRunning(readInt75);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUserRunning);
                    return true;
                case 91:
                    int readInt76 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isUserForeground = isUserForeground(readInt76);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUserForeground);
                    return true;
                case 92:
                    int readInt77 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isUserVisible = isUserVisible(readInt77);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUserVisible);
                    return true;
                case 93:
                    int[] visibleUsers = getVisibleUsers();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(visibleUsers);
                    return true;
                case 94:
                    int readInt78 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int mainDisplayIdAssignedToUser = getMainDisplayIdAssignedToUser(readInt78);
                    parcel2.writeNoException();
                    parcel2.writeInt(mainDisplayIdAssignedToUser);
                    return true;
                case 95:
                    boolean isForegroundUserAdmin = isForegroundUserAdmin();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isForegroundUserAdmin);
                    return true;
                case 96:
                    int readInt79 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isUserNameSet = isUserNameSet(readInt79);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUserNameSet);
                    return true;
                case 97:
                    int readInt80 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean hasRestrictedProfiles = hasRestrictedProfiles(readInt80);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasRestrictedProfiles);
                    return true;
                case 98:
                    String readString39 = parcel.readString();
                    boolean readBoolean12 = parcel.readBoolean();
                    int readInt81 = parcel.readInt();
                    IntentSender intentSender = (IntentSender) parcel.readTypedObject(IntentSender.CREATOR);
                    int readInt82 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean requestQuietModeEnabled = requestQuietModeEnabled(readString39, readBoolean12, readInt81, intentSender, readInt82);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requestQuietModeEnabled);
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
                    int readInt83 = parcel.readInt();
                    boolean readBoolean13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean userEphemeral = setUserEphemeral(readInt83, readBoolean13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(userEphemeral);
                    return true;
                case 103:
                    int readInt84 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setBootUser(readInt84);
                    parcel2.writeNoException();
                    return true;
                case 104:
                    int bootUser = getBootUser();
                    parcel2.writeNoException();
                    parcel2.writeInt(bootUser);
                    return true;
                case 105:
                    int readInt85 = parcel.readInt();
                    boolean readBoolean14 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int[] profileIdsExcludingHidden = getProfileIdsExcludingHidden(readInt85, readBoolean14);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(profileIdsExcludingHidden);
                    return true;
                case 106:
                    int readInt86 = parcel.readInt();
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean updateUserInfo = updateUserInfo(readInt86, bundle3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(updateUserInfo);
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int getProfileParentId(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public UserInfo createUserWithThrow(String str, String str2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (UserInfo) obtain2.readTypedObject(UserInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public UserInfo preCreateUserWithThrow(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (UserInfo) obtain2.readTypedObject(UserInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public UserInfo createProfileForUserWithThrow(String str, String str2, int i, int i2, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (UserInfo) obtain2.readTypedObject(UserInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public UserInfo createRestrictedProfileWithThrow(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return (UserInfo) obtain2.readTypedObject(UserInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public String[] getPreInstallableSystemPackages(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public void setUserEnabled(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public void setUserAdmin(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public void revokeUserAdmin(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public void evictCredentialEncryptionKey(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean removeUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean removeUserEvenWhenDisallowed(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public void setUserName(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public void setUserIcon(int i, Bitmap bitmap) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bitmap, 0);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public ParcelFileDescriptor getUserIcon(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParcelFileDescriptor) obtain2.readTypedObject(ParcelFileDescriptor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public UserInfo getPrimaryUser() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return (UserInfo) obtain2.readTypedObject(UserInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int getMainUserId() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int getCommunalProfileId() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int getPreviousFullUserToEnterForeground() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public List<UserInfo> getUsers(boolean z, boolean z2, boolean z3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeBoolean(z3);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(UserInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public List<UserInfo> getProfiles(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(UserInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int[] getProfileIds(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean isUserTypeEnabled(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean canAddMoreUsersOfType(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int getRemainingCreatableUserCount(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int getRemainingCreatableProfileCount(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean canAddMoreProfilesToUser(String str, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean canAddMoreManagedProfiles(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public UserInfo getProfileParent(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return (UserInfo) obtain2.readTypedObject(UserInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean isSameProfileGroup(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean isHeadlessSystemUserMode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean isUserOfType(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public UserInfo getUserInfo(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return (UserInfo) obtain2.readTypedObject(UserInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public UserProperties getUserPropertiesCopy(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return (UserProperties) obtain2.readTypedObject(UserProperties.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public String getUserAccount(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public void setUserAccount(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public long getUserCreationTime(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int getUserSwitchability(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean isUserSwitcherEnabled(boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int getUserLogoutability(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean isRestricted(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean canHaveRestrictedProfile(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean canAddPrivateProfile(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int getUserSerialNumber(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int getUserHandle(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int getUserRestrictionSource(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public List<UserManager.EnforcingUser> getUserRestrictionSources(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(UserManager.EnforcingUser.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public Bundle getUserRestrictions(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean hasBaseUserRestriction(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean hasUserRestriction(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean hasUserRestrictionOnAnyUser(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean isSettingRestrictedForUser(String str, int i, String str2, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeInt(i2);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public void addUserRestrictionsListener(IUserRestrictionsListener iUserRestrictionsListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iUserRestrictionsListener);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public void setUserRestriction(String str, boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public void setApplicationRestrictions(String str, Bundle bundle, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public Bundle getApplicationRestrictions(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public Bundle getApplicationRestrictionsForUser(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public void setDefaultGuestRestrictions(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public Bundle getDefaultGuestRestrictions() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int removeUserWhenPossible(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean markGuestForDeletion(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(62, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public List<UserInfo> getGuestUsers() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(UserInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean isQuietModeEnabled(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public UserHandle createUserWithAttributes(String str, String str2, int i, Bitmap bitmap, String str3, String str4, PersistableBundle persistableBundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bitmap, 0);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeTypedObject(persistableBundle, 0);
                    this.mRemote.transact(65, obtain, obtain2, 0);
                    obtain2.readException();
                    return (UserHandle) obtain2.readTypedObject(UserHandle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public void setSeedAccountData(int i, String str, String str2, PersistableBundle persistableBundle, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(persistableBundle, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(66, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public String getSeedAccountName(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(67, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public String getSeedAccountType(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(68, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public PersistableBundle getSeedAccountOptions(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(69, obtain, obtain2, 0);
                    obtain2.readException();
                    return (PersistableBundle) obtain2.readTypedObject(PersistableBundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public void clearSeedAccountData(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(70, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean someUserHasSeedAccount(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(71, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean someUserHasAccount(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(72, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public String getProfileType(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(73, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean isDemoUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(74, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean isAdminUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(75, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean isPreCreated(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(76, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public UserInfo createProfileForUserEvenWhenDisallowedWithThrow(String str, String str2, int i, int i2, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(77, obtain, obtain2, 0);
                    obtain2.readException();
                    return (UserInfo) obtain2.readTypedObject(UserInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean isUserUnlockingOrUnlocked(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(78, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int getUserIconBadgeResId(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(79, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int getUserBadgeResId(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(80, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int getUserBadgeNoBackgroundResId(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(81, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int getUserBadgeLabelResId(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(82, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int getUserBadgeColorResId(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(83, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int getUserBadgeDarkColorResId(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(84, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int getUserStatusBarIconResId(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(85, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean hasBadge(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(86, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int getProfileLabelResId(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(87, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int getProfileAccessibilityLabelResId(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(88, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean isUserUnlocked(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(89, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean isUserRunning(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(90, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean isUserForeground(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(91, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean isUserVisible(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(92, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int[] getVisibleUsers() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(93, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int getMainDisplayIdAssignedToUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(94, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean isForegroundUserAdmin() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(95, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean isUserNameSet(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(96, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean hasRestrictedProfiles(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(97, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean requestQuietModeEnabled(String str, boolean z, int i, IntentSender intentSender, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(intentSender, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(98, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public String getUserName() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(99, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public long getUserStartRealtime() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(100, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public long getUserUnlockRealtime() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(101, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean setUserEphemeral(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(102, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public void setBootUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(103, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int getBootUser() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(104, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int[] getProfileIdsExcludingHidden(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(105, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean updateUserInfo(int i, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(106, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
