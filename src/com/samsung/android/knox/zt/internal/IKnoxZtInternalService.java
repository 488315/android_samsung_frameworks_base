package com.samsung.android.knox.zt.internal;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IKnoxZtInternalService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.zt.internal.IKnoxZtInternalService";

    public static class Default implements IKnoxZtInternalService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.zt.internal.IKnoxZtInternalService
        public void notifyFrameworkEvent(int i, int i2, Bundle bundle) throws RemoteException {
        }
    }

    void notifyFrameworkEvent(int i, int i2, Bundle bundle) throws RemoteException;

    public static abstract class Stub extends Binder implements IKnoxZtInternalService {
        static final int TRANSACTION_notifyFrameworkEvent = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IKnoxZtInternalService.DESCRIPTOR);
        }

        public static IKnoxZtInternalService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IKnoxZtInternalService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IKnoxZtInternalService)) {
                return (IKnoxZtInternalService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "notifyFrameworkEvent";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IKnoxZtInternalService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IKnoxZtInternalService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                int readInt2 = parcel.readInt();
                Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                notifyFrameworkEvent(readInt, readInt2, bundle);
                parcel2.writeNoException();
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IKnoxZtInternalService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IKnoxZtInternalService.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.zt.internal.IKnoxZtInternalService
            public void notifyFrameworkEvent(int i, int i2, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxZtInternalService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
