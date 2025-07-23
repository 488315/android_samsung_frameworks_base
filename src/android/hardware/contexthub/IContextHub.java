package android.hardware.contexthub;

import android.hardware.contexthub.IContextHubCallback;
import android.hardware.contexthub.IEndpointCallback;
import android.hardware.contexthub.IEndpointCommunication;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

/* loaded from: classes2.dex */
public interface IContextHub extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$contexthub$IContextHub".replace('$', '.');
    public static final int EX_CONTEXT_HUB_UNSPECIFIED = -1;
    public static final String HASH = "df80fdbb6f95a8a2988bc72b7f08f891847b80eb";
    public static final int VERSION = 4;

    void disableNanoapp(int i, long j, int i2) throws RemoteException;

    void enableNanoapp(int i, long j, int i2) throws RemoteException;

    List<ContextHubInfo> getContextHubs() throws RemoteException;

    List<EndpointInfo> getEndpoints() throws RemoteException;

    List<HubInfo> getHubs() throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    long[] getPreloadedNanoappIds(int i) throws RemoteException;

    void loadNanoapp(int i, NanoappBinary nanoappBinary, int i2) throws RemoteException;

    void onHostEndpointConnected(HostEndpointInfo hostEndpointInfo) throws RemoteException;

    void onHostEndpointDisconnected(char c) throws RemoteException;

    void onNanSessionStateChanged(NanSessionStateUpdate nanSessionStateUpdate) throws RemoteException;

    void onSettingChanged(byte b, boolean z) throws RemoteException;

    void queryNanoapps(int i) throws RemoteException;

    void registerCallback(int i, IContextHubCallback iContextHubCallback) throws RemoteException;

    IEndpointCommunication registerEndpointHub(IEndpointCallback iEndpointCallback, HubInfo hubInfo) throws RemoteException;

    void sendMessageDeliveryStatusToHub(int i, MessageDeliveryStatus messageDeliveryStatus) throws RemoteException;

    void sendMessageToHub(int i, ContextHubMessage contextHubMessage) throws RemoteException;

    void setTestMode(boolean z) throws RemoteException;

    void unloadNanoapp(int i, long j, int i2) throws RemoteException;

    public static class Default implements IContextHub {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.contexthub.IContextHub
        public void disableNanoapp(int i, long j, int i2) throws RemoteException {
        }

        @Override // android.hardware.contexthub.IContextHub
        public void enableNanoapp(int i, long j, int i2) throws RemoteException {
        }

        @Override // android.hardware.contexthub.IContextHub
        public List<ContextHubInfo> getContextHubs() throws RemoteException {
            return null;
        }

        @Override // android.hardware.contexthub.IContextHub
        public List<EndpointInfo> getEndpoints() throws RemoteException {
            return null;
        }

        @Override // android.hardware.contexthub.IContextHub
        public List<HubInfo> getHubs() throws RemoteException {
            return null;
        }

        @Override // android.hardware.contexthub.IContextHub
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.contexthub.IContextHub
        public long[] getPreloadedNanoappIds(int i) throws RemoteException {
            return null;
        }

        @Override // android.hardware.contexthub.IContextHub
        public void loadNanoapp(int i, NanoappBinary nanoappBinary, int i2) throws RemoteException {
        }

        @Override // android.hardware.contexthub.IContextHub
        public void onHostEndpointConnected(HostEndpointInfo hostEndpointInfo) throws RemoteException {
        }

        @Override // android.hardware.contexthub.IContextHub
        public void onHostEndpointDisconnected(char c) throws RemoteException {
        }

        @Override // android.hardware.contexthub.IContextHub
        public void onNanSessionStateChanged(NanSessionStateUpdate nanSessionStateUpdate) throws RemoteException {
        }

        @Override // android.hardware.contexthub.IContextHub
        public void onSettingChanged(byte b, boolean z) throws RemoteException {
        }

        @Override // android.hardware.contexthub.IContextHub
        public void queryNanoapps(int i) throws RemoteException {
        }

        @Override // android.hardware.contexthub.IContextHub
        public void registerCallback(int i, IContextHubCallback iContextHubCallback) throws RemoteException {
        }

        @Override // android.hardware.contexthub.IContextHub
        public IEndpointCommunication registerEndpointHub(IEndpointCallback iEndpointCallback, HubInfo hubInfo) throws RemoteException {
            return null;
        }

        @Override // android.hardware.contexthub.IContextHub
        public void sendMessageDeliveryStatusToHub(int i, MessageDeliveryStatus messageDeliveryStatus) throws RemoteException {
        }

        @Override // android.hardware.contexthub.IContextHub
        public void sendMessageToHub(int i, ContextHubMessage contextHubMessage) throws RemoteException {
        }

        @Override // android.hardware.contexthub.IContextHub
        public void setTestMode(boolean z) throws RemoteException {
        }

        @Override // android.hardware.contexthub.IContextHub
        public void unloadNanoapp(int i, long j, int i2) throws RemoteException {
        }

        @Override // android.hardware.contexthub.IContextHub
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements IContextHub {
        static final int TRANSACTION_disableNanoapp = 4;
        static final int TRANSACTION_enableNanoapp = 5;
        static final int TRANSACTION_getContextHubs = 1;
        static final int TRANSACTION_getEndpoints = 17;
        static final int TRANSACTION_getHubs = 16;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getPreloadedNanoappIds = 12;
        static final int TRANSACTION_loadNanoapp = 2;
        static final int TRANSACTION_onHostEndpointConnected = 10;
        static final int TRANSACTION_onHostEndpointDisconnected = 11;
        static final int TRANSACTION_onNanSessionStateChanged = 13;
        static final int TRANSACTION_onSettingChanged = 6;
        static final int TRANSACTION_queryNanoapps = 7;
        static final int TRANSACTION_registerCallback = 8;
        static final int TRANSACTION_registerEndpointHub = 18;
        static final int TRANSACTION_sendMessageDeliveryStatusToHub = 15;
        static final int TRANSACTION_sendMessageToHub = 9;
        static final int TRANSACTION_setTestMode = 14;
        static final int TRANSACTION_unloadNanoapp = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IContextHub asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IContextHub)) {
                return (IContextHub) queryLocalInterface;
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
                    List<ContextHubInfo> contextHubs = getContextHubs();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(contextHubs, 1);
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    NanoappBinary nanoappBinary = (NanoappBinary) parcel.readTypedObject(NanoappBinary.CREATOR);
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    loadNanoapp(readInt, nanoappBinary, readInt2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    long readLong = parcel.readLong();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unloadNanoapp(readInt3, readLong, readInt4);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int readInt5 = parcel.readInt();
                    long readLong2 = parcel.readLong();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    disableNanoapp(readInt5, readLong2, readInt6);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt7 = parcel.readInt();
                    long readLong3 = parcel.readLong();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    enableNanoapp(readInt7, readLong3, readInt8);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    byte readByte = parcel.readByte();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onSettingChanged(readByte, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    queryNanoapps(readInt9);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int readInt10 = parcel.readInt();
                    IContextHubCallback asInterface = IContextHubCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerCallback(readInt10, asInterface);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int readInt11 = parcel.readInt();
                    ContextHubMessage contextHubMessage = (ContextHubMessage) parcel.readTypedObject(ContextHubMessage.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendMessageToHub(readInt11, contextHubMessage);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    HostEndpointInfo hostEndpointInfo = (HostEndpointInfo) parcel.readTypedObject(HostEndpointInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onHostEndpointConnected(hostEndpointInfo);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    char readInt12 = (char) parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onHostEndpointDisconnected(readInt12);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long[] preloadedNanoappIds = getPreloadedNanoappIds(readInt13);
                    parcel2.writeNoException();
                    parcel2.writeLongArray(preloadedNanoappIds);
                    return true;
                case 13:
                    NanSessionStateUpdate nanSessionStateUpdate = (NanSessionStateUpdate) parcel.readTypedObject(NanSessionStateUpdate.CREATOR);
                    parcel.enforceNoDataAvail();
                    onNanSessionStateChanged(nanSessionStateUpdate);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setTestMode(readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int readInt14 = parcel.readInt();
                    MessageDeliveryStatus messageDeliveryStatus = (MessageDeliveryStatus) parcel.readTypedObject(MessageDeliveryStatus.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendMessageDeliveryStatusToHub(readInt14, messageDeliveryStatus);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    List<HubInfo> hubs = getHubs();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(hubs, 1);
                    return true;
                case 17:
                    List<EndpointInfo> endpoints = getEndpoints();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(endpoints, 1);
                    return true;
                case 18:
                    IEndpointCallback asInterface2 = IEndpointCallback.Stub.asInterface(parcel.readStrongBinder());
                    HubInfo hubInfo = (HubInfo) parcel.readTypedObject(HubInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    IEndpointCommunication registerEndpointHub = registerEndpointHub(asInterface2, hubInfo);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(registerEndpointHub);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IContextHub {
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

            @Override // android.hardware.contexthub.IContextHub
            public List<ContextHubInfo> getContextHubs() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getContextHubs is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.createTypedArrayList(ContextHubInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHub
            public void loadNanoapp(int i, NanoappBinary nanoappBinary, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(nanoappBinary, 0);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new RemoteException("Method loadNanoapp is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHub
            public void unloadNanoapp(int i, long j, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new RemoteException("Method unloadNanoapp is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHub
            public void disableNanoapp(int i, long j, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new RemoteException("Method disableNanoapp is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHub
            public void enableNanoapp(int i, long j, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0)) {
                        throw new RemoteException("Method enableNanoapp is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHub
            public void onSettingChanged(byte b, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeByte(b);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(6, obtain, obtain2, 0)) {
                        throw new RemoteException("Method onSettingChanged is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHub
            public void queryNanoapps(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(7, obtain, obtain2, 0)) {
                        throw new RemoteException("Method queryNanoapps is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHub
            public void registerCallback(int i, IContextHubCallback iContextHubCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iContextHubCallback);
                    if (!this.mRemote.transact(8, obtain, obtain2, 0)) {
                        throw new RemoteException("Method registerCallback is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHub
            public void sendMessageToHub(int i, ContextHubMessage contextHubMessage) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(contextHubMessage, 0);
                    if (!this.mRemote.transact(9, obtain, obtain2, 0)) {
                        throw new RemoteException("Method sendMessageToHub is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHub
            public void onHostEndpointConnected(HostEndpointInfo hostEndpointInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(hostEndpointInfo, 0);
                    if (!this.mRemote.transact(10, obtain, obtain2, 0)) {
                        throw new RemoteException("Method onHostEndpointConnected is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHub
            public void onHostEndpointDisconnected(char c) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(c);
                    if (!this.mRemote.transact(11, obtain, obtain2, 0)) {
                        throw new RemoteException("Method onHostEndpointDisconnected is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHub
            public long[] getPreloadedNanoappIds(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(12, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getPreloadedNanoappIds is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.createLongArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHub
            public void onNanSessionStateChanged(NanSessionStateUpdate nanSessionStateUpdate) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(nanSessionStateUpdate, 0);
                    if (!this.mRemote.transact(13, obtain, obtain2, 0)) {
                        throw new RemoteException("Method onNanSessionStateChanged is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHub
            public void setTestMode(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(14, obtain, obtain2, 0)) {
                        throw new RemoteException("Method setTestMode is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHub
            public void sendMessageDeliveryStatusToHub(int i, MessageDeliveryStatus messageDeliveryStatus) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(messageDeliveryStatus, 0);
                    if (!this.mRemote.transact(15, obtain, obtain2, 0)) {
                        throw new RemoteException("Method sendMessageDeliveryStatusToHub is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHub
            public List<HubInfo> getHubs() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(16, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getHubs is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.createTypedArrayList(HubInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHub
            public List<EndpointInfo> getEndpoints() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(17, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getEndpoints is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.createTypedArrayList(EndpointInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHub
            public IEndpointCommunication registerEndpointHub(IEndpointCallback iEndpointCallback, HubInfo hubInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeStrongInterface(iEndpointCallback);
                    obtain.writeTypedObject(hubInfo, 0);
                    boolean transact = this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.setPropagateAllowBlocking();
                    if (!transact) {
                        throw new RemoteException("Method registerEndpointHub is unimplemented.");
                    }
                    obtain2.readException();
                    return IEndpointCommunication.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHub
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

            @Override // android.hardware.contexthub.IContextHub
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
