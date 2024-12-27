package com.samsung.android.multiwindow;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.ThreadedRenderer;

/* loaded from: classes6.dex */
public interface IDragAndDropControllerProxy extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.multiwindow.IDragAndDropControllerProxy";

    void show(int i) throws RemoteException;

    public static class Default implements IDragAndDropControllerProxy {
        @Override // com.samsung.android.multiwindow.IDragAndDropControllerProxy
        public void show(int displayId) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IDragAndDropControllerProxy {
        static final int TRANSACTION_show = 1;

        public Stub() {
            attachInterface(this, IDragAndDropControllerProxy.DESCRIPTOR);
        }

        public static IDragAndDropControllerProxy asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IDragAndDropControllerProxy.DESCRIPTOR);
            if (iin != null && (iin instanceof IDragAndDropControllerProxy)) {
                return (IDragAndDropControllerProxy) iin;
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
                    return ThreadedRenderer.OVERDRAW_PROPERTY_SHOW;
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
                data.enforceInterface(IDragAndDropControllerProxy.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IDragAndDropControllerProxy.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    data.enforceNoDataAvail();
                    show(_arg0);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IDragAndDropControllerProxy {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDragAndDropControllerProxy.DESCRIPTOR;
            }

            @Override // com.samsung.android.multiwindow.IDragAndDropControllerProxy
            public void show(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IDragAndDropControllerProxy.DESCRIPTOR);
                    _data.writeInt(displayId);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
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
