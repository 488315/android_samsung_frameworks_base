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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IContextHubCallback)) {
                return (IContextHubCallback) iInterfaceQueryLocalInterface;
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
                    String[] strArrCreateStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    handleContextHubMessage(contextHubMessage, strArrCreateStringArray);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    handleContextHubAsyncEvent(i3);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int i4 = parcel.readInt();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    handleTransactionResult(i4, z);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    NanSessionRequest nanSessionRequest = (NanSessionRequest) parcel.readTypedObject(NanSessionRequest.CREATOR);
                    parcel.enforceNoDataAvail();
                    handleNanSessionRequest(nanSessionRequest);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    char c = (char) parcel.readInt();
                    MessageDeliveryStatus messageDeliveryStatus = (MessageDeliveryStatus) parcel.readTypedObject(MessageDeliveryStatus.CREATOR);
                    parcel.enforceNoDataAvail();
                    handleMessageDeliveryStatus(c, messageDeliveryStatus);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedArray(nanoappInfoArr, 0);
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method handleNanoappInfo is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHubCallback
            public void handleContextHubMessage(ContextHubMessage contextHubMessage, String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextHubMessage, 0);
                    parcelObtain.writeStringArray(strArr);
                    if (!this.mRemote.transact(2, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method handleContextHubMessage is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHubCallback
            public void handleContextHubAsyncEvent(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (!this.mRemote.transact(3, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method handleContextHubAsyncEvent is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHubCallback
            public void handleTransactionResult(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    if (!this.mRemote.transact(4, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method handleTransactionResult is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHubCallback
            public void handleNanSessionRequest(NanSessionRequest nanSessionRequest) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(nanSessionRequest, 0);
                    if (!this.mRemote.transact(5, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method handleNanSessionRequest is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHubCallback
            public void handleMessageDeliveryStatus(char c, MessageDeliveryStatus messageDeliveryStatus) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(c);
                    parcelObtain.writeTypedObject(messageDeliveryStatus, 0);
                    if (!this.mRemote.transact(6, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method handleMessageDeliveryStatus is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHubCallback
            public byte[] getUuid() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(7, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method getUuid is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return (byte[]) parcelObtain2.createFixedArray(byte[].class, 16);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHubCallback
            public String getName() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(8, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method getName is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHubCallback
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

            @Override // android.hardware.contexthub.IContextHubCallback
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
