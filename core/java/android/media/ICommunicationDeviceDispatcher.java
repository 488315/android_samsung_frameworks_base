package android.media;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface ICommunicationDeviceDispatcher extends IInterface {
    public static final String DESCRIPTOR = "android.media.ICommunicationDeviceDispatcher";

    void dispatchCommunicationDeviceChanged(int i) throws RemoteException;

    public static class Default implements ICommunicationDeviceDispatcher {
        @Override // android.media.ICommunicationDeviceDispatcher
        public void dispatchCommunicationDeviceChanged(int portId) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ICommunicationDeviceDispatcher {
        static final int TRANSACTION_dispatchCommunicationDeviceChanged = 1;

        public Stub() {
            attachInterface(this, ICommunicationDeviceDispatcher.DESCRIPTOR);
        }

        public static ICommunicationDeviceDispatcher asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ICommunicationDeviceDispatcher.DESCRIPTOR);
            if (iin != null && (iin instanceof ICommunicationDeviceDispatcher)) {
                return (ICommunicationDeviceDispatcher) iin;
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
                    return "dispatchCommunicationDeviceChanged";
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
                data.enforceInterface(ICommunicationDeviceDispatcher.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ICommunicationDeviceDispatcher.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    data.enforceNoDataAvail();
                    dispatchCommunicationDeviceChanged(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ICommunicationDeviceDispatcher {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICommunicationDeviceDispatcher.DESCRIPTOR;
            }

            @Override // android.media.ICommunicationDeviceDispatcher
            public void dispatchCommunicationDeviceChanged(int portId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ICommunicationDeviceDispatcher.DESCRIPTOR);
                    _data.writeInt(portId);
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
