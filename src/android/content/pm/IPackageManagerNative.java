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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IPackageManagerNative)) {
                return (IPackageManagerNative) iInterfaceQueryLocalInterface;
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
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    String[] namesForUids = getNamesForUids(iArrCreateIntArray);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(namesForUids);
                    return true;
                case 2:
                    String string = parcel.readString();
                    long j = parcel.readLong();
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int packageUid = getPackageUid(string, j, i3);
                    parcel2.writeNoException();
                    parcel2.writeInt(packageUid);
                    return true;
                case 3:
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String installerForPackage = getInstallerForPackage(string2);
                    parcel2.writeNoException();
                    parcel2.writeString(installerForPackage);
                    return true;
                case 4:
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long versionCodeForPackage = getVersionCodeForPackage(string3);
                    parcel2.writeNoException();
                    parcel2.writeLong(versionCodeForPackage);
                    return true;
                case 5:
                    String[] strArrCreateStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    boolean[] zArrIsAudioPlaybackCaptureAllowed = isAudioPlaybackCaptureAllowed(strArrCreateStringArray);
                    parcel2.writeNoException();
                    parcel2.writeBooleanArray(zArrIsAudioPlaybackCaptureAllowed);
                    return true;
                case 6:
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int locationFlags = getLocationFlags(string4);
                    parcel2.writeNoException();
                    parcel2.writeInt(locationFlags);
                    return true;
                case 7:
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int targetSdkVersionForPackage = getTargetSdkVersionForPackage(string5);
                    parcel2.writeNoException();
                    parcel2.writeInt(targetSdkVersionForPackage);
                    return true;
                case 8:
                    String moduleMetadataPackageName = getModuleMetadataPackageName();
                    parcel2.writeNoException();
                    parcel2.writeString(moduleMetadataPackageName);
                    return true;
                case 9:
                    String string6 = parcel.readString();
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    boolean zHasSha256SigningCertificate = hasSha256SigningCertificate(string6, bArrCreateByteArray);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasSha256SigningCertificate);
                    return true;
                case 10:
                    String string7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsPackageDebuggable = isPackageDebuggable(string7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsPackageDebuggable);
                    return true;
                case 11:
                    String string8 = parcel.readString();
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zHasSystemFeature = hasSystemFeature(string8, i4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasSystemFeature);
                    return true;
                case 12:
                    IStagedApexObserver iStagedApexObserverAsInterface = IStagedApexObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerStagedApexObserver(iStagedApexObserverAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    IStagedApexObserver iStagedApexObserverAsInterface2 = IStagedApexObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterStagedApexObserver(iStagedApexObserverAsInterface2);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManagerNative
            public int getPackageUid(String str, long j, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManagerNative
            public String getInstallerForPackage(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManagerNative
            public long getVersionCodeForPackage(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManagerNative
            public boolean[] isAudioPlaybackCaptureAllowed(String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createBooleanArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManagerNative
            public int getLocationFlags(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManagerNative
            public int getTargetSdkVersionForPackage(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManagerNative
            public String getModuleMetadataPackageName() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManagerNative
            public boolean hasSha256SigningCertificate(String str, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManagerNative
            public boolean isPackageDebuggable(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManagerNative
            public boolean hasSystemFeature(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManagerNative
            public void registerStagedApexObserver(IStagedApexObserver iStagedApexObserver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iStagedApexObserver);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManagerNative
            public void unregisterStagedApexObserver(IStagedApexObserver iStagedApexObserver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iStagedApexObserver);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManagerNative
            public StagedApexInfo[] getStagedApexInfos() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (StagedApexInfo[]) parcelObtain2.createTypedArray(StagedApexInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
