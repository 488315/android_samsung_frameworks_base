package com.samsung.android.onehandop;

import android.Manifest;
import android.app.ActivityThread;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PermissionEnforcer;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IOneHandOpWatcher extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.onehandop.IOneHandOpWatcher";

    public static class Default implements IOneHandOpWatcher {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.onehandop.IOneHandOpWatcher
        public void onInputFilterChanged() throws RemoteException {
        }

        @Override // com.samsung.android.onehandop.IOneHandOpWatcher
        public void onMagnificationSpecChanged() throws RemoteException {
        }
    }

    void onInputFilterChanged() throws RemoteException;

    void onMagnificationSpecChanged() throws RemoteException;

    public static abstract class Stub extends Binder implements IOneHandOpWatcher {
        static final int TRANSACTION_onInputFilterChanged = 2;
        static final int TRANSACTION_onMagnificationSpecChanged = 1;
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
            attachInterface(this, IOneHandOpWatcher.DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IOneHandOpWatcher asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IOneHandOpWatcher.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IOneHandOpWatcher)) {
                return (IOneHandOpWatcher) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onMagnificationSpecChanged";
            }
            if (i != 2) {
                return null;
            }
            return "onInputFilterChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IOneHandOpWatcher.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IOneHandOpWatcher.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                onMagnificationSpecChanged();
            } else if (i == 2) {
                onInputFilterChanged();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IOneHandOpWatcher {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IOneHandOpWatcher.DESCRIPTOR;
            }

            @Override // com.samsung.android.onehandop.IOneHandOpWatcher
            public void onMagnificationSpecChanged() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IOneHandOpWatcher.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.onehandop.IOneHandOpWatcher
            public void onInputFilterChanged() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IOneHandOpWatcher.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }

        protected void onMagnificationSpecChanged_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.WRITE_SECURE_SETTINGS, getCallingPid(), getCallingUid());
        }

        protected void onInputFilterChanged_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.WRITE_SECURE_SETTINGS, getCallingPid(), getCallingUid());
        }
    }
}
