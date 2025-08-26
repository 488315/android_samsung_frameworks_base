package com.sec.ims.settings;

import android.content.ContentValues;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SemSystemProperties;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.compose.ui.autofill.PopulateViewStructure_androidKt$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import com.android.keyguard.ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0;
import com.android.systemui.util.SystemUIAnalytics;
import com.samsung.android.feature.SemCarrierFeature;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.accounts.HostAuth;
import com.sec.ims.settings.ImsSettings;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class ImsProfile implements Parcelable, Cloneable {
    public static final int AUDIO_CAPABILITIES_NB_ONLY = 3;
    public static final int AUDIO_CAPABILITIES_NB_PREF = 1;
    public static final int AUDIO_CAPABILITIES_WB_ONLY = 2;
    public static final int AUDIO_CAPABILITIES_WB_PREF = 0;
    public static final int AUDIO_CODEC_BANDWIDTH_EFFICIENT = 0;
    public static final int AUDIO_CODEC_BANDWIDTH_PREF = 2;
    public static final int AUDIO_CODEC_MANUAL = 4;
    public static final int AUDIO_CODEC_OCTET_ALIGNED = 1;
    public static final int AUDIO_CODEC_OCTET_ALIGNED_PREF = 3;
    public static final int AUTOCONFIG_NEEDED = 1;
    public static final int AUTOCONFIG_NEEDED_PARTIALLY = 2;
    public static final int AUTOCONFIG_NOT_NEEDED = 0;
    public static final String CMC_BT_HS_PD_PROFILE = "SamsungCMC_BT_HS_PD";
    public static final String CMC_BT_HS_SD_PROFILE = "SamsungCMC_BT_HS_SD";
    public static final int CMC_BT_HS_TYPE_PRIMARY = 9;
    public static final int CMC_BT_HS_TYPE_SECONDARY = 10;
    public static final String CMC_PD_PROFILE = "SamsungCMC_PD";
    public static final String CMC_SD_PROFILE = "SamsungCMC_SD";
    public static final int CMC_TYPE_NONE = 0;
    public static final int CMC_TYPE_PRIMARY = 1;
    public static final int CMC_TYPE_SECONDARY = 2;
    public static final String CMC_WIFI_HS_PD_PROFILE = "SamsungCMC_WIFI_HS_PD";
    public static final int CMC_WIFI_HS_TYPE_PRIMARY = 5;
    public static final int CMC_WIFI_HS_TYPE_SECONDARY = 6;
    public static final String CMC_WIFI_P2P_PD_PROFILE = "SamsungCMC_WIFI_P2P_PD";
    public static final String CMC_WIFI_P2P_SD_PROFILE = "SamsungCMC_WIFI_P2P_SD";
    public static final int CMC_WIFI_P2P_TYPE_PRIMARY = 7;
    public static final int CMC_WIFI_P2P_TYPE_SECONDARY = 8;
    public static final String CMC_WIFI_PD_PROFILE = "SamsungCMC_WIFI_PD";
    public static final String CMC_WIFI_SD_PROFILE = "SamsungCMC_WIFI_SD";
    public static final int CMC_WIFI_TYPE_PRIMARY = 3;
    public static final int CMC_WIFI_TYPE_SECONDARY = 4;
    public static final int DEFAULT_DEREG_TIMEOUT = 4000;
    public static final int DTMF_CODEC_ENABLED = 0;
    public static final int DTMF_IN_BAND = 1;
    public static final int ENABLE_STATUS_MANUAL = 1;
    public static final int ENABLE_STATUS_OFF = 0;
    public static final int ENABLE_STATUS_ON = 2;
    public static final int GEOLOCATION_IN_PANI = 1;
    public static final int GEOLOCATION_IN_PIDF = 2;
    public static final int GEOLOCATION_IN_PIDF_PUBLISH = 4;
    public static final int GEOLOCATION_IN_PIDF_WITH_CD = 3;
    public static final int IP_TYPE_IPV4 = 1;
    public static final int IP_TYPE_IPV4V6 = 3;
    public static final int IP_TYPE_IPV6 = 2;
    public static final int LOCATION_REQ_EMERGENCY_CALL = 1;
    public static final int LOCATION_REQ_EPDG_AVAILABLE_USER_AGREEMENT = 2;
    public static final int LOCATION_REQ_PERIODIC = 4;
    public static final String LOG_TAG = "ImsProfile";
    public static final int NOTIFY_ALWAYS = 1;
    public static final int NOTIFY_NONE = 0;
    public static final int NOTIFY_REMOTE_NOT_AVAILABLE = 2;
    public static final int PCSCF_PREF_AUTOCONF = 3;
    public static final int PCSCF_PREF_AUTOCONF_IF_RCSONLY = 4;
    public static final int PCSCF_PREF_ISIM = 1;
    public static final int PCSCF_PREF_MANUAL = 2;
    public static final int PCSCF_PREF_OMADM = 5;
    public static final int PCSCF_PREF_PCO = 0;
    public static final String PDN_BT_HS = "p2p-bt";
    public static final String PDN_DEFAULT = "default";
    public static final String PDN_EMERGENCY = "emergency";
    public static final String PDN_IMS = "ims";
    public static final String PDN_INTERNET = "internet";
    public static final String PDN_WIFI = "wifi";
    public static final String PDN_WIFI_DIRECT = "p2p-wlan";
    public static final String PDN_WIFI_HS = "swlan";
    public static final String PDN_XCAP = "xcap";
    public static final int PIDF_INVITE = 4;
    public static final int PIDF_INVITE_RESPONSE = 16;
    public static final int PIDF_LTE = 2;
    public static final int PIDF_MESSAGE = 64;
    public static final int PIDF_REGISTER = 1;
    public static final int PIDF_REINVITE = 8;
    public static final int PIDF_REREGISTER = 2;
    public static final int PIDF_UPDATE = 32;
    public static final int PIDF_WIFI = 1;
    public static final String RCS_CHAT_SERVICE = "chat";
    public static final String RCS_PROFILE_BB = "joyn_blackbird";
    public static final String RCS_PROFILE_CPR = "joyn_cpr";
    public static final String RCS_PROFILE_NAGUIDELINES = "NAGuidelines";
    public static final String RCS_PROFILE_UP = "UP";
    public static final String RCS_PROFILE_UP10 = "UP_1.0";
    public static final String RCS_PROFILE_UP20 = "UP_2.0";
    public static final String RCS_PROFILE_UP2_2 = "UP_2.2";
    public static final String RCS_PROFILE_UP2_3 = "UP_2.3";
    public static final String RCS_PROFILE_UP2_4 = "UP_2.4";
    public static final String RCS_PROFILE_UP2_5 = "UP_2.5";
    public static final String RCS_PROFILE_UP2_PREFIX = "UP_2";
    public static final String RCS_PROFILE_UP_T = "UP_T";
    public static final String RCS_SERVICE = "rcs";
    public static final int REREGI_FORCE_ON_NR = 2;
    public static final int REREGI_OFF = 0;
    public static final int REREGI_OFF_ON_RAT_CHANGE = 1;
    public static final int REREGI_ON = 3;
    public static final String SERVICE_ACCOUNT_AUTH = "scab_account_authenticator";
    public static final String SERVICE_CAB = "cab";
    public static final String SERVICE_CMS = "cms";
    public static final String SERVICE_CONTACT = "contact_tapi";
    public static final int SERVICE_GROUP_NONE = 0;
    public static final int SERVICE_GROUP_RCS = 2;
    public static final int SERVICE_GROUP_VOLTE = 1;
    public static final int SERVICE_GROUP_VOLTE_RCS = 3;
    public static final String SERVICE_HISTORYLOG = "historylog_tapi";
    public static final String SERVICE_MDMI = "mdmi";
    public static final String SERVICE_MMTEL_VOICE = "mmtel";
    public static final String SERVICE_MMTEL_VOICE_VIDEO = "mmtel-video";
    public static final String SERVICE_PRESENCE = "presence";
    public static final String SERVICE_XDM = "xdm";
    private static final int SIPROID_PCSCF_PORT = 9528;
    public static final String TIMER_NAME_1 = "1";
    public static final String TIMER_NAME_2 = "2";
    public static final String TIMER_NAME_4 = "4";
    public static final String TIMER_NAME_A = "A";
    public static final String TIMER_NAME_B = "B";
    public static final String TIMER_NAME_C = "C";
    public static final String TIMER_NAME_D = "D";
    public static final String TIMER_NAME_E = "E";
    public static final String TIMER_NAME_F = "F";
    public static final String TIMER_NAME_G = "G";
    public static final String TIMER_NAME_H = "H";
    public static final String TIMER_NAME_I = "I";
    public static final String TIMER_NAME_J = "J";
    public static final String TIMER_NAME_K = "K";
    public static final int TRANSPORT_TCP = 3;
    public static final int TRANSPORT_TLS = 4;
    public static final int TRANSPORT_UDP = 2;
    public static final int TRANSPORT_UDP_PREFERRED = 1;
    public static final int TTY_TYPE_CS = 1;
    public static final int TTY_TYPE_CS_RTT = 3;
    public static final int TTY_TYPE_NONE = 0;
    public static final int TTY_TYPE_PS = 2;
    public static final int TTY_TYPE_PS_RTT = 4;
    public static final int VCRBT_DTMF = 4;
    public static final int VCRBT_MO = 1;
    public static final int VCRBT_MT = 2;
    public static final int VCRBT_NONE = 0;
    public static final String VOLTE_SERVICE = "volte";
    private JSONObject mBody;
    public static final String SERVICE_MMTEL_CALL_COMPOSER = "mmtel-call-composer";
    public static final String SERVICE_SMSIP = "smsip";
    public static final String SERVICE_SS = "ss";
    public static final String SERVICE_CDPN = "cdpn";
    public static final String SERVICE_DATACHANNEL = "datachannel";
    protected static final String[] volteServices = {"mmtel", "mmtel-video", SERVICE_MMTEL_CALL_COMPOSER, SERVICE_SMSIP, SERVICE_SS, SERVICE_CDPN, SERVICE_DATACHANNEL};
    public static final String SERVICE_OPTIONS = "options";
    public static final String SERVICE_IM = "im";
    public static final String SERVICE_FT = "ft";
    public static final String SERVICE_FT_HTTP = "ft_http";
    public static final String SERVICE_SLM = "slm";
    public static final String SERVICE_IS = "is";
    public static final String SERVICE_VS = "vs";
    public static final String SERVICE_EUC = "euc";
    public static final String SERVICE_GLS = "gls";
    public static final String SERVICE_PROFILE = "profile";
    public static final String SERVICE_EC = "ec";
    public static final String SERVICE_CHATBOT_COMMUNICATION = "chatbot-communication";
    public static final String SERVICE_PLUG_IN = "plug-in";
    public static final String SERVICE_LASTSEEN = "lastseen";
    protected static final String[] rcsServices = {SERVICE_OPTIONS, "presence", SERVICE_IM, SERVICE_FT, SERVICE_FT_HTTP, SERVICE_SLM, SERVICE_IS, SERVICE_VS, SERVICE_EUC, SERVICE_GLS, SERVICE_PROFILE, SERVICE_EC, SERVICE_CHATBOT_COMMUNICATION, SERVICE_PLUG_IN, SERVICE_LASTSEEN};
    public static final String SERVICE_FT_TAPI = "ft_tapi";
    public static final String SERVICE_ISH = "ish_tapi";
    public static final String SERVICE_VSH = "vsh_tapi";
    public static final String SERVICE_CAPABILITY = "capability_tapi";
    public static final String SERVICE_CHAT = "chat_tapi";
    public static final String SERVICE_FILEUPLOAD = "fileupload_tapi";
    public static final String SERVICE_GLS_TAPI = "gls_tapi";
    public static final String SERVICE_MULTIMEDIASESSION = "multimediasession_tapi";
    protected static final String[] tapiServices = {SERVICE_FT_TAPI, SERVICE_ISH, SERVICE_VSH, SERVICE_CAPABILITY, SERVICE_CHAT, SERVICE_FILEUPLOAD, SERVICE_GLS_TAPI, SERVICE_MULTIMEDIASESSION};
    protected static final String[] chatServices = {SERVICE_IM, SERVICE_FT, SERVICE_SLM, SERVICE_FT_HTTP, SERVICE_CHATBOT_COMMUNICATION, SERVICE_PLUG_IN, SERVICE_GLS};
    public static final Parcelable.Creator<ImsProfile> CREATOR = new Parcelable.Creator<ImsProfile>() { // from class: com.sec.ims.settings.ImsProfile.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ImsProfile createFromParcel(Parcel parcel) {
            return new ImsProfile(parcel, 0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ImsProfile[] newArray(int i) {
            return new ImsProfile[i];
        }
    };

    public enum PROFILE_TYPE {
        EMERGENCY,
        VOLTE,
        RCS,
        CHAT
    }

    public enum RCS_PROFILE {
        UNKNOWN,
        JOYN_BB,
        JOYN_CPR,
        UP_T,
        UP_1_0,
        UP_2_0,
        UP_2_2,
        UP_2_3,
        UP_2_4,
        UP_2_5;

        public static int getProfileType(String str) {
            if (TextUtils.isEmpty(str)) {
                return UNKNOWN.ordinal();
            }
            str.getClass();
            switch (str) {
            }
            return UNKNOWN.ordinal();
        }
    }

    public /* synthetic */ ImsProfile(Parcel parcel, int i) {
        this(parcel);
    }

    private void fromJson(String str) {
        try {
            this.mBody = new JSONObject(str);
            splitNetwork();
        } catch (IllegalArgumentException | JSONException e) {
            this.mBody = new JSONObject();
            e.printStackTrace();
        }
    }

    public static String[] getAllNetworkNameSet() {
        ArrayList arrayList = new ArrayList();
        for (NETWORK_TYPE network_type : NETWORK_TYPE.values()) {
            if (!network_type.isOneOf(NETWORK_TYPE.UNKNOWN, NETWORK_TYPE.ALL)) {
                arrayList.add(network_type.toString());
            }
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    public static String[] getChatServiceList() {
        return chatServices;
    }

    private JSONObject getNetwork(int i) {
        return getNetwork(getNetworkName(i));
    }

    public static String getNetworkName(int i) {
        return NETWORK_TYPE.from(i).toString();
    }

    public static int getNetworkType(NETWORK_TYPE network_type) {
        return network_type.mType;
    }

    public static String[] getRcsServiceList() {
        return rcsServices;
    }

    public static String[] getTapiServiceList() {
        return tapiServices;
    }

    private int getTimer(String str) {
        Map<String, Integer> timerMap = getTimerMap();
        if (timerMap.containsKey(str)) {
            return timerMap.get(str).intValue();
        }
        return 0;
    }

    private Map<String, Integer> getTimerMap() {
        ArrayMap arrayMap = new ArrayMap();
        for (String str : TextUtils.split(getAsString("timer"), ",")) {
            String[] strArrSplit = TextUtils.split(str, ":");
            if (strArrSplit.length == 2) {
                arrayMap.put(strArrSplit[0], Integer.valueOf(strArrSplit[1]));
            }
        }
        return arrayMap;
    }

    public static String[] getVoLteServiceList() {
        return volteServices;
    }

    public static boolean hasChatService(ImsProfile imsProfile) {
        return hasChatService(imsProfile, NETWORK_TYPE.ALL);
    }

    public static boolean hasRcsService(ImsProfile imsProfile) {
        return hasRcsService(imsProfile, NETWORK_TYPE.ALL);
    }

    public static boolean hasVolteService(ImsProfile imsProfile) {
        return hasVolteService(imsProfile, NETWORK_TYPE.ALL);
    }

    public static boolean isRcsService(String str) {
        for (String str2 : getRcsServiceList()) {
            if (str2.equals(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isRcsUp10Profile(String str) {
        return !TextUtils.isEmpty(str) && str.startsWith(RCS_PROFILE_UP10);
    }

    public static boolean isRcsUp23AndUp24Profile(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return str.startsWith(RCS_PROFILE_UP2_3) || str.startsWith(RCS_PROFILE_UP2_4);
    }

    public static boolean isRcsUp24Profile(String str) {
        return !TextUtils.isEmpty(str) && str.startsWith(RCS_PROFILE_UP2_4);
    }

    public static boolean isRcsUp2Profile(String str) {
        return !TextUtils.isEmpty(str) && str.startsWith(RCS_PROFILE_UP2_PREFIX);
    }

    public static boolean isRcsUpProfile(String str) {
        return !TextUtils.isEmpty(str) && str.startsWith(RCS_PROFILE_UP);
    }

    public static boolean isRcsUpTransitionProfile(String str) {
        return !TextUtils.isEmpty(str) && str.startsWith(RCS_PROFILE_UP_T);
    }

    private boolean isRttSupported(int i) {
        boolean z = SemCarrierFeature.getInstance().getBoolean(i, "CarrierFeature_VoiceCall_SupportRTT", false, false);
        String str = SemSystemProperties.get("ro.boot.carrierid", "DEFAULT");
        Log.i(LOG_TAG, "carrierId : " + str + " isRttSupportByCallApp " + z);
        return z || "EUX".equals(str) || "EUY".equals(str) || "EEX".equals(str) || "EEY".equals(str) || str.equals("SEK");
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:65:0x0137. Please report as an issue. */
    public static String trimAudioCodec(String str, String str2, String str3, String str4, int i) throws NumberFormatException {
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        String strM;
        String str5;
        char c;
        String str6;
        String strM2;
        String str7;
        String str8 = "DTMFWB";
        String str9 = LOG_TAG;
        try {
            int i7 = Integer.parseInt(str2);
            int i8 = Integer.parseInt(str3);
            i5 = Integer.parseInt(str4);
            i4 = i8;
            i3 = i7;
            i2 = i;
        } catch (NumberFormatException unused) {
            Log.e(LOG_TAG, "trimAudioCodec: Invalid values. Use default.");
            i2 = 0;
            i3 = 2;
            i4 = 0;
            i5 = 0;
        }
        StringBuilder sbM = MutableObjectList$$ExternalSyntheticOutline0.m(i3, i4, "trimAudioCodec : audioCodecMode=", " audioCapabilities=", " dtmfCodecMode=");
        sbM.append(i5);
        sbM.append(" isEnableEvs=");
        sbM.append(i2);
        Log.i(LOG_TAG, sbM.toString());
        StringTokenizer stringTokenizer = new StringTokenizer(str, ",");
        String str10 = "";
        String str11 = "";
        String str12 = str11;
        String str13 = str12;
        String str14 = str13;
        int i9 = i2;
        int i10 = i3;
        int i11 = i4;
        int i12 = i5;
        String strM3 = str14;
        String str15 = strM3;
        String str16 = str15;
        String str17 = str16;
        while (stringTokenizer.hasMoreElements()) {
            String str18 = str9;
            String upperCase = stringTokenizer.nextToken().toUpperCase();
            upperCase.getClass();
            char c2 = 65535;
            switch (upperCase.hashCode()) {
                case -652494161:
                    str5 = str15;
                    if (upperCase.equals("AMRBE-WB")) {
                        c2 = 0;
                        break;
                    }
                    break;
                case -159196944:
                    str5 = str15;
                    if (upperCase.equals("AMROPEN")) {
                        c2 = 1;
                        break;
                    }
                    break;
                case 64934:
                    str5 = str15;
                    if (upperCase.equals("AMR")) {
                        c2 = 2;
                        break;
                    }
                    break;
                case 69058:
                    str5 = str15;
                    if (upperCase.equals("EVS")) {
                        c2 = 3;
                        break;
                    }
                    break;
                case 2108969:
                    str5 = str15;
                    if (upperCase.equals("DTMF")) {
                        c = 4;
                        c2 = c;
                        break;
                    }
                    break;
                case 62403689:
                    str5 = str15;
                    if (upperCase.equals("AMRBE")) {
                        c = 5;
                        c2 = c;
                        break;
                    }
                    break;
                case 1934494802:
                    str5 = str15;
                    if (upperCase.equals("AMR-WB")) {
                        c = 6;
                        c2 = c;
                        break;
                    }
                    break;
                case 2026721972:
                    str5 = str15;
                    if (upperCase.equals(str8)) {
                        c = 7;
                        c2 = c;
                        break;
                    }
                    break;
                case 2057400237:
                    str5 = str15;
                    if (upperCase.equals("EVS_A1")) {
                        c = '\b';
                        c2 = c;
                        break;
                    }
                    break;
                case 2057400238:
                    str5 = str15;
                    if (upperCase.equals("EVS_A2")) {
                        c = '\t';
                        c2 = c;
                        break;
                    }
                    break;
                case 2057400267:
                    str5 = str15;
                    if (upperCase.equals("EVS_B0")) {
                        c = '\n';
                        c2 = c;
                        break;
                    }
                    break;
                case 2057400268:
                    str5 = str15;
                    if (upperCase.equals("EVS_B1")) {
                        c = 11;
                        c2 = c;
                        break;
                    }
                    break;
                case 2057400269:
                    str5 = str15;
                    if (upperCase.equals("EVS_B2")) {
                        c = '\f';
                        c2 = c;
                        break;
                    }
                    break;
                default:
                    str5 = str15;
                    break;
            }
            switch (c2) {
                case 0:
                    str6 = str8;
                    strM2 = str11;
                    str13 = "AMRBE-WB";
                    str15 = str5;
                    break;
                case 1:
                    str6 = str8;
                    strM2 = str11;
                    str14 = "AMROPEN";
                    str15 = str5;
                    break;
                case 2:
                    str6 = str8;
                    strM2 = str11;
                    str17 = "AMR";
                    str15 = str5;
                    break;
                case 3:
                case '\b':
                case '\t':
                case '\n':
                case 11:
                case '\f':
                    StringBuilder sbM2 = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(str11);
                    if (TextUtils.isEmpty(str11)) {
                        str6 = str8;
                        str7 = str11;
                    } else {
                        str6 = str8;
                        str7 = ",";
                    }
                    sbM2.append(str7);
                    strM2 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(sbM2.toString(), upperCase);
                    str15 = str5;
                    break;
                case 4:
                    str6 = str8;
                    str15 = "DTMF";
                    strM2 = str11;
                    break;
                case 5:
                    str6 = str8;
                    str12 = "AMRBE";
                    str15 = str5;
                    strM2 = str11;
                    break;
                case 6:
                    str6 = str8;
                    str10 = "AMR-WB";
                    str15 = str5;
                    strM2 = str11;
                    break;
                case 7:
                    str16 = str8;
                    str6 = str16;
                    str15 = str5;
                    strM2 = str11;
                    break;
                default:
                    strM3 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(strM3, ",", upperCase);
                    str6 = str8;
                    str15 = str5;
                    strM2 = str11;
                    break;
            }
            str11 = strM2;
            str8 = str6;
            str9 = str18;
        }
        String str19 = str9;
        String str20 = str11;
        StringBuilder sbM3 = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("trimAudioCodec : EVS=", str20, " AMRBE_WB=", str13, " AMRBE=");
        MoveResult$$ExternalSyntheticOutline0.m(sbM3, str12, " AMR-WB=", str10, " AMR=");
        MoveResult$$ExternalSyntheticOutline0.m(sbM3, str17, " DTMFWB=", str16, " DTMF=");
        sbM3.append(str15);
        sbM3.append(" OTHERS=");
        sbM3.append(strM3);
        Log.i(str19, sbM3.toString());
        if (i9 != 1 || TextUtils.isEmpty(str20)) {
            str20 = str11;
        }
        if (i10 == 0) {
            i6 = i11;
            if (i6 == 1) {
                StringBuilder sbM4 = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(str20);
                sbM4.append((TextUtils.isEmpty(str20) || TextUtils.isEmpty(str12)) ? str11 : ",");
                String strM4 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(sbM4.toString(), str12);
                StringBuilder sbM5 = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(strM4);
                sbM5.append((TextUtils.isEmpty(strM4) || TextUtils.isEmpty(str13)) ? str11 : ",");
                strM = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(sbM5.toString(), str13);
            } else if (i6 == 2) {
                StringBuilder sbM6 = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(str20);
                sbM6.append((TextUtils.isEmpty(str20) || TextUtils.isEmpty(str13)) ? str11 : ",");
                strM = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(sbM6.toString(), str13);
            } else if (i6 != 3) {
                StringBuilder sbM7 = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(str20);
                sbM7.append((TextUtils.isEmpty(str20) || TextUtils.isEmpty(str13)) ? str11 : ",");
                String strM5 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(sbM7.toString(), str13);
                StringBuilder sbM8 = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(strM5);
                sbM8.append((TextUtils.isEmpty(strM5) || TextUtils.isEmpty(str12)) ? str11 : ",");
                strM = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(sbM8.toString(), str12);
            } else {
                StringBuilder sbM9 = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(str20);
                sbM9.append((TextUtils.isEmpty(str20) || TextUtils.isEmpty(str12)) ? str11 : ",");
                strM = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(sbM9.toString(), str12);
            }
        } else if (i10 == 1) {
            i6 = i11;
            if (i6 == 1) {
                StringBuilder sbM10 = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(str20);
                sbM10.append((TextUtils.isEmpty(str20) || TextUtils.isEmpty(str17)) ? str11 : ",");
                String strM6 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(sbM10.toString(), str17);
                StringBuilder sbM11 = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(strM6);
                sbM11.append((TextUtils.isEmpty(strM6) || TextUtils.isEmpty(str10)) ? str11 : ",");
                strM = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(sbM11.toString(), str10);
            } else if (i6 == 2) {
                StringBuilder sbM12 = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(str20);
                sbM12.append((TextUtils.isEmpty(str20) || TextUtils.isEmpty(str10)) ? str11 : ",");
                strM = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(sbM12.toString(), str10);
            } else if (i6 != 3) {
                StringBuilder sbM13 = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(str20);
                sbM13.append((TextUtils.isEmpty(str20) || TextUtils.isEmpty(str10)) ? str11 : ",");
                String strM7 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(sbM13.toString(), str10);
                StringBuilder sbM14 = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(strM7);
                sbM14.append((TextUtils.isEmpty(strM7) || TextUtils.isEmpty(str17)) ? str11 : ",");
                strM = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(sbM14.toString(), str17);
            } else {
                StringBuilder sbM15 = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(str20);
                sbM15.append((TextUtils.isEmpty(str20) || TextUtils.isEmpty(str17)) ? str11 : ",");
                strM = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(sbM15.toString(), str17);
            }
        } else if (i10 != 3) {
            i6 = i11;
            if (i6 == 1) {
                StringBuilder sbM16 = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(str20);
                sbM16.append((TextUtils.isEmpty(str20) || TextUtils.isEmpty(str12)) ? str11 : ",");
                String strM8 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(sbM16.toString(), str12);
                StringBuilder sbM17 = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(strM8);
                sbM17.append((TextUtils.isEmpty(strM8) || TextUtils.isEmpty(str17)) ? str11 : ",");
                String strM9 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(sbM17.toString(), str17);
                StringBuilder sbM18 = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(strM9);
                sbM18.append((TextUtils.isEmpty(strM9) || TextUtils.isEmpty(str13)) ? str11 : ",");
                String strM10 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(sbM18.toString(), str13);
                StringBuilder sbM19 = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(strM10);
                sbM19.append((TextUtils.isEmpty(strM10) || TextUtils.isEmpty(str10)) ? str11 : ",");
                strM = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(sbM19.toString(), str10);
            } else if (i6 == 2) {
                StringBuilder sbM20 = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(str20);
                sbM20.append((TextUtils.isEmpty(str20) || TextUtils.isEmpty(str13)) ? str11 : ",");
                String strM11 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(sbM20.toString(), str13);
                StringBuilder sbM21 = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(strM11);
                sbM21.append((TextUtils.isEmpty(strM11) || TextUtils.isEmpty(str10)) ? str11 : ",");
                strM = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(sbM21.toString(), str10);
            } else if (i6 != 3) {
                StringBuilder sbM22 = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(str20);
                sbM22.append((TextUtils.isEmpty(str20) || TextUtils.isEmpty(str13)) ? str11 : ",");
                String strM12 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(sbM22.toString(), str13);
                StringBuilder sbM23 = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(strM12);
                sbM23.append((TextUtils.isEmpty(strM12) || TextUtils.isEmpty(str10)) ? str11 : ",");
                String strM13 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(sbM23.toString(), str10);
                StringBuilder sbM24 = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(strM13);
                sbM24.append((TextUtils.isEmpty(strM13) || TextUtils.isEmpty(str12)) ? str11 : ",");
                String strM14 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(sbM24.toString(), str12);
                StringBuilder sbM25 = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(strM14);
                sbM25.append((TextUtils.isEmpty(strM14) || TextUtils.isEmpty(str17)) ? str11 : ",");
                strM = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(sbM25.toString(), str17);
            } else {
                StringBuilder sbM26 = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(str20);
                sbM26.append((TextUtils.isEmpty(str20) || TextUtils.isEmpty(str12)) ? str11 : ",");
                String strM15 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(sbM26.toString(), str12);
                StringBuilder sbM27 = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(strM15);
                sbM27.append((TextUtils.isEmpty(strM15) || TextUtils.isEmpty(str17)) ? str11 : ",");
                strM = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(sbM27.toString(), str17);
            }
        } else {
            i6 = i11;
            if (i6 == 1) {
                StringBuilder sbM28 = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(str20);
                sbM28.append((TextUtils.isEmpty(str20) || TextUtils.isEmpty(str17)) ? str11 : ",");
                String strM16 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(sbM28.toString(), str17);
                StringBuilder sbM29 = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(strM16);
                sbM29.append((TextUtils.isEmpty(strM16) || TextUtils.isEmpty(str12)) ? str11 : ",");
                String strM17 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(sbM29.toString(), str12);
                StringBuilder sbM30 = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(strM17);
                sbM30.append((TextUtils.isEmpty(strM17) || TextUtils.isEmpty(str10)) ? str11 : ",");
                String strM18 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(sbM30.toString(), str10);
                StringBuilder sbM31 = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(strM18);
                sbM31.append((TextUtils.isEmpty(strM18) || TextUtils.isEmpty(str13)) ? str11 : ",");
                strM = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(sbM31.toString(), str13);
            } else if (i6 == 2) {
                StringBuilder sbM32 = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(str20);
                sbM32.append((TextUtils.isEmpty(str20) || TextUtils.isEmpty(str10)) ? str11 : ",");
                String strM19 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(sbM32.toString(), str10);
                StringBuilder sbM33 = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(strM19);
                sbM33.append((TextUtils.isEmpty(strM19) || TextUtils.isEmpty(str13)) ? str11 : ",");
                strM = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(sbM33.toString(), str13);
            } else if (i6 != 3) {
                StringBuilder sbM34 = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(str20);
                sbM34.append((TextUtils.isEmpty(str20) || TextUtils.isEmpty(str10)) ? str11 : ",");
                String strM20 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(sbM34.toString(), str10);
                StringBuilder sbM35 = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(strM20);
                sbM35.append((TextUtils.isEmpty(strM20) || TextUtils.isEmpty(str13)) ? str11 : ",");
                String strM21 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(sbM35.toString(), str13);
                StringBuilder sbM36 = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(strM21);
                sbM36.append((TextUtils.isEmpty(strM21) || TextUtils.isEmpty(str17)) ? str11 : ",");
                String strM22 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(sbM36.toString(), str17);
                StringBuilder sbM37 = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(strM22);
                sbM37.append((TextUtils.isEmpty(strM22) || TextUtils.isEmpty(str12)) ? str11 : ",");
                strM = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(sbM37.toString(), str12);
            } else {
                StringBuilder sbM38 = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(str20);
                sbM38.append((TextUtils.isEmpty(str20) || TextUtils.isEmpty(str17)) ? str11 : ",");
                String strM23 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(sbM38.toString(), str17);
                StringBuilder sbM39 = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(strM23);
                sbM39.append((TextUtils.isEmpty(strM23) || TextUtils.isEmpty(str12)) ? str11 : ",");
                strM = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(sbM39.toString(), str12);
            }
        }
        if (!TextUtils.isEmpty(strM) && !TextUtils.isEmpty(str14)) {
            Log.d(str19, "Add AMROPEN");
            strM = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(strM + ",", str14);
        }
        if (i12 != 0) {
            Log.i(str19, "trimAudioCodec : DTMF is disabled");
        } else if (i6 == 1) {
            StringBuilder sbM40 = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(strM);
            sbM40.append((TextUtils.isEmpty(strM) || TextUtils.isEmpty(str15)) ? str11 : ",");
            String strM24 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(sbM40.toString(), str15);
            StringBuilder sbM41 = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(strM24);
            sbM41.append((TextUtils.isEmpty(strM24) || TextUtils.isEmpty(str16)) ? str11 : ",");
            strM = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(sbM41.toString(), str16);
        } else if (i6 == 2) {
            StringBuilder sbM42 = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(strM);
            sbM42.append((TextUtils.isEmpty(strM) || TextUtils.isEmpty(str16)) ? str11 : ",");
            strM = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(sbM42.toString(), str16);
        } else if (i6 != 3) {
            StringBuilder sbM43 = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(strM);
            sbM43.append((TextUtils.isEmpty(strM) || TextUtils.isEmpty(str16)) ? str11 : ",");
            String strM25 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(sbM43.toString(), str16);
            StringBuilder sbM44 = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(strM25);
            sbM44.append((TextUtils.isEmpty(strM25) || TextUtils.isEmpty(str15)) ? str11 : ",");
            strM = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(sbM44.toString(), str15);
        } else {
            StringBuilder sbM45 = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(strM);
            sbM45.append((TextUtils.isEmpty(strM) || TextUtils.isEmpty(str15)) ? str11 : ",");
            strM = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(sbM45.toString(), str15);
        }
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(strM, strM3);
    }

    public void addImpu(String str) throws JSONException {
        ArrayList arrayList = new ArrayList(getImpuList());
        arrayList.add(str);
        setImpuList(TextUtils.join(",", arrayList));
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String dump() {
        return toJson();
    }

    public void enable(int i) throws JSONException {
        put("enabled", Integer.valueOf(i));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return getAsContentValues().equals(((ImsProfile) obj).getAsContentValues());
    }

    public int get100tryingTimer() {
        return getAsInteger("timer_100trying").intValue();
    }

    public List<String> getAcb() {
        return getAsStringList("acb");
    }

    public String getAccessToken() {
        return getAsString("accessToken");
    }

    public boolean getAddHistinfo() {
        return getAsBoolean("add_histinfo").booleanValue();
    }

    public Map<Integer, Set<String>> getAllServiceSet() {
        ArrayMap arrayMap = new ArrayMap();
        JSONArray jSONArrayOptJSONArray = this.mBody.optJSONArray("network");
        if (jSONArrayOptJSONArray != null) {
            for (int i = 0; i < jSONArrayOptJSONArray.length(); i++) {
                JSONObject jSONObjectOptJSONObject = jSONArrayOptJSONArray.optJSONObject(i);
                JSONArray jSONArrayOptJSONArray2 = jSONObjectOptJSONObject.optJSONArray("services");
                if (jSONArrayOptJSONArray2 == null) {
                    Log.e(LOG_TAG, "getAllServiceSet: No services array in " + jSONObjectOptJSONObject.toString());
                } else {
                    ArraySet arraySet = new ArraySet();
                    for (int i2 = 0; i2 < jSONArrayOptJSONArray2.length(); i2++) {
                        arraySet.add(jSONArrayOptJSONArray2.optString(i2));
                    }
                    arrayMap.put(Integer.valueOf(getNetworkType(jSONObjectOptJSONObject.optString("type"))), arraySet);
                }
            }
        }
        return arrayMap;
    }

    public Set<String> getAllServiceSetFromAllNetwork() {
        ArraySet arraySet = new ArraySet();
        Iterator<Set<String>> it = getAllServiceSet().values().iterator();
        while (it.hasNext()) {
            arraySet.addAll(it.next());
        }
        return arraySet;
    }

    public String getAmrnbMode() {
        return getAsString("amrnb_mode");
    }

    public int getAmrnbbeMaxRed() {
        return getAsInteger("amrnbbe_max_red").intValue();
    }

    public int getAmrnbbePayload() {
        return getAsInteger("amrnbbe_payload").intValue();
    }

    public int getAmrnboaMaxRed() {
        return getAsInteger("amrnboa_max_red").intValue();
    }

    public int getAmrnboaPayload() {
        return getAsInteger("amrnboa_payload").intValue();
    }

    public int getAmropenPayload() {
        return getAsInteger("amropen_payload").intValue();
    }

    public String getAmrwbMode() {
        return getAsString("amrwb_mode");
    }

    public int getAmrwbbeMaxRed() {
        return getAsInteger("amrwbbe_max_red").intValue();
    }

    public int getAmrwbbePayload() {
        return getAsInteger("amrwbbe_payload").intValue();
    }

    public int getAmrwboaMaxRed() {
        return getAsInteger("amrwboa_max_red").intValue();
    }

    public int getAmrwboaPayload() {
        return getAsInteger("amrwboa_payload").intValue();
    }

    public String getAppId() {
        return getAsString("app_id");
    }

    public Boolean getAsBoolean(String str) {
        return Boolean.valueOf(this.mBody.optBoolean(str));
    }

    public ContentValues getAsContentValues() {
        ContentValues contentValues = new ContentValues();
        Iterator<String> itKeys = this.mBody.keys();
        while (itKeys.hasNext()) {
            String next = itKeys.next();
            Object objOpt = this.mBody.opt(next);
            if (objOpt instanceof Integer) {
                contentValues.put(next, (Integer) objOpt);
            } else if (objOpt instanceof Boolean) {
                contentValues.put(next, (Boolean) objOpt);
            } else if (objOpt instanceof String) {
                contentValues.put(next, (String) objOpt);
            } else if (objOpt instanceof JSONArray) {
                contentValues.put(next, objOpt.toString());
            }
        }
        return contentValues;
    }

    public Integer getAsInteger(String str) {
        return Integer.valueOf(this.mBody.optInt(str));
    }

    public List<JSONObject> getAsJSONObjectList(String str) {
        ArrayList arrayList = new ArrayList();
        JSONArray jSONArrayOptJSONArray = this.mBody.optJSONArray(str);
        if (jSONArrayOptJSONArray != null) {
            for (int i = 0; i < jSONArrayOptJSONArray.length(); i++) {
                arrayList.add(jSONArrayOptJSONArray.optJSONObject(i));
            }
        }
        return arrayList;
    }

    public String getAsString(String str) {
        return this.mBody.optString(str);
    }

    public List<String> getAsStringList(String str) {
        ArrayList arrayList = new ArrayList();
        JSONArray jSONArrayOptJSONArray = this.mBody.optJSONArray(str);
        if (jSONArrayOptJSONArray != null) {
            for (int i = 0; i < jSONArrayOptJSONArray.length(); i++) {
                arrayList.add(jSONArrayOptJSONArray.optString(i));
            }
        }
        return arrayList;
    }

    public int getAudioAS() {
        return getAsInteger("audio_as").intValue();
    }

    public int getAudioAvpf() {
        return getAsInteger("audio_avpf").intValue();
    }

    public String getAudioCapabilities() {
        return getAsString("audio_capabilities");
    }

    public String getAudioCodec() {
        return trimAudioCodec(getAsString("audio_codec"), getAudioCodecMode(), getAudioCapabilities(), getDtmfCodecMode(), getEnableEvsCodec());
    }

    public String getAudioCodecMode() {
        return getAsString("audio_codec_mode");
    }

    public int getAudioDscp() {
        return getAsInteger("audio_dscp").intValue();
    }

    public int getAudioPortEnd() {
        return getAsInteger("audio_port_end").intValue();
    }

    public int getAudioPortStart() {
        return getAsInteger("audio_port_start").intValue();
    }

    public int getAudioRR() {
        return getAsInteger("audio_rr").intValue();
    }

    public int getAudioRS() {
        return getAsInteger("audio_rs").intValue();
    }

    public int getAudioRtcpXr() {
        return getAsInteger("audio_rtcpxr").intValue();
    }

    public int getAudioSrtp() {
        return getAsInteger("audio_srtp").intValue();
    }

    public String getAuthAlgorithm() {
        return getAsString("auth_algo");
    }

    public String getAuthName() {
        return getAsString("authname");
    }

    public int getAvailCacheExpiry() {
        return getAsInteger("avail_cache_exp").intValue();
    }

    public int getBadEventExpiry() {
        return getAsInteger("bad_event_expiry").intValue();
    }

    public boolean getBlockDeregiOnSrvcc() {
        return getAsBoolean("block_deregi_on_srvcc").booleanValue();
    }

    public int getCapCacheExp() {
        return getAsInteger("cap_cache_exp").intValue();
    }

    public int getCapPollInterval() {
        return getAsInteger("cap_poll_interval").intValue();
    }

    public List<String> getCmcExtendedContentFeaturesList() {
        return getAsStringList("cmc_ext_content_features_list");
    }

    public int getCmcType() {
        String name = getName();
        if (name == null) {
            return 0;
        }
        switch (name) {
            case "SamsungCMC_PD":
                return 1;
            case "SamsungCMC_SD":
                return 2;
            case "SamsungCMC_WIFI_PD":
                return 3;
            case "SamsungCMC_WIFI_SD":
                return 4;
            case "SamsungCMC_WIFI_P2P_PD":
                return 7;
            case "SamsungCMC_WIFI_P2P_SD":
                return 8;
            case "SamsungCMC_WIFI_HS_PD":
                return 5;
            case "SamsungCMC_BT_HS_PD":
                return 9;
            case "SamsungCMC_BT_HS_SD":
                return 10;
            default:
                return 0;
        }
    }

    public String getConferenceDialogType() {
        return getAsString("conference_dialog_type");
    }

    public String getConferenceReferUriAsserted() {
        return getAsString("conference_referuri_asserted");
    }

    public String getConferenceReferUriType() {
        return getAsString("conference_referuri_type");
    }

    public String getConferenceRemoveReferUriType() {
        return getAsString("conference_remove_referuri_type");
    }

    public String getConferenceSubscribe() {
        return getAsString("conference_subscribe");
    }

    public boolean getConferenceSupportPrematureEnd() {
        return getAsBoolean("conference_support_premature_end").booleanValue();
    }

    public String getConferenceUri() {
        return getAsString("conference_uri");
    }

    public int getConferenceUriMccmncType() {
        return getAsInteger("conference_uri_mccmnc_type").intValue();
    }

    public String getConferenceUseAnonymousUpdate() {
        return getAsString("conference_use_anonymous_update");
    }

    public int getConfidenceLevel() {
        return getAsInteger("confidence_level").intValue();
    }

    public int getControlDscp() {
        return getAsInteger("control_dscp").intValue();
    }

    public int getDbrTimer() {
        return getAsInteger("dbr_timer").intValue();
    }

    public String getDefaultMcc() {
        return getMcc();
    }

    public String getDefaultMnc() {
        return getMnc();
    }

    public boolean getDelayPcscfChangeDuringCall() {
        return getAsBoolean("delay_pcscf_change_during_call").booleanValue();
    }

    public int getDeregTimeout(int i) {
        JSONObject network = getNetwork(i);
        return network != null ? network.optInt("dereg_timeout", DEFAULT_DEREG_TIMEOUT) : DEFAULT_DEREG_TIMEOUT;
    }

    public boolean getDisallowReregi() {
        return getAsBoolean("disallow_reregi").booleanValue();
    }

    public String getDisplayFormat() {
        return getAsString("display_format");
    }

    public String getDisplayFormatHevc() {
        return getAsString("display_format_hevc");
    }

    public String getDisplayName() {
        return getAsString("display_name");
    }

    public int getDmPollingPeriod() {
        return getAsInteger("dm_polling_period").intValue();
    }

    public String getDomain() {
        return getAsString("domain");
    }

    public String getDtmfCodecMode() {
        return getAsString("dtmf_codec_mode");
    }

    public int getDtmfMode() {
        return getAsInteger("dtmf_mode").intValue();
    }

    public int getDtmfNbPayload() {
        return getAsInteger("dtmf_nb_payload").intValue();
    }

    public int getDtmfWbPayload() {
        return getAsInteger("dtmf_wb_payload").intValue();
    }

    public String getDuid() {
        return getAsString("duid");
    }

    public int getE911InviteTo18x() {
        return getAsInteger("t_e911_invite_to_18x").intValue();
    }

    public int getE911PdnSelectionVowifi() {
        return getAsInteger("e911_pdn_selection_vowifi").intValue();
    }

    public int getE911PermFail() {
        return getAsInteger("e911_perm_fail").intValue();
    }

    public int getE911RegiTime() {
        return getAsInteger("t_e911_regi").intValue();
    }

    public int getEarlyMediaRtpTimeoutTimer() {
        return getAsInteger("early_media_rtp_timeout_timer").intValue();
    }

    public boolean getEcallCsfbWithoutActionTag() {
        return getAsBoolean("ecall_csfb_without_action_tag").booleanValue();
    }

    public boolean getEctNoHoldForActiveCall() {
        return getAsBoolean("ect_no_hold_for_active_call").booleanValue();
    }

    public int getEmm() {
        return getAsInteger("emm").intValue();
    }

    public boolean getEnableAvSync() {
        return getAsBoolean("enable_av_sync").booleanValue();
    }

    public int getEnableEvsCodec() {
        if (getAsBoolean("gcf_vonr_mode").booleanValue()) {
            Log.i(LOG_TAG, "getEnableEvsCodec(enable): force enable EVS for GCF VoNR mode by ImsSettings APP");
            return 1;
        }
        String string = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_IMS_CONFIG_EVS_MAX_HW_BANDWIDTH");
        if (TextUtils.isEmpty(string)) {
            string = "swb";
        }
        String asString = getAsString("evs_default_bandwidth");
        if (!string.equals("wb") || (!asString.equals("nb-swb") && !asString.equals("swb"))) {
            return (SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_EVS") && getAsBoolean("enable_evs_codec").booleanValue()) ? 1 : 0;
        }
        Log.i(LOG_TAG, MotionLayout$$ExternalSyntheticOutline0.m("getEnableEvsCodec(disable): evsHwBW(", string, ") + evsSwBW(", asString, ")"));
        return 0;
    }

    public boolean getEnableRcs() {
        return getAsBoolean("enable_rcs").booleanValue();
    }

    public boolean getEnableRcsChat() {
        return getAsBoolean("enable_rcs_chat").booleanValue();
    }

    public boolean getEnableRtcpOnActiveCall() {
        return getAsBoolean("enable_rtcp_on_active_call").booleanValue();
    }

    public boolean getEnableScr() {
        return getAsBoolean("enable_scr").booleanValue();
    }

    public int getEnableStatus() {
        return getAsInteger("enabled").intValue();
    }

    public boolean getEnableVerstat() {
        return getAsBoolean("enable_verstat").booleanValue();
    }

    public Set<String> getEnabledNetwork() {
        ArraySet arraySet = new ArraySet();
        JSONArray jSONArrayOptJSONArray = this.mBody.optJSONArray("network");
        if (jSONArrayOptJSONArray != null) {
            for (int i = 0; i < jSONArrayOptJSONArray.length(); i++) {
                JSONObject jSONObjectOptJSONObject = jSONArrayOptJSONArray.optJSONObject(i);
                if (jSONObjectOptJSONObject.optBoolean("enabled")) {
                    arraySet.add(jSONObjectOptJSONObject.optString("type"));
                }
            }
        }
        return arraySet;
    }

    public String getEncAlgorithm() {
        return getAsString("enc_algo");
    }

    public boolean getEncrNullRoaming() {
        return getAsBoolean("encr_null_roaming").booleanValue();
    }

    public int getEvs2ndPayload() {
        return getAsInteger("evs_2nd_payload").intValue();
    }

    public String getEvsBandwidthReceive() {
        return getAsString("evs_bandwidth_receive");
    }

    public String getEvsBandwidthReceiveExt() {
        return getAsString("evs_bandwidth_receive_ext");
    }

    public String getEvsBandwidthSend() {
        return getAsString("evs_bandwidth_send");
    }

    public String getEvsBandwidthSendExt() {
        return getAsString("evs_bandwidth_send_ext");
    }

    public String getEvsBitRateReceive() {
        return getAsString("evs_bit_rate_receive");
    }

    public String getEvsBitRateReceiveExt() {
        return getAsString("evs_bit_rate_receive_ext");
    }

    public String getEvsBitRateSend() {
        return getAsString("evs_bit_rate_send");
    }

    public String getEvsBitRateSendExt() {
        return getAsString("evs_bit_rate_send_ext");
    }

    public String getEvsChannelAwareReceive() {
        return getAsString("evs_channel_aware_receive");
    }

    public String getEvsChannelRecv() {
        return getAsString("evs_channel_recv");
    }

    public String getEvsChannelSend() {
        return getAsString("evs_channel_send");
    }

    public String getEvsCodecModeRequest() {
        return getAsString("evs_codec_mode_request");
    }

    public String getEvsDefaultBandwidth() {
        return getAsString("evs_default_bandwidth");
    }

    public String getEvsDefaultBitrate() {
        return getAsString("evs_default_bitrate");
    }

    public String getEvsDiscontinuousTransmission() {
        return getAsString("evs_discontinuous_transmission");
    }

    public String getEvsDtxRecv() {
        return getAsString("evs_dtx_recv");
    }

    public String getEvsHeaderFull() {
        return getAsString("evs_header_full");
    }

    public String getEvsLimitedCodec() {
        return getAsString("evs_limited_codec");
    }

    public int getEvsMaxRed() {
        return getAsInteger("evs_max_red").intValue();
    }

    public String getEvsModeSwitch() {
        return getAsString("evs_mode_switch");
    }

    public int getEvsPayload() {
        return getAsInteger("evs_payload").intValue();
    }

    public int getEvsPayloadExt() {
        return getAsInteger("evs_payload_ext").intValue();
    }

    public boolean getEvsUseDefaultRtcpBw() {
        return getAsBoolean("evs_use_default_rtcp_bw").booleanValue();
    }

    public boolean getExcludePaniVowifiInitialRegi() {
        return getAsBoolean("exclude_pani_vowifi_initial_regi").booleanValue();
    }

    public List<String> getExtImpuList() {
        return Arrays.asList(TextUtils.split(getAsString("ext_impu"), ","));
    }

    public int getExtendedPublishTimer() {
        return getAsInteger("extended_publish_timer").intValue();
    }

    public int getFramerate() {
        return getAsInteger("framerate").intValue();
    }

    public boolean getFullCodecOfferRequired() {
        return getAsBoolean("is_full_codec_offer_required").booleanValue();
    }

    public int getH263QcifPayload() {
        return getAsInteger("h263_qcif_payload").intValue();
    }

    public int getH264720pChpPayload() {
        return getAsInteger("h264_720p_chp_payload").intValue();
    }

    public int getH264720pChplPayload() {
        return getAsInteger("h264_720pl_chp_payload").intValue();
    }

    public int getH264720pPayload() {
        return getAsInteger("h264_720p_payload").intValue();
    }

    public int getH264720plPayload() {
        return getAsInteger("h264_720pl_payload").intValue();
    }

    public int getH264CifPayload() {
        return getAsInteger("h264_cif_payload").intValue();
    }

    public int getH264CiflPayload() {
        return getAsInteger("h264_cifl_payload").intValue();
    }

    public int getH264QvgaPayload() {
        return getAsInteger("h264_qvga_payload").intValue();
    }

    public int getH264QvgalPayload() {
        return getAsInteger("h264_qvgal_payload").intValue();
    }

    public int getH264VgaPayload() {
        return getAsInteger("h264_vga_payload").intValue();
    }

    public int getH264VgalPayload() {
        return getAsInteger("h264_vgal_payload").intValue();
    }

    public int getH265Hd720pPayload() {
        return getAsInteger("h265_hd720p_payload").intValue();
    }

    public int getH265Hd720plPayload() {
        return getAsInteger("h265_hd720pl_payload").intValue();
    }

    public int getH265QvgaPayload() {
        return getAsInteger("h265_qvga_payload").intValue();
    }

    public int getH265QvgalPayload() {
        return getAsInteger("h265_qvgal_payload").intValue();
    }

    public int getH265VgaPayload() {
        return getAsInteger("h265_vga_payload").intValue();
    }

    public int getH265VgalPayload() {
        return getAsInteger("h265_vgal_payload").intValue();
    }

    public int getHashAlgoType() {
        return getAsInteger("hash_algo_type").intValue();
    }

    public int getId() {
        return getAsInteger("id").intValue();
    }

    public boolean getIgnoreDbrLossForAudio() {
        return getAsBoolean("ignore_dbr_loss_for_audio").booleanValue();
    }

    public boolean getIgnorePreambleForH265Sprop() {
        return getAsBoolean("ignore_preamble_for_h265_sprop").booleanValue();
    }

    public boolean getIgnoreRtcpTimeoutOnHoldCall() {
        return getAsBoolean("ignore_rtcp_timeout_on_hold_call").booleanValue();
    }

    public String getImpi() {
        return getAsString(ImsSettings.ProfileTable.IMPI);
    }

    public List<String> getImpuList() {
        return Arrays.asList(TextUtils.split(getAsString("impu"), ","));
    }

    public int getInviteTimeout() {
        return getAsInteger("invite_timeout").intValue();
    }

    public int getIpVer() {
        String asString = getAsString("ipver");
        if (asString.isEmpty() || "ipv4".equalsIgnoreCase(asString)) {
            return 1;
        }
        if ("ipv6".equalsIgnoreCase(asString)) {
            return 2;
        }
        return "ipv4v6".equalsIgnoreCase(asString) ? 3 : 1;
    }

    public String getIpVersionName() {
        return getAsString("ipver");
    }

    public boolean getIsTransportNeeded() {
        return getAsBoolean("need_transport_in_contact").booleanValue();
    }

    public String getLastPaniHeader() {
        return getAsString("last_pani_header");
    }

    public List<String> getLboPcscfAddressList() {
        return Arrays.asList(TextUtils.split(getAsString("lbo_pcscf_address"), ","));
    }

    public int getLboPcscfPort() {
        return getAsInteger("lbo_pcscf_port").intValue();
    }

    public int getLocationAcquireFail() {
        return getAsInteger("t_location_acquire_fail").intValue();
    }

    public int getLocationAcquireFailIncall() {
        return getAsInteger("t_location_acquire_fail_incall").intValue();
    }

    public int getLocationAcquireFailSMS() {
        return getAsInteger("t_location_acquire_fail_sms").intValue();
    }

    public int getLocationAcquireFailVolte() {
        return getAsInteger("t_location_acquire_fail_volte").intValue();
    }

    public int getLte911Fail() {
        return getAsInteger("t_lte_911_fail").intValue();
    }

    public int getMaxPTime() {
        return getAsInteger("maxptime").intValue();
    }

    public String getMcc() {
        String asString = getAsString("mcc");
        if (TextUtils.isEmpty(asString)) {
            String operator = getOperator();
            if (!TextUtils.isEmpty(operator)) {
                return operator.substring(0, 3);
            }
        }
        return asString;
    }

    public String getMdmnType() {
        return getAsString(ImsSettings.ProfileTable.MDMN_TYPE);
    }

    public String getMediaTypeRestrictionPolicy() {
        return getAsString("media_type_restriction_policy");
    }

    public int getMinSe() {
        return getAsInteger("min_se").intValue();
    }

    public String getMnc() {
        String asString = getAsString("mnc");
        if (TextUtils.isEmpty(asString)) {
            String operator = getOperator();
            if (!TextUtils.isEmpty(operator)) {
                return operator.substring(3);
            }
        }
        return asString;
    }

    public String getMnoName() {
        return getAsString("mnoname");
    }

    public int getMssSize() {
        return getAsInteger("mss_size").intValue();
    }

    public String getName() {
        return getAsString("name");
    }

    public boolean getNeedAutoconfig() {
        return getAsBoolean("need_autoconfig").booleanValue();
    }

    public boolean getNeedCheckAllowedMethodForRefresh() {
        return getAsBoolean("need_check_allowed_method_for_refresh").booleanValue();
    }

    public boolean getNeedIpv4Dns() {
        return getAsBoolean("need_ipv4_dns").booleanValue();
    }

    public boolean getNeedNaptrDns() {
        return getAsBoolean("need_naptr_dns").booleanValue();
    }

    public boolean getNeedOmadmConfig() {
        return getAsBoolean("need_omadm_config").booleanValue();
    }

    public int getNeedPidfRat() {
        if (getSupportedGeolocationPhase() < 2) {
            return 0;
        }
        String asString = getAsString("need_pidf_rat");
        Log.i(LOG_TAG, "pidfRatType : " + asString);
        if (TextUtils.isEmpty(asString)) {
            return 0;
        }
        int i = 0;
        for (String str : asString.replace(" ", "").split(",")) {
            if (PDN_WIFI.equalsIgnoreCase(str)) {
                i++;
            } else if ("lte".equalsIgnoreCase(str)) {
                i += 2;
            }
        }
        return i;
    }

    public int getNeedPidfSipMsg() {
        String asString = getAsString("need_pidf_sip_msg");
        if (getSupportedGeolocationPhase() < 2) {
            return 0;
        }
        Log.d(LOG_TAG, "getNeedPidfSipMsg : " + asString);
        if (TextUtils.isEmpty(asString)) {
            return 0;
        }
        int i = 0;
        for (String str : asString.replace(" ", "").split(",")) {
            if ("register".equalsIgnoreCase(str)) {
                i++;
            } else if ("reregister".equalsIgnoreCase(str)) {
                i += 2;
            } else if ("invite".equalsIgnoreCase(str)) {
                i += 4;
            } else if ("reinvite".equalsIgnoreCase(str)) {
                i += 8;
            } else if ("invite_response".equalsIgnoreCase(str)) {
                i += 16;
            } else if ("update".equalsIgnoreCase(str)) {
                i += 32;
            } else if ("message".equalsIgnoreCase(str)) {
                i += 64;
            }
        }
        return i;
    }

    public boolean getNeedRemoveE911TimerOn18x() {
        return getAsBoolean("need_remove_e911_timer_on_18x").booleanValue();
    }

    public boolean getNeedStartE911TimerOnAlerting() {
        return getAsBoolean("need_start_e911_timer_on_alerting").booleanValue();
    }

    public boolean getNeedVoLteRetryInNr() {
        return getAsBoolean("need_volte_retry_in_nr").booleanValue();
    }

    public Set<String> getNetworkNameSet() {
        ArraySet arraySet = new ArraySet();
        JSONArray jSONArrayOptJSONArray = this.mBody.optJSONArray("network");
        if (jSONArrayOptJSONArray != null) {
            for (int i = 0; i < jSONArrayOptJSONArray.length(); i++) {
                arraySet.add(jSONArrayOptJSONArray.optJSONObject(i).optString("type"));
            }
        }
        return arraySet;
    }

    public Set<Integer> getNetworkSet() {
        ArraySet arraySet = new ArraySet();
        JSONArray jSONArrayOptJSONArray = this.mBody.optJSONArray("network");
        if (jSONArrayOptJSONArray != null) {
            for (int i = 0; i < jSONArrayOptJSONArray.length(); i++) {
                arraySet.add(Integer.valueOf(getNetworkType(jSONArrayOptJSONArray.optJSONObject(i).optString("type"))));
            }
        }
        return arraySet;
    }

    public int getNotifyCallDowngraded() {
        return getAsInteger("notify_call_downgraded").intValue();
    }

    public boolean getNotifyCodecOnEstablished() {
        return getAsBoolean("notify_codec_on_established").booleanValue();
    }

    public String getNotifyHistoryInfo() {
        return getAsString("notify_history_info");
    }

    public String getOipFromPreferred() {
        return getAsString("oip_from_preferred");
    }

    public String getOperator() {
        String asString = getAsString("representative_plmn");
        return !TextUtils.isEmpty(asString) ? asString : "";
    }

    public int getPTime() {
        return getAsInteger("ptime").intValue();
    }

    public String getPacketizationMode() {
        return getAsString("packetization_mode");
    }

    public String getPassword() {
        return getAsString(HostAuth.PASSWORD);
    }

    public List<String> getPcscfList() {
        return Arrays.asList(TextUtils.split(getAsString("pcscf"), ","));
    }

    public int getPcscfPreference() {
        return getAsInteger("pcscf_pref").intValue();
    }

    public String getPdn() {
        return getAsString(ImsSettings.ProfileTable.PDN);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:6:0x0012  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int getPdnType() {
        String pdn = getPdn();
        if (pdn == null) {
            return -1;
        }
        switch (pdn) {
            case "p2p-wlan":
            case "p2p-bt":
            case "swlan":
                Log.i(LOG_TAG, "PDN_WIFI_DIRECT or PDN_WIFI_HS or PDN_BT_HS");
                break;
            case "ims":
                break;
            case "wifi":
                break;
            case "internet":
                break;
            case "default":
                break;
            case "emergency":
                break;
            default:
                Log.d(LOG_TAG, "PDN not null and not matched, value is ".concat(pdn));
                break;
        }
        return -1;
    }

    public String getPolicyOnLocalNumbers() {
        return getAsString("policy_on_local_numbers");
    }

    public int getPollListSubExp() {
        return getAsInteger("poll_list_sub_exp").intValue();
    }

    public boolean getPrecondtionInitialSendrecv() {
        return getAsBoolean("precondtion_initial_sendrecv").booleanValue();
    }

    public String getPriDeviceIdWithURN() {
        return getAsString("priDeviceIdWithURN");
    }

    public int getPriority() {
        return getAsInteger(SystemUIAnalytics.QPNE_VID_PRIORITY).intValue();
    }

    public String getPrivacyHeaderRestricted() {
        return getAsString("privacy_header_restricted");
    }

    public int getPublishErrRetryTimer() {
        return getAsInteger("publish_err_retry_timer").intValue();
    }

    public int getPublishExpiry() {
        return getAsInteger("publish_expiry").intValue();
    }

    public int getPublishTimer() {
        return getAsInteger("publish_timer").intValue();
    }

    public String getPullingServerUri() {
        return getAsString("pulling_server_uri");
    }

    public int getQValue() {
        return getAsInteger("qvalue").intValue();
    }

    public int getRPort() {
        return getAsInteger("rport").intValue();
    }

    public int getRTCPTimeout() {
        return getAsInteger("rtcp_timeout").intValue();
    }

    public int getRTPTimeout() {
        return getAsInteger("rtp_timeout").intValue();
    }

    public String getRcsConfigMark() {
        return getAsString("config_version_mark");
    }

    public String getRcsProfile() {
        return getAsString("rcs_profile");
    }

    public int getRcsProfileType() {
        return RCS_PROFILE.getProfileType(getRcsProfile());
    }

    public boolean getRcsTelephonyFeatureTagRequired() {
        return getAsBoolean("is_rcs_telephony_feature_tag_required").booleanValue();
    }

    public int getRegExpire() {
        return getAsInteger("reg_expires").intValue();
    }

    public int getRegRetryBaseTime() {
        return getAsInteger("reg_retry_base_time").intValue();
    }

    public int getRegRetryMaxTime() {
        return getAsInteger("reg_retry_max_time").intValue();
    }

    public String getRegRetryPcscfPolicyOn403() {
        return getAsString("reg_retry_pcscf_policy_on_403");
    }

    public String getRegistrationAlgorithm() {
        return getAsString("regi_algo");
    }

    public String getRemoteUriType() {
        return getAsString("remote_uri_type");
    }

    public boolean getReplaceCFNL() {
        if (!getAsBoolean("gcf_cfnl_mode").booleanValue()) {
            return false;
        }
        Log.i(LOG_TAG, "getReplaceCFNL(enable): force replace CFNL for GCF by ImsSettings APP");
        return true;
    }

    public int getRequestLocationTiming() {
        String asString = getAsString("request_location_timing");
        Log.d(LOG_TAG, "getRequestLocationTiming : " + asString);
        if (TextUtils.isEmpty(asString)) {
            return 0;
        }
        int i = 0;
        for (String str : asString.replace(" ", "").split(",")) {
            if ("emergency_call".equalsIgnoreCase(str)) {
                i++;
            } else if ("epdg_available_user_agreement".equalsIgnoreCase(str)) {
                i += 2;
            } else if ("periodic".equalsIgnoreCase(str)) {
                i += 4;
            }
        }
        return i;
    }

    public int getReregiOnRatChange() {
        String asString = getAsString("reregi_on_ratchange");
        if ("off_rat_change".equalsIgnoreCase(asString)) {
            return 1;
        }
        if ("force_nr".equalsIgnoreCase(asString)) {
            return 2;
        }
        return "on".equalsIgnoreCase(asString) ? 3 : 0;
    }

    public boolean getRetryInviteOnTcpReset() {
        return getAsBoolean("retry_invite_on_tcp_reset").booleanValue();
    }

    public int getRingbackTimer() {
        return getAsInteger("ringback_timer").intValue();
    }

    public int getRingingTimer() {
        return getAsInteger("ringing_timer").intValue();
    }

    public int getSaClientPort() {
        return getAsInteger("secure_client_port").intValue();
    }

    public int getSaServerPort() {
        return getAsInteger("secure_server_port").intValue();
    }

    public int getScmVersion() {
        return getAsInteger("scm_version", 0).intValue();
    }

    public String getSelectTransportAfterTcpReset() {
        return getAsString("select_transport_after_tcp_reset");
    }

    public int getSelfPort() {
        return getAsInteger("self_port", 5060).intValue();
    }

    public boolean getSend18xReliably() {
        return getAsBoolean("send_18x_reliable").booleanValue();
    }

    public boolean getSendByeForUssi() {
        return getAsBoolean("send_bye_for_ussi").booleanValue();
    }

    public Set<String> getServiceSet(NETWORK_TYPE network_type) {
        return network_type == NETWORK_TYPE.ALL ? getAllServiceSetFromAllNetwork() : getServiceSet(Integer.valueOf(getNetworkType(network_type)), false);
    }

    public int getSessionExpire() {
        return getAsInteger("session_expires").intValue();
    }

    public int getSessionRefreshMethod() {
        return getAsInteger("session_refresh_method").intValue();
    }

    public String getSessionRefresher() {
        return getAsString("session_refresher");
    }

    public boolean getSimMobility() {
        return getAsBoolean("simmobility").booleanValue();
    }

    public boolean getSimMobilityForRcs() {
        return getAsBoolean("simmobilityForRcs").booleanValue();
    }

    public JSONObject getSimMobilityUpdate() {
        try {
            return this.mBody.getJSONObject("simmobility_update");
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getClass().getSimpleName() + "!! " + e.getMessage());
            return null;
        }
    }

    public int getSipMobility() {
        return getAsInteger("sip_mobility").intValue();
    }

    public int getSipPort() {
        return getAsInteger(HostAuth.PORT).intValue();
    }

    public String getSipUserAgent() {
        return getAsString("useragent");
    }

    public String getSmsPsi() {
        return getAsString("sms_psi");
    }

    public String getSmscSet() {
        return getAsString("smsc_set");
    }

    public String getSmsoipUsagePolicy() {
        return getAsString("smsoip_usage_policy");
    }

    public boolean getSosUrnRequired() {
        return getAsBoolean("sos_urn_required").booleanValue();
    }

    public int getSslType() {
        return getAsInteger("ssl_type").intValue();
    }

    public int getSubscribeForReg() {
        return getAsBoolean("subscribe_for_reg").booleanValue() ? 1 : 0;
    }

    public int getSubscribeMaxEntry() {
        return getAsInteger("subscribe_max_entry").intValue();
    }

    public int getSubscriberTimer() {
        return getAsInteger("subscriber_timer").intValue();
    }

    public boolean getSupport183ForIr92v9Precondition() {
        return getAsBoolean("support_183_for_ir92v9_precondition").booleanValue();
    }

    public boolean getSupport199ProvisionalResponse() {
        return getAsBoolean(ImsSettings.ProfileTable.SUPPORT_199_PROVISIONAL_RESPONSE).booleanValue();
    }

    public boolean getSupport380PolicyByEmcbs() {
        return getAsBoolean("support_380_policy_by_emcbs").booleanValue();
    }

    public boolean getSupport3gppUssi() {
        return getAsBoolean("support_3gpp_ussi").booleanValue();
    }

    public boolean getSupportAccessType() {
        return getAsBoolean("support_access_type").booleanValue();
    }

    public boolean getSupportAltitude() {
        return getAsBoolean("support_altitude").booleanValue();
    }

    public int getSupportB2cCallcomposerWithoutFeaturetag() {
        return getAsInteger("support_b2c_callcomposer_without_featuretag").intValue();
    }

    public boolean getSupportClir() {
        return getAsBoolean("support_clir").booleanValue();
    }

    public boolean getSupportDatachannelWithFeatureCaps() {
        return getAsBoolean("support_datachannel_with_feature_caps").booleanValue();
    }

    public boolean getSupportEct() {
        return getAsBoolean("support_ect").booleanValue();
    }

    public boolean getSupportImsNotAvailable() {
        return getAsBoolean("support_ims_not_available").booleanValue();
    }

    public boolean getSupportInitRegiByPcscfRestoration() {
        return getAsBoolean("support_init_regi_by_pcscf_restoration").booleanValue();
    }

    public boolean getSupportLtePreferred() {
        return getAsBoolean("support_lte_preferred").booleanValue();
    }

    public boolean getSupportMergeVideoConference() {
        return getAsBoolean("support_merge_video_conference").booleanValue();
    }

    public boolean getSupportNetworkInitUssi() {
        return getAsBoolean("support_network_init_ussi").booleanValue();
    }

    public boolean getSupportRcsAcrossSalesCode() {
        return getAsBoolean("support_rcs_across_sales_code").booleanValue();
    }

    public boolean getSupportReplaceMerge() {
        return getAsBoolean("support_replace_merge").booleanValue();
    }

    public boolean getSupportRfc6337ForDelayedOffer() {
        return getAsBoolean("support_rfc6337_for_delayed_offer").booleanValue();
    }

    public boolean getSupportUpgradeVideoConference() {
        return getAsBoolean("support_upgrade_video_conference").booleanValue();
    }

    public int getSupportedGeolocationPhase() {
        return getAsInteger("supported_geolocation_phase").intValue();
    }

    public int getT140Payload() {
        return getAsInteger("t140_payload").intValue();
    }

    public int getT140RedPayload() {
        return getAsInteger("t140_red_payload").intValue();
    }

    public int getTcpRstUacErrorcode() {
        return getAsInteger("tcprst_uac_errorcode").intValue();
    }

    public int getTcpRstUasErrorcode() {
        return getAsInteger("tcprst_uas_errorcode").intValue();
    }

    public int getTextAS() {
        return getAsInteger("text_as").intValue();
    }

    public int getTextAvpf() {
        return getAsInteger("text_avpf").intValue();
    }

    public int getTextHoldDirection() {
        return getAsInteger("text_hold_direction").intValue();
    }

    public int getTextPort() {
        return getAsInteger("text_port").intValue();
    }

    public int getTextRR() {
        return getAsInteger("text_rr").intValue();
    }

    public int getTextRS() {
        return getAsInteger("text_rs").intValue();
    }

    public int getTextSrtp() {
        return getAsInteger("text_srtp").intValue();
    }

    public int getTimer1() {
        return getTimer("1");
    }

    public int getTimer2() {
        return getTimer("2");
    }

    public int getTimer4() {
        return getTimer("4");
    }

    public int getTimerA() {
        return getTimer(TIMER_NAME_A);
    }

    public int getTimerB() {
        return getTimer(TIMER_NAME_B);
    }

    public int getTimerC() {
        return getTimer(TIMER_NAME_C);
    }

    public int getTimerD() {
        return getTimer(TIMER_NAME_D);
    }

    public int getTimerE() {
        return getTimer(TIMER_NAME_E);
    }

    public int getTimerEpsFbWatchdog() {
        return getAsInteger("t_eps_fb_watchdog").intValue();
    }

    public int getTimerF() {
        return getTimer(TIMER_NAME_F);
    }

    public int getTimerG() {
        return getTimer(TIMER_NAME_G);
    }

    public int getTimerH() {
        return getTimer(TIMER_NAME_H);
    }

    public int getTimerI() {
        return getTimer(TIMER_NAME_I);
    }

    public int getTimerJ() {
        return getTimer(TIMER_NAME_J);
    }

    public int getTimerK() {
        return getTimer(TIMER_NAME_K);
    }

    public int getTransport() {
        String asString = getAsString("transport");
        if (asString == null) {
            return -1;
        }
        if ("udp-preferred".equalsIgnoreCase(asString)) {
            return 1;
        }
        if ("udp".equalsIgnoreCase(asString)) {
            return 2;
        }
        if ("tcp".equalsIgnoreCase(asString)) {
            return 3;
        }
        return "tls".equalsIgnoreCase(asString) ? 4 : -1;
    }

    public String getTransportName() {
        return getAsString("transport");
    }

    public boolean getTryReregisterFromKeepalive() {
        return getAsBoolean("try_reregister_from_keepalive").booleanValue();
    }

    public int getTtyType(int i) {
        boolean zIsRttSupported = isRttSupported(i);
        int iIntValue = getAsInteger("tty_type").intValue();
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(iIntValue, "ttyType : ", LOG_TAG);
        return (zIsRttSupported && (iIntValue == 1 || iIntValue == 2)) ? iIntValue + 2 : !zIsRttSupported ? (iIntValue == 3 || iIntValue == 4) ? iIntValue - 2 : iIntValue : iIntValue;
    }

    public List<String> getUacList() {
        return getAsStringList("uac_sip_list");
    }

    public String getUiccMobilityVersion() {
        return getAsString("uicc_mobility_ver");
    }

    public boolean getUse183OnProgressIncoming() {
        return getAsBoolean("use_183_on_progress_incoming").booleanValue();
    }

    public boolean getUse200offerWhenRemoteNotSupport100rel() {
        return getAsBoolean("use_200offer_when_remote_not_support_100rel").booleanValue();
    }

    public boolean getUsePemHeader() {
        return getAsBoolean("use_pem_header").booleanValue();
    }

    public int getUsePrecondition() {
        return getAsBoolean("use_precondition").booleanValue() ? 1 : 0;
    }

    public boolean getUseProvisionalResponse100rel() {
        return getAsBoolean("use_provisional_response_100rel").booleanValue();
    }

    public boolean getUseQ850causeOn480() {
        return getAsBoolean("use_q850cause_on_480").booleanValue();
    }

    public boolean getUseSpsForH264Hd() {
        return getAsBoolean("use_sps_for_h264_hd").booleanValue();
    }

    public boolean getUseSubcontactWhenResub() {
        return getAsBoolean("use_subcontact_when_resub").booleanValue();
    }

    public int getValidLocationAccuracy() {
        return getAsInteger("valid_location_accuracy").intValue();
    }

    public int getValidLocationTime() {
        return getAsInteger("t_valid_location_time").intValue();
    }

    public int getVideoAS() {
        return getAsInteger("video_as").intValue();
    }

    public int getVideoAvpf() {
        return getAsInteger("video_avpf").intValue();
    }

    public String getVideoCodec() {
        return getAsString("video_codec");
    }

    public int getVideoCrbtSupportType() {
        return getAsInteger("video_crbt_support_type", 0).intValue();
    }

    public int getVideoPortEnd() {
        return getAsInteger("video_port_end").intValue();
    }

    public int getVideoPortStart() {
        return getAsInteger("video_port_start").intValue();
    }

    public int getVideoRR() {
        return getAsInteger("video_rr").intValue();
    }

    public int getVideoRS() {
        return getAsInteger("video_rs").intValue();
    }

    public int getVideoRtcpXr() {
        return getAsInteger("video_rtcpxr").intValue();
    }

    public int getVideoSrtp() {
        return getAsInteger("video_srtp").intValue();
    }

    public boolean hasEmergencySupport() {
        return getAsBoolean("emergency_support").booleanValue();
    }

    public boolean hasService(String str) {
        Iterator<Set<String>> it = getAllServiceSet().values().iterator();
        while (it.hasNext()) {
            if (it.next().contains(str)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        int iHashCode = super.hashCode() * 31;
        JSONObject jSONObject = this.mBody;
        return iHashCode + (jSONObject != null ? jSONObject.hashCode() : 0);
    }

    public boolean isAllowedOnRoaming() {
        return getAsBoolean("support_roaming").booleanValue();
    }

    public boolean isAllowedRegiWhenLocationUnavailable() {
        return this.mBody.optBoolean("allow_regi_when_location_unavailable");
    }

    public boolean isAnonymousFetch() {
        return getAsBoolean("anonymous_fetch").booleanValue();
    }

    public boolean isCmcExtendedContentSupport() {
        return getAsBoolean("cmc_ext_content_support").booleanValue();
    }

    public boolean isEnableGruu() {
        return getAsBoolean("enable_gruu").booleanValue();
    }

    public boolean isEnableSessionId() {
        return getAsBoolean("enable_session_id").booleanValue();
    }

    public boolean isEnableVcid() {
        if (SemSystemProperties.get("ro.build.characteristics", "").contains("tablet")) {
            return false;
        }
        String str = SemSystemProperties.get("persist.omc.sales_code", "");
        if (TextUtils.isEmpty(str)) {
            str = SemSystemProperties.get("ro.csc.sales_code", "");
        }
        if (!"SKT".equals(str) && !"SKC".equals(str) && !"KTT".equals(str) && !"KTC".equals(str) && !"LGT".equals(str) && !"LUC".equals(str) && !"KOO".equals(str) && !str.contains("K0")) {
            return false;
        }
        if (SemSystemProperties.getInt("ro.build.version.oneui", 0) >= 50100 || getAsBoolean("enable_vcid_aux").booleanValue()) {
            return getAsBoolean("enable_vcid").booleanValue();
        }
        return false;
    }

    public boolean isEpdgSupported() {
        return TextUtils.equals(getPdn(), PDN_IMS) && getNetworkSet().contains(18);
    }

    public boolean isGzipEnabled() {
        return getAsBoolean("enable_gzip").booleanValue();
    }

    public boolean isIpSecEnabled() {
        return getAsBoolean("support_ipsec").booleanValue();
    }

    public boolean isMmtelVideoExempt() {
        return getAsInteger("mmtel_video_exempt").intValue() == 1;
    }

    public boolean isMmtelVoiceExempt() {
        return getAsInteger("mmtel_voice_exempt").intValue() == 1;
    }

    public boolean isMsrpBearerUsed() {
        return getAsBoolean("use_msrp_bearer").booleanValue();
    }

    public boolean isNeedPidfSipMsg(int i) {
        return (getNeedPidfSipMsg() & i) == i;
    }

    public boolean isNetworkEnabled(int i) {
        JSONObject network = getNetwork(i);
        return network != null && network.optBoolean("enabled");
    }

    public boolean isProper() {
        return (TextUtils.isEmpty(getImpi()) || getImpuList().isEmpty() || TextUtils.isEmpty(getPdn())) ? false : true;
    }

    public boolean isPublishGzipEnabled() {
        return getAsBoolean("enable_gzip_for_publish").booleanValue();
    }

    public boolean isSamsungMdmnEnabled() {
        return ImsSettings.MDMN.SAMSUNG.equals(getAsString(ImsSettings.ProfileTable.MDMN_TYPE));
    }

    public boolean isSipUriOnly() {
        return getAsBoolean("sip_uri_only").booleanValue();
    }

    public boolean isSiproidMode() {
        if (getSipPort() != SIPROID_PCSCF_PORT) {
            return false;
        }
        String str = Build.TYPE;
        return "eng".equals(str) || "userdebug".equals(str);
    }

    public boolean isSmsIpExempt() {
        return getAsInteger("smsoip_exempt").intValue() == 1;
    }

    public boolean isSoftphoneEnabled() {
        return ImsSettings.MDMN.SOFTPHONE.equals(getAsString(ImsSettings.ProfileTable.MDMN_TYPE));
    }

    public boolean isSupportSmsOverIms() {
        return getAsBoolean("support_sms_over_ims").booleanValue();
    }

    public boolean isSupportVideoCapabilities() {
        return getAsBoolean("video_capabilities").booleanValue();
    }

    public boolean isTcpGracefulShutdownEnabled() {
        return getAsBoolean("enable_tcp_graceful_shutdown").booleanValue();
    }

    public boolean isUicclessEmergency() {
        return getAsBoolean("uiccless_emergency").booleanValue();
    }

    public boolean isVceConfigEnabled() {
        return getAsBoolean("vce_config_enabled").booleanValue();
    }

    public boolean isVolteServiceStatus() {
        return getAsBoolean("volte_service_status").booleanValue();
    }

    public boolean isWifiPreConditionEnabled() {
        return getAsBoolean("wifi_precondition_enabled").booleanValue();
    }

    public void put(String str, Boolean bool) throws JSONException {
        try {
            this.mBody.put(str, bool);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void removeImpu(String str) throws JSONException {
        ArrayList arrayList = new ArrayList(getImpuList());
        arrayList.remove(str);
        setImpuList(TextUtils.join(",", arrayList));
    }

    public void setAccessToken(String str) throws JSONException {
        put("accessToken", str);
    }

    public void setAppId(String str) throws JSONException {
        put("app_id", str);
    }

    public void setAudioPortEnd(int i) throws JSONException {
        put("audio_port_end", Integer.valueOf(i));
    }

    public void setAudioPortStart(int i) throws JSONException {
        put("audio_port_start", Integer.valueOf(i));
    }

    public void setAudioSrtp(int i) throws JSONException {
        put("audio_srtp", Integer.valueOf(i));
    }

    public void setAuthAlgorithm(String str) throws JSONException {
        put("auth_algo", str);
    }

    public void setAuthName(String str) throws JSONException {
        put("authname", str);
    }

    public void setConferenceSupportPrematureEnd(boolean z) throws JSONException {
        put("conference_support_premature_end", Boolean.valueOf(z));
    }

    public void setDelayPcscfChangeDuringCall(boolean z) throws JSONException {
        put("delay_pcscf_change_during_call", Boolean.valueOf(z));
    }

    public void setDeregTimeout(String str, int i) throws JSONException {
        JSONObject network = getNetwork(str);
        if (network != null) {
            try {
                network.put("dereg_timeout", i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void setDisplayName(String str) throws JSONException {
        put("display_name", str);
    }

    public void setDomain(String str) throws JSONException {
        put("domain", str);
    }

    public void setDuid(String str) throws JSONException {
        put("duid", str);
    }

    public void setEctNoHoldForActiveCall(boolean z) throws JSONException {
        put("ect_no_hold_for_active_call", Boolean.valueOf(z));
    }

    public void setEmergencySupport(boolean z) throws JSONException {
        put("emergency_support", Boolean.valueOf(z));
    }

    public void setEnableEvsCodec(boolean z) throws JSONException {
        put("enable_evs_codec", Boolean.valueOf(z));
    }

    public void setEnableScr(boolean z) throws JSONException {
        put("enable_scr", Boolean.valueOf(z));
    }

    public void setEnableVerstat(boolean z) throws JSONException {
        put("enable_verstat", Boolean.valueOf(z));
    }

    public void setEncAlgorithm(String str) throws JSONException {
        put("enc_algo", str);
    }

    public void setEvs2ndPayload(int i) throws JSONException {
        put("evs_2nd_payload", Integer.valueOf(i));
    }

    public void setEvsBandwidthReceive(String str) throws JSONException {
        put("evs_bandwidth_receive", str);
    }

    public void setEvsBandwidthReceiveExt(String str) throws JSONException {
        put("evs_bandwidth_receive_ext", str);
    }

    public void setEvsBandwidthSend(String str) throws JSONException {
        put("evs_bandwidth_send", str);
    }

    public void setEvsBandwidthSendExt(String str) throws JSONException {
        put("evs_bandwidth_send_ext", str);
    }

    public void setEvsBitRateReceive(String str) throws JSONException {
        put("evs_bit_rate_receive", str);
    }

    public void setEvsBitRateReceiveExt(String str) throws JSONException {
        put("evs_bit_rate_receive_ext", str);
    }

    public void setEvsBitRateSend(String str) throws JSONException {
        put("evs_bit_rate_send", str);
    }

    public void setEvsBitRateSendExt(String str) throws JSONException {
        put("evs_bit_rate_send_ext", str);
    }

    public void setEvsChannelAwareReceive(String str) throws JSONException {
        put("evs_channel_aware_receive", str);
    }

    public void setEvsChannelRecv(String str) throws JSONException {
        put("evs_channel_recv", str);
    }

    public void setEvsChannelSend(String str) throws JSONException {
        put("evs_channel_send", str);
    }

    public void setEvsCodecModeRequest(String str) throws JSONException {
        put("evs_codec_mode_request", str);
    }

    public void setEvsDefaultBandwidth(String str) throws JSONException {
        put("evs_default_bandwidth", str);
    }

    public void setEvsDefaultBitrate(String str) throws JSONException {
        put("evs_default_bitrate", str);
    }

    public void setEvsDiscontinuousTransmission(String str) throws JSONException {
        put("evs_discontinuous_transmission", str);
    }

    public void setEvsDtxRecv(String str) throws JSONException {
        put("evs_dtx_recv", str);
    }

    public void setEvsHeaderFull(String str) throws JSONException {
        put("evs_header_full", str);
    }

    public void setEvsLimitedCodec(String str) throws JSONException {
        put("evs_limited_codec", str);
    }

    public void setEvsModeSwitch(String str) throws JSONException {
        put("evs_mode_switch", str);
    }

    public void setEvsPayload(int i) throws JSONException {
        put("evs_payload", Integer.valueOf(i));
    }

    public void setEvsPayloadExt(int i) throws JSONException {
        put("evs_payload_ext", Integer.valueOf(i));
    }

    public void setEvsUseDefaultRtcpBw(boolean z) throws JSONException {
        put("evs_use_default_rtcp_bw", Boolean.valueOf(z));
    }

    public void setExcludePaniVowifiInitialRegi(boolean z) throws JSONException {
        put("exclude_pani_vowifi_initial_regi", Boolean.valueOf(z));
    }

    public void setExtImpuList(List<String> list) throws JSONException {
        if (list != null) {
            put("ext_impu", TextUtils.join(",", list));
        } else {
            Log.e(LOG_TAG, "setExtImpuList: impuList is null.");
            put("ext_impu", "");
        }
    }

    public void setId(int i) throws JSONException {
        put("id", Integer.valueOf(i));
    }

    public void setImpi(String str) throws JSONException {
        put(ImsSettings.ProfileTable.IMPI, str);
    }

    public void setImpuList(String str) throws JSONException {
        put("impu", str);
    }

    public void setIpSpecEnabled(boolean z) throws JSONException {
        put("support_ipsec", Boolean.valueOf(z));
    }

    public void setIpVer(int i) throws JSONException {
        if (i == 1) {
            put("ipver", "ipv4");
        } else if (i == 2) {
            put("ipver", "ipv6");
        } else {
            if (i != 3) {
                throw new IllegalArgumentException("wrong ipVer");
            }
            put("ipver", "ipv4v6");
        }
    }

    public void setIsSipUriOnly(boolean z) throws JSONException {
        put("sip_uri_only", Boolean.valueOf(z));
    }

    public void setLboPcscfAddressList(List<String> list) throws JSONException {
        put("lbo_pcscf_address", TextUtils.join(",", list));
    }

    public void setLboPcscfPort(int i) throws JSONException {
        put("lbo_pcscf_port", Integer.valueOf(i));
    }

    public void setMcc(String str) throws JSONException {
        put("mcc", str);
    }

    public void setMediaTypeRestrictionPolicy(String str) throws JSONException {
        put("media_type_restriction_policy", str);
    }

    public void setMnc(String str) throws JSONException {
        put("mnc", str);
    }

    public void setMnoName(String str) throws JSONException {
        put("mnoname", str);
    }

    public void setMsrpBearerUsed(boolean z) throws JSONException {
        put("use_msrp_bearer", Boolean.valueOf(z));
    }

    public void setMssSize(int i) throws JSONException {
        put("mss_size", Integer.valueOf(i));
    }

    public void setName(String str) throws JSONException {
        put("name", str);
    }

    public void setNeedAutoconfig(boolean z) throws JSONException {
        put("need_autoconfig", Boolean.valueOf(z));
    }

    public void setNeedCheckAllowedMethodForRefresh(boolean z) throws JSONException {
        put("need_check_allowed_method_for_refresh", Boolean.valueOf(z));
    }

    public void setNeedNaptrDns(boolean z) throws JSONException {
        put("need_naptr_dns", Boolean.valueOf(z));
    }

    public void setNeedOmadmConfig(boolean z) throws JSONException {
        put("need_omadm_config", Boolean.valueOf(z));
    }

    public void setNeedPidfRat(String str) throws JSONException {
        if (getSupportedGeolocationPhase() < 2) {
            str = "";
        }
        put("need_pidf_rat", str);
    }

    public void setNeedPidfSipMsg(String str) throws JSONException {
        if (getSupportedGeolocationPhase() < 2) {
            str = "";
        }
        put("need_pidf_sip_msg", str);
    }

    public void setNetworkEnabled(int i, boolean z) throws JSONException {
        JSONObject network = getNetwork(i);
        if (network == null) {
            try {
                network = new JSONObject();
                network.put("type", getNetworkName(i));
                network.put("services", new JSONArray());
                JSONArray jSONArray = this.mBody.getJSONArray("network");
                if (jSONArray != null) {
                    jSONArray.put(network);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                return;
            }
        }
        network.put("enabled", z);
    }

    public void setNetworkList(String str) throws JSONException {
        List listAsList = Arrays.asList(TextUtils.split(str, "\\s*,\\s*"));
        JSONArray jSONArrayOptJSONArray = this.mBody.optJSONArray("network");
        if (jSONArrayOptJSONArray != null) {
            int i = 0;
            for (String str2 : TextUtils.split(str, ",")) {
                if (getNetwork(str2) == null) {
                    JSONObject jSONObject = new JSONObject();
                    try {
                        jSONObject.put("type", str2);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    jSONArrayOptJSONArray.put(jSONObject);
                }
            }
            while (i < jSONArrayOptJSONArray.length()) {
                if (!listAsList.contains(jSONArrayOptJSONArray.optJSONObject(i).optString("type"))) {
                    jSONArrayOptJSONArray.remove(i);
                    i--;
                }
                i++;
            }
        }
    }

    public void setNotifyCallDowngraded(int i) throws JSONException {
        put("notify_call_downgraded", Integer.valueOf(i));
    }

    public void setNotifyCodecOnEstablished(boolean z) throws JSONException {
        put("notify_codec_on_established", Boolean.valueOf(z));
    }

    public void setNotifyHistoryInfo(String str) throws JSONException {
        put("notify_history_info", str);
    }

    public void setOipFromPreferred(String str) throws JSONException {
        put("oip_from_preferred", str);
    }

    public void setPassword(String str) throws JSONException {
        put(HostAuth.PASSWORD, str);
    }

    public void setPcscfList(List<String> list) throws JSONException {
        put("pcscf", TextUtils.join(",", list));
    }

    public void setPcscfPreference(int i) throws JSONException {
        put("pcscf_pref", Integer.valueOf(i));
    }

    public void setPdn(String str) throws JSONException {
        put(ImsSettings.ProfileTable.PDN, str);
    }

    public void setPolicyOnLocalNumbers(String str) throws JSONException {
        put("policy_on_local_numbers", str);
    }

    public void setPriDeviceIdWithURN(String str) throws JSONException {
        put("priDeviceIdWithURN", str);
    }

    public void setPriority(int i) throws JSONException {
        put(SystemUIAnalytics.QPNE_VID_PRIORITY, Integer.valueOf(i));
    }

    public void setRPort(int i) throws JSONException {
        put("rport", Integer.valueOf(i));
    }

    public void setRcsProfile(String str) throws JSONException {
        put("rcs_profile", str);
    }

    public void setRegistrationAlgorithm(String str) throws JSONException {
        put("regi_algo", str);
    }

    public void setRequestLocationTiming(String str) throws JSONException {
        put("request_location_timing", str);
    }

    public void setReregiOnRatChange(int i) throws JSONException {
        if (i == 1) {
            put("reregi_on_ratchange", "off_rat_change");
        } else if (i != 3) {
            put("reregi_on_ratchange", "off");
        } else {
            put("reregi_on_ratchange", "on");
        }
    }

    public void setSaClientPort(int i) throws JSONException {
        put("secure_client_port", Integer.valueOf(i));
    }

    public void setSaServerPort(int i) throws JSONException {
        put("secure_server_port", Integer.valueOf(i));
    }

    public void setSend18xReliably(boolean z) throws JSONException {
        put("send_18x_reliable", Boolean.valueOf(z));
    }

    public void setSendByeForUssi(boolean z) throws JSONException {
        put("send_bye_for_ussi", Boolean.valueOf(z));
    }

    public void setServiceSet(int i, Set<String> set) throws JSONException {
        JSONObject network = getNetwork(i);
        if (network == null) {
            Log.e(LOG_TAG, "setServiceSet: getNetwork return null.");
            return;
        }
        try {
            network.put("services", new JSONArray((Collection) set));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setSimMobility(boolean z) throws JSONException {
        Log.d(LOG_TAG, "setSimMobility: " + z);
        put("simmobility", Boolean.valueOf(z));
    }

    public void setSimMobilityForRcs(boolean z) throws JSONException {
        Log.d(LOG_TAG, "setSimMobilityForRcs: " + z);
        put("simmobilityForRcs", Boolean.valueOf(z));
    }

    public void setSipPort(int i) throws JSONException {
        put(HostAuth.PORT, Integer.valueOf(i));
    }

    public void setSipUserAgent(String str) throws JSONException {
        put("useragent", str);
    }

    public void setSmsPsi(String str) throws JSONException {
        put("sms_psi", str);
    }

    public void setSmsoipUsagePolicy(String str) throws JSONException {
        put("smsoip_usage_policy", str);
    }

    public void setSoftphoneEnabled(String str) throws JSONException {
        put(ImsSettings.ProfileTable.MDMN_TYPE, ImsSettings.MDMN.SOFTPHONE);
    }

    public void setSosUrnRequired(boolean z) throws JSONException {
        put("sos_urn_required", Boolean.valueOf(z));
    }

    public void setSslType(int i) throws JSONException {
        put("ssl_type", Integer.valueOf(i));
    }

    public void setSupport199ProvisionalResponse(boolean z) throws JSONException {
        put(ImsSettings.ProfileTable.SUPPORT_199_PROVISIONAL_RESPONSE, Boolean.valueOf(z));
    }

    public void setSupport380PolicyByEmcbs(boolean z) throws JSONException {
        put("support_380_policy_by_emcbs", Boolean.valueOf(z));
    }

    public void setSupport3gppUssi(boolean z) throws JSONException {
        put("support_3gpp_ussi", Boolean.valueOf(z));
    }

    public void setSupportAltitude(boolean z) throws JSONException {
        put("support_altitude", Boolean.valueOf(z));
    }

    public void setSupportClir(boolean z) throws JSONException {
        put("support_clir", Boolean.valueOf(z));
    }

    public void setSupportNetworkInitUssi(boolean z) throws JSONException {
        put("support_network_init_ussi", Boolean.valueOf(z));
    }

    public void setSupportRcsAcrossSalesCode(boolean z) throws JSONException {
        put("support_rcs_across_sales_code", Boolean.valueOf(z));
    }

    public void setSupportRfc6337ForDelayedOffer(boolean z) throws JSONException {
        put("support_rfc6337_for_delayed_offer", Boolean.valueOf(z));
    }

    public void setSupportSmsOverIms(boolean z) throws JSONException {
        put("support_sms_over_ims", Boolean.valueOf(z));
    }

    public void setSupportedGeolocationPhase(int i) throws JSONException {
        put("supported_geolocation_phase", Integer.valueOf(i));
    }

    public void setTcpGracefulShutdownEnabled(boolean z) throws JSONException {
        put("enable_tcp_graceful_shutdown", Boolean.valueOf(z));
    }

    public void setTimer(String str, int i) throws JSONException {
        Map<String, Integer> timerMap = getTimerMap();
        timerMap.put(str, Integer.valueOf(i));
        ArrayList arrayList = new ArrayList();
        for (Map.Entry<String, Integer> entry : timerMap.entrySet()) {
            arrayList.add(entry.getKey() + ":" + entry.getValue());
        }
        put("timer", TextUtils.join(",", arrayList));
    }

    public void setTimer1(int i) throws JSONException {
        setTimer("1", i);
    }

    public void setTimer2(int i) throws JSONException {
        setTimer("2", i);
    }

    public void setTimer4(int i) throws JSONException {
        setTimer("4", i);
    }

    public void setTimerA(int i) throws JSONException {
        setTimer(TIMER_NAME_A, i);
    }

    public void setTimerB(int i) throws JSONException {
        setTimer(TIMER_NAME_B, i);
    }

    public void setTimerC(int i) throws JSONException {
        setTimer(TIMER_NAME_C, i);
    }

    public void setTimerD(int i) throws JSONException {
        setTimer(TIMER_NAME_D, i);
    }

    public void setTimerE(int i) throws JSONException {
        setTimer(TIMER_NAME_E, i);
    }

    public void setTimerF(int i) throws JSONException {
        setTimer(TIMER_NAME_F, i);
    }

    public void setTimerG(int i) throws JSONException {
        setTimer(TIMER_NAME_G, i);
    }

    public void setTimerH(int i) throws JSONException {
        setTimer(TIMER_NAME_H, i);
    }

    public void setTimerI(int i) throws JSONException {
        setTimer(TIMER_NAME_I, i);
    }

    public void setTimerJ(int i) throws JSONException {
        setTimer(TIMER_NAME_J, i);
    }

    public void setTimerK(int i) throws JSONException {
        setTimer(TIMER_NAME_K, i);
    }

    public void setTransport(int i) throws JSONException {
        if (i == 1) {
            put("transport", "udp-preferred");
            return;
        }
        if (i == 2) {
            put("transport", "udp");
        } else if (i == 3) {
            put("transport", "tcp");
        } else {
            if (i != 4) {
                throw new IllegalArgumentException("wrong transport type");
            }
            put("transport", "tls");
        }
    }

    public void setUicclessEmergency(boolean z) throws JSONException {
        put("uiccless_emergency", Boolean.valueOf(z));
    }

    public void setUse200offerWhenRemoteNotSupport100rel(boolean z) throws JSONException {
        put("use_200offer_when_remote_not_support_100rel", Boolean.valueOf(z));
    }

    public void setUseQ850causeOn480(boolean z) throws JSONException {
        put("use_q850cause_on_480", Boolean.valueOf(z));
    }

    public void setVceConfigEnabled(boolean z) throws JSONException {
        put("vce_config_enabled", Boolean.valueOf(z));
    }

    public void setVideoPortEnd(int i) throws JSONException {
        put("video_port_end", Integer.valueOf(i));
    }

    public void setVideoPortStart(int i) throws JSONException {
        put("video_port_start", Integer.valueOf(i));
    }

    public void setVideoSrtp(int i) throws JSONException {
        put("video_srtp", Integer.valueOf(i));
    }

    public boolean shouldUseCompactHeader() {
        return getAsBoolean("sip_compact_header").booleanValue();
    }

    public void splitNetwork() throws JSONException {
        JSONArray jSONArray = new JSONArray();
        JSONArray jSONArray2 = this.mBody.getJSONArray("network");
        if (jSONArray2 != null) {
            for (int i = 0; i < jSONArray2.length(); i++) {
                JSONObject jSONObjectOptJSONObject = jSONArray2.optJSONObject(i);
                for (String str : TextUtils.split(jSONObjectOptJSONObject.optString("type"), ",")) {
                    JSONObject jSONObject = new JSONObject(jSONObjectOptJSONObject, new String[]{"services", "enabled", "dereg_timeout"});
                    jSONObject.put("type", str);
                    jSONArray.put(jSONObject);
                }
            }
            this.mBody.put("network", jSONArray);
        }
    }

    public String toJson() {
        return this.mBody.toString();
    }

    public String toString() {
        return "Name : " + getName() + ", enabled : " + getEnableStatus() + ", pdn : " + getPdn() + ", transport : " + getTransportName() + ", roaming : " + isAllowedOnRoaming() + ", scmversion : " + getScmVersion() + ", selfport : " + getSelfPort() + ", emergency : " + hasEmergencySupport() + ", hashAlgoType : " + getHashAlgoType();
    }

    public void update(ContentValues contentValues) throws JSONException {
        try {
            for (String str : contentValues.keySet()) {
                String asString = contentValues.getAsString(str);
                if (asString != null) {
                    if ("useragent".equals(str) || !asString.matches("\\[.*\\]")) {
                        this.mBody.put(str, contentValues.get(str));
                    } else {
                        this.mBody.put(str, new JSONArray(asString));
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(toJson());
    }

    private ImsProfile() {
    }

    public static int getNetworkType(String str) {
        return NETWORK_TYPE.from(str).mType;
    }

    public static int getRcsProfileType(String str) {
        return RCS_PROFILE.getProfileType(str);
    }

    public static boolean hasChatService(ImsProfile imsProfile, int i) {
        return hasChatService(imsProfile, NETWORK_TYPE.from(i));
    }

    public static boolean hasRcsService(ImsProfile imsProfile, int i) {
        return hasRcsService(imsProfile, NETWORK_TYPE.from(i));
    }

    public static boolean hasVolteService(ImsProfile imsProfile, int i) {
        return hasVolteService(imsProfile, NETWORK_TYPE.from(i));
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public ImsProfile m3437clone() throws CloneNotSupportedException {
        return (ImsProfile) super.clone();
    }

    public Integer getAsInteger(String str, int i) {
        return Integer.valueOf(this.mBody.optInt(str, i));
    }

    public JSONObject getNetwork(String str) throws JSONException {
        try {
            JSONArray jSONArray = this.mBody.getJSONArray("network");
            if (jSONArray == null) {
                return null;
            }
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                if (TextUtils.equals(jSONObject.optString("type"), str)) {
                    return jSONObject;
                }
            }
            return null;
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getClass().getSimpleName() + "!! " + e.getMessage());
            return null;
        }
    }

    public enum NETWORK_TYPE {
        UNKNOWN(0),
        GPRS(1),
        EDGE(2),
        UMTS(3),
        CDMA(4),
        EVDO_0(5),
        EVDO_A(6),
        _1XRTT(7),
        HSDPA(8),
        HSUPA(9),
        HSPA(10),
        EVDO_B(12),
        LTE(13),
        EHRPD(14),
        HSPAP(15),
        GSM(16),
        TDSCDMA(17),
        WIFI(18),
        NR(20),
        ALL(100);

        private int mType;

        NETWORK_TYPE(int i) {
            this.mType = i;
        }

        public static NETWORK_TYPE from(int i) {
            for (NETWORK_TYPE network_type : values()) {
                if (network_type.mType == i) {
                    return network_type;
                }
            }
            return UNKNOWN;
        }

        public boolean isOneOf(NETWORK_TYPE... network_typeArr) {
            for (NETWORK_TYPE network_type : network_typeArr) {
                if (this == network_type) {
                    return true;
                }
            }
            return false;
        }

        @Override // java.lang.Enum
        public String toString() {
            int iOrdinal = ordinal();
            return iOrdinal != 7 ? iOrdinal != 14 ? super.toString().toLowerCase(Locale.US) : "hspa+" : "1xrtt";
        }

        public static NETWORK_TYPE from(String str) {
            for (NETWORK_TYPE network_type : values()) {
                if (network_type.toString().equalsIgnoreCase(str)) {
                    return network_type;
                }
            }
            return UNKNOWN;
        }
    }

    private ImsProfile(Parcel parcel) {
        fromJson(parcel.readString());
    }

    public static boolean hasChatService(ImsProfile imsProfile, NETWORK_TYPE network_type) {
        return new HashSet(Arrays.asList(chatServices)).removeAll(imsProfile.getServiceSet(network_type));
    }

    public static boolean hasRcsService(ImsProfile imsProfile, NETWORK_TYPE network_type) {
        return new HashSet(Arrays.asList(rcsServices)).removeAll(imsProfile.getServiceSet(network_type));
    }

    public static boolean hasVolteService(ImsProfile imsProfile, NETWORK_TYPE network_type) {
        return new HashSet(Arrays.asList(volteServices)).removeAll(imsProfile.getServiceSet(network_type));
    }

    public boolean hasService(String str, int i) {
        if (i != -1 && i != 0) {
            Map<Integer, Set<String>> allServiceSet = getAllServiceSet();
            if (allServiceSet.containsKey(Integer.valueOf(i))) {
                return allServiceSet.get(Integer.valueOf(i)).contains(str);
            }
            return false;
        }
        return hasService(str);
    }

    public void put(String str, Object obj) throws JSONException {
        try {
            this.mBody.put(str, obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Set<String> getServiceSet(Integer num) {
        return getServiceSet(num, false);
    }

    public ImsProfile(String str) {
        fromJson(str);
    }

    public Set<String> getServiceSet(Integer num, boolean z) {
        ArraySet arraySet = new ArraySet();
        JSONObject network = getNetwork(num.intValue());
        if (network != null && (network.optBoolean("enabled") || z)) {
            for (int i = 0; i < network.optJSONArray("services").length(); i++) {
                arraySet.add(network.optJSONArray("services").optString(i));
            }
        }
        return arraySet;
    }

    public void put(String str, Integer num) throws JSONException {
        try {
            this.mBody.put(str, num);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ImsProfile(ImsProfile imsProfile) {
        this(imsProfile.toJson());
    }

    public void put(String str, String str2) throws JSONException {
        try {
            if (this.mBody.opt(str) instanceof JSONArray) {
                this.mBody.put(str, new JSONArray(str2));
            } else {
                this.mBody.put(str, str2);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ImsProfile(ContentValues contentValues) throws JSONException {
        this.mBody = new JSONObject();
        update(contentValues);
    }

    public void setNetworkEnabled(String str, boolean z) throws JSONException {
        setNetworkEnabled(getNetworkType(str), z);
    }
}
