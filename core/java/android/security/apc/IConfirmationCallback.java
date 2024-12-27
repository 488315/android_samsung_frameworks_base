package android.security.apc;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IConfirmationCallback extends IInterface {
    public static final String DESCRIPTOR = "android.security.apc.IConfirmationCallback";

    void onCompleted(int i, byte[] bArr) throws RemoteException;

    public static class Default implements IConfirmationCallback {
        @Override // android.security.apc.IConfirmationCallback
        public void onCompleted(int result, byte[] dataConfirmed) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IConfirmationCallback {
        static final int TRANSACTION_onCompleted = 1;

        public Stub() {
            attachInterface(this, IConfirmationCallback.DESCRIPTOR);
        }

        public static IConfirmationCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IConfirmationCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IConfirmationCallback)) {
                return (IConfirmationCallback) iin;
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
                data.enforceInterface(IConfirmationCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IConfirmationCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    byte[] _arg1 = data.createByteArray();
                    data.enforceNoDataAvail();
                    onCompleted(_arg0, _arg1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IConfirmationCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IConfirmationCallback.DESCRIPTOR;
            }

            @Override // android.security.apc.IConfirmationCallback
            public void onCompleted(int result, byte[] dataConfirmed) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IConfirmationCallback.DESCRIPTOR);
                    _data.writeInt(result);
                    _data.writeByteArray(dataConfirmed);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }
    }
}
