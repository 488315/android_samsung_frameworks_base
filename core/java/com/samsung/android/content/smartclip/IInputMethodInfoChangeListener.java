package com.samsung.android.content.smartclip;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.inputmethod.EditorInfo;

import com.android.internal.inputmethod.IRemoteInputConnection;

/* loaded from: classes5.dex */
public interface IInputMethodInfoChangeListener extends IInterface {
    public static final String DESCRIPTOR =
            "com.samsung.android.content.smartclip.IInputMethodInfoChangeListener";

    void onInputInfoChanged(IRemoteInputConnection iRemoteInputConnection, EditorInfo editorInfo)
            throws RemoteException;

    void onKeyboardClosed() throws RemoteException;

    public static class Default implements IInputMethodInfoChangeListener {
        @Override // com.samsung.android.content.smartclip.IInputMethodInfoChangeListener
        public void onInputInfoChanged(
                IRemoteInputConnection inputConnection, EditorInfo editorInfo)
                throws RemoteException {}

        @Override // com.samsung.android.content.smartclip.IInputMethodInfoChangeListener
        public void onKeyboardClosed() throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IInputMethodInfoChangeListener {
        static final int TRANSACTION_onInputInfoChanged = 1;
        static final int TRANSACTION_onKeyboardClosed = 2;

        public Stub() {
            attachInterface(this, IInputMethodInfoChangeListener.DESCRIPTOR);
        }

        public static IInputMethodInfoChangeListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IInputMethodInfoChangeListener.DESCRIPTOR);
            if (iin != null && (iin instanceof IInputMethodInfoChangeListener)) {
                return (IInputMethodInfoChangeListener) iin;
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
                    return "onInputInfoChanged";
                case 2:
                    return "onKeyboardClosed";
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
                data.enforceInterface(IInputMethodInfoChangeListener.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IInputMethodInfoChangeListener.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    IRemoteInputConnection _arg0 =
                            IRemoteInputConnection.Stub.asInterface(data.readStrongBinder());
                    EditorInfo _arg1 = (EditorInfo) data.readTypedObject(EditorInfo.CREATOR);
                    data.enforceNoDataAvail();
                    onInputInfoChanged(_arg0, _arg1);
                    return true;
                case 2:
                    onKeyboardClosed();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IInputMethodInfoChangeListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IInputMethodInfoChangeListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.content.smartclip.IInputMethodInfoChangeListener
            public void onInputInfoChanged(
                    IRemoteInputConnection inputConnection, EditorInfo editorInfo)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IInputMethodInfoChangeListener.DESCRIPTOR);
                    _data.writeStrongInterface(inputConnection);
                    _data.writeTypedObject(editorInfo, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.IInputMethodInfoChangeListener
            public void onKeyboardClosed() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IInputMethodInfoChangeListener.DESCRIPTOR);
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
