package com.android.internal.inputmethod;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.text.TextUtils;

/* loaded from: classes5.dex */
public interface IConnectionlessHandwritingCallback extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.inputmethod.IConnectionlessHandwritingCallback";

    public static class Default implements IConnectionlessHandwritingCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.inputmethod.IConnectionlessHandwritingCallback
        public void onError(int i) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IConnectionlessHandwritingCallback
        public void onResult(CharSequence charSequence) throws RemoteException {
        }
    }

    void onError(int i) throws RemoteException;

    void onResult(CharSequence charSequence) throws RemoteException;

    public static abstract class Stub extends Binder implements IConnectionlessHandwritingCallback {
        static final int TRANSACTION_onError = 2;
        static final int TRANSACTION_onResult = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IConnectionlessHandwritingCallback.DESCRIPTOR);
        }

        public static IConnectionlessHandwritingCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IConnectionlessHandwritingCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IConnectionlessHandwritingCallback)) {
                return (IConnectionlessHandwritingCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onResult";
            }
            if (i != 2) {
                return null;
            }
            return "onError";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IConnectionlessHandwritingCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IConnectionlessHandwritingCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                CharSequence charSequence = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                parcel.enforceNoDataAvail();
                onResult(charSequence);
            } else if (i == 2) {
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onError(i3);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IConnectionlessHandwritingCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IConnectionlessHandwritingCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.inputmethod.IConnectionlessHandwritingCallback
            public void onResult(CharSequence charSequence) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IConnectionlessHandwritingCallback.DESCRIPTOR);
                    if (charSequence != null) {
                        parcelObtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IConnectionlessHandwritingCallback
            public void onError(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IConnectionlessHandwritingCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
