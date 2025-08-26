package android.service.assist.classification;

import android.os.Binder;
import android.os.IBinder;
import android.os.ICancellationSignal;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IFieldClassificationCallback extends IInterface {
    public static final String DESCRIPTOR = "android.service.assist.classification.IFieldClassificationCallback";

    public static class Default implements IFieldClassificationCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.assist.classification.IFieldClassificationCallback
        public void cancel() throws RemoteException {
        }

        @Override // android.service.assist.classification.IFieldClassificationCallback
        public boolean isCompleted() throws RemoteException {
            return false;
        }

        @Override // android.service.assist.classification.IFieldClassificationCallback
        public void onCancellable(ICancellationSignal iCancellationSignal) throws RemoteException {
        }

        @Override // android.service.assist.classification.IFieldClassificationCallback
        public void onFailure() throws RemoteException {
        }

        @Override // android.service.assist.classification.IFieldClassificationCallback
        public void onSuccess(FieldClassificationResponse fieldClassificationResponse) throws RemoteException {
        }
    }

    void cancel() throws RemoteException;

    boolean isCompleted() throws RemoteException;

    void onCancellable(ICancellationSignal iCancellationSignal) throws RemoteException;

    void onFailure() throws RemoteException;

    void onSuccess(FieldClassificationResponse fieldClassificationResponse) throws RemoteException;

    public static abstract class Stub extends Binder implements IFieldClassificationCallback {
        static final int TRANSACTION_cancel = 5;
        static final int TRANSACTION_isCompleted = 4;
        static final int TRANSACTION_onCancellable = 1;
        static final int TRANSACTION_onFailure = 3;
        static final int TRANSACTION_onSuccess = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }

        public Stub() {
            attachInterface(this, IFieldClassificationCallback.DESCRIPTOR);
        }

        public static IFieldClassificationCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IFieldClassificationCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IFieldClassificationCallback)) {
                return (IFieldClassificationCallback) iInterfaceQueryLocalInterface;
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
                return "onFailure";
            }
            if (i == 4) {
                return "isCompleted";
            }
            if (i != 5) {
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
                parcel.enforceInterface(IFieldClassificationCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IFieldClassificationCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ICancellationSignal iCancellationSignalAsInterface = ICancellationSignal.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                onCancellable(iCancellationSignalAsInterface);
                parcel2.writeNoException();
            } else if (i == 2) {
                FieldClassificationResponse fieldClassificationResponse = (FieldClassificationResponse) parcel.readTypedObject(FieldClassificationResponse.CREATOR);
                parcel.enforceNoDataAvail();
                onSuccess(fieldClassificationResponse);
                parcel2.writeNoException();
            } else if (i == 3) {
                onFailure();
                parcel2.writeNoException();
            } else if (i == 4) {
                boolean zIsCompleted = isCompleted();
                parcel2.writeNoException();
                parcel2.writeBoolean(zIsCompleted);
            } else if (i == 5) {
                cancel();
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IFieldClassificationCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IFieldClassificationCallback.DESCRIPTOR;
            }

            @Override // android.service.assist.classification.IFieldClassificationCallback
            public void onCancellable(ICancellationSignal iCancellationSignal) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFieldClassificationCallback.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iCancellationSignal);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.assist.classification.IFieldClassificationCallback
            public void onSuccess(FieldClassificationResponse fieldClassificationResponse) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFieldClassificationCallback.DESCRIPTOR);
                    parcelObtain.writeTypedObject(fieldClassificationResponse, 0);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.assist.classification.IFieldClassificationCallback
            public void onFailure() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFieldClassificationCallback.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.assist.classification.IFieldClassificationCallback
            public boolean isCompleted() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFieldClassificationCallback.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.assist.classification.IFieldClassificationCallback
            public void cancel() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFieldClassificationCallback.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
