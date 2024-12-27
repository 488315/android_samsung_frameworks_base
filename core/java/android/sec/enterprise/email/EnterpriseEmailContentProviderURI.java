package android.sec.enterprise.email;

public class EnterpriseEmailContentProviderURI {
    public static final String ACTION_UNLOCK_CREDENTIAL_INTERNAL = "com.android.credentials.UNLOCK";
    public static final String EMAILACCOUNTPOLICY_GET_ACCOUNT = "getEmailAccount";
    public static final String EMAILACCOUNTPOLICY_GET_EAS_ACCOUNT = "getEASAccount";
    public static final String EMAILPOLICY_ACTION_INTERNAL_MDM_ACCOUNT_DELETE_RESULT =
            "com.samsung.android.knox.intent.action.MDM_ACCOUNT_DELETE_RESULT_INTERNAL";
    public static final String EMAILPOLICY_ACTION_INTERNAL_MDM_ACCOUNT_SETUP_RESULT =
            "com.samsung.android.knox.intent.action.MDM_ACCOUNT_SETUP_RESULT_INTERNAL";
    public static final int EMAILPOLICY_EMAIL_FAIL_BIND_PASSWORD_INTERNAL = 1001;
    public static final String EMAILPOLICY_GET_ACCOUNT_INFO = "getAccountInfo";
    public static final String EMAILPOLICY_UPDATE_ACCOUNT = "updateEmailAccount";
    public static final int KEYSTORE_LOCKED = 2;
    public static final int KEYSTORE_UNINITIALIZED = 3;
    public static final String KEY_GET_ALL_LDAP_ACCOUNT = "email.ldap.all.account";
    public static final String KEY_GET_EAS_ACCOUNT = "eas.account";
    public static final String KEY_GET_EMAIL_ACCOUNT = "email.account";
    public static final String KEY_GET_LDAP_ACCOUNT = "email.ldap.account";
    public static final String KEY_UPDATE_EMAIL_ACCOUNT = "update.account";
    public static final String LDAPACCOUNTPOLICY_DELETE_LDAP_ACCOUNT = "deleteLDAPAccount";
    public static final String LDAPACCOUNTPOLICY_GET_ALL_LDAP_ACCOUNT = "getAllLDAPAccounts";
    public static final String LDAPACCOUNTPOLICY_GET_LDAP_ACCOUNT = "getLDAPAccount";

    private EnterpriseEmailContentProviderURI() {}
}
