package android.permission;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.UserHandle;
import com.android.internal.infra.AndroidFuture;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public interface IPermissionController extends IInterface {
    public static final String DESCRIPTOR = "android.permission.IPermissionController";

    public static class Default implements IPermissionController {
        @Override // android.permission.IPermissionController
        public void applyStagedRuntimePermissionBackup(String str, UserHandle userHandle, AndroidFuture androidFuture) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.permission.IPermissionController
        public void countPermissionApps(List<String> list, int i, AndroidFuture androidFuture) throws RemoteException {
        }

        @Override // android.permission.IPermissionController
        public void getAppPermissions(String str, AndroidFuture androidFuture) throws RemoteException {
        }

        @Override // android.permission.IPermissionController
        public void getGroupOfPlatformPermission(String str, AndroidFuture<String> androidFuture) throws RemoteException {
        }

        @Override // android.permission.IPermissionController
        public void getHibernationEligibility(String str, AndroidFuture androidFuture) throws RemoteException {
        }

        @Override // android.permission.IPermissionController
        public void getPermissionUsages(boolean z, long j, AndroidFuture androidFuture) throws RemoteException {
        }

        @Override // android.permission.IPermissionController
        public void getPlatformPermissionsForGroup(String str, AndroidFuture<List<String>> androidFuture) throws RemoteException {
        }

        @Override // android.permission.IPermissionController
        public void getPrivilegesDescriptionStringForProfile(String str, AndroidFuture<String> androidFuture) throws RemoteException {
        }

        @Override // android.permission.IPermissionController
        public void getRuntimePermissionBackup(UserHandle userHandle, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
        }

        @Override // android.permission.IPermissionController
        public void getUnusedAppCount(AndroidFuture androidFuture) throws RemoteException {
        }

        @Override // android.permission.IPermissionController
        public void grantOrUpgradeDefaultRuntimePermissions(AndroidFuture androidFuture) throws RemoteException {
        }

        @Override // android.permission.IPermissionController
        public void notifyOneTimePermissionSessionTimeout(String str, int i) throws RemoteException {
        }

        @Override // android.permission.IPermissionController
        public void revokeRuntimePermission(String str, String str2) throws RemoteException {
        }

        @Override // android.permission.IPermissionController
        public void revokeRuntimePermissions(Bundle bundle, boolean z, int i, String str, AndroidFuture androidFuture) throws RemoteException {
        }

        @Override // android.permission.IPermissionController
        public void revokeSelfPermissionsOnKill(String str, List<String> list, int i, AndroidFuture androidFuture) throws RemoteException {
        }

        @Override // android.permission.IPermissionController
        public void setRuntimePermissionGrantStateByDeviceAdminFromParams(String str, AdminPermissionControlParams adminPermissionControlParams, AndroidFuture androidFuture) throws RemoteException {
        }

        @Override // android.permission.IPermissionController
        public void stageAndApplyRuntimePermissionsBackup(UserHandle userHandle, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
        }

        @Override // android.permission.IPermissionController
        public void updateUserSensitiveForApp(int i, AndroidFuture androidFuture) throws RemoteException {
        }
    }

    void applyStagedRuntimePermissionBackup(String str, UserHandle userHandle, AndroidFuture androidFuture) throws RemoteException;

    void countPermissionApps(List<String> list, int i, AndroidFuture androidFuture) throws RemoteException;

    void getAppPermissions(String str, AndroidFuture androidFuture) throws RemoteException;

    void getGroupOfPlatformPermission(String str, AndroidFuture<String> androidFuture) throws RemoteException;

    void getHibernationEligibility(String str, AndroidFuture androidFuture) throws RemoteException;

    void getPermissionUsages(boolean z, long j, AndroidFuture androidFuture) throws RemoteException;

    void getPlatformPermissionsForGroup(String str, AndroidFuture<List<String>> androidFuture) throws RemoteException;

    void getPrivilegesDescriptionStringForProfile(String str, AndroidFuture<String> androidFuture) throws RemoteException;

    void getRuntimePermissionBackup(UserHandle userHandle, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException;

    void getUnusedAppCount(AndroidFuture androidFuture) throws RemoteException;

    void grantOrUpgradeDefaultRuntimePermissions(AndroidFuture androidFuture) throws RemoteException;

    void notifyOneTimePermissionSessionTimeout(String str, int i) throws RemoteException;

    void revokeRuntimePermission(String str, String str2) throws RemoteException;

    void revokeRuntimePermissions(Bundle bundle, boolean z, int i, String str, AndroidFuture androidFuture) throws RemoteException;

    void revokeSelfPermissionsOnKill(String str, List<String> list, int i, AndroidFuture androidFuture) throws RemoteException;

    void setRuntimePermissionGrantStateByDeviceAdminFromParams(String str, AdminPermissionControlParams adminPermissionControlParams, AndroidFuture androidFuture) throws RemoteException;

    void stageAndApplyRuntimePermissionsBackup(UserHandle userHandle, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException;

    void updateUserSensitiveForApp(int i, AndroidFuture androidFuture) throws RemoteException;

    public static abstract class Stub extends Binder implements IPermissionController {
        static final int TRANSACTION_applyStagedRuntimePermissionBackup = 4;
        static final int TRANSACTION_countPermissionApps = 7;
        static final int TRANSACTION_getAppPermissions = 5;
        static final int TRANSACTION_getGroupOfPlatformPermission = 15;
        static final int TRANSACTION_getHibernationEligibility = 17;
        static final int TRANSACTION_getPermissionUsages = 8;
        static final int TRANSACTION_getPlatformPermissionsForGroup = 14;
        static final int TRANSACTION_getPrivilegesDescriptionStringForProfile = 13;
        static final int TRANSACTION_getRuntimePermissionBackup = 2;
        static final int TRANSACTION_getUnusedAppCount = 16;
        static final int TRANSACTION_grantOrUpgradeDefaultRuntimePermissions = 10;
        static final int TRANSACTION_notifyOneTimePermissionSessionTimeout = 11;
        static final int TRANSACTION_revokeRuntimePermission = 6;
        static final int TRANSACTION_revokeRuntimePermissions = 1;
        static final int TRANSACTION_revokeSelfPermissionsOnKill = 18;
        static final int TRANSACTION_setRuntimePermissionGrantStateByDeviceAdminFromParams = 9;
        static final int TRANSACTION_stageAndApplyRuntimePermissionsBackup = 3;
        static final int TRANSACTION_updateUserSensitiveForApp = 12;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 17;
        }

        public Stub() {
            attachInterface(this, IPermissionController.DESCRIPTOR);
        }

        public static IPermissionController asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IPermissionController.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IPermissionController)) {
                return (IPermissionController) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "revokeRuntimePermissions";
                case 2:
                    return "getRuntimePermissionBackup";
                case 3:
                    return "stageAndApplyRuntimePermissionsBackup";
                case 4:
                    return "applyStagedRuntimePermissionBackup";
                case 5:
                    return "getAppPermissions";
                case 6:
                    return "revokeRuntimePermission";
                case 7:
                    return "countPermissionApps";
                case 8:
                    return "getPermissionUsages";
                case 9:
                    return "setRuntimePermissionGrantStateByDeviceAdminFromParams";
                case 10:
                    return "grantOrUpgradeDefaultRuntimePermissions";
                case 11:
                    return "notifyOneTimePermissionSessionTimeout";
                case 12:
                    return "updateUserSensitiveForApp";
                case 13:
                    return "getPrivilegesDescriptionStringForProfile";
                case 14:
                    return "getPlatformPermissionsForGroup";
                case 15:
                    return "getGroupOfPlatformPermission";
                case 16:
                    return "getUnusedAppCount";
                case 17:
                    return "getHibernationEligibility";
                case 18:
                    return "revokeSelfPermissionsOnKill";
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
                parcel.enforceInterface(IPermissionController.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IPermissionController.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    int readInt = parcel.readInt();
                    String readString = parcel.readString();
                    AndroidFuture androidFuture = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    revokeRuntimePermissions(bundle, readBoolean, readInt, readString, androidFuture);
                    return true;
                case 2:
                    UserHandle userHandle = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    parcel.enforceNoDataAvail();
                    getRuntimePermissionBackup(userHandle, parcelFileDescriptor);
                    return true;
                case 3:
                    UserHandle userHandle2 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    ParcelFileDescriptor parcelFileDescriptor2 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    parcel.enforceNoDataAvail();
                    stageAndApplyRuntimePermissionsBackup(userHandle2, parcelFileDescriptor2);
                    return true;
                case 4:
                    String readString2 = parcel.readString();
                    UserHandle userHandle3 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    AndroidFuture androidFuture2 = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    applyStagedRuntimePermissionBackup(readString2, userHandle3, androidFuture2);
                    return true;
                case 5:
                    String readString3 = parcel.readString();
                    AndroidFuture androidFuture3 = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    getAppPermissions(readString3, androidFuture3);
                    return true;
                case 6:
                    String readString4 = parcel.readString();
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    revokeRuntimePermission(readString4, readString5);
                    return true;
                case 7:
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    int readInt2 = parcel.readInt();
                    AndroidFuture androidFuture4 = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    countPermissionApps(createStringArrayList, readInt2, androidFuture4);
                    return true;
                case 8:
                    boolean readBoolean2 = parcel.readBoolean();
                    long readLong = parcel.readLong();
                    AndroidFuture androidFuture5 = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    getPermissionUsages(readBoolean2, readLong, androidFuture5);
                    return true;
                case 9:
                    String readString6 = parcel.readString();
                    AdminPermissionControlParams adminPermissionControlParams = (AdminPermissionControlParams) parcel.readTypedObject(AdminPermissionControlParams.CREATOR);
                    AndroidFuture androidFuture6 = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    setRuntimePermissionGrantStateByDeviceAdminFromParams(readString6, adminPermissionControlParams, androidFuture6);
                    return true;
                case 10:
                    AndroidFuture androidFuture7 = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    grantOrUpgradeDefaultRuntimePermissions(androidFuture7);
                    return true;
                case 11:
                    String readString7 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyOneTimePermissionSessionTimeout(readString7, readInt3);
                    return true;
                case 12:
                    int readInt4 = parcel.readInt();
                    AndroidFuture androidFuture8 = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateUserSensitiveForApp(readInt4, androidFuture8);
                    return true;
                case 13:
                    String readString8 = parcel.readString();
                    AndroidFuture<String> androidFuture9 = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    getPrivilegesDescriptionStringForProfile(readString8, androidFuture9);
                    return true;
                case 14:
                    String readString9 = parcel.readString();
                    AndroidFuture<List<String>> androidFuture10 = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    getPlatformPermissionsForGroup(readString9, androidFuture10);
                    return true;
                case 15:
                    String readString10 = parcel.readString();
                    AndroidFuture<String> androidFuture11 = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    getGroupOfPlatformPermission(readString10, androidFuture11);
                    return true;
                case 16:
                    AndroidFuture androidFuture12 = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    getUnusedAppCount(androidFuture12);
                    return true;
                case 17:
                    String readString11 = parcel.readString();
                    AndroidFuture androidFuture13 = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    getHibernationEligibility(readString11, androidFuture13);
                    return true;
                case 18:
                    String readString12 = parcel.readString();
                    ArrayList<String> createStringArrayList2 = parcel.createStringArrayList();
                    int readInt5 = parcel.readInt();
                    AndroidFuture androidFuture14 = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    revokeSelfPermissionsOnKill(readString12, createStringArrayList2, readInt5, androidFuture14);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IPermissionController {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IPermissionController.DESCRIPTOR;
            }

            @Override // android.permission.IPermissionController
            public void revokeRuntimePermissions(Bundle bundle, boolean z, int i, String str, AndroidFuture androidFuture) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IPermissionController.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionController
            public void getRuntimePermissionBackup(UserHandle userHandle, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IPermissionController.DESCRIPTOR);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionController
            public void stageAndApplyRuntimePermissionsBackup(UserHandle userHandle, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IPermissionController.DESCRIPTOR);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionController
            public void applyStagedRuntimePermissionBackup(String str, UserHandle userHandle, AndroidFuture androidFuture) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IPermissionController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionController
            public void getAppPermissions(String str, AndroidFuture androidFuture) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IPermissionController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionController
            public void revokeRuntimePermission(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IPermissionController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionController
            public void countPermissionApps(List<String> list, int i, AndroidFuture androidFuture) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IPermissionController.DESCRIPTOR);
                    obtain.writeStringList(list);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionController
            public void getPermissionUsages(boolean z, long j, AndroidFuture androidFuture) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IPermissionController.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeLong(j);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionController
            public void setRuntimePermissionGrantStateByDeviceAdminFromParams(String str, AdminPermissionControlParams adminPermissionControlParams, AndroidFuture androidFuture) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IPermissionController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(adminPermissionControlParams, 0);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionController
            public void grantOrUpgradeDefaultRuntimePermissions(AndroidFuture androidFuture) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IPermissionController.DESCRIPTOR);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionController
            public void notifyOneTimePermissionSessionTimeout(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IPermissionController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionController
            public void updateUserSensitiveForApp(int i, AndroidFuture androidFuture) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IPermissionController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionController
            public void getPrivilegesDescriptionStringForProfile(String str, AndroidFuture<String> androidFuture) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IPermissionController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionController
            public void getPlatformPermissionsForGroup(String str, AndroidFuture<List<String>> androidFuture) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IPermissionController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionController
            public void getGroupOfPlatformPermission(String str, AndroidFuture<String> androidFuture) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IPermissionController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionController
            public void getUnusedAppCount(AndroidFuture androidFuture) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IPermissionController.DESCRIPTOR);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionController
            public void getHibernationEligibility(String str, AndroidFuture androidFuture) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IPermissionController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(17, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionController
            public void revokeSelfPermissionsOnKill(String str, List<String> list, int i, AndroidFuture androidFuture) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IPermissionController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStringList(list);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(18, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
