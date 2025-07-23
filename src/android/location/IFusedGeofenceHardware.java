package android.location;

import android.hardware.location.GeofenceHardwareRequestParcelable;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IFusedGeofenceHardware extends IInterface {

    public static class Default implements IFusedGeofenceHardware {
        @Override // android.location.IFusedGeofenceHardware
        public void addGeofences(GeofenceHardwareRequestParcelable[] geofenceHardwareRequestParcelableArr) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.location.IFusedGeofenceHardware
        public boolean isSupported() throws RemoteException {
            return false;
        }

        @Override // android.location.IFusedGeofenceHardware
        public void modifyGeofenceOptions(int i, int i2, int i3, int i4, int i5, int i6) throws RemoteException {
        }

        @Override // android.location.IFusedGeofenceHardware
        public void pauseMonitoringGeofence(int i) throws RemoteException {
        }

        @Override // android.location.IFusedGeofenceHardware
        public void removeGeofences(int[] iArr) throws RemoteException {
        }

        @Override // android.location.IFusedGeofenceHardware
        public void resumeMonitoringGeofence(int i, int i2) throws RemoteException {
        }
    }

    void addGeofences(GeofenceHardwareRequestParcelable[] geofenceHardwareRequestParcelableArr) throws RemoteException;

    boolean isSupported() throws RemoteException;

    void modifyGeofenceOptions(int i, int i2, int i3, int i4, int i5, int i6) throws RemoteException;

    void pauseMonitoringGeofence(int i) throws RemoteException;

    void removeGeofences(int[] iArr) throws RemoteException;

    void resumeMonitoringGeofence(int i, int i2) throws RemoteException;

    public static abstract class Stub extends Binder implements IFusedGeofenceHardware {
        public static final String DESCRIPTOR = "android.location.IFusedGeofenceHardware";
        static final int TRANSACTION_addGeofences = 2;
        static final int TRANSACTION_isSupported = 1;
        static final int TRANSACTION_modifyGeofenceOptions = 6;
        static final int TRANSACTION_pauseMonitoringGeofence = 4;
        static final int TRANSACTION_removeGeofences = 3;
        static final int TRANSACTION_resumeMonitoringGeofence = 5;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IFusedGeofenceHardware asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IFusedGeofenceHardware)) {
                return (IFusedGeofenceHardware) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "isSupported";
                case 2:
                    return "addGeofences";
                case 3:
                    return "removeGeofences";
                case 4:
                    return "pauseMonitoringGeofence";
                case 5:
                    return "resumeMonitoringGeofence";
                case 6:
                    return "modifyGeofenceOptions";
                default:
                    return null;
            }
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
            switch (i) {
                case 1:
                    boolean isSupported = isSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSupported);
                    return true;
                case 2:
                    GeofenceHardwareRequestParcelable[] geofenceHardwareRequestParcelableArr = (GeofenceHardwareRequestParcelable[]) parcel.createTypedArray(GeofenceHardwareRequestParcelable.CREATOR);
                    parcel.enforceNoDataAvail();
                    addGeofences(geofenceHardwareRequestParcelableArr);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    removeGeofences(createIntArray);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    pauseMonitoringGeofence(readInt);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    resumeMonitoringGeofence(readInt2, readInt3);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    modifyGeofenceOptions(readInt4, readInt5, readInt6, readInt7, readInt8, readInt9);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IFusedGeofenceHardware {
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

            @Override // android.location.IFusedGeofenceHardware
            public boolean isSupported() throws RemoteException {
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

            @Override // android.location.IFusedGeofenceHardware
            public void addGeofences(GeofenceHardwareRequestParcelable[] geofenceHardwareRequestParcelableArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedArray(geofenceHardwareRequestParcelableArr, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.location.IFusedGeofenceHardware
            public void removeGeofences(int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.location.IFusedGeofenceHardware
            public void pauseMonitoringGeofence(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.location.IFusedGeofenceHardware
            public void resumeMonitoringGeofence(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.location.IFusedGeofenceHardware
            public void modifyGeofenceOptions(int i, int i2, int i3, int i4, int i5, int i6) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    obtain.writeInt(i6);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
