package com.samsung.android.knox.keystore;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@Deprecated
/* loaded from: classes3.dex */
public final class CEPConstants {
    public static final String CEP_ACTION_CERT_ENROLL_STATUS = "com.samsung.android.knox.intent.CEP_CERT_ENROLL_STATUS";
    public static final String CEP_ACTION_SERVICE_DISCONNECTED = "com.samsung.android.knox.intent.CEP_SERVICE_DISCONNECTED";
    public static final String CEP_KEYALGO_TYPE_RSA = "RSA";
    public static final String CEP_KEYSTORETYPE_ANDROID = "ANDROID";
    public static final String CEP_KEYSTORETYPE_CCM = "CCM";
    public static final String CEP_KEYSTORETYPE_UCM = "UCM";
    public static final int CEP_TLV_CLEAR_CHALLENGE_PASSWORD = 0;
    public static final int CEP_TLV_ENCRYPTED_CHALLENGE_PASSWORD = 1;
    public static final int CEP_TLV_ENCRYPTED_DATA = 2;
    public static final int CEP_TLV_ENCRYPTED_KEY = 3;
    public static final String CERT_PROFILE_TYPE_CMC = "CMC";
    public static final String CERT_PROFILE_TYPE_CMP = "CMP";
    public static final String CERT_PROFILE_TYPE_SCEP = "SCEP";
    public static final int CERT_SUCCESS = 0;
    public static final int ERR_ALIAS_EXIST = -309;
    public static final int ERR_CCM = -307;
    public static final int ERR_CCM_NOT_SUPPORTED = -308;
    public static final int ERR_CERT_FAILURE = -1;
    public static final int ERR_CERT_NOT_FOUND = -400;
    public static final int ERR_CERT_PENDING = 1;
    public static final int ERR_CERT_VERIFICATION_FAILURE = -401;
    public static final int ERR_CHALLENGE_PASSWORD_EXPIRED = -303;
    public static final int ERR_INVALID_CA_CERT = -304;
    public static final int ERR_KEYLOCK_NOT_SET = -504;
    public static final int ERR_KEYSTORE_EXCEPTION = -501;
    public static final int ERR_MISSING_INPUTFIELDS = -305;
    public static final int ERR_NETWORK_UNAVAILABLE = -2;
    public static final int ERR_OPERATION_NOT_SUPPORTED = -602;
    public static final int ERR_SERVICE_BIND_FAILED = -601;
    public static final int ERR_TLV_DECODE_FAILURE = -301;
    public static final int ERR_TRANSACTIONID_NOTFOUND = -306;
    public static final int ERR_UCM = -311;
    public static final int ERR_UCM_INPUT_INVALID = -310;
    public static final int ERR_UCM_PACKAGE_NOT_WHITELISTED = -312;
    public static final int ERR_UNKNOWN = -3;
    public static final String EXTRA_ENROLL_ALIAS = "com.samsung.extra.knox.certenroll.ALIAS";
    public static final String EXTRA_ENROLL_CERT_HASH = "com.samsung.extra.knox.certenroll.CERT_HASH";
    public static final String EXTRA_ENROLL_KEYSTORE_TYPE = "com.samsung.extra.knox.certenroll.KEYSTORE_TYPE";
    public static final String EXTRA_ENROLL_REFERENCE_NUMBER = "com.samsung.extra.knox.certenroll.REFERENCE_NUMBER";
    public static final String EXTRA_ENROLL_STATUS = "com.samsung.extra.knox.certenroll.STATUS";
    public static final String EXTRA_ENROLL_TRANSACTION_ID = "com.samsung.extra.knox.certenroll.TRANSACTION_ID";
    public static final String EXTRA_SERVICE_PROTOCOL = "com.samsung.extra.knox.certenroll.SERVICE_PROTOCOL";
    public static final String EXTRA_SERVICE_USERID = "com.samsung.extra.knox.certenroll.SERVICE_USERID";
    public static final int SERVICE_BIND_SUCCESS = -600;
}
