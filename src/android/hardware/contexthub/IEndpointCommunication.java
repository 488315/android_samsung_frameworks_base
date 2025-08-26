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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IEndpointCommunication)) {
                return (IEndpointCommunication) iInterfaceQueryLocalInterface;
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
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int[] iArrRequestSessionIdRange = requestSessionIdRange(i3);
                    parcel2.writeNoException();
                    parcel2.writeFixedArray(iArrRequestSessionIdRange, 1, 2);
                    return true;
                case 4:
                    int i4 = parcel.readInt();
                    EndpointId endpointId = (EndpointId) parcel.readTypedObject(EndpointId.CREATOR);
                    EndpointId endpointId2 = (EndpointId) parcel.readTypedObject(EndpointId.CREATOR);
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    openEndpointSession(i4, endpointId, endpointId2, string);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int i5 = parcel.readInt();
                    Message message = (Message) parcel.readTypedObject(Message.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendMessageToEndpoint(i5, message);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int i6 = parcel.readInt();
                    MessageDeliveryStatus messageDeliveryStatus = (MessageDeliveryStatus) parcel.readTypedObject(MessageDeliveryStatus.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendMessageDeliveryStatusToEndpoint(i6, messageDeliveryStatus);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int i7 = parcel.readInt();
                    byte b = parcel.readByte();
                    parcel.enforceNoDataAvail();
                    closeEndpointSession(i7, b);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    endpointSessionOpenComplete(i8);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(endpointInfo, 0);
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method registerEndpoint is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IEndpointCommunication
            public void unregisterEndpoint(EndpointInfo endpointInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(endpointInfo, 0);
                    if (!this.mRemote.transact(2, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method unregisterEndpoint is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IEndpointCommunication
            public int[] requestSessionIdRange(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (!this.mRemote.transact(3, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method requestSessionIdRange is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return (int[]) parcelObtain2.createFixedArray(int[].class, 2);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IEndpointCommunication
            public void openEndpointSession(int i, EndpointId endpointId, EndpointId endpointId2, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(endpointId, 0);
                    parcelObtain.writeTypedObject(endpointId2, 0);
                    parcelObtain.writeString(str);
                    if (!this.mRemote.transact(4, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method openEndpointSession is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IEndpointCommunication
            public void sendMessageToEndpoint(int i, Message message) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(message, 0);
                    if (!this.mRemote.transact(5, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method sendMessageToEndpoint is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IEndpointCommunication
            public void sendMessageDeliveryStatusToEndpoint(int i, MessageDeliveryStatus messageDeliveryStatus) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(messageDeliveryStatus, 0);
                    if (!this.mRemote.transact(6, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method sendMessageDeliveryStatusToEndpoint is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IEndpointCommunication
            public void closeEndpointSession(int i, byte b) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByte(b);
                    if (!this.mRemote.transact(7, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method closeEndpointSession is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IEndpointCommunication
            public void endpointSessionOpenComplete(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (!this.mRemote.transact(8, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method endpointSessionOpenComplete is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IEndpointCommunication
            public void unregister() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(9, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method unregister is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IEndpointCommunication
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

            @Override // android.hardware.contexthub.IEndpointCommunication
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
