package com.samsung.android.knox.multiuser;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.knox.ContextInfo;

/* loaded from: classes4.dex */
public interface IMultiUserManager extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.multiuser.IMultiUserManager";

    public class Default implements IMultiUserManager {
        @Override // com.samsung.android.knox.multiuser.IMultiUserManager
        public int allowMultipleUsers(ContextInfo contextInfo, boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.multiuser.IMultiUserManager
        public boolean allowUserCreation(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.multiuser.IMultiUserManager
        public boolean allowUserRemoval(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.multiuser.IMultiUserManager
        public int createUser(ContextInfo contextInfo, String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.multiuser.IMultiUserManager
        public int[] getUsers(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.multiuser.IMultiUserManager
        public boolean isUserCreationAllowed(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.multiuser.IMultiUserManager
        public boolean isUserRemovalAllowed(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.multiuser.IMultiUserManager
        public int multipleUsersAllowed(ContextInfo contextInfo, boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.multiuser.IMultiUserManager
        public boolean multipleUsersSupported(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.multiuser.IMultiUserManager
        public boolean removeUser(ContextInfo contextInfo, int i) throws RemoteException {
            return false;
        }
    }

    int allowMultipleUsers(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean allowUserCreation(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean allowUserRemoval(ContextInfo contextInfo, boolean z) throws RemoteException;

    int createUser(ContextInfo contextInfo, String str) throws RemoteException;

    int[] getUsers(ContextInfo contextInfo) throws RemoteException;

    boolean isUserCreationAllowed(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean isUserRemovalAllowed(ContextInfo contextInfo, boolean z) throws RemoteException;

    int multipleUsersAllowed(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean multipleUsersSupported(ContextInfo contextInfo) throws RemoteException;

    boolean removeUser(ContextInfo contextInfo, int i) throws RemoteException;

    public abstract class Stub extends Binder implements IMultiUserManager {
        public static final int TRANSACTION_allowMultipleUsers = 3;
        public static final int TRANSACTION_allowUserCreation = 7;
        public static final int TRANSACTION_allowUserRemoval = 9;
        public static final int TRANSACTION_createUser = 4;
        public static final int TRANSACTION_getUsers = 6;
        public static final int TRANSACTION_isUserCreationAllowed = 8;
        public static final int TRANSACTION_isUserRemovalAllowed = 10;
        public static final int TRANSACTION_multipleUsersAllowed = 2;
        public static final int TRANSACTION_multipleUsersSupported = 1;
        public static final int TRANSACTION_removeUser = 5;

        class Proxy implements IMultiUserManager {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.multiuser.IMultiUserManager
            public int allowMultipleUsers(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiUserManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.multiuser.IMultiUserManager
            public boolean allowUserCreation(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiUserManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.multiuser.IMultiUserManager
            public boolean allowUserRemoval(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiUserManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.multiuser.IMultiUserManager
            public int createUser(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiUserManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IMultiUserManager.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.multiuser.IMultiUserManager
            public int[] getUsers(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiUserManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.multiuser.IMultiUserManager
            public boolean isUserCreationAllowed(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiUserManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.multiuser.IMultiUserManager
            public boolean isUserRemovalAllowed(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiUserManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.multiuser.IMultiUserManager
            public int multipleUsersAllowed(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiUserManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.multiuser.IMultiUserManager
            public boolean multipleUsersSupported(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiUserManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.multiuser.IMultiUserManager
            public boolean removeUser(ContextInfo contextInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiUserManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IMultiUserManager.DESCRIPTOR);
        }

        public static IMultiUserManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IMultiUserManager.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IMultiUserManager)) ? new Proxy(iBinder) : (IMultiUserManager) iInterfaceQueryLocalInterface;
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "multipleUsersSupported";
                case 2:
                    return "multipleUsersAllowed";
                case 3:
                    return "allowMultipleUsers";
                case 4:
                    return "createUser";
                case 5:
                    return "removeUser";
                case 6:
                    return "getUsers";
                case 7:
                    return "allowUserCreation";
                case 8:
                    return "isUserCreationAllowed";
                case 9:
                    return "allowUserRemoval";
                case 10:
                    return "isUserRemovalAllowed";
                default:
                    return null;
            }
        }

        public int getMaxTransactionId() {
            return 9;
        }

        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IMultiUserManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IMultiUserManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ContextInfo contextInfo = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zMultipleUsersSupported = multipleUsersSupported(contextInfo);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zMultipleUsersSupported);
                    return true;
                case 2:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int iMultipleUsersAllowed = multipleUsersAllowed(contextInfo2, z);
                    parcel2.writeNoException();
                    parcel2.writeInt(iMultipleUsersAllowed);
                    return true;
                case 3:
                    ContextInfo contextInfo3 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int iAllowMultipleUsers = allowMultipleUsers(contextInfo3, z2);
                    parcel2.writeNoException();
                    parcel2.writeInt(iAllowMultipleUsers);
                    return true;
                case 4:
                    ContextInfo contextInfo4 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iCreateUser = createUser(contextInfo4, string);
                    parcel2.writeNoException();
                    parcel2.writeInt(iCreateUser);
                    return true;
                case 5:
                    ContextInfo contextInfo5 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zRemoveUser = removeUser(contextInfo5, i3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveUser);
                    return true;
                case 6:
                    ContextInfo contextInfo6 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    int[] users = getUsers(contextInfo6);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(users);
                    return true;
                case 7:
                    ContextInfo contextInfo7 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowUserCreation = allowUserCreation(contextInfo7, z3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowUserCreation);
                    return true;
                case 8:
                    ContextInfo contextInfo8 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsUserCreationAllowed = isUserCreationAllowed(contextInfo8, z4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsUserCreationAllowed);
                    return true;
                case 9:
                    ContextInfo contextInfo9 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowUserRemoval = allowUserRemoval(contextInfo9, z5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowUserRemoval);
                    return true;
                case 10:
                    ContextInfo contextInfo10 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsUserRemovalAllowed = isUserRemovalAllowed(contextInfo10, z6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsUserRemovalAllowed);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }
}
