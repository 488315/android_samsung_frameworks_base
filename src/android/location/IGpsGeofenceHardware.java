package android.location;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IGpsGeofenceHardware extends IInterface {

    public static class Default implements IGpsGeofenceHardware {
        @Override // android.location.IGpsGeofenceHardware
        public boolean addCircularHardwareGeofence(int i, double d, double d2, double d3, int i2, int i3, int i4, int i5) throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.location.IGpsGeofenceHardware
        public boolean isHardwareGeofenceSupported() throws RemoteException {
            return false;
        }

        @Override // android.location.IGpsGeofenceHardware
        public boolean pauseHardwareGeofence(int i) throws RemoteException {
            return false;
        }

        @Override // android.location.IGpsGeofenceHardware
        public boolean removeHardwareGeofence(int i) throws RemoteException {
            return false;
        }

        @Override // android.location.IGpsGeofenceHardware
        public boolean resumeHardwareGeofence(int i, int i2) throws RemoteException {
            return false;
        }
    }

    boolean addCircularHardwareGeofence(int i, double d, double d2, double d3, int i2, int i3, int i4, int i5) throws RemoteException;

    boolean isHardwareGeofenceSupported() throws RemoteException;

    boolean pauseHardwareGeofence(int i) throws RemoteException;

    boolean removeHardwareGeofence(int i) throws RemoteException;

    boolean resumeHardwareGeofence(int i, int i2) throws RemoteException;

    public static abstract class Stub extends Binder implements IGpsGeofenceHardware {
        public static final String DESCRIPTOR = "android.location.IGpsGeofenceHardware";
        static final int TRANSACTION_addCircularHardwareGeofence = 2;
        static final int TRANSACTION_isHardwareGeofenceSupported = 1;
        static final int TRANSACTION_pauseHardwareGeofence = 4;
        static final int TRANSACTION_removeHardwareGeofence = 3;
        static final int TRANSACTION_resumeHardwareGeofence = 5;

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

        public static IGpsGeofenceHardware asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IGpsGeofenceHardware)) {
                return (IGpsGeofenceHardware) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "isHardwareGeofenceSupported";
            }
            if (i == 2) {
                return "addCircularHardwareGeofence";
            }
            if (i == 3) {
                return "removeHardwareGeofence";
            }
            if (i == 4) {
                return "pauseHardwareGeofence";
            }
            if (i != 5) {
                return null;
            }
            return "resumeHardwareGeofence";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            Parcel parcel3;
            if (i < 1 || i > 16777215) {
                parcel3 = parcel;
            } else {
                parcel3 = parcel;
                parcel3.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                boolean isHardwareGeofenceSupported = isHardwareGeofenceSupported();
                parcel2.writeNoException();
                parcel2.writeBoolean(isHardwareGeofenceSupported);
            } else if (i == 2) {
                int readInt = parcel3.readInt();
                double readDouble = parcel.readDouble();
                double readDouble2 = parcel.readDouble();
                double readDouble3 = parcel.readDouble();
                int readInt2 = parcel.readInt();
                int readInt3 = parcel.readInt();
                int readInt4 = parcel.readInt();
                int readInt5 = parcel.readInt();
                parcel.enforceNoDataAvail();
                boolean addCircularHardwareGeofence = addCircularHardwareGeofence(readInt, readDouble, readDouble2, readDouble3, readInt2, readInt3, readInt4, readInt5);
                parcel2.writeNoException();
                parcel2.writeBoolean(addCircularHardwareGeofence);
            } else if (i == 3) {
                int readInt6 = parcel3.readInt();
                parcel3.enforceNoDataAvail();
                boolean removeHardwareGeofence = removeHardwareGeofence(readInt6);
                parcel2.writeNoException();
                parcel2.writeBoolean(removeHardwareGeofence);
            } else if (i == 4) {
                int readInt7 = parcel3.readInt();
                parcel3.enforceNoDataAvail();
                boolean pauseHardwareGeofence = pauseHardwareGeofence(readInt7);
                parcel2.writeNoException();
                parcel2.writeBoolean(pauseHardwareGeofence);
            } else if (i == 5) {
                int readInt8 = parcel3.readInt();
                int readInt9 = parcel3.readInt();
                parcel3.enforceNoDataAvail();
                boolean resumeHardwareGeofence = resumeHardwareGeofence(readInt8, readInt9);
                parcel2.writeNoException();
                parcel2.writeBoolean(resumeHardwareGeofence);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IGpsGeofenceHardware {
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

            @Override // android.location.IGpsGeofenceHardware
            public boolean isHardwareGeofenceSupported() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.location.IGpsGeofenceHardware
            public boolean addCircularHardwareGeofence(int i, double d, double d2, double d3, int i2, int i3, int i4, int i5) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeDouble(d);
                    obtain.writeDouble(d2);
                    obtain.writeDouble(d3);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.location.IGpsGeofenceHardware
            public boolean removeHardwareGeofence(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.location.IGpsGeofenceHardware
            public boolean pauseHardwareGeofence(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.location.IGpsGeofenceHardware
            public boolean resumeHardwareGeofence(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
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
