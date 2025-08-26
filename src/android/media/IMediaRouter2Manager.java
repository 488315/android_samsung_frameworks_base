package android.media;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public interface IMediaRouter2Manager extends IInterface {
    public static final String DESCRIPTOR = "android.media.IMediaRouter2Manager";

    public static class Default implements IMediaRouter2Manager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.IMediaRouter2Manager
        public void invalidateInstance() throws RemoteException {
        }

        @Override // android.media.IMediaRouter2Manager
        public void notifyDeviceSuggestionsUpdated(String str, String str2, List<SuggestedDeviceInfo> list) throws RemoteException {
        }

        @Override // android.media.IMediaRouter2Manager
        public void notifyDiscoveryPreferenceChanged(String str, RouteDiscoveryPreference routeDiscoveryPreference) throws RemoteException {
        }

        @Override // android.media.IMediaRouter2Manager
        public void notifyRequestFailed(int i, int i2) throws RemoteException {
        }

        @Override // android.media.IMediaRouter2Manager
        public void notifyRouteListingPreferenceChange(String str, RouteListingPreference routeListingPreference) throws RemoteException {
        }

        @Override // android.media.IMediaRouter2Manager
        public void notifyRoutesUpdated(List<MediaRoute2Info> list) throws RemoteException {
        }

        @Override // android.media.IMediaRouter2Manager
        public void notifySessionCreated(int i, RoutingSessionInfo routingSessionInfo) throws RemoteException {
        }

        @Override // android.media.IMediaRouter2Manager
        public void notifySessionReleased(RoutingSessionInfo routingSessionInfo) throws RemoteException {
        }

        @Override // android.media.IMediaRouter2Manager
        public void notifySessionUpdated(RoutingSessionInfo routingSessionInfo) throws RemoteException {
        }
    }

    void invalidateInstance() throws RemoteException;

    void notifyDeviceSuggestionsUpdated(String str, String str2, List<SuggestedDeviceInfo> list) throws RemoteException;

    void notifyDiscoveryPreferenceChanged(String str, RouteDiscoveryPreference routeDiscoveryPreference) throws RemoteException;

    void notifyRequestFailed(int i, int i2) throws RemoteException;

    void notifyRouteListingPreferenceChange(String str, RouteListingPreference routeListingPreference) throws RemoteException;

    void notifyRoutesUpdated(List<MediaRoute2Info> list) throws RemoteException;

    void notifySessionCreated(int i, RoutingSessionInfo routingSessionInfo) throws RemoteException;

    void notifySessionReleased(RoutingSessionInfo routingSessionInfo) throws RemoteException;

    void notifySessionUpdated(RoutingSessionInfo routingSessionInfo) throws RemoteException;

    public static abstract class Stub extends Binder implements IMediaRouter2Manager {
        static final int TRANSACTION_invalidateInstance = 9;
        static final int TRANSACTION_notifyDeviceSuggestionsUpdated = 6;
        static final int TRANSACTION_notifyDiscoveryPreferenceChanged = 4;
        static final int TRANSACTION_notifyRequestFailed = 8;
        static final int TRANSACTION_notifyRouteListingPreferenceChange = 5;
        static final int TRANSACTION_notifyRoutesUpdated = 7;
        static final int TRANSACTION_notifySessionCreated = 1;
        static final int TRANSACTION_notifySessionReleased = 3;
        static final int TRANSACTION_notifySessionUpdated = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 8;
        }

        public Stub() {
            attachInterface(this, IMediaRouter2Manager.DESCRIPTOR);
        }

        public static IMediaRouter2Manager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IMediaRouter2Manager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IMediaRouter2Manager)) {
                return (IMediaRouter2Manager) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "notifySessionCreated";
                case 2:
                    return "notifySessionUpdated";
                case 3:
                    return "notifySessionReleased";
                case 4:
                    return "notifyDiscoveryPreferenceChanged";
                case 5:
                    return "notifyRouteListingPreferenceChange";
                case 6:
                    return "notifyDeviceSuggestionsUpdated";
                case 7:
                    return "notifyRoutesUpdated";
                case 8:
                    return "notifyRequestFailed";
                case 9:
                    return "invalidateInstance";
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
                parcel.enforceInterface(IMediaRouter2Manager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IMediaRouter2Manager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int i3 = parcel.readInt();
                    RoutingSessionInfo routingSessionInfo = (RoutingSessionInfo) parcel.readTypedObject(RoutingSessionInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifySessionCreated(i3, routingSessionInfo);
                    return true;
                case 2:
                    RoutingSessionInfo routingSessionInfo2 = (RoutingSessionInfo) parcel.readTypedObject(RoutingSessionInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifySessionUpdated(routingSessionInfo2);
                    return true;
                case 3:
                    RoutingSessionInfo routingSessionInfo3 = (RoutingSessionInfo) parcel.readTypedObject(RoutingSessionInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifySessionReleased(routingSessionInfo3);
                    return true;
                case 4:
                    String string = parcel.readString();
                    RouteDiscoveryPreference routeDiscoveryPreference = (RouteDiscoveryPreference) parcel.readTypedObject(RouteDiscoveryPreference.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyDiscoveryPreferenceChanged(string, routeDiscoveryPreference);
                    return true;
                case 5:
                    String string2 = parcel.readString();
                    RouteListingPreference routeListingPreference = (RouteListingPreference) parcel.readTypedObject(RouteListingPreference.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyRouteListingPreferenceChange(string2, routeListingPreference);
                    return true;
                case 6:
                    String string3 = parcel.readString();
                    String string4 = parcel.readString();
                    ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(SuggestedDeviceInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyDeviceSuggestionsUpdated(string3, string4, arrayListCreateTypedArrayList);
                    return true;
                case 7:
                    ArrayList arrayListCreateTypedArrayList2 = parcel.createTypedArrayList(MediaRoute2Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyRoutesUpdated(arrayListCreateTypedArrayList2);
                    return true;
                case 8:
                    int i4 = parcel.readInt();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyRequestFailed(i4, i5);
                    return true;
                case 9:
                    invalidateInstance();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IMediaRouter2Manager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IMediaRouter2Manager.DESCRIPTOR;
            }

            @Override // android.media.IMediaRouter2Manager
            public void notifySessionCreated(int i, RoutingSessionInfo routingSessionInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IMediaRouter2Manager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(routingSessionInfo, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IMediaRouter2Manager
            public void notifySessionUpdated(RoutingSessionInfo routingSessionInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IMediaRouter2Manager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(routingSessionInfo, 0);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IMediaRouter2Manager
            public void notifySessionReleased(RoutingSessionInfo routingSessionInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IMediaRouter2Manager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(routingSessionInfo, 0);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IMediaRouter2Manager
            public void notifyDiscoveryPreferenceChanged(String str, RouteDiscoveryPreference routeDiscoveryPreference) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IMediaRouter2Manager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(routeDiscoveryPreference, 0);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IMediaRouter2Manager
            public void notifyRouteListingPreferenceChange(String str, RouteListingPreference routeListingPreference) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IMediaRouter2Manager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(routeListingPreference, 0);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IMediaRouter2Manager
            public void notifyDeviceSuggestionsUpdated(String str, String str2, List<SuggestedDeviceInfo> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IMediaRouter2Manager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IMediaRouter2Manager
            public void notifyRoutesUpdated(List<MediaRoute2Info> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IMediaRouter2Manager.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IMediaRouter2Manager
            public void notifyRequestFailed(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IMediaRouter2Manager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IMediaRouter2Manager
            public void invalidateInstance() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IMediaRouter2Manager.DESCRIPTOR);
                    this.mRemote.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
