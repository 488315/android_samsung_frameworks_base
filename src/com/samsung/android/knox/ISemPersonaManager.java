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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISemPersonaManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISemPersonaManager)) {
                return (ISemPersonaManager) iInterfaceQueryLocalInterface;
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
                    boolean zIsFOTAUpgrade = isFOTAUpgrade();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsFOTAUpgrade);
                    return true;
                case 2:
                    int i3 = parcel.readInt();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    List<UserInfo> profiles = getProfiles(i3, z);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(profiles, 1);
                    return true;
                case 3:
                    ISystemPersonaObserver iSystemPersonaObserverAsInterface = ISystemPersonaObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean zRegisterSystemPersonaObserver = registerSystemPersonaObserver(iSystemPersonaObserverAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRegisterSystemPersonaObserver);
                    return true;
                case 4:
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setFocusedLauncherId(i4);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    boolean zIsFotaUpgradeVersionChanged = isFotaUpgradeVersionChanged();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsFotaUpgradeVersionChanged);
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
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String containerName = getContainerName(i5);
                    parcel2.writeNoException();
                    parcel2.writeString(containerName);
                    return true;
                case 9:
                    UserInfo userInfo = (UserInfo) parcel.readTypedObject(UserInfo.CREATOR);
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    String workspaceName = getWorkspaceName(userInfo, z2);
                    parcel2.writeNoException();
                    parcel2.writeString(workspaceName);
                    return true;
                case 10:
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String eCName = getECName(i6);
                    parcel2.writeNoException();
                    parcel2.writeString(eCName);
                    return true;
                case 11:
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String profileName = getProfileName(i7);
                    parcel2.writeNoException();
                    parcel2.writeString(profileName);
                    return true;
                case 12:
                    int i8 = parcel.readInt();
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean profileName2 = setProfileName(i8, string);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(profileName2);
                    return true;
                case 13:
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String personalModeName = getPersonalModeName(i9);
                    parcel2.writeNoException();
                    parcel2.writeString(personalModeName);
                    return true;
                case 14:
                    int i10 = parcel.readInt();
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean personalModeName2 = setPersonalModeName(i10, string2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(personalModeName2);
                    return true;
                case 15:
                    String string3 = parcel.readString();
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsPossibleAddAppsToContainer = isPossibleAddAppsToContainer(string3, i11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsPossibleAddAppsToContainer);
                    return true;
                case 16:
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsInSeparatedAppsOnly = isInSeparatedAppsOnly(string4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsInSeparatedAppsOnly);
                    return true;
                case 17:
                    List<String> separatedAppsList = getSeparatedAppsList();
                    parcel2.writeNoException();
                    parcel2.writeStringList(separatedAppsList);
                    return true;
                case 18:
                    boolean zIsAppSeparationPresent = isAppSeparationPresent();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAppSeparationPresent);
                    return true;
                case 19:
                    ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(ResolveInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List updatedListWithAppSeparation = getUpdatedListWithAppSeparation(arrayListCreateTypedArrayList);
                    parcel2.writeNoException();
                    parcel2.writeList(updatedListWithAppSeparation);
                    return true;
                case 20:
                    String string5 = parcel.readString();
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyApplicationChanged(string5, i12);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    int i13 = parcel.readInt();
                    int i14 = parcel.readInt();
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsKnoxWindowExist = isKnoxWindowExist(i13, i14, i15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsKnoxWindowExist);
                    return true;
                case 22:
                    int i16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int containerOrder = getContainerOrder(i16);
                    parcel2.writeNoException();
                    parcel2.writeInt(containerOrder);
                    return true;
                case 23:
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsExternalStorageEnabled = isExternalStorageEnabled(i17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsExternalStorageEnabled);
                    return true;
                case 24:
                    Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zStartActivityThroughPersona = startActivityThroughPersona(intent);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zStartActivityThroughPersona);
                    return true;
                case 25:
                    Intent intent2 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    int i18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zBroadcastIntentThroughPersona = broadcastIntentThroughPersona(intent2, i18);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zBroadcastIntentThroughPersona);
                    return true;
                case 26:
                    int fotaVersion = getFotaVersion();
                    parcel2.writeNoException();
                    parcel2.writeInt(fotaVersion);
                    return true;
                case 27:
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String personaCacheValue = getPersonaCacheValue(string6);
                    parcel2.writeNoException();
                    parcel2.writeString(personaCacheValue);
                    return true;
                case 28:
                    String string7 = parcel.readString();
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zUpdatePersonaCache = updatePersonaCache(string7, string8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zUpdatePersonaCache);
                    return true;
                case 29:
                    int i19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ComponentName adminComponentName = getAdminComponentName(i19);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(adminComponentName, 1);
                    return true;
                case 30:
                    int i20 = parcel.readInt();
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    addAppPackageNameToAllowList(i20, arrayListCreateStringArrayList);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    int focusedLauncherId = getFocusedLauncherId();
                    parcel2.writeNoException();
                    parcel2.writeInt(focusedLauncherId);
                    return true;
                case 32:
                    int i21 = parcel.readInt();
                    int i22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean attributes = setAttributes(i21, i22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(attributes);
                    return true;
                case 33:
                    int i23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int attributes2 = getAttributes(i23);
                    parcel2.writeNoException();
                    parcel2.writeInt(attributes2);
                    return true;
                case 34:
                    int i24 = parcel.readInt();
                    int i25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zClearAttributes = clearAttributes(i24, i25);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zClearAttributes);
                    return true;
                case 35:
                    int i26 = parcel.readInt();
                    String string9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String customResource = getCustomResource(i26, string9);
                    parcel2.writeNoException();
                    parcel2.writeString(customResource);
                    return true;
                case 36:
                    String string10 = parcel.readString();
                    String string11 = parcel.readString();
                    int i27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    byte[] knoxIcon = getKnoxIcon(string10, string11, i27);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(knoxIcon);
                    return true;
                case 37:
                    String string12 = parcel.readString();
                    boolean z3 = parcel.readBoolean();
                    int i28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean packageSettingInstalled = setPackageSettingInstalled(string12, z3, i28);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(packageSettingInstalled);
                    return true;
                case 38:
                    int i29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    refreshLockTimer(i29);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    String string13 = parcel.readString();
                    String string14 = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle bundleSendProxyMessage = sendProxyMessage(string13, string14, bundle);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bundleSendProxyMessage, 1);
                    return true;
                case 40:
                    int i30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    hideMultiWindows(i30);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    IApplicationThread iApplicationThreadAsInterface = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    IBinder strongBinder = parcel.readStrongBinder();
                    Intent intent3 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    IServiceConnection iServiceConnectionAsInterface = IServiceConnection.Stub.asInterface(parcel.readStrongBinder());
                    int i31 = parcel.readInt();
                    int i32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zBindCoreServiceAsUser = bindCoreServiceAsUser(componentName, iApplicationThreadAsInterface, strongBinder, intent3, iServiceConnectionAsInterface, i31, i32);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zBindCoreServiceAsUser);
                    return true;
                case 42:
                    int i33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendRequestKeyStatus(i33);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    int i34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<Bundle> moveToKnoxMenuList = getMoveToKnoxMenuList(i34);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(moveToKnoxMenuList, 1);
                    return true;
                case 44:
                    int focusedUser = getFocusedUser();
                    parcel2.writeNoException();
                    parcel2.writeInt(focusedUser);
                    return true;
                case 45:
                    int i35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsKnoxProfileActivePasswordSufficientForParent = isKnoxProfileActivePasswordSufficientForParent(i35);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsKnoxProfileActivePasswordSufficientForParent);
                    return true;
                case 46:
                    int i36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsPasswordSufficientAfterKnoxProfileUnification = isPasswordSufficientAfterKnoxProfileUnification(i36);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsPasswordSufficientAfterKnoxProfileUnification);
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
                    int iResetUCMProfile = resetUCMProfile();
                    parcel2.writeNoException();
                    parcel2.writeInt(iResetUCMProfile);
                    return true;
                case 52:
                    int i37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean personaUserHasBeenShutdownBefore = getPersonaUserHasBeenShutdownBefore(i37);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(personaUserHasBeenShutdownBefore);
                    return true;
                case 53:
                    int i38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zAppliedPasswordPolicy = appliedPasswordPolicy(i38);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAppliedPasswordPolicy);
                    return true;
                case 54:
                    String string15 = parcel.readString();
                    String string16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String rCPDataPolicy = getRCPDataPolicy(string15, string16);
                    parcel2.writeNoException();
                    parcel2.writeString(rCPDataPolicy);
                    return true;
                case 55:
                    int i39 = parcel.readInt();
                    String string17 = parcel.readString();
                    String string18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String rCPDataPolicyForUser = getRCPDataPolicyForUser(i39, string17, string18);
                    parcel2.writeNoException();
                    parcel2.writeString(rCPDataPolicyForUser);
                    return true;
                case 56:
                    String string19 = parcel.readString();
                    String string20 = parcel.readString();
                    String string21 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean rCPDataPolicy2 = setRCPDataPolicy(string19, string20, string21);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(rCPDataPolicy2);
                    return true;
                case 57:
                    int i40 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsShareClipboardDataToOwnerAllowed = isShareClipboardDataToOwnerAllowed(i40);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsShareClipboardDataToOwnerAllowed);
                    return true;
                case 58:
                    int i41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsMoveFilesToContainerAllowed = isMoveFilesToContainerAllowed(i41);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsMoveFilesToContainerAllowed);
                    return true;
                case 59:
                    int i42 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsMoveFilesToOwnerAllowed = isMoveFilesToOwnerAllowed(i42);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsMoveFilesToOwnerAllowed);
                    return true;
                case 60:
                    Bundle bundle4 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    logDpmsKA(bundle4);
                    parcel2.writeNoException();
                    return true;
                case 61:
                    int i43 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsShareClipboardDataToContainerAllowed = isShareClipboardDataToContainerAllowed(i43);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsShareClipboardDataToContainerAllowed);
                    return true;
                case 62:
                    String string22 = parcel.readString();
                    int i44 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> secureFolderPolicy = getSecureFolderPolicy(string22, i44);
                    parcel2.writeNoException();
                    parcel2.writeStringList(secureFolderPolicy);
                    return true;
                case 63:
                    String string23 = parcel.readString();
                    ArrayList<String> arrayListCreateStringArrayList2 = parcel.createStringArrayList();
                    int i45 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean secureFolderPolicy2 = setSecureFolderPolicy(string23, arrayListCreateStringArrayList2, i45);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(secureFolderPolicy2);
                    return true;
                case 64:
                    int i46 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    CMFALock(i46);
                    parcel2.writeNoException();
                    return true;
                case 65:
                    int i47 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    CMFAUnLock(i47);
                    parcel2.writeNoException();
                    return true;
                case 66:
                    Bundle separationConfigfromCache = getSeparationConfigfromCache();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(separationConfigfromCache, 1);
                    return true;
                case 67:
                    int i48 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAppSeparationDefaultPolicy(i48);
                    parcel2.writeNoException();
                    return true;
                case 68:
                    int i49 = parcel.readInt();
                    long j = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    updateProfileActivityTimeFromKnox(i49, j);
                    parcel2.writeNoException();
                    return true;
                case 69:
                    int i50 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsContainerCorePackageUID = isContainerCorePackageUID(i50);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsContainerCorePackageUID);
                    return true;
                case 70:
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    startCountrySelectionActivity(z4);
                    parcel2.writeNoException();
                    return true;
                case 71:
                    startTermsActivity();
                    parcel2.writeNoException();
                    return true;
                case 72:
                    int i51 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    postPwdChangeNotificationForDeviceOwner(i51);
                    parcel2.writeNoException();
                    return true;
                case 73:
                    int i52 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsContainerService = isContainerService(i52);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsContainerService);
                    return true;
                case 74:
                    Intent intent4 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zSendKnoxForesightBroadcast = sendKnoxForesightBroadcast(intent4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSendKnoxForesightBroadcast);
                    return true;
                case 75:
                    int i53 = parcel.readInt();
                    String string24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zHasLicensePermission = hasLicensePermission(i53, string24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasLicensePermission);
                    return true;
                case 76:
                    IBasicCommand knoxForesightService = getKnoxForesightService();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(knoxForesightService);
                    return true;
                case 77:
                    String string25 = parcel.readString();
                    int i54 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean ddmPolicy = setDdmPolicy(string25, i54);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(ddmPolicy);
                    return true;
                case 78:
                    IntentFilter intentFilter = (IntentFilter) parcel.readTypedObject(IntentFilter.CREATOR);
                    Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    int i55 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerDdmBroadcastReceiver(intentFilter, uri, i55);
                    parcel2.writeNoException();
                    return true;
                case 79:
                    String string26 = parcel.readString();
                    String string27 = parcel.readString();
                    int i56 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zShouldBlockCommand = shouldBlockCommand(string26, string27, i56);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zShouldBlockCommand);
                    return true;
                case 80:
                    boolean zIsUsbDebuggingAllowed = isUsbDebuggingAllowed();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsUsbDebuggingAllowed);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public List<UserInfo> getProfiles(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(UserInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean registerSystemPersonaObserver(ISystemPersonaObserver iSystemPersonaObserver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSystemPersonaObserver);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public void setFocusedLauncherId(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean isFotaUpgradeVersionChanged() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public int getSecureFolderId() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public String getSecureFolderName() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public String getContainerName(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public String getWorkspaceName(UserInfo userInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(userInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public String getECName(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public String getProfileName(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean setProfileName(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public String getPersonalModeName(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean setPersonalModeName(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean isPossibleAddAppsToContainer(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean isInSeparatedAppsOnly(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public List<String> getSeparatedAppsList() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean isAppSeparationPresent() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public List getUpdatedListWithAppSeparation(List<ResolveInfo> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readArrayList(getClass().getClassLoader());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public void notifyApplicationChanged(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean isKnoxWindowExist(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public int getContainerOrder(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean isExternalStorageEnabled(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean startActivityThroughPersona(Intent intent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(intent, 0);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean broadcastIntentThroughPersona(Intent intent, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public int getFotaVersion() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public String getPersonaCacheValue(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean updatePersonaCache(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public ComponentName getAdminComponentName(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ComponentName) parcelObtain2.readTypedObject(ComponentName.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public void addAppPackageNameToAllowList(int i, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public int getFocusedLauncherId() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean setAttributes(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public int getAttributes(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean clearAttributes(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public String getCustomResource(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public byte[] getKnoxIcon(String str, String str2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean setPackageSettingInstalled(String str, boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public void refreshLockTimer(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public Bundle sendProxyMessage(String str, String str2, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public void hideMultiWindows(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean bindCoreServiceAsUser(ComponentName componentName, IApplicationThread iApplicationThread, IBinder iBinder, Intent intent, IServiceConnection iServiceConnection, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeStrongInterface(iApplicationThread);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeStrongInterface(iServiceConnection);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public void sendRequestKeyStatus(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public List<Bundle> getMoveToKnoxMenuList(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public int getFocusedUser() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean isKnoxProfileActivePasswordSufficientForParent(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean isPasswordSufficientAfterKnoxProfileUnification(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public int setDualDARProfile(Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public Bundle getDualDARProfile() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public int setUCMProfile(Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(49, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public Bundle getUCMProfile() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    this.mRemote.transact(50, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public int resetUCMProfile() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    this.mRemote.transact(51, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean getPersonaUserHasBeenShutdownBefore(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(52, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean appliedPasswordPolicy(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(53, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public String getRCPDataPolicy(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(54, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public String getRCPDataPolicyForUser(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(55, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean setRCPDataPolicy(String str, String str2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(56, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean isShareClipboardDataToOwnerAllowed(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(57, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean isMoveFilesToContainerAllowed(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(58, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean isMoveFilesToOwnerAllowed(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(59, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public void logDpmsKA(Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(60, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean isShareClipboardDataToContainerAllowed(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(61, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public List<String> getSecureFolderPolicy(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(62, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean setSecureFolderPolicy(String str, List<String> list, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(63, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public void CMFALock(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(64, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public void CMFAUnLock(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(65, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public Bundle getSeparationConfigfromCache() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    this.mRemote.transact(66, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public void setAppSeparationDefaultPolicy(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(67, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public void updateProfileActivityTimeFromKnox(int i, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(68, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean isContainerCorePackageUID(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(69, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public void startCountrySelectionActivity(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(70, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public void startTermsActivity() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    this.mRemote.transact(71, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public void postPwdChangeNotificationForDeviceOwner(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(72, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean isContainerService(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(73, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean sendKnoxForesightBroadcast(Intent intent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(intent, 0);
                    this.mRemote.transact(74, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean hasLicensePermission(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(75, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public IBasicCommand getKnoxForesightService() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    this.mRemote.transact(76, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IBasicCommand.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean setDdmPolicy(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(77, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public void registerDdmBroadcastReceiver(IntentFilter intentFilter, Uri uri, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(intentFilter, 0);
                    parcelObtain.writeTypedObject(uri, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(78, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean shouldBlockCommand(String str, String str2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(79, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemPersonaManager
            public boolean isUsbDebuggingAllowed() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPersonaManager.DESCRIPTOR);
                    this.mRemote.transact(80, parcelObtain, parcelObtain2, 0);
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
