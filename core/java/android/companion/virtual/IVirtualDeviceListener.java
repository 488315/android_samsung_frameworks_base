package android.companion.virtual;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IVirtualDeviceListener extends IInterface {
    public static final String DESCRIPTOR = "android.companion.virtual.IVirtualDeviceListener";

    void onVirtualDeviceClosed(int i) throws RemoteException;

    void onVirtualDeviceCreated(int i) throws RemoteException;

    public static class Default implements IVirtualDeviceListener {
        @Override // android.companion.virtual.IVirtualDeviceListener
        public void onVirtualDeviceCreated(int deviceId) throws RemoteException {}

        @Override // android.companion.virtual.IVirtualDeviceListener
        public void onVirtualDeviceClosed(int deviceId) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IVirtualDeviceListener {
        static final int TRANSACTION_onVirtualDeviceClosed = 2;
        static final int TRANSACTION_onVirtualDeviceCreated = 1;

        public Stub() {
            attachInterface(this, IVirtualDeviceListener.DESCRIPTOR);
        }

        public static IVirtualDeviceListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IVirtualDeviceListener.DESCRIPTOR);
            if (iin != null && (iin instanceof IVirtualDeviceListener)) {
                return (IVirtualDeviceListener) iin;
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
                    return "onVirtualDeviceCreated";
                case 2:
                    return "onVirtualDeviceClosed";
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
                data.enforceInterface(IVirtualDeviceListener.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IVirtualDeviceListener.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    data.enforceNoDataAvail();
                    onVirtualDeviceCreated(_arg0);
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    data.enforceNoDataAvail();
                    onVirtualDeviceClosed(_arg02);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IVirtualDeviceListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IVirtualDeviceListener.DESCRIPTOR;
            }

            @Override // android.companion.virtual.IVirtualDeviceListener
            public void onVirtualDeviceCreated(int deviceId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IVirtualDeviceListener.DESCRIPTOR);
                    _data.writeInt(deviceId);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceListener
            public void onVirtualDeviceClosed(int deviceId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IVirtualDeviceListener.DESCRIPTOR);
                    _data.writeInt(deviceId);
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
