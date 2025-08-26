package android.window;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.SurfaceControl;

/* loaded from: classes5.dex */
public interface IWindowContainerTransactionCallback extends IInterface {
    public static final String DESCRIPTOR = "android.window.IWindowContainerTransactionCallback";

    public static class Default implements IWindowContainerTransactionCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.window.IWindowContainerTransactionCallback
        public void onTransactionReady(int i, SurfaceControl.Transaction transaction) throws RemoteException {
        }
    }

    void onTransactionReady(int i, SurfaceControl.Transaction transaction) throws RemoteException;

    public static abstract class Stub extends Binder implements IWindowContainerTransactionCallback {
        static final int TRANSACTION_onTransactionReady = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IWindowContainerTransactionCallback.DESCRIPTOR);
        }

        public static IWindowContainerTransactionCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IWindowContainerTransactionCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IWindowContainerTransactionCallback)) {
                return (IWindowContainerTransactionCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onTransactionReady";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IWindowContainerTransactionCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IWindowContainerTransactionCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int i3 = parcel.readInt();
                SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) parcel.readTypedObject(SurfaceControl.Transaction.CREATOR);
                parcel.enforceNoDataAvail();
                onTransactionReady(i3, transaction);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IWindowContainerTransactionCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IWindowContainerTransactionCallback.DESCRIPTOR;
            }

            @Override // android.window.IWindowContainerTransactionCallback
            public void onTransactionReady(int i, SurfaceControl.Transaction transaction) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IWindowContainerTransactionCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(transaction, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
