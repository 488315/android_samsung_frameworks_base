package com.samsung.android.ssdid;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ISemSsdidManagerService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.ssdid.ISemSsdidManagerService";

    public static class Default implements ISemSsdidManagerService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.ssdid.ISemSsdidManagerService
        public String getSsdid() throws RemoteException {
            return null;
        }
    }

    String getSsdid() throws RemoteException;

    public static abstract class Stub extends Binder implements ISemSsdidManagerService {
        static final int TRANSACTION_getSsdid = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, ISemSsdidManagerService.DESCRIPTOR);
        }

        public static ISemSsdidManagerService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISemSsdidManagerService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISemSsdidManagerService)) {
                return (ISemSsdidManagerService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "getSsdid";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISemSsdidManagerService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISemSsdidManagerService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String ssdid = getSsdid();
                parcel2.writeNoException();
                parcel2.writeString(ssdid);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ISemSsdidManagerService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemSsdidManagerService.DESCRIPTOR;
            }

            @Override // com.samsung.android.ssdid.ISemSsdidManagerService
            public String getSsdid() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemSsdidManagerService.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
