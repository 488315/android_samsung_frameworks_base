package com.samsung.android.knox.accounts;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.knox.ContextInfo;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface IDeviceAccountPolicy extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.accounts.IDeviceAccountPolicy";

    public class Default implements IDeviceAccountPolicy {
        @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
        public boolean addAccountsToAdditionBlackList(ContextInfo contextInfo, String str, List<String> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
        public boolean addAccountsToAdditionWhiteList(ContextInfo contextInfo, String str, List<String> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
        public boolean addAccountsToRemovalBlackList(ContextInfo contextInfo, String str, List<String> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
        public boolean addAccountsToRemovalWhiteList(ContextInfo contextInfo, String str, List<String> list) throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
        public boolean clearAccountsFromAdditionBlackList(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
        public boolean clearAccountsFromAdditionWhiteList(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
        public boolean clearAccountsFromRemovalBlackList(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
        public boolean clearAccountsFromRemovalWhiteList(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
        public List<AccountControlInfo> getAccountsFromAdditionBlackLists(ContextInfo contextInfo, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
        public List<AccountControlInfo> getAccountsFromAdditionWhiteLists(ContextInfo contextInfo, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
        public List<AccountControlInfo> getAccountsFromRemovalBlackLists(ContextInfo contextInfo, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
        public List<AccountControlInfo> getAccountsFromRemovalWhiteLists(ContextInfo contextInfo, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
        public List<String> getSupportedAccountTypes() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
        public boolean isAccountAdditionAllowed(String str, String str2, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
        public boolean isAccountRemovalAllowed(String str, String str2, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
        public boolean isAccountRemovalAllowedAsUser(String str, String str2, boolean z, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
        public boolean removeAccountsFromAdditionBlackList(ContextInfo contextInfo, String str, List<String> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
        public boolean removeAccountsFromAdditionWhiteList(ContextInfo contextInfo, String str, List<String> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
        public boolean removeAccountsFromRemovalBlackList(ContextInfo contextInfo, String str, List<String> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
        public boolean removeAccountsFromRemovalWhiteList(ContextInfo contextInfo, String str, List<String> list) throws RemoteException {
            return false;
        }
    }

    boolean addAccountsToAdditionBlackList(ContextInfo contextInfo, String str, List<String> list) throws RemoteException;

    boolean addAccountsToAdditionWhiteList(ContextInfo contextInfo, String str, List<String> list) throws RemoteException;

    boolean addAccountsToRemovalBlackList(ContextInfo contextInfo, String str, List<String> list) throws RemoteException;

    boolean addAccountsToRemovalWhiteList(ContextInfo contextInfo, String str, List<String> list) throws RemoteException;

    boolean clearAccountsFromAdditionBlackList(ContextInfo contextInfo, String str) throws RemoteException;

    boolean clearAccountsFromAdditionWhiteList(ContextInfo contextInfo, String str) throws RemoteException;

    boolean clearAccountsFromRemovalBlackList(ContextInfo contextInfo, String str) throws RemoteException;

    boolean clearAccountsFromRemovalWhiteList(ContextInfo contextInfo, String str) throws RemoteException;

    List<AccountControlInfo> getAccountsFromAdditionBlackLists(ContextInfo contextInfo, String str) throws RemoteException;

    List<AccountControlInfo> getAccountsFromAdditionWhiteLists(ContextInfo contextInfo, String str) throws RemoteException;

    List<AccountControlInfo> getAccountsFromRemovalBlackLists(ContextInfo contextInfo, String str) throws RemoteException;

    List<AccountControlInfo> getAccountsFromRemovalWhiteLists(ContextInfo contextInfo, String str) throws RemoteException;

    List<String> getSupportedAccountTypes() throws RemoteException;

    boolean isAccountAdditionAllowed(String str, String str2, boolean z) throws RemoteException;

    boolean isAccountRemovalAllowed(String str, String str2, boolean z) throws RemoteException;

    boolean isAccountRemovalAllowedAsUser(String str, String str2, boolean z, int i) throws RemoteException;

    boolean removeAccountsFromAdditionBlackList(ContextInfo contextInfo, String str, List<String> list) throws RemoteException;

    boolean removeAccountsFromAdditionWhiteList(ContextInfo contextInfo, String str, List<String> list) throws RemoteException;

    boolean removeAccountsFromRemovalBlackList(ContextInfo contextInfo, String str, List<String> list) throws RemoteException;

    boolean removeAccountsFromRemovalWhiteList(ContextInfo contextInfo, String str, List<String> list) throws RemoteException;

    public abstract class Stub extends Binder implements IDeviceAccountPolicy {
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

        class Proxy implements IDeviceAccountPolicy {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
            public boolean addAccountsToAdditionBlackList(ContextInfo contextInfo, String str, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
            public boolean addAccountsToAdditionWhiteList(ContextInfo contextInfo, String str, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
            public boolean addAccountsToRemovalBlackList(ContextInfo contextInfo, String str, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
            public boolean addAccountsToRemovalWhiteList(ContextInfo contextInfo, String str, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
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

            @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
            public boolean clearAccountsFromAdditionBlackList(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
            public boolean clearAccountsFromAdditionWhiteList(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
            public boolean clearAccountsFromRemovalBlackList(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
            public boolean clearAccountsFromRemovalWhiteList(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
            public List<AccountControlInfo> getAccountsFromAdditionBlackLists(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(AccountControlInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
            public List<AccountControlInfo> getAccountsFromAdditionWhiteLists(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(AccountControlInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
            public List<AccountControlInfo> getAccountsFromRemovalBlackLists(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(AccountControlInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
            public List<AccountControlInfo> getAccountsFromRemovalWhiteLists(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(AccountControlInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IDeviceAccountPolicy.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
            public List<String> getSupportedAccountTypes() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceAccountPolicy.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
            public boolean isAccountAdditionAllowed(String str, String str2, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
            public boolean isAccountRemovalAllowed(String str, String str2, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
            public boolean isAccountRemovalAllowedAsUser(String str, String str2, boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
            public boolean removeAccountsFromAdditionBlackList(ContextInfo contextInfo, String str, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
            public boolean removeAccountsFromAdditionWhiteList(ContextInfo contextInfo, String str, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
            public boolean removeAccountsFromRemovalBlackList(ContextInfo contextInfo, String str, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IDeviceAccountPolicy
            public boolean removeAccountsFromRemovalWhiteList(ContextInfo contextInfo, String str, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IDeviceAccountPolicy.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IDeviceAccountPolicy)) ? new Proxy(iBinder) : (IDeviceAccountPolicy) iInterfaceQueryLocalInterface;
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

        public int getMaxTransactionId() {
            return 19;
        }

        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
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
                    String string = parcel.readString();
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean zAddAccountsToRemovalBlackList = addAccountsToRemovalBlackList(contextInfo, string, arrayListCreateStringArrayList);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddAccountsToRemovalBlackList);
                    return true;
                case 3:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string2 = parcel.readString();
                    ArrayList<String> arrayListCreateStringArrayList2 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean zRemoveAccountsFromRemovalBlackList = removeAccountsFromRemovalBlackList(contextInfo2, string2, arrayListCreateStringArrayList2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveAccountsFromRemovalBlackList);
                    return true;
                case 4:
                    ContextInfo contextInfo3 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<AccountControlInfo> accountsFromRemovalBlackLists = getAccountsFromRemovalBlackLists(contextInfo3, string3);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(accountsFromRemovalBlackLists, 1);
                    return true;
                case 5:
                    ContextInfo contextInfo4 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zClearAccountsFromRemovalBlackList = clearAccountsFromRemovalBlackList(contextInfo4, string4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zClearAccountsFromRemovalBlackList);
                    return true;
                case 6:
                    ContextInfo contextInfo5 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string5 = parcel.readString();
                    ArrayList<String> arrayListCreateStringArrayList3 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean zAddAccountsToRemovalWhiteList = addAccountsToRemovalWhiteList(contextInfo5, string5, arrayListCreateStringArrayList3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddAccountsToRemovalWhiteList);
                    return true;
                case 7:
                    ContextInfo contextInfo6 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string6 = parcel.readString();
                    ArrayList<String> arrayListCreateStringArrayList4 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean zRemoveAccountsFromRemovalWhiteList = removeAccountsFromRemovalWhiteList(contextInfo6, string6, arrayListCreateStringArrayList4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveAccountsFromRemovalWhiteList);
                    return true;
                case 8:
                    ContextInfo contextInfo7 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<AccountControlInfo> accountsFromRemovalWhiteLists = getAccountsFromRemovalWhiteLists(contextInfo7, string7);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(accountsFromRemovalWhiteLists, 1);
                    return true;
                case 9:
                    ContextInfo contextInfo8 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zClearAccountsFromRemovalWhiteList = clearAccountsFromRemovalWhiteList(contextInfo8, string8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zClearAccountsFromRemovalWhiteList);
                    return true;
                case 10:
                    String string9 = parcel.readString();
                    String string10 = parcel.readString();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsAccountRemovalAllowed = isAccountRemovalAllowed(string9, string10, z);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAccountRemovalAllowed);
                    return true;
                case 11:
                    String string11 = parcel.readString();
                    String string12 = parcel.readString();
                    boolean z2 = parcel.readBoolean();
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsAccountRemovalAllowedAsUser = isAccountRemovalAllowedAsUser(string11, string12, z2, i3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAccountRemovalAllowedAsUser);
                    return true;
                case 12:
                    ContextInfo contextInfo9 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string13 = parcel.readString();
                    ArrayList<String> arrayListCreateStringArrayList5 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean zAddAccountsToAdditionBlackList = addAccountsToAdditionBlackList(contextInfo9, string13, arrayListCreateStringArrayList5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddAccountsToAdditionBlackList);
                    return true;
                case 13:
                    ContextInfo contextInfo10 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string14 = parcel.readString();
                    ArrayList<String> arrayListCreateStringArrayList6 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean zRemoveAccountsFromAdditionBlackList = removeAccountsFromAdditionBlackList(contextInfo10, string14, arrayListCreateStringArrayList6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveAccountsFromAdditionBlackList);
                    return true;
                case 14:
                    ContextInfo contextInfo11 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<AccountControlInfo> accountsFromAdditionBlackLists = getAccountsFromAdditionBlackLists(contextInfo11, string15);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(accountsFromAdditionBlackLists, 1);
                    return true;
                case 15:
                    ContextInfo contextInfo12 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zClearAccountsFromAdditionBlackList = clearAccountsFromAdditionBlackList(contextInfo12, string16);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zClearAccountsFromAdditionBlackList);
                    return true;
                case 16:
                    ContextInfo contextInfo13 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string17 = parcel.readString();
                    ArrayList<String> arrayListCreateStringArrayList7 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean zAddAccountsToAdditionWhiteList = addAccountsToAdditionWhiteList(contextInfo13, string17, arrayListCreateStringArrayList7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddAccountsToAdditionWhiteList);
                    return true;
                case 17:
                    ContextInfo contextInfo14 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string18 = parcel.readString();
                    ArrayList<String> arrayListCreateStringArrayList8 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean zRemoveAccountsFromAdditionWhiteList = removeAccountsFromAdditionWhiteList(contextInfo14, string18, arrayListCreateStringArrayList8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveAccountsFromAdditionWhiteList);
                    return true;
                case 18:
                    ContextInfo contextInfo15 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string19 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<AccountControlInfo> accountsFromAdditionWhiteLists = getAccountsFromAdditionWhiteLists(contextInfo15, string19);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(accountsFromAdditionWhiteLists, 1);
                    return true;
                case 19:
                    ContextInfo contextInfo16 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zClearAccountsFromAdditionWhiteList = clearAccountsFromAdditionWhiteList(contextInfo16, string20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zClearAccountsFromAdditionWhiteList);
                    return true;
                case 20:
                    String string21 = parcel.readString();
                    String string22 = parcel.readString();
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsAccountAdditionAllowed = isAccountAdditionAllowed(string21, string22, z3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAccountAdditionAllowed);
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
