package android.security.authenticationpolicy;

import android.Manifest;
import android.app.ActivityThread;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PermissionEnforcer;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IAuthenticationPolicyService extends IInterface {
    public static final String DESCRIPTOR = "android.security.authenticationpolicy.IAuthenticationPolicyService";

    public static class Default implements IAuthenticationPolicyService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.security.authenticationpolicy.IAuthenticationPolicyService
        public int disableSecureLockDevice(DisableSecureLockDeviceParams disableSecureLockDeviceParams) throws RemoteException {
            return 0;
        }

        @Override // android.security.authenticationpolicy.IAuthenticationPolicyService
        public int enableSecureLockDevice(EnableSecureLockDeviceParams enableSecureLockDeviceParams) throws RemoteException {
            return 0;
        }
    }

    int disableSecureLockDevice(DisableSecureLockDeviceParams disableSecureLockDeviceParams) throws RemoteException;

    int enableSecureLockDevice(EnableSecureLockDeviceParams enableSecureLockDeviceParams) throws RemoteException;

    public static abstract class Stub extends Binder implements IAuthenticationPolicyService {
        static final int TRANSACTION_disableSecureLockDevice = 2;
        static final int TRANSACTION_enableSecureLockDevice = 1;
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
            attachInterface(this, IAuthenticationPolicyService.DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IAuthenticationPolicyService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IAuthenticationPolicyService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IAuthenticationPolicyService)) {
                return (IAuthenticationPolicyService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "enableSecureLockDevice";
            }
            if (i != 2) {
                return null;
            }
            return "disableSecureLockDevice";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IAuthenticationPolicyService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IAuthenticationPolicyService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                EnableSecureLockDeviceParams enableSecureLockDeviceParams = (EnableSecureLockDeviceParams) parcel.readTypedObject(EnableSecureLockDeviceParams.CREATOR);
                parcel.enforceNoDataAvail();
                int iEnableSecureLockDevice = enableSecureLockDevice(enableSecureLockDeviceParams);
                parcel2.writeNoException();
                parcel2.writeInt(iEnableSecureLockDevice);
            } else if (i == 2) {
                DisableSecureLockDeviceParams disableSecureLockDeviceParams = (DisableSecureLockDeviceParams) parcel.readTypedObject(DisableSecureLockDeviceParams.CREATOR);
                parcel.enforceNoDataAvail();
                int iDisableSecureLockDevice = disableSecureLockDevice(disableSecureLockDeviceParams);
                parcel2.writeNoException();
                parcel2.writeInt(iDisableSecureLockDevice);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IAuthenticationPolicyService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAuthenticationPolicyService.DESCRIPTOR;
            }

            @Override // android.security.authenticationpolicy.IAuthenticationPolicyService
            public int enableSecureLockDevice(EnableSecureLockDeviceParams enableSecureLockDeviceParams) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAuthenticationPolicyService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(enableSecureLockDeviceParams, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.authenticationpolicy.IAuthenticationPolicyService
            public int disableSecureLockDevice(DisableSecureLockDeviceParams disableSecureLockDeviceParams) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAuthenticationPolicyService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(disableSecureLockDeviceParams, 0);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        protected void enableSecureLockDevice_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_SECURE_LOCK_DEVICE, getCallingPid(), getCallingUid());
        }

        protected void disableSecureLockDevice_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_SECURE_LOCK_DEVICE, getCallingPid(), getCallingUid());
        }
    }
}
