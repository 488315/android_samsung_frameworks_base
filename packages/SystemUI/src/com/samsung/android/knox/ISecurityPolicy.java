package com.samsung.android.knox;

import android.content.ComponentName;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.knox.keystore.CertificateInfo;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface ISecurityPolicy extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.ISecurityPolicy";

    boolean addPackagesToCertificateWhiteList(ContextInfo contextInfo, List<AppIdentity> list) throws RemoteException;

    boolean deleteCertificateFromKeystore(ContextInfo contextInfo, CertificateInfo certificateInfo, int i) throws RemoteException;

    boolean deleteCertificateFromUserKeystore(ContextInfo contextInfo, CertificateInfo certificateInfo, int i) throws RemoteException;

    boolean enableRebootBanner(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean enableRebootBannerWithText(ContextInfo contextInfo, boolean z, String str) throws RemoteException;

    String[] formatSelective(ContextInfo contextInfo, String[] strArr, String[] strArr2) throws RemoteException;

    List<CertificateInfo> getCertificatesFromKeystore(ContextInfo contextInfo, int i, int i2) throws RemoteException;

    List<CertificateInfo> getCertificatesFromUserKeystore(ContextInfo contextInfo, int i) throws RemoteException;

    String getDeviceLastAccessDate(ContextInfo contextInfo) throws RemoteException;

    List<AppIdentity> getPackagesFromCertificateWhiteList(ContextInfo contextInfo) throws RemoteException;

    String getRebootBannerText(ContextInfo contextInfo) throws RemoteException;

    boolean getRequireDeviceEncryption(ContextInfo contextInfo, ComponentName componentName) throws RemoteException;

    boolean getRequireStorageCardEncryption(ContextInfo contextInfo, ComponentName componentName) throws RemoteException;

    List<CertificateInfo> getSystemCertificates(ContextInfo contextInfo) throws RemoteException;

    int installCertificateToKeystore(ContextInfo contextInfo, String str, byte[] bArr, String str2, String str3, int i, boolean z) throws RemoteException;

    boolean installCertificateToUserKeystore(ContextInfo contextInfo, String str, byte[] bArr, String str2, String str3, int i) throws RemoteException;

    void installCertificateWithType(ContextInfo contextInfo, String str, byte[] bArr) throws RemoteException;

    void installCertificatesFromSdCard(ContextInfo contextInfo) throws RemoteException;

    boolean isDodBannerVisible(ContextInfo contextInfo) throws RemoteException;

    boolean isDodBannerVisibleAsUser(int i) throws RemoteException;

    boolean isExternalStorageEncrypted(ContextInfo contextInfo) throws RemoteException;

    boolean isInternalStorageEncrypted(ContextInfo contextInfo) throws RemoteException;

    boolean isRebootBannerEnabled(ContextInfo contextInfo) throws RemoteException;

    void onKeyguardLaunched() throws RemoteException;

    boolean removeAccountsByType(ContextInfo contextInfo, String str) throws RemoteException;

    boolean removePackagesFromCertificateWhiteList(ContextInfo contextInfo, List<AppIdentity> list) throws RemoteException;

    boolean resetCredentialStorage(ContextInfo contextInfo) throws RemoteException;

    boolean setDeviceLastAccessDate(ContextInfo contextInfo, String str) throws RemoteException;

    boolean setDodBannerVisibleStatus(ContextInfo contextInfo, boolean z) throws RemoteException;

    void setExternalStorageEncryption(ContextInfo contextInfo, boolean z) throws RemoteException;

    void setInternalStorageEncryption(ContextInfo contextInfo, boolean z) throws RemoteException;

    void setRequireDeviceEncryption(ContextInfo contextInfo, ComponentName componentName, boolean z) throws RemoteException;

    void setRequireStorageCardEncryption(ContextInfo contextInfo, ComponentName componentName, boolean z) throws RemoteException;

    boolean wipeDevice(ContextInfo contextInfo, int i) throws RemoteException;

    public class Default implements ISecurityPolicy {
        @Override // com.samsung.android.knox.ISecurityPolicy
        public boolean addPackagesToCertificateWhiteList(ContextInfo contextInfo, List<AppIdentity> list) throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public boolean deleteCertificateFromKeystore(ContextInfo contextInfo, CertificateInfo certificateInfo, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public boolean deleteCertificateFromUserKeystore(ContextInfo contextInfo, CertificateInfo certificateInfo, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public boolean enableRebootBanner(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public boolean enableRebootBannerWithText(ContextInfo contextInfo, boolean z, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public String[] formatSelective(ContextInfo contextInfo, String[] strArr, String[] strArr2) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public List<CertificateInfo> getCertificatesFromKeystore(ContextInfo contextInfo, int i, int i2) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public List<CertificateInfo> getCertificatesFromUserKeystore(ContextInfo contextInfo, int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public String getDeviceLastAccessDate(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public List<AppIdentity> getPackagesFromCertificateWhiteList(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public String getRebootBannerText(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public boolean getRequireDeviceEncryption(ContextInfo contextInfo, ComponentName componentName) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public boolean getRequireStorageCardEncryption(ContextInfo contextInfo, ComponentName componentName) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public List<CertificateInfo> getSystemCertificates(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public int installCertificateToKeystore(ContextInfo contextInfo, String str, byte[] bArr, String str2, String str3, int i, boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public boolean installCertificateToUserKeystore(ContextInfo contextInfo, String str, byte[] bArr, String str2, String str3, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public boolean isDodBannerVisible(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public boolean isDodBannerVisibleAsUser(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public boolean isExternalStorageEncrypted(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public boolean isInternalStorageEncrypted(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public boolean isRebootBannerEnabled(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public boolean removeAccountsByType(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public boolean removePackagesFromCertificateWhiteList(ContextInfo contextInfo, List<AppIdentity> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public boolean resetCredentialStorage(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public boolean setDeviceLastAccessDate(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public boolean setDodBannerVisibleStatus(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public boolean wipeDevice(ContextInfo contextInfo, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public void onKeyguardLaunched() throws RemoteException {
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public void installCertificatesFromSdCard(ContextInfo contextInfo) throws RemoteException {
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public void setExternalStorageEncryption(ContextInfo contextInfo, boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public void setInternalStorageEncryption(ContextInfo contextInfo, boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public void installCertificateWithType(ContextInfo contextInfo, String str, byte[] bArr) throws RemoteException {
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public void setRequireDeviceEncryption(ContextInfo contextInfo, ComponentName componentName, boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public void setRequireStorageCardEncryption(ContextInfo contextInfo, ComponentName componentName, boolean z) throws RemoteException {
        }
    }

    public abstract class Stub extends Binder implements ISecurityPolicy {
        public static final int TRANSACTION_addPackagesToCertificateWhiteList = 32;
        public static final int TRANSACTION_deleteCertificateFromKeystore = 31;
        public static final int TRANSACTION_deleteCertificateFromUserKeystore = 20;
        public static final int TRANSACTION_enableRebootBanner = 11;
        public static final int TRANSACTION_enableRebootBannerWithText = 21;
        public static final int TRANSACTION_formatSelective = 1;
        public static final int TRANSACTION_getCertificatesFromKeystore = 30;
        public static final int TRANSACTION_getCertificatesFromUserKeystore = 19;
        public static final int TRANSACTION_getDeviceLastAccessDate = 17;
        public static final int TRANSACTION_getPackagesFromCertificateWhiteList = 33;
        public static final int TRANSACTION_getRebootBannerText = 22;
        public static final int TRANSACTION_getRequireDeviceEncryption = 7;
        public static final int TRANSACTION_getRequireStorageCardEncryption = 9;
        public static final int TRANSACTION_getSystemCertificates = 27;
        public static final int TRANSACTION_installCertificateToKeystore = 29;
        public static final int TRANSACTION_installCertificateToUserKeystore = 18;
        public static final int TRANSACTION_installCertificateWithType = 25;
        public static final int TRANSACTION_installCertificatesFromSdCard = 26;
        public static final int TRANSACTION_isDodBannerVisible = 14;
        public static final int TRANSACTION_isDodBannerVisibleAsUser = 15;
        public static final int TRANSACTION_isExternalStorageEncrypted = 5;
        public static final int TRANSACTION_isInternalStorageEncrypted = 4;
        public static final int TRANSACTION_isRebootBannerEnabled = 12;
        public static final int TRANSACTION_onKeyguardLaunched = 24;
        public static final int TRANSACTION_removeAccountsByType = 10;
        public static final int TRANSACTION_removePackagesFromCertificateWhiteList = 34;
        public static final int TRANSACTION_resetCredentialStorage = 28;
        public static final int TRANSACTION_setDeviceLastAccessDate = 16;
        public static final int TRANSACTION_setDodBannerVisibleStatus = 13;
        public static final int TRANSACTION_setExternalStorageEncryption = 3;
        public static final int TRANSACTION_setInternalStorageEncryption = 2;
        public static final int TRANSACTION_setRequireDeviceEncryption = 6;
        public static final int TRANSACTION_setRequireStorageCardEncryption = 8;
        public static final int TRANSACTION_wipeDevice = 23;

        class Proxy implements ISecurityPolicy {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public boolean addPackagesToCertificateWhiteList(ContextInfo contextInfo, List<AppIdentity> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
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

            @Override // com.samsung.android.knox.ISecurityPolicy
            public boolean deleteCertificateFromKeystore(ContextInfo contextInfo, CertificateInfo certificateInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeTypedObject(certificateInfo, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public boolean deleteCertificateFromUserKeystore(ContextInfo contextInfo, CertificateInfo certificateInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeTypedObject(certificateInfo, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public boolean enableRebootBanner(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public boolean enableRebootBannerWithText(ContextInfo contextInfo, boolean z, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public String[] formatSelective(ContextInfo contextInfo, String[] strArr, String[] strArr2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeStringArray(strArr2);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public List<CertificateInfo> getCertificatesFromKeystore(ContextInfo contextInfo, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(CertificateInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public List<CertificateInfo> getCertificatesFromUserKeystore(ContextInfo contextInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(CertificateInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public String getDeviceLastAccessDate(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return ISecurityPolicy.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public List<AppIdentity> getPackagesFromCertificateWhiteList(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(AppIdentity.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public String getRebootBannerText(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public boolean getRequireDeviceEncryption(ContextInfo contextInfo, ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public boolean getRequireStorageCardEncryption(ContextInfo contextInfo, ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public List<CertificateInfo> getSystemCertificates(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(CertificateInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public int installCertificateToKeystore(ContextInfo contextInfo, String str, byte[] bArr, String str2, String str3, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public boolean installCertificateToUserKeystore(ContextInfo contextInfo, String str, byte[] bArr, String str2, String str3, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public void installCertificateWithType(ContextInfo contextInfo, String str, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public void installCertificatesFromSdCard(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public boolean isDodBannerVisible(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public boolean isDodBannerVisibleAsUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public boolean isExternalStorageEncrypted(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public boolean isInternalStorageEncrypted(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public boolean isRebootBannerEnabled(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public void onKeyguardLaunched() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public boolean removeAccountsByType(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public boolean removePackagesFromCertificateWhiteList(ContextInfo contextInfo, List<AppIdentity> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public boolean resetCredentialStorage(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public boolean setDeviceLastAccessDate(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public boolean setDodBannerVisibleStatus(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public void setExternalStorageEncryption(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public void setInternalStorageEncryption(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public void setRequireDeviceEncryption(ContextInfo contextInfo, ComponentName componentName, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public void setRequireStorageCardEncryption(ContextInfo contextInfo, ComponentName componentName, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public boolean wipeDevice(ContextInfo contextInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, ISecurityPolicy.DESCRIPTOR);
        }

        public static ISecurityPolicy asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISecurityPolicy.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof ISecurityPolicy)) ? new Proxy(iBinder) : (ISecurityPolicy) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISecurityPolicy.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISecurityPolicy.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ContextInfo contextInfo = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String[] strArrCreateStringArray = parcel.createStringArray();
                    String[] strArrCreateStringArray2 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    String[] selective = formatSelective(contextInfo, strArrCreateStringArray, strArrCreateStringArray2);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(selective);
                    return true;
                case 2:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setInternalStorageEncryption(contextInfo2, z);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    ContextInfo contextInfo3 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setExternalStorageEncryption(contextInfo3, z2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    ContextInfo contextInfo4 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsInternalStorageEncrypted = isInternalStorageEncrypted(contextInfo4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsInternalStorageEncrypted);
                    return true;
                case 5:
                    ContextInfo contextInfo5 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsExternalStorageEncrypted = isExternalStorageEncrypted(contextInfo5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsExternalStorageEncrypted);
                    return true;
                case 6:
                    ContextInfo contextInfo6 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setRequireDeviceEncryption(contextInfo6, componentName, z3);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    ContextInfo contextInfo7 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ComponentName componentName2 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean requireDeviceEncryption = getRequireDeviceEncryption(contextInfo7, componentName2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requireDeviceEncryption);
                    return true;
                case 8:
                    ContextInfo contextInfo8 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ComponentName componentName3 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setRequireStorageCardEncryption(contextInfo8, componentName3, z4);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    ContextInfo contextInfo9 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ComponentName componentName4 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean requireStorageCardEncryption = getRequireStorageCardEncryption(contextInfo9, componentName4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requireStorageCardEncryption);
                    return true;
                case 10:
                    ContextInfo contextInfo10 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zRemoveAccountsByType = removeAccountsByType(contextInfo10, string);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveAccountsByType);
                    return true;
                case 11:
                    ContextInfo contextInfo11 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zEnableRebootBanner = enableRebootBanner(contextInfo11, z5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zEnableRebootBanner);
                    return true;
                case 12:
                    ContextInfo contextInfo12 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsRebootBannerEnabled = isRebootBannerEnabled(contextInfo12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsRebootBannerEnabled);
                    return true;
                case 13:
                    ContextInfo contextInfo13 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean dodBannerVisibleStatus = setDodBannerVisibleStatus(contextInfo13, z6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(dodBannerVisibleStatus);
                    return true;
                case 14:
                    ContextInfo contextInfo14 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsDodBannerVisible = isDodBannerVisible(contextInfo14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsDodBannerVisible);
                    return true;
                case 15:
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsDodBannerVisibleAsUser = isDodBannerVisibleAsUser(i3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsDodBannerVisibleAsUser);
                    return true;
                case 16:
                    ContextInfo contextInfo15 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean deviceLastAccessDate = setDeviceLastAccessDate(contextInfo15, string2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(deviceLastAccessDate);
                    return true;
                case 17:
                    ContextInfo contextInfo16 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    String deviceLastAccessDate2 = getDeviceLastAccessDate(contextInfo16);
                    parcel2.writeNoException();
                    parcel2.writeString(deviceLastAccessDate2);
                    return true;
                case 18:
                    ContextInfo contextInfo17 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string3 = parcel.readString();
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    String string4 = parcel.readString();
                    String string5 = parcel.readString();
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zInstallCertificateToUserKeystore = installCertificateToUserKeystore(contextInfo17, string3, bArrCreateByteArray, string4, string5, i4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zInstallCertificateToUserKeystore);
                    return true;
                case 19:
                    ContextInfo contextInfo18 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<CertificateInfo> certificatesFromUserKeystore = getCertificatesFromUserKeystore(contextInfo18, i5);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(certificatesFromUserKeystore, 1);
                    return true;
                case 20:
                    ContextInfo contextInfo19 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    CertificateInfo certificateInfo = (CertificateInfo) parcel.readTypedObject(CertificateInfo.CREATOR);
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zDeleteCertificateFromUserKeystore = deleteCertificateFromUserKeystore(contextInfo19, certificateInfo, i6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zDeleteCertificateFromUserKeystore);
                    return true;
                case 21:
                    ContextInfo contextInfo20 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z7 = parcel.readBoolean();
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zEnableRebootBannerWithText = enableRebootBannerWithText(contextInfo20, z7, string6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zEnableRebootBannerWithText);
                    return true;
                case 22:
                    ContextInfo contextInfo21 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    String rebootBannerText = getRebootBannerText(contextInfo21);
                    parcel2.writeNoException();
                    parcel2.writeString(rebootBannerText);
                    return true;
                case 23:
                    ContextInfo contextInfo22 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zWipeDevice = wipeDevice(contextInfo22, i7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zWipeDevice);
                    return true;
                case 24:
                    onKeyguardLaunched();
                    parcel2.writeNoException();
                    return true;
                case 25:
                    ContextInfo contextInfo23 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string7 = parcel.readString();
                    byte[] bArrCreateByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    installCertificateWithType(contextInfo23, string7, bArrCreateByteArray2);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    ContextInfo contextInfo24 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    installCertificatesFromSdCard(contextInfo24);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    ContextInfo contextInfo25 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<CertificateInfo> systemCertificates = getSystemCertificates(contextInfo25);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(systemCertificates, 1);
                    return true;
                case 28:
                    ContextInfo contextInfo26 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zResetCredentialStorage = resetCredentialStorage(contextInfo26);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zResetCredentialStorage);
                    return true;
                case 29:
                    ContextInfo contextInfo27 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string8 = parcel.readString();
                    byte[] bArrCreateByteArray3 = parcel.createByteArray();
                    String string9 = parcel.readString();
                    String string10 = parcel.readString();
                    int i8 = parcel.readInt();
                    boolean z8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int iInstallCertificateToKeystore = installCertificateToKeystore(contextInfo27, string8, bArrCreateByteArray3, string9, string10, i8, z8);
                    parcel2.writeNoException();
                    parcel2.writeInt(iInstallCertificateToKeystore);
                    return true;
                case 30:
                    ContextInfo contextInfo28 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i9 = parcel.readInt();
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<CertificateInfo> certificatesFromKeystore = getCertificatesFromKeystore(contextInfo28, i9, i10);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(certificatesFromKeystore, 1);
                    return true;
                case 31:
                    ContextInfo contextInfo29 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    CertificateInfo certificateInfo2 = (CertificateInfo) parcel.readTypedObject(CertificateInfo.CREATOR);
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zDeleteCertificateFromKeystore = deleteCertificateFromKeystore(contextInfo29, certificateInfo2, i11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zDeleteCertificateFromKeystore);
                    return true;
                case 32:
                    ContextInfo contextInfo30 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(AppIdentity.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zAddPackagesToCertificateWhiteList = addPackagesToCertificateWhiteList(contextInfo30, arrayListCreateTypedArrayList);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddPackagesToCertificateWhiteList);
                    return true;
                case 33:
                    ContextInfo contextInfo31 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<AppIdentity> packagesFromCertificateWhiteList = getPackagesFromCertificateWhiteList(contextInfo31);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(packagesFromCertificateWhiteList, 1);
                    return true;
                case 34:
                    ContextInfo contextInfo32 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList arrayListCreateTypedArrayList2 = parcel.createTypedArrayList(AppIdentity.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zRemovePackagesFromCertificateWhiteList = removePackagesFromCertificateWhiteList(contextInfo32, arrayListCreateTypedArrayList2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemovePackagesFromCertificateWhiteList);
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
