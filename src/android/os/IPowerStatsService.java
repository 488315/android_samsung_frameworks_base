package android.os;

/* loaded from: classes3.dex */
public interface IPowerStatsService extends IInterface {
    public static final String DESCRIPTOR = "android.os.IPowerStatsService";
    public static final String KEY_ENERGY = "energy";
    public static final String KEY_GRANULARITY = "granularity";
    public static final String KEY_MONITORS = "monitors";
    public static final String KEY_TIMESTAMPS = "timestamps";
    public static final int RESULT_SUCCESS = 0;
    public static final int RESULT_UNSUPPORTED_POWER_MONITOR = 1;

    public static class Default implements IPowerStatsService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.IPowerStatsService
        public void getPowerMonitorReadings(int[] iArr, ResultReceiver resultReceiver) throws RemoteException {
        }

        @Override // android.os.IPowerStatsService
        public void getSupportedPowerMonitors(ResultReceiver resultReceiver) throws RemoteException {
        }
    }

    void getPowerMonitorReadings(int[] iArr, ResultReceiver resultReceiver) throws RemoteException;

    void getSupportedPowerMonitors(ResultReceiver resultReceiver) throws RemoteException;

    public static abstract class Stub extends Binder implements IPowerStatsService {
        static final int TRANSACTION_getPowerMonitorReadings = 2;
        static final int TRANSACTION_getSupportedPowerMonitors = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IPowerStatsService.DESCRIPTOR);
        }

        public static IPowerStatsService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IPowerStatsService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IPowerStatsService)) {
                return (IPowerStatsService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "getSupportedPowerMonitors";
            }
            if (i != 2) {
                return null;
            }
            return "getPowerMonitorReadings";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IPowerStatsService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IPowerStatsService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ResultReceiver resultReceiver = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                parcel.enforceNoDataAvail();
                getSupportedPowerMonitors(resultReceiver);
            } else if (i == 2) {
                int[] iArrCreateIntArray = parcel.createIntArray();
                ResultReceiver resultReceiver2 = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                parcel.enforceNoDataAvail();
                getPowerMonitorReadings(iArrCreateIntArray, resultReceiver2);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IPowerStatsService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IPowerStatsService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerStatsService
            public void getPowerMonitorReadings(int[] iArr, ResultReceiver resultReceiver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IPowerStatsService.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
