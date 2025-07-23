package com.samsung.android.sepunion;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IGoodCatchDispatcher extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.sepunion.IGoodCatchDispatcher";

    public static class Default implements IGoodCatchDispatcher {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.sepunion.IGoodCatchDispatcher
        public void onStart(String str) throws RemoteException {
        }

        @Override // com.samsung.android.sepunion.IGoodCatchDispatcher
        public void onStop(String str) throws RemoteException {
        }
    }

    void onStart(String str) throws RemoteException;

    void onStop(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IGoodCatchDispatcher {
        static final int TRANSACTION_onStart = 1;
        static final int TRANSACTION_onStop = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IGoodCatchDispatcher.DESCRIPTOR);
        }

        public static IGoodCatchDispatcher asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IGoodCatchDispatcher.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IGoodCatchDispatcher)) {
                return (IGoodCatchDispatcher) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onStart";
            }
            if (i != 2) {
                return null;
            }
            return "onStop";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IGoodCatchDispatcher.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IGoodCatchDispatcher.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                onStart(readString);
            } else if (i == 2) {
                String readString2 = parcel.readString();
                parcel.enforceNoDataAvail();
                onStop(readString2);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IGoodCatchDispatcher {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IGoodCatchDispatcher.DESCRIPTOR;
            }

            @Override // com.samsung.android.sepunion.IGoodCatchDispatcher
            public void onStart(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IGoodCatchDispatcher.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sepunion.IGoodCatchDispatcher
            public void onStop(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IGoodCatchDispatcher.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
