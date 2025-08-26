package android.media;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public interface IMediaRouter2 extends IInterface {
    public static final String DESCRIPTOR = "android.media.IMediaRouter2";

    public static class Default implements IMediaRouter2 {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.IMediaRouter2
        public void notifyDeviceSuggestionsUpdated(String str, List<SuggestedDeviceInfo> list) throws RemoteException {
        }

        @Override // android.media.IMediaRouter2
        public void notifyRouterRegistered(List<MediaRoute2Info> list, RoutingSessionInfo routingSessionInfo) throws RemoteException {
        }

        @Override // android.media.IMediaRouter2
        public void notifyRoutesUpdated(List<MediaRoute2Info> list) throws RemoteException {
        }

        @Override // android.media.IMediaRouter2
        public void notifySessionCreated(int i, RoutingSessionInfo routingSessionInfo) throws RemoteException {
        }

        @Override // android.media.IMediaRouter2
        public void notifySessionInfoChanged(RoutingSessionInfo routingSessionInfo) throws RemoteException {
        }

        @Override // android.media.IMediaRouter2
        public void notifySessionReleased(RoutingSessionInfo routingSessionInfo) throws RemoteException {
        }

        @Override // android.media.IMediaRouter2
        public void requestCreateSessionByManager(long j, RoutingSessionInfo routingSessionInfo, MediaRoute2Info mediaRoute2Info) throws RemoteException {
        }
    }

    void notifyDeviceSuggestionsUpdated(String str, List<SuggestedDeviceInfo> list) throws RemoteException;

    void notifyRouterRegistered(List<MediaRoute2Info> list, RoutingSessionInfo routingSessionInfo) throws RemoteException;

    void notifyRoutesUpdated(List<MediaRoute2Info> list) throws RemoteException;

    void notifySessionCreated(int i, RoutingSessionInfo routingSessionInfo) throws RemoteException;

    void notifySessionInfoChanged(RoutingSessionInfo routingSessionInfo) throws RemoteException;

    void notifySessionReleased(RoutingSessionInfo routingSessionInfo) throws RemoteException;

    void requestCreateSessionByManager(long j, RoutingSessionInfo routingSessionInfo, MediaRoute2Info mediaRoute2Info) throws RemoteException;

    public static abstract class Stub extends Binder implements IMediaRouter2 {
        static final int TRANSACTION_notifyDeviceSuggestionsUpdated = 7;
        static final int TRANSACTION_notifyRouterRegistered = 1;
        static final int TRANSACTION_notifyRoutesUpdated = 2;
        static final int TRANSACTION_notifySessionCreated = 3;
        static final int TRANSACTION_notifySessionInfoChanged = 4;
        static final int TRANSACTION_notifySessionReleased = 5;
        static final int TRANSACTION_requestCreateSessionByManager = 6;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 6;
        }

        public Stub() {
            attachInterface(this, IMediaRouter2.DESCRIPTOR);
        }

        public static IMediaRouter2 asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IMediaRouter2.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IMediaRouter2)) {
                return (IMediaRouter2) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "notifyRouterRegistered";
                case 2:
                    return "notifyRoutesUpdated";
                case 3:
                    return "notifySessionCreated";
                case 4:
                    return "notifySessionInfoChanged";
                case 5:
                    return "notifySessionReleased";
                case 6:
                    return "requestCreateSessionByManager";
                case 7:
                    return "notifyDeviceSuggestionsUpdated";
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
                parcel.enforceInterface(IMediaRouter2.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IMediaRouter2.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(MediaRoute2Info.CREATOR);
                    RoutingSessionInfo routingSessionInfo = (RoutingSessionInfo) parcel.readTypedObject(RoutingSessionInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyRouterRegistered(arrayListCreateTypedArrayList, routingSessionInfo);
                    return true;
                case 2:
                    ArrayList arrayListCreateTypedArrayList2 = parcel.createTypedArrayList(MediaRoute2Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyRoutesUpdated(arrayListCreateTypedArrayList2);
                    return true;
                case 3:
                    int i3 = parcel.readInt();
                    RoutingSessionInfo routingSessionInfo2 = (RoutingSessionInfo) parcel.readTypedObject(RoutingSessionInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifySessionCreated(i3, routingSessionInfo2);
                    return true;
                case 4:
                    RoutingSessionInfo routingSessionInfo3 = (RoutingSessionInfo) parcel.readTypedObject(RoutingSessionInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifySessionInfoChanged(routingSessionInfo3);
                    return true;
                case 5:
                    RoutingSessionInfo routingSessionInfo4 = (RoutingSessionInfo) parcel.readTypedObject(RoutingSessionInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifySessionReleased(routingSessionInfo4);
                    return true;
                case 6:
                    long j = parcel.readLong();
                    RoutingSessionInfo routingSessionInfo5 = (RoutingSessionInfo) parcel.readTypedObject(RoutingSessionInfo.CREATOR);
                    MediaRoute2Info mediaRoute2Info = (MediaRoute2Info) parcel.readTypedObject(MediaRoute2Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestCreateSessionByManager(j, routingSessionInfo5, mediaRoute2Info);
                    return true;
                case 7:
                    String string = parcel.readString();
                    ArrayList arrayListCreateTypedArrayList3 = parcel.createTypedArrayList(SuggestedDeviceInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyDeviceSuggestionsUpdated(string, arrayListCreateTypedArrayList3);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IMediaRouter2 {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IMediaRouter2.DESCRIPTOR;
            }

            @Override // android.media.IMediaRouter2
            public void notifyRouterRegistered(List<MediaRoute2Info> list, RoutingSessionInfo routingSessionInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IMediaRouter2.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    parcelObtain.writeTypedObject(routingSessionInfo, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IMediaRouter2
            public void notifyRoutesUpdated(List<MediaRoute2Info> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IMediaRouter2.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IMediaRouter2
            public void notifySessionCreated(int i, RoutingSessionInfo routingSessionInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IMediaRouter2.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(routingSessionInfo, 0);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IMediaRouter2
            public void notifySessionInfoChanged(RoutingSessionInfo routingSessionInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IMediaRouter2.DESCRIPTOR);
                    parcelObtain.writeTypedObject(routingSessionInfo, 0);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IMediaRouter2
            public void notifySessionReleased(RoutingSessionInfo routingSessionInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IMediaRouter2.DESCRIPTOR);
                    parcelObtain.writeTypedObject(routingSessionInfo, 0);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IMediaRouter2
            public void requestCreateSessionByManager(long j, RoutingSessionInfo routingSessionInfo, MediaRoute2Info mediaRoute2Info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IMediaRouter2.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeTypedObject(routingSessionInfo, 0);
                    parcelObtain.writeTypedObject(mediaRoute2Info, 0);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IMediaRouter2
            public void notifyDeviceSuggestionsUpdated(String str, List<SuggestedDeviceInfo> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IMediaRouter2.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
