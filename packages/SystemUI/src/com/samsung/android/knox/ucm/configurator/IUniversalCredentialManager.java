package com.samsung.android.knox.ucm.configurator;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.knox.AppIdentity;
import com.samsung.android.knox.ContextInfo;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface IUniversalCredentialManager extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager";

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Default implements IUniversalCredentialManager {
        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public int addPackagesToExemptList(ContextInfo contextInfo, CredentialStorage credentialStorage, int i, List<AppIdentity> list) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public int addPackagesToWhiteList(ContextInfo contextInfo, CredentialStorage credentialStorage, List<AppIdentity> list, Bundle bundle) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public int addPackagesToWhiteListInternal(int i, int i2, CredentialStorage credentialStorage, List<AppIdentity> list, Bundle bundle) throws RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public int changeKeyguardPin(ContextInfo contextInfo, CredentialStorage credentialStorage, String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public int clearWhiteList(ContextInfo contextInfo, CredentialStorage credentialStorage, Bundle bundle) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public int configureCredentialStorageForODESettings(ContextInfo contextInfo, CredentialStorage credentialStorage, Bundle bundle) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public int configureCredentialStoragePlugin(ContextInfo contextInfo, CredentialStorage credentialStorage, Bundle bundle) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public int configureWpcDar(ContextInfo contextInfo, CredentialStorage credentialStorage) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public int deleteCACertificate(ContextInfo contextInfo, String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public int deleteCertificate(ContextInfo contextInfo, CredentialStorage credentialStorage, String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public int deleteCertificateInternal(int i, int i2, CredentialStorage credentialStorage, String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public int enableCredentialStorageForLockType(ContextInfo contextInfo, CredentialStorage credentialStorage, boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public int enforceCredentialStorageAsLockType(ContextInfo contextInfo, CredentialStorage credentialStorage, Bundle bundle) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public Bundle generateKeyPair(ContextInfo contextInfo, CredentialStorage credentialStorage, String str, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public int getAdminForEnforcedCredentialStorageAsUser(int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public String[] getAliases(ContextInfo contextInfo, CredentialStorage credentialStorage) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public String[] getAllCertificateAliases(CredentialStorage credentialStorage) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public int getAuthType(ContextInfo contextInfo, CredentialStorage credentialStorage) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public CredentialStorage[] getAvailableCredentialStorages(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public CACertificateInfo getCACertificate(ContextInfo contextInfo, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public String[] getCACertificateAliases(ContextInfo contextInfo, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public String[] getCertificateAliases(int i, CredentialStorage credentialStorage) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public String[] getCertificateAliasesAsUser(int i, CredentialStorage credentialStorage) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public Bundle getCredentialStoragePluginConfiguration(ContextInfo contextInfo, CredentialStorage credentialStorage) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public Bundle getCredentialStorageProperty(ContextInfo contextInfo, CredentialStorage credentialStorage, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public CredentialStorage[] getCredentialStorages(ContextInfo contextInfo, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public CredentialStorage getDefaultInstallStorage(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public CredentialStorage getDefaultInstallStorageAsUser(int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public CredentialStorage getEnforcedCredentialStorageForLockType(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public CredentialStorage getEnforcedCredentialStorageForLockTypeAsUser(int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public int getKeyguardPinCurrentRetryCount(ContextInfo contextInfo, CredentialStorage credentialStorage) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public int getKeyguardPinMaximumLength(ContextInfo contextInfo, CredentialStorage credentialStorage) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public int getKeyguardPinMaximumRetryCount(ContextInfo contextInfo, CredentialStorage credentialStorage) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public int getKeyguardPinMinimumLength(ContextInfo contextInfo, CredentialStorage credentialStorage) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public Bundle getODESettingsConfiguration(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public List<AppIdentity> getPackagesFromExemptList(ContextInfo contextInfo, CredentialStorage credentialStorage, int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public List<AppIdentity> getPackagesFromWhiteList(ContextInfo contextInfo, CredentialStorage credentialStorage, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public int getStorageAuthenticationType(int i, CredentialStorage credentialStorage) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public String[] getSupportedAlgorithms(ContextInfo contextInfo, CredentialStorage credentialStorage) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public String[] getWifiCertificateAliasesAsUser(int i, CredentialStorage credentialStorage) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public int initKeyguardPin(ContextInfo contextInfo, CredentialStorage credentialStorage, String str, Bundle bundle) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public Bundle initPluginForWpc(ContextInfo contextInfo, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public int installCACertificate(ContextInfo contextInfo, byte[] bArr, String str, Bundle bundle) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public int installCertificate(ContextInfo contextInfo, CredentialStorage credentialStorage, byte[] bArr, String str, String str2, Bundle bundle) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public int installCertificateInternal(int i, int i2, CredentialStorage credentialStorage, byte[] bArr, String str, Bundle bundle, boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public boolean isAccessAllowed(int i, CredentialStorage credentialStorage, Bundle bundle) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public boolean isCallerDelegated(ContextInfo contextInfo, CredentialStorage credentialStorage, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public boolean isCredentialStorageEnabledForLockType(ContextInfo contextInfo, CredentialStorage credentialStorage) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public boolean isCredentialStorageEnabledForLockTypeAsUser(int i, CredentialStorage credentialStorage) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public boolean isCredentialStorageLocked(ContextInfo contextInfo, CredentialStorage credentialStorage) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public boolean isCredentialStorageLockedAsUser(int i, CredentialStorage credentialStorage) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public boolean isCredentialStorageManaged(ContextInfo contextInfo, CredentialStorage credentialStorage) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public boolean isCredentialStorageManagedAsUser(int i, CredentialStorage credentialStorage) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public boolean isPackageFromExemptList(int i, CredentialStorage credentialStorage, int i2) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public int lockCredentialStorage(ContextInfo contextInfo, CredentialStorage credentialStorage, boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public int manageCredentialStorage(ContextInfo contextInfo, CredentialStorage credentialStorage, boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public boolean notifyLicenseStatus(int i, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public int removeODESettingsForWPC() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public int removePackagesFromExemptList(ContextInfo contextInfo, CredentialStorage credentialStorage, int i, List<AppIdentity> list) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public int removePackagesFromWhiteList(ContextInfo contextInfo, CredentialStorage credentialStorage, List<AppIdentity> list, Bundle bundle) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public int setAuthType(ContextInfo contextInfo, CredentialStorage credentialStorage, int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public Bundle setCredentialStorageProperty(ContextInfo contextInfo, CredentialStorage credentialStorage, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public int setDefaultInstallStorage(ContextInfo contextInfo, CredentialStorage credentialStorage) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public boolean setKeyPairCertificate(ContextInfo contextInfo, CredentialStorage credentialStorage, String str, byte[] bArr) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public int setKeyguardPinMaximumLength(ContextInfo contextInfo, CredentialStorage credentialStorage, int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public int setKeyguardPinMaximumRetryCount(ContextInfo contextInfo, CredentialStorage credentialStorage, int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
        public int setKeyguardPinMinimumLength(ContextInfo contextInfo, CredentialStorage credentialStorage, int i) throws RemoteException {
            return 0;
        }
    }

    int addPackagesToExemptList(ContextInfo contextInfo, CredentialStorage credentialStorage, int i, List<AppIdentity> list) throws RemoteException;

    int addPackagesToWhiteList(ContextInfo contextInfo, CredentialStorage credentialStorage, List<AppIdentity> list, Bundle bundle) throws RemoteException;

    int addPackagesToWhiteListInternal(int i, int i2, CredentialStorage credentialStorage, List<AppIdentity> list, Bundle bundle) throws RemoteException;

    int changeKeyguardPin(ContextInfo contextInfo, CredentialStorage credentialStorage, String str, String str2) throws RemoteException;

    int clearWhiteList(ContextInfo contextInfo, CredentialStorage credentialStorage, Bundle bundle) throws RemoteException;

    int configureCredentialStorageForODESettings(ContextInfo contextInfo, CredentialStorage credentialStorage, Bundle bundle) throws RemoteException;

    int configureCredentialStoragePlugin(ContextInfo contextInfo, CredentialStorage credentialStorage, Bundle bundle) throws RemoteException;

    int configureWpcDar(ContextInfo contextInfo, CredentialStorage credentialStorage) throws RemoteException;

    int deleteCACertificate(ContextInfo contextInfo, String str) throws RemoteException;

    int deleteCertificate(ContextInfo contextInfo, CredentialStorage credentialStorage, String str) throws RemoteException;

    int deleteCertificateInternal(int i, int i2, CredentialStorage credentialStorage, String str) throws RemoteException;

    int enableCredentialStorageForLockType(ContextInfo contextInfo, CredentialStorage credentialStorage, boolean z) throws RemoteException;

    int enforceCredentialStorageAsLockType(ContextInfo contextInfo, CredentialStorage credentialStorage, Bundle bundle) throws RemoteException;

    Bundle generateKeyPair(ContextInfo contextInfo, CredentialStorage credentialStorage, String str, Bundle bundle) throws RemoteException;

    int getAdminForEnforcedCredentialStorageAsUser(int i) throws RemoteException;

    String[] getAliases(ContextInfo contextInfo, CredentialStorage credentialStorage) throws RemoteException;

    String[] getAllCertificateAliases(CredentialStorage credentialStorage) throws RemoteException;

    int getAuthType(ContextInfo contextInfo, CredentialStorage credentialStorage) throws RemoteException;

    CredentialStorage[] getAvailableCredentialStorages(ContextInfo contextInfo) throws RemoteException;

    CACertificateInfo getCACertificate(ContextInfo contextInfo, String str) throws RemoteException;

    String[] getCACertificateAliases(ContextInfo contextInfo, Bundle bundle) throws RemoteException;

    String[] getCertificateAliases(int i, CredentialStorage credentialStorage) throws RemoteException;

    String[] getCertificateAliasesAsUser(int i, CredentialStorage credentialStorage) throws RemoteException;

    Bundle getCredentialStoragePluginConfiguration(ContextInfo contextInfo, CredentialStorage credentialStorage) throws RemoteException;

    Bundle getCredentialStorageProperty(ContextInfo contextInfo, CredentialStorage credentialStorage, Bundle bundle) throws RemoteException;

    CredentialStorage[] getCredentialStorages(ContextInfo contextInfo, String str) throws RemoteException;

    CredentialStorage getDefaultInstallStorage(ContextInfo contextInfo) throws RemoteException;

    CredentialStorage getDefaultInstallStorageAsUser(int i) throws RemoteException;

    CredentialStorage getEnforcedCredentialStorageForLockType(ContextInfo contextInfo) throws RemoteException;

    CredentialStorage getEnforcedCredentialStorageForLockTypeAsUser(int i) throws RemoteException;

    int getKeyguardPinCurrentRetryCount(ContextInfo contextInfo, CredentialStorage credentialStorage) throws RemoteException;

    int getKeyguardPinMaximumLength(ContextInfo contextInfo, CredentialStorage credentialStorage) throws RemoteException;

    int getKeyguardPinMaximumRetryCount(ContextInfo contextInfo, CredentialStorage credentialStorage) throws RemoteException;

    int getKeyguardPinMinimumLength(ContextInfo contextInfo, CredentialStorage credentialStorage) throws RemoteException;

    Bundle getODESettingsConfiguration(ContextInfo contextInfo) throws RemoteException;

    List<AppIdentity> getPackagesFromExemptList(ContextInfo contextInfo, CredentialStorage credentialStorage, int i) throws RemoteException;

    List<AppIdentity> getPackagesFromWhiteList(ContextInfo contextInfo, CredentialStorage credentialStorage, Bundle bundle) throws RemoteException;

    int getStorageAuthenticationType(int i, CredentialStorage credentialStorage) throws RemoteException;

    String[] getSupportedAlgorithms(ContextInfo contextInfo, CredentialStorage credentialStorage) throws RemoteException;

    String[] getWifiCertificateAliasesAsUser(int i, CredentialStorage credentialStorage) throws RemoteException;

    int initKeyguardPin(ContextInfo contextInfo, CredentialStorage credentialStorage, String str, Bundle bundle) throws RemoteException;

    Bundle initPluginForWpc(ContextInfo contextInfo, String str) throws RemoteException;

    int installCACertificate(ContextInfo contextInfo, byte[] bArr, String str, Bundle bundle) throws RemoteException;

    int installCertificate(ContextInfo contextInfo, CredentialStorage credentialStorage, byte[] bArr, String str, String str2, Bundle bundle) throws RemoteException;

    int installCertificateInternal(int i, int i2, CredentialStorage credentialStorage, byte[] bArr, String str, Bundle bundle, boolean z) throws RemoteException;

    boolean isAccessAllowed(int i, CredentialStorage credentialStorage, Bundle bundle) throws RemoteException;

    boolean isCallerDelegated(ContextInfo contextInfo, CredentialStorage credentialStorage, int i) throws RemoteException;

    boolean isCredentialStorageEnabledForLockType(ContextInfo contextInfo, CredentialStorage credentialStorage) throws RemoteException;

    boolean isCredentialStorageEnabledForLockTypeAsUser(int i, CredentialStorage credentialStorage) throws RemoteException;

    boolean isCredentialStorageLocked(ContextInfo contextInfo, CredentialStorage credentialStorage) throws RemoteException;

    boolean isCredentialStorageLockedAsUser(int i, CredentialStorage credentialStorage) throws RemoteException;

    boolean isCredentialStorageManaged(ContextInfo contextInfo, CredentialStorage credentialStorage) throws RemoteException;

    boolean isCredentialStorageManagedAsUser(int i, CredentialStorage credentialStorage) throws RemoteException;

    boolean isPackageFromExemptList(int i, CredentialStorage credentialStorage, int i2) throws RemoteException;

    int lockCredentialStorage(ContextInfo contextInfo, CredentialStorage credentialStorage, boolean z) throws RemoteException;

    int manageCredentialStorage(ContextInfo contextInfo, CredentialStorage credentialStorage, boolean z) throws RemoteException;

    boolean notifyLicenseStatus(int i, String str) throws RemoteException;

    int removeODESettingsForWPC() throws RemoteException;

    int removePackagesFromExemptList(ContextInfo contextInfo, CredentialStorage credentialStorage, int i, List<AppIdentity> list) throws RemoteException;

    int removePackagesFromWhiteList(ContextInfo contextInfo, CredentialStorage credentialStorage, List<AppIdentity> list, Bundle bundle) throws RemoteException;

    int setAuthType(ContextInfo contextInfo, CredentialStorage credentialStorage, int i) throws RemoteException;

    Bundle setCredentialStorageProperty(ContextInfo contextInfo, CredentialStorage credentialStorage, Bundle bundle) throws RemoteException;

    int setDefaultInstallStorage(ContextInfo contextInfo, CredentialStorage credentialStorage) throws RemoteException;

    boolean setKeyPairCertificate(ContextInfo contextInfo, CredentialStorage credentialStorage, String str, byte[] bArr) throws RemoteException;

    int setKeyguardPinMaximumLength(ContextInfo contextInfo, CredentialStorage credentialStorage, int i) throws RemoteException;

    int setKeyguardPinMaximumRetryCount(ContextInfo contextInfo, CredentialStorage credentialStorage, int i) throws RemoteException;

    int setKeyguardPinMinimumLength(ContextInfo contextInfo, CredentialStorage credentialStorage, int i) throws RemoteException;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Stub extends Binder implements IUniversalCredentialManager {
        public static final int TRANSACTION_addPackagesToExemptList = 38;
        public static final int TRANSACTION_addPackagesToWhiteList = 13;
        public static final int TRANSACTION_addPackagesToWhiteListInternal = 14;
        public static final int TRANSACTION_changeKeyguardPin = 62;
        public static final int TRANSACTION_clearWhiteList = 18;
        public static final int TRANSACTION_configureCredentialStorageForODESettings = 43;
        public static final int TRANSACTION_configureCredentialStoragePlugin = 2;
        public static final int TRANSACTION_configureWpcDar = 64;
        public static final int TRANSACTION_deleteCACertificate = 32;
        public static final int TRANSACTION_deleteCertificate = 25;
        public static final int TRANSACTION_deleteCertificateInternal = 26;
        public static final int TRANSACTION_enableCredentialStorageForLockType = 52;
        public static final int TRANSACTION_enforceCredentialStorageAsLockType = 49;
        public static final int TRANSACTION_generateKeyPair = 66;
        public static final int TRANSACTION_getAdminForEnforcedCredentialStorageAsUser = 48;
        public static final int TRANSACTION_getAliases = 27;
        public static final int TRANSACTION_getAllCertificateAliases = 30;
        public static final int TRANSACTION_getAuthType = 8;
        public static final int TRANSACTION_getAvailableCredentialStorages = 1;
        public static final int TRANSACTION_getCACertificate = 34;
        public static final int TRANSACTION_getCACertificateAliases = 33;
        public static final int TRANSACTION_getCertificateAliases = 28;
        public static final int TRANSACTION_getCertificateAliasesAsUser = 29;
        public static final int TRANSACTION_getCredentialStoragePluginConfiguration = 3;
        public static final int TRANSACTION_getCredentialStorageProperty = 37;
        public static final int TRANSACTION_getCredentialStorages = 19;
        public static final int TRANSACTION_getDefaultInstallStorage = 20;
        public static final int TRANSACTION_getDefaultInstallStorageAsUser = 21;
        public static final int TRANSACTION_getEnforcedCredentialStorageForLockType = 46;
        public static final int TRANSACTION_getEnforcedCredentialStorageForLockTypeAsUser = 47;
        public static final int TRANSACTION_getKeyguardPinCurrentRetryCount = 59;
        public static final int TRANSACTION_getKeyguardPinMaximumLength = 61;
        public static final int TRANSACTION_getKeyguardPinMaximumRetryCount = 58;
        public static final int TRANSACTION_getKeyguardPinMinimumLength = 60;
        public static final int TRANSACTION_getODESettingsConfiguration = 44;
        public static final int TRANSACTION_getPackagesFromExemptList = 39;
        public static final int TRANSACTION_getPackagesFromWhiteList = 16;
        public static final int TRANSACTION_getStorageAuthenticationType = 9;
        public static final int TRANSACTION_getSupportedAlgorithms = 35;
        public static final int TRANSACTION_getWifiCertificateAliasesAsUser = 45;
        public static final int TRANSACTION_initKeyguardPin = 54;
        public static final int TRANSACTION_initPluginForWpc = 63;
        public static final int TRANSACTION_installCACertificate = 31;
        public static final int TRANSACTION_installCertificate = 23;
        public static final int TRANSACTION_installCertificateInternal = 24;
        public static final int TRANSACTION_isAccessAllowed = 17;
        public static final int TRANSACTION_isCallerDelegated = 53;
        public static final int TRANSACTION_isCredentialStorageEnabledForLockType = 50;
        public static final int TRANSACTION_isCredentialStorageEnabledForLockTypeAsUser = 51;
        public static final int TRANSACTION_isCredentialStorageLocked = 11;
        public static final int TRANSACTION_isCredentialStorageLockedAsUser = 12;
        public static final int TRANSACTION_isCredentialStorageManaged = 5;
        public static final int TRANSACTION_isCredentialStorageManagedAsUser = 6;
        public static final int TRANSACTION_isPackageFromExemptList = 41;
        public static final int TRANSACTION_lockCredentialStorage = 10;
        public static final int TRANSACTION_manageCredentialStorage = 4;
        public static final int TRANSACTION_notifyLicenseStatus = 42;
        public static final int TRANSACTION_removeODESettingsForWPC = 65;
        public static final int TRANSACTION_removePackagesFromExemptList = 40;
        public static final int TRANSACTION_removePackagesFromWhiteList = 15;
        public static final int TRANSACTION_setAuthType = 7;
        public static final int TRANSACTION_setCredentialStorageProperty = 36;
        public static final int TRANSACTION_setDefaultInstallStorage = 22;
        public static final int TRANSACTION_setKeyPairCertificate = 67;
        public static final int TRANSACTION_setKeyguardPinMaximumLength = 57;
        public static final int TRANSACTION_setKeyguardPinMaximumRetryCount = 55;
        public static final int TRANSACTION_setKeyguardPinMinimumLength = 56;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        class Proxy implements IUniversalCredentialManager {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public int addPackagesToExemptList(ContextInfo contextInfo, CredentialStorage credentialStorage, int i, List<AppIdentity> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(credentialStorage, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public int addPackagesToWhiteList(ContextInfo contextInfo, CredentialStorage credentialStorage, List<AppIdentity> list, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(credentialStorage, 0);
                    obtain.writeTypedList(list, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public int addPackagesToWhiteListInternal(int i, int i2, CredentialStorage credentialStorage, List<AppIdentity> list, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(credentialStorage, 0);
                    obtain.writeTypedList(list, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public int changeKeyguardPin(ContextInfo contextInfo, CredentialStorage credentialStorage, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(credentialStorage, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(62, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public int clearWhiteList(ContextInfo contextInfo, CredentialStorage credentialStorage, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(credentialStorage, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public int configureCredentialStorageForODESettings(ContextInfo contextInfo, CredentialStorage credentialStorage, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(credentialStorage, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public int configureCredentialStoragePlugin(ContextInfo contextInfo, CredentialStorage credentialStorage, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(credentialStorage, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public int configureWpcDar(ContextInfo contextInfo, CredentialStorage credentialStorage) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(credentialStorage, 0);
                    this.mRemote.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public int deleteCACertificate(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public int deleteCertificate(ContextInfo contextInfo, CredentialStorage credentialStorage, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(credentialStorage, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public int deleteCertificateInternal(int i, int i2, CredentialStorage credentialStorage, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(credentialStorage, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public int enableCredentialStorageForLockType(ContextInfo contextInfo, CredentialStorage credentialStorage, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(credentialStorage, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public int enforceCredentialStorageAsLockType(ContextInfo contextInfo, CredentialStorage credentialStorage, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(credentialStorage, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public Bundle generateKeyPair(ContextInfo contextInfo, CredentialStorage credentialStorage, String str, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(credentialStorage, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(66, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public int getAdminForEnforcedCredentialStorageAsUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public String[] getAliases(ContextInfo contextInfo, CredentialStorage credentialStorage) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(credentialStorage, 0);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public String[] getAllCertificateAliases(CredentialStorage credentialStorage) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(credentialStorage, 0);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public int getAuthType(ContextInfo contextInfo, CredentialStorage credentialStorage) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(credentialStorage, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public CredentialStorage[] getAvailableCredentialStorages(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CredentialStorage[]) obtain2.createTypedArray(CredentialStorage.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public CACertificateInfo getCACertificate(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CACertificateInfo) obtain2.readTypedObject(CACertificateInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public String[] getCACertificateAliases(ContextInfo contextInfo, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public String[] getCertificateAliases(int i, CredentialStorage credentialStorage) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(credentialStorage, 0);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public String[] getCertificateAliasesAsUser(int i, CredentialStorage credentialStorage) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(credentialStorage, 0);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public Bundle getCredentialStoragePluginConfiguration(ContextInfo contextInfo, CredentialStorage credentialStorage) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(credentialStorage, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public Bundle getCredentialStorageProperty(ContextInfo contextInfo, CredentialStorage credentialStorage, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(credentialStorage, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public CredentialStorage[] getCredentialStorages(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CredentialStorage[]) obtain2.createTypedArray(CredentialStorage.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public CredentialStorage getDefaultInstallStorage(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CredentialStorage) obtain2.readTypedObject(CredentialStorage.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public CredentialStorage getDefaultInstallStorageAsUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CredentialStorage) obtain2.readTypedObject(CredentialStorage.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public CredentialStorage getEnforcedCredentialStorageForLockType(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CredentialStorage) obtain2.readTypedObject(CredentialStorage.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public CredentialStorage getEnforcedCredentialStorageForLockTypeAsUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CredentialStorage) obtain2.readTypedObject(CredentialStorage.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IUniversalCredentialManager.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public int getKeyguardPinCurrentRetryCount(ContextInfo contextInfo, CredentialStorage credentialStorage) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(credentialStorage, 0);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public int getKeyguardPinMaximumLength(ContextInfo contextInfo, CredentialStorage credentialStorage) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(credentialStorage, 0);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public int getKeyguardPinMaximumRetryCount(ContextInfo contextInfo, CredentialStorage credentialStorage) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(credentialStorage, 0);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public int getKeyguardPinMinimumLength(ContextInfo contextInfo, CredentialStorage credentialStorage) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(credentialStorage, 0);
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public Bundle getODESettingsConfiguration(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public List<AppIdentity> getPackagesFromExemptList(ContextInfo contextInfo, CredentialStorage credentialStorage, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(credentialStorage, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(AppIdentity.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public List<AppIdentity> getPackagesFromWhiteList(ContextInfo contextInfo, CredentialStorage credentialStorage, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(credentialStorage, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(AppIdentity.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public int getStorageAuthenticationType(int i, CredentialStorage credentialStorage) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(credentialStorage, 0);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public String[] getSupportedAlgorithms(ContextInfo contextInfo, CredentialStorage credentialStorage) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(credentialStorage, 0);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public String[] getWifiCertificateAliasesAsUser(int i, CredentialStorage credentialStorage) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(credentialStorage, 0);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public int initKeyguardPin(ContextInfo contextInfo, CredentialStorage credentialStorage, String str, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(credentialStorage, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public Bundle initPluginForWpc(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public int installCACertificate(ContextInfo contextInfo, byte[] bArr, String str, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeByteArray(bArr);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public int installCertificate(ContextInfo contextInfo, CredentialStorage credentialStorage, byte[] bArr, String str, String str2, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(credentialStorage, 0);
                    obtain.writeByteArray(bArr);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public int installCertificateInternal(int i, int i2, CredentialStorage credentialStorage, byte[] bArr, String str, Bundle bundle, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(credentialStorage, 0);
                    obtain.writeByteArray(bArr);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public boolean isAccessAllowed(int i, CredentialStorage credentialStorage, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(credentialStorage, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public boolean isCallerDelegated(ContextInfo contextInfo, CredentialStorage credentialStorage, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(credentialStorage, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public boolean isCredentialStorageEnabledForLockType(ContextInfo contextInfo, CredentialStorage credentialStorage) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(credentialStorage, 0);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public boolean isCredentialStorageEnabledForLockTypeAsUser(int i, CredentialStorage credentialStorage) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(credentialStorage, 0);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public boolean isCredentialStorageLocked(ContextInfo contextInfo, CredentialStorage credentialStorage) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(credentialStorage, 0);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public boolean isCredentialStorageLockedAsUser(int i, CredentialStorage credentialStorage) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(credentialStorage, 0);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public boolean isCredentialStorageManaged(ContextInfo contextInfo, CredentialStorage credentialStorage) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(credentialStorage, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public boolean isCredentialStorageManagedAsUser(int i, CredentialStorage credentialStorage) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(credentialStorage, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public boolean isPackageFromExemptList(int i, CredentialStorage credentialStorage, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(credentialStorage, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public int lockCredentialStorage(ContextInfo contextInfo, CredentialStorage credentialStorage, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(credentialStorage, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public int manageCredentialStorage(ContextInfo contextInfo, CredentialStorage credentialStorage, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(credentialStorage, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public boolean notifyLicenseStatus(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public int removeODESettingsForWPC() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    this.mRemote.transact(65, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public int removePackagesFromExemptList(ContextInfo contextInfo, CredentialStorage credentialStorage, int i, List<AppIdentity> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(credentialStorage, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public int removePackagesFromWhiteList(ContextInfo contextInfo, CredentialStorage credentialStorage, List<AppIdentity> list, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(credentialStorage, 0);
                    obtain.writeTypedList(list, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public int setAuthType(ContextInfo contextInfo, CredentialStorage credentialStorage, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(credentialStorage, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public Bundle setCredentialStorageProperty(ContextInfo contextInfo, CredentialStorage credentialStorage, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(credentialStorage, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public int setDefaultInstallStorage(ContextInfo contextInfo, CredentialStorage credentialStorage) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(credentialStorage, 0);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public boolean setKeyPairCertificate(ContextInfo contextInfo, CredentialStorage credentialStorage, String str, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(credentialStorage, 0);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(67, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public int setKeyguardPinMaximumLength(ContextInfo contextInfo, CredentialStorage credentialStorage, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(credentialStorage, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public int setKeyguardPinMaximumRetryCount(ContextInfo contextInfo, CredentialStorage credentialStorage, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(credentialStorage, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager
            public int setKeyguardPinMinimumLength(ContextInfo contextInfo, CredentialStorage credentialStorage, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUniversalCredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(credentialStorage, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IUniversalCredentialManager.DESCRIPTOR);
        }

        public static IUniversalCredentialManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IUniversalCredentialManager.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IUniversalCredentialManager)) ? new Proxy(iBinder) : (IUniversalCredentialManager) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IUniversalCredentialManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IUniversalCredentialManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ContextInfo contextInfo = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    CredentialStorage[] availableCredentialStorages = getAvailableCredentialStorages(contextInfo);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(availableCredentialStorages, 1);
                    return true;
                case 2:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    CredentialStorage credentialStorage = (CredentialStorage) parcel.readTypedObject(CredentialStorage.CREATOR);
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int configureCredentialStoragePlugin = configureCredentialStoragePlugin(contextInfo2, credentialStorage, bundle);
                    parcel2.writeNoException();
                    parcel2.writeInt(configureCredentialStoragePlugin);
                    return true;
                case 3:
                    ContextInfo contextInfo3 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    CredentialStorage credentialStorage2 = (CredentialStorage) parcel.readTypedObject(CredentialStorage.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle credentialStoragePluginConfiguration = getCredentialStoragePluginConfiguration(contextInfo3, credentialStorage2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(credentialStoragePluginConfiguration, 1);
                    return true;
                case 4:
                    ContextInfo contextInfo4 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    CredentialStorage credentialStorage3 = (CredentialStorage) parcel.readTypedObject(CredentialStorage.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int manageCredentialStorage = manageCredentialStorage(contextInfo4, credentialStorage3, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeInt(manageCredentialStorage);
                    return true;
                case 5:
                    ContextInfo contextInfo5 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    CredentialStorage credentialStorage4 = (CredentialStorage) parcel.readTypedObject(CredentialStorage.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isCredentialStorageManaged = isCredentialStorageManaged(contextInfo5, credentialStorage4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCredentialStorageManaged);
                    return true;
                case 6:
                    int readInt = parcel.readInt();
                    CredentialStorage credentialStorage5 = (CredentialStorage) parcel.readTypedObject(CredentialStorage.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isCredentialStorageManagedAsUser = isCredentialStorageManagedAsUser(readInt, credentialStorage5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCredentialStorageManagedAsUser);
                    return true;
                case 7:
                    ContextInfo contextInfo6 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    CredentialStorage credentialStorage6 = (CredentialStorage) parcel.readTypedObject(CredentialStorage.CREATOR);
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int authType = setAuthType(contextInfo6, credentialStorage6, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeInt(authType);
                    return true;
                case 8:
                    ContextInfo contextInfo7 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    CredentialStorage credentialStorage7 = (CredentialStorage) parcel.readTypedObject(CredentialStorage.CREATOR);
                    parcel.enforceNoDataAvail();
                    int authType2 = getAuthType(contextInfo7, credentialStorage7);
                    parcel2.writeNoException();
                    parcel2.writeInt(authType2);
                    return true;
                case 9:
                    int readInt3 = parcel.readInt();
                    CredentialStorage credentialStorage8 = (CredentialStorage) parcel.readTypedObject(CredentialStorage.CREATOR);
                    parcel.enforceNoDataAvail();
                    int storageAuthenticationType = getStorageAuthenticationType(readInt3, credentialStorage8);
                    parcel2.writeNoException();
                    parcel2.writeInt(storageAuthenticationType);
                    return true;
                case 10:
                    ContextInfo contextInfo8 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    CredentialStorage credentialStorage9 = (CredentialStorage) parcel.readTypedObject(CredentialStorage.CREATOR);
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int lockCredentialStorage = lockCredentialStorage(contextInfo8, credentialStorage9, readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeInt(lockCredentialStorage);
                    return true;
                case 11:
                    ContextInfo contextInfo9 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    CredentialStorage credentialStorage10 = (CredentialStorage) parcel.readTypedObject(CredentialStorage.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isCredentialStorageLocked = isCredentialStorageLocked(contextInfo9, credentialStorage10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCredentialStorageLocked);
                    return true;
                case 12:
                    int readInt4 = parcel.readInt();
                    CredentialStorage credentialStorage11 = (CredentialStorage) parcel.readTypedObject(CredentialStorage.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isCredentialStorageLockedAsUser = isCredentialStorageLockedAsUser(readInt4, credentialStorage11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCredentialStorageLockedAsUser);
                    return true;
                case 13:
                    ContextInfo contextInfo10 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    CredentialStorage credentialStorage12 = (CredentialStorage) parcel.readTypedObject(CredentialStorage.CREATOR);
                    ArrayList createTypedArrayList = parcel.createTypedArrayList(AppIdentity.CREATOR);
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int addPackagesToWhiteList = addPackagesToWhiteList(contextInfo10, credentialStorage12, createTypedArrayList, bundle2);
                    parcel2.writeNoException();
                    parcel2.writeInt(addPackagesToWhiteList);
                    return true;
                case 14:
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    CredentialStorage credentialStorage13 = (CredentialStorage) parcel.readTypedObject(CredentialStorage.CREATOR);
                    ArrayList createTypedArrayList2 = parcel.createTypedArrayList(AppIdentity.CREATOR);
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int addPackagesToWhiteListInternal = addPackagesToWhiteListInternal(readInt5, readInt6, credentialStorage13, createTypedArrayList2, bundle3);
                    parcel2.writeNoException();
                    parcel2.writeInt(addPackagesToWhiteListInternal);
                    return true;
                case 15:
                    ContextInfo contextInfo11 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    CredentialStorage credentialStorage14 = (CredentialStorage) parcel.readTypedObject(CredentialStorage.CREATOR);
                    ArrayList createTypedArrayList3 = parcel.createTypedArrayList(AppIdentity.CREATOR);
                    Bundle bundle4 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int removePackagesFromWhiteList = removePackagesFromWhiteList(contextInfo11, credentialStorage14, createTypedArrayList3, bundle4);
                    parcel2.writeNoException();
                    parcel2.writeInt(removePackagesFromWhiteList);
                    return true;
                case 16:
                    ContextInfo contextInfo12 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    CredentialStorage credentialStorage15 = (CredentialStorage) parcel.readTypedObject(CredentialStorage.CREATOR);
                    Bundle bundle5 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<AppIdentity> packagesFromWhiteList = getPackagesFromWhiteList(contextInfo12, credentialStorage15, bundle5);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(packagesFromWhiteList, 1);
                    return true;
                case 17:
                    int readInt7 = parcel.readInt();
                    CredentialStorage credentialStorage16 = (CredentialStorage) parcel.readTypedObject(CredentialStorage.CREATOR);
                    Bundle bundle6 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isAccessAllowed = isAccessAllowed(readInt7, credentialStorage16, bundle6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAccessAllowed);
                    return true;
                case 18:
                    ContextInfo contextInfo13 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    CredentialStorage credentialStorage17 = (CredentialStorage) parcel.readTypedObject(CredentialStorage.CREATOR);
                    Bundle bundle7 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int clearWhiteList = clearWhiteList(contextInfo13, credentialStorage17, bundle7);
                    parcel2.writeNoException();
                    parcel2.writeInt(clearWhiteList);
                    return true;
                case 19:
                    ContextInfo contextInfo14 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    CredentialStorage[] credentialStorages = getCredentialStorages(contextInfo14, readString);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(credentialStorages, 1);
                    return true;
                case 20:
                    ContextInfo contextInfo15 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    CredentialStorage defaultInstallStorage = getDefaultInstallStorage(contextInfo15);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(defaultInstallStorage, 1);
                    return true;
                case 21:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    CredentialStorage defaultInstallStorageAsUser = getDefaultInstallStorageAsUser(readInt8);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(defaultInstallStorageAsUser, 1);
                    return true;
                case 22:
                    ContextInfo contextInfo16 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    CredentialStorage credentialStorage18 = (CredentialStorage) parcel.readTypedObject(CredentialStorage.CREATOR);
                    parcel.enforceNoDataAvail();
                    int defaultInstallStorage2 = setDefaultInstallStorage(contextInfo16, credentialStorage18);
                    parcel2.writeNoException();
                    parcel2.writeInt(defaultInstallStorage2);
                    return true;
                case 23:
                    ContextInfo contextInfo17 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    CredentialStorage credentialStorage19 = (CredentialStorage) parcel.readTypedObject(CredentialStorage.CREATOR);
                    byte[] createByteArray = parcel.createByteArray();
                    String readString2 = parcel.readString();
                    String readString3 = parcel.readString();
                    Bundle bundle8 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int installCertificate = installCertificate(contextInfo17, credentialStorage19, createByteArray, readString2, readString3, bundle8);
                    parcel2.writeNoException();
                    parcel2.writeInt(installCertificate);
                    return true;
                case 24:
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    CredentialStorage credentialStorage20 = (CredentialStorage) parcel.readTypedObject(CredentialStorage.CREATOR);
                    byte[] createByteArray2 = parcel.createByteArray();
                    String readString4 = parcel.readString();
                    Bundle bundle9 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int installCertificateInternal = installCertificateInternal(readInt9, readInt10, credentialStorage20, createByteArray2, readString4, bundle9, readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeInt(installCertificateInternal);
                    return true;
                case 25:
                    ContextInfo contextInfo18 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    CredentialStorage credentialStorage21 = (CredentialStorage) parcel.readTypedObject(CredentialStorage.CREATOR);
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int deleteCertificate = deleteCertificate(contextInfo18, credentialStorage21, readString5);
                    parcel2.writeNoException();
                    parcel2.writeInt(deleteCertificate);
                    return true;
                case 26:
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    CredentialStorage credentialStorage22 = (CredentialStorage) parcel.readTypedObject(CredentialStorage.CREATOR);
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int deleteCertificateInternal = deleteCertificateInternal(readInt11, readInt12, credentialStorage22, readString6);
                    parcel2.writeNoException();
                    parcel2.writeInt(deleteCertificateInternal);
                    return true;
                case 27:
                    ContextInfo contextInfo19 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    CredentialStorage credentialStorage23 = (CredentialStorage) parcel.readTypedObject(CredentialStorage.CREATOR);
                    parcel.enforceNoDataAvail();
                    String[] aliases = getAliases(contextInfo19, credentialStorage23);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(aliases);
                    return true;
                case 28:
                    int readInt13 = parcel.readInt();
                    CredentialStorage credentialStorage24 = (CredentialStorage) parcel.readTypedObject(CredentialStorage.CREATOR);
                    parcel.enforceNoDataAvail();
                    String[] certificateAliases = getCertificateAliases(readInt13, credentialStorage24);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(certificateAliases);
                    return true;
                case 29:
                    int readInt14 = parcel.readInt();
                    CredentialStorage credentialStorage25 = (CredentialStorage) parcel.readTypedObject(CredentialStorage.CREATOR);
                    parcel.enforceNoDataAvail();
                    String[] certificateAliasesAsUser = getCertificateAliasesAsUser(readInt14, credentialStorage25);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(certificateAliasesAsUser);
                    return true;
                case 30:
                    CredentialStorage credentialStorage26 = (CredentialStorage) parcel.readTypedObject(CredentialStorage.CREATOR);
                    parcel.enforceNoDataAvail();
                    String[] allCertificateAliases = getAllCertificateAliases(credentialStorage26);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(allCertificateAliases);
                    return true;
                case 31:
                    ContextInfo contextInfo20 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    byte[] createByteArray3 = parcel.createByteArray();
                    String readString7 = parcel.readString();
                    Bundle bundle10 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int installCACertificate = installCACertificate(contextInfo20, createByteArray3, readString7, bundle10);
                    parcel2.writeNoException();
                    parcel2.writeInt(installCACertificate);
                    return true;
                case 32:
                    ContextInfo contextInfo21 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int deleteCACertificate = deleteCACertificate(contextInfo21, readString8);
                    parcel2.writeNoException();
                    parcel2.writeInt(deleteCACertificate);
                    return true;
                case 33:
                    ContextInfo contextInfo22 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    Bundle bundle11 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    String[] cACertificateAliases = getCACertificateAliases(contextInfo22, bundle11);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(cACertificateAliases);
                    return true;
                case 34:
                    ContextInfo contextInfo23 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    CACertificateInfo cACertificate = getCACertificate(contextInfo23, readString9);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(cACertificate, 1);
                    return true;
                case 35:
                    ContextInfo contextInfo24 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    CredentialStorage credentialStorage27 = (CredentialStorage) parcel.readTypedObject(CredentialStorage.CREATOR);
                    parcel.enforceNoDataAvail();
                    String[] supportedAlgorithms = getSupportedAlgorithms(contextInfo24, credentialStorage27);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(supportedAlgorithms);
                    return true;
                case 36:
                    ContextInfo contextInfo25 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    CredentialStorage credentialStorage28 = (CredentialStorage) parcel.readTypedObject(CredentialStorage.CREATOR);
                    Bundle bundle12 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle credentialStorageProperty = setCredentialStorageProperty(contextInfo25, credentialStorage28, bundle12);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(credentialStorageProperty, 1);
                    return true;
                case 37:
                    ContextInfo contextInfo26 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    CredentialStorage credentialStorage29 = (CredentialStorage) parcel.readTypedObject(CredentialStorage.CREATOR);
                    Bundle bundle13 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle credentialStorageProperty2 = getCredentialStorageProperty(contextInfo26, credentialStorage29, bundle13);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(credentialStorageProperty2, 1);
                    return true;
                case 38:
                    ContextInfo contextInfo27 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    CredentialStorage credentialStorage30 = (CredentialStorage) parcel.readTypedObject(CredentialStorage.CREATOR);
                    int readInt15 = parcel.readInt();
                    ArrayList createTypedArrayList4 = parcel.createTypedArrayList(AppIdentity.CREATOR);
                    parcel.enforceNoDataAvail();
                    int addPackagesToExemptList = addPackagesToExemptList(contextInfo27, credentialStorage30, readInt15, createTypedArrayList4);
                    parcel2.writeNoException();
                    parcel2.writeInt(addPackagesToExemptList);
                    return true;
                case 39:
                    ContextInfo contextInfo28 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    CredentialStorage credentialStorage31 = (CredentialStorage) parcel.readTypedObject(CredentialStorage.CREATOR);
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<AppIdentity> packagesFromExemptList = getPackagesFromExemptList(contextInfo28, credentialStorage31, readInt16);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(packagesFromExemptList, 1);
                    return true;
                case 40:
                    ContextInfo contextInfo29 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    CredentialStorage credentialStorage32 = (CredentialStorage) parcel.readTypedObject(CredentialStorage.CREATOR);
                    int readInt17 = parcel.readInt();
                    ArrayList createTypedArrayList5 = parcel.createTypedArrayList(AppIdentity.CREATOR);
                    parcel.enforceNoDataAvail();
                    int removePackagesFromExemptList = removePackagesFromExemptList(contextInfo29, credentialStorage32, readInt17, createTypedArrayList5);
                    parcel2.writeNoException();
                    parcel2.writeInt(removePackagesFromExemptList);
                    return true;
                case 41:
                    int readInt18 = parcel.readInt();
                    CredentialStorage credentialStorage33 = (CredentialStorage) parcel.readTypedObject(CredentialStorage.CREATOR);
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isPackageFromExemptList = isPackageFromExemptList(readInt18, credentialStorage33, readInt19);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPackageFromExemptList);
                    return true;
                case 42:
                    int readInt20 = parcel.readInt();
                    String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean notifyLicenseStatus = notifyLicenseStatus(readInt20, readString10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(notifyLicenseStatus);
                    return true;
                case 43:
                    ContextInfo contextInfo30 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    CredentialStorage credentialStorage34 = (CredentialStorage) parcel.readTypedObject(CredentialStorage.CREATOR);
                    Bundle bundle14 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int configureCredentialStorageForODESettings = configureCredentialStorageForODESettings(contextInfo30, credentialStorage34, bundle14);
                    parcel2.writeNoException();
                    parcel2.writeInt(configureCredentialStorageForODESettings);
                    return true;
                case 44:
                    ContextInfo contextInfo31 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle oDESettingsConfiguration = getODESettingsConfiguration(contextInfo31);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(oDESettingsConfiguration, 1);
                    return true;
                case 45:
                    int readInt21 = parcel.readInt();
                    CredentialStorage credentialStorage35 = (CredentialStorage) parcel.readTypedObject(CredentialStorage.CREATOR);
                    parcel.enforceNoDataAvail();
                    String[] wifiCertificateAliasesAsUser = getWifiCertificateAliasesAsUser(readInt21, credentialStorage35);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(wifiCertificateAliasesAsUser);
                    return true;
                case 46:
                    ContextInfo contextInfo32 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    CredentialStorage enforcedCredentialStorageForLockType = getEnforcedCredentialStorageForLockType(contextInfo32);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(enforcedCredentialStorageForLockType, 1);
                    return true;
                case 47:
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    CredentialStorage enforcedCredentialStorageForLockTypeAsUser = getEnforcedCredentialStorageForLockTypeAsUser(readInt22);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(enforcedCredentialStorageForLockTypeAsUser, 1);
                    return true;
                case 48:
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int adminForEnforcedCredentialStorageAsUser = getAdminForEnforcedCredentialStorageAsUser(readInt23);
                    parcel2.writeNoException();
                    parcel2.writeInt(adminForEnforcedCredentialStorageAsUser);
                    return true;
                case 49:
                    ContextInfo contextInfo33 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    CredentialStorage credentialStorage36 = (CredentialStorage) parcel.readTypedObject(CredentialStorage.CREATOR);
                    Bundle bundle15 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int enforceCredentialStorageAsLockType = enforceCredentialStorageAsLockType(contextInfo33, credentialStorage36, bundle15);
                    parcel2.writeNoException();
                    parcel2.writeInt(enforceCredentialStorageAsLockType);
                    return true;
                case 50:
                    ContextInfo contextInfo34 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    CredentialStorage credentialStorage37 = (CredentialStorage) parcel.readTypedObject(CredentialStorage.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isCredentialStorageEnabledForLockType = isCredentialStorageEnabledForLockType(contextInfo34, credentialStorage37);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCredentialStorageEnabledForLockType);
                    return true;
                case 51:
                    int readInt24 = parcel.readInt();
                    CredentialStorage credentialStorage38 = (CredentialStorage) parcel.readTypedObject(CredentialStorage.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isCredentialStorageEnabledForLockTypeAsUser = isCredentialStorageEnabledForLockTypeAsUser(readInt24, credentialStorage38);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCredentialStorageEnabledForLockTypeAsUser);
                    return true;
                case 52:
                    ContextInfo contextInfo35 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    CredentialStorage credentialStorage39 = (CredentialStorage) parcel.readTypedObject(CredentialStorage.CREATOR);
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int enableCredentialStorageForLockType = enableCredentialStorageForLockType(contextInfo35, credentialStorage39, readBoolean4);
                    parcel2.writeNoException();
                    parcel2.writeInt(enableCredentialStorageForLockType);
                    return true;
                case 53:
                    ContextInfo contextInfo36 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    CredentialStorage credentialStorage40 = (CredentialStorage) parcel.readTypedObject(CredentialStorage.CREATOR);
                    int readInt25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isCallerDelegated = isCallerDelegated(contextInfo36, credentialStorage40, readInt25);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCallerDelegated);
                    return true;
                case 54:
                    ContextInfo contextInfo37 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    CredentialStorage credentialStorage41 = (CredentialStorage) parcel.readTypedObject(CredentialStorage.CREATOR);
                    String readString11 = parcel.readString();
                    Bundle bundle16 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int initKeyguardPin = initKeyguardPin(contextInfo37, credentialStorage41, readString11, bundle16);
                    parcel2.writeNoException();
                    parcel2.writeInt(initKeyguardPin);
                    return true;
                case 55:
                    ContextInfo contextInfo38 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    CredentialStorage credentialStorage42 = (CredentialStorage) parcel.readTypedObject(CredentialStorage.CREATOR);
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int keyguardPinMaximumRetryCount = setKeyguardPinMaximumRetryCount(contextInfo38, credentialStorage42, readInt26);
                    parcel2.writeNoException();
                    parcel2.writeInt(keyguardPinMaximumRetryCount);
                    return true;
                case 56:
                    ContextInfo contextInfo39 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    CredentialStorage credentialStorage43 = (CredentialStorage) parcel.readTypedObject(CredentialStorage.CREATOR);
                    int readInt27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int keyguardPinMinimumLength = setKeyguardPinMinimumLength(contextInfo39, credentialStorage43, readInt27);
                    parcel2.writeNoException();
                    parcel2.writeInt(keyguardPinMinimumLength);
                    return true;
                case 57:
                    ContextInfo contextInfo40 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    CredentialStorage credentialStorage44 = (CredentialStorage) parcel.readTypedObject(CredentialStorage.CREATOR);
                    int readInt28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int keyguardPinMaximumLength = setKeyguardPinMaximumLength(contextInfo40, credentialStorage44, readInt28);
                    parcel2.writeNoException();
                    parcel2.writeInt(keyguardPinMaximumLength);
                    return true;
                case 58:
                    ContextInfo contextInfo41 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    CredentialStorage credentialStorage45 = (CredentialStorage) parcel.readTypedObject(CredentialStorage.CREATOR);
                    parcel.enforceNoDataAvail();
                    int keyguardPinMaximumRetryCount2 = getKeyguardPinMaximumRetryCount(contextInfo41, credentialStorage45);
                    parcel2.writeNoException();
                    parcel2.writeInt(keyguardPinMaximumRetryCount2);
                    return true;
                case 59:
                    ContextInfo contextInfo42 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    CredentialStorage credentialStorage46 = (CredentialStorage) parcel.readTypedObject(CredentialStorage.CREATOR);
                    parcel.enforceNoDataAvail();
                    int keyguardPinCurrentRetryCount = getKeyguardPinCurrentRetryCount(contextInfo42, credentialStorage46);
                    parcel2.writeNoException();
                    parcel2.writeInt(keyguardPinCurrentRetryCount);
                    return true;
                case 60:
                    ContextInfo contextInfo43 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    CredentialStorage credentialStorage47 = (CredentialStorage) parcel.readTypedObject(CredentialStorage.CREATOR);
                    parcel.enforceNoDataAvail();
                    int keyguardPinMinimumLength2 = getKeyguardPinMinimumLength(contextInfo43, credentialStorage47);
                    parcel2.writeNoException();
                    parcel2.writeInt(keyguardPinMinimumLength2);
                    return true;
                case 61:
                    ContextInfo contextInfo44 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    CredentialStorage credentialStorage48 = (CredentialStorage) parcel.readTypedObject(CredentialStorage.CREATOR);
                    parcel.enforceNoDataAvail();
                    int keyguardPinMaximumLength2 = getKeyguardPinMaximumLength(contextInfo44, credentialStorage48);
                    parcel2.writeNoException();
                    parcel2.writeInt(keyguardPinMaximumLength2);
                    return true;
                case 62:
                    ContextInfo contextInfo45 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    CredentialStorage credentialStorage49 = (CredentialStorage) parcel.readTypedObject(CredentialStorage.CREATOR);
                    String readString12 = parcel.readString();
                    String readString13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int changeKeyguardPin = changeKeyguardPin(contextInfo45, credentialStorage49, readString12, readString13);
                    parcel2.writeNoException();
                    parcel2.writeInt(changeKeyguardPin);
                    return true;
                case 63:
                    ContextInfo contextInfo46 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Bundle initPluginForWpc = initPluginForWpc(contextInfo46, readString14);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(initPluginForWpc, 1);
                    return true;
                case 64:
                    ContextInfo contextInfo47 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    CredentialStorage credentialStorage50 = (CredentialStorage) parcel.readTypedObject(CredentialStorage.CREATOR);
                    parcel.enforceNoDataAvail();
                    int configureWpcDar = configureWpcDar(contextInfo47, credentialStorage50);
                    parcel2.writeNoException();
                    parcel2.writeInt(configureWpcDar);
                    return true;
                case 65:
                    int removeODESettingsForWPC = removeODESettingsForWPC();
                    parcel2.writeNoException();
                    parcel2.writeInt(removeODESettingsForWPC);
                    return true;
                case 66:
                    ContextInfo contextInfo48 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    CredentialStorage credentialStorage51 = (CredentialStorage) parcel.readTypedObject(CredentialStorage.CREATOR);
                    String readString15 = parcel.readString();
                    Bundle bundle17 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle generateKeyPair = generateKeyPair(contextInfo48, credentialStorage51, readString15, bundle17);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(generateKeyPair, 1);
                    return true;
                case 67:
                    ContextInfo contextInfo49 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    CredentialStorage credentialStorage52 = (CredentialStorage) parcel.readTypedObject(CredentialStorage.CREATOR);
                    String readString16 = parcel.readString();
                    byte[] createByteArray4 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    boolean keyPairCertificate = setKeyPairCertificate(contextInfo49, credentialStorage52, readString16, createByteArray4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(keyPairCertificate);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }
}
