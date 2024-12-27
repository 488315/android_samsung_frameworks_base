package android.hardware.location;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface ISignificantPlaceProviderManager extends IInterface {
    public static final String DESCRIPTOR =
            "android.hardware.location.ISignificantPlaceProviderManager";

    void setInSignificantPlace(boolean z) throws RemoteException;

    public static class Default implements ISignificantPlaceProviderManager {
        @Override // android.hardware.location.ISignificantPlaceProviderManager
        public void setInSignificantPlace(boolean inSignificantPlace) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ISignificantPlaceProviderManager {
        static final int TRANSACTION_setInSignificantPlace = 1;

        public Stub() {
            attachInterface(this, ISignificantPlaceProviderManager.DESCRIPTOR);
        }

        public static ISignificantPlaceProviderManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISignificantPlaceProviderManager.DESCRIPTOR);
            if (iin != null && (iin instanceof ISignificantPlaceProviderManager)) {
                return (ISignificantPlaceProviderManager) iin;
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
                    return "setInSignificantPlace";
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
                data.enforceInterface(ISignificantPlaceProviderManager.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISignificantPlaceProviderManager.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    boolean _arg0 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setInSignificantPlace(_arg0);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISignificantPlaceProviderManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISignificantPlaceProviderManager.DESCRIPTOR;
            }

            @Override // android.hardware.location.ISignificantPlaceProviderManager
            public void setInSignificantPlace(boolean inSignificantPlace) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISignificantPlaceProviderManager.DESCRIPTOR);
                    _data.writeBoolean(inSignificantPlace);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
