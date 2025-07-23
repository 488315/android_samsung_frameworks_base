package com.samsung.android.dsms.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IDsmsService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.dsms.aidl.IDsmsService";

    public static class Default implements IDsmsService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.dsms.aidl.IDsmsService
        public void sendMessage(String str, String str2, long j) throws RemoteException {
        }
    }

    void sendMessage(String str, String str2, long j) throws RemoteException;

    public static abstract class Stub extends Binder implements IDsmsService {
        static final int TRANSACTION_sendMessage = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IDsmsService.DESCRIPTOR);
        }

        public static IDsmsService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IDsmsService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IDsmsService)) {
                return (IDsmsService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "sendMessage";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IDsmsService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDsmsService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String readString = parcel.readString();
                String readString2 = parcel.readString();
                long readLong = parcel.readLong();
                parcel.enforceNoDataAvail();
                sendMessage(readString, readString2, readLong);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IDsmsService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDsmsService.DESCRIPTOR;
            }

            @Override // com.samsung.android.dsms.aidl.IDsmsService
            public void sendMessage(String str, String str2, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IDsmsService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeLong(j);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
