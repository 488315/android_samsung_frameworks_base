package android.service.attention;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IProximityUpdateCallback extends IInterface {
    public static final String DESCRIPTOR = "android.service.attention.IProximityUpdateCallback";

    public static class Default implements IProximityUpdateCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.attention.IProximityUpdateCallback
        public void onProximityUpdate(double d) throws RemoteException {
        }
    }

    void onProximityUpdate(double d) throws RemoteException;

    public static abstract class Stub extends Binder implements IProximityUpdateCallback {
        static final int TRANSACTION_onProximityUpdate = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IProximityUpdateCallback.DESCRIPTOR);
        }

        public static IProximityUpdateCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IProximityUpdateCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IProximityUpdateCallback)) {
                return (IProximityUpdateCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onProximityUpdate";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IProximityUpdateCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IProximityUpdateCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                double readDouble = parcel.readDouble();
                parcel.enforceNoDataAvail();
                onProximityUpdate(readDouble);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IProximityUpdateCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IProximityUpdateCallback.DESCRIPTOR;
            }

            @Override // android.service.attention.IProximityUpdateCallback
            public void onProximityUpdate(double d) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IProximityUpdateCallback.DESCRIPTOR);
                    obtain.writeDouble(d);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
