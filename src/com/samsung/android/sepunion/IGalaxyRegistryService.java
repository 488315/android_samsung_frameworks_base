package com.samsung.android.sepunion;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IGalaxyRegistryService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.sepunion.IGalaxyRegistryService";

    public static class Default implements IGalaxyRegistryService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.sepunion.IGalaxyRegistryService
        public void registerListener(String str, IBinder iBinder) throws RemoteException {
        }

        @Override // com.samsung.android.sepunion.IGalaxyRegistryService
        public void unRegisterListener(String str, IBinder iBinder) throws RemoteException {
        }
    }

    void registerListener(String str, IBinder iBinder) throws RemoteException;

    void unRegisterListener(String str, IBinder iBinder) throws RemoteException;

    public static abstract class Stub extends Binder implements IGalaxyRegistryService {
        static final int TRANSACTION_registerListener = 1;
        static final int TRANSACTION_unRegisterListener = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IGalaxyRegistryService.DESCRIPTOR);
        }

        public static IGalaxyRegistryService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IGalaxyRegistryService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IGalaxyRegistryService)) {
                return (IGalaxyRegistryService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "registerListener";
            }
            if (i != 2) {
                return null;
            }
            return "unRegisterListener";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IGalaxyRegistryService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IGalaxyRegistryService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String string = parcel.readString();
                IBinder strongBinder = parcel.readStrongBinder();
                parcel.enforceNoDataAvail();
                registerListener(string, strongBinder);
            } else if (i == 2) {
                String string2 = parcel.readString();
                IBinder strongBinder2 = parcel.readStrongBinder();
                parcel.enforceNoDataAvail();
                unRegisterListener(string2, strongBinder2);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IGalaxyRegistryService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IGalaxyRegistryService.DESCRIPTOR;
            }

            @Override // com.samsung.android.sepunion.IGalaxyRegistryService
            public void registerListener(String str, IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IGalaxyRegistryService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.sepunion.IGalaxyRegistryService
            public void unRegisterListener(String str, IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IGalaxyRegistryService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
