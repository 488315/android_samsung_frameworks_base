package android.hardware.tv.tuner;

import android.hardware.common.NativeHandle;
import android.hardware.common.fmq.MQDescriptor;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IFilter extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$tv$tuner$IFilter".replace('$', '.');
    public static final String HASH = "b0d0067a930514438d7772c2e02069c7370f3620";
    public static final int VERSION = 3;

    void close() throws RemoteException;

    void configure(DemuxFilterSettings demuxFilterSettings) throws RemoteException;

    void configureAvStreamType(AvStreamType avStreamType) throws RemoteException;

    void configureIpCid(int i) throws RemoteException;

    void configureMonitorEvent(int i) throws RemoteException;

    void flush() throws RemoteException;

    long getAvSharedHandle(NativeHandle nativeHandle) throws RemoteException;

    int getId() throws RemoteException;

    long getId64Bit() throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void getQueueDesc(MQDescriptor<Byte, Byte> mQDescriptor) throws RemoteException;

    void releaseAvHandle(NativeHandle nativeHandle, long j) throws RemoteException;

    void setDataSource(IFilter iFilter) throws RemoteException;

    void setDelayHint(FilterDelayHint filterDelayHint) throws RemoteException;

    void start() throws RemoteException;

    void stop() throws RemoteException;

    public static class Default implements IFilter {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.tv.tuner.IFilter
        public void close() throws RemoteException {
        }

        @Override // android.hardware.tv.tuner.IFilter
        public void configure(DemuxFilterSettings demuxFilterSettings) throws RemoteException {
        }

        @Override // android.hardware.tv.tuner.IFilter
        public void configureAvStreamType(AvStreamType avStreamType) throws RemoteException {
        }

        @Override // android.hardware.tv.tuner.IFilter
        public void configureIpCid(int i) throws RemoteException {
        }

        @Override // android.hardware.tv.tuner.IFilter
        public void configureMonitorEvent(int i) throws RemoteException {
        }

        @Override // android.hardware.tv.tuner.IFilter
        public void flush() throws RemoteException {
        }

        @Override // android.hardware.tv.tuner.IFilter
        public long getAvSharedHandle(NativeHandle nativeHandle) throws RemoteException {
            return 0L;
        }

        @Override // android.hardware.tv.tuner.IFilter
        public int getId() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.tv.tuner.IFilter
        public long getId64Bit() throws RemoteException {
            return 0L;
        }

        @Override // android.hardware.tv.tuner.IFilter
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.tv.tuner.IFilter
        public void getQueueDesc(MQDescriptor<Byte, Byte> mQDescriptor) throws RemoteException {
        }

        @Override // android.hardware.tv.tuner.IFilter
        public void releaseAvHandle(NativeHandle nativeHandle, long j) throws RemoteException {
        }

        @Override // android.hardware.tv.tuner.IFilter
        public void setDataSource(IFilter iFilter) throws RemoteException {
        }

        @Override // android.hardware.tv.tuner.IFilter
        public void setDelayHint(FilterDelayHint filterDelayHint) throws RemoteException {
        }

        @Override // android.hardware.tv.tuner.IFilter
        public void start() throws RemoteException {
        }

        @Override // android.hardware.tv.tuner.IFilter
        public void stop() throws RemoteException {
        }

        @Override // android.hardware.tv.tuner.IFilter
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements IFilter {
        static final int TRANSACTION_close = 2;
        static final int TRANSACTION_configure = 3;
        static final int TRANSACTION_configureAvStreamType = 4;
        static final int TRANSACTION_configureIpCid = 5;
        static final int TRANSACTION_configureMonitorEvent = 6;
        static final int TRANSACTION_flush = 9;
        static final int TRANSACTION_getAvSharedHandle = 10;
        static final int TRANSACTION_getId = 11;
        static final int TRANSACTION_getId64Bit = 12;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getQueueDesc = 1;
        static final int TRANSACTION_releaseAvHandle = 13;
        static final int TRANSACTION_setDataSource = 14;
        static final int TRANSACTION_setDelayHint = 15;
        static final int TRANSACTION_start = 7;
        static final int TRANSACTION_stop = 8;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IFilter asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IFilter)) {
                return (IFilter) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
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
                    MQDescriptor<Byte, Byte> mQDescriptor = new MQDescriptor<>();
                    parcel.enforceNoDataAvail();
                    getQueueDesc(mQDescriptor);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(mQDescriptor, 1);
                    return true;
                case 2:
                    close();
                    parcel2.writeNoException();
                    return true;
                case 3:
                    DemuxFilterSettings demuxFilterSettings = (DemuxFilterSettings) parcel.readTypedObject(DemuxFilterSettings.CREATOR);
                    parcel.enforceNoDataAvail();
                    configure(demuxFilterSettings);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    AvStreamType avStreamType = (AvStreamType) parcel.readTypedObject(AvStreamType.CREATOR);
                    parcel.enforceNoDataAvail();
                    configureAvStreamType(avStreamType);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    configureIpCid(i3);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    configureMonitorEvent(i4);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    start();
                    parcel2.writeNoException();
                    return true;
                case 8:
                    stop();
                    parcel2.writeNoException();
                    return true;
                case 9:
                    flush();
                    parcel2.writeNoException();
                    return true;
                case 10:
                    NativeHandle nativeHandle = new NativeHandle();
                    parcel.enforceNoDataAvail();
                    long avSharedHandle = getAvSharedHandle(nativeHandle);
                    parcel2.writeNoException();
                    parcel2.writeLong(avSharedHandle);
                    parcel2.writeTypedObject(nativeHandle, 1);
                    return true;
                case 11:
                    int id = getId();
                    parcel2.writeNoException();
                    parcel2.writeInt(id);
                    return true;
                case 12:
                    long id64Bit = getId64Bit();
                    parcel2.writeNoException();
                    parcel2.writeLong(id64Bit);
                    return true;
                case 13:
                    NativeHandle nativeHandle2 = (NativeHandle) parcel.readTypedObject(NativeHandle.CREATOR);
                    long j = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    releaseAvHandle(nativeHandle2, j);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    IFilter iFilterAsInterface = asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setDataSource(iFilterAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    FilterDelayHint filterDelayHint = (FilterDelayHint) parcel.readTypedObject(FilterDelayHint.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDelayHint(filterDelayHint);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IFilter {
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

            @Override // android.hardware.tv.tuner.IFilter
            public void getQueueDesc(MQDescriptor<Byte, Byte> mQDescriptor) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method getQueueDesc is unimplemented.");
                    }
                    parcelObtain2.readException();
                    if (parcelObtain2.readInt() != 0) {
                        mQDescriptor.readFromParcel(parcelObtain2);
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFilter
            public void close() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method close is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFilter
            public void configure(DemuxFilterSettings demuxFilterSettings) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(demuxFilterSettings, 0);
                    if (!this.mRemote.transact(3, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method configure is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFilter
            public void configureAvStreamType(AvStreamType avStreamType) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(avStreamType, 0);
                    if (!this.mRemote.transact(4, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method configureAvStreamType is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFilter
            public void configureIpCid(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (!this.mRemote.transact(5, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method configureIpCid is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFilter
            public void configureMonitorEvent(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (!this.mRemote.transact(6, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method configureMonitorEvent is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFilter
            public void start() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(7, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method start is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFilter
            public void stop() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(8, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method stop is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFilter
            public void flush() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(9, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method flush is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFilter
            public long getAvSharedHandle(NativeHandle nativeHandle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(10, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method getAvSharedHandle is unimplemented.");
                    }
                    parcelObtain2.readException();
                    long j = parcelObtain2.readLong();
                    if (parcelObtain2.readInt() != 0) {
                        nativeHandle.readFromParcel(parcelObtain2);
                    }
                    return j;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFilter
            public int getId() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(11, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method getId is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFilter
            public long getId64Bit() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(12, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method getId64Bit is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFilter
            public void releaseAvHandle(NativeHandle nativeHandle, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(nativeHandle, 0);
                    parcelObtain.writeLong(j);
                    if (!this.mRemote.transact(13, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method releaseAvHandle is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFilter
            public void setDataSource(IFilter iFilter) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iFilter);
                    if (!this.mRemote.transact(14, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method setDataSource is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFilter
            public void setDelayHint(FilterDelayHint filterDelayHint) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(filterDelayHint, 0);
                    if (!this.mRemote.transact(15, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method setDelayHint is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFilter
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

            @Override // android.hardware.tv.tuner.IFilter
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
