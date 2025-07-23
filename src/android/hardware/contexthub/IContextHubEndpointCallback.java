package android.hardware.contexthub;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IContextHubEndpointCallback extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.contexthub.IContextHubEndpointCallback";

    public static class Default implements IContextHubEndpointCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.contexthub.IContextHubEndpointCallback
        public void onMessageReceived(int i, HubMessage hubMessage) throws RemoteException {
        }

        @Override // android.hardware.contexthub.IContextHubEndpointCallback
        public void onSessionClosed(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.contexthub.IContextHubEndpointCallback
        public void onSessionOpenComplete(int i) throws RemoteException {
        }

        @Override // android.hardware.contexthub.IContextHubEndpointCallback
        public void onSessionOpenRequest(int i, HubEndpointInfo hubEndpointInfo, String str) throws RemoteException {
        }
    }

    void onMessageReceived(int i, HubMessage hubMessage) throws RemoteException;

    void onSessionClosed(int i, int i2) throws RemoteException;

    void onSessionOpenComplete(int i) throws RemoteException;

    void onSessionOpenRequest(int i, HubEndpointInfo hubEndpointInfo, String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IContextHubEndpointCallback {
        static final int TRANSACTION_onMessageReceived = 4;
        static final int TRANSACTION_onSessionClosed = 2;
        static final int TRANSACTION_onSessionOpenComplete = 3;
        static final int TRANSACTION_onSessionOpenRequest = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, IContextHubEndpointCallback.DESCRIPTOR);
        }

        public static IContextHubEndpointCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IContextHubEndpointCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IContextHubEndpointCallback)) {
                return (IContextHubEndpointCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onSessionOpenRequest";
            }
            if (i == 2) {
                return "onSessionClosed";
            }
            if (i == 3) {
                return "onSessionOpenComplete";
            }
            if (i != 4) {
                return null;
            }
            return "onMessageReceived";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IContextHubEndpointCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IContextHubEndpointCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                HubEndpointInfo hubEndpointInfo = (HubEndpointInfo) parcel.readTypedObject(HubEndpointInfo.CREATOR);
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                onSessionOpenRequest(readInt, hubEndpointInfo, readString);
            } else if (i == 2) {
                int readInt2 = parcel.readInt();
                int readInt3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onSessionClosed(readInt2, readInt3);
            } else if (i == 3) {
                int readInt4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onSessionOpenComplete(readInt4);
            } else if (i == 4) {
                int readInt5 = parcel.readInt();
                HubMessage hubMessage = (HubMessage) parcel.readTypedObject(HubMessage.CREATOR);
                parcel.enforceNoDataAvail();
                onMessageReceived(readInt5, hubMessage);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IContextHubEndpointCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IContextHubEndpointCallback.DESCRIPTOR;
            }

            @Override // android.hardware.contexthub.IContextHubEndpointCallback
            public void onSessionOpenRequest(int i, HubEndpointInfo hubEndpointInfo, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IContextHubEndpointCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(hubEndpointInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHubEndpointCallback
            public void onSessionClosed(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IContextHubEndpointCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHubEndpointCallback
            public void onSessionOpenComplete(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IContextHubEndpointCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHubEndpointCallback
            public void onMessageReceived(int i, HubMessage hubMessage) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IContextHubEndpointCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(hubMessage, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
