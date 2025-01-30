package com.samsung.android.aod;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes5.dex */
public interface IAODDozeCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.aod.IAODDozeCallback";

    void onAODToastRequested(AODToast aODToast) throws RemoteException;

    void onDozeAcquired() throws RemoteException;

    void onDozeReleased() throws RemoteException;

    public static class Default implements IAODDozeCallback {
        @Override // com.samsung.android.aod.IAODDozeCallback
        public void onDozeAcquired() throws RemoteException {
        }

        @Override // com.samsung.android.aod.IAODDozeCallback
        public void onDozeReleased() throws RemoteException {
        }

        @Override // com.samsung.android.aod.IAODDozeCallback
        public void onAODToastRequested(AODToast toast) throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IAODDozeCallback {
        static final int TRANSACTION_onAODToastRequested = 3;
        static final int TRANSACTION_onDozeAcquired = 1;
        static final int TRANSACTION_onDozeReleased = 2;

        public Stub() {
            attachInterface(this, IAODDozeCallback.DESCRIPTOR);
        }

        public static IAODDozeCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IAODDozeCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IAODDozeCallback)) {
                return (IAODDozeCallback) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onDozeAcquired";
                case 2:
                    return "onDozeReleased";
                case 3:
                    return "onAODToastRequested";
                default:
                    return null;
            }
        }

        @Override // android.p009os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p009os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IAODDozeCallback.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(IAODDozeCallback.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            onDozeAcquired();
                            return true;
                        case 2:
                            onDozeReleased();
                            return true;
                        case 3:
                            AODToast _arg0 = (AODToast) data.readTypedObject(AODToast.CREATOR);
                            data.enforceNoDataAvail();
                            onAODToastRequested(_arg0);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IAODDozeCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAODDozeCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.aod.IAODDozeCallback
            public void onDozeAcquired() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IAODDozeCallback.DESCRIPTOR);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.aod.IAODDozeCallback
            public void onDozeReleased() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IAODDozeCallback.DESCRIPTOR);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.aod.IAODDozeCallback
            public void onAODToastRequested(AODToast toast) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IAODDozeCallback.DESCRIPTOR);
                    _data.writeTypedObject(toast, 0);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 2;
        }
    }
}
