package android.view.accessibility;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IAccessibilityManagerClient extends IInterface {

    public static class Default implements IAccessibilityManagerClient {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.view.accessibility.IAccessibilityManagerClient
        public void notifyServicesStateChanged(long j) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManagerClient
        public void setFocusAppearance(int i, int i2) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManagerClient
        public void setRelevantEventTypes(int i) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManagerClient
        public void setState(int i) throws RemoteException {
        }
    }

    void notifyServicesStateChanged(long j) throws RemoteException;

    void setFocusAppearance(int i, int i2) throws RemoteException;

    void setRelevantEventTypes(int i) throws RemoteException;

    void setState(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IAccessibilityManagerClient {
        public static final String DESCRIPTOR = "android.view.accessibility.IAccessibilityManagerClient";
        static final int TRANSACTION_notifyServicesStateChanged = 2;
        static final int TRANSACTION_setFocusAppearance = 4;
        static final int TRANSACTION_setRelevantEventTypes = 3;
        static final int TRANSACTION_setState = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAccessibilityManagerClient asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IAccessibilityManagerClient)) {
                return (IAccessibilityManagerClient) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "setState";
            }
            if (i == 2) {
                return "notifyServicesStateChanged";
            }
            if (i == 3) {
                return "setRelevantEventTypes";
            }
            if (i != 4) {
                return null;
            }
            return "setFocusAppearance";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                setState(readInt);
            } else if (i == 2) {
                long readLong = parcel.readLong();
                parcel.enforceNoDataAvail();
                notifyServicesStateChanged(readLong);
            } else if (i == 3) {
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                setRelevantEventTypes(readInt2);
            } else if (i == 4) {
                int readInt3 = parcel.readInt();
                int readInt4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                setFocusAppearance(readInt3, readInt4);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IAccessibilityManagerClient {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.view.accessibility.IAccessibilityManagerClient
            public void setState(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManagerClient
            public void notifyServicesStateChanged(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManagerClient
            public void setRelevantEventTypes(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManagerClient
            public void setFocusAppearance(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
