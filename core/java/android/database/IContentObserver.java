package android.database;

import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IContentObserver extends IInterface {
    void onChange(boolean z, Uri uri, int i) throws RemoteException;

    void onChangeEtc(boolean z, Uri[] uriArr, int i, int i2) throws RemoteException;

    public static class Default implements IContentObserver {
        @Override // android.database.IContentObserver
        public void onChange(boolean selfUpdate, Uri uri, int userId) throws RemoteException {}

        @Override // android.database.IContentObserver
        public void onChangeEtc(boolean selfUpdate, Uri[] uri, int flags, int userId)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IContentObserver {
        public static final String DESCRIPTOR = "android.database.IContentObserver";
        static final int TRANSACTION_onChange = 1;
        static final int TRANSACTION_onChangeEtc = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IContentObserver asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IContentObserver)) {
                return (IContentObserver) iin;
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
                    return "onChange";
                case 2:
                    return "onChangeEtc";
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
                data.enforceInterface(DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    boolean _arg0 = data.readBoolean();
                    Uri _arg1 = (Uri) data.readTypedObject(Uri.CREATOR);
                    int _arg2 = data.readInt();
                    data.enforceNoDataAvail();
                    onChange(_arg0, _arg1, _arg2);
                    return true;
                case 2:
                    boolean _arg02 = data.readBoolean();
                    Uri[] _arg12 = (Uri[]) data.createTypedArray(Uri.CREATOR);
                    int _arg22 = data.readInt();
                    int _arg3 = data.readInt();
                    data.enforceNoDataAvail();
                    onChangeEtc(_arg02, _arg12, _arg22, _arg3);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IContentObserver {
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

            @Override // android.database.IContentObserver
            public void onChange(boolean selfUpdate, Uri uri, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(selfUpdate);
                    _data.writeTypedObject(uri, 0);
                    _data.writeInt(userId);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.database.IContentObserver
            public void onChangeEtc(boolean selfUpdate, Uri[] uri, int flags, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(selfUpdate);
                    _data.writeTypedArray(uri, 0);
                    _data.writeInt(flags);
                    _data.writeInt(userId);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
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
