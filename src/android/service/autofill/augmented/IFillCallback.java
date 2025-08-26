package android.service.autofill.augmented;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ICancellationSignal;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.service.autofill.Dataset;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public interface IFillCallback extends IInterface {
    public static final String DESCRIPTOR = "android.service.autofill.augmented.IFillCallback";

    public static class Default implements IFillCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.autofill.augmented.IFillCallback
        public void cancel() throws RemoteException {
        }

        @Override // android.service.autofill.augmented.IFillCallback
        public boolean isCompleted() throws RemoteException {
            return false;
        }

        @Override // android.service.autofill.augmented.IFillCallback
        public void onCancellable(ICancellationSignal iCancellationSignal) throws RemoteException {
        }

        @Override // android.service.autofill.augmented.IFillCallback
        public void onSuccess(List<Dataset> list, Bundle bundle, boolean z) throws RemoteException {
        }
    }

    void cancel() throws RemoteException;

    boolean isCompleted() throws RemoteException;

    void onCancellable(ICancellationSignal iCancellationSignal) throws RemoteException;

    void onSuccess(List<Dataset> list, Bundle bundle, boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements IFillCallback {
        static final int TRANSACTION_cancel = 4;
        static final int TRANSACTION_isCompleted = 3;
        static final int TRANSACTION_onCancellable = 1;
        static final int TRANSACTION_onSuccess = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, IFillCallback.DESCRIPTOR);
        }

        public static IFillCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IFillCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IFillCallback)) {
                return (IFillCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onCancellable";
            }
            if (i == 2) {
                return "onSuccess";
            }
            if (i == 3) {
                return "isCompleted";
            }
            if (i != 4) {
                return null;
            }
            return "cancel";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IFillCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IFillCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ICancellationSignal iCancellationSignalAsInterface = ICancellationSignal.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                onCancellable(iCancellationSignalAsInterface);
                parcel2.writeNoException();
            } else if (i == 2) {
                ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(Dataset.CREATOR);
                Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                boolean z = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onSuccess(arrayListCreateTypedArrayList, bundle, z);
                parcel2.writeNoException();
            } else if (i == 3) {
                boolean zIsCompleted = isCompleted();
                parcel2.writeNoException();
                parcel2.writeBoolean(zIsCompleted);
            } else if (i == 4) {
                cancel();
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IFillCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IFillCallback.DESCRIPTOR;
            }

            @Override // android.service.autofill.augmented.IFillCallback
            public void onCancellable(ICancellationSignal iCancellationSignal) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFillCallback.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iCancellationSignal);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.autofill.augmented.IFillCallback
            public void onSuccess(List<Dataset> list, Bundle bundle, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFillCallback.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.autofill.augmented.IFillCallback
            public boolean isCompleted() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFillCallback.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.autofill.augmented.IFillCallback
            public void cancel() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFillCallback.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
