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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IMediaRoute2ProviderService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IMediaRoute2ProviderService)) {
                return (IMediaRoute2ProviderService) queryLocalInterface;
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
                    IMediaRoute2ProviderServiceCallback asInterface = IMediaRoute2ProviderServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setCallback(asInterface);
                    return true;
                case 2:
                    RouteDiscoveryPreference routeDiscoveryPreference = (RouteDiscoveryPreference) parcel.readTypedObject(RouteDiscoveryPreference.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateDiscoveryPreference(routeDiscoveryPreference);
                    return true;
                case 3:
                    long readLong = parcel.readLong();
                    String readString = parcel.readString();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setRouteVolume(readLong, readString, readInt);
                    return true;
                case 4:
                    long readLong2 = parcel.readLong();
                    String readString2 = parcel.readString();
                    String readString3 = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestCreateSession(readLong2, readString2, readString3, bundle);
                    return true;
                case 5:
                    long readLong3 = parcel.readLong();
                    int readInt2 = parcel.readInt();
                    String readString4 = parcel.readString();
                    String readString5 = parcel.readString();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestCreateSystemMediaSession(readLong3, readInt2, readString4, readString5, bundle2);
                    return true;
                case 6:
                    long readLong4 = parcel.readLong();
                    String readString6 = parcel.readString();
                    String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    selectRoute(readLong4, readString6, readString7);
                    return true;
                case 7:
                    long readLong5 = parcel.readLong();
                    String readString8 = parcel.readString();
                    String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    deselectRoute(readLong5, readString8, readString9);
                    return true;
                case 8:
                    long readLong6 = parcel.readLong();
                    String readString10 = parcel.readString();
                    String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    transferToRoute(readLong6, readString10, readString11);
                    return true;
                case 9:
                    long readLong7 = parcel.readLong();
                    String readString12 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setSessionVolume(readLong7, readString12, readInt3);
                    return true;
                case 10:
                    long readLong8 = parcel.readLong();
                    String readString13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    releaseSession(readLong8, readString13);
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
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IMediaRoute2ProviderService.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRoute2ProviderServiceCallback);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRoute2ProviderService
            public void updateDiscoveryPreference(RouteDiscoveryPreference routeDiscoveryPreference) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IMediaRoute2ProviderService.DESCRIPTOR);
                    obtain.writeTypedObject(routeDiscoveryPreference, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRoute2ProviderService
            public void setRouteVolume(long j, String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IMediaRoute2ProviderService.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRoute2ProviderService
            public void requestCreateSession(long j, String str, String str2, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IMediaRoute2ProviderService.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRoute2ProviderService
            public void requestCreateSystemMediaSession(long j, int i, String str, String str2, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IMediaRoute2ProviderService.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRoute2ProviderService
            public void selectRoute(long j, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IMediaRoute2ProviderService.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRoute2ProviderService
            public void deselectRoute(long j, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IMediaRoute2ProviderService.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRoute2ProviderService
            public void transferToRoute(long j, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IMediaRoute2ProviderService.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRoute2ProviderService
            public void setSessionVolume(long j, String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IMediaRoute2ProviderService.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRoute2ProviderService
            public void releaseSession(long j, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IMediaRoute2ProviderService.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
