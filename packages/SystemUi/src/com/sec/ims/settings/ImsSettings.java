package com.sec.ims.settings;

import android.net.Uri;
import android.provider.BaseColumns;
import com.sec.ims.configuration.DATA;
import java.util.HashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class ImsSettings {
    public static final String AUTHORITY = "com.sec.ims.settings";
    public static final String DEFAULT_MCC_MNC = "45001";
    public static final Uri GLOBAL_CONTENT_URI = Uri.parse("content://com.sec.ims.settings/global");
    public static final String TYPE_INT = "INT";
    public static final String TYPE_TEXT = "TEXT";

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static final class DmConfigTable {
        public static final Uri CONTENT_URI = Uri.parse(DATA.URI.DMCONFIG_PROVIDER);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static final class DnsQueryTable implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.parse("content://com.sec.ims.settings/dnsblock");
        public static final String DNS_BLOCK_ENABLE = "DNS_BLOCK_ENABLE";
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static final class GcfConfigTable implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.parse("content://com.sec.ims.settings/gcfconfig");
        public static final String GCF_CONFIG_ENABLE = "GCF_CONFIG_ENABLE";
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static final class GlobalTable implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.parse("content://com.sec.ims.settings/global");
        public static final String DM_APP_ID = "dm_app_id";
        public static final String DM_USER_DISP_NAME = "dm_user_disp_name";
        public static final String EMERGENCY_DOMAIN_SETTING = "emergency_domain_setting";
        public static final String NAME = "mnoname";
        public static final String NETWORK = "network";
        public static final String SMS_WRITE_UICC = "sms_write_uicc";
        public static final String SRVCC_VERSION = "srvcc_version";
        public static final String SS_CLIP_CLIR_BY_NETWORK = "ss_clip_clir_by_network";
        public static final String SS_DOMAIN_SETTING = "ss_domain_setting";
        public static final String USSD_DOMAIN_SETTING = "ussd_domain_setting";
        public static final String VOICE_DOMAIN_PREF_EUTRAN = "voice_domain_pref_eutran";
        public static final String VOICE_DOMAIN_PREF_UTRAN = "voice_domain_pref_utran";

        private GlobalTable() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static final class IdcConfigTable implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.parse("content://com.sec.ims.settings/idcconfig");
        public static final String IDC_APPDATA_PROCESS_MODE = "IDC_APPDATA_PROCESS_MODE";
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class ImsServiceSwitchTable {
        public static final String ENABLED = "enabled";
        public static final String NAME = "name";
        public static final String[] PROJECTION = {"name", "enabled"};
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class ImsUserSettingTable {
        public static final String NAME = "name";
        public static final String[] PROJECTION = {"name", "value"};
        public static final String VALUE = "value";
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class MDMN {
        public static final String DEVICE_GROUP = "DeviceGroup";
        public static final String JANSKY = "Jansky";
        public static final String JANSKY2 = "JanskyPhase2";
        public static final String MEP = "MEP";
        public static final String NOT_SUPPORT = "NotSupport";
        public static final String NSDS = "Nsds";
        public static final String NSDS_CONFIG = "Nsdsconfig";
        public static final String NSDS_EUR = "nsds_eur";
        public static final String SAMSUNG = "Samsung";
        public static final String SOFTPHONE = "Softphone";
        public static final String TS43 = "ts43";
        public static final String V4B = "V4B";
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static final class ProfileIdTable implements BaseColumns {
        public static final String MCCMNC = "mccmnc";
        public static final String MNO_NAME = "mnoname";
        public static final String SALES_CODE = "salescode";
        public static final String TABLE_NAME = "match_profile_id";
        private static final HashMap<String, String> mTableMap;

        static {
            HashMap<String, String> hashMap = new HashMap<>();
            mTableMap = hashMap;
            hashMap.put("_id", "INTEGER PRIMARY KEY");
            hashMap.put("mccmnc", ImsSettings.TYPE_TEXT);
        }

        private ProfileIdTable() {
        }

        public static HashMap<String, String> getTableMap() {
            return mTableMap;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static final class ProfileTable implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.parse("content://com.sec.ims.settings/profile");
        public static final String DOMAIN = "domain";
        public static final String ENABLED = "enabled";
        public static final String IMPI = "impi";
        public static final String IMPU = "impu";
        public static final String MCCMNC = "mccmnc";
        public static final String MDMN_TYPE = "mdmn_type";
        public static final String NAME = "name";
        public static final String NETWORK = "network";
        public static final String PDN = "pdn";
        public static final String SUPPORT_199_PROVISIONAL_RESPONSE = "support_199_provisional_response";
        public static final String _ID = "id";

        private ProfileTable() {
        }
    }
}
