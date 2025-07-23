package android.content.pm;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IStagedApexObserver extends IInterface {
    public static final String DESCRIPTOR = "android.content.pm.IStagedApexObserver";

    public static class Default implements IStagedApexObserver {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.content.pm.IStagedApexObserver
        public void onApexStaged(ApexStagedEvent apexStagedEvent) throws RemoteException {
        }
    }

    void onApexStaged(ApexStagedEvent apexStagedEvent) throws RemoteException;

    public static abstract class Stub extends Binder implements IStagedApexObserver {
        static final int TRANSACTION_onApexStaged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IStagedApexObserver.DESCRIPTOR);
        }

        public static IStagedApexObserver asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IStagedApexObserver.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IStagedApexObserver)) {
                return (IStagedApexObserver) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IStagedApexObserver.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IStagedApexObserver.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ApexStagedEvent apexStagedEvent = (ApexStagedEvent) parcel.readTypedObject(ApexStagedEvent.CREATOR);
                parcel.enforceNoDataAvail();
                onApexStaged(apexStagedEvent);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IStagedApexObserver {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IStagedApexObserver.DESCRIPTOR;
            }

            @Override // android.content.pm.IStagedApexObserver
            public void onApexStaged(ApexStagedEvent apexStagedEvent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IStagedApexObserver.DESCRIPTOR);
                    obtain.writeTypedObject(apexStagedEvent, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
