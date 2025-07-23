package com.samsung.android.wifi.intelligence.iwh;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IIwhInfService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.wifi.intelligence.iwh.IIwhInfService";

    public static class Default implements IIwhInfService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.wifi.intelligence.iwh.IIwhInfService
        public void iwhInfResult(String str, int i, String str2, int i2) throws RemoteException {
        }
    }

    void iwhInfResult(String str, int i, String str2, int i2) throws RemoteException;

    public static abstract class Stub extends Binder implements IIwhInfService {
        static final int TRANSACTION_iwhInfResult = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IIwhInfService.DESCRIPTOR);
        }

        public static IIwhInfService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IIwhInfService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IIwhInfService)) {
                return (IIwhInfService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "iwhInfResult";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IIwhInfService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IIwhInfService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String readString = parcel.readString();
                int readInt = parcel.readInt();
                String readString2 = parcel.readString();
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                iwhInfResult(readString, readInt, readString2, readInt2);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IIwhInfService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IIwhInfService.DESCRIPTOR;
            }

            @Override // com.samsung.android.wifi.intelligence.iwh.IIwhInfService
            public void iwhInfResult(String str, int i, String str2, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IIwhInfService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeInt(i2);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
