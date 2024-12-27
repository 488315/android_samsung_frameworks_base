package android.permission;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IOnPermissionsChangeListener extends IInterface {
    public static final String DESCRIPTOR = "android.permission.IOnPermissionsChangeListener";

    void onPermissionsChanged(int i, String str) throws RemoteException;

    public static class Default implements IOnPermissionsChangeListener {
        @Override // android.permission.IOnPermissionsChangeListener
        public void onPermissionsChanged(int uid, String persistentDeviceId)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IOnPermissionsChangeListener {
        static final int TRANSACTION_onPermissionsChanged = 1;

        public Stub() {
            attachInterface(this, IOnPermissionsChangeListener.DESCRIPTOR);
        }

        public static IOnPermissionsChangeListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IOnPermissionsChangeListener.DESCRIPTOR);
            if (iin != null && (iin instanceof IOnPermissionsChangeListener)) {
                return (IOnPermissionsChangeListener) iin;
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
                    return "onPermissionsChanged";
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
                data.enforceInterface(IOnPermissionsChangeListener.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IOnPermissionsChangeListener.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    String _arg1 = data.readString();
                    data.enforceNoDataAvail();
                    onPermissionsChanged(_arg0, _arg1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IOnPermissionsChangeListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IOnPermissionsChangeListener.DESCRIPTOR;
            }

            @Override // android.permission.IOnPermissionsChangeListener
            public void onPermissionsChanged(int uid, String persistentDeviceId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IOnPermissionsChangeListener.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeString(persistentDeviceId);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
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
