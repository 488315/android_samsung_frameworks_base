package android.accounts;

import android.accounts.IAccountAuthenticator;
import android.content.Context;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import java.util.Arrays;

/* loaded from: classes.dex */
public abstract class AbstractAccountAuthenticator {
    private static final String KEY_ACCOUNT = "android.accounts.AbstractAccountAuthenticator.KEY_ACCOUNT";
    private static final String KEY_AUTH_TOKEN_TYPE = "android.accounts.AbstractAccountAuthenticato.KEY_AUTH_TOKEN_TYPE";
    public static final String KEY_CUSTOM_TOKEN_EXPIRY = "android.accounts.expiry";
    private static final String KEY_OPTIONS = "android.accounts.AbstractAccountAuthenticator.KEY_OPTIONS";
    private static final String KEY_REQUIRED_FEATURES = "android.accounts.AbstractAccountAuthenticator.KEY_REQUIRED_FEATURES";
    private static final String TAG = "AccountAuthenticator";
    private Transport mTransport = new Transport();

    public abstract Bundle addAccount(AccountAuthenticatorResponse accountAuthenticatorResponse, String str, String str2, String[] strArr, Bundle bundle) throws NetworkErrorException;

    public abstract Bundle confirmCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, Bundle bundle) throws NetworkErrorException;

    public abstract Bundle editProperties(AccountAuthenticatorResponse accountAuthenticatorResponse, String str);

    public abstract Bundle getAuthToken(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String str, Bundle bundle) throws NetworkErrorException;

    public abstract String getAuthTokenLabel(String str);

    public abstract Bundle hasFeatures(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String[] strArr) throws NetworkErrorException;

    public abstract Bundle updateCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String str, Bundle bundle) throws NetworkErrorException;

    public AbstractAccountAuthenticator(Context context) {
    }

    private class Transport extends IAccountAuthenticator.Stub {
        private Transport() {
        }

        @Override // android.accounts.IAccountAuthenticator
        public void addAccount(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, String str, String str2, String[] strArr, Bundle bundle) throws RemoteException {
            String str3;
            super.addAccount_enforcePermission();
            if (Log.isLoggable(AbstractAccountAuthenticator.TAG, 2)) {
                StringBuilder sb = new StringBuilder("addAccount: accountType ");
                sb.append(str);
                sb.append(", authTokenType ");
                sb.append(str2);
                sb.append(", features ");
                sb.append(strArr == null ? "[]" : Arrays.toString(strArr));
                Log.v(AbstractAccountAuthenticator.TAG, sb.toString());
            }
            try {
                str3 = str;
                try {
                    Bundle addAccount = AbstractAccountAuthenticator.this.addAccount(new AccountAuthenticatorResponse(iAccountAuthenticatorResponse), str3, str2, strArr, bundle);
                    if (Log.isLoggable(AbstractAccountAuthenticator.TAG, 2)) {
                        if (addAccount != null) {
                            addAccount.keySet();
                        }
                        Log.v(AbstractAccountAuthenticator.TAG, "addAccount: result " + AccountManager.sanitizeResult(addAccount));
                    }
                    if (addAccount != null) {
                        iAccountAuthenticatorResponse.onResult(addAccount);
                    } else {
                        iAccountAuthenticatorResponse.onError(5, "null bundle returned");
                    }
                } catch (Exception e) {
                    e = e;
                    AbstractAccountAuthenticator.this.handleException(iAccountAuthenticatorResponse, "addAccount", str3, e);
                }
            } catch (Exception e2) {
                e = e2;
                str3 = str;
            }
        }

        @Override // android.accounts.IAccountAuthenticator
        public void confirmCredentials(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, Bundle bundle) throws RemoteException {
            super.confirmCredentials_enforcePermission();
            if (Log.isLoggable(AbstractAccountAuthenticator.TAG, 2)) {
                Log.v(AbstractAccountAuthenticator.TAG, "confirmCredentials: " + account);
            }
            try {
                Bundle confirmCredentials = AbstractAccountAuthenticator.this.confirmCredentials(new AccountAuthenticatorResponse(iAccountAuthenticatorResponse), account, bundle);
                if (Log.isLoggable(AbstractAccountAuthenticator.TAG, 2)) {
                    if (confirmCredentials != null) {
                        confirmCredentials.keySet();
                    }
                    Log.v(AbstractAccountAuthenticator.TAG, "confirmCredentials: result " + AccountManager.sanitizeResult(confirmCredentials));
                }
                if (confirmCredentials != null) {
                    iAccountAuthenticatorResponse.onResult(confirmCredentials);
                }
            } catch (Exception e) {
                AbstractAccountAuthenticator.this.handleException(iAccountAuthenticatorResponse, "confirmCredentials", account.toString(), e);
            }
        }

        @Override // android.accounts.IAccountAuthenticator
        public void getAuthTokenLabel(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, String str) throws RemoteException {
            super.getAuthTokenLabel_enforcePermission();
            if (Log.isLoggable(AbstractAccountAuthenticator.TAG, 2)) {
                Log.v(AbstractAccountAuthenticator.TAG, "getAuthTokenLabel: authTokenType " + str);
            }
            try {
                Bundle bundle = new Bundle();
                bundle.putString(AccountManager.KEY_AUTH_TOKEN_LABEL, AbstractAccountAuthenticator.this.getAuthTokenLabel(str));
                if (Log.isLoggable(AbstractAccountAuthenticator.TAG, 2)) {
                    bundle.keySet();
                    Log.v(AbstractAccountAuthenticator.TAG, "getAuthTokenLabel: result " + AccountManager.sanitizeResult(bundle));
                }
                iAccountAuthenticatorResponse.onResult(bundle);
            } catch (Exception e) {
                AbstractAccountAuthenticator.this.handleException(iAccountAuthenticatorResponse, "getAuthTokenLabel", str, e);
            }
        }

        @Override // android.accounts.IAccountAuthenticator
        public void getAuthToken(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, String str, Bundle bundle) throws RemoteException {
            super.getAuthToken_enforcePermission();
            if (Log.isLoggable(AbstractAccountAuthenticator.TAG, 2)) {
                Log.v(AbstractAccountAuthenticator.TAG, "getAuthToken: " + account + ", authTokenType " + str);
            }
            try {
                Bundle authToken = AbstractAccountAuthenticator.this.getAuthToken(new AccountAuthenticatorResponse(iAccountAuthenticatorResponse), account, str, bundle);
                if (Log.isLoggable(AbstractAccountAuthenticator.TAG, 2)) {
                    if (authToken != null) {
                        authToken.keySet();
                    }
                    Log.v(AbstractAccountAuthenticator.TAG, "getAuthToken: result " + AccountManager.sanitizeResult(authToken));
                }
                if (authToken != null) {
                    iAccountAuthenticatorResponse.onResult(authToken);
                }
            } catch (Exception e) {
                AbstractAccountAuthenticator.this.handleException(iAccountAuthenticatorResponse, "getAuthToken", account.toString() + "," + str, e);
            }
        }

        @Override // android.accounts.IAccountAuthenticator
        public void updateCredentials(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, String str, Bundle bundle) throws RemoteException {
            super.updateCredentials_enforcePermission();
            if (Log.isLoggable(AbstractAccountAuthenticator.TAG, 2)) {
                Log.v(AbstractAccountAuthenticator.TAG, "updateCredentials: " + account + ", authTokenType " + str);
            }
            try {
                Bundle updateCredentials = AbstractAccountAuthenticator.this.updateCredentials(new AccountAuthenticatorResponse(iAccountAuthenticatorResponse), account, str, bundle);
                if (Log.isLoggable(AbstractAccountAuthenticator.TAG, 2)) {
                    if (updateCredentials != null) {
                        updateCredentials.keySet();
                    }
                    Log.v(AbstractAccountAuthenticator.TAG, "updateCredentials: result " + AccountManager.sanitizeResult(updateCredentials));
                }
                if (updateCredentials != null) {
                    iAccountAuthenticatorResponse.onResult(updateCredentials);
                }
            } catch (Exception e) {
                AbstractAccountAuthenticator.this.handleException(iAccountAuthenticatorResponse, "updateCredentials", account.toString() + "," + str, e);
            }
        }

        @Override // android.accounts.IAccountAuthenticator
        public void editProperties(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, String str) throws RemoteException {
            super.editProperties_enforcePermission();
            try {
                Bundle editProperties = AbstractAccountAuthenticator.this.editProperties(new AccountAuthenticatorResponse(iAccountAuthenticatorResponse), str);
                if (editProperties != null) {
                    iAccountAuthenticatorResponse.onResult(editProperties);
                }
            } catch (Exception e) {
                AbstractAccountAuthenticator.this.handleException(iAccountAuthenticatorResponse, "editProperties", str, e);
            }
        }

        @Override // android.accounts.IAccountAuthenticator
        public void hasFeatures(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, String[] strArr) throws RemoteException {
            super.hasFeatures_enforcePermission();
            try {
                Bundle hasFeatures = AbstractAccountAuthenticator.this.hasFeatures(new AccountAuthenticatorResponse(iAccountAuthenticatorResponse), account, strArr);
                if (hasFeatures != null) {
                    iAccountAuthenticatorResponse.onResult(hasFeatures);
                }
            } catch (Exception e) {
                AbstractAccountAuthenticator.this.handleException(iAccountAuthenticatorResponse, "hasFeatures", account.toString(), e);
            }
        }

        @Override // android.accounts.IAccountAuthenticator
        public void getAccountRemovalAllowed(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account) throws RemoteException {
            super.getAccountRemovalAllowed_enforcePermission();
            try {
                Bundle accountRemovalAllowed = AbstractAccountAuthenticator.this.getAccountRemovalAllowed(new AccountAuthenticatorResponse(iAccountAuthenticatorResponse), account);
                if (accountRemovalAllowed != null) {
                    iAccountAuthenticatorResponse.onResult(accountRemovalAllowed);
                }
            } catch (Exception e) {
                AbstractAccountAuthenticator.this.handleException(iAccountAuthenticatorResponse, "getAccountRemovalAllowed", account.toString(), e);
            }
        }

        @Override // android.accounts.IAccountAuthenticator
        public void getAccountCredentialsForCloning(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account) throws RemoteException {
            super.getAccountCredentialsForCloning_enforcePermission();
            try {
                Bundle accountCredentialsForCloning = AbstractAccountAuthenticator.this.getAccountCredentialsForCloning(new AccountAuthenticatorResponse(iAccountAuthenticatorResponse), account);
                if (accountCredentialsForCloning != null) {
                    iAccountAuthenticatorResponse.onResult(accountCredentialsForCloning);
                }
            } catch (Exception e) {
                AbstractAccountAuthenticator.this.handleException(iAccountAuthenticatorResponse, "getAccountCredentialsForCloning", account.toString(), e);
            }
        }

        @Override // android.accounts.IAccountAuthenticator
        public void addAccountFromCredentials(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, Bundle bundle) throws RemoteException {
            super.addAccountFromCredentials_enforcePermission();
            try {
                Bundle addAccountFromCredentials = AbstractAccountAuthenticator.this.addAccountFromCredentials(new AccountAuthenticatorResponse(iAccountAuthenticatorResponse), account, bundle);
                if (addAccountFromCredentials != null) {
                    iAccountAuthenticatorResponse.onResult(addAccountFromCredentials);
                }
            } catch (Exception e) {
                AbstractAccountAuthenticator.this.handleException(iAccountAuthenticatorResponse, "addAccountFromCredentials", account.toString(), e);
            }
        }

        @Override // android.accounts.IAccountAuthenticator
        public void startAddAccountSession(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, String str, String str2, String[] strArr, Bundle bundle) throws RemoteException {
            String str3;
            super.startAddAccountSession_enforcePermission();
            if (Log.isLoggable(AbstractAccountAuthenticator.TAG, 2)) {
                StringBuilder sb = new StringBuilder("startAddAccountSession: accountType ");
                sb.append(str);
                sb.append(", authTokenType ");
                sb.append(str2);
                sb.append(", features ");
                sb.append(strArr == null ? "[]" : Arrays.toString(strArr));
                Log.v(AbstractAccountAuthenticator.TAG, sb.toString());
            }
            try {
                str3 = str;
                try {
                    Bundle startAddAccountSession = AbstractAccountAuthenticator.this.startAddAccountSession(new AccountAuthenticatorResponse(iAccountAuthenticatorResponse), str3, str2, strArr, bundle);
                    if (Log.isLoggable(AbstractAccountAuthenticator.TAG, 2)) {
                        if (startAddAccountSession != null) {
                            startAddAccountSession.keySet();
                        }
                        Log.v(AbstractAccountAuthenticator.TAG, "startAddAccountSession: result " + AccountManager.sanitizeResult(startAddAccountSession));
                    }
                    if (startAddAccountSession != null) {
                        iAccountAuthenticatorResponse.onResult(startAddAccountSession);
                    }
                } catch (Exception e) {
                    e = e;
                    AbstractAccountAuthenticator.this.handleException(iAccountAuthenticatorResponse, "startAddAccountSession", str3, e);
                }
            } catch (Exception e2) {
                e = e2;
                str3 = str;
            }
        }

        @Override // android.accounts.IAccountAuthenticator
        public void startUpdateCredentialsSession(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, String str, Bundle bundle) throws RemoteException {
            super.startUpdateCredentialsSession_enforcePermission();
            if (Log.isLoggable(AbstractAccountAuthenticator.TAG, 2)) {
                Log.v(AbstractAccountAuthenticator.TAG, "startUpdateCredentialsSession: " + account + ", authTokenType " + str);
            }
            try {
                Bundle startUpdateCredentialsSession = AbstractAccountAuthenticator.this.startUpdateCredentialsSession(new AccountAuthenticatorResponse(iAccountAuthenticatorResponse), account, str, bundle);
                if (Log.isLoggable(AbstractAccountAuthenticator.TAG, 2)) {
                    if (startUpdateCredentialsSession != null) {
                        startUpdateCredentialsSession.keySet();
                    }
                    Log.v(AbstractAccountAuthenticator.TAG, "startUpdateCredentialsSession: result " + AccountManager.sanitizeResult(startUpdateCredentialsSession));
                }
                if (startUpdateCredentialsSession != null) {
                    iAccountAuthenticatorResponse.onResult(startUpdateCredentialsSession);
                }
            } catch (Exception e) {
                AbstractAccountAuthenticator.this.handleException(iAccountAuthenticatorResponse, "startUpdateCredentialsSession", account.toString() + "," + str, e);
            }
        }

        @Override // android.accounts.IAccountAuthenticator
        public void finishSession(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, String str, Bundle bundle) throws RemoteException {
            super.finishSession_enforcePermission();
            if (Log.isLoggable(AbstractAccountAuthenticator.TAG, 2)) {
                Log.v(AbstractAccountAuthenticator.TAG, "finishSession: accountType " + str);
            }
            try {
                Bundle finishSession = AbstractAccountAuthenticator.this.finishSession(new AccountAuthenticatorResponse(iAccountAuthenticatorResponse), str, bundle);
                if (finishSession != null) {
                    finishSession.keySet();
                }
                if (Log.isLoggable(AbstractAccountAuthenticator.TAG, 2)) {
                    Log.v(AbstractAccountAuthenticator.TAG, "finishSession: result " + AccountManager.sanitizeResult(finishSession));
                }
                if (finishSession != null) {
                    iAccountAuthenticatorResponse.onResult(finishSession);
                }
            } catch (Exception e) {
                AbstractAccountAuthenticator.this.handleException(iAccountAuthenticatorResponse, "finishSession", str, e);
            }
        }

        @Override // android.accounts.IAccountAuthenticator
        public void isCredentialsUpdateSuggested(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, String str) throws RemoteException {
            super.isCredentialsUpdateSuggested_enforcePermission();
            try {
                Bundle isCredentialsUpdateSuggested = AbstractAccountAuthenticator.this.isCredentialsUpdateSuggested(new AccountAuthenticatorResponse(iAccountAuthenticatorResponse), account, str);
                if (isCredentialsUpdateSuggested != null) {
                    iAccountAuthenticatorResponse.onResult(isCredentialsUpdateSuggested);
                }
            } catch (Exception e) {
                AbstractAccountAuthenticator.this.handleException(iAccountAuthenticatorResponse, "isCredentialsUpdateSuggested", account.toString(), e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleException(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, String str, String str2, Exception exc) throws RemoteException {
        if (exc instanceof NetworkErrorException) {
            if (Log.isLoggable(TAG, 2)) {
                Log.v(TAG, str + NavigationBarInflaterView.KEY_CODE_START + str2 + NavigationBarInflaterView.KEY_CODE_END, exc);
            }
            iAccountAuthenticatorResponse.onError(3, exc.getMessage());
            return;
        }
        if (exc instanceof UnsupportedOperationException) {
            if (Log.isLoggable(TAG, 2)) {
                Log.v(TAG, str + NavigationBarInflaterView.KEY_CODE_START + str2 + NavigationBarInflaterView.KEY_CODE_END, exc);
            }
            iAccountAuthenticatorResponse.onError(6, str + " not supported");
            return;
        }
        if (exc instanceof IllegalArgumentException) {
            if (Log.isLoggable(TAG, 2)) {
                Log.v(TAG, str + NavigationBarInflaterView.KEY_CODE_START + str2 + NavigationBarInflaterView.KEY_CODE_END, exc);
            }
            iAccountAuthenticatorResponse.onError(7, str + " not supported");
            return;
        }
        Log.w(TAG, str + NavigationBarInflaterView.KEY_CODE_START + str2 + NavigationBarInflaterView.KEY_CODE_END, exc);
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(" failed");
        iAccountAuthenticatorResponse.onError(1, sb.toString());
    }

    public final IBinder getIBinder() {
        return this.mTransport.asBinder();
    }

    public Bundle getAccountRemovalAllowed(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account) throws NetworkErrorException {
        Bundle bundle = new Bundle();
        bundle.putBoolean(AccountManager.KEY_BOOLEAN_RESULT, true);
        return bundle;
    }

    public Bundle getAccountCredentialsForCloning(final AccountAuthenticatorResponse accountAuthenticatorResponse, Account account) throws NetworkErrorException {
        new Thread(new Runnable(this) { // from class: android.accounts.AbstractAccountAuthenticator.1
            @Override // java.lang.Runnable
            public void run() {
                Bundle bundle = new Bundle();
                bundle.putBoolean(AccountManager.KEY_BOOLEAN_RESULT, false);
                accountAuthenticatorResponse.onResult(bundle);
            }
        }).start();
        return null;
    }

    public Bundle addAccountFromCredentials(final AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, Bundle bundle) throws NetworkErrorException {
        new Thread(new Runnable(this) { // from class: android.accounts.AbstractAccountAuthenticator.2
            @Override // java.lang.Runnable
            public void run() {
                Bundle bundle2 = new Bundle();
                bundle2.putBoolean(AccountManager.KEY_BOOLEAN_RESULT, false);
                accountAuthenticatorResponse.onResult(bundle2);
            }
        }).start();
        return null;
    }

    public Bundle startAddAccountSession(final AccountAuthenticatorResponse accountAuthenticatorResponse, String str, final String str2, final String[] strArr, final Bundle bundle) throws NetworkErrorException {
        new Thread(new Runnable(this) { // from class: android.accounts.AbstractAccountAuthenticator.3
            @Override // java.lang.Runnable
            public void run() {
                Bundle bundle2 = new Bundle();
                bundle2.putString(AbstractAccountAuthenticator.KEY_AUTH_TOKEN_TYPE, str2);
                bundle2.putStringArray(AbstractAccountAuthenticator.KEY_REQUIRED_FEATURES, strArr);
                bundle2.putBundle(AbstractAccountAuthenticator.KEY_OPTIONS, bundle);
                Bundle bundle3 = new Bundle();
                bundle3.putBundle(AccountManager.KEY_ACCOUNT_SESSION_BUNDLE, bundle2);
                accountAuthenticatorResponse.onResult(bundle3);
            }
        }).start();
        return null;
    }

    public Bundle startUpdateCredentialsSession(final AccountAuthenticatorResponse accountAuthenticatorResponse, final Account account, final String str, final Bundle bundle) throws NetworkErrorException {
        new Thread(new Runnable(this) { // from class: android.accounts.AbstractAccountAuthenticator.4
            @Override // java.lang.Runnable
            public void run() {
                Bundle bundle2 = new Bundle();
                bundle2.putString(AbstractAccountAuthenticator.KEY_AUTH_TOKEN_TYPE, str);
                bundle2.putParcelable(AbstractAccountAuthenticator.KEY_ACCOUNT, account);
                bundle2.putBundle(AbstractAccountAuthenticator.KEY_OPTIONS, bundle);
                Bundle bundle3 = new Bundle();
                bundle3.putBundle(AccountManager.KEY_ACCOUNT_SESSION_BUNDLE, bundle2);
                accountAuthenticatorResponse.onResult(bundle3);
            }
        }).start();
        return null;
    }

    public Bundle finishSession(AccountAuthenticatorResponse accountAuthenticatorResponse, String str, Bundle bundle) throws NetworkErrorException {
        if (TextUtils.isEmpty(str)) {
            Log.e(TAG, "Account type cannot be empty.");
            Bundle bundle2 = new Bundle();
            bundle2.putInt("errorCode", 7);
            bundle2.putString(AccountManager.KEY_ERROR_MESSAGE, "accountType cannot be empty.");
            return bundle2;
        }
        if (bundle == null) {
            Log.e(TAG, "Session bundle cannot be null.");
            Bundle bundle3 = new Bundle();
            bundle3.putInt("errorCode", 7);
            bundle3.putString(AccountManager.KEY_ERROR_MESSAGE, "sessionBundle cannot be null.");
            return bundle3;
        }
        if (!bundle.containsKey(KEY_AUTH_TOKEN_TYPE)) {
            Bundle bundle4 = new Bundle();
            bundle4.putInt("errorCode", 6);
            bundle4.putString(AccountManager.KEY_ERROR_MESSAGE, "Authenticator must override finishSession if startAddAccountSession or startUpdateCredentialsSession is overridden.");
            accountAuthenticatorResponse.onResult(bundle4);
            return bundle4;
        }
        String string = bundle.getString(KEY_AUTH_TOKEN_TYPE);
        Bundle bundle5 = bundle.getBundle(KEY_OPTIONS);
        String[] stringArray = bundle.getStringArray(KEY_REQUIRED_FEATURES);
        Account account = (Account) bundle.getParcelable(KEY_ACCOUNT, Account.class);
        boolean containsKey = bundle.containsKey(KEY_ACCOUNT);
        Bundle bundle6 = new Bundle(bundle);
        bundle6.remove(KEY_AUTH_TOKEN_TYPE);
        bundle6.remove(KEY_REQUIRED_FEATURES);
        bundle6.remove(KEY_OPTIONS);
        bundle6.remove(KEY_ACCOUNT);
        if (bundle5 != null) {
            bundle5.putAll(bundle6);
            bundle6 = bundle5;
        }
        if (containsKey) {
            return updateCredentials(accountAuthenticatorResponse, account, string, bundle5);
        }
        return addAccount(accountAuthenticatorResponse, str, string, stringArray, bundle6);
    }

    public Bundle isCredentialsUpdateSuggested(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String str) throws NetworkErrorException {
        Bundle bundle = new Bundle();
        bundle.putBoolean(AccountManager.KEY_BOOLEAN_RESULT, false);
        return bundle;
    }
}
