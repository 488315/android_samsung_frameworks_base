package android.os;

public interface ISpegHelperService extends IInterface {
    public static final String DESCRIPTOR = "android.os.ISpegHelperService";

    boolean createOrDeleteMarkerFiles(String str, boolean z, int i) throws RemoteException;

    boolean storePrimaryProf(String str, String str2, int i) throws RemoteException;

    public static class Default implements ISpegHelperService {
        @Override // android.os.ISpegHelperService
        public boolean createOrDeleteMarkerFiles(String path, boolean isCreate, int userId)
                throws RemoteException {
            return false;
        }

        @Override // android.os.ISpegHelperService
        public boolean storePrimaryProf(String pkgPath, String profilePath, int userId)
                throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ISpegHelperService {
        static final int TRANSACTION_createOrDeleteMarkerFiles = 1;
        static final int TRANSACTION_storePrimaryProf = 2;

        public Stub() {
            attachInterface(this, ISpegHelperService.DESCRIPTOR);
        }

        public static ISpegHelperService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISpegHelperService.DESCRIPTOR);
            if (iin != null && (iin instanceof ISpegHelperService)) {
                return (ISpegHelperService) iin;
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
                    return "createOrDeleteMarkerFiles";
                case 2:
                    return "storePrimaryProf";
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
                data.enforceInterface(ISpegHelperService.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISpegHelperService.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    boolean _arg1 = data.readBoolean();
                    int _arg2 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result = createOrDeleteMarkerFiles(_arg0, _arg1, _arg2);
                    reply.writeNoException();
                    reply.writeBoolean(_result);
                    return true;
                case 2:
                    String _arg02 = data.readString();
                    String _arg12 = data.readString();
                    int _arg22 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result2 = storePrimaryProf(_arg02, _arg12, _arg22);
                    reply.writeNoException();
                    reply.writeBoolean(_result2);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISpegHelperService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISpegHelperService.DESCRIPTOR;
            }

            @Override // android.os.ISpegHelperService
            public boolean createOrDeleteMarkerFiles(String path, boolean isCreate, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISpegHelperService.DESCRIPTOR);
                    _data.writeString(path);
                    _data.writeBoolean(isCreate);
                    _data.writeInt(userId);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.ISpegHelperService
            public boolean storePrimaryProf(String pkgPath, String profilePath, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISpegHelperService.DESCRIPTOR);
                    _data.writeString(pkgPath);
                    _data.writeString(profilePath);
                    _data.writeInt(userId);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
