package android.app.contextualsearch;

import android.app.contextualsearch.IContextualSearchCallback;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IContextualSearchManager extends IInterface {
    public static final String DESCRIPTOR = "android.app.contextualsearch.IContextualSearchManager";

    void getContextualSearchState(IBinder iBinder, IContextualSearchCallback iContextualSearchCallback) throws RemoteException;

    void startContextualSearch(int i) throws RemoteException;

    public static class Default implements IContextualSearchManager {
        @Override // android.app.contextualsearch.IContextualSearchManager
        public void startContextualSearch(int entrypoint) throws RemoteException {
        }

        @Override // android.app.contextualsearch.IContextualSearchManager
        public void getContextualSearchState(IBinder token, IContextualSearchCallback callback) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IContextualSearchManager {
        static final int TRANSACTION_getContextualSearchState = 2;
        static final int TRANSACTION_startContextualSearch = 1;

        public Stub() {
            attachInterface(this, IContextualSearchManager.DESCRIPTOR);
        }

        public static IContextualSearchManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IContextualSearchManager.DESCRIPTOR);
            if (iin != null && (iin instanceof IContextualSearchManager)) {
                return (IContextualSearchManager) iin;
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
                    return "startContextualSearch";
                case 2:
                    return "getContextualSearchState";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IContextualSearchManager.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IContextualSearchManager.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    data.enforceNoDataAvail();
                    startContextualSearch(_arg0);
                    return true;
                case 2:
                    IBinder _arg02 = data.readStrongBinder();
                    IContextualSearchCallback _arg1 = IContextualSearchCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    getContextualSearchState(_arg02, _arg1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IContextualSearchManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IContextualSearchManager.DESCRIPTOR;
            }

            @Override // android.app.contextualsearch.IContextualSearchManager
            public void startContextualSearch(int entrypoint) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IContextualSearchManager.DESCRIPTOR);
                    _data.writeInt(entrypoint);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.contextualsearch.IContextualSearchManager
            public void getContextualSearchState(IBinder token, IContextualSearchCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IContextualSearchManager.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeStrongInterface(callback);
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
