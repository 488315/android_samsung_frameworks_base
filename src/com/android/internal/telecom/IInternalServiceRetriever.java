package com.android.internal.telecom;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.internal.telecom.IDeviceIdleControllerAdapter;

/* loaded from: classes4.dex */
public interface IInternalServiceRetriever extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.telecom.IInternalServiceRetriever";

    public static class Default implements IInternalServiceRetriever {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.telecom.IInternalServiceRetriever
        public IDeviceIdleControllerAdapter getDeviceIdleController() throws RemoteException {
            return null;
        }
    }

    IDeviceIdleControllerAdapter getDeviceIdleController() throws RemoteException;

    public static abstract class Stub extends Binder implements IInternalServiceRetriever {
        static final int TRANSACTION_getDeviceIdleController = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IInternalServiceRetriever.DESCRIPTOR);
        }

        public static IInternalServiceRetriever asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IInternalServiceRetriever.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IInternalServiceRetriever)) {
                return (IInternalServiceRetriever) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "getDeviceIdleController";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IInternalServiceRetriever.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IInternalServiceRetriever.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                IDeviceIdleControllerAdapter deviceIdleController = getDeviceIdleController();
                parcel2.writeNoException();
                parcel2.writeStrongInterface(deviceIdleController);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IInternalServiceRetriever {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IInternalServiceRetriever.DESCRIPTOR;
            }

            @Override // com.android.internal.telecom.IInternalServiceRetriever
            public IDeviceIdleControllerAdapter getDeviceIdleController() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IInternalServiceRetriever.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return IDeviceIdleControllerAdapter.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
