package android.hardware.contexthub;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IContextHubCallback extends IInterface {
    public static final int CONTEXTHUB_NAN_TRANSACTION_TIMEOUT_MS = 10000;
    public static final String DESCRIPTOR = "android$hardware$contexthub$IContextHubCallback".replace('$', '.');
    public static final String HASH = "df80fdbb6f95a8a2988bc72b7f08f891847b80eb";
    public static final int VERSION = 4;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    String getName() throws RemoteException;

    byte[] getUuid() throws RemoteException;

    void handleContextHubAsyncEvent(int i) throws RemoteException;

    void handleContextHubMessage(ContextHubMessage contextHubMessage, String[] strArr) throws RemoteException;

    void handleMessageDeliveryStatus(char c, MessageDeliveryStatus messageDeliveryStatus) throws RemoteException;

    void handleNanSessionRequest(NanSessionRequest nanSessionRequest) throws RemoteException;

    void handleNanoappInfo(NanoappInfo[] nanoappInfoArr) throws RemoteException;

    void handleTransactionResult(int i, boolean z) throws RemoteException;

    public static class Default implements IContextHubCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.contexthub.IContextHubCallback
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.contexthub.IContextHubCallback
        public String getName() throws RemoteException {
            return null;
        }

        @Override // android.hardware.contexthub.IContextHubCallback
        public byte[] getUuid() throws RemoteException {
            return null;
        }

        @Override // android.hardware.contexthub.IContextHubCallback
        public void handleContextHubAsyncEvent(int i) throws RemoteException {
        }

        @Override // android.hardware.contexthub.IContextHubCallback
        public void handleContextHubMessage(ContextHubMessage contextHubMessage, String[] strArr) throws RemoteException {
        }

        @Override // android.hardware.contexthub.IContextHubCallback
        public void handleMessageDeliveryStatus(char c, MessageDeliveryStatus messageDeliveryStatus) throws RemoteException {
        }

        @Override // android.hardware.contexthub.IContextHubCallback
        public void handleNanSessionRequest(NanSessionRequest nanSessionRequest) throws RemoteException {
        }

        @Override // android.hardware.contexthub.IContextHubCallback
        public void handleNanoappInfo(NanoappInfo[] nanoappInfoArr) throws RemoteException {
        }

        @Override // android.hardware.contexthub.IContextHubCallback
        public void handleTransactionResult(int i, boolean z) throws RemoteException {
        }

        @Override // android.hardware.contexthub.IContextHubCallback
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements IContextHubCallback {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getName = 8;
        static final int TRANSACTION_getUuid = 7;
        static final int TRANSACTION_handleContextHubAsyncEvent = 3;
        static final int TRANSACTION_handleContextHubMessage = 2;
        static final int TRANSACTION_handleMessageDeliveryStatus = 6;
        static final int TRANSACTION_handleNanSessionRequest = 5;
        static final int TRANSACTION_handleNanoappInfo = 1;
        static final int TRANSACTION_handleTransactionResult = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IContextHubCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IContextHubCallback)) {
                return (IContextHubCallback) queryLocalInterface;
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
                    NanoappInfo[] nanoappInfoArr = (NanoappInfo[]) parcel.createTypedArray(NanoappInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    handleNanoappInfo(nanoappInfoArr);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    ContextHubMessage contextHubMessage = (ContextHubMessage) parcel.readTypedObject(ContextHubMessage.CREATOR);
                    String[] createStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    handleContextHubMessage(contextHubMessage, createStringArray);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    handleContextHubAsyncEvent(readInt);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int readInt2 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    handleTransactionResult(readInt2, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    NanSessionRequest nanSessionRequest = (NanSessionRequest) parcel.readTypedObject(NanSessionRequest.CREATOR);
                    parcel.enforceNoDataAvail();
                    handleNanSessionRequest(nanSessionRequest);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    char readInt3 = (char) parcel.readInt();
                    MessageDeliveryStatus messageDeliveryStatus = (MessageDeliveryStatus) parcel.readTypedObject(MessageDeliveryStatus.CREATOR);
                    parcel.enforceNoDataAvail();
                    handleMessageDeliveryStatus(readInt3, messageDeliveryStatus);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    byte[] uuid = getUuid();
                    parcel2.writeNoException();
                    parcel2.writeFixedArray(uuid, 1, 16);
                    return true;
                case 8:
                    String name = getName();
                    parcel2.writeNoException();
                    parcel2.writeString(name);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IContextHubCallback {
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

            @Override // android.hardware.contexthub.IContextHubCallback
            public void handleNanoappInfo(NanoappInfo[] nanoappInfoArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedArray(nanoappInfoArr, 0);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new RemoteException("Method handleNanoappInfo is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHubCallback
            public void handleContextHubMessage(ContextHubMessage contextHubMessage, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(contextHubMessage, 0);
                    obtain.writeStringArray(strArr);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new RemoteException("Method handleContextHubMessage is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHubCallback
            public void handleContextHubAsyncEvent(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new RemoteException("Method handleContextHubAsyncEvent is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHubCallback
            public void handleTransactionResult(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new RemoteException("Method handleTransactionResult is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHubCallback
            public void handleNanSessionRequest(NanSessionRequest nanSessionRequest) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(nanSessionRequest, 0);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0)) {
                        throw new RemoteException("Method handleNanSessionRequest is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHubCallback
            public void handleMessageDeliveryStatus(char c, MessageDeliveryStatus messageDeliveryStatus) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(c);
                    obtain.writeTypedObject(messageDeliveryStatus, 0);
                    if (!this.mRemote.transact(6, obtain, obtain2, 0)) {
                        throw new RemoteException("Method handleMessageDeliveryStatus is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHubCallback
            public byte[] getUuid() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(7, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getUuid is unimplemented.");
                    }
                    obtain2.readException();
                    return (byte[]) obtain2.createFixedArray(byte[].class, 16);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHubCallback
            public String getName() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(8, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getName is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHubCallback
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

            @Override // android.hardware.contexthub.IContextHubCallback
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
