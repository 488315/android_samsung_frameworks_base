package android.service.credentials;

import android.os.Binder;
import android.os.IBinder;
import android.os.ICancellationSignal;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.text.TextUtils;

/* loaded from: classes3.dex */
public interface IBeginGetCredentialCallback extends IInterface {
    public static final String DESCRIPTOR = "android.service.credentials.IBeginGetCredentialCallback";

    public static class Default implements IBeginGetCredentialCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.credentials.IBeginGetCredentialCallback
        public void onCancellable(ICancellationSignal iCancellationSignal) throws RemoteException {
        }

        @Override // android.service.credentials.IBeginGetCredentialCallback
        public void onFailure(String str, CharSequence charSequence) throws RemoteException {
        }

        @Override // android.service.credentials.IBeginGetCredentialCallback
        public void onSuccess(BeginGetCredentialResponse beginGetCredentialResponse) throws RemoteException {
        }
    }

    void onCancellable(ICancellationSignal iCancellationSignal) throws RemoteException;

    void onFailure(String str, CharSequence charSequence) throws RemoteException;

    void onSuccess(BeginGetCredentialResponse beginGetCredentialResponse) throws RemoteException;

    public static abstract class Stub extends Binder implements IBeginGetCredentialCallback {
        static final int TRANSACTION_onCancellable = 3;
        static final int TRANSACTION_onFailure = 2;
        static final int TRANSACTION_onSuccess = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, IBeginGetCredentialCallback.DESCRIPTOR);
        }

        public static IBeginGetCredentialCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IBeginGetCredentialCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IBeginGetCredentialCallback)) {
                return (IBeginGetCredentialCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onSuccess";
            }
            if (i == 2) {
                return "onFailure";
            }
            if (i != 3) {
                return null;
            }
            return "onCancellable";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IBeginGetCredentialCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IBeginGetCredentialCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                BeginGetCredentialResponse beginGetCredentialResponse = (BeginGetCredentialResponse) parcel.readTypedObject(BeginGetCredentialResponse.CREATOR);
                parcel.enforceNoDataAvail();
                onSuccess(beginGetCredentialResponse);
            } else if (i == 2) {
                String string = parcel.readString();
                CharSequence charSequence = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                parcel.enforceNoDataAvail();
                onFailure(string, charSequence);
            } else if (i == 3) {
                ICancellationSignal iCancellationSignalAsInterface = ICancellationSignal.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                onCancellable(iCancellationSignalAsInterface);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IBeginGetCredentialCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IBeginGetCredentialCallback.DESCRIPTOR;
            }

            @Override // android.service.credentials.IBeginGetCredentialCallback
            public void onSuccess(BeginGetCredentialResponse beginGetCredentialResponse) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IBeginGetCredentialCallback.DESCRIPTOR);
                    parcelObtain.writeTypedObject(beginGetCredentialResponse, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.credentials.IBeginGetCredentialCallback
            public void onFailure(String str, CharSequence charSequence) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IBeginGetCredentialCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    if (charSequence != null) {
                        parcelObtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.credentials.IBeginGetCredentialCallback
            public void onCancellable(ICancellationSignal iCancellationSignal) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IBeginGetCredentialCallback.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iCancellationSignal);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
