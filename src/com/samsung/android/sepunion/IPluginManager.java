package com.samsung.android.sepunion;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.cover.CoverState;

/* loaded from: classes6.dex */
public interface IPluginManager extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.sepunion.IPluginManager";

    public static class Default implements IPluginManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.sepunion.IPluginManager
        public CoverState getCoverState() throws RemoteException {
            return null;
        }
    }

    CoverState getCoverState() throws RemoteException;

    public static abstract class Stub extends Binder implements IPluginManager {
        static final int TRANSACTION_getCoverState = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IPluginManager.DESCRIPTOR);
        }

        public static IPluginManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IPluginManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IPluginManager)) {
                return (IPluginManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "getCoverState";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IPluginManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IPluginManager.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                CoverState coverState = getCoverState();
                parcel2.writeNoException();
                parcel2.writeTypedObject(coverState, 1);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IPluginManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IPluginManager.DESCRIPTOR;
            }

            @Override // com.samsung.android.sepunion.IPluginManager
            public CoverState getCoverState() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPluginManager.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CoverState) obtain2.readTypedObject(CoverState.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
