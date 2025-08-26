package android.media;

import android.media.IMediaRoute2ProviderServiceCallback;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IMediaRoute2ProviderService extends IInterface {
    public static final String DESCRIPTOR = "android.media.IMediaRoute2ProviderService";

    public static class Default implements IMediaRoute2ProviderService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.IMediaRoute2ProviderService
        public void deselectRoute(long j, String str, String str2) throws RemoteException {
        }

        @Override // android.media.IMediaRoute2ProviderService
        public void releaseSession(long j, String str) throws RemoteException {
        }

        @Override // android.media.IMediaRoute2ProviderService
        public void requestCreateSession(long j, String str, String str2, Bundle bundle) throws RemoteException {
        }

        @Override // android.media.IMediaRoute2ProviderService
        public void requestCreateSystemMediaSession(long j, int i, String str, String str2, Bundle bundle) throws RemoteException {
        }

        @Override // android.media.IMediaRoute2ProviderService
        public void selectRoute(long j, String str, String str2) throws RemoteException {
        }

        @Override // android.media.IMediaRoute2ProviderService
        public void setCallback(IMediaRoute2ProviderServiceCallback iMediaRoute2ProviderServiceCallback) throws RemoteException {
        }

        @Override // android.media.IMediaRoute2ProviderService
        public void setRouteVolume(long j, String str, int i) throws RemoteException {
        }

        @Override // android.media.IMediaRoute2ProviderService
        public void setSessionVolume(long j, String str, int i) throws RemoteException {
        }

        @Override // android.media.IMediaRoute2ProviderService
        public void transferToRoute(long j, String str, String str2) throws RemoteException {
        }

        @Override // android.media.IMediaRoute2ProviderService
        public void updateDiscoveryPreference(RouteDiscoveryPreference routeDiscoveryPreference) throws RemoteException {
        }
    }

    void deselectRoute(long j, String str, String str2) throws RemoteException;

    void releaseSession(long j, String str) throws RemoteException;

    void requestCreateSession(long j, String str, String str2, Bundle bundle) throws RemoteException;

    void requestCreateSystemMediaSession(long j, int i, String str, String str2, Bundle bundle) throws RemoteException;

    void selectRoute(long j, String str, String str2) throws RemoteException;

    void setCallback(IMediaRoute2ProviderServiceCallback iMediaRoute2ProviderServiceCallback) throws RemoteException;

    void setRouteVolume(long j, String str, int i) throws RemoteException;

    void setSessionVolume(long j, String str, int i) throws RemoteException;

    void transferToRoute(long j, String str, String str2) throws RemoteException;

    void updateDiscoveryPreference(RouteDiscoveryPreference routeDiscoveryPreference) throws RemoteException;

    public static abstract class Stub extends Binder implements IMediaRoute2ProviderService {
        static final int TRANSACTION_deselectRoute = 7;
        static final int TRANSACTION_releaseSession = 10;
        static final int TRANSACTION_requestCreateSession = 4;
        static final int TRANSACTION_requestCreateSystemMediaSession = 5;
        static final int TRANSACTION_selectRoute = 6;
        static final int TRANSACTION_setCallback = 1;
        static final int TRANSACTION_setRouteVolume = 3;
        static final int TRANSACTION_setSessionVolume = 9;
        static final int TRANSACTION_transferToRoute = 8;
        static final int TRANSACTION_updateDiscoveryPreference = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 9;
        }

        public Stub() {
            attachInterface(this, IMediaRoute2ProviderService.DESCRIPTOR);
        }

        public static IMediaRoute2ProviderService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IMediaRoute2ProviderService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IMediaRoute2ProviderService)) {
                return (IMediaRoute2ProviderService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setCallback";
                case 2:
                    return "updateDiscoveryPreference";
                case 3:
                    return "setRouteVolume";
                case 4:
                    return "requestCreateSession";
                case 5:
                    return "requestCreateSystemMediaSession";
                case 6:
                    return "selectRoute";
                case 7:
                    return "deselectRoute";
                case 8:
                    return "transferToRoute";
                case 9:
                    return "setSessionVolume";
                case 10:
                    return "releaseSession";
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
                parcel.enforceInterface(IMediaRoute2ProviderService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IMediaRoute2ProviderService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    IMediaRoute2ProviderServiceCallback iMediaRoute2ProviderServiceCallbackAsInterface = IMediaRoute2ProviderServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setCallback(iMediaRoute2ProviderServiceCallbackAsInterface);
                    return true;
                case 2:
                    RouteDiscoveryPreference routeDiscoveryPreference = (RouteDiscoveryPreference) parcel.readTypedObject(RouteDiscoveryPreference.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateDiscoveryPreference(routeDiscoveryPreference);
                    return true;
                case 3:
                    long j = parcel.readLong();
                    String string = parcel.readString();
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setRouteVolume(j, string, i3);
                    return true;
                case 4:
                    long j2 = parcel.readLong();
                    String string2 = parcel.readString();
                    String string3 = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestCreateSession(j2, string2, string3, bundle);
                    return true;
                case 5:
                    long j3 = parcel.readLong();
                    int i4 = parcel.readInt();
                    String string4 = parcel.readString();
                    String string5 = parcel.readString();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestCreateSystemMediaSession(j3, i4, string4, string5, bundle2);
                    return true;
                case 6:
                    long j4 = parcel.readLong();
                    String string6 = parcel.readString();
                    String string7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    selectRoute(j4, string6, string7);
                    return true;
                case 7:
                    long j5 = parcel.readLong();
                    String string8 = parcel.readString();
                    String string9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    deselectRoute(j5, string8, string9);
                    return true;
                case 8:
                    long j6 = parcel.readLong();
                    String string10 = parcel.readString();
                    String string11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    transferToRoute(j6, string10, string11);
                    return true;
                case 9:
                    long j7 = parcel.readLong();
                    String string12 = parcel.readString();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setSessionVolume(j7, string12, i5);
                    return true;
                case 10:
                    long j8 = parcel.readLong();
                    String string13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    releaseSession(j8, string13);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IMediaRoute2ProviderService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IMediaRoute2ProviderService.DESCRIPTOR;
            }

            @Override // android.media.IMediaRoute2ProviderService
            public void setCallback(IMediaRoute2ProviderServiceCallback iMediaRoute2ProviderServiceCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IMediaRoute2ProviderService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iMediaRoute2ProviderServiceCallback);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IMediaRoute2ProviderService
            public void updateDiscoveryPreference(RouteDiscoveryPreference routeDiscoveryPreference) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IMediaRoute2ProviderService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(routeDiscoveryPreference, 0);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IMediaRoute2ProviderService
            public void setRouteVolume(long j, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IMediaRoute2ProviderService.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IMediaRoute2ProviderService
            public void requestCreateSession(long j, String str, String str2, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IMediaRoute2ProviderService.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IMediaRoute2ProviderService
            public void requestCreateSystemMediaSession(long j, int i, String str, String str2, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IMediaRoute2ProviderService.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IMediaRoute2ProviderService
            public void selectRoute(long j, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IMediaRoute2ProviderService.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IMediaRoute2ProviderService
            public void deselectRoute(long j, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IMediaRoute2ProviderService.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IMediaRoute2ProviderService
            public void transferToRoute(long j, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IMediaRoute2ProviderService.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IMediaRoute2ProviderService
            public void setSessionVolume(long j, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IMediaRoute2ProviderService.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IMediaRoute2ProviderService
            public void releaseSession(long j, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IMediaRoute2ProviderService.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(10, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
