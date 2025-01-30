package android.app;

import android.content.res.Configuration;
import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes.dex */
public interface IWindowToken extends IInterface {
    public static final String DESCRIPTOR = "android.app.IWindowToken";

    void onConfigurationChanged(Configuration configuration, int i) throws RemoteException;

    void onWindowTokenRemoved() throws RemoteException;

    public static class Default implements IWindowToken {
        @Override // android.app.IWindowToken
        public void onConfigurationChanged(Configuration newConfig, int newDisplayId) throws RemoteException {
        }

        @Override // android.app.IWindowToken
        public void onWindowTokenRemoved() throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IWindowToken {
        static final int TRANSACTION_onConfigurationChanged = 1;
        static final int TRANSACTION_onWindowTokenRemoved = 2;

        public Stub() {
            attachInterface(this, IWindowToken.DESCRIPTOR);
        }

        public static IWindowToken asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IWindowToken.DESCRIPTOR);
            if (iin != null && (iin instanceof IWindowToken)) {
                return (IWindowToken) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onConfigurationChanged";
                case 2:
                    return "onWindowTokenRemoved";
                default:
                    return null;
            }
        }

        @Override // android.p009os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p009os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IWindowToken.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(IWindowToken.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            Configuration _arg0 = (Configuration) data.readTypedObject(Configuration.CREATOR);
                            int _arg1 = data.readInt();
                            data.enforceNoDataAvail();
                            onConfigurationChanged(_arg0, _arg1);
                            return true;
                        case 2:
                            onWindowTokenRemoved();
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IWindowToken {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IWindowToken.DESCRIPTOR;
            }

            @Override // android.app.IWindowToken
            public void onConfigurationChanged(Configuration newConfig, int newDisplayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IWindowToken.DESCRIPTOR);
                    _data.writeTypedObject(newConfig, 0);
                    _data.writeInt(newDisplayId);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IWindowToken
            public void onWindowTokenRemoved() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IWindowToken.DESCRIPTOR);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
