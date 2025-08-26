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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IMdfService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IMdfService)) {
                return (IMdfService) iInterfaceQueryLocalInterface;
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
                int iInitCCMode = initCCMode();
                parcel2.writeNoException();
                parcel2.writeInt(iInitCCMode);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMdfService.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
