package com.samsung.android.wifi.intelligence.ila;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IIlaTrService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.wifi.intelligence.ila.IIlaTrService";

    public static class Default implements IIlaTrService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.wifi.intelligence.ila.IIlaTrService
        public void train(String str) throws RemoteException {
        }
    }

    void train(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IIlaTrService {
        static final int TRANSACTION_train = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IIlaTrService.DESCRIPTOR);
        }

        public static IIlaTrService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IIlaTrService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IIlaTrService)) {
                return (IIlaTrService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "train";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IIlaTrService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IIlaTrService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                train(readString);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IIlaTrService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IIlaTrService.DESCRIPTOR;
            }

            @Override // com.samsung.android.wifi.intelligence.ila.IIlaTrService
            public void train(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IIlaTrService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
