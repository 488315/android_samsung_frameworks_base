package android.service.dreams;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.service.dreams.IDreamOverlayClient;

/* loaded from: classes3.dex */
public interface IDreamOverlayClientCallback extends IInterface {
    public static final String DESCRIPTOR = "android.service.dreams.IDreamOverlayClientCallback";

    public static class Default implements IDreamOverlayClientCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.dreams.IDreamOverlayClientCallback
        public void onDreamOverlayClient(IDreamOverlayClient iDreamOverlayClient) throws RemoteException {
        }
    }

    void onDreamOverlayClient(IDreamOverlayClient iDreamOverlayClient) throws RemoteException;

    public static abstract class Stub extends Binder implements IDreamOverlayClientCallback {
        static final int TRANSACTION_onDreamOverlayClient = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IDreamOverlayClientCallback.DESCRIPTOR);
        }

        public static IDreamOverlayClientCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IDreamOverlayClientCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IDreamOverlayClientCallback)) {
                return (IDreamOverlayClientCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onDreamOverlayClient";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IDreamOverlayClientCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDreamOverlayClientCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                IDreamOverlayClient iDreamOverlayClientAsInterface = IDreamOverlayClient.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                onDreamOverlayClient(iDreamOverlayClientAsInterface);
                parcel2.writeNoException();
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IDreamOverlayClientCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDreamOverlayClientCallback.DESCRIPTOR;
            }

            @Override // android.service.dreams.IDreamOverlayClientCallback
            public void onDreamOverlayClient(IDreamOverlayClient iDreamOverlayClient) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDreamOverlayClientCallback.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iDreamOverlayClient);
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
