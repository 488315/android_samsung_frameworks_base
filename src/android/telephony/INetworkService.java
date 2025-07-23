package android.telephony;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.INetworkServiceCallback;

/* loaded from: classes4.dex */
public interface INetworkService extends IInterface {

    public static class Default implements INetworkService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.telephony.INetworkService
        public void createNetworkServiceProvider(int i) throws RemoteException {
        }

        @Override // android.telephony.INetworkService
        public void registerForNetworkRegistrationInfoChanged(int i, INetworkServiceCallback iNetworkServiceCallback) throws RemoteException {
        }

        @Override // android.telephony.INetworkService
        public void removeNetworkServiceProvider(int i) throws RemoteException {
        }

        @Override // android.telephony.INetworkService
        public void requestNetworkRegistrationInfo(int i, int i2, INetworkServiceCallback iNetworkServiceCallback) throws RemoteException {
        }

        @Override // android.telephony.INetworkService
        public void unregisterForNetworkRegistrationInfoChanged(int i, INetworkServiceCallback iNetworkServiceCallback) throws RemoteException {
        }
    }

    void createNetworkServiceProvider(int i) throws RemoteException;

    void registerForNetworkRegistrationInfoChanged(int i, INetworkServiceCallback iNetworkServiceCallback) throws RemoteException;

    void removeNetworkServiceProvider(int i) throws RemoteException;

    void requestNetworkRegistrationInfo(int i, int i2, INetworkServiceCallback iNetworkServiceCallback) throws RemoteException;

    void unregisterForNetworkRegistrationInfoChanged(int i, INetworkServiceCallback iNetworkServiceCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements INetworkService {
        public static final String DESCRIPTOR = "android.telephony.INetworkService";
        static final int TRANSACTION_createNetworkServiceProvider = 1;
        static final int TRANSACTION_registerForNetworkRegistrationInfoChanged = 4;
        static final int TRANSACTION_removeNetworkServiceProvider = 2;
        static final int TRANSACTION_requestNetworkRegistrationInfo = 3;
        static final int TRANSACTION_unregisterForNetworkRegistrationInfoChanged = 5;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static INetworkService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof INetworkService)) {
                return (INetworkService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "createNetworkServiceProvider";
            }
            if (i == 2) {
                return "removeNetworkServiceProvider";
            }
            if (i == 3) {
                return "requestNetworkRegistrationInfo";
            }
            if (i == 4) {
                return "registerForNetworkRegistrationInfoChanged";
            }
            if (i != 5) {
                return null;
            }
            return "unregisterForNetworkRegistrationInfoChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                createNetworkServiceProvider(readInt);
            } else if (i == 2) {
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                removeNetworkServiceProvider(readInt2);
            } else if (i == 3) {
                int readInt3 = parcel.readInt();
                int readInt4 = parcel.readInt();
                INetworkServiceCallback asInterface = INetworkServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                requestNetworkRegistrationInfo(readInt3, readInt4, asInterface);
            } else if (i == 4) {
                int readInt5 = parcel.readInt();
                INetworkServiceCallback asInterface2 = INetworkServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                registerForNetworkRegistrationInfoChanged(readInt5, asInterface2);
            } else if (i == 5) {
                int readInt6 = parcel.readInt();
                INetworkServiceCallback asInterface3 = INetworkServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                unregisterForNetworkRegistrationInfoChanged(readInt6, asInterface3);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements INetworkService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.telephony.INetworkService
            public void createNetworkServiceProvider(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.INetworkService
            public void removeNetworkServiceProvider(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.INetworkService
            public void requestNetworkRegistrationInfo(int i, int i2, INetworkServiceCallback iNetworkServiceCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iNetworkServiceCallback);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.INetworkService
            public void registerForNetworkRegistrationInfoChanged(int i, INetworkServiceCallback iNetworkServiceCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iNetworkServiceCallback);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.INetworkService
            public void unregisterForNetworkRegistrationInfoChanged(int i, INetworkServiceCallback iNetworkServiceCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iNetworkServiceCallback);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
