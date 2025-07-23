package android.service.media;

import android.content.pm.ParceledListSlice;
import android.media.session.MediaSession;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IMediaBrowserServiceCallbacks extends IInterface {

    public static class Default implements IMediaBrowserServiceCallbacks {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.media.IMediaBrowserServiceCallbacks
        public void onConnect(String str, MediaSession.Token token, Bundle bundle) throws RemoteException {
        }

        @Override // android.service.media.IMediaBrowserServiceCallbacks
        public void onConnectFailed() throws RemoteException {
        }

        @Override // android.service.media.IMediaBrowserServiceCallbacks
        public void onDisconnect() throws RemoteException {
        }

        @Override // android.service.media.IMediaBrowserServiceCallbacks
        public void onLoadChildren(String str, ParceledListSlice parceledListSlice, Bundle bundle) throws RemoteException {
        }
    }

    void onConnect(String str, MediaSession.Token token, Bundle bundle) throws RemoteException;

    void onConnectFailed() throws RemoteException;

    void onDisconnect() throws RemoteException;

    void onLoadChildren(String str, ParceledListSlice parceledListSlice, Bundle bundle) throws RemoteException;

    public static abstract class Stub extends Binder implements IMediaBrowserServiceCallbacks {
        public static final String DESCRIPTOR = "android.service.media.IMediaBrowserServiceCallbacks";
        static final int TRANSACTION_onConnect = 1;
        static final int TRANSACTION_onConnectFailed = 2;
        static final int TRANSACTION_onDisconnect = 4;
        static final int TRANSACTION_onLoadChildren = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IMediaBrowserServiceCallbacks asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IMediaBrowserServiceCallbacks)) {
                return (IMediaBrowserServiceCallbacks) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onConnect";
            }
            if (i == 2) {
                return "onConnectFailed";
            }
            if (i == 3) {
                return "onLoadChildren";
            }
            if (i != 4) {
                return null;
            }
            return "onDisconnect";
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
            if (i == 1) {
                String readString = parcel.readString();
                MediaSession.Token token = (MediaSession.Token) parcel.readTypedObject(MediaSession.Token.CREATOR);
                Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                onConnect(readString, token, bundle);
            } else if (i == 2) {
                onConnectFailed();
            } else if (i == 3) {
                String readString2 = parcel.readString();
                ParceledListSlice parceledListSlice = (ParceledListSlice) parcel.readTypedObject(ParceledListSlice.CREATOR);
                Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                onLoadChildren(readString2, parceledListSlice, bundle2);
            } else if (i == 4) {
                onDisconnect();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IMediaBrowserServiceCallbacks {
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

            @Override // android.service.media.IMediaBrowserServiceCallbacks
            public void onConnect(String str, MediaSession.Token token, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(token, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.media.IMediaBrowserServiceCallbacks
            public void onConnectFailed() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.media.IMediaBrowserServiceCallbacks
            public void onLoadChildren(String str, ParceledListSlice parceledListSlice, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(parceledListSlice, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.media.IMediaBrowserServiceCallbacks
            public void onDisconnect() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
