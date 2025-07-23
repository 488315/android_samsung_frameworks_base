package com.samsung.android.sepunion;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IUnionManager extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.sepunion.IUnionManager";

    public static class Default implements IUnionManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.sepunion.IUnionManager
        public IBinder getSemSystemService(String str, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.sepunion.IUnionManager
        public void setDumpEnabled(String str, String str2) throws RemoteException {
        }
    }

    IBinder getSemSystemService(String str, Bundle bundle) throws RemoteException;

    void setDumpEnabled(String str, String str2) throws RemoteException;

    public static abstract class Stub extends Binder implements IUnionManager {
        static final int TRANSACTION_getSemSystemService = 1;
        static final int TRANSACTION_setDumpEnabled = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IUnionManager.DESCRIPTOR);
        }

        public static IUnionManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IUnionManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IUnionManager)) {
                return (IUnionManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "getSemSystemService";
            }
            if (i != 2) {
                return null;
            }
            return "setDumpEnabled";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IUnionManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IUnionManager.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String readString = parcel.readString();
                Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                IBinder semSystemService = getSemSystemService(readString, bundle);
                parcel2.writeNoException();
                parcel2.writeStrongBinder(semSystemService);
            } else if (i == 2) {
                String readString2 = parcel.readString();
                String readString3 = parcel.readString();
                parcel.enforceNoDataAvail();
                setDumpEnabled(readString2, readString3);
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IUnionManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IUnionManager.DESCRIPTOR;
            }

            @Override // com.samsung.android.sepunion.IUnionManager
            public IBinder getSemSystemService(String str, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUnionManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readStrongBinder();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sepunion.IUnionManager
            public void setDumpEnabled(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUnionManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
