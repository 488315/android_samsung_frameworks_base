package com.android.internal.app;

import android.app.AsyncNotedAppOp;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface IAppOpsAsyncNotedCallback extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.app.IAppOpsAsyncNotedCallback";

    public static class Default implements IAppOpsAsyncNotedCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.app.IAppOpsAsyncNotedCallback
        public void opNoted(AsyncNotedAppOp asyncNotedAppOp) throws RemoteException {
        }
    }

    void opNoted(AsyncNotedAppOp asyncNotedAppOp) throws RemoteException;

    public static abstract class Stub extends Binder implements IAppOpsAsyncNotedCallback {
        static final int TRANSACTION_opNoted = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IAppOpsAsyncNotedCallback.DESCRIPTOR);
        }

        public static IAppOpsAsyncNotedCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IAppOpsAsyncNotedCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IAppOpsAsyncNotedCallback)) {
                return (IAppOpsAsyncNotedCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "opNoted";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IAppOpsAsyncNotedCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IAppOpsAsyncNotedCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                AsyncNotedAppOp asyncNotedAppOp = (AsyncNotedAppOp) parcel.readTypedObject(AsyncNotedAppOp.CREATOR);
                parcel.enforceNoDataAvail();
                opNoted(asyncNotedAppOp);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IAppOpsAsyncNotedCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAppOpsAsyncNotedCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.app.IAppOpsAsyncNotedCallback
            public void opNoted(AsyncNotedAppOp asyncNotedAppOp) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IAppOpsAsyncNotedCallback.DESCRIPTOR);
                    obtain.writeTypedObject(asyncNotedAppOp, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
