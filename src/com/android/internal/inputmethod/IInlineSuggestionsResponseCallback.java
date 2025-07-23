package com.android.internal.inputmethod;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.autofill.AutofillId;
import android.view.inputmethod.InlineSuggestionsResponse;

/* loaded from: classes5.dex */
public interface IInlineSuggestionsResponseCallback extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.inputmethod.IInlineSuggestionsResponseCallback";

    public static class Default implements IInlineSuggestionsResponseCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.inputmethod.IInlineSuggestionsResponseCallback
        public void onInlineSuggestionsResponse(AutofillId autofillId, InlineSuggestionsResponse inlineSuggestionsResponse) throws RemoteException {
        }
    }

    void onInlineSuggestionsResponse(AutofillId autofillId, InlineSuggestionsResponse inlineSuggestionsResponse) throws RemoteException;

    public static abstract class Stub extends Binder implements IInlineSuggestionsResponseCallback {
        static final int TRANSACTION_onInlineSuggestionsResponse = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IInlineSuggestionsResponseCallback.DESCRIPTOR);
        }

        public static IInlineSuggestionsResponseCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IInlineSuggestionsResponseCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IInlineSuggestionsResponseCallback)) {
                return (IInlineSuggestionsResponseCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onInlineSuggestionsResponse";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IInlineSuggestionsResponseCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IInlineSuggestionsResponseCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                AutofillId autofillId = (AutofillId) parcel.readTypedObject(AutofillId.CREATOR);
                InlineSuggestionsResponse inlineSuggestionsResponse = (InlineSuggestionsResponse) parcel.readTypedObject(InlineSuggestionsResponse.CREATOR);
                parcel.enforceNoDataAvail();
                onInlineSuggestionsResponse(autofillId, inlineSuggestionsResponse);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IInlineSuggestionsResponseCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IInlineSuggestionsResponseCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.inputmethod.IInlineSuggestionsResponseCallback
            public void onInlineSuggestionsResponse(AutofillId autofillId, InlineSuggestionsResponse inlineSuggestionsResponse) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInlineSuggestionsResponseCallback.DESCRIPTOR);
                    obtain.writeTypedObject(autofillId, 0);
                    obtain.writeTypedObject(inlineSuggestionsResponse, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
