package android.media;

import android.media.IRemoteDisplayCallback;
import android.media.MediaMetrics;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IRemoteDisplayProvider extends IInterface {

    public static class Default implements IRemoteDisplayProvider {
        @Override // android.media.IRemoteDisplayProvider
        public void adjustVolume(String str, int i) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.IRemoteDisplayProvider
        public void connect(String str) throws RemoteException {
        }

        @Override // android.media.IRemoteDisplayProvider
        public void disconnect(String str) throws RemoteException {
        }

        @Override // android.media.IRemoteDisplayProvider
        public void setCallback(IRemoteDisplayCallback iRemoteDisplayCallback) throws RemoteException {
        }

        @Override // android.media.IRemoteDisplayProvider
        public void setDiscoveryMode(int i) throws RemoteException {
        }

        @Override // android.media.IRemoteDisplayProvider
        public void setVolume(String str, int i) throws RemoteException {
        }
    }

    void adjustVolume(String str, int i) throws RemoteException;

    void connect(String str) throws RemoteException;

    void disconnect(String str) throws RemoteException;

    void setCallback(IRemoteDisplayCallback iRemoteDisplayCallback) throws RemoteException;

    void setDiscoveryMode(int i) throws RemoteException;

    void setVolume(String str, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IRemoteDisplayProvider {
        public static final String DESCRIPTOR = "android.media.IRemoteDisplayProvider";
        static final int TRANSACTION_adjustVolume = 6;
        static final int TRANSACTION_connect = 3;
        static final int TRANSACTION_disconnect = 4;
        static final int TRANSACTION_setCallback = 1;
        static final int TRANSACTION_setDiscoveryMode = 2;
        static final int TRANSACTION_setVolume = 5;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IRemoteDisplayProvider asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IRemoteDisplayProvider)) {
                return (IRemoteDisplayProvider) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setCallback";
                case 2:
                    return "setDiscoveryMode";
                case 3:
                    return MediaMetrics.Value.CONNECT;
                case 4:
                    return MediaMetrics.Value.DISCONNECT;
                case 5:
                    return "setVolume";
                case 6:
                    return "adjustVolume";
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
                    IRemoteDisplayCallback asInterface = IRemoteDisplayCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setCallback(asInterface);
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDiscoveryMode(readInt);
                    return true;
                case 3:
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    connect(readString);
                    return true;
                case 4:
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    disconnect(readString2);
                    return true;
                case 5:
                    String readString3 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setVolume(readString3, readInt2);
                    return true;
                case 6:
                    String readString4 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    adjustVolume(readString4, readInt3);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IRemoteDisplayProvider {
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

            @Override // android.media.IRemoteDisplayProvider
            public void setCallback(IRemoteDisplayCallback iRemoteDisplayCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iRemoteDisplayCallback);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IRemoteDisplayProvider
            public void setDiscoveryMode(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IRemoteDisplayProvider
            public void connect(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IRemoteDisplayProvider
            public void disconnect(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IRemoteDisplayProvider
            public void setVolume(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IRemoteDisplayProvider
            public void adjustVolume(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
