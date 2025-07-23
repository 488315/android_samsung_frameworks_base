package android.hardware.location;

import android.location.Location;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IGeofenceHardwareCallback extends IInterface {

    public static class Default implements IGeofenceHardwareCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.location.IGeofenceHardwareCallback
        public void onGeofenceAdd(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.location.IGeofenceHardwareCallback
        public void onGeofencePause(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.location.IGeofenceHardwareCallback
        public void onGeofenceRemove(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.location.IGeofenceHardwareCallback
        public void onGeofenceResume(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.location.IGeofenceHardwareCallback
        public void onGeofenceTransition(int i, int i2, Location location, long j, int i3) throws RemoteException {
        }
    }

    void onGeofenceAdd(int i, int i2) throws RemoteException;

    void onGeofencePause(int i, int i2) throws RemoteException;

    void onGeofenceRemove(int i, int i2) throws RemoteException;

    void onGeofenceResume(int i, int i2) throws RemoteException;

    void onGeofenceTransition(int i, int i2, Location location, long j, int i3) throws RemoteException;

    public static abstract class Stub extends Binder implements IGeofenceHardwareCallback {
        public static final String DESCRIPTOR = "android.hardware.location.IGeofenceHardwareCallback";
        static final int TRANSACTION_onGeofenceAdd = 2;
        static final int TRANSACTION_onGeofencePause = 4;
        static final int TRANSACTION_onGeofenceRemove = 3;
        static final int TRANSACTION_onGeofenceResume = 5;
        static final int TRANSACTION_onGeofenceTransition = 1;

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

        public static IGeofenceHardwareCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IGeofenceHardwareCallback)) {
                return (IGeofenceHardwareCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onGeofenceTransition";
            }
            if (i == 2) {
                return "onGeofenceAdd";
            }
            if (i == 3) {
                return "onGeofenceRemove";
            }
            if (i == 4) {
                return "onGeofencePause";
            }
            if (i != 5) {
                return null;
            }
            return "onGeofenceResume";
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
                Location location = (Location) parcel.readTypedObject(Location.CREATOR);
                long readLong = parcel.readLong();
                int readInt3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onGeofenceTransition(readInt, readInt2, location, readLong, readInt3);
            } else if (i == 2) {
                int readInt4 = parcel.readInt();
                int readInt5 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onGeofenceAdd(readInt4, readInt5);
            } else if (i == 3) {
                int readInt6 = parcel.readInt();
                int readInt7 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onGeofenceRemove(readInt6, readInt7);
            } else if (i == 4) {
                int readInt8 = parcel.readInt();
                int readInt9 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onGeofencePause(readInt8, readInt9);
            } else if (i == 5) {
                int readInt10 = parcel.readInt();
                int readInt11 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onGeofenceResume(readInt10, readInt11);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IGeofenceHardwareCallback {
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

            @Override // android.hardware.location.IGeofenceHardwareCallback
            public void onGeofenceTransition(int i, int i2, Location location, long j, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(location, 0);
                    obtain.writeLong(j);
                    obtain.writeInt(i3);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.location.IGeofenceHardwareCallback
            public void onGeofenceAdd(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.location.IGeofenceHardwareCallback
            public void onGeofenceRemove(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.location.IGeofenceHardwareCallback
            public void onGeofencePause(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.location.IGeofenceHardwareCallback
            public void onGeofenceResume(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
