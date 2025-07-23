package android.hardware.location;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IContextHubCallback extends IInterface {

    public static class Default implements IContextHubCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.location.IContextHubCallback
        public void onMessageReceipt(int i, int i2, ContextHubMessage contextHubMessage) throws RemoteException {
        }
    }

    void onMessageReceipt(int i, int i2, ContextHubMessage contextHubMessage) throws RemoteException;

    public static abstract class Stub extends Binder implements IContextHubCallback {
        public static final String DESCRIPTOR = "android.hardware.location.IContextHubCallback";
        static final int TRANSACTION_onMessageReceipt = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IContextHubCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IContextHubCallback)) {
                return (IContextHubCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onMessageReceipt";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                int readInt2 = parcel.readInt();
                ContextHubMessage contextHubMessage = (ContextHubMessage) parcel.readTypedObject(ContextHubMessage.CREATOR);
                parcel.enforceNoDataAvail();
                onMessageReceipt(readInt, readInt2, contextHubMessage);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IContextHubCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.hardware.location.IContextHubCallback
            public void onMessageReceipt(int i, int i2, ContextHubMessage contextHubMessage) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(contextHubMessage, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
