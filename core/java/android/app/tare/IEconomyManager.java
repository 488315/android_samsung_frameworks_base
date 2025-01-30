package android.app.tare;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes.dex */
public interface IEconomyManager extends IInterface {
    public static final String DESCRIPTOR = "android.app.tare.IEconomyManager";

    int getEnabledMode() throws RemoteException;

    public static class Default implements IEconomyManager {
        @Override // android.app.tare.IEconomyManager
        public int getEnabledMode() throws RemoteException {
            return 0;
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IEconomyManager {
        static final int TRANSACTION_getEnabledMode = 1;

        public Stub() {
            attachInterface(this, IEconomyManager.DESCRIPTOR);
        }

        public static IEconomyManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IEconomyManager.DESCRIPTOR);
            if (iin != null && (iin instanceof IEconomyManager)) {
                return (IEconomyManager) iin;
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
                    return "getEnabledMode";
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
                data.enforceInterface(IEconomyManager.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(IEconomyManager.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            int _result = getEnabledMode();
                            reply.writeNoException();
                            reply.writeInt(_result);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IEconomyManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IEconomyManager.DESCRIPTOR;
            }

            @Override // android.app.tare.IEconomyManager
            public int getEnabledMode() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IEconomyManager.DESCRIPTOR);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
