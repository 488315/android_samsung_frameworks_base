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

    public static class Default implements IDragAndDropControllerProxy {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.multiwindow.IDragAndDropControllerProxy
        public void show(int i) throws RemoteException {
        }
    }

    void show(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IDragAndDropControllerProxy {
        static final int TRANSACTION_show = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IDragAndDropControllerProxy.DESCRIPTOR);
        }

        public static IDragAndDropControllerProxy asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IDragAndDropControllerProxy.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IDragAndDropControllerProxy)) {
                return (IDragAndDropControllerProxy) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return ThreadedRenderer.OVERDRAW_PROPERTY_SHOW;
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IDragAndDropControllerProxy.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDragAndDropControllerProxy.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                show(readInt);
                parcel2.writeNoException();
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IDragAndDropControllerProxy {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDragAndDropControllerProxy.DESCRIPTOR;
            }

            @Override // com.samsung.android.multiwindow.IDragAndDropControllerProxy
            public void show(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDragAndDropControllerProxy.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
