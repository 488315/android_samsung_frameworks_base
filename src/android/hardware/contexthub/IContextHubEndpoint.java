package android.hardware.contexthub;

import android.Manifest;
import android.app.ActivityThread;
import android.graphics.rendererpolicy.ScpmApiContract;
import android.hardware.location.IContextHubTransactionCallback;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PermissionEnforcer;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IContextHubEndpoint extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.contexthub.IContextHubEndpoint";

    public static class Default implements IContextHubEndpoint {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.contexthub.IContextHubEndpoint
        public void closeSession(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.contexthub.IContextHubEndpoint
        public HubEndpointInfo getAssignedHubEndpointInfo() throws RemoteException {
            return null;
        }

        @Override // android.hardware.contexthub.IContextHubEndpoint
        public void onCallbackFinished() throws RemoteException {
        }

        @Override // android.hardware.contexthub.IContextHubEndpoint
        public int openSession(HubEndpointInfo hubEndpointInfo, String str) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.contexthub.IContextHubEndpoint
        public void openSessionRequestComplete(int i) throws RemoteException {
        }

        @Override // android.hardware.contexthub.IContextHubEndpoint
        public void sendMessage(int i, HubMessage hubMessage, IContextHubTransactionCallback iContextHubTransactionCallback) throws RemoteException {
        }

        @Override // android.hardware.contexthub.IContextHubEndpoint
        public void sendMessageDeliveryStatus(int i, int i2, byte b) throws RemoteException {
        }

        @Override // android.hardware.contexthub.IContextHubEndpoint
        public void unregister() throws RemoteException {
        }
    }

    void closeSession(int i, int i2) throws RemoteException;

    HubEndpointInfo getAssignedHubEndpointInfo() throws RemoteException;

    void onCallbackFinished() throws RemoteException;

    int openSession(HubEndpointInfo hubEndpointInfo, String str) throws RemoteException;

    void openSessionRequestComplete(int i) throws RemoteException;

    void sendMessage(int i, HubMessage hubMessage, IContextHubTransactionCallback iContextHubTransactionCallback) throws RemoteException;

    void sendMessageDeliveryStatus(int i, int i2, byte b) throws RemoteException;

    void unregister() throws RemoteException;

    public static abstract class Stub extends Binder implements IContextHubEndpoint {
        static final int TRANSACTION_closeSession = 3;
        static final int TRANSACTION_getAssignedHubEndpointInfo = 1;
        static final int TRANSACTION_onCallbackFinished = 8;
        static final int TRANSACTION_openSession = 2;
        static final int TRANSACTION_openSessionRequestComplete = 4;
        static final int TRANSACTION_sendMessage = 6;
        static final int TRANSACTION_sendMessageDeliveryStatus = 7;
        static final int TRANSACTION_unregister = 5;
        private final PermissionEnforcer mEnforcer;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 7;
        }

        public Stub(PermissionEnforcer permissionEnforcer) {
            attachInterface(this, IContextHubEndpoint.DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IContextHubEndpoint asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IContextHubEndpoint.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IContextHubEndpoint)) {
                return (IContextHubEndpoint) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getAssignedHubEndpointInfo";
                case 2:
                    return "openSession";
                case 3:
                    return "closeSession";
                case 4:
                    return "openSessionRequestComplete";
                case 5:
                    return ScpmApiContract.Method.UNREGISTER;
                case 6:
                    return "sendMessage";
                case 7:
                    return "sendMessageDeliveryStatus";
                case 8:
                    return "onCallbackFinished";
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
                parcel.enforceInterface(IContextHubEndpoint.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IContextHubEndpoint.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    HubEndpointInfo assignedHubEndpointInfo = getAssignedHubEndpointInfo();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(assignedHubEndpointInfo, 1);
                    return true;
                case 2:
                    HubEndpointInfo hubEndpointInfo = (HubEndpointInfo) parcel.readTypedObject(HubEndpointInfo.CREATOR);
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int openSession = openSession(hubEndpointInfo, readString);
                    parcel2.writeNoException();
                    parcel2.writeInt(openSession);
                    return true;
                case 3:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    closeSession(readInt, readInt2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    openSessionRequestComplete(readInt3);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    unregister();
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int readInt4 = parcel.readInt();
                    HubMessage hubMessage = (HubMessage) parcel.readTypedObject(HubMessage.CREATOR);
                    IContextHubTransactionCallback asInterface = IContextHubTransactionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    sendMessage(readInt4, hubMessage, asInterface);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    byte readByte = parcel.readByte();
                    parcel.enforceNoDataAvail();
                    sendMessageDeliveryStatus(readInt5, readInt6, readByte);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    onCallbackFinished();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IContextHubEndpoint {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IContextHubEndpoint.DESCRIPTOR;
            }

            @Override // android.hardware.contexthub.IContextHubEndpoint
            public HubEndpointInfo getAssignedHubEndpointInfo() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IContextHubEndpoint.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (HubEndpointInfo) obtain2.readTypedObject(HubEndpointInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHubEndpoint
            public int openSession(HubEndpointInfo hubEndpointInfo, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IContextHubEndpoint.DESCRIPTOR);
                    obtain.writeTypedObject(hubEndpointInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHubEndpoint
            public void closeSession(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IContextHubEndpoint.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHubEndpoint
            public void openSessionRequestComplete(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IContextHubEndpoint.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHubEndpoint
            public void unregister() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IContextHubEndpoint.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHubEndpoint
            public void sendMessage(int i, HubMessage hubMessage, IContextHubTransactionCallback iContextHubTransactionCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IContextHubEndpoint.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(hubMessage, 0);
                    obtain.writeStrongInterface(iContextHubTransactionCallback);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHubEndpoint
            public void sendMessageDeliveryStatus(int i, int i2, byte b) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IContextHubEndpoint.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeByte(b);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHubEndpoint
            public void onCallbackFinished() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IContextHubEndpoint.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        protected void openSession_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCESS_CONTEXT_HUB, getCallingPid(), getCallingUid());
        }

        protected void closeSession_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCESS_CONTEXT_HUB, getCallingPid(), getCallingUid());
        }

        protected void openSessionRequestComplete_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCESS_CONTEXT_HUB, getCallingPid(), getCallingUid());
        }

        protected void unregister_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCESS_CONTEXT_HUB, getCallingPid(), getCallingUid());
        }

        protected void sendMessage_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCESS_CONTEXT_HUB, getCallingPid(), getCallingUid());
        }

        protected void sendMessageDeliveryStatus_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCESS_CONTEXT_HUB, getCallingPid(), getCallingUid());
        }

        protected void onCallbackFinished_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCESS_CONTEXT_HUB, getCallingPid(), getCallingUid());
        }
    }
}
