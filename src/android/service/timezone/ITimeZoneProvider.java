package android.service.timezone;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.service.timezone.ITimeZoneProviderManager;

/* loaded from: classes3.dex */
public interface ITimeZoneProvider extends IInterface {
    public static final String DESCRIPTOR = "android.service.timezone.ITimeZoneProvider";

    public static class Default implements ITimeZoneProvider {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.timezone.ITimeZoneProvider
        public void startUpdates(ITimeZoneProviderManager iTimeZoneProviderManager, long j, long j2) throws RemoteException {
        }

        @Override // android.service.timezone.ITimeZoneProvider
        public void stopUpdates() throws RemoteException {
        }
    }

    void startUpdates(ITimeZoneProviderManager iTimeZoneProviderManager, long j, long j2) throws RemoteException;

    void stopUpdates() throws RemoteException;

    public static abstract class Stub extends Binder implements ITimeZoneProvider {
        static final int TRANSACTION_startUpdates = 1;
        static final int TRANSACTION_stopUpdates = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, ITimeZoneProvider.DESCRIPTOR);
        }

        public static ITimeZoneProvider asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ITimeZoneProvider.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ITimeZoneProvider)) {
                return (ITimeZoneProvider) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "startUpdates";
            }
            if (i != 2) {
                return null;
            }
            return "stopUpdates";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ITimeZoneProvider.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ITimeZoneProvider.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ITimeZoneProviderManager asInterface = ITimeZoneProviderManager.Stub.asInterface(parcel.readStrongBinder());
                long readLong = parcel.readLong();
                long readLong2 = parcel.readLong();
                parcel.enforceNoDataAvail();
                startUpdates(asInterface, readLong, readLong2);
            } else if (i == 2) {
                stopUpdates();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ITimeZoneProvider {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITimeZoneProvider.DESCRIPTOR;
            }

            @Override // android.service.timezone.ITimeZoneProvider
            public void startUpdates(ITimeZoneProviderManager iTimeZoneProviderManager, long j, long j2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITimeZoneProvider.DESCRIPTOR);
                    obtain.writeStrongInterface(iTimeZoneProviderManager);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.timezone.ITimeZoneProvider
            public void stopUpdates() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITimeZoneProvider.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
