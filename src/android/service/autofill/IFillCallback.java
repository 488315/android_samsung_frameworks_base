package android.service.autofill;

import android.os.Binder;
import android.os.IBinder;
import android.os.ICancellationSignal;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.text.TextUtils;

/* loaded from: classes3.dex */
public interface IFillCallback extends IInterface {

    public static class Default implements IFillCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.autofill.IFillCallback
        public void onCancellable(ICancellationSignal iCancellationSignal) throws RemoteException {
        }

        @Override // android.service.autofill.IFillCallback
        public void onFailure(int i, CharSequence charSequence) throws RemoteException {
        }

        @Override // android.service.autofill.IFillCallback
        public void onSuccess(FillResponse fillResponse) throws RemoteException {
        }
    }

    void onCancellable(ICancellationSignal iCancellationSignal) throws RemoteException;

    void onFailure(int i, CharSequence charSequence) throws RemoteException;

    void onSuccess(FillResponse fillResponse) throws RemoteException;

    public static abstract class Stub extends Binder implements IFillCallback {
        public static final String DESCRIPTOR = "android.service.autofill.IFillCallback";
        static final int TRANSACTION_onCancellable = 1;
        static final int TRANSACTION_onFailure = 3;
        static final int TRANSACTION_onSuccess = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IFillCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IFillCallback)) {
                return (IFillCallback) queryLocalInterface;
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
            if (i != 3) {
                return null;
            }
            return "onFailure";
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
                ICancellationSignal asInterface = ICancellationSignal.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                onCancellable(asInterface);
            } else if (i == 2) {
                FillResponse fillResponse = (FillResponse) parcel.readTypedObject(FillResponse.CREATOR);
                parcel.enforceNoDataAvail();
                onSuccess(fillResponse);
            } else if (i == 3) {
                int readInt = parcel.readInt();
                CharSequence charSequence = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                parcel.enforceNoDataAvail();
                onFailure(readInt, charSequence);
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
                return Stub.DESCRIPTOR;
            }

            @Override // android.service.autofill.IFillCallback
            public void onCancellable(ICancellationSignal iCancellationSignal) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iCancellationSignal);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.autofill.IFillCallback
            public void onSuccess(FillResponse fillResponse) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(fillResponse, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.autofill.IFillCallback
            public void onFailure(int i, CharSequence charSequence) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
