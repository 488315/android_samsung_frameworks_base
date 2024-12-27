package android.security.metrics;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IKeystoreMetrics extends IInterface {
    public static final String DESCRIPTOR = "android.security.metrics.IKeystoreMetrics";

    KeystoreAtom[] pullMetrics(int i) throws RemoteException;

    public static class Default implements IKeystoreMetrics {
        @Override // android.security.metrics.IKeystoreMetrics
        public KeystoreAtom[] pullMetrics(int atomID) throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IKeystoreMetrics {
        static final int TRANSACTION_pullMetrics = 1;

        public Stub() {
            attachInterface(this, IKeystoreMetrics.DESCRIPTOR);
        }

        public static IKeystoreMetrics asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IKeystoreMetrics.DESCRIPTOR);
            if (iin != null && (iin instanceof IKeystoreMetrics)) {
                return (IKeystoreMetrics) iin;
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
                    return "pullMetrics";
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
                data.enforceInterface(IKeystoreMetrics.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IKeystoreMetrics.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    data.enforceNoDataAvail();
                    KeystoreAtom[] _result = pullMetrics(_arg0);
                    reply.writeNoException();
                    reply.writeTypedArray(_result, 1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IKeystoreMetrics {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IKeystoreMetrics.DESCRIPTOR;
            }

            @Override // android.security.metrics.IKeystoreMetrics
            public KeystoreAtom[] pullMetrics(int atomID) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IKeystoreMetrics.DESCRIPTOR);
                    _data.writeInt(atomID);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    KeystoreAtom[] _result = (KeystoreAtom[]) _reply.createTypedArray(KeystoreAtom.CREATOR);
                    return _result;
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
