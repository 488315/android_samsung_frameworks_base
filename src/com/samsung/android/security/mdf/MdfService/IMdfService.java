package com.samsung.android.security.mdf.MdfService;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IMdfService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.security.mdf.MdfService.IMdfService";

    public static class Default implements IMdfService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.security.mdf.MdfService.IMdfService
        public int initCCMode() throws RemoteException {
            return 0;
        }
    }

    int initCCMode() throws RemoteException;

    public static abstract class Stub extends Binder implements IMdfService {
        static final int TRANSACTION_initCCMode = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IMdfService.DESCRIPTOR);
        }

        public static IMdfService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IMdfService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IMdfService)) {
                return (IMdfService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "initCCMode";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IMdfService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IMdfService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int initCCMode = initCCMode();
                parcel2.writeNoException();
                parcel2.writeInt(initCCMode);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IMdfService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IMdfService.DESCRIPTOR;
            }

            @Override // com.samsung.android.security.mdf.MdfService.IMdfService
            public int initCCMode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMdfService.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
