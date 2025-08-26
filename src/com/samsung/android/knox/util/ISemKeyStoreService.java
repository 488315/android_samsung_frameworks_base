package com.samsung.android.knox.util;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ISemKeyStoreService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.util.ISemKeyStoreService";

    public static class Default implements ISemKeyStoreService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.util.ISemKeyStoreService
        public int getKeystoreStatus() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.util.ISemKeyStoreService
        public void grantAccessForAKS(int i, String str) throws RemoteException {
        }

        @Override // com.samsung.android.knox.util.ISemKeyStoreService
        public int installCACert(SemCertAndroidKeyStore semCertAndroidKeyStore) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.util.ISemKeyStoreService
        public int installCertificateInAndroidKeyStore(SemCertByte semCertByte, String str, char[] cArr, int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.util.ISemKeyStoreService
        public int isAliasExists(String str) throws RemoteException {
            return 0;
        }
    }

    int getKeystoreStatus() throws RemoteException;

    void grantAccessForAKS(int i, String str) throws RemoteException;

    int installCACert(SemCertAndroidKeyStore semCertAndroidKeyStore) throws RemoteException;

    int installCertificateInAndroidKeyStore(SemCertByte semCertByte, String str, char[] cArr, int i) throws RemoteException;

    int isAliasExists(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements ISemKeyStoreService {
        static final int TRANSACTION_getKeystoreStatus = 5;
        static final int TRANSACTION_grantAccessForAKS = 3;
        static final int TRANSACTION_installCACert = 4;
        static final int TRANSACTION_installCertificateInAndroidKeyStore = 2;
        static final int TRANSACTION_isAliasExists = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }

        public Stub() {
            attachInterface(this, ISemKeyStoreService.DESCRIPTOR);
        }

        public static ISemKeyStoreService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISemKeyStoreService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISemKeyStoreService)) {
                return (ISemKeyStoreService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "isAliasExists";
            }
            if (i == 2) {
                return "installCertificateInAndroidKeyStore";
            }
            if (i == 3) {
                return "grantAccessForAKS";
            }
            if (i == 4) {
                return "installCACert";
            }
            if (i != 5) {
                return null;
            }
            return "getKeystoreStatus";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISemKeyStoreService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISemKeyStoreService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String string = parcel.readString();
                parcel.enforceNoDataAvail();
                int iIsAliasExists = isAliasExists(string);
                parcel2.writeNoException();
                parcel2.writeInt(iIsAliasExists);
            } else if (i == 2) {
                SemCertByte semCertByte = (SemCertByte) parcel.readTypedObject(SemCertByte.CREATOR);
                String string2 = parcel.readString();
                char[] cArrCreateCharArray = parcel.createCharArray();
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                int iInstallCertificateInAndroidKeyStore = installCertificateInAndroidKeyStore(semCertByte, string2, cArrCreateCharArray, i3);
                parcel2.writeNoException();
                parcel2.writeInt(iInstallCertificateInAndroidKeyStore);
            } else if (i == 3) {
                int i4 = parcel.readInt();
                String string3 = parcel.readString();
                parcel.enforceNoDataAvail();
                grantAccessForAKS(i4, string3);
                parcel2.writeNoException();
            } else if (i == 4) {
                SemCertAndroidKeyStore semCertAndroidKeyStore = (SemCertAndroidKeyStore) parcel.readTypedObject(SemCertAndroidKeyStore.CREATOR);
                parcel.enforceNoDataAvail();
                int iInstallCACert = installCACert(semCertAndroidKeyStore);
                parcel2.writeNoException();
                parcel2.writeInt(iInstallCACert);
            } else if (i == 5) {
                int keystoreStatus = getKeystoreStatus();
                parcel2.writeNoException();
                parcel2.writeInt(keystoreStatus);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISemKeyStoreService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemKeyStoreService.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.util.ISemKeyStoreService
            public int isAliasExists(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemKeyStoreService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.util.ISemKeyStoreService
            public int installCertificateInAndroidKeyStore(SemCertByte semCertByte, String str, char[] cArr, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemKeyStoreService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(semCertByte, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeCharArray(cArr);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.util.ISemKeyStoreService
            public void grantAccessForAKS(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemKeyStoreService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.util.ISemKeyStoreService
            public int installCACert(SemCertAndroidKeyStore semCertAndroidKeyStore) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemKeyStoreService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(semCertAndroidKeyStore, 0);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.util.ISemKeyStoreService
            public int getKeystoreStatus() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemKeyStoreService.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
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
