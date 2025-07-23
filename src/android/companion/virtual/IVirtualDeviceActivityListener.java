package android.companion.virtual;

import android.content.ComponentName;
import android.content.IntentSender;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.UserHandle;

/* loaded from: classes.dex */
public interface IVirtualDeviceActivityListener extends IInterface {
    public static final String DESCRIPTOR = "android.companion.virtual.IVirtualDeviceActivityListener";

    public static class Default implements IVirtualDeviceActivityListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.companion.virtual.IVirtualDeviceActivityListener
        public void onActivityLaunchBlocked(int i, ComponentName componentName, UserHandle userHandle, IntentSender intentSender) throws RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDeviceActivityListener
        public void onDisplayEmpty(int i) throws RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDeviceActivityListener
        public void onSecureWindowHidden(int i) throws RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDeviceActivityListener
        public void onSecureWindowShown(int i, ComponentName componentName, UserHandle userHandle) throws RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDeviceActivityListener
        public void onTopActivityChanged(int i, ComponentName componentName, int i2) throws RemoteException {
        }
    }

    void onActivityLaunchBlocked(int i, ComponentName componentName, UserHandle userHandle, IntentSender intentSender) throws RemoteException;

    void onDisplayEmpty(int i) throws RemoteException;

    void onSecureWindowHidden(int i) throws RemoteException;

    void onSecureWindowShown(int i, ComponentName componentName, UserHandle userHandle) throws RemoteException;

    void onTopActivityChanged(int i, ComponentName componentName, int i2) throws RemoteException;

    public static abstract class Stub extends Binder implements IVirtualDeviceActivityListener {
        static final int TRANSACTION_onActivityLaunchBlocked = 3;
        static final int TRANSACTION_onDisplayEmpty = 2;
        static final int TRANSACTION_onSecureWindowHidden = 5;
        static final int TRANSACTION_onSecureWindowShown = 4;
        static final int TRANSACTION_onTopActivityChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }

        public Stub() {
            attachInterface(this, IVirtualDeviceActivityListener.DESCRIPTOR);
        }

        public static IVirtualDeviceActivityListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IVirtualDeviceActivityListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IVirtualDeviceActivityListener)) {
                return (IVirtualDeviceActivityListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onTopActivityChanged";
            }
            if (i == 2) {
                return "onDisplayEmpty";
            }
            if (i == 3) {
                return "onActivityLaunchBlocked";
            }
            if (i == 4) {
                return "onSecureWindowShown";
            }
            if (i != 5) {
                return null;
            }
            return "onSecureWindowHidden";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IVirtualDeviceActivityListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IVirtualDeviceActivityListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onTopActivityChanged(readInt, componentName, readInt2);
            } else if (i == 2) {
                int readInt3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onDisplayEmpty(readInt3);
            } else if (i == 3) {
                int readInt4 = parcel.readInt();
                ComponentName componentName2 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                UserHandle userHandle = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                IntentSender intentSender = (IntentSender) parcel.readTypedObject(IntentSender.CREATOR);
                parcel.enforceNoDataAvail();
                onActivityLaunchBlocked(readInt4, componentName2, userHandle, intentSender);
            } else if (i == 4) {
                int readInt5 = parcel.readInt();
                ComponentName componentName3 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                UserHandle userHandle2 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                parcel.enforceNoDataAvail();
                onSecureWindowShown(readInt5, componentName3, userHandle2);
            } else if (i == 5) {
                int readInt6 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onSecureWindowHidden(readInt6);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IVirtualDeviceActivityListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IVirtualDeviceActivityListener.DESCRIPTOR;
            }

            @Override // android.companion.virtual.IVirtualDeviceActivityListener
            public void onTopActivityChanged(int i, ComponentName componentName, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IVirtualDeviceActivityListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceActivityListener
            public void onDisplayEmpty(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IVirtualDeviceActivityListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceActivityListener
            public void onActivityLaunchBlocked(int i, ComponentName componentName, UserHandle userHandle, IntentSender intentSender) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IVirtualDeviceActivityListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeTypedObject(intentSender, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceActivityListener
            public void onSecureWindowShown(int i, ComponentName componentName, UserHandle userHandle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IVirtualDeviceActivityListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceActivityListener
            public void onSecureWindowHidden(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IVirtualDeviceActivityListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
