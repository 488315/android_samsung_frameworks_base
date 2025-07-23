package android.accounts;

import android.Manifest;
import android.accounts.IAccountAuthenticatorResponse;
import android.app.ActivityThread;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PermissionEnforcer;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IAccountAuthenticator extends IInterface {

    public static class Default implements IAccountAuthenticator {
        @Override // android.accounts.IAccountAuthenticator
        public void addAccount(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, String str, String str2, String[] strArr, Bundle bundle) throws RemoteException {
        }

        @Override // android.accounts.IAccountAuthenticator
        public void addAccountFromCredentials(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, Bundle bundle) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.accounts.IAccountAuthenticator
        public void confirmCredentials(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, Bundle bundle) throws RemoteException {
        }

        @Override // android.accounts.IAccountAuthenticator
        public void editProperties(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, String str) throws RemoteException {
        }

        @Override // android.accounts.IAccountAuthenticator
        public void finishSession(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, String str, Bundle bundle) throws RemoteException {
        }

        @Override // android.accounts.IAccountAuthenticator
        public void getAccountCredentialsForCloning(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account) throws RemoteException {
        }

        @Override // android.accounts.IAccountAuthenticator
        public void getAccountRemovalAllowed(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account) throws RemoteException {
        }

        @Override // android.accounts.IAccountAuthenticator
        public void getAuthToken(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, String str, Bundle bundle) throws RemoteException {
        }

        @Override // android.accounts.IAccountAuthenticator
        public void getAuthTokenLabel(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, String str) throws RemoteException {
        }

        @Override // android.accounts.IAccountAuthenticator
        public void hasFeatures(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, String[] strArr) throws RemoteException {
        }

        @Override // android.accounts.IAccountAuthenticator
        public void isCredentialsUpdateSuggested(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, String str) throws RemoteException {
        }

        @Override // android.accounts.IAccountAuthenticator
        public void startAddAccountSession(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, String str, String str2, String[] strArr, Bundle bundle) throws RemoteException {
        }

        @Override // android.accounts.IAccountAuthenticator
        public void startUpdateCredentialsSession(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, String str, Bundle bundle) throws RemoteException {
        }

        @Override // android.accounts.IAccountAuthenticator
        public void updateCredentials(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, String str, Bundle bundle) throws RemoteException {
        }
    }

    void addAccount(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, String str, String str2, String[] strArr, Bundle bundle) throws RemoteException;

    void addAccountFromCredentials(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, Bundle bundle) throws RemoteException;

    void confirmCredentials(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, Bundle bundle) throws RemoteException;

    void editProperties(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, String str) throws RemoteException;

    void finishSession(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, String str, Bundle bundle) throws RemoteException;

    void getAccountCredentialsForCloning(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account) throws RemoteException;

    void getAccountRemovalAllowed(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account) throws RemoteException;

    void getAuthToken(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, String str, Bundle bundle) throws RemoteException;

    void getAuthTokenLabel(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, String str) throws RemoteException;

    void hasFeatures(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, String[] strArr) throws RemoteException;

    void isCredentialsUpdateSuggested(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, String str) throws RemoteException;

    void startAddAccountSession(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, String str, String str2, String[] strArr, Bundle bundle) throws RemoteException;

    void startUpdateCredentialsSession(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, String str, Bundle bundle) throws RemoteException;

    void updateCredentials(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, String str, Bundle bundle) throws RemoteException;

    public static abstract class Stub extends Binder implements IAccountAuthenticator {
        public static final String DESCRIPTOR = "android.accounts.IAccountAuthenticator";
        static final int TRANSACTION_addAccount = 1;
        static final int TRANSACTION_addAccountFromCredentials = 10;
        static final int TRANSACTION_confirmCredentials = 2;
        static final int TRANSACTION_editProperties = 6;
        static final int TRANSACTION_finishSession = 13;
        static final int TRANSACTION_getAccountCredentialsForCloning = 9;
        static final int TRANSACTION_getAccountRemovalAllowed = 8;
        static final int TRANSACTION_getAuthToken = 3;
        static final int TRANSACTION_getAuthTokenLabel = 4;
        static final int TRANSACTION_hasFeatures = 7;
        static final int TRANSACTION_isCredentialsUpdateSuggested = 14;
        static final int TRANSACTION_startAddAccountSession = 11;
        static final int TRANSACTION_startUpdateCredentialsSession = 12;
        static final int TRANSACTION_updateCredentials = 5;
        private final PermissionEnforcer mEnforcer;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 13;
        }

        public Stub(PermissionEnforcer permissionEnforcer) {
            attachInterface(this, DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IAccountAuthenticator asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IAccountAuthenticator)) {
                return (IAccountAuthenticator) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "addAccount";
                case 2:
                    return "confirmCredentials";
                case 3:
                    return "getAuthToken";
                case 4:
                    return "getAuthTokenLabel";
                case 5:
                    return "updateCredentials";
                case 6:
                    return "editProperties";
                case 7:
                    return "hasFeatures";
                case 8:
                    return "getAccountRemovalAllowed";
                case 9:
                    return "getAccountCredentialsForCloning";
                case 10:
                    return "addAccountFromCredentials";
                case 11:
                    return "startAddAccountSession";
                case 12:
                    return "startUpdateCredentialsSession";
                case 13:
                    return "finishSession";
                case 14:
                    return "isCredentialsUpdateSuggested";
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
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    IAccountAuthenticatorResponse asInterface = IAccountAuthenticatorResponse.Stub.asInterface(parcel.readStrongBinder());
                    String readString = parcel.readString();
                    String readString2 = parcel.readString();
                    String[] createStringArray = parcel.createStringArray();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    addAccount(asInterface, readString, readString2, createStringArray, bundle);
                    return true;
                case 2:
                    IAccountAuthenticatorResponse asInterface2 = IAccountAuthenticatorResponse.Stub.asInterface(parcel.readStrongBinder());
                    Account account = (Account) parcel.readTypedObject(Account.CREATOR);
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    confirmCredentials(asInterface2, account, bundle2);
                    return true;
                case 3:
                    IAccountAuthenticatorResponse asInterface3 = IAccountAuthenticatorResponse.Stub.asInterface(parcel.readStrongBinder());
                    Account account2 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String readString3 = parcel.readString();
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    getAuthToken(asInterface3, account2, readString3, bundle3);
                    return true;
                case 4:
                    IAccountAuthenticatorResponse asInterface4 = IAccountAuthenticatorResponse.Stub.asInterface(parcel.readStrongBinder());
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    getAuthTokenLabel(asInterface4, readString4);
                    return true;
                case 5:
                    IAccountAuthenticatorResponse asInterface5 = IAccountAuthenticatorResponse.Stub.asInterface(parcel.readStrongBinder());
                    Account account3 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String readString5 = parcel.readString();
                    Bundle bundle4 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateCredentials(asInterface5, account3, readString5, bundle4);
                    return true;
                case 6:
                    IAccountAuthenticatorResponse asInterface6 = IAccountAuthenticatorResponse.Stub.asInterface(parcel.readStrongBinder());
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    editProperties(asInterface6, readString6);
                    return true;
                case 7:
                    IAccountAuthenticatorResponse asInterface7 = IAccountAuthenticatorResponse.Stub.asInterface(parcel.readStrongBinder());
                    Account account4 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String[] createStringArray2 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    hasFeatures(asInterface7, account4, createStringArray2);
                    return true;
                case 8:
                    IAccountAuthenticatorResponse asInterface8 = IAccountAuthenticatorResponse.Stub.asInterface(parcel.readStrongBinder());
                    Account account5 = (Account) parcel.readTypedObject(Account.CREATOR);
                    parcel.enforceNoDataAvail();
                    getAccountRemovalAllowed(asInterface8, account5);
                    return true;
                case 9:
                    IAccountAuthenticatorResponse asInterface9 = IAccountAuthenticatorResponse.Stub.asInterface(parcel.readStrongBinder());
                    Account account6 = (Account) parcel.readTypedObject(Account.CREATOR);
                    parcel.enforceNoDataAvail();
                    getAccountCredentialsForCloning(asInterface9, account6);
                    return true;
                case 10:
                    IAccountAuthenticatorResponse asInterface10 = IAccountAuthenticatorResponse.Stub.asInterface(parcel.readStrongBinder());
                    Account account7 = (Account) parcel.readTypedObject(Account.CREATOR);
                    Bundle bundle5 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    addAccountFromCredentials(asInterface10, account7, bundle5);
                    return true;
                case 11:
                    IAccountAuthenticatorResponse asInterface11 = IAccountAuthenticatorResponse.Stub.asInterface(parcel.readStrongBinder());
                    String readString7 = parcel.readString();
                    String readString8 = parcel.readString();
                    String[] createStringArray3 = parcel.createStringArray();
                    Bundle bundle6 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    startAddAccountSession(asInterface11, readString7, readString8, createStringArray3, bundle6);
                    return true;
                case 12:
                    IAccountAuthenticatorResponse asInterface12 = IAccountAuthenticatorResponse.Stub.asInterface(parcel.readStrongBinder());
                    Account account8 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String readString9 = parcel.readString();
                    Bundle bundle7 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    startUpdateCredentialsSession(asInterface12, account8, readString9, bundle7);
                    return true;
                case 13:
                    IAccountAuthenticatorResponse asInterface13 = IAccountAuthenticatorResponse.Stub.asInterface(parcel.readStrongBinder());
                    String readString10 = parcel.readString();
                    Bundle bundle8 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    finishSession(asInterface13, readString10, bundle8);
                    return true;
                case 14:
                    IAccountAuthenticatorResponse asInterface14 = IAccountAuthenticatorResponse.Stub.asInterface(parcel.readStrongBinder());
                    Account account9 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    isCredentialsUpdateSuggested(asInterface14, account9, readString11);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IAccountAuthenticator {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.accounts.IAccountAuthenticator
            public void addAccount(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, String str, String str2, String[] strArr, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccountAuthenticatorResponse);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStringArray(strArr);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountAuthenticator
            public void confirmCredentials(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccountAuthenticatorResponse);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountAuthenticator
            public void getAuthToken(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, String str, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccountAuthenticatorResponse);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountAuthenticator
            public void getAuthTokenLabel(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccountAuthenticatorResponse);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountAuthenticator
            public void updateCredentials(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, String str, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccountAuthenticatorResponse);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountAuthenticator
            public void editProperties(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccountAuthenticatorResponse);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountAuthenticator
            public void hasFeatures(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccountAuthenticatorResponse);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountAuthenticator
            public void getAccountRemovalAllowed(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccountAuthenticatorResponse);
                    obtain.writeTypedObject(account, 0);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountAuthenticator
            public void getAccountCredentialsForCloning(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccountAuthenticatorResponse);
                    obtain.writeTypedObject(account, 0);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountAuthenticator
            public void addAccountFromCredentials(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccountAuthenticatorResponse);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountAuthenticator
            public void startAddAccountSession(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, String str, String str2, String[] strArr, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccountAuthenticatorResponse);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStringArray(strArr);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountAuthenticator
            public void startUpdateCredentialsSession(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, String str, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccountAuthenticatorResponse);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountAuthenticator
            public void finishSession(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, String str, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccountAuthenticatorResponse);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountAuthenticator
            public void isCredentialsUpdateSuggested(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccountAuthenticatorResponse);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        protected void addAccount_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCOUNT_MANAGER, getCallingPid(), getCallingUid());
        }

        protected void confirmCredentials_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCOUNT_MANAGER, getCallingPid(), getCallingUid());
        }

        protected void getAuthToken_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCOUNT_MANAGER, getCallingPid(), getCallingUid());
        }

        protected void getAuthTokenLabel_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCOUNT_MANAGER, getCallingPid(), getCallingUid());
        }

        protected void updateCredentials_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCOUNT_MANAGER, getCallingPid(), getCallingUid());
        }

        protected void editProperties_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCOUNT_MANAGER, getCallingPid(), getCallingUid());
        }

        protected void hasFeatures_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCOUNT_MANAGER, getCallingPid(), getCallingUid());
        }

        protected void getAccountRemovalAllowed_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCOUNT_MANAGER, getCallingPid(), getCallingUid());
        }

        protected void getAccountCredentialsForCloning_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCOUNT_MANAGER, getCallingPid(), getCallingUid());
        }

        protected void addAccountFromCredentials_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCOUNT_MANAGER, getCallingPid(), getCallingUid());
        }

        protected void startAddAccountSession_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCOUNT_MANAGER, getCallingPid(), getCallingUid());
        }

        protected void startUpdateCredentialsSession_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCOUNT_MANAGER, getCallingPid(), getCallingUid());
        }

        protected void finishSession_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCOUNT_MANAGER, getCallingPid(), getCallingUid());
        }

        protected void isCredentialsUpdateSuggested_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCOUNT_MANAGER, getCallingPid(), getCallingUid());
        }
    }
}
