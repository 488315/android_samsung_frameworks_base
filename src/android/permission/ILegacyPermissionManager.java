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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ILegacyPermissionManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ILegacyPermissionManager)) {
                return (ILegacyPermissionManager) iInterfaceQueryLocalInterface;
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
                    String string = parcel.readString();
                    String string2 = parcel.readString();
                    String string3 = parcel.readString();
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iCheckDeviceIdentifierAccess = checkDeviceIdentifierAccess(string, string2, string3, i3, i4);
                    parcel2.writeNoException();
                    parcel2.writeInt(iCheckDeviceIdentifierAccess);
                    return true;
                case 2:
                    String string4 = parcel.readString();
                    String string5 = parcel.readString();
                    String string6 = parcel.readString();
                    int i5 = parcel.readInt();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iCheckPhoneNumberAccess = checkPhoneNumberAccess(string4, string5, string6, i5, i6);
                    parcel2.writeNoException();
                    parcel2.writeInt(iCheckPhoneNumberAccess);
                    return true;
                case 3:
                    String[] strArrCreateStringArray = parcel.createStringArray();
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    grantDefaultPermissionsToEnabledCarrierApps(strArrCreateStringArray, i7);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    String[] strArrCreateStringArray2 = parcel.createStringArray();
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    grantDefaultPermissionsToEnabledImsServices(strArrCreateStringArray2, i8);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    String[] strArrCreateStringArray3 = parcel.createStringArray();
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    grantDefaultPermissionsToEnabledTelephonyDataServices(strArrCreateStringArray3, i9);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    String[] strArrCreateStringArray4 = parcel.createStringArray();
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    revokeDefaultPermissionsFromDisabledTelephonyDataServices(strArrCreateStringArray4, i10);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    String string7 = parcel.readString();
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    grantDefaultPermissionsToActiveLuiApp(string7, i11);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    String[] strArrCreateStringArray5 = parcel.createStringArray();
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    revokeDefaultPermissionsFromLuiApps(strArrCreateStringArray5, i12);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    String string8 = parcel.readString();
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    grantDefaultPermissionsToCarrierServiceApp(string8, i13);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ILegacyPermissionManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.permission.ILegacyPermissionManager
            public int checkPhoneNumberAccess(String str, String str2, String str3, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ILegacyPermissionManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.permission.ILegacyPermissionManager
            public void grantDefaultPermissionsToEnabledCarrierApps(String[] strArr, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ILegacyPermissionManager.DESCRIPTOR);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.permission.ILegacyPermissionManager
            public void grantDefaultPermissionsToEnabledImsServices(String[] strArr, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ILegacyPermissionManager.DESCRIPTOR);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.permission.ILegacyPermissionManager
            public void grantDefaultPermissionsToEnabledTelephonyDataServices(String[] strArr, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ILegacyPermissionManager.DESCRIPTOR);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.permission.ILegacyPermissionManager
            public void revokeDefaultPermissionsFromDisabledTelephonyDataServices(String[] strArr, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ILegacyPermissionManager.DESCRIPTOR);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.permission.ILegacyPermissionManager
            public void grantDefaultPermissionsToActiveLuiApp(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ILegacyPermissionManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.permission.ILegacyPermissionManager
            public void revokeDefaultPermissionsFromLuiApps(String[] strArr, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ILegacyPermissionManager.DESCRIPTOR);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.permission.ILegacyPermissionManager
            public void grantDefaultPermissionsToCarrierServiceApp(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ILegacyPermissionManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
