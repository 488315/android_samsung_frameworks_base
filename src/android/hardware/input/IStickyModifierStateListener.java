package android.hardware.input;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IStickyModifierStateListener extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.input.IStickyModifierStateListener";

    public static class Default implements IStickyModifierStateListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.input.IStickyModifierStateListener
        public void onStickyModifierStateChanged(int i, int i2) throws RemoteException {
        }
    }

    void onStickyModifierStateChanged(int i, int i2) throws RemoteException;

    public static abstract class Stub extends Binder implements IStickyModifierStateListener {
        static final int TRANSACTION_onStickyModifierStateChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IStickyModifierStateListener.DESCRIPTOR);
        }

        public static IStickyModifierStateListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IStickyModifierStateListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IStickyModifierStateListener)) {
                return (IStickyModifierStateListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onStickyModifierStateChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IStickyModifierStateListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IStickyModifierStateListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onStickyModifierStateChanged(readInt, readInt2);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IStickyModifierStateListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IStickyModifierStateListener.DESCRIPTOR;
            }

            @Override // android.hardware.input.IStickyModifierStateListener
            public void onStickyModifierStateChanged(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IStickyModifierStateListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
