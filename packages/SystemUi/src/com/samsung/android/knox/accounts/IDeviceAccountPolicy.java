package com.samsung.android.knox.accounts;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.samsung.android.knox.ContextInfo;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface IDeviceAccountPolicy extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.accounts.IDeviceAccountPolicy";

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements IDeviceAccountPolicy {
        @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
        public final boolean addAccountsToAdditionBlackList(ContextInfo contextInfo, String str, List<String> list) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
        public final boolean addAccountsToAdditionWhiteList(ContextInfo contextInfo, String str, List<String> list) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
        public final boolean addAccountsToRemovalBlackList(ContextInfo contextInfo, String str, List<String> list) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
        public final boolean addAccountsToRemovalWhiteList(ContextInfo contextInfo, String str, List<String> list) {
            return false;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
        public final boolean clearAccountsFromAdditionBlackList(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
        public final boolean clearAccountsFromAdditionWhiteList(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
        public final boolean clearAccountsFromRemovalBlackList(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
        public final boolean clearAccountsFromRemovalWhiteList(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
        public final List<AccountControlInfo> getAccountsFromAdditionBlackLists(ContextInfo contextInfo, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
        public final List<AccountControlInfo> getAccountsFromAdditionWhiteLists(ContextInfo contextInfo, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
        public final List<AccountControlInfo> getAccountsFromRemovalBlackLists(ContextInfo contextInfo, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
        public final List<AccountControlInfo> getAccountsFromRemovalWhiteLists(ContextInfo contextInfo, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
        public final List<String> getSupportedAccountTypes() {
            return null;
        }

        @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
        public final boolean isAccountAdditionAllowed(String str, String str2, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
        public final boolean isAccountRemovalAllowed(String str, String str2, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
        public final boolean isAccountRemovalAllowedAsUser(String str, String str2, boolean z, int i) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
        public final boolean removeAccountsFromAdditionBlackList(ContextInfo contextInfo, String str, List<String> list) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
        public final boolean removeAccountsFromAdditionWhiteList(ContextInfo contextInfo, String str, List<String> list) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
        public final boolean removeAccountsFromRemovalBlackList(ContextInfo contextInfo, String str, List<String> list) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
        public final boolean removeAccountsFromRemovalWhiteList(ContextInfo contextInfo, String str, List<String> list) {
            return false;
        }
    }

    boolean addAccountsToAdditionBlackList(ContextInfo contextInfo, String str, List<String> list);

    boolean addAccountsToAdditionWhiteList(ContextInfo contextInfo, String str, List<String> list);

    boolean addAccountsToRemovalBlackList(ContextInfo contextInfo, String str, List<String> list);

    boolean addAccountsToRemovalWhiteList(ContextInfo contextInfo, String str, List<String> list);

    boolean clearAccountsFromAdditionBlackList(ContextInfo contextInfo, String str);

    boolean clearAccountsFromAdditionWhiteList(ContextInfo contextInfo, String str);

    boolean clearAccountsFromRemovalBlackList(ContextInfo contextInfo, String str);

    boolean clearAccountsFromRemovalWhiteList(ContextInfo contextInfo, String str);

    List<AccountControlInfo> getAccountsFromAdditionBlackLists(ContextInfo contextInfo, String str);

    List<AccountControlInfo> getAccountsFromAdditionWhiteLists(ContextInfo contextInfo, String str);

    List<AccountControlInfo> getAccountsFromRemovalBlackLists(ContextInfo contextInfo, String str);

    List<AccountControlInfo> getAccountsFromRemovalWhiteLists(ContextInfo contextInfo, String str);

    List<String> getSupportedAccountTypes();

    boolean isAccountAdditionAllowed(String str, String str2, boolean z);

    boolean isAccountRemovalAllowed(String str, String str2, boolean z);

    boolean isAccountRemovalAllowedAsUser(String str, String str2, boolean z, int i);

    boolean removeAccountsFromAdditionBlackList(ContextInfo contextInfo, String str, List<String> list);

    boolean removeAccountsFromAdditionWhiteList(ContextInfo contextInfo, String str, List<String> list);

    boolean removeAccountsFromRemovalBlackList(ContextInfo contextInfo, String str, List<String> list);

    boolean removeAccountsFromRemovalWhiteList(ContextInfo contextInfo, String str, List<String> list);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements IDeviceAccountPolicy {
        public static final int TRANSACTION_addAccountsToAdditionBlackList = 12;
        public static final int TRANSACTION_addAccountsToAdditionWhiteList = 16;
        public static final int TRANSACTION_addAccountsToRemovalBlackList = 2;
        public static final int TRANSACTION_addAccountsToRemovalWhiteList = 6;
        public static final int TRANSACTION_clearAccountsFromAdditionBlackList = 15;
        public static final int TRANSACTION_clearAccountsFromAdditionWhiteList = 19;
        public static final int TRANSACTION_clearAccountsFromRemovalBlackList = 5;
        public static final int TRANSACTION_clearAccountsFromRemovalWhiteList = 9;
        public static final int TRANSACTION_getAccountsFromAdditionBlackLists = 14;
        public static final int TRANSACTION_getAccountsFromAdditionWhiteLists = 18;
        public static final int TRANSACTION_getAccountsFromRemovalBlackLists = 4;
        public static final int TRANSACTION_getAccountsFromRemovalWhiteLists = 8;
        public static final int TRANSACTION_getSupportedAccountTypes = 1;
        public static final int TRANSACTION_isAccountAdditionAllowed = 20;
        public static final int TRANSACTION_isAccountRemovalAllowed = 10;
        public static final int TRANSACTION_isAccountRemovalAllowedAsUser = 11;
        public static final int TRANSACTION_removeAccountsFromAdditionBlackList = 13;
        public static final int TRANSACTION_removeAccountsFromAdditionWhiteList = 17;
        public static final int TRANSACTION_removeAccountsFromRemovalBlackList = 3;
        public static final int TRANSACTION_removeAccountsFromRemovalWhiteList = 7;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements IDeviceAccountPolicy {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
            public final boolean addAccountsToAdditionBlackList(ContextInfo contextInfo, String str, List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeStringList(list);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
            public final boolean addAccountsToAdditionWhiteList(ContextInfo contextInfo, String str, List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeStringList(list);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
            public final boolean addAccountsToRemovalBlackList(ContextInfo contextInfo, String str, List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeStringList(list);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
            public final boolean addAccountsToRemovalWhiteList(ContextInfo contextInfo, String str, List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeStringList(list);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
            public final boolean clearAccountsFromAdditionBlackList(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
            public final boolean clearAccountsFromAdditionWhiteList(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
            public final boolean clearAccountsFromRemovalBlackList(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
            public final boolean clearAccountsFromRemovalWhiteList(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
            public final List<AccountControlInfo> getAccountsFromAdditionBlackLists(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(AccountControlInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
            public final List<AccountControlInfo> getAccountsFromAdditionWhiteLists(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(AccountControlInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
            public final List<AccountControlInfo> getAccountsFromRemovalBlackLists(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(AccountControlInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
            public final List<AccountControlInfo> getAccountsFromRemovalWhiteLists(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(AccountControlInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final String getInterfaceDescriptor() {
                return IDeviceAccountPolicy.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
            public final List<String> getSupportedAccountTypes() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceAccountPolicy.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
            public final boolean isAccountAdditionAllowed(String str, String str2, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceAccountPolicy.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
            public final boolean isAccountRemovalAllowed(String str, String str2, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceAccountPolicy.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
            public final boolean isAccountRemovalAllowedAsUser(String str, String str2, boolean z, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceAccountPolicy.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
            public final boolean removeAccountsFromAdditionBlackList(ContextInfo contextInfo, String str, List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeStringList(list);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
            public final boolean removeAccountsFromAdditionWhiteList(ContextInfo contextInfo, String str, List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeStringList(list);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
            public final boolean removeAccountsFromRemovalBlackList(ContextInfo contextInfo, String str, List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeStringList(list);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
            public final boolean removeAccountsFromRemovalWhiteList(ContextInfo contextInfo, String str, List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeStringList(list);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IDeviceAccountPolicy.DESCRIPTOR);
        }

        public static IDeviceAccountPolicy asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IDeviceAccountPolicy.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IDeviceAccountPolicy)) ? new Proxy(iBinder) : (IDeviceAccountPolicy) queryLocalInterface;
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getSupportedAccountTypes";
                case 2:
                    return "addAccountsToRemovalBlackList";
                case 3:
                    return "removeAccountsFromRemovalBlackList";
                case 4:
                    return "getAccountsFromRemovalBlackLists";
                case 5:
                    return "clearAccountsFromRemovalBlackList";
                case 6:
                    return "addAccountsToRemovalWhiteList";
                case 7:
                    return "removeAccountsFromRemovalWhiteList";
                case 8:
                    return "getAccountsFromRemovalWhiteLists";
                case 9:
                    return "clearAccountsFromRemovalWhiteList";
                case 10:
                    return "isAccountRemovalAllowed";
                case 11:
                    return "isAccountRemovalAllowedAsUser";
                case 12:
                    return "addAccountsToAdditionBlackList";
                case 13:
                    return "removeAccountsFromAdditionBlackList";
                case 14:
                    return "getAccountsFromAdditionBlackLists";
                case 15:
                    return "clearAccountsFromAdditionBlackList";
                case 16:
                    return "addAccountsToAdditionWhiteList";
                case 17:
                    return "removeAccountsFromAdditionWhiteList";
                case 18:
                    return "getAccountsFromAdditionWhiteLists";
                case 19:
                    return "clearAccountsFromAdditionWhiteList";
                case 20:
                    return "isAccountAdditionAllowed";
                default:
                    return null;
            }
        }

        public final int getMaxTransactionId() {
            return 19;
        }

        public final String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IDeviceAccountPolicy.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDeviceAccountPolicy.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    List<String> supportedAccountTypes = getSupportedAccountTypes();
                    parcel2.writeNoException();
                    parcel2.writeStringList(supportedAccountTypes);
                    return true;
                case 2:
                    ContextInfo contextInfo = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString = parcel.readString();
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean addAccountsToRemovalBlackList = addAccountsToRemovalBlackList(contextInfo, readString, createStringArrayList);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addAccountsToRemovalBlackList);
                    return true;
                case 3:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString2 = parcel.readString();
                    ArrayList<String> createStringArrayList2 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean removeAccountsFromRemovalBlackList = removeAccountsFromRemovalBlackList(contextInfo2, readString2, createStringArrayList2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeAccountsFromRemovalBlackList);
                    return true;
                case 4:
                    ContextInfo contextInfo3 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<AccountControlInfo> accountsFromRemovalBlackLists = getAccountsFromRemovalBlackLists(contextInfo3, readString3);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(accountsFromRemovalBlackLists, 1);
                    return true;
                case 5:
                    ContextInfo contextInfo4 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean clearAccountsFromRemovalBlackList = clearAccountsFromRemovalBlackList(contextInfo4, readString4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(clearAccountsFromRemovalBlackList);
                    return true;
                case 6:
                    ContextInfo contextInfo5 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString5 = parcel.readString();
                    ArrayList<String> createStringArrayList3 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean addAccountsToRemovalWhiteList = addAccountsToRemovalWhiteList(contextInfo5, readString5, createStringArrayList3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addAccountsToRemovalWhiteList);
                    return true;
                case 7:
                    ContextInfo contextInfo6 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString6 = parcel.readString();
                    ArrayList<String> createStringArrayList4 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean removeAccountsFromRemovalWhiteList = removeAccountsFromRemovalWhiteList(contextInfo6, readString6, createStringArrayList4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeAccountsFromRemovalWhiteList);
                    return true;
                case 8:
                    ContextInfo contextInfo7 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<AccountControlInfo> accountsFromRemovalWhiteLists = getAccountsFromRemovalWhiteLists(contextInfo7, readString7);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(accountsFromRemovalWhiteLists, 1);
                    return true;
                case 9:
                    ContextInfo contextInfo8 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean clearAccountsFromRemovalWhiteList = clearAccountsFromRemovalWhiteList(contextInfo8, readString8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(clearAccountsFromRemovalWhiteList);
                    return true;
                case 10:
                    String readString9 = parcel.readString();
                    String readString10 = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isAccountRemovalAllowed = isAccountRemovalAllowed(readString9, readString10, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAccountRemovalAllowed);
                    return true;
                case 11:
                    String readString11 = parcel.readString();
                    String readString12 = parcel.readString();
                    boolean readBoolean2 = parcel.readBoolean();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isAccountRemovalAllowedAsUser = isAccountRemovalAllowedAsUser(readString11, readString12, readBoolean2, readInt);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAccountRemovalAllowedAsUser);
                    return true;
                case 12:
                    ContextInfo contextInfo9 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString13 = parcel.readString();
                    ArrayList<String> createStringArrayList5 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean addAccountsToAdditionBlackList = addAccountsToAdditionBlackList(contextInfo9, readString13, createStringArrayList5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addAccountsToAdditionBlackList);
                    return true;
                case 13:
                    ContextInfo contextInfo10 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString14 = parcel.readString();
                    ArrayList<String> createStringArrayList6 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean removeAccountsFromAdditionBlackList = removeAccountsFromAdditionBlackList(contextInfo10, readString14, createStringArrayList6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeAccountsFromAdditionBlackList);
                    return true;
                case 14:
                    ContextInfo contextInfo11 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<AccountControlInfo> accountsFromAdditionBlackLists = getAccountsFromAdditionBlackLists(contextInfo11, readString15);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(accountsFromAdditionBlackLists, 1);
                    return true;
                case 15:
                    ContextInfo contextInfo12 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean clearAccountsFromAdditionBlackList = clearAccountsFromAdditionBlackList(contextInfo12, readString16);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(clearAccountsFromAdditionBlackList);
                    return true;
                case 16:
                    ContextInfo contextInfo13 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString17 = parcel.readString();
                    ArrayList<String> createStringArrayList7 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean addAccountsToAdditionWhiteList = addAccountsToAdditionWhiteList(contextInfo13, readString17, createStringArrayList7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addAccountsToAdditionWhiteList);
                    return true;
                case 17:
                    ContextInfo contextInfo14 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString18 = parcel.readString();
                    ArrayList<String> createStringArrayList8 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean removeAccountsFromAdditionWhiteList = removeAccountsFromAdditionWhiteList(contextInfo14, readString18, createStringArrayList8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeAccountsFromAdditionWhiteList);
                    return true;
                case 18:
                    ContextInfo contextInfo15 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString19 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<AccountControlInfo> accountsFromAdditionWhiteLists = getAccountsFromAdditionWhiteLists(contextInfo15, readString19);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(accountsFromAdditionWhiteLists, 1);
                    return true;
                case 19:
                    ContextInfo contextInfo16 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean clearAccountsFromAdditionWhiteList = clearAccountsFromAdditionWhiteList(contextInfo16, readString20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(clearAccountsFromAdditionWhiteList);
                    return true;
                case 20:
                    String readString21 = parcel.readString();
                    String readString22 = parcel.readString();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isAccountAdditionAllowed = isAccountAdditionAllowed(readString21, readString22, readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAccountAdditionAllowed);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }
}
