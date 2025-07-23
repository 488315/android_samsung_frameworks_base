package android.content.pm;

import android.content.pm.IStagedApexObserver;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IPackageManagerNative extends IInterface {
    public static final int LOCATION_PRODUCT = 4;
    public static final int LOCATION_SYSTEM = 1;
    public static final int LOCATION_VENDOR = 2;

    public static class Default implements IPackageManagerNative {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.content.pm.IPackageManagerNative
        public String getInstallerForPackage(String str) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManagerNative
        public int getLocationFlags(String str) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManagerNative
        public String getModuleMetadataPackageName() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManagerNative
        public String[] getNamesForUids(int[] iArr) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManagerNative
        public int getPackageUid(String str, long j, int i) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManagerNative
        public StagedApexInfo[] getStagedApexInfos() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManagerNative
        public int getTargetSdkVersionForPackage(String str) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManagerNative
        public long getVersionCodeForPackage(String str) throws RemoteException {
            return 0L;
        }

        @Override // android.content.pm.IPackageManagerNative
        public boolean hasSha256SigningCertificate(String str, byte[] bArr) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManagerNative
        public boolean hasSystemFeature(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManagerNative
        public boolean[] isAudioPlaybackCaptureAllowed(String[] strArr) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManagerNative
        public boolean isPackageDebuggable(String str) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManagerNative
        public void registerStagedApexObserver(IStagedApexObserver iStagedApexObserver) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManagerNative
        public void unregisterStagedApexObserver(IStagedApexObserver iStagedApexObserver) throws RemoteException {
        }
    }

    String getInstallerForPackage(String str) throws RemoteException;

    int getLocationFlags(String str) throws RemoteException;

    String getModuleMetadataPackageName() throws RemoteException;

    String[] getNamesForUids(int[] iArr) throws RemoteException;

    int getPackageUid(String str, long j, int i) throws RemoteException;

    StagedApexInfo[] getStagedApexInfos() throws RemoteException;

    int getTargetSdkVersionForPackage(String str) throws RemoteException;

    long getVersionCodeForPackage(String str) throws RemoteException;

    boolean hasSha256SigningCertificate(String str, byte[] bArr) throws RemoteException;

    boolean hasSystemFeature(String str, int i) throws RemoteException;

    boolean[] isAudioPlaybackCaptureAllowed(String[] strArr) throws RemoteException;

    boolean isPackageDebuggable(String str) throws RemoteException;

    void registerStagedApexObserver(IStagedApexObserver iStagedApexObserver) throws RemoteException;

    void unregisterStagedApexObserver(IStagedApexObserver iStagedApexObserver) throws RemoteException;

    public static abstract class Stub extends Binder implements IPackageManagerNative {
        public static final String DESCRIPTOR = "android.content.pm.IPackageManagerNative";
        static final int TRANSACTION_getInstallerForPackage = 3;
        static final int TRANSACTION_getLocationFlags = 6;
        static final int TRANSACTION_getModuleMetadataPackageName = 8;
        static final int TRANSACTION_getNamesForUids = 1;
        static final int TRANSACTION_getPackageUid = 2;
        static final int TRANSACTION_getStagedApexInfos = 14;
        static final int TRANSACTION_getTargetSdkVersionForPackage = 7;
        static final int TRANSACTION_getVersionCodeForPackage = 4;
        static final int TRANSACTION_hasSha256SigningCertificate = 9;
        static final int TRANSACTION_hasSystemFeature = 11;
        static final int TRANSACTION_isAudioPlaybackCaptureAllowed = 5;
        static final int TRANSACTION_isPackageDebuggable = 10;
        static final int TRANSACTION_registerStagedApexObserver = 12;
        static final int TRANSACTION_unregisterStagedApexObserver = 13;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IPackageManagerNative asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IPackageManagerNative)) {
                return (IPackageManagerNative) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    String[] namesForUids = getNamesForUids(createIntArray);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(namesForUids);
                    return true;
                case 2:
                    String readString = parcel.readString();
                    long readLong = parcel.readLong();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int packageUid = getPackageUid(readString, readLong, readInt);
                    parcel2.writeNoException();
                    parcel2.writeInt(packageUid);
                    return true;
                case 3:
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String installerForPackage = getInstallerForPackage(readString2);
                    parcel2.writeNoException();
                    parcel2.writeString(installerForPackage);
                    return true;
                case 4:
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long versionCodeForPackage = getVersionCodeForPackage(readString3);
                    parcel2.writeNoException();
                    parcel2.writeLong(versionCodeForPackage);
                    return true;
                case 5:
                    String[] createStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    boolean[] isAudioPlaybackCaptureAllowed = isAudioPlaybackCaptureAllowed(createStringArray);
                    parcel2.writeNoException();
                    parcel2.writeBooleanArray(isAudioPlaybackCaptureAllowed);
                    return true;
                case 6:
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int locationFlags = getLocationFlags(readString4);
                    parcel2.writeNoException();
                    parcel2.writeInt(locationFlags);
                    return true;
                case 7:
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int targetSdkVersionForPackage = getTargetSdkVersionForPackage(readString5);
                    parcel2.writeNoException();
                    parcel2.writeInt(targetSdkVersionForPackage);
                    return true;
                case 8:
                    String moduleMetadataPackageName = getModuleMetadataPackageName();
                    parcel2.writeNoException();
                    parcel2.writeString(moduleMetadataPackageName);
                    return true;
                case 9:
                    String readString6 = parcel.readString();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    boolean hasSha256SigningCertificate = hasSha256SigningCertificate(readString6, createByteArray);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasSha256SigningCertificate);
                    return true;
                case 10:
                    String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isPackageDebuggable = isPackageDebuggable(readString7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPackageDebuggable);
                    return true;
                case 11:
                    String readString8 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean hasSystemFeature = hasSystemFeature(readString8, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasSystemFeature);
                    return true;
                case 12:
                    IStagedApexObserver asInterface = IStagedApexObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerStagedApexObserver(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    IStagedApexObserver asInterface2 = IStagedApexObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterStagedApexObserver(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    StagedApexInfo[] stagedApexInfos = getStagedApexInfos();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(stagedApexInfos, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IPackageManagerNative {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.content.pm.IPackageManagerNative
            public String[] getNamesForUids(int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManagerNative
            public int getPackageUid(String str, long j, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManagerNative
            public String getInstallerForPackage(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManagerNative
            public long getVersionCodeForPackage(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManagerNative
            public boolean[] isAudioPlaybackCaptureAllowed(String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createBooleanArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManagerNative
            public int getLocationFlags(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManagerNative
            public int getTargetSdkVersionForPackage(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManagerNative
            public String getModuleMetadataPackageName() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManagerNative
            public boolean hasSha256SigningCertificate(String str, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManagerNative
            public boolean isPackageDebuggable(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManagerNative
            public boolean hasSystemFeature(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManagerNative
            public void registerStagedApexObserver(IStagedApexObserver iStagedApexObserver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iStagedApexObserver);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManagerNative
            public void unregisterStagedApexObserver(IStagedApexObserver iStagedApexObserver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iStagedApexObserver);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManagerNative
            public StagedApexInfo[] getStagedApexInfos() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return (StagedApexInfo[]) obtain2.createTypedArray(StagedApexInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
