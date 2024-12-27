package android.companion.virtual;

import android.content.ComponentName;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IVirtualDeviceActivityListener extends IInterface {
    public static final String DESCRIPTOR =
            "android.companion.virtual.IVirtualDeviceActivityListener";

    void onDisplayEmpty(int i) throws RemoteException;

    void onTopActivityChanged(int i, ComponentName componentName, int i2) throws RemoteException;

    public static class Default implements IVirtualDeviceActivityListener {
        @Override // android.companion.virtual.IVirtualDeviceActivityListener
        public void onTopActivityChanged(int displayId, ComponentName topActivity, int userId)
                throws RemoteException {}

        @Override // android.companion.virtual.IVirtualDeviceActivityListener
        public void onDisplayEmpty(int displayId) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IVirtualDeviceActivityListener {
        static final int TRANSACTION_onDisplayEmpty = 2;
        static final int TRANSACTION_onTopActivityChanged = 1;

        public Stub() {
            attachInterface(this, IVirtualDeviceActivityListener.DESCRIPTOR);
        }

        public static IVirtualDeviceActivityListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IVirtualDeviceActivityListener.DESCRIPTOR);
            if (iin != null && (iin instanceof IVirtualDeviceActivityListener)) {
                return (IVirtualDeviceActivityListener) iin;
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
                    return "onTopActivityChanged";
                case 2:
                    return "onDisplayEmpty";
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
                data.enforceInterface(IVirtualDeviceActivityListener.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IVirtualDeviceActivityListener.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    ComponentName _arg1 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    int _arg2 = data.readInt();
                    data.enforceNoDataAvail();
                    onTopActivityChanged(_arg0, _arg1, _arg2);
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    data.enforceNoDataAvail();
                    onDisplayEmpty(_arg02);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IVirtualDeviceActivityListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IVirtualDeviceActivityListener.DESCRIPTOR;
            }

            @Override // android.companion.virtual.IVirtualDeviceActivityListener
            public void onTopActivityChanged(int displayId, ComponentName topActivity, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IVirtualDeviceActivityListener.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeTypedObject(topActivity, 0);
                    _data.writeInt(userId);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceActivityListener
            public void onDisplayEmpty(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IVirtualDeviceActivityListener.DESCRIPTOR);
                    _data.writeInt(displayId);
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
