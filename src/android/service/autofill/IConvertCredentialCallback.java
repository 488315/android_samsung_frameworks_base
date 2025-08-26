package android.service.autofill;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.text.TextUtils;

/* loaded from: classes3.dex */
public interface IConvertCredentialCallback extends IInterface {
    public static final String DESCRIPTOR = "android.service.autofill.IConvertCredentialCallback";

    public static class Default implements IConvertCredentialCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.autofill.IConvertCredentialCallback
        public void onFailure(CharSequence charSequence) throws RemoteException {
        }

        @Override // android.service.autofill.IConvertCredentialCallback
        public void onSuccess(ConvertCredentialResponse convertCredentialResponse) throws RemoteException {
        }
    }

    void onFailure(CharSequence charSequence) throws RemoteException;

    void onSuccess(ConvertCredentialResponse convertCredentialResponse) throws RemoteException;

    public static abstract class Stub extends Binder implements IConvertCredentialCallback {
        static final int TRANSACTION_onFailure = 2;
        static final int TRANSACTION_onSuccess = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IConvertCredentialCallback.DESCRIPTOR);
        }

        public static IConvertCredentialCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IConvertCredentialCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IConvertCredentialCallback)) {
                return (IConvertCredentialCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onSuccess";
            }
            if (i != 2) {
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
                parcel.enforceInterface(IConvertCredentialCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IConvertCredentialCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ConvertCredentialResponse convertCredentialResponse = (ConvertCredentialResponse) parcel.readTypedObject(ConvertCredentialResponse.CREATOR);
                parcel.enforceNoDataAvail();
                onSuccess(convertCredentialResponse);
            } else if (i == 2) {
                CharSequence charSequence = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                parcel.enforceNoDataAvail();
                onFailure(charSequence);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IConvertCredentialCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IConvertCredentialCallback.DESCRIPTOR;
            }

            @Override // android.service.autofill.IConvertCredentialCallback
            public void onSuccess(ConvertCredentialResponse convertCredentialResponse) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IConvertCredentialCallback.DESCRIPTOR);
                    parcelObtain.writeTypedObject(convertCredentialResponse, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.autofill.IConvertCredentialCallback
            public void onFailure(CharSequence charSequence) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IConvertCredentialCallback.DESCRIPTOR);
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
        }
    }
}
