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

    void onInlineSuggestionsRequest(InlineSuggestionsRequest inlineSuggestionsRequest, IInlineSuggestionsResponseCallback iInlineSuggestionsResponseCallback) throws RemoteException;

    void onInlineSuggestionsSessionInvalidated() throws RemoteException;

    void onInlineSuggestionsUnsupported() throws RemoteException;

    void onInputMethodFinishInput() throws RemoteException;

    void onInputMethodFinishInputView() throws RemoteException;

    void onInputMethodShowInputRequested(boolean z) throws RemoteException;

    void onInputMethodStartInput(AutofillId autofillId) throws RemoteException;

    void onInputMethodStartInputView() throws RemoteException;

    public static class Default implements IInlineSuggestionsRequestCallback {
        @Override // com.android.internal.inputmethod.IInlineSuggestionsRequestCallback
        public void onInlineSuggestionsUnsupported() throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInlineSuggestionsRequestCallback
        public void onInlineSuggestionsRequest(InlineSuggestionsRequest request, IInlineSuggestionsResponseCallback callback) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInlineSuggestionsRequestCallback
        public void onInputMethodStartInput(AutofillId imeFieldId) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInlineSuggestionsRequestCallback
        public void onInputMethodShowInputRequested(boolean requestResult) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInlineSuggestionsRequestCallback
        public void onInputMethodStartInputView() throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInlineSuggestionsRequestCallback
        public void onInputMethodFinishInputView() throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInlineSuggestionsRequestCallback
        public void onInputMethodFinishInput() throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInlineSuggestionsRequestCallback
        public void onInlineSuggestionsSessionInvalidated() throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IInlineSuggestionsRequestCallback {
        static final int TRANSACTION_onInlineSuggestionsRequest = 2;
        static final int TRANSACTION_onInlineSuggestionsSessionInvalidated = 8;
        static final int TRANSACTION_onInlineSuggestionsUnsupported = 1;
        static final int TRANSACTION_onInputMethodFinishInput = 7;
        static final int TRANSACTION_onInputMethodFinishInputView = 6;
        static final int TRANSACTION_onInputMethodShowInputRequested = 4;
        static final int TRANSACTION_onInputMethodStartInput = 3;
        static final int TRANSACTION_onInputMethodStartInputView = 5;

        public Stub() {
            attachInterface(this, IInlineSuggestionsRequestCallback.DESCRIPTOR);
        }

        public static IInlineSuggestionsRequestCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IInlineSuggestionsRequestCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IInlineSuggestionsRequestCallback)) {
                return (IInlineSuggestionsRequestCallback) iin;
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
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IInlineSuggestionsRequestCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IInlineSuggestionsRequestCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    onInlineSuggestionsUnsupported();
                    return true;
                case 2:
                    InlineSuggestionsRequest _arg0 = (InlineSuggestionsRequest) data.readTypedObject(InlineSuggestionsRequest.CREATOR);
                    IInlineSuggestionsResponseCallback _arg1 = IInlineSuggestionsResponseCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    onInlineSuggestionsRequest(_arg0, _arg1);
                    return true;
                case 3:
                    AutofillId _arg02 = (AutofillId) data.readTypedObject(AutofillId.CREATOR);
                    data.enforceNoDataAvail();
                    onInputMethodStartInput(_arg02);
                    return true;
                case 4:
                    boolean _arg03 = data.readBoolean();
                    data.enforceNoDataAvail();
                    onInputMethodShowInputRequested(_arg03);
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
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IInlineSuggestionsRequestCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
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
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IInlineSuggestionsRequestCallback.DESCRIPTOR);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInlineSuggestionsRequestCallback
            public void onInlineSuggestionsRequest(InlineSuggestionsRequest request, IInlineSuggestionsResponseCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IInlineSuggestionsRequestCallback.DESCRIPTOR);
                    _data.writeTypedObject(request, 0);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInlineSuggestionsRequestCallback
            public void onInputMethodStartInput(AutofillId imeFieldId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IInlineSuggestionsRequestCallback.DESCRIPTOR);
                    _data.writeTypedObject(imeFieldId, 0);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInlineSuggestionsRequestCallback
            public void onInputMethodShowInputRequested(boolean requestResult) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IInlineSuggestionsRequestCallback.DESCRIPTOR);
                    _data.writeBoolean(requestResult);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInlineSuggestionsRequestCallback
            public void onInputMethodStartInputView() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IInlineSuggestionsRequestCallback.DESCRIPTOR);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInlineSuggestionsRequestCallback
            public void onInputMethodFinishInputView() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IInlineSuggestionsRequestCallback.DESCRIPTOR);
                    this.mRemote.transact(6, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInlineSuggestionsRequestCallback
            public void onInputMethodFinishInput() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IInlineSuggestionsRequestCallback.DESCRIPTOR);
                    this.mRemote.transact(7, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInlineSuggestionsRequestCallback
            public void onInlineSuggestionsSessionInvalidated() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IInlineSuggestionsRequestCallback.DESCRIPTOR);
                    this.mRemote.transact(8, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 7;
        }
    }
}
