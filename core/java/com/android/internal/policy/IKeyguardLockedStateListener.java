package com.android.internal.policy;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface IKeyguardLockedStateListener extends IInterface {
    public static final String DESCRIPTOR =
            "com.android.internal.policy.IKeyguardLockedStateListener";

    void onKeyguardLockedStateChanged(boolean z) throws RemoteException;

    public static class Default implements IKeyguardLockedStateListener {
        @Override // com.android.internal.policy.IKeyguardLockedStateListener
        public void onKeyguardLockedStateChanged(boolean isKeyguardLocked) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IKeyguardLockedStateListener {
        static final int TRANSACTION_onKeyguardLockedStateChanged = 1;

        public Stub() {
            attachInterface(this, IKeyguardLockedStateListener.DESCRIPTOR);
        }

        public static IKeyguardLockedStateListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IKeyguardLockedStateListener.DESCRIPTOR);
            if (iin != null && (iin instanceof IKeyguardLockedStateListener)) {
                return (IKeyguardLockedStateListener) iin;
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
                    return "onKeyguardLockedStateChanged";
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
                data.enforceInterface(IKeyguardLockedStateListener.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IKeyguardLockedStateListener.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    boolean _arg0 = data.readBoolean();
                    data.enforceNoDataAvail();
                    onKeyguardLockedStateChanged(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IKeyguardLockedStateListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IKeyguardLockedStateListener.DESCRIPTOR;
            }

            @Override // com.android.internal.policy.IKeyguardLockedStateListener
            public void onKeyguardLockedStateChanged(boolean isKeyguardLocked)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IKeyguardLockedStateListener.DESCRIPTOR);
                    _data.writeBoolean(isKeyguardLocked);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
