package android.permission;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface ILegacyPermissionManager extends IInterface {
    public static final String DESCRIPTOR = "android.permission.ILegacyPermissionManager";

    public static class Default implements ILegacyPermissionManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.permission.ILegacyPermissionManager
        public int checkDeviceIdentifierAccess(String str, String str2, String str3, int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // android.permission.ILegacyPermissionManager
        public int checkPhoneNumberAccess(String str, String str2, String str3, int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // android.permission.ILegacyPermissionManager
        public void grantDefaultPermissionsToActiveLuiApp(String str, int i) throws RemoteException {
        }

        @Override // android.permission.ILegacyPermissionManager
        public void grantDefaultPermissionsToCarrierServiceApp(String str, int i) throws RemoteException {
        }

        @Override // android.permission.ILegacyPermissionManager
        public void grantDefaultPermissionsToEnabledCarrierApps(String[] strArr, int i) throws RemoteException {
        }

        @Override // android.permission.ILegacyPermissionManager
        public void grantDefaultPermissionsToEnabledImsServices(String[] strArr, int i) throws RemoteException {
        }

        @Override // android.permission.ILegacyPermissionManager
        public void grantDefaultPermissionsToEnabledTelephonyDataServices(String[] strArr, int i) throws RemoteException {
        }

        @Override // android.permission.ILegacyPermissionManager
        public void revokeDefaultPermissionsFromDisabledTelephonyDataServices(String[] strArr, int i) throws RemoteException {
        }

        @Override // android.permission.ILegacyPermissionManager
        public void revokeDefaultPermissionsFromLuiApps(String[] strArr, int i) throws RemoteException {
        }
    }

    int checkDeviceIdentifierAccess(String str, String str2, String str3, int i, int i2) throws RemoteException;

    int checkPhoneNumberAccess(String str, String str2, String str3, int i, int i2) throws RemoteException;

    void grantDefaultPermissionsToActiveLuiApp(String str, int i) throws RemoteException;

    void grantDefaultPermissionsToCarrierServiceApp(String str, int i) throws RemoteException;

    void grantDefaultPermissionsToEnabledCarrierApps(String[] strArr, int i) throws RemoteException;

    void grantDefaultPermissionsToEnabledImsServices(String[] strArr, int i) throws RemoteException;

    void grantDefaultPermissionsToEnabledTelephonyDataServices(String[] strArr, int i) throws RemoteException;

    void revokeDefaultPermissionsFromDisabledTelephonyDataServices(String[] strArr, int i) throws RemoteException;

    void revokeDefaultPermissionsFromLuiApps(String[] strArr, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements ILegacyPermissionManager {
        static final int TRANSACTION_checkDeviceIdentifierAccess = 1;
        static final int TRANSACTION_checkPhoneNumberAccess = 2;
        static final int TRANSACTION_grantDefaultPermissionsToActiveLuiApp = 7;
        static final int TRANSACTION_grantDefaultPermissionsToCarrierServiceApp = 9;
        static final int TRANSACTION_grantDefaultPermissionsToEnabledCarrierApps = 3;
        static final int TRANSACTION_grantDefaultPermissionsToEnabledImsServices = 4;
        static final int TRANSACTION_grantDefaultPermissionsToEnabledTelephonyDataServices = 5;
        static final int TRANSACTION_revokeDefaultPermissionsFromDisabledTelephonyDataServices = 6;
        static final int TRANSACTION_revokeDefaultPermissionsFromLuiApps = 8;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 8;
        }

        public Stub() {
            attachInterface(this, ILegacyPermissionManager.DESCRIPTOR);
        }

        public static ILegacyPermissionManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ILegacyPermissionManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ILegacyPermissionManager)) {
                return (ILegacyPermissionManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "checkDeviceIdentifierAccess";
                case 2:
                    return "checkPhoneNumberAccess";
                case 3:
                    return "grantDefaultPermissionsToEnabledCarrierApps";
                case 4:
                    return "grantDefaultPermissionsToEnabledImsServices";
                case 5:
                    return "grantDefaultPermissionsToEnabledTelephonyDataServices";
                case 6:
                    return "revokeDefaultPermissionsFromDisabledTelephonyDataServices";
                case 7:
                    return "grantDefaultPermissionsToActiveLuiApp";
                case 8:
                    return "revokeDefaultPermissionsFromLuiApps";
                case 9:
                    return "grantDefaultPermissionsToCarrierServiceApp";
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
                parcel.enforceInterface(ILegacyPermissionManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ILegacyPermissionManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String readString = parcel.readString();
                    String readString2 = parcel.readString();
                    String readString3 = parcel.readString();
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int checkDeviceIdentifierAccess = checkDeviceIdentifierAccess(readString, readString2, readString3, readInt, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeInt(checkDeviceIdentifierAccess);
                    return true;
                case 2:
                    String readString4 = parcel.readString();
                    String readString5 = parcel.readString();
                    String readString6 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int checkPhoneNumberAccess = checkPhoneNumberAccess(readString4, readString5, readString6, readInt3, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeInt(checkPhoneNumberAccess);
                    return true;
                case 3:
                    String[] createStringArray = parcel.createStringArray();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    grantDefaultPermissionsToEnabledCarrierApps(createStringArray, readInt5);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    String[] createStringArray2 = parcel.createStringArray();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    grantDefaultPermissionsToEnabledImsServices(createStringArray2, readInt6);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    String[] createStringArray3 = parcel.createStringArray();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    grantDefaultPermissionsToEnabledTelephonyDataServices(createStringArray3, readInt7);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    String[] createStringArray4 = parcel.createStringArray();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    revokeDefaultPermissionsFromDisabledTelephonyDataServices(createStringArray4, readInt8);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    String readString7 = parcel.readString();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    grantDefaultPermissionsToActiveLuiApp(readString7, readInt9);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    String[] createStringArray5 = parcel.createStringArray();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    revokeDefaultPermissionsFromLuiApps(createStringArray5, readInt10);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    String readString8 = parcel.readString();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    grantDefaultPermissionsToCarrierServiceApp(readString8, readInt11);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ILegacyPermissionManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ILegacyPermissionManager.DESCRIPTOR;
            }

            @Override // android.permission.ILegacyPermissionManager
            public int checkDeviceIdentifierAccess(String str, String str2, String str3, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ILegacyPermissionManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.ILegacyPermissionManager
            public int checkPhoneNumberAccess(String str, String str2, String str3, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ILegacyPermissionManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.ILegacyPermissionManager
            public void grantDefaultPermissionsToEnabledCarrierApps(String[] strArr, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ILegacyPermissionManager.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.ILegacyPermissionManager
            public void grantDefaultPermissionsToEnabledImsServices(String[] strArr, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ILegacyPermissionManager.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.ILegacyPermissionManager
            public void grantDefaultPermissionsToEnabledTelephonyDataServices(String[] strArr, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ILegacyPermissionManager.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.ILegacyPermissionManager
            public void revokeDefaultPermissionsFromDisabledTelephonyDataServices(String[] strArr, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ILegacyPermissionManager.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.ILegacyPermissionManager
            public void grantDefaultPermissionsToActiveLuiApp(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ILegacyPermissionManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.ILegacyPermissionManager
            public void revokeDefaultPermissionsFromLuiApps(String[] strArr, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ILegacyPermissionManager.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.ILegacyPermissionManager
            public void grantDefaultPermissionsToCarrierServiceApp(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ILegacyPermissionManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
