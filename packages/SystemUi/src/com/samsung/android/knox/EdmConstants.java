package com.samsung.android.knox;

import com.samsung.android.knox.ddar.DualDARClient;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.knox.p045ex.peripheral.PeripheralConstants;
import com.sec.ims.configuration.DATA;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class EdmConstants {
    public static final String ACTION_CALL_STATE_CHANGED = "com.samsung.android.knox.intent.action.CALL_STATE_CHANGED";
    public static final String ACTION_CHECK_REENROLLMENT_INTERNAL = "com.samsung.android.knox.intent.action.CHECK_REENROLLMENT_INTERNAL";
    public static final String ACTION_DO_KEYGUARD_INTERNAL = "com.samsung.android.knox.intent.action.DO_KEYGUARD_INTERNAL";
    public static final String ACTION_EDM_BOOT_COMPLETED_INTERNAL = "com.samsung.android.knox.intent.action.EDM_BOOT_COMPLETED_INTERNAL";
    public static final String ACTION_HARD_KEY_PRESS = "com.samsung.android.knox.intent.action.HARD_KEY_PRESS";
    public static final String ACTION_KEYGUARD_REFRESH_INTERNAL = "com.samsung.android.knox.intent.action.KEYGUARD_REFRESH_INTERNAL";
    public static final String ACTION_MTP_BLOCKED_INTERNAL = "com.samsung.android.knox.intent.action.MTP_BLOCKED_INTERNAL";
    public static final String ACTION_NOTIFY_STORAGE_CARD_INTERNAL = "com.samsung.android.knox.intent.action.NOTIFY_STORAGE_CARD_INTERNAL";
    public static final String ACTION_NO_USER_ACTIVITY = "com.samsung.android.knox.intent.action.NO_USER_ACTIVITY";
    public static final String ACTION_OPERATOR_NAME_INTERNAL = "com.samsung.android.knox.intent.action.OPERATOR_NAME_INTERNAL";
    public static final String ACTION_QUICKSETTING_REFRESH_INTERNAL = "com.samsung.android.knox.intent.action.QUICKSETTING_REFRESH_INTERNAL";
    public static final String ACTION_SEND_DTMF_INTERNAL = "com.samsung.android.knox.intent.action.SEND_DTMF_INTERNAL";
    public static final String ACTION_SET_KEYBOARD_MODE_INTERNAL = "com.samsung.android.knox.intent.action.SET_KEYBOARD_MODE_INTERNAL";
    public static final String ACTION_USER_ACTIVITY = "com.samsung.android.knox.intent.action.USER_ACTIVITY";
    public static final String APN_SETTINGS_POLICY_SERVICE = "apn_settings_policy";
    public static final String APPLICATION_POLICY_SERVICE = "application_policy";
    public static final String AUDIT_LOG = "auditlog";
    public static final String BLUETOOTH_POLICY_SERVICE = "bluetooth_policy";
    public static final String BROWSER_SETTINGS_POLICY_SERVICE = "browser_policy";
    public static final String BT_SECURE_MODE_POLICY_SERVICE = "bluetooth_secure_mode_policy";
    public static final String CERTIFICATE_POLICY_SERVICE = "certificate_policy";
    public static final String DATE_TIME_POLICY_SERVICE = "date_time_policy";
    public static final int DEFAULT_USER_ACTIVITY_TIMEOUT = 0;
    public static final String DEVICE_ACCOUNT_POLICY_SERVICE = "device_account_policy";
    public static final int DEVICE_CONTAINER_ID = 0;
    public static final String DEVICE_INVENTORY_SERVICE = "device_info";
    public static final String DEX_POLICY_SERVICE = "dex_policy";
    public static final String EAS_ACCOUNT_POLICY_SERVICE = "eas_account_policy";
    public static final String EMAIL_ACCOUNT_POLICY_SERVICE = "email_account_policy";
    public static final String EMAIL_POLICY_SERVICE = "email_policy";
    public static final String ENTERPRISE_BILLING_POLICY_SERVICE = "enterprise_billing_policy";
    public static final String ENTERPRISE_LICENSE_POLICY_SERVICE = "enterprise_license_policy";
    public static final String ENTERPRISE_POLICY_SERVICE = "enterprise_policy";
    public static final int ERROR_CRYPTO_CHECK_FAILURE = -5;
    public static final int ERROR_INVALID_FILE = -3;
    public static final int ERROR_NONE = 0;
    public static final int ERROR_NOT_ACTIVE_ADMIN = -2;
    public static final int ERROR_PACKAGE_NAME_MISMATCH = -4;
    public static final int ERROR_UNKNOWN = -1;
    public static final String EXTRA_ADMIN_UID = "com.samsung.android.knox.intent.extra.ADMIN_UID";
    public static final String EXTRA_CALL_STATE = "com.samsung.android.knox.intent.extra.CALL_STATE";
    public static final String EXTRA_CURRENT_VERSION = "com.samsung.android.knox.intent.extra.CURRENT_VERSION";
    public static final String EXTRA_DTMF_DURATION_INTERNAL = "com.samsung.android.knox.intent.extra.DTMF_DURATION_INTERNAL";
    public static final String EXTRA_DTMF_TONE_INTERNAL = "com.samsung.android.knox.intent.extra.DTMF_TONE_INTERNAL";
    public static final String EXTRA_KEYBOARD_MODE_INTERNAL = "com.samsung.android.knox.intent.extra.KEYBOARD_MODE_INTERNAL";
    public static final String EXTRA_KEY_CODE = "com.samsung.android.knox.intent.extra.KEY_CODE";
    public static final String EXTRA_MIGRATION_RESULT = "com.samsung.android.knox.intent.extra.MIGRATION_RESULT";
    public static final String EXTRA_PHONE_STATE = "com.samsung.android.knox.intent.extra.PHONE_STATE";
    public static final String FIREWALL_SERVICE = "firewall";
    public static final String GEOFENCING = "geofencing";
    public static final String HDM_SERVICE = "hdm_service";
    public static final int INVALID_CONTAINER_ID = -1;
    public static final String KIOSKMODE = "kioskmode";
    public static final String KLMS_PKG_NAME = "com.samsung.klmsagent";
    public static final int KNOX_2_7_1 = 21;
    public static final int KNOX_2_8 = 22;
    public static final String KNOX_CCM_POLICY_SERVICE = "knox_ccm_policy";
    public static final String KNOX_CUSTOM_MANAGER_SERVICE = "knoxcustom";
    public static final String KNOX_KPCC_MANAGER_SERVICE = "kpcc";
    public static final String KNOX_NETWORK_ANALYTICS_SERVICE = "knoxnap";
    public static final String KNOX_NETWORK_FILTER_SERVICE = "knox_nwFilterMgr_policy";
    public static final String KNOX_SCEP_POLICY_SERVICE = "knox_scep_policy";
    public static final String KNOX_TIMAKEYSTORE_POLICY_SERVICE = "knox_timakeystore_policy";
    public static final String KNOX_TRUSTED_PINPAD_POLICY_SERVICE = "knox_pinpad_service";
    public static final String KNOX_UCSM_POLICY_SERVICE = "knox_ucsm_policy";
    public static final String KPE_CORE_PACKAGE_NAME = "com.samsung.android.knox.kpecore";
    public static final String KPU_PACKAGE_NAME = "com.samsung.android.knox.kpu";
    public static final String LDAP_ACCOUNT_POLICY_SERVICE = "ldap_account_policy";
    public static final String LICENSE_LOG_SERVICE = "license_log_service";
    public static final String LOCATION_POLICY_SERVICE = "location_policy";
    public static final String LOCKSCREEN_OVERLAY_SERVICE = "lockscreen_overlay";
    public static final String MISC_POLICY_SERVICE = "misc_policy";
    public static final String MULTI_USER_MANAGER_SERVICE = "multi_user_manager_service";
    public static final String MUM_CONTAINER_POLICY_SERVICE = "mum_container_policy";
    public static final String MUM_CONTAINER_RCP_POLICY_SERVICE = "mum_container_rcp_policy";
    public static final String NEW_EMAIL_PKG_NAME = "com.samsung.android.email.provider";
    public static final String PASSWORD_POLICY_SERVICE = "password_policy";
    public static final String PHONE_RESTRICTION_POLICY_SERVICE = "phone_restriction_policy";
    public static final String PROFILE_POLICY_SERVICE = "profilepolicy";
    public static final String REMOTE_INJECTION_SERVICE = "remoteinjection";
    public static final String RESTRICTION_POLICY_SERVICE = "restriction_policy";
    public static final String ROAMING_POLICY_SERVICE = "roaming_policy";
    public static final String SECURITY_POLICY_SERVICE = "security_policy";
    public static final String SMARTCARD_BROWSER_POLICY_SERVICE = "smartcard_browser_policy";
    public static final String SMARTCARD_EMAIL_POLICY_SERVICE = "smartcard_email_policy";
    public static final String THREAT_DEFENSE_SERVICE = "threat_defense_service";
    public static final String UMC_PACKAGENAME = "com.sec.enterprise.knox.cloudmdm.smdms";
    public static final int UNKNOWN_ADMIN_ID = -1;
    public static final String VPN_POLICY_SERVICE = "vpn_policy";
    public static final String WIFI_POLICY_SERVICE = "wifi_policy";
    public static final Map<String, String> APP_RESTRICTIONS_PACKAGES = Collections.unmodifiableMap(new HashMap<String, String>() { // from class: com.samsung.android.knox.EdmConstants.1
        {
            put(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "root");
            put("com.android.settings.intelligence", "basic");
            put("com.samsung.accessibility", "basic");
            put("com.samsung.android.honeyboard", "basic");
            put("com.samsung.android.server.wifi.mobilewips.client", "basic");
            put("com.samsung.android.server.wifi.mobilewips", "basic");
            put("com.sec.android.inputmethod", "basic");
            put("com.samsung.android.app.telephonyui", "basic");
            put("com.samsung.android.app.smartcapture", "basic");
            put("com.android.systemui", "dynamic");
            put("com.samsung.android.SettingsReceiver", "dynamic");
            put("com.sec.android.dexsystemui", "dynamic");
            put("com.sec.android.app.desktoplauncher", "dynamic");
            put("com.sec.android.app.dexonpc", "dynamic");
            put("com.sec.android.app.launcher", "dynamic");
            put("com.sec.android.desktopcommunity", "dynamic");
            put("com.sec.android.desktopmode.uiservice", "dynamic");
            put("com.samsung.android.knox.kam", "dynamic");
            put("com.samsung.android.knox.kam.demo", "dynamic");
            put("com.android.settings.feedback", "feedback");
            put("com.samsung.android.app.telephonyui.feedback", "feedback");
            put("com.samsung.android.SettingsReceiver.feedback", "feedback");
            put("com.sec.android.dexsystemui.feedback", "feedback");
            put("com.sec.android.app.desktoplauncher.feedback", "feedback");
            put("com.sec.android.app.launcher.feedback", "feedback");
            put("com.sec.android.desktopmode.uiservice.feedback", "feedback");
        }
    });
    public static final List<String> APP_MANAGEMENT_SERVICE_PACKAGES = Collections.unmodifiableList(new ArrayList<String>() { // from class: com.samsung.android.knox.EdmConstants.2
        {
            add(EdmConstants.KPU_PACKAGE_NAME);
            add(EdmConstants.UMC_PACKAGENAME);
            add("com.sec.knox.kccagent");
            add("com.samsung.android.knox.kpecore");
            add("com.samsung.android.app.smartscan");
        }
    });

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.samsung.android.knox.EdmConstants$3 */
    public static /* synthetic */ class C45893 {

        /* renamed from: $SwitchMap$com$samsung$android$knox$EdmConstants$EnterpriseKnoxSdkVersion */
        public static final /* synthetic */ int[] f470x3e864b8;

        /* renamed from: $SwitchMap$com$samsung$android$knox$EdmConstants$EnterpriseSdkVersion */
        public static final /* synthetic */ int[] f471xc59b100c;

        static {
            int[] iArr = new int[EnterpriseKnoxSdkVersion.values().length];
            f470x3e864b8 = iArr;
            try {
                iArr[EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_1_0.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f470x3e864b8[EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_1_0_1.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f470x3e864b8[EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_1_0_2.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f470x3e864b8[EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_1_1_0.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f470x3e864b8[EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_1_2_0.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f470x3e864b8[EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_0.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f470x3e864b8[EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_1.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                f470x3e864b8[EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_2.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                f470x3e864b8[EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_3.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                f470x3e864b8[EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_4.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                f470x3e864b8[EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_4_1.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                f470x3e864b8[EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_5.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                f470x3e864b8[EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_5_1.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                f470x3e864b8[EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_6.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                f470x3e864b8[EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_7.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                f470x3e864b8[EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_7_1.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                f470x3e864b8[EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_8.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                f470x3e864b8[EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_9.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                f470x3e864b8[EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_3_0.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                f470x3e864b8[EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_3_1.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                f470x3e864b8[EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_3_2.ordinal()] = 21;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                f470x3e864b8[EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_3_2_1.ordinal()] = 22;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                f470x3e864b8[EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_3_3.ordinal()] = 23;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                f470x3e864b8[EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_3_4.ordinal()] = 24;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                f470x3e864b8[EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_3_4_1.ordinal()] = 25;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                f470x3e864b8[EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_3_5.ordinal()] = 26;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                f470x3e864b8[EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_3_6.ordinal()] = 27;
            } catch (NoSuchFieldError unused27) {
            }
            try {
                f470x3e864b8[EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_3_7.ordinal()] = 28;
            } catch (NoSuchFieldError unused28) {
            }
            try {
                f470x3e864b8[EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_3_7_1.ordinal()] = 29;
            } catch (NoSuchFieldError unused29) {
            }
            try {
                f470x3e864b8[EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_3_8.ordinal()] = 30;
            } catch (NoSuchFieldError unused30) {
            }
            try {
                f470x3e864b8[EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_3_9.ordinal()] = 31;
            } catch (NoSuchFieldError unused31) {
            }
            try {
                f470x3e864b8[EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_3_10.ordinal()] = 32;
            } catch (NoSuchFieldError unused32) {
            }
            try {
                f470x3e864b8[EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_NONE.ordinal()] = 33;
            } catch (NoSuchFieldError unused33) {
            }
            int[] iArr2 = new int[EnterpriseSdkVersion.values().length];
            f471xc59b100c = iArr2;
            try {
                iArr2[EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_2.ordinal()] = 1;
            } catch (NoSuchFieldError unused34) {
            }
            try {
                f471xc59b100c[EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_2_1.ordinal()] = 2;
            } catch (NoSuchFieldError unused35) {
            }
            try {
                f471xc59b100c[EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_2_2.ordinal()] = 3;
            } catch (NoSuchFieldError unused36) {
            }
            try {
                f471xc59b100c[EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_3.ordinal()] = 4;
            } catch (NoSuchFieldError unused37) {
            }
            try {
                f471xc59b100c[EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_4.ordinal()] = 5;
            } catch (NoSuchFieldError unused38) {
            }
            try {
                f471xc59b100c[EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_4_0_1.ordinal()] = 6;
            } catch (NoSuchFieldError unused39) {
            }
            try {
                f471xc59b100c[EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_4_1.ordinal()] = 7;
            } catch (NoSuchFieldError unused40) {
            }
            try {
                f471xc59b100c[EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_5.ordinal()] = 8;
            } catch (NoSuchFieldError unused41) {
            }
            try {
                f471xc59b100c[EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_5_1.ordinal()] = 9;
            } catch (NoSuchFieldError unused42) {
            }
            try {
                f471xc59b100c[EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_5_2.ordinal()] = 10;
            } catch (NoSuchFieldError unused43) {
            }
            try {
                f471xc59b100c[EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_5_3.ordinal()] = 11;
            } catch (NoSuchFieldError unused44) {
            }
            try {
                f471xc59b100c[EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_5_4.ordinal()] = 12;
            } catch (NoSuchFieldError unused45) {
            }
            try {
                f471xc59b100c[EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_5_4_1.ordinal()] = 13;
            } catch (NoSuchFieldError unused46) {
            }
            try {
                f471xc59b100c[EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_5_5.ordinal()] = 14;
            } catch (NoSuchFieldError unused47) {
            }
            try {
                f471xc59b100c[EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_5_5_1.ordinal()] = 15;
            } catch (NoSuchFieldError unused48) {
            }
            try {
                f471xc59b100c[EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_5_6.ordinal()] = 16;
            } catch (NoSuchFieldError unused49) {
            }
            try {
                f471xc59b100c[EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_5_7.ordinal()] = 17;
            } catch (NoSuchFieldError unused50) {
            }
            try {
                f471xc59b100c[EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_5_7_1.ordinal()] = 18;
            } catch (NoSuchFieldError unused51) {
            }
            try {
                f471xc59b100c[EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_5_8.ordinal()] = 19;
            } catch (NoSuchFieldError unused52) {
            }
            try {
                f471xc59b100c[EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_5_9.ordinal()] = 20;
            } catch (NoSuchFieldError unused53) {
            }
            try {
                f471xc59b100c[EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_6_0.ordinal()] = 21;
            } catch (NoSuchFieldError unused54) {
            }
            try {
                f471xc59b100c[EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_6_1.ordinal()] = 22;
            } catch (NoSuchFieldError unused55) {
            }
            try {
                f471xc59b100c[EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_6_2.ordinal()] = 23;
            } catch (NoSuchFieldError unused56) {
            }
            try {
                f471xc59b100c[EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_6_2_1.ordinal()] = 24;
            } catch (NoSuchFieldError unused57) {
            }
            try {
                f471xc59b100c[EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_6_3.ordinal()] = 25;
            } catch (NoSuchFieldError unused58) {
            }
            try {
                f471xc59b100c[EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_6_4.ordinal()] = 26;
            } catch (NoSuchFieldError unused59) {
            }
            try {
                f471xc59b100c[EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_6_4_1.ordinal()] = 27;
            } catch (NoSuchFieldError unused60) {
            }
            try {
                f471xc59b100c[EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_6_5.ordinal()] = 28;
            } catch (NoSuchFieldError unused61) {
            }
            try {
                f471xc59b100c[EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_6_6.ordinal()] = 29;
            } catch (NoSuchFieldError unused62) {
            }
            try {
                f471xc59b100c[EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_6_7.ordinal()] = 30;
            } catch (NoSuchFieldError unused63) {
            }
            try {
                f471xc59b100c[EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_6_7_1.ordinal()] = 31;
            } catch (NoSuchFieldError unused64) {
            }
            try {
                f471xc59b100c[EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_6_8.ordinal()] = 32;
            } catch (NoSuchFieldError unused65) {
            }
            try {
                f471xc59b100c[EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_6_9.ordinal()] = 33;
            } catch (NoSuchFieldError unused66) {
            }
            try {
                f471xc59b100c[EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_6_10.ordinal()] = 34;
            } catch (NoSuchFieldError unused67) {
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum EnterpriseKnoxSdkVersion {
        KNOX_ENTERPRISE_SDK_VERSION_NONE,
        KNOX_ENTERPRISE_SDK_VERSION_1_0,
        KNOX_ENTERPRISE_SDK_VERSION_1_0_1,
        KNOX_ENTERPRISE_SDK_VERSION_1_0_2,
        KNOX_ENTERPRISE_SDK_VERSION_1_1_0,
        KNOX_ENTERPRISE_SDK_VERSION_1_2_0,
        KNOX_ENTERPRISE_SDK_VERSION_2_0,
        KNOX_ENTERPRISE_SDK_VERSION_2_1,
        KNOX_ENTERPRISE_SDK_VERSION_2_2,
        KNOX_ENTERPRISE_SDK_VERSION_2_3,
        KNOX_ENTERPRISE_SDK_VERSION_2_4,
        KNOX_ENTERPRISE_SDK_VERSION_2_4_1,
        KNOX_ENTERPRISE_SDK_VERSION_2_5,
        KNOX_ENTERPRISE_SDK_VERSION_2_5_1,
        KNOX_ENTERPRISE_SDK_VERSION_2_6,
        KNOX_ENTERPRISE_SDK_VERSION_2_7,
        KNOX_ENTERPRISE_SDK_VERSION_2_7_1,
        KNOX_ENTERPRISE_SDK_VERSION_2_8,
        KNOX_ENTERPRISE_SDK_VERSION_2_9,
        KNOX_ENTERPRISE_SDK_VERSION_3_0,
        KNOX_ENTERPRISE_SDK_VERSION_3_1,
        KNOX_ENTERPRISE_SDK_VERSION_3_2,
        KNOX_ENTERPRISE_SDK_VERSION_3_2_1,
        KNOX_ENTERPRISE_SDK_VERSION_3_3,
        KNOX_ENTERPRISE_SDK_VERSION_3_4,
        KNOX_ENTERPRISE_SDK_VERSION_3_4_1,
        KNOX_ENTERPRISE_SDK_VERSION_3_5,
        KNOX_ENTERPRISE_SDK_VERSION_3_6,
        KNOX_ENTERPRISE_SDK_VERSION_3_7,
        KNOX_ENTERPRISE_SDK_VERSION_3_7_1,
        KNOX_ENTERPRISE_SDK_VERSION_3_8,
        KNOX_ENTERPRISE_SDK_VERSION_3_9,
        KNOX_ENTERPRISE_SDK_VERSION_3_10;

        public final String getInternalVersion() {
            switch (C45893.f470x3e864b8[ordinal()]) {
                case 1:
                    return DualDARClient.DUAL_DAR_SDK_VERSION_1_0_0;
                case 2:
                    return "1.0.1";
                case 3:
                    return "1.0.2";
                case 4:
                    return "1.1.0";
                case 5:
                    return "1.2.0";
                case 6:
                    return "2.0.0";
                case 7:
                    return "2.1.0";
                case 8:
                    return "2.2.0";
                case 9:
                    return "2.3.0";
                case 10:
                    return "2.4.0";
                case 11:
                    return "2.4.1";
                case 12:
                    return "2.5.0";
                case 13:
                    return "2.5.1";
                case 14:
                    return "2.6.0";
                case 15:
                    return "2.7.0";
                case 16:
                    return "2.7.1";
                case 17:
                    return "2.8.0";
                case 18:
                    return "2.9.0";
                case 19:
                    return "3.0.0";
                case 20:
                    return "3.1.0";
                case 21:
                    return "3.2.0";
                case 22:
                    return "3.2.1";
                case 23:
                    return "3.3.0";
                case 24:
                    return "3.4.0";
                case 25:
                    return "3.4.1";
                case 26:
                    return "3.5";
                case 27:
                    return "3.6";
                case 28:
                    return "3.7";
                case 29:
                    return "3.7.1";
                case 30:
                    return "3.8";
                case 31:
                    return "3.9";
                case 32:
                    return "3.10";
                default:
                    return PeripheralConstants.Result.NOT_AVAILABLE;
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum EnterpriseSdkVersion {
        ENTERPRISE_SDK_VERSION_2,
        ENTERPRISE_SDK_VERSION_2_1,
        ENTERPRISE_SDK_VERSION_2_2,
        ENTERPRISE_SDK_VERSION_3,
        ENTERPRISE_SDK_VERSION_4,
        ENTERPRISE_SDK_VERSION_4_0_1,
        ENTERPRISE_SDK_VERSION_4_1,
        ENTERPRISE_SDK_VERSION_5,
        ENTERPRISE_SDK_VERSION_5_1,
        ENTERPRISE_SDK_VERSION_5_2,
        ENTERPRISE_SDK_VERSION_5_3,
        ENTERPRISE_SDK_VERSION_NONE,
        ENTERPRISE_SDK_VERSION_5_4,
        ENTERPRISE_SDK_VERSION_5_4_1,
        ENTERPRISE_SDK_VERSION_5_5,
        ENTERPRISE_SDK_VERSION_5_5_1,
        ENTERPRISE_SDK_VERSION_5_6,
        ENTERPRISE_SDK_VERSION_5_7,
        ENTERPRISE_SDK_VERSION_5_7_1,
        ENTERPRISE_SDK_VERSION_5_8,
        ENTERPRISE_SDK_VERSION_5_9,
        ENTERPRISE_SDK_VERSION_6_0,
        ENTERPRISE_SDK_VERSION_6_1,
        ENTERPRISE_SDK_VERSION_6_2,
        ENTERPRISE_SDK_VERSION_6_2_1,
        ENTERPRISE_SDK_VERSION_6_3,
        ENTERPRISE_SDK_VERSION_6_4,
        ENTERPRISE_SDK_VERSION_6_4_1,
        ENTERPRISE_SDK_VERSION_6_5,
        ENTERPRISE_SDK_VERSION_6_6,
        ENTERPRISE_SDK_VERSION_6_7,
        ENTERPRISE_SDK_VERSION_6_7_1,
        ENTERPRISE_SDK_VERSION_6_8,
        ENTERPRISE_SDK_VERSION_6_9,
        ENTERPRISE_SDK_VERSION_6_10;

        public final String getInternalVersion() {
            switch (C45893.f471xc59b100c[ordinal()]) {
                case 1:
                    return "2.0.0";
                case 2:
                    return "2.1.0";
                case 3:
                    return "2.2.0";
                case 4:
                    return "3.0.0";
                case 5:
                    return "4.0.0";
                case 6:
                    return "4.0.1";
                case 7:
                    return "4.1.0";
                case 8:
                    return "5.0.0";
                case 9:
                    return "5.1.0";
                case 10:
                    return "5.2.0";
                case 11:
                    return "5.3.0";
                case 12:
                    return "5.4.0";
                case 13:
                    return "5.4.1";
                case 14:
                    return "5.5.0";
                case 15:
                    return "5.5.1";
                case 16:
                    return "5.6.0";
                case 17:
                    return "5.7.0";
                case 18:
                    return "5.7.1";
                case 19:
                    return "5.8.0";
                case 20:
                    return "5.9.0";
                case 21:
                    return "6.0.0";
                case 22:
                    return "6.1.0";
                case 23:
                    return "6.2.0";
                case 24:
                    return "6.2.1";
                case 25:
                    return "6.3.0";
                case 26:
                    return "6.4.0";
                case 27:
                    return "6.4.1";
                case 28:
                    return "6.5";
                case 29:
                    return "6.6";
                case 30:
                    return "6.7";
                case 31:
                    return "6.7.1";
                case 32:
                    return "6.8";
                case 33:
                    return "6.9";
                case 34:
                    return "6.10";
                default:
                    return PeripheralConstants.Result.NOT_AVAILABLE;
            }
        }
    }

    public static EnterpriseKnoxSdkVersion getEnterpriseKnoxSdkVersion() {
        switch (Integer.parseInt(DATA.DM_FIELD_INDEX.PUBLISH_TIMER_EXTEND)) {
            case 13:
                return EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_2;
            case 14:
                return EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_3;
            case 15:
                return EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_4;
            case 16:
                return EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_4_1;
            case 17:
                return EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_5;
            case 18:
                return EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_5_1;
            case 19:
                return EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_6;
            case 20:
                return EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_7;
            case 21:
                return EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_7_1;
            case 22:
                return EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_8;
            case 23:
                return EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_9;
            case 24:
                return EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_3_0;
            case 25:
                return EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_3_1;
            case 26:
                return EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_3_2;
            case 27:
                return EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_3_2_1;
            case 28:
                return EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_3_3;
            case 29:
                return EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_3_4;
            case 30:
                return EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_3_4_1;
            case 31:
                return EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_3_5;
            case 32:
                return EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_3_6;
            case 33:
                return EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_3_7;
            case 34:
                return EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_3_7_1;
            case 35:
                return EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_3_8;
            case 36:
                return EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_3_9;
            case 37:
                return EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_3_10;
            default:
                return EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_1;
        }
    }

    public static EnterpriseSdkVersion getEnterpriseSdkVerInternal() {
        int i = KnoxInternalFeature.KNOX_CONFIG_MDM_VERSION;
        return i == 34 ? EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_6_10 : i == 33 ? EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_6_9 : i == 32 ? EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_6_8 : i == 31 ? EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_6_7_1 : i == 30 ? EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_6_7 : i == 29 ? EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_6_6 : i == 28 ? EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_6_5 : i == 27 ? EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_6_4_1 : i == 26 ? EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_6_4 : i == 25 ? EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_6_3 : i == 24 ? EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_6_2_1 : i == 23 ? EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_6_2 : i == 22 ? EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_6_1 : i == 21 ? EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_6_0 : i == 20 ? EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_5_9 : i == 19 ? EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_5_8 : i == 18 ? EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_5_7_1 : i == 17 ? EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_5_7 : i == 16 ? EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_5_6 : i == 15 ? EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_5_5_1 : i == 14 ? EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_5_5 : i == 13 ? EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_5_4_1 : i == 12 ? EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_5_4 : i == 11 ? EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_5_3 : i == 10 ? EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_5_2 : EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_5_1;
    }
}
