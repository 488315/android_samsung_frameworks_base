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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IInputMethodSession.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IInputMethodSession)) {
                return (IInputMethodSession) iInterfaceQueryLocalInterface;
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
                    int i3 = parcel.readInt();
                    ExtractedText extractedText = (ExtractedText) parcel.readTypedObject(ExtractedText.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateExtractedText(i3, extractedText);
                    return true;
                case 2:
                    int i4 = parcel.readInt();
                    int i5 = parcel.readInt();
                    int i6 = parcel.readInt();
                    int i7 = parcel.readInt();
                    int i8 = parcel.readInt();
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateSelection(i4, i5, i6, i7, i8, i9);
                    return true;
                case 3:
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    viewClicked(z);
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
                    String string = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    appPrivateCommand(string, bundle);
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
                    IRemoteInputConnection iRemoteInputConnectionAsInterface = IRemoteInputConnection.Stub.asInterface(parcel.readStrongBinder());
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    invalidateInput(editorInfo, iRemoteInputConnectionAsInterface, i10);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethodSession.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(extractedText, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodSession
            public void updateSelection(int i, int i2, int i3, int i4, int i5, int i6) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethodSession.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeInt(i5);
                    parcelObtain.writeInt(i6);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodSession
            public void viewClicked(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethodSession.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodSession
            public void updateCursor(Rect rect) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethodSession.DESCRIPTOR);
                    parcelObtain.writeTypedObject(rect, 0);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodSession
            public void displayCompletions(CompletionInfo[] completionInfoArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethodSession.DESCRIPTOR);
                    parcelObtain.writeTypedArray(completionInfoArr, 0);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodSession
            public void appPrivateCommand(String str, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethodSession.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodSession
            public void finishSession() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethodSession.DESCRIPTOR);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodSession
            public void updateCursorAnchorInfo(CursorAnchorInfo cursorAnchorInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethodSession.DESCRIPTOR);
                    parcelObtain.writeTypedObject(cursorAnchorInfo, 0);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodSession
            public void removeImeSurface() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethodSession.DESCRIPTOR);
                    this.mRemote.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodSession
            public void finishInput() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethodSession.DESCRIPTOR);
                    this.mRemote.transact(10, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodSession
            public void invalidateInput(EditorInfo editorInfo, IRemoteInputConnection iRemoteInputConnection, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethodSession.DESCRIPTOR);
                    parcelObtain.writeTypedObject(editorInfo, 0);
                    parcelObtain.writeStrongInterface(iRemoteInputConnection);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(11, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
