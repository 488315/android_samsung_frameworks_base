package com.android.internal.inputmethod;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface IInputMethodClient extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.inputmethod.IInputMethodClient";

    void onBindAccessibilityService(InputBindResult inputBindResult, int i) throws RemoteException;

    void onBindMethod(InputBindResult inputBindResult) throws RemoteException;

    void onStartInputResult(InputBindResult inputBindResult, int i) throws RemoteException;

    void onUnbindAccessibilityService(int i, int i2) throws RemoteException;

    void onUnbindMethod(int i, int i2) throws RemoteException;

    void reportFullscreenMode(boolean z) throws RemoteException;

    void scheduleStartInputIfNecessary(boolean z) throws RemoteException;

    void setActive(boolean z, boolean z2) throws RemoteException;

    void setImeTraceEnabled(boolean z) throws RemoteException;

    void setImeVisibility(boolean z) throws RemoteException;

    void setInteractive(boolean z, boolean z2) throws RemoteException;

    void throwExceptionFromSystem(String str) throws RemoteException;

    public static class Default implements IInputMethodClient {
        @Override // com.android.internal.inputmethod.IInputMethodClient
        public void onBindMethod(InputBindResult res) throws RemoteException {}

        @Override // com.android.internal.inputmethod.IInputMethodClient
        public void onStartInputResult(InputBindResult res, int startInputSeq)
                throws RemoteException {}

        @Override // com.android.internal.inputmethod.IInputMethodClient
        public void onBindAccessibilityService(InputBindResult res, int id)
                throws RemoteException {}

        @Override // com.android.internal.inputmethod.IInputMethodClient
        public void onUnbindMethod(int sequence, int unbindReason) throws RemoteException {}

        @Override // com.android.internal.inputmethod.IInputMethodClient
        public void onUnbindAccessibilityService(int sequence, int id) throws RemoteException {}

        @Override // com.android.internal.inputmethod.IInputMethodClient
        public void setActive(boolean active, boolean fullscreen) throws RemoteException {}

        @Override // com.android.internal.inputmethod.IInputMethodClient
        public void setInteractive(boolean active, boolean fullscreen) throws RemoteException {}

        @Override // com.android.internal.inputmethod.IInputMethodClient
        public void setImeVisibility(boolean visible) throws RemoteException {}

        @Override // com.android.internal.inputmethod.IInputMethodClient
        public void scheduleStartInputIfNecessary(boolean fullscreen) throws RemoteException {}

        @Override // com.android.internal.inputmethod.IInputMethodClient
        public void reportFullscreenMode(boolean fullscreen) throws RemoteException {}

        @Override // com.android.internal.inputmethod.IInputMethodClient
        public void setImeTraceEnabled(boolean enabled) throws RemoteException {}

        @Override // com.android.internal.inputmethod.IInputMethodClient
        public void throwExceptionFromSystem(String message) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IInputMethodClient {
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

        public Stub() {
            attachInterface(this, IInputMethodClient.DESCRIPTOR);
        }

        public static IInputMethodClient asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IInputMethodClient.DESCRIPTOR);
            if (iin != null && (iin instanceof IInputMethodClient)) {
                return (IInputMethodClient) iin;
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
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IInputMethodClient.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IInputMethodClient.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    InputBindResult _arg0 =
                            (InputBindResult) data.readTypedObject(InputBindResult.CREATOR);
                    data.enforceNoDataAvail();
                    onBindMethod(_arg0);
                    return true;
                case 2:
                    InputBindResult _arg02 =
                            (InputBindResult) data.readTypedObject(InputBindResult.CREATOR);
                    int _arg1 = data.readInt();
                    data.enforceNoDataAvail();
                    onStartInputResult(_arg02, _arg1);
                    return true;
                case 3:
                    InputBindResult _arg03 =
                            (InputBindResult) data.readTypedObject(InputBindResult.CREATOR);
                    int _arg12 = data.readInt();
                    data.enforceNoDataAvail();
                    onBindAccessibilityService(_arg03, _arg12);
                    return true;
                case 4:
                    int _arg04 = data.readInt();
                    int _arg13 = data.readInt();
                    data.enforceNoDataAvail();
                    onUnbindMethod(_arg04, _arg13);
                    return true;
                case 5:
                    int _arg05 = data.readInt();
                    int _arg14 = data.readInt();
                    data.enforceNoDataAvail();
                    onUnbindAccessibilityService(_arg05, _arg14);
                    return true;
                case 6:
                    boolean _arg06 = data.readBoolean();
                    boolean _arg15 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setActive(_arg06, _arg15);
                    return true;
                case 7:
                    boolean _arg07 = data.readBoolean();
                    boolean _arg16 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setInteractive(_arg07, _arg16);
                    return true;
                case 8:
                    boolean _arg08 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setImeVisibility(_arg08);
                    return true;
                case 9:
                    boolean _arg09 = data.readBoolean();
                    data.enforceNoDataAvail();
                    scheduleStartInputIfNecessary(_arg09);
                    return true;
                case 10:
                    boolean _arg010 = data.readBoolean();
                    data.enforceNoDataAvail();
                    reportFullscreenMode(_arg010);
                    return true;
                case 11:
                    boolean _arg011 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setImeTraceEnabled(_arg011);
                    return true;
                case 12:
                    String _arg012 = data.readString();
                    data.enforceNoDataAvail();
                    throwExceptionFromSystem(_arg012);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IInputMethodClient {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IInputMethodClient.DESCRIPTOR;
            }

            @Override // com.android.internal.inputmethod.IInputMethodClient
            public void onBindMethod(InputBindResult res) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IInputMethodClient.DESCRIPTOR);
                    _data.writeTypedObject(res, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodClient
            public void onStartInputResult(InputBindResult res, int startInputSeq)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IInputMethodClient.DESCRIPTOR);
                    _data.writeTypedObject(res, 0);
                    _data.writeInt(startInputSeq);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodClient
            public void onBindAccessibilityService(InputBindResult res, int id)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IInputMethodClient.DESCRIPTOR);
                    _data.writeTypedObject(res, 0);
                    _data.writeInt(id);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodClient
            public void onUnbindMethod(int sequence, int unbindReason) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IInputMethodClient.DESCRIPTOR);
                    _data.writeInt(sequence);
                    _data.writeInt(unbindReason);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodClient
            public void onUnbindAccessibilityService(int sequence, int id) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IInputMethodClient.DESCRIPTOR);
                    _data.writeInt(sequence);
                    _data.writeInt(id);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodClient
            public void setActive(boolean active, boolean fullscreen) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IInputMethodClient.DESCRIPTOR);
                    _data.writeBoolean(active);
                    _data.writeBoolean(fullscreen);
                    this.mRemote.transact(6, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodClient
            public void setInteractive(boolean active, boolean fullscreen) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IInputMethodClient.DESCRIPTOR);
                    _data.writeBoolean(active);
                    _data.writeBoolean(fullscreen);
                    this.mRemote.transact(7, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodClient
            public void setImeVisibility(boolean visible) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IInputMethodClient.DESCRIPTOR);
                    _data.writeBoolean(visible);
                    this.mRemote.transact(8, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodClient
            public void scheduleStartInputIfNecessary(boolean fullscreen) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IInputMethodClient.DESCRIPTOR);
                    _data.writeBoolean(fullscreen);
                    this.mRemote.transact(9, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodClient
            public void reportFullscreenMode(boolean fullscreen) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IInputMethodClient.DESCRIPTOR);
                    _data.writeBoolean(fullscreen);
                    this.mRemote.transact(10, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodClient
            public void setImeTraceEnabled(boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IInputMethodClient.DESCRIPTOR);
                    _data.writeBoolean(enabled);
                    this.mRemote.transact(11, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodClient
            public void throwExceptionFromSystem(String message) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IInputMethodClient.DESCRIPTOR);
                    _data.writeString(message);
                    this.mRemote.transact(12, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 11;
        }
    }
}
