package com.samsung.android.knox;

import android.app.IApplicationThread;
import android.app.IServiceConnection;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ISystemPersonaObserver;
import android.content.pm.ResolveInfo;
import android.content.pm.UserInfo;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.knox.IBasicCommand;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public interface ISemPersonaManager extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.ISemPersonaManager";

    public static class Default implements ISemPersonaManager {
        @Override // com.samsung.android.knox.ISemPersonaManager
        public void CMFALock(int i) throws RemoteException {
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public void CMFAUnLock(int i) throws RemoteException {
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public void addAppPackageNameToAllowList(int i, List<String> list) throws RemoteException {
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public boolean appliedPasswordPolicy(int i) throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public boolean bindCoreServiceAsUser(ComponentName componentName, IApplicationThread iApplicationThread, IBinder iBinder, Intent intent, IServiceConnection iServiceConnection, int i, int i2) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public boolean broadcastIntentThroughPersona(Intent intent, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public boolean clearAttributes(int i, int i2) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public ComponentName getAdminComponentName(int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public int getAttributes(int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public String getContainerName(int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public int getContainerOrder(int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public String getCustomResource(int i, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public Bundle getDualDARProfile() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public String getECName(int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public int getFocusedLauncherId() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public int getFocusedUser() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public int getFotaVersion() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public IBasicCommand getKnoxForesightService() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public byte[] getKnoxIcon(String str, String str2, int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public List<Bundle> getMoveToKnoxMenuList(int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public String getPersonaCacheValue(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public boolean getPersonaUserHasBeenShutdownBefore(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public String getPersonalModeName(int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public String getProfileName(int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public List<UserInfo> getProfiles(int i, boolean z) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public String getRCPDataPolicy(String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public String getRCPDataPolicyForUser(int i, String str, String str2) throws RemoteException {
            return null;
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
        public List<String> getSecureFolderPolicy(String str, int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public List<String> getSeparatedAppsList() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public Bundle getSeparationConfigfromCache() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public Bundle getUCMProfile() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public List getUpdatedListWithAppSeparation(List<ResolveInfo> list) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public String getWorkspaceName(UserInfo userInfo, boolean z) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public boolean hasLicensePermission(int i, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public void hideMultiWindows(int i) throws RemoteException {
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public boolean isAppSeparationPresent() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public boolean isContainerCorePackageUID(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public boolean isContainerService(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public boolean isExternalStorageEnabled(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public boolean isFOTAUpgrade() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public boolean isFotaUpgradeVersionChanged() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public boolean isInSeparatedAppsOnly(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public boolean isKnoxProfileActivePasswordSufficientForParent(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public boolean isKnoxWindowExist(int i, int i2, int i3) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public boolean isMoveFilesToContainerAllowed(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public boolean isMoveFilesToOwnerAllowed(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public boolean isPasswordSufficientAfterKnoxProfileUnification(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public boolean isPossibleAddAppsToContainer(String str, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public boolean isShareClipboardDataToContainerAllowed(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public boolean isShareClipboardDataToOwnerAllowed(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public boolean isUsbDebuggingAllowed() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public void logDpmsKA(Bundle bundle) throws RemoteException {
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public void notifyApplicationChanged(String str, int i) throws RemoteException {
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public void postPwdChangeNotificationForDeviceOwner(int i) throws RemoteException {
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public void refreshLockTimer(int i) throws RemoteException {
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public void registerDdmBroadcastReceiver(IntentFilter intentFilter, Uri uri, int i) throws RemoteException {
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public boolean registerSystemPersonaObserver(ISystemPersonaObserver iSystemPersonaObserver) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public int resetUCMProfile() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public boolean sendKnoxForesightBroadcast(Intent intent) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public Bundle sendProxyMessage(String str, String str2, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public void sendRequestKeyStatus(int i) throws RemoteException {
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public void setAppSeparationDefaultPolicy(int i) throws RemoteException {
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public boolean setAttributes(int i, int i2) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public boolean setDdmPolicy(String str, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public int setDualDARProfile(Bundle bundle) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public void setFocusedLauncherId(int i) throws RemoteException {
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public boolean setPackageSettingInstalled(String str, boolean z, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public boolean setPersonalModeName(int i, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public boolean setProfileName(int i, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public boolean setRCPDataPolicy(String str, String str2, String str3) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public boolean setSecureFolderPolicy(String str, List<String> list, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public int setUCMProfile(Bundle bundle) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public boolean shouldBlockCommand(String str, String str2, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public boolean startActivityThroughPersona(Intent intent) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public void startCountrySelectionActivity(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public void startTermsActivity() throws RemoteException {
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public boolean updatePersonaCache(String str, String str2) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemPersonaManager
        public void updateProfileActivityTimeFromKnox(int i, long j) throws RemoteException {
        }
    }

    void CMFALock(int i) throws RemoteException;

    void CMFAUnLock(int i) throws RemoteException;

    void addAppPackageNameToAllowList(int i, List<String> list) throws RemoteException;

    boolean appliedPasswordPolicy(int i) throws RemoteException;

    boolean bindCoreServiceAsUser(ComponentName componentName, IApplicationThread iApplicationThread, IBinder iBinder, Intent intent, IServiceConnection iServiceConnection, int i, int i2) throws RemoteException;

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

    boolean isUsbDebuggingAllowed() throws RemoteException;

    void logDpmsKA(Bundle bundle) throws RemoteException;

    void notifyApplicationChanged(String str, int i) throws RemoteException;

    void postPwdChangeNotificationForDeviceOwner(int i) throws RemoteException;

    void refreshLockTimer(int i) throws RemoteException;

    void registerDdmBroadcastReceiver(IntentFilter intentFilter, Uri uri, int i) throws RemoteException;

    boolean registerSystemPersonaObserver(ISystemPersonaObserver iSystemPersonaObserver) throws RemoteException;

    int resetUCMProfile() throws RemoteException;

    boolean sendKnoxForesightBroadcast(Intent intent) throws RemoteException;

    Bundle sendProxyMessage(String str, String str2, Bundle bundle) throws RemoteException;

    void sendRequestKeyStatus(int i) throws RemoteException;

    void setAppSeparationDefaultPolicy(int i) throws RemoteException;

    boolean setAttributes(int i, int i2) throws RemoteException;

    boolean setDdmPolicy(String str, int i) throws RemoteException;

    int setDualDARProfile(Bundle bundle) throws RemoteException;

    void setFocusedLauncherId(int i) throws RemoteException;

    boolean setPackageSettingInstalled(String str, boolean z, int i) throws RemoteException;

    boolean setPersonalModeName(int i, String str) throws RemoteException;

    boolean setProfileName(int i, String str) throws RemoteException;

    boolean setRCPDataPolicy(String str, String str2, String str3) throws RemoteException;

    boolean setSecureFolderPolicy(String str, List<String> list, int i) throws RemoteException;

    int setUCMProfile(Bundle bundle) throws RemoteException;

    boolean shouldBlockCommand(String str, String str2, int i) throws RemoteException;

    boolean startActivityThroughPersona(Intent intent) throws RemoteException;

    void startCountrySelectionActivity(boolean z) throws RemoteException;

    void startTermsActivity() throws RemoteException;

    boolean updatePersonaCache(String str, String str2) throws RemoteException;

    void updateProfileActivityTimeFromKnox(int i, long j) throws RemoteException;

    public static abstract class Stub extends Binder implements ISemPersonaManager {
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
        static final int TRANSACTION_isUsbDebuggingAllowed = 80;
        static final int TRANSACTION_logDpmsKA = 60;
        static final int TRANSACTION_notifyApplicationChanged = 20;
        static final int TRANSACTION_postPwdChangeNotificationForDeviceOwner = 72;
        static final int TRANSACTION_refreshLockTimer = 38;
        static final int TRANSACTION_registerDdmBroadcastReceiver = 78;
        static final int TRANSACTION_registerSystemPersonaObserver = 3;
        static final int TRANSACTION_resetUCMProfile = 51;
        static final int TRANSACTION_sendKnoxForesightBroadcast = 74;
        static final int TRANSACTION_sendProxyMessage = 39;
        static final int TRANSACTION_sendRequestKeyStatus = 42;
        static final int TRANSACTION_setAppSeparationDefaultPolicy = 67;
        static final int TRANSACTION_setAttributes = 32;
        static final int TRANSACTION_setDdmPolicy = 77;
        static final int TRANSACTION_setDualDARProfile = 47;
        static final int TRANSACTION_setFocusedLauncherId = 4;
        static final int TRANSACTION_setPackageSettingInstalled = 37;
        static final int TRANSACTION_setPersonalModeName = 14;
        static final int TRANSACTION_setProfileName = 12;
        static final int TRANSACTION_setRCPDataPolicy = 56;
        static final int TRANSACTION_setSecureFolderPolicy = 63;
        static final int TRANSACTION_setUCMProfile = 49;
        static final int TRANSACTION_shouldBlockCommand = 79;
        static final int TRANSACTION_startActivityThroughPersona = 24;
        static final int TRANSACTION_startCountrySelectionActivity = 70;
        static final int TRANSACTION_startTermsActivity = 71;
        static final int TRANSACTION_updatePersonaCache = 28;
        static final int TRANSACTION_updateProfileActivityTimeFromKnox = 68;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 79;
        }

        public Stub() {
            attachInterface(this, ISemPersonaManager.DESCRIPTOR);
        }

        public static ISemPersonaManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISemPersonaManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISemPersonaManager)) {
                return (ISemPersonaManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
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
                case 77:
                    return "setDdmPolicy";
                case 78:
                    return "registerDdmBroadcastReceiver";
                case 79:
                    return "shouldBlockCommand";
                case 80:
                    return "isUsbDebuggingAllowed";
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
                parcel.enforceInterface(ISemPersonaManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISemPersonaManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    boolean isFOTAUpgrade = isFOTAUpgrade();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isFOTAUpgrade);
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    List<UserInfo> profiles = getProfiles(readInt, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(profiles, 1);
                    return true;
                case 3:
                    ISystemPersonaObserver asInterface = ISystemPersonaObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean registerSystemPersonaObserver = registerSystemPersonaObserver(asInterface);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(registerSystemPersonaObserver);
                    return true;
                case 4:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setFocusedLauncherId(readInt2);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    boolean isFotaUpgradeVersionChanged = isFotaUpgradeVersionChanged();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isFotaUpgradeVersionChanged);
                    return true;
                case 6:
                    int secureFolderId = getSecureFolderId();
                    parcel2.writeNoException();
                    parcel2.writeInt(secureFolderId);
                    return true;
                case 7:
                    String secureFolderName = getSecureFolderName();
                    parcel2.writeNoException();
                    parcel2.writeString(secureFolderName);
                    return true;
                case 8:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String containerName = getContainerName(readInt3);
                    parcel2.writeNoException();
                    parcel2.writeString(containerName);
                    return true;
                case 9:
                    UserInfo userInfo = (UserInfo) parcel.readTypedObject(UserInfo.CREATOR);
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    String workspaceName = getWorkspaceName(userInfo, readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeString(workspaceName);
                    return true;
                case 10:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String eCName = getECName(readInt4);
                    parcel2.writeNoException();
                    parcel2.writeString(eCName);
                    return true;
                case 11:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String profileName = getProfileName(readInt5);
                    parcel2.writeNoException();
                    parcel2.writeString(profileName);
                    return true;
                case 12:
                    int readInt6 = parcel.readInt();
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean profileName2 = setProfileName(readInt6, readString);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(profileName2);
                    return true;
                case 13:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String personalModeName = getPersonalModeName(readInt7);
                    parcel2.writeNoException();
                    parcel2.writeString(personalModeName);
                    return true;
                case 14:
                    int readInt8 = parcel.readInt();
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean personalModeName2 = setPersonalModeName(readInt8, readString2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(personalModeName2);
                    return true;
                case 15:
                    String readString3 = parcel.readString();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isPossibleAddAppsToContainer = isPossibleAddAppsToContainer(readString3, readInt9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPossibleAddAppsToContainer);
                    return true;
                case 16:
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isInSeparatedAppsOnly = isInSeparatedAppsOnly(readString4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isInSeparatedAppsOnly);
                    return true;
                case 17:
                    List<String> separatedAppsList = getSeparatedAppsList();
                    parcel2.writeNoException();
                    parcel2.writeStringList(separatedAppsList);
                    return true;
                case 18:
                    boolean isAppSeparationPresent = isAppSeparationPresent();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAppSeparationPresent);
                    return true;
                case 19:
                    ArrayList createTypedArrayList = parcel.createTypedArrayList(ResolveInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List updatedListWithAppSeparation = getUpdatedListWithAppSeparation(createTypedArrayList);
                    parcel2.writeNoException();
                    parcel2.writeList(updatedListWithAppSeparation);
                    return true;
                case 20:
                    String readString5 = parcel.readString();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyApplicationChanged(readString5, readInt10);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isKnoxWindowExist = isKnoxWindowExist(readInt11, readInt12, readInt13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isKnoxWindowExist);
                    return true;
                case 22:
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int containerOrder = getContainerOrder(readInt14);
                    parcel2.writeNoException();
                    parcel2.writeInt(containerOrder);
                    return true;
                case 23:
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isExternalStorageEnabled = isExternalStorageEnabled(readInt15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isExternalStorageEnabled);
                    return true;
                case 24:
                    Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean startActivityThroughPersona = startActivityThroughPersona(intent);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(startActivityThroughPersona);
                    return true;
                case 25:
                    Intent intent2 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean broadcastIntentThroughPersona = broadcastIntentThroughPersona(intent2, readInt16);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(broadcastIntentThroughPersona);
                    return true;
                case 26:
                    int fotaVersion = getFotaVersion();
                    parcel2.writeNoException();
                    parcel2.writeInt(fotaVersion);
                    return true;
                case 27:
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String personaCacheValue = getPersonaCacheValue(readString6);
                    parcel2.writeNoException();
                    parcel2.writeString(personaCacheValue);
                    return true;
                case 28:
                    String readString7 = parcel.readString();
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean updatePersonaCache = updatePersonaCache(readString7, readString8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(updatePersonaCache);
                    return true;
                case 29:
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ComponentName adminComponentName = getAdminComponentName(readInt17);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(adminComponentName, 1);
                    return true;
                case 30:
                    int readInt18 = parcel.readInt();
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    addAppPackageNameToAllowList(readInt18, createStringArrayList);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    int focusedLauncherId = getFocusedLauncherId();
                    parcel2.writeNoException();
                    parcel2.writeInt(focusedLauncherId);
                    return true;
                case 32:
                    int readInt19 = parcel.readInt();
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean attributes = setAttributes(readInt19, readInt20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(attributes);
                    return true;
                case 33:
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int attributes2 = getAttributes(readInt21);
                    parcel2.writeNoException();
                    parcel2.writeInt(attributes2);
                    return true;
                case 34:
                    int readInt22 = parcel.readInt();
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean clearAttributes = clearAttributes(readInt22, readInt23);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(clearAttributes);
                    return true;
                case 35:
                    int readInt24 = parcel.readInt();
                    String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String customResource = getCustomResource(readInt24, readString9);
                    parcel2.writeNoException();
                    parcel2.writeString(customResource);
                    return true;
                case 36:
                    String readString10 = parcel.readString();
                    String readString11 = parcel.readString();
                    int readInt25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    byte[] knoxIcon = getKnoxIcon(readString10, readString11, readInt25);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(knoxIcon);
                    return true;
                case 37:
                    String readString12 = parcel.readString();
                    boolean readBoolean3 = parcel.readBoolean();
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean packageSettingInstalled = setPackageSettingInstalled(readString12, readBoolean3, readInt26);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(packageSettingInstalled);
                    return true;
                case 38:
                    int readInt27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    refreshLockTimer(readInt27);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    String readString13 = parcel.readString();
                    String readString14 = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle sendProxyMessage = sendProxyMessage(readString13, readString14, bundle);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(sendProxyMessage, 1);
                    return true;
                case 40:
                    int readInt28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    hideMultiWindows(readInt28);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    IApplicationThread asInterface2 = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    Intent intent3 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    IServiceConnection asInterface3 = IServiceConnection.Stub.asInterface(parcel.readStrongBinder());
                    int readInt29 = parcel.readInt();
                    int readInt30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean bindCoreServiceAsUser = bindCoreServiceAsUser(componentName, asInterface2, readStrongBinder, intent3, asInterface3, readInt29, readInt30);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(bindCoreServiceAsUser);
                    return true;
                case 42:
                    int readInt31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendRequestKeyStatus(readInt31);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    int readInt32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<Bundle> moveToKnoxMenuList = getMoveToKnoxMenuList(readInt32);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(moveToKnoxMenuList, 1);
                    return true;
                case 44:
                    int focusedUser = getFocusedUser();
                    parcel2.writeNoException();
                    parcel2.writeInt(focusedUser);
                    return true;
                case 45:
                    int readInt33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isKnoxProfileActivePasswordSufficientForParent = isKnoxProfileActivePasswordSufficientForParent(readInt33);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isKnoxProfileActivePasswordSufficientForParent);
                    return true;
                case 46:
                    int readInt34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isPasswordSufficientAfterKnoxProfileUnification = isPasswordSufficientAfterKnoxProfileUnification(readInt34);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPasswordSufficientAfterKnoxProfileUnification);
                    return true;
                case 47:
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int dualDARProfile = setDualDARProfile(bundle2);
                    parcel2.writeNoException();
                    parcel2.writeInt(dualDARProfile);
                    return true;
                case 48:
                    Bundle dualDARProfile2 = getDualDARProfile();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(dualDARProfile2, 1);
                    return true;
                case 49:
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int uCMProfile = setUCMProfile(bundle3);
                    parcel2.writeNoException();
                    parcel2.writeInt(uCMProfile);
                    return true;
                case 50:
                    Bundle uCMProfile2 = getUCMProfile();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(uCMProfile2, 1);
                    return true;
                case 51:
                    int resetUCMProfile = resetUCMProfile();
                    parcel2.writeNoException();
                    parcel2.writeInt(resetUCMProfile);
                    return true;
                case 52:
                    int readInt35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean personaUserHasBeenShutdownBefore = getPersonaUserHasBeenShutdownBefore(readInt35);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(personaUserHasBeenShutdownBefore);
                    return true;
                case 53:
                    int readInt36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean appliedPasswordPolicy = appliedPasswordPolicy(readInt36);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(appliedPasswordPolicy);
                    return true;
                case 54:
                    String readString15 = parcel.readString();
                    String readString16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String rCPDataPolicy = getRCPDataPolicy(readString15, readString16);
                    parcel2.writeNoException();
                    parcel2.writeString(rCPDataPolicy);
                    return true;
                case 55:
                    int readInt37 = parcel.readInt();
                    String readString17 = parcel.readString();
                    String readString18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String rCPDataPolicyForUser = getRCPDataPolicyForUser(readInt37, readString17, readString18);
                    parcel2.writeNoException();
                    parcel2.writeString(rCPDataPolicyForUser);
                    return true;
                case 56:
                    String readString19 = parcel.readString();
                    String readString20 = parcel.readString();
                    String readString21 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean rCPDataPolicy2 = setRCPDataPolicy(readString19, readString20, readString21);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(rCPDataPolicy2);
                    return true;
                case 57:
                    int readInt38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isShareClipboardDataToOwnerAllowed = isShareClipboardDataToOwnerAllowed(readInt38);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isShareClipboardDataToOwnerAllowed);
                    return true;
                case 58:
                    int readInt39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isMoveFilesToContainerAllowed = isMoveFilesToContainerAllowed(readInt39);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isMoveFilesToContainerAllowed);
                    return true;
                case 59:
                    int readInt40 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isMoveFilesToOwnerAllowed = isMoveFilesToOwnerAllowed(readInt40);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isMoveFilesToOwnerAllowed);
                    return true;
                case 60:
                    Bundle bundle4 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    logDpmsKA(bundle4);
                    parcel2.writeNoException();
                    return true;
                case 61:
                    int readInt41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isShareClipboardDataToContainerAllowed = isShareClipboardDataToContainerAllowed(readInt41);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isShareClipboardDataToContainerAllowed);
                    return true;
                case 62:
                    String readString22 = parcel.readString();
                    int readInt42 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> secureFolderPolicy = getSecureFolderPolicy(readString22, readInt42);
                    parcel2.writeNoException();
                    parcel2.writeStringList(secureFolderPolicy);
                    return true;
                case 63:
                    String readString23 = parcel.readString();
                    ArrayList<String> createStringArrayList2 = parcel.createStringArrayList();
                    int readInt43 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean secureFolderPolicy2 = setSecureFolderPolicy(readString23, createStringArrayList2, readInt43);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(secureFolderPolicy2);
                    return true;
                case 64:
                    int readInt44 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    CMFALock(readInt44);
                    parcel2.writeNoException();
                    return true;
                case 65:
                    int readInt45 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    CMFAUnLock(readInt45);
                    parcel2.writeNoException();
                    return true;
                case 66:
                    Bundle separationConfigfromCache = getSeparationConfigfromCache();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(separationConfigfromCache, 1);
                    return true;
                case 67:
                    int readInt46 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAppSeparationDefaultPolicy(readInt46);
                    parcel2.writeNoException();
                    return true;
                case 68:
                    int readInt47 = parcel.readInt();
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    updateProfileActivityTimeFromKnox(readInt47, readLong);
                    parcel2.writeNoException();
                    return true;
                case 69:
                    int readInt48 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isContainerCorePackageUID = isContainerCorePackageUID(readInt48);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isContainerCorePackageUID);
                    return true;
                case 70:
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    startCountrySelectionActivity(readBoolean4);
                    parcel2.writeNoException();
                    return true;
                case 71:
                    startTermsActivity();
                    parcel2.writeNoException();
                    return true;
                case 72:
                    int readInt49 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    postPwdChangeNotificationForDeviceOwner(readInt49);
                    parcel2.writeNoException();
                    return true;
                case 73:
                    int readInt50 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isContainerService = isContainerService(readInt50);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isContainerService);
                    return true;
                case 74:
                    Intent intent4 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean sendKnoxForesightBroadcast = sendKnoxForesightBroadcast(intent4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(sendKnoxForesightBroadcast);
                    return true;
                case 75:
                    int readInt51 = parcel.readInt();
                    String readString24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean hasLicensePermission = hasLicensePermission(readInt51, readString24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasLicensePermission);
                    return true;
                case 76:
                    IBasicCommand knoxForesightService = getKnoxForesightService();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(knoxForesightService);
                    return true;
                case 77:
                    String readString25 = parcel.readString();
                    int readInt52 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean ddmPolicy = setDdmPolicy(readString25, readInt52);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(ddmPolicy);
                    return true;
                case 78:
                    IntentFilter intentFilter = (IntentFilter) parcel.readTypedObject(IntentFilter.CREATOR);
                    Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    int readInt53 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerDdmBroadcastReceiver(intentFilter, uri, readInt53);
                    parcel2.writeNoException();
                    return true;
                case 79:
                    String readString26 = parcel.readString();
                    String readString27 = parcel.readString();
                    int readInt54 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean shouldBlockCommand = shouldBlockCommand(readString26, readString27, readInt54);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(shouldBlockCommand);
                    return true;
                case 80:
                    boolean isUsbDebuggingAllowed = isUsbDebuggingAllowed();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUsbDebuggingAllowed);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISemPersonaManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public List<UserInfo> getProfiles(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(UserInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean registerSystemPersonaObserver(ISystemPersonaObserver iSystemPersonaObserver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iSystemPersonaObserver);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public void setFocusedLauncherId(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean isFotaUpgradeVersionChanged() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public int getSecureFolderId() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public String getSecureFolderName() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public String getContainerName(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public String getWorkspaceName(UserInfo userInfo, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    obtain.writeTypedObject(userInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public String getECName(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public String getProfileName(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean setProfileName(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public String getPersonalModeName(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean setPersonalModeName(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean isPossibleAddAppsToContainer(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean isInSeparatedAppsOnly(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public List<String> getSeparatedAppsList() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean isAppSeparationPresent() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public List getUpdatedListWithAppSeparation(List<ResolveInfo> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readArrayList(getClass().getClassLoader());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public void notifyApplicationChanged(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean isKnoxWindowExist(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public int getContainerOrder(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean isExternalStorageEnabled(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean startActivityThroughPersona(Intent intent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    obtain.writeTypedObject(intent, 0);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean broadcastIntentThroughPersona(Intent intent, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public int getFotaVersion() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public String getPersonaCacheValue(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean updatePersonaCache(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public ComponentName getAdminComponentName(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ComponentName) obtain2.readTypedObject(ComponentName.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public void addAppPackageNameToAllowList(int i, List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStringList(list);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public int getFocusedLauncherId() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean setAttributes(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public int getAttributes(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean clearAttributes(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public String getCustomResource(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public byte[] getKnoxIcon(String str, String str2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean setPackageSettingInstalled(String str, boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public void refreshLockTimer(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public Bundle sendProxyMessage(String str, String str2, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public void hideMultiWindows(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean bindCoreServiceAsUser(ComponentName componentName, IApplicationThread iApplicationThread, IBinder iBinder, Intent intent, IServiceConnection iServiceConnection, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeStrongInterface(iServiceConnection);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public void sendRequestKeyStatus(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public List<Bundle> getMoveToKnoxMenuList(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public int getFocusedUser() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean isKnoxProfileActivePasswordSufficientForParent(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean isPasswordSufficientAfterKnoxProfileUnification(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public int setDualDARProfile(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public Bundle getDualDARProfile() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public int setUCMProfile(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public Bundle getUCMProfile() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public int resetUCMProfile() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean getPersonaUserHasBeenShutdownBefore(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean appliedPasswordPolicy(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public String getRCPDataPolicy(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public String getRCPDataPolicyForUser(int i, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean setRCPDataPolicy(String str, String str2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean isShareClipboardDataToOwnerAllowed(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean isMoveFilesToContainerAllowed(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean isMoveFilesToOwnerAllowed(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public void logDpmsKA(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean isShareClipboardDataToContainerAllowed(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public List<String> getSecureFolderPolicy(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(62, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean setSecureFolderPolicy(String str, List<String> list, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStringList(list);
                    obtain.writeInt(i);
                    this.mRemote.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public void CMFALock(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public void CMFAUnLock(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(65, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public Bundle getSeparationConfigfromCache() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    this.mRemote.transact(66, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public void setAppSeparationDefaultPolicy(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(67, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public void updateProfileActivityTimeFromKnox(int i, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    this.mRemote.transact(68, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean isContainerCorePackageUID(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(69, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public void startCountrySelectionActivity(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(70, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public void startTermsActivity() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    this.mRemote.transact(71, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public void postPwdChangeNotificationForDeviceOwner(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(72, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean isContainerService(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(73, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean sendKnoxForesightBroadcast(Intent intent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    obtain.writeTypedObject(intent, 0);
                    this.mRemote.transact(74, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean hasLicensePermission(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(75, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public IBasicCommand getKnoxForesightService() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    this.mRemote.transact(76, obtain, obtain2, 0);
                    obtain2.readException();
                    return IBasicCommand.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean setDdmPolicy(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(77, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public void registerDdmBroadcastReceiver(IntentFilter intentFilter, Uri uri, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    obtain.writeTypedObject(intentFilter, 0);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(78, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean shouldBlockCommand(String str, String str2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(79, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean isUsbDebuggingAllowed() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    this.mRemote.transact(80, obtain, obtain2, 0);
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
