package com.samsung.android.service.sats;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ISatsService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.service.sats.ISatsService";

    public static class Default implements ISatsService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.service.sats.ISatsService
        public String executePseudoDrkAtCommnd(String str) throws RemoteException {
            return null;
        }
    }

    String executePseudoDrkAtCommnd(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements ISatsService {
        static final int TRANSACTION_executePseudoDrkAtCommnd = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, ISatsService.DESCRIPTOR);
        }

        public static ISatsService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISatsService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISatsService)) {
                return (ISatsService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "executePseudoDrkAtCommnd";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISatsService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISatsService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                String executePseudoDrkAtCommnd = executePseudoDrkAtCommnd(readString);
                parcel2.writeNoException();
                parcel2.writeString(executePseudoDrkAtCommnd);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ISatsService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISatsService.DESCRIPTOR;
            }

            @Override // com.samsung.android.service.sats.ISatsService
            public String executePseudoDrkAtCommnd(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISatsService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
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
