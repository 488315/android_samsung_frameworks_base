package android.os;

/* loaded from: classes3.dex */
public interface ISpqrService extends IInterface {
    public static final String DESCRIPTOR = "android.os.ISpqrService";

    boolean createInvariantProfile(
            String str,
            String str2,
            int i,
            int i2,
            String str3,
            String str4,
            String str5,
            String str6)
            throws RemoteException;

    public static class Default implements ISpqrService {
        @Override // android.os.ISpqrService
        public boolean createInvariantProfile(
                String oldPkgPath,
                String newPkgPath,
                int userId,
                int appId,
                String packageName,
                String profPath,
                String oldVersionName,
                String newVersionName)
                throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ISpqrService {
        static final int TRANSACTION_createInvariantProfile = 1;

        public Stub() {
            attachInterface(this, ISpqrService.DESCRIPTOR);
        }

        public static ISpqrService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISpqrService.DESCRIPTOR);
            if (iin != null && (iin instanceof ISpqrService)) {
                return (ISpqrService) iin;
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
                    return "createInvariantProfile";
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
                data.enforceInterface(ISpqrService.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISpqrService.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    String _arg1 = data.readString();
                    int _arg2 = data.readInt();
                    int _arg3 = data.readInt();
                    String _arg4 = data.readString();
                    String _arg5 = data.readString();
                    String _arg6 = data.readString();
                    String _arg7 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result =
                            createInvariantProfile(
                                    _arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6, _arg7);
                    reply.writeNoException();
                    reply.writeBoolean(_result);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISpqrService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISpqrService.DESCRIPTOR;
            }

            @Override // android.os.ISpqrService
            public boolean createInvariantProfile(
                    String oldPkgPath,
                    String newPkgPath,
                    int userId,
                    int appId,
                    String packageName,
                    String profPath,
                    String oldVersionName,
                    String newVersionName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISpqrService.DESCRIPTOR);
                    _data.writeString(oldPkgPath);
                    _data.writeString(newPkgPath);
                    _data.writeInt(userId);
                    _data.writeInt(appId);
                    _data.writeString(packageName);
                    _data.writeString(profPath);
                    _data.writeString(oldVersionName);
                    _data.writeString(newVersionName);
                    this.mRemote.transact(1, _data, _reply, 0);
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
            return 0;
        }
    }
}
