package com.samsung.android.knox.accounts;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.knox.ContextInfo;

/* loaded from: classes4.dex */
public interface IEmailPolicy extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.accounts.IEmailPolicy";

    public class Default implements IEmailPolicy {
        @Override // com.samsung.android.knox.accounts.IEmailPolicy
        public boolean allowAccountAddition(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IEmailPolicy
        public boolean allowEmailSettingsChange(ContextInfo contextInfo, boolean z, long j) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IEmailPolicy
        public boolean allowPopImapEmail(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.accounts.IEmailPolicy
        public boolean getAllowEmailForwarding(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IEmailPolicy
        public boolean getAllowHTMLEmail(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IEmailPolicy
        public boolean isAccountAdditionAllowed(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IEmailPolicy
        public boolean isEmailNotificationsEnabled(ContextInfo contextInfo, long j) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IEmailPolicy
        public boolean isEmailSettingsChangeAllowed(ContextInfo contextInfo, long j) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IEmailPolicy
        public boolean isPopImapEmailAllowed(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IEmailPolicy
        public boolean setAllowEmailForwarding(ContextInfo contextInfo, String str, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IEmailPolicy
        public boolean setAllowHTMLEmail(ContextInfo contextInfo, String str, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IEmailPolicy
        public boolean setEmailNotificationsState(ContextInfo contextInfo, boolean z, long j) throws RemoteException {
            return false;
        }
    }

    boolean allowAccountAddition(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean allowEmailSettingsChange(ContextInfo contextInfo, boolean z, long j) throws RemoteException;

    boolean allowPopImapEmail(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean getAllowEmailForwarding(ContextInfo contextInfo, String str) throws RemoteException;

    boolean getAllowHTMLEmail(ContextInfo contextInfo, String str) throws RemoteException;

    boolean isAccountAdditionAllowed(ContextInfo contextInfo) throws RemoteException;

    boolean isEmailNotificationsEnabled(ContextInfo contextInfo, long j) throws RemoteException;

    boolean isEmailSettingsChangeAllowed(ContextInfo contextInfo, long j) throws RemoteException;

    boolean isPopImapEmailAllowed(ContextInfo contextInfo) throws RemoteException;

    boolean setAllowEmailForwarding(ContextInfo contextInfo, String str, boolean z) throws RemoteException;

    boolean setAllowHTMLEmail(ContextInfo contextInfo, String str, boolean z) throws RemoteException;

    boolean setEmailNotificationsState(ContextInfo contextInfo, boolean z, long j) throws RemoteException;

    public abstract class Stub extends Binder implements IEmailPolicy {
        public static final int TRANSACTION_allowAccountAddition = 1;
        public static final int TRANSACTION_allowEmailSettingsChange = 11;
        public static final int TRANSACTION_allowPopImapEmail = 3;
        public static final int TRANSACTION_getAllowEmailForwarding = 6;
        public static final int TRANSACTION_getAllowHTMLEmail = 8;
        public static final int TRANSACTION_isAccountAdditionAllowed = 2;
        public static final int TRANSACTION_isEmailNotificationsEnabled = 10;
        public static final int TRANSACTION_isEmailSettingsChangeAllowed = 12;
        public static final int TRANSACTION_isPopImapEmailAllowed = 4;
        public static final int TRANSACTION_setAllowEmailForwarding = 5;
        public static final int TRANSACTION_setAllowHTMLEmail = 7;
        public static final int TRANSACTION_setEmailNotificationsState = 9;

        class Proxy implements IEmailPolicy {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.accounts.IEmailPolicy
            public boolean allowAccountAddition(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEmailPolicy.DESCRIPTOR);
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

            @Override // com.samsung.android.knox.accounts.IEmailPolicy
            public boolean allowEmailSettingsChange(ContextInfo contextInfo, boolean z, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEmailPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailPolicy
            public boolean allowPopImapEmail(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEmailPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
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

            @Override // com.samsung.android.knox.accounts.IEmailPolicy
            public boolean getAllowEmailForwarding(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEmailPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailPolicy
            public boolean getAllowHTMLEmail(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEmailPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IEmailPolicy.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.accounts.IEmailPolicy
            public boolean isAccountAdditionAllowed(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEmailPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailPolicy
            public boolean isEmailNotificationsEnabled(ContextInfo contextInfo, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEmailPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailPolicy
            public boolean isEmailSettingsChangeAllowed(ContextInfo contextInfo, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEmailPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailPolicy
            public boolean isPopImapEmailAllowed(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEmailPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailPolicy
            public boolean setAllowEmailForwarding(ContextInfo contextInfo, String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEmailPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailPolicy
            public boolean setAllowHTMLEmail(ContextInfo contextInfo, String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEmailPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailPolicy
            public boolean setEmailNotificationsState(ContextInfo contextInfo, boolean z, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEmailPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IEmailPolicy.DESCRIPTOR);
        }

        public static IEmailPolicy asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IEmailPolicy.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IEmailPolicy)) ? new Proxy(iBinder) : (IEmailPolicy) iInterfaceQueryLocalInterface;
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "allowAccountAddition";
                case 2:
                    return "isAccountAdditionAllowed";
                case 3:
                    return "allowPopImapEmail";
                case 4:
                    return "isPopImapEmailAllowed";
                case 5:
                    return "setAllowEmailForwarding";
                case 6:
                    return "getAllowEmailForwarding";
                case 7:
                    return "setAllowHTMLEmail";
                case 8:
                    return "getAllowHTMLEmail";
                case 9:
                    return "setEmailNotificationsState";
                case 10:
                    return "isEmailNotificationsEnabled";
                case 11:
                    return "allowEmailSettingsChange";
                case 12:
                    return "isEmailSettingsChangeAllowed";
                default:
                    return null;
            }
        }

        public int getMaxTransactionId() {
            return 11;
        }

        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IEmailPolicy.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IEmailPolicy.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ContextInfo contextInfo = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowAccountAddition = allowAccountAddition(contextInfo, z);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowAccountAddition);
                    return true;
                case 2:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsAccountAdditionAllowed = isAccountAdditionAllowed(contextInfo2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAccountAdditionAllowed);
                    return true;
                case 3:
                    ContextInfo contextInfo3 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowPopImapEmail = allowPopImapEmail(contextInfo3, z2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowPopImapEmail);
                    return true;
                case 4:
                    ContextInfo contextInfo4 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsPopImapEmailAllowed = isPopImapEmailAllowed(contextInfo4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsPopImapEmailAllowed);
                    return true;
                case 5:
                    ContextInfo contextInfo5 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string = parcel.readString();
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowEmailForwarding = setAllowEmailForwarding(contextInfo5, string, z3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowEmailForwarding);
                    return true;
                case 6:
                    ContextInfo contextInfo6 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean allowEmailForwarding2 = getAllowEmailForwarding(contextInfo6, string2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowEmailForwarding2);
                    return true;
                case 7:
                    ContextInfo contextInfo7 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string3 = parcel.readString();
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowHTMLEmail = setAllowHTMLEmail(contextInfo7, string3, z4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowHTMLEmail);
                    return true;
                case 8:
                    ContextInfo contextInfo8 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean allowHTMLEmail2 = getAllowHTMLEmail(contextInfo8, string4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowHTMLEmail2);
                    return true;
                case 9:
                    ContextInfo contextInfo9 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z5 = parcel.readBoolean();
                    long j = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean emailNotificationsState = setEmailNotificationsState(contextInfo9, z5, j);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(emailNotificationsState);
                    return true;
                case 10:
                    ContextInfo contextInfo10 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long j2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean zIsEmailNotificationsEnabled = isEmailNotificationsEnabled(contextInfo10, j2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsEmailNotificationsEnabled);
                    return true;
                case 11:
                    ContextInfo contextInfo11 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z6 = parcel.readBoolean();
                    long j3 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean zAllowEmailSettingsChange = allowEmailSettingsChange(contextInfo11, z6, j3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowEmailSettingsChange);
                    return true;
                case 12:
                    ContextInfo contextInfo12 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long j4 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean zIsEmailSettingsChangeAllowed = isEmailSettingsChangeAllowed(contextInfo12, j4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsEmailSettingsChangeAllowed);
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
