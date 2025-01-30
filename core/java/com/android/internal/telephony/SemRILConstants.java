package com.android.internal.telephony;

/* loaded from: classes5.dex */
public interface SemRILConstants {
    public static final String CALL_DOMAIN_CS = "CS";
    public static final String CALL_DOMAIN_PS = "PS";
    public static final String EXTRA_CLEAR_FAKE_RADIO_ON_BEFORE_DIAL = "clearFakeRadioOnBeforeDial";
    public static final String EXTRA_EMERGENCY_CALL_ROUTING = "emergencyCallRoute";
    public static final String EXTRA_IGNORE_ONGOING_IMS_CALL_IN_USE_IMS_FOR_CALL = "ignoreOngoingImsCallInUseImsForCall";
    public static final String EXTRA_LATEST_CALL_DOMAIN = "latestDomain";
    public static final String EXTRA_TEST_CS_ECALL_CONVERTED_TO_NORMAL = "isTestCsECallConvertedToNormal";
    public static final String EXTRA_TEST_EMERGENCY_NUMBER_URN = "test";

    public interface CarrierConfig {
        public static final String SEM_KEY_SPN_DISPLAY_RULE_IN_HOME = "sem_spn_display_rule_in_home";
        public static final String SEM_KEY_SPN_DISPLAY_RULE_IN_ROAMING = "sem_spn_display_rule_in_roaming";
        public static final String SEM_KEY_VOWIFI_OPNAME_STRING = "sem_vowifi_opname_string";
    }

    public interface CmcCall {
        public static final String CMC_CALL_SD_ANSWER = "answer";
        public static final String CMC_CALL_SD_CLEAR_INFO = "sdClearInfo";
        public static final String CMC_CALL_SD_END = "end";
        public static final String CMC_CALL_SD_HOLD = "hold";
        public static final String CMC_CALL_SD_PULL = "sdPull";
        public static final String CMC_CALL_SD_PULL_COMPLETE = "sdPullComplete";
        public static final String CMC_CALL_SD_REJECT = "reject";
        public static final String CMC_CALL_SD_RESUME = "resume";
        public static final String CMC_CALL_SD_SRVCC = "sdSrvcc";
        public static final String CMC_CALL_SD_TERMINATE = "sdTerminate";
    }

    public interface ConnectionAttribute {
        public static final int ATTR_FALLBACK = 256;
        public static final int ATTR_IS_MULTIPARTY = 512;
        public static final int ATTR_MIGRATE_FROM_IMSPHONE = 64;
        public static final int ATTR_NORMAL_CALL_ROUTING_EMERGENCY_CALL = 4;
        public static final int ATTR_ON_EMERGENCY_SEARCH = 1;
        public static final int ATTR_PREPARE_CS_ONLY_DAN = 32;
        public static final int ATTR_SKIP_EMERGENCY_SEARCH = 2;
        public static final int ATTR_START_DURING_VOLTE_ENABLED = 8;
        public static final int ATTR_TRIGGER_E911_START_DAN = 16;
        public static final int ATTR_USE_ASSISTED_DIALING = 128;
    }

    public interface E911Route {
        public static final int E911_ROUTE_CS = 1;
        public static final int E911_ROUTE_IGNORE = 5;
        public static final int E911_ROUTE_IWLAN = 3;
        public static final int E911_ROUTE_LTE = 2;
        public static final int E911_ROUTE_MAX = 6;
        public static final int E911_ROUTE_NONE = 4;
        public static final int E911_ROUTE_NR = 6;
    }

    public interface EmergencyControl {
        public static final int EMERGENCY_CONTROL_CONNECTED = 1;
        public static final int EMERGENCY_CONTROL_DIALED = 0;
        public static final int EMERGENCY_CONTROL_FAILED = 4;
        public static final int EMERGENCY_CONTROL_FAILED_MIN_ERROR = 200;
        public static final int EMERGENCY_CONTROL_FINISHED = 2;
        public static final int EMERGENCY_CONTROL_FINISHED_WITH_ECM = 3;
    }

    public interface EpsfbTriggerResult {
        public static final int REDIAL_TO_CS = 0;
        public static final int REDIAL_TO_LTE = 1;
    }

