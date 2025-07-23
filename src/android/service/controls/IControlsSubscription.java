package android.service.controls;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IControlsSubscription extends IInterface {
    public static final String DESCRIPTOR = "android.service.controls.IControlsSubscription";

    public static class Default implements IControlsSubscription {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.controls.IControlsSubscription
        public void cancel() throws RemoteException {
        }

        @Override // android.service.controls.IControlsSubscription
        public void request(long j) throws RemoteException {
        }
    }

    void cancel() throws RemoteException;

    void request(long j) throws RemoteException;

    public static abstract class Stub extends Binder implements IControlsSubscription {
        static final int TRANSACTION_cancel = 2;
        static final int TRANSACTION_request = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IControlsSubscription.DESCRIPTOR);
        }

        public static IControlsSubscription asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IControlsSubscription.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IControlsSubscription)) {
                return (IControlsSubscription) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "request";
            }
            if (i != 2) {
                return null;
            }
            return "cancel";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IControlsSubscription.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IControlsSubscription.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                long readLong = parcel.readLong();
                parcel.enforceNoDataAvail();
                request(readLong);
            } else if (i == 2) {
                cancel();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IControlsSubscription {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IControlsSubscription.DESCRIPTOR;
            }

            @Override // android.service.controls.IControlsSubscription
            public void request(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IControlsSubscription.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.controls.IControlsSubscription
            public void cancel() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IControlsSubscription.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
