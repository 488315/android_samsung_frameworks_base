package com.samsung.android.knox;

import android.R;
import android.app.admin.DeviceAdminInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemProperties;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Printer;
import android.util.SparseArray;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import com.android.internal.util.XmlUtils;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.samsung.android.knox.license.EnterpriseLicenseManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes4.dex */
public final class EnterpriseDeviceAdminInfo implements Parcelable {
    public static final Parcelable.Creator<EnterpriseDeviceAdminInfo> CREATOR;
    public static final String TAG = "EnterpriseDeviceAdminInfo";
    public static final int USES_POLICY_KNOX_ACCESS_GRANT = 129;
    public static final String USES_POLICY_KNOX_ACCESS_GRANT_TAG = "com.samsung.android.knox.permission.KNOX_API_ACCESS_GRANT";
    public static final int USES_POLICY_KNOX_ADVANCED_APP_MGMT = 80;
    public static final String USES_POLICY_KNOX_ADVANCED_APP_MGMT_TAG = "com.samsung.android.knox.permission.KNOX_ADVANCED_APP_MGMT";
    public static final int USES_POLICY_KNOX_ADVANCED_SECURITY = 81;
    public static final String USES_POLICY_KNOX_ADVANCED_SECURITY_TAG = "com.samsung.android.knox.permission.KNOX_ADVANCED_SECURITY";
    public static final int USES_POLICY_KNOX_ANALYTICS_DEVELOPER = 131;
    public static final String USES_POLICY_KNOX_ANALYTICS_DEVELOPER_TAG = "com.samsung.android.knox.permission.KNOX_ANALYTICS_DEVELOPER";
    public static final int USES_POLICY_KNOX_APP_SEPARATION = 112;
    public static final String USES_POLICY_KNOX_APP_SEPARATION_TAG = "com.samsung.android.knox.permission.KNOX_APP_SEPARATION";
    public static final int USES_POLICY_KNOX_AUTHENTICATION_MANAGER = 123;
    public static final String USES_POLICY_KNOX_AUTHENTICATION_MANAGER_TAG = "com.samsung.android.knox.permission.KNOX_AUTH_MGMT";
    public static final int USES_POLICY_KNOX_AUTHORIZATION = 128;
    public static final String USES_POLICY_KNOX_AUTHORIZATION_TAG = "com.samsung.android.knox.permission.KNOX_AUTHORIZATION";
    public static final int USES_POLICY_KNOX_CAPTURE = 113;
    public static final int USES_POLICY_KNOX_CAPTURE_ADVANCED = 120;
    public static final String USES_POLICY_KNOX_CAPTURE_ADVANCED_TAG = "com.samsung.android.knox.permission.SMART_SCAN_ADVANCED";
    public static final int USES_POLICY_KNOX_CAPTURE_BASIC = 119;
    public static final String USES_POLICY_KNOX_CAPTURE_BASIC_TAG = "com.samsung.android.knox.permission.SMART_SCAN_BASIC";
    public static final String USES_POLICY_KNOX_CAPTURE_TAG = "com.samsung.android.knox.permission.SMART_SCAN";
    public static final int USES_POLICY_KNOX_CCM = 61;
    public static final String USES_POLICY_KNOX_CCM_TAG = "com.sec.enterprise.knox.permission.KNOX_CCM,com.samsung.android.knox.permission.KNOX_CCM_KEYSTORE";
    public static final int USES_POLICY_KNOX_CERTENROL = 66;
    public static final String USES_POLICY_KNOX_CERTENROL_TAG = "com.sec.enterprise.knox.permission.KNOX_CERTENROLL,com.samsung.android.knox.permission.KNOX_CERTIFICATE_ENROLLMENT";
    public static final int USES_POLICY_KNOX_CERT_PROVISIONING = 78;
    public static final String USES_POLICY_KNOX_CERT_PROVISIONING_TAG = "com.samsung.android.knox.permission.KNOX_CERT_PROVISIONING";
    public static final int USES_POLICY_KNOX_CLIPBOARD = 79;
    public static final String USES_POLICY_KNOX_CLIPBOARD_TAG = "com.samsung.android.knox.permission.KNOX_CLIPBOARD";
    public static final int USES_POLICY_KNOX_CONTAINER_VPN = 55;
    public static final String USES_POLICY_KNOX_CONTAINER_VPN_TAG = "com.samsung.android.knox.permission.KNOX_VPN_CONTAINER";
    public static final int USES_POLICY_KNOX_CRITICAL_COMMUNICATIONS = 108;
    public static final String USES_POLICY_KNOX_CRITICAL_COMMUNICATIONS_TAG = "com.samsung.android.knox.permission.KNOX_CRITICAL_COMMUNICATIONS";
    public static final int USES_POLICY_KNOX_CUSTOM_DEX = 85;
    public static final String USES_POLICY_KNOX_CUSTOM_DEX_TAG = "com.samsung.android.knox.permission.KNOX_CUSTOM_DEX";
    public static final int USES_POLICY_KNOX_CUSTOM_PROKIOSK = 70;
    public static final String USES_POLICY_KNOX_CUSTOM_PROKIOSK_TAG = "com.samsung.android.knox.permission.KNOX_CUSTOM_PROKIOSK";
    public static final int USES_POLICY_KNOX_CUSTOM_SEALEDMODE = 65;
    public static final String USES_POLICY_KNOX_CUSTOM_SEALEDMODE_TAG = "com.sec.enterprise.knox.permission.CUSTOM_SEALEDMODE,com.samsung.android.knox.permission.KNOX_CUSTOM_SEALEDMODE";
    public static final int USES_POLICY_KNOX_CUSTOM_SETTING = 63;
    public static final String USES_POLICY_KNOX_CUSTOM_SETTING_TAG = "com.sec.enterprise.knox.permission.CUSTOM_SETTING,com.samsung.android.knox.permission.KNOX_CUSTOM_SETTING";
    public static final int USES_POLICY_KNOX_CUSTOM_SYSTEM = 64;
    public static final String USES_POLICY_KNOX_CUSTOM_SYSTEM_TAG = "com.sec.enterprise.knox.permission.CUSTOM_SYSTEM,com.samsung.android.knox.permission.KNOX_CUSTOM_SYSTEM";
    public static final int USES_POLICY_KNOX_DEACTIVATE_LICENSE = 111;
    public static final String USES_POLICY_KNOX_DEACTIVATE_LICENSE_TAG = "com.sec.enterprise.knox.permission.KNOX_DEACTIVATE_LICENSE";
    public static final int USES_POLICY_KNOX_DEVICE_CONFIGURATION = 109;
    public static final String USES_POLICY_KNOX_DEVICE_CONFIGURATION_TAG = "com.samsung.android.knox.permission.KNOX_DEVICE_CONFIGURATION";
    public static final int USES_POLICY_KNOX_DEX = 84;
    public static final String USES_POLICY_KNOX_DEX_TAG = "com.samsung.android.knox.permission.KNOX_DEX";
    public static final int USES_POLICY_KNOX_DUAL_DAR = 87;
    public static final String USES_POLICY_KNOX_DUAL_DAR_TAG = "com.samsung.android.knox.permission.KNOX_DUAL_DAR";
    public static final int USES_POLICY_KNOX_EBILLING_NOMDM = 83;
    public static final String USES_POLICY_KNOX_EBILLING_NOMDM_TAG = "com.samsung.android.knox.permission.KNOX_EBILLING_NOMDM";
    public static final int USES_POLICY_KNOX_ENHANCED_ATTESTATION = 107;
    public static final String USES_POLICY_KNOX_ENHANCED_ATTESTATION_TAG = "com.samsung.android.knox.permission.KNOX_ENHANCED_ATTESTATION";
    public static final int USES_POLICY_KNOX_ENTERPRISE_BILLING = 68;
    public static final String USES_POLICY_KNOX_ENTERPRISE_BILLING_TAG = "com.samsung.android.knox.permission.KNOX_EBILLING";
    public static final int USES_POLICY_KNOX_FORESIGHT = 122;
    public static final String USES_POLICY_KNOX_FORESIGHT_TAG = "com.samsung.android.knox.permission.KNOX_FORESIGHT";
    public static final int USES_POLICY_KNOX_GENERIC_VPN = 54;
    public static final String USES_POLICY_KNOX_GENERIC_VPN_TAG = "com.samsung.android.knox.permission.KNOX_VPN_GENERIC";
    public static final int USES_POLICY_KNOX_HDM = 110;
    public static final String USES_POLICY_KNOX_HDM_TAG = "com.samsung.android.knox.permission.KNOX_HDM";
    public static final int USES_POLICY_KNOX_KEYSTORE = 62;
    public static final int USES_POLICY_KNOX_KEYSTORE_PER_APP = 75;
    public static final String USES_POLICY_KNOX_KEYSTORE_PER_APP_TAG = "com.samsung.android.knox.permission.KNOX_TIMA_KEYSTORE_PER_APP";
    public static final String USES_POLICY_KNOX_KEYSTORE_TAG = "com.sec.enterprise.knox.permission.KNOX_KEYSTORE,com.samsung.android.knox.permission.KNOX_TIMA_KEYSTORE";
    public static final int USES_POLICY_KNOX_MPOS = 121;
    public static final String USES_POLICY_KNOX_MPOS_TAG = "com.samsung.android.knox.permission.KNOX_MPOS";
    public static final int USES_POLICY_KNOX_NDA_AI = 118;
    public static final String USES_POLICY_KNOX_NDA_AI_TAG = "com.samsung.android.knox.permission.KNOX_NDA_AI";
    public static final int USES_POLICY_KNOX_NDA_DATA_ANALYTICS = 117;
    public static final String USES_POLICY_KNOX_NDA_DATA_ANALYTICS_TAG = "com.samsung.android.knox.permission.KNOX_NDA_DATA_ANALYTICS";
    public static final int USES_POLICY_KNOX_NDA_DEVICE_SETTINGS = 116;
    public static final String USES_POLICY_KNOX_NDA_DEVICE_SETTINGS_TAG = "com.samsung.android.knox.permission.KNOX_NDA_DEVICE_SETTINGS";
    public static final int USES_POLICY_KNOX_NDA_PERIPHERAL = 115;
    public static final String USES_POLICY_KNOX_NDA_PERIPHERAL_TAG = "com.samsung.android.knox.permission.KNOX_NDA_PERIPHERAL";
    public static final int USES_POLICY_KNOX_NETWORK_FILTER_MGMT = 124;
    public static final String USES_POLICY_KNOX_NETWORK_FILTER_MGMT_TAG = "com.samsung.android.knox.permission.KNOX_NETWORK_FILTER_MGMT";
    public static final int USES_POLICY_KNOX_NETWORK_FILTER_SP = 125;
    public static final String USES_POLICY_KNOX_NETWORK_FILTER_SP_TAG = "com.samsung.android.knox.permission.KNOX_NETWORK_FILTER_SERVICE_PROVIDER";
    public static final int USES_POLICY_KNOX_NPA = 82;
    public static final String USES_POLICY_KNOX_NPA_TAG = "com.samsung.android.knox.permission.KNOX_NPA";
    public static final int USES_POLICY_KNOX_RC_ACCESS_GRANT = 130;
    public static final String USES_POLICY_KNOX_RC_ACCESS_GRANT_TAG = "com.samsung.android.knox.permission.KNOX_RC_API_ACCESS_GRANT";
    public static final int USES_POLICY_KNOX_RESTRICTION_PERM = 60;
    public static final String USES_POLICY_KNOX_RESTRICTION_PERM_TAG = "com.samsung.android.knox.permission.KNOX_ADVANCED_RESTRICTION";
    public static final int USES_POLICY_KNOX_SDP = 71;
    public static final String USES_POLICY_KNOX_SDP_TAG = "com.samsung.android.knox.permission.KNOX_SENSITIVE_DATA_PROTECTION";
    public static final int USES_POLICY_KNOX_SEAMS_PERM = 58;
    public static final String USES_POLICY_KNOX_SEAMS_PERM_TAG = "com.sec.enterprise.knox.permission.KNOX_SEAMS,com.samsung.android.knox.permission.KNOX_SEAMS_MGMT";
    public static final int USES_POLICY_KNOX_SEAMS_SEPOLICY = 114;
    public static final int USES_POLICY_KNOX_SEAMS_SEPOLICY_PERM = 59;
    public static final String USES_POLICY_KNOX_SEAMS_SEPOLICY_PERM_TAG = "com.samsung.android.knox.permission.KNOX_SEAMS_SEPOLICY_INTERNAL";
    public static final String USES_POLICY_KNOX_SEAMS_SEPOLICY_TAG = "com.sec.enterprise.knox.permission.KNOX_SEAMS_SEPOLICY";
    public static final int USES_POLICY_KNOX_SECURE_TIMER = 88;
    public static final String USES_POLICY_KNOX_SECURE_TIMER_TAG = "com.samsung.android.knox.permission.KNOX_SECURE_TIMER";
    public static final int USES_POLICY_KNOX_SIM_RESTRICTION = 89;
    public static final String USES_POLICY_KNOX_SIM_RESTRICTION_TAG = "com.samsung.android.knox.permission.KNOX_SIM_RESTRICTION";
    public static final int USES_POLICY_KNOX_UCM_MGMT = 86;
    public static final String USES_POLICY_KNOX_UCM_MGMT_TAG = "com.samsung.android.knox.permission.KNOX_UCM_MGMT";
    public static final int USES_POLICY_KNOX_UCM_PRIVILEGED = 76;
    public static final String USES_POLICY_KNOX_UCM_PRIVILEGED_TAG = "com.samsung.android.knox.permission.KNOX_UCM_PRIVILEGED_MGMT";
    public static final int USES_POLICY_KNOX_UCSM_ESE = 72;
    public static final String USES_POLICY_KNOX_UCSM_ESE_TAG = "com.samsung.android.knox.permission.KNOX_UCM_ESE_MGMT";
    public static final int USES_POLICY_KNOX_UCSM_OTHER = 73;
    public static final String USES_POLICY_KNOX_UCSM_OTHER_TAG = "com.samsung.android.knox.permission.KNOX_UCM_OTHER_MGMT";
    public static final int USES_POLICY_KNOX_UCS_PLUGIN = 74;
    public static final String USES_POLICY_KNOX_UCS_PLUGIN_TAG = "com.samsung.android.knox.permission.KNOX_UCM_PLUGIN_SERVICE";
    public static final int USES_POLICY_MDM_APN_SETTINGS = 34;
    public static final String USES_POLICY_MDM_APN_SETTINGS_TAG = "com.samsung.android.knox.permission.KNOX_APN";
    public static final int USES_POLICY_MDM_APPLICATION = 22;
    public static final int USES_POLICY_MDM_APPLICATION_PERMISSION = 90;
    public static final String USES_POLICY_MDM_APPLICATION_PERMISSION_TAG = "com.samsung.android.knox.permission.KNOX_APP_PERMISSION_MGMT";
    public static final String USES_POLICY_MDM_APPLICATION_TAG = "com.samsung.android.knox.permission.KNOX_APP_MGMT";
    public static final int USES_POLICY_MDM_AUDIT_LOG_PERMISSION = 43;
    public static final String USES_POLICY_MDM_AUDIT_LOG_PERMISSION_TAG = "com.samsung.android.knox.permission.KNOX_AUDIT_LOG";
    public static final int USES_POLICY_MDM_BLUETOOTH = 23;
    public static final int USES_POLICY_MDM_BLUETOOTH_SECURE_MODE = 51;
    public static final String USES_POLICY_MDM_BLUETOOTH_SECURE_MODE_TAG = "com.samsung.android.knox.permission.KNOX_BLUETOOTH_SECUREMODE";
    public static final String USES_POLICY_MDM_BLUETOOTH_TAG = "com.samsung.android.knox.permission.KNOX_BLUETOOTH";
    public static final int USES_POLICY_MDM_BROWSER_PROXY = 53;
    public static final String USES_POLICY_MDM_BROWSER_PROXY_TAG = "com.samsung.android.knox.permission.KNOX_BROWSER_PROXY";
    public static final int USES_POLICY_MDM_BROWSER_SETTINGS = 36;
    public static final String USES_POLICY_MDM_BROWSER_SETTINGS_TAG = "com.samsung.android.knox.permission.KNOX_BROWSER_SETTINGS";
    public static final int USES_POLICY_MDM_CERTIFICATE_PERMISSION = 42;
    public static final String USES_POLICY_MDM_CERTIFICATE_PERMISSION_TAG = "com.samsung.android.knox.permission.KNOX_CERTIFICATE";
    public static final int USES_POLICY_MDM_DATE_TIME = 37;
    public static final String USES_POLICY_MDM_DATE_TIME_TAG = "com.samsung.android.knox.permission.KNOX_DATE_TIME";
    public static final int USES_POLICY_MDM_DEVICE_INVENTORY = 24;
    public static final String USES_POLICY_MDM_DEVICE_INVENTORY_TAG = "com.samsung.android.knox.permission.KNOX_INVENTORY";
    public static final int USES_POLICY_MDM_DUAL_SIM = 47;
    public static final String USES_POLICY_MDM_DUAL_SIM_TAG = "com.samsung.android.knox.permission.KNOX_DUAL_SIM";
    public static final int USES_POLICY_MDM_EMAIL_ACCOUNT = 32;
    public static final String USES_POLICY_MDM_EMAIL_ACCOUNT_TAG = "com.samsung.android.knox.permission.KNOX_EMAIL";
    public static final int USES_POLICY_MDM_ENTERPRISE_CONTAINER = 48;
    public static final String USES_POLICY_MDM_ENTERPRISE_CONTAINER_TAG = "com.samsung.android.knox.permission.KNOX_CONTAINER";
    public static final int USES_POLICY_MDM_ENTERPRISE_DEVICE_ADMIN = 39;
    public static final String USES_POLICY_MDM_ENTERPRISE_DEVICE_ADMIN_TAG = "com.samsung.android.knox.permission.KNOX_ENTERPRISE_DEVICE_ADMIN";
    public static final int USES_POLICY_MDM_EXCHANGE_ACCOUNT = 25;
    public static final String USES_POLICY_MDM_EXCHANGE_ACCOUNT_TAG = "com.samsung.android.knox.permission.KNOX_EXCHANGE";
    public static final int USES_POLICY_MDM_FIREWALL = 38;
    public static final String USES_POLICY_MDM_FIREWALL_TAG = "com.samsung.android.knox.permission.KNOX_FIREWALL";
    public static final int USES_POLICY_MDM_GEOFENCING = 45;
    public static final String USES_POLICY_MDM_GEOFENCING_TAG = "com.samsung.android.knox.permission.KNOX_GEOFENCING";
    public static final int USES_POLICY_MDM_GLOBALPROXY = 77;
    public static final String USES_POLICY_MDM_GLOBALPROXY_TAG = "com.samsung.android.knox.permission.KNOX_GLOBALPROXY";
    public static final int USES_POLICY_MDM_HARDWARE_CONTROL = 29;
    public static final String USES_POLICY_MDM_HARDWARE_CONTROL_TAG = "com.samsung.android.knox.permission.KNOX_HW_CONTROL";
    public static final int USES_POLICY_MDM_KIOSK_MODE = 41;
    public static final String USES_POLICY_MDM_KIOSK_MODE_TAG = "com.samsung.android.knox.permission.KNOX_KIOSK_MODE";
    public static final int USES_POLICY_MDM_KNOX_ACTIVATE_DEVICE_PERMISSIONS = 56;
    public static final String USES_POLICY_MDM_KNOX_ACTIVATE_DEVICE_PERMISSIONS_TAG = "com.samsung.android.knox.permission.KNOX_ACTIVATE_DEVICE_PERMISSIONS_INTERNAL";
    public static final int USES_POLICY_MDM_KNOX_MOBILE_THREAT_DEFENSE = 106;
    public static final String USES_POLICY_MDM_KNOX_MOBILE_THREAT_DEFENSE_TAG = "com.samsung.android.knox.permission.KNOX_MOBILE_THREAT_DEFENSE";
    public static final int USES_POLICY_MDM_LDAP_SETTINGS = 44;
    public static final String USES_POLICY_MDM_LDAP_SETTINGS_TAG = "com.samsung.android.knox.permission.KNOX_LDAP";
    public static final int USES_POLICY_MDM_LICENSE_LOG = 49;
    public static final String USES_POLICY_MDM_LICENSE_LOG_TAG = "com.samsung.android.knox.permission.KNOX_LICENSE_LOG";
    public static final int USES_POLICY_MDM_LOCATION = 31;
    public static final String USES_POLICY_MDM_LOCATION_TAG = "com.samsung.android.knox.permission.KNOX_LOCATION";
    public static final int USES_POLICY_MDM_LOCKSCREEN = 46;
    public static final String USES_POLICY_MDM_LOCKSCREEN_TAG = "com.samsung.android.knox.permission.KNOX_LOCKSCREEN";
    public static final int USES_POLICY_MDM_MULTI_USER_MGMT = 50;
    public static final String USES_POLICY_MDM_MULTI_USER_MGMT_TAG = "com.samsung.android.knox.permission.KNOX_MULTI_USER_MGMT";
    public static final int USES_POLICY_MDM_PHONE_RESTRICTION = 35;
    public static final String USES_POLICY_MDM_PHONE_RESTRICTION_TAG = "com.samsung.android.knox.permission.KNOX_PHONE_RESTRICTION";
    public static final int USES_POLICY_MDM_RCP_SYNC_MGMT = 57;
    public static final String USES_POLICY_MDM_RCP_SYNC_MGMT_TAG = "com.sec.enterprise.knox.permission.KNOX_RCP_SYNC_MGMT,com.samsung.android.knox.permission.KNOX_CONTAINER_RCP";
    public static final int USES_POLICY_MDM_REMOTE_CONTROL = 40;
    public static final String USES_POLICY_MDM_REMOTE_CONTROL_TAG = "com.samsung.android.knox.permission.KNOX_REMOTE_CONTROL";
    public static final int USES_POLICY_MDM_RESTRICTION = 30;
    public static final String USES_POLICY_MDM_RESTRICTION_TAG = "com.samsung.android.knox.permission.KNOX_RESTRICTION_MGMT";
    public static final int USES_POLICY_MDM_ROAMING = 26;
    public static final String USES_POLICY_MDM_ROAMING_TAG = "com.samsung.android.knox.permission.KNOX_ROAMING";
    public static final int USES_POLICY_MDM_SECURITY = 28;
    public static final String USES_POLICY_MDM_SECURITY_TAG = "com.samsung.android.knox.permission.KNOX_SECURITY";
    public static final int USES_POLICY_MDM_SMARTCARD = 91;
    public static final String USES_POLICY_MDM_SMARTCARD_TAG = "com.samsung.android.knox.permission.KNOX_SMARTCARD";
    public static final int USES_POLICY_MDM_SSO = 67;
    public static final String USES_POLICY_MDM_SSO_TAG = "com.sec.enterprise.mdm.permission.MDM_SSO,com.samsung.android.knox.permission.KNOX_SSO";
    public static final int USES_POLICY_MDM_VPN = 33;
    public static final String USES_POLICY_MDM_VPN_TAG = "com.samsung.android.knox.permission.KNOX_VPN";
    public static final int USES_POLICY_MDM_WIFI = 27;
    public static final String USES_POLICY_MDM_WIFI_TAG = "com.samsung.android.knox.permission.KNOX_WIFI";
    public static final int USES_POLICY_RAPID_TEST_ATTESTATION = 127;
    public static final String USES_POLICY_RAPID_TEST_ATTESTATION_TAG = "com.sec.enterprise.knox.permission.KNOX_ATTESTATION";
    public static final int USES_POLICY_RAPID_TEST_SPD = 126;
    public static final String USES_POLICY_RAPID_TEST_SPD_TAG = "com.samsung.android.knox.permission.KNOX_SPDCONTROL";
    public boolean mAuthorized;
    public DeviceAdminInfo mDeviceAdminInfo;
    public boolean mIsPseudoAdmin;
    public long mLicenseExpiryTime;
    public final ResolveInfo mReceiver;
    public List<String> mRequestedPermissions;
    public BitSet mUsesPolicies;
    public boolean mVisible;
    public static final boolean timaversion = "3.0".equals(SystemProperties.get("ro.config.timaversion", "0"));
    public static HashMap<String, String> sOldToNewPermissionMapping = new HashMap<>();
    public static HashMap<String, String> sNewToOldPermissionMapping = new HashMap<>();
    public static ArrayList<PolicyInfo> sPoliciesDisplayOrder = new ArrayList<>();
    public static HashMap<String, Integer> sKnownPolicies = new HashMap<>();
    public static SparseArray<PolicyInfo> sRevKnownPolicies = new SparseArray<>();

