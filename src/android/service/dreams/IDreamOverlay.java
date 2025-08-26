package android.service.dreams;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.service.dreams.IDreamOverlayClientCallback;

/* loaded from: classes3.dex */
public interface IDreamOverlay extends IInterface {
    public static final String DESCRIPTOR = "android.service.dreams.IDreamOverlay";

    public static class Default implements IDreamOverlay {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.dreams.IDreamOverlay
        public void getClient(IDreamOverlayClientCallback iDreamOverlayClientCallback) throws RemoteException {
        }
    }

    void getClient(IDreamOverlayClientCallback iDreamOverlayClientCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IDreamOverlay {
        static final int TRANSACTION_getClient = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IDreamOverlay.DESCRIPTOR);
        }

        public static IDreamOverlay asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IDreamOverlay.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IDreamOverlay)) {
                return (IDreamOverlay) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "getClient";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IDreamOverlay.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDreamOverlay.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                IDreamOverlayClientCallback iDreamOverlayClientCallbackAsInterface = IDreamOverlayClientCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                getClient(iDreamOverlayClientCallbackAsInterface);
                parcel2.writeNoException();
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IDreamOverlay {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDreamOverlay.DESCRIPTOR;
            }

            @Override // android.service.dreams.IDreamOverlay
            public void getClient(IDreamOverlayClientCallback iDreamOverlayClientCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDreamOverlay.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iDreamOverlayClientCallback);
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
