package com.samsung.android.core;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

/* loaded from: classes6.dex */
public interface ICompatChangeableManager extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.core.ICompatChangeableManager";

    public static class Default implements ICompatChangeableManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.core.ICompatChangeableManager
        public List<String> getCompatChangeablePackageNameList() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.core.ICompatChangeableManager
        public int getUid(String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.core.ICompatChangeableManager
        public boolean hasGameCategory(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.core.ICompatChangeableManager
        public boolean hasLauncherActivity(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.core.ICompatChangeableManager
        public boolean isMinAspectRatioOverrideDisallowed(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.core.ICompatChangeableManager
        public boolean isOrientationOverrideDisallowed(String str) throws RemoteException {
            return false;
        }
    }

    List<String> getCompatChangeablePackageNameList() throws RemoteException;

    int getUid(String str) throws RemoteException;

    boolean hasGameCategory(String str) throws RemoteException;

    boolean hasLauncherActivity(String str) throws RemoteException;

    boolean isMinAspectRatioOverrideDisallowed(String str) throws RemoteException;

    boolean isOrientationOverrideDisallowed(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements ICompatChangeableManager {
        static final int TRANSACTION_getCompatChangeablePackageNameList = 2;
        static final int TRANSACTION_getUid = 3;
        static final int TRANSACTION_hasGameCategory = 5;
        static final int TRANSACTION_hasLauncherActivity = 4;
        static final int TRANSACTION_isMinAspectRatioOverrideDisallowed = 102;
        static final int TRANSACTION_isOrientationOverrideDisallowed = 101;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 101;
        }

        public Stub() {
            attachInterface(this, ICompatChangeableManager.DESCRIPTOR);
        }

        public static ICompatChangeableManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ICompatChangeableManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ICompatChangeableManager)) {
                return (ICompatChangeableManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 2) {
                return "getCompatChangeablePackageNameList";
            }
            if (i == 3) {
                return "getUid";
            }
            if (i == 4) {
                return "hasLauncherActivity";
            }
            if (i == 5) {
                return "hasGameCategory";
            }
            if (i == 101) {
                return "isOrientationOverrideDisallowed";
            }
            if (i != 102) {
                return null;
            }
            return "isMinAspectRatioOverrideDisallowed";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ICompatChangeableManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ICompatChangeableManager.DESCRIPTOR);
                return true;
            }
            if (i == 2) {
                List<String> compatChangeablePackageNameList = getCompatChangeablePackageNameList();
                parcel2.writeNoException();
                parcel2.writeStringList(compatChangeablePackageNameList);
            } else if (i == 3) {
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                int uid = getUid(readString);
                parcel2.writeNoException();
                parcel2.writeInt(uid);
            } else if (i == 4) {
                String readString2 = parcel.readString();
                parcel.enforceNoDataAvail();
                boolean hasLauncherActivity = hasLauncherActivity(readString2);
                parcel2.writeNoException();
                parcel2.writeBoolean(hasLauncherActivity);
            } else if (i == 5) {
                String readString3 = parcel.readString();
                parcel.enforceNoDataAvail();
                boolean hasGameCategory = hasGameCategory(readString3);
                parcel2.writeNoException();
                parcel2.writeBoolean(hasGameCategory);
            } else if (i == 101) {
                String readString4 = parcel.readString();
                parcel.enforceNoDataAvail();
                boolean isOrientationOverrideDisallowed = isOrientationOverrideDisallowed(readString4);
                parcel2.writeNoException();
                parcel2.writeBoolean(isOrientationOverrideDisallowed);
            } else if (i == 102) {
                String readString5 = parcel.readString();
                parcel.enforceNoDataAvail();
                boolean isMinAspectRatioOverrideDisallowed = isMinAspectRatioOverrideDisallowed(readString5);
                parcel2.writeNoException();
                parcel2.writeBoolean(isMinAspectRatioOverrideDisallowed);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ICompatChangeableManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICompatChangeableManager.DESCRIPTOR;
            }

            @Override // com.samsung.android.core.ICompatChangeableManager
            public List<String> getCompatChangeablePackageNameList() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICompatChangeableManager.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.core.ICompatChangeableManager
            public int getUid(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICompatChangeableManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.core.ICompatChangeableManager
            public boolean hasLauncherActivity(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICompatChangeableManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.core.ICompatChangeableManager
            public boolean hasGameCategory(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICompatChangeableManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.core.ICompatChangeableManager
            public boolean isOrientationOverrideDisallowed(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICompatChangeableManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(101, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.core.ICompatChangeableManager
            public boolean isMinAspectRatioOverrideDisallowed(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICompatChangeableManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(102, obtain, obtain2, 0);
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
