package android.hardware.camera2.extension;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes.dex */
public interface IInitializeSessionCallback extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.camera2.extension.IInitializeSessionCallback";

    void onFailure() throws RemoteException;

    void onSuccess() throws RemoteException;

    public static class Default implements IInitializeSessionCallback {
        @Override // android.hardware.camera2.extension.IInitializeSessionCallback
        public void onSuccess() throws RemoteException {
        }

        @Override // android.hardware.camera2.extension.IInitializeSessionCallback
        public void onFailure() throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IInitializeSessionCallback {
        static final int TRANSACTION_onFailure = 2;
        static final int TRANSACTION_onSuccess = 1;

        public Stub() {
            attachInterface(this, IInitializeSessionCallback.DESCRIPTOR);
        }

        public static IInitializeSessionCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IInitializeSessionCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IInitializeSessionCallback)) {
                return (IInitializeSessionCallback) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onSuccess";
                case 2:
                    return "onFailure";
                default:
                    return null;
            }
        }

        @Override // android.p009os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p009os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IInitializeSessionCallback.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(IInitializeSessionCallback.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            onSuccess();
                            reply.writeNoException();
                            return true;
                        case 2:
                            onFailure();
                            reply.writeNoException();
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IInitializeSessionCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IInitializeSessionCallback.DESCRIPTOR;
            }

            @Override // android.hardware.camera2.extension.IInitializeSessionCallback
            public void onSuccess() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IInitializeSessionCallback.DESCRIPTOR);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IInitializeSessionCallback
            public void onFailure() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IInitializeSessionCallback.DESCRIPTOR);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
