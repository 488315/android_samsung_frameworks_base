package com.samsung.android.hardware.context;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ISemContextCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.hardware.context.ISemContextCallback";

    public static class Default implements ISemContextCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.hardware.context.ISemContextCallback
        public String getListenerInfo() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.hardware.context.ISemContextCallback
        public void semContextCallback(SemContextEvent semContextEvent) throws RemoteException {
        }
    }

    String getListenerInfo() throws RemoteException;

    void semContextCallback(SemContextEvent semContextEvent) throws RemoteException;

    public static abstract class Stub extends Binder implements ISemContextCallback {
        static final int TRANSACTION_getListenerInfo = 2;
        static final int TRANSACTION_semContextCallback = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, ISemContextCallback.DESCRIPTOR);
        }

        public static ISemContextCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISemContextCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISemContextCallback)) {
                return (ISemContextCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "semContextCallback";
            }
            if (i != 2) {
                return null;
            }
            return "getListenerInfo";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISemContextCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISemContextCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                SemContextEvent semContextEvent = (SemContextEvent) parcel.readTypedObject(SemContextEvent.CREATOR);
                parcel.enforceNoDataAvail();
                semContextCallback(semContextEvent);
            } else if (i == 2) {
                String listenerInfo = getListenerInfo();
                parcel2.writeNoException();
                parcel2.writeString(listenerInfo);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISemContextCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemContextCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.hardware.context.ISemContextCallback
            public void semContextCallback(SemContextEvent semContextEvent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemContextCallback.DESCRIPTOR);
                    obtain.writeTypedObject(semContextEvent, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.context.ISemContextCallback
            public String getListenerInfo() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemContextCallback.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
