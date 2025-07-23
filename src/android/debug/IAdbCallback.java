package android.debug;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IAdbCallback extends IInterface {
    public static final String DESCRIPTOR = "android.debug.IAdbCallback";

    public static class Default implements IAdbCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.debug.IAdbCallback
        public void onDebuggingChanged(boolean z, byte b) throws RemoteException {
        }
    }

    void onDebuggingChanged(boolean z, byte b) throws RemoteException;

    public static abstract class Stub extends Binder implements IAdbCallback {
        static final int TRANSACTION_onDebuggingChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IAdbCallback.DESCRIPTOR);
        }

        public static IAdbCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IAdbCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IAdbCallback)) {
                return (IAdbCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onDebuggingChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IAdbCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IAdbCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                boolean readBoolean = parcel.readBoolean();
                byte readByte = parcel.readByte();
                parcel.enforceNoDataAvail();
                onDebuggingChanged(readBoolean, readByte);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IAdbCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAdbCallback.DESCRIPTOR;
            }

            @Override // android.debug.IAdbCallback
            public void onDebuggingChanged(boolean z, byte b) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IAdbCallback.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeByte(b);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
