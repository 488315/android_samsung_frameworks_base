package android.os;

/* loaded from: classes3.dex */
public interface IPowerStatsService extends IInterface {
    public static final String DESCRIPTOR = "android.os.IPowerStatsService";
    public static final String KEY_ENERGY = "energy";
    public static final String KEY_MONITORS = "monitors";
    public static final String KEY_TIMESTAMPS = "timestamps";
    public static final int RESULT_SUCCESS = 0;
    public static final int RESULT_UNSUPPORTED_POWER_MONITOR = 1;

    void getPowerMonitorReadings(int[] iArr, ResultReceiver resultReceiver) throws RemoteException;

    void getSupportedPowerMonitors(ResultReceiver resultReceiver) throws RemoteException;

    public static class Default implements IPowerStatsService {
        @Override // android.os.IPowerStatsService
        public void getSupportedPowerMonitors(ResultReceiver resultReceiver) throws RemoteException {
        }

        @Override // android.os.IPowerStatsService
        public void getPowerMonitorReadings(int[] powerMonitorIndices, ResultReceiver resultReceiver) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IPowerStatsService {
        static final int TRANSACTION_getPowerMonitorReadings = 2;
        static final int TRANSACTION_getSupportedPowerMonitors = 1;

        public Stub() {
            attachInterface(this, IPowerStatsService.DESCRIPTOR);
        }

        public static IPowerStatsService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IPowerStatsService.DESCRIPTOR);
            if (iin != null && (iin instanceof IPowerStatsService)) {
                return (IPowerStatsService) iin;
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
                    return "getSupportedPowerMonitors";
                case 2:
                    return "getPowerMonitorReadings";
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
                data.enforceInterface(IPowerStatsService.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IPowerStatsService.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    ResultReceiver _arg0 = (ResultReceiver) data.readTypedObject(ResultReceiver.CREATOR);
                    data.enforceNoDataAvail();
                    getSupportedPowerMonitors(_arg0);
                    return true;
                case 2:
                    int[] _arg02 = data.createIntArray();
                    ResultReceiver _arg1 = (ResultReceiver) data.readTypedObject(ResultReceiver.CREATOR);
                    data.enforceNoDataAvail();
                    getPowerMonitorReadings(_arg02, _arg1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IPowerStatsService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IPowerStatsService.DESCRIPTOR;
            }

            @Override // android.os.IPowerStatsService
            public void getSupportedPowerMonitors(ResultReceiver resultReceiver) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IPowerStatsService.DESCRIPTOR);
                    _data.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerStatsService
            public void getPowerMonitorReadings(int[] powerMonitorIndices, ResultReceiver resultReceiver) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IPowerStatsService.DESCRIPTOR);
                    _data.writeIntArray(powerMonitorIndices);
                    _data.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
