package com.samsung.aasaservice;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IAASA extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.aasaservice.IAASA";

    public static class Default implements IAASA {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.aasaservice.IAASA
        public void getRemoveTargetList(int i) throws RemoteException {
        }

        @Override // com.samsung.aasaservice.IAASA
        public String getTrustedToday() throws RemoteException {
            return null;
        }

        @Override // com.samsung.aasaservice.IAASA
        public void onReceivePolicyUpdateCompletion() throws RemoteException {
        }

        @Override // com.samsung.aasaservice.IAASA
        public String[] readInstallBlockedPKList() throws RemoteException {
            return null;
        }
    }

    void getRemoveTargetList(int i) throws RemoteException;

    String getTrustedToday() throws RemoteException;

    void onReceivePolicyUpdateCompletion() throws RemoteException;

    String[] readInstallBlockedPKList() throws RemoteException;

    public static abstract class Stub extends Binder implements IAASA {
        static final int TRANSACTION_getRemoveTargetList = 1;
        static final int TRANSACTION_getTrustedToday = 3;
        static final int TRANSACTION_onReceivePolicyUpdateCompletion = 4;
        static final int TRANSACTION_readInstallBlockedPKList = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, IAASA.DESCRIPTOR);
        }

        public static IAASA asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IAASA.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IAASA)) {
                return (IAASA) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "getRemoveTargetList";
            }
            if (i == 2) {
                return "readInstallBlockedPKList";
            }
            if (i == 3) {
                return "getTrustedToday";
            }
            if (i != 4) {
                return null;
            }
            return "onReceivePolicyUpdateCompletion";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IAASA.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IAASA.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                getRemoveTargetList(readInt);
                parcel2.writeNoException();
            } else if (i == 2) {
                String[] readInstallBlockedPKList = readInstallBlockedPKList();
                parcel2.writeNoException();
                parcel2.writeStringArray(readInstallBlockedPKList);
            } else if (i == 3) {
                String trustedToday = getTrustedToday();
                parcel2.writeNoException();
                parcel2.writeString(trustedToday);
            } else if (i == 4) {
                onReceivePolicyUpdateCompletion();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IAASA {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAASA.DESCRIPTOR;
            }

            @Override // com.samsung.aasaservice.IAASA
            public void getRemoveTargetList(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAASA.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.aasaservice.IAASA
            public String[] readInstallBlockedPKList() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAASA.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.aasaservice.IAASA
            public String getTrustedToday() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAASA.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.aasaservice.IAASA
            public void onReceivePolicyUpdateCompletion() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IAASA.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
