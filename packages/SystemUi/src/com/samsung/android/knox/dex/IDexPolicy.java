package com.samsung.android.knox.dex;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.samsung.android.knox.ContextInfo;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface IDexPolicy extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.dex.IDexPolicy";

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements IDexPolicy {
        @Override // com.samsung.android.knox.dex.IDexPolicy
        public final int addPackageToDisableList(ContextInfo contextInfo, String str) {
            return 0;
        }

        @Override // com.samsung.android.knox.dex.IDexPolicy
        public final boolean allowScreenTimeoutChange(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.dex.IDexPolicy
        public final boolean enforceEthernetOnly(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.dex.IDexPolicy
        public final boolean enforceVirtualMacAddress(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.dex.IDexPolicy
        public final List<String> getPackagesFromDisableList(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.dex.IDexPolicy
        public final String getVirtualMacAddress() {
            return null;
        }

        @Override // com.samsung.android.knox.dex.IDexPolicy
        public final boolean isDexActivated() {
            return false;
        }

        @Override // com.samsung.android.knox.dex.IDexPolicy
        public final boolean isDexDisabled() {
            return false;
        }

        @Override // com.samsung.android.knox.dex.IDexPolicy
        public final boolean isEthernetOnlyEnforced() {
            return false;
        }

        @Override // com.samsung.android.knox.dex.IDexPolicy
        public final boolean isScreenTimeoutChangeAllowed() {
            return false;
        }

        @Override // com.samsung.android.knox.dex.IDexPolicy
        public final boolean isVirtualMacAddressEnforced() {
            return false;
        }

        @Override // com.samsung.android.knox.dex.IDexPolicy
        public final int removePackageFromDisableList(ContextInfo contextInfo, String str) {
            return 0;
        }

        @Override // com.samsung.android.knox.dex.IDexPolicy
        public final boolean setDexDisabled(ContextInfo contextInfo, boolean z) {
            return false;
        }
    }

    int addPackageToDisableList(ContextInfo contextInfo, String str);

    boolean allowScreenTimeoutChange(ContextInfo contextInfo, boolean z);

    boolean enforceEthernetOnly(ContextInfo contextInfo, boolean z);

    boolean enforceVirtualMacAddress(ContextInfo contextInfo, boolean z);

    List<String> getPackagesFromDisableList(ContextInfo contextInfo);

    String getVirtualMacAddress();

    boolean isDexActivated();

    boolean isDexDisabled();

    boolean isEthernetOnlyEnforced();

    boolean isScreenTimeoutChangeAllowed();

    boolean isVirtualMacAddressEnforced();

    int removePackageFromDisableList(ContextInfo contextInfo, String str);

    boolean setDexDisabled(ContextInfo contextInfo, boolean z);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements IDexPolicy {
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

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements IDexPolicy {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.dex.IDexPolicy
            public final int addPackageToDisableList(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDexPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dex.IDexPolicy
            public final boolean allowScreenTimeoutChange(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDexPolicy.DESCRIPTOR);
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
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.dex.IDexPolicy
            public final boolean enforceEthernetOnly(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDexPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dex.IDexPolicy
            public final boolean enforceVirtualMacAddress(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDexPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final String getInterfaceDescriptor() {
                return IDexPolicy.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.dex.IDexPolicy
            public final List<String> getPackagesFromDisableList(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDexPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dex.IDexPolicy
            public final String getVirtualMacAddress() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDexPolicy.DESCRIPTOR);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dex.IDexPolicy
            public final boolean isDexActivated() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDexPolicy.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dex.IDexPolicy
            public final boolean isDexDisabled() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDexPolicy.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dex.IDexPolicy
            public final boolean isEthernetOnlyEnforced() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDexPolicy.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dex.IDexPolicy
            public final boolean isScreenTimeoutChangeAllowed() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDexPolicy.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dex.IDexPolicy
            public final boolean isVirtualMacAddressEnforced() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDexPolicy.DESCRIPTOR);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dex.IDexPolicy
            public final int removePackageFromDisableList(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDexPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dex.IDexPolicy
            public final boolean setDexDisabled(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDexPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IDexPolicy.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IDexPolicy)) ? new Proxy(iBinder) : (IDexPolicy) queryLocalInterface;
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

        public final int getMaxTransactionId() {
            return 12;
        }

        public final String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
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
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean dexDisabled = setDexDisabled(contextInfo, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(dexDisabled);
                    return true;
                case 2:
                    boolean isDexDisabled = isDexDisabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDexDisabled);
                    return true;
                case 3:
                    boolean isDexActivated = isDexActivated();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDexActivated);
                    return true;
                case 4:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean enforceEthernetOnly = enforceEthernetOnly(contextInfo2, readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enforceEthernetOnly);
                    return true;
                case 5:
                    boolean isEthernetOnlyEnforced = isEthernetOnlyEnforced();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isEthernetOnlyEnforced);
                    return true;
                case 6:
                    ContextInfo contextInfo3 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int addPackageToDisableList = addPackageToDisableList(contextInfo3, readString);
                    parcel2.writeNoException();
                    parcel2.writeInt(addPackageToDisableList);
                    return true;
                case 7:
                    ContextInfo contextInfo4 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int removePackageFromDisableList = removePackageFromDisableList(contextInfo4, readString2);
                    parcel2.writeNoException();
                    parcel2.writeInt(removePackageFromDisableList);
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
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowScreenTimeoutChange = allowScreenTimeoutChange(contextInfo6, readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowScreenTimeoutChange);
                    return true;
                case 10:
                    boolean isScreenTimeoutChangeAllowed = isScreenTimeoutChangeAllowed();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isScreenTimeoutChangeAllowed);
                    return true;
                case 11:
                    ContextInfo contextInfo7 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean enforceVirtualMacAddress = enforceVirtualMacAddress(contextInfo7, readBoolean4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enforceVirtualMacAddress);
                    return true;
                case 12:
                    boolean isVirtualMacAddressEnforced = isVirtualMacAddressEnforced();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isVirtualMacAddressEnforced);
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
        public final IBinder asBinder() {
            return this;
        }
    }
}
