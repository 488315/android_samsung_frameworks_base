package com.samsung.android.content.smartclip;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface IAirGestureListener extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.content.smartclip.IAirGestureListener";

    void onGesture(String str) throws RemoteException;

    public static class Default implements IAirGestureListener {
        @Override // com.samsung.android.content.smartclip.IAirGestureListener
        public void onGesture(String gesture) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IAirGestureListener {
        static final int TRANSACTION_onGesture = 1;

        public Stub() {
            attachInterface(this, IAirGestureListener.DESCRIPTOR);
        }

        public static IAirGestureListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IAirGestureListener.DESCRIPTOR);
            if (iin != null && (iin instanceof IAirGestureListener)) {
                return (IAirGestureListener) iin;
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
                    return "onGesture";
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
                data.enforceInterface(IAirGestureListener.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IAirGestureListener.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    data.enforceNoDataAvail();
                    onGesture(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IAirGestureListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAirGestureListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.content.smartclip.IAirGestureListener
            public void onGesture(String gesture) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IAirGestureListener.DESCRIPTOR);
                    _data.writeString(gesture);
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
