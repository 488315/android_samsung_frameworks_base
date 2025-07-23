package android.app.prediction;

import android.content.pm.ParceledListSlice;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IPredictionCallback extends IInterface {
    public static final String DESCRIPTOR = "android.app.prediction.IPredictionCallback";

    public static class Default implements IPredictionCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.prediction.IPredictionCallback
        public void onResult(ParceledListSlice parceledListSlice) throws RemoteException {
        }
    }

    void onResult(ParceledListSlice parceledListSlice) throws RemoteException;

    public static abstract class Stub extends Binder implements IPredictionCallback {
        static final int TRANSACTION_onResult = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IPredictionCallback.DESCRIPTOR);
        }

        public static IPredictionCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IPredictionCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IPredictionCallback)) {
                return (IPredictionCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onResult";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IPredictionCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IPredictionCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ParceledListSlice parceledListSlice = (ParceledListSlice) parcel.readTypedObject(ParceledListSlice.CREATOR);
                parcel.enforceNoDataAvail();
                onResult(parceledListSlice);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IPredictionCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IPredictionCallback.DESCRIPTOR;
            }

            @Override // android.app.prediction.IPredictionCallback
            public void onResult(ParceledListSlice parceledListSlice) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IPredictionCallback.DESCRIPTOR);
                    obtain.writeTypedObject(parceledListSlice, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
