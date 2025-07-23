package android.view.accessibility;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IAccessibilityEmbeddedConnection extends IInterface {
    public static final String DESCRIPTOR = "android.view.accessibility.IAccessibilityEmbeddedConnection";

    public static class Default implements IAccessibilityEmbeddedConnection {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.view.accessibility.IAccessibilityEmbeddedConnection
        public IBinder associateEmbeddedHierarchy(IBinder iBinder, int i) throws RemoteException {
            return null;
        }

        @Override // android.view.accessibility.IAccessibilityEmbeddedConnection
        public void disassociateEmbeddedHierarchy() throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityEmbeddedConnection
        public void setWindowMatrix(float[] fArr) throws RemoteException {
        }
    }

    IBinder associateEmbeddedHierarchy(IBinder iBinder, int i) throws RemoteException;

    void disassociateEmbeddedHierarchy() throws RemoteException;

    void setWindowMatrix(float[] fArr) throws RemoteException;

    public static abstract class Stub extends Binder implements IAccessibilityEmbeddedConnection {
        static final int TRANSACTION_associateEmbeddedHierarchy = 1;
        static final int TRANSACTION_disassociateEmbeddedHierarchy = 2;
        static final int TRANSACTION_setWindowMatrix = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, IAccessibilityEmbeddedConnection.DESCRIPTOR);
        }

        public static IAccessibilityEmbeddedConnection asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IAccessibilityEmbeddedConnection.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IAccessibilityEmbeddedConnection)) {
                return (IAccessibilityEmbeddedConnection) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "associateEmbeddedHierarchy";
            }
            if (i == 2) {
                return "disassociateEmbeddedHierarchy";
            }
            if (i != 3) {
                return null;
            }
            return "setWindowMatrix";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IAccessibilityEmbeddedConnection.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IAccessibilityEmbeddedConnection.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                IBinder readStrongBinder = parcel.readStrongBinder();
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                IBinder associateEmbeddedHierarchy = associateEmbeddedHierarchy(readStrongBinder, readInt);
                parcel2.writeNoException();
                parcel2.writeStrongBinder(associateEmbeddedHierarchy);
            } else if (i == 2) {
                disassociateEmbeddedHierarchy();
                parcel2.writeNoException();
            } else if (i == 3) {
                float[] createFloatArray = parcel.createFloatArray();
                parcel.enforceNoDataAvail();
                setWindowMatrix(createFloatArray);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IAccessibilityEmbeddedConnection {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAccessibilityEmbeddedConnection.DESCRIPTOR;
            }

            @Override // android.view.accessibility.IAccessibilityEmbeddedConnection
            public IBinder associateEmbeddedHierarchy(IBinder iBinder, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAccessibilityEmbeddedConnection.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readStrongBinder();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityEmbeddedConnection
            public void disassociateEmbeddedHierarchy() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAccessibilityEmbeddedConnection.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityEmbeddedConnection
            public void setWindowMatrix(float[] fArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IAccessibilityEmbeddedConnection.DESCRIPTOR);
                    obtain.writeFloatArray(fArr);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
