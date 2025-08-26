package android.view;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.window.WindowContainerTransaction;

/* loaded from: classes4.dex */
public interface IDisplayChangeWindowCallback extends IInterface {
    public static final String DESCRIPTOR = "android.view.IDisplayChangeWindowCallback";

    public static class Default implements IDisplayChangeWindowCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.view.IDisplayChangeWindowCallback
        public void continueDisplayChange(WindowContainerTransaction windowContainerTransaction) throws RemoteException {
        }
    }

    void continueDisplayChange(WindowContainerTransaction windowContainerTransaction) throws RemoteException;

    public static abstract class Stub extends Binder implements IDisplayChangeWindowCallback {
        static final int TRANSACTION_continueDisplayChange = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IDisplayChangeWindowCallback.DESCRIPTOR);
        }

        public static IDisplayChangeWindowCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IDisplayChangeWindowCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IDisplayChangeWindowCallback)) {
                return (IDisplayChangeWindowCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "continueDisplayChange";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IDisplayChangeWindowCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDisplayChangeWindowCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                WindowContainerTransaction windowContainerTransaction = (WindowContainerTransaction) parcel.readTypedObject(WindowContainerTransaction.CREATOR);
                parcel.enforceNoDataAvail();
                continueDisplayChange(windowContainerTransaction);
                parcel2.writeNoException();
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IDisplayChangeWindowCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDisplayChangeWindowCallback.DESCRIPTOR;
            }

            @Override // android.view.IDisplayChangeWindowCallback
            public void continueDisplayChange(WindowContainerTransaction windowContainerTransaction) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDisplayChangeWindowCallback.DESCRIPTOR);
                    parcelObtain.writeTypedObject(windowContainerTransaction, 0);
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
