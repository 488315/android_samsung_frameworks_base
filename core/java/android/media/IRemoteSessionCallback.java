package android.media;

import android.media.session.MediaSession;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IRemoteSessionCallback extends IInterface {
    public static final String DESCRIPTOR = "android.media.IRemoteSessionCallback";

    void onSessionChanged(MediaSession.Token token) throws RemoteException;

    void onVolumeChanged(MediaSession.Token token, int i) throws RemoteException;

    public static class Default implements IRemoteSessionCallback {
        @Override // android.media.IRemoteSessionCallback
        public void onVolumeChanged(MediaSession.Token sessionToken, int flags)
                throws RemoteException {}

        @Override // android.media.IRemoteSessionCallback
        public void onSessionChanged(MediaSession.Token sessionToken) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IRemoteSessionCallback {
        static final int TRANSACTION_onSessionChanged = 2;
        static final int TRANSACTION_onVolumeChanged = 1;

        public Stub() {
            attachInterface(this, IRemoteSessionCallback.DESCRIPTOR);
        }

        public static IRemoteSessionCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IRemoteSessionCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IRemoteSessionCallback)) {
                return (IRemoteSessionCallback) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onVolumeChanged";
                case 2:
                    return "onSessionChanged";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IRemoteSessionCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IRemoteSessionCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    MediaSession.Token _arg0 =
                            (MediaSession.Token) data.readTypedObject(MediaSession.Token.CREATOR);
                    int _arg1 = data.readInt();
                    data.enforceNoDataAvail();
                    onVolumeChanged(_arg0, _arg1);
                    return true;
                case 2:
                    MediaSession.Token _arg02 =
                            (MediaSession.Token) data.readTypedObject(MediaSession.Token.CREATOR);
                    data.enforceNoDataAvail();
                    onSessionChanged(_arg02);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IRemoteSessionCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRemoteSessionCallback.DESCRIPTOR;
            }

            @Override // android.media.IRemoteSessionCallback
            public void onVolumeChanged(MediaSession.Token sessionToken, int flags)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IRemoteSessionCallback.DESCRIPTOR);
                    _data.writeTypedObject(sessionToken, 0);
                    _data.writeInt(flags);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IRemoteSessionCallback
            public void onSessionChanged(MediaSession.Token sessionToken) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IRemoteSessionCallback.DESCRIPTOR);
                    _data.writeTypedObject(sessionToken, 0);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
