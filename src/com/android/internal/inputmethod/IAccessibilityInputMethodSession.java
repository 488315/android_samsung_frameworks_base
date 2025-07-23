package com.android.internal.inputmethod;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.inputmethod.EditorInfo;
import com.android.internal.inputmethod.IRemoteAccessibilityInputConnection;

/* loaded from: classes5.dex */
public interface IAccessibilityInputMethodSession extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.inputmethod.IAccessibilityInputMethodSession";

    public static class Default implements IAccessibilityInputMethodSession {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.inputmethod.IAccessibilityInputMethodSession
        public void finishInput() throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IAccessibilityInputMethodSession
        public void finishSession() throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IAccessibilityInputMethodSession
        public void invalidateInput(EditorInfo editorInfo, IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, int i) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IAccessibilityInputMethodSession
        public void updateSelection(int i, int i2, int i3, int i4, int i5, int i6) throws RemoteException {
        }
    }

    void finishInput() throws RemoteException;

    void finishSession() throws RemoteException;

    void invalidateInput(EditorInfo editorInfo, IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, int i) throws RemoteException;

    void updateSelection(int i, int i2, int i3, int i4, int i5, int i6) throws RemoteException;

    public static abstract class Stub extends Binder implements IAccessibilityInputMethodSession {
        static final int TRANSACTION_finishInput = 2;
        static final int TRANSACTION_finishSession = 3;
        static final int TRANSACTION_invalidateInput = 4;
        static final int TRANSACTION_updateSelection = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, IAccessibilityInputMethodSession.DESCRIPTOR);
        }

        public static IAccessibilityInputMethodSession asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IAccessibilityInputMethodSession.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IAccessibilityInputMethodSession)) {
                return (IAccessibilityInputMethodSession) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "updateSelection";
            }
            if (i == 2) {
                return "finishInput";
            }
            if (i == 3) {
                return "finishSession";
            }
            if (i != 4) {
                return null;
            }
            return "invalidateInput";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IAccessibilityInputMethodSession.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IAccessibilityInputMethodSession.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                int readInt2 = parcel.readInt();
                int readInt3 = parcel.readInt();
                int readInt4 = parcel.readInt();
                int readInt5 = parcel.readInt();
                int readInt6 = parcel.readInt();
                parcel.enforceNoDataAvail();
                updateSelection(readInt, readInt2, readInt3, readInt4, readInt5, readInt6);
            } else if (i == 2) {
                finishInput();
            } else if (i == 3) {
                finishSession();
            } else if (i == 4) {
                EditorInfo editorInfo = (EditorInfo) parcel.readTypedObject(EditorInfo.CREATOR);
                IRemoteAccessibilityInputConnection asInterface = IRemoteAccessibilityInputConnection.Stub.asInterface(parcel.readStrongBinder());
                int readInt7 = parcel.readInt();
                parcel.enforceNoDataAvail();
                invalidateInput(editorInfo, asInterface, readInt7);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IAccessibilityInputMethodSession {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAccessibilityInputMethodSession.DESCRIPTOR;
            }

            @Override // com.android.internal.inputmethod.IAccessibilityInputMethodSession
            public void updateSelection(int i, int i2, int i3, int i4, int i5, int i6) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IAccessibilityInputMethodSession.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    obtain.writeInt(i6);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IAccessibilityInputMethodSession
            public void finishInput() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IAccessibilityInputMethodSession.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IAccessibilityInputMethodSession
            public void finishSession() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IAccessibilityInputMethodSession.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IAccessibilityInputMethodSession
            public void invalidateInput(EditorInfo editorInfo, IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IAccessibilityInputMethodSession.DESCRIPTOR);
                    obtain.writeTypedObject(editorInfo, 0);
                    obtain.writeStrongInterface(iRemoteAccessibilityInputConnection);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
