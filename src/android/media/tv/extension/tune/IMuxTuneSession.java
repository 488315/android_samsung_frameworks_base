package android.media.tv.extension.tune;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IMuxTuneSession extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.tune.IMuxTuneSession";

    public static class Default implements IMuxTuneSession {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.tune.IMuxTuneSession
        public String getSessionToken() throws RemoteException {
            return null;
        }

        @Override // android.media.tv.extension.tune.IMuxTuneSession
        public void release() throws RemoteException {
        }

        @Override // android.media.tv.extension.tune.IMuxTuneSession
        public void start(int i, int i2, int i3, Bundle bundle) throws RemoteException {
        }

        @Override // android.media.tv.extension.tune.IMuxTuneSession
        public void stop() throws RemoteException {
        }
    }

    String getSessionToken() throws RemoteException;

    void release() throws RemoteException;

    void start(int i, int i2, int i3, Bundle bundle) throws RemoteException;

    void stop() throws RemoteException;

    public static abstract class Stub extends Binder implements IMuxTuneSession {
        static final int TRANSACTION_getSessionToken = 4;
        static final int TRANSACTION_release = 3;
        static final int TRANSACTION_start = 1;
        static final int TRANSACTION_stop = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, "android.media.tv.extension.tune.IMuxTuneSession");
        }

        public static IMuxTuneSession asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.tune.IMuxTuneSession");
            if (queryLocalInterface != null && (queryLocalInterface instanceof IMuxTuneSession)) {
                return (IMuxTuneSession) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "start";
            }
            if (i == 2) {
                return "stop";
            }
            if (i == 3) {
                return "release";
            }
            if (i != 4) {
                return null;
            }
            return "getSessionToken";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("android.media.tv.extension.tune.IMuxTuneSession");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.tune.IMuxTuneSession");
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                int readInt2 = parcel.readInt();
                int readInt3 = parcel.readInt();
                Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                start(readInt, readInt2, readInt3, bundle);
                parcel2.writeNoException();
            } else if (i == 2) {
                stop();
                parcel2.writeNoException();
            } else if (i == 3) {
                release();
                parcel2.writeNoException();
            } else if (i == 4) {
                String sessionToken = getSessionToken();
                parcel2.writeNoException();
                parcel2.writeString(sessionToken);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IMuxTuneSession {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.tune.IMuxTuneSession";
            }

            @Override // android.media.tv.extension.tune.IMuxTuneSession
            public void start(int i, int i2, int i3, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.tune.IMuxTuneSession");
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.tune.IMuxTuneSession
            public void stop() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.tune.IMuxTuneSession");
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.tune.IMuxTuneSession
            public void release() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.tune.IMuxTuneSession");
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.tune.IMuxTuneSession
            public String getSessionToken() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.tune.IMuxTuneSession");
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
