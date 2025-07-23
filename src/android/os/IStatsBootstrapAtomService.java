package android.os;

/* loaded from: classes3.dex */
public interface IStatsBootstrapAtomService extends IInterface {
    public static final String DESCRIPTOR = "android.os.IStatsBootstrapAtomService";

    public static class Default implements IStatsBootstrapAtomService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.IStatsBootstrapAtomService
        public void reportBootstrapAtom(StatsBootstrapAtom statsBootstrapAtom) throws RemoteException {
        }
    }

    void reportBootstrapAtom(StatsBootstrapAtom statsBootstrapAtom) throws RemoteException;

    public static abstract class Stub extends Binder implements IStatsBootstrapAtomService {
        static final int TRANSACTION_reportBootstrapAtom = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IStatsBootstrapAtomService.DESCRIPTOR);
        }

        public static IStatsBootstrapAtomService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IStatsBootstrapAtomService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IStatsBootstrapAtomService)) {
                return (IStatsBootstrapAtomService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "reportBootstrapAtom";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IStatsBootstrapAtomService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IStatsBootstrapAtomService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                StatsBootstrapAtom statsBootstrapAtom = (StatsBootstrapAtom) parcel.readTypedObject(StatsBootstrapAtom.CREATOR);
                parcel.enforceNoDataAvail();
                reportBootstrapAtom(statsBootstrapAtom);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IStatsBootstrapAtomService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IStatsBootstrapAtomService.DESCRIPTOR;
            }

            @Override // android.os.IStatsBootstrapAtomService
            public void reportBootstrapAtom(StatsBootstrapAtom statsBootstrapAtom) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IStatsBootstrapAtomService.DESCRIPTOR);
                    obtain.writeTypedObject(statsBootstrapAtom, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
