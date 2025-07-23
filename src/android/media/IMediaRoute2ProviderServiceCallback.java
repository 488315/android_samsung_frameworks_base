package android.media;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public interface IMediaRoute2ProviderServiceCallback extends IInterface {
    public static final String DESCRIPTOR = "android.media.IMediaRoute2ProviderServiceCallback";

    public static class Default implements IMediaRoute2ProviderServiceCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.IMediaRoute2ProviderServiceCallback
        public void notifyProviderUpdated(MediaRoute2ProviderInfo mediaRoute2ProviderInfo) throws RemoteException {
        }

        @Override // android.media.IMediaRoute2ProviderServiceCallback
        public void notifyRequestFailed(long j, int i) throws RemoteException {
        }

        @Override // android.media.IMediaRoute2ProviderServiceCallback
        public void notifySessionCreated(long j, RoutingSessionInfo routingSessionInfo) throws RemoteException {
        }

        @Override // android.media.IMediaRoute2ProviderServiceCallback
        public void notifySessionReleased(RoutingSessionInfo routingSessionInfo) throws RemoteException {
        }

        @Override // android.media.IMediaRoute2ProviderServiceCallback
        public void notifySessionsUpdated(List<RoutingSessionInfo> list) throws RemoteException {
        }
    }

    void notifyProviderUpdated(MediaRoute2ProviderInfo mediaRoute2ProviderInfo) throws RemoteException;

    void notifyRequestFailed(long j, int i) throws RemoteException;

    void notifySessionCreated(long j, RoutingSessionInfo routingSessionInfo) throws RemoteException;

    void notifySessionReleased(RoutingSessionInfo routingSessionInfo) throws RemoteException;

    void notifySessionsUpdated(List<RoutingSessionInfo> list) throws RemoteException;

    public static abstract class Stub extends Binder implements IMediaRoute2ProviderServiceCallback {
        static final int TRANSACTION_notifyProviderUpdated = 1;
        static final int TRANSACTION_notifyRequestFailed = 5;
        static final int TRANSACTION_notifySessionCreated = 2;
        static final int TRANSACTION_notifySessionReleased = 4;
        static final int TRANSACTION_notifySessionsUpdated = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }

        public Stub() {
            attachInterface(this, IMediaRoute2ProviderServiceCallback.DESCRIPTOR);
        }

        public static IMediaRoute2ProviderServiceCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IMediaRoute2ProviderServiceCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IMediaRoute2ProviderServiceCallback)) {
                return (IMediaRoute2ProviderServiceCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "notifyProviderUpdated";
            }
            if (i == 2) {
                return "notifySessionCreated";
            }
            if (i == 3) {
                return "notifySessionsUpdated";
            }
            if (i == 4) {
                return "notifySessionReleased";
            }
            if (i != 5) {
                return null;
            }
            return "notifyRequestFailed";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IMediaRoute2ProviderServiceCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IMediaRoute2ProviderServiceCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                MediaRoute2ProviderInfo mediaRoute2ProviderInfo = (MediaRoute2ProviderInfo) parcel.readTypedObject(MediaRoute2ProviderInfo.CREATOR);
                parcel.enforceNoDataAvail();
                notifyProviderUpdated(mediaRoute2ProviderInfo);
            } else if (i == 2) {
                long readLong = parcel.readLong();
                RoutingSessionInfo routingSessionInfo = (RoutingSessionInfo) parcel.readTypedObject(RoutingSessionInfo.CREATOR);
                parcel.enforceNoDataAvail();
                notifySessionCreated(readLong, routingSessionInfo);
            } else if (i == 3) {
                ArrayList createTypedArrayList = parcel.createTypedArrayList(RoutingSessionInfo.CREATOR);
                parcel.enforceNoDataAvail();
                notifySessionsUpdated(createTypedArrayList);
            } else if (i == 4) {
                RoutingSessionInfo routingSessionInfo2 = (RoutingSessionInfo) parcel.readTypedObject(RoutingSessionInfo.CREATOR);
                parcel.enforceNoDataAvail();
                notifySessionReleased(routingSessionInfo2);
            } else if (i == 5) {
                long readLong2 = parcel.readLong();
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                notifyRequestFailed(readLong2, readInt);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IMediaRoute2ProviderServiceCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IMediaRoute2ProviderServiceCallback.DESCRIPTOR;
            }

            @Override // android.media.IMediaRoute2ProviderServiceCallback
            public void notifyProviderUpdated(MediaRoute2ProviderInfo mediaRoute2ProviderInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IMediaRoute2ProviderServiceCallback.DESCRIPTOR);
                    obtain.writeTypedObject(mediaRoute2ProviderInfo, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRoute2ProviderServiceCallback
            public void notifySessionCreated(long j, RoutingSessionInfo routingSessionInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IMediaRoute2ProviderServiceCallback.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeTypedObject(routingSessionInfo, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRoute2ProviderServiceCallback
            public void notifySessionsUpdated(List<RoutingSessionInfo> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IMediaRoute2ProviderServiceCallback.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRoute2ProviderServiceCallback
            public void notifySessionReleased(RoutingSessionInfo routingSessionInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IMediaRoute2ProviderServiceCallback.DESCRIPTOR);
                    obtain.writeTypedObject(routingSessionInfo, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRoute2ProviderServiceCallback
            public void notifyRequestFailed(long j, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IMediaRoute2ProviderServiceCallback.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
