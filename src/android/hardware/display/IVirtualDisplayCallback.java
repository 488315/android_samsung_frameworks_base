package android.hardware.display;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IVirtualDisplayCallback extends IInterface {

    public static class Default implements IVirtualDisplayCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.display.IVirtualDisplayCallback
        public void onPaused() throws RemoteException {
        }

        @Override // android.hardware.display.IVirtualDisplayCallback
        public void onResumed() throws RemoteException {
        }

        @Override // android.hardware.display.IVirtualDisplayCallback
        public void onStopped() throws RemoteException {
        }
    }

    void onPaused() throws RemoteException;

    void onResumed() throws RemoteException;

    void onStopped() throws RemoteException;

    public static abstract class Stub extends Binder implements IVirtualDisplayCallback {
        public static final String DESCRIPTOR = "android.hardware.display.IVirtualDisplayCallback";
        static final int TRANSACTION_onPaused = 1;
        static final int TRANSACTION_onResumed = 2;
        static final int TRANSACTION_onStopped = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IVirtualDisplayCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IVirtualDisplayCallback)) {
                return (IVirtualDisplayCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onPaused";
            }
            if (i == 2) {
                return "onResumed";
            }
            if (i != 3) {
                return null;
            }
            return "onStopped";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                onPaused();
            } else if (i == 2) {
                onResumed();
            } else if (i == 3) {
                onStopped();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IVirtualDisplayCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.hardware.display.IVirtualDisplayCallback
            public void onPaused() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IVirtualDisplayCallback
            public void onResumed() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IVirtualDisplayCallback
            public void onStopped() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
