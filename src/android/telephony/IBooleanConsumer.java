package android.telephony;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IBooleanConsumer extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.IBooleanConsumer";

    public static class Default implements IBooleanConsumer {
        @Override // android.telephony.IBooleanConsumer
        public void accept(boolean z) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    void accept(boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements IBooleanConsumer {
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
            attachInterface(this, IBooleanConsumer.DESCRIPTOR);
        }

        public static IBooleanConsumer asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IBooleanConsumer.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IBooleanConsumer)) {
                return (IBooleanConsumer) queryLocalInterface;
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
                parcel.enforceInterface(IBooleanConsumer.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IBooleanConsumer.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                accept(readBoolean);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IBooleanConsumer {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IBooleanConsumer.DESCRIPTOR;
            }

            @Override // android.telephony.IBooleanConsumer
            public void accept(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IBooleanConsumer.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