    public interface Request {
        public static final int RIL_OEM_REQUEST_BASE = 10000;
        public static final int RIL_REQUEST_OEM_ACCESS_PHONEBOOK_ENTRY = 10011;
        public static final int RIL_REQUEST_OEM_CHANGE_SIM_PERSO = 10017;
        public static final int RIL_REQUEST_OEM_EMERGENCY_CONTROL = 10039;
        public static final int RIL_REQUEST_OEM_EMERGENCY_SEARCH = 10038;
        public static final int RIL_REQUEST_OEM_ENTER_SIM_PERSO = 10018;
        public static final int RIL_REQUEST_OEM_GET_CELL_BROADCAST_CONFIG = 10008;
        public static final int RIL_REQUEST_OEM_GET_DISABLE_2G = 10031;
        public static final int RIL_REQUEST_OEM_GET_NR_DISABLE_MODE = 10048;
        public static final int RIL_REQUEST_OEM_GET_NR_ICON_TYPE = 10049;
        public static final int RIL_REQUEST_OEM_GET_PHONEBOOK_ENTRY = 10010;
        public static final int RIL_REQUEST_OEM_GET_PHONEBOOK_STORAGE_INFO = 10009;
        public static final int RIL_REQUEST_OEM_GET_PREFERRED_NETWORK_LIST = 10016;
        public static final int RIL_REQUEST_OEM_GET_STORED_MSG_COUNT_FROM_SIM = 10045;
        public static final int RIL_REQUEST_OEM_GET_VENDOR_CONFIGURATION = 10050;
        public static final int RIL_REQUEST_OEM_LOCK_INFO = 10013;
        public static final int RIL_REQUEST_OEM_QUERY_CNAP = 10029;
        public static final int RIL_REQUEST_OEM_QUERY_CSG_LIST = 10042;
        public static final int RIL_REQUEST_OEM_READ_SMS_FROM_SIM = 10046;
        public static final int RIL_REQUEST_OEM_SAFE_MODE = 10006;
        public static final int RIL_REQUEST_OEM_SELECT_CSG_MANUAL = 10043;
        public static final int RIL_REQUEST_OEM_SEND_ENCODED_USSD = 10019;
        public static final int RIL_REQUEST_OEM_SET_DISABLE_2G = 10032;
        public static final int RIL_REQUEST_OEM_SET_IMS_CALL_LIST = 10004;
        public static final int RIL_REQUEST_OEM_SET_MOBILE_DATA_SETTING = 10044;
        public static final int RIL_REQUEST_OEM_SET_NR_DISABLE_MODE = 10047;
        public static final int RIL_REQUEST_OEM_SET_PREFERRED_NETWORK_LIST = 10015;
        public static final int RIL_REQUEST_OEM_SET_SIM_ONOFF = 10041;
        public static final int RIL_REQUEST_OEM_SET_SIM_POWER = 10023;
        public static final int RIL_REQUEST_OEM_SET_TRANSMIT_POWER = 10007;
        public static final int RIL_REQUEST_OEM_SET_VENDOR_CONFIGURATION = 10051;
        public static final int RIL_REQUEST_OEM_SIM_GET_ATR = 10040;
        public static final int RIL_REQUEST_OEM_STK_SIM_INIT_EVENT = 10014;
        public static final int RIL_REQUEST_OEM_UICC_GBA_AUTHENTICATE_BOOTSTRAP = 10025;
        public static final int RIL_REQUEST_OEM_UICC_GBA_AUTHENTICATE_NAF = 10026;
        public static final int RIL_REQUEST_OEM_USIM_PB_CAPA = 10012;
        public static final int RIL_REQUEST_SEC_CDMA_SEND_SMS = 20087;
        public static final int RIL_REQUEST_SEC_CDMA_SEND_SMS_EXPECT_MORE = 20148;
        public static final int RIL_REQUEST_SEC_IMS_SEND_SMS = 20113;
        public static final int RIL_REQUEST_SEC_SEND_SMS = 20025;
        public static final int RIL_REQUEST_SEC_SEND_SMS_EXPECT_MORE = 20026;
        public static final int RIL_REQUEST_SEC_WRITE_SMS_TO_SIM = 20063;
        public static final int RIL_SEC_REQUEST_BASE = 20000;
    }

