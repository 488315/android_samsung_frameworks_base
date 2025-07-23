package android.hardware.location;

import android.Manifest;
import android.app.ActivityThread;
import android.hardware.location.IGeofenceHardwareCallback;
import android.hardware.location.IGeofenceHardwareMonitorCallback;
import android.location.IFusedGeofenceHardware;
import android.location.IGpsGeofenceHardware;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PermissionEnforcer;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IGeofenceHardware extends IInterface {

    public static class Default implements IGeofenceHardware {
        @Override // android.hardware.location.IGeofenceHardware
        public boolean addCircularFence(int i, GeofenceHardwareRequestParcelable geofenceHardwareRequestParcelable, IGeofenceHardwareCallback iGeofenceHardwareCallback) throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.location.IGeofenceHardware
        public int[] getMonitoringTypes() throws RemoteException {
            return null;
        }

        @Override // android.hardware.location.IGeofenceHardware
        public int getStatusOfMonitoringType(int i) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.location.IGeofenceHardware
        public boolean pauseGeofence(int i, int i2) throws RemoteException {
            return false;
        }

        @Override // android.hardware.location.IGeofenceHardware
        public boolean registerForMonitorStateChangeCallback(int i, IGeofenceHardwareMonitorCallback iGeofenceHardwareMonitorCallback) throws RemoteException {
            return false;
        }

        @Override // android.hardware.location.IGeofenceHardware
        public boolean removeGeofence(int i, int i2) throws RemoteException {
            return false;
        }

        @Override // android.hardware.location.IGeofenceHardware
        public boolean resumeGeofence(int i, int i2, int i3) throws RemoteException {
            return false;
        }

        @Override // android.hardware.location.IGeofenceHardware
        public void setFusedGeofenceHardware(IFusedGeofenceHardware iFusedGeofenceHardware) throws RemoteException {
        }

        @Override // android.hardware.location.IGeofenceHardware
        public void setGpsGeofenceHardware(IGpsGeofenceHardware iGpsGeofenceHardware) throws RemoteException {
        }

        @Override // android.hardware.location.IGeofenceHardware
        public boolean unregisterForMonitorStateChangeCallback(int i, IGeofenceHardwareMonitorCallback iGeofenceHardwareMonitorCallback) throws RemoteException {
            return false;
        }
    }

    boolean addCircularFence(int i, GeofenceHardwareRequestParcelable geofenceHardwareRequestParcelable, IGeofenceHardwareCallback iGeofenceHardwareCallback) throws RemoteException;

    int[] getMonitoringTypes() throws RemoteException;

    int getStatusOfMonitoringType(int i) throws RemoteException;

    boolean pauseGeofence(int i, int i2) throws RemoteException;

    boolean registerForMonitorStateChangeCallback(int i, IGeofenceHardwareMonitorCallback iGeofenceHardwareMonitorCallback) throws RemoteException;

    boolean removeGeofence(int i, int i2) throws RemoteException;

    boolean resumeGeofence(int i, int i2, int i3) throws RemoteException;

    void setFusedGeofenceHardware(IFusedGeofenceHardware iFusedGeofenceHardware) throws RemoteException;

    void setGpsGeofenceHardware(IGpsGeofenceHardware iGpsGeofenceHardware) throws RemoteException;

    boolean unregisterForMonitorStateChangeCallback(int i, IGeofenceHardwareMonitorCallback iGeofenceHardwareMonitorCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IGeofenceHardware {
        public static final String DESCRIPTOR = "android.hardware.location.IGeofenceHardware";
        static final int TRANSACTION_addCircularFence = 5;
        static final int TRANSACTION_getMonitoringTypes = 3;
        static final int TRANSACTION_getStatusOfMonitoringType = 4;
        static final int TRANSACTION_pauseGeofence = 7;
        static final int TRANSACTION_registerForMonitorStateChangeCallback = 9;
        static final int TRANSACTION_removeGeofence = 6;
        static final int TRANSACTION_resumeGeofence = 8;
        static final int TRANSACTION_setFusedGeofenceHardware = 2;
        static final int TRANSACTION_setGpsGeofenceHardware = 1;
        static final int TRANSACTION_unregisterForMonitorStateChangeCallback = 10;
        private final PermissionEnforcer mEnforcer;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 9;
        }

        public Stub(PermissionEnforcer permissionEnforcer) {
            attachInterface(this, DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IGeofenceHardware asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IGeofenceHardware)) {
                return (IGeofenceHardware) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setGpsGeofenceHardware";
                case 2:
                    return "setFusedGeofenceHardware";
                case 3:
                    return "getMonitoringTypes";
                case 4:
                    return "getStatusOfMonitoringType";
                case 5:
                    return "addCircularFence";
                case 6:
                    return "removeGeofence";
                case 7:
                    return "pauseGeofence";
                case 8:
                    return "resumeGeofence";
                case 9:
                    return "registerForMonitorStateChangeCallback";
                case 10:
                    return "unregisterForMonitorStateChangeCallback";
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
                    IGpsGeofenceHardware asInterface = IGpsGeofenceHardware.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setGpsGeofenceHardware(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    IFusedGeofenceHardware asInterface2 = IFusedGeofenceHardware.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setFusedGeofenceHardware(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int[] monitoringTypes = getMonitoringTypes();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(monitoringTypes);
                    return true;
                case 4:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int statusOfMonitoringType = getStatusOfMonitoringType(readInt);
                    parcel2.writeNoException();
                    parcel2.writeInt(statusOfMonitoringType);
                    return true;
                case 5:
                    int readInt2 = parcel.readInt();
                    GeofenceHardwareRequestParcelable geofenceHardwareRequestParcelable = (GeofenceHardwareRequestParcelable) parcel.readTypedObject(GeofenceHardwareRequestParcelable.CREATOR);
                    IGeofenceHardwareCallback asInterface3 = IGeofenceHardwareCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean addCircularFence = addCircularFence(readInt2, geofenceHardwareRequestParcelable, asInterface3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addCircularFence);
                    return true;
                case 6:
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean removeGeofence = removeGeofence(readInt3, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeGeofence);
                    return true;
                case 7:
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean pauseGeofence = pauseGeofence(readInt5, readInt6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(pauseGeofence);
                    return true;
                case 8:
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean resumeGeofence = resumeGeofence(readInt7, readInt8, readInt9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(resumeGeofence);
                    return true;
                case 9:
                    int readInt10 = parcel.readInt();
                    IGeofenceHardwareMonitorCallback asInterface4 = IGeofenceHardwareMonitorCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean registerForMonitorStateChangeCallback = registerForMonitorStateChangeCallback(readInt10, asInterface4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(registerForMonitorStateChangeCallback);
                    return true;
                case 10:
                    int readInt11 = parcel.readInt();
                    IGeofenceHardwareMonitorCallback asInterface5 = IGeofenceHardwareMonitorCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean unregisterForMonitorStateChangeCallback = unregisterForMonitorStateChangeCallback(readInt11, asInterface5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(unregisterForMonitorStateChangeCallback);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IGeofenceHardware {
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

            @Override // android.hardware.location.IGeofenceHardware
            public void setGpsGeofenceHardware(IGpsGeofenceHardware iGpsGeofenceHardware) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iGpsGeofenceHardware);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.location.IGeofenceHardware
            public void setFusedGeofenceHardware(IFusedGeofenceHardware iFusedGeofenceHardware) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iFusedGeofenceHardware);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.location.IGeofenceHardware
            public int[] getMonitoringTypes() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.location.IGeofenceHardware
            public int getStatusOfMonitoringType(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.location.IGeofenceHardware
            public boolean addCircularFence(int i, GeofenceHardwareRequestParcelable geofenceHardwareRequestParcelable, IGeofenceHardwareCallback iGeofenceHardwareCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(geofenceHardwareRequestParcelable, 0);
                    obtain.writeStrongInterface(iGeofenceHardwareCallback);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.location.IGeofenceHardware
            public boolean removeGeofence(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.location.IGeofenceHardware
            public boolean pauseGeofence(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.location.IGeofenceHardware
            public boolean resumeGeofence(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.location.IGeofenceHardware
            public boolean registerForMonitorStateChangeCallback(int i, IGeofenceHardwareMonitorCallback iGeofenceHardwareMonitorCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iGeofenceHardwareMonitorCallback);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.location.IGeofenceHardware
            public boolean unregisterForMonitorStateChangeCallback(int i, IGeofenceHardwareMonitorCallback iGeofenceHardwareMonitorCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iGeofenceHardwareMonitorCallback);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void getMonitoringTypes_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.LOCATION_HARDWARE, getCallingPid(), getCallingUid());
        }

        protected void getStatusOfMonitoringType_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.LOCATION_HARDWARE, getCallingPid(), getCallingUid());
        }

        protected void addCircularFence_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.LOCATION_HARDWARE, getCallingPid(), getCallingUid());
        }

        protected void removeGeofence_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.LOCATION_HARDWARE, getCallingPid(), getCallingUid());
        }

        protected void pauseGeofence_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.LOCATION_HARDWARE, getCallingPid(), getCallingUid());
        }

        protected void resumeGeofence_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.LOCATION_HARDWARE, getCallingPid(), getCallingUid());
        }

        protected void registerForMonitorStateChangeCallback_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.LOCATION_HARDWARE, getCallingPid(), getCallingUid());
        }

        protected void unregisterForMonitorStateChangeCallback_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.LOCATION_HARDWARE, getCallingPid(), getCallingUid());
        }
    }
}
