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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IInputMethodPrivilegedOperations.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IInputMethodPrivilegedOperations)) {
                return (IInputMethodPrivilegedOperations) iInterfaceQueryLocalInterface;
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
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setImeWindowStatusAsync(i3, i4);
                    return true;
                case 2:
                    IBinder strongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    reportStartInputAsync(strongBinder);
                    return true;
                case 3:
                    Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    String string = parcel.readString();
                    AndroidFuture androidFuture = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    createInputContentUriToken(uri, string, androidFuture);
                    return true;
                case 4:
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    reportFullscreenModeAsync(z);
                    return true;
                case 5:
                    String string2 = parcel.readString();
                    AndroidFuture androidFuture2 = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    setInputMethod(string2, androidFuture2);
                    return true;
                case 6:
                    String string3 = parcel.readString();
                    InputMethodSubtype inputMethodSubtype = (InputMethodSubtype) parcel.readTypedObject(InputMethodSubtype.CREATOR);
                    AndroidFuture androidFuture3 = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    setInputMethodAndSubtype(string3, inputMethodSubtype, androidFuture3);
                    return true;
                case 7:
                    ImeTracker.Token token = (ImeTracker.Token) parcel.readTypedObject(ImeTracker.Token.CREATOR);
                    int i5 = parcel.readInt();
                    int i6 = parcel.readInt();
                    AndroidFuture androidFuture4 = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    hideMySoftInput(token, i5, i6, androidFuture4);
                    return true;
                case 8:
                    ImeTracker.Token token2 = (ImeTracker.Token) parcel.readTypedObject(ImeTracker.Token.CREATOR);
                    int i7 = parcel.readInt();
                    int i8 = parcel.readInt();
                    AndroidFuture androidFuture5 = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    showMySoftInput(token2, i7, i8, androidFuture5);
                    return true;
                case 9:
                    String string4 = parcel.readString();
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateStatusIconAsync(string4, i9);
                    return true;
                case 10:
                    AndroidFuture androidFuture6 = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    switchToPreviousInputMethod(androidFuture6);
                    return true;
                case 11:
                    boolean z2 = parcel.readBoolean();
                    AndroidFuture androidFuture7 = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    switchToNextInputMethod(z2, androidFuture7);
                    return true;
                case 12:
                    AndroidFuture androidFuture8 = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    shouldOfferSwitchingToNextInputMethod(androidFuture8);
                    return true;
                case 13:
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onImeSwitchButtonClickFromClient(i10);
                    return true;
                case 14:
                    notifyUserActionAsync();
                    return true;
                case 15:
                    IBinder strongBinder2 = parcel.readStrongBinder();
                    boolean z3 = parcel.readBoolean();
                    ImeTracker.Token token3 = (ImeTracker.Token) parcel.readTypedObject(ImeTracker.Token.CREATOR);
                    parcel.enforceNoDataAvail();
                    applyImeVisibilityAsync(strongBinder2, z3, token3);
                    return true;
                case 16:
                    int i11 = parcel.readInt();
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onStylusHandwritingReady(i11, i12);
                    return true;
                case 17:
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    resetStylusHandwriting(i13);
                    return true;
                case 18:
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    switchKeyboardLayoutAsync(i14);
                    return true;
                case 19:
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setHandwritingSurfaceNotTouchable(z4);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethodPrivilegedOperations.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodPrivilegedOperations
            public void reportStartInputAsync(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethodPrivilegedOperations.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodPrivilegedOperations
            public void createInputContentUriToken(Uri uri, String str, AndroidFuture androidFuture) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethodPrivilegedOperations.DESCRIPTOR);
                    parcelObtain.writeTypedObject(uri, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodPrivilegedOperations
            public void reportFullscreenModeAsync(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethodPrivilegedOperations.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodPrivilegedOperations
            public void setInputMethod(String str, AndroidFuture androidFuture) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethodPrivilegedOperations.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodPrivilegedOperations
            public void setInputMethodAndSubtype(String str, InputMethodSubtype inputMethodSubtype, AndroidFuture androidFuture) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethodPrivilegedOperations.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(inputMethodSubtype, 0);
                    parcelObtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodPrivilegedOperations
            public void hideMySoftInput(ImeTracker.Token token, int i, int i2, AndroidFuture androidFuture) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethodPrivilegedOperations.DESCRIPTOR);
                    parcelObtain.writeTypedObject(token, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodPrivilegedOperations
            public void showMySoftInput(ImeTracker.Token token, int i, int i2, AndroidFuture androidFuture) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethodPrivilegedOperations.DESCRIPTOR);
                    parcelObtain.writeTypedObject(token, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodPrivilegedOperations
            public void updateStatusIconAsync(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethodPrivilegedOperations.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodPrivilegedOperations
            public void switchToPreviousInputMethod(AndroidFuture androidFuture) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethodPrivilegedOperations.DESCRIPTOR);
                    parcelObtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(10, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodPrivilegedOperations
            public void switchToNextInputMethod(boolean z, AndroidFuture androidFuture) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethodPrivilegedOperations.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(11, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodPrivilegedOperations
            public void shouldOfferSwitchingToNextInputMethod(AndroidFuture androidFuture) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethodPrivilegedOperations.DESCRIPTOR);
                    parcelObtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(12, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodPrivilegedOperations
            public void onImeSwitchButtonClickFromClient(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethodPrivilegedOperations.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(13, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodPrivilegedOperations
            public void notifyUserActionAsync() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethodPrivilegedOperations.DESCRIPTOR);
                    this.mRemote.transact(14, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodPrivilegedOperations
            public void applyImeVisibilityAsync(IBinder iBinder, boolean z, ImeTracker.Token token) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethodPrivilegedOperations.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeTypedObject(token, 0);
                    this.mRemote.transact(15, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodPrivilegedOperations
            public void onStylusHandwritingReady(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethodPrivilegedOperations.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(16, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodPrivilegedOperations
            public void resetStylusHandwriting(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethodPrivilegedOperations.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(17, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodPrivilegedOperations
            public void switchKeyboardLayoutAsync(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethodPrivilegedOperations.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(18, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodPrivilegedOperations
            public void setHandwritingSurfaceNotTouchable(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethodPrivilegedOperations.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(19, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodPrivilegedOperations
            public void setHandwritingTouchableRegion(Region region) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethodPrivilegedOperations.DESCRIPTOR);
                    parcelObtain.writeTypedObject(region, 0);
                    this.mRemote.transact(20, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
