package android.hardware.input;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface ISemLidStateChangedListener extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.input.ISemLidStateChangedListener";

    public static class Default implements ISemLidStateChangedListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.input.ISemLidStateChangedListener
        public void onLidStateChanged(long j, boolean z) throws RemoteException {
        }
    }

    void onLidStateChanged(long j, boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements ISemLidStateChangedListener {
        static final int TRANSACTION_onLidStateChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, ISemLidStateChangedListener.DESCRIPTOR);
        }

        public static ISemLidStateChangedListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISemLidStateChangedListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISemLidStateChangedListener)) {
                return (ISemLidStateChangedListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onLidStateChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISemLidStateChangedListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISemLidStateChangedListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                long readLong = parcel.readLong();
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onLidStateChanged(readLong, readBoolean);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ISemLidStateChangedListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemLidStateChangedListener.DESCRIPTOR;
            }

            @Override // android.hardware.input.ISemLidStateChangedListener
            public void onLidStateChanged(long j, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemLidStateChangedListener.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
