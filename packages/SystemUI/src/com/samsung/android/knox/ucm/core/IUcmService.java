package com.samsung.android.knox.ucm.core;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.knox.ucm.core.ICredentialManagerServiceSystemUICallback;

/* loaded from: classes4.dex */
public interface IUcmService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.ucm.core.IUcmService";

    Bundle APDUCommand(String str, byte[] bArr, Bundle bundle) throws RemoteException;

    Bundle changePin(String str, String str2, String str3) throws RemoteException;

    boolean configureKeyguardSettings(int i, String str) throws RemoteException;

    int configureODESettings(String str, Bundle bundle, String str2) throws RemoteException;

    int configureWPCDARFlag(String str, String str2) throws RemoteException;

    Bundle containsAlias(String str, int i) throws RemoteException;

    ucmRetParcelable decrypt(String str, byte[] bArr, String str2, Bundle bundle) throws RemoteException;

    Bundle delete(String str) throws RemoteException;

    Bundle deleteCertificate(String str, int i) throws RemoteException;

    ucmRetParcelable encrypt(String str, byte[] bArr, String str2, Bundle bundle) throws RemoteException;

    ucmRetParcelable generateDek(String str) throws RemoteException;

    Bundle generateKey(String str, String str2, int i, Bundle bundle) throws RemoteException;

    Bundle generateKeyPair(String str, String str2, int i, Bundle bundle) throws RemoteException;

    Bundle generateKeyPairInternal(String str, String str2, int i, Bundle bundle) throws RemoteException;

    Bundle generateKeyguardPassword(int i, String str, Bundle bundle) throws RemoteException;

    Bundle generateSecureRandom(String str, int i, byte[] bArr) throws RemoteException;

    ucmRetParcelable generateWrappedDek(String str) throws RemoteException;

    Bundle getAdminConfigureBundleFromCs(int i, int i2, String str) throws RemoteException;

    Bundle getAgentInfo(String str) throws RemoteException;

    ucmRetParcelable getCertificateChain(String str) throws RemoteException;

    Bundle getCredentialStorageProperty(int i, String str, Bundle bundle, int i2) throws RemoteException;

    ucmRetParcelable getDek(String str) throws RemoteException;

    ucmRetParcelable getDekForVold(String str, byte[] bArr) throws RemoteException;

    ucmRetParcelable getDekForVoldInternalKey(String str, byte[] bArr) throws RemoteException;

    String getDetailErrorMessage(String str, int i) throws RemoteException;

    Bundle getInfo(String str) throws RemoteException;

    Bundle getKeyType(String str) throws RemoteException;

    Bundle getKeyguardPinCurrentRetryCount(String str) throws RemoteException;

    Bundle getKeyguardPinMaximumLength(String str) throws RemoteException;

    Bundle getKeyguardPinMaximumRetryCount(String str) throws RemoteException;

    Bundle getKeyguardPinMinimumLength(String str) throws RemoteException;

    String getKeyguardStorageForCurrentUser(int i) throws RemoteException;

    ucmRetParcelable getODEConfigurationForVold(String str) throws RemoteException;

    Bundle getODESettingsConfiguration() throws RemoteException;

    ucmRetParcelable getOdeKey(String str, byte[] bArr) throws RemoteException;

    Bundle getStatus(String str) throws RemoteException;

    boolean grantKeyChainAccess(String str, int i) throws RemoteException;

    Bundle importKey(String str, Bundle bundle) throws RemoteException;

    Bundle importKeyPair(String str, byte[] bArr, byte[] bArr2, Bundle bundle) throws RemoteException;

    Bundle initKeyguardPin(String str, String str2, Bundle bundle) throws RemoteException;

    Bundle installCertificate(String str, byte[] bArr, byte[] bArr2, Bundle bundle) throws RemoteException;

    Bundle installCertificateIfSupported(String str, byte[] bArr, String str2, Bundle bundle) throws RemoteException;

    boolean isKeyChainGranted(String str, int i) throws RemoteException;

    boolean isUserCertificatesExistInUCS() throws RemoteException;

    ucmRetParcelable keyAgreement(String str, String str2, byte[] bArr) throws RemoteException;

    Bundle[] listAllProviders() throws RemoteException;

    Bundle[] listProviders() throws RemoteException;

    ucmRetParcelable mac(String str, byte[] bArr, String str2) throws RemoteException;

    Bundle notifyChangeToPlugin(String str, int i, Bundle bundle) throws RemoteException;

    boolean notifyLicenseStatus(String str, String str2, int i) throws RemoteException;

    void notifyPluginResult(Bundle bundle) throws RemoteException;

    ucmRetParcelable notifyVoldComplete(String str, byte[] bArr) throws RemoteException;

    void registerSystemUICallback(ICredentialManagerServiceSystemUICallback iCredentialManagerServiceSystemUICallback) throws RemoteException;

    void removeEnforcedLockTypeNotification(int i) throws RemoteException;

    int removeWpcOdeSettings() throws RemoteException;

    void resetNonMdmCertificates() throws RemoteException;

    Bundle resetUid(String str, int i) throws RemoteException;

    Bundle resetUser(String str, int i) throws RemoteException;

    Bundle saw(String str, int i) throws RemoteException;

    Bundle sawInternal(String str, int i, int i2) throws RemoteException;

    Bundle setAdminConfigureBundleForCs(int i, int i2, String str, Bundle bundle, int i3) throws RemoteException;

    Bundle setCertificateChain(String str, byte[] bArr, Bundle bundle) throws RemoteException;

    Bundle setCredentialStorageProperty(int i, String str, Bundle bundle, int i2) throws RemoteException;

    Bundle setKeyguardPinMaximumLength(String str, int i) throws RemoteException;

    Bundle setKeyguardPinMaximumRetryCount(String str, int i) throws RemoteException;

    Bundle setKeyguardPinMinimumLength(String str, int i) throws RemoteException;

    Bundle setState(String str, int i) throws RemoteException;

    void showEnforcedLockTypeNotification(int i, String str) throws RemoteException;

    ucmRetParcelable sign(String str, byte[] bArr, String str2) throws RemoteException;

    ucmRetParcelable unwrapDek(String str, byte[] bArr) throws RemoteException;

    void updateAgentList() throws RemoteException;

    Bundle verifyPin(int i, String str, String str2, Bundle bundle) throws RemoteException;

    Bundle verifyPuk(String str, String str2, String str3) throws RemoteException;

    public class Default implements IUcmService {
        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public Bundle APDUCommand(String str, byte[] bArr, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public Bundle changePin(String str, String str2, String str3) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public boolean configureKeyguardSettings(int i, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public int configureODESettings(String str, Bundle bundle, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public int configureWPCDARFlag(String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public Bundle containsAlias(String str, int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public ucmRetParcelable decrypt(String str, byte[] bArr, String str2, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public Bundle delete(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public Bundle deleteCertificate(String str, int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public ucmRetParcelable encrypt(String str, byte[] bArr, String str2, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public ucmRetParcelable generateDek(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public Bundle generateKey(String str, String str2, int i, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public Bundle generateKeyPair(String str, String str2, int i, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public Bundle generateKeyPairInternal(String str, String str2, int i, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public Bundle generateKeyguardPassword(int i, String str, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public Bundle generateSecureRandom(String str, int i, byte[] bArr) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public ucmRetParcelable generateWrappedDek(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public Bundle getAdminConfigureBundleFromCs(int i, int i2, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public Bundle getAgentInfo(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public ucmRetParcelable getCertificateChain(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public Bundle getCredentialStorageProperty(int i, String str, Bundle bundle, int i2) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public ucmRetParcelable getDek(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public ucmRetParcelable getDekForVold(String str, byte[] bArr) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public ucmRetParcelable getDekForVoldInternalKey(String str, byte[] bArr) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public String getDetailErrorMessage(String str, int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public Bundle getInfo(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public Bundle getKeyType(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public Bundle getKeyguardPinCurrentRetryCount(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public Bundle getKeyguardPinMaximumLength(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public Bundle getKeyguardPinMaximumRetryCount(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public Bundle getKeyguardPinMinimumLength(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public String getKeyguardStorageForCurrentUser(int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public ucmRetParcelable getODEConfigurationForVold(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public Bundle getODESettingsConfiguration() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public ucmRetParcelable getOdeKey(String str, byte[] bArr) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public Bundle getStatus(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public boolean grantKeyChainAccess(String str, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public Bundle importKey(String str, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public Bundle importKeyPair(String str, byte[] bArr, byte[] bArr2, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public Bundle initKeyguardPin(String str, String str2, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public Bundle installCertificate(String str, byte[] bArr, byte[] bArr2, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public Bundle installCertificateIfSupported(String str, byte[] bArr, String str2, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public boolean isKeyChainGranted(String str, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public boolean isUserCertificatesExistInUCS() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public ucmRetParcelable keyAgreement(String str, String str2, byte[] bArr) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public Bundle[] listAllProviders() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public Bundle[] listProviders() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public ucmRetParcelable mac(String str, byte[] bArr, String str2) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public Bundle notifyChangeToPlugin(String str, int i, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public boolean notifyLicenseStatus(String str, String str2, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public ucmRetParcelable notifyVoldComplete(String str, byte[] bArr) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public int removeWpcOdeSettings() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public Bundle resetUid(String str, int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public Bundle resetUser(String str, int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public Bundle saw(String str, int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public Bundle sawInternal(String str, int i, int i2) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public Bundle setAdminConfigureBundleForCs(int i, int i2, String str, Bundle bundle, int i3) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public Bundle setCertificateChain(String str, byte[] bArr, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public Bundle setCredentialStorageProperty(int i, String str, Bundle bundle, int i2) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public Bundle setKeyguardPinMaximumLength(String str, int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public Bundle setKeyguardPinMaximumRetryCount(String str, int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public Bundle setKeyguardPinMinimumLength(String str, int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public Bundle setState(String str, int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public ucmRetParcelable sign(String str, byte[] bArr, String str2) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public ucmRetParcelable unwrapDek(String str, byte[] bArr) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public Bundle verifyPin(int i, String str, String str2, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public Bundle verifyPuk(String str, String str2, String str3) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public void resetNonMdmCertificates() throws RemoteException {
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public void updateAgentList() throws RemoteException {
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public void notifyPluginResult(Bundle bundle) throws RemoteException {
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public void registerSystemUICallback(ICredentialManagerServiceSystemUICallback iCredentialManagerServiceSystemUICallback) throws RemoteException {
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public void removeEnforcedLockTypeNotification(int i) throws RemoteException {
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public void showEnforcedLockTypeNotification(int i, String str) throws RemoteException {
        }
    }

    public abstract class Stub extends Binder implements IUcmService {
        public static final int TRANSACTION_APDUCommand = 28;
        public static final int TRANSACTION_changePin = 26;
        public static final int TRANSACTION_configureKeyguardSettings = 31;
        public static final int TRANSACTION_configureODESettings = 32;
        public static final int TRANSACTION_configureWPCDARFlag = 71;
        public static final int TRANSACTION_containsAlias = 36;
        public static final int TRANSACTION_decrypt = 3;
        public static final int TRANSACTION_delete = 12;
        public static final int TRANSACTION_deleteCertificate = 13;
        public static final int TRANSACTION_encrypt = 56;
        public static final int TRANSACTION_generateDek = 4;
        public static final int TRANSACTION_generateKey = 66;
        public static final int TRANSACTION_generateKeyPair = 14;
        public static final int TRANSACTION_generateKeyPairInternal = 15;
        public static final int TRANSACTION_generateKeyguardPassword = 30;
        public static final int TRANSACTION_generateSecureRandom = 19;
        public static final int TRANSACTION_generateWrappedDek = 5;
        public static final int TRANSACTION_getAdminConfigureBundleFromCs = 21;
        public static final int TRANSACTION_getAgentInfo = 18;
        public static final int TRANSACTION_getCertificateChain = 2;
        public static final int TRANSACTION_getCredentialStorageProperty = 23;
        public static final int TRANSACTION_getDek = 6;
        public static final int TRANSACTION_getDekForVold = 51;
        public static final int TRANSACTION_getDekForVoldInternalKey = 52;
        public static final int TRANSACTION_getDetailErrorMessage = 49;
        public static final int TRANSACTION_getInfo = 29;
        public static final int TRANSACTION_getKeyType = 68;
        public static final int TRANSACTION_getKeyguardPinCurrentRetryCount = 62;
        public static final int TRANSACTION_getKeyguardPinMaximumLength = 64;
        public static final int TRANSACTION_getKeyguardPinMaximumRetryCount = 61;
        public static final int TRANSACTION_getKeyguardPinMinimumLength = 63;
        public static final int TRANSACTION_getKeyguardStorageForCurrentUser = 45;
        public static final int TRANSACTION_getODEConfigurationForVold = 53;
        public static final int TRANSACTION_getODESettingsConfiguration = 33;
        public static final int TRANSACTION_getOdeKey = 54;
        public static final int TRANSACTION_getStatus = 39;
        public static final int TRANSACTION_grantKeyChainAccess = 37;
        public static final int TRANSACTION_importKey = 67;
        public static final int TRANSACTION_importKeyPair = 9;
        public static final int TRANSACTION_initKeyguardPin = 57;
        public static final int TRANSACTION_installCertificate = 10;
        public static final int TRANSACTION_installCertificateIfSupported = 69;
        public static final int TRANSACTION_isKeyChainGranted = 38;
        public static final int TRANSACTION_isUserCertificatesExistInUCS = 43;
        public static final int TRANSACTION_keyAgreement = 73;
        public static final int TRANSACTION_listAllProviders = 17;
        public static final int TRANSACTION_listProviders = 16;
        public static final int TRANSACTION_mac = 70;
        public static final int TRANSACTION_notifyChangeToPlugin = 41;
        public static final int TRANSACTION_notifyLicenseStatus = 40;
        public static final int TRANSACTION_notifyPluginResult = 65;
        public static final int TRANSACTION_notifyVoldComplete = 55;
        public static final int TRANSACTION_registerSystemUICallback = 48;
        public static final int TRANSACTION_removeEnforcedLockTypeNotification = 47;
        public static final int TRANSACTION_removeWpcOdeSettings = 72;
        public static final int TRANSACTION_resetNonMdmCertificates = 42;
        public static final int TRANSACTION_resetUid = 35;
        public static final int TRANSACTION_resetUser = 34;
        public static final int TRANSACTION_saw = 8;
        public static final int TRANSACTION_sawInternal = 44;
        public static final int TRANSACTION_setAdminConfigureBundleForCs = 20;
        public static final int TRANSACTION_setCertificateChain = 11;
        public static final int TRANSACTION_setCredentialStorageProperty = 22;
        public static final int TRANSACTION_setKeyguardPinMaximumLength = 60;
        public static final int TRANSACTION_setKeyguardPinMaximumRetryCount = 58;
        public static final int TRANSACTION_setKeyguardPinMinimumLength = 59;
        public static final int TRANSACTION_setState = 27;
        public static final int TRANSACTION_showEnforcedLockTypeNotification = 46;
        public static final int TRANSACTION_sign = 1;
        public static final int TRANSACTION_unwrapDek = 7;
        public static final int TRANSACTION_updateAgentList = 50;
        public static final int TRANSACTION_verifyPin = 24;
        public static final int TRANSACTION_verifyPuk = 25;

        class Proxy implements IUcmService {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle APDUCommand(String str, byte[] bArr, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle changePin(String str, String str2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public boolean configureKeyguardSettings(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public int configureODESettings(String str, Bundle bundle, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public int configureWPCDARFlag(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(71, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle containsAlias(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public ucmRetParcelable decrypt(String str, byte[] bArr, String str2, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ucmRetParcelable) parcelObtain2.readTypedObject(ucmRetParcelable.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle delete(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle deleteCertificate(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public ucmRetParcelable encrypt(String str, byte[] bArr, String str2, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(56, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ucmRetParcelable) parcelObtain2.readTypedObject(ucmRetParcelable.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public ucmRetParcelable generateDek(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ucmRetParcelable) parcelObtain2.readTypedObject(ucmRetParcelable.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle generateKey(String str, String str2, int i, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(66, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle generateKeyPair(String str, String str2, int i, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle generateKeyPairInternal(String str, String str2, int i, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle generateKeyguardPassword(int i, String str, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle generateSecureRandom(String str, int i, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public ucmRetParcelable generateWrappedDek(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ucmRetParcelable) parcelObtain2.readTypedObject(ucmRetParcelable.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle getAdminConfigureBundleFromCs(int i, int i2, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle getAgentInfo(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public ucmRetParcelable getCertificateChain(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ucmRetParcelable) parcelObtain2.readTypedObject(ucmRetParcelable.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle getCredentialStorageProperty(int i, String str, Bundle bundle, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public ucmRetParcelable getDek(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ucmRetParcelable) parcelObtain2.readTypedObject(ucmRetParcelable.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public ucmRetParcelable getDekForVold(String str, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(51, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ucmRetParcelable) parcelObtain2.readTypedObject(ucmRetParcelable.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public ucmRetParcelable getDekForVoldInternalKey(String str, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(52, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ucmRetParcelable) parcelObtain2.readTypedObject(ucmRetParcelable.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public String getDetailErrorMessage(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(49, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle getInfo(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IUcmService.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle getKeyType(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(68, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle getKeyguardPinCurrentRetryCount(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(62, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle getKeyguardPinMaximumLength(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(64, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle getKeyguardPinMaximumRetryCount(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(61, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle getKeyguardPinMinimumLength(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(63, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public String getKeyguardStorageForCurrentUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public ucmRetParcelable getODEConfigurationForVold(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(53, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ucmRetParcelable) parcelObtain2.readTypedObject(ucmRetParcelable.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle getODESettingsConfiguration() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public ucmRetParcelable getOdeKey(String str, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(54, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ucmRetParcelable) parcelObtain2.readTypedObject(ucmRetParcelable.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle getStatus(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public boolean grantKeyChainAccess(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle importKey(String str, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(67, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle importKeyPair(String str, byte[] bArr, byte[] bArr2, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeByteArray(bArr2);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle initKeyguardPin(String str, String str2, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(57, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle installCertificate(String str, byte[] bArr, byte[] bArr2, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeByteArray(bArr2);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle installCertificateIfSupported(String str, byte[] bArr, String str2, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(69, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public boolean isKeyChainGranted(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public boolean isUserCertificatesExistInUCS() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public ucmRetParcelable keyAgreement(String str, String str2, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(73, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ucmRetParcelable) parcelObtain2.readTypedObject(ucmRetParcelable.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle[] listAllProviders() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle[]) parcelObtain2.createTypedArray(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle[] listProviders() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle[]) parcelObtain2.createTypedArray(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public ucmRetParcelable mac(String str, byte[] bArr, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(70, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ucmRetParcelable) parcelObtain2.readTypedObject(ucmRetParcelable.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle notifyChangeToPlugin(String str, int i, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public boolean notifyLicenseStatus(String str, String str2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public void notifyPluginResult(Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(65, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public ucmRetParcelable notifyVoldComplete(String str, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(55, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ucmRetParcelable) parcelObtain2.readTypedObject(ucmRetParcelable.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public void registerSystemUICallback(ICredentialManagerServiceSystemUICallback iCredentialManagerServiceSystemUICallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iCredentialManagerServiceSystemUICallback);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public void removeEnforcedLockTypeNotification(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public int removeWpcOdeSettings() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    this.mRemote.transact(72, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public void resetNonMdmCertificates() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle resetUid(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle resetUser(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle saw(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle sawInternal(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle setAdminConfigureBundleForCs(int i, int i2, String str, Bundle bundle, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle setCertificateChain(String str, byte[] bArr, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle setCredentialStorageProperty(int i, String str, Bundle bundle, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle setKeyguardPinMaximumLength(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(60, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle setKeyguardPinMaximumRetryCount(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
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

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle setKeyguardPinMinimumLength(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(59, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle setState(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public void showEnforcedLockTypeNotification(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public ucmRetParcelable sign(String str, byte[] bArr, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ucmRetParcelable) parcelObtain2.readTypedObject(ucmRetParcelable.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public ucmRetParcelable unwrapDek(String str, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ucmRetParcelable) parcelObtain2.readTypedObject(ucmRetParcelable.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public void updateAgentList() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    this.mRemote.transact(50, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle verifyPin(int i, String str, String str2, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle verifyPuk(String str, String str2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IUcmService.DESCRIPTOR);
        }

        public static IUcmService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IUcmService.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IUcmService)) ? new Proxy(iBinder) : (IUcmService) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IUcmService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IUcmService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String string = parcel.readString();
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ucmRetParcelable ucmretparcelableSign = sign(string, bArrCreateByteArray, string2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(ucmretparcelableSign, 1);
                    return true;
                case 2:
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ucmRetParcelable certificateChain = getCertificateChain(string3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(certificateChain, 1);
                    return true;
                case 3:
                    String string4 = parcel.readString();
                    byte[] bArrCreateByteArray2 = parcel.createByteArray();
                    String string5 = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    ucmRetParcelable ucmretparcelableDecrypt = decrypt(string4, bArrCreateByteArray2, string5, bundle);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(ucmretparcelableDecrypt, 1);
                    return true;
                case 4:
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ucmRetParcelable ucmretparcelableGenerateDek = generateDek(string6);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(ucmretparcelableGenerateDek, 1);
                    return true;
                case 5:
                    String string7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ucmRetParcelable ucmretparcelableGenerateWrappedDek = generateWrappedDek(string7);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(ucmretparcelableGenerateWrappedDek, 1);
                    return true;
                case 6:
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ucmRetParcelable dek = getDek(string8);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(dek, 1);
                    return true;
                case 7:
                    String string9 = parcel.readString();
                    byte[] bArrCreateByteArray3 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    ucmRetParcelable ucmretparcelableUnwrapDek = unwrapDek(string9, bArrCreateByteArray3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(ucmretparcelableUnwrapDek, 1);
                    return true;
                case 8:
                    String string10 = parcel.readString();
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle bundleSaw = saw(string10, i3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bundleSaw, 1);
                    return true;
                case 9:
                    String string11 = parcel.readString();
                    byte[] bArrCreateByteArray4 = parcel.createByteArray();
                    byte[] bArrCreateByteArray5 = parcel.createByteArray();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle bundleImportKeyPair = importKeyPair(string11, bArrCreateByteArray4, bArrCreateByteArray5, bundle2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bundleImportKeyPair, 1);
                    return true;
                case 10:
                    String string12 = parcel.readString();
                    byte[] bArrCreateByteArray6 = parcel.createByteArray();
                    byte[] bArrCreateByteArray7 = parcel.createByteArray();
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle bundleInstallCertificate = installCertificate(string12, bArrCreateByteArray6, bArrCreateByteArray7, bundle3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bundleInstallCertificate, 1);
                    return true;
                case 11:
                    String string13 = parcel.readString();
                    byte[] bArrCreateByteArray8 = parcel.createByteArray();
                    Bundle bundle4 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle certificateChain2 = setCertificateChain(string13, bArrCreateByteArray8, bundle4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(certificateChain2, 1);
                    return true;
                case 12:
                    String string14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Bundle bundleDelete = delete(string14);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bundleDelete, 1);
                    return true;
                case 13:
                    String string15 = parcel.readString();
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle bundleDeleteCertificate = deleteCertificate(string15, i4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bundleDeleteCertificate, 1);
                    return true;
                case 14:
                    String string16 = parcel.readString();
                    String string17 = parcel.readString();
                    int i5 = parcel.readInt();
                    Bundle bundle5 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle bundleGenerateKeyPair = generateKeyPair(string16, string17, i5, bundle5);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bundleGenerateKeyPair, 1);
                    return true;
                case 15:
                    String string18 = parcel.readString();
                    String string19 = parcel.readString();
                    int i6 = parcel.readInt();
                    Bundle bundle6 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle bundleGenerateKeyPairInternal = generateKeyPairInternal(string18, string19, i6, bundle6);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bundleGenerateKeyPairInternal, 1);
                    return true;
                case 16:
                    Bundle[] bundleArrListProviders = listProviders();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(bundleArrListProviders, 1);
                    return true;
                case 17:
                    Bundle[] bundleArrListAllProviders = listAllProviders();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(bundleArrListAllProviders, 1);
                    return true;
                case 18:
                    String string20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Bundle agentInfo = getAgentInfo(string20);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(agentInfo, 1);
                    return true;
                case 19:
                    String string21 = parcel.readString();
                    int i7 = parcel.readInt();
                    byte[] bArrCreateByteArray9 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    Bundle bundleGenerateSecureRandom = generateSecureRandom(string21, i7, bArrCreateByteArray9);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bundleGenerateSecureRandom, 1);
                    return true;
                case 20:
                    int i8 = parcel.readInt();
                    int i9 = parcel.readInt();
                    String string22 = parcel.readString();
                    Bundle bundle7 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle adminConfigureBundleForCs = setAdminConfigureBundleForCs(i8, i9, string22, bundle7, i10);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(adminConfigureBundleForCs, 1);
                    return true;
                case 21:
                    int i11 = parcel.readInt();
                    int i12 = parcel.readInt();
                    String string23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Bundle adminConfigureBundleFromCs = getAdminConfigureBundleFromCs(i11, i12, string23);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(adminConfigureBundleFromCs, 1);
                    return true;
                case 22:
                    int i13 = parcel.readInt();
                    String string24 = parcel.readString();
                    Bundle bundle8 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle credentialStorageProperty = setCredentialStorageProperty(i13, string24, bundle8, i14);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(credentialStorageProperty, 1);
                    return true;
                case 23:
                    int i15 = parcel.readInt();
                    String string25 = parcel.readString();
                    Bundle bundle9 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int i16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle credentialStorageProperty2 = getCredentialStorageProperty(i15, string25, bundle9, i16);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(credentialStorageProperty2, 1);
                    return true;
                case 24:
                    int i17 = parcel.readInt();
                    String string26 = parcel.readString();
                    String string27 = parcel.readString();
                    Bundle bundle10 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle bundleVerifyPin = verifyPin(i17, string26, string27, bundle10);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bundleVerifyPin, 1);
                    return true;
                case 25:
                    String string28 = parcel.readString();
                    String string29 = parcel.readString();
                    String string30 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Bundle bundleVerifyPuk = verifyPuk(string28, string29, string30);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bundleVerifyPuk, 1);
                    return true;
                case 26:
                    String string31 = parcel.readString();
                    String string32 = parcel.readString();
                    String string33 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Bundle bundleChangePin = changePin(string31, string32, string33);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bundleChangePin, 1);
                    return true;
                case 27:
                    String string34 = parcel.readString();
                    int i18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle state = setState(string34, i18);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(state, 1);
                    return true;
                case 28:
                    String string35 = parcel.readString();
                    byte[] bArrCreateByteArray10 = parcel.createByteArray();
                    Bundle bundle11 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle bundleAPDUCommand = APDUCommand(string35, bArrCreateByteArray10, bundle11);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bundleAPDUCommand, 1);
                    return true;
                case 29:
                    String string36 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Bundle info = getInfo(string36);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(info, 1);
                    return true;
                case 30:
                    int i19 = parcel.readInt();
                    String string37 = parcel.readString();
                    Bundle bundle12 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle bundleGenerateKeyguardPassword = generateKeyguardPassword(i19, string37, bundle12);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bundleGenerateKeyguardPassword, 1);
                    return true;
                case 31:
                    int i20 = parcel.readInt();
                    String string38 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zConfigureKeyguardSettings = configureKeyguardSettings(i20, string38);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zConfigureKeyguardSettings);
                    return true;
                case 32:
                    String string39 = parcel.readString();
                    Bundle bundle13 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    String string40 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iConfigureODESettings = configureODESettings(string39, bundle13, string40);
                    parcel2.writeNoException();
                    parcel2.writeInt(iConfigureODESettings);
                    return true;
                case 33:
                    Bundle oDESettingsConfiguration = getODESettingsConfiguration();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(oDESettingsConfiguration, 1);
                    return true;
                case 34:
                    String string41 = parcel.readString();
                    int i21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle bundleResetUser = resetUser(string41, i21);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bundleResetUser, 1);
                    return true;
                case 35:
                    String string42 = parcel.readString();
                    int i22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle bundleResetUid = resetUid(string42, i22);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bundleResetUid, 1);
                    return true;
                case 36:
                    String string43 = parcel.readString();
                    int i23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle bundleContainsAlias = containsAlias(string43, i23);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bundleContainsAlias, 1);
                    return true;
                case 37:
                    String string44 = parcel.readString();
                    int i24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zGrantKeyChainAccess = grantKeyChainAccess(string44, i24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zGrantKeyChainAccess);
                    return true;
                case 38:
                    String string45 = parcel.readString();
                    int i25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsKeyChainGranted = isKeyChainGranted(string45, i25);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsKeyChainGranted);
                    return true;
                case 39:
                    String string46 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Bundle status = getStatus(string46);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(status, 1);
                    return true;
                case 40:
                    String string47 = parcel.readString();
                    String string48 = parcel.readString();
                    int i26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zNotifyLicenseStatus = notifyLicenseStatus(string47, string48, i26);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zNotifyLicenseStatus);
                    return true;
                case 41:
                    String string49 = parcel.readString();
                    int i27 = parcel.readInt();
                    Bundle bundle14 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle bundleNotifyChangeToPlugin = notifyChangeToPlugin(string49, i27, bundle14);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bundleNotifyChangeToPlugin, 1);
                    return true;
                case 42:
                    resetNonMdmCertificates();
                    parcel2.writeNoException();
                    return true;
                case 43:
                    boolean zIsUserCertificatesExistInUCS = isUserCertificatesExistInUCS();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsUserCertificatesExistInUCS);
                    return true;
                case 44:
                    String string50 = parcel.readString();
                    int i28 = parcel.readInt();
                    int i29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle bundleSawInternal = sawInternal(string50, i28, i29);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bundleSawInternal, 1);
                    return true;
                case 45:
                    int i30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String keyguardStorageForCurrentUser = getKeyguardStorageForCurrentUser(i30);
                    parcel2.writeNoException();
                    parcel2.writeString(keyguardStorageForCurrentUser);
                    return true;
                case 46:
                    int i31 = parcel.readInt();
                    String string51 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    showEnforcedLockTypeNotification(i31, string51);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    int i32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeEnforcedLockTypeNotification(i32);
                    parcel2.writeNoException();
                    return true;
                case 48:
                    ICredentialManagerServiceSystemUICallback iCredentialManagerServiceSystemUICallbackAsInterface = ICredentialManagerServiceSystemUICallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerSystemUICallback(iCredentialManagerServiceSystemUICallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 49:
                    String string52 = parcel.readString();
                    int i33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String detailErrorMessage = getDetailErrorMessage(string52, i33);
                    parcel2.writeNoException();
                    parcel2.writeString(detailErrorMessage);
                    return true;
                case 50:
                    updateAgentList();
                    parcel2.writeNoException();
                    return true;
                case 51:
                    String string53 = parcel.readString();
                    byte[] bArrCreateByteArray11 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    ucmRetParcelable dekForVold = getDekForVold(string53, bArrCreateByteArray11);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(dekForVold, 1);
                    return true;
                case 52:
                    String string54 = parcel.readString();
                    byte[] bArrCreateByteArray12 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    ucmRetParcelable dekForVoldInternalKey = getDekForVoldInternalKey(string54, bArrCreateByteArray12);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(dekForVoldInternalKey, 1);
                    return true;
                case 53:
                    String string55 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ucmRetParcelable oDEConfigurationForVold = getODEConfigurationForVold(string55);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(oDEConfigurationForVold, 1);
                    return true;
                case 54:
                    String string56 = parcel.readString();
                    byte[] bArrCreateByteArray13 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    ucmRetParcelable odeKey = getOdeKey(string56, bArrCreateByteArray13);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(odeKey, 1);
                    return true;
                case 55:
                    String string57 = parcel.readString();
                    byte[] bArrCreateByteArray14 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    ucmRetParcelable ucmretparcelableNotifyVoldComplete = notifyVoldComplete(string57, bArrCreateByteArray14);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(ucmretparcelableNotifyVoldComplete, 1);
                    return true;
                case 56:
                    String string58 = parcel.readString();
                    byte[] bArrCreateByteArray15 = parcel.createByteArray();
                    String string59 = parcel.readString();
                    Bundle bundle15 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    ucmRetParcelable ucmretparcelableEncrypt = encrypt(string58, bArrCreateByteArray15, string59, bundle15);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(ucmretparcelableEncrypt, 1);
                    return true;
                case 57:
                    String string60 = parcel.readString();
                    String string61 = parcel.readString();
                    Bundle bundle16 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle bundleInitKeyguardPin = initKeyguardPin(string60, string61, bundle16);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bundleInitKeyguardPin, 1);
                    return true;
                case 58:
                    String string62 = parcel.readString();
                    int i34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle keyguardPinMaximumRetryCount = setKeyguardPinMaximumRetryCount(string62, i34);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyguardPinMaximumRetryCount, 1);
                    return true;
                case 59:
                    String string63 = parcel.readString();
                    int i35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle keyguardPinMinimumLength = setKeyguardPinMinimumLength(string63, i35);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyguardPinMinimumLength, 1);
                    return true;
                case 60:
                    String string64 = parcel.readString();
                    int i36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle keyguardPinMaximumLength = setKeyguardPinMaximumLength(string64, i36);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyguardPinMaximumLength, 1);
                    return true;
                case 61:
                    String string65 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Bundle keyguardPinMaximumRetryCount2 = getKeyguardPinMaximumRetryCount(string65);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyguardPinMaximumRetryCount2, 1);
                    return true;
                case 62:
                    String string66 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Bundle keyguardPinCurrentRetryCount = getKeyguardPinCurrentRetryCount(string66);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyguardPinCurrentRetryCount, 1);
                    return true;
                case 63:
                    String string67 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Bundle keyguardPinMinimumLength2 = getKeyguardPinMinimumLength(string67);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyguardPinMinimumLength2, 1);
                    return true;
                case 64:
                    String string68 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Bundle keyguardPinMaximumLength2 = getKeyguardPinMaximumLength(string68);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyguardPinMaximumLength2, 1);
                    return true;
                case 65:
                    Bundle bundle17 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyPluginResult(bundle17);
                    parcel2.writeNoException();
                    return true;
                case 66:
                    String string69 = parcel.readString();
                    String string70 = parcel.readString();
                    int i37 = parcel.readInt();
                    Bundle bundle18 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle bundleGenerateKey = generateKey(string69, string70, i37, bundle18);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bundleGenerateKey, 1);
                    return true;
                case 67:
                    String string71 = parcel.readString();
                    Bundle bundle19 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle bundleImportKey = importKey(string71, bundle19);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bundleImportKey, 1);
                    return true;
                case 68:
                    String string72 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Bundle keyType = getKeyType(string72);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyType, 1);
                    return true;
                case 69:
                    String string73 = parcel.readString();
                    byte[] bArrCreateByteArray16 = parcel.createByteArray();
                    String string74 = parcel.readString();
                    Bundle bundle20 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle bundleInstallCertificateIfSupported = installCertificateIfSupported(string73, bArrCreateByteArray16, string74, bundle20);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bundleInstallCertificateIfSupported, 1);
                    return true;
                case 70:
                    String string75 = parcel.readString();
                    byte[] bArrCreateByteArray17 = parcel.createByteArray();
                    String string76 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ucmRetParcelable ucmretparcelableMac = mac(string75, bArrCreateByteArray17, string76);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(ucmretparcelableMac, 1);
                    return true;
                case 71:
                    String string77 = parcel.readString();
                    String string78 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iConfigureWPCDARFlag = configureWPCDARFlag(string77, string78);
                    parcel2.writeNoException();
                    parcel2.writeInt(iConfigureWPCDARFlag);
                    return true;
                case 72:
                    int iRemoveWpcOdeSettings = removeWpcOdeSettings();
                    parcel2.writeNoException();
                    parcel2.writeInt(iRemoveWpcOdeSettings);
                    return true;
                case 73:
                    String string79 = parcel.readString();
                    String string80 = parcel.readString();
                    byte[] bArrCreateByteArray18 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    ucmRetParcelable ucmretparcelableKeyAgreement = keyAgreement(string79, string80, bArrCreateByteArray18);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(ucmretparcelableKeyAgreement, 1);
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
