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

    void onFailure(CharSequence charSequence) throws RemoteException;

    void onSuccess(ConvertCredentialResponse convertCredentialResponse) throws RemoteException;

    public static class Default implements IConvertCredentialCallback {
        @Override // android.service.autofill.IConvertCredentialCallback
        public void onSuccess(ConvertCredentialResponse convertCredentialResponse) throws RemoteException {
        }

        @Override // android.service.autofill.IConvertCredentialCallback
        public void onFailure(CharSequence message) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IConvertCredentialCallback {
        static final int TRANSACTION_onFailure = 2;
        static final int TRANSACTION_onSuccess = 1;

        public Stub() {
            attachInterface(this, IConvertCredentialCallback.DESCRIPTOR);
        }

        public static IConvertCredentialCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IConvertCredentialCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IConvertCredentialCallback)) {
                return (IConvertCredentialCallback) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onSuccess";
                case 2:
                    return "onFailure";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IConvertCredentialCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IConvertCredentialCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    ConvertCredentialResponse _arg0 = (ConvertCredentialResponse) data.readTypedObject(ConvertCredentialResponse.CREATOR);
                    data.enforceNoDataAvail();
                    onSuccess(_arg0);
                    return true;
                case 2:
                    CharSequence _arg02 = (CharSequence) data.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    data.enforceNoDataAvail();
                    onFailure(_arg02);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IConvertCredentialCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
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
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IConvertCredentialCallback.DESCRIPTOR);
                    _data.writeTypedObject(convertCredentialResponse, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.autofill.IConvertCredentialCallback
            public void onFailure(CharSequence message) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IConvertCredentialCallback.DESCRIPTOR);
                    if (message != null) {
                        _data.writeInt(1);
                        TextUtils.writeToParcel(message, _data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
