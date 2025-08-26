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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISupervisionManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISupervisionManager)) {
                return (ISupervisionManager) iInterfaceQueryLocalInterface;
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
                Intent intentCreateConfirmSupervisionCredentialsIntent = createConfirmSupervisionCredentialsIntent();
                parcel2.writeNoException();
                parcel2.writeTypedObject(intentCreateConfirmSupervisionCredentialsIntent, 1);
            } else if (i == 2) {
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                boolean zIsSupervisionEnabledForUser = isSupervisionEnabledForUser(i3);
                parcel2.writeNoException();
                parcel2.writeBoolean(zIsSupervisionEnabledForUser);
            } else if (i == 3) {
                int i4 = parcel.readInt();
                boolean z = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                setSupervisionEnabledForUser(i4, z);
                parcel2.writeNoException();
            } else if (i == 4) {
                int i5 = parcel.readInt();
                parcel.enforceNoDataAvail();
                String activeSupervisionAppPackage = getActiveSupervisionAppPackage(i5);
                parcel2.writeNoException();
                parcel2.writeString(activeSupervisionAppPackage);
            } else if (i == 5) {
                boolean zShouldAllowBypassingSupervisionRoleQualification = shouldAllowBypassingSupervisionRoleQualification();
                parcel2.writeNoException();
                parcel2.writeBoolean(zShouldAllowBypassingSupervisionRoleQualification);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISupervisionManager.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Intent) parcelObtain2.readTypedObject(Intent.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.supervision.ISupervisionManager
            public boolean isSupervisionEnabledForUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISupervisionManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.supervision.ISupervisionManager
            public void setSupervisionEnabledForUser(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISupervisionManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.supervision.ISupervisionManager
            public String getActiveSupervisionAppPackage(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISupervisionManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.supervision.ISupervisionManager
            public boolean shouldAllowBypassingSupervisionRoleQualification() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISupervisionManager.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
