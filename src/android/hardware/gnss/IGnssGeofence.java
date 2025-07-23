package android.hardware.gnss;

import android.hardware.gnss.IGnssGeofenceCallback;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IGnssGeofence extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$gnss$IGnssGeofence".replace('$', '.');
    public static final String HASH = "fc957f1d3d261d065ff5e5415f2d21caa79c310f";
    public static final int VERSION = 2;

    void addGeofence(int i, double d, double d2, double d3, int i2, int i3, int i4, int i5) throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void pauseGeofence(int i) throws RemoteException;

    void removeGeofence(int i) throws RemoteException;

    void resumeGeofence(int i, int i2) throws RemoteException;

    void setCallback(IGnssGeofenceCallback iGnssGeofenceCallback) throws RemoteException;

    public static class Default implements IGnssGeofence {
        @Override // android.hardware.gnss.IGnssGeofence
        public void addGeofence(int i, double d, double d2, double d3, int i2, int i3, int i4, int i5) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.gnss.IGnssGeofence
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.gnss.IGnssGeofence
        public void pauseGeofence(int i) throws RemoteException {
        }

        @Override // android.hardware.gnss.IGnssGeofence
        public void removeGeofence(int i) throws RemoteException {
        }

        @Override // android.hardware.gnss.IGnssGeofence
        public void resumeGeofence(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.gnss.IGnssGeofence
        public void setCallback(IGnssGeofenceCallback iGnssGeofenceCallback) throws RemoteException {
        }

        @Override // android.hardware.gnss.IGnssGeofence
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements IGnssGeofence {
        static final int TRANSACTION_addGeofence = 2;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_pauseGeofence = 3;
        static final int TRANSACTION_removeGeofence = 5;
        static final int TRANSACTION_resumeGeofence = 4;
        static final int TRANSACTION_setCallback = 1;

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

        public static IGnssGeofence asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IGnssGeofence)) {
                return (IGnssGeofence) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "setCallback";
            }
            if (i == 2) {
                return "addGeofence";
            }
            if (i == 3) {
                return "pauseGeofence";
            }
            if (i == 4) {
                return "resumeGeofence";
            }
            if (i == 5) {
                return "removeGeofence";
            }
            switch (i) {
                case 16777214:
                    return "getInterfaceHash";
                case 16777215:
                    return "getInterfaceVersion";
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
            Parcel parcel3;
            String str = DESCRIPTOR;
            if (i < 1 || i > 16777215) {
                parcel3 = parcel;
            } else {
                parcel3 = parcel;
                parcel3.enforceInterface(str);
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
            if (i == 1) {
                IGnssGeofenceCallback asInterface = IGnssGeofenceCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                setCallback(asInterface);
                parcel2.writeNoException();
            } else if (i == 2) {
                int readInt = parcel3.readInt();
                double readDouble = parcel3.readDouble();
                double readDouble2 = parcel.readDouble();
                double readDouble3 = parcel.readDouble();
                int readInt2 = parcel.readInt();
                int readInt3 = parcel.readInt();
                int readInt4 = parcel.readInt();
                int readInt5 = parcel.readInt();
                parcel.enforceNoDataAvail();
                addGeofence(readInt, readDouble, readDouble2, readDouble3, readInt2, readInt3, readInt4, readInt5);
                parcel2.writeNoException();
            } else if (i == 3) {
                int readInt6 = parcel3.readInt();
                parcel3.enforceNoDataAvail();
                pauseGeofence(readInt6);
                parcel2.writeNoException();
            } else if (i == 4) {
                int readInt7 = parcel3.readInt();
                int readInt8 = parcel3.readInt();
                parcel3.enforceNoDataAvail();
                resumeGeofence(readInt7, readInt8);
                parcel2.writeNoException();
            } else if (i == 5) {
                int readInt9 = parcel3.readInt();
                parcel3.enforceNoDataAvail();
                removeGeofence(readInt9);
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IGnssGeofence {
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

            @Override // android.hardware.gnss.IGnssGeofence
            public void setCallback(IGnssGeofenceCallback iGnssGeofenceCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeStrongInterface(iGnssGeofenceCallback);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new RemoteException("Method setCallback is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnssGeofence
            public void addGeofence(int i, double d, double d2, double d3, int i2, int i3, int i4, int i5) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeDouble(d);
                    obtain.writeDouble(d2);
                    obtain.writeDouble(d3);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new RemoteException("Method addGeofence is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnssGeofence
            public void pauseGeofence(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new RemoteException("Method pauseGeofence is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnssGeofence
            public void resumeGeofence(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new RemoteException("Method resumeGeofence is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnssGeofence
            public void removeGeofence(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0)) {
                        throw new RemoteException("Method removeGeofence is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnssGeofence
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

            @Override // android.hardware.gnss.IGnssGeofence
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
