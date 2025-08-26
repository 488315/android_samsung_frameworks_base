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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IGnssGeofenceCallback)) {
                return (IGnssGeofenceCallback) iInterfaceQueryLocalInterface;
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
                    int i3 = parcel.readInt();
                    GnssLocation gnssLocation = (GnssLocation) parcel.readTypedObject(GnssLocation.CREATOR);
                    int i4 = parcel.readInt();
                    long j = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    gnssGeofenceTransitionCb(i3, gnssLocation, i4, j);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int i5 = parcel.readInt();
                    GnssLocation gnssLocation2 = (GnssLocation) parcel.readTypedObject(GnssLocation.CREATOR);
                    parcel.enforceNoDataAvail();
                    gnssGeofenceStatusCb(i5, gnssLocation2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int i6 = parcel.readInt();
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    gnssGeofenceAddCb(i6, i7);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int i8 = parcel.readInt();
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    gnssGeofenceRemoveCb(i8, i9);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int i10 = parcel.readInt();
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    gnssGeofencePauseCb(i10, i11);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int i12 = parcel.readInt();
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    gnssGeofenceResumeCb(i12, i13);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(gnssLocation, 0);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeLong(j);
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method gnssGeofenceTransitionCb is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnssGeofenceCallback
            public void gnssGeofenceStatusCb(int i, GnssLocation gnssLocation) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(gnssLocation, 0);
                    if (!this.mRemote.transact(2, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method gnssGeofenceStatusCb is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnssGeofenceCallback
            public void gnssGeofenceAddCb(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (!this.mRemote.transact(3, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method gnssGeofenceAddCb is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnssGeofenceCallback
            public void gnssGeofenceRemoveCb(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (!this.mRemote.transact(4, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method gnssGeofenceRemoveCb is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnssGeofenceCallback
            public void gnssGeofencePauseCb(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (!this.mRemote.transact(5, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method gnssGeofencePauseCb is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnssGeofenceCallback
            public void gnssGeofenceResumeCb(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (!this.mRemote.transact(6, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method gnssGeofenceResumeCb is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnssGeofenceCallback
            public int getInterfaceVersion() throws RemoteException {
                if (this.mCachedVersion == -1) {
                    Parcel parcelObtain = Parcel.obtain(asBinder());
                    Parcel parcelObtain2 = Parcel.obtain();
                    try {
                        parcelObtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777215, parcelObtain, parcelObtain2, 0);
                        parcelObtain2.readException();
                        this.mCachedVersion = parcelObtain2.readInt();
                    } finally {
                        parcelObtain2.recycle();
                        parcelObtain.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // android.hardware.gnss.IGnssGeofenceCallback
            public synchronized String getInterfaceHash() throws RemoteException {
                if ("-1".equals(this.mCachedHash)) {
                    Parcel parcelObtain = Parcel.obtain(asBinder());
                    Parcel parcelObtain2 = Parcel.obtain();
                    try {
                        parcelObtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777214, parcelObtain, parcelObtain2, 0);
                        parcelObtain2.readException();
                        this.mCachedHash = parcelObtain2.readString();
                        parcelObtain2.recycle();
                        parcelObtain.recycle();
                    } catch (Throwable th) {
                        parcelObtain2.recycle();
                        parcelObtain.recycle();
                        throw th;
                    }
                }
                return this.mCachedHash;
            }
        }
    }
}