    public interface UnsolResponse {
        public static final int RIL_OEM_UNSOL_RESPONSE_BASE = 11000;
        public static final int RIL_UNSOL_OEM_ACB_INFO_CHANGED = 11005;
        public static final int RIL_UNSOL_OEM_CLM_NOTI = 11065;
        public static final int RIL_UNSOL_OEM_CP_POSITION = 11038;
        public static final int RIL_UNSOL_OEM_CS_FALLBACK = 11030;
        public static final int RIL_UNSOL_OEM_DEVICE_READY_NOTI = 11008;
        public static final int RIL_UNSOL_OEM_EXECUTE = 11090;
        public static final int RIL_UNSOL_OEM_EXTENDED_REGISTRATION_STATE = 11086;
        public static final int RIL_UNSOL_OEM_FACTORY_AM = 11026;
        public static final int RIL_UNSOL_OEM_FIVEG_STATUS_CHANGED = 11081;
        public static final int RIL_UNSOL_OEM_GPS_NOTI = 11009;
        public static final int RIL_UNSOL_OEM_HOME_NETWORK_NOTI = 11043;
        public static final int RIL_UNSOL_OEM_IMS_PREFERENCE_CHANGED = 11061;
        public static final int RIL_UNSOL_OEM_IMS_RETRYOVER = 11034;
        public static final int RIL_UNSOL_OEM_MCPTT_NOTI = 11069;
        public static final int RIL_UNSOL_OEM_NR_BEARER_ALLOCATION_CHANGED = 11080;
        public static final int RIL_UNSOL_OEM_NR_ICON_TYPE_CHANGED = 11092;
        public static final int RIL_UNSOL_OEM_PB_INIT_COMPLETE = 11035;
        public static final int RIL_UNSOL_OEM_PROSE_NOTI = 11068;
        public static final int RIL_UNSOL_OEM_RELEASE_COMPLETE_MESSAGE = 11001;
        public static final int RIL_UNSOL_OEM_RMTUIM_CARD_POWER_DOWN = 11074;
        public static final int RIL_UNSOL_OEM_RMTUIM_CARD_POWER_UP = 11073;
        public static final int RIL_UNSOL_OEM_RMTUIM_CARD_RESET = 11075;
        public static final int RIL_UNSOL_OEM_RMTUIM_CONNECT = 11071;
        public static final int RIL_UNSOL_OEM_RMTUIM_DISCONNECT = 11072;
        public static final int RIL_UNSOL_OEM_RMTUIM_NEED_APDU = 11070;
        public static final int RIL_UNSOL_OEM_RRC_STATE_CHANGED = 11088;
        public static final int RIL_UNSOL_OEM_SAP = 11013;
        public static final int RIL_UNSOL_OEM_SIGNAL_LEVEL_INFOS = 11091;
        public static final int RIL_UNSOL_OEM_SIM_APPLICATION_REFRESH = 11062;
        public static final int RIL_UNSOL_OEM_SIM_COUNT_MISMATCHED = 11058;
        public static final int RIL_UNSOL_OEM_SIM_ONOFF_NOTI = 11078;
        public static final int RIL_UNSOL_OEM_SIM_PB_READY = 11021;
        public static final int RIL_UNSOL_OEM_SIM_SWAP_STATE_CHANGED = 11057;
        public static final int RIL_UNSOL_OEM_STK_CALL_CONTROL_RESULT = 11003;
        public static final int RIL_UNSOL_OEM_STK_CALL_STATUS = 11054;
        public static final int RIL_UNSOL_OEM_STK_SEND_SMS_RESULT = 11002;
        public static final int RIL_UNSOL_OEM_TIMER_STATUS_CHANGED_NOTI = 11067;
        public static final int RIL_UNSOL_OEM_TURN_RADIO_ON = 11076;
        public static final int RIL_UNSOL_OEM_UART = 11020;
        public static final int RIL_UNSOL_OEM_UICC_APPLICATION_STATUS = 11063;
        public static final int RIL_UNSOL_OEM_VE = 11024;
        public static final int RIL_UNSOL_OEM_VENDOR_CONFIGURATION_CHANGED = 11093;
    }
}
