package com.samsung.android.knoxguard.service.utils;

import android.net.Uri;
import android.os.SystemProperties;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class Constants {
    public static final int ALARM_ACTION_RETRY_LOCK = 1;
    public static final String ALARM_TYPE = "alarm_type";
    public static final int ALARM_TYPE_UNDEFINED = -1;
    public static final String CSC_FEATURE_SUPPORT_KNOXGUARD = "CscFeature_Knox_SupportKnoxGuard";
    public static final String EMMC_CID = "/sys/block/mmcblk0/device/cid";
    public static final String EMMC_NAME = "/sys/block/mmcblk0/device/name";
    public static final String EMMC_UN = "/sys/block/mmcblk0/device/unique_number";
    public static final String EMMC_UN_R = "/sys/class/sec/mmc/un";
    public static final String ERROR_CLIENT_APP_DATA_CLEARED = "3001";
    public static final String ERROR_CLIENT_INTEGRITY_FOR_CHINA = "3040";
    public static final String ERROR_KGTA_APSERIAL_FAILED = "1002";
    public static final String ERROR_KGTA_HMAC_MISMATCH = "1004";
    public static final String ERROR_KGTA_INIT_FAILED = "1001";
    public static final String ERROR_KGTA_RPMB_UNAVAILABLE = "1003";
    public static final String ERROR_LOCK_FROM_USER_PRESENT = "2003";
    public static final String ERROR_RETRY_LOCK_DEFAULT = "2002";
    public static final String INTENT_KG_PACKAGE_ADDED =
            "com.samsung.kgclient.android.intent.action.KG_PACKAGE_ADDED";
    public static final String INTENT_RETRY_LOCK = "com.samsung.android.knoxguard.RETRY_LOCK";
    public static final String INTENT_SECSETUPWIZARD_COMPLETE =
            "com.sec.android.app.secsetupwizard.SETUPWIZARD_COMPLETE";
    public static final String INTENT_SETUPWIZARD_COMPLETE =
            "com.sec.android.app.setupwizard.SETUPWIZARD_COMPLETE";
    public static final boolean IS_FIRST_API_SUPPORT_SF_POLICY;
    public static final boolean IS_SUPPORT_KGTA;
    public static final String JSON_CLIENT_DATA_COMPANY = "companyName";
    public static final String JSON_CLIENT_DATA_STATUS = "status";
    public static final String JSON_KG_POLICY_COMPANY = "companyName";
    public static final String JSON_KG_POLICY_GENERAL = "generalInfo";
    public static final String KG_APP_TITLE = "Payment Services";
    public static final String KG_OTP_BIT_SYSTEM_PROPERTY = "ro.boot.kg.bit";
    public static final String KG_PACKAGE_NAME = "com.samsung.android.kgclient";
    public static final String KG_PERMISSION = "com.samsung.android.knoxguard.STATUS";
    public static final int KG_SERVICE_VERSION = 170000001;
    public static final int KG_STATE_ACTIVE = 2;
    public static final int KG_STATE_CHECKING = 1;
    public static final int KG_STATE_COMPLETED = 4;
    public static final int KG_STATE_ERROR = 5;
    public static final int KG_STATE_LOCKED = 3;
    public static final int KG_STATE_PRENORMAL = 0;
    public static final String KG_SYSTEM_PROPERTY = "knox.kg.state";
    public static final String MESSAGE_TYPE_COMPLETE = "complete";
    public static final String OTP_BIT_FIRST_BOOT = "00";
    public static final String OTP_BIT_KG_COMPLETED = "11";
    public static final String OTP_BIT_KG_ENABLED = "01";
    public static final String OTP_BIT_KG_UNKNOWN = "FF";
    public static final int POWEROFF_CODE_SYSTEMUI_DISABLED = 1;
    public static final int POWEROFF_CODE_SYSTEMUI_HIDDEN = 2;
    public static final int POWEROFF_CODE_SYSTEMUI_NOTREADY = 4;
    public static final List PROTECTED_APP_OPS_LIST;
    public static final long RETRY_LOCK_ALARM_PERIOD = 300000;
    public static final String RLC_STATE_BLINKED = "Blink";
    public static final String RLC_STATE_NORMAL = "Normal";
    public static final String RLC_STATE_NULL = "";
    public static final String SIM_STATE_CHANGED = "android.intent.action.SIM_STATE_CHANGED";
    public static final String SYSTEMUI_PACKAGE_NAME = "com.android.systemui";
    public static final int TA_INFO_AP_SERIAL = 4;
    public static final int TA_INFO_CERT_PROVISIONED = 2;
    public static final int TA_INFO_HOTP_RETRY_COUNTER = 3;
    public static final int TA_INFO_VERSION = 1;
    public static final int TA_V3 = 3;
    public static final int TA_V4 = 4;
    public static final int TZ_KGTA_INIT_FAILED = -1006;
    public static final int TZ_KGTA_RPMB_HMAC_MISMATCH = 519;
    public static final int TZ_KGTA_RPMB_UNAVAILABLE = 513;
    public static final String UFS_UN = "/sys/class/scsi_host/host0/unique_number";
    public static final String UFS_UN_R = "/sys/class/sec/ufs/un";
    public static final String V3_OTP_BIT_KG_FUSED = "1";
    public static final String V3_OTP_BIT_KG_INITIAL = "0";
    public static final Uri KG_LOG_URI =
            Uri.parse("content://com.samsung.android.kgclient.statusprovider/CONTENT_LOG");
    public static final String RLC_STATE_PRENORMAL = "Prenormal";
    public static final String RLC_STATE_CHECKING = "Checking";
    public static final String RLC_STATE_LOCKED = "Locked";
    public static final String RLC_STATE_COMPLETED = "Completed";
    public static final String[] strState = {
        RLC_STATE_PRENORMAL,
        RLC_STATE_CHECKING,
        "Active",
        RLC_STATE_LOCKED,
        RLC_STATE_COMPLETED,
        "Error"
    };

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LockResult {
        public static final int DEFAULT = 0;
        public static final int FAILURE = 2;
        public static final int SUCCESS = 1;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Rot {
        public static final int ERROR_KG_SERVICE_CHECK_CALLER = 6000;
        public static final int ERROR_KG_SERVICE_CHECK_PACKAGE_STATE = 6001;
        public static final int ERROR_KG_SERVICE_CHECK_SIGNATURE = 6002;
        public static final int ERROR_KG_SERVICE_NONE = 0;
        public static final int ERROR_KG_SERVICE_TA_INTERNAL = 6003;
    }

    static {
        IS_SUPPORT_KGTA = SystemProperties.getInt("ro.product.first_api_level", 0) >= 30;
        IS_FIRST_API_SUPPORT_SF_POLICY =
                SystemProperties.getInt("ro.product.first_api_level", 0) >= 35;
        ArrayList arrayList = new ArrayList();
        PROTECTED_APP_OPS_LIST = arrayList;
        arrayList.add(63);
        arrayList.add(70);
        arrayList.add(40);
        arrayList.add(11);
        arrayList.add(119);
        arrayList.add(107);
        arrayList.add(125);
    }
}
