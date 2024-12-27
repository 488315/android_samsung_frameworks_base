package android.hardware.usb;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IUsbOperationInternal extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.usb.IUsbOperationInternal";

    void onOperationComplete(int i) throws RemoteException;

    public static class Default implements IUsbOperationInternal {
        @Override // android.hardware.usb.IUsbOperationInternal
        public void onOperationComplete(int status) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IUsbOperationInternal {
        static final int TRANSACTION_onOperationComplete = 1;

        public Stub() {
            attachInterface(this, IUsbOperationInternal.DESCRIPTOR);
        }

        public static IUsbOperationInternal asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IUsbOperationInternal.DESCRIPTOR);
            if (iin != null && (iin instanceof IUsbOperationInternal)) {
                return (IUsbOperationInternal) iin;
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
                    return "onOperationComplete";
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
                data.enforceInterface(IUsbOperationInternal.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IUsbOperationInternal.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    data.enforceNoDataAvail();
                    onOperationComplete(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IUsbOperationInternal {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IUsbOperationInternal.DESCRIPTOR;
            }

            @Override // android.hardware.usb.IUsbOperationInternal
            public void onOperationComplete(int status) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IUsbOperationInternal.DESCRIPTOR);
                    _data.writeInt(status);
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
