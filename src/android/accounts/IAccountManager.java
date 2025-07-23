package android.accounts;

import android.accounts.IAccountManagerResponse;
import android.content.IntentSender;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.UserHandle;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public interface IAccountManager extends IInterface {

    public static class Default implements IAccountManager {
        @Override // android.accounts.IAccountManager
        public boolean accountAuthenticated(Account account) throws RemoteException {
            return false;
        }

        @Override // android.accounts.IAccountManager
        public void addAccount(IAccountManagerResponse iAccountManagerResponse, String str, String str2, String[] strArr, boolean z, Bundle bundle) throws RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public void addAccountAsUser(IAccountManagerResponse iAccountManagerResponse, String str, String str2, String[] strArr, boolean z, Bundle bundle, int i) throws RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public boolean addAccountExplicitly(Account account, String str, Bundle bundle, String str2) throws RemoteException {
            return false;
        }

        @Override // android.accounts.IAccountManager
        public boolean addAccountExplicitlyWithVisibility(Account account, String str, Bundle bundle, Map map, String str2) throws RemoteException {
            return false;
        }

        @Override // android.accounts.IAccountManager
        public void addSharedAccountsFromParentUser(int i, int i2, String str) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.accounts.IAccountManager
        public void clearPassword(Account account) throws RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public void confirmCredentialsAsUser(IAccountManagerResponse iAccountManagerResponse, Account account, Bundle bundle, boolean z, int i) throws RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public void copyAccountToUser(IAccountManagerResponse iAccountManagerResponse, Account account, int i, int i2) throws RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public IntentSender createRequestAccountAccessIntentSenderAsUser(Account account, String str, UserHandle userHandle) throws RemoteException {
            return null;
        }

        @Override // android.accounts.IAccountManager
        public void editProperties(IAccountManagerResponse iAccountManagerResponse, String str, boolean z) throws RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public void finishSessionAsUser(IAccountManagerResponse iAccountManagerResponse, Bundle bundle, boolean z, Bundle bundle2, int i) throws RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public void getAccountByTypeAndFeatures(IAccountManagerResponse iAccountManagerResponse, String str, String[] strArr, String str2) throws RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public int getAccountVisibility(Account account, String str) throws RemoteException {
            return 0;
        }

        @Override // android.accounts.IAccountManager
        public Map getAccountsAndVisibilityForPackage(String str, String str2) throws RemoteException {
            return null;
        }

        @Override // android.accounts.IAccountManager
        public Account[] getAccountsAsUser(String str, int i, String str2) throws RemoteException {
            return null;
        }

        @Override // android.accounts.IAccountManager
        public void getAccountsByFeatures(IAccountManagerResponse iAccountManagerResponse, String str, String[] strArr, String str2) throws RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public Account[] getAccountsByTypeForPackage(String str, String str2, String str3) throws RemoteException {
            return null;
        }

        @Override // android.accounts.IAccountManager
        public Account[] getAccountsForPackage(String str, int i, String str2) throws RemoteException {
            return null;
        }

        @Override // android.accounts.IAccountManager
        public void getAuthToken(IAccountManagerResponse iAccountManagerResponse, Account account, String str, boolean z, boolean z2, Bundle bundle) throws RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public void getAuthTokenLabel(IAccountManagerResponse iAccountManagerResponse, String str, String str2) throws RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public AuthenticatorDescription[] getAuthenticatorTypes(int i) throws RemoteException {
            return null;
        }

        @Override // android.accounts.IAccountManager
        public Map getPackagesAndVisibilityForAccount(Account account) throws RemoteException {
            return null;
        }

        @Override // android.accounts.IAccountManager
        public String getPassword(Account account) throws RemoteException {
            return null;
        }

        @Override // android.accounts.IAccountManager
        public String getPreviousName(Account account) throws RemoteException {
            return null;
        }

        @Override // android.accounts.IAccountManager
        public String getUserData(Account account, String str) throws RemoteException {
            return null;
        }

        @Override // android.accounts.IAccountManager
        public boolean hasAccountAccess(Account account, String str, UserHandle userHandle) throws RemoteException {
            return false;
        }

        @Override // android.accounts.IAccountManager
        public void hasFeatures(IAccountManagerResponse iAccountManagerResponse, Account account, String[] strArr, int i, String str) throws RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public void invalidateAuthToken(String str, String str2) throws RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public void isCredentialsUpdateSuggested(IAccountManagerResponse iAccountManagerResponse, Account account, String str) throws RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public void onAccountAccessed(String str) throws RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public String peekAuthToken(Account account, String str) throws RemoteException {
            return null;
        }

        @Override // android.accounts.IAccountManager
        public void registerAccountListener(String[] strArr, String str) throws RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public void removeAccountAsUser(IAccountManagerResponse iAccountManagerResponse, Account account, boolean z, int i) throws RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public boolean removeAccountExplicitly(Account account) throws RemoteException {
            return false;
        }

        @Override // android.accounts.IAccountManager
        public void renameAccount(IAccountManagerResponse iAccountManagerResponse, Account account, String str) throws RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public boolean setAccountVisibility(Account account, String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.accounts.IAccountManager
        public void setAuthToken(Account account, String str, String str2) throws RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public void setPassword(Account account, String str) throws RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public void setUserData(Account account, String str, String str2) throws RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public boolean someUserHasAccount(Account account) throws RemoteException {
            return false;
        }

        @Override // android.accounts.IAccountManager
        public void startAddAccountSession(IAccountManagerResponse iAccountManagerResponse, String str, String str2, String[] strArr, boolean z, Bundle bundle) throws RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public void startUpdateCredentialsSession(IAccountManagerResponse iAccountManagerResponse, Account account, String str, boolean z, Bundle bundle) throws RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public void unregisterAccountListener(String[] strArr, String str) throws RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public void updateAppPermission(Account account, String str, int i, boolean z) throws RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public void updateCredentials(IAccountManagerResponse iAccountManagerResponse, Account account, String str, boolean z, Bundle bundle) throws RemoteException {
        }
    }

    boolean accountAuthenticated(Account account) throws RemoteException;

    void addAccount(IAccountManagerResponse iAccountManagerResponse, String str, String str2, String[] strArr, boolean z, Bundle bundle) throws RemoteException;

    void addAccountAsUser(IAccountManagerResponse iAccountManagerResponse, String str, String str2, String[] strArr, boolean z, Bundle bundle, int i) throws RemoteException;

    boolean addAccountExplicitly(Account account, String str, Bundle bundle, String str2) throws RemoteException;

    boolean addAccountExplicitlyWithVisibility(Account account, String str, Bundle bundle, Map map, String str2) throws RemoteException;

    void addSharedAccountsFromParentUser(int i, int i2, String str) throws RemoteException;

    void clearPassword(Account account) throws RemoteException;

    void confirmCredentialsAsUser(IAccountManagerResponse iAccountManagerResponse, Account account, Bundle bundle, boolean z, int i) throws RemoteException;

    void copyAccountToUser(IAccountManagerResponse iAccountManagerResponse, Account account, int i, int i2) throws RemoteException;

    IntentSender createRequestAccountAccessIntentSenderAsUser(Account account, String str, UserHandle userHandle) throws RemoteException;

    void editProperties(IAccountManagerResponse iAccountManagerResponse, String str, boolean z) throws RemoteException;

    void finishSessionAsUser(IAccountManagerResponse iAccountManagerResponse, Bundle bundle, boolean z, Bundle bundle2, int i) throws RemoteException;

    void getAccountByTypeAndFeatures(IAccountManagerResponse iAccountManagerResponse, String str, String[] strArr, String str2) throws RemoteException;

    int getAccountVisibility(Account account, String str) throws RemoteException;

    Map getAccountsAndVisibilityForPackage(String str, String str2) throws RemoteException;

    Account[] getAccountsAsUser(String str, int i, String str2) throws RemoteException;

    void getAccountsByFeatures(IAccountManagerResponse iAccountManagerResponse, String str, String[] strArr, String str2) throws RemoteException;

    Account[] getAccountsByTypeForPackage(String str, String str2, String str3) throws RemoteException;

    Account[] getAccountsForPackage(String str, int i, String str2) throws RemoteException;

    void getAuthToken(IAccountManagerResponse iAccountManagerResponse, Account account, String str, boolean z, boolean z2, Bundle bundle) throws RemoteException;

    void getAuthTokenLabel(IAccountManagerResponse iAccountManagerResponse, String str, String str2) throws RemoteException;

    AuthenticatorDescription[] getAuthenticatorTypes(int i) throws RemoteException;

    Map getPackagesAndVisibilityForAccount(Account account) throws RemoteException;

    String getPassword(Account account) throws RemoteException;

    String getPreviousName(Account account) throws RemoteException;

    String getUserData(Account account, String str) throws RemoteException;

    boolean hasAccountAccess(Account account, String str, UserHandle userHandle) throws RemoteException;

    void hasFeatures(IAccountManagerResponse iAccountManagerResponse, Account account, String[] strArr, int i, String str) throws RemoteException;

    void invalidateAuthToken(String str, String str2) throws RemoteException;

    void isCredentialsUpdateSuggested(IAccountManagerResponse iAccountManagerResponse, Account account, String str) throws RemoteException;

    void onAccountAccessed(String str) throws RemoteException;

    String peekAuthToken(Account account, String str) throws RemoteException;

    void registerAccountListener(String[] strArr, String str) throws RemoteException;

    void removeAccountAsUser(IAccountManagerResponse iAccountManagerResponse, Account account, boolean z, int i) throws RemoteException;

    boolean removeAccountExplicitly(Account account) throws RemoteException;

    void renameAccount(IAccountManagerResponse iAccountManagerResponse, Account account, String str) throws RemoteException;

    boolean setAccountVisibility(Account account, String str, int i) throws RemoteException;

    void setAuthToken(Account account, String str, String str2) throws RemoteException;

    void setPassword(Account account, String str) throws RemoteException;

    void setUserData(Account account, String str, String str2) throws RemoteException;

    boolean someUserHasAccount(Account account) throws RemoteException;

    void startAddAccountSession(IAccountManagerResponse iAccountManagerResponse, String str, String str2, String[] strArr, boolean z, Bundle bundle) throws RemoteException;

    void startUpdateCredentialsSession(IAccountManagerResponse iAccountManagerResponse, Account account, String str, boolean z, Bundle bundle) throws RemoteException;

    void unregisterAccountListener(String[] strArr, String str) throws RemoteException;

    void updateAppPermission(Account account, String str, int i, boolean z) throws RemoteException;

    void updateCredentials(IAccountManagerResponse iAccountManagerResponse, Account account, String str, boolean z, Bundle bundle) throws RemoteException;

    public static abstract class Stub extends Binder implements IAccountManager {
        public static final String DESCRIPTOR = "android.accounts.IAccountManager";
        static final int TRANSACTION_accountAuthenticated = 27;
        static final int TRANSACTION_addAccount = 22;
        static final int TRANSACTION_addAccountAsUser = 23;
        static final int TRANSACTION_addAccountExplicitly = 10;
        static final int TRANSACTION_addAccountExplicitlyWithVisibility = 38;
        static final int TRANSACTION_addSharedAccountsFromParentUser = 29;
        static final int TRANSACTION_clearPassword = 18;
        static final int TRANSACTION_confirmCredentialsAsUser = 26;
        static final int TRANSACTION_copyAccountToUser = 13;
        static final int TRANSACTION_createRequestAccountAccessIntentSenderAsUser = 45;
        static final int TRANSACTION_editProperties = 25;
        static final int TRANSACTION_finishSessionAsUser = 34;
        static final int TRANSACTION_getAccountByTypeAndFeatures = 8;
        static final int TRANSACTION_getAccountVisibility = 40;
        static final int TRANSACTION_getAccountsAndVisibilityForPackage = 41;
        static final int TRANSACTION_getAccountsAsUser = 6;
        static final int TRANSACTION_getAccountsByFeatures = 9;
        static final int TRANSACTION_getAccountsByTypeForPackage = 5;
        static final int TRANSACTION_getAccountsForPackage = 4;
        static final int TRANSACTION_getAuthToken = 21;
        static final int TRANSACTION_getAuthTokenLabel = 28;
        static final int TRANSACTION_getAuthenticatorTypes = 3;
        static final int TRANSACTION_getPackagesAndVisibilityForAccount = 37;
        static final int TRANSACTION_getPassword = 1;
        static final int TRANSACTION_getPreviousName = 31;
        static final int TRANSACTION_getUserData = 2;
        static final int TRANSACTION_hasAccountAccess = 44;
        static final int TRANSACTION_hasFeatures = 7;
        static final int TRANSACTION_invalidateAuthToken = 14;
        static final int TRANSACTION_isCredentialsUpdateSuggested = 36;
        static final int TRANSACTION_onAccountAccessed = 46;
        static final int TRANSACTION_peekAuthToken = 15;
        static final int TRANSACTION_registerAccountListener = 42;
        static final int TRANSACTION_removeAccountAsUser = 11;
        static final int TRANSACTION_removeAccountExplicitly = 12;
        static final int TRANSACTION_renameAccount = 30;
        static final int TRANSACTION_setAccountVisibility = 39;
        static final int TRANSACTION_setAuthToken = 16;
        static final int TRANSACTION_setPassword = 17;
        static final int TRANSACTION_setUserData = 19;
        static final int TRANSACTION_someUserHasAccount = 35;
        static final int TRANSACTION_startAddAccountSession = 32;
        static final int TRANSACTION_startUpdateCredentialsSession = 33;
        static final int TRANSACTION_unregisterAccountListener = 43;
        static final int TRANSACTION_updateAppPermission = 20;
        static final int TRANSACTION_updateCredentials = 24;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 45;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAccountManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IAccountManager)) {
                return (IAccountManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getPassword";
                case 2:
                    return "getUserData";
                case 3:
                    return "getAuthenticatorTypes";
                case 4:
                    return "getAccountsForPackage";
                case 5:
                    return "getAccountsByTypeForPackage";
                case 6:
                    return "getAccountsAsUser";
                case 7:
                    return "hasFeatures";
                case 8:
                    return "getAccountByTypeAndFeatures";
                case 9:
                    return "getAccountsByFeatures";
                case 10:
                    return "addAccountExplicitly";
                case 11:
                    return "removeAccountAsUser";
                case 12:
                    return "removeAccountExplicitly";
                case 13:
                    return "copyAccountToUser";
                case 14:
                    return "invalidateAuthToken";
                case 15:
                    return "peekAuthToken";
                case 16:
                    return "setAuthToken";
                case 17:
                    return "setPassword";
                case 18:
                    return "clearPassword";
                case 19:
                    return "setUserData";
                case 20:
                    return "updateAppPermission";
                case 21:
                    return "getAuthToken";
                case 22:
                    return "addAccount";
                case 23:
                    return "addAccountAsUser";
                case 24:
                    return "updateCredentials";
                case 25:
                    return "editProperties";
                case 26:
                    return "confirmCredentialsAsUser";
                case 27:
                    return "accountAuthenticated";
                case 28:
                    return "getAuthTokenLabel";
                case 29:
                    return "addSharedAccountsFromParentUser";
                case 30:
                    return "renameAccount";
                case 31:
                    return "getPreviousName";
                case 32:
                    return "startAddAccountSession";
                case 33:
                    return "startUpdateCredentialsSession";
                case 34:
                    return "finishSessionAsUser";
                case 35:
                    return "someUserHasAccount";
                case 36:
                    return "isCredentialsUpdateSuggested";
                case 37:
                    return "getPackagesAndVisibilityForAccount";
                case 38:
                    return "addAccountExplicitlyWithVisibility";
                case 39:
                    return "setAccountVisibility";
                case 40:
                    return "getAccountVisibility";
                case 41:
                    return "getAccountsAndVisibilityForPackage";
                case 42:
                    return "registerAccountListener";
                case 43:
                    return "unregisterAccountListener";
                case 44:
                    return "hasAccountAccess";
                case 45:
                    return "createRequestAccountAccessIntentSenderAsUser";
                case 46:
                    return "onAccountAccessed";
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
                    Account account = (Account) parcel.readTypedObject(Account.CREATOR);
                    parcel.enforceNoDataAvail();
                    String password = getPassword(account);
                    parcel2.writeNoException();
                    parcel2.writeString(password);
                    return true;
                case 2:
                    Account account2 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String userData = getUserData(account2, readString);
                    parcel2.writeNoException();
                    parcel2.writeString(userData);
                    return true;
                case 3:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    AuthenticatorDescription[] authenticatorTypes = getAuthenticatorTypes(readInt);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(authenticatorTypes, 1);
                    return true;
                case 4:
                    String readString2 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Account[] accountsForPackage = getAccountsForPackage(readString2, readInt2, readString3);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(accountsForPackage, 1);
                    return true;
                case 5:
                    String readString4 = parcel.readString();
                    String readString5 = parcel.readString();
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Account[] accountsByTypeForPackage = getAccountsByTypeForPackage(readString4, readString5, readString6);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(accountsByTypeForPackage, 1);
                    return true;
                case 6:
                    String readString7 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Account[] accountsAsUser = getAccountsAsUser(readString7, readInt3, readString8);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(accountsAsUser, 1);
                    return true;
                case 7:
                    IAccountManagerResponse asInterface = IAccountManagerResponse.Stub.asInterface(parcel.readStrongBinder());
                    Account account3 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String[] createStringArray = parcel.createStringArray();
                    int readInt4 = parcel.readInt();
                    String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    hasFeatures(asInterface, account3, createStringArray, readInt4, readString9);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    IAccountManagerResponse asInterface2 = IAccountManagerResponse.Stub.asInterface(parcel.readStrongBinder());
                    String readString10 = parcel.readString();
                    String[] createStringArray2 = parcel.createStringArray();
                    String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    getAccountByTypeAndFeatures(asInterface2, readString10, createStringArray2, readString11);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    IAccountManagerResponse asInterface3 = IAccountManagerResponse.Stub.asInterface(parcel.readStrongBinder());
                    String readString12 = parcel.readString();
                    String[] createStringArray3 = parcel.createStringArray();
                    String readString13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    getAccountsByFeatures(asInterface3, readString12, createStringArray3, readString13);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    Account account4 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String readString14 = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    String readString15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean addAccountExplicitly = addAccountExplicitly(account4, readString14, bundle, readString15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addAccountExplicitly);
                    return true;
                case 11:
                    IAccountManagerResponse asInterface4 = IAccountManagerResponse.Stub.asInterface(parcel.readStrongBinder());
                    Account account5 = (Account) parcel.readTypedObject(Account.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeAccountAsUser(asInterface4, account5, readBoolean, readInt5);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    Account account6 = (Account) parcel.readTypedObject(Account.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean removeAccountExplicitly = removeAccountExplicitly(account6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeAccountExplicitly);
                    return true;
                case 13:
                    IAccountManagerResponse asInterface5 = IAccountManagerResponse.Stub.asInterface(parcel.readStrongBinder());
                    Account account7 = (Account) parcel.readTypedObject(Account.CREATOR);
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    copyAccountToUser(asInterface5, account7, readInt6, readInt7);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    String readString16 = parcel.readString();
                    String readString17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    invalidateAuthToken(readString16, readString17);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    Account account8 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String readString18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String peekAuthToken = peekAuthToken(account8, readString18);
                    parcel2.writeNoException();
                    parcel2.writeString(peekAuthToken);
                    return true;
                case 16:
                    Account account9 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String readString19 = parcel.readString();
                    String readString20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setAuthToken(account9, readString19, readString20);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    Account account10 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String readString21 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setPassword(account10, readString21);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    Account account11 = (Account) parcel.readTypedObject(Account.CREATOR);
                    parcel.enforceNoDataAvail();
                    clearPassword(account11);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    Account account12 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String readString22 = parcel.readString();
                    String readString23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setUserData(account12, readString22, readString23);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    Account account13 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String readString24 = parcel.readString();
                    int readInt8 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    updateAppPermission(account13, readString24, readInt8, readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    IAccountManagerResponse asInterface6 = IAccountManagerResponse.Stub.asInterface(parcel.readStrongBinder());
                    Account account14 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String readString25 = parcel.readString();
                    boolean readBoolean3 = parcel.readBoolean();
                    boolean readBoolean4 = parcel.readBoolean();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    getAuthToken(asInterface6, account14, readString25, readBoolean3, readBoolean4, bundle2);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    IAccountManagerResponse asInterface7 = IAccountManagerResponse.Stub.asInterface(parcel.readStrongBinder());
                    String readString26 = parcel.readString();
                    String readString27 = parcel.readString();
                    String[] createStringArray4 = parcel.createStringArray();
                    boolean readBoolean5 = parcel.readBoolean();
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    addAccount(asInterface7, readString26, readString27, createStringArray4, readBoolean5, bundle3);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    IAccountManagerResponse asInterface8 = IAccountManagerResponse.Stub.asInterface(parcel.readStrongBinder());
                    String readString28 = parcel.readString();
                    String readString29 = parcel.readString();
                    String[] createStringArray5 = parcel.createStringArray();
                    boolean readBoolean6 = parcel.readBoolean();
                    Bundle bundle4 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addAccountAsUser(asInterface8, readString28, readString29, createStringArray5, readBoolean6, bundle4, readInt9);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    IAccountManagerResponse asInterface9 = IAccountManagerResponse.Stub.asInterface(parcel.readStrongBinder());
                    Account account15 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String readString30 = parcel.readString();
                    boolean readBoolean7 = parcel.readBoolean();
                    Bundle bundle5 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateCredentials(asInterface9, account15, readString30, readBoolean7, bundle5);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    IAccountManagerResponse asInterface10 = IAccountManagerResponse.Stub.asInterface(parcel.readStrongBinder());
                    String readString31 = parcel.readString();
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    editProperties(asInterface10, readString31, readBoolean8);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    IAccountManagerResponse asInterface11 = IAccountManagerResponse.Stub.asInterface(parcel.readStrongBinder());
                    Account account16 = (Account) parcel.readTypedObject(Account.CREATOR);
                    Bundle bundle6 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    boolean readBoolean9 = parcel.readBoolean();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    confirmCredentialsAsUser(asInterface11, account16, bundle6, readBoolean9, readInt10);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    Account account17 = (Account) parcel.readTypedObject(Account.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean accountAuthenticated = accountAuthenticated(account17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(accountAuthenticated);
                    return true;
                case 28:
                    IAccountManagerResponse asInterface12 = IAccountManagerResponse.Stub.asInterface(parcel.readStrongBinder());
                    String readString32 = parcel.readString();
                    String readString33 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    getAuthTokenLabel(asInterface12, readString32, readString33);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    String readString34 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addSharedAccountsFromParentUser(readInt11, readInt12, readString34);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    IAccountManagerResponse asInterface13 = IAccountManagerResponse.Stub.asInterface(parcel.readStrongBinder());
                    Account account18 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String readString35 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    renameAccount(asInterface13, account18, readString35);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    Account account19 = (Account) parcel.readTypedObject(Account.CREATOR);
                    parcel.enforceNoDataAvail();
                    String previousName = getPreviousName(account19);
                    parcel2.writeNoException();
                    parcel2.writeString(previousName);
                    return true;
                case 32:
                    IAccountManagerResponse asInterface14 = IAccountManagerResponse.Stub.asInterface(parcel.readStrongBinder());
                    String readString36 = parcel.readString();
                    String readString37 = parcel.readString();
                    String[] createStringArray6 = parcel.createStringArray();
                    boolean readBoolean10 = parcel.readBoolean();
                    Bundle bundle7 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    startAddAccountSession(asInterface14, readString36, readString37, createStringArray6, readBoolean10, bundle7);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    IAccountManagerResponse asInterface15 = IAccountManagerResponse.Stub.asInterface(parcel.readStrongBinder());
                    Account account20 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String readString38 = parcel.readString();
                    boolean readBoolean11 = parcel.readBoolean();
                    Bundle bundle8 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    startUpdateCredentialsSession(asInterface15, account20, readString38, readBoolean11, bundle8);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    IAccountManagerResponse asInterface16 = IAccountManagerResponse.Stub.asInterface(parcel.readStrongBinder());
                    Bundle bundle9 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    boolean readBoolean12 = parcel.readBoolean();
                    Bundle bundle10 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    finishSessionAsUser(asInterface16, bundle9, readBoolean12, bundle10, readInt13);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    Account account21 = (Account) parcel.readTypedObject(Account.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean someUserHasAccount = someUserHasAccount(account21);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(someUserHasAccount);
                    return true;
                case 36:
                    IAccountManagerResponse asInterface17 = IAccountManagerResponse.Stub.asInterface(parcel.readStrongBinder());
                    Account account22 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String readString39 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    isCredentialsUpdateSuggested(asInterface17, account22, readString39);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    Account account23 = (Account) parcel.readTypedObject(Account.CREATOR);
                    parcel.enforceNoDataAvail();
                    Map packagesAndVisibilityForAccount = getPackagesAndVisibilityForAccount(account23);
                    parcel2.writeNoException();
                    parcel2.writeMap(packagesAndVisibilityForAccount);
                    return true;
                case 38:
                    Account account24 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String readString40 = parcel.readString();
                    Bundle bundle11 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    HashMap readHashMap = parcel.readHashMap(getClass().getClassLoader());
                    String readString41 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean addAccountExplicitlyWithVisibility = addAccountExplicitlyWithVisibility(account24, readString40, bundle11, readHashMap, readString41);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addAccountExplicitlyWithVisibility);
                    return true;
                case 39:
                    Account account25 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String readString42 = parcel.readString();
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean accountVisibility = setAccountVisibility(account25, readString42, readInt14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(accountVisibility);
                    return true;
                case 40:
                    Account account26 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String readString43 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int accountVisibility2 = getAccountVisibility(account26, readString43);
                    parcel2.writeNoException();
                    parcel2.writeInt(accountVisibility2);
                    return true;
                case 41:
                    String readString44 = parcel.readString();
                    String readString45 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Map accountsAndVisibilityForPackage = getAccountsAndVisibilityForPackage(readString44, readString45);
                    parcel2.writeNoException();
                    parcel2.writeMap(accountsAndVisibilityForPackage);
                    return true;
                case 42:
                    String[] createStringArray7 = parcel.createStringArray();
                    String readString46 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    registerAccountListener(createStringArray7, readString46);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    String[] createStringArray8 = parcel.createStringArray();
                    String readString47 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unregisterAccountListener(createStringArray8, readString47);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    Account account27 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String readString48 = parcel.readString();
                    UserHandle userHandle = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean hasAccountAccess = hasAccountAccess(account27, readString48, userHandle);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasAccountAccess);
                    return true;
                case 45:
                    Account account28 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String readString49 = parcel.readString();
                    UserHandle userHandle2 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    IntentSender createRequestAccountAccessIntentSenderAsUser = createRequestAccountAccessIntentSenderAsUser(account28, readString49, userHandle2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(createRequestAccountAccessIntentSenderAsUser, 1);
                    return true;
                case 46:
                    String readString50 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onAccountAccessed(readString50);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IAccountManager {
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

            @Override // android.accounts.IAccountManager
            public String getPassword(Account account) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public String getUserData(Account account, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public AuthenticatorDescription[] getAuthenticatorTypes(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (AuthenticatorDescription[]) obtain2.createTypedArray(AuthenticatorDescription.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public Account[] getAccountsForPackage(String str, int i, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Account[]) obtain2.createTypedArray(Account.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public Account[] getAccountsByTypeForPackage(String str, String str2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Account[]) obtain2.createTypedArray(Account.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public Account[] getAccountsAsUser(String str, int i, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Account[]) obtain2.createTypedArray(Account.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void hasFeatures(IAccountManagerResponse iAccountManagerResponse, Account account, String[] strArr, int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccountManagerResponse);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeStringArray(strArr);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void getAccountByTypeAndFeatures(IAccountManagerResponse iAccountManagerResponse, String str, String[] strArr, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccountManagerResponse);
                    obtain.writeString(str);
                    obtain.writeStringArray(strArr);
                    obtain.writeString(str2);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void getAccountsByFeatures(IAccountManagerResponse iAccountManagerResponse, String str, String[] strArr, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccountManagerResponse);
                    obtain.writeString(str);
                    obtain.writeStringArray(strArr);
                    obtain.writeString(str2);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public boolean addAccountExplicitly(Account account, String str, Bundle bundle, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeString(str2);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void removeAccountAsUser(IAccountManagerResponse iAccountManagerResponse, Account account, boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccountManagerResponse);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public boolean removeAccountExplicitly(Account account) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void copyAccountToUser(IAccountManagerResponse iAccountManagerResponse, Account account, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccountManagerResponse);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void invalidateAuthToken(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public String peekAuthToken(Account account, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void setAuthToken(Account account, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void setPassword(Account account, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void clearPassword(Account account) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void setUserData(Account account, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void updateAppPermission(Account account, String str, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void getAuthToken(IAccountManagerResponse iAccountManagerResponse, Account account, String str, boolean z, boolean z2, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccountManagerResponse);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void addAccount(IAccountManagerResponse iAccountManagerResponse, String str, String str2, String[] strArr, boolean z, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccountManagerResponse);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStringArray(strArr);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void addAccountAsUser(IAccountManagerResponse iAccountManagerResponse, String str, String str2, String[] strArr, boolean z, Bundle bundle, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccountManagerResponse);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStringArray(strArr);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void updateCredentials(IAccountManagerResponse iAccountManagerResponse, Account account, String str, boolean z, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccountManagerResponse);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void editProperties(IAccountManagerResponse iAccountManagerResponse, String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccountManagerResponse);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void confirmCredentialsAsUser(IAccountManagerResponse iAccountManagerResponse, Account account, Bundle bundle, boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccountManagerResponse);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public boolean accountAuthenticated(Account account) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void getAuthTokenLabel(IAccountManagerResponse iAccountManagerResponse, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccountManagerResponse);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void addSharedAccountsFromParentUser(int i, int i2, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void renameAccount(IAccountManagerResponse iAccountManagerResponse, Account account, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccountManagerResponse);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public String getPreviousName(Account account) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void startAddAccountSession(IAccountManagerResponse iAccountManagerResponse, String str, String str2, String[] strArr, boolean z, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccountManagerResponse);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStringArray(strArr);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void startUpdateCredentialsSession(IAccountManagerResponse iAccountManagerResponse, Account account, String str, boolean z, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccountManagerResponse);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void finishSessionAsUser(IAccountManagerResponse iAccountManagerResponse, Bundle bundle, boolean z, Bundle bundle2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccountManagerResponse);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(bundle2, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public boolean someUserHasAccount(Account account) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void isCredentialsUpdateSuggested(IAccountManagerResponse iAccountManagerResponse, Account account, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccountManagerResponse);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public Map getPackagesAndVisibilityForAccount(Account account) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readHashMap(getClass().getClassLoader());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public boolean addAccountExplicitlyWithVisibility(Account account, String str, Bundle bundle, Map map, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeMap(map);
                    obtain.writeString(str2);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public boolean setAccountVisibility(Account account, String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public int getAccountVisibility(Account account, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public Map getAccountsAndVisibilityForPackage(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readHashMap(getClass().getClassLoader());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void registerAccountListener(String[] strArr, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    obtain.writeString(str);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void unregisterAccountListener(String[] strArr, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    obtain.writeString(str);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public boolean hasAccountAccess(Account account, String str, UserHandle userHandle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public IntentSender createRequestAccountAccessIntentSenderAsUser(Account account, String str, UserHandle userHandle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                    return (IntentSender) obtain2.readTypedObject(IntentSender.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void onAccountAccessed(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
