package android.hardware.gnss;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IGnssGeofenceCallback extends IInterface {
    public static final int AVAILABLE = 2;
    public static final String DESCRIPTOR = "android$hardware$gnss$IGnssGeofenceCallback".replace('$', '.');
    public static final int ENTERED = 1;
    public static final int ERROR_GENERIC = -149;
    public static final int ERROR_ID_EXISTS = -101;
    public static final int ERROR_ID_UNKNOWN = -102;
    public static final int ERROR_INVALID_TRANSITION = -103;
    public static final int ERROR_TOO_MANY_GEOFENCES = -100;
    public static final int EXITED = 2;
    public static final String HASH = "fc957f1d3d261d065ff5e5415f2d21caa79c310f";
    public static final int OPERATION_SUCCESS = 0;
    public static final int UNAVAILABLE = 1;
    public static final int UNCERTAIN = 4;
    public static final int VERSION = 2;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void gnssGeofenceAddCb(int i, int i2) throws RemoteException;

    void gnssGeofencePauseCb(int i, int i2) throws RemoteException;

    void gnssGeofenceRemoveCb(int i, int i2) throws RemoteException;

    void gnssGeofenceResumeCb(int i, int i2) throws RemoteException;

    void gnssGeofenceStatusCb(int i, GnssLocation gnssLocation) throws RemoteException;

    void gnssGeofenceTransitionCb(int i, GnssLocation gnssLocation, int i2, long j) throws RemoteException;

    public static class Default implements IGnssGeofenceCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.gnss.IGnssGeofenceCallback
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.gnss.IGnssGeofenceCallback
        public void gnssGeofenceAddCb(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.gnss.IGnssGeofenceCallback
        public void gnssGeofencePauseCb(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.gnss.IGnssGeofenceCallback
        public void gnssGeofenceRemoveCb(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.gnss.IGnssGeofenceCallback
        public void gnssGeofenceResumeCb(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.gnss.IGnssGeofenceCallback
        public void gnssGeofenceStatusCb(int i, GnssLocation gnssLocation) throws RemoteException {
        }

        @Override // android.hardware.gnss.IGnssGeofenceCallback
        public void gnssGeofenceTransitionCb(int i, GnssLocation gnssLocation, int i2, long j) throws RemoteException {
        }

        @Override // android.hardware.gnss.IGnssGeofenceCallback
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements IGnssGeofenceCallback {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_gnssGeofenceAddCb = 3;
        static final int TRANSACTION_gnssGeofencePauseCb = 5;
        static final int TRANSACTION_gnssGeofenceRemoveCb = 4;
        static final int TRANSACTION_gnssGeofenceResumeCb = 6;
        static final int TRANSACTION_gnssGeofenceStatusCb = 2;
        static final int TRANSACTION_gnssGeofenceTransitionCb = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 16777214;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IGnssGeofenceCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IGnssGeofenceCallback)) {
                return (IGnssGeofenceCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "gnssGeofenceTransitionCb";
                case 2:
                    return "gnssGeofenceStatusCb";
                case 3:
                    return "gnssGeofenceAddCb";
                case 4:
                    return "gnssGeofenceRemoveCb";
                case 5:
                    return "gnssGeofencePauseCb";
                case 6:
                    return "gnssGeofenceResumeCb";
                default:
                    switch (i) {
                        case 16777214:
                            return "getInterfaceHash";
                        case 16777215:
                            return "getInterfaceVersion";
                        default:
                            return null;
                    }
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            String str = DESCRIPTOR;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(str);
            }
            if (i == 1598968902) {
                parcel2.writeString(str);
                return true;
            }
            if (i == 16777215) {
                parcel2.writeNoException();
                parcel2.writeInt(getInterfaceVersion());
                return true;
            }
            if (i == 16777214) {
                parcel2.writeNoException();
                parcel2.writeString(getInterfaceHash());
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    GnssLocation gnssLocation = (GnssLocation) parcel.readTypedObject(GnssLocation.CREATOR);
                    int readInt2 = parcel.readInt();
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    gnssGeofenceTransitionCb(readInt, gnssLocation, readInt2, readLong);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int readInt3 = parcel.readInt();
                    GnssLocation gnssLocation2 = (GnssLocation) parcel.readTypedObject(GnssLocation.CREATOR);
                    parcel.enforceNoDataAvail();
                    gnssGeofenceStatusCb(readInt3, gnssLocation2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    gnssGeofenceAddCb(readInt4, readInt5);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    gnssGeofenceRemoveCb(readInt6, readInt7);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    gnssGeofencePauseCb(readInt8, readInt9);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    gnssGeofenceResumeCb(readInt10, readInt11);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IGnssGeofenceCallback {
            private IBinder mRemote;
            private int mCachedVersion = -1;
            private String mCachedHash = "-1";

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override // android.hardware.gnss.IGnssGeofenceCallback
            public void gnssGeofenceTransitionCb(int i, GnssLocation gnssLocation, int i2, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(gnssLocation, 0);
                    obtain.writeInt(i2);
                    obtain.writeLong(j);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new RemoteException("Method gnssGeofenceTransitionCb is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnssGeofenceCallback
            public void gnssGeofenceStatusCb(int i, GnssLocation gnssLocation) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(gnssLocation, 0);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new RemoteException("Method gnssGeofenceStatusCb is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnssGeofenceCallback
            public void gnssGeofenceAddCb(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new RemoteException("Method gnssGeofenceAddCb is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnssGeofenceCallback
            public void gnssGeofenceRemoveCb(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new RemoteException("Method gnssGeofenceRemoveCb is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnssGeofenceCallback
            public void gnssGeofencePauseCb(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0)) {
                        throw new RemoteException("Method gnssGeofencePauseCb is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnssGeofenceCallback
            public void gnssGeofenceResumeCb(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(6, obtain, obtain2, 0)) {
                        throw new RemoteException("Method gnssGeofenceResumeCb is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnssGeofenceCallback
            public int getInterfaceVersion() throws RemoteException {
                if (this.mCachedVersion == -1) {
                    Parcel obtain = Parcel.obtain(asBinder());
                    Parcel obtain2 = Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777215, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedVersion = obtain2.readInt();
                    } finally {
                        obtain2.recycle();
                        obtain.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // android.hardware.gnss.IGnssGeofenceCallback
            public synchronized String getInterfaceHash() throws RemoteException {
                if ("-1".equals(this.mCachedHash)) {
                    Parcel obtain = Parcel.obtain(asBinder());
                    Parcel obtain2 = Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777214, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedHash = obtain2.readString();
                        obtain2.recycle();
                        obtain.recycle();
                    } catch (Throwable th) {
                        obtain2.recycle();
                        obtain.recycle();
                        throw th;
                    }
                }
                return this.mCachedHash;
            }
        }
    }
}
