package android.os;

/* loaded from: classes3.dex */
public interface ISchedulingPolicyService extends IInterface {

    public static class Default implements ISchedulingPolicyService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.ISchedulingPolicyService
        public int requestCpusetBoost(boolean z, IBinder iBinder) throws RemoteException {
            return 0;
        }

        @Override // android.os.ISchedulingPolicyService
        public int requestPriority(int i, int i2, int i3, boolean z) throws RemoteException {
            return 0;
        }
    }

    int requestCpusetBoost(boolean z, IBinder iBinder) throws RemoteException;

    int requestPriority(int i, int i2, int i3, boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements ISchedulingPolicyService {
        public static final String DESCRIPTOR = "android.os.ISchedulingPolicyService";
        static final int TRANSACTION_requestCpusetBoost = 2;
        static final int TRANSACTION_requestPriority = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ISchedulingPolicyService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISchedulingPolicyService)) {
                return (ISchedulingPolicyService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "requestPriority";
            }
            if (i != 2) {
                return null;
            }
            return "requestCpusetBoost";
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
                int readInt2 = parcel.readInt();
                int readInt3 = parcel.readInt();
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                int requestPriority = requestPriority(readInt, readInt2, readInt3, readBoolean);
                parcel2.writeNoException();
                parcel2.writeInt(requestPriority);
            } else if (i == 2) {
                boolean readBoolean2 = parcel.readBoolean();
                IBinder readStrongBinder = parcel.readStrongBinder();
                parcel.enforceNoDataAvail();
                int requestCpusetBoost = requestCpusetBoost(readBoolean2, readStrongBinder);
                parcel2.writeNoException();
                parcel2.writeInt(requestCpusetBoost);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISchedulingPolicyService {
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

            @Override // android.os.ISchedulingPolicyService
            public int requestPriority(int i, int i2, int i3, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.ISchedulingPolicyService
            public int requestCpusetBoost(boolean z, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
