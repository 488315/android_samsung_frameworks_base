package android.telephony.satellite;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface INtnSignalStrengthCallback extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.satellite.INtnSignalStrengthCallback";

    public static class Default implements INtnSignalStrengthCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.telephony.satellite.INtnSignalStrengthCallback
        public void onNtnSignalStrengthChanged(NtnSignalStrength ntnSignalStrength) throws RemoteException {
        }
    }

    void onNtnSignalStrengthChanged(NtnSignalStrength ntnSignalStrength) throws RemoteException;

    public static abstract class Stub extends Binder implements INtnSignalStrengthCallback {
        static final int TRANSACTION_onNtnSignalStrengthChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, INtnSignalStrengthCallback.DESCRIPTOR);
        }

        public static INtnSignalStrengthCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(INtnSignalStrengthCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof INtnSignalStrengthCallback)) {
                return (INtnSignalStrengthCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onNtnSignalStrengthChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(INtnSignalStrengthCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(INtnSignalStrengthCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                NtnSignalStrength ntnSignalStrength = (NtnSignalStrength) parcel.readTypedObject(NtnSignalStrength.CREATOR);
                parcel.enforceNoDataAvail();
                onNtnSignalStrengthChanged(ntnSignalStrength);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements INtnSignalStrengthCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return INtnSignalStrengthCallback.DESCRIPTOR;
            }

            @Override // android.telephony.satellite.INtnSignalStrengthCallback
            public void onNtnSignalStrengthChanged(NtnSignalStrength ntnSignalStrength) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(INtnSignalStrengthCallback.DESCRIPTOR);
                    obtain.writeTypedObject(ntnSignalStrength, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
