package com.android.internal.inputmethod;

import android.graphics.Rect;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.CursorAnchorInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.ExtractedText;
import com.android.internal.inputmethod.IRemoteInputConnection;

/* loaded from: classes5.dex */
public interface IInputMethodSession extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.inputmethod.IInputMethodSession";

    public static class Default implements IInputMethodSession {
        @Override // com.android.internal.inputmethod.IInputMethodSession
        public void appPrivateCommand(String str, Bundle bundle) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.inputmethod.IInputMethodSession
        public void displayCompletions(CompletionInfo[] completionInfoArr) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethodSession
        public void finishInput() throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethodSession
        public void finishSession() throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethodSession
        public void invalidateInput(EditorInfo editorInfo, IRemoteInputConnection iRemoteInputConnection, int i) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethodSession
        public void removeImeSurface() throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethodSession
        public void updateCursor(Rect rect) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethodSession
        public void updateCursorAnchorInfo(CursorAnchorInfo cursorAnchorInfo) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethodSession
        public void updateExtractedText(int i, ExtractedText extractedText) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethodSession
        public void updateSelection(int i, int i2, int i3, int i4, int i5, int i6) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethodSession
        public void viewClicked(boolean z) throws RemoteException {
        }
    }

    void appPrivateCommand(String str, Bundle bundle) throws RemoteException;

    void displayCompletions(CompletionInfo[] completionInfoArr) throws RemoteException;

    void finishInput() throws RemoteException;

    void finishSession() throws RemoteException;

    void invalidateInput(EditorInfo editorInfo, IRemoteInputConnection iRemoteInputConnection, int i) throws RemoteException;

    void removeImeSurface() throws RemoteException;

    void updateCursor(Rect rect) throws RemoteException;

    void updateCursorAnchorInfo(CursorAnchorInfo cursorAnchorInfo) throws RemoteException;

    void updateExtractedText(int i, ExtractedText extractedText) throws RemoteException;

    void updateSelection(int i, int i2, int i3, int i4, int i5, int i6) throws RemoteException;

    void viewClicked(boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements IInputMethodSession {
        static final int TRANSACTION_appPrivateCommand = 6;
        static final int TRANSACTION_displayCompletions = 5;
        static final int TRANSACTION_finishInput = 10;
        static final int TRANSACTION_finishSession = 7;
        static final int TRANSACTION_invalidateInput = 11;
        static final int TRANSACTION_removeImeSurface = 9;
        static final int TRANSACTION_updateCursor = 4;
        static final int TRANSACTION_updateCursorAnchorInfo = 8;
        static final int TRANSACTION_updateExtractedText = 1;
        static final int TRANSACTION_updateSelection = 2;
        static final int TRANSACTION_viewClicked = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 10;
        }

        public Stub() {
            attachInterface(this, IInputMethodSession.DESCRIPTOR);
        }

        public static IInputMethodSession asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IInputMethodSession.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IInputMethodSession)) {
                return (IInputMethodSession) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "updateExtractedText";
                case 2:
                    return "updateSelection";
                case 3:
                    return "viewClicked";
                case 4:
                    return "updateCursor";
                case 5:
                    return "displayCompletions";
                case 6:
                    return "appPrivateCommand";
                case 7:
                    return "finishSession";
                case 8:
                    return "updateCursorAnchorInfo";
                case 9:
                    return "removeImeSurface";
                case 10:
                    return "finishInput";
                case 11:
                    return "invalidateInput";
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
                parcel.enforceInterface(IInputMethodSession.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IInputMethodSession.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    ExtractedText extractedText = (ExtractedText) parcel.readTypedObject(ExtractedText.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateExtractedText(readInt, extractedText);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateSelection(readInt2, readInt3, readInt4, readInt5, readInt6, readInt7);
                    return true;
                case 3:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    viewClicked(readBoolean);
                    return true;
                case 4:
                    Rect rect = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateCursor(rect);
                    return true;
                case 5:
                    CompletionInfo[] completionInfoArr = (CompletionInfo[]) parcel.createTypedArray(CompletionInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    displayCompletions(completionInfoArr);
                    return true;
                case 6:
                    String readString = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    appPrivateCommand(readString, bundle);
                    return true;
                case 7:
                    finishSession();
                    return true;
                case 8:
                    CursorAnchorInfo cursorAnchorInfo = (CursorAnchorInfo) parcel.readTypedObject(CursorAnchorInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateCursorAnchorInfo(cursorAnchorInfo);
                    return true;
                case 9:
                    removeImeSurface();
                    return true;
                case 10:
                    finishInput();
                    return true;
                case 11:
                    EditorInfo editorInfo = (EditorInfo) parcel.readTypedObject(EditorInfo.CREATOR);
                    IRemoteInputConnection asInterface = IRemoteInputConnection.Stub.asInterface(parcel.readStrongBinder());
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    invalidateInput(editorInfo, asInterface, readInt8);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IInputMethodSession {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IInputMethodSession.DESCRIPTOR;
            }

            @Override // com.android.internal.inputmethod.IInputMethodSession
            public void updateExtractedText(int i, ExtractedText extractedText) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInputMethodSession.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(extractedText, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodSession
            public void updateSelection(int i, int i2, int i3, int i4, int i5, int i6) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInputMethodSession.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    obtain.writeInt(i6);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodSession
            public void viewClicked(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInputMethodSession.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodSession
            public void updateCursor(Rect rect) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInputMethodSession.DESCRIPTOR);
                    obtain.writeTypedObject(rect, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodSession
            public void displayCompletions(CompletionInfo[] completionInfoArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInputMethodSession.DESCRIPTOR);
                    obtain.writeTypedArray(completionInfoArr, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodSession
            public void appPrivateCommand(String str, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInputMethodSession.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodSession
            public void finishSession() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInputMethodSession.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodSession
            public void updateCursorAnchorInfo(CursorAnchorInfo cursorAnchorInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInputMethodSession.DESCRIPTOR);
                    obtain.writeTypedObject(cursorAnchorInfo, 0);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodSession
            public void removeImeSurface() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInputMethodSession.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodSession
            public void finishInput() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInputMethodSession.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodSession
            public void invalidateInput(EditorInfo editorInfo, IRemoteInputConnection iRemoteInputConnection, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInputMethodSession.DESCRIPTOR);
                    obtain.writeTypedObject(editorInfo, 0);
                    obtain.writeStrongInterface(iRemoteInputConnection);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
