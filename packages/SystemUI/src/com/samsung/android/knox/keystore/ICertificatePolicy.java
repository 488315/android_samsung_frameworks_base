package com.samsung.android.knox.keystore;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.knox.ContextInfo;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface ICertificatePolicy extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.keystore.ICertificatePolicy";

    boolean addPermissionApplicationPrivateKey(ContextInfo contextInfo, PermissionApplicationPrivateKey permissionApplicationPrivateKey) throws RemoteException;

    boolean addTrustedCaCertificateList(ContextInfo contextInfo, List<CertificateInfo> list) throws RemoteException;

    boolean addUntrustedCertificateList(ContextInfo contextInfo, List<CertificateInfo> list) throws RemoteException;

    boolean allowUserRemoveCertificates(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean clearPermissionApplicationPrivateKey(ContextInfo contextInfo) throws RemoteException;

    boolean clearTrustedCaCertificateList(ContextInfo contextInfo) throws RemoteException;

    boolean clearUntrustedCertificateList(ContextInfo contextInfo) throws RemoteException;

    boolean enableCertificateFailureNotification(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean enableCertificateValidationAtInstall(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean enableSignatureIdentityInformation(ContextInfo contextInfo, boolean z) throws RemoteException;

    List getIdentitiesFromSignatures(ContextInfo contextInfo, List<String> list) throws RemoteException;

    List<PermissionApplicationPrivateKey> getListPermissionApplicationPrivateKey(ContextInfo contextInfo) throws RemoteException;

    List<CertificateControlInfo> getTrustedCaCertificateList(ContextInfo contextInfo) throws RemoteException;

    List<CertificateControlInfo> getUntrustedCertificateList(ContextInfo contextInfo) throws RemoteException;

    boolean isCaCertificateDisabledAsUser(String str, int i) throws RemoteException;

    boolean isCaCertificateTrustedAsUser(CertificateInfo certificateInfo, boolean z, int i) throws RemoteException;

    boolean isCertificateFailureNotificationEnabled(ContextInfo contextInfo) throws RemoteException;

    boolean isCertificateTrustedUntrustedEnabledAsUser(int i) throws RemoteException;

    boolean isCertificateValidationAtInstallEnabled(ContextInfo contextInfo) throws RemoteException;

    boolean isCertificateValidationAtInstallEnabledAsUser(int i) throws RemoteException;

    String isPrivateKeyApplicationPermitted(ContextInfo contextInfo, String str, String str2, int i, List<String> list) throws RemoteException;

    String isPrivateKeyApplicationPermittedAsUser(String str, String str2, int i, List<String> list, int i2) throws RemoteException;

    boolean isSignatureIdentityInformationEnabled(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean isUserRemoveCertificatesAllowed(ContextInfo contextInfo) throws RemoteException;

    boolean isUserRemoveCertificatesAllowedAsUser(int i) throws RemoteException;

    void notifyCertificateFailure(String str, String str2, boolean z) throws RemoteException;

    void notifyCertificateFailureAsUser(String str, String str2, boolean z, int i) throws RemoteException;

    void notifyCertificateRemovedAsUser(String str, int i) throws RemoteException;

    void notifyUserKeystoreUnlocked(int i) throws RemoteException;

    boolean removePermissionApplicationPrivateKey(ContextInfo contextInfo, PermissionApplicationPrivateKey permissionApplicationPrivateKey) throws RemoteException;

    boolean removeTrustedCaCertificateList(ContextInfo contextInfo, List<CertificateInfo> list) throws RemoteException;

    boolean removeUntrustedCertificateList(ContextInfo contextInfo, List<CertificateInfo> list) throws RemoteException;

    int validateCertificateAtInstall(CertificateInfo certificateInfo) throws RemoteException;

    int validateCertificateAtInstallAsUser(CertificateInfo certificateInfo, int i) throws RemoteException;

    int validateChainAtInstall(List<CertificateInfo> list) throws RemoteException;

    int validateChainAtInstallAsUser(List<CertificateInfo> list, int i) throws RemoteException;

    public abstract class Stub extends Binder implements ICertificatePolicy {
        public static final int TRANSACTION_addPermissionApplicationPrivateKey = 27;
        public static final int TRANSACTION_addTrustedCaCertificateList = 1;
        public static final int TRANSACTION_addUntrustedCertificateList = 7;
        public static final int TRANSACTION_allowUserRemoveCertificates = 24;
        public static final int TRANSACTION_clearPermissionApplicationPrivateKey = 29;
        public static final int TRANSACTION_clearTrustedCaCertificateList = 4;
        public static final int TRANSACTION_clearUntrustedCertificateList = 10;
        public static final int TRANSACTION_enableCertificateFailureNotification = 12;
        public static final int TRANSACTION_enableCertificateValidationAtInstall = 16;
        public static final int TRANSACTION_enableSignatureIdentityInformation = 35;
        public static final int TRANSACTION_getIdentitiesFromSignatures = 11;
        public static final int TRANSACTION_getListPermissionApplicationPrivateKey = 30;
        public static final int TRANSACTION_getTrustedCaCertificateList = 2;
        public static final int TRANSACTION_getUntrustedCertificateList = 9;
        public static final int TRANSACTION_isCaCertificateDisabledAsUser = 6;
        public static final int TRANSACTION_isCaCertificateTrustedAsUser = 3;
        public static final int TRANSACTION_isCertificateFailureNotificationEnabled = 13;
        public static final int TRANSACTION_isCertificateTrustedUntrustedEnabledAsUser = 34;
        public static final int TRANSACTION_isCertificateValidationAtInstallEnabled = 17;
        public static final int TRANSACTION_isCertificateValidationAtInstallEnabledAsUser = 18;
        public static final int TRANSACTION_isPrivateKeyApplicationPermitted = 31;
        public static final int TRANSACTION_isPrivateKeyApplicationPermittedAsUser = 32;
        public static final int TRANSACTION_isSignatureIdentityInformationEnabled = 36;
        public static final int TRANSACTION_isUserRemoveCertificatesAllowed = 25;
        public static final int TRANSACTION_isUserRemoveCertificatesAllowedAsUser = 26;
        public static final int TRANSACTION_notifyCertificateFailure = 14;
        public static final int TRANSACTION_notifyCertificateFailureAsUser = 15;
        public static final int TRANSACTION_notifyCertificateRemovedAsUser = 23;
        public static final int TRANSACTION_notifyUserKeystoreUnlocked = 33;
        public static final int TRANSACTION_removePermissionApplicationPrivateKey = 28;
        public static final int TRANSACTION_removeTrustedCaCertificateList = 5;
        public static final int TRANSACTION_removeUntrustedCertificateList = 8;
        public static final int TRANSACTION_validateCertificateAtInstall = 19;
        public static final int TRANSACTION_validateCertificateAtInstallAsUser = 20;
        public static final int TRANSACTION_validateChainAtInstall = 21;
        public static final int TRANSACTION_validateChainAtInstallAsUser = 22;

        class Proxy implements ICertificatePolicy {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public boolean addPermissionApplicationPrivateKey(ContextInfo contextInfo, PermissionApplicationPrivateKey permissionApplicationPrivateKey) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeTypedObject(permissionApplicationPrivateKey, 0);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public boolean addTrustedCaCertificateList(ContextInfo contextInfo, List<CertificateInfo> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public boolean addUntrustedCertificateList(ContextInfo contextInfo, List<CertificateInfo> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public boolean allowUserRemoveCertificates(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public boolean clearPermissionApplicationPrivateKey(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public boolean clearTrustedCaCertificateList(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public boolean clearUntrustedCertificateList(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public boolean enableCertificateFailureNotification(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public boolean enableCertificateValidationAtInstall(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public boolean enableSignatureIdentityInformation(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public List getIdentitiesFromSignatures(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readArrayList(getClass().getClassLoader());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return ICertificatePolicy.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public List<PermissionApplicationPrivateKey> getListPermissionApplicationPrivateKey(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(PermissionApplicationPrivateKey.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public List<CertificateControlInfo> getTrustedCaCertificateList(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(CertificateControlInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public List<CertificateControlInfo> getUntrustedCertificateList(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(CertificateControlInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public boolean isCaCertificateDisabledAsUser(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public boolean isCaCertificateTrustedAsUser(CertificateInfo certificateInfo, boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(certificateInfo, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public boolean isCertificateFailureNotificationEnabled(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public boolean isCertificateTrustedUntrustedEnabledAsUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public boolean isCertificateValidationAtInstallEnabled(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public boolean isCertificateValidationAtInstallEnabledAsUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public String isPrivateKeyApplicationPermitted(ContextInfo contextInfo, String str, String str2, int i, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public String isPrivateKeyApplicationPermittedAsUser(String str, String str2, int i, List<String> list, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public boolean isSignatureIdentityInformationEnabled(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public boolean isUserRemoveCertificatesAllowed(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public boolean isUserRemoveCertificatesAllowedAsUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public void notifyCertificateFailure(String str, String str2, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public void notifyCertificateFailureAsUser(String str, String str2, boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public void notifyCertificateRemovedAsUser(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public void notifyUserKeystoreUnlocked(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public boolean removePermissionApplicationPrivateKey(ContextInfo contextInfo, PermissionApplicationPrivateKey permissionApplicationPrivateKey) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeTypedObject(permissionApplicationPrivateKey, 0);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public boolean removeTrustedCaCertificateList(ContextInfo contextInfo, List<CertificateInfo> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public boolean removeUntrustedCertificateList(ContextInfo contextInfo, List<CertificateInfo> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public int validateCertificateAtInstall(CertificateInfo certificateInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(certificateInfo, 0);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public int validateCertificateAtInstallAsUser(CertificateInfo certificateInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(certificateInfo, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public int validateChainAtInstall(List<CertificateInfo> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public int validateChainAtInstallAsUser(List<CertificateInfo> list, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, ICertificatePolicy.DESCRIPTOR);
        }

        public static ICertificatePolicy asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ICertificatePolicy.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof ICertificatePolicy)) ? new Proxy(iBinder) : (ICertificatePolicy) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ICertificatePolicy.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ICertificatePolicy.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ContextInfo contextInfo = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(CertificateInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zAddTrustedCaCertificateList = addTrustedCaCertificateList(contextInfo, arrayListCreateTypedArrayList);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddTrustedCaCertificateList);
                    return true;
                case 2:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<CertificateControlInfo> trustedCaCertificateList = getTrustedCaCertificateList(contextInfo2);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(trustedCaCertificateList, 1);
                    return true;
                case 3:
                    CertificateInfo certificateInfo = (CertificateInfo) parcel.readTypedObject(CertificateInfo.CREATOR);
                    boolean z = parcel.readBoolean();
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsCaCertificateTrustedAsUser = isCaCertificateTrustedAsUser(certificateInfo, z, i3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCaCertificateTrustedAsUser);
                    return true;
                case 4:
                    ContextInfo contextInfo3 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zClearTrustedCaCertificateList = clearTrustedCaCertificateList(contextInfo3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zClearTrustedCaCertificateList);
                    return true;
                case 5:
                    ContextInfo contextInfo4 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList arrayListCreateTypedArrayList2 = parcel.createTypedArrayList(CertificateInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zRemoveTrustedCaCertificateList = removeTrustedCaCertificateList(contextInfo4, arrayListCreateTypedArrayList2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveTrustedCaCertificateList);
                    return true;
                case 6:
                    String string = parcel.readString();
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsCaCertificateDisabledAsUser = isCaCertificateDisabledAsUser(string, i4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCaCertificateDisabledAsUser);
                    return true;
                case 7:
                    ContextInfo contextInfo5 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList arrayListCreateTypedArrayList3 = parcel.createTypedArrayList(CertificateInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zAddUntrustedCertificateList = addUntrustedCertificateList(contextInfo5, arrayListCreateTypedArrayList3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddUntrustedCertificateList);
                    return true;
                case 8:
                    ContextInfo contextInfo6 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList arrayListCreateTypedArrayList4 = parcel.createTypedArrayList(CertificateInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zRemoveUntrustedCertificateList = removeUntrustedCertificateList(contextInfo6, arrayListCreateTypedArrayList4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveUntrustedCertificateList);
                    return true;
                case 9:
                    ContextInfo contextInfo7 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<CertificateControlInfo> untrustedCertificateList = getUntrustedCertificateList(contextInfo7);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(untrustedCertificateList, 1);
                    return true;
                case 10:
                    ContextInfo contextInfo8 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zClearUntrustedCertificateList = clearUntrustedCertificateList(contextInfo8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zClearUntrustedCertificateList);
                    return true;
                case 11:
                    ContextInfo contextInfo9 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    List identitiesFromSignatures = getIdentitiesFromSignatures(contextInfo9, arrayListCreateStringArrayList);
                    parcel2.writeNoException();
                    parcel2.writeList(identitiesFromSignatures);
                    return true;
                case 12:
                    ContextInfo contextInfo10 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zEnableCertificateFailureNotification = enableCertificateFailureNotification(contextInfo10, z2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zEnableCertificateFailureNotification);
                    return true;
                case 13:
                    ContextInfo contextInfo11 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsCertificateFailureNotificationEnabled = isCertificateFailureNotificationEnabled(contextInfo11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCertificateFailureNotificationEnabled);
                    return true;
                case 14:
                    String string2 = parcel.readString();
                    String string3 = parcel.readString();
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    notifyCertificateFailure(string2, string3, z3);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    String string4 = parcel.readString();
                    String string5 = parcel.readString();
                    boolean z4 = parcel.readBoolean();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyCertificateFailureAsUser(string4, string5, z4, i5);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    ContextInfo contextInfo12 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zEnableCertificateValidationAtInstall = enableCertificateValidationAtInstall(contextInfo12, z5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zEnableCertificateValidationAtInstall);
                    return true;
                case 17:
                    ContextInfo contextInfo13 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsCertificateValidationAtInstallEnabled = isCertificateValidationAtInstallEnabled(contextInfo13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCertificateValidationAtInstallEnabled);
                    return true;
                case 18:
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsCertificateValidationAtInstallEnabledAsUser = isCertificateValidationAtInstallEnabledAsUser(i6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCertificateValidationAtInstallEnabledAsUser);
                    return true;
                case 19:
                    CertificateInfo certificateInfo2 = (CertificateInfo) parcel.readTypedObject(CertificateInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iValidateCertificateAtInstall = validateCertificateAtInstall(certificateInfo2);
                    parcel2.writeNoException();
                    parcel2.writeInt(iValidateCertificateAtInstall);
                    return true;
                case 20:
                    CertificateInfo certificateInfo3 = (CertificateInfo) parcel.readTypedObject(CertificateInfo.CREATOR);
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iValidateCertificateAtInstallAsUser = validateCertificateAtInstallAsUser(certificateInfo3, i7);
                    parcel2.writeNoException();
                    parcel2.writeInt(iValidateCertificateAtInstallAsUser);
                    return true;
                case 21:
                    ArrayList arrayListCreateTypedArrayList5 = parcel.createTypedArrayList(CertificateInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iValidateChainAtInstall = validateChainAtInstall(arrayListCreateTypedArrayList5);
                    parcel2.writeNoException();
                    parcel2.writeInt(iValidateChainAtInstall);
                    return true;
                case 22:
                    ArrayList arrayListCreateTypedArrayList6 = parcel.createTypedArrayList(CertificateInfo.CREATOR);
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iValidateChainAtInstallAsUser = validateChainAtInstallAsUser(arrayListCreateTypedArrayList6, i8);
                    parcel2.writeNoException();
                    parcel2.writeInt(iValidateChainAtInstallAsUser);
                    return true;
                case 23:
                    String string6 = parcel.readString();
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyCertificateRemovedAsUser(string6, i9);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    ContextInfo contextInfo14 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowUserRemoveCertificates = allowUserRemoveCertificates(contextInfo14, z6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowUserRemoveCertificates);
                    return true;
                case 25:
                    ContextInfo contextInfo15 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsUserRemoveCertificatesAllowed = isUserRemoveCertificatesAllowed(contextInfo15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsUserRemoveCertificatesAllowed);
                    return true;
                case 26:
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsUserRemoveCertificatesAllowedAsUser = isUserRemoveCertificatesAllowedAsUser(i10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsUserRemoveCertificatesAllowedAsUser);
                    return true;
                case 27:
                    ContextInfo contextInfo16 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    PermissionApplicationPrivateKey permissionApplicationPrivateKey = (PermissionApplicationPrivateKey) parcel.readTypedObject(PermissionApplicationPrivateKey.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zAddPermissionApplicationPrivateKey = addPermissionApplicationPrivateKey(contextInfo16, permissionApplicationPrivateKey);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddPermissionApplicationPrivateKey);
                    return true;
                case 28:
                    ContextInfo contextInfo17 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    PermissionApplicationPrivateKey permissionApplicationPrivateKey2 = (PermissionApplicationPrivateKey) parcel.readTypedObject(PermissionApplicationPrivateKey.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zRemovePermissionApplicationPrivateKey = removePermissionApplicationPrivateKey(contextInfo17, permissionApplicationPrivateKey2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemovePermissionApplicationPrivateKey);
                    return true;
                case 29:
                    ContextInfo contextInfo18 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zClearPermissionApplicationPrivateKey = clearPermissionApplicationPrivateKey(contextInfo18);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zClearPermissionApplicationPrivateKey);
                    return true;
                case 30:
                    ContextInfo contextInfo19 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<PermissionApplicationPrivateKey> listPermissionApplicationPrivateKey = getListPermissionApplicationPrivateKey(contextInfo19);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(listPermissionApplicationPrivateKey, 1);
                    return true;
                case 31:
                    ContextInfo contextInfo20 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string7 = parcel.readString();
                    String string8 = parcel.readString();
                    int i11 = parcel.readInt();
                    ArrayList<String> arrayListCreateStringArrayList2 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    String strIsPrivateKeyApplicationPermitted = isPrivateKeyApplicationPermitted(contextInfo20, string7, string8, i11, arrayListCreateStringArrayList2);
                    parcel2.writeNoException();
                    parcel2.writeString(strIsPrivateKeyApplicationPermitted);
                    return true;
                case 32:
                    String string9 = parcel.readString();
                    String string10 = parcel.readString();
                    int i12 = parcel.readInt();
                    ArrayList<String> arrayListCreateStringArrayList3 = parcel.createStringArrayList();
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String strIsPrivateKeyApplicationPermittedAsUser = isPrivateKeyApplicationPermittedAsUser(string9, string10, i12, arrayListCreateStringArrayList3, i13);
                    parcel2.writeNoException();
                    parcel2.writeString(strIsPrivateKeyApplicationPermittedAsUser);
                    return true;
                case 33:
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyUserKeystoreUnlocked(i14);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsCertificateTrustedUntrustedEnabledAsUser = isCertificateTrustedUntrustedEnabledAsUser(i15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCertificateTrustedUntrustedEnabledAsUser);
                    return true;
                case 35:
                    ContextInfo contextInfo21 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zEnableSignatureIdentityInformation = enableSignatureIdentityInformation(contextInfo21, z7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zEnableSignatureIdentityInformation);
                    return true;
                case 36:
                    ContextInfo contextInfo22 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsSignatureIdentityInformationEnabled = isSignatureIdentityInformationEnabled(contextInfo22, z8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSignatureIdentityInformationEnabled);
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

    public class Default implements ICertificatePolicy {
        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public boolean addPermissionApplicationPrivateKey(ContextInfo contextInfo, PermissionApplicationPrivateKey permissionApplicationPrivateKey) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public boolean addTrustedCaCertificateList(ContextInfo contextInfo, List<CertificateInfo> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public boolean addUntrustedCertificateList(ContextInfo contextInfo, List<CertificateInfo> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public boolean allowUserRemoveCertificates(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public boolean clearPermissionApplicationPrivateKey(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public boolean clearTrustedCaCertificateList(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public boolean clearUntrustedCertificateList(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public boolean enableCertificateFailureNotification(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public boolean enableCertificateValidationAtInstall(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public boolean enableSignatureIdentityInformation(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public List getIdentitiesFromSignatures(ContextInfo contextInfo, List<String> list) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public List<PermissionApplicationPrivateKey> getListPermissionApplicationPrivateKey(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public List<CertificateControlInfo> getTrustedCaCertificateList(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public List<CertificateControlInfo> getUntrustedCertificateList(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public boolean isCaCertificateDisabledAsUser(String str, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public boolean isCaCertificateTrustedAsUser(CertificateInfo certificateInfo, boolean z, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public boolean isCertificateFailureNotificationEnabled(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public boolean isCertificateTrustedUntrustedEnabledAsUser(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public boolean isCertificateValidationAtInstallEnabled(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public boolean isCertificateValidationAtInstallEnabledAsUser(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public String isPrivateKeyApplicationPermitted(ContextInfo contextInfo, String str, String str2, int i, List<String> list) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public String isPrivateKeyApplicationPermittedAsUser(String str, String str2, int i, List<String> list, int i2) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public boolean isSignatureIdentityInformationEnabled(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public boolean isUserRemoveCertificatesAllowed(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public boolean isUserRemoveCertificatesAllowedAsUser(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public boolean removePermissionApplicationPrivateKey(ContextInfo contextInfo, PermissionApplicationPrivateKey permissionApplicationPrivateKey) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public boolean removeTrustedCaCertificateList(ContextInfo contextInfo, List<CertificateInfo> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public boolean removeUntrustedCertificateList(ContextInfo contextInfo, List<CertificateInfo> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public int validateCertificateAtInstall(CertificateInfo certificateInfo) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public int validateCertificateAtInstallAsUser(CertificateInfo certificateInfo, int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public int validateChainAtInstall(List<CertificateInfo> list) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public int validateChainAtInstallAsUser(List<CertificateInfo> list, int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public void notifyUserKeystoreUnlocked(int i) throws RemoteException {
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public void notifyCertificateRemovedAsUser(String str, int i) throws RemoteException {
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public void notifyCertificateFailure(String str, String str2, boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public void notifyCertificateFailureAsUser(String str, String str2, boolean z, int i) throws RemoteException {
        }
    }
}
