package android.app.appfunctions;

import android.os.Binder;
import android.os.IBinder;
import android.os.ICancellationSignal;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface ICancellationCallback extends IInterface {
    public static final String DESCRIPTOR = "android.app.appfunctions.ICancellationCallback";

    public static class Default implements ICancellationCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.appfunctions.ICancellationCallback
        public void sendCancellationTransport(ICancellationSignal iCancellationSignal) throws RemoteException {
        }
    }

    void sendCancellationTransport(ICancellationSignal iCancellationSignal) throws RemoteException;

    public static abstract class Stub extends Binder implements ICancellationCallback {
        static final int TRANSACTION_sendCancellationTransport = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, ICancellationCallback.DESCRIPTOR);
        }

        public static ICancellationCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ICancellationCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ICancellationCallback)) {
                return (ICancellationCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "sendCancellationTransport";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ICancellationCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ICancellationCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ICancellationSignal iCancellationSignalAsInterface = ICancellationSignal.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                sendCancellationTransport(iCancellationSignalAsInterface);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ICancellationCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICancellationCallback.DESCRIPTOR;
            }

            @Override // android.app.appfunctions.ICancellationCallback
            public void sendCancellationTransport(ICancellationSignal iCancellationSignal) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICancellationCallback.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iCancellationSignal);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
