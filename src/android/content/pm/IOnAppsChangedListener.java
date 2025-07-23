package android.content.pm;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.UserHandle;

/* loaded from: classes.dex */
public interface IOnAppsChangedListener extends IInterface {

    public static class Default implements IOnAppsChangedListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.content.pm.IOnAppsChangedListener
        public void onPackageAdded(UserHandle userHandle, String str) throws RemoteException {
        }

        @Override // android.content.pm.IOnAppsChangedListener
        public void onPackageChanged(UserHandle userHandle, String str) throws RemoteException {
        }

        @Override // android.content.pm.IOnAppsChangedListener
        public void onPackageLoadingProgressChanged(UserHandle userHandle, String str, float f) throws RemoteException {
        }

        @Override // android.content.pm.IOnAppsChangedListener
        public void onPackageRemoved(UserHandle userHandle, String str) throws RemoteException {
        }

        @Override // android.content.pm.IOnAppsChangedListener
        public void onPackagesAvailable(UserHandle userHandle, String[] strArr, boolean z) throws RemoteException {
        }

        @Override // android.content.pm.IOnAppsChangedListener
        public void onPackagesSuspended(UserHandle userHandle, String[] strArr, Bundle bundle) throws RemoteException {
        }

        @Override // android.content.pm.IOnAppsChangedListener
        public void onPackagesUnavailable(UserHandle userHandle, String[] strArr, boolean z) throws RemoteException {
        }

        @Override // android.content.pm.IOnAppsChangedListener
        public void onPackagesUnsuspended(UserHandle userHandle, String[] strArr) throws RemoteException {
        }

        @Override // android.content.pm.IOnAppsChangedListener
        public void onShortcutChanged(UserHandle userHandle, String str, ParceledListSlice parceledListSlice) throws RemoteException {
        }

        @Override // android.content.pm.IOnAppsChangedListener
        public void onUserConfigChanged(LauncherUserInfo launcherUserInfo) throws RemoteException {
        }
    }

    void onPackageAdded(UserHandle userHandle, String str) throws RemoteException;

    void onPackageChanged(UserHandle userHandle, String str) throws RemoteException;

    void onPackageLoadingProgressChanged(UserHandle userHandle, String str, float f) throws RemoteException;

    void onPackageRemoved(UserHandle userHandle, String str) throws RemoteException;

    void onPackagesAvailable(UserHandle userHandle, String[] strArr, boolean z) throws RemoteException;

    void onPackagesSuspended(UserHandle userHandle, String[] strArr, Bundle bundle) throws RemoteException;

    void onPackagesUnavailable(UserHandle userHandle, String[] strArr, boolean z) throws RemoteException;

    void onPackagesUnsuspended(UserHandle userHandle, String[] strArr) throws RemoteException;

    void onShortcutChanged(UserHandle userHandle, String str, ParceledListSlice parceledListSlice) throws RemoteException;

    void onUserConfigChanged(LauncherUserInfo launcherUserInfo) throws RemoteException;

    public static abstract class Stub extends Binder implements IOnAppsChangedListener {
        public static final String DESCRIPTOR = "android.content.pm.IOnAppsChangedListener";
        static final int TRANSACTION_onPackageAdded = 2;
        static final int TRANSACTION_onPackageChanged = 3;
        static final int TRANSACTION_onPackageLoadingProgressChanged = 9;
        static final int TRANSACTION_onPackageRemoved = 1;
        static final int TRANSACTION_onPackagesAvailable = 4;
        static final int TRANSACTION_onPackagesSuspended = 6;
        static final int TRANSACTION_onPackagesUnavailable = 5;
        static final int TRANSACTION_onPackagesUnsuspended = 7;
        static final int TRANSACTION_onShortcutChanged = 8;
        static final int TRANSACTION_onUserConfigChanged = 10;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 9;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IOnAppsChangedListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IOnAppsChangedListener)) {
                return (IOnAppsChangedListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onPackageRemoved";
                case 2:
                    return "onPackageAdded";
                case 3:
                    return "onPackageChanged";
                case 4:
                    return "onPackagesAvailable";
                case 5:
                    return "onPackagesUnavailable";
                case 6:
                    return "onPackagesSuspended";
                case 7:
                    return "onPackagesUnsuspended";
                case 8:
                    return "onShortcutChanged";
                case 9:
                    return "onPackageLoadingProgressChanged";
                case 10:
                    return "onUserConfigChanged";
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
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    UserHandle userHandle = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onPackageRemoved(userHandle, readString);
                    return true;
                case 2:
                    UserHandle userHandle2 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onPackageAdded(userHandle2, readString2);
                    return true;
                case 3:
                    UserHandle userHandle3 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onPackageChanged(userHandle3, readString3);
                    return true;
                case 4:
                    UserHandle userHandle4 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    String[] createStringArray = parcel.createStringArray();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onPackagesAvailable(userHandle4, createStringArray, readBoolean);
                    return true;
                case 5:
                    UserHandle userHandle5 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    String[] createStringArray2 = parcel.createStringArray();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onPackagesUnavailable(userHandle5, createStringArray2, readBoolean2);
                    return true;
                case 6:
                    UserHandle userHandle6 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    String[] createStringArray3 = parcel.createStringArray();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPackagesSuspended(userHandle6, createStringArray3, bundle);
                    return true;
                case 7:
                    UserHandle userHandle7 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    String[] createStringArray4 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    onPackagesUnsuspended(userHandle7, createStringArray4);
                    return true;
                case 8:
                    UserHandle userHandle8 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    String readString4 = parcel.readString();
                    ParceledListSlice parceledListSlice = (ParceledListSlice) parcel.readTypedObject(ParceledListSlice.CREATOR);
                    parcel.enforceNoDataAvail();
                    onShortcutChanged(userHandle8, readString4, parceledListSlice);
                    return true;
                case 9:
                    UserHandle userHandle9 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    String readString5 = parcel.readString();
                    float readFloat = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    onPackageLoadingProgressChanged(userHandle9, readString5, readFloat);
                    return true;
                case 10:
                    LauncherUserInfo launcherUserInfo = (LauncherUserInfo) parcel.readTypedObject(LauncherUserInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onUserConfigChanged(launcherUserInfo);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IOnAppsChangedListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.content.pm.IOnAppsChangedListener
            public void onPackageRemoved(UserHandle userHandle, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IOnAppsChangedListener
            public void onPackageAdded(UserHandle userHandle, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IOnAppsChangedListener
            public void onPackageChanged(UserHandle userHandle, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IOnAppsChangedListener
            public void onPackagesAvailable(UserHandle userHandle, String[] strArr, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeStringArray(strArr);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IOnAppsChangedListener
            public void onPackagesUnavailable(UserHandle userHandle, String[] strArr, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeStringArray(strArr);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IOnAppsChangedListener
            public void onPackagesSuspended(UserHandle userHandle, String[] strArr, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeStringArray(strArr);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IOnAppsChangedListener
            public void onPackagesUnsuspended(UserHandle userHandle, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IOnAppsChangedListener
            public void onShortcutChanged(UserHandle userHandle, String str, ParceledListSlice parceledListSlice) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(parceledListSlice, 0);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IOnAppsChangedListener
            public void onPackageLoadingProgressChanged(UserHandle userHandle, String str, float f) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeString(str);
                    obtain.writeFloat(f);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IOnAppsChangedListener
            public void onUserConfigChanged(LauncherUserInfo launcherUserInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(launcherUserInfo, 0);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
