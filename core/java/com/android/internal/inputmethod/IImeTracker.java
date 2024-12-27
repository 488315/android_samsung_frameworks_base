package com.android.internal.inputmethod;

import android.Manifest;
import android.app.ActivityThread;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PermissionEnforcer;
import android.os.RemoteException;
import android.view.inputmethod.ImeTracker;
import com.android.internal.infra.AndroidFuture;

/* loaded from: classes5.dex */
public interface IImeTracker extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.inputmethod.IImeTracker";

    void finishTrackingPendingImeVisibilityRequests(AndroidFuture androidFuture) throws RemoteException;

    boolean hasPendingImeVisibilityRequests() throws RemoteException;

    void onCancelled(ImeTracker.Token token, int i) throws RemoteException;

    void onDispatched(ImeTracker.Token token) throws RemoteException;

    void onFailed(ImeTracker.Token token, int i) throws RemoteException;

    void onHidden(ImeTracker.Token token) throws RemoteException;

    void onProgress(IBinder iBinder, int i) throws RemoteException;

    void onShown(ImeTracker.Token token) throws RemoteException;

    ImeTracker.Token onStart(String str, int i, int i2, int i3, int i4, boolean z) throws RemoteException;

    public static class Default implements IImeTracker {
        @Override // com.android.internal.inputmethod.IImeTracker
        public ImeTracker.Token onStart(String tag, int uid, int type, int origin, int reason, boolean fromUser) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.inputmethod.IImeTracker
        public void onProgress(IBinder binder, int phase) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IImeTracker
        public void onFailed(ImeTracker.Token statsToken, int phase) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IImeTracker
        public void onCancelled(ImeTracker.Token statsToken, int phase) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IImeTracker
        public void onShown(ImeTracker.Token statsToken) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IImeTracker
        public void onHidden(ImeTracker.Token statsToken) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IImeTracker
        public void onDispatched(ImeTracker.Token statsToken) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IImeTracker
        public boolean hasPendingImeVisibilityRequests() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.inputmethod.IImeTracker
        public void finishTrackingPendingImeVisibilityRequests(AndroidFuture completionSignal) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IImeTracker {
        static final int TRANSACTION_finishTrackingPendingImeVisibilityRequests = 9;
        static final int TRANSACTION_hasPendingImeVisibilityRequests = 8;
        static final int TRANSACTION_onCancelled = 4;
        static final int TRANSACTION_onDispatched = 7;
        static final int TRANSACTION_onFailed = 3;
        static final int TRANSACTION_onHidden = 6;
        static final int TRANSACTION_onProgress = 2;
        static final int TRANSACTION_onShown = 5;
        static final int TRANSACTION_onStart = 1;
        private final PermissionEnforcer mEnforcer;

        public Stub(PermissionEnforcer enforcer) {
            attachInterface(this, IImeTracker.DESCRIPTOR);
            if (enforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = enforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IImeTracker asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IImeTracker.DESCRIPTOR);
            if (iin != null && (iin instanceof IImeTracker)) {
                return (IImeTracker) iin;
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
                    return "onStart";
                case 2:
                    return "onProgress";
                case 3:
                    return "onFailed";
                case 4:
                    return "onCancelled";
                case 5:
                    return "onShown";
                case 6:
                    return "onHidden";
                case 7:
                    return "onDispatched";
                case 8:
                    return "hasPendingImeVisibilityRequests";
                case 9:
                    return "finishTrackingPendingImeVisibilityRequests";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IImeTracker.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IImeTracker.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    int _arg1 = data.readInt();
                    int _arg2 = data.readInt();
                    int _arg3 = data.readInt();
                    int _arg4 = data.readInt();
                    boolean _arg5 = data.readBoolean();
                    data.enforceNoDataAvail();
                    ImeTracker.Token _result = onStart(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5);
                    reply.writeNoException();
                    reply.writeTypedObject(_result, 1);
                    return true;
                case 2:
                    IBinder _arg02 = data.readStrongBinder();
                    int _arg12 = data.readInt();
                    data.enforceNoDataAvail();
                    onProgress(_arg02, _arg12);
                    return true;
                case 3:
                    ImeTracker.Token _arg03 = (ImeTracker.Token) data.readTypedObject(ImeTracker.Token.CREATOR);
                    int _arg13 = data.readInt();
                    data.enforceNoDataAvail();
                    onFailed(_arg03, _arg13);
                    return true;
                case 4:
                    ImeTracker.Token _arg04 = (ImeTracker.Token) data.readTypedObject(ImeTracker.Token.CREATOR);
                    int _arg14 = data.readInt();
                    data.enforceNoDataAvail();
                    onCancelled(_arg04, _arg14);
                    return true;
                case 5:
                    ImeTracker.Token _arg05 = (ImeTracker.Token) data.readTypedObject(ImeTracker.Token.CREATOR);
                    data.enforceNoDataAvail();
                    onShown(_arg05);
                    return true;
                case 6:
                    ImeTracker.Token _arg06 = (ImeTracker.Token) data.readTypedObject(ImeTracker.Token.CREATOR);
                    data.enforceNoDataAvail();
                    onHidden(_arg06);
                    return true;
                case 7:
                    ImeTracker.Token _arg07 = (ImeTracker.Token) data.readTypedObject(ImeTracker.Token.CREATOR);
                    data.enforceNoDataAvail();
                    onDispatched(_arg07);
                    return true;
                case 8:
                    boolean _result2 = hasPendingImeVisibilityRequests();
                    reply.writeNoException();
                    reply.writeBoolean(_result2);
                    return true;
                case 9:
                    AndroidFuture _arg08 = (AndroidFuture) data.readTypedObject(AndroidFuture.CREATOR);
                    data.enforceNoDataAvail();
                    finishTrackingPendingImeVisibilityRequests(_arg08);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IImeTracker {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IImeTracker.DESCRIPTOR;
            }

            @Override // com.android.internal.inputmethod.IImeTracker
            public ImeTracker.Token onStart(String tag, int uid, int type, int origin, int reason, boolean fromUser) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IImeTracker.DESCRIPTOR);
                    _data.writeString(tag);
                    _data.writeInt(uid);
                    _data.writeInt(type);
                    _data.writeInt(origin);
                    _data.writeInt(reason);
                    _data.writeBoolean(fromUser);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    ImeTracker.Token _result = (ImeTracker.Token) _reply.readTypedObject(ImeTracker.Token.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IImeTracker
            public void onProgress(IBinder binder, int phase) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IImeTracker.DESCRIPTOR);
                    _data.writeStrongBinder(binder);
                    _data.writeInt(phase);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IImeTracker
            public void onFailed(ImeTracker.Token statsToken, int phase) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IImeTracker.DESCRIPTOR);
                    _data.writeTypedObject(statsToken, 0);
                    _data.writeInt(phase);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IImeTracker
            public void onCancelled(ImeTracker.Token statsToken, int phase) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IImeTracker.DESCRIPTOR);
                    _data.writeTypedObject(statsToken, 0);
                    _data.writeInt(phase);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IImeTracker
            public void onShown(ImeTracker.Token statsToken) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IImeTracker.DESCRIPTOR);
                    _data.writeTypedObject(statsToken, 0);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IImeTracker
            public void onHidden(ImeTracker.Token statsToken) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IImeTracker.DESCRIPTOR);
                    _data.writeTypedObject(statsToken, 0);
                    this.mRemote.transact(6, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IImeTracker
            public void onDispatched(ImeTracker.Token statsToken) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IImeTracker.DESCRIPTOR);
                    _data.writeTypedObject(statsToken, 0);
                    this.mRemote.transact(7, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IImeTracker
            public boolean hasPendingImeVisibilityRequests() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IImeTracker.DESCRIPTOR);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IImeTracker
            public void finishTrackingPendingImeVisibilityRequests(AndroidFuture completionSignal) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IImeTracker.DESCRIPTOR);
                    _data.writeTypedObject(completionSignal, 0);
                    this.mRemote.transact(9, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        protected void hasPendingImeVisibilityRequests_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.TEST_INPUT_METHOD, getCallingPid(), getCallingUid());
        }

        protected void finishTrackingPendingImeVisibilityRequests_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.TEST_INPUT_METHOD, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 8;
        }
    }
}
