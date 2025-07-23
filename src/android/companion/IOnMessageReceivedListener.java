package android.companion;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IOnMessageReceivedListener extends IInterface {
    public static final String DESCRIPTOR = "android.companion.IOnMessageReceivedListener";

    public static class Default implements IOnMessageReceivedListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.companion.IOnMessageReceivedListener
        public void onMessageReceived(int i, byte[] bArr) throws RemoteException {
        }
    }

    void onMessageReceived(int i, byte[] bArr) throws RemoteException;

    public static abstract class Stub extends Binder implements IOnMessageReceivedListener {
        static final int TRANSACTION_onMessageReceived = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IOnMessageReceivedListener.DESCRIPTOR);
        }

        public static IOnMessageReceivedListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IOnMessageReceivedListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IOnMessageReceivedListener)) {
                return (IOnMessageReceivedListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onMessageReceived";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IOnMessageReceivedListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IOnMessageReceivedListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                byte[] createByteArray = parcel.createByteArray();
                parcel.enforceNoDataAvail();
                onMessageReceived(readInt, createByteArray);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IOnMessageReceivedListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IOnMessageReceivedListener.DESCRIPTOR;
            }

            @Override // android.companion.IOnMessageReceivedListener
            public void onMessageReceived(int i, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IOnMessageReceivedListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
