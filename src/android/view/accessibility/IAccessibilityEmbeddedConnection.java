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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IAccessibilityEmbeddedConnection.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IAccessibilityEmbeddedConnection)) {
                return (IAccessibilityEmbeddedConnection) iInterfaceQueryLocalInterface;
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
                IBinder strongBinder = parcel.readStrongBinder();
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                IBinder iBinderAssociateEmbeddedHierarchy = associateEmbeddedHierarchy(strongBinder, i3);
                parcel2.writeNoException();
                parcel2.writeStrongBinder(iBinderAssociateEmbeddedHierarchy);
            } else if (i == 2) {
                disassociateEmbeddedHierarchy();
                parcel2.writeNoException();
            } else if (i == 3) {
                float[] fArrCreateFloatArray = parcel.createFloatArray();
                parcel.enforceNoDataAvail();
                setWindowMatrix(fArrCreateFloatArray);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAccessibilityEmbeddedConnection.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readStrongBinder();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityEmbeddedConnection
            public void disassociateEmbeddedHierarchy() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAccessibilityEmbeddedConnection.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityEmbeddedConnection
            public void setWindowMatrix(float[] fArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IAccessibilityEmbeddedConnection.DESCRIPTOR);
                    parcelObtain.writeFloatArray(fArr);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
