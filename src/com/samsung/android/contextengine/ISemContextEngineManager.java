package com.samsung.android.contextengine;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ISemContextEngineManager extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.contextengine.ISemContextEngineManager";

    public static class Default implements ISemContextEngineManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.contextengine.ISemContextEngineManager
        public void setDefault() throws RemoteException {
        }
    }

    void setDefault() throws RemoteException;

    public static abstract class Stub extends Binder implements ISemContextEngineManager {
        static final int TRANSACTION_setDefault = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, ISemContextEngineManager.DESCRIPTOR);
        }

        public static ISemContextEngineManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISemContextEngineManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISemContextEngineManager)) {
                return (ISemContextEngineManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "setDefault";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISemContextEngineManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISemContextEngineManager.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                setDefault();
                parcel2.writeNoException();
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ISemContextEngineManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemContextEngineManager.DESCRIPTOR;
            }

            @Override // com.samsung.android.contextengine.ISemContextEngineManager
            public void setDefault() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemContextEngineManager.DESCRIPTOR);
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
