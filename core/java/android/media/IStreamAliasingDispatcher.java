package android.media;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IStreamAliasingDispatcher extends IInterface {
    public static final String DESCRIPTOR = "android.media.IStreamAliasingDispatcher";

    void dispatchStreamAliasingChanged() throws RemoteException;

    public static class Default implements IStreamAliasingDispatcher {
        @Override // android.media.IStreamAliasingDispatcher
        public void dispatchStreamAliasingChanged() throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IStreamAliasingDispatcher {
        static final int TRANSACTION_dispatchStreamAliasingChanged = 1;

        public Stub() {
            attachInterface(this, IStreamAliasingDispatcher.DESCRIPTOR);
        }

        public static IStreamAliasingDispatcher asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IStreamAliasingDispatcher.DESCRIPTOR);
            if (iin != null && (iin instanceof IStreamAliasingDispatcher)) {
                return (IStreamAliasingDispatcher) iin;
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
                    return "dispatchStreamAliasingChanged";
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
                data.enforceInterface(IStreamAliasingDispatcher.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IStreamAliasingDispatcher.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    dispatchStreamAliasingChanged();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IStreamAliasingDispatcher {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IStreamAliasingDispatcher.DESCRIPTOR;
            }

            @Override // android.media.IStreamAliasingDispatcher
            public void dispatchStreamAliasingChanged() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IStreamAliasingDispatcher.DESCRIPTOR);
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
