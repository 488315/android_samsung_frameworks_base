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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IBatteryPropertiesRegistrar)) {
                return (IBatteryPropertiesRegistrar) iInterfaceQueryLocalInterface;
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
                int i3 = parcel.readInt();
                BatteryProperty batteryProperty = new BatteryProperty();
                parcel.enforceNoDataAvail();
                int property = getProperty(i3, batteryProperty);
                parcel2.writeNoException();
                parcel2.writeInt(property);
                parcel2.writeTypedObject(batteryProperty, 1);
            } else if (i == 2) {
                scheduleUpdate();
            } else if (i == 3) {
                int i4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                long[] jArrSemGetValuesAsLong = semGetValuesAsLong(i4);
                parcel2.writeNoException();
                parcel2.writeLongArray(jArrSemGetValuesAsLong);
            } else if (i == 4) {
                int i5 = parcel.readInt();
                parcel.enforceNoDataAvail();
                String[] strArrSemGetValuesAsString = semGetValuesAsString(i5);
                parcel2.writeNoException();
                parcel2.writeStringArray(strArrSemGetValuesAsString);
            } else if (i == 5) {
                int i6 = parcel.readInt();
                parcel.enforceNoDataAvail();
                boolean zSemGetValueAsBoolean = semGetValueAsBoolean(i6);
                parcel2.writeNoException();
                parcel2.writeBoolean(zSemGetValueAsBoolean);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    int i2 = parcelObtain2.readInt();
                    if (parcelObtain2.readInt() != 0) {
                        batteryProperty.readFromParcel(parcelObtain2);
                    }
                    return i2;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IBatteryPropertiesRegistrar
            public void scheduleUpdate() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IBatteryPropertiesRegistrar
            public long[] semGetValuesAsLong(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createLongArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IBatteryPropertiesRegistrar
            public String[] semGetValuesAsString(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IBatteryPropertiesRegistrar
            public boolean semGetValueAsBoolean(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
