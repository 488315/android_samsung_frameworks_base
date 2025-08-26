package android.os;

import android.hardware.power.ChannelConfig;
import android.hardware.power.CpuHeadroomResult;
import android.hardware.power.GpuHeadroomResult;
import android.hardware.power.SessionConfig;
import android.hardware.power.SupportInfo;
import android.os.IHintSession;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public interface IHintManager extends IInterface {
    public static final String DESCRIPTOR = "android.os.IHintManager";

    public static class Default implements IHintManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.IHintManager
        public void closeSessionChannel() throws RemoteException {
        }

        @Override // android.os.IHintManager
        public SessionCreationReturn createHintSessionWithConfig(IBinder iBinder, int i, SessionCreationConfig sessionCreationConfig, SessionConfig sessionConfig) throws RemoteException {
            return null;
        }

        @Override // android.os.IHintManager
        public HintManagerClientData getClientData() throws RemoteException {
            return null;
        }

        @Override // android.os.IHintManager
        public CpuHeadroomResult getCpuHeadroom(CpuHeadroomParamsInternal cpuHeadroomParamsInternal) throws RemoteException {
            return null;
        }

        @Override // android.os.IHintManager
        public long getCpuHeadroomMinIntervalMillis() throws RemoteException {
            return 0L;
        }

        @Override // android.os.IHintManager
        public GpuHeadroomResult getGpuHeadroom(GpuHeadroomParamsInternal gpuHeadroomParamsInternal) throws RemoteException {
            return null;
        }

        @Override // android.os.IHintManager
        public long getGpuHeadroomMinIntervalMillis() throws RemoteException {
            return 0L;
        }

        @Override // android.os.IHintManager
        public int[] getHintSessionThreadIds(IHintSession iHintSession) throws RemoteException {
            return null;
        }

        @Override // android.os.IHintManager
        public ChannelConfig getSessionChannel(IBinder iBinder) throws RemoteException {
            return null;
        }

        @Override // android.os.IHintManager
        public void passSessionManagerBinder(IBinder iBinder) throws RemoteException {
        }

        @Override // android.os.IHintManager
        public HintManagerClientData registerClient(IHintManagerClient iHintManagerClient) throws RemoteException {
            return null;
        }

        @Override // android.os.IHintManager
        public void setHintSessionThreads(IHintSession iHintSession, int[] iArr) throws RemoteException {
        }
    }

    void closeSessionChannel() throws RemoteException;

    SessionCreationReturn createHintSessionWithConfig(IBinder iBinder, int i, SessionCreationConfig sessionCreationConfig, SessionConfig sessionConfig) throws RemoteException;

    HintManagerClientData getClientData() throws RemoteException;

    CpuHeadroomResult getCpuHeadroom(CpuHeadroomParamsInternal cpuHeadroomParamsInternal) throws RemoteException;

    long getCpuHeadroomMinIntervalMillis() throws RemoteException;

    GpuHeadroomResult getGpuHeadroom(GpuHeadroomParamsInternal gpuHeadroomParamsInternal) throws RemoteException;

    long getGpuHeadroomMinIntervalMillis() throws RemoteException;

    int[] getHintSessionThreadIds(IHintSession iHintSession) throws RemoteException;

    ChannelConfig getSessionChannel(IBinder iBinder) throws RemoteException;

    void passSessionManagerBinder(IBinder iBinder) throws RemoteException;

    HintManagerClientData registerClient(IHintManagerClient iHintManagerClient) throws RemoteException;

    void setHintSessionThreads(IHintSession iHintSession, int[] iArr) throws RemoteException;

    public static abstract class Stub extends Binder implements IHintManager {
        static final int TRANSACTION_closeSessionChannel = 5;
        static final int TRANSACTION_createHintSessionWithConfig = 1;
        static final int TRANSACTION_getClientData = 12;
        static final int TRANSACTION_getCpuHeadroom = 6;
        static final int TRANSACTION_getCpuHeadroomMinIntervalMillis = 7;
        static final int TRANSACTION_getGpuHeadroom = 8;
        static final int TRANSACTION_getGpuHeadroomMinIntervalMillis = 9;
        static final int TRANSACTION_getHintSessionThreadIds = 3;
        static final int TRANSACTION_getSessionChannel = 4;
        static final int TRANSACTION_passSessionManagerBinder = 10;
        static final int TRANSACTION_registerClient = 11;
        static final int TRANSACTION_setHintSessionThreads = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 11;
        }

        public Stub() {
            attachInterface(this, IHintManager.DESCRIPTOR);
        }

        public static IHintManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IHintManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IHintManager)) {
                return (IHintManager) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "createHintSessionWithConfig";
                case 2:
                    return "setHintSessionThreads";
                case 3:
                    return "getHintSessionThreadIds";
                case 4:
                    return "getSessionChannel";
                case 5:
                    return "closeSessionChannel";
                case 6:
                    return "getCpuHeadroom";
                case 7:
                    return "getCpuHeadroomMinIntervalMillis";
                case 8:
                    return "getGpuHeadroom";
                case 9:
                    return "getGpuHeadroomMinIntervalMillis";
                case 10:
                    return "passSessionManagerBinder";
                case 11:
                    return "registerClient";
                case 12:
                    return "getClientData";
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
                parcel.enforceInterface(IHintManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IHintManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    IBinder strongBinder = parcel.readStrongBinder();
                    int i3 = parcel.readInt();
                    SessionCreationConfig sessionCreationConfig = (SessionCreationConfig) parcel.readTypedObject(SessionCreationConfig.CREATOR);
                    SessionConfig sessionConfig = new SessionConfig();
                    parcel.enforceNoDataAvail();
                    SessionCreationReturn sessionCreationReturnCreateHintSessionWithConfig = createHintSessionWithConfig(strongBinder, i3, sessionCreationConfig, sessionConfig);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(sessionCreationReturnCreateHintSessionWithConfig, 1);
                    parcel2.writeTypedObject(sessionConfig, 1);
                    return true;
                case 2:
                    IHintSession iHintSessionAsInterface = IHintSession.Stub.asInterface(parcel.readStrongBinder());
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    setHintSessionThreads(iHintSessionAsInterface, iArrCreateIntArray);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    IHintSession iHintSessionAsInterface2 = IHintSession.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int[] hintSessionThreadIds = getHintSessionThreadIds(iHintSessionAsInterface2);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(hintSessionThreadIds);
                    return true;
                case 4:
                    IBinder strongBinder2 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    ChannelConfig sessionChannel = getSessionChannel(strongBinder2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(sessionChannel, 1);
                    return true;
                case 5:
                    closeSessionChannel();
                    return true;
                case 6:
                    CpuHeadroomParamsInternal cpuHeadroomParamsInternal = (CpuHeadroomParamsInternal) parcel.readTypedObject(CpuHeadroomParamsInternal.CREATOR);
                    parcel.enforceNoDataAvail();
                    CpuHeadroomResult cpuHeadroom = getCpuHeadroom(cpuHeadroomParamsInternal);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(cpuHeadroom, 1);
                    return true;
                case 7:
                    long cpuHeadroomMinIntervalMillis = getCpuHeadroomMinIntervalMillis();
                    parcel2.writeNoException();
                    parcel2.writeLong(cpuHeadroomMinIntervalMillis);
                    return true;
                case 8:
                    GpuHeadroomParamsInternal gpuHeadroomParamsInternal = (GpuHeadroomParamsInternal) parcel.readTypedObject(GpuHeadroomParamsInternal.CREATOR);
                    parcel.enforceNoDataAvail();
                    GpuHeadroomResult gpuHeadroom = getGpuHeadroom(gpuHeadroomParamsInternal);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(gpuHeadroom, 1);
                    return true;
                case 9:
                    long gpuHeadroomMinIntervalMillis = getGpuHeadroomMinIntervalMillis();
                    parcel2.writeNoException();
                    parcel2.writeLong(gpuHeadroomMinIntervalMillis);
                    return true;
                case 10:
                    IBinder strongBinder3 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    passSessionManagerBinder(strongBinder3);
                    return true;
                case 11:
                    IHintManagerClient iHintManagerClientAsInterface = IHintManagerClient.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    HintManagerClientData hintManagerClientDataRegisterClient = registerClient(iHintManagerClientAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(hintManagerClientDataRegisterClient, 1);
                    return true;
                case 12:
                    HintManagerClientData clientData = getClientData();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(clientData, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IHintManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IHintManager.DESCRIPTOR;
            }

            @Override // android.os.IHintManager
            public SessionCreationReturn createHintSessionWithConfig(IBinder iBinder, int i, SessionCreationConfig sessionCreationConfig, SessionConfig sessionConfig) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IHintManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(sessionCreationConfig, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    SessionCreationReturn sessionCreationReturn = (SessionCreationReturn) parcelObtain2.readTypedObject(SessionCreationReturn.CREATOR);
                    if (parcelObtain2.readInt() != 0) {
                        sessionConfig.readFromParcel(parcelObtain2);
                    }
                    return sessionCreationReturn;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IHintManager
            public void setHintSessionThreads(IHintSession iHintSession, int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IHintManager.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iHintSession);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IHintManager
            public int[] getHintSessionThreadIds(IHintSession iHintSession) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IHintManager.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iHintSession);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IHintManager
            public ChannelConfig getSessionChannel(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IHintManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ChannelConfig) parcelObtain2.readTypedObject(ChannelConfig.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IHintManager
            public void closeSessionChannel() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IHintManager.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IHintManager
            public CpuHeadroomResult getCpuHeadroom(CpuHeadroomParamsInternal cpuHeadroomParamsInternal) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IHintManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(cpuHeadroomParamsInternal, 0);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CpuHeadroomResult) parcelObtain2.readTypedObject(CpuHeadroomResult.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IHintManager
            public long getCpuHeadroomMinIntervalMillis() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IHintManager.DESCRIPTOR);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IHintManager
            public GpuHeadroomResult getGpuHeadroom(GpuHeadroomParamsInternal gpuHeadroomParamsInternal) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IHintManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(gpuHeadroomParamsInternal, 0);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (GpuHeadroomResult) parcelObtain2.readTypedObject(GpuHeadroomResult.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IHintManager
            public long getGpuHeadroomMinIntervalMillis() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IHintManager.DESCRIPTOR);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IHintManager
            public void passSessionManagerBinder(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IHintManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(10, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IHintManager
            public HintManagerClientData registerClient(IHintManagerClient iHintManagerClient) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IHintManager.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iHintManagerClient);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (HintManagerClientData) parcelObtain2.readTypedObject(HintManagerClientData.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IHintManager
            public HintManagerClientData getClientData() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IHintManager.DESCRIPTOR);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (HintManagerClientData) parcelObtain2.readTypedObject(HintManagerClientData.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }

    public static class SessionCreationReturn implements Parcelable {
        public static final Parcelable.Creator<SessionCreationReturn> CREATOR = new Parcelable.Creator<SessionCreationReturn>() { // from class: android.os.IHintManager.SessionCreationReturn.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SessionCreationReturn createFromParcel(Parcel parcel) {
                SessionCreationReturn sessionCreationReturn = new SessionCreationReturn();
                sessionCreationReturn.readFromParcel(parcel);
                return sessionCreationReturn;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SessionCreationReturn[] newArray(int i) {
                return new SessionCreationReturn[i];
            }
        };
        public boolean pipelineThreadLimitExceeded = false;
        public IHintSession session;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            int iDataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeStrongInterface(this.session);
            parcel.writeBoolean(this.pipelineThreadLimitExceeded);
            int iDataPosition2 = parcel.dataPosition();
            parcel.setDataPosition(iDataPosition);
            parcel.writeInt(iDataPosition2 - iDataPosition);
            parcel.setDataPosition(iDataPosition2);
        }

        public final void readFromParcel(Parcel parcel) {
            int iDataPosition = parcel.dataPosition();
            int i = parcel.readInt();
            try {
                if (i < 4) {
                    throw new BadParcelableException("Parcelable too small");
                }
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.session = IHintSession.Stub.asInterface(parcel.readStrongBinder());
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.pipelineThreadLimitExceeded = parcel.readBoolean();
                        if (iDataPosition > Integer.MAX_VALUE - i) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (iDataPosition > Integer.MAX_VALUE - i) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (iDataPosition > Integer.MAX_VALUE - i) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(iDataPosition + i);
            } catch (Throwable th) {
                if (iDataPosition > Integer.MAX_VALUE - i) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(iDataPosition + i);
                throw th;
            }
        }
    }

    public static class HintManagerClientData implements Parcelable {
        public static final Parcelable.Creator<HintManagerClientData> CREATOR = new Parcelable.Creator<HintManagerClientData>() { // from class: android.os.IHintManager.HintManagerClientData.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public HintManagerClientData createFromParcel(Parcel parcel) {
                HintManagerClientData hintManagerClientData = new HintManagerClientData();
                hintManagerClientData.readFromParcel(parcel);
                return hintManagerClientData;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public HintManagerClientData[] newArray(int i) {
                return new HintManagerClientData[i];
            }
        };
        public SupportInfo supportInfo;
        public int powerHalVersion = 0;
        public int maxGraphicsPipelineThreads = 0;
        public int maxCpuHeadroomThreads = 0;
        public long preferredRateNanos = 0;

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            int iDataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeInt(this.powerHalVersion);
            parcel.writeInt(this.maxGraphicsPipelineThreads);
            parcel.writeInt(this.maxCpuHeadroomThreads);
            parcel.writeLong(this.preferredRateNanos);
            parcel.writeTypedObject(this.supportInfo, i);
            int iDataPosition2 = parcel.dataPosition();
            parcel.setDataPosition(iDataPosition);
            parcel.writeInt(iDataPosition2 - iDataPosition);
            parcel.setDataPosition(iDataPosition2);
        }

        public final void readFromParcel(Parcel parcel) {
            int iDataPosition = parcel.dataPosition();
            int i = parcel.readInt();
            try {
                if (i < 4) {
                    throw new BadParcelableException("Parcelable too small");
                }
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.powerHalVersion = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.maxGraphicsPipelineThreads = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.maxCpuHeadroomThreads = parcel.readInt();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.preferredRateNanos = parcel.readLong();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.supportInfo = (SupportInfo) parcel.readTypedObject(SupportInfo.CREATOR);
                                    if (iDataPosition > Integer.MAX_VALUE - i) {
                                        throw new BadParcelableException("Overflow in the size of parcelable");
                                    }
                                } else if (iDataPosition > Integer.MAX_VALUE - i) {
                                    throw new BadParcelableException("Overflow in the size of parcelable");
                                }
                            } else if (iDataPosition > Integer.MAX_VALUE - i) {
                                throw new BadParcelableException("Overflow in the size of parcelable");
                            }
                        } else if (iDataPosition > Integer.MAX_VALUE - i) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (iDataPosition > Integer.MAX_VALUE - i) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (iDataPosition > Integer.MAX_VALUE - i) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(iDataPosition + i);
            } catch (Throwable th) {
                if (iDataPosition > Integer.MAX_VALUE - i) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(iDataPosition + i);
                throw th;
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return describeContents(this.supportInfo);
        }

        private int describeContents(Object obj) {
            if (obj != null && (obj instanceof Parcelable)) {
                return ((Parcelable) obj).describeContents();
            }
            return 0;
        }
    }

    public interface IHintManagerClient extends IInterface {
        public static final String DESCRIPTOR = "android.os.IHintManager.IHintManagerClient";

        public static class Default implements IHintManagerClient {
            @Override // android.os.IInterface
            public IBinder asBinder() {
                return null;
            }

            @Override // android.os.IHintManager.IHintManagerClient
            public void receiveChannelConfig(ChannelConfig channelConfig) throws RemoteException {
            }
        }

        void receiveChannelConfig(ChannelConfig channelConfig) throws RemoteException;

        public static abstract class Stub extends Binder implements IHintManagerClient {
            static final int TRANSACTION_receiveChannelConfig = 1;

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this;
            }

            @Override // android.os.Binder
            public int getMaxTransactionId() {
                return 0;
            }

            public Stub() {
                attachInterface(this, IHintManagerClient.DESCRIPTOR);
            }

            public static IHintManagerClient asInterface(IBinder iBinder) {
                if (iBinder == null) {
                    return null;
                }
                IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IHintManagerClient.DESCRIPTOR);
                if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IHintManagerClient)) {
                    return (IHintManagerClient) iInterfaceQueryLocalInterface;
                }
                return new Proxy(iBinder);
            }

            public static String getDefaultTransactionName(int i) {
                if (i != 1) {
                    return null;
                }
                return "receiveChannelConfig";
            }

            @Override // android.os.Binder
            public String getTransactionName(int i) {
                return getDefaultTransactionName(i);
            }

            @Override // android.os.Binder
            public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
                if (i >= 1 && i <= 16777215) {
                    parcel.enforceInterface(IHintManagerClient.DESCRIPTOR);
                }
                if (i == 1598968902) {
                    parcel2.writeString(IHintManagerClient.DESCRIPTOR);
                    return true;
                }
                if (i == 1) {
                    ChannelConfig channelConfig = (ChannelConfig) parcel.readTypedObject(ChannelConfig.CREATOR);
                    parcel.enforceNoDataAvail();
                    receiveChannelConfig(channelConfig);
                    return true;
                }
                return super.onTransact(i, parcel, parcel2, i2);
            }

            private static class Proxy implements IHintManagerClient {
                private IBinder mRemote;

                Proxy(IBinder iBinder) {
                    this.mRemote = iBinder;
                }

                @Override // android.os.IInterface
                public IBinder asBinder() {
                    return this.mRemote;
                }

                public String getInterfaceDescriptor() {
                    return IHintManagerClient.DESCRIPTOR;
                }

                @Override // android.os.IHintManager.IHintManagerClient
                public void receiveChannelConfig(ChannelConfig channelConfig) throws RemoteException {
                    Parcel parcelObtain = Parcel.obtain(asBinder());
                    try {
                        parcelObtain.writeInterfaceToken(IHintManagerClient.DESCRIPTOR);
                        parcelObtain.writeTypedObject(channelConfig, 0);
                        this.mRemote.transact(1, parcelObtain, null, 1);
                    } finally {
                        parcelObtain.recycle();
                    }
                }
            }
        }
    }
}
