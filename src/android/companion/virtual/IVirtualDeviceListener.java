package android.companion.virtual;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IVirtualDeviceListener extends IInterface {
    public static final String DESCRIPTOR = "android.companion.virtual.IVirtualDeviceListener";

    public static class Default implements IVirtualDeviceListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.companion.virtual.IVirtualDeviceListener
        public void onVirtualDeviceClosed(int i) throws RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDeviceListener
        public void onVirtualDeviceCreated(int i) throws RemoteException {
        }
    }

    void onVirtualDeviceClosed(int i) throws RemoteException;

    void onVirtualDeviceCreated(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IVirtualDeviceListener {
        static final int TRANSACTION_onVirtualDeviceClosed = 2;
        static final int TRANSACTION_onVirtualDeviceCreated = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IVirtualDeviceListener.DESCRIPTOR);
        }

        public static IVirtualDeviceListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IVirtualDeviceListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IVirtualDeviceListener)) {
                return (IVirtualDeviceListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onVirtualDeviceCreated";
            }
            if (i != 2) {
                return null;
            }
            return "onVirtualDeviceClosed";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IVirtualDeviceListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IVirtualDeviceListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                onVirtualDeviceCreated(readInt);
            } else if (i == 2) {
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onVirtualDeviceClosed(readInt2);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IVirtualDeviceListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IVirtualDeviceListener.DESCRIPTOR;
            }

            @Override // android.companion.virtual.IVirtualDeviceListener
            public void onVirtualDeviceCreated(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IVirtualDeviceListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceListener
            public void onVirtualDeviceClosed(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IVirtualDeviceListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
