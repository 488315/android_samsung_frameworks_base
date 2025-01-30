package com.samsung.android.knox.ucm.core;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.samsung.android.knox.ucm.core.ICredentialManagerServiceSystemUICallback;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface IUcmService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.ucm.core.IUcmService";

    Bundle APDUCommand(String str, byte[] bArr, Bundle bundle);

    Bundle changePin(String str, String str2, String str3, boolean z);

    boolean configureKeyguardSettings(int i, String str);

    int configureODESettings(String str, Bundle bundle, String str2);

    Bundle containsAlias(String str, int i);

    ApduMessage createSecureChannel(int i, Bundle bundle);

    ucmRetParcelable decrypt(String str, byte[] bArr, String str2, Bundle bundle);

    boolean delegateDeleteFile(String str);

    Bundle delegateGetTaProfile();

    Bundle delegateLoadTa(boolean z);

    Bundle delegateProcessTACommand(Bundle bundle);

    byte[] delegateReadFile(String str);

    boolean delegateSaveFile(String str, byte[] bArr);

    Bundle delegateUnloadTa();

    Bundle delegateWrapSessionKey(byte[] bArr);

    Bundle delete(String str);

    Bundle deleteCertificate(String str, int i);

    int destroySecureChannel();

    ucmRetParcelable encrypt(String str, byte[] bArr, String str2, Bundle bundle);

    ucmRetParcelable generateDek(String str);

    Bundle generateKey(String str, String str2, int i, Bundle bundle);

    Bundle generateKeyPair(String str, String str2, int i, Bundle bundle);

    Bundle generateKeyPairInternal(String str, String str2, int i, Bundle bundle);

    Bundle generateKeyguardPassword(int i, String str, Bundle bundle);

    Bundle generateSecureRandom(String str, int i, byte[] bArr);

    ucmRetParcelable generateWrappedDek(String str);

    Bundle getAdminConfigureBundleFromCs(int i, int i2, String str);

    Bundle getAgentInfo(String str);

    ucmRetParcelable getCertificateChain(String str);

    Bundle getCredentialStorageProperty(int i, String str, Bundle bundle, int i2);

    ucmRetParcelable getDek(String str);

    ucmRetParcelable getDekForVold(String str, byte[] bArr);

    ucmRetParcelable getDekForVoldInternalKey(String str, byte[] bArr);

    String getDetailErrorMessage(String str, int i);

    Bundle getInfo(String str);

    Bundle getKeyType(String str);

    Bundle getKeyguardPinCurrentRetryCount(String str);

    Bundle getKeyguardPinMaximumLength(String str);

    Bundle getKeyguardPinMaximumRetryCount(String str);

    Bundle getKeyguardPinMinimumLength(String str);

    String getKeyguardStorageForCurrentUser(int i);

    ucmRetParcelable getODEConfigurationForVold(String str);

    Bundle getODESettingsConfiguration();

    ucmRetParcelable getOdeKey(String str, byte[] bArr);

    Bundle getStatus(String str);

    boolean grantKeyChainAccess(String str, int i);

    Bundle importKey(String str, Bundle bundle);

    Bundle importKeyPair(String str, byte[] bArr, byte[] bArr2, Bundle bundle);

    Bundle initKeyguardPin(String str, String str2, Bundle bundle);

    Bundle installCertificate(String str, byte[] bArr, byte[] bArr2, Bundle bundle);

    Bundle installCertificateIfSupported(String str, byte[] bArr, String str2, Bundle bundle);

    boolean isKeyChainGranted(String str, int i);

    boolean isUserCertificatesExistInUCS();

    Bundle[] listAllProviders();

    Bundle[] listProviders();

    ucmRetParcelable mac(String str, byte[] bArr, String str2);

    Bundle notifyChangeToPlugin(String str, int i, Bundle bundle);

    boolean notifyLicenseStatus(String str, String str2, int i);

    void notifyPluginResult(Bundle bundle);

    ucmRetParcelable notifyVoldComplete(String str, byte[] bArr);

    ApduMessage processMessage(int i, byte[] bArr);

    void registerSystemUICallback(ICredentialManagerServiceSystemUICallback iCredentialManagerServiceSystemUICallback);

    void removeEnforcedLockTypeNotification(int i);

    void resetNonMdmCertificates();

    Bundle resetUid(String str, int i);

    Bundle resetUser(String str, int i);

    Bundle saw(String str, int i);

    Bundle sawInternal(String str, int i, int i2);

    Bundle setAdminConfigureBundleForCs(int i, int i2, String str, Bundle bundle, int i3);

    Bundle setCertificateChain(String str, byte[] bArr, Bundle bundle);

    Bundle setCredentialStorageProperty(int i, String str, Bundle bundle, int i2);

    Bundle setKeyguardPinMaximumLength(String str, int i);

    Bundle setKeyguardPinMaximumRetryCount(String str, int i);

    Bundle setKeyguardPinMinimumLength(String str, int i);

    Bundle setState(String str, int i);

    void showEnforcedLockTypeNotification(int i, String str);

    ucmRetParcelable sign(String str, byte[] bArr, String str2);

    ucmRetParcelable unwrapDek(String str, byte[] bArr);

    void updateAgentList();

    Bundle verifyPin(int i, String str, String str2, Bundle bundle);

    Bundle verifyPuk(String str, String str2, String str3);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements IUcmService {
        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final Bundle APDUCommand(String str, byte[] bArr, Bundle bundle) {
            return null;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final Bundle changePin(String str, String str2, String str3, boolean z) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final boolean configureKeyguardSettings(int i, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final int configureODESettings(String str, Bundle bundle, String str2) {
            return 0;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final Bundle containsAlias(String str, int i) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final ApduMessage createSecureChannel(int i, Bundle bundle) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final ucmRetParcelable decrypt(String str, byte[] bArr, String str2, Bundle bundle) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final boolean delegateDeleteFile(String str) {
            return false;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final Bundle delegateGetTaProfile() {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final Bundle delegateLoadTa(boolean z) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final Bundle delegateProcessTACommand(Bundle bundle) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final byte[] delegateReadFile(String str) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final boolean delegateSaveFile(String str, byte[] bArr) {
            return false;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final Bundle delegateUnloadTa() {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final Bundle delegateWrapSessionKey(byte[] bArr) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final Bundle delete(String str) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final Bundle deleteCertificate(String str, int i) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final int destroySecureChannel() {
            return 0;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final ucmRetParcelable encrypt(String str, byte[] bArr, String str2, Bundle bundle) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final ucmRetParcelable generateDek(String str) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final Bundle generateKey(String str, String str2, int i, Bundle bundle) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final Bundle generateKeyPair(String str, String str2, int i, Bundle bundle) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final Bundle generateKeyPairInternal(String str, String str2, int i, Bundle bundle) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final Bundle generateKeyguardPassword(int i, String str, Bundle bundle) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final Bundle generateSecureRandom(String str, int i, byte[] bArr) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final ucmRetParcelable generateWrappedDek(String str) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final Bundle getAdminConfigureBundleFromCs(int i, int i2, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final Bundle getAgentInfo(String str) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final ucmRetParcelable getCertificateChain(String str) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final Bundle getCredentialStorageProperty(int i, String str, Bundle bundle, int i2) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final ucmRetParcelable getDek(String str) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final ucmRetParcelable getDekForVold(String str, byte[] bArr) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final ucmRetParcelable getDekForVoldInternalKey(String str, byte[] bArr) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final String getDetailErrorMessage(String str, int i) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final Bundle getInfo(String str) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final Bundle getKeyType(String str) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final Bundle getKeyguardPinCurrentRetryCount(String str) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final Bundle getKeyguardPinMaximumLength(String str) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final Bundle getKeyguardPinMaximumRetryCount(String str) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final Bundle getKeyguardPinMinimumLength(String str) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final String getKeyguardStorageForCurrentUser(int i) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final ucmRetParcelable getODEConfigurationForVold(String str) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final Bundle getODESettingsConfiguration() {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final ucmRetParcelable getOdeKey(String str, byte[] bArr) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final Bundle getStatus(String str) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final boolean grantKeyChainAccess(String str, int i) {
            return false;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final Bundle importKey(String str, Bundle bundle) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final Bundle importKeyPair(String str, byte[] bArr, byte[] bArr2, Bundle bundle) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final Bundle initKeyguardPin(String str, String str2, Bundle bundle) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final Bundle installCertificate(String str, byte[] bArr, byte[] bArr2, Bundle bundle) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final Bundle installCertificateIfSupported(String str, byte[] bArr, String str2, Bundle bundle) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final boolean isKeyChainGranted(String str, int i) {
            return false;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final boolean isUserCertificatesExistInUCS() {
            return false;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final Bundle[] listAllProviders() {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final Bundle[] listProviders() {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final ucmRetParcelable mac(String str, byte[] bArr, String str2) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final Bundle notifyChangeToPlugin(String str, int i, Bundle bundle) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final boolean notifyLicenseStatus(String str, String str2, int i) {
            return false;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final ucmRetParcelable notifyVoldComplete(String str, byte[] bArr) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final ApduMessage processMessage(int i, byte[] bArr) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final Bundle resetUid(String str, int i) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final Bundle resetUser(String str, int i) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final Bundle saw(String str, int i) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final Bundle sawInternal(String str, int i, int i2) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final Bundle setAdminConfigureBundleForCs(int i, int i2, String str, Bundle bundle, int i3) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final Bundle setCertificateChain(String str, byte[] bArr, Bundle bundle) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final Bundle setCredentialStorageProperty(int i, String str, Bundle bundle, int i2) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final Bundle setKeyguardPinMaximumLength(String str, int i) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final Bundle setKeyguardPinMaximumRetryCount(String str, int i) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final Bundle setKeyguardPinMinimumLength(String str, int i) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final Bundle setState(String str, int i) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final ucmRetParcelable sign(String str, byte[] bArr, String str2) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final ucmRetParcelable unwrapDek(String str, byte[] bArr) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final Bundle verifyPin(int i, String str, String str2, Bundle bundle) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final Bundle verifyPuk(String str, String str2, String str3) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final void resetNonMdmCertificates() {
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final void updateAgentList() {
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final void notifyPluginResult(Bundle bundle) {
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final void registerSystemUICallback(ICredentialManagerServiceSystemUICallback iCredentialManagerServiceSystemUICallback) {
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final void removeEnforcedLockTypeNotification(int i) {
        }

        @Override // com.samsung.android.knox.ucm.core.IUcmService
        public final void showEnforcedLockTypeNotification(int i, String str) {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements IUcmService {
        public static final int TRANSACTION_APDUCommand = 28;
        public static final int TRANSACTION_changePin = 26;
        public static final int TRANSACTION_configureKeyguardSettings = 31;
        public static final int TRANSACTION_configureODESettings = 32;
        public static final int TRANSACTION_containsAlias = 36;
        public static final int TRANSACTION_createSecureChannel = 44;
        public static final int TRANSACTION_decrypt = 3;
        public static final int TRANSACTION_delegateDeleteFile = 67;
        public static final int TRANSACTION_delegateGetTaProfile = 62;
        public static final int TRANSACTION_delegateLoadTa = 60;
        public static final int TRANSACTION_delegateProcessTACommand = 64;
        public static final int TRANSACTION_delegateReadFile = 65;
        public static final int TRANSACTION_delegateSaveFile = 66;
        public static final int TRANSACTION_delegateUnloadTa = 61;
        public static final int TRANSACTION_delegateWrapSessionKey = 63;
        public static final int TRANSACTION_delete = 12;
        public static final int TRANSACTION_deleteCertificate = 13;
        public static final int TRANSACTION_destroySecureChannel = 46;
        public static final int TRANSACTION_encrypt = 59;
        public static final int TRANSACTION_generateDek = 4;
        public static final int TRANSACTION_generateKey = 77;
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
        public static final int TRANSACTION_getDekForVold = 54;
        public static final int TRANSACTION_getDekForVoldInternalKey = 55;
        public static final int TRANSACTION_getDetailErrorMessage = 52;
        public static final int TRANSACTION_getInfo = 29;
        public static final int TRANSACTION_getKeyType = 79;
        public static final int TRANSACTION_getKeyguardPinCurrentRetryCount = 73;
        public static final int TRANSACTION_getKeyguardPinMaximumLength = 75;
        public static final int TRANSACTION_getKeyguardPinMaximumRetryCount = 72;
        public static final int TRANSACTION_getKeyguardPinMinimumLength = 74;
        public static final int TRANSACTION_getKeyguardStorageForCurrentUser = 48;
        public static final int TRANSACTION_getODEConfigurationForVold = 56;
        public static final int TRANSACTION_getODESettingsConfiguration = 33;
        public static final int TRANSACTION_getOdeKey = 57;
        public static final int TRANSACTION_getStatus = 39;
        public static final int TRANSACTION_grantKeyChainAccess = 37;
        public static final int TRANSACTION_importKey = 78;
        public static final int TRANSACTION_importKeyPair = 9;
        public static final int TRANSACTION_initKeyguardPin = 68;
        public static final int TRANSACTION_installCertificate = 10;
        public static final int TRANSACTION_installCertificateIfSupported = 80;
        public static final int TRANSACTION_isKeyChainGranted = 38;
        public static final int TRANSACTION_isUserCertificatesExistInUCS = 43;
        public static final int TRANSACTION_listAllProviders = 17;
        public static final int TRANSACTION_listProviders = 16;
        public static final int TRANSACTION_mac = 81;
        public static final int TRANSACTION_notifyChangeToPlugin = 41;
        public static final int TRANSACTION_notifyLicenseStatus = 40;
        public static final int TRANSACTION_notifyPluginResult = 76;
        public static final int TRANSACTION_notifyVoldComplete = 58;
        public static final int TRANSACTION_processMessage = 45;
        public static final int TRANSACTION_registerSystemUICallback = 51;
        public static final int TRANSACTION_removeEnforcedLockTypeNotification = 50;
        public static final int TRANSACTION_resetNonMdmCertificates = 42;
        public static final int TRANSACTION_resetUid = 35;
        public static final int TRANSACTION_resetUser = 34;
        public static final int TRANSACTION_saw = 8;
        public static final int TRANSACTION_sawInternal = 47;
        public static final int TRANSACTION_setAdminConfigureBundleForCs = 20;
        public static final int TRANSACTION_setCertificateChain = 11;
        public static final int TRANSACTION_setCredentialStorageProperty = 22;
        public static final int TRANSACTION_setKeyguardPinMaximumLength = 71;
        public static final int TRANSACTION_setKeyguardPinMaximumRetryCount = 69;
        public static final int TRANSACTION_setKeyguardPinMinimumLength = 70;
        public static final int TRANSACTION_setState = 27;
        public static final int TRANSACTION_showEnforcedLockTypeNotification = 49;
        public static final int TRANSACTION_sign = 1;
        public static final int TRANSACTION_unwrapDek = 7;
        public static final int TRANSACTION_updateAgentList = 53;
        public static final int TRANSACTION_verifyPin = 24;
        public static final int TRANSACTION_verifyPuk = 25;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements IUcmService {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public final Bundle APDUCommand(String str, byte[] bArr, Bundle bundle) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public final Bundle changePin(String str, String str2, String str3, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public final boolean configureKeyguardSettings(int i, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final int configureODESettings(String str, Bundle bundle, String str2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final Bundle containsAlias(String str, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final ApduMessage createSecureChannel(int i, Bundle bundle) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ApduMessage) obtain2.readTypedObject(ApduMessage.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public final ucmRetParcelable decrypt(String str, byte[] bArr, String str2, Bundle bundle) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final boolean delegateDeleteFile(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(67, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public final Bundle delegateGetTaProfile() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    this.mRemote.transact(62, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public final Bundle delegateLoadTa(boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public final Bundle delegateProcessTACommand(Bundle bundle) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public final byte[] delegateReadFile(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(65, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public final boolean delegateSaveFile(String str, byte[] bArr) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(66, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public final Bundle delegateUnloadTa() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public final Bundle delegateWrapSessionKey(byte[] bArr) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public final Bundle delete(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final Bundle deleteCertificate(String str, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final int destroySecureChannel() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public final ucmRetParcelable encrypt(String str, byte[] bArr, String str2, Bundle bundle) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ucmRetParcelable) obtain2.readTypedObject(ucmRetParcelable.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public final ucmRetParcelable generateDek(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final Bundle generateKey(String str, String str2, int i, Bundle bundle) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(77, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public final Bundle generateKeyPair(String str, String str2, int i, Bundle bundle) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final Bundle generateKeyPairInternal(String str, String str2, int i, Bundle bundle) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final Bundle generateKeyguardPassword(int i, String str, Bundle bundle) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final Bundle generateSecureRandom(String str, int i, byte[] bArr) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final ucmRetParcelable generateWrappedDek(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final Bundle getAdminConfigureBundleFromCs(int i, int i2, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final Bundle getAgentInfo(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final ucmRetParcelable getCertificateChain(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final Bundle getCredentialStorageProperty(int i, String str, Bundle bundle, int i2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final ucmRetParcelable getDek(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final ucmRetParcelable getDekForVold(String str, byte[] bArr) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final ucmRetParcelable getDekForVoldInternalKey(String str, byte[] bArr) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final String getDetailErrorMessage(String str, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public final Bundle getInfo(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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

            public final String getInterfaceDescriptor() {
                return IUcmService.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public final Bundle getKeyType(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(79, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public final Bundle getKeyguardPinCurrentRetryCount(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(73, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public final Bundle getKeyguardPinMaximumLength(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(75, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public final Bundle getKeyguardPinMaximumRetryCount(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(72, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public final Bundle getKeyguardPinMinimumLength(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(74, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public final String getKeyguardStorageForCurrentUser(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public final ucmRetParcelable getODEConfigurationForVold(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ucmRetParcelable) obtain2.readTypedObject(ucmRetParcelable.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public final Bundle getODESettingsConfiguration() {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final ucmRetParcelable getOdeKey(String str, byte[] bArr) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ucmRetParcelable) obtain2.readTypedObject(ucmRetParcelable.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public final Bundle getStatus(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final boolean grantKeyChainAccess(String str, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final Bundle importKey(String str, Bundle bundle) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(78, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public final Bundle importKeyPair(String str, byte[] bArr, byte[] bArr2, Bundle bundle) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final Bundle initKeyguardPin(String str, String str2, Bundle bundle) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(68, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public final Bundle installCertificate(String str, byte[] bArr, byte[] bArr2, Bundle bundle) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final Bundle installCertificateIfSupported(String str, byte[] bArr, String str2, Bundle bundle) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(80, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public final boolean isKeyChainGranted(String str, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final boolean isUserCertificatesExistInUCS() {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final Bundle[] listAllProviders() {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final Bundle[] listProviders() {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final ucmRetParcelable mac(String str, byte[] bArr, String str2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeString(str2);
                    this.mRemote.transact(81, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ucmRetParcelable) obtain2.readTypedObject(ucmRetParcelable.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public final Bundle notifyChangeToPlugin(String str, int i, Bundle bundle) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final boolean notifyLicenseStatus(String str, String str2, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final void notifyPluginResult(Bundle bundle) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(76, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public final ucmRetParcelable notifyVoldComplete(String str, byte[] bArr) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ucmRetParcelable) obtain2.readTypedObject(ucmRetParcelable.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public final ApduMessage processMessage(int i, byte[] bArr) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ApduMessage) obtain2.readTypedObject(ApduMessage.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public final void registerSystemUICallback(ICredentialManagerServiceSystemUICallback iCredentialManagerServiceSystemUICallback) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeStrongInterface(iCredentialManagerServiceSystemUICallback);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public final void removeEnforcedLockTypeNotification(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public final void resetNonMdmCertificates() {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final Bundle resetUid(String str, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final Bundle resetUser(String str, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final Bundle saw(String str, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final Bundle sawInternal(String str, int i, int i2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public final Bundle setAdminConfigureBundleForCs(int i, int i2, String str, Bundle bundle, int i3) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final Bundle setCertificateChain(String str, byte[] bArr, Bundle bundle) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final Bundle setCredentialStorageProperty(int i, String str, Bundle bundle, int i2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final Bundle setKeyguardPinMaximumLength(String str, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(71, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public final Bundle setKeyguardPinMaximumRetryCount(String str, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(69, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public final Bundle setKeyguardPinMinimumLength(String str, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(70, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public final Bundle setState(String str, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final void showEnforcedLockTypeNotification(int i, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public final ucmRetParcelable sign(String str, byte[] bArr, String str2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final ucmRetParcelable unwrapDek(String str, byte[] bArr) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final void updateAgentList() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmService.DESCRIPTOR);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.core.IUcmService
            public final Bundle verifyPin(int i, String str, String str2, Bundle bundle) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final Bundle verifyPuk(String str, String str2, String str3) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
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
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    Bundle changePin = changePin(readString31, readString32, readString33, readBoolean);
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
                    int readInt26 = parcel.readInt();
                    Bundle bundle15 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    ApduMessage createSecureChannel = createSecureChannel(readInt26, bundle15);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(createSecureChannel, 1);
                    return true;
                case 45:
                    int readInt27 = parcel.readInt();
                    byte[] createByteArray11 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    ApduMessage processMessage = processMessage(readInt27, createByteArray11);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(processMessage, 1);
                    return true;
                case 46:
                    int destroySecureChannel = destroySecureChannel();
                    parcel2.writeNoException();
                    parcel2.writeInt(destroySecureChannel);
                    return true;
                case 47:
                    String readString50 = parcel.readString();
                    int readInt28 = parcel.readInt();
                    int readInt29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle sawInternal = sawInternal(readString50, readInt28, readInt29);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(sawInternal, 1);
                    return true;
                case 48:
                    int readInt30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String keyguardStorageForCurrentUser = getKeyguardStorageForCurrentUser(readInt30);
                    parcel2.writeNoException();
                    parcel2.writeString(keyguardStorageForCurrentUser);
                    return true;
                case 49:
                    int readInt31 = parcel.readInt();
                    String readString51 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    showEnforcedLockTypeNotification(readInt31, readString51);
                    parcel2.writeNoException();
                    return true;
                case 50:
                    int readInt32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeEnforcedLockTypeNotification(readInt32);
                    parcel2.writeNoException();
                    return true;
                case 51:
                    ICredentialManagerServiceSystemUICallback asInterface = ICredentialManagerServiceSystemUICallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerSystemUICallback(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 52:
                    String readString52 = parcel.readString();
                    int readInt33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String detailErrorMessage = getDetailErrorMessage(readString52, readInt33);
                    parcel2.writeNoException();
                    parcel2.writeString(detailErrorMessage);
                    return true;
                case 53:
                    updateAgentList();
                    parcel2.writeNoException();
                    return true;
                case 54:
                    String readString53 = parcel.readString();
                    byte[] createByteArray12 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    ucmRetParcelable dekForVold = getDekForVold(readString53, createByteArray12);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(dekForVold, 1);
                    return true;
                case 55:
                    String readString54 = parcel.readString();
                    byte[] createByteArray13 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    ucmRetParcelable dekForVoldInternalKey = getDekForVoldInternalKey(readString54, createByteArray13);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(dekForVoldInternalKey, 1);
                    return true;
                case 56:
                    String readString55 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ucmRetParcelable oDEConfigurationForVold = getODEConfigurationForVold(readString55);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(oDEConfigurationForVold, 1);
                    return true;
                case 57:
                    String readString56 = parcel.readString();
                    byte[] createByteArray14 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    ucmRetParcelable odeKey = getOdeKey(readString56, createByteArray14);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(odeKey, 1);
                    return true;
                case 58:
                    String readString57 = parcel.readString();
                    byte[] createByteArray15 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    ucmRetParcelable notifyVoldComplete = notifyVoldComplete(readString57, createByteArray15);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(notifyVoldComplete, 1);
                    return true;
                case 59:
                    String readString58 = parcel.readString();
                    byte[] createByteArray16 = parcel.createByteArray();
                    String readString59 = parcel.readString();
                    Bundle bundle16 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    ucmRetParcelable encrypt = encrypt(readString58, createByteArray16, readString59, bundle16);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(encrypt, 1);
                    return true;
                case 60:
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    Bundle delegateLoadTa = delegateLoadTa(readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(delegateLoadTa, 1);
                    return true;
                case 61:
                    Bundle delegateUnloadTa = delegateUnloadTa();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(delegateUnloadTa, 1);
                    return true;
                case 62:
                    Bundle delegateGetTaProfile = delegateGetTaProfile();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(delegateGetTaProfile, 1);
                    return true;
                case 63:
                    byte[] createByteArray17 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    Bundle delegateWrapSessionKey = delegateWrapSessionKey(createByteArray17);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(delegateWrapSessionKey, 1);
                    return true;
                case 64:
                    Bundle bundle17 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle delegateProcessTACommand = delegateProcessTACommand(bundle17);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(delegateProcessTACommand, 1);
                    return true;
                case 65:
                    String readString60 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    byte[] delegateReadFile = delegateReadFile(readString60);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(delegateReadFile);
                    return true;
                case 66:
                    String readString61 = parcel.readString();
                    byte[] createByteArray18 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    boolean delegateSaveFile = delegateSaveFile(readString61, createByteArray18);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(delegateSaveFile);
                    return true;
                case 67:
                    String readString62 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean delegateDeleteFile = delegateDeleteFile(readString62);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(delegateDeleteFile);
                    return true;
                case 68:
                    String readString63 = parcel.readString();
                    String readString64 = parcel.readString();
                    Bundle bundle18 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle initKeyguardPin = initKeyguardPin(readString63, readString64, bundle18);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(initKeyguardPin, 1);
                    return true;
                case 69:
                    String readString65 = parcel.readString();
                    int readInt34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle keyguardPinMaximumRetryCount = setKeyguardPinMaximumRetryCount(readString65, readInt34);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyguardPinMaximumRetryCount, 1);
                    return true;
                case 70:
                    String readString66 = parcel.readString();
                    int readInt35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle keyguardPinMinimumLength = setKeyguardPinMinimumLength(readString66, readInt35);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyguardPinMinimumLength, 1);
                    return true;
                case 71:
                    String readString67 = parcel.readString();
                    int readInt36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle keyguardPinMaximumLength = setKeyguardPinMaximumLength(readString67, readInt36);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyguardPinMaximumLength, 1);
                    return true;
                case 72:
                    String readString68 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Bundle keyguardPinMaximumRetryCount2 = getKeyguardPinMaximumRetryCount(readString68);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyguardPinMaximumRetryCount2, 1);
                    return true;
                case 73:
                    String readString69 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Bundle keyguardPinCurrentRetryCount = getKeyguardPinCurrentRetryCount(readString69);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyguardPinCurrentRetryCount, 1);
                    return true;
                case 74:
                    String readString70 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Bundle keyguardPinMinimumLength2 = getKeyguardPinMinimumLength(readString70);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyguardPinMinimumLength2, 1);
                    return true;
                case 75:
                    String readString71 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Bundle keyguardPinMaximumLength2 = getKeyguardPinMaximumLength(readString71);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyguardPinMaximumLength2, 1);
                    return true;
                case 76:
                    Bundle bundle19 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyPluginResult(bundle19);
                    parcel2.writeNoException();
                    return true;
                case 77:
                    String readString72 = parcel.readString();
                    String readString73 = parcel.readString();
                    int readInt37 = parcel.readInt();
                    Bundle bundle20 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle generateKey = generateKey(readString72, readString73, readInt37, bundle20);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(generateKey, 1);
                    return true;
                case 78:
                    String readString74 = parcel.readString();
                    Bundle bundle21 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle importKey = importKey(readString74, bundle21);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(importKey, 1);
                    return true;
                case 79:
                    String readString75 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Bundle keyType = getKeyType(readString75);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyType, 1);
                    return true;
                case 80:
                    String readString76 = parcel.readString();
                    byte[] createByteArray19 = parcel.createByteArray();
                    String readString77 = parcel.readString();
                    Bundle bundle22 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle installCertificateIfSupported = installCertificateIfSupported(readString76, createByteArray19, readString77, bundle22);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(installCertificateIfSupported, 1);
                    return true;
                case 81:
                    String readString78 = parcel.readString();
                    byte[] createByteArray20 = parcel.createByteArray();
                    String readString79 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ucmRetParcelable mac = mac(readString78, createByteArray20, readString79);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(mac, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }
}