    public class PolicyInfo {
        public final int description;
        public final int descriptionForSecondaryUsers;
        public final int ident;
        public final int label;
        public final int labelForSecondaryUsers;
        public final String tag;

        public PolicyInfo(int i, String str, int i2, int i3) {
            this(i, str, i2, i3, i2, i3);
        }

        public PolicyInfo(int i, String str, int i2, int i3, int i4, int i5) {
            this.ident = i;
            this.tag = str;
            this.label = i2;
            this.description = i3;
            this.labelForSecondaryUsers = i4;
            this.descriptionForSecondaryUsers = i5;
        }
    }

    static {
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(22, USES_POLICY_MDM_APPLICATION_TAG, 17042326, R.string.wireless_display_route_description, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(23, USES_POLICY_MDM_BLUETOOTH_TAG, 17042329, R.string.work_mode_turn_on, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(24, USES_POLICY_MDM_DEVICE_INVENTORY_TAG, 17042341, R.string.zen_mode_alarm, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(25, USES_POLICY_MDM_EXCHANGE_ACCOUNT_TAG, 17042352, R.string.zen_mode_duration_minutes_short, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(26, USES_POLICY_MDM_ROAMING_TAG, 17042373, 17042033, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(27, USES_POLICY_MDM_WIFI_TAG, 17042391, 17042051, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(28, USES_POLICY_MDM_SECURITY_TAG, 17042380, 17042040, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(29, USES_POLICY_MDM_HARDWARE_CONTROL_TAG, 17042356, R.string.zen_mode_forever, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(30, USES_POLICY_MDM_RESTRICTION_TAG, 17042372, 17042032, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(31, USES_POLICY_MDM_LOCATION_TAG, 17042366, R.string.zen_upgrade_notification_visd_title, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(32, USES_POLICY_MDM_EMAIL_ACCOUNT_TAG, 17042345, R.string.zen_mode_default_weeknights_name, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(33, USES_POLICY_MDM_VPN_TAG, 17042390, 17042050, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(34, USES_POLICY_MDM_APN_SETTINGS_TAG, 17042324, R.string.window_magnification_prompt_content, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(35, USES_POLICY_MDM_PHONE_RESTRICTION_TAG, 17042368, 17042028, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(36, USES_POLICY_MDM_BROWSER_SETTINGS_TAG, 17042332, R.string.work_profile_deleted_details, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(53, USES_POLICY_MDM_BROWSER_PROXY_TAG, 17042331, R.string.work_profile_deleted_description_dpm_wipe, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(37, USES_POLICY_MDM_DATE_TIME_TAG, 17042339, R.string.year, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(54, "com.samsung.android.knox.permission.KNOX_VPN_GENERIC", 17042308, R.string.whichOpenLinksWith, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(55, "com.samsung.android.knox.permission.KNOX_VPN_CONTAINER", 17042300, R.string.whichHomeApplication, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(38, USES_POLICY_MDM_FIREWALL_TAG, 17042353, R.string.zen_mode_duration_minutes_summary, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(39, USES_POLICY_MDM_ENTERPRISE_DEVICE_ADMIN_TAG, 17042346, R.string.zen_mode_downtime_feature_name, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(40, USES_POLICY_MDM_REMOTE_CONTROL_TAG, 17042371, 17042031, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(41, USES_POLICY_MDM_KIOSK_MODE_TAG, 17042359, R.string.zen_mode_implicit_deactivated, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(42, USES_POLICY_MDM_CERTIFICATE_PERMISSION_TAG, 17042336, R.string.work_profile_telephony_paused_turn_on_button, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(43, USES_POLICY_MDM_AUDIT_LOG_PERMISSION_TAG, 17042328, R.string.work_mode_off_title, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(48, USES_POLICY_MDM_ENTERPRISE_CONTAINER_TAG, 17042347, R.string.zen_mode_duration_hours, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(44, USES_POLICY_MDM_LDAP_SETTINGS_TAG, 17042364, R.string.zen_mode_trigger_summary_range_symbol_combination, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(46, USES_POLICY_MDM_LOCKSCREEN_TAG, 17042365, R.string.zen_mode_until, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(47, USES_POLICY_MDM_DUAL_SIM_TAG, 17042343, R.string.zen_mode_default_every_night_name, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(67, USES_POLICY_MDM_SSO_TAG, 17042376, 17042036, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(45, USES_POLICY_MDM_GEOFENCING_TAG, 17042354, R.string.zen_mode_duration_minutes_summary_short, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(49, USES_POLICY_MDM_LICENSE_LOG_TAG, 17042344, R.string.zen_mode_default_weekends_name, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(50, USES_POLICY_MDM_MULTI_USER_MGMT_TAG, 17042367, 17042027, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(51, USES_POLICY_MDM_BLUETOOTH_SECURE_MODE_TAG, 17042330, R.string.work_profile_deleted, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(107, USES_POLICY_KNOX_ENHANCED_ATTESTATION_TAG, 17042255, R.string.volume_music_hint_silent_ringtone_selected, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(106, "com.samsung.android.knox.permission.KNOX_MOBILE_THREAT_DEFENSE", 17042384, 17042044, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(108, USES_POLICY_KNOX_CRITICAL_COMMUNICATIONS_TAG, 17042247, R.string.volume_dialog_ringer_guidance_vibrate, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(57, USES_POLICY_MDM_RCP_SYNC_MGMT_TAG, 17042362, R.string.zen_mode_trigger_event_calendar_any, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(56, USES_POLICY_MDM_KNOX_ACTIVATE_DEVICE_PERMISSIONS_TAG, 17042360, R.string.zen_mode_implicit_trigger_description, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(111, USES_POLICY_KNOX_DEACTIVATE_LICENSE_TAG, 17042340, R.string.years, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(58, USES_POLICY_KNOX_SEAMS_PERM_TAG, 17042378, 17042038, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(59, USES_POLICY_KNOX_SEAMS_SEPOLICY_PERM_TAG, 17042379, 17042039, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(60, USES_POLICY_KNOX_RESTRICTION_PERM_TAG, 17042363, R.string.zen_mode_trigger_summary_divider_text, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(63, USES_POLICY_KNOX_CUSTOM_SETTING_TAG, 17042303, R.string.whichImageCaptureApplication, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(64, USES_POLICY_KNOX_CUSTOM_SYSTEM_TAG, 17042304, R.string.whichImageCaptureApplicationLabel, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(65, USES_POLICY_KNOX_CUSTOM_SEALEDMODE_TAG, 17042301, R.string.whichHomeApplicationLabel, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(70, USES_POLICY_KNOX_CUSTOM_PROKIOSK_TAG, 17042301, R.string.whichHomeApplicationLabel, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(68, USES_POLICY_KNOX_ENTERPRISE_BILLING_TAG, 17042256, R.string.volume_notification, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(61, USES_POLICY_KNOX_CCM_TAG, 17042334, R.string.work_profile_telephony_paused_text, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(72, USES_POLICY_KNOX_UCSM_ESE_TAG, 17042388, 17042048, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(73, USES_POLICY_KNOX_UCSM_OTHER_TAG, 17042389, 17042049, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(74, USES_POLICY_KNOX_UCS_PLUGIN_TAG, 17042475, 17042134, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(76, USES_POLICY_KNOX_UCM_PRIVILEGED_TAG, 17042387, 17042047, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(62, USES_POLICY_KNOX_KEYSTORE_TAG, 17042357, R.string.zen_mode_forever_dnd, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(75, USES_POLICY_KNOX_KEYSTORE_PER_APP_TAG, 17042358, R.string.zen_mode_implicit_activated, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(66, USES_POLICY_KNOX_CERTENROL_TAG, 17042377, 17042037, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(71, USES_POLICY_KNOX_SDP_TAG, 17042374, 17042034, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(77, USES_POLICY_MDM_GLOBALPROXY_TAG, 17042355, R.string.zen_mode_feature_name, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(78, USES_POLICY_KNOX_CERT_PROVISIONING_TAG, 17042335, R.string.work_profile_telephony_paused_title, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(79, USES_POLICY_KNOX_CLIPBOARD_TAG, 17042337, R.string.write_fail_reason_cancelled, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(80, USES_POLICY_KNOX_ADVANCED_APP_MGMT_TAG, 17042294, R.string.whichApplicationLabel, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(81, USES_POLICY_KNOX_ADVANCED_SECURITY_TAG, 17042295, R.string.whichApplicationNamed, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(82, "com.samsung.android.knox.permission.KNOX_NPA", 17042311, R.string.whichSendApplicationLabel, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(83, USES_POLICY_KNOX_EBILLING_NOMDM_TAG, 17042256, R.string.volume_notification, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(84, USES_POLICY_KNOX_DEX_TAG, 17042342, R.string.zen_mode_default_events_name, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(85, USES_POLICY_KNOX_CUSTOM_DEX_TAG, 17042304, R.string.whichImageCaptureApplicationLabel, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(86, USES_POLICY_KNOX_UCM_MGMT_TAG, 17042474, 17042133, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(87, USES_POLICY_KNOX_DUAL_DAR_TAG, 17042306, R.string.whichOpenHostLinksWith, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(89, USES_POLICY_KNOX_SIM_RESTRICTION_TAG, 17042368, 17042028, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(90, USES_POLICY_MDM_APPLICATION_PERMISSION_TAG, 17042327, R.string.work_mode_emergency_call_button, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(91, USES_POLICY_MDM_SMARTCARD_TAG, 17042383, 17042043, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(110, USES_POLICY_KNOX_HDM_TAG, 17042309, R.string.whichOpenLinksWithApp, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(112, USES_POLICY_KNOX_APP_SEPARATION_TAG, 17042292, R.string.wfc_mode_wifi_preferred_summary, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(113, USES_POLICY_KNOX_CAPTURE_TAG, 17042299, R.string.whichGiveAccessToApplicationLabel, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(122, USES_POLICY_KNOX_FORESIGHT_TAG, 17042307, R.string.whichOpenHostLinksWithApp, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(123, USES_POLICY_KNOX_AUTHENTICATION_MANAGER_TAG, 17042297, R.string.whichEditApplicationLabel, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(114, USES_POLICY_KNOX_SEAMS_SEPOLICY_TAG, 17042379, 17042039, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(109, "com.samsung.android.knox.permission.KNOX_DEVICE_CONFIGURATION", 17042305, R.string.whichImageCaptureApplicationNamed, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(115, USES_POLICY_KNOX_NDA_PERIPHERAL_TAG, 17042406, 17042066, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(116, USES_POLICY_KNOX_NDA_DEVICE_SETTINGS_TAG, 17042252, R.string.volume_icon_description_ringer, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(117, USES_POLICY_KNOX_NDA_DATA_ANALYTICS_TAG, 17042251, R.string.volume_icon_description_notification, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(118, USES_POLICY_KNOX_NDA_AI_TAG, 17042317, R.string.whichViewApplicationNamed, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(119, USES_POLICY_KNOX_CAPTURE_BASIC_TAG, 17042299, R.string.whichGiveAccessToApplicationLabel, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(120, USES_POLICY_KNOX_CAPTURE_ADVANCED_TAG, 17042299, R.string.whichGiveAccessToApplicationLabel, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(121, USES_POLICY_KNOX_MPOS_TAG, 17042310, R.string.whichSendApplication, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(124, USES_POLICY_KNOX_NETWORK_FILTER_MGMT_TAG, 17042312, R.string.whichSendApplicationNamed, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(125, USES_POLICY_KNOX_NETWORK_FILTER_SP_TAG, 17042312, R.string.whichSendToApplication, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(126, USES_POLICY_RAPID_TEST_SPD_TAG, 17042312, R.string.whichSendToApplication, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(127, USES_POLICY_RAPID_TEST_ATTESTATION_TAG, 17042312, R.string.whichSendToApplication, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(128, USES_POLICY_KNOX_AUTHORIZATION_TAG, 17042298, R.string.whichEditApplicationNamed, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(129, USES_POLICY_KNOX_ACCESS_GRANT_TAG, 17042293, R.string.whichApplication, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(130, USES_POLICY_KNOX_RC_ACCESS_GRANT_TAG, 17042314, R.string.whichSendToApplicationNamed, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(131, USES_POLICY_KNOX_ANALYTICS_DEVELOPER_TAG, 17042296, R.string.whichEditApplication, sPoliciesDisplayOrder);
        for (int i = 0; i < sPoliciesDisplayOrder.size(); i++) {
            PolicyInfo policyInfo = sPoliciesDisplayOrder.get(i);
            sRevKnownPolicies.put(policyInfo.ident, policyInfo);
            sKnownPolicies.put(policyInfo.tag, Integer.valueOf(policyInfo.ident));
            String[] strArrSplit = policyInfo.tag.split(",");
            if (strArrSplit != null && strArrSplit.length == 2) {
                sOldToNewPermissionMapping.put(strArrSplit[0], strArrSplit[1]);
                sNewToOldPermissionMapping.put(strArrSplit[1], strArrSplit[0]);
            }
        }
        CREATOR = new Parcelable.Creator<EnterpriseDeviceAdminInfo>() { // from class: com.samsung.android.knox.EnterpriseDeviceAdminInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public EnterpriseDeviceAdminInfo createFromParcel(Parcel parcel) {
                return new EnterpriseDeviceAdminInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public EnterpriseDeviceAdminInfo[] newArray(int i2) {
                return new EnterpriseDeviceAdminInfo[i2];
            }
        };
    }

    public EnterpriseDeviceAdminInfo(Context context, ResolveInfo resolveInfo) throws Throwable {
        this.mRequestedPermissions = new ArrayList();
        this.mDeviceAdminInfo = new DeviceAdminInfo(context, resolveInfo);
        this.mUsesPolicies = new BitSet();
        this.mReceiver = resolveInfo;
        if ("com.android.email".equals(resolveInfo.activityInfo.packageName)) {
            return;
        }
        parseRequestedPermissions(context);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void dump(Printer printer, String str) {
        printer.println(str + "Receiver:");
        this.mReceiver.dump(printer, str + "  ");
    }

    public ActivityInfo getActivityInfo() {
        DeviceAdminInfo deviceAdminInfo = this.mDeviceAdminInfo;
        if (deviceAdminInfo != null) {
            return deviceAdminInfo.getActivityInfo();
        }
        return null;
    }

    public ComponentName getComponent() {
        DeviceAdminInfo deviceAdminInfo = this.mDeviceAdminInfo;
        if (deviceAdminInfo != null) {
            return deviceAdminInfo.getComponent();
        }
        return null;
    }

    public long getLicenseExpiry() {
        return this.mLicenseExpiryTime;
    }

    public String getPackageName() {
        DeviceAdminInfo deviceAdminInfo = this.mDeviceAdminInfo;
        return deviceAdminInfo != null ? deviceAdminInfo.getPackageName() : "NonExist";
    }

    public ResolveInfo getReceiver() {
        return this.mReceiver;
    }

    public String getReceiverName() {
        return this.mDeviceAdminInfo.getReceiverName();
    }

    public List<String> getRequestedPermissions() {
        return this.mRequestedPermissions;
    }

    public String getTagForPolicy(int i) {
        DeviceAdminInfo deviceAdminInfo = this.mDeviceAdminInfo;
        if (deviceAdminInfo == null) {
            return null;
        }
        if (i < 22) {
            return deviceAdminInfo.getTagForPolicy(i);
        }
        if (sRevKnownPolicies.get(i) != null) {
            return sRevKnownPolicies.get(i).tag;
        }
        return null;
    }

    public ArrayList<PolicyInfo> getUsedPolicies() {
        ArrayList<PolicyInfo> arrayList = new ArrayList<>();
        ArrayList usedPolicies = this.mDeviceAdminInfo.getUsedPolicies();
        for (int i = 0; i < usedPolicies.size(); i++) {
            arrayList.add(new PolicyInfo(((DeviceAdminInfo.PolicyInfo) usedPolicies.get(i)).ident, ((DeviceAdminInfo.PolicyInfo) usedPolicies.get(i)).tag, ((DeviceAdminInfo.PolicyInfo) usedPolicies.get(i)).label, ((DeviceAdminInfo.PolicyInfo) usedPolicies.get(i)).description));
        }
        for (int i2 = 0; i2 < sPoliciesDisplayOrder.size(); i2++) {
            PolicyInfo policyInfo = sPoliciesDisplayOrder.get(i2);
            if (usesPolicy(policyInfo.ident)) {
                arrayList.add(policyInfo);
            }
        }
        return arrayList;
    }

    public boolean isAuthorized() {
        return this.mAuthorized;
    }

    public boolean isProxy() {
        return false;
    }

    public boolean isPseudo() {
        return this.mIsPseudoAdmin;
    }

    public boolean isVisible() {
        return this.mDeviceAdminInfo.isVisible();
    }

    public CharSequence loadDescription(PackageManager packageManager) throws Resources.NotFoundException {
        return this.mDeviceAdminInfo.loadDescription(packageManager);
    }

    public Drawable loadIcon(PackageManager packageManager) {
        return this.mDeviceAdminInfo.loadIcon(packageManager);
    }

    public CharSequence loadLabel(PackageManager packageManager) {
        return this.mDeviceAdminInfo.loadLabel(packageManager);
    }

    /* JADX WARN: Removed duplicated region for block: B:103:0x01f5  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x004f  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0116 A[PHI: r8
      0x0116: PHI (r8v7 android.content.res.TypedArray) = (r8v2 android.content.res.TypedArray), (r8v10 android.content.res.TypedArray) binds: [B:69:0x0122, B:62:0x0114] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:73:0x012a  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0141 A[Catch: Exception -> 0x01eb, TryCatch #2 {Exception -> 0x01eb, blocks: (B:75:0x0135, B:77:0x0141, B:78:0x0145, B:80:0x014b, B:82:0x0159, B:87:0x0198, B:89:0x01a2, B:90:0x01a7, B:92:0x01aa, B:94:0x01b8, B:96:0x01dd, B:83:0x0174, B:85:0x017c), top: B:107:0x0135 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public List<String> parseRequestedPermissions(Context context) throws Throwable {
        AssetManager assetManager;
        XmlResourceParser xmlResourceParserOpenXmlResourceParser;
        TypedArray typedArrayObtainAttributes;
        List<String> eLMPermissions;
        String str;
        int next;
        String name;
        String str2;
        int iAddAssetPath;
        String str3 = this.mReceiver.activityInfo.applicationInfo.publicSourceDir;
        TypedArray typedArray = null;
        try {
            assetManager = new AssetManager();
        } catch (Exception e) {
            e = e;
            assetManager = null;
        }
        try {
            iAddAssetPath = assetManager.addAssetPath(str3);
        } catch (Exception e2) {
            e = e2;
            Log.w(TAG, "Unable to read AndroidManifest.xml of " + str3, e);
            xmlResourceParserOpenXmlResourceParser = null;
            if (xmlResourceParserOpenXmlResourceParser == null) {
            }
        }
        if (iAddAssetPath != 0) {
            xmlResourceParserOpenXmlResourceParser = assetManager.openXmlResourceParser(iAddAssetPath, "AndroidManifest.xml");
            if (xmlResourceParserOpenXmlResourceParser == null) {
                if (assetManager != null) {
                    assetManager.close();
                }
                return null;
            }
            DisplayMetrics displayMetrics = new DisplayMetrics();
            displayMetrics.setToDefaults();
            try {
                Resources resources = new Resources(assetManager, displayMetrics, null);
                do {
                    next = xmlResourceParserOpenXmlResourceParser.next();
                    if (next == 2) {
                        break;
                    }
                } while (next != 1);
                int depth = xmlResourceParserOpenXmlResourceParser.getDepth();
                typedArrayObtainAttributes = null;
                while (true) {
                    try {
                        try {
                            int next2 = xmlResourceParserOpenXmlResourceParser.next();
                            if (next2 == 1 || (next2 == 3 && xmlResourceParserOpenXmlResourceParser.getDepth() <= depth)) {
                                break;
                            }
                            if (next2 != 3 && next2 != 4 && (name = xmlResourceParserOpenXmlResourceParser.getName()) != null && name.equals("uses-permission")) {
                                typedArrayObtainAttributes = resources.obtainAttributes(xmlResourceParserOpenXmlResourceParser, com.android.internal.R.styleable.AndroidManifestUsesPermission);
                                String nonResourceString = typedArrayObtainAttributes.getNonResourceString(0);
                                if (sOldToNewPermissionMapping.containsKey(nonResourceString)) {
                                    str2 = nonResourceString + "," + sOldToNewPermissionMapping.get(nonResourceString);
                                } else if (sNewToOldPermissionMapping.containsKey(nonResourceString)) {
                                    str2 = sNewToOldPermissionMapping.get(nonResourceString) + "," + nonResourceString;
                                } else {
                                    str2 = nonResourceString;
                                }
                                Integer num = sKnownPolicies.get(str2);
                                if (num != null) {
                                    this.mUsesPolicies.set(num.intValue());
                                    if (nonResourceString != null && !this.mRequestedPermissions.contains(nonResourceString)) {
                                        this.mRequestedPermissions.add(nonResourceString.intern());
                                    }
                                }
                                XmlUtils.skipCurrentTag(xmlResourceParserOpenXmlResourceParser);
                            }
                        } catch (IOException | RuntimeException | XmlPullParserException e3) {
                            e = e3;
                            e.printStackTrace();
                            if (typedArrayObtainAttributes != null) {
                            }
                            xmlResourceParserOpenXmlResourceParser.close();
                            if (assetManager != null) {
                            }
                            EnterpriseLicenseManager enterpriseLicenseManager = EnterpriseLicenseManager.getInstance(null);
                            PackageManager packageManager = context.getPackageManager();
                            eLMPermissions = enterpriseLicenseManager.getELMPermissions(this.mDeviceAdminInfo.getPackageName());
                            if (eLMPermissions != null) {
                            }
                            return this.mRequestedPermissions;
                        }
                    } catch (Throwable th) {
                        th = th;
                        typedArray = typedArrayObtainAttributes;
                        if (typedArray != null) {
                            typedArray.recycle();
                        }
                        throw th;
                    }
                }
            } catch (IOException | RuntimeException | XmlPullParserException e4) {
                e = e4;
                typedArrayObtainAttributes = null;
            } catch (Throwable th2) {
                th = th2;
                if (typedArray != null) {
                }
                throw th;
            }
            if (typedArrayObtainAttributes != null) {
                typedArrayObtainAttributes.recycle();
            }
            xmlResourceParserOpenXmlResourceParser.close();
            if (assetManager != null) {
                assetManager.close();
            }
            EnterpriseLicenseManager enterpriseLicenseManager2 = EnterpriseLicenseManager.getInstance(null);
            PackageManager packageManager2 = context.getPackageManager();
            try {
                eLMPermissions = enterpriseLicenseManager2.getELMPermissions(this.mDeviceAdminInfo.getPackageName());
                if (eLMPermissions != null) {
                    for (String str4 : eLMPermissions) {
                        if (sOldToNewPermissionMapping.containsKey(str4)) {
                            str = str4 + "," + sOldToNewPermissionMapping.get(str4);
                        } else if (sNewToOldPermissionMapping.containsKey(str4)) {
                            str = sNewToOldPermissionMapping.get(str4) + "," + str4;
                        } else {
                            str = str4;
                        }
                        Integer num2 = sKnownPolicies.get(str);
                        if (num2 != null) {
                            String[] strArrSplit = str.split(",");
                            int i = 0;
                            while (true) {
                                if (i >= strArrSplit.length) {
                                    break;
                                }
                                if (packageManager2.checkPermission(strArrSplit[i], this.mDeviceAdminInfo.getPackageName()) == 0) {
                                    Log.i(TAG, "Add Granted permission : " + str);
                                    this.mUsesPolicies.set(num2.intValue());
                                    if (!this.mRequestedPermissions.contains(str4)) {
                                        this.mRequestedPermissions.add(str4.intern());
                                    }
                                } else {
                                    i++;
                                }
                            }
                        }
                    }
                }
            } catch (Exception unused) {
                Log.e(TAG, "Failed to get ELM permissions");
            }
            return this.mRequestedPermissions;
        }
        Log.w(TAG, "Failed adding asset path:" + str3);
        xmlResourceParserOpenXmlResourceParser = null;
        if (xmlResourceParserOpenXmlResourceParser == null) {
        }
    }

    public final BitSet readBitSet(Parcel parcel) {
        int i = parcel.readInt();
        BitSet bitSet = new BitSet();
        for (int i2 = 0; i2 < i; i2++) {
            bitSet.set(parcel.readInt());
        }
        return bitSet;
    }

    public void readPoliciesFromXml(TypedXmlPullParser typedXmlPullParser) throws XmlPullParserException, IOException {
        this.mDeviceAdminInfo.readPoliciesFromXml(typedXmlPullParser);
    }

    public void setAuthorized(boolean z) {
        this.mAuthorized = z;
    }

    public void setLicenseExpiry(long j) {
        this.mLicenseExpiryTime = j;
    }

    public String toString() {
        return this.mReceiver != null ? TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder("DeviceAdminInfo{"), this.mReceiver.activityInfo.name, "}") : "";
    }

    public boolean usesMDMPolicy() {
        BitSet bitSet = this.mUsesPolicies;
        return (bitSet == null || bitSet.isEmpty()) ? false : true;
    }

    public boolean usesPolicy(int i) {
        DeviceAdminInfo deviceAdminInfo = this.mDeviceAdminInfo;
        if (deviceAdminInfo == null) {
            return false;
        }
        if (deviceAdminInfo.usesPolicy(i)) {
            return true;
        }
        return this.mUsesPolicies.get(i);
    }

    public final void writeBitSet(Parcel parcel, BitSet bitSet) {
        parcel.writeInt(bitSet.cardinality());
        int iNextSetBit = -1;
        while (true) {
            iNextSetBit = bitSet.nextSetBit(iNextSetBit + 1);
            if (iNextSetBit == -1) {
                return;
            } else {
                parcel.writeInt(iNextSetBit);
            }
        }
    }

    public void writePoliciesToXml(TypedXmlSerializer typedXmlSerializer) throws IllegalStateException, IOException, IllegalArgumentException {
        this.mDeviceAdminInfo.writePoliciesToXml(typedXmlSerializer);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        this.mReceiver.writeToParcel(parcel, i);
        writeBitSet(parcel, this.mUsesPolicies);
        parcel.writeInt(this.mIsPseudoAdmin ? 1 : 0);
    }

    public EnterpriseDeviceAdminInfo(Parcel parcel) {
        this.mRequestedPermissions = new ArrayList();
        this.mReceiver = (ResolveInfo) ResolveInfo.CREATOR.createFromParcel(parcel);
        this.mUsesPolicies = readBitSet(parcel);
        this.mIsPseudoAdmin = parcel.readInt() == 1;
    }

    public EnterpriseDeviceAdminInfo(boolean z) {
        this.mRequestedPermissions = new ArrayList();
        this.mIsPseudoAdmin = z;
        this.mReceiver = null;
    }
}
