package com.samsung.android.knox.ucm.core;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.knox.ucm.core.ICredentialManagerServiceSystemUICallback;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    int removeODESettings() throws RemoteException;

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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        public int removeODESettings() throws RemoteException {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        public static final int TRANSACTION_removeODESettings = 72;
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

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        class Proxy implements IUcmService {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle APDUCommand(String str, byte[] bArr, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle changePin(String str, String str2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public boolean configureKeyguardSettings(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public int configureODESettings(String str, Bundle bundle, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeString(str2);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public int configureWPCDARFlag(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(71, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle containsAlias(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public ucmRetParcelable decrypt(String str, byte[] bArr, String str2, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ucmRetParcelable) obtain2.readTypedObject(ucmRetParcelable.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle delete(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle deleteCertificate(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public ucmRetParcelable encrypt(String str, byte[] bArr, String str2, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ucmRetParcelable) obtain2.readTypedObject(ucmRetParcelable.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public ucmRetParcelable generateDek(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ucmRetParcelable) obtain2.readTypedObject(ucmRetParcelable.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle generateKey(String str, String str2, int i, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(66, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle generateKeyPair(String str, String str2, int i, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle generateKeyPairInternal(String str, String str2, int i, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle generateKeyguardPassword(int i, String str, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle generateSecureRandom(String str, int i, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public ucmRetParcelable generateWrappedDek(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ucmRetParcelable) obtain2.readTypedObject(ucmRetParcelable.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle getAdminConfigureBundleFromCs(int i, int i2, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle getAgentInfo(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public ucmRetParcelable getCertificateChain(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ucmRetParcelable) obtain2.readTypedObject(ucmRetParcelable.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle getCredentialStorageProperty(int i, String str, Bundle bundle, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public ucmRetParcelable getDek(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ucmRetParcelable) obtain2.readTypedObject(ucmRetParcelable.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public ucmRetParcelable getDekForVold(String str, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ucmRetParcelable) obtain2.readTypedObject(ucmRetParcelable.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public ucmRetParcelable getDekForVoldInternalKey(String str, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ucmRetParcelable) obtain2.readTypedObject(ucmRetParcelable.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public String getDetailErrorMessage(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle getInfo(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IUcmService.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle getKeyType(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(68, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle getKeyguardPinCurrentRetryCount(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(62, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle getKeyguardPinMaximumLength(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle getKeyguardPinMaximumRetryCount(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle getKeyguardPinMinimumLength(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public String getKeyguardStorageForCurrentUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public ucmRetParcelable getODEConfigurationForVold(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ucmRetParcelable) obtain2.readTypedObject(ucmRetParcelable.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle getODESettingsConfiguration() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public ucmRetParcelable getOdeKey(String str, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ucmRetParcelable) obtain2.readTypedObject(ucmRetParcelable.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle getStatus(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public boolean grantKeyChainAccess(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle importKey(String str, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(67, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle importKeyPair(String str, byte[] bArr, byte[] bArr2, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle initKeyguardPin(String str, String str2, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle installCertificate(String str, byte[] bArr, byte[] bArr2, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle installCertificateIfSupported(String str, byte[] bArr, String str2, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(69, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public boolean isKeyChainGranted(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public boolean isUserCertificatesExistInUCS() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public ucmRetParcelable keyAgreement(String str, String str2, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(73, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ucmRetParcelable) obtain2.readTypedObject(ucmRetParcelable.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle[] listAllProviders() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle[]) obtain2.createTypedArray(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle[] listProviders() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle[]) obtain2.createTypedArray(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public ucmRetParcelable mac(String str, byte[] bArr, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeString(str2);
                    this.mRemote.transact(70, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ucmRetParcelable) obtain2.readTypedObject(ucmRetParcelable.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle notifyChangeToPlugin(String str, int i, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public boolean notifyLicenseStatus(String str, String str2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public void notifyPluginResult(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(65, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public ucmRetParcelable notifyVoldComplete(String str, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ucmRetParcelable) obtain2.readTypedObject(ucmRetParcelable.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public void registerSystemUICallback(ICredentialManagerServiceSystemUICallback iCredentialManagerServiceSystemUICallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeStrongInterface(iCredentialManagerServiceSystemUICallback);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public void removeEnforcedLockTypeNotification(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public int removeODESettings() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    this.mRemote.transact(72, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public void resetNonMdmCertificates() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle resetUid(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle resetUser(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle saw(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle sawInternal(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle setAdminConfigureBundleForCs(int i, int i2, String str, Bundle bundle, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i3);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle setCertificateChain(String str, byte[] bArr, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle setCredentialStorageProperty(int i, String str, Bundle bundle, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle setKeyguardPinMaximumLength(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle setKeyguardPinMaximumRetryCount(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
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

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle setKeyguardPinMinimumLength(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle setState(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public void showEnforcedLockTypeNotification(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public ucmRetParcelable sign(String str, byte[] bArr, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeString(str2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ucmRetParcelable) obtain2.readTypedObject(ucmRetParcelable.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public ucmRetParcelable unwrapDek(String str, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ucmRetParcelable) obtain2.readTypedObject(ucmRetParcelable.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public void updateAgentList() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle verifyPin(int i, String str, String str2, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public Bundle verifyPuk(String str, String str2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IUcmService.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IUcmService)) ? new Proxy(iBinder) : (IUcmService) queryLocalInterface;
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
                    String readString = parcel.readString();
                    byte[] createByteArray = parcel.createByteArray();
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ucmRetParcelable sign = sign(readString, createByteArray, readString2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(sign, 1);
                    return true;
                case 2:
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ucmRetParcelable certificateChain = getCertificateChain(readString3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(certificateChain, 1);
                    return true;
                case 3:
                    String readString4 = parcel.readString();
                    byte[] createByteArray2 = parcel.createByteArray();
                    String readString5 = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    ucmRetParcelable decrypt = decrypt(readString4, createByteArray2, readString5, bundle);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(decrypt, 1);
                    return true;
                case 4:
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ucmRetParcelable generateDek = generateDek(readString6);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(generateDek, 1);
                    return true;
                case 5:
                    String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ucmRetParcelable generateWrappedDek = generateWrappedDek(readString7);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(generateWrappedDek, 1);
                    return true;
                case 6:
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ucmRetParcelable dek = getDek(readString8);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(dek, 1);
                    return true;
                case 7:
                    String readString9 = parcel.readString();
                    byte[] createByteArray3 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    ucmRetParcelable unwrapDek = unwrapDek(readString9, createByteArray3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(unwrapDek, 1);
                    return true;
                case 8:
                    String readString10 = parcel.readString();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle saw = saw(readString10, readInt);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(saw, 1);
                    return true;
                case 9:
                    String readString11 = parcel.readString();
                    byte[] createByteArray4 = parcel.createByteArray();
                    byte[] createByteArray5 = parcel.createByteArray();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle importKeyPair = importKeyPair(readString11, createByteArray4, createByteArray5, bundle2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(importKeyPair, 1);
                    return true;
                case 10:
                    String readString12 = parcel.readString();
                    byte[] createByteArray6 = parcel.createByteArray();
                    byte[] createByteArray7 = parcel.createByteArray();
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle installCertificate = installCertificate(readString12, createByteArray6, createByteArray7, bundle3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(installCertificate, 1);
                    return true;
                case 11:
                    String readString13 = parcel.readString();
                    byte[] createByteArray8 = parcel.createByteArray();
                    Bundle bundle4 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle certificateChain2 = setCertificateChain(readString13, createByteArray8, bundle4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(certificateChain2, 1);
                    return true;
                case 12:
                    String readString14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Bundle delete = delete(readString14);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(delete, 1);
                    return true;
                case 13:
                    String readString15 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle deleteCertificate = deleteCertificate(readString15, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(deleteCertificate, 1);
                    return true;
                case 14:
                    String readString16 = parcel.readString();
                    String readString17 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    Bundle bundle5 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle generateKeyPair = generateKeyPair(readString16, readString17, readInt3, bundle5);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(generateKeyPair, 1);
                    return true;
                case 15:
                    String readString18 = parcel.readString();
                    String readString19 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    Bundle bundle6 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle generateKeyPairInternal = generateKeyPairInternal(readString18, readString19, readInt4, bundle6);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(generateKeyPairInternal, 1);
                    return true;
                case 16:
                    Bundle[] listProviders = listProviders();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(listProviders, 1);
                    return true;
                case 17:
                    Bundle[] listAllProviders = listAllProviders();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(listAllProviders, 1);
                    return true;
                case 18:
                    String readString20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Bundle agentInfo = getAgentInfo(readString20);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(agentInfo, 1);
                    return true;
                case 19:
                    String readString21 = parcel.readString();
                    int readInt5 = parcel.readInt();
                    byte[] createByteArray9 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    Bundle generateSecureRandom = generateSecureRandom(readString21, readInt5, createByteArray9);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(generateSecureRandom, 1);
                    return true;
                case 20:
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    String readString22 = parcel.readString();
                    Bundle bundle7 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle adminConfigureBundleForCs = setAdminConfigureBundleForCs(readInt6, readInt7, readString22, bundle7, readInt8);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(adminConfigureBundleForCs, 1);
                    return true;
                case 21:
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    String readString23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Bundle adminConfigureBundleFromCs = getAdminConfigureBundleFromCs(readInt9, readInt10, readString23);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(adminConfigureBundleFromCs, 1);
                    return true;
                case 22:
                    int readInt11 = parcel.readInt();
                    String readString24 = parcel.readString();
                    Bundle bundle8 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle credentialStorageProperty = setCredentialStorageProperty(readInt11, readString24, bundle8, readInt12);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(credentialStorageProperty, 1);
                    return true;
                case 23:
                    int readInt13 = parcel.readInt();
                    String readString25 = parcel.readString();
                    Bundle bundle9 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle credentialStorageProperty2 = getCredentialStorageProperty(readInt13, readString25, bundle9, readInt14);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(credentialStorageProperty2, 1);
                    return true;
                case 24:
                    int readInt15 = parcel.readInt();
                    String readString26 = parcel.readString();
                    String readString27 = parcel.readString();
                    Bundle bundle10 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle verifyPin = verifyPin(readInt15, readString26, readString27, bundle10);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(verifyPin, 1);
                    return true;
                case 25:
                    String readString28 = parcel.readString();
                    String readString29 = parcel.readString();
                    String readString30 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Bundle verifyPuk = verifyPuk(readString28, readString29, readString30);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(verifyPuk, 1);
                    return true;
                case 26:
                    String readString31 = parcel.readString();
                    String readString32 = parcel.readString();
                    String readString33 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Bundle changePin = changePin(readString31, readString32, readString33);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(changePin, 1);
                    return true;
                case 27:
                    String readString34 = parcel.readString();
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle state = setState(readString34, readInt16);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(state, 1);
                    return true;
                case 28:
                    String readString35 = parcel.readString();
                    byte[] createByteArray10 = parcel.createByteArray();
                    Bundle bundle11 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle APDUCommand = APDUCommand(readString35, createByteArray10, bundle11);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(APDUCommand, 1);
                    return true;
                case 29:
                    String readString36 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Bundle info = getInfo(readString36);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(info, 1);
                    return true;
                case 30:
                    int readInt17 = parcel.readInt();
                    String readString37 = parcel.readString();
                    Bundle bundle12 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle generateKeyguardPassword = generateKeyguardPassword(readInt17, readString37, bundle12);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(generateKeyguardPassword, 1);
                    return true;
                case 31:
                    int readInt18 = parcel.readInt();
                    String readString38 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean configureKeyguardSettings = configureKeyguardSettings(readInt18, readString38);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(configureKeyguardSettings);
                    return true;
                case 32:
                    String readString39 = parcel.readString();
                    Bundle bundle13 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    String readString40 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int configureODESettings = configureODESettings(readString39, bundle13, readString40);
                    parcel2.writeNoException();
                    parcel2.writeInt(configureODESettings);
                    return true;
                case 33:
                    Bundle oDESettingsConfiguration = getODESettingsConfiguration();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(oDESettingsConfiguration, 1);
                    return true;
                case 34:
                    String readString41 = parcel.readString();
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle resetUser = resetUser(readString41, readInt19);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(resetUser, 1);
                    return true;
                case 35:
                    String readString42 = parcel.readString();
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle resetUid = resetUid(readString42, readInt20);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(resetUid, 1);
                    return true;
                case 36:
                    String readString43 = parcel.readString();
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle containsAlias = containsAlias(readString43, readInt21);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(containsAlias, 1);
                    return true;
                case 37:
                    String readString44 = parcel.readString();
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean grantKeyChainAccess = grantKeyChainAccess(readString44, readInt22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(grantKeyChainAccess);
                    return true;
                case 38:
                    String readString45 = parcel.readString();
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isKeyChainGranted = isKeyChainGranted(readString45, readInt23);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isKeyChainGranted);
                    return true;
                case 39:
                    String readString46 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Bundle status = getStatus(readString46);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(status, 1);
                    return true;
                case 40:
                    String readString47 = parcel.readString();
                    String readString48 = parcel.readString();
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean notifyLicenseStatus = notifyLicenseStatus(readString47, readString48, readInt24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(notifyLicenseStatus);
                    return true;
                case 41:
                    String readString49 = parcel.readString();
                    int readInt25 = parcel.readInt();
                    Bundle bundle14 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle notifyChangeToPlugin = notifyChangeToPlugin(readString49, readInt25, bundle14);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(notifyChangeToPlugin, 1);
                    return true;
                case 42:
                    resetNonMdmCertificates();
                    parcel2.writeNoException();
                    return true;
                case 43:
                    boolean isUserCertificatesExistInUCS = isUserCertificatesExistInUCS();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUserCertificatesExistInUCS);
                    return true;
                case 44:
                    String readString50 = parcel.readString();
                    int readInt26 = parcel.readInt();
                    int readInt27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle sawInternal = sawInternal(readString50, readInt26, readInt27);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(sawInternal, 1);
                    return true;
                case 45:
                    int readInt28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String keyguardStorageForCurrentUser = getKeyguardStorageForCurrentUser(readInt28);
                    parcel2.writeNoException();
                    parcel2.writeString(keyguardStorageForCurrentUser);
                    return true;
                case 46:
                    int readInt29 = parcel.readInt();
                    String readString51 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    showEnforcedLockTypeNotification(readInt29, readString51);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    int readInt30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeEnforcedLockTypeNotification(readInt30);
                    parcel2.writeNoException();
                    return true;
                case 48:
                    ICredentialManagerServiceSystemUICallback asInterface = ICredentialManagerServiceSystemUICallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerSystemUICallback(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 49:
                    String readString52 = parcel.readString();
                    int readInt31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String detailErrorMessage = getDetailErrorMessage(readString52, readInt31);
                    parcel2.writeNoException();
                    parcel2.writeString(detailErrorMessage);
                    return true;
                case 50:
                    updateAgentList();
                    parcel2.writeNoException();
                    return true;
                case 51:
                    String readString53 = parcel.readString();
                    byte[] createByteArray11 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    ucmRetParcelable dekForVold = getDekForVold(readString53, createByteArray11);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(dekForVold, 1);
                    return true;
                case 52:
                    String readString54 = parcel.readString();
                    byte[] createByteArray12 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    ucmRetParcelable dekForVoldInternalKey = getDekForVoldInternalKey(readString54, createByteArray12);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(dekForVoldInternalKey, 1);
                    return true;
                case 53:
                    String readString55 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ucmRetParcelable oDEConfigurationForVold = getODEConfigurationForVold(readString55);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(oDEConfigurationForVold, 1);
                    return true;
                case 54:
                    String readString56 = parcel.readString();
                    byte[] createByteArray13 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    ucmRetParcelable odeKey = getOdeKey(readString56, createByteArray13);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(odeKey, 1);
                    return true;
                case 55:
                    String readString57 = parcel.readString();
                    byte[] createByteArray14 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    ucmRetParcelable notifyVoldComplete = notifyVoldComplete(readString57, createByteArray14);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(notifyVoldComplete, 1);
                    return true;
                case 56:
                    String readString58 = parcel.readString();
                    byte[] createByteArray15 = parcel.createByteArray();
                    String readString59 = parcel.readString();
                    Bundle bundle15 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    ucmRetParcelable encrypt = encrypt(readString58, createByteArray15, readString59, bundle15);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(encrypt, 1);
                    return true;
                case 57:
                    String readString60 = parcel.readString();
                    String readString61 = parcel.readString();
                    Bundle bundle16 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle initKeyguardPin = initKeyguardPin(readString60, readString61, bundle16);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(initKeyguardPin, 1);
                    return true;
                case 58:
                    String readString62 = parcel.readString();
                    int readInt32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle keyguardPinMaximumRetryCount = setKeyguardPinMaximumRetryCount(readString62, readInt32);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyguardPinMaximumRetryCount, 1);
                    return true;
                case 59:
                    String readString63 = parcel.readString();
                    int readInt33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle keyguardPinMinimumLength = setKeyguardPinMinimumLength(readString63, readInt33);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyguardPinMinimumLength, 1);
                    return true;
                case 60:
                    String readString64 = parcel.readString();
                    int readInt34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle keyguardPinMaximumLength = setKeyguardPinMaximumLength(readString64, readInt34);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyguardPinMaximumLength, 1);
                    return true;
                case 61:
                    String readString65 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Bundle keyguardPinMaximumRetryCount2 = getKeyguardPinMaximumRetryCount(readString65);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyguardPinMaximumRetryCount2, 1);
                    return true;
                case 62:
                    String readString66 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Bundle keyguardPinCurrentRetryCount = getKeyguardPinCurrentRetryCount(readString66);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyguardPinCurrentRetryCount, 1);
                    return true;
                case 63:
                    String readString67 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Bundle keyguardPinMinimumLength2 = getKeyguardPinMinimumLength(readString67);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyguardPinMinimumLength2, 1);
                    return true;
                case 64:
                    String readString68 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Bundle keyguardPinMaximumLength2 = getKeyguardPinMaximumLength(readString68);
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
                    String readString69 = parcel.readString();
                    String readString70 = parcel.readString();
                    int readInt35 = parcel.readInt();
                    Bundle bundle18 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle generateKey = generateKey(readString69, readString70, readInt35, bundle18);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(generateKey, 1);
                    return true;
                case 67:
                    String readString71 = parcel.readString();
                    Bundle bundle19 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle importKey = importKey(readString71, bundle19);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(importKey, 1);
                    return true;
                case 68:
                    String readString72 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Bundle keyType = getKeyType(readString72);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyType, 1);
                    return true;
                case 69:
                    String readString73 = parcel.readString();
                    byte[] createByteArray16 = parcel.createByteArray();
                    String readString74 = parcel.readString();
                    Bundle bundle20 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle installCertificateIfSupported = installCertificateIfSupported(readString73, createByteArray16, readString74, bundle20);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(installCertificateIfSupported, 1);
                    return true;
                case 70:
                    String readString75 = parcel.readString();
                    byte[] createByteArray17 = parcel.createByteArray();
                    String readString76 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ucmRetParcelable mac = mac(readString75, createByteArray17, readString76);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(mac, 1);
                    return true;
                case 71:
                    String readString77 = parcel.readString();
                    String readString78 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int configureWPCDARFlag = configureWPCDARFlag(readString77, readString78);
                    parcel2.writeNoException();
                    parcel2.writeInt(configureWPCDARFlag);
                    return true;
                case 72:
                    int removeODESettings = removeODESettings();
                    parcel2.writeNoException();
                    parcel2.writeInt(removeODESettings);
                    return true;
                case 73:
                    String readString79 = parcel.readString();
                    String readString80 = parcel.readString();
                    byte[] createByteArray18 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    ucmRetParcelable keyAgreement = keyAgreement(readString79, readString80, createByteArray18);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyAgreement, 1);
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
