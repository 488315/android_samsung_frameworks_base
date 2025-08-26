package android.debug;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IAdbTransport extends IInterface {
    public static final String DESCRIPTOR = "android.debug.IAdbTransport";

    public static class Default implements IAdbTransport {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.debug.IAdbTransport
        public void onAdbEnabled(boolean z, byte b) throws RemoteException {
        }
    }

    void onAdbEnabled(boolean z, byte b) throws RemoteException;

    public static abstract class Stub extends Binder implements IAdbTransport {
        static final int TRANSACTION_onAdbEnabled = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IAdbTransport.DESCRIPTOR);
        }

        public static IAdbTransport asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IAdbTransport.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IAdbTransport)) {
                return (IAdbTransport) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onAdbEnabled";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IAdbTransport.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IAdbTransport.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                boolean z = parcel.readBoolean();
                byte b = parcel.readByte();
                parcel.enforceNoDataAvail();
                onAdbEnabled(z, b);
                parcel2.writeNoException();
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IAdbTransport {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAdbTransport.DESCRIPTOR;
            }

            @Override // android.debug.IAdbTransport
            public void onAdbEnabled(boolean z, byte b) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAdbTransport.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeByte(b);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
