package android.hardware.gnss;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IGnssPowerIndicationCallback extends IInterface {
    public static final int CAPABILITY_MULTIBAND_ACQUISITION = 16;
    public static final int CAPABILITY_MULTIBAND_TRACKING = 4;
    public static final int CAPABILITY_OTHER_MODES = 32;
    public static final int CAPABILITY_SINGLEBAND_ACQUISITION = 8;
    public static final int CAPABILITY_SINGLEBAND_TRACKING = 2;
    public static final int CAPABILITY_TOTAL = 1;
    public static final String DESCRIPTOR = "android$hardware$gnss$IGnssPowerIndicationCallback".replace('$', '.');
    public static final String HASH = "fc957f1d3d261d065ff5e5415f2d21caa79c310f";
    public static final int VERSION = 2;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void gnssPowerStatsCb(GnssPowerStats gnssPowerStats) throws RemoteException;

    void setCapabilitiesCb(int i) throws RemoteException;

    public static class Default implements IGnssPowerIndicationCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.gnss.IGnssPowerIndicationCallback
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.gnss.IGnssPowerIndicationCallback
        public void gnssPowerStatsCb(GnssPowerStats gnssPowerStats) throws RemoteException {
        }

        @Override // android.hardware.gnss.IGnssPowerIndicationCallback
        public void setCapabilitiesCb(int i) throws RemoteException {
        }

        @Override // android.hardware.gnss.IGnssPowerIndicationCallback
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements IGnssPowerIndicationCallback {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_gnssPowerStatsCb = 2;
        static final int TRANSACTION_setCapabilitiesCb = 1;

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

        public static IGnssPowerIndicationCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IGnssPowerIndicationCallback)) {
                return (IGnssPowerIndicationCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "setCapabilitiesCb";
            }
            if (i == 2) {
                return "gnssPowerStatsCb";
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
            if (i == 1) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                setCapabilitiesCb(readInt);
                parcel2.writeNoException();
            } else if (i == 2) {
                GnssPowerStats gnssPowerStats = (GnssPowerStats) parcel.readTypedObject(GnssPowerStats.CREATOR);
                parcel.enforceNoDataAvail();
                gnssPowerStatsCb(gnssPowerStats);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IGnssPowerIndicationCallback {
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

            @Override // android.hardware.gnss.IGnssPowerIndicationCallback
            public void setCapabilitiesCb(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new RemoteException("Method setCapabilitiesCb is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnssPowerIndicationCallback
            public void gnssPowerStatsCb(GnssPowerStats gnssPowerStats) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(gnssPowerStats, 0);
                    if (this.mRemote.transact(2, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method gnssPowerStatsCb is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnssPowerIndicationCallback
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

            @Override // android.hardware.gnss.IGnssPowerIndicationCallback
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
