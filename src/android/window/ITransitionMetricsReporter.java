package android.window;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface ITransitionMetricsReporter extends IInterface {
    public static final String DESCRIPTOR = "android.window.ITransitionMetricsReporter";

    public static class Default implements ITransitionMetricsReporter {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.window.ITransitionMetricsReporter
        public void reportAnimationStart(IBinder iBinder, long j) throws RemoteException {
        }
    }

    void reportAnimationStart(IBinder iBinder, long j) throws RemoteException;

    public static abstract class Stub extends Binder implements ITransitionMetricsReporter {
        static final int TRANSACTION_reportAnimationStart = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, ITransitionMetricsReporter.DESCRIPTOR);
        }

        public static ITransitionMetricsReporter asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ITransitionMetricsReporter.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ITransitionMetricsReporter)) {
                return (ITransitionMetricsReporter) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "reportAnimationStart";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ITransitionMetricsReporter.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ITransitionMetricsReporter.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                IBinder readStrongBinder = parcel.readStrongBinder();
                long readLong = parcel.readLong();
                parcel.enforceNoDataAvail();
                reportAnimationStart(readStrongBinder, readLong);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ITransitionMetricsReporter {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITransitionMetricsReporter.DESCRIPTOR;
            }

            @Override // android.window.ITransitionMetricsReporter
            public void reportAnimationStart(IBinder iBinder, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITransitionMetricsReporter.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeLong(j);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
