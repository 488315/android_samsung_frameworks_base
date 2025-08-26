package android.hardware.devicestate;

import android.Manifest;
import android.app.ActivityThread;
import android.hardware.devicestate.IDeviceStateManagerCallback;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PermissionEnforcer;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IDeviceStateManager extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.devicestate.IDeviceStateManager";

    public static class Default implements IDeviceStateManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.devicestate.IDeviceStateManager
        public void cancelBaseStateOverride() throws RemoteException {
        }

        @Override // android.hardware.devicestate.IDeviceStateManager
        public void cancelStateRequest() throws RemoteException {
        }

        @Override // android.hardware.devicestate.IDeviceStateManager
        public DeviceStateInfo getDeviceStateInfo() throws RemoteException {
            return null;
        }

        @Override // android.hardware.devicestate.IDeviceStateManager
        public void onStateRequestOverlayDismissed(boolean z) throws RemoteException {
        }

        @Override // android.hardware.devicestate.IDeviceStateManager
        public DeviceStateInfo registerCallback(IDeviceStateManagerCallback iDeviceStateManagerCallback) throws RemoteException {
            return null;
        }

        @Override // android.hardware.devicestate.IDeviceStateManager
        public void requestBaseStateOverride(IBinder iBinder, int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.devicestate.IDeviceStateManager
        public void requestState(IBinder iBinder, int i, int i2) throws RemoteException {
        }
    }

    void cancelBaseStateOverride() throws RemoteException;

    void cancelStateRequest() throws RemoteException;

    DeviceStateInfo getDeviceStateInfo() throws RemoteException;

    void onStateRequestOverlayDismissed(boolean z) throws RemoteException;

    DeviceStateInfo registerCallback(IDeviceStateManagerCallback iDeviceStateManagerCallback) throws RemoteException;

    void requestBaseStateOverride(IBinder iBinder, int i, int i2) throws RemoteException;

    void requestState(IBinder iBinder, int i, int i2) throws RemoteException;

    public static abstract class Stub extends Binder implements IDeviceStateManager {
        static final int TRANSACTION_cancelBaseStateOverride = 6;
        static final int TRANSACTION_cancelStateRequest = 4;
        static final int TRANSACTION_getDeviceStateInfo = 1;
        static final int TRANSACTION_onStateRequestOverlayDismissed = 7;
        static final int TRANSACTION_registerCallback = 2;
        static final int TRANSACTION_requestBaseStateOverride = 5;
        static final int TRANSACTION_requestState = 3;
        private final PermissionEnforcer mEnforcer;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 6;
        }

        public Stub(PermissionEnforcer permissionEnforcer) {
            attachInterface(this, IDeviceStateManager.DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IDeviceStateManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IDeviceStateManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IDeviceStateManager)) {
                return (IDeviceStateManager) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getDeviceStateInfo";
                case 2:
                    return "registerCallback";
                case 3:
                    return "requestState";
                case 4:
                    return "cancelStateRequest";
                case 5:
                    return "requestBaseStateOverride";
                case 6:
                    return "cancelBaseStateOverride";
                case 7:
                    return "onStateRequestOverlayDismissed";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IDeviceStateManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDeviceStateManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    DeviceStateInfo deviceStateInfo = getDeviceStateInfo();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(deviceStateInfo, 1);
                    return true;
                case 2:
                    IDeviceStateManagerCallback iDeviceStateManagerCallbackAsInterface = IDeviceStateManagerCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    DeviceStateInfo deviceStateInfoRegisterCallback = registerCallback(iDeviceStateManagerCallbackAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(deviceStateInfoRegisterCallback, 1);
                    return true;
                case 3:
                    IBinder strongBinder = parcel.readStrongBinder();
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestState(strongBinder, i3, i4);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    cancelStateRequest();
                    parcel2.writeNoException();
                    return true;
                case 5:
                    IBinder strongBinder2 = parcel.readStrongBinder();
                    int i5 = parcel.readInt();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestBaseStateOverride(strongBinder2, i5, i6);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    cancelBaseStateOverride();
                    parcel2.writeNoException();
                    return true;
                case 7:
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onStateRequestOverlayDismissed(z);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IDeviceStateManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDeviceStateManager.DESCRIPTOR;
            }

            @Override // android.hardware.devicestate.IDeviceStateManager
            public DeviceStateInfo getDeviceStateInfo() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceStateManager.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (DeviceStateInfo) parcelObtain2.readTypedObject(DeviceStateInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.devicestate.IDeviceStateManager
            public DeviceStateInfo registerCallback(IDeviceStateManagerCallback iDeviceStateManagerCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceStateManager.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iDeviceStateManagerCallback);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (DeviceStateInfo) parcelObtain2.readTypedObject(DeviceStateInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.devicestate.IDeviceStateManager
            public void requestState(IBinder iBinder, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceStateManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.devicestate.IDeviceStateManager
            public void cancelStateRequest() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceStateManager.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.devicestate.IDeviceStateManager
            public void requestBaseStateOverride(IBinder iBinder, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceStateManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.devicestate.IDeviceStateManager
            public void cancelBaseStateOverride() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceStateManager.DESCRIPTOR);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.devicestate.IDeviceStateManager
            public void onStateRequestOverlayDismissed(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceStateManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        protected void onStateRequestOverlayDismissed_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CONTROL_DEVICE_STATE, getCallingPid(), getCallingUid());
        }
    }
}
