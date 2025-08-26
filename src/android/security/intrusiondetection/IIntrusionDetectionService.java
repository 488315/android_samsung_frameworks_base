package android.security.intrusiondetection;

import android.Manifest;
import android.app.ActivityThread;
import android.hardware.display.SemWifiDisplayParameter;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PermissionEnforcer;
import android.os.RemoteException;
import android.security.intrusiondetection.IIntrusionDetectionServiceCommandCallback;
import android.security.intrusiondetection.IIntrusionDetectionServiceStateCallback;

/* loaded from: classes3.dex */
public interface IIntrusionDetectionService extends IInterface {
    public static final String DESCRIPTOR = "android.security.intrusiondetection.IIntrusionDetectionService";

    public static class Default implements IIntrusionDetectionService {
        @Override // android.security.intrusiondetection.IIntrusionDetectionService
        public void addStateCallback(IIntrusionDetectionServiceStateCallback iIntrusionDetectionServiceStateCallback) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.security.intrusiondetection.IIntrusionDetectionService
        public void disable(IIntrusionDetectionServiceCommandCallback iIntrusionDetectionServiceCommandCallback) throws RemoteException {
        }

        @Override // android.security.intrusiondetection.IIntrusionDetectionService
        public void enable(IIntrusionDetectionServiceCommandCallback iIntrusionDetectionServiceCommandCallback) throws RemoteException {
        }

        @Override // android.security.intrusiondetection.IIntrusionDetectionService
        public void removeStateCallback(IIntrusionDetectionServiceStateCallback iIntrusionDetectionServiceStateCallback) throws RemoteException {
        }
    }

    void addStateCallback(IIntrusionDetectionServiceStateCallback iIntrusionDetectionServiceStateCallback) throws RemoteException;

    void disable(IIntrusionDetectionServiceCommandCallback iIntrusionDetectionServiceCommandCallback) throws RemoteException;

    void enable(IIntrusionDetectionServiceCommandCallback iIntrusionDetectionServiceCommandCallback) throws RemoteException;

    void removeStateCallback(IIntrusionDetectionServiceStateCallback iIntrusionDetectionServiceStateCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IIntrusionDetectionService {
        static final int TRANSACTION_addStateCallback = 1;
        static final int TRANSACTION_disable = 4;
        static final int TRANSACTION_enable = 3;
        static final int TRANSACTION_removeStateCallback = 2;
        private final PermissionEnforcer mEnforcer;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub(PermissionEnforcer permissionEnforcer) {
            attachInterface(this, IIntrusionDetectionService.DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IIntrusionDetectionService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IIntrusionDetectionService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IIntrusionDetectionService)) {
                return (IIntrusionDetectionService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "addStateCallback";
            }
            if (i == 2) {
                return "removeStateCallback";
            }
            if (i == 3) {
                return "enable";
            }
            if (i != 4) {
                return null;
            }
            return SemWifiDisplayParameter.VALUE_DISABLE;
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IIntrusionDetectionService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IIntrusionDetectionService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                IIntrusionDetectionServiceStateCallback iIntrusionDetectionServiceStateCallbackAsInterface = IIntrusionDetectionServiceStateCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                addStateCallback(iIntrusionDetectionServiceStateCallbackAsInterface);
                parcel2.writeNoException();
            } else if (i == 2) {
                IIntrusionDetectionServiceStateCallback iIntrusionDetectionServiceStateCallbackAsInterface2 = IIntrusionDetectionServiceStateCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                removeStateCallback(iIntrusionDetectionServiceStateCallbackAsInterface2);
                parcel2.writeNoException();
            } else if (i == 3) {
                IIntrusionDetectionServiceCommandCallback iIntrusionDetectionServiceCommandCallbackAsInterface = IIntrusionDetectionServiceCommandCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                enable(iIntrusionDetectionServiceCommandCallbackAsInterface);
                parcel2.writeNoException();
            } else if (i == 4) {
                IIntrusionDetectionServiceCommandCallback iIntrusionDetectionServiceCommandCallbackAsInterface2 = IIntrusionDetectionServiceCommandCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                disable(iIntrusionDetectionServiceCommandCallbackAsInterface2);
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IIntrusionDetectionService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IIntrusionDetectionService.DESCRIPTOR;
            }

            @Override // android.security.intrusiondetection.IIntrusionDetectionService
            public void addStateCallback(IIntrusionDetectionServiceStateCallback iIntrusionDetectionServiceStateCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIntrusionDetectionService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iIntrusionDetectionServiceStateCallback);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.intrusiondetection.IIntrusionDetectionService
            public void removeStateCallback(IIntrusionDetectionServiceStateCallback iIntrusionDetectionServiceStateCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIntrusionDetectionService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iIntrusionDetectionServiceStateCallback);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.intrusiondetection.IIntrusionDetectionService
            public void enable(IIntrusionDetectionServiceCommandCallback iIntrusionDetectionServiceCommandCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIntrusionDetectionService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iIntrusionDetectionServiceCommandCallback);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.intrusiondetection.IIntrusionDetectionService
            public void disable(IIntrusionDetectionServiceCommandCallback iIntrusionDetectionServiceCommandCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIntrusionDetectionService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iIntrusionDetectionServiceCommandCallback);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        protected void addStateCallback_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.READ_INTRUSION_DETECTION_STATE, getCallingPid(), getCallingUid());
        }

        protected void removeStateCallback_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.READ_INTRUSION_DETECTION_STATE, getCallingPid(), getCallingUid());
        }

        protected void enable_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_INTRUSION_DETECTION_STATE, getCallingPid(), getCallingUid());
        }

        protected void disable_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_INTRUSION_DETECTION_STATE, getCallingPid(), getCallingUid());
        }
    }
}
