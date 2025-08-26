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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IAccountManager)) {
                return (IAccountManager) iInterfaceQueryLocalInterface;
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
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String userData = getUserData(account2, string);
                    parcel2.writeNoException();
                    parcel2.writeString(userData);
                    return true;
                case 3:
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    AuthenticatorDescription[] authenticatorTypes = getAuthenticatorTypes(i3);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(authenticatorTypes, 1);
                    return true;
                case 4:
                    String string2 = parcel.readString();
                    int i4 = parcel.readInt();
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Account[] accountsForPackage = getAccountsForPackage(string2, i4, string3);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(accountsForPackage, 1);
                    return true;
                case 5:
                    String string4 = parcel.readString();
                    String string5 = parcel.readString();
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Account[] accountsByTypeForPackage = getAccountsByTypeForPackage(string4, string5, string6);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(accountsByTypeForPackage, 1);
                    return true;
                case 6:
                    String string7 = parcel.readString();
                    int i5 = parcel.readInt();
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Account[] accountsAsUser = getAccountsAsUser(string7, i5, string8);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(accountsAsUser, 1);
                    return true;
                case 7:
                    IAccountManagerResponse iAccountManagerResponseAsInterface = IAccountManagerResponse.Stub.asInterface(parcel.readStrongBinder());
                    Account account3 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String[] strArrCreateStringArray = parcel.createStringArray();
                    int i6 = parcel.readInt();
                    String string9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    hasFeatures(iAccountManagerResponseAsInterface, account3, strArrCreateStringArray, i6, string9);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    IAccountManagerResponse iAccountManagerResponseAsInterface2 = IAccountManagerResponse.Stub.asInterface(parcel.readStrongBinder());
                    String string10 = parcel.readString();
                    String[] strArrCreateStringArray2 = parcel.createStringArray();
                    String string11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    getAccountByTypeAndFeatures(iAccountManagerResponseAsInterface2, string10, strArrCreateStringArray2, string11);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    IAccountManagerResponse iAccountManagerResponseAsInterface3 = IAccountManagerResponse.Stub.asInterface(parcel.readStrongBinder());
                    String string12 = parcel.readString();
                    String[] strArrCreateStringArray3 = parcel.createStringArray();
                    String string13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    getAccountsByFeatures(iAccountManagerResponseAsInterface3, string12, strArrCreateStringArray3, string13);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    Account account4 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String string14 = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    String string15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zAddAccountExplicitly = addAccountExplicitly(account4, string14, bundle, string15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddAccountExplicitly);
                    return true;
                case 11:
                    IAccountManagerResponse iAccountManagerResponseAsInterface4 = IAccountManagerResponse.Stub.asInterface(parcel.readStrongBinder());
                    Account account5 = (Account) parcel.readTypedObject(Account.CREATOR);
                    boolean z = parcel.readBoolean();
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeAccountAsUser(iAccountManagerResponseAsInterface4, account5, z, i7);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    Account account6 = (Account) parcel.readTypedObject(Account.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zRemoveAccountExplicitly = removeAccountExplicitly(account6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveAccountExplicitly);
                    return true;
                case 13:
                    IAccountManagerResponse iAccountManagerResponseAsInterface5 = IAccountManagerResponse.Stub.asInterface(parcel.readStrongBinder());
                    Account account7 = (Account) parcel.readTypedObject(Account.CREATOR);
                    int i8 = parcel.readInt();
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    copyAccountToUser(iAccountManagerResponseAsInterface5, account7, i8, i9);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    String string16 = parcel.readString();
                    String string17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    invalidateAuthToken(string16, string17);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    Account account8 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String string18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String strPeekAuthToken = peekAuthToken(account8, string18);
                    parcel2.writeNoException();
                    parcel2.writeString(strPeekAuthToken);
                    return true;
                case 16:
                    Account account9 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String string19 = parcel.readString();
                    String string20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setAuthToken(account9, string19, string20);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    Account account10 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String string21 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setPassword(account10, string21);
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
                    String string22 = parcel.readString();
                    String string23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setUserData(account12, string22, string23);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    Account account13 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String string24 = parcel.readString();
                    int i10 = parcel.readInt();
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    updateAppPermission(account13, string24, i10, z2);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    IAccountManagerResponse iAccountManagerResponseAsInterface6 = IAccountManagerResponse.Stub.asInterface(parcel.readStrongBinder());
                    Account account14 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String string25 = parcel.readString();
                    boolean z3 = parcel.readBoolean();
                    boolean z4 = parcel.readBoolean();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    getAuthToken(iAccountManagerResponseAsInterface6, account14, string25, z3, z4, bundle2);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    IAccountManagerResponse iAccountManagerResponseAsInterface7 = IAccountManagerResponse.Stub.asInterface(parcel.readStrongBinder());
                    String string26 = parcel.readString();
                    String string27 = parcel.readString();
                    String[] strArrCreateStringArray4 = parcel.createStringArray();
                    boolean z5 = parcel.readBoolean();
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    addAccount(iAccountManagerResponseAsInterface7, string26, string27, strArrCreateStringArray4, z5, bundle3);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    IAccountManagerResponse iAccountManagerResponseAsInterface8 = IAccountManagerResponse.Stub.asInterface(parcel.readStrongBinder());
                    String string28 = parcel.readString();
                    String string29 = parcel.readString();
                    String[] strArrCreateStringArray5 = parcel.createStringArray();
                    boolean z6 = parcel.readBoolean();
                    Bundle bundle4 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addAccountAsUser(iAccountManagerResponseAsInterface8, string28, string29, strArrCreateStringArray5, z6, bundle4, i11);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    IAccountManagerResponse iAccountManagerResponseAsInterface9 = IAccountManagerResponse.Stub.asInterface(parcel.readStrongBinder());
                    Account account15 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String string30 = parcel.readString();
                    boolean z7 = parcel.readBoolean();
                    Bundle bundle5 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateCredentials(iAccountManagerResponseAsInterface9, account15, string30, z7, bundle5);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    IAccountManagerResponse iAccountManagerResponseAsInterface10 = IAccountManagerResponse.Stub.asInterface(parcel.readStrongBinder());
                    String string31 = parcel.readString();
                    boolean z8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    editProperties(iAccountManagerResponseAsInterface10, string31, z8);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    IAccountManagerResponse iAccountManagerResponseAsInterface11 = IAccountManagerResponse.Stub.asInterface(parcel.readStrongBinder());
                    Account account16 = (Account) parcel.readTypedObject(Account.CREATOR);
                    Bundle bundle6 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    boolean z9 = parcel.readBoolean();
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    confirmCredentialsAsUser(iAccountManagerResponseAsInterface11, account16, bundle6, z9, i12);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    Account account17 = (Account) parcel.readTypedObject(Account.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zAccountAuthenticated = accountAuthenticated(account17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAccountAuthenticated);
                    return true;
                case 28:
                    IAccountManagerResponse iAccountManagerResponseAsInterface12 = IAccountManagerResponse.Stub.asInterface(parcel.readStrongBinder());
                    String string32 = parcel.readString();
                    String string33 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    getAuthTokenLabel(iAccountManagerResponseAsInterface12, string32, string33);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    int i13 = parcel.readInt();
                    int i14 = parcel.readInt();
                    String string34 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addSharedAccountsFromParentUser(i13, i14, string34);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    IAccountManagerResponse iAccountManagerResponseAsInterface13 = IAccountManagerResponse.Stub.asInterface(parcel.readStrongBinder());
                    Account account18 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String string35 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    renameAccount(iAccountManagerResponseAsInterface13, account18, string35);
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
                    IAccountManagerResponse iAccountManagerResponseAsInterface14 = IAccountManagerResponse.Stub.asInterface(parcel.readStrongBinder());
                    String string36 = parcel.readString();
                    String string37 = parcel.readString();
                    String[] strArrCreateStringArray6 = parcel.createStringArray();
                    boolean z10 = parcel.readBoolean();
                    Bundle bundle7 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    startAddAccountSession(iAccountManagerResponseAsInterface14, string36, string37, strArrCreateStringArray6, z10, bundle7);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    IAccountManagerResponse iAccountManagerResponseAsInterface15 = IAccountManagerResponse.Stub.asInterface(parcel.readStrongBinder());
                    Account account20 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String string38 = parcel.readString();
                    boolean z11 = parcel.readBoolean();
                    Bundle bundle8 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    startUpdateCredentialsSession(iAccountManagerResponseAsInterface15, account20, string38, z11, bundle8);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    IAccountManagerResponse iAccountManagerResponseAsInterface16 = IAccountManagerResponse.Stub.asInterface(parcel.readStrongBinder());
                    Bundle bundle9 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    boolean z12 = parcel.readBoolean();
                    Bundle bundle10 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    finishSessionAsUser(iAccountManagerResponseAsInterface16, bundle9, z12, bundle10, i15);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    Account account21 = (Account) parcel.readTypedObject(Account.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zSomeUserHasAccount = someUserHasAccount(account21);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSomeUserHasAccount);
                    return true;
                case 36:
                    IAccountManagerResponse iAccountManagerResponseAsInterface17 = IAccountManagerResponse.Stub.asInterface(parcel.readStrongBinder());
                    Account account22 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String string39 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    isCredentialsUpdateSuggested(iAccountManagerResponseAsInterface17, account22, string39);
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
                    String string40 = parcel.readString();
                    Bundle bundle11 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    HashMap hashMap = parcel.readHashMap(getClass().getClassLoader());
                    String string41 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zAddAccountExplicitlyWithVisibility = addAccountExplicitlyWithVisibility(account24, string40, bundle11, hashMap, string41);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddAccountExplicitlyWithVisibility);
                    return true;
                case 39:
                    Account account25 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String string42 = parcel.readString();
                    int i16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean accountVisibility = setAccountVisibility(account25, string42, i16);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(accountVisibility);
                    return true;
                case 40:
                    Account account26 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String string43 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int accountVisibility2 = getAccountVisibility(account26, string43);
                    parcel2.writeNoException();
                    parcel2.writeInt(accountVisibility2);
                    return true;
                case 41:
                    String string44 = parcel.readString();
                    String string45 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Map accountsAndVisibilityForPackage = getAccountsAndVisibilityForPackage(string44, string45);
                    parcel2.writeNoException();
                    parcel2.writeMap(accountsAndVisibilityForPackage);
                    return true;
                case 42:
                    String[] strArrCreateStringArray7 = parcel.createStringArray();
                    String string46 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    registerAccountListener(strArrCreateStringArray7, string46);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    String[] strArrCreateStringArray8 = parcel.createStringArray();
                    String string47 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unregisterAccountListener(strArrCreateStringArray8, string47);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    Account account27 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String string48 = parcel.readString();
                    UserHandle userHandle = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zHasAccountAccess = hasAccountAccess(account27, string48, userHandle);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasAccountAccess);
                    return true;
                case 45:
                    Account account28 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String string49 = parcel.readString();
                    UserHandle userHandle2 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    IntentSender intentSenderCreateRequestAccountAccessIntentSenderAsUser = createRequestAccountAccessIntentSenderAsUser(account28, string49, userHandle2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(intentSenderCreateRequestAccountAccessIntentSenderAsUser, 1);
                    return true;
                case 46:
                    String string50 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onAccountAccessed(string50);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(account, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public String getUserData(Account account, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(account, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public AuthenticatorDescription[] getAuthenticatorTypes(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (AuthenticatorDescription[]) parcelObtain2.createTypedArray(AuthenticatorDescription.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public Account[] getAccountsForPackage(String str, int i, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Account[]) parcelObtain2.createTypedArray(Account.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public Account[] getAccountsByTypeForPackage(String str, String str2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Account[]) parcelObtain2.createTypedArray(Account.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public Account[] getAccountsAsUser(String str, int i, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Account[]) parcelObtain2.createTypedArray(Account.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void hasFeatures(IAccountManagerResponse iAccountManagerResponse, Account account, String[] strArr, int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAccountManagerResponse);
                    parcelObtain.writeTypedObject(account, 0);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void getAccountByTypeAndFeatures(IAccountManagerResponse iAccountManagerResponse, String str, String[] strArr, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAccountManagerResponse);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void getAccountsByFeatures(IAccountManagerResponse iAccountManagerResponse, String str, String[] strArr, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAccountManagerResponse);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public boolean addAccountExplicitly(Account account, String str, Bundle bundle, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(account, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void removeAccountAsUser(IAccountManagerResponse iAccountManagerResponse, Account account, boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAccountManagerResponse);
                    parcelObtain.writeTypedObject(account, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public boolean removeAccountExplicitly(Account account) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(account, 0);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void copyAccountToUser(IAccountManagerResponse iAccountManagerResponse, Account account, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAccountManagerResponse);
                    parcelObtain.writeTypedObject(account, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void invalidateAuthToken(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public String peekAuthToken(Account account, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(account, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void setAuthToken(Account account, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(account, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void setPassword(Account account, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(account, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void clearPassword(Account account) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(account, 0);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void setUserData(Account account, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(account, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void updateAppPermission(Account account, String str, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(account, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void getAuthToken(IAccountManagerResponse iAccountManagerResponse, Account account, String str, boolean z, boolean z2, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAccountManagerResponse);
                    parcelObtain.writeTypedObject(account, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void addAccount(IAccountManagerResponse iAccountManagerResponse, String str, String str2, String[] strArr, boolean z, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAccountManagerResponse);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void addAccountAsUser(IAccountManagerResponse iAccountManagerResponse, String str, String str2, String[] strArr, boolean z, Bundle bundle, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAccountManagerResponse);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void updateCredentials(IAccountManagerResponse iAccountManagerResponse, Account account, String str, boolean z, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAccountManagerResponse);
                    parcelObtain.writeTypedObject(account, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void editProperties(IAccountManagerResponse iAccountManagerResponse, String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAccountManagerResponse);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void confirmCredentialsAsUser(IAccountManagerResponse iAccountManagerResponse, Account account, Bundle bundle, boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAccountManagerResponse);
                    parcelObtain.writeTypedObject(account, 0);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public boolean accountAuthenticated(Account account) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(account, 0);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void getAuthTokenLabel(IAccountManagerResponse iAccountManagerResponse, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAccountManagerResponse);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void addSharedAccountsFromParentUser(int i, int i2, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void renameAccount(IAccountManagerResponse iAccountManagerResponse, Account account, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAccountManagerResponse);
                    parcelObtain.writeTypedObject(account, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public String getPreviousName(Account account) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(account, 0);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void startAddAccountSession(IAccountManagerResponse iAccountManagerResponse, String str, String str2, String[] strArr, boolean z, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAccountManagerResponse);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void startUpdateCredentialsSession(IAccountManagerResponse iAccountManagerResponse, Account account, String str, boolean z, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAccountManagerResponse);
                    parcelObtain.writeTypedObject(account, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void finishSessionAsUser(IAccountManagerResponse iAccountManagerResponse, Bundle bundle, boolean z, Bundle bundle2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAccountManagerResponse);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeTypedObject(bundle2, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public boolean someUserHasAccount(Account account) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(account, 0);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void isCredentialsUpdateSuggested(IAccountManagerResponse iAccountManagerResponse, Account account, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAccountManagerResponse);
                    parcelObtain.writeTypedObject(account, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public Map getPackagesAndVisibilityForAccount(Account account) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(account, 0);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readHashMap(getClass().getClassLoader());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public boolean addAccountExplicitlyWithVisibility(Account account, String str, Bundle bundle, Map map, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(account, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeMap(map);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public boolean setAccountVisibility(Account account, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(account, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public int getAccountVisibility(Account account, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(account, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public Map getAccountsAndVisibilityForPackage(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readHashMap(getClass().getClassLoader());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void registerAccountListener(String[] strArr, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void unregisterAccountListener(String[] strArr, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public boolean hasAccountAccess(Account account, String str, UserHandle userHandle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(account, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public IntentSender createRequestAccountAccessIntentSenderAsUser(Account account, String str, UserHandle userHandle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(account, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (IntentSender) parcelObtain2.readTypedObject(IntentSender.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void onAccountAccessed(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
