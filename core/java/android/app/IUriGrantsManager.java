package android.app;

import android.content.pm.ParceledListSlice;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IUriGrantsManager extends IInterface {
    public static final String DESCRIPTOR = "android.app.IUriGrantsManager";

    int checkGrantUriPermission_ignoreNonSystem(int i, String str, Uri uri, int i2, int i3)
            throws RemoteException;

    void clearGrantedUriPermissions(String str, int i) throws RemoteException;

    ParceledListSlice getGrantedUriPermissions(String str, int i) throws RemoteException;

    ParceledListSlice getUriPermissions(String str, boolean z, boolean z2) throws RemoteException;

    void grantUriPermissionFromOwner(
            IBinder iBinder, int i, String str, Uri uri, int i2, int i3, int i4)
            throws RemoteException;

    void releasePersistableUriPermission(Uri uri, int i, String str, int i2) throws RemoteException;

    void takePersistableUriPermission(Uri uri, int i, String str, int i2) throws RemoteException;

    public static class Default implements IUriGrantsManager {
        @Override // android.app.IUriGrantsManager
        public void takePersistableUriPermission(
                Uri uri, int modeFlags, String toPackage, int userId) throws RemoteException {}

        @Override // android.app.IUriGrantsManager
        public void releasePersistableUriPermission(
                Uri uri, int modeFlags, String toPackage, int userId) throws RemoteException {}

        @Override // android.app.IUriGrantsManager
        public void grantUriPermissionFromOwner(
                IBinder owner,
                int fromUid,
                String targetPkg,
                Uri uri,
                int mode,
                int sourceUserId,
                int targetUserId)
                throws RemoteException {}

        @Override // android.app.IUriGrantsManager
        public ParceledListSlice getGrantedUriPermissions(String packageName, int userId)
                throws RemoteException {
            return null;
        }

        @Override // android.app.IUriGrantsManager
        public void clearGrantedUriPermissions(String packageName, int userId)
                throws RemoteException {}

        @Override // android.app.IUriGrantsManager
        public ParceledListSlice getUriPermissions(
                String packageName, boolean incoming, boolean persistedOnly)
                throws RemoteException {
            return null;
        }

        @Override // android.app.IUriGrantsManager
        public int checkGrantUriPermission_ignoreNonSystem(
                int sourceUid, String targetPkg, Uri uri, int modeFlags, int userId)
                throws RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IUriGrantsManager {
        static final int TRANSACTION_checkGrantUriPermission_ignoreNonSystem = 7;
        static final int TRANSACTION_clearGrantedUriPermissions = 5;
        static final int TRANSACTION_getGrantedUriPermissions = 4;
        static final int TRANSACTION_getUriPermissions = 6;
        static final int TRANSACTION_grantUriPermissionFromOwner = 3;
        static final int TRANSACTION_releasePersistableUriPermission = 2;
        static final int TRANSACTION_takePersistableUriPermission = 1;

        public Stub() {
            attachInterface(this, IUriGrantsManager.DESCRIPTOR);
        }

        public static IUriGrantsManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IUriGrantsManager.DESCRIPTOR);
            if (iin != null && (iin instanceof IUriGrantsManager)) {
                return (IUriGrantsManager) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "takePersistableUriPermission";
                case 2:
                    return "releasePersistableUriPermission";
                case 3:
                    return "grantUriPermissionFromOwner";
                case 4:
                    return "getGrantedUriPermissions";
                case 5:
                    return "clearGrantedUriPermissions";
                case 6:
                    return "getUriPermissions";
                case 7:
                    return "checkGrantUriPermission_ignoreNonSystem";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IUriGrantsManager.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IUriGrantsManager.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    Uri _arg0 = (Uri) data.readTypedObject(Uri.CREATOR);
                    int _arg1 = data.readInt();
                    String _arg2 = data.readString();
                    int _arg3 = data.readInt();
                    data.enforceNoDataAvail();
                    takePersistableUriPermission(_arg0, _arg1, _arg2, _arg3);
                    reply.writeNoException();
                    return true;
                case 2:
                    Uri _arg02 = (Uri) data.readTypedObject(Uri.CREATOR);
                    int _arg12 = data.readInt();
                    String _arg22 = data.readString();
                    int _arg32 = data.readInt();
                    data.enforceNoDataAvail();
                    releasePersistableUriPermission(_arg02, _arg12, _arg22, _arg32);
                    reply.writeNoException();
                    return true;
                case 3:
                    IBinder _arg03 = data.readStrongBinder();
                    int _arg13 = data.readInt();
                    String _arg23 = data.readString();
                    Uri _arg33 = (Uri) data.readTypedObject(Uri.CREATOR);
                    int _arg4 = data.readInt();
                    int _arg5 = data.readInt();
                    int _arg6 = data.readInt();
                    data.enforceNoDataAvail();
                    grantUriPermissionFromOwner(
                            _arg03, _arg13, _arg23, _arg33, _arg4, _arg5, _arg6);
                    reply.writeNoException();
                    return true;
                case 4:
                    String _arg04 = data.readString();
                    int _arg14 = data.readInt();
                    data.enforceNoDataAvail();
                    ParceledListSlice _result = getGrantedUriPermissions(_arg04, _arg14);
                    reply.writeNoException();
                    reply.writeTypedObject(_result, 1);
                    return true;
                case 5:
                    String _arg05 = data.readString();
                    int _arg15 = data.readInt();
                    data.enforceNoDataAvail();
                    clearGrantedUriPermissions(_arg05, _arg15);
                    reply.writeNoException();
                    return true;
                case 6:
                    String _arg06 = data.readString();
                    boolean _arg16 = data.readBoolean();
                    boolean _arg24 = data.readBoolean();
                    data.enforceNoDataAvail();
                    ParceledListSlice _result2 = getUriPermissions(_arg06, _arg16, _arg24);
                    reply.writeNoException();
                    reply.writeTypedObject(_result2, 1);
                    return true;
                case 7:
                    int _arg07 = data.readInt();
                    String _arg17 = data.readString();
                    Uri _arg25 = (Uri) data.readTypedObject(Uri.CREATOR);
                    int _arg34 = data.readInt();
                    int _arg42 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result3 =
                            checkGrantUriPermission_ignoreNonSystem(
                                    _arg07, _arg17, _arg25, _arg34, _arg42);
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IUriGrantsManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IUriGrantsManager.DESCRIPTOR;
            }

            @Override // android.app.IUriGrantsManager
            public void takePersistableUriPermission(
                    Uri uri, int modeFlags, String toPackage, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUriGrantsManager.DESCRIPTOR);
                    _data.writeTypedObject(uri, 0);
                    _data.writeInt(modeFlags);
                    _data.writeString(toPackage);
                    _data.writeInt(userId);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IUriGrantsManager
            public void releasePersistableUriPermission(
                    Uri uri, int modeFlags, String toPackage, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUriGrantsManager.DESCRIPTOR);
                    _data.writeTypedObject(uri, 0);
                    _data.writeInt(modeFlags);
                    _data.writeString(toPackage);
                    _data.writeInt(userId);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IUriGrantsManager
            public void grantUriPermissionFromOwner(
                    IBinder owner,
                    int fromUid,
                    String targetPkg,
                    Uri uri,
                    int mode,
                    int sourceUserId,
                    int targetUserId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUriGrantsManager.DESCRIPTOR);
                    _data.writeStrongBinder(owner);
                    _data.writeInt(fromUid);
                    _data.writeString(targetPkg);
                    _data.writeTypedObject(uri, 0);
                    _data.writeInt(mode);
                    _data.writeInt(sourceUserId);
                    _data.writeInt(targetUserId);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IUriGrantsManager
            public ParceledListSlice getGrantedUriPermissions(String packageName, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUriGrantsManager.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    ParceledListSlice _result =
                            (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IUriGrantsManager
            public void clearGrantedUriPermissions(String packageName, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUriGrantsManager.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IUriGrantsManager
            public ParceledListSlice getUriPermissions(
                    String packageName, boolean incoming, boolean persistedOnly)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUriGrantsManager.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeBoolean(incoming);
                    _data.writeBoolean(persistedOnly);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    ParceledListSlice _result =
                            (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IUriGrantsManager
            public int checkGrantUriPermission_ignoreNonSystem(
                    int sourceUid, String targetPkg, Uri uri, int modeFlags, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUriGrantsManager.DESCRIPTOR);
                    _data.writeInt(sourceUid);
                    _data.writeString(targetPkg);
                    _data.writeTypedObject(uri, 0);
                    _data.writeInt(modeFlags);
                    _data.writeInt(userId);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 6;
        }
    }
}
