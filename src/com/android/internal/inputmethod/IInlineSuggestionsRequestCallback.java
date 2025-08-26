package com.android.internal.inputmethod;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.autofill.AutofillId;
import android.view.inputmethod.InlineSuggestionsRequest;
import com.android.internal.inputmethod.IInlineSuggestionsResponseCallback;

/* loaded from: classes5.dex */
public interface IInlineSuggestionsRequestCallback extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.inputmethod.IInlineSuggestionsRequestCallback";

    public static class Default implements IInlineSuggestionsRequestCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.inputmethod.IInlineSuggestionsRequestCallback
        public void onInlineSuggestionsRequest(InlineSuggestionsRequest inlineSuggestionsRequest, IInlineSuggestionsResponseCallback iInlineSuggestionsResponseCallback) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInlineSuggestionsRequestCallback
        public void onInlineSuggestionsSessionInvalidated() throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInlineSuggestionsRequestCallback
        public void onInlineSuggestionsUnsupported() throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInlineSuggestionsRequestCallback
        public void onInputMethodFinishInput() throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInlineSuggestionsRequestCallback
        public void onInputMethodFinishInputView() throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInlineSuggestionsRequestCallback
        public void onInputMethodShowInputRequested(boolean z) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInlineSuggestionsRequestCallback
        public void onInputMethodStartInput(AutofillId autofillId) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInlineSuggestionsRequestCallback
        public void onInputMethodStartInputView() throws RemoteException {
        }
    }

    void onInlineSuggestionsRequest(InlineSuggestionsRequest inlineSuggestionsRequest, IInlineSuggestionsResponseCallback iInlineSuggestionsResponseCallback) throws RemoteException;

    void onInlineSuggestionsSessionInvalidated() throws RemoteException;

    void onInlineSuggestionsUnsupported() throws RemoteException;

    void onInputMethodFinishInput() throws RemoteException;

    void onInputMethodFinishInputView() throws RemoteException;

    void onInputMethodShowInputRequested(boolean z) throws RemoteException;

    void onInputMethodStartInput(AutofillId autofillId) throws RemoteException;

    void onInputMethodStartInputView() throws RemoteException;

    public static abstract class Stub extends Binder implements IInlineSuggestionsRequestCallback {
        static final int TRANSACTION_onInlineSuggestionsRequest = 2;
        static final int TRANSACTION_onInlineSuggestionsSessionInvalidated = 8;
        static final int TRANSACTION_onInlineSuggestionsUnsupported = 1;
        static final int TRANSACTION_onInputMethodFinishInput = 7;
        static final int TRANSACTION_onInputMethodFinishInputView = 6;
        static final int TRANSACTION_onInputMethodShowInputRequested = 4;
        static final int TRANSACTION_onInputMethodStartInput = 3;
        static final int TRANSACTION_onInputMethodStartInputView = 5;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 7;
        }

        public Stub() {
            attachInterface(this, IInlineSuggestionsRequestCallback.DESCRIPTOR);
        }

        public static IInlineSuggestionsRequestCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IInlineSuggestionsRequestCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IInlineSuggestionsRequestCallback)) {
                return (IInlineSuggestionsRequestCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onInlineSuggestionsUnsupported";
                case 2:
                    return "onInlineSuggestionsRequest";
                case 3:
                    return "onInputMethodStartInput";
                case 4:
                    return "onInputMethodShowInputRequested";
                case 5:
                    return "onInputMethodStartInputView";
                case 6:
                    return "onInputMethodFinishInputView";
                case 7:
                    return "onInputMethodFinishInput";
                case 8:
                    return "onInlineSuggestionsSessionInvalidated";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IInlineSuggestionsRequestCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IInlineSuggestionsRequestCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    onInlineSuggestionsUnsupported();
                    return true;
                case 2:
                    InlineSuggestionsRequest inlineSuggestionsRequest = (InlineSuggestionsRequest) parcel.readTypedObject(InlineSuggestionsRequest.CREATOR);
                    IInlineSuggestionsResponseCallback iInlineSuggestionsResponseCallbackAsInterface = IInlineSuggestionsResponseCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onInlineSuggestionsRequest(inlineSuggestionsRequest, iInlineSuggestionsResponseCallbackAsInterface);
                    return true;
                case 3:
                    AutofillId autofillId = (AutofillId) parcel.readTypedObject(AutofillId.CREATOR);
                    parcel.enforceNoDataAvail();
                    onInputMethodStartInput(autofillId);
                    return true;
                case 4:
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onInputMethodShowInputRequested(z);
                    return true;
                case 5:
                    onInputMethodStartInputView();
                    return true;
                case 6:
                    onInputMethodFinishInputView();
                    return true;
                case 7:
                    onInputMethodFinishInput();
                    return true;
                case 8:
                    onInlineSuggestionsSessionInvalidated();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IInlineSuggestionsRequestCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IInlineSuggestionsRequestCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.inputmethod.IInlineSuggestionsRequestCallback
            public void onInlineSuggestionsUnsupported() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInlineSuggestionsRequestCallback.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInlineSuggestionsRequestCallback
            public void onInlineSuggestionsRequest(InlineSuggestionsRequest inlineSuggestionsRequest, IInlineSuggestionsResponseCallback iInlineSuggestionsResponseCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInlineSuggestionsRequestCallback.DESCRIPTOR);
                    parcelObtain.writeTypedObject(inlineSuggestionsRequest, 0);
                    parcelObtain.writeStrongInterface(iInlineSuggestionsResponseCallback);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInlineSuggestionsRequestCallback
            public void onInputMethodStartInput(AutofillId autofillId) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInlineSuggestionsRequestCallback.DESCRIPTOR);
                    parcelObtain.writeTypedObject(autofillId, 0);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInlineSuggestionsRequestCallback
            public void onInputMethodShowInputRequested(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInlineSuggestionsRequestCallback.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInlineSuggestionsRequestCallback
            public void onInputMethodStartInputView() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInlineSuggestionsRequestCallback.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInlineSuggestionsRequestCallback
            public void onInputMethodFinishInputView() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInlineSuggestionsRequestCallback.DESCRIPTOR);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInlineSuggestionsRequestCallback
            public void onInputMethodFinishInput() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInlineSuggestionsRequestCallback.DESCRIPTOR);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInlineSuggestionsRequestCallback
            public void onInlineSuggestionsSessionInvalidated() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInlineSuggestionsRequestCallback.DESCRIPTOR);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
