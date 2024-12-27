package com.android.internal.inputmethod;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.text.TextUtils;

/* loaded from: classes5.dex */
public interface IConnectionlessHandwritingCallback extends IInterface {
    public static final String DESCRIPTOR =
            "com.android.internal.inputmethod.IConnectionlessHandwritingCallback";

    void onError(int i) throws RemoteException;

    void onResult(CharSequence charSequence) throws RemoteException;

    public static class Default implements IConnectionlessHandwritingCallback {
        @Override // com.android.internal.inputmethod.IConnectionlessHandwritingCallback
        public void onResult(CharSequence text) throws RemoteException {}

        @Override // com.android.internal.inputmethod.IConnectionlessHandwritingCallback
        public void onError(int errorCode) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IConnectionlessHandwritingCallback {
        static final int TRANSACTION_onError = 2;
        static final int TRANSACTION_onResult = 1;

        public Stub() {
            attachInterface(this, IConnectionlessHandwritingCallback.DESCRIPTOR);
        }

        public static IConnectionlessHandwritingCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IConnectionlessHandwritingCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IConnectionlessHandwritingCallback)) {
                return (IConnectionlessHandwritingCallback) iin;
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
                    return "onResult";
                case 2:
                    return "onError";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IConnectionlessHandwritingCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IConnectionlessHandwritingCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    CharSequence _arg0 =
                            (CharSequence) data.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    data.enforceNoDataAvail();
                    onResult(_arg0);
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    data.enforceNoDataAvail();
                    onError(_arg02);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IConnectionlessHandwritingCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IConnectionlessHandwritingCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.inputmethod.IConnectionlessHandwritingCallback
            public void onResult(CharSequence text) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IConnectionlessHandwritingCallback.DESCRIPTOR);
                    if (text != null) {
                        _data.writeInt(1);
                        TextUtils.writeToParcel(text, _data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IConnectionlessHandwritingCallback
            public void onError(int errorCode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IConnectionlessHandwritingCallback.DESCRIPTOR);
                    _data.writeInt(errorCode);
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
