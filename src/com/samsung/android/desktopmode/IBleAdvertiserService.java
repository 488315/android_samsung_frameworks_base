package com.samsung.android.desktopmode;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IBleAdvertiserService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.desktopmode.IBleAdvertiserService";

    public static class Default implements IBleAdvertiserService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.desktopmode.IBleAdvertiserService
        public boolean needToKeepBinding() throws RemoteException {
            return false;
        }
    }

    boolean needToKeepBinding() throws RemoteException;

    public static abstract class Stub extends Binder implements IBleAdvertiserService {
        static final int TRANSACTION_needToKeepBinding = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IBleAdvertiserService.DESCRIPTOR);
        }

        public static IBleAdvertiserService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IBleAdvertiserService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IBleAdvertiserService)) {
                return (IBleAdvertiserService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "needToKeepBinding";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IBleAdvertiserService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IBleAdvertiserService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                boolean zNeedToKeepBinding = needToKeepBinding();
                parcel2.writeNoException();
                parcel2.writeBoolean(zNeedToKeepBinding);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IBleAdvertiserService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IBleAdvertiserService.DESCRIPTOR;
            }

            @Override // com.samsung.android.desktopmode.IBleAdvertiserService
            public boolean needToKeepBinding() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBleAdvertiserService.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
