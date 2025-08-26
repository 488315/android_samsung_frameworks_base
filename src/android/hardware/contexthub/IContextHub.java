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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IContextHub)) {
                return (IContextHub) iInterfaceQueryLocalInterface;
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
                    int i3 = parcel.readInt();
                    NanoappBinary nanoappBinary = (NanoappBinary) parcel.readTypedObject(NanoappBinary.CREATOR);
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    loadNanoapp(i3, nanoappBinary, i4);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int i5 = parcel.readInt();
                    long j = parcel.readLong();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unloadNanoapp(i5, j, i6);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int i7 = parcel.readInt();
                    long j2 = parcel.readLong();
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    disableNanoapp(i7, j2, i8);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int i9 = parcel.readInt();
                    long j3 = parcel.readLong();
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    enableNanoapp(i9, j3, i10);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    byte b = parcel.readByte();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onSettingChanged(b, z);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    queryNanoapps(i11);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int i12 = parcel.readInt();
                    IContextHubCallback iContextHubCallbackAsInterface = IContextHubCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerCallback(i12, iContextHubCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int i13 = parcel.readInt();
                    ContextHubMessage contextHubMessage = (ContextHubMessage) parcel.readTypedObject(ContextHubMessage.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendMessageToHub(i13, contextHubMessage);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    HostEndpointInfo hostEndpointInfo = (HostEndpointInfo) parcel.readTypedObject(HostEndpointInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onHostEndpointConnected(hostEndpointInfo);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    char c = (char) parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onHostEndpointDisconnected(c);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long[] preloadedNanoappIds = getPreloadedNanoappIds(i14);
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
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setTestMode(z2);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int i15 = parcel.readInt();
                    MessageDeliveryStatus messageDeliveryStatus = (MessageDeliveryStatus) parcel.readTypedObject(MessageDeliveryStatus.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendMessageDeliveryStatusToHub(i15, messageDeliveryStatus);
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
                    IEndpointCallback iEndpointCallbackAsInterface = IEndpointCallback.Stub.asInterface(parcel.readStrongBinder());
                    HubInfo hubInfo = (HubInfo) parcel.readTypedObject(HubInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    IEndpointCommunication iEndpointCommunicationRegisterEndpointHub = registerEndpointHub(iEndpointCallbackAsInterface, hubInfo);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(iEndpointCommunicationRegisterEndpointHub);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method getContextHubs is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(ContextHubInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHub
            public void loadNanoapp(int i, NanoappBinary nanoappBinary, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(nanoappBinary, 0);
                    parcelObtain.writeInt(i2);
                    if (!this.mRemote.transact(2, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method loadNanoapp is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHub
            public void unloadNanoapp(int i, long j, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i2);
                    if (!this.mRemote.transact(3, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method unloadNanoapp is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHub
            public void disableNanoapp(int i, long j, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i2);
                    if (!this.mRemote.transact(4, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method disableNanoapp is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHub
            public void enableNanoapp(int i, long j, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i2);
                    if (!this.mRemote.transact(5, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method enableNanoapp is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHub
            public void onSettingChanged(byte b, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeByte(b);
                    parcelObtain.writeBoolean(z);
                    if (!this.mRemote.transact(6, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method onSettingChanged is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHub
            public void queryNanoapps(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (!this.mRemote.transact(7, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method queryNanoapps is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHub
            public void registerCallback(int i, IContextHubCallback iContextHubCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iContextHubCallback);
                    if (!this.mRemote.transact(8, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method registerCallback is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHub
            public void sendMessageToHub(int i, ContextHubMessage contextHubMessage) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(contextHubMessage, 0);
                    if (!this.mRemote.transact(9, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method sendMessageToHub is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHub
            public void onHostEndpointConnected(HostEndpointInfo hostEndpointInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(hostEndpointInfo, 0);
                    if (!this.mRemote.transact(10, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method onHostEndpointConnected is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHub
            public void onHostEndpointDisconnected(char c) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(c);
                    if (!this.mRemote.transact(11, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method onHostEndpointDisconnected is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHub
            public long[] getPreloadedNanoappIds(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (!this.mRemote.transact(12, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method getPreloadedNanoappIds is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.createLongArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHub
            public void onNanSessionStateChanged(NanSessionStateUpdate nanSessionStateUpdate) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(nanSessionStateUpdate, 0);
                    if (!this.mRemote.transact(13, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method onNanSessionStateChanged is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHub
            public void setTestMode(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    if (!this.mRemote.transact(14, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method setTestMode is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHub
            public void sendMessageDeliveryStatusToHub(int i, MessageDeliveryStatus messageDeliveryStatus) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(messageDeliveryStatus, 0);
                    if (!this.mRemote.transact(15, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method sendMessageDeliveryStatusToHub is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHub
            public List<HubInfo> getHubs() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(16, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method getHubs is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(HubInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHub
            public List<EndpointInfo> getEndpoints() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(17, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method getEndpoints is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(EndpointInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHub
            public IEndpointCommunication registerEndpointHub(IEndpointCallback iEndpointCallback, HubInfo hubInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iEndpointCallback);
                    parcelObtain.writeTypedObject(hubInfo, 0);
                    boolean zTransact = this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.setPropagateAllowBlocking();
                    if (!zTransact) {
                        throw new RemoteException("Method registerEndpointHub is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return IEndpointCommunication.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHub
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

            @Override // android.hardware.contexthub.IContextHub
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
