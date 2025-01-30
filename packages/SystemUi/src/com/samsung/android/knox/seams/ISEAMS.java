package com.samsung.android.knox.seams;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.samsung.android.knox.ContextInfo;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface ISEAMS extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.seams.ISEAMS";

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements ISEAMS {
        @Override // com.samsung.android.knox.seams.ISEAMS
        public final int activateDomain(boolean z) {
            return 0;
        }

        @Override // com.samsung.android.knox.seams.ISEAMS
        public final int addAppToContainer(String str, String str2, int i, int i2) {
            return 0;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.seams.ISEAMS
        public final int changeAppDomain(String str, boolean z) {
            return 0;
        }

        @Override // com.samsung.android.knox.seams.ISEAMS
        public final int createSEContainer() {
            return 0;
        }

        @Override // com.samsung.android.knox.seams.ISEAMS
        public final int deActivateDomain() {
            return 0;
        }

        @Override // com.samsung.android.knox.seams.ISEAMS
        public final String getAMSLog(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.seams.ISEAMS
        public final int getAMSLogLevel(ContextInfo contextInfo) {
            return 0;
        }

        @Override // com.samsung.android.knox.seams.ISEAMS
        public final int getAMSMode(ContextInfo contextInfo) {
            return 0;
        }

        @Override // com.samsung.android.knox.seams.ISEAMS
        public final String getAVCLog(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.seams.ISEAMS
        public final int getActivationStatus() {
            return 0;
        }

        @Override // com.samsung.android.knox.seams.ISEAMS
        public final String getDataType(ContextInfo contextInfo, String str, int i) {
            return null;
        }

        @Override // com.samsung.android.knox.seams.ISEAMS
        public final String getDomain(ContextInfo contextInfo, String str, int i) {
            return null;
        }

        @Override // com.samsung.android.knox.seams.ISEAMS
        public final String[] getPackageNamesFromSEContainer(int i, int i2) {
            return null;
        }

        @Override // com.samsung.android.knox.seams.ISEAMS
        public final String getSEAMSLog(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.seams.ISEAMS
        public final int[] getSEContainerIDs() {
            return null;
        }

        @Override // com.samsung.android.knox.seams.ISEAMS
        public final int[] getSEContainerIDsFromPackageName(String str, int i) {
            return null;
        }

        @Override // com.samsung.android.knox.seams.ISEAMS
        public final int getSELinuxMode(ContextInfo contextInfo) {
            return 0;
        }

        @Override // com.samsung.android.knox.seams.ISEAMS
        public final String getSepolicyVersion(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.seams.ISEAMS
        public final String getSignatureFromCertificate(byte[] bArr) {
            return null;
        }

        @Override // com.samsung.android.knox.seams.ISEAMS
        public final String getSignatureFromPackage(String str) {
            return null;
        }

        @Override // com.samsung.android.knox.seams.ISEAMS
        public final int hasKnoxContainers() {
            return 0;
        }

        @Override // com.samsung.android.knox.seams.ISEAMS
        public final int hasSEContainers() {
            return 0;
        }

        @Override // com.samsung.android.knox.seams.ISEAMS
        public final int isAuthorized(int i, int i2, String str, String str2) {
            return 0;
        }

        @Override // com.samsung.android.knox.seams.ISEAMS
        public final int isSEAndroidLogDumpStateInclude(ContextInfo contextInfo) {
            return 0;
        }

        @Override // com.samsung.android.knox.seams.ISEAMS
        public final int isSEPolicyAutoUpdateEnabled(ContextInfo contextInfo) {
            return 0;
        }

        @Override // com.samsung.android.knox.seams.ISEAMS
        public final int loadContainerSetting(String str) {
            return 0;
        }

        @Override // com.samsung.android.knox.seams.ISEAMS
        public final int relabelAppDir(String str) {
            return 0;
        }

        @Override // com.samsung.android.knox.seams.ISEAMS
        public final int relabelData(ContextInfo contextInfo) {
            return 0;
        }

        @Override // com.samsung.android.knox.seams.ISEAMS
        public final int removeAppFromContainer(String str, String str2, int i, int i2) {
            return 0;
        }

        @Override // com.samsung.android.knox.seams.ISEAMS
        public final int removeSEContainer(int i) {
            return 0;
        }

        @Override // com.samsung.android.knox.seams.ISEAMS
        public final int setAMSLogLevel(ContextInfo contextInfo, int i) {
            return 0;
        }

        @Override // com.samsung.android.knox.seams.ISEAMS
        public final int setSEAndroidLogDumpStateInclude(ContextInfo contextInfo, boolean z) {
            return 0;
        }
    }

    int activateDomain(boolean z);

    int addAppToContainer(String str, String str2, int i, int i2);

    int changeAppDomain(String str, boolean z);

    int createSEContainer();

    int deActivateDomain();

    String getAMSLog(ContextInfo contextInfo);

    int getAMSLogLevel(ContextInfo contextInfo);

    int getAMSMode(ContextInfo contextInfo);

    String getAVCLog(ContextInfo contextInfo);

    int getActivationStatus();

    String getDataType(ContextInfo contextInfo, String str, int i);

    String getDomain(ContextInfo contextInfo, String str, int i);

    String[] getPackageNamesFromSEContainer(int i, int i2);

    String getSEAMSLog(ContextInfo contextInfo);

    int[] getSEContainerIDs();

    int[] getSEContainerIDsFromPackageName(String str, int i);

    int getSELinuxMode(ContextInfo contextInfo);

    String getSepolicyVersion(ContextInfo contextInfo);

    String getSignatureFromCertificate(byte[] bArr);

    String getSignatureFromPackage(String str);

    int hasKnoxContainers();

    int hasSEContainers();

    int isAuthorized(int i, int i2, String str, String str2);

    int isSEAndroidLogDumpStateInclude(ContextInfo contextInfo);

    int isSEPolicyAutoUpdateEnabled(ContextInfo contextInfo);

    int loadContainerSetting(String str);

    int relabelAppDir(String str);

    int relabelData(ContextInfo contextInfo);

    int removeAppFromContainer(String str, String str2, int i, int i2);

    int removeSEContainer(int i);

    int setAMSLogLevel(ContextInfo contextInfo, int i);

    int setSEAndroidLogDumpStateInclude(ContextInfo contextInfo, boolean z);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements ISEAMS {
        public static final int TRANSACTION_activateDomain = 2;
        public static final int TRANSACTION_addAppToContainer = 3;
        public static final int TRANSACTION_changeAppDomain = 32;
        public static final int TRANSACTION_createSEContainer = 4;
        public static final int TRANSACTION_deActivateDomain = 5;
        public static final int TRANSACTION_getAMSLog = 7;
        public static final int TRANSACTION_getAMSLogLevel = 9;
        public static final int TRANSACTION_getAMSMode = 10;
        public static final int TRANSACTION_getAVCLog = 11;
        public static final int TRANSACTION_getActivationStatus = 6;
        public static final int TRANSACTION_getDataType = 14;
        public static final int TRANSACTION_getDomain = 15;
        public static final int TRANSACTION_getPackageNamesFromSEContainer = 16;
        public static final int TRANSACTION_getSEAMSLog = 8;
        public static final int TRANSACTION_getSEContainerIDs = 13;
        public static final int TRANSACTION_getSEContainerIDsFromPackageName = 12;
        public static final int TRANSACTION_getSELinuxMode = 18;
        public static final int TRANSACTION_getSepolicyVersion = 19;
        public static final int TRANSACTION_getSignatureFromCertificate = 21;
        public static final int TRANSACTION_getSignatureFromPackage = 22;
        public static final int TRANSACTION_hasKnoxContainers = 23;
        public static final int TRANSACTION_hasSEContainers = 24;
        public static final int TRANSACTION_isAuthorized = 1;
        public static final int TRANSACTION_isSEAndroidLogDumpStateInclude = 17;
        public static final int TRANSACTION_isSEPolicyAutoUpdateEnabled = 20;
        public static final int TRANSACTION_loadContainerSetting = 25;
        public static final int TRANSACTION_relabelAppDir = 26;
        public static final int TRANSACTION_relabelData = 27;
        public static final int TRANSACTION_removeAppFromContainer = 28;
        public static final int TRANSACTION_removeSEContainer = 29;
        public static final int TRANSACTION_setAMSLogLevel = 30;
        public static final int TRANSACTION_setSEAndroidLogDumpStateInclude = 31;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements ISEAMS {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.seams.ISEAMS
            public final int activateDomain(boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISEAMS.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.seams.ISEAMS
            public final int addAppToContainer(String str, String str2, int i, int i2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISEAMS.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.seams.ISEAMS
            public final int changeAppDomain(String str, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISEAMS.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.seams.ISEAMS
            public final int createSEContainer() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISEAMS.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.seams.ISEAMS
            public final int deActivateDomain() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISEAMS.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.seams.ISEAMS
            public final String getAMSLog(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISEAMS.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.seams.ISEAMS
            public final int getAMSLogLevel(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISEAMS.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.seams.ISEAMS
            public final int getAMSMode(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISEAMS.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.seams.ISEAMS
            public final String getAVCLog(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISEAMS.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.seams.ISEAMS
            public final int getActivationStatus() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISEAMS.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.seams.ISEAMS
            public final String getDataType(ContextInfo contextInfo, String str, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISEAMS.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.seams.ISEAMS
            public final String getDomain(ContextInfo contextInfo, String str, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISEAMS.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final String getInterfaceDescriptor() {
                return ISEAMS.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.seams.ISEAMS
            public final String[] getPackageNamesFromSEContainer(int i, int i2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISEAMS.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.seams.ISEAMS
            public final String getSEAMSLog(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISEAMS.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.seams.ISEAMS
            public final int[] getSEContainerIDs() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISEAMS.DESCRIPTOR);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.seams.ISEAMS
            public final int[] getSEContainerIDsFromPackageName(String str, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISEAMS.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.seams.ISEAMS
            public final int getSELinuxMode(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISEAMS.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.seams.ISEAMS
            public final String getSepolicyVersion(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISEAMS.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.seams.ISEAMS
            public final String getSignatureFromCertificate(byte[] bArr) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISEAMS.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.seams.ISEAMS
            public final String getSignatureFromPackage(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISEAMS.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.seams.ISEAMS
            public final int hasKnoxContainers() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISEAMS.DESCRIPTOR);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.seams.ISEAMS
            public final int hasSEContainers() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISEAMS.DESCRIPTOR);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.seams.ISEAMS
            public final int isAuthorized(int i, int i2, String str, String str2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISEAMS.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.seams.ISEAMS
            public final int isSEAndroidLogDumpStateInclude(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISEAMS.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.seams.ISEAMS
            public final int isSEPolicyAutoUpdateEnabled(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISEAMS.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.seams.ISEAMS
            public final int loadContainerSetting(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISEAMS.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.seams.ISEAMS
            public final int relabelAppDir(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISEAMS.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.seams.ISEAMS
            public final int relabelData(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISEAMS.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.seams.ISEAMS
            public final int removeAppFromContainer(String str, String str2, int i, int i2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISEAMS.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.seams.ISEAMS
            public final int removeSEContainer(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISEAMS.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.seams.ISEAMS
            public final int setAMSLogLevel(ContextInfo contextInfo, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISEAMS.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.seams.ISEAMS
            public final int setSEAndroidLogDumpStateInclude(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISEAMS.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, ISEAMS.DESCRIPTOR);
        }

        public static ISEAMS asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISEAMS.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof ISEAMS)) ? new Proxy(iBinder) : (ISEAMS) queryLocalInterface;
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "isAuthorized";
                case 2:
                    return "activateDomain";
                case 3:
                    return "addAppToContainer";
                case 4:
                    return "createSEContainer";
                case 5:
                    return "deActivateDomain";
                case 6:
                    return "getActivationStatus";
                case 7:
                    return "getAMSLog";
                case 8:
                    return "getSEAMSLog";
                case 9:
                    return "getAMSLogLevel";
                case 10:
                    return "getAMSMode";
                case 11:
                    return "getAVCLog";
                case 12:
                    return "getSEContainerIDsFromPackageName";
                case 13:
                    return "getSEContainerIDs";
                case 14:
                    return "getDataType";
                case 15:
                    return "getDomain";
                case 16:
                    return "getPackageNamesFromSEContainer";
                case 17:
                    return "isSEAndroidLogDumpStateInclude";
                case 18:
                    return "getSELinuxMode";
                case 19:
                    return "getSepolicyVersion";
                case 20:
                    return "isSEPolicyAutoUpdateEnabled";
                case 21:
                    return "getSignatureFromCertificate";
                case 22:
                    return "getSignatureFromPackage";
                case 23:
                    return "hasKnoxContainers";
                case 24:
                    return "hasSEContainers";
                case 25:
                    return "loadContainerSetting";
                case 26:
                    return "relabelAppDir";
                case 27:
                    return "relabelData";
                case 28:
                    return "removeAppFromContainer";
                case 29:
                    return "removeSEContainer";
                case 30:
                    return "setAMSLogLevel";
                case 31:
                    return "setSEAndroidLogDumpStateInclude";
                case 32:
                    return "changeAppDomain";
                default:
                    return null;
            }
        }

        public final int getMaxTransactionId() {
            return 31;
        }

        public final String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISEAMS.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISEAMS.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    String readString = parcel.readString();
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int isAuthorized = isAuthorized(readInt, readInt2, readString, readString2);
                    parcel2.writeNoException();
                    parcel2.writeInt(isAuthorized);
                    return true;
                case 2:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int activateDomain = activateDomain(readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeInt(activateDomain);
                    return true;
                case 3:
                    String readString3 = parcel.readString();
                    String readString4 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int addAppToContainer = addAppToContainer(readString3, readString4, readInt3, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeInt(addAppToContainer);
                    return true;
                case 4:
                    int createSEContainer = createSEContainer();
                    parcel2.writeNoException();
                    parcel2.writeInt(createSEContainer);
                    return true;
                case 5:
                    int deActivateDomain = deActivateDomain();
                    parcel2.writeNoException();
                    parcel2.writeInt(deActivateDomain);
                    return true;
                case 6:
                    int activationStatus = getActivationStatus();
                    parcel2.writeNoException();
                    parcel2.writeInt(activationStatus);
                    return true;
                case 7:
                    ContextInfo contextInfo = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    String aMSLog = getAMSLog(contextInfo);
                    parcel2.writeNoException();
                    parcel2.writeString(aMSLog);
                    return true;
                case 8:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    String sEAMSLog = getSEAMSLog(contextInfo2);
                    parcel2.writeNoException();
                    parcel2.writeString(sEAMSLog);
                    return true;
                case 9:
                    ContextInfo contextInfo3 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    int aMSLogLevel = getAMSLogLevel(contextInfo3);
                    parcel2.writeNoException();
                    parcel2.writeInt(aMSLogLevel);
                    return true;
                case 10:
                    ContextInfo contextInfo4 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    int aMSMode = getAMSMode(contextInfo4);
                    parcel2.writeNoException();
                    parcel2.writeInt(aMSMode);
                    return true;
                case 11:
                    ContextInfo contextInfo5 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    String aVCLog = getAVCLog(contextInfo5);
                    parcel2.writeNoException();
                    parcel2.writeString(aVCLog);
                    return true;
                case 12:
                    String readString5 = parcel.readString();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int[] sEContainerIDsFromPackageName = getSEContainerIDsFromPackageName(readString5, readInt5);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(sEContainerIDsFromPackageName);
                    return true;
                case 13:
                    int[] sEContainerIDs = getSEContainerIDs();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(sEContainerIDs);
                    return true;
                case 14:
                    ContextInfo contextInfo6 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString6 = parcel.readString();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String dataType = getDataType(contextInfo6, readString6, readInt6);
                    parcel2.writeNoException();
                    parcel2.writeString(dataType);
                    return true;
                case 15:
                    ContextInfo contextInfo7 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString7 = parcel.readString();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String domain = getDomain(contextInfo7, readString7, readInt7);
                    parcel2.writeNoException();
                    parcel2.writeString(domain);
                    return true;
                case 16:
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String[] packageNamesFromSEContainer = getPackageNamesFromSEContainer(readInt8, readInt9);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(packageNamesFromSEContainer);
                    return true;
                case 17:
                    ContextInfo contextInfo8 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    int isSEAndroidLogDumpStateInclude = isSEAndroidLogDumpStateInclude(contextInfo8);
                    parcel2.writeNoException();
                    parcel2.writeInt(isSEAndroidLogDumpStateInclude);
                    return true;
                case 18:
                    ContextInfo contextInfo9 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    int sELinuxMode = getSELinuxMode(contextInfo9);
                    parcel2.writeNoException();
                    parcel2.writeInt(sELinuxMode);
                    return true;
                case 19:
                    ContextInfo contextInfo10 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    String sepolicyVersion = getSepolicyVersion(contextInfo10);
                    parcel2.writeNoException();
                    parcel2.writeString(sepolicyVersion);
                    return true;
                case 20:
                    ContextInfo contextInfo11 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    int isSEPolicyAutoUpdateEnabled = isSEPolicyAutoUpdateEnabled(contextInfo11);
                    parcel2.writeNoException();
                    parcel2.writeInt(isSEPolicyAutoUpdateEnabled);
                    return true;
                case 21:
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    String signatureFromCertificate = getSignatureFromCertificate(createByteArray);
                    parcel2.writeNoException();
                    parcel2.writeString(signatureFromCertificate);
                    return true;
                case 22:
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String signatureFromPackage = getSignatureFromPackage(readString8);
                    parcel2.writeNoException();
                    parcel2.writeString(signatureFromPackage);
                    return true;
                case 23:
                    int hasKnoxContainers = hasKnoxContainers();
                    parcel2.writeNoException();
                    parcel2.writeInt(hasKnoxContainers);
                    return true;
                case 24:
                    int hasSEContainers = hasSEContainers();
                    parcel2.writeNoException();
                    parcel2.writeInt(hasSEContainers);
                    return true;
                case 25:
                    String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int loadContainerSetting = loadContainerSetting(readString9);
                    parcel2.writeNoException();
                    parcel2.writeInt(loadContainerSetting);
                    return true;
                case 26:
                    String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int relabelAppDir = relabelAppDir(readString10);
                    parcel2.writeNoException();
                    parcel2.writeInt(relabelAppDir);
                    return true;
                case 27:
                    ContextInfo contextInfo12 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    int relabelData = relabelData(contextInfo12);
                    parcel2.writeNoException();
                    parcel2.writeInt(relabelData);
                    return true;
                case 28:
                    String readString11 = parcel.readString();
                    String readString12 = parcel.readString();
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int removeAppFromContainer = removeAppFromContainer(readString11, readString12, readInt10, readInt11);
                    parcel2.writeNoException();
                    parcel2.writeInt(removeAppFromContainer);
                    return true;
                case 29:
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int removeSEContainer = removeSEContainer(readInt12);
                    parcel2.writeNoException();
                    parcel2.writeInt(removeSEContainer);
                    return true;
                case 30:
                    ContextInfo contextInfo13 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int aMSLogLevel2 = setAMSLogLevel(contextInfo13, readInt13);
                    parcel2.writeNoException();
                    parcel2.writeInt(aMSLogLevel2);
                    return true;
                case 31:
                    ContextInfo contextInfo14 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int sEAndroidLogDumpStateInclude = setSEAndroidLogDumpStateInclude(contextInfo14, readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeInt(sEAndroidLogDumpStateInclude);
                    return true;
                case 32:
                    String readString13 = parcel.readString();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int changeAppDomain = changeAppDomain(readString13, readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeInt(changeAppDomain);
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
