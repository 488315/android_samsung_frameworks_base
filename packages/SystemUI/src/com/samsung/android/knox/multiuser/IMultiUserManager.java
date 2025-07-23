package com.samsung.android.knox.multiuser;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.knox.ContextInfo;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface IMultiUserManager extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.multiuser.IMultiUserManager";

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        class Proxy implements IMultiUserManager {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.multiuser.IMultiUserManager
            public int allowMultipleUsers(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiUserManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.multiuser.IMultiUserManager
            public boolean allowUserCreation(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiUserManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.multiuser.IMultiUserManager
            public boolean allowUserRemoval(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiUserManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.multiuser.IMultiUserManager
            public int createUser(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiUserManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IMultiUserManager.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.multiuser.IMultiUserManager
            public int[] getUsers(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiUserManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.multiuser.IMultiUserManager
            public boolean isUserCreationAllowed(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiUserManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.multiuser.IMultiUserManager
            public boolean isUserRemovalAllowed(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiUserManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.multiuser.IMultiUserManager
            public int multipleUsersAllowed(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiUserManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.multiuser.IMultiUserManager
            public boolean multipleUsersSupported(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiUserManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.multiuser.IMultiUserManager
            public boolean removeUser(ContextInfo contextInfo, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiUserManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IMultiUserManager.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IMultiUserManager)) ? new Proxy(iBinder) : (IMultiUserManager) queryLocalInterface;
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
                    boolean multipleUsersSupported = multipleUsersSupported(contextInfo);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(multipleUsersSupported);
                    return true;
                case 2:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int multipleUsersAllowed = multipleUsersAllowed(contextInfo2, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeInt(multipleUsersAllowed);
                    return true;
                case 3:
                    ContextInfo contextInfo3 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int allowMultipleUsers = allowMultipleUsers(contextInfo3, readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeInt(allowMultipleUsers);
                    return true;
                case 4:
                    ContextInfo contextInfo4 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int createUser = createUser(contextInfo4, readString);
                    parcel2.writeNoException();
                    parcel2.writeInt(createUser);
                    return true;
                case 5:
                    ContextInfo contextInfo5 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean removeUser = removeUser(contextInfo5, readInt);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeUser);
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
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowUserCreation = allowUserCreation(contextInfo7, readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowUserCreation);
                    return true;
                case 8:
                    ContextInfo contextInfo8 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isUserCreationAllowed = isUserCreationAllowed(contextInfo8, readBoolean4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUserCreationAllowed);
                    return true;
                case 9:
                    ContextInfo contextInfo9 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowUserRemoval = allowUserRemoval(contextInfo9, readBoolean5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowUserRemoval);
                    return true;
                case 10:
                    ContextInfo contextInfo10 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isUserRemovalAllowed = isUserRemovalAllowed(contextInfo10, readBoolean6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUserRemovalAllowed);
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
