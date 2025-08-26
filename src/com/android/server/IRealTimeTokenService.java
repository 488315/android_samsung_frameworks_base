package com.android.server;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IRealTimeTokenService extends IInterface {
    public static final String DESCRIPTOR = "com.android.server.IRealTimeTokenService";

    public static class Default implements IRealTimeTokenService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.server.IRealTimeTokenService
        public int checkTokenInfoExpiry(long j) throws RemoteException {
            return 0;
        }

        @Override // com.android.server.IRealTimeTokenService
        public int registerTokenInfo(long j, long j2) throws RemoteException {
            return 0;
        }

        @Override // com.android.server.IRealTimeTokenService
        public int unregisterAllTokenInfo() throws RemoteException {
            return 0;
        }

        @Override // com.android.server.IRealTimeTokenService
        public int unregisterTokenInfo(long j) throws RemoteException {
            return 0;
        }
    }

    int checkTokenInfoExpiry(long j) throws RemoteException;

    int registerTokenInfo(long j, long j2) throws RemoteException;

    int unregisterAllTokenInfo() throws RemoteException;

    int unregisterTokenInfo(long j) throws RemoteException;

    public static abstract class Stub extends Binder implements IRealTimeTokenService {
        static final int TRANSACTION_checkTokenInfoExpiry = 2;
        static final int TRANSACTION_registerTokenInfo = 1;
        static final int TRANSACTION_unregisterAllTokenInfo = 4;
        static final int TRANSACTION_unregisterTokenInfo = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, IRealTimeTokenService.DESCRIPTOR);
        }

        public static IRealTimeTokenService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IRealTimeTokenService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IRealTimeTokenService)) {
                return (IRealTimeTokenService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "registerTokenInfo";
            }
            if (i == 2) {
                return "checkTokenInfoExpiry";
            }
            if (i == 3) {
                return "unregisterTokenInfo";
            }
            if (i != 4) {
                return null;
            }
            return "unregisterAllTokenInfo";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IRealTimeTokenService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IRealTimeTokenService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                long j = parcel.readLong();
                long j2 = parcel.readLong();
                parcel.enforceNoDataAvail();
                int iRegisterTokenInfo = registerTokenInfo(j, j2);
                parcel2.writeNoException();
                parcel2.writeInt(iRegisterTokenInfo);
            } else if (i == 2) {
                long j3 = parcel.readLong();
                parcel.enforceNoDataAvail();
                int iCheckTokenInfoExpiry = checkTokenInfoExpiry(j3);
                parcel2.writeNoException();
                parcel2.writeInt(iCheckTokenInfoExpiry);
            } else if (i == 3) {
                long j4 = parcel.readLong();
                parcel.enforceNoDataAvail();
                int iUnregisterTokenInfo = unregisterTokenInfo(j4);
                parcel2.writeNoException();
                parcel2.writeInt(iUnregisterTokenInfo);
            } else if (i == 4) {
                int iUnregisterAllTokenInfo = unregisterAllTokenInfo();
                parcel2.writeNoException();
                parcel2.writeInt(iUnregisterAllTokenInfo);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IRealTimeTokenService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRealTimeTokenService.DESCRIPTOR;
            }

            @Override // com.android.server.IRealTimeTokenService
            public int registerTokenInfo(long j, long j2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRealTimeTokenService.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeLong(j2);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.server.IRealTimeTokenService
            public int checkTokenInfoExpiry(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRealTimeTokenService.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.server.IRealTimeTokenService
            public int unregisterTokenInfo(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRealTimeTokenService.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.server.IRealTimeTokenService
            public int unregisterAllTokenInfo() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRealTimeTokenService.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
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
