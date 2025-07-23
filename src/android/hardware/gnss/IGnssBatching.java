package android.hardware.gnss;

import android.hardware.gnss.IGnssBatchingCallback;
import android.os.BadParcelableException;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IGnssBatching extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$gnss$IGnssBatching".replace('$', '.');
    public static final String HASH = "fc957f1d3d261d065ff5e5415f2d21caa79c310f";
    public static final int VERSION = 2;
    public static final int WAKEUP_ON_FIFO_FULL = 1;

    void cleanup() throws RemoteException;

    void flush() throws RemoteException;

    int getBatchSize() throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void init(IGnssBatchingCallback iGnssBatchingCallback) throws RemoteException;

    void start(Options options) throws RemoteException;

    void stop() throws RemoteException;

    public static class Default implements IGnssBatching {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.gnss.IGnssBatching
        public void cleanup() throws RemoteException {
        }

        @Override // android.hardware.gnss.IGnssBatching
        public void flush() throws RemoteException {
        }

        @Override // android.hardware.gnss.IGnssBatching
        public int getBatchSize() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.gnss.IGnssBatching
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.gnss.IGnssBatching
        public void init(IGnssBatchingCallback iGnssBatchingCallback) throws RemoteException {
        }

        @Override // android.hardware.gnss.IGnssBatching
        public void start(Options options) throws RemoteException {
        }

        @Override // android.hardware.gnss.IGnssBatching
        public void stop() throws RemoteException {
        }

        @Override // android.hardware.gnss.IGnssBatching
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements IGnssBatching {
        static final int TRANSACTION_cleanup = 6;
        static final int TRANSACTION_flush = 4;
        static final int TRANSACTION_getBatchSize = 2;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_init = 1;
        static final int TRANSACTION_start = 3;
        static final int TRANSACTION_stop = 5;

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

        public static IGnssBatching asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IGnssBatching)) {
                return (IGnssBatching) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "init";
                case 2:
                    return "getBatchSize";
                case 3:
                    return "start";
                case 4:
                    return "flush";
                case 5:
                    return "stop";
                case 6:
                    return "cleanup";
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
                    IGnssBatchingCallback asInterface = IGnssBatchingCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    init(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int batchSize = getBatchSize();
                    parcel2.writeNoException();
                    parcel2.writeInt(batchSize);
                    return true;
                case 3:
                    Options options = (Options) parcel.readTypedObject(Options.CREATOR);
                    parcel.enforceNoDataAvail();
                    start(options);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    flush();
                    parcel2.writeNoException();
                    return true;
                case 5:
                    stop();
                    parcel2.writeNoException();
                    return true;
                case 6:
                    cleanup();
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IGnssBatching {
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

            @Override // android.hardware.gnss.IGnssBatching
            public void init(IGnssBatchingCallback iGnssBatchingCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeStrongInterface(iGnssBatchingCallback);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new RemoteException("Method init is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnssBatching
            public int getBatchSize() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getBatchSize is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnssBatching
            public void start(Options options) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(options, 0);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new RemoteException("Method start is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnssBatching
            public void flush() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new RemoteException("Method flush is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnssBatching
            public void stop() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0)) {
                        throw new RemoteException("Method stop is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnssBatching
            public void cleanup() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(6, obtain, obtain2, 0)) {
                        throw new RemoteException("Method cleanup is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnssBatching
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

            @Override // android.hardware.gnss.IGnssBatching
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

    public static class Options implements Parcelable {
        public static final Parcelable.Creator<Options> CREATOR = new Parcelable.Creator<Options>() { // from class: android.hardware.gnss.IGnssBatching.Options.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Options createFromParcel(Parcel parcel) {
                Options options = new Options();
                options.readFromParcel(parcel);
                return options;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Options[] newArray(int i) {
                return new Options[i];
            }
        };
        public long periodNanos = 0;
        public float minDistanceMeters = 0.0f;
        public int flags = 0;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public final int getStability() {
            return 1;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            int dataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeLong(this.periodNanos);
            parcel.writeFloat(this.minDistanceMeters);
            parcel.writeInt(this.flags);
            int dataPosition2 = parcel.dataPosition();
            parcel.setDataPosition(dataPosition);
            parcel.writeInt(dataPosition2 - dataPosition);
            parcel.setDataPosition(dataPosition2);
        }

        public final void readFromParcel(Parcel parcel) {
            int dataPosition = parcel.dataPosition();
            int readInt = parcel.readInt();
            try {
                if (readInt < 4) {
                    throw new BadParcelableException("Parcelable too small");
                }
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.periodNanos = parcel.readLong();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.minDistanceMeters = parcel.readFloat();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.flags = parcel.readInt();
                            if (dataPosition > Integer.MAX_VALUE - readInt) {
                                throw new BadParcelableException("Overflow in the size of parcelable");
                            }
                        } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } catch (Throwable th) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                throw th;
            }
        }
    }
}
