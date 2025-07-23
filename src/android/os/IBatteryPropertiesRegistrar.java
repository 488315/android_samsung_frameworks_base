package android.os;

/* loaded from: classes3.dex */
public interface IBatteryPropertiesRegistrar extends IInterface {

    public static class Default implements IBatteryPropertiesRegistrar {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.IBatteryPropertiesRegistrar
        public int getProperty(int i, BatteryProperty batteryProperty) throws RemoteException {
            return 0;
        }

        @Override // android.os.IBatteryPropertiesRegistrar
        public void scheduleUpdate() throws RemoteException {
        }

        @Override // android.os.IBatteryPropertiesRegistrar
        public boolean semGetValueAsBoolean(int i) throws RemoteException {
            return false;
        }

        @Override // android.os.IBatteryPropertiesRegistrar
        public long[] semGetValuesAsLong(int i) throws RemoteException {
            return null;
        }

        @Override // android.os.IBatteryPropertiesRegistrar
        public String[] semGetValuesAsString(int i) throws RemoteException {
            return null;
        }
    }

    int getProperty(int i, BatteryProperty batteryProperty) throws RemoteException;

    void scheduleUpdate() throws RemoteException;

    boolean semGetValueAsBoolean(int i) throws RemoteException;

    long[] semGetValuesAsLong(int i) throws RemoteException;

    String[] semGetValuesAsString(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IBatteryPropertiesRegistrar {
        public static final String DESCRIPTOR = "android.os.IBatteryPropertiesRegistrar";
        static final int TRANSACTION_getProperty = 1;
        static final int TRANSACTION_scheduleUpdate = 2;
        static final int TRANSACTION_semGetValueAsBoolean = 5;
        static final int TRANSACTION_semGetValuesAsLong = 3;
        static final int TRANSACTION_semGetValuesAsString = 4;

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

        public static IBatteryPropertiesRegistrar asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IBatteryPropertiesRegistrar)) {
                return (IBatteryPropertiesRegistrar) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "getProperty";
            }
            if (i == 2) {
                return "scheduleUpdate";
            }
            if (i == 3) {
                return "semGetValuesAsLong";
            }
            if (i == 4) {
                return "semGetValuesAsString";
            }
            if (i != 5) {
                return null;
            }
            return "semGetValueAsBoolean";
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
                BatteryProperty batteryProperty = new BatteryProperty();
                parcel.enforceNoDataAvail();
                int property = getProperty(readInt, batteryProperty);
                parcel2.writeNoException();
                parcel2.writeInt(property);
                parcel2.writeTypedObject(batteryProperty, 1);
            } else if (i == 2) {
                scheduleUpdate();
            } else if (i == 3) {
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                long[] semGetValuesAsLong = semGetValuesAsLong(readInt2);
                parcel2.writeNoException();
                parcel2.writeLongArray(semGetValuesAsLong);
            } else if (i == 4) {
                int readInt3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                String[] semGetValuesAsString = semGetValuesAsString(readInt3);
                parcel2.writeNoException();
                parcel2.writeStringArray(semGetValuesAsString);
            } else if (i == 5) {
                int readInt4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                boolean semGetValueAsBoolean = semGetValueAsBoolean(readInt4);
                parcel2.writeNoException();
                parcel2.writeBoolean(semGetValueAsBoolean);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IBatteryPropertiesRegistrar {
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

            @Override // android.os.IBatteryPropertiesRegistrar
            public int getProperty(int i, BatteryProperty batteryProperty) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    if (obtain2.readInt() != 0) {
                        batteryProperty.readFromParcel(obtain2);
                    }
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IBatteryPropertiesRegistrar
            public void scheduleUpdate() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.IBatteryPropertiesRegistrar
            public long[] semGetValuesAsLong(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createLongArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IBatteryPropertiesRegistrar
            public String[] semGetValuesAsString(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IBatteryPropertiesRegistrar
            public boolean semGetValueAsBoolean(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
