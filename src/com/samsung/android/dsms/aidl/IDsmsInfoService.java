package com.samsung.android.dsms.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IDsmsInfoService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.dsms.aidl.IDsmsInfoService";

    public static class Default implements IDsmsInfoService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.dsms.aidl.IDsmsInfoService
        public boolean isCommercializedDevice() throws RemoteException {
            return false;
        }
    }

    boolean isCommercializedDevice() throws RemoteException;

    public static abstract class Stub extends Binder implements IDsmsInfoService {
        static final int TRANSACTION_isCommercializedDevice = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IDsmsInfoService.DESCRIPTOR);
        }

        public static IDsmsInfoService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IDsmsInfoService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IDsmsInfoService)) {
                return (IDsmsInfoService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "isCommercializedDevice";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IDsmsInfoService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDsmsInfoService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                boolean isCommercializedDevice = isCommercializedDevice();
                parcel2.writeNoException();
                parcel2.writeBoolean(isCommercializedDevice);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IDsmsInfoService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDsmsInfoService.DESCRIPTOR;
            }

            @Override // com.samsung.android.dsms.aidl.IDsmsInfoService
            public boolean isCommercializedDevice() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDsmsInfoService.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
