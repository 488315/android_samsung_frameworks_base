package com.android.internal.inputmethod;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.inputmethod.ImeTracker;

/* loaded from: classes5.dex */
public interface IInputMethodClient extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.inputmethod.IInputMethodClient";

    public static class Default implements IInputMethodClient {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.inputmethod.IInputMethodClient
        public void onBindAccessibilityService(InputBindResult inputBindResult, int i) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethodClient
        public void onBindMethod(InputBindResult inputBindResult) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethodClient
        public void onStartInputResult(InputBindResult inputBindResult, int i) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethodClient
        public void onUnbindAccessibilityService(int i, int i2) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethodClient
        public void onUnbindMethod(int i, int i2) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethodClient
        public void reportFullscreenMode(boolean z) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethodClient
        public void scheduleStartInputIfNecessary(boolean z) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethodClient
        public void setActive(boolean z, boolean z2) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethodClient
        public void setImeTraceEnabled(boolean z) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethodClient
        public void setImeVisibility(boolean z, ImeTracker.Token token) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethodClient
        public void setInteractive(boolean z, boolean z2) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethodClient
        public void throwExceptionFromSystem(String str) throws RemoteException {
        }
    }

    void onBindAccessibilityService(InputBindResult inputBindResult, int i) throws RemoteException;

    void onBindMethod(InputBindResult inputBindResult) throws RemoteException;

    void onStartInputResult(InputBindResult inputBindResult, int i) throws RemoteException;

    void onUnbindAccessibilityService(int i, int i2) throws RemoteException;

    void onUnbindMethod(int i, int i2) throws RemoteException;

    void reportFullscreenMode(boolean z) throws RemoteException;

    void scheduleStartInputIfNecessary(boolean z) throws RemoteException;

    void setActive(boolean z, boolean z2) throws RemoteException;

    void setImeTraceEnabled(boolean z) throws RemoteException;

    void setImeVisibility(boolean z, ImeTracker.Token token) throws RemoteException;

    void setInteractive(boolean z, boolean z2) throws RemoteException;

    void throwExceptionFromSystem(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IInputMethodClient {
        static final int TRANSACTION_onBindAccessibilityService = 3;
        static final int TRANSACTION_onBindMethod = 1;
        static final int TRANSACTION_onStartInputResult = 2;
        static final int TRANSACTION_onUnbindAccessibilityService = 5;
        static final int TRANSACTION_onUnbindMethod = 4;
        static final int TRANSACTION_reportFullscreenMode = 10;
        static final int TRANSACTION_scheduleStartInputIfNecessary = 9;
        static final int TRANSACTION_setActive = 6;
        static final int TRANSACTION_setImeTraceEnabled = 11;
        static final int TRANSACTION_setImeVisibility = 8;
        static final int TRANSACTION_setInteractive = 7;
        static final int TRANSACTION_throwExceptionFromSystem = 12;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 11;
        }

        public Stub() {
            attachInterface(this, IInputMethodClient.DESCRIPTOR);
        }

        public static IInputMethodClient asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IInputMethodClient.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IInputMethodClient)) {
                return (IInputMethodClient) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onBindMethod";
                case 2:
                    return "onStartInputResult";
                case 3:
                    return "onBindAccessibilityService";
                case 4:
                    return "onUnbindMethod";
                case 5:
                    return "onUnbindAccessibilityService";
                case 6:
                    return "setActive";
                case 7:
                    return "setInteractive";
                case 8:
                    return "setImeVisibility";
                case 9:
                    return "scheduleStartInputIfNecessary";
                case 10:
                    return "reportFullscreenMode";
                case 11:
                    return "setImeTraceEnabled";
                case 12:
                    return "throwExceptionFromSystem";
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
                parcel.enforceInterface(IInputMethodClient.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IInputMethodClient.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    InputBindResult inputBindResult = (InputBindResult) parcel.readTypedObject(InputBindResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    onBindMethod(inputBindResult);
                    return true;
                case 2:
                    InputBindResult inputBindResult2 = (InputBindResult) parcel.readTypedObject(InputBindResult.CREATOR);
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onStartInputResult(inputBindResult2, i3);
                    return true;
                case 3:
                    InputBindResult inputBindResult3 = (InputBindResult) parcel.readTypedObject(InputBindResult.CREATOR);
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onBindAccessibilityService(inputBindResult3, i4);
                    return true;
                case 4:
                    int i5 = parcel.readInt();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onUnbindMethod(i5, i6);
                    return true;
                case 5:
                    int i7 = parcel.readInt();
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onUnbindAccessibilityService(i7, i8);
                    return true;
                case 6:
                    boolean z = parcel.readBoolean();
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setActive(z, z2);
                    return true;
                case 7:
                    boolean z3 = parcel.readBoolean();
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setInteractive(z3, z4);
                    return true;
                case 8:
                    boolean z5 = parcel.readBoolean();
                    ImeTracker.Token token = (ImeTracker.Token) parcel.readTypedObject(ImeTracker.Token.CREATOR);
                    parcel.enforceNoDataAvail();
                    setImeVisibility(z5, token);
                    return true;
                case 9:
                    boolean z6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    scheduleStartInputIfNecessary(z6);
                    return true;
                case 10:
                    boolean z7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    reportFullscreenMode(z7);
                    return true;
                case 11:
                    boolean z8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setImeTraceEnabled(z8);
                    return true;
                case 12:
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    throwExceptionFromSystem(string);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IInputMethodClient {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IInputMethodClient.DESCRIPTOR;
            }

            @Override // com.android.internal.inputmethod.IInputMethodClient
            public void onBindMethod(InputBindResult inputBindResult) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethodClient.DESCRIPTOR);
                    parcelObtain.writeTypedObject(inputBindResult, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodClient
            public void onStartInputResult(InputBindResult inputBindResult, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethodClient.DESCRIPTOR);
                    parcelObtain.writeTypedObject(inputBindResult, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodClient
            public void onBindAccessibilityService(InputBindResult inputBindResult, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethodClient.DESCRIPTOR);
                    parcelObtain.writeTypedObject(inputBindResult, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodClient
            public void onUnbindMethod(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethodClient.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodClient
            public void onUnbindAccessibilityService(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethodClient.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodClient
            public void setActive(boolean z, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethodClient.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodClient
            public void setInteractive(boolean z, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethodClient.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodClient
            public void setImeVisibility(boolean z, ImeTracker.Token token) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethodClient.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeTypedObject(token, 0);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodClient
            public void scheduleStartInputIfNecessary(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethodClient.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodClient
            public void reportFullscreenMode(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethodClient.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(10, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodClient
            public void setImeTraceEnabled(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethodClient.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(11, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodClient
            public void throwExceptionFromSystem(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethodClient.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(12, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
