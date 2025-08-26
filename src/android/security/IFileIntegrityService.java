package android.security;

import android.Manifest;
import android.app.ActivityThread;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInstalld;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.PermissionEnforcer;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IFileIntegrityService extends IInterface {
    public static final String DESCRIPTOR = "android.security.IFileIntegrityService";

    public static class Default implements IFileIntegrityService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.security.IFileIntegrityService
        public IInstalld.IFsveritySetupAuthToken createAuthToken(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
            return null;
        }

        @Override // android.security.IFileIntegrityService
        public int setupFsverity(IInstalld.IFsveritySetupAuthToken iFsveritySetupAuthToken, String str, String str2) throws RemoteException {
            return 0;
        }
    }

    IInstalld.IFsveritySetupAuthToken createAuthToken(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException;

    int setupFsverity(IInstalld.IFsveritySetupAuthToken iFsveritySetupAuthToken, String str, String str2) throws RemoteException;

    public static abstract class Stub extends Binder implements IFileIntegrityService {
        static final int TRANSACTION_createAuthToken = 1;
        static final int TRANSACTION_setupFsverity = 2;
        private final PermissionEnforcer mEnforcer;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub(PermissionEnforcer permissionEnforcer) {
            attachInterface(this, IFileIntegrityService.DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IFileIntegrityService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IFileIntegrityService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IFileIntegrityService)) {
                return (IFileIntegrityService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "createAuthToken";
            }
            if (i != 2) {
                return null;
            }
            return "setupFsverity";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IFileIntegrityService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IFileIntegrityService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                parcel.enforceNoDataAvail();
                IInstalld.IFsveritySetupAuthToken iFsveritySetupAuthTokenCreateAuthToken = createAuthToken(parcelFileDescriptor);
                parcel2.writeNoException();
                parcel2.writeStrongInterface(iFsveritySetupAuthTokenCreateAuthToken);
            } else if (i == 2) {
                IInstalld.IFsveritySetupAuthToken iFsveritySetupAuthTokenAsInterface = IInstalld.IFsveritySetupAuthToken.Stub.asInterface(parcel.readStrongBinder());
                String string = parcel.readString();
                String string2 = parcel.readString();
                parcel.enforceNoDataAvail();
                int i3 = setupFsverity(iFsveritySetupAuthTokenAsInterface, string, string2);
                parcel2.writeNoException();
                parcel2.writeInt(i3);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IFileIntegrityService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IFileIntegrityService.DESCRIPTOR;
            }

            @Override // android.security.IFileIntegrityService
            public IInstalld.IFsveritySetupAuthToken createAuthToken(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFileIntegrityService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IInstalld.IFsveritySetupAuthToken.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.IFileIntegrityService
            public int setupFsverity(IInstalld.IFsveritySetupAuthToken iFsveritySetupAuthToken, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFileIntegrityService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iFsveritySetupAuthToken);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        protected void setupFsverity_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.SETUP_FSVERITY, getCallingPid(), getCallingUid());
        }
    }
}
