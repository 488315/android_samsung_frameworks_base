package android.os;

/* loaded from: classes3.dex */
public interface IStatsBootstrapAtomService extends IInterface {
    public static final String DESCRIPTOR = "android.os.IStatsBootstrapAtomService";

    void reportBootstrapAtom(StatsBootstrapAtom statsBootstrapAtom) throws RemoteException;

    public static class Default implements IStatsBootstrapAtomService {
        @Override // android.os.IStatsBootstrapAtomService
        public void reportBootstrapAtom(StatsBootstrapAtom atom) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IStatsBootstrapAtomService {
        static final int TRANSACTION_reportBootstrapAtom = 1;

        public Stub() {
            attachInterface(this, IStatsBootstrapAtomService.DESCRIPTOR);
        }

        public static IStatsBootstrapAtomService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IStatsBootstrapAtomService.DESCRIPTOR);
            if (iin != null && (iin instanceof IStatsBootstrapAtomService)) {
                return (IStatsBootstrapAtomService) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "reportBootstrapAtom";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IStatsBootstrapAtomService.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IStatsBootstrapAtomService.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    StatsBootstrapAtom _arg0 = (StatsBootstrapAtom) data.readTypedObject(StatsBootstrapAtom.CREATOR);
                    data.enforceNoDataAvail();
                    reportBootstrapAtom(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IStatsBootstrapAtomService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IStatsBootstrapAtomService.DESCRIPTOR;
            }

            @Override // android.os.IStatsBootstrapAtomService
            public void reportBootstrapAtom(StatsBootstrapAtom atom) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IStatsBootstrapAtomService.DESCRIPTOR);
                    _data.writeTypedObject(atom, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
