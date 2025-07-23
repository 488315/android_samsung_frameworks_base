package android.service.attention;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.service.attention.IAttentionCallback;
import android.service.attention.IProximityUpdateCallback;

/* loaded from: classes3.dex */
public interface IAttentionService extends IInterface {
    public static final String DESCRIPTOR = "android.service.attention.IAttentionService";

    public static class Default implements IAttentionService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.attention.IAttentionService
        public void cancelAttentionCheck(IAttentionCallback iAttentionCallback) throws RemoteException {
        }

        @Override // android.service.attention.IAttentionService
        public void checkAttention(IAttentionCallback iAttentionCallback) throws RemoteException {
        }

        @Override // android.service.attention.IAttentionService
        public void onStartProximityUpdates(IProximityUpdateCallback iProximityUpdateCallback) throws RemoteException {
        }

        @Override // android.service.attention.IAttentionService
        public void onStopProximityUpdates() throws RemoteException {
        }
    }

    void cancelAttentionCheck(IAttentionCallback iAttentionCallback) throws RemoteException;

    void checkAttention(IAttentionCallback iAttentionCallback) throws RemoteException;

    void onStartProximityUpdates(IProximityUpdateCallback iProximityUpdateCallback) throws RemoteException;

    void onStopProximityUpdates() throws RemoteException;

    public static abstract class Stub extends Binder implements IAttentionService {
        static final int TRANSACTION_cancelAttentionCheck = 2;
        static final int TRANSACTION_checkAttention = 1;
        static final int TRANSACTION_onStartProximityUpdates = 3;
        static final int TRANSACTION_onStopProximityUpdates = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, IAttentionService.DESCRIPTOR);
        }

        public static IAttentionService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IAttentionService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IAttentionService)) {
                return (IAttentionService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "checkAttention";
            }
            if (i == 2) {
                return "cancelAttentionCheck";
            }
            if (i == 3) {
                return "onStartProximityUpdates";
            }
            if (i != 4) {
                return null;
            }
            return "onStopProximityUpdates";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IAttentionService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IAttentionService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                IAttentionCallback asInterface = IAttentionCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                checkAttention(asInterface);
            } else if (i == 2) {
                IAttentionCallback asInterface2 = IAttentionCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                cancelAttentionCheck(asInterface2);
            } else if (i == 3) {
                IProximityUpdateCallback asInterface3 = IProximityUpdateCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                onStartProximityUpdates(asInterface3);
            } else if (i == 4) {
                onStopProximityUpdates();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IAttentionService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAttentionService.DESCRIPTOR;
            }

            @Override // android.service.attention.IAttentionService
            public void checkAttention(IAttentionCallback iAttentionCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IAttentionService.DESCRIPTOR);
                    obtain.writeStrongInterface(iAttentionCallback);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.attention.IAttentionService
            public void cancelAttentionCheck(IAttentionCallback iAttentionCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IAttentionService.DESCRIPTOR);
                    obtain.writeStrongInterface(iAttentionCallback);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.attention.IAttentionService
            public void onStartProximityUpdates(IProximityUpdateCallback iProximityUpdateCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IAttentionService.DESCRIPTOR);
                    obtain.writeStrongInterface(iProximityUpdateCallback);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.attention.IAttentionService
            public void onStopProximityUpdates() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IAttentionService.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
