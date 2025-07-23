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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        class Proxy implements ISecurityPolicy {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public boolean addPackagesToCertificateWhiteList(ContextInfo contextInfo, List<AppIdentity> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public boolean deleteCertificateFromKeystore(ContextInfo contextInfo, CertificateInfo certificateInfo, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(certificateInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public boolean deleteCertificateFromUserKeystore(ContextInfo contextInfo, CertificateInfo certificateInfo, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(certificateInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public boolean enableRebootBanner(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public boolean enableRebootBannerWithText(ContextInfo contextInfo, boolean z, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public String[] formatSelective(ContextInfo contextInfo, String[] strArr, String[] strArr2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringArray(strArr);
                    obtain.writeStringArray(strArr2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public List<CertificateInfo> getCertificatesFromKeystore(ContextInfo contextInfo, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(CertificateInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public List<CertificateInfo> getCertificatesFromUserKeystore(ContextInfo contextInfo, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(CertificateInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public String getDeviceLastAccessDate(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return ISecurityPolicy.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public List<AppIdentity> getPackagesFromCertificateWhiteList(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(AppIdentity.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public String getRebootBannerText(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public boolean getRequireDeviceEncryption(ContextInfo contextInfo, ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public boolean getRequireStorageCardEncryption(ContextInfo contextInfo, ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public List<CertificateInfo> getSystemCertificates(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(CertificateInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public int installCertificateToKeystore(ContextInfo contextInfo, String str, byte[] bArr, String str2, String str3, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public boolean installCertificateToUserKeystore(ContextInfo contextInfo, String str, byte[] bArr, String str2, String str3, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public void installCertificateWithType(ContextInfo contextInfo, String str, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public void installCertificatesFromSdCard(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public boolean isDodBannerVisible(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public boolean isDodBannerVisibleAsUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public boolean isExternalStorageEncrypted(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public boolean isInternalStorageEncrypted(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public boolean isRebootBannerEnabled(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public void onKeyguardLaunched() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public boolean removeAccountsByType(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public boolean removePackagesFromCertificateWhiteList(ContextInfo contextInfo, List<AppIdentity> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public boolean resetCredentialStorage(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public boolean setDeviceLastAccessDate(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public boolean setDodBannerVisibleStatus(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public void setExternalStorageEncryption(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public void setInternalStorageEncryption(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public void setRequireDeviceEncryption(ContextInfo contextInfo, ComponentName componentName, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public void setRequireStorageCardEncryption(ContextInfo contextInfo, ComponentName componentName, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public boolean wipeDevice(ContextInfo contextInfo, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISecurityPolicy.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof ISecurityPolicy)) ? new Proxy(iBinder) : (ISecurityPolicy) queryLocalInterface;
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
                    String[] createStringArray = parcel.createStringArray();
                    String[] createStringArray2 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    String[] formatSelective = formatSelective(contextInfo, createStringArray, createStringArray2);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(formatSelective);
                    return true;
                case 2:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setInternalStorageEncryption(contextInfo2, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    ContextInfo contextInfo3 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setExternalStorageEncryption(contextInfo3, readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    ContextInfo contextInfo4 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isInternalStorageEncrypted = isInternalStorageEncrypted(contextInfo4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isInternalStorageEncrypted);
                    return true;
                case 5:
                    ContextInfo contextInfo5 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isExternalStorageEncrypted = isExternalStorageEncrypted(contextInfo5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isExternalStorageEncrypted);
                    return true;
                case 6:
                    ContextInfo contextInfo6 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setRequireDeviceEncryption(contextInfo6, componentName, readBoolean3);
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
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setRequireStorageCardEncryption(contextInfo8, componentName3, readBoolean4);
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
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean removeAccountsByType = removeAccountsByType(contextInfo10, readString);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeAccountsByType);
                    return true;
                case 11:
                    ContextInfo contextInfo11 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean enableRebootBanner = enableRebootBanner(contextInfo11, readBoolean5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enableRebootBanner);
                    return true;
                case 12:
                    ContextInfo contextInfo12 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isRebootBannerEnabled = isRebootBannerEnabled(contextInfo12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isRebootBannerEnabled);
                    return true;
                case 13:
                    ContextInfo contextInfo13 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean dodBannerVisibleStatus = setDodBannerVisibleStatus(contextInfo13, readBoolean6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(dodBannerVisibleStatus);
                    return true;
                case 14:
                    ContextInfo contextInfo14 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isDodBannerVisible = isDodBannerVisible(contextInfo14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDodBannerVisible);
                    return true;
                case 15:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isDodBannerVisibleAsUser = isDodBannerVisibleAsUser(readInt);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDodBannerVisibleAsUser);
                    return true;
                case 16:
                    ContextInfo contextInfo15 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean deviceLastAccessDate = setDeviceLastAccessDate(contextInfo15, readString2);
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
                    String readString3 = parcel.readString();
                    byte[] createByteArray = parcel.createByteArray();
                    String readString4 = parcel.readString();
                    String readString5 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean installCertificateToUserKeystore = installCertificateToUserKeystore(contextInfo17, readString3, createByteArray, readString4, readString5, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(installCertificateToUserKeystore);
                    return true;
                case 19:
                    ContextInfo contextInfo18 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<CertificateInfo> certificatesFromUserKeystore = getCertificatesFromUserKeystore(contextInfo18, readInt3);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(certificatesFromUserKeystore, 1);
                    return true;
                case 20:
                    ContextInfo contextInfo19 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    CertificateInfo certificateInfo = (CertificateInfo) parcel.readTypedObject(CertificateInfo.CREATOR);
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean deleteCertificateFromUserKeystore = deleteCertificateFromUserKeystore(contextInfo19, certificateInfo, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(deleteCertificateFromUserKeystore);
                    return true;
                case 21:
                    ContextInfo contextInfo20 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean7 = parcel.readBoolean();
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean enableRebootBannerWithText = enableRebootBannerWithText(contextInfo20, readBoolean7, readString6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enableRebootBannerWithText);
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
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean wipeDevice = wipeDevice(contextInfo22, readInt5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(wipeDevice);
                    return true;
                case 24:
                    onKeyguardLaunched();
                    parcel2.writeNoException();
                    return true;
                case 25:
                    ContextInfo contextInfo23 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString7 = parcel.readString();
                    byte[] createByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    installCertificateWithType(contextInfo23, readString7, createByteArray2);
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
                    boolean resetCredentialStorage = resetCredentialStorage(contextInfo26);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(resetCredentialStorage);
                    return true;
                case 29:
                    ContextInfo contextInfo27 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString8 = parcel.readString();
                    byte[] createByteArray3 = parcel.createByteArray();
                    String readString9 = parcel.readString();
                    String readString10 = parcel.readString();
                    int readInt6 = parcel.readInt();
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int installCertificateToKeystore = installCertificateToKeystore(contextInfo27, readString8, createByteArray3, readString9, readString10, readInt6, readBoolean8);
                    parcel2.writeNoException();
                    parcel2.writeInt(installCertificateToKeystore);
                    return true;
                case 30:
                    ContextInfo contextInfo28 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<CertificateInfo> certificatesFromKeystore = getCertificatesFromKeystore(contextInfo28, readInt7, readInt8);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(certificatesFromKeystore, 1);
                    return true;
                case 31:
                    ContextInfo contextInfo29 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    CertificateInfo certificateInfo2 = (CertificateInfo) parcel.readTypedObject(CertificateInfo.CREATOR);
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean deleteCertificateFromKeystore = deleteCertificateFromKeystore(contextInfo29, certificateInfo2, readInt9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(deleteCertificateFromKeystore);
                    return true;
                case 32:
                    ContextInfo contextInfo30 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList createTypedArrayList = parcel.createTypedArrayList(AppIdentity.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean addPackagesToCertificateWhiteList = addPackagesToCertificateWhiteList(contextInfo30, createTypedArrayList);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addPackagesToCertificateWhiteList);
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
                    ArrayList createTypedArrayList2 = parcel.createTypedArrayList(AppIdentity.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean removePackagesFromCertificateWhiteList = removePackagesFromCertificateWhiteList(contextInfo32, createTypedArrayList2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removePackagesFromCertificateWhiteList);
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
