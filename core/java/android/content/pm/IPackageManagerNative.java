package android.content.pm;

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

    String getInstallerForPackage(String str) throws RemoteException;

    int getLocationFlags(String str) throws RemoteException;

    String getModuleMetadataPackageName() throws RemoteException;

    String[] getNamesForUids(int[] iArr) throws RemoteException;

    int getPackageUid(String str, long j, int i) throws RemoteException;

    StagedApexInfo getStagedApexInfo(String str) throws RemoteException;

    String[] getStagedApexModuleNames() throws RemoteException;

    int getTargetSdkVersionForPackage(String str) throws RemoteException;

    long getVersionCodeForPackage(String str) throws RemoteException;

    boolean hasSha256SigningCertificate(String str, byte[] bArr) throws RemoteException;

    boolean hasSystemFeature(String str, int i) throws RemoteException;

    boolean[] isAudioPlaybackCaptureAllowed(String[] strArr) throws RemoteException;

    boolean isPackageDebuggable(String str) throws RemoteException;

    void registerStagedApexObserver(IStagedApexObserver iStagedApexObserver) throws RemoteException;

    void unregisterStagedApexObserver(IStagedApexObserver iStagedApexObserver)
            throws RemoteException;

    public static class Default implements IPackageManagerNative {
        @Override // android.content.pm.IPackageManagerNative
        public String[] getNamesForUids(int[] uids) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManagerNative
        public int getPackageUid(String packageName, long flags, int userId)
                throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManagerNative
        public String getInstallerForPackage(String packageName) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManagerNative
        public long getVersionCodeForPackage(String packageName) throws RemoteException {
            return 0L;
        }

        @Override // android.content.pm.IPackageManagerNative
        public boolean[] isAudioPlaybackCaptureAllowed(String[] packageNames)
                throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManagerNative
        public int getLocationFlags(String packageName) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManagerNative
        public int getTargetSdkVersionForPackage(String packageName) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManagerNative
        public String getModuleMetadataPackageName() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManagerNative
        public boolean hasSha256SigningCertificate(String packageName, byte[] certificate)
                throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManagerNative
        public boolean isPackageDebuggable(String packageName) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManagerNative
        public boolean hasSystemFeature(String featureName, int version) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManagerNative
        public void registerStagedApexObserver(IStagedApexObserver observer)
                throws RemoteException {}

        @Override // android.content.pm.IPackageManagerNative
        public void unregisterStagedApexObserver(IStagedApexObserver observer)
                throws RemoteException {}

        @Override // android.content.pm.IPackageManagerNative
        public String[] getStagedApexModuleNames() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManagerNative
        public StagedApexInfo getStagedApexInfo(String moduleName) throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IPackageManagerNative {
        public static final String DESCRIPTOR = "android.content.pm.IPackageManagerNative";
        static final int TRANSACTION_getInstallerForPackage = 3;
        static final int TRANSACTION_getLocationFlags = 6;
        static final int TRANSACTION_getModuleMetadataPackageName = 8;
        static final int TRANSACTION_getNamesForUids = 1;
        static final int TRANSACTION_getPackageUid = 2;
        static final int TRANSACTION_getStagedApexInfo = 15;
        static final int TRANSACTION_getStagedApexModuleNames = 14;
        static final int TRANSACTION_getTargetSdkVersionForPackage = 7;
        static final int TRANSACTION_getVersionCodeForPackage = 4;
        static final int TRANSACTION_hasSha256SigningCertificate = 9;
        static final int TRANSACTION_hasSystemFeature = 11;
        static final int TRANSACTION_isAudioPlaybackCaptureAllowed = 5;
        static final int TRANSACTION_isPackageDebuggable = 10;
        static final int TRANSACTION_registerStagedApexObserver = 12;
        static final int TRANSACTION_unregisterStagedApexObserver = 13;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IPackageManagerNative asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IPackageManagerNative)) {
                return (IPackageManagerNative) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int[] _arg0 = data.createIntArray();
                    data.enforceNoDataAvail();
                    String[] _result = getNamesForUids(_arg0);
                    reply.writeNoException();
                    reply.writeStringArray(_result);
                    return true;
                case 2:
                    String _arg02 = data.readString();
                    long _arg1 = data.readLong();
                    int _arg2 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result2 = getPackageUid(_arg02, _arg1, _arg2);
                    reply.writeNoException();
                    reply.writeInt(_result2);
                    return true;
                case 3:
                    String _arg03 = data.readString();
                    data.enforceNoDataAvail();
                    String _result3 = getInstallerForPackage(_arg03);
                    reply.writeNoException();
                    reply.writeString(_result3);
                    return true;
                case 4:
                    String _arg04 = data.readString();
                    data.enforceNoDataAvail();
                    long _result4 = getVersionCodeForPackage(_arg04);
                    reply.writeNoException();
                    reply.writeLong(_result4);
                    return true;
                case 5:
                    String[] _arg05 = data.createStringArray();
                    data.enforceNoDataAvail();
                    boolean[] _result5 = isAudioPlaybackCaptureAllowed(_arg05);
                    reply.writeNoException();
                    reply.writeBooleanArray(_result5);
                    return true;
                case 6:
                    String _arg06 = data.readString();
                    data.enforceNoDataAvail();
                    int _result6 = getLocationFlags(_arg06);
                    reply.writeNoException();
                    reply.writeInt(_result6);
                    return true;
                case 7:
                    String _arg07 = data.readString();
                    data.enforceNoDataAvail();
                    int _result7 = getTargetSdkVersionForPackage(_arg07);
                    reply.writeNoException();
                    reply.writeInt(_result7);
                    return true;
                case 8:
                    String _result8 = getModuleMetadataPackageName();
                    reply.writeNoException();
                    reply.writeString(_result8);
                    return true;
                case 9:
                    String _arg08 = data.readString();
                    byte[] _arg12 = data.createByteArray();
                    data.enforceNoDataAvail();
                    boolean _result9 = hasSha256SigningCertificate(_arg08, _arg12);
                    reply.writeNoException();
                    reply.writeBoolean(_result9);
                    return true;
                case 10:
                    String _arg09 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result10 = isPackageDebuggable(_arg09);
                    reply.writeNoException();
                    reply.writeBoolean(_result10);
                    return true;
                case 11:
                    String _arg010 = data.readString();
                    int _arg13 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result11 = hasSystemFeature(_arg010, _arg13);
                    reply.writeNoException();
                    reply.writeBoolean(_result11);
                    return true;
                case 12:
                    IStagedApexObserver _arg011 =
                            IStagedApexObserver.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerStagedApexObserver(_arg011);
                    reply.writeNoException();
                    return true;
                case 13:
                    IStagedApexObserver _arg012 =
                            IStagedApexObserver.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterStagedApexObserver(_arg012);
                    reply.writeNoException();
                    return true;
                case 14:
                    String[] _result12 = getStagedApexModuleNames();
                    reply.writeNoException();
                    reply.writeStringArray(_result12);
                    return true;
                case 15:
                    String _arg013 = data.readString();
                    data.enforceNoDataAvail();
                    StagedApexInfo _result13 = getStagedApexInfo(_arg013);
                    reply.writeNoException();
                    reply.writeTypedObject(_result13, 1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IPackageManagerNative {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.content.pm.IPackageManagerNative
            public String[] getNamesForUids(int[] uids) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeIntArray(uids);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManagerNative
            public int getPackageUid(String packageName, long flags, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeLong(flags);
                    _data.writeInt(userId);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManagerNative
            public String getInstallerForPackage(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManagerNative
            public long getVersionCodeForPackage(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManagerNative
            public boolean[] isAudioPlaybackCaptureAllowed(String[] packageNames)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringArray(packageNames);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    boolean[] _result = _reply.createBooleanArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManagerNative
            public int getLocationFlags(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManagerNative
            public int getTargetSdkVersionForPackage(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManagerNative
            public String getModuleMetadataPackageName() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManagerNative
            public boolean hasSha256SigningCertificate(String packageName, byte[] certificate)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeByteArray(certificate);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManagerNative
            public boolean isPackageDebuggable(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManagerNative
            public boolean hasSystemFeature(String featureName, int version)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(featureName);
                    _data.writeInt(version);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManagerNative
            public void registerStagedApexObserver(IStagedApexObserver observer)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(observer);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManagerNative
            public void unregisterStagedApexObserver(IStagedApexObserver observer)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(observer);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManagerNative
            public String[] getStagedApexModuleNames() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManagerNative
            public StagedApexInfo getStagedApexInfo(String moduleName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(moduleName);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                    StagedApexInfo _result =
                            (StagedApexInfo) _reply.readTypedObject(StagedApexInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }
    }
}
