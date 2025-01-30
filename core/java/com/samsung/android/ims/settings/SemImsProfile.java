package com.samsung.android.ims.settings;

import android.content.ContentValues;
import android.location.LocationManager;
import android.p009os.Parcel;
import android.p009os.Parcelable;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class SemImsProfile implements Parcelable {
    public static final Parcelable.Creator<SemImsProfile> CREATOR = new Parcelable.Creator<SemImsProfile>() { // from class: com.samsung.android.ims.settings.SemImsProfile.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemImsProfile createFromParcel(Parcel in) {
            return new SemImsProfile(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemImsProfile[] newArray(int size) {
            return new SemImsProfile[size];
        }
    };
    private static final String LOG_TAG = "SemImsProfile";
    private JSONObject mBody;

    public static class ImsCategory {
        public static final String RCS_SERVICE = "rcs";
        public static final String VOLTE_SERVICE = "volte";
    }

    public static class RcsProfileType {
        public static final String RCS_PROFILE_NONE = "";
        public static final String RCS_PROFILE_TYPE_JOYN_BLACKBIRD = "joyn_blackbird";
        public static final String RCS_PROFILE_TYPE_JOYN_CPR = "joyn_cpr";
        public static final String RCS_PROFILE_TYPE_NAGUIDELINES = "NAGuidelines";
        public static final String RCS_PROFILE_TYPE_UP = "UP";
        public static final String RCS_PROFILE_TYPE_UP10 = "UP_1.0";
        public static final String RCS_PROFILE_TYPE_UP20 = "UP_2.0";
        public static final String RCS_PROFILE_TYPE_UP21 = "UP_2.1";
        public static final String RCS_PROFILE_TYPE_UP22 = "UP_2.2";
        public static final String RCS_PROFILE_TYPE_UP23 = "UP_2.3";
        public static final String RCS_PROFILE_TYPE_UP2_PREFIX = "UP_2";
        public static final String RCS_PROFILE_TYPE_UP30 = "UP_3.0";
        public static final String RCS_PROFILE_TYPE_UPT = "UP_T";
    }

    public static class ImsFeature {
        public static final String CDPN = "cdpn";

        /* renamed from: IM */
        public static final String f3007IM = "im";
        public static final String MMTEL_VOICE = "mmtel";
        public static final String MMTEL_VOICE_VIDEO = "mmtel-video";
        public static final String OPTIONS = "options";
        public static final String PRESENCE = "presence";
        public static final String PROFILE = "profile";
        public static final String SMSIP = "smsip";

        /* renamed from: SS */
        public static final String f3009SS = "ss";
        public static final String MMTEL_CALL_COMPOSER = "mmtel-call-composer";
        protected static final String[] volteServices = {"mmtel", "mmtel-video", MMTEL_CALL_COMPOSER, "smsip", "ss", "cdpn"};

        /* renamed from: FT */
        public static final String f3006FT = "ft";
        public static final String FT_HTTP = "ft_http";
        public static final String SLM = "slm";

        /* renamed from: IS */
        public static final String f3008IS = "is";

        /* renamed from: VS */
        public static final String f3010VS = "vs";
        public static final String EUC = "euc";
        public static final String GLS = "gls";

        /* renamed from: EC */
        public static final String f3005EC = "ec";
        public static final String CHATBOT_COMMUNICATION = "chatbot-communication";
        public static final String PLUG_IN = "plug-in";
        public static final String LASTSEEN = "lastseen";
        protected static final String[] rcsServices = {"options", "presence", "im", f3006FT, FT_HTTP, SLM, f3008IS, f3010VS, EUC, GLS, "profile", f3005EC, CHATBOT_COMMUNICATION, PLUG_IN, LASTSEEN};
        public static final String XDM = "xdm";
        private static final String[] mImsFeatureList = {"mmtel-video", "mmtel", "smsip", SLM, "im", f3006FT, FT_HTTP, f3008IS, f3010VS, "options", "presence", XDM, EUC};

        public static boolean isValidImsFeature(String queriedFeature) {
            for (String feature : mImsFeatureList) {
                if (feature.equals(queriedFeature)) {
                    return true;
                }
            }
            return false;
        }

        public static String[] getVoLteServiceList() {
            return volteServices;
        }

        public static String[] getRcsServiceList() {
            return rcsServices;
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

        NETWORK_TYPE(int type) {
            this.mType = 0;
            this.mType = type;
        }

        public static NETWORK_TYPE from(int type) {
            for (NETWORK_TYPE n : values()) {
                if (n.mType == type) {
                    return n;
                }
            }
            return UNKNOWN;
        }

        public static NETWORK_TYPE from(String typeName) {
            for (NETWORK_TYPE n : values()) {
                if (n.toString().equalsIgnoreCase(typeName)) {
                    return n;
                }
            }
            return UNKNOWN;
        }

        @Override // java.lang.Enum
        public String toString() {
            switch (C51272.f3004x1461984[ordinal()]) {
                case 1:
                    return "1xrtt";
                case 2:
                    return "hspa+";
                default:
                    String rtn = super.toString().toLowerCase(Locale.US);
                    return rtn;
            }
        }

        public boolean isOneOf(NETWORK_TYPE... types) {
            if (types != null) {
                for (NETWORK_TYPE type : types) {
                    if (this == type) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    /* renamed from: com.samsung.android.ims.settings.SemImsProfile$2 */
    static /* synthetic */ class C51272 {

        /* renamed from: $SwitchMap$com$samsung$android$ims$settings$SemImsProfile$NETWORK_TYPE */
        static final /* synthetic */ int[] f3004x1461984;

        static {
            int[] iArr = new int[NETWORK_TYPE.values().length];
            f3004x1461984 = iArr;
            try {
                iArr[NETWORK_TYPE._1XRTT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f3004x1461984[NETWORK_TYPE.HSPAP.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    public boolean hasService(String service) {
        for (Set<String> s : getAllServiceSet().values()) {
            if (s.contains(service)) {
                return true;
            }
        }
        return false;
    }

    private SemImsProfile(Parcel in) {
        fromJson(in.readString());
    }

    public SemImsProfile(String json) {
        fromJson(json);
    }

    public SemImsProfile(SemImsProfile profile) {
        if (profile != null) {
            fromJson(profile.toJson());
        }
    }

    public SemImsProfile(ContentValues cv) {
        this.mBody = new JSONObject();
        update(cv);
    }

    private void update(ContentValues cv) {
        if (cv != null) {
            try {
                for (String key : cv.keySet()) {
                    String val = cv.getAsString(key);
                    if (val != null) {
                        if (val.matches("\\[\\{.*\\}\\]")) {
                            this.mBody.put(key, new JSONArray(val));
                        } else {
                            this.mBody.put(key, cv.get(key));
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void fromJson(String json) {
        if (json != null) {
            try {
                this.mBody = new JSONObject(json);
                splitNetwork();
            } catch (IllegalArgumentException | JSONException e) {
                this.mBody = new JSONObject();
                e.printStackTrace();
            }
        }
    }

    private String toJson() {
        return this.mBody.toString();
    }

    protected void splitNetwork() throws JSONException {
        JSONArray newNetwork = new JSONArray();
        JSONArray network = this.mBody.getJSONArray(LocationManager.NETWORK_PROVIDER);
        if (network != null) {
            for (int i = 0; i < network.length(); i++) {
                JSONObject obj = network.optJSONObject(i);
                String types = obj.optString("type");
                for (String s : TextUtils.split(types, ",")) {
                    JSONObject tmp = new JSONObject(obj, new String[]{"services", "enabled", "dereg_timeout"});
                    tmp.put("type", s);
                    newNetwork.put(tmp);
                }
            }
            this.mBody.put(LocationManager.NETWORK_PROVIDER, newNetwork);
        }
    }

    private static int getNetworkType(String name) {
        return NETWORK_TYPE.from(name).mType;
    }

    private Map<Integer, Set<String>> getAllServiceSet() {
        Map<Integer, Set<String>> result = new ArrayMap<>();
        JSONArray network = this.mBody.optJSONArray(LocationManager.NETWORK_PROVIDER);
        if (network != null) {
            for (int i = 0; i < network.length(); i++) {
                JSONObject n = network.optJSONObject(i);
                JSONArray serviceArr = n.optJSONArray("services");
                if (serviceArr == null) {
                    Log.m96e(LOG_TAG, "getAllServiceSet: No services array in " + n.toString());
                } else {
                    Set<String> services = new ArraySet<>();
                    for (int j = 0; j < serviceArr.length(); j++) {
                        services.add(serviceArr.optString(j));
                    }
                    result.put(Integer.valueOf(getNetworkType(n.optString("type"))), services);
                }
            }
        }
        return result;
    }

    @Override // android.p009os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(toJson());
    }

    @Override // android.p009os.Parcelable
    public int describeContents() {
        return 0;
    }

    private void put(String key, Boolean value) {
        try {
            this.mBody.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean hasEmergencySupport() {
        return getAsBoolean("emergency_support").booleanValue();
    }

    private Boolean getAsBoolean(String key) {
        return Boolean.valueOf(this.mBody.optBoolean(key));
    }
}
