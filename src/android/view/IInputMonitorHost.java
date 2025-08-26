package android.view;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IInputMonitorHost extends IInterface {
    public static final String DESCRIPTOR = "android.view.IInputMonitorHost";

    public static class Default implements IInputMonitorHost {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.view.IInputMonitorHost
        public void dispose() throws RemoteException {
        }

        @Override // android.view.IInputMonitorHost
        public void pilferPointers() throws RemoteException {
        }
    }

    void dispose() throws RemoteException;

    void pilferPointers() throws RemoteException;

    public static abstract class Stub extends Binder implements IInputMonitorHost {
        static final int TRANSACTION_dispose = 2;
        static final int TRANSACTION_pilferPointers = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IInputMonitorHost.DESCRIPTOR);
        }

        public static IInputMonitorHost asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IInputMonitorHost.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IInputMonitorHost)) {
                return (IInputMonitorHost) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "pilferPointers";
            }
            if (i != 2) {
                return null;
            }
            return "dispose";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IInputMonitorHost.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IInputMonitorHost.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                pilferPointers();
            } else if (i == 2) {
                dispose();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IInputMonitorHost {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IInputMonitorHost.DESCRIPTOR;
            }

            @Override // android.view.IInputMonitorHost
            public void pilferPointers() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMonitorHost.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IInputMonitorHost
            public void dispose() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMonitorHost.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
