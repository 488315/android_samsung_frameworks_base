package com.samsung.android.content.smartclip;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.inputmethod.EditorInfo;
import com.android.internal.inputmethod.IRemoteInputConnection;

/* loaded from: classes6.dex */
public interface IInputMethodInfoChangeListener extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.content.smartclip.IInputMethodInfoChangeListener";

    public static class Default implements IInputMethodInfoChangeListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.content.smartclip.IInputMethodInfoChangeListener
        public void onInputInfoChanged(IRemoteInputConnection iRemoteInputConnection, EditorInfo editorInfo) throws RemoteException {
        }

        @Override // com.samsung.android.content.smartclip.IInputMethodInfoChangeListener
        public void onKeyboardClosed() throws RemoteException {
        }
    }

    void onInputInfoChanged(IRemoteInputConnection iRemoteInputConnection, EditorInfo editorInfo) throws RemoteException;

    void onKeyboardClosed() throws RemoteException;

    public static abstract class Stub extends Binder implements IInputMethodInfoChangeListener {
        static final int TRANSACTION_onInputInfoChanged = 1;
        static final int TRANSACTION_onKeyboardClosed = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IInputMethodInfoChangeListener.DESCRIPTOR);
        }

        public static IInputMethodInfoChangeListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IInputMethodInfoChangeListener.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IInputMethodInfoChangeListener)) {
                return (IInputMethodInfoChangeListener) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onInputInfoChanged";
            }
            if (i != 2) {
                return null;
            }
            return "onKeyboardClosed";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IInputMethodInfoChangeListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IInputMethodInfoChangeListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                IRemoteInputConnection iRemoteInputConnectionAsInterface = IRemoteInputConnection.Stub.asInterface(parcel.readStrongBinder());
                EditorInfo editorInfo = (EditorInfo) parcel.readTypedObject(EditorInfo.CREATOR);
                parcel.enforceNoDataAvail();
                onInputInfoChanged(iRemoteInputConnectionAsInterface, editorInfo);
            } else if (i == 2) {
                onKeyboardClosed();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IInputMethodInfoChangeListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IInputMethodInfoChangeListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.content.smartclip.IInputMethodInfoChangeListener
            public void onInputInfoChanged(IRemoteInputConnection iRemoteInputConnection, EditorInfo editorInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethodInfoChangeListener.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iRemoteInputConnection);
                    parcelObtain.writeTypedObject(editorInfo, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.IInputMethodInfoChangeListener
            public void onKeyboardClosed() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethodInfoChangeListener.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
