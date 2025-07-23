package android.security.metrics;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IKeystoreMetrics extends IInterface {
    public static final String DESCRIPTOR = "android.security.metrics.IKeystoreMetrics";

    public static class Default implements IKeystoreMetrics {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.security.metrics.IKeystoreMetrics
        public KeystoreAtom[] pullMetrics(int i) throws RemoteException {
            return null;
        }
    }

    KeystoreAtom[] pullMetrics(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IKeystoreMetrics {
        static final int TRANSACTION_pullMetrics = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IKeystoreMetrics.DESCRIPTOR);
        }

        public static IKeystoreMetrics asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IKeystoreMetrics.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IKeystoreMetrics)) {
                return (IKeystoreMetrics) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "pullMetrics";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IKeystoreMetrics.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IKeystoreMetrics.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                KeystoreAtom[] pullMetrics = pullMetrics(readInt);
                parcel2.writeNoException();
                parcel2.writeTypedArray(pullMetrics, 1);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IKeystoreMetrics {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IKeystoreMetrics.DESCRIPTOR;
            }

            @Override // android.security.metrics.IKeystoreMetrics
            public KeystoreAtom[] pullMetrics(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKeystoreMetrics.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (KeystoreAtom[]) obtain2.createTypedArray(KeystoreAtom.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
