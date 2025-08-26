package android.hardware.location;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IContextHubClientCallback extends IInterface {

    public static class Default implements IContextHubClientCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.location.IContextHubClientCallback
        public void onClientAuthorizationChanged(long j, int i) throws RemoteException {
        }

        @Override // android.hardware.location.IContextHubClientCallback
        public void onHubReset() throws RemoteException {
        }

        @Override // android.hardware.location.IContextHubClientCallback
        public void onMessageFromNanoApp(NanoAppMessage nanoAppMessage) throws RemoteException {
        }

        @Override // android.hardware.location.IContextHubClientCallback
        public void onNanoAppAborted(long j, int i) throws RemoteException {
        }

        @Override // android.hardware.location.IContextHubClientCallback
        public void onNanoAppDisabled(long j) throws RemoteException {
        }

        @Override // android.hardware.location.IContextHubClientCallback
        public void onNanoAppEnabled(long j) throws RemoteException {
        }

        @Override // android.hardware.location.IContextHubClientCallback
        public void onNanoAppLoaded(long j) throws RemoteException {
        }

        @Override // android.hardware.location.IContextHubClientCallback
        public void onNanoAppUnloaded(long j) throws RemoteException {
        }
    }

    void onClientAuthorizationChanged(long j, int i) throws RemoteException;

    void onHubReset() throws RemoteException;

    void onMessageFromNanoApp(NanoAppMessage nanoAppMessage) throws RemoteException;

    void onNanoAppAborted(long j, int i) throws RemoteException;

    void onNanoAppDisabled(long j) throws RemoteException;

    void onNanoAppEnabled(long j) throws RemoteException;

    void onNanoAppLoaded(long j) throws RemoteException;

    void onNanoAppUnloaded(long j) throws RemoteException;

    public static abstract class Stub extends Binder implements IContextHubClientCallback {
        public static final String DESCRIPTOR = "android.hardware.location.IContextHubClientCallback";
        static final int TRANSACTION_onClientAuthorizationChanged = 8;
        static final int TRANSACTION_onHubReset = 2;
        static final int TRANSACTION_onMessageFromNanoApp = 1;
        static final int TRANSACTION_onNanoAppAborted = 3;
        static final int TRANSACTION_onNanoAppDisabled = 7;
        static final int TRANSACTION_onNanoAppEnabled = 6;
        static final int TRANSACTION_onNanoAppLoaded = 4;
        static final int TRANSACTION_onNanoAppUnloaded = 5;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 7;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IContextHubClientCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IContextHubClientCallback)) {
                return (IContextHubClientCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onMessageFromNanoApp";
                case 2:
                    return "onHubReset";
                case 3:
                    return "onNanoAppAborted";
                case 4:
                    return "onNanoAppLoaded";
                case 5:
                    return "onNanoAppUnloaded";
                case 6:
                    return "onNanoAppEnabled";
                case 7:
                    return "onNanoAppDisabled";
                case 8:
                    return "onClientAuthorizationChanged";
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
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    NanoAppMessage nanoAppMessage = (NanoAppMessage) parcel.readTypedObject(NanoAppMessage.CREATOR);
                    parcel.enforceNoDataAvail();
                    onMessageFromNanoApp(nanoAppMessage);
                    return true;
                case 2:
                    onHubReset();
                    return true;
                case 3:
                    long j = parcel.readLong();
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onNanoAppAborted(j, i3);
                    return true;
                case 4:
                    long j2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    onNanoAppLoaded(j2);
                    return true;
                case 5:
                    long j3 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    onNanoAppUnloaded(j3);
                    return true;
                case 6:
                    long j4 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    onNanoAppEnabled(j4);
                    return true;
                case 7:
                    long j5 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    onNanoAppDisabled(j5);
                    return true;
                case 8:
                    long j6 = parcel.readLong();
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onClientAuthorizationChanged(j6, i4);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IContextHubClientCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.hardware.location.IContextHubClientCallback
            public void onMessageFromNanoApp(NanoAppMessage nanoAppMessage) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(nanoAppMessage, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubClientCallback
            public void onHubReset() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubClientCallback
            public void onNanoAppAborted(long j, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubClientCallback
            public void onNanoAppLoaded(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubClientCallback
            public void onNanoAppUnloaded(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubClientCallback
            public void onNanoAppEnabled(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubClientCallback
            public void onNanoAppDisabled(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubClientCallback
            public void onClientAuthorizationChanged(long j, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
