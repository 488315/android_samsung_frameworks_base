package android.os;

/* loaded from: classes3.dex */
public interface IHardwarePropertiesManager extends IInterface {

    public static class Default implements IHardwarePropertiesManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.IHardwarePropertiesManager
        public CpuUsageInfo[] getCpuUsages(String str) throws RemoteException {
            return null;
        }

        @Override // android.os.IHardwarePropertiesManager
        public float[] getDeviceTemperatures(String str, int i, int i2) throws RemoteException {
            return null;
        }

        @Override // android.os.IHardwarePropertiesManager
        public float[] getFanSpeeds(String str) throws RemoteException {
            return null;
        }
    }

    CpuUsageInfo[] getCpuUsages(String str) throws RemoteException;

    float[] getDeviceTemperatures(String str, int i, int i2) throws RemoteException;

    float[] getFanSpeeds(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IHardwarePropertiesManager {
        public static final String DESCRIPTOR = "android.os.IHardwarePropertiesManager";
        static final int TRANSACTION_getCpuUsages = 2;
        static final int TRANSACTION_getDeviceTemperatures = 1;
        static final int TRANSACTION_getFanSpeeds = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IHardwarePropertiesManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IHardwarePropertiesManager)) {
                return (IHardwarePropertiesManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "getDeviceTemperatures";
            }
            if (i == 2) {
                return "getCpuUsages";
            }
            if (i != 3) {
                return null;
            }
            return "getFanSpeeds";
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
                String readString = parcel.readString();
                int readInt = parcel.readInt();
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                float[] deviceTemperatures = getDeviceTemperatures(readString, readInt, readInt2);
                parcel2.writeNoException();
                parcel2.writeFloatArray(deviceTemperatures);
            } else if (i == 2) {
                String readString2 = parcel.readString();
                parcel.enforceNoDataAvail();
                CpuUsageInfo[] cpuUsages = getCpuUsages(readString2);
                parcel2.writeNoException();
                parcel2.writeTypedArray(cpuUsages, 1);
            } else if (i == 3) {
                String readString3 = parcel.readString();
                parcel.enforceNoDataAvail();
                float[] fanSpeeds = getFanSpeeds(readString3);
                parcel2.writeNoException();
                parcel2.writeFloatArray(fanSpeeds);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IHardwarePropertiesManager {
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

            @Override // android.os.IHardwarePropertiesManager
            public float[] getDeviceTemperatures(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createFloatArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IHardwarePropertiesManager
            public CpuUsageInfo[] getCpuUsages(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CpuUsageInfo[]) obtain2.createTypedArray(CpuUsageInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IHardwarePropertiesManager
            public float[] getFanSpeeds(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createFloatArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
