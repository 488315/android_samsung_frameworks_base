package android.media;

import android.media.session.MediaSession;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IRemoteSessionCallback extends IInterface {
    public static final String DESCRIPTOR = "android.media.IRemoteSessionCallback";

    public static class Default implements IRemoteSessionCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.IRemoteSessionCallback
        public void onSessionChanged(MediaSession.Token token) throws RemoteException {
        }

        @Override // android.media.IRemoteSessionCallback
        public void onVolumeChanged(MediaSession.Token token, int i) throws RemoteException {
        }
    }

    void onSessionChanged(MediaSession.Token token) throws RemoteException;

    void onVolumeChanged(MediaSession.Token token, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IRemoteSessionCallback {
        static final int TRANSACTION_onSessionChanged = 2;
        static final int TRANSACTION_onVolumeChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IRemoteSessionCallback.DESCRIPTOR);
        }

        public static IRemoteSessionCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IRemoteSessionCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IRemoteSessionCallback)) {
                return (IRemoteSessionCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onVolumeChanged";
            }
            if (i != 2) {
                return null;
            }
            return "onSessionChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IRemoteSessionCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IRemoteSessionCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                MediaSession.Token token = (MediaSession.Token) parcel.readTypedObject(MediaSession.Token.CREATOR);
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                onVolumeChanged(token, readInt);
            } else if (i == 2) {
                MediaSession.Token token2 = (MediaSession.Token) parcel.readTypedObject(MediaSession.Token.CREATOR);
                parcel.enforceNoDataAvail();
                onSessionChanged(token2);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IRemoteSessionCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRemoteSessionCallback.DESCRIPTOR;
            }

            @Override // android.media.IRemoteSessionCallback
            public void onVolumeChanged(MediaSession.Token token, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRemoteSessionCallback.DESCRIPTOR);
                    obtain.writeTypedObject(token, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IRemoteSessionCallback
            public void onSessionChanged(MediaSession.Token token) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRemoteSessionCallback.DESCRIPTOR);
                    obtain.writeTypedObject(token, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
