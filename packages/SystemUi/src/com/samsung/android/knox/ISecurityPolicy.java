package com.samsung.android.knox;

import android.content.ComponentName;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.samsung.android.knox.keystore.CertificateInfo;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface ISecurityPolicy extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.ISecurityPolicy";

    boolean addPackagesToCertificateWhiteList(ContextInfo contextInfo, List<AppIdentity> list);

    boolean deleteCertificateFromKeystore(ContextInfo contextInfo, CertificateInfo certificateInfo, int i);

    boolean deleteCertificateFromUserKeystore(ContextInfo contextInfo, CertificateInfo certificateInfo, int i);

    boolean enableRebootBanner(ContextInfo contextInfo, boolean z);

    boolean enableRebootBannerWithText(ContextInfo contextInfo, boolean z, String str);

    String[] formatSelective(ContextInfo contextInfo, String[] strArr, String[] strArr2);

    List<CertificateInfo> getCertificatesFromKeystore(ContextInfo contextInfo, int i, int i2);

    List<CertificateInfo> getCertificatesFromUserKeystore(ContextInfo contextInfo, int i);

    int getCredentialStorageStatus(ContextInfo contextInfo);

    String getDeviceLastAccessDate(ContextInfo contextInfo);

    List<AppIdentity> getPackagesFromCertificateWhiteList(ContextInfo contextInfo);

    String getRebootBannerText(ContextInfo contextInfo);

    boolean getRequireDeviceEncryption(ContextInfo contextInfo, ComponentName componentName);

    boolean getRequireStorageCardEncryption(ContextInfo contextInfo, ComponentName componentName);

    List<CertificateInfo> getSystemCertificates(ContextInfo contextInfo);

    int installCertificateToKeystore(ContextInfo contextInfo, String str, byte[] bArr, String str2, String str3, int i, boolean z);

    boolean installCertificateToUserKeystore(ContextInfo contextInfo, String str, byte[] bArr, String str2, String str3, int i);

    void installCertificateWithType(ContextInfo contextInfo, String str, byte[] bArr);

    void installCertificatesFromSdCard(ContextInfo contextInfo);

    boolean isDodBannerVisible(ContextInfo contextInfo);

    boolean isDodBannerVisibleAsUser(int i);

    boolean isExternalStorageEncrypted(ContextInfo contextInfo);

    boolean isInternalStorageEncrypted(ContextInfo contextInfo);

    boolean isRebootBannerEnabled(ContextInfo contextInfo);

    void onKeyguardLaunched();

    boolean removeAccountsByType(ContextInfo contextInfo, String str);

    boolean removeAccountsWithoutAdminPrivilege(ContextInfo contextInfo, String str);

    boolean removePackagesFromCertificateWhiteList(ContextInfo contextInfo, List<AppIdentity> list);

    boolean resetCredentialStorage(ContextInfo contextInfo);

    boolean setDeviceLastAccessDate(ContextInfo contextInfo, String str);

    boolean setDodBannerVisibleStatus(ContextInfo contextInfo, boolean z);

    void setExternalStorageEncryption(ContextInfo contextInfo, boolean z);

    void setInternalStorageEncryption(ContextInfo contextInfo, boolean z);

    void setRequireDeviceEncryption(ContextInfo contextInfo, ComponentName componentName, boolean z);

    void setRequireStorageCardEncryption(ContextInfo contextInfo, ComponentName componentName, boolean z);

    boolean wipeDevice(ContextInfo contextInfo, int i);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements ISecurityPolicy {
        @Override // com.samsung.android.knox.ISecurityPolicy
        public final boolean addPackagesToCertificateWhiteList(ContextInfo contextInfo, List<AppIdentity> list) {
            return false;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public final boolean deleteCertificateFromKeystore(ContextInfo contextInfo, CertificateInfo certificateInfo, int i) {
            return false;
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public final boolean deleteCertificateFromUserKeystore(ContextInfo contextInfo, CertificateInfo certificateInfo, int i) {
            return false;
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public final boolean enableRebootBanner(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public final boolean enableRebootBannerWithText(ContextInfo contextInfo, boolean z, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public final String[] formatSelective(ContextInfo contextInfo, String[] strArr, String[] strArr2) {
            return null;
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public final List<CertificateInfo> getCertificatesFromKeystore(ContextInfo contextInfo, int i, int i2) {
            return null;
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public final List<CertificateInfo> getCertificatesFromUserKeystore(ContextInfo contextInfo, int i) {
            return null;
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public final int getCredentialStorageStatus(ContextInfo contextInfo) {
            return 0;
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public final String getDeviceLastAccessDate(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public final List<AppIdentity> getPackagesFromCertificateWhiteList(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public final String getRebootBannerText(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public final boolean getRequireDeviceEncryption(ContextInfo contextInfo, ComponentName componentName) {
            return false;
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public final boolean getRequireStorageCardEncryption(ContextInfo contextInfo, ComponentName componentName) {
            return false;
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public final List<CertificateInfo> getSystemCertificates(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public final int installCertificateToKeystore(ContextInfo contextInfo, String str, byte[] bArr, String str2, String str3, int i, boolean z) {
            return 0;
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public final boolean installCertificateToUserKeystore(ContextInfo contextInfo, String str, byte[] bArr, String str2, String str3, int i) {
            return false;
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public final boolean isDodBannerVisible(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public final boolean isDodBannerVisibleAsUser(int i) {
            return false;
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public final boolean isExternalStorageEncrypted(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public final boolean isInternalStorageEncrypted(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public final boolean isRebootBannerEnabled(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public final boolean removeAccountsByType(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public final boolean removeAccountsWithoutAdminPrivilege(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public final boolean removePackagesFromCertificateWhiteList(ContextInfo contextInfo, List<AppIdentity> list) {
            return false;
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public final boolean resetCredentialStorage(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public final boolean setDeviceLastAccessDate(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public final boolean setDodBannerVisibleStatus(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public final boolean wipeDevice(ContextInfo contextInfo, int i) {
            return false;
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public final void onKeyguardLaunched() {
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public final void installCertificatesFromSdCard(ContextInfo contextInfo) {
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public final void setExternalStorageEncryption(ContextInfo contextInfo, boolean z) {
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public final void setInternalStorageEncryption(ContextInfo contextInfo, boolean z) {
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public final void installCertificateWithType(ContextInfo contextInfo, String str, byte[] bArr) {
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public final void setRequireDeviceEncryption(ContextInfo contextInfo, ComponentName componentName, boolean z) {
        }

        @Override // com.samsung.android.knox.ISecurityPolicy
        public final void setRequireStorageCardEncryption(ContextInfo contextInfo, ComponentName componentName, boolean z) {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements ISecurityPolicy {
        public static final int TRANSACTION_addPackagesToCertificateWhiteList = 34;
        public static final int TRANSACTION_deleteCertificateFromKeystore = 33;
        public static final int TRANSACTION_deleteCertificateFromUserKeystore = 21;
        public static final int TRANSACTION_enableRebootBanner = 12;
        public static final int TRANSACTION_enableRebootBannerWithText = 22;
        public static final int TRANSACTION_formatSelective = 1;
        public static final int TRANSACTION_getCertificatesFromKeystore = 32;
        public static final int TRANSACTION_getCertificatesFromUserKeystore = 20;
        public static final int TRANSACTION_getCredentialStorageStatus = 28;
        public static final int TRANSACTION_getDeviceLastAccessDate = 18;
        public static final int TRANSACTION_getPackagesFromCertificateWhiteList = 35;
        public static final int TRANSACTION_getRebootBannerText = 23;
        public static final int TRANSACTION_getRequireDeviceEncryption = 7;
        public static final int TRANSACTION_getRequireStorageCardEncryption = 9;
        public static final int TRANSACTION_getSystemCertificates = 29;
        public static final int TRANSACTION_installCertificateToKeystore = 31;
        public static final int TRANSACTION_installCertificateToUserKeystore = 19;
        public static final int TRANSACTION_installCertificateWithType = 26;
        public static final int TRANSACTION_installCertificatesFromSdCard = 27;
        public static final int TRANSACTION_isDodBannerVisible = 15;
        public static final int TRANSACTION_isDodBannerVisibleAsUser = 16;
        public static final int TRANSACTION_isExternalStorageEncrypted = 5;
        public static final int TRANSACTION_isInternalStorageEncrypted = 4;
        public static final int TRANSACTION_isRebootBannerEnabled = 13;
        public static final int TRANSACTION_onKeyguardLaunched = 25;
        public static final int TRANSACTION_removeAccountsByType = 10;
        public static final int TRANSACTION_removeAccountsWithoutAdminPrivilege = 11;
        public static final int TRANSACTION_removePackagesFromCertificateWhiteList = 36;
        public static final int TRANSACTION_resetCredentialStorage = 30;
        public static final int TRANSACTION_setDeviceLastAccessDate = 17;
        public static final int TRANSACTION_setDodBannerVisibleStatus = 14;
        public static final int TRANSACTION_setExternalStorageEncryption = 3;
        public static final int TRANSACTION_setInternalStorageEncryption = 2;
        public static final int TRANSACTION_setRequireDeviceEncryption = 6;
        public static final int TRANSACTION_setRequireStorageCardEncryption = 8;
        public static final int TRANSACTION_wipeDevice = 24;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements ISecurityPolicy {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public final boolean addPackagesToCertificateWhiteList(ContextInfo contextInfo, List<AppIdentity> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public final boolean deleteCertificateFromKeystore(ContextInfo contextInfo, CertificateInfo certificateInfo, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(certificateInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public final boolean deleteCertificateFromUserKeystore(ContextInfo contextInfo, CertificateInfo certificateInfo, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(certificateInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public final boolean enableRebootBanner(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public final boolean enableRebootBannerWithText(ContextInfo contextInfo, boolean z, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public final String[] formatSelective(ContextInfo contextInfo, String[] strArr, String[] strArr2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final List<CertificateInfo> getCertificatesFromKeystore(ContextInfo contextInfo, int i, int i2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(CertificateInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public final List<CertificateInfo> getCertificatesFromUserKeystore(ContextInfo contextInfo, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(CertificateInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public final int getCredentialStorageStatus(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public final String getDeviceLastAccessDate(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final String getInterfaceDescriptor() {
                return ISecurityPolicy.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public final List<AppIdentity> getPackagesFromCertificateWhiteList(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(AppIdentity.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public final String getRebootBannerText(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public final boolean getRequireDeviceEncryption(ContextInfo contextInfo, ComponentName componentName) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final boolean getRequireStorageCardEncryption(ContextInfo contextInfo, ComponentName componentName) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final List<CertificateInfo> getSystemCertificates(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(CertificateInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public final int installCertificateToKeystore(ContextInfo contextInfo, String str, byte[] bArr, String str2, String str3, int i, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public final boolean installCertificateToUserKeystore(ContextInfo contextInfo, String str, byte[] bArr, String str2, String str3, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public final void installCertificateWithType(ContextInfo contextInfo, String str, byte[] bArr) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public final void installCertificatesFromSdCard(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public final boolean isDodBannerVisible(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public final boolean isDodBannerVisibleAsUser(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public final boolean isExternalStorageEncrypted(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final boolean isInternalStorageEncrypted(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final boolean isRebootBannerEnabled(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public final void onKeyguardLaunched() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public final boolean removeAccountsByType(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final boolean removeAccountsWithoutAdminPrivilege(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public final boolean removePackagesFromCertificateWhiteList(ContextInfo contextInfo, List<AppIdentity> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public final boolean resetCredentialStorage(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public final boolean setDeviceLastAccessDate(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public final boolean setDodBannerVisibleStatus(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISecurityPolicy
            public final void setExternalStorageEncryption(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final void setInternalStorageEncryption(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final void setRequireDeviceEncryption(ContextInfo contextInfo, ComponentName componentName, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final void setRequireStorageCardEncryption(ContextInfo contextInfo, ComponentName componentName, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final boolean wipeDevice(ContextInfo contextInfo, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISecurityPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(24, obtain, obtain2, 0);
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
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
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
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean removeAccountsWithoutAdminPrivilege = removeAccountsWithoutAdminPrivilege(contextInfo11, readString2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeAccountsWithoutAdminPrivilege);
                    return true;
                case 12:
                    ContextInfo contextInfo12 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean enableRebootBanner = enableRebootBanner(contextInfo12, readBoolean5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enableRebootBanner);
                    return true;
                case 13:
                    ContextInfo contextInfo13 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isRebootBannerEnabled = isRebootBannerEnabled(contextInfo13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isRebootBannerEnabled);
                    return true;
                case 14:
                    ContextInfo contextInfo14 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean dodBannerVisibleStatus = setDodBannerVisibleStatus(contextInfo14, readBoolean6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(dodBannerVisibleStatus);
                    return true;
                case 15:
                    ContextInfo contextInfo15 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isDodBannerVisible = isDodBannerVisible(contextInfo15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDodBannerVisible);
                    return true;
                case 16:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isDodBannerVisibleAsUser = isDodBannerVisibleAsUser(readInt);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDodBannerVisibleAsUser);
                    return true;
                case 17:
                    ContextInfo contextInfo16 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean deviceLastAccessDate = setDeviceLastAccessDate(contextInfo16, readString3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(deviceLastAccessDate);
                    return true;
                case 18:
                    ContextInfo contextInfo17 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    String deviceLastAccessDate2 = getDeviceLastAccessDate(contextInfo17);
                    parcel2.writeNoException();
                    parcel2.writeString(deviceLastAccessDate2);
                    return true;
                case 19:
                    ContextInfo contextInfo18 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString4 = parcel.readString();
                    byte[] createByteArray = parcel.createByteArray();
                    String readString5 = parcel.readString();
                    String readString6 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean installCertificateToUserKeystore = installCertificateToUserKeystore(contextInfo18, readString4, createByteArray, readString5, readString6, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(installCertificateToUserKeystore);
                    return true;
                case 20:
                    ContextInfo contextInfo19 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<CertificateInfo> certificatesFromUserKeystore = getCertificatesFromUserKeystore(contextInfo19, readInt3);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(certificatesFromUserKeystore, 1);
                    return true;
                case 21:
                    ContextInfo contextInfo20 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    CertificateInfo certificateInfo = (CertificateInfo) parcel.readTypedObject(CertificateInfo.CREATOR);
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean deleteCertificateFromUserKeystore = deleteCertificateFromUserKeystore(contextInfo20, certificateInfo, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(deleteCertificateFromUserKeystore);
                    return true;
                case 22:
                    ContextInfo contextInfo21 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean7 = parcel.readBoolean();
                    String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean enableRebootBannerWithText = enableRebootBannerWithText(contextInfo21, readBoolean7, readString7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enableRebootBannerWithText);
                    return true;
                case 23:
                    ContextInfo contextInfo22 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    String rebootBannerText = getRebootBannerText(contextInfo22);
                    parcel2.writeNoException();
                    parcel2.writeString(rebootBannerText);
                    return true;
                case 24:
                    ContextInfo contextInfo23 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean wipeDevice = wipeDevice(contextInfo23, readInt5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(wipeDevice);
                    return true;
                case 25:
                    onKeyguardLaunched();
                    parcel2.writeNoException();
                    return true;
                case 26:
                    ContextInfo contextInfo24 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString8 = parcel.readString();
                    byte[] createByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    installCertificateWithType(contextInfo24, readString8, createByteArray2);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    ContextInfo contextInfo25 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    installCertificatesFromSdCard(contextInfo25);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    ContextInfo contextInfo26 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    int credentialStorageStatus = getCredentialStorageStatus(contextInfo26);
                    parcel2.writeNoException();
                    parcel2.writeInt(credentialStorageStatus);
                    return true;
                case 29:
                    ContextInfo contextInfo27 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<CertificateInfo> systemCertificates = getSystemCertificates(contextInfo27);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(systemCertificates, 1);
                    return true;
                case 30:
                    ContextInfo contextInfo28 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean resetCredentialStorage = resetCredentialStorage(contextInfo28);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(resetCredentialStorage);
                    return true;
                case 31:
                    ContextInfo contextInfo29 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString9 = parcel.readString();
                    byte[] createByteArray3 = parcel.createByteArray();
                    String readString10 = parcel.readString();
                    String readString11 = parcel.readString();
                    int readInt6 = parcel.readInt();
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int installCertificateToKeystore = installCertificateToKeystore(contextInfo29, readString9, createByteArray3, readString10, readString11, readInt6, readBoolean8);
                    parcel2.writeNoException();
                    parcel2.writeInt(installCertificateToKeystore);
                    return true;
                case 32:
                    ContextInfo contextInfo30 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<CertificateInfo> certificatesFromKeystore = getCertificatesFromKeystore(contextInfo30, readInt7, readInt8);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(certificatesFromKeystore, 1);
                    return true;
                case 33:
                    ContextInfo contextInfo31 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    CertificateInfo certificateInfo2 = (CertificateInfo) parcel.readTypedObject(CertificateInfo.CREATOR);
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean deleteCertificateFromKeystore = deleteCertificateFromKeystore(contextInfo31, certificateInfo2, readInt9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(deleteCertificateFromKeystore);
                    return true;
                case 34:
                    ContextInfo contextInfo32 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList createTypedArrayList = parcel.createTypedArrayList(AppIdentity.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean addPackagesToCertificateWhiteList = addPackagesToCertificateWhiteList(contextInfo32, createTypedArrayList);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addPackagesToCertificateWhiteList);
                    return true;
                case 35:
                    ContextInfo contextInfo33 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<AppIdentity> packagesFromCertificateWhiteList = getPackagesFromCertificateWhiteList(contextInfo33);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(packagesFromCertificateWhiteList, 1);
                    return true;
                case 36:
                    ContextInfo contextInfo34 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList createTypedArrayList2 = parcel.createTypedArrayList(AppIdentity.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean removePackagesFromCertificateWhiteList = removePackagesFromCertificateWhiteList(contextInfo34, createTypedArrayList2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removePackagesFromCertificateWhiteList);
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
