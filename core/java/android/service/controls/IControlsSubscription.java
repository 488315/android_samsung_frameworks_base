package android.service.controls;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IControlsSubscription extends IInterface {
    public static final String DESCRIPTOR = "android.service.controls.IControlsSubscription";

    void cancel() throws RemoteException;

    void request(long j) throws RemoteException;

    public static class Default implements IControlsSubscription {
        @Override // android.service.controls.IControlsSubscription
        public void request(long n) throws RemoteException {
        }

        @Override // android.service.controls.IControlsSubscription
        public void cancel() throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IControlsSubscription {
        static final int TRANSACTION_cancel = 2;
        static final int TRANSACTION_request = 1;

        public Stub() {
            attachInterface(this, IControlsSubscription.DESCRIPTOR);
        }

        public static IControlsSubscription asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IControlsSubscription.DESCRIPTOR);
            if (iin != null && (iin instanceof IControlsSubscription)) {
                return (IControlsSubscription) iin;
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
                    return "request";
                case 2:
                    return "cancel";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IControlsSubscription.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IControlsSubscription.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    long _arg0 = data.readLong();
                    data.enforceNoDataAvail();
                    request(_arg0);
                    return true;
                case 2:
                    cancel();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IControlsSubscription {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IControlsSubscription.DESCRIPTOR;
            }

            @Override // android.service.controls.IControlsSubscription
            public void request(long n) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IControlsSubscription.DESCRIPTOR);
                    _data.writeLong(n);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.controls.IControlsSubscription
            public void cancel() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IControlsSubscription.DESCRIPTOR);
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
