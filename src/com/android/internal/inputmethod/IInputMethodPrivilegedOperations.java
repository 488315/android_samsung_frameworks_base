package com.android.internal.inputmethod;

import android.graphics.Region;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.inputmethod.ImeTracker;
import android.view.inputmethod.InputMethodSubtype;
import com.android.internal.infra.AndroidFuture;

/* loaded from: classes5.dex */
public interface IInputMethodPrivilegedOperations extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.inputmethod.IInputMethodPrivilegedOperations";

    public static class Default implements IInputMethodPrivilegedOperations {
        @Override // com.android.internal.inputmethod.IInputMethodPrivilegedOperations
        public void applyImeVisibilityAsync(IBinder iBinder, boolean z, ImeTracker.Token token) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.inputmethod.IInputMethodPrivilegedOperations
        public void createInputContentUriToken(Uri uri, String str, AndroidFuture androidFuture) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethodPrivilegedOperations
        public void hideMySoftInput(ImeTracker.Token token, int i, int i2, AndroidFuture androidFuture) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethodPrivilegedOperations
        public void notifyUserActionAsync() throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethodPrivilegedOperations
        public void onImeSwitchButtonClickFromClient(int i) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethodPrivilegedOperations
        public void onStylusHandwritingReady(int i, int i2) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethodPrivilegedOperations
        public void reportFullscreenModeAsync(boolean z) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethodPrivilegedOperations
        public void reportStartInputAsync(IBinder iBinder) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethodPrivilegedOperations
        public void resetStylusHandwriting(int i) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethodPrivilegedOperations
        public void setHandwritingSurfaceNotTouchable(boolean z) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethodPrivilegedOperations
        public void setHandwritingTouchableRegion(Region region) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethodPrivilegedOperations
        public void setImeWindowStatusAsync(int i, int i2) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethodPrivilegedOperations
        public void setInputMethod(String str, AndroidFuture androidFuture) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethodPrivilegedOperations
        public void setInputMethodAndSubtype(String str, InputMethodSubtype inputMethodSubtype, AndroidFuture androidFuture) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethodPrivilegedOperations
        public void shouldOfferSwitchingToNextInputMethod(AndroidFuture androidFuture) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethodPrivilegedOperations
        public void showMySoftInput(ImeTracker.Token token, int i, int i2, AndroidFuture androidFuture) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethodPrivilegedOperations
        public void switchKeyboardLayoutAsync(int i) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethodPrivilegedOperations
        public void switchToNextInputMethod(boolean z, AndroidFuture androidFuture) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethodPrivilegedOperations
        public void switchToPreviousInputMethod(AndroidFuture androidFuture) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethodPrivilegedOperations
        public void updateStatusIconAsync(String str, int i) throws RemoteException {
        }
    }

    void applyImeVisibilityAsync(IBinder iBinder, boolean z, ImeTracker.Token token) throws RemoteException;

    void createInputContentUriToken(Uri uri, String str, AndroidFuture androidFuture) throws RemoteException;

    void hideMySoftInput(ImeTracker.Token token, int i, int i2, AndroidFuture androidFuture) throws RemoteException;

    void notifyUserActionAsync() throws RemoteException;

    void onImeSwitchButtonClickFromClient(int i) throws RemoteException;

    void onStylusHandwritingReady(int i, int i2) throws RemoteException;

    void reportFullscreenModeAsync(boolean z) throws RemoteException;

    void reportStartInputAsync(IBinder iBinder) throws RemoteException;

    void resetStylusHandwriting(int i) throws RemoteException;

    void setHandwritingSurfaceNotTouchable(boolean z) throws RemoteException;

    void setHandwritingTouchableRegion(Region region) throws RemoteException;

    void setImeWindowStatusAsync(int i, int i2) throws RemoteException;

    void setInputMethod(String str, AndroidFuture androidFuture) throws RemoteException;

    void setInputMethodAndSubtype(String str, InputMethodSubtype inputMethodSubtype, AndroidFuture androidFuture) throws RemoteException;

    void shouldOfferSwitchingToNextInputMethod(AndroidFuture androidFuture) throws RemoteException;

    void showMySoftInput(ImeTracker.Token token, int i, int i2, AndroidFuture androidFuture) throws RemoteException;

    void switchKeyboardLayoutAsync(int i) throws RemoteException;

    void switchToNextInputMethod(boolean z, AndroidFuture androidFuture) throws RemoteException;

    void switchToPreviousInputMethod(AndroidFuture androidFuture) throws RemoteException;

    void updateStatusIconAsync(String str, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IInputMethodPrivilegedOperations {
        static final int TRANSACTION_applyImeVisibilityAsync = 15;
        static final int TRANSACTION_createInputContentUriToken = 3;
        static final int TRANSACTION_hideMySoftInput = 7;
        static final int TRANSACTION_notifyUserActionAsync = 14;
        static final int TRANSACTION_onImeSwitchButtonClickFromClient = 13;
        static final int TRANSACTION_onStylusHandwritingReady = 16;
        static final int TRANSACTION_reportFullscreenModeAsync = 4;
        static final int TRANSACTION_reportStartInputAsync = 2;
        static final int TRANSACTION_resetStylusHandwriting = 17;
        static final int TRANSACTION_setHandwritingSurfaceNotTouchable = 19;
        static final int TRANSACTION_setHandwritingTouchableRegion = 20;
        static final int TRANSACTION_setImeWindowStatusAsync = 1;
        static final int TRANSACTION_setInputMethod = 5;
        static final int TRANSACTION_setInputMethodAndSubtype = 6;
        static final int TRANSACTION_shouldOfferSwitchingToNextInputMethod = 12;
        static final int TRANSACTION_showMySoftInput = 8;
        static final int TRANSACTION_switchKeyboardLayoutAsync = 18;
        static final int TRANSACTION_switchToNextInputMethod = 11;
        static final int TRANSACTION_switchToPreviousInputMethod = 10;
        static final int TRANSACTION_updateStatusIconAsync = 9;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 19;
        }

        public Stub() {
            attachInterface(this, IInputMethodPrivilegedOperations.DESCRIPTOR);
        }

        public static IInputMethodPrivilegedOperations asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IInputMethodPrivilegedOperations.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IInputMethodPrivilegedOperations)) {
                return (IInputMethodPrivilegedOperations) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setImeWindowStatusAsync";
                case 2:
                    return "reportStartInputAsync";
                case 3:
                    return "createInputContentUriToken";
                case 4:
                    return "reportFullscreenModeAsync";
                case 5:
                    return "setInputMethod";
                case 6:
                    return "setInputMethodAndSubtype";
                case 7:
                    return "hideMySoftInput";
                case 8:
                    return "showMySoftInput";
                case 9:
                    return "updateStatusIconAsync";
                case 10:
                    return "switchToPreviousInputMethod";
                case 11:
                    return "switchToNextInputMethod";
                case 12:
                    return "shouldOfferSwitchingToNextInputMethod";
                case 13:
                    return "onImeSwitchButtonClickFromClient";
                case 14:
                    return "notifyUserActionAsync";
                case 15:
                    return "applyImeVisibilityAsync";
                case 16:
                    return "onStylusHandwritingReady";
                case 17:
                    return "resetStylusHandwriting";
                case 18:
                    return "switchKeyboardLayoutAsync";
                case 19:
                    return "setHandwritingSurfaceNotTouchable";
                case 20:
                    return "setHandwritingTouchableRegion";
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
                parcel.enforceInterface(IInputMethodPrivilegedOperations.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IInputMethodPrivilegedOperations.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setImeWindowStatusAsync(readInt, readInt2);
                    return true;
                case 2:
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    reportStartInputAsync(readStrongBinder);
                    return true;
                case 3:
                    Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    String readString = parcel.readString();
                    AndroidFuture androidFuture = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    createInputContentUriToken(uri, readString, androidFuture);
                    return true;
                case 4:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    reportFullscreenModeAsync(readBoolean);
                    return true;
                case 5:
                    String readString2 = parcel.readString();
                    AndroidFuture androidFuture2 = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    setInputMethod(readString2, androidFuture2);
                    return true;
                case 6:
                    String readString3 = parcel.readString();
                    InputMethodSubtype inputMethodSubtype = (InputMethodSubtype) parcel.readTypedObject(InputMethodSubtype.CREATOR);
                    AndroidFuture androidFuture3 = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    setInputMethodAndSubtype(readString3, inputMethodSubtype, androidFuture3);
                    return true;
                case 7:
                    ImeTracker.Token token = (ImeTracker.Token) parcel.readTypedObject(ImeTracker.Token.CREATOR);
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    AndroidFuture androidFuture4 = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    hideMySoftInput(token, readInt3, readInt4, androidFuture4);
                    return true;
                case 8:
                    ImeTracker.Token token2 = (ImeTracker.Token) parcel.readTypedObject(ImeTracker.Token.CREATOR);
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    AndroidFuture androidFuture5 = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    showMySoftInput(token2, readInt5, readInt6, androidFuture5);
                    return true;
                case 9:
                    String readString4 = parcel.readString();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateStatusIconAsync(readString4, readInt7);
                    return true;
                case 10:
                    AndroidFuture androidFuture6 = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    switchToPreviousInputMethod(androidFuture6);
                    return true;
                case 11:
                    boolean readBoolean2 = parcel.readBoolean();
                    AndroidFuture androidFuture7 = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    switchToNextInputMethod(readBoolean2, androidFuture7);
                    return true;
                case 12:
                    AndroidFuture androidFuture8 = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    shouldOfferSwitchingToNextInputMethod(androidFuture8);
                    return true;
                case 13:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onImeSwitchButtonClickFromClient(readInt8);
                    return true;
                case 14:
                    notifyUserActionAsync();
                    return true;
                case 15:
                    IBinder readStrongBinder2 = parcel.readStrongBinder();
                    boolean readBoolean3 = parcel.readBoolean();
                    ImeTracker.Token token3 = (ImeTracker.Token) parcel.readTypedObject(ImeTracker.Token.CREATOR);
                    parcel.enforceNoDataAvail();
                    applyImeVisibilityAsync(readStrongBinder2, readBoolean3, token3);
                    return true;
                case 16:
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onStylusHandwritingReady(readInt9, readInt10);
                    return true;
                case 17:
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    resetStylusHandwriting(readInt11);
                    return true;
                case 18:
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    switchKeyboardLayoutAsync(readInt12);
                    return true;
                case 19:
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setHandwritingSurfaceNotTouchable(readBoolean4);
                    return true;
                case 20:
                    Region region = (Region) parcel.readTypedObject(Region.CREATOR);
                    parcel.enforceNoDataAvail();
                    setHandwritingTouchableRegion(region);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IInputMethodPrivilegedOperations {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IInputMethodPrivilegedOperations.DESCRIPTOR;
            }

            @Override // com.android.internal.inputmethod.IInputMethodPrivilegedOperations
            public void setImeWindowStatusAsync(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInputMethodPrivilegedOperations.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodPrivilegedOperations
            public void reportStartInputAsync(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInputMethodPrivilegedOperations.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodPrivilegedOperations
            public void createInputContentUriToken(Uri uri, String str, AndroidFuture androidFuture) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInputMethodPrivilegedOperations.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodPrivilegedOperations
            public void reportFullscreenModeAsync(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInputMethodPrivilegedOperations.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodPrivilegedOperations
            public void setInputMethod(String str, AndroidFuture androidFuture) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInputMethodPrivilegedOperations.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodPrivilegedOperations
            public void setInputMethodAndSubtype(String str, InputMethodSubtype inputMethodSubtype, AndroidFuture androidFuture) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInputMethodPrivilegedOperations.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(inputMethodSubtype, 0);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodPrivilegedOperations
            public void hideMySoftInput(ImeTracker.Token token, int i, int i2, AndroidFuture androidFuture) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInputMethodPrivilegedOperations.DESCRIPTOR);
                    obtain.writeTypedObject(token, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodPrivilegedOperations
            public void showMySoftInput(ImeTracker.Token token, int i, int i2, AndroidFuture androidFuture) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInputMethodPrivilegedOperations.DESCRIPTOR);
                    obtain.writeTypedObject(token, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodPrivilegedOperations
            public void updateStatusIconAsync(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInputMethodPrivilegedOperations.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodPrivilegedOperations
            public void switchToPreviousInputMethod(AndroidFuture androidFuture) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInputMethodPrivilegedOperations.DESCRIPTOR);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodPrivilegedOperations
            public void switchToNextInputMethod(boolean z, AndroidFuture androidFuture) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInputMethodPrivilegedOperations.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodPrivilegedOperations
            public void shouldOfferSwitchingToNextInputMethod(AndroidFuture androidFuture) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInputMethodPrivilegedOperations.DESCRIPTOR);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodPrivilegedOperations
            public void onImeSwitchButtonClickFromClient(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInputMethodPrivilegedOperations.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodPrivilegedOperations
            public void notifyUserActionAsync() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInputMethodPrivilegedOperations.DESCRIPTOR);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodPrivilegedOperations
            public void applyImeVisibilityAsync(IBinder iBinder, boolean z, ImeTracker.Token token) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInputMethodPrivilegedOperations.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(token, 0);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodPrivilegedOperations
            public void onStylusHandwritingReady(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInputMethodPrivilegedOperations.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodPrivilegedOperations
            public void resetStylusHandwriting(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInputMethodPrivilegedOperations.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(17, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodPrivilegedOperations
            public void switchKeyboardLayoutAsync(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInputMethodPrivilegedOperations.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(18, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodPrivilegedOperations
            public void setHandwritingSurfaceNotTouchable(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInputMethodPrivilegedOperations.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(19, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodPrivilegedOperations
            public void setHandwritingTouchableRegion(Region region) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInputMethodPrivilegedOperations.DESCRIPTOR);
                    obtain.writeTypedObject(region, 0);
                    this.mRemote.transact(20, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
