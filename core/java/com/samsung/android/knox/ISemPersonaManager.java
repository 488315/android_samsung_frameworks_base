package com.samsung.android.knox;

import android.app.IApplicationThread;
import android.app.IServiceConnection;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ISystemPersonaObserver;
import android.content.pm.ResolveInfo;
import android.content.pm.UserInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import java.util.List;

/* loaded from: classes6.dex */
public interface ISemPersonaManager extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.ISemPersonaManager";

    void CMFALock(int i) throws RemoteException;

    void CMFAUnLock(int i) throws RemoteException;

    void addAppPackageNameToAllowList(int i, List<String> list) throws RemoteException;

    boolean appliedPasswordPolicy(int i) throws RemoteException;

    boolean bindCoreServiceAsUser(
            ComponentName componentName,
            IApplicationThread iApplicationThread,
            IBinder iBinder,
            Intent intent,
            IServiceConnection iServiceConnection,
            int i,
            int i2)
            throws RemoteException;

    boolean broadcastIntentThroughPersona(Intent intent, int i) throws RemoteException;

    boolean clearAttributes(int i, int i2) throws RemoteException;

    ComponentName getAdminComponentName(int i) throws RemoteException;

    int getAttributes(int i) throws RemoteException;

    String getContainerName(int i) throws RemoteException;

    int getContainerOrder(int i) throws RemoteException;

    String getCustomResource(int i, String str) throws RemoteException;

    Bundle getDualDARProfile() throws RemoteException;

    String getECName(int i) throws RemoteException;

    int getFocusedLauncherId() throws RemoteException;

    int getFocusedUser() throws RemoteException;

    int getFotaVersion() throws RemoteException;

    IBasicCommand getKnoxForesightService() throws RemoteException;

    byte[] getKnoxIcon(String str, String str2, int i) throws RemoteException;

    List<Bundle> getMoveToKnoxMenuList(int i) throws RemoteException;

    String getPersonaCacheValue(String str) throws RemoteException;

    boolean getPersonaUserHasBeenShutdownBefore(int i) throws RemoteException;

    String getPersonalModeName(int i) throws RemoteException;

    String getProfileName(int i) throws RemoteException;

    List<UserInfo> getProfiles(int i, boolean z) throws RemoteException;

    String getRCPDataPolicy(String str, String str2) throws RemoteException;

    String getRCPDataPolicyForUser(int i, String str, String str2) throws RemoteException;

    int getSecureFolderId() throws RemoteException;

    String getSecureFolderName() throws RemoteException;

    List<String> getSecureFolderPolicy(String str, int i) throws RemoteException;

    List<String> getSeparatedAppsList() throws RemoteException;

    Bundle getSeparationConfigfromCache() throws RemoteException;

    Bundle getUCMProfile() throws RemoteException;

    List getUpdatedListWithAppSeparation(List<ResolveInfo> list) throws RemoteException;

    String getWorkspaceName(UserInfo userInfo, boolean z) throws RemoteException;

    boolean hasLicensePermission(int i, String str) throws RemoteException;

    void hideMultiWindows(int i) throws RemoteException;

    boolean isAppSeparationPresent() throws RemoteException;

    boolean isContainerCorePackageUID(int i) throws RemoteException;

    boolean isContainerService(int i) throws RemoteException;

    boolean isExternalStorageEnabled(int i) throws RemoteException;

    boolean isFOTAUpgrade() throws RemoteException;

    boolean isFotaUpgradeVersionChanged() throws RemoteException;

    boolean isInSeparatedAppsOnly(String str) throws RemoteException;

    boolean isKnoxProfileActivePasswordSufficientForParent(int i) throws RemoteException;

    boolean isKnoxWindowExist(int i, int i2, int i3) throws RemoteException;

    boolean isMoveFilesToContainerAllowed(int i) throws RemoteException;

    boolean isMoveFilesToOwnerAllowed(int i) throws RemoteException;

    boolean isPasswordSufficientAfterKnoxProfileUnification(int i) throws RemoteException;

    boolean isPossibleAddAppsToContainer(String str, int i) throws RemoteException;

    boolean isShareClipboardDataToContainerAllowed(int i) throws RemoteException;

    boolean isShareClipboardDataToOwnerAllowed(int i) throws RemoteException;

    void logDpmsKA(Bundle bundle) throws RemoteException;

    void notifyApplicationChanged(String str, int i) throws RemoteException;

    void postPwdChangeNotificationForDeviceOwner(int i) throws RemoteException;

    void refreshLockTimer(int i) throws RemoteException;

    boolean registerSystemPersonaObserver(ISystemPersonaObserver iSystemPersonaObserver)
            throws RemoteException;

    int resetUCMProfile() throws RemoteException;

    boolean sendKnoxForesightBroadcast(Intent intent) throws RemoteException;

    Bundle sendProxyMessage(String str, String str2, Bundle bundle) throws RemoteException;

    void sendRequestKeyStatus(int i) throws RemoteException;

    void setAppSeparationDefaultPolicy(int i) throws RemoteException;

    boolean setAttributes(int i, int i2) throws RemoteException;

    int setDualDARProfile(Bundle bundle) throws RemoteException;

    void setFocusedLauncherId(int i) throws RemoteException;

    boolean setPackageSettingInstalled(String str, boolean z, int i) throws RemoteException;

    boolean setPersonalModeName(int i, String str) throws RemoteException;

    boolean setProfileName(int i, String str) throws RemoteException;

    boolean setRCPDataPolicy(String str, String str2, String str3) throws RemoteException;

    boolean setSecureFolderPolicy(String str, List<String> list, int i) throws RemoteException;

    int setUCMProfile(Bundle bundle) throws RemoteException;

    boolean startActivityThroughPersona(Intent intent) throws RemoteException;

    void startCountrySelectionActivity(boolean z) throws RemoteException;

    void startTermsActivity() throws RemoteException;

    boolean updatePersonaCache(String str, String str2) throws RemoteException;

    void updateProfileActivityTimeFromKnox(int i, long j) throws RemoteException;

    public static class Default implements ISemPersonaManager {
        @Override // com.samsung.android.knox.ISemPersonaManager
        public boolean isFOTAUpgrade() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public List<UserInfo> getProfiles(int userHandle, boolean includeParent)
                throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public boolean registerSystemPersonaObserver(ISystemPersonaObserver mISystemPersonaObserver)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public void setFocusedLauncherId(int userId) throws RemoteException {}

        @Override // com.samsung.android.knox.ISemPersonaManager
        public boolean isFotaUpgradeVersionChanged() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public int getSecureFolderId() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public String getSecureFolderName() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public String getContainerName(int userId) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public String getWorkspaceName(UserInfo uInfo, boolean isWorkProfile)
                throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public String getECName(int userId) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public String getProfileName(int userId) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public boolean setProfileName(int userId, String value) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public String getPersonalModeName(int userId) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public boolean setPersonalModeName(int userId, String value) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public boolean isPossibleAddAppsToContainer(String pkgName, int personaId)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public boolean isInSeparatedAppsOnly(String pkgName) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public List<String> getSeparatedAppsList() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public boolean isAppSeparationPresent() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public List getUpdatedListWithAppSeparation(List<ResolveInfo> originalList)
                throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public void notifyApplicationChanged(String packageName, int userId)
                throws RemoteException {}

        @Override // com.samsung.android.knox.ISemPersonaManager
        public boolean isKnoxWindowExist(int personaId, int visibleFlag, int stackId)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public int getContainerOrder(int userId) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public boolean isExternalStorageEnabled(int personaId) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public boolean startActivityThroughPersona(Intent intent) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public boolean broadcastIntentThroughPersona(Intent intent, int userId)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public int getFotaVersion() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public String getPersonaCacheValue(String key) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public boolean updatePersonaCache(String key, String value) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public ComponentName getAdminComponentName(int containerId) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public void addAppPackageNameToAllowList(int containerId, List<String> appInstallationList)
                throws RemoteException {}

        @Override // com.samsung.android.knox.ISemPersonaManager
        public int getFocusedLauncherId() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public boolean setAttributes(int userId, int attr) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public int getAttributes(int userId) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public boolean clearAttributes(int userId, int attr) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public String getCustomResource(int userId, String resourceType) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public byte[] getKnoxIcon(String packageName, String className, int userId)
                throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public boolean setPackageSettingInstalled(String packageName, boolean installed, int userId)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public void refreshLockTimer(int userId) throws RemoteException {}

        @Override // com.samsung.android.knox.ISemPersonaManager
        public Bundle sendProxyMessage(String category, String name, Bundle args)
                throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public void hideMultiWindows(int userId) throws RemoteException {}

        @Override // com.samsung.android.knox.ISemPersonaManager
        public boolean bindCoreServiceAsUser(
                ComponentName admin,
                IApplicationThread caller,
                IBinder token,
                Intent service,
                IServiceConnection connection,
                int flags,
                int targetUserId)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public void sendRequestKeyStatus(int containerId) throws RemoteException {}

        @Override // com.samsung.android.knox.ISemPersonaManager
        public List<Bundle> getMoveToKnoxMenuList(int appUserId) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public int getFocusedUser() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public boolean isKnoxProfileActivePasswordSufficientForParent(int userId)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public boolean isPasswordSufficientAfterKnoxProfileUnification(int profileUser)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public int setDualDARProfile(Bundle config) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public Bundle getDualDARProfile() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public int setUCMProfile(Bundle config) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public Bundle getUCMProfile() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public int resetUCMProfile() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public boolean getPersonaUserHasBeenShutdownBefore(int personaId) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public boolean appliedPasswordPolicy(int userHandle) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public String getRCPDataPolicy(String appName, String policyProperty)
                throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public String getRCPDataPolicyForUser(int userId, String appName, String policyProperty)
                throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public boolean setRCPDataPolicy(String appName, String policyProperty, String value)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public boolean isShareClipboardDataToOwnerAllowed(int userId) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public boolean isMoveFilesToContainerAllowed(int userId) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public boolean isMoveFilesToOwnerAllowed(int userId) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public void logDpmsKA(Bundle b) throws RemoteException {}

        @Override // com.samsung.android.knox.ISemPersonaManager
        public boolean isShareClipboardDataToContainerAllowed(int userId) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public List<String> getSecureFolderPolicy(String key, int userId) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public boolean setSecureFolderPolicy(String key, List<String> pkgList, int userId)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public void CMFALock(int userId) throws RemoteException {}

        @Override // com.samsung.android.knox.ISemPersonaManager
        public void CMFAUnLock(int userId) throws RemoteException {}

        @Override // com.samsung.android.knox.ISemPersonaManager
        public Bundle getSeparationConfigfromCache() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public void setAppSeparationDefaultPolicy(int userId) throws RemoteException {}

        @Override // com.samsung.android.knox.ISemPersonaManager
        public void updateProfileActivityTimeFromKnox(int userId, long eventTime)
                throws RemoteException {}

        @Override // com.samsung.android.knox.ISemPersonaManager
        public boolean isContainerCorePackageUID(int uid) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public void startCountrySelectionActivity(boolean isUnified) throws RemoteException {}

        @Override // com.samsung.android.knox.ISemPersonaManager
        public void startTermsActivity() throws RemoteException {}

        @Override // com.samsung.android.knox.ISemPersonaManager
        public void postPwdChangeNotificationForDeviceOwner(int userId) throws RemoteException {}

        @Override // com.samsung.android.knox.ISemPersonaManager
        public boolean isContainerService(int pid) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public boolean sendKnoxForesightBroadcast(Intent intent) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public boolean hasLicensePermission(int uid, String permission) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public IBasicCommand getKnoxForesightService() throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ISemPersonaManager {
        static final int TRANSACTION_CMFALock = 64;
        static final int TRANSACTION_CMFAUnLock = 65;
        static final int TRANSACTION_addAppPackageNameToAllowList = 30;
        static final int TRANSACTION_appliedPasswordPolicy = 53;
        static final int TRANSACTION_bindCoreServiceAsUser = 41;
        static final int TRANSACTION_broadcastIntentThroughPersona = 25;
        static final int TRANSACTION_clearAttributes = 34;
        static final int TRANSACTION_getAdminComponentName = 29;
        static final int TRANSACTION_getAttributes = 33;
        static final int TRANSACTION_getContainerName = 8;
        static final int TRANSACTION_getContainerOrder = 22;
        static final int TRANSACTION_getCustomResource = 35;
        static final int TRANSACTION_getDualDARProfile = 48;
        static final int TRANSACTION_getECName = 10;
        static final int TRANSACTION_getFocusedLauncherId = 31;
        static final int TRANSACTION_getFocusedUser = 44;
        static final int TRANSACTION_getFotaVersion = 26;
        static final int TRANSACTION_getKnoxForesightService = 76;
        static final int TRANSACTION_getKnoxIcon = 36;
        static final int TRANSACTION_getMoveToKnoxMenuList = 43;
        static final int TRANSACTION_getPersonaCacheValue = 27;
        static final int TRANSACTION_getPersonaUserHasBeenShutdownBefore = 52;
        static final int TRANSACTION_getPersonalModeName = 13;
        static final int TRANSACTION_getProfileName = 11;
        static final int TRANSACTION_getProfiles = 2;
        static final int TRANSACTION_getRCPDataPolicy = 54;
        static final int TRANSACTION_getRCPDataPolicyForUser = 55;
        static final int TRANSACTION_getSecureFolderId = 6;
        static final int TRANSACTION_getSecureFolderName = 7;
        static final int TRANSACTION_getSecureFolderPolicy = 62;
        static final int TRANSACTION_getSeparatedAppsList = 17;
        static final int TRANSACTION_getSeparationConfigfromCache = 66;
        static final int TRANSACTION_getUCMProfile = 50;
        static final int TRANSACTION_getUpdatedListWithAppSeparation = 19;
        static final int TRANSACTION_getWorkspaceName = 9;
        static final int TRANSACTION_hasLicensePermission = 75;
        static final int TRANSACTION_hideMultiWindows = 40;
        static final int TRANSACTION_isAppSeparationPresent = 18;
        static final int TRANSACTION_isContainerCorePackageUID = 69;
        static final int TRANSACTION_isContainerService = 73;
        static final int TRANSACTION_isExternalStorageEnabled = 23;
        static final int TRANSACTION_isFOTAUpgrade = 1;
        static final int TRANSACTION_isFotaUpgradeVersionChanged = 5;
        static final int TRANSACTION_isInSeparatedAppsOnly = 16;
        static final int TRANSACTION_isKnoxProfileActivePasswordSufficientForParent = 45;
        static final int TRANSACTION_isKnoxWindowExist = 21;
        static final int TRANSACTION_isMoveFilesToContainerAllowed = 58;
        static final int TRANSACTION_isMoveFilesToOwnerAllowed = 59;
        static final int TRANSACTION_isPasswordSufficientAfterKnoxProfileUnification = 46;
        static final int TRANSACTION_isPossibleAddAppsToContainer = 15;
        static final int TRANSACTION_isShareClipboardDataToContainerAllowed = 61;
        static final int TRANSACTION_isShareClipboardDataToOwnerAllowed = 57;
        static final int TRANSACTION_logDpmsKA = 60;
        static final int TRANSACTION_notifyApplicationChanged = 20;
        static final int TRANSACTION_postPwdChangeNotificationForDeviceOwner = 72;
        static final int TRANSACTION_refreshLockTimer = 38;
        static final int TRANSACTION_registerSystemPersonaObserver = 3;
        static final int TRANSACTION_resetUCMProfile = 51;
        static final int TRANSACTION_sendKnoxForesightBroadcast = 74;
        static final int TRANSACTION_sendProxyMessage = 39;
        static final int TRANSACTION_sendRequestKeyStatus = 42;
        static final int TRANSACTION_setAppSeparationDefaultPolicy = 67;
        static final int TRANSACTION_setAttributes = 32;
        static final int TRANSACTION_setDualDARProfile = 47;
        static final int TRANSACTION_setFocusedLauncherId = 4;
        static final int TRANSACTION_setPackageSettingInstalled = 37;
        static final int TRANSACTION_setPersonalModeName = 14;
        static final int TRANSACTION_setProfileName = 12;
        static final int TRANSACTION_setRCPDataPolicy = 56;
        static final int TRANSACTION_setSecureFolderPolicy = 63;
        static final int TRANSACTION_setUCMProfile = 49;
        static final int TRANSACTION_startActivityThroughPersona = 24;
        static final int TRANSACTION_startCountrySelectionActivity = 70;
        static final int TRANSACTION_startTermsActivity = 71;
        static final int TRANSACTION_updatePersonaCache = 28;
        static final int TRANSACTION_updateProfileActivityTimeFromKnox = 68;

        public Stub() {
            attachInterface(this, ISemPersonaManager.DESCRIPTOR);
        }

        public static ISemPersonaManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISemPersonaManager.DESCRIPTOR);
            if (iin != null && (iin instanceof ISemPersonaManager)) {
                return (ISemPersonaManager) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "isFOTAUpgrade";
                case 2:
                    return "getProfiles";
                case 3:
                    return "registerSystemPersonaObserver";
                case 4:
                    return "setFocusedLauncherId";
                case 5:
                    return "isFotaUpgradeVersionChanged";
                case 6:
                    return "getSecureFolderId";
                case 7:
                    return "getSecureFolderName";
                case 8:
                    return "getContainerName";
                case 9:
                    return "getWorkspaceName";
                case 10:
                    return "getECName";
                case 11:
                    return "getProfileName";
                case 12:
                    return "setProfileName";
                case 13:
                    return "getPersonalModeName";
                case 14:
                    return "setPersonalModeName";
                case 15:
                    return "isPossibleAddAppsToContainer";
                case 16:
                    return "isInSeparatedAppsOnly";
                case 17:
                    return "getSeparatedAppsList";
                case 18:
                    return "isAppSeparationPresent";
                case 19:
                    return "getUpdatedListWithAppSeparation";
                case 20:
                    return "notifyApplicationChanged";
                case 21:
                    return "isKnoxWindowExist";
                case 22:
                    return "getContainerOrder";
                case 23:
                    return "isExternalStorageEnabled";
                case 24:
                    return "startActivityThroughPersona";
                case 25:
                    return "broadcastIntentThroughPersona";
                case 26:
                    return "getFotaVersion";
                case 27:
                    return "getPersonaCacheValue";
                case 28:
                    return "updatePersonaCache";
                case 29:
                    return "getAdminComponentName";
                case 30:
                    return "addAppPackageNameToAllowList";
                case 31:
                    return "getFocusedLauncherId";
                case 32:
                    return "setAttributes";
                case 33:
                    return "getAttributes";
                case 34:
                    return "clearAttributes";
                case 35:
                    return "getCustomResource";
                case 36:
                    return "getKnoxIcon";
                case 37:
                    return "setPackageSettingInstalled";
                case 38:
                    return "refreshLockTimer";
                case 39:
                    return "sendProxyMessage";
                case 40:
                    return "hideMultiWindows";
                case 41:
                    return "bindCoreServiceAsUser";
                case 42:
                    return "sendRequestKeyStatus";
                case 43:
                    return "getMoveToKnoxMenuList";
                case 44:
                    return "getFocusedUser";
                case 45:
                    return "isKnoxProfileActivePasswordSufficientForParent";
                case 46:
                    return "isPasswordSufficientAfterKnoxProfileUnification";
                case 47:
                    return "setDualDARProfile";
                case 48:
                    return "getDualDARProfile";
                case 49:
                    return "setUCMProfile";
                case 50:
                    return "getUCMProfile";
                case 51:
                    return "resetUCMProfile";
                case 52:
                    return "getPersonaUserHasBeenShutdownBefore";
                case 53:
                    return "appliedPasswordPolicy";
                case 54:
                    return "getRCPDataPolicy";
                case 55:
                    return "getRCPDataPolicyForUser";
                case 56:
                    return "setRCPDataPolicy";
                case 57:
                    return "isShareClipboardDataToOwnerAllowed";
                case 58:
                    return "isMoveFilesToContainerAllowed";
                case 59:
                    return "isMoveFilesToOwnerAllowed";
                case 60:
                    return "logDpmsKA";
                case 61:
                    return "isShareClipboardDataToContainerAllowed";
                case 62:
                    return "getSecureFolderPolicy";
                case 63:
                    return "setSecureFolderPolicy";
                case 64:
                    return "CMFALock";
                case 65:
                    return "CMFAUnLock";
                case 66:
                    return "getSeparationConfigfromCache";
                case 67:
                    return "setAppSeparationDefaultPolicy";
                case 68:
                    return "updateProfileActivityTimeFromKnox";
                case 69:
                    return "isContainerCorePackageUID";
                case 70:
                    return "startCountrySelectionActivity";
                case 71:
                    return "startTermsActivity";
                case 72:
                    return "postPwdChangeNotificationForDeviceOwner";
                case 73:
                    return "isContainerService";
                case 74:
                    return "sendKnoxForesightBroadcast";
                case 75:
                    return "hasLicensePermission";
                case 76:
                    return "getKnoxForesightService";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ISemPersonaManager.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISemPersonaManager.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    boolean _result = isFOTAUpgrade();
                    reply.writeNoException();
                    reply.writeBoolean(_result);
                    return true;
                case 2:
                    int _arg0 = data.readInt();
                    boolean _arg1 = data.readBoolean();
                    data.enforceNoDataAvail();
                    List<UserInfo> _result2 = getProfiles(_arg0, _arg1);
                    reply.writeNoException();
                    reply.writeTypedList(_result2, 1);
                    return true;
                case 3:
                    ISystemPersonaObserver _arg02 =
                            ISystemPersonaObserver.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    boolean _result3 = registerSystemPersonaObserver(_arg02);
                    reply.writeNoException();
                    reply.writeBoolean(_result3);
                    return true;
                case 4:
                    int _arg03 = data.readInt();
                    data.enforceNoDataAvail();
                    setFocusedLauncherId(_arg03);
                    reply.writeNoException();
                    return true;
                case 5:
                    boolean _result4 = isFotaUpgradeVersionChanged();
                    reply.writeNoException();
                    reply.writeBoolean(_result4);
                    return true;
                case 6:
                    int _result5 = getSecureFolderId();
                    reply.writeNoException();
                    reply.writeInt(_result5);
                    return true;
                case 7:
                    String _result6 = getSecureFolderName();
                    reply.writeNoException();
                    reply.writeString(_result6);
                    return true;
                case 8:
                    int _arg04 = data.readInt();
                    data.enforceNoDataAvail();
                    String _result7 = getContainerName(_arg04);
                    reply.writeNoException();
                    reply.writeString(_result7);
                    return true;
                case 9:
                    UserInfo _arg05 = (UserInfo) data.readTypedObject(UserInfo.CREATOR);
                    boolean _arg12 = data.readBoolean();
                    data.enforceNoDataAvail();
                    String _result8 = getWorkspaceName(_arg05, _arg12);
                    reply.writeNoException();
                    reply.writeString(_result8);
                    return true;
                case 10:
                    int _arg06 = data.readInt();
                    data.enforceNoDataAvail();
                    String _result9 = getECName(_arg06);
                    reply.writeNoException();
                    reply.writeString(_result9);
                    return true;
                case 11:
                    int _arg07 = data.readInt();
                    data.enforceNoDataAvail();
                    String _result10 = getProfileName(_arg07);
                    reply.writeNoException();
                    reply.writeString(_result10);
                    return true;
                case 12:
                    int _arg08 = data.readInt();
                    String _arg13 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result11 = setProfileName(_arg08, _arg13);
                    reply.writeNoException();
                    reply.writeBoolean(_result11);
                    return true;
                case 13:
                    int _arg09 = data.readInt();
                    data.enforceNoDataAvail();
                    String _result12 = getPersonalModeName(_arg09);
                    reply.writeNoException();
                    reply.writeString(_result12);
                    return true;
                case 14:
                    int _arg010 = data.readInt();
                    String _arg14 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result13 = setPersonalModeName(_arg010, _arg14);
                    reply.writeNoException();
                    reply.writeBoolean(_result13);
                    return true;
                case 15:
                    String _arg011 = data.readString();
                    int _arg15 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result14 = isPossibleAddAppsToContainer(_arg011, _arg15);
                    reply.writeNoException();
                    reply.writeBoolean(_result14);
                    return true;
                case 16:
                    String _arg012 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result15 = isInSeparatedAppsOnly(_arg012);
                    reply.writeNoException();
                    reply.writeBoolean(_result15);
                    return true;
                case 17:
                    List<String> _result16 = getSeparatedAppsList();
                    reply.writeNoException();
                    reply.writeStringList(_result16);
                    return true;
                case 18:
                    boolean _result17 = isAppSeparationPresent();
                    reply.writeNoException();
                    reply.writeBoolean(_result17);
                    return true;
                case 19:
                    List<ResolveInfo> _arg013 = data.createTypedArrayList(ResolveInfo.CREATOR);
                    data.enforceNoDataAvail();
                    List _result18 = getUpdatedListWithAppSeparation(_arg013);
                    reply.writeNoException();
                    reply.writeList(_result18);
                    return true;
                case 20:
                    String _arg014 = data.readString();
                    int _arg16 = data.readInt();
                    data.enforceNoDataAvail();
                    notifyApplicationChanged(_arg014, _arg16);
                    reply.writeNoException();
                    return true;
                case 21:
                    int _arg015 = data.readInt();
                    int _arg17 = data.readInt();
                    int _arg2 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result19 = isKnoxWindowExist(_arg015, _arg17, _arg2);
                    reply.writeNoException();
                    reply.writeBoolean(_result19);
                    return true;
                case 22:
                    int _arg016 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result20 = getContainerOrder(_arg016);
                    reply.writeNoException();
                    reply.writeInt(_result20);
                    return true;
                case 23:
                    int _arg017 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result21 = isExternalStorageEnabled(_arg017);
                    reply.writeNoException();
                    reply.writeBoolean(_result21);
                    return true;
                case 24:
                    Intent _arg018 = (Intent) data.readTypedObject(Intent.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result22 = startActivityThroughPersona(_arg018);
                    reply.writeNoException();
                    reply.writeBoolean(_result22);
                    return true;
                case 25:
                    Intent _arg019 = (Intent) data.readTypedObject(Intent.CREATOR);
                    int _arg18 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result23 = broadcastIntentThroughPersona(_arg019, _arg18);
                    reply.writeNoException();
                    reply.writeBoolean(_result23);
                    return true;
                case 26:
                    int _result24 = getFotaVersion();
                    reply.writeNoException();
                    reply.writeInt(_result24);
                    return true;
                case 27:
                    String _arg020 = data.readString();
                    data.enforceNoDataAvail();
                    String _result25 = getPersonaCacheValue(_arg020);
                    reply.writeNoException();
                    reply.writeString(_result25);
                    return true;
                case 28:
                    String _arg021 = data.readString();
                    String _arg19 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result26 = updatePersonaCache(_arg021, _arg19);
                    reply.writeNoException();
                    reply.writeBoolean(_result26);
                    return true;
                case 29:
                    int _arg022 = data.readInt();
                    data.enforceNoDataAvail();
                    ComponentName _result27 = getAdminComponentName(_arg022);
                    reply.writeNoException();
                    reply.writeTypedObject(_result27, 1);
                    return true;
                case 30:
                    int _arg023 = data.readInt();
                    List<String> _arg110 = data.createStringArrayList();
                    data.enforceNoDataAvail();
                    addAppPackageNameToAllowList(_arg023, _arg110);
                    reply.writeNoException();
                    return true;
                case 31:
                    int _result28 = getFocusedLauncherId();
                    reply.writeNoException();
                    reply.writeInt(_result28);
                    return true;
                case 32:
                    int _arg024 = data.readInt();
                    int _arg111 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result29 = setAttributes(_arg024, _arg111);
                    reply.writeNoException();
                    reply.writeBoolean(_result29);
                    return true;
                case 33:
                    int _arg025 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result30 = getAttributes(_arg025);
                    reply.writeNoException();
                    reply.writeInt(_result30);
                    return true;
                case 34:
                    int _arg026 = data.readInt();
                    int _arg112 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result31 = clearAttributes(_arg026, _arg112);
                    reply.writeNoException();
                    reply.writeBoolean(_result31);
                    return true;
                case 35:
                    int _arg027 = data.readInt();
                    String _arg113 = data.readString();
                    data.enforceNoDataAvail();
                    String _result32 = getCustomResource(_arg027, _arg113);
                    reply.writeNoException();
                    reply.writeString(_result32);
                    return true;
                case 36:
                    String _arg028 = data.readString();
                    String _arg114 = data.readString();
                    int _arg22 = data.readInt();
                    data.enforceNoDataAvail();
                    byte[] _result33 = getKnoxIcon(_arg028, _arg114, _arg22);
                    reply.writeNoException();
                    reply.writeByteArray(_result33);
                    return true;
                case 37:
                    String _arg029 = data.readString();
                    boolean _arg115 = data.readBoolean();
                    int _arg23 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result34 = setPackageSettingInstalled(_arg029, _arg115, _arg23);
                    reply.writeNoException();
                    reply.writeBoolean(_result34);
                    return true;
                case 38:
                    int _arg030 = data.readInt();
                    data.enforceNoDataAvail();
                    refreshLockTimer(_arg030);
                    reply.writeNoException();
                    return true;
                case 39:
                    String _arg031 = data.readString();
                    String _arg116 = data.readString();
                    Bundle _arg24 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    Bundle _result35 = sendProxyMessage(_arg031, _arg116, _arg24);
                    reply.writeNoException();
                    reply.writeTypedObject(_result35, 1);
                    return true;
                case 40:
                    int _arg032 = data.readInt();
                    data.enforceNoDataAvail();
                    hideMultiWindows(_arg032);
                    reply.writeNoException();
                    return true;
                case 41:
                    ComponentName _arg033 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    IApplicationThread _arg117 =
                            IApplicationThread.Stub.asInterface(data.readStrongBinder());
                    IBinder _arg25 = data.readStrongBinder();
                    Intent _arg3 = (Intent) data.readTypedObject(Intent.CREATOR);
                    IServiceConnection _arg4 =
                            IServiceConnection.Stub.asInterface(data.readStrongBinder());
                    int _arg5 = data.readInt();
                    int _arg6 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result36 =
                            bindCoreServiceAsUser(
                                    _arg033, _arg117, _arg25, _arg3, _arg4, _arg5, _arg6);
                    reply.writeNoException();
                    reply.writeBoolean(_result36);
                    return true;
                case 42:
                    int _arg034 = data.readInt();
                    data.enforceNoDataAvail();
                    sendRequestKeyStatus(_arg034);
                    reply.writeNoException();
                    return true;
                case 43:
                    int _arg035 = data.readInt();
                    data.enforceNoDataAvail();
                    List<Bundle> _result37 = getMoveToKnoxMenuList(_arg035);
                    reply.writeNoException();
                    reply.writeTypedList(_result37, 1);
                    return true;
                case 44:
                    int _result38 = getFocusedUser();
                    reply.writeNoException();
                    reply.writeInt(_result38);
                    return true;
                case 45:
                    int _arg036 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result39 = isKnoxProfileActivePasswordSufficientForParent(_arg036);
                    reply.writeNoException();
                    reply.writeBoolean(_result39);
                    return true;
                case 46:
                    int _arg037 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result40 = isPasswordSufficientAfterKnoxProfileUnification(_arg037);
                    reply.writeNoException();
                    reply.writeBoolean(_result40);
                    return true;
                case 47:
                    Bundle _arg038 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    int _result41 = setDualDARProfile(_arg038);
                    reply.writeNoException();
                    reply.writeInt(_result41);
                    return true;
                case 48:
                    Bundle _result42 = getDualDARProfile();
                    reply.writeNoException();
                    reply.writeTypedObject(_result42, 1);
                    return true;
                case 49:
                    Bundle _arg039 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    int _result43 = setUCMProfile(_arg039);
                    reply.writeNoException();
                    reply.writeInt(_result43);
                    return true;
                case 50:
                    Bundle _result44 = getUCMProfile();
                    reply.writeNoException();
                    reply.writeTypedObject(_result44, 1);
                    return true;
                case 51:
                    int _result45 = resetUCMProfile();
                    reply.writeNoException();
                    reply.writeInt(_result45);
                    return true;
                case 52:
                    int _arg040 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result46 = getPersonaUserHasBeenShutdownBefore(_arg040);
                    reply.writeNoException();
                    reply.writeBoolean(_result46);
                    return true;
                case 53:
                    int _arg041 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result47 = appliedPasswordPolicy(_arg041);
                    reply.writeNoException();
                    reply.writeBoolean(_result47);
                    return true;
                case 54:
                    String _arg042 = data.readString();
                    String _arg118 = data.readString();
                    data.enforceNoDataAvail();
                    String _result48 = getRCPDataPolicy(_arg042, _arg118);
                    reply.writeNoException();
                    reply.writeString(_result48);
                    return true;
                case 55:
                    int _arg043 = data.readInt();
                    String _arg119 = data.readString();
                    String _arg26 = data.readString();
                    data.enforceNoDataAvail();
                    String _result49 = getRCPDataPolicyForUser(_arg043, _arg119, _arg26);
                    reply.writeNoException();
                    reply.writeString(_result49);
                    return true;
                case 56:
                    String _arg044 = data.readString();
                    String _arg120 = data.readString();
                    String _arg27 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result50 = setRCPDataPolicy(_arg044, _arg120, _arg27);
                    reply.writeNoException();
                    reply.writeBoolean(_result50);
                    return true;
                case 57:
                    int _arg045 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result51 = isShareClipboardDataToOwnerAllowed(_arg045);
                    reply.writeNoException();
                    reply.writeBoolean(_result51);
                    return true;
                case 58:
                    int _arg046 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result52 = isMoveFilesToContainerAllowed(_arg046);
                    reply.writeNoException();
                    reply.writeBoolean(_result52);
                    return true;
                case 59:
                    int _arg047 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result53 = isMoveFilesToOwnerAllowed(_arg047);
                    reply.writeNoException();
                    reply.writeBoolean(_result53);
                    return true;
                case 60:
                    Bundle _arg048 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    logDpmsKA(_arg048);
                    reply.writeNoException();
                    return true;
                case 61:
                    int _arg049 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result54 = isShareClipboardDataToContainerAllowed(_arg049);
                    reply.writeNoException();
                    reply.writeBoolean(_result54);
                    return true;
                case 62:
                    String _arg050 = data.readString();
                    int _arg121 = data.readInt();
                    data.enforceNoDataAvail();
                    List<String> _result55 = getSecureFolderPolicy(_arg050, _arg121);
                    reply.writeNoException();
                    reply.writeStringList(_result55);
                    return true;
                case 63:
                    String _arg051 = data.readString();
                    List<String> _arg122 = data.createStringArrayList();
                    int _arg28 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result56 = setSecureFolderPolicy(_arg051, _arg122, _arg28);
                    reply.writeNoException();
                    reply.writeBoolean(_result56);
                    return true;
                case 64:
                    int _arg052 = data.readInt();
                    data.enforceNoDataAvail();
                    CMFALock(_arg052);
                    reply.writeNoException();
                    return true;
                case 65:
                    int _arg053 = data.readInt();
                    data.enforceNoDataAvail();
                    CMFAUnLock(_arg053);
                    reply.writeNoException();
                    return true;
                case 66:
                    Bundle _result57 = getSeparationConfigfromCache();
                    reply.writeNoException();
                    reply.writeTypedObject(_result57, 1);
                    return true;
                case 67:
                    int _arg054 = data.readInt();
                    data.enforceNoDataAvail();
                    setAppSeparationDefaultPolicy(_arg054);
                    reply.writeNoException();
                    return true;
                case 68:
                    int _arg055 = data.readInt();
                    long _arg123 = data.readLong();
                    data.enforceNoDataAvail();
                    updateProfileActivityTimeFromKnox(_arg055, _arg123);
                    reply.writeNoException();
                    return true;
                case 69:
                    int _arg056 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result58 = isContainerCorePackageUID(_arg056);
                    reply.writeNoException();
                    reply.writeBoolean(_result58);
                    return true;
                case 70:
                    boolean _arg057 = data.readBoolean();
                    data.enforceNoDataAvail();
                    startCountrySelectionActivity(_arg057);
                    reply.writeNoException();
                    return true;
                case 71:
                    startTermsActivity();
                    reply.writeNoException();
                    return true;
                case 72:
                    int _arg058 = data.readInt();
                    data.enforceNoDataAvail();
                    postPwdChangeNotificationForDeviceOwner(_arg058);
                    reply.writeNoException();
                    return true;
                case 73:
                    int _arg059 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result59 = isContainerService(_arg059);
                    reply.writeNoException();
                    reply.writeBoolean(_result59);
                    return true;
                case 74:
                    Intent _arg060 = (Intent) data.readTypedObject(Intent.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result60 = sendKnoxForesightBroadcast(_arg060);
                    reply.writeNoException();
                    reply.writeBoolean(_result60);
                    return true;
                case 75:
                    int _arg061 = data.readInt();
                    String _arg124 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result61 = hasLicensePermission(_arg061, _arg124);
                    reply.writeNoException();
                    reply.writeBoolean(_result61);
                    return true;
                case 76:
                    IBasicCommand _result62 = getKnoxForesightService();
                    reply.writeNoException();
                    reply.writeStrongInterface(_result62);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISemPersonaManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemPersonaManager.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean isFOTAUpgrade() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public List<UserInfo> getProfiles(int userHandle, boolean includeParent)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    _data.writeBoolean(includeParent);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    List<UserInfo> _result = _reply.createTypedArrayList(UserInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean registerSystemPersonaObserver(
                    ISystemPersonaObserver mISystemPersonaObserver) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    _data.writeStrongInterface(mISystemPersonaObserver);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public void setFocusedLauncherId(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean isFotaUpgradeVersionChanged() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public int getSecureFolderId() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public String getSecureFolderName() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public String getContainerName(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public String getWorkspaceName(UserInfo uInfo, boolean isWorkProfile)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    _data.writeTypedObject(uInfo, 0);
                    _data.writeBoolean(isWorkProfile);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public String getECName(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public String getProfileName(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean setProfileName(int userId, String value) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeString(value);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public String getPersonalModeName(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean setPersonalModeName(int userId, String value) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeString(value);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean isPossibleAddAppsToContainer(String pkgName, int personaId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    _data.writeString(pkgName);
                    _data.writeInt(personaId);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean isInSeparatedAppsOnly(String pkgName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    _data.writeString(pkgName);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public List<String> getSeparatedAppsList() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean isAppSeparationPresent() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public List getUpdatedListWithAppSeparation(List<ResolveInfo> originalList)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    _data.writeTypedList(originalList, 0);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                    ClassLoader cl = getClass().getClassLoader();
                    List _result = _reply.readArrayList(cl);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public void notifyApplicationChanged(String packageName, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean isKnoxWindowExist(int personaId, int visibleFlag, int stackId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    _data.writeInt(personaId);
                    _data.writeInt(visibleFlag);
                    _data.writeInt(stackId);
                    this.mRemote.transact(21, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public int getContainerOrder(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean isExternalStorageEnabled(int personaId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    _data.writeInt(personaId);
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean startActivityThroughPersona(Intent intent) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    _data.writeTypedObject(intent, 0);
                    this.mRemote.transact(24, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean broadcastIntentThroughPersona(Intent intent, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    _data.writeTypedObject(intent, 0);
                    _data.writeInt(userId);
                    this.mRemote.transact(25, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public int getFotaVersion() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    this.mRemote.transact(26, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public String getPersonaCacheValue(String key) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    _data.writeString(key);
                    this.mRemote.transact(27, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean updatePersonaCache(String key, String value) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    _data.writeString(key);
                    _data.writeString(value);
                    this.mRemote.transact(28, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public ComponentName getAdminComponentName(int containerId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    _data.writeInt(containerId);
                    this.mRemote.transact(29, _data, _reply, 0);
                    _reply.readException();
                    ComponentName _result =
                            (ComponentName) _reply.readTypedObject(ComponentName.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public void addAppPackageNameToAllowList(
                    int containerId, List<String> appInstallationList) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    _data.writeInt(containerId);
                    _data.writeStringList(appInstallationList);
                    this.mRemote.transact(30, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public int getFocusedLauncherId() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    this.mRemote.transact(31, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean setAttributes(int userId, int attr) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeInt(attr);
                    this.mRemote.transact(32, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public int getAttributes(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(33, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean clearAttributes(int userId, int attr) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeInt(attr);
                    this.mRemote.transact(34, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public String getCustomResource(int userId, String resourceType)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeString(resourceType);
                    this.mRemote.transact(35, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public byte[] getKnoxIcon(String packageName, String className, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(className);
                    _data.writeInt(userId);
                    this.mRemote.transact(36, _data, _reply, 0);
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean setPackageSettingInstalled(
                    String packageName, boolean installed, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeBoolean(installed);
                    _data.writeInt(userId);
                    this.mRemote.transact(37, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public void refreshLockTimer(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(38, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public Bundle sendProxyMessage(String category, String name, Bundle args)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    _data.writeString(category);
                    _data.writeString(name);
                    _data.writeTypedObject(args, 0);
                    this.mRemote.transact(39, _data, _reply, 0);
                    _reply.readException();
                    Bundle _result = (Bundle) _reply.readTypedObject(Bundle.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public void hideMultiWindows(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(40, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean bindCoreServiceAsUser(
                    ComponentName admin,
                    IApplicationThread caller,
                    IBinder token,
                    Intent service,
                    IServiceConnection connection,
                    int flags,
                    int targetUserId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    _data.writeTypedObject(admin, 0);
                    _data.writeStrongInterface(caller);
                    _data.writeStrongBinder(token);
                    _data.writeTypedObject(service, 0);
                    _data.writeStrongInterface(connection);
                    _data.writeInt(flags);
                    _data.writeInt(targetUserId);
                    this.mRemote.transact(41, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public void sendRequestKeyStatus(int containerId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    _data.writeInt(containerId);
                    this.mRemote.transact(42, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public List<Bundle> getMoveToKnoxMenuList(int appUserId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    _data.writeInt(appUserId);
                    this.mRemote.transact(43, _data, _reply, 0);
                    _reply.readException();
                    List<Bundle> _result = _reply.createTypedArrayList(Bundle.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public int getFocusedUser() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    this.mRemote.transact(44, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean isKnoxProfileActivePasswordSufficientForParent(int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(45, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean isPasswordSufficientAfterKnoxProfileUnification(int profileUser)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    _data.writeInt(profileUser);
                    this.mRemote.transact(46, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public int setDualDARProfile(Bundle config) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    _data.writeTypedObject(config, 0);
                    this.mRemote.transact(47, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public Bundle getDualDARProfile() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    this.mRemote.transact(48, _data, _reply, 0);
                    _reply.readException();
                    Bundle _result = (Bundle) _reply.readTypedObject(Bundle.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public int setUCMProfile(Bundle config) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    _data.writeTypedObject(config, 0);
                    this.mRemote.transact(49, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public Bundle getUCMProfile() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    this.mRemote.transact(50, _data, _reply, 0);
                    _reply.readException();
                    Bundle _result = (Bundle) _reply.readTypedObject(Bundle.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public int resetUCMProfile() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    this.mRemote.transact(51, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean getPersonaUserHasBeenShutdownBefore(int personaId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    _data.writeInt(personaId);
                    this.mRemote.transact(52, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean appliedPasswordPolicy(int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    this.mRemote.transact(53, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public String getRCPDataPolicy(String appName, String policyProperty)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    _data.writeString(appName);
                    _data.writeString(policyProperty);
                    this.mRemote.transact(54, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public String getRCPDataPolicyForUser(int userId, String appName, String policyProperty)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeString(appName);
                    _data.writeString(policyProperty);
                    this.mRemote.transact(55, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean setRCPDataPolicy(String appName, String policyProperty, String value)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    _data.writeString(appName);
                    _data.writeString(policyProperty);
                    _data.writeString(value);
                    this.mRemote.transact(56, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean isShareClipboardDataToOwnerAllowed(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(57, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean isMoveFilesToContainerAllowed(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(58, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean isMoveFilesToOwnerAllowed(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(59, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public void logDpmsKA(Bundle b) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    _data.writeTypedObject(b, 0);
                    this.mRemote.transact(60, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean isShareClipboardDataToContainerAllowed(int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(61, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public List<String> getSecureFolderPolicy(String key, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    _data.writeString(key);
                    _data.writeInt(userId);
                    this.mRemote.transact(62, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean setSecureFolderPolicy(String key, List<String> pkgList, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    _data.writeString(key);
                    _data.writeStringList(pkgList);
                    _data.writeInt(userId);
                    this.mRemote.transact(63, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public void CMFALock(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(64, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public void CMFAUnLock(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(65, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public Bundle getSeparationConfigfromCache() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    this.mRemote.transact(66, _data, _reply, 0);
                    _reply.readException();
                    Bundle _result = (Bundle) _reply.readTypedObject(Bundle.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public void setAppSeparationDefaultPolicy(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(67, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public void updateProfileActivityTimeFromKnox(int userId, long eventTime)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeLong(eventTime);
                    this.mRemote.transact(68, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean isContainerCorePackageUID(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    _data.writeInt(uid);
                    this.mRemote.transact(69, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public void startCountrySelectionActivity(boolean isUnified) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    _data.writeBoolean(isUnified);
                    this.mRemote.transact(70, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public void startTermsActivity() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    this.mRemote.transact(71, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public void postPwdChangeNotificationForDeviceOwner(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(72, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean isContainerService(int pid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    _data.writeInt(pid);
                    this.mRemote.transact(73, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean sendKnoxForesightBroadcast(Intent intent) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    _data.writeTypedObject(intent, 0);
                    this.mRemote.transact(74, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean hasLicensePermission(int uid, String permission) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeString(permission);
                    this.mRemote.transact(75, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public IBasicCommand getKnoxForesightService() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    this.mRemote.transact(76, _data, _reply, 0);
                    _reply.readException();
                    IBasicCommand _result =
                            IBasicCommand.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 75;
        }
    }
}
