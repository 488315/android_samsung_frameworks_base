package com.sec.ims.settings;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.sec.ims.configuration.DATA;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class RcsConfigurationReader {
    public static final String APPAUTH_PATH = "root/application/0/appauth/";
    public static final String AUTHORITY = "com.samsung.rcs.autoconfigurationprovider";
    public static final String AUTOCONFIG_COMPLETED = "info/completed";
    public static final Uri AUTO_CONFIGURATION_URI = Uri.parse(DATA.URI.CONFIG_PROVIDER);
    public static final String AUT_ACCEPT = "root/application/1/im/autaccept";
    public static final String AUT_ACCEPT_GROUP_CHAT = "root/application/1/im/autacceptgroupchat";
    public static final String CALL_COMPOSER_TIMER_IDLE = "root/application/1/other/callComposerTimerIdle";
    public static final String CALL_LOG_BEARER_DIFFER = "root/application/1/ux/callLogsBearerDiffer";
    public static final String CAPDISCOVERY_ALLOWED_PREFIXES = "root/application/1/capdiscovery/capdiscoverywhitelist/capdiscoveryallowedprefixes/prefix";
    public static final String CAPDISCOVERY_CAPINFO_EXPIRY = "root/application/1/capdiscovery/capinfoexpiry";
    public static final String CAPDISCOVERY_CAP_DISC_COMMON_STACK = "root/application/1/capdiscovery/capDiscCommonStack";
    public static final String CAPDISCOVERY_CHARACTERISTIC_PATH = "root/application/1/capdiscovery/";
    public static final String CAPDISCOVERY_DEFAULT_DISC = "root/application/1/capdiscovery/defaultdisc";
    public static final String CAPDISCOVERY_DISABLE_INITIAL_SCAN = "root/application/1/capdiscovery/disableInitialAddressBookScan";
    public static final String CAPDISCOVERY_MAX_ENTRIES_IN_LIST = "root/application/1/capdiscovery/maxentriesinlist";
    public static final String CAPDISCOVERY_NON_RCS_CAPINFO_EXPIRY = "root/application/1/capdiscovery/nonRCScapInfoExpiry";
    public static final String CAPDISCOVERY_POLLING_PERIOD = "root/application/1/capdiscovery/pollingperiod";
    public static final String CAPDISCOVERY_POLLING_RATE = "root/application/1/capdiscovery/pollingrate";
    public static final String CAPDISCOVERY_POLLING_RATE_PERIOD = "root/application/1/capdiscovery/pollingrateperiod";
    public static final String CFS_TRIGGER = "root/application/1/clientControl/cfsTrigger";
    public static final String CHATBOT_BOTINFOFQDNROOT = "root/application/1/messaging/chatbot/BotinfoFQDNRoot";
    public static final String CHATBOT_CHATBOTBLACKLIST = "root/application/1/messaging/chatbot/ChatbotBlacklist";
    public static final String CHATBOT_CHATBOTDIRECTORY = "root/application/1/messaging/chatbot/ChatbotDirectory";
    public static final String CHATBOT_CHATBOT_MSG_TECH = "root/application/1/messaging/chatbot/ChatbotMsgTech";
    public static final String CHATBOT_IDENTITY_IN_ENRICHED_SEARCH = "root/application/1/messaging/chatbot/IdentityInEnrichedSearch";
    public static final String CHATBOT_MSGHISTORYSELECTABLE = "root/application/1/messaging/chatbot/MsgHistorySelectable";
    public static final String CHATBOT_PRIVACY_DISABLE = "root/application/1/messaging/chatbot/PrivacyDisable";
    public static final String CHATBOT_SPECIFIC_CHATBOTS_LIST = "root/application/1/messaging/chatbot/SpecificChatbotsList";
    public static final String CHAT_AUTH = "ChatAuth";
    public static final String CHAT_ENABLED = "root/application/1/services/ChatAuth";
    public static final String CHAT_REVOKE_TIMER = "root/application/1/im/ChatRevokeTimer";
    public static final String CLIENT_CONTROL_CHARACTERISTIC_PATH = "root/application/1/clientControl/";
    public static final String CNMS_URL = "root/application/1/serviceproviderext/nms_url";
    public static final String CNS_URL = "root/application/1/serviceproviderext/nc_url";
    public static final String COMPOSER_AUTH = "root/application/1/services/composerAuth";
    public static final String CONFIG_VERSION = "root/vers/version";
    public static final String CONF_FCTY_URI = "root/application/1/im/conf-fcty-uri";
    public static final String DATAOFF_CHRACTERISTIC_PATH = "root/application/1/services/ext/dataoff";
    public static final String DATAOFF_CONTENT_SHARE = "root/application/1/services/ext/dataoffcontentShareDataOff";
    public static final String DATAOFF_FILE_TRANSFER = "root/application/1/services/ext/dataofffileTransferDataOff";
    public static final String DATAOFF_IP_VIDEO = "root/application/1/services/ext/dataoffIPVideoCallDataOff";
    public static final String DATAOFF_MMS = "root/application/1/services/ext/dataoffmmsDataOff";
    public static final String DATAOFF_PRE_AND_POST_CALL = "root/application/1/services/ext/dataoffpreAndPostCallDataOff";
    public static final String DATAOFF_PROVISIONING = "root/application/1/services/ext/dataoffprovisioningDataOff";
    public static final String DATAOFF_RCS_MESSAGING = "root/application/1/services/ext/dataoffrcsMessagingDataOff";
    public static final String DATAOFF_SMSOIP = "root/application/1/services/ext/dataoffsmsoIPDataOff";
    public static final String DATAOFF_SYNC = "root/application/1/services/ext/dataoffsyncDataOff";
    public static final String DATAOFF_VOLTE = "root/application/1/services/ext/dataoffvolteDataOff";
    public static final String DEFERRED_MSG_FUNC_URI = "root/application/1/im/deferred-msg-func-uri";
    public static final String DISPLAY_NOTIFICATION_SWITCH = "root/application/1/clientControl/displayNotificationSwitch";
    public static final String EXPLODER_URI = "root/application/1/im/exploder-uri";
    public static final String EXTENSIONS_MAX_MSRP_SIZE = "root/application/1/other/extensionsMaxMSRPSize";
    public static final String EXTENSION_ENABLED = "root/application/1/services/allowRCSExtensions";
    public static final String EXT_ATT_IM_MSRP_FT_MAX_SIZE = "root/application/1/im/ext/att/MSRPFtMaxSize";
    public static final String EXT_ATT_IM_SLM_MAX_RECIPIENTS = "root/application/1/im/ext/att/slmMaxRecipients";
    public static final String EXT_FT_HTTP_FT_WARN_SIZE = "root/application/1/im/ext/fthttpftwarnsize";
    public static final String EXT_FT_HTTP_MAX_SIZE_FILE_TR = "root/application/1/im/ext/fthttpmaxsizefiletr";
    public static final String EXT_FT_HTTP_MAX_SIZE_FILE_TR_INCOMING = "root/application/1/im/ext/fthttpmaxsizefiletrincoming";
    public static final String EXT_MAX_ADHOC_OPEN_GROUP_SIZE = "root/application/1/im/ext/max_adhoc_open_group_size";
    public static final String FIRST_MSG_INVITE = "root/application/1/im/firstMessageInvite";
    public static final String FTHTTP_CAP_ALWAYS_ON = "root/application/1/clientControl/ftHTTPCapAlwaysOn";
    public static final String FT_AUT_ACCEPT = "root/application/1/im/ftautaccept";
    public static final String FT_CAP_ALWAYS_ON = "root/application/1/im/ftCapalwaysON";
    public static final String FT_DEFAULT_MECH = "root/application/1/im/ftDefaultMech";
    public static final String FT_ENABLED = "root/application/1/services/ftAuth";
    public static final String FT_FB_DEFAULT = "root/application/1/ux/ftFBDefault";
    public static final String FT_HTTP_CS_PWD = "root/application/1/im/ftHTTPCSPwd";
    public static final String FT_HTTP_CS_URI = "root/application/1/im/ftHTTPCSURI";
    public static final String FT_HTTP_CS_USER = "root/application/1/im/ftHTTPCSUser";
    public static final String FT_HTTP_EXTRA_CS_URI = "root/application/1/im/ftHTTPExtraCSURI";
    public static final String FT_HTTP_FALLBACK = "root/application/1/im/ftHTTPFallback";
    public static final String FT_HTTP_FT_WARN_SIZE = "root/application/1/im/ftHTTPftWarnSize";
    public static final String FT_HTTP_MAX_SIZE_FILE_TR = "root/application/1/im/ftHTTPMaxSizeFileTr";
    public static final String FT_HTTP_MAX_SIZE_FILE_TR_INCOMING = "root/application/1/im/ftHTTPMaxSizeFileTrIncoming";
    public static final String FT_MAX1_TO_MANY_RECIPIENTS = "root/application/1/clientControl/ftMax1ToManyRecipients";
    public static final String FT_MSRP_FT_WARN_SIZE = "root/application/1/im/ext/ftMSRPftWarnSize";
    public static final String FT_MSRP_MAX_SIZE_FILE_TR = "root/application/1/im/ext/ftMSRPMaxSizeFileTr";
    public static final String FT_MSRP_MAX_SIZE_FILE_TR_INCOMING = "root/application/1/im/ext/ftMSRPMaxSizeFileTrIncoming";
    public static final String FT_ST_AND_FW_ENABLED = "root/application/1/im/ftStAndFwEnabled";
    public static final String FT_THUMB = "root/application/1/im/ftThumb";
    public static final String FT_WARN_SIZE = "root/application/1/im/ftWarnSize";
    public static final String GEOPULL_ENABLED = "root/application/1/services/geolocPullAuth";
    public static final String GEOPUSH_ENABLED = "root/application/1/services/geolocPushAuth";
    public static final String GROUP_CHAT_ENABLED = "root/application/1/services/GroupChatAuth";
    public static final String GROUP_CHAT_FULL_STAND_FWD = "root/application/1/im/GroupChatFullStandFwd";
    public static final String GROUP_CHAT_ONLY_F_STAND_FWD = "root/application/1/im/GroupChatOnlyFStandFwd";
    public static final String HOME_NETWORK_DOMAIN_NAME = "root/application/0/home_network_domain_name";
    public static final String IMS_APPLICATION_CHARACTERISTIC_PATH = "root/application/0/";
    public static final String IMS_APPLICATION_EXT_CHARACTERISTIC_PATH = "root/application/0/ext/";
    public static final String IM_CAP_ALWAYS_ON = "root/application/1/im/imcapalwayson";
    public static final String IM_CAP_NON_RCS = "root/application/1/im/imCapNonRCS";
    public static final String IM_CHARACTERISTIC_PATH = "root/application/1/im/";
    public static final String IM_EXT_ATT_CHARACTERISTIC_PATH = "root/application/1/im/ext/att/";
    public static final String IM_EXT_CHARACTERISTIC_PATH = "root/application/1/im/ext/";
    public static final String IM_MAX_SIZE = "root/application/1/im/MaxSize";
    public static final String IM_MAX_SIZE_1_TO_1 = "root/application/1/im/maxsize1to1";
    public static final String IM_MAX_SIZE_1_TO_M = "root/application/1/im/maxsize1tom";
    public static final String IM_MSG_TECH = "root/application/1/im/imMsgTech";
    public static final String IM_SESSION_START = "root/application/1/im/imsessionstart";
    public static final String IM_WARN_IW = "root/application/1/im/imwarniw";
    public static final String IM_WARN_SF = "root/application/1/im/imWarnSF";
    public static final String INT_URL_FORMAT = "root/application/0/ext/inturlfmt";
    public static final String IR51_SWITCH_UX = "root/application/1/ux/IR51SwitchUx";
    public static final String IR94_VIDEO_AUTH = "root/application/1/services/IR94VideoAuth";
    public static final String IS_ENABLED = "root/application/1/services/isAuth";
    public static final String JOYN_UX_MESSAGING_UX = "root/application/1/SERVICEPROVIDEREXT/joyn/UX/messagingUX";
    public static final String JOYN_UX_MSG_FB_DEFAULT = "root/application/1/SERVICEPROVIDEREXT/joyn/UX/msgFBDefault";
    public static final String JOYN_UX_PATH = "root/application/1/SERVICEPROVIDEREXT/joyn/UX/";
    public static final String KEEP_ALIVE_ENABLED = "root/application/0/keep_alive_enabled";
    public static final String LASTSEENACTIVE = "root/application/1/capdiscovery/ext/joyn/lastseenactive";
    public static final String LBO_PCSCF_ADDRESS = "root/application/0/lbo_p-cscf_address/0/address";
    public static final String LBO_PCSCF_ADDRESS_NEW = "root/application/0/lbo_p-cscf_address/0/lbo_p-cscf_addresses/address1";
    public static final String LBO_PCSCF_ADDRESS_TYPE = "root/application/0/lbo_p-cscf_address/0/addresstype";
    public static final String LBO_PCSCF_ADDRESS_TYPE_NEW = "root/application/0/lbo_p-cscf_address/0/lbo_p-cscf_addresses/addresstype1";
    public static final String MASS_FCTY_URI = "root/application/1/im/mass-fcty-uri";
    public static final String MAX1_TO_MANY_RECIPIENTS = "root/application/1/clientControl/max1toManyRecipients";
    public static final String MAX_ADHOC_CLOSED_GROUP_SIZE = "root/application/1/im/ext/max_adhoc_closed_group_size";
    public static final String MAX_ADHOC_GROUP_SIZE = "root/application/1/im/max_adhoc_group_size";
    public static final String MAX_ADHOC_OPEN_GROUP_SIZE = "root/application/1/im/max_adhoc_open_group_size";
    public static final String MAX_CONCURRENT_SESSION = "root/application/1/im/maxConcurrentSession";
    public static final String MAX_SIZE_EXTRA_FILE_TR = "root/application/1/im/MaxSizeExtraFileTr";
    public static final String MAX_SIZE_FILE_TR = "root/application/1/im/MaxSizeFileTr";
    public static final String MAX_SIZE_FILE_TR_INCOMING = "root/application/1/im/MaxSizeFileTrIncoming";
    public static final String MAX_SIZE_IMAGE_SHARE = "root/application/0/ext/maxsizeimageshare";
    public static final String MAX_TIME_VIDEO_SHARE = "root/application/0/ext/maxtimevideoshare";
    public static final String MESSAGING_CHARACTERISTIC_PATH = "root/application/1/messaging/";
    public static final String MESSAGING_CHATBOT_PATH = "root/application/1/messaging/chatbot/";
    public static final String MESSAGING_PLUGINS_PATH = "root/application/1/messaging/plugins/";
    public static final String MESSAGING_UX = "root/application/1/ux/messagingUX";
    public static final String MSGCAPVALIDITY = "root/application/1/capdiscovery/ext/joyn/msgcapvalidity";
    public static final String MSG_FB_DEFAULT = "root/application/1/ux/msgFBDefault";
    public static final String MULTIMEDIA_CHAT = "root/application/1/im/multiMediaChat";
    public static final String OMADM_AMR_AUDIO_BITRATE = "omadm/./3GPP_IMS/AMR_AUDIO_BITRATE";
    public static final String OMADM_AMR_AUDIO_BITRATE_WB = "omadm/./3GPP_IMS/AMR_AUDIO_BITRATE_WB";
    public static final String OMADM_AMR_WB = "omadm/./3GPP_IMS/AMR_WB";
    public static final String OMADM_AVAIL_CACHE_EXP = "omadm/./3GPP_IMS/AVAIL_CACHE_EXP";
    public static final String OMADM_CAP_CACHE_EXP = "omadm/./3GPP_IMS/CAP_CACHE_EXP";
    public static final String OMADM_CAP_DISCOVERY = "omadm/./3GPP_IMS/CAP_DISCOVERY";
    public static final String OMADM_CAP_POLL_INTERVAL = "omadm/./3GPP_IMS/CAP_POLL_INTERVAL";
    public static final String OMADM_DCN_NUMBER = "omadm/./3GPP_IMS/DCN_NUMBER";
    public static final String OMADM_DOMAIN_PUI = "omadm/./3GPP_IMS/DOMAIN_PUI";
    public static final String OMADM_EAB_SETTING = "omadm/./3GPP_IMS/EAB_SETTING";
    public static final String OMADM_FQDN_FOR_PCSCF = "omadm/./3GPP_IMS/FQDN_FOR_PCSCF";
    public static final String OMADM_GZIP_FLAG = "omadm/./3GPP_IMS/GZIP_FLAG";
    public static final String OMADM_HD_VOICE = "omadm/./3GPP_IMS/HD_VOICE";
    public static final String OMADM_HOME_NETWORK_DOMAIN_NAME = "omadm/./3GPP_IMS/Home_network_domain_name";
    public static final String OMADM_IMS_TEST_MODE = "omadm/./3GPP_IMS/IMS_TEST_MODE";
    public static final String OMADM_LVC_BETA_SETTING = "omadm/./3GPP_IMS/LVC_BETA_SETTING";
    public static final String OMADM_LVC_ENABLED = "omadm/./3GPP_IMS/LVC_ENABLED";
    public static final String OMADM_MIN_SE = "omadm/./3GPP_IMS/MIN_SE";
    public static final String OMADM_PATH = "omadm/./3GPP_IMS/";
    public static final String OMADM_PHONE_CONTEXT_URI = "omadm/./3GPP_IMS/PHONE_CONTEXT_URI";
    public static final String OMADM_POLL_LIST_SUB_EXP = "omadm/./3GPP_IMS/POLL_LIST_SUB_EXP";
    public static final String OMADM_PREF_CSCF_PORT = "omadm/./3GPP_IMS/PREF_CSCF_PORT";
    public static final String OMADM_PRIVATE_USER_IDENTITY = "omadm/./3GPP_IMS/Private_user_identity";
    public static final String OMADM_PUBLIC_USER_ID = "omadm/./3GPP_IMS/Public_user_identity_List/Public_user_identity_List_1/Public_user_identity";
    public static final String OMADM_PUBLISH_ERR_RETRY_TIMER = "omadm/./3GPP_IMS/PUBLISH_ERR_RETRY_TIMER";
    public static final String OMADM_PUBLISH_TIMER = "omadm/./3GPP_IMS/PUBLISH_TIMER";
    public static final String OMADM_PUBLISH_TIMER_EXTEND = "omadm/./3GPP_IMS/PUBLISH_TIMER_EXTEND";
    public static final String OMADM_P_CSCF_ADDRESS = "omadm/./3GPP_IMS/P-CSCF_Address";
    public static final String OMADM_RINGBACK_TIMER = "omadm/./3GPP_IMS/RINGBACK_TIMER";
    public static final String OMADM_RINGING_TIMER = "omadm/./3GPP_IMS/RINGING_TIMER";
    public static final String OMADM_RTP_RTCP_TIMER = "omadm/./3GPP_IMS/RTP_RTCP_TIMER";
    public static final String OMADM_SIP_SESSION_TIMER = "omadm/./3GPP_IMS/SIP_SESSION_TIMER";
    public static final String OMADM_SPEAKER_DEFAULT_VIDEO = "omadm/./3GPP_IMS/SPEAKER_DEFAULT_VIDEO";
    public static final String OMADM_SPR_IMS_ALPHA_ID = "omadm/./3GPP_IMS/SPR_IMS_ALPHA_ID";
    public static final String OMADM_SPR_IMS_NVISIM = "omadm/./3GPP_IMS/SPR_IMS_NVISIM";
    public static final String OMADM_SPR_IMS_PARAM_IND = "omadm/./3GPP_IMS/SPR_IMS_PARAM_IND";
    public static final String OMADM_SPR_IMS_PCSCF_ADDR_TYPE = "omadm/./3GPP_IMS/SPR_IMS_PCSCF_ADDR_TYPE";
    public static final String OMADM_SPR_IMS_PUID1 = "omadm/./3GPP_IMS/SPR_IMS_PUID1";
    public static final String OMADM_SPR_IMS_PUID2 = "omadm/./3GPP_IMS/SPR_IMS_PUID2";
    public static final String OMADM_SPR_IMS_PUID3 = "omadm/./3GPP_IMS/SPR_IMS_PUID3";
    public static final String OMADM_SPR_IMS_PUID4 = "omadm/./3GPP_IMS/SPR_IMS_PUID4";
    public static final String OMADM_SPR_IMS_PUID5 = "omadm/./3GPP_IMS/SPR_IMS_PUID5";
    public static final String OMADM_SPR_IMS_TPDA = "omadm/./3GPP_IMS/SPR_IMS_TPDA";
    public static final String OMADM_SPR_IMS_TPDCS = "omadm/./3GPP_IMS/SPR_IMS_TPDCS";
    public static final String OMADM_SPR_IMS_TPPID = "omadm/./3GPP_IMS/SPR_IMS_TPPID";
    public static final String OMADM_SPR_IMS_TPSCA = "omadm/./3GPP_IMS/SPR_IMS_TPSCA";
    public static final String OMADM_SPR_IMS_TPVP = "omadm/./3GPP_IMS/SPR_IMS_TPVP";
    public static final String OMADM_SRC_AMR = "omadm/./3GPP_IMS/SRC_AMR";
    public static final String OMADM_SRC_AMR_WB = "omadm/./3GPP_IMS/SRC_AMR_WB";
    public static final String OMADM_SRC_THROTTLE_PUBLISH = "omadm/./3GPP_IMS/SRC_THROTTLE_PUBLISH";
    public static final String OMADM_SUBSCRIBE_MAX_ENTRY = "omadm/./3GPP_IMS/SUBSCRIBE_MAX_ENTRY";
    public static final String OMADM_TWLAN_911_CALLFAIL_TIMER = "omadm/./3GPP_IMS/TWLAN_911_CALLFAIL_TIMER";
    public static final String OMADM_T_LTE_911_FAIL = "omadm/./3GPP_IMS/T_LTE_911_FAIL";
    public static final String OMADM_Timer_T1 = "omadm/./3GPP_IMS/Timer_T1";
    public static final String OMADM_Timer_T2 = "omadm/./3GPP_IMS/Timer_T2";
    public static final String OMADM_Timer_TF = "omadm/./3GPP_IMS/Timer_TF";
    public static final String OMADM_UDP_KEEP_ALIVE = "omadm/./3GPP_IMS/UDP_KEEP_ALIVE";
    public static final String OMADM_URI_MEDIA_RSC_SERV_3WAY_CALL = "omadm/./3GPP_IMS/URI_MEDIA_RSC_SERV_3WAY_CALL";
    public static final String OMADM_VCE_CONFIG = "omadm/./3GPP_IMS/VCE_CONFIG";
    public static final String OMADM_VOICE_DOMAIN_PREF_EUTRAN = "omadm/./3GPP_IMS/VOICE_DOMAIN_PREF_EUTRAN";
    public static final String OMADM_VOLTE_ENABLED = "omadm/./3GPP_IMS/VOLTE_ENABLED";
    public static final String OMADM_VOWIFI_ENABLED = "omadm/./3GPP_IMS/SHOW_VOWIFI_REGI_ICON";
    public static final String OMADM_VWF_ENABLED = "omadm/./3GPP_IMS/VWF_ENABLED";
    public static final String OMADM_VZW_TIMS_TIMER = "omadm/./3GPP_IMS/VZW_TIMS_TIMER";
    public static final String ONE_TO_MANY_SELECTED_TECH = "root/application/1/clientControl/1toManySelectedTech";
    public static final String OTHER_CHARACTERISTIC_PATH = "root/application/1/other/";
    public static final String PERSONAL_PROFILE_ADDR = "root/profile/addr";
    public static final String PERSONAL_PROFILE_ADDR_TYPE = "root/profile/addrtype";
    public static final String PLUGINS_CATALOGURI = "root/application/1/messaging/plugins/catalogURI";
    public static final String POST_CALL_AUTH = "root/application/1/services/postCallAuth";
    public static final String PRESENCE_CHARACTERISTIC_PATH = "root/application/1/presence/";
    public static final String PRESENCE_MAX_SUBSCRIPTION_URI = "root/application/1/presence/max-number-ofsubscriptions-inpresence-list";
    public static final String PRESENCE_PUBLISH_TIMER = "root/application/1/presence/PublishTimer";
    public static final String PRESENCE_RLS_URI = "root/application/1/presence/RLS-URI";
    public static final String PRESENCE_THROTTLE_PUBLISH = "root/application/1/presence/source-throttlepublish";
    public static final String PRES_SRV_CAP = "root/application/1/im/pres-srv-cap";
    public static final String PRIVATE_USER_IDENTITY = "root/application/0/private_user_identity";
    public static final String PS_MEDIA = "root/application/1/other/transportproto/psmedia";
    public static final String PS_MEDIA_NEW = "root/application/0/ext/transportproto/psmedia";
    public static final String PS_SIGNALLING = "root/application/1/other/transportproto/pssignalling";
    public static final String PS_SIGNALLING_NEW = "root/application/0/ext/transportproto/pssignalling";
    public static final String PUBLICACCOUNT_ADDR = "root/application/1/publicaccount/Addr";
    public static final String PUBLICACCOUNT_ADDRTYPE = "root/application/1/publicaccount/AddrType";
    public static final String PUBLICACCOUNT_PATH = "root/application/1/publicaccount/";
    public static final String PUBLIC_USER_IDENTITY = "root/application/0/public_user_identity_list/0/public_user_identity";
    public static final String PUBLIC_USER_IDENTITY_LIST = "root/application/0/public_user_identity_list";
    public static final String PUBLIC_USER_IDENTITY_LIST_CON = "public_user_identity";
    public static final String PUBLIC_USER_IDENTITY_NEW = "root/application/0/public_user_identity_list/0/public_user_identities/public_user_identity";
    public static final String Q_VALUE = "root/application/0/ext/q-value";
    public static final String RAW_CONFIG_XML_FILE = "info/raw_config_xml_file";
    public static final String RCS_VIDEO_ENABLED = "root/application/1/services/rcsIPVideoCallAuth";
    public static final String RCS_VOICE_ENABLED = "root/application/1/services/rcsIPVoiceCallAuth";
    public static final String RCS_VOLTE_SINGLE_REGISTRATION = "root/application/0/ext/rcsVolteSingleRegistration";
    public static final String REALM = "root/application/0/appauth/realm";
    public static final String RECONNECT_GUARD_TIMER = "root/application/1/clientControl/reconnectGuardTimer";
    public static final String RECONNECT_GUARD_TIMER_JOYN = "root/application/1/serviceproviderext/joyn/messaging/reconnectGuardTimer";
    public static final String SERVICEPROVIDEREXT_PATH = "root/application/1/serviceproviderext";
    public static final String SERVICE_AVAILABILITY_INFO_EXPIRY = "root/application/1/clientControl/serviceAvailabilityInfoExpiry";
    public static final String SERVICE_CHRACTERISTIC_PATH = "root/application/1/services/";
    public static final String SHARED_MAP_AUTH = "root/application/1/services/sharedMapAuth";
    public static final String SHARED_SKETCH_AUTH = "root/application/1/services/sharedSketchAuth";
    public static final String SLM_CHARACTERISTIC_PATH = "root/application/1/cpm/standalonemsg";
    public static final String SLM_ENABLED = "root/application/1/services/standaloneMsgAuth";
    public static final String SLM_MAX_MSG_SIZE = "root/application/1/cpm/standalonemsg/MaxSizeStandalone";
    public static final String SMS_FALLBACK_AUTH = "root/application/1/im/smsfallbackauth";
    public static final String SOCIAL_PRESENCE_ENABLED = "root/application/1/services/presencePrfl";
    private static final String TAG = "RcsConfigurationReader";
    public static final String TIMER_IDLE = "root/application/1/im/TimerIdle";
    public static final String TRANSPORT_PROTO_CHARACTERISTIC_NEW_PATH = "root/application/0/ext/transportproto/";
    public static final String TRANSPORT_PROTO_CHARACTERISTIC_PATH = "root/application/1/other/transportproto/";
    public static final long UNDEFINED_MAX_SIZE_FILE_TR_INCOMING = -1;
    public static final String USERPWD = "root/application/0/appauth/userpwd";
    public static final String USER_ALIAS_AUTH = "root/application/1/ux/userAliasAuth";
    public static final String USER_NAME = "root/application/0/appauth/UserName";
    public static final String USER_PWD = "root/application/0/appauth/UserPwd";
    public static final String UX_CHARACTERISTIC_PATH = "root/application/1/ux/";
    public static final String VIDEO_AND_ENCALL_UX = "root/application/1/ux/videoAndEnCallUX";
    public static final String VS_ENABLED = "root/application/1/services/vsAuth";
    public static final String WARN_SIZE_IMAGE_SHARE = "root/application/1/other/warnsizeimageshare";
    public static final String WIFI_MEDIA = "root/application/1/other/transportproto/wifimedia";
    public static final String WIFI_MEDIA_NEW = "root/application/0/ext/transportproto/wifimedia";
    public static final String WIFI_SIGNALLING = "root/application/1/other/transportproto/wifisignalling";
    public static final String WIFI_SIGNALLING_NEW = "root/application/0/ext/transportproto/wifisignalling";
    public static final String XCAP_ROOT_URI = "root/application/1/xdms/xcaprooturi";
    public static final String XDMS_PATH = "root/application/1/xdms/";
    private final Context mContext;

    public RcsConfigurationReader(Context context) {
        this.mContext = context;
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x0035  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private String getValue(String str) {
        int columnIndex;
        String string;
        Cursor query = this.mContext.getContentResolver().query(Uri.withAppendedPath(AUTO_CONFIGURATION_URI, str), null, null, null, null);
        if (query != null) {
            try {
                if (query.moveToFirst() && (columnIndex = query.getColumnIndex(str)) != -1) {
                    string = query.getString(columnIndex);
                    if (query != null) {
                        query.close();
                    }
                    return string;
                }
            } catch (Throwable th) {
                try {
                    query.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
        string = null;
        if (query != null) {
        }
        return string;
    }

    public Boolean getBoolean(String str) {
        String value = getValue(str);
        if (value == null) {
            return Boolean.FALSE;
        }
        try {
            return Boolean.valueOf(Boolean.parseBoolean(value));
        } catch (NumberFormatException unused) {
            Log.e(TAG, "Error while parsing integer in parseBoolean() - NumberFormatException");
            return Boolean.FALSE;
        }
    }

    public int getInt(String str) {
        String value = getValue(str);
        if (value != null) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException unused) {
                Log.e(TAG, "Error while parsing integer in getIntValue() - NumberFormatException");
            }
        }
        return -1;
    }

    public long getLong(String str) {
        String value = getValue(str);
        if (value != null) {
            try {
                return Long.parseLong(value);
            } catch (NumberFormatException unused) {
                Log.e(TAG, "Error while parsing integer in parseLong() - NumberFormatException");
            }
        }
        return -1L;
    }

    public String getString(String str) {
        return getValue(str);
    }
}
