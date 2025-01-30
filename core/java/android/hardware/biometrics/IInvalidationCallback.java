package android.hardware.biometrics;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes.dex */
public interface IInvalidationCallback extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.biometrics.IInvalidationCallback";

    void onCompleted() throws RemoteException;

    public static class Default implements IInvalidationCallback {
        @Override // android.hardware.biometrics.IInvalidationCallback
        public void onCompleted() throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IInvalidationCallback {
        static final int TRANSACTION_onCompleted = 1;

        public Stub() {
            attachInterface(this, IInvalidationCallback.DESCRIPTOR);
        }

        public static IInvalidationCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IInvalidationCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IInvalidationCallback)) {
                return (IInvalidationCallback) iin;
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
                    return "onCompleted";
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
                data.enforceInterface(IInvalidationCallback.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(IInvalidationCallback.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            onCompleted();
                            reply.writeNoException();
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IInvalidationCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IInvalidationCallback.DESCRIPTOR;
            }

            @Override // android.hardware.biometrics.IInvalidationCallback
            public void onCompleted() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IInvalidationCallback.DESCRIPTOR);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
