package android.hardware.contexthub;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IEndpointCallback extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$contexthub$IEndpointCallback".replace('$', '.');
    public static final String HASH = "df80fdbb6f95a8a2988bc72b7f08f891847b80eb";
    public static final int VERSION = 4;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void onCloseEndpointSession(int i, byte b) throws RemoteException;

    void onEndpointSessionOpenComplete(int i) throws RemoteException;

    void onEndpointSessionOpenRequest(int i, EndpointId endpointId, EndpointId endpointId2, String str) throws RemoteException;

    void onEndpointStarted(EndpointInfo[] endpointInfoArr) throws RemoteException;

    void onEndpointStopped(EndpointId[] endpointIdArr, byte b) throws RemoteException;

    void onMessageDeliveryStatusReceived(int i, MessageDeliveryStatus messageDeliveryStatus) throws RemoteException;

    void onMessageReceived(int i, Message message) throws RemoteException;

    public static class Default implements IEndpointCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.contexthub.IEndpointCallback
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.contexthub.IEndpointCallback
        public void onCloseEndpointSession(int i, byte b) throws RemoteException {
        }

        @Override // android.hardware.contexthub.IEndpointCallback
        public void onEndpointSessionOpenComplete(int i) throws RemoteException {
        }

        @Override // android.hardware.contexthub.IEndpointCallback
        public void onEndpointSessionOpenRequest(int i, EndpointId endpointId, EndpointId endpointId2, String str) throws RemoteException {
        }

        @Override // android.hardware.contexthub.IEndpointCallback
        public void onEndpointStarted(EndpointInfo[] endpointInfoArr) throws RemoteException {
        }

        @Override // android.hardware.contexthub.IEndpointCallback
        public void onEndpointStopped(EndpointId[] endpointIdArr, byte b) throws RemoteException {
        }

        @Override // android.hardware.contexthub.IEndpointCallback
        public void onMessageDeliveryStatusReceived(int i, MessageDeliveryStatus messageDeliveryStatus) throws RemoteException {
        }

        @Override // android.hardware.contexthub.IEndpointCallback
        public void onMessageReceived(int i, Message message) throws RemoteException {
        }

        @Override // android.hardware.contexthub.IEndpointCallback
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements IEndpointCallback {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_onCloseEndpointSession = 6;
        static final int TRANSACTION_onEndpointSessionOpenComplete = 7;
        static final int TRANSACTION_onEndpointSessionOpenRequest = 5;
        static final int TRANSACTION_onEndpointStarted = 1;
        static final int TRANSACTION_onEndpointStopped = 2;
        static final int TRANSACTION_onMessageDeliveryStatusReceived = 4;
        static final int TRANSACTION_onMessageReceived = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IEndpointCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IEndpointCallback)) {
                return (IEndpointCallback) queryLocalInterface;
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
                    EndpointInfo[] endpointInfoArr = (EndpointInfo[]) parcel.createTypedArray(EndpointInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onEndpointStarted(endpointInfoArr);
                    return true;
                case 2:
                    EndpointId[] endpointIdArr = (EndpointId[]) parcel.createTypedArray(EndpointId.CREATOR);
                    byte readByte = parcel.readByte();
                    parcel.enforceNoDataAvail();
                    onEndpointStopped(endpointIdArr, readByte);
                    return true;
                case 3:
                    int readInt = parcel.readInt();
                    Message message = (Message) parcel.readTypedObject(Message.CREATOR);
                    parcel.enforceNoDataAvail();
                    onMessageReceived(readInt, message);
                    return true;
                case 4:
                    int readInt2 = parcel.readInt();
                    MessageDeliveryStatus messageDeliveryStatus = (MessageDeliveryStatus) parcel.readTypedObject(MessageDeliveryStatus.CREATOR);
                    parcel.enforceNoDataAvail();
                    onMessageDeliveryStatusReceived(readInt2, messageDeliveryStatus);
                    return true;
                case 5:
                    int readInt3 = parcel.readInt();
                    EndpointId endpointId = (EndpointId) parcel.readTypedObject(EndpointId.CREATOR);
                    EndpointId endpointId2 = (EndpointId) parcel.readTypedObject(EndpointId.CREATOR);
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onEndpointSessionOpenRequest(readInt3, endpointId, endpointId2, readString);
                    return true;
                case 6:
                    int readInt4 = parcel.readInt();
                    byte readByte2 = parcel.readByte();
                    parcel.enforceNoDataAvail();
                    onCloseEndpointSession(readInt4, readByte2);
                    return true;
                case 7:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onEndpointSessionOpenComplete(readInt5);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IEndpointCallback {
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

            @Override // android.hardware.contexthub.IEndpointCallback
            public void onEndpointStarted(EndpointInfo[] endpointInfoArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedArray(endpointInfoArr, 0);
                    if (this.mRemote.transact(1, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method onEndpointStarted is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IEndpointCallback
            public void onEndpointStopped(EndpointId[] endpointIdArr, byte b) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedArray(endpointIdArr, 0);
                    obtain.writeByte(b);
                    if (this.mRemote.transact(2, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method onEndpointStopped is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IEndpointCallback
            public void onMessageReceived(int i, Message message) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(message, 0);
                    if (this.mRemote.transact(3, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method onMessageReceived is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IEndpointCallback
            public void onMessageDeliveryStatusReceived(int i, MessageDeliveryStatus messageDeliveryStatus) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(messageDeliveryStatus, 0);
                    if (this.mRemote.transact(4, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method onMessageDeliveryStatusReceived is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IEndpointCallback
            public void onEndpointSessionOpenRequest(int i, EndpointId endpointId, EndpointId endpointId2, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(endpointId, 0);
                    obtain.writeTypedObject(endpointId2, 0);
                    obtain.writeString(str);
                    if (this.mRemote.transact(5, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method onEndpointSessionOpenRequest is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IEndpointCallback
            public void onCloseEndpointSession(int i, byte b) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByte(b);
                    if (this.mRemote.transact(6, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method onCloseEndpointSession is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IEndpointCallback
            public void onEndpointSessionOpenComplete(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(7, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method onEndpointSessionOpenComplete is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IEndpointCallback
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

            @Override // android.hardware.contexthub.IEndpointCallback
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
