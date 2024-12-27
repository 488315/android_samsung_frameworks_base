package android.app.smartspace;

import android.content.pm.ParceledListSlice;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ISmartspaceCallback extends IInterface {
    public static final String DESCRIPTOR = "android.app.smartspace.ISmartspaceCallback";

    void onResult(ParceledListSlice parceledListSlice) throws RemoteException;

    public static class Default implements ISmartspaceCallback {
        @Override // android.app.smartspace.ISmartspaceCallback
        public void onResult(ParceledListSlice result) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ISmartspaceCallback {
        static final int TRANSACTION_onResult = 1;

        public Stub() {
            attachInterface(this, ISmartspaceCallback.DESCRIPTOR);
        }

        public static ISmartspaceCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISmartspaceCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof ISmartspaceCallback)) {
                return (ISmartspaceCallback) iin;
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
                    return "onResult";
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
                data.enforceInterface(ISmartspaceCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISmartspaceCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    ParceledListSlice _arg0 =
                            (ParceledListSlice) data.readTypedObject(ParceledListSlice.CREATOR);
                    data.enforceNoDataAvail();
                    onResult(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISmartspaceCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISmartspaceCallback.DESCRIPTOR;
            }

            @Override // android.app.smartspace.ISmartspaceCallback
            public void onResult(ParceledListSlice result) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISmartspaceCallback.DESCRIPTOR);
                    _data.writeTypedObject(result, 0);
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
