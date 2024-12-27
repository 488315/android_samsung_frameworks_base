package android.media;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IAudioRoutesObserver extends IInterface {
    void dispatchAudioRoutesChanged(AudioRoutesInfo audioRoutesInfo) throws RemoteException;

    public static class Default implements IAudioRoutesObserver {
        @Override // android.media.IAudioRoutesObserver
        public void dispatchAudioRoutesChanged(AudioRoutesInfo newRoutes) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IAudioRoutesObserver {
        public static final String DESCRIPTOR = "android.media.IAudioRoutesObserver";
        static final int TRANSACTION_dispatchAudioRoutesChanged = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAudioRoutesObserver asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IAudioRoutesObserver)) {
                return (IAudioRoutesObserver) iin;
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
                    return "dispatchAudioRoutesChanged";
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
                    AudioRoutesInfo _arg0 =
                            (AudioRoutesInfo) data.readTypedObject(AudioRoutesInfo.CREATOR);
                    data.enforceNoDataAvail();
                    dispatchAudioRoutesChanged(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IAudioRoutesObserver {
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

            @Override // android.media.IAudioRoutesObserver
            public void dispatchAudioRoutesChanged(AudioRoutesInfo newRoutes)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(newRoutes, 0);
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
