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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IOnAppsChangedListener)) {
                return (IOnAppsChangedListener) iInterfaceQueryLocalInterface;
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
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onPackageRemoved(userHandle, string);
                    return true;
                case 2:
                    UserHandle userHandle2 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onPackageAdded(userHandle2, string2);
                    return true;
                case 3:
                    UserHandle userHandle3 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onPackageChanged(userHandle3, string3);
                    return true;
                case 4:
                    UserHandle userHandle4 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    String[] strArrCreateStringArray = parcel.createStringArray();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onPackagesAvailable(userHandle4, strArrCreateStringArray, z);
                    return true;
                case 5:
                    UserHandle userHandle5 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    String[] strArrCreateStringArray2 = parcel.createStringArray();
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onPackagesUnavailable(userHandle5, strArrCreateStringArray2, z2);
                    return true;
                case 6:
                    UserHandle userHandle6 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    String[] strArrCreateStringArray3 = parcel.createStringArray();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPackagesSuspended(userHandle6, strArrCreateStringArray3, bundle);
                    return true;
                case 7:
                    UserHandle userHandle7 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    String[] strArrCreateStringArray4 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    onPackagesUnsuspended(userHandle7, strArrCreateStringArray4);
                    return true;
                case 8:
                    UserHandle userHandle8 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    String string4 = parcel.readString();
                    ParceledListSlice parceledListSlice = (ParceledListSlice) parcel.readTypedObject(ParceledListSlice.CREATOR);
                    parcel.enforceNoDataAvail();
                    onShortcutChanged(userHandle8, string4, parceledListSlice);
                    return true;
                case 9:
                    UserHandle userHandle9 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    String string5 = parcel.readString();
                    float f = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    onPackageLoadingProgressChanged(userHandle9, string5, f);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(userHandle, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IOnAppsChangedListener
            public void onPackageAdded(UserHandle userHandle, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(userHandle, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IOnAppsChangedListener
            public void onPackageChanged(UserHandle userHandle, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(userHandle, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IOnAppsChangedListener
            public void onPackagesAvailable(UserHandle userHandle, String[] strArr, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(userHandle, 0);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IOnAppsChangedListener
            public void onPackagesUnavailable(UserHandle userHandle, String[] strArr, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(userHandle, 0);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IOnAppsChangedListener
            public void onPackagesSuspended(UserHandle userHandle, String[] strArr, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(userHandle, 0);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IOnAppsChangedListener
            public void onPackagesUnsuspended(UserHandle userHandle, String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(userHandle, 0);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IOnAppsChangedListener
            public void onShortcutChanged(UserHandle userHandle, String str, ParceledListSlice parceledListSlice) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(userHandle, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(parceledListSlice, 0);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IOnAppsChangedListener
            public void onPackageLoadingProgressChanged(UserHandle userHandle, String str, float f) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(userHandle, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeFloat(f);
                    this.mRemote.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IOnAppsChangedListener
            public void onUserConfigChanged(LauncherUserInfo launcherUserInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(launcherUserInfo, 0);
                    this.mRemote.transact(10, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
