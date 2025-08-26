package com.samsung.android.knox.dex;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.knox.ContextInfo;
import java.util.List;

/* loaded from: classes4.dex */
public interface IDexPolicy extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.dex.IDexPolicy";

    public class Default implements IDexPolicy {
        @Override // com.samsung.android.knox.dex.IDexPolicy
        public int addPackageToDisableList(ContextInfo contextInfo, String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.dex.IDexPolicy
        public boolean allowScreenTimeoutChange(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.dex.IDexPolicy
        public boolean enforceEthernetOnly(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.dex.IDexPolicy
        public boolean enforceVirtualMacAddress(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.dex.IDexPolicy
        public List<String> getPackagesFromDisableList(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.dex.IDexPolicy
        public String getVirtualMacAddress() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.dex.IDexPolicy
        public boolean isDexActivated() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.dex.IDexPolicy
        public boolean isDexDisabled() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.dex.IDexPolicy
        public boolean isEthernetOnlyEnforced() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.dex.IDexPolicy
        public boolean isScreenTimeoutChangeAllowed() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.dex.IDexPolicy
        public boolean isVirtualMacAddressEnforced() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.dex.IDexPolicy
        public int removePackageFromDisableList(ContextInfo contextInfo, String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.dex.IDexPolicy
        public boolean setDexDisabled(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }
    }

    int addPackageToDisableList(ContextInfo contextInfo, String str) throws RemoteException;

    boolean allowScreenTimeoutChange(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean enforceEthernetOnly(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean enforceVirtualMacAddress(ContextInfo contextInfo, boolean z) throws RemoteException;

    List<String> getPackagesFromDisableList(ContextInfo contextInfo) throws RemoteException;

    String getVirtualMacAddress() throws RemoteException;

    boolean isDexActivated() throws RemoteException;

    boolean isDexDisabled() throws RemoteException;

    boolean isEthernetOnlyEnforced() throws RemoteException;

    boolean isScreenTimeoutChangeAllowed() throws RemoteException;

    boolean isVirtualMacAddressEnforced() throws RemoteException;

    int removePackageFromDisableList(ContextInfo contextInfo, String str) throws RemoteException;

    boolean setDexDisabled(ContextInfo contextInfo, boolean z) throws RemoteException;

    public abstract class Stub extends Binder implements IDexPolicy {
        public static final int TRANSACTION_addPackageToDisableList = 6;
        public static final int TRANSACTION_allowScreenTimeoutChange = 9;
        public static final int TRANSACTION_enforceEthernetOnly = 4;
        public static final int TRANSACTION_enforceVirtualMacAddress = 11;
        public static final int TRANSACTION_getPackagesFromDisableList = 8;
        public static final int TRANSACTION_getVirtualMacAddress = 13;
        public static final int TRANSACTION_isDexActivated = 3;
        public static final int TRANSACTION_isDexDisabled = 2;
        public static final int TRANSACTION_isEthernetOnlyEnforced = 5;
        public static final int TRANSACTION_isScreenTimeoutChangeAllowed = 10;
        public static final int TRANSACTION_isVirtualMacAddressEnforced = 12;
        public static final int TRANSACTION_removePackageFromDisableList = 7;
        public static final int TRANSACTION_setDexDisabled = 1;

        class Proxy implements IDexPolicy {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.dex.IDexPolicy
            public int addPackageToDisableList(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDexPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dex.IDexPolicy
            public boolean allowScreenTimeoutChange(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDexPolicy.DESCRIPTOR);
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

            @Override // com.samsung.android.knox.dex.IDexPolicy
            public boolean enforceEthernetOnly(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDexPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dex.IDexPolicy
            public boolean enforceVirtualMacAddress(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDexPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IDexPolicy.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.dex.IDexPolicy
            public List<String> getPackagesFromDisableList(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDexPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dex.IDexPolicy
            public String getVirtualMacAddress() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDexPolicy.DESCRIPTOR);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dex.IDexPolicy
            public boolean isDexActivated() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDexPolicy.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dex.IDexPolicy
            public boolean isDexDisabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDexPolicy.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dex.IDexPolicy
            public boolean isEthernetOnlyEnforced() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDexPolicy.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dex.IDexPolicy
            public boolean isScreenTimeoutChangeAllowed() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDexPolicy.DESCRIPTOR);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dex.IDexPolicy
            public boolean isVirtualMacAddressEnforced() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDexPolicy.DESCRIPTOR);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dex.IDexPolicy
            public int removePackageFromDisableList(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDexPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dex.IDexPolicy
            public boolean setDexDisabled(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDexPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IDexPolicy.DESCRIPTOR);
        }

        public static IDexPolicy asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IDexPolicy.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IDexPolicy)) ? new Proxy(iBinder) : (IDexPolicy) iInterfaceQueryLocalInterface;
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setDexDisabled";
                case 2:
                    return "isDexDisabled";
                case 3:
                    return "isDexActivated";
                case 4:
                    return "enforceEthernetOnly";
                case 5:
                    return "isEthernetOnlyEnforced";
                case 6:
                    return "addPackageToDisableList";
                case 7:
                    return "removePackageFromDisableList";
                case 8:
                    return "getPackagesFromDisableList";
                case 9:
                    return "allowScreenTimeoutChange";
                case 10:
                    return "isScreenTimeoutChangeAllowed";
                case 11:
                    return "enforceVirtualMacAddress";
                case 12:
                    return "isVirtualMacAddressEnforced";
                case 13:
                    return "getVirtualMacAddress";
                default:
                    return null;
            }
        }

        public int getMaxTransactionId() {
            return 12;
        }

        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IDexPolicy.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDexPolicy.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ContextInfo contextInfo = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean dexDisabled = setDexDisabled(contextInfo, z);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(dexDisabled);
                    return true;
                case 2:
                    boolean zIsDexDisabled = isDexDisabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsDexDisabled);
                    return true;
                case 3:
                    boolean zIsDexActivated = isDexActivated();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsDexActivated);
                    return true;
                case 4:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zEnforceEthernetOnly = enforceEthernetOnly(contextInfo2, z2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zEnforceEthernetOnly);
                    return true;
                case 5:
                    boolean zIsEthernetOnlyEnforced = isEthernetOnlyEnforced();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsEthernetOnlyEnforced);
                    return true;
                case 6:
                    ContextInfo contextInfo3 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iAddPackageToDisableList = addPackageToDisableList(contextInfo3, string);
                    parcel2.writeNoException();
                    parcel2.writeInt(iAddPackageToDisableList);
                    return true;
                case 7:
                    ContextInfo contextInfo4 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iRemovePackageFromDisableList = removePackageFromDisableList(contextInfo4, string2);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRemovePackageFromDisableList);
                    return true;
                case 8:
                    ContextInfo contextInfo5 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<String> packagesFromDisableList = getPackagesFromDisableList(contextInfo5);
                    parcel2.writeNoException();
                    parcel2.writeStringList(packagesFromDisableList);
                    return true;
                case 9:
                    ContextInfo contextInfo6 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowScreenTimeoutChange = allowScreenTimeoutChange(contextInfo6, z3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowScreenTimeoutChange);
                    return true;
                case 10:
                    boolean zIsScreenTimeoutChangeAllowed = isScreenTimeoutChangeAllowed();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsScreenTimeoutChangeAllowed);
                    return true;
                case 11:
                    ContextInfo contextInfo7 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zEnforceVirtualMacAddress = enforceVirtualMacAddress(contextInfo7, z4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zEnforceVirtualMacAddress);
                    return true;
                case 12:
                    boolean zIsVirtualMacAddressEnforced = isVirtualMacAddressEnforced();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsVirtualMacAddressEnforced);
                    return true;
                case 13:
                    String virtualMacAddress = getVirtualMacAddress();
                    parcel2.writeNoException();
                    parcel2.writeString(virtualMacAddress);
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
