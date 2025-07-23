package android.telephony.satellite.stub;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface INtnSignalStrengthConsumer extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.satellite.stub.INtnSignalStrengthConsumer";

    public static class Default implements INtnSignalStrengthConsumer {
        @Override // android.telephony.satellite.stub.INtnSignalStrengthConsumer
        public void accept(NtnSignalStrength ntnSignalStrength) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    void accept(NtnSignalStrength ntnSignalStrength) throws RemoteException;

    public static abstract class Stub extends Binder implements INtnSignalStrengthConsumer {
        static final int TRANSACTION_accept = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, INtnSignalStrengthConsumer.DESCRIPTOR);
        }

        public static INtnSignalStrengthConsumer asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(INtnSignalStrengthConsumer.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof INtnSignalStrengthConsumer)) {
                return (INtnSignalStrengthConsumer) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "accept";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(INtnSignalStrengthConsumer.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(INtnSignalStrengthConsumer.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                NtnSignalStrength ntnSignalStrength = (NtnSignalStrength) parcel.readTypedObject(NtnSignalStrength.CREATOR);
                parcel.enforceNoDataAvail();
                accept(ntnSignalStrength);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements INtnSignalStrengthConsumer {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return INtnSignalStrengthConsumer.DESCRIPTOR;
            }

            @Override // android.telephony.satellite.stub.INtnSignalStrengthConsumer
            public void accept(NtnSignalStrength ntnSignalStrength) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(INtnSignalStrengthConsumer.DESCRIPTOR);
                    obtain.writeTypedObject(ntnSignalStrength, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
