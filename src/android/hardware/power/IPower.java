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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IPower)) {
                return (IPower) iInterfaceQueryLocalInterface;
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
                    int i3 = parcel.readInt();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setMode(i3, z);
                    return true;
                case 2:
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsModeSupported = isModeSupported(i4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsModeSupported);
                    return true;
                case 3:
                    int i5 = parcel.readInt();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setBoost(i5, i6);
                    return true;
                case 4:
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsBoostSupported = isBoostSupported(i7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsBoostSupported);
                    return true;
                case 5:
                    int i8 = parcel.readInt();
                    int i9 = parcel.readInt();
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    long j = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    IPowerHintSession iPowerHintSessionCreateHintSession = createHintSession(i8, i9, iArrCreateIntArray, j);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(iPowerHintSessionCreateHintSession);
                    return true;
                case 6:
                    long hintSessionPreferredRate = getHintSessionPreferredRate();
                    parcel2.writeNoException();
                    parcel2.writeLong(hintSessionPreferredRate);
                    return true;
                case 7:
                    int i10 = parcel.readInt();
                    int i11 = parcel.readInt();
                    int[] iArrCreateIntArray2 = parcel.createIntArray();
                    long j2 = parcel.readLong();
                    int i12 = parcel.readInt();
                    SessionConfig sessionConfig = new SessionConfig();
                    parcel.enforceNoDataAvail();
                    IPowerHintSession iPowerHintSessionCreateHintSessionWithConfig = createHintSessionWithConfig(i10, i11, iArrCreateIntArray2, j2, i12, sessionConfig);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(iPowerHintSessionCreateHintSessionWithConfig);
                    parcel2.writeTypedObject(sessionConfig, 1);
                    return true;
                case 8:
                    int i13 = parcel.readInt();
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ChannelConfig sessionChannel = getSessionChannel(i13, i14);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(sessionChannel, 1);
                    return true;
                case 9:
                    int i15 = parcel.readInt();
                    int i16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    closeSessionChannel(i15, i16);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    if (this.mRemote.transact(1, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setMode is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.power.IPower
            public boolean isModeSupported(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (!this.mRemote.transact(2, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method isModeSupported is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.power.IPower
            public void setBoost(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (this.mRemote.transact(3, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setBoost is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.power.IPower
            public boolean isBoostSupported(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (!this.mRemote.transact(4, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method isBoostSupported is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.power.IPower
            public IPowerHintSession createHintSession(int i, int i2, int[] iArr, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeLong(j);
                    if (!this.mRemote.transact(5, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method createHintSession is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return IPowerHintSession.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.power.IPower
            public long getHintSessionPreferredRate() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(6, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method getHintSessionPreferredRate is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.power.IPower
            public IPowerHintSession createHintSessionWithConfig(int i, int i2, int[] iArr, long j, int i3, SessionConfig sessionConfig) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i3);
                    if (!this.mRemote.transact(7, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method createHintSessionWithConfig is unimplemented.");
                    }
                    parcelObtain2.readException();
                    IPowerHintSession iPowerHintSessionAsInterface = IPowerHintSession.Stub.asInterface(parcelObtain2.readStrongBinder());
                    if (parcelObtain2.readInt() != 0) {
                        sessionConfig.readFromParcel(parcelObtain2);
                    }
                    return iPowerHintSessionAsInterface;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.power.IPower
            public ChannelConfig getSessionChannel(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (!this.mRemote.transact(8, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method getSessionChannel is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return (ChannelConfig) parcelObtain2.readTypedObject(ChannelConfig.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.power.IPower
            public void closeSessionChannel(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (this.mRemote.transact(9, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method closeSessionChannel is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.power.IPower
            public SupportInfo getSupportInfo() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(10, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method getSupportInfo is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return (SupportInfo) parcelObtain2.readTypedObject(SupportInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.power.IPower
            public CpuHeadroomResult getCpuHeadroom(CpuHeadroomParams cpuHeadroomParams) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(cpuHeadroomParams, 0);
                    if (!this.mRemote.transact(11, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method getCpuHeadroom is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return (CpuHeadroomResult) parcelObtain2.readTypedObject(CpuHeadroomResult.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.power.IPower
            public GpuHeadroomResult getGpuHeadroom(GpuHeadroomParams gpuHeadroomParams) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(gpuHeadroomParams, 0);
                    if (!this.mRemote.transact(12, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method getGpuHeadroom is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return (GpuHeadroomResult) parcelObtain2.readTypedObject(GpuHeadroomResult.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.power.IPower
            public void sendCompositionData(CompositionData[] compositionDataArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedArray(compositionDataArr, 0);
                    if (this.mRemote.transact(13, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendCompositionData is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.power.IPower
            public void sendCompositionUpdate(CompositionUpdate compositionUpdate) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(compositionUpdate, 0);
                    if (this.mRemote.transact(14, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendCompositionUpdate is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.power.IPower
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

            @Override // android.hardware.power.IPower
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
