package android.hardware.contexthub;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IEndpointCommunication extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$contexthub$IEndpointCommunication".replace('$', '.');
    public static final String HASH = "df80fdbb6f95a8a2988bc72b7f08f891847b80eb";
    public static final int VERSION = 4;

    void closeEndpointSession(int i, byte b) throws RemoteException;

    void endpointSessionOpenComplete(int i) throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void openEndpointSession(int i, EndpointId endpointId, EndpointId endpointId2, String str) throws RemoteException;

    void registerEndpoint(EndpointInfo endpointInfo) throws RemoteException;

    int[] requestSessionIdRange(int i) throws RemoteException;

    void sendMessageDeliveryStatusToEndpoint(int i, MessageDeliveryStatus messageDeliveryStatus) throws RemoteException;

    void sendMessageToEndpoint(int i, Message message) throws RemoteException;

    void unregister() throws RemoteException;

    void unregisterEndpoint(EndpointInfo endpointInfo) throws RemoteException;

    public static class Default implements IEndpointCommunication {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.contexthub.IEndpointCommunication
        public void closeEndpointSession(int i, byte b) throws RemoteException {
        }

        @Override // android.hardware.contexthub.IEndpointCommunication
        public void endpointSessionOpenComplete(int i) throws RemoteException {
        }

        @Override // android.hardware.contexthub.IEndpointCommunication
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.contexthub.IEndpointCommunication
        public void openEndpointSession(int i, EndpointId endpointId, EndpointId endpointId2, String str) throws RemoteException {
        }

        @Override // android.hardware.contexthub.IEndpointCommunication
        public void registerEndpoint(EndpointInfo endpointInfo) throws RemoteException {
        }

        @Override // android.hardware.contexthub.IEndpointCommunication
        public int[] requestSessionIdRange(int i) throws RemoteException {
            return null;
        }

        @Override // android.hardware.contexthub.IEndpointCommunication
        public void sendMessageDeliveryStatusToEndpoint(int i, MessageDeliveryStatus messageDeliveryStatus) throws RemoteException {
        }

        @Override // android.hardware.contexthub.IEndpointCommunication
        public void sendMessageToEndpoint(int i, Message message) throws RemoteException {
        }

        @Override // android.hardware.contexthub.IEndpointCommunication
        public void unregister() throws RemoteException {
        }

        @Override // android.hardware.contexthub.IEndpointCommunication
        public void unregisterEndpoint(EndpointInfo endpointInfo) throws RemoteException {
        }

        @Override // android.hardware.contexthub.IEndpointCommunication
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements IEndpointCommunication {
        static final int TRANSACTION_closeEndpointSession = 7;
        static final int TRANSACTION_endpointSessionOpenComplete = 8;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_openEndpointSession = 4;
        static final int TRANSACTION_registerEndpoint = 1;
        static final int TRANSACTION_requestSessionIdRange = 3;
        static final int TRANSACTION_sendMessageDeliveryStatusToEndpoint = 6;
        static final int TRANSACTION_sendMessageToEndpoint = 5;
        static final int TRANSACTION_unregister = 9;
        static final int TRANSACTION_unregisterEndpoint = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IEndpointCommunication asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IEndpointCommunication)) {
                return (IEndpointCommunication) queryLocalInterface;
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
                    EndpointInfo endpointInfo = (EndpointInfo) parcel.readTypedObject(EndpointInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    registerEndpoint(endpointInfo);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    EndpointInfo endpointInfo2 = (EndpointInfo) parcel.readTypedObject(EndpointInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    unregisterEndpoint(endpointInfo2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int[] requestSessionIdRange = requestSessionIdRange(readInt);
                    parcel2.writeNoException();
                    parcel2.writeFixedArray(requestSessionIdRange, 1, 2);
                    return true;
                case 4:
                    int readInt2 = parcel.readInt();
                    EndpointId endpointId = (EndpointId) parcel.readTypedObject(EndpointId.CREATOR);
                    EndpointId endpointId2 = (EndpointId) parcel.readTypedObject(EndpointId.CREATOR);
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    openEndpointSession(readInt2, endpointId, endpointId2, readString);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt3 = parcel.readInt();
                    Message message = (Message) parcel.readTypedObject(Message.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendMessageToEndpoint(readInt3, message);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int readInt4 = parcel.readInt();
                    MessageDeliveryStatus messageDeliveryStatus = (MessageDeliveryStatus) parcel.readTypedObject(MessageDeliveryStatus.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendMessageDeliveryStatusToEndpoint(readInt4, messageDeliveryStatus);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int readInt5 = parcel.readInt();
                    byte readByte = parcel.readByte();
                    parcel.enforceNoDataAvail();
                    closeEndpointSession(readInt5, readByte);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    endpointSessionOpenComplete(readInt6);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    unregister();
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IEndpointCommunication {
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

            @Override // android.hardware.contexthub.IEndpointCommunication
            public void registerEndpoint(EndpointInfo endpointInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(endpointInfo, 0);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new RemoteException("Method registerEndpoint is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IEndpointCommunication
            public void unregisterEndpoint(EndpointInfo endpointInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(endpointInfo, 0);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new RemoteException("Method unregisterEndpoint is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IEndpointCommunication
            public int[] requestSessionIdRange(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new RemoteException("Method requestSessionIdRange is unimplemented.");
                    }
                    obtain2.readException();
                    return (int[]) obtain2.createFixedArray(int[].class, 2);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IEndpointCommunication
            public void openEndpointSession(int i, EndpointId endpointId, EndpointId endpointId2, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(endpointId, 0);
                    obtain.writeTypedObject(endpointId2, 0);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new RemoteException("Method openEndpointSession is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IEndpointCommunication
            public void sendMessageToEndpoint(int i, Message message) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(message, 0);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0)) {
                        throw new RemoteException("Method sendMessageToEndpoint is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IEndpointCommunication
            public void sendMessageDeliveryStatusToEndpoint(int i, MessageDeliveryStatus messageDeliveryStatus) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(messageDeliveryStatus, 0);
                    if (!this.mRemote.transact(6, obtain, obtain2, 0)) {
                        throw new RemoteException("Method sendMessageDeliveryStatusToEndpoint is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IEndpointCommunication
            public void closeEndpointSession(int i, byte b) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByte(b);
                    if (!this.mRemote.transact(7, obtain, obtain2, 0)) {
                        throw new RemoteException("Method closeEndpointSession is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IEndpointCommunication
            public void endpointSessionOpenComplete(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(8, obtain, obtain2, 0)) {
                        throw new RemoteException("Method endpointSessionOpenComplete is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IEndpointCommunication
            public void unregister() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(9, obtain, obtain2, 0)) {
                        throw new RemoteException("Method unregister is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IEndpointCommunication
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

            @Override // android.hardware.contexthub.IEndpointCommunication
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
