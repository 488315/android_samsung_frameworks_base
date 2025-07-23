package com.samsung.android.wifi.intelligence.ins;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.wifi.intelligence.ins.ISemInsSensorCallback;

/* loaded from: classes6.dex */
public interface ISemInsSensorCollectionService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.wifi.intelligence.ins.ISemInsSensorCollectionService";

    public static class Default implements ISemInsSensorCollectionService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.wifi.intelligence.ins.ISemInsSensorCollectionService
        public void start(ISemInsSensorCallback iSemInsSensorCallback) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.intelligence.ins.ISemInsSensorCollectionService
        public void stop() throws RemoteException {
        }
    }

    void start(ISemInsSensorCallback iSemInsSensorCallback) throws RemoteException;

    void stop() throws RemoteException;

    public static abstract class Stub extends Binder implements ISemInsSensorCollectionService {
        static final int TRANSACTION_start = 1;
        static final int TRANSACTION_stop = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, ISemInsSensorCollectionService.DESCRIPTOR);
        }

        public static ISemInsSensorCollectionService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISemInsSensorCollectionService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISemInsSensorCollectionService)) {
                return (ISemInsSensorCollectionService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "start";
            }
            if (i != 2) {
                return null;
            }
            return "stop";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISemInsSensorCollectionService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISemInsSensorCollectionService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ISemInsSensorCallback asInterface = ISemInsSensorCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                start(asInterface);
            } else if (i == 2) {
                stop();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISemInsSensorCollectionService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemInsSensorCollectionService.DESCRIPTOR;
            }

            @Override // com.samsung.android.wifi.intelligence.ins.ISemInsSensorCollectionService
            public void start(ISemInsSensorCallback iSemInsSensorCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemInsSensorCollectionService.DESCRIPTOR);
                    obtain.writeStrongInterface(iSemInsSensorCallback);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.intelligence.ins.ISemInsSensorCollectionService
            public void stop() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemInsSensorCollectionService.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
