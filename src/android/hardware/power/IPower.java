package android.hardware.power;

import android.hardware.power.IPowerHintSession;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IPower extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$power$IPower".replace('$', '.');
    public static final String HASH = "13171cf98a48de298baf85167633376ea3db4ea0";
    public static final int VERSION = 6;

    void closeSessionChannel(int i, int i2) throws RemoteException;

    IPowerHintSession createHintSession(int i, int i2, int[] iArr, long j) throws RemoteException;

    IPowerHintSession createHintSessionWithConfig(int i, int i2, int[] iArr, long j, int i3, SessionConfig sessionConfig) throws RemoteException;

    CpuHeadroomResult getCpuHeadroom(CpuHeadroomParams cpuHeadroomParams) throws RemoteException;

    GpuHeadroomResult getGpuHeadroom(GpuHeadroomParams gpuHeadroomParams) throws RemoteException;

    long getHintSessionPreferredRate() throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    ChannelConfig getSessionChannel(int i, int i2) throws RemoteException;

    SupportInfo getSupportInfo() throws RemoteException;

    boolean isBoostSupported(int i) throws RemoteException;

    boolean isModeSupported(int i) throws RemoteException;

    void sendCompositionData(CompositionData[] compositionDataArr) throws RemoteException;

    void sendCompositionUpdate(CompositionUpdate compositionUpdate) throws RemoteException;

    void setBoost(int i, int i2) throws RemoteException;

    void setMode(int i, boolean z) throws RemoteException;

    public static class Default implements IPower {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.power.IPower
        public void closeSessionChannel(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.power.IPower
        public IPowerHintSession createHintSession(int i, int i2, int[] iArr, long j) throws RemoteException {
            return null;
        }

        @Override // android.hardware.power.IPower
        public IPowerHintSession createHintSessionWithConfig(int i, int i2, int[] iArr, long j, int i3, SessionConfig sessionConfig) throws RemoteException {
            return null;
        }

        @Override // android.hardware.power.IPower
        public CpuHeadroomResult getCpuHeadroom(CpuHeadroomParams cpuHeadroomParams) throws RemoteException {
            return null;
        }

        @Override // android.hardware.power.IPower
        public GpuHeadroomResult getGpuHeadroom(GpuHeadroomParams gpuHeadroomParams) throws RemoteException {
            return null;
        }

        @Override // android.hardware.power.IPower
        public long getHintSessionPreferredRate() throws RemoteException {
            return 0L;
        }

        @Override // android.hardware.power.IPower
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.power.IPower
        public ChannelConfig getSessionChannel(int i, int i2) throws RemoteException {
            return null;
        }

        @Override // android.hardware.power.IPower
        public SupportInfo getSupportInfo() throws RemoteException {
            return null;
        }

        @Override // android.hardware.power.IPower
        public boolean isBoostSupported(int i) throws RemoteException {
            return false;
        }

        @Override // android.hardware.power.IPower
        public boolean isModeSupported(int i) throws RemoteException {
            return false;
        }

        @Override // android.hardware.power.IPower
        public void sendCompositionData(CompositionData[] compositionDataArr) throws RemoteException {
        }

        @Override // android.hardware.power.IPower
        public void sendCompositionUpdate(CompositionUpdate compositionUpdate) throws RemoteException {
        }

        @Override // android.hardware.power.IPower
        public void setBoost(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.power.IPower
        public void setMode(int i, boolean z) throws RemoteException {
        }

        @Override // android.hardware.power.IPower
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements IPower {
        static final int TRANSACTION_closeSessionChannel = 9;
        static final int TRANSACTION_createHintSession = 5;
        static final int TRANSACTION_createHintSessionWithConfig = 7;
        static final int TRANSACTION_getCpuHeadroom = 11;
        static final int TRANSACTION_getGpuHeadroom = 12;
        static final int TRANSACTION_getHintSessionPreferredRate = 6;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getSessionChannel = 8;
        static final int TRANSACTION_getSupportInfo = 10;
        static final int TRANSACTION_isBoostSupported = 4;
        static final int TRANSACTION_isModeSupported = 2;
        static final int TRANSACTION_sendCompositionData = 13;
        static final int TRANSACTION_sendCompositionUpdate = 14;
        static final int TRANSACTION_setBoost = 3;
        static final int TRANSACTION_setMode = 1;

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

        public static IPower asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IPower)) {
                return (IPower) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setMode";
                case 2:
                    return "isModeSupported";
                case 3:
                    return "setBoost";
                case 4:
                    return "isBoostSupported";
                case 5:
                    return "createHintSession";
                case 6:
                    return "getHintSessionPreferredRate";
                case 7:
                    return "createHintSessionWithConfig";
                case 8:
                    return "getSessionChannel";
                case 9:
                    return "closeSessionChannel";
                case 10:
                    return "getSupportInfo";
                case 11:
                    return "getCpuHeadroom";
                case 12:
                    return "getGpuHeadroom";
                case 13:
                    return "sendCompositionData";
                case 14:
                    return "sendCompositionUpdate";
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
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setMode(readInt, readBoolean);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isModeSupported = isModeSupported(readInt2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isModeSupported);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setBoost(readInt3, readInt4);
                    return true;
                case 4:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isBoostSupported = isBoostSupported(readInt5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isBoostSupported);
                    return true;
                case 5:
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    int[] createIntArray = parcel.createIntArray();
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    IPowerHintSession createHintSession = createHintSession(readInt6, readInt7, createIntArray, readLong);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(createHintSession);
                    return true;
                case 6:
                    long hintSessionPreferredRate = getHintSessionPreferredRate();
                    parcel2.writeNoException();
                    parcel2.writeLong(hintSessionPreferredRate);
                    return true;
                case 7:
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    int[] createIntArray2 = parcel.createIntArray();
                    long readLong2 = parcel.readLong();
                    int readInt10 = parcel.readInt();
                    SessionConfig sessionConfig = new SessionConfig();
                    parcel.enforceNoDataAvail();
                    IPowerHintSession createHintSessionWithConfig = createHintSessionWithConfig(readInt8, readInt9, createIntArray2, readLong2, readInt10, sessionConfig);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(createHintSessionWithConfig);
                    parcel2.writeTypedObject(sessionConfig, 1);
                    return true;
                case 8:
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ChannelConfig sessionChannel = getSessionChannel(readInt11, readInt12);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(sessionChannel, 1);
                    return true;
                case 9:
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    closeSessionChannel(readInt13, readInt14);
                    return true;
                case 10:
                    SupportInfo supportInfo = getSupportInfo();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(supportInfo, 1);
                    return true;
                case 11:
                    CpuHeadroomParams cpuHeadroomParams = (CpuHeadroomParams) parcel.readTypedObject(CpuHeadroomParams.CREATOR);
                    parcel.enforceNoDataAvail();
                    CpuHeadroomResult cpuHeadroom = getCpuHeadroom(cpuHeadroomParams);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(cpuHeadroom, 1);
                    return true;
                case 12:
                    GpuHeadroomParams gpuHeadroomParams = (GpuHeadroomParams) parcel.readTypedObject(GpuHeadroomParams.CREATOR);
                    parcel.enforceNoDataAvail();
                    GpuHeadroomResult gpuHeadroom = getGpuHeadroom(gpuHeadroomParams);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(gpuHeadroom, 1);
                    return true;
                case 13:
                    CompositionData[] compositionDataArr = (CompositionData[]) parcel.createTypedArray(CompositionData.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendCompositionData(compositionDataArr);
                    return true;
                case 14:
                    CompositionUpdate compositionUpdate = (CompositionUpdate) parcel.readTypedObject(CompositionUpdate.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendCompositionUpdate(compositionUpdate);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IPower {
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

            @Override // android.hardware.power.IPower
            public void setMode(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    if (this.mRemote.transact(1, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setMode is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.power.IPower
            public boolean isModeSupported(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new RemoteException("Method isModeSupported is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.power.IPower
            public void setBoost(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (this.mRemote.transact(3, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setBoost is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.power.IPower
            public boolean isBoostSupported(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new RemoteException("Method isBoostSupported is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.power.IPower
            public IPowerHintSession createHintSession(int i, int i2, int[] iArr, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeIntArray(iArr);
                    obtain.writeLong(j);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0)) {
                        throw new RemoteException("Method createHintSession is unimplemented.");
                    }
                    obtain2.readException();
                    return IPowerHintSession.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.power.IPower
            public long getHintSessionPreferredRate() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(6, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getHintSessionPreferredRate is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.power.IPower
            public IPowerHintSession createHintSessionWithConfig(int i, int i2, int[] iArr, long j, int i3, SessionConfig sessionConfig) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeIntArray(iArr);
                    obtain.writeLong(j);
                    obtain.writeInt(i3);
                    if (!this.mRemote.transact(7, obtain, obtain2, 0)) {
                        throw new RemoteException("Method createHintSessionWithConfig is unimplemented.");
                    }
                    obtain2.readException();
                    IPowerHintSession asInterface = IPowerHintSession.Stub.asInterface(obtain2.readStrongBinder());
                    if (obtain2.readInt() != 0) {
                        sessionConfig.readFromParcel(obtain2);
                    }
                    return asInterface;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.power.IPower
            public ChannelConfig getSessionChannel(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(8, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getSessionChannel is unimplemented.");
                    }
                    obtain2.readException();
                    return (ChannelConfig) obtain2.readTypedObject(ChannelConfig.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.power.IPower
            public void closeSessionChannel(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (this.mRemote.transact(9, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method closeSessionChannel is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.power.IPower
            public SupportInfo getSupportInfo() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(10, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getSupportInfo is unimplemented.");
                    }
                    obtain2.readException();
                    return (SupportInfo) obtain2.readTypedObject(SupportInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.power.IPower
            public CpuHeadroomResult getCpuHeadroom(CpuHeadroomParams cpuHeadroomParams) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(cpuHeadroomParams, 0);
                    if (!this.mRemote.transact(11, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getCpuHeadroom is unimplemented.");
                    }
                    obtain2.readException();
                    return (CpuHeadroomResult) obtain2.readTypedObject(CpuHeadroomResult.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.power.IPower
            public GpuHeadroomResult getGpuHeadroom(GpuHeadroomParams gpuHeadroomParams) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(gpuHeadroomParams, 0);
                    if (!this.mRemote.transact(12, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getGpuHeadroom is unimplemented.");
                    }
                    obtain2.readException();
                    return (GpuHeadroomResult) obtain2.readTypedObject(GpuHeadroomResult.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.power.IPower
            public void sendCompositionData(CompositionData[] compositionDataArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedArray(compositionDataArr, 0);
                    if (this.mRemote.transact(13, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendCompositionData is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.power.IPower
            public void sendCompositionUpdate(CompositionUpdate compositionUpdate) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(compositionUpdate, 0);
                    if (this.mRemote.transact(14, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendCompositionUpdate is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.power.IPower
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

            @Override // android.hardware.power.IPower
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
