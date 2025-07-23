package com.android.internal.inputmethod;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.TextAttribute;
import com.android.internal.infra.AndroidFuture;

/* loaded from: classes5.dex */
public interface IRemoteAccessibilityInputConnection extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.inputmethod.IRemoteAccessibilityInputConnection";

    public static class Default implements IRemoteAccessibilityInputConnection {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.inputmethod.IRemoteAccessibilityInputConnection
        public void clearMetaKeyStates(InputConnectionCommandHeader inputConnectionCommandHeader, int i) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteAccessibilityInputConnection
        public void commitText(InputConnectionCommandHeader inputConnectionCommandHeader, CharSequence charSequence, int i, TextAttribute textAttribute) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteAccessibilityInputConnection
        public void deleteSurroundingText(InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteAccessibilityInputConnection
        public void getCursorCapsMode(InputConnectionCommandHeader inputConnectionCommandHeader, int i, AndroidFuture androidFuture) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteAccessibilityInputConnection
        public void getSurroundingText(InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2, int i3, AndroidFuture androidFuture) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteAccessibilityInputConnection
        public void performContextMenuAction(InputConnectionCommandHeader inputConnectionCommandHeader, int i) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteAccessibilityInputConnection
        public void performEditorAction(InputConnectionCommandHeader inputConnectionCommandHeader, int i) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteAccessibilityInputConnection
        public void sendKeyEvent(InputConnectionCommandHeader inputConnectionCommandHeader, KeyEvent keyEvent) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteAccessibilityInputConnection
        public void setSelection(InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2) throws RemoteException {
        }
    }

    void clearMetaKeyStates(InputConnectionCommandHeader inputConnectionCommandHeader, int i) throws RemoteException;

    void commitText(InputConnectionCommandHeader inputConnectionCommandHeader, CharSequence charSequence, int i, TextAttribute textAttribute) throws RemoteException;

    void deleteSurroundingText(InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2) throws RemoteException;

    void getCursorCapsMode(InputConnectionCommandHeader inputConnectionCommandHeader, int i, AndroidFuture androidFuture) throws RemoteException;

    void getSurroundingText(InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2, int i3, AndroidFuture androidFuture) throws RemoteException;

    void performContextMenuAction(InputConnectionCommandHeader inputConnectionCommandHeader, int i) throws RemoteException;

    void performEditorAction(InputConnectionCommandHeader inputConnectionCommandHeader, int i) throws RemoteException;

    void sendKeyEvent(InputConnectionCommandHeader inputConnectionCommandHeader, KeyEvent keyEvent) throws RemoteException;

    void setSelection(InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2) throws RemoteException;

    public static abstract class Stub extends Binder implements IRemoteAccessibilityInputConnection {
        static final int TRANSACTION_clearMetaKeyStates = 9;
        static final int TRANSACTION_commitText = 1;
        static final int TRANSACTION_deleteSurroundingText = 4;
        static final int TRANSACTION_getCursorCapsMode = 8;
        static final int TRANSACTION_getSurroundingText = 3;
        static final int TRANSACTION_performContextMenuAction = 7;
        static final int TRANSACTION_performEditorAction = 6;
        static final int TRANSACTION_sendKeyEvent = 5;
        static final int TRANSACTION_setSelection = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 8;
        }

        public Stub() {
            attachInterface(this, IRemoteAccessibilityInputConnection.DESCRIPTOR);
        }

        public static IRemoteAccessibilityInputConnection asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IRemoteAccessibilityInputConnection.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IRemoteAccessibilityInputConnection)) {
                return (IRemoteAccessibilityInputConnection) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "commitText";
                case 2:
                    return "setSelection";
                case 3:
                    return "getSurroundingText";
                case 4:
                    return "deleteSurroundingText";
                case 5:
                    return "sendKeyEvent";
                case 6:
                    return "performEditorAction";
                case 7:
                    return "performContextMenuAction";
                case 8:
                    return "getCursorCapsMode";
                case 9:
                    return "clearMetaKeyStates";
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
                parcel.enforceInterface(IRemoteAccessibilityInputConnection.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IRemoteAccessibilityInputConnection.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    InputConnectionCommandHeader inputConnectionCommandHeader = (InputConnectionCommandHeader) parcel.readTypedObject(InputConnectionCommandHeader.CREATOR);
                    CharSequence charSequence = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    int readInt = parcel.readInt();
                    TextAttribute textAttribute = (TextAttribute) parcel.readTypedObject(TextAttribute.CREATOR);
                    parcel.enforceNoDataAvail();
                    commitText(inputConnectionCommandHeader, charSequence, readInt, textAttribute);
                    return true;
                case 2:
                    InputConnectionCommandHeader inputConnectionCommandHeader2 = (InputConnectionCommandHeader) parcel.readTypedObject(InputConnectionCommandHeader.CREATOR);
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setSelection(inputConnectionCommandHeader2, readInt2, readInt3);
                    return true;
                case 3:
                    InputConnectionCommandHeader inputConnectionCommandHeader3 = (InputConnectionCommandHeader) parcel.readTypedObject(InputConnectionCommandHeader.CREATOR);
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    AndroidFuture androidFuture = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    getSurroundingText(inputConnectionCommandHeader3, readInt4, readInt5, readInt6, androidFuture);
                    return true;
                case 4:
                    InputConnectionCommandHeader inputConnectionCommandHeader4 = (InputConnectionCommandHeader) parcel.readTypedObject(InputConnectionCommandHeader.CREATOR);
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    deleteSurroundingText(inputConnectionCommandHeader4, readInt7, readInt8);
                    return true;
                case 5:
                    InputConnectionCommandHeader inputConnectionCommandHeader5 = (InputConnectionCommandHeader) parcel.readTypedObject(InputConnectionCommandHeader.CREATOR);
                    KeyEvent keyEvent = (KeyEvent) parcel.readTypedObject(KeyEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendKeyEvent(inputConnectionCommandHeader5, keyEvent);
                    return true;
                case 6:
                    InputConnectionCommandHeader inputConnectionCommandHeader6 = (InputConnectionCommandHeader) parcel.readTypedObject(InputConnectionCommandHeader.CREATOR);
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    performEditorAction(inputConnectionCommandHeader6, readInt9);
                    return true;
                case 7:
                    InputConnectionCommandHeader inputConnectionCommandHeader7 = (InputConnectionCommandHeader) parcel.readTypedObject(InputConnectionCommandHeader.CREATOR);
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    performContextMenuAction(inputConnectionCommandHeader7, readInt10);
                    return true;
                case 8:
                    InputConnectionCommandHeader inputConnectionCommandHeader8 = (InputConnectionCommandHeader) parcel.readTypedObject(InputConnectionCommandHeader.CREATOR);
                    int readInt11 = parcel.readInt();
                    AndroidFuture androidFuture2 = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    getCursorCapsMode(inputConnectionCommandHeader8, readInt11, androidFuture2);
                    return true;
                case 9:
                    InputConnectionCommandHeader inputConnectionCommandHeader9 = (InputConnectionCommandHeader) parcel.readTypedObject(InputConnectionCommandHeader.CREATOR);
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearMetaKeyStates(inputConnectionCommandHeader9, readInt12);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IRemoteAccessibilityInputConnection {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRemoteAccessibilityInputConnection.DESCRIPTOR;
            }

            @Override // com.android.internal.inputmethod.IRemoteAccessibilityInputConnection
            public void commitText(InputConnectionCommandHeader inputConnectionCommandHeader, CharSequence charSequence, int i, TextAttribute textAttribute) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRemoteAccessibilityInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeInt(i);
                    obtain.writeTypedObject(textAttribute, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteAccessibilityInputConnection
            public void setSelection(InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRemoteAccessibilityInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteAccessibilityInputConnection
            public void getSurroundingText(InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2, int i3, AndroidFuture androidFuture) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRemoteAccessibilityInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteAccessibilityInputConnection
            public void deleteSurroundingText(InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRemoteAccessibilityInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteAccessibilityInputConnection
            public void sendKeyEvent(InputConnectionCommandHeader inputConnectionCommandHeader, KeyEvent keyEvent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRemoteAccessibilityInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeTypedObject(keyEvent, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteAccessibilityInputConnection
            public void performEditorAction(InputConnectionCommandHeader inputConnectionCommandHeader, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRemoteAccessibilityInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteAccessibilityInputConnection
            public void performContextMenuAction(InputConnectionCommandHeader inputConnectionCommandHeader, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRemoteAccessibilityInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteAccessibilityInputConnection
            public void getCursorCapsMode(InputConnectionCommandHeader inputConnectionCommandHeader, int i, AndroidFuture androidFuture) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRemoteAccessibilityInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteAccessibilityInputConnection
            public void clearMetaKeyStates(InputConnectionCommandHeader inputConnectionCommandHeader, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRemoteAccessibilityInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
