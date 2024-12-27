package com.samsung.android.multiwindow;

import android.graphics.Rect;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IDexSnappingCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.multiwindow.IDexSnappingCallback";

    void onWindowSnappingChanged(int i, Rect rect) throws RemoteException;

    public static class Default implements IDexSnappingCallback {
        @Override // com.samsung.android.multiwindow.IDexSnappingCallback
        public void onWindowSnappingChanged(int taskID, Rect othersBounds) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IDexSnappingCallback {
        static final int TRANSACTION_onWindowSnappingChanged = 1;

        public Stub() {
            attachInterface(this, IDexSnappingCallback.DESCRIPTOR);
        }

        public static IDexSnappingCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IDexSnappingCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IDexSnappingCallback)) {
                return (IDexSnappingCallback) iin;
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
                    return "onWindowSnappingChanged";
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
                data.enforceInterface(IDexSnappingCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IDexSnappingCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    Rect _arg1 = (Rect) data.readTypedObject(Rect.CREATOR);
                    data.enforceNoDataAvail();
                    onWindowSnappingChanged(_arg0, _arg1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IDexSnappingCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDexSnappingCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.multiwindow.IDexSnappingCallback
            public void onWindowSnappingChanged(int taskID, Rect othersBounds) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IDexSnappingCallback.DESCRIPTOR);
                    _data.writeInt(taskID);
                    _data.writeTypedObject(othersBounds, 0);
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
