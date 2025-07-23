package com.samsung.android.app;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

/* loaded from: classes6.dex */
public interface ISemDualAppManager extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.app.ISemDualAppManager";

    public static class Default implements ISemDualAppManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.app.ISemDualAppManager
        public List<String> getAllInstalledWhitelistedPackages() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.app.ISemDualAppManager
        public String[] getAllWhitelistedPackages() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.app.ISemDualAppManager
        public boolean isInstalledWhitelistedPackage(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.app.ISemDualAppManager
        public Bundle updateDualAppData(String str, int i, Bundle bundle) throws RemoteException {
            return null;
        }
    }

    List<String> getAllInstalledWhitelistedPackages() throws RemoteException;

    String[] getAllWhitelistedPackages() throws RemoteException;

    boolean isInstalledWhitelistedPackage(String str) throws RemoteException;

    Bundle updateDualAppData(String str, int i, Bundle bundle) throws RemoteException;

    public static abstract class Stub extends Binder implements ISemDualAppManager {
        static final int TRANSACTION_getAllInstalledWhitelistedPackages = 1;
        static final int TRANSACTION_getAllWhitelistedPackages = 3;
        static final int TRANSACTION_isInstalledWhitelistedPackage = 2;
        static final int TRANSACTION_updateDualAppData = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, ISemDualAppManager.DESCRIPTOR);
        }

        public static ISemDualAppManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISemDualAppManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISemDualAppManager)) {
                return (ISemDualAppManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "getAllInstalledWhitelistedPackages";
            }
            if (i == 2) {
                return "isInstalledWhitelistedPackage";
            }
            if (i == 3) {
                return "getAllWhitelistedPackages";
            }
            if (i != 4) {
                return null;
            }
            return "updateDualAppData";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISemDualAppManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISemDualAppManager.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                List<String> allInstalledWhitelistedPackages = getAllInstalledWhitelistedPackages();
                parcel2.writeNoException();
                parcel2.writeStringList(allInstalledWhitelistedPackages);
            } else if (i == 2) {
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                boolean isInstalledWhitelistedPackage = isInstalledWhitelistedPackage(readString);
                parcel2.writeNoException();
                parcel2.writeBoolean(isInstalledWhitelistedPackage);
            } else if (i == 3) {
                String[] allWhitelistedPackages = getAllWhitelistedPackages();
                parcel2.writeNoException();
                parcel2.writeStringArray(allWhitelistedPackages);
            } else if (i == 4) {
                String readString2 = parcel.readString();
                int readInt = parcel.readInt();
                Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                Bundle updateDualAppData = updateDualAppData(readString2, readInt, bundle);
                parcel2.writeNoException();
                parcel2.writeTypedObject(updateDualAppData, 1);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISemDualAppManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemDualAppManager.DESCRIPTOR;
            }

            @Override // com.samsung.android.app.ISemDualAppManager
            public List<String> getAllInstalledWhitelistedPackages() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemDualAppManager.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.app.ISemDualAppManager
            public boolean isInstalledWhitelistedPackage(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemDualAppManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.app.ISemDualAppManager
            public String[] getAllWhitelistedPackages() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemDualAppManager.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.app.ISemDualAppManager
            public Bundle updateDualAppData(String str, int i, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemDualAppManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
