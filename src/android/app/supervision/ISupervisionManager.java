package android.app.supervision;

import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface ISupervisionManager extends IInterface {
    public static final String DESCRIPTOR = "android.app.supervision.ISupervisionManager";

    public static class Default implements ISupervisionManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.supervision.ISupervisionManager
        public Intent createConfirmSupervisionCredentialsIntent() throws RemoteException {
            return null;
        }

        @Override // android.app.supervision.ISupervisionManager
        public String getActiveSupervisionAppPackage(int i) throws RemoteException {
            return null;
        }

        @Override // android.app.supervision.ISupervisionManager
        public boolean isSupervisionEnabledForUser(int i) throws RemoteException {
            return false;
        }

        @Override // android.app.supervision.ISupervisionManager
        public void setSupervisionEnabledForUser(int i, boolean z) throws RemoteException {
        }

        @Override // android.app.supervision.ISupervisionManager
        public boolean shouldAllowBypassingSupervisionRoleQualification() throws RemoteException {
            return false;
        }
    }

    Intent createConfirmSupervisionCredentialsIntent() throws RemoteException;

    String getActiveSupervisionAppPackage(int i) throws RemoteException;

    boolean isSupervisionEnabledForUser(int i) throws RemoteException;

    void setSupervisionEnabledForUser(int i, boolean z) throws RemoteException;

    boolean shouldAllowBypassingSupervisionRoleQualification() throws RemoteException;

    public static abstract class Stub extends Binder implements ISupervisionManager {
        static final int TRANSACTION_createConfirmSupervisionCredentialsIntent = 1;
        static final int TRANSACTION_getActiveSupervisionAppPackage = 4;
        static final int TRANSACTION_isSupervisionEnabledForUser = 2;
        static final int TRANSACTION_setSupervisionEnabledForUser = 3;
        static final int TRANSACTION_shouldAllowBypassingSupervisionRoleQualification = 5;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }

        public Stub() {
            attachInterface(this, ISupervisionManager.DESCRIPTOR);
        }

        public static ISupervisionManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISupervisionManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISupervisionManager)) {
                return (ISupervisionManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "createConfirmSupervisionCredentialsIntent";
            }
            if (i == 2) {
                return "isSupervisionEnabledForUser";
            }
            if (i == 3) {
                return "setSupervisionEnabledForUser";
            }
            if (i == 4) {
                return "getActiveSupervisionAppPackage";
            }
            if (i != 5) {
                return null;
            }
            return "shouldAllowBypassingSupervisionRoleQualification";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISupervisionManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISupervisionManager.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                Intent createConfirmSupervisionCredentialsIntent = createConfirmSupervisionCredentialsIntent();
                parcel2.writeNoException();
                parcel2.writeTypedObject(createConfirmSupervisionCredentialsIntent, 1);
            } else if (i == 2) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                boolean isSupervisionEnabledForUser = isSupervisionEnabledForUser(readInt);
                parcel2.writeNoException();
                parcel2.writeBoolean(isSupervisionEnabledForUser);
            } else if (i == 3) {
                int readInt2 = parcel.readInt();
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                setSupervisionEnabledForUser(readInt2, readBoolean);
                parcel2.writeNoException();
            } else if (i == 4) {
                int readInt3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                String activeSupervisionAppPackage = getActiveSupervisionAppPackage(readInt3);
                parcel2.writeNoException();
                parcel2.writeString(activeSupervisionAppPackage);
            } else if (i == 5) {
                boolean shouldAllowBypassingSupervisionRoleQualification = shouldAllowBypassingSupervisionRoleQualification();
                parcel2.writeNoException();
                parcel2.writeBoolean(shouldAllowBypassingSupervisionRoleQualification);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISupervisionManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISupervisionManager.DESCRIPTOR;
            }

            @Override // android.app.supervision.ISupervisionManager
            public Intent createConfirmSupervisionCredentialsIntent() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISupervisionManager.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Intent) obtain2.readTypedObject(Intent.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.supervision.ISupervisionManager
            public boolean isSupervisionEnabledForUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISupervisionManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.supervision.ISupervisionManager
            public void setSupervisionEnabledForUser(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISupervisionManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.supervision.ISupervisionManager
            public String getActiveSupervisionAppPackage(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISupervisionManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.supervision.ISupervisionManager
            public boolean shouldAllowBypassingSupervisionRoleQualification() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISupervisionManager.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
