package android.telephony.data;

import android.annotation.SystemApi;
import android.app.admin.PreferentialNetworkServiceConfig$$ExternalSyntheticLambda2;
import android.content.ContentValues;
import android.content.IntentFilter;
import android.database.Cursor;
import android.media.audio.common.AudioChannelLayout;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.Telephony;
import android.telephony.CarrierConfigManager;
import android.telephony.ServiceState;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import com.android.internal.telephony.util.TelephonyUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

/* loaded from: classes4.dex */
public class ApnSetting implements Parcelable {
    private static final Map<Integer, String> APN_TYPE_INT_MAP;
    private static final Map<String, Integer> APN_TYPE_STRING_MAP;
    public static final int AUTH_TYPE_CHAP = 2;
    public static final int AUTH_TYPE_NONE = 0;
    public static final int AUTH_TYPE_PAP = 1;
    public static final int AUTH_TYPE_PAP_OR_CHAP = 3;
    public static final int AUTH_TYPE_UNKNOWN = -1;
    public static final Parcelable.Creator<ApnSetting> CREATOR;
    public static final int INFRASTRUCTURE_CELLULAR = 1;
    public static final int INFRASTRUCTURE_SATELLITE = 2;
    private static final String LOG_TAG = "ApnSetting";
    public static final int MVNO_TYPE_GID = 2;
    public static final int MVNO_TYPE_ICCID = 3;
    public static final int MVNO_TYPE_IMSI = 1;
    private static final Map<Integer, String> MVNO_TYPE_INT_MAP;
    public static final int MVNO_TYPE_SPN = 0;
    private static final Map<String, Integer> MVNO_TYPE_STRING_MAP;
    public static final int MVNO_TYPE_UNKNOWN = -1;
    private static final Map<Integer, String> PROTOCOL_INT_MAP;
    public static final int PROTOCOL_IP = 0;
    public static final int PROTOCOL_IPV4V6 = 2;
    public static final int PROTOCOL_IPV6 = 1;
    public static final int PROTOCOL_NON_IP = 4;
    public static final int PROTOCOL_PPP = 3;
    private static final Map<String, Integer> PROTOCOL_STRING_MAP;
    public static final int PROTOCOL_UNKNOWN = -1;
    public static final int PROTOCOL_UNSTRUCTURED = 5;
    public static final int TYPE_ALL = 255;

    @SystemApi
    public static final String TYPE_ALL_STRING = "*";
    public static final int TYPE_BANDWIDTH = 1048576;
    public static final int TYPE_BIP = 8192;

    @SystemApi
    public static final String TYPE_BIP_STRING = "bip";
    public static final int TYPE_CBS = 128;

    @SystemApi
    public static final String TYPE_CBS_STRING = "cbs";
    public static final int TYPE_DEFAULT = 17;

    @SystemApi
    public static final String TYPE_DEFAULT_STRING = "default";
    public static final int TYPE_DUN = 8;

    @SystemApi
    public static final String TYPE_DUN_STRING = "dun";
    public static final int TYPE_EMERGENCY = 512;

    @SystemApi
    public static final String TYPE_EMERGENCY_STRING = "emergency";
    public static final int TYPE_ENT1 = 262144;
    public static final String TYPE_ENT1_STRING = "ent1";
    public static final int TYPE_ENTERPRISE = 16384;

    @SystemApi
    public static final String TYPE_ENTERPRISE_STRING = "enterprise";
    public static final int TYPE_FOTA = 32;

    @SystemApi
    public static final String TYPE_FOTA_STRING = "fota";
    public static final int TYPE_HIPRI = 16;

    @SystemApi
    public static final String TYPE_HIPRI_STRING = "hipri";
    public static final int TYPE_IA = 256;

    @SystemApi
    public static final String TYPE_IA_STRING = "ia";
    public static final int TYPE_IMS = 64;

    @SystemApi
    public static final String TYPE_IMS_STRING = "ims";
    public static final int TYPE_LATENCY = 524288;
    public static final int TYPE_MCX = 1024;

    @SystemApi
    public static final String TYPE_MCX_STRING = "mcx";
    public static final int TYPE_MMS = 2;

    @SystemApi
    public static final String TYPE_MMS_STRING = "mms";
    public static final int TYPE_NONE = 0;
    public static final int TYPE_OEM_PAID = 65536;

    @SystemApi
    public static final String TYPE_OEM_PAID_STRING = "oem_paid";
    public static final int TYPE_OEM_PRIVATE = 131072;

    @SystemApi
    public static final String TYPE_OEM_PRIVATE_STRING = "oem_private";
    public static final int TYPE_RCS = 32768;

    @SystemApi
    public static final String TYPE_RCS_STRING = "rcs";
    public static final int TYPE_SUPL = 4;

    @SystemApi
    public static final String TYPE_SUPL_STRING = "supl";
    private static final int TYPE_VENDOR = 131072;
    public static final int TYPE_VSIM = 4096;

    @SystemApi
    public static final String TYPE_VSIM_STRING = "vsim";
    public static final int TYPE_XCAP = 2048;

    @SystemApi
    public static final String TYPE_XCAP_STRING = "xcap";
    public static final int UNSET_MTU = 0;
    private static final int UNSPECIFIED_INT = -1;
    private static final String UNSPECIFIED_STRING = "";
    private static final String V2_FORMAT_REGEX = "^\\[ApnSettingV2\\]\\s*";
    private static final String V3_FORMAT_REGEX = "^\\[ApnSettingV3\\]\\s*";
    private static final String V4_FORMAT_REGEX = "^\\[ApnSettingV4\\]\\s*";
    private static final String V5_FORMAT_REGEX = "^\\[ApnSettingV5\\]\\s*";
    private static final String V6_FORMAT_REGEX = "^\\[ApnSettingV6\\]\\s*";
    private static final String V7_FORMAT_REGEX = "^\\[ApnSettingV7\\]\\s*";
    private static final boolean VDBG = false;
    private final boolean mAlwaysOn;
    private final String mApnName;
    private final int mApnSetId;
    private final int mApnTypeBitmask;
    private final int mAuthType;
    private final boolean mCarrierEnabled;
    private final int mCarrierId;
    private final int mEditedStatus;
    private final String mEntryName;
    private final boolean mEsimBootstrapProvisioning;
    private final int mId;
    private final int mInfrastructureBitmask;
    private final long mLingeringNetworkTypeBitmask;
    private final int mMaxConns;
    private final int mMaxConnsTime;
    private final String mMmsProxyAddress;
    private final int mMmsProxyPort;
    private final Uri mMmsc;
    private final int mMtuV4;
    private final int mMtuV6;
    private final String mMvnoMatchData;
    private final int mMvnoType;
    private final int mNetworkTypeBitmask;
    private final String mOperatorNumeric;
    private final String mPassword;
    private boolean mPermanentFailed;
    private final boolean mPersistent;
    private final int mProfileId;
    private final int mProtocol;
    private final String mProxyAddress;
    private final int mProxyPort;
    private final int mRoamingProtocol;
    private final int mSkip464Xlat;
    private final String mUser;
    private final int mWaitTime;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ApnType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ApnTypeString {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface AuthType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface InfrastructureBitmask {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface MvnoType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ProtocolType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Skip464XlatStatus {
    }

    private boolean mtuUnsetOrEquals(int i, int i2) {
        return i <= 0 || i2 <= 0 || i == i2;
    }

    private boolean xorEqualsInt(int i, int i2) {
        return i == -1 || i2 == -1 || i == i2;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    static {
        ArrayMap arrayMap = new ArrayMap();
        APN_TYPE_STRING_MAP = arrayMap;
        arrayMap.put("*", 255);
        arrayMap.put("default", 17);
        arrayMap.put("mms", 2);
        arrayMap.put("supl", 4);
        arrayMap.put("dun", 8);
        arrayMap.put("hipri", 16);
        arrayMap.put("fota", 32);
        arrayMap.put("ims", 64);
        arrayMap.put("cbs", 128);
        arrayMap.put("ia", 256);
        arrayMap.put("emergency", 512);
        arrayMap.put("mcx", 1024);
        arrayMap.put("xcap", 2048);
        arrayMap.put(TYPE_ENTERPRISE_STRING, 16384);
        arrayMap.put(TYPE_VSIM_STRING, 4096);
        arrayMap.put("bip", 8192);
        arrayMap.put("rcs", 32768);
        arrayMap.put(TYPE_OEM_PAID_STRING, 65536);
        arrayMap.put(TYPE_OEM_PRIVATE_STRING, 131072);
        arrayMap.put("ent1", 262144);
        ArrayMap arrayMap2 = new ArrayMap();
        APN_TYPE_INT_MAP = arrayMap2;
        arrayMap2.put(17, "default");
        arrayMap2.put(2, "mms");
        arrayMap2.put(4, "supl");
        arrayMap2.put(8, "dun");
        arrayMap2.put(16, "hipri");
        arrayMap2.put(32, "fota");
        arrayMap2.put(64, "ims");
        arrayMap2.put(128, "cbs");
        arrayMap2.put(256, "ia");
        arrayMap2.put(512, "emergency");
        arrayMap2.put(1024, "mcx");
        arrayMap2.put(2048, "xcap");
        arrayMap2.put(16384, TYPE_ENTERPRISE_STRING);
        arrayMap2.put(4096, TYPE_VSIM_STRING);
        arrayMap2.put(8192, "bip");
        arrayMap2.put(32768, "rcs");
        arrayMap2.put(65536, TYPE_OEM_PAID_STRING);
        arrayMap2.put(131072, TYPE_OEM_PRIVATE_STRING);
        arrayMap2.put(262144, "ent1");
        ArrayMap arrayMap3 = new ArrayMap();
        PROTOCOL_STRING_MAP = arrayMap3;
        arrayMap3.put(CarrierConfigManager.Apn.PROTOCOL_IPV4, 0);
        arrayMap3.put("IPV6", 1);
        arrayMap3.put(CarrierConfigManager.Apn.PROTOCOL_IPV4V6, 2);
        arrayMap3.put("PPP", 3);
        arrayMap3.put("NON-IP", 4);
        arrayMap3.put("UNSTRUCTURED", 5);
        ArrayMap arrayMap4 = new ArrayMap();
        PROTOCOL_INT_MAP = arrayMap4;
        arrayMap4.put(0, CarrierConfigManager.Apn.PROTOCOL_IPV4);
        arrayMap4.put(1, "IPV6");
        arrayMap4.put(2, CarrierConfigManager.Apn.PROTOCOL_IPV4V6);
        arrayMap4.put(3, "PPP");
        arrayMap4.put(4, "NON-IP");
        arrayMap4.put(5, "UNSTRUCTURED");
        ArrayMap arrayMap5 = new ArrayMap();
        MVNO_TYPE_STRING_MAP = arrayMap5;
        arrayMap5.put(Telephony.CarrierId.All.SPN, 0);
        arrayMap5.put("imsi", 1);
        arrayMap5.put("gid", 2);
        arrayMap5.put("iccid", 3);
        ArrayMap arrayMap6 = new ArrayMap();
        MVNO_TYPE_INT_MAP = arrayMap6;
        arrayMap6.put(0, Telephony.CarrierId.All.SPN);
        arrayMap6.put(1, "imsi");
        arrayMap6.put(2, "gid");
        arrayMap6.put(3, "iccid");
        CREATOR = new Parcelable.Creator<ApnSetting>() { // from class: android.telephony.data.ApnSetting.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ApnSetting createFromParcel(Parcel parcel) {
                return ApnSetting.readFromParcel(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ApnSetting[] newArray(int i) {
                return new ApnSetting[i];
            }
        };
    }

    public int getMtuV4() {
        return this.mMtuV4;
    }

    public int getMtuV6() {
        return this.mMtuV6;
    }

    public int getProfileId() {
        return this.mProfileId;
    }

    public boolean isPersistent() {
        return this.mPersistent;
    }

    public int getMaxConns() {
        return this.mMaxConns;
    }

    public int getWaitTime() {
        return this.mWaitTime;
    }

    public int getMaxConnsTime() {
        return this.mMaxConnsTime;
    }

    public String getMvnoMatchData() {
        return this.mMvnoMatchData;
    }

    public int getApnSetId() {
        return this.mApnSetId;
    }

    public boolean getPermanentFailed() {
        return this.mPermanentFailed;
    }

    public void setPermanentFailed(boolean z) {
        this.mPermanentFailed = z;
    }

    public String getEntryName() {
        return this.mEntryName;
    }

    public String getApnName() {
        return this.mApnName;
    }

    @Deprecated
    public InetAddress getProxyAddress() {
        return inetAddressFromString(this.mProxyAddress);
    }

    public String getProxyAddressAsString() {
        return this.mProxyAddress;
    }

    public int getProxyPort() {
        return this.mProxyPort;
    }

    public Uri getMmsc() {
        return this.mMmsc;
    }

    @Deprecated
    public InetAddress getMmsProxyAddress() {
        return inetAddressFromString(this.mMmsProxyAddress);
    }

    public String getMmsProxyAddressAsString() {
        return this.mMmsProxyAddress;
    }

    public int getMmsProxyPort() {
        return this.mMmsProxyPort;
    }

    public String getUser() {
        return this.mUser;
    }

    public String getPassword() {
        return this.mPassword;
    }

    public int getAuthType() {
        return this.mAuthType;
    }

    public int getApnTypeBitmask() {
        return this.mApnTypeBitmask;
    }

    public int getId() {
        return this.mId;
    }

    public String getOperatorNumeric() {
        return this.mOperatorNumeric;
    }

    public int getProtocol() {
        return this.mProtocol;
    }

    public int getRoamingProtocol() {
        return this.mRoamingProtocol;
    }

    public boolean isEnabled() {
        return this.mCarrierEnabled;
    }

    public int getNetworkTypeBitmask() {
        return this.mNetworkTypeBitmask;
    }

    public long getLingeringNetworkTypeBitmask() {
        return this.mLingeringNetworkTypeBitmask;
    }

    public int getMvnoType() {
        return this.mMvnoType;
    }

    public int getCarrierId() {
        return this.mCarrierId;
    }

    public int getSkip464Xlat() {
        return this.mSkip464Xlat;
    }

    public boolean isAlwaysOn() {
        return this.mAlwaysOn;
    }

    public boolean isForInfrastructure(int i) {
        return (this.mInfrastructureBitmask & i) != 0;
    }

    public int getInfrastructureBitmask() {
        return this.mInfrastructureBitmask;
    }

    public boolean isEsimBootstrapProvisioning() {
        return this.mEsimBootstrapProvisioning;
    }

    public int getEditedStatus() {
        return this.mEditedStatus;
    }

    private ApnSetting(Builder builder) {
        int i = 0;
        this.mPermanentFailed = false;
        this.mEntryName = builder.mEntryName;
        this.mApnName = builder.mApnName;
        this.mProxyAddress = builder.mProxyAddress;
        this.mProxyPort = builder.mProxyPort;
        this.mMmsc = builder.mMmsc;
        this.mMmsProxyAddress = builder.mMmsProxyAddress;
        this.mMmsProxyPort = builder.mMmsProxyPort;
        this.mUser = builder.mUser;
        this.mPassword = builder.mPassword;
        if (builder.mAuthType != -1) {
            i = builder.mAuthType;
        } else if (!TextUtils.isEmpty(builder.mUser)) {
            i = 3;
        }
        this.mAuthType = i;
        this.mApnTypeBitmask = builder.mApnTypeBitmask;
        this.mId = builder.mId;
        this.mOperatorNumeric = builder.mOperatorNumeric;
        this.mProtocol = builder.mProtocol;
        this.mRoamingProtocol = builder.mRoamingProtocol;
        this.mMtuV4 = builder.mMtuV4;
        this.mMtuV6 = builder.mMtuV6;
        this.mCarrierEnabled = builder.mCarrierEnabled;
        this.mNetworkTypeBitmask = builder.mNetworkTypeBitmask;
        this.mLingeringNetworkTypeBitmask = builder.mLingeringNetworkTypeBitmask;
        this.mProfileId = builder.mProfileId;
        this.mPersistent = builder.mModemCognitive;
        this.mMaxConns = builder.mMaxConns;
        this.mWaitTime = builder.mWaitTime;
        this.mMaxConnsTime = builder.mMaxConnsTime;
        this.mMvnoType = builder.mMvnoType;
        this.mMvnoMatchData = builder.mMvnoMatchData;
        this.mApnSetId = builder.mApnSetId;
        this.mCarrierId = builder.mCarrierId;
        this.mSkip464Xlat = builder.mSkip464Xlat;
        this.mAlwaysOn = builder.mAlwaysOn;
        this.mInfrastructureBitmask = builder.mInfrastructureBitmask;
        this.mEsimBootstrapProvisioning = builder.mEsimBootstrapProvisioning;
        this.mEditedStatus = builder.mEditedStatus;
    }

    public static ApnSetting makeApnSetting(Cursor cursor) {
        int apnTypesBitmaskFromString = getApnTypesBitmaskFromString(cursor.getString(cursor.getColumnIndexOrThrow("type")));
        int iConvertBearerBitmaskToNetworkTypeBitmask = cursor.getInt(cursor.getColumnIndexOrThrow(Telephony.Carriers.NETWORK_TYPE_BITMASK));
        if (iConvertBearerBitmaskToNetworkTypeBitmask == 0) {
            iConvertBearerBitmaskToNetworkTypeBitmask = ServiceState.convertBearerBitmaskToNetworkTypeBitmask(cursor.getInt(cursor.getColumnIndexOrThrow(Telephony.Carriers.BEARER_BITMASK)));
        }
        int i = cursor.getInt(cursor.getColumnIndexOrThrow(Telephony.Carriers.MTU_V4));
        if (i == 0) {
            i = cursor.getInt(cursor.getColumnIndexOrThrow("mtu"));
        }
        return new Builder().setId(cursor.getInt(cursor.getColumnIndexOrThrow("_id"))).setOperatorNumeric(cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Carriers.NUMERIC))).setEntryName(cursor.getString(cursor.getColumnIndexOrThrow("name"))).setApnName(cursor.getString(cursor.getColumnIndexOrThrow("apn"))).setProxyAddress(cursor.getString(cursor.getColumnIndexOrThrow("proxy"))).setProxyPort(portFromString(cursor.getString(cursor.getColumnIndexOrThrow("port")))).setMmsc(UriFromString(cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Carriers.MMSC)))).setMmsProxyAddress(cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Carriers.MMSPROXY))).setMmsProxyPort(portFromString(cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Carriers.MMSPORT)))).setUser(cursor.getString(cursor.getColumnIndexOrThrow("user"))).setPassword(cursor.getString(cursor.getColumnIndexOrThrow("password"))).setAuthType(cursor.getInt(cursor.getColumnIndexOrThrow(Telephony.Carriers.AUTH_TYPE))).setApnTypeBitmask(apnTypesBitmaskFromString).setProtocol(getProtocolIntFromString(cursor.getString(cursor.getColumnIndexOrThrow("protocol")))).setRoamingProtocol(getProtocolIntFromString(cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Carriers.ROAMING_PROTOCOL)))).setCarrierEnabled(cursor.getInt(cursor.getColumnIndexOrThrow(Telephony.Carriers.CARRIER_ENABLED)) == 1).setNetworkTypeBitmask(iConvertBearerBitmaskToNetworkTypeBitmask).setLingeringNetworkTypeBitmask(cursor.getInt(cursor.getColumnIndexOrThrow(Telephony.Carriers.LINGERING_NETWORK_TYPE_BITMASK))).setProfileId(cursor.getInt(cursor.getColumnIndexOrThrow(Telephony.Carriers.PROFILE_ID))).setModemCognitive(cursor.getInt(cursor.getColumnIndexOrThrow(Telephony.Carriers.MODEM_PERSIST)) == 1).setMaxConns(cursor.getInt(cursor.getColumnIndexOrThrow(Telephony.Carriers.MAX_CONNECTIONS))).setWaitTime(cursor.getInt(cursor.getColumnIndexOrThrow(Telephony.Carriers.WAIT_TIME_RETRY))).setMaxConnsTime(cursor.getInt(cursor.getColumnIndexOrThrow(Telephony.Carriers.TIME_LIMIT_FOR_MAX_CONNECTIONS))).setMtuV4(i).setMtuV6(cursor.getInt(cursor.getColumnIndexOrThrow(Telephony.Carriers.MTU_V6))).setMvnoType(getMvnoTypeIntFromString(cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Carriers.MVNO_TYPE)))).setMvnoMatchData(cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Carriers.MVNO_MATCH_DATA))).setApnSetId(cursor.getInt(cursor.getColumnIndexOrThrow(Telephony.Carriers.APN_SET_ID))).setCarrierId(cursor.getInt(cursor.getColumnIndexOrThrow("carrier_id"))).setSkip464Xlat(cursor.getInt(cursor.getColumnIndexOrThrow(Telephony.Carriers.SKIP_464XLAT))).setAlwaysOn(cursor.getInt(cursor.getColumnIndexOrThrow(Telephony.Carriers.ALWAYS_ON)) == 1).setInfrastructureBitmask(cursor.getInt(cursor.getColumnIndexOrThrow(Telephony.Carriers.INFRASTRUCTURE_BITMASK))).setEsimBootstrapProvisioning(cursor.getInt(cursor.getColumnIndexOrThrow(Telephony.Carriers.ESIM_BOOTSTRAP_PROVISIONING)) == 1).setEditedStatus(cursor.getInt(cursor.getColumnIndexOrThrow(Telephony.Carriers.EDITED_STATUS))).buildWithoutCheck();
    }

    public static ApnSetting makeApnSetting(ApnSetting apnSetting) {
        return makeApnSetting(apnSetting, false);
    }

    public static ApnSetting makeApnSetting(ApnSetting apnSetting, boolean z) {
        return new Builder().setId(apnSetting.mId).setOperatorNumeric(apnSetting.mOperatorNumeric).setEntryName(apnSetting.mEntryName).setApnName(apnSetting.mApnName).setProxyAddress(apnSetting.mProxyAddress).setProxyPort(apnSetting.mProxyPort).setMmsc(apnSetting.mMmsc).setMmsProxyAddress(apnSetting.mMmsProxyAddress).setMmsProxyPort(apnSetting.mMmsProxyPort).setUser(apnSetting.mUser).setPassword(apnSetting.mPassword).setAuthType(apnSetting.mAuthType).setApnTypeBitmask(z ? apnSetting.mApnTypeBitmask & (-513) : apnSetting.mApnTypeBitmask).setProtocol(apnSetting.mProtocol).setRoamingProtocol(apnSetting.mRoamingProtocol).setCarrierEnabled(apnSetting.mCarrierEnabled).setNetworkTypeBitmask(apnSetting.mNetworkTypeBitmask).setLingeringNetworkTypeBitmask(apnSetting.mLingeringNetworkTypeBitmask).setProfileId(apnSetting.mProfileId).setModemCognitive(apnSetting.mPersistent).setMaxConns(apnSetting.mMaxConns).setWaitTime(apnSetting.mWaitTime).setMaxConnsTime(apnSetting.mMaxConnsTime).setMtuV4(apnSetting.mMtuV4).setMtuV6(apnSetting.mMtuV6).setMvnoType(apnSetting.mMvnoType).setMvnoMatchData(apnSetting.mMvnoMatchData).setApnSetId(apnSetting.mApnSetId).setCarrierId(apnSetting.mCarrierId).setSkip464Xlat(apnSetting.mSkip464Xlat).setAlwaysOn(apnSetting.mAlwaysOn).setInfrastructureBitmask(apnSetting.mInfrastructureBitmask).setEsimBootstrapProvisioning(apnSetting.mEsimBootstrapProvisioning).setEditedStatus(apnSetting.mEditedStatus).buildWithoutCheck();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[ApnSetting] ");
        sb.append(this.mEntryName);
        sb.append(", ");
        sb.append(this.mId);
        sb.append(", ");
        sb.append(this.mOperatorNumeric);
        sb.append(", ");
        sb.append(this.mApnName);
        sb.append(", ");
        sb.append(this.mProxyAddress);
        sb.append(", ");
        sb.append(UriToString(this.mMmsc));
        sb.append(", ");
        sb.append(this.mMmsProxyAddress);
        sb.append(", ");
        sb.append(portToString(this.mMmsProxyPort));
        sb.append(", ");
        sb.append(portToString(this.mProxyPort));
        sb.append(", ");
        sb.append(this.mAuthType);
        sb.append(", ");
        sb.append(TextUtils.join(" | ", getApnTypesStringFromBitmask(this.mApnTypeBitmask).split(",")));
        sb.append(", ");
        Map<Integer, String> map = PROTOCOL_INT_MAP;
        sb.append(map.get(Integer.valueOf(this.mProtocol)));
        sb.append(", ");
        sb.append(map.get(Integer.valueOf(this.mRoamingProtocol)));
        sb.append(", ");
        sb.append(this.mCarrierEnabled);
        sb.append(", ");
        sb.append(this.mProfileId);
        sb.append(", ");
        sb.append(this.mPersistent);
        sb.append(", ");
        sb.append(this.mMaxConns);
        sb.append(", ");
        sb.append(this.mWaitTime);
        sb.append(", ");
        sb.append(this.mMaxConnsTime);
        sb.append(", ");
        sb.append(this.mMtuV4);
        sb.append(", ");
        sb.append(this.mMtuV6);
        sb.append(", ");
        sb.append(MVNO_TYPE_INT_MAP.get(Integer.valueOf(this.mMvnoType)));
        sb.append(", ");
        sb.append(this.mMvnoMatchData);
        sb.append(", ");
        sb.append(this.mPermanentFailed);
        sb.append(", ");
        sb.append(TelephonyManager.convertNetworkTypeBitmaskToString(this.mNetworkTypeBitmask));
        sb.append(", ");
        sb.append(TelephonyManager.convertNetworkTypeBitmaskToString(this.mLingeringNetworkTypeBitmask));
        sb.append(", ");
        sb.append(this.mApnSetId);
        sb.append(", ");
        sb.append(this.mCarrierId);
        sb.append(", ");
        sb.append(this.mSkip464Xlat);
        sb.append(", ");
        sb.append(this.mAlwaysOn);
        sb.append(", ");
        sb.append(this.mInfrastructureBitmask);
        sb.append(", ");
        sb.append(Objects.hash(this.mUser, this.mPassword));
        sb.append(", ");
        sb.append(this.mEsimBootstrapProvisioning);
        sb.append(", ");
        sb.append(TelephonyUtils.apnEditedStatusToString(this.mEditedStatus));
        return sb.toString();
    }

    public String toStringExceptID() {
        StringBuilder sb = new StringBuilder("[ApnSetting] ");
        sb.append(this.mEntryName);
        sb.append(", ");
        sb.append(this.mOperatorNumeric);
        sb.append(", ");
        sb.append(this.mApnName);
        sb.append(", ");
        sb.append(this.mProxyAddress);
        sb.append(", ");
        sb.append(UriToString(this.mMmsc));
        sb.append(", ");
        sb.append(this.mMmsProxyAddress);
        sb.append(", ");
        sb.append(portToString(this.mMmsProxyPort));
        sb.append(", ");
        sb.append(portToString(this.mProxyPort));
        sb.append(", ");
        sb.append(this.mAuthType);
        sb.append(", ");
        sb.append(TextUtils.join(" | ", getApnTypesStringFromBitmask(this.mApnTypeBitmask).split(",")));
        sb.append(", ");
        Map<Integer, String> map = PROTOCOL_INT_MAP;
        sb.append(map.get(Integer.valueOf(this.mProtocol)));
        sb.append(", ");
        sb.append(map.get(Integer.valueOf(this.mRoamingProtocol)));
        sb.append(", ");
        sb.append(this.mCarrierEnabled);
        sb.append(", ");
        sb.append(this.mProfileId);
        sb.append(", ");
        sb.append(this.mPersistent);
        sb.append(", ");
        sb.append(this.mMaxConns);
        sb.append(", ");
        sb.append(this.mWaitTime);
        sb.append(", ");
        sb.append(this.mMaxConnsTime);
        sb.append(", ");
        sb.append(this.mMtuV4);
        sb.append(", ");
        sb.append(this.mMtuV6);
        sb.append(", ");
        sb.append(MVNO_TYPE_INT_MAP.get(Integer.valueOf(this.mMvnoType)));
        sb.append(", ");
        sb.append(this.mMvnoMatchData);
        sb.append(", ");
        sb.append(this.mPermanentFailed);
        sb.append(", ");
        sb.append(TelephonyManager.convertNetworkTypeBitmaskToString(this.mNetworkTypeBitmask));
        sb.append(", ");
        sb.append(TelephonyManager.convertNetworkTypeBitmaskToString(this.mLingeringNetworkTypeBitmask));
        sb.append(", ");
        sb.append(this.mApnSetId);
        sb.append(", ");
        sb.append(this.mCarrierId);
        sb.append(", ");
        sb.append(this.mSkip464Xlat);
        sb.append(", ");
        sb.append(this.mAlwaysOn);
        sb.append(", ");
        sb.append(this.mInfrastructureBitmask);
        sb.append(", ");
        sb.append(Objects.hash(this.mUser, this.mPassword));
        sb.append(", ");
        sb.append(this.mEsimBootstrapProvisioning);
        sb.append(", ");
        sb.append(TelephonyUtils.apnEditedStatusToString(this.mEditedStatus));
        return sb.toString();
    }

    public boolean equalsExceptID(Object obj) {
        if (!(obj instanceof ApnSetting)) {
            return false;
        }
        ApnSetting apnSetting = (ApnSetting) obj;
        return toStringExceptID().equals(apnSetting.toStringExceptID()) && defaultString(this.mUser).equals(defaultString(apnSetting.mUser)) && defaultString(this.mPassword).equals(defaultString(apnSetting.mPassword));
    }

    public String defaultString(String str) {
        return str == null ? "" : str;
    }

    public boolean hasMvnoParams() {
        return (TextUtils.isEmpty(getMvnoTypeStringFromInt(this.mMvnoType)) || TextUtils.isEmpty(this.mMvnoMatchData)) ? false : true;
    }

    private boolean hasApnType(int i) {
        return (this.mApnTypeBitmask & i) == i;
    }

    public boolean isEmergencyApn() {
        return hasApnType(512);
    }

    public boolean canHandleType(int i) {
        if (this.mCarrierEnabled) {
            return hasApnType(i);
        }
        return false;
    }

    private boolean typeSameAny(ApnSetting apnSetting, ApnSetting apnSetting2) {
        return (apnSetting.mApnTypeBitmask & apnSetting2.mApnTypeBitmask) != 0;
    }

    public int hashCode() {
        return Objects.hash(this.mApnName, this.mProxyAddress, Integer.valueOf(this.mProxyPort), this.mMmsc, this.mMmsProxyAddress, Integer.valueOf(this.mMmsProxyPort), this.mUser, this.mPassword, Integer.valueOf(this.mAuthType), Integer.valueOf(this.mApnTypeBitmask), Integer.valueOf(this.mId), this.mOperatorNumeric, Integer.valueOf(this.mProtocol), Integer.valueOf(this.mRoamingProtocol), Integer.valueOf(this.mMtuV4), Integer.valueOf(this.mMtuV6), Boolean.valueOf(this.mCarrierEnabled), Integer.valueOf(this.mNetworkTypeBitmask), Long.valueOf(this.mLingeringNetworkTypeBitmask), Integer.valueOf(this.mProfileId), Boolean.valueOf(this.mPersistent), Integer.valueOf(this.mMaxConns), Integer.valueOf(this.mWaitTime), Integer.valueOf(this.mMaxConnsTime), Integer.valueOf(this.mMvnoType), this.mMvnoMatchData, Integer.valueOf(this.mApnSetId), Integer.valueOf(this.mCarrierId), Integer.valueOf(this.mSkip464Xlat), Boolean.valueOf(this.mAlwaysOn), Integer.valueOf(this.mInfrastructureBitmask), Boolean.valueOf(this.mEsimBootstrapProvisioning));
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ApnSetting)) {
            return false;
        }
        ApnSetting apnSetting = (ApnSetting) obj;
        return this.mEntryName.equals(apnSetting.mEntryName) && this.mId == apnSetting.mId && Objects.equals(this.mOperatorNumeric, apnSetting.mOperatorNumeric) && Objects.equals(this.mApnName, apnSetting.mApnName) && Objects.equals(this.mProxyAddress, apnSetting.mProxyAddress) && Objects.equals(this.mMmsc, apnSetting.mMmsc) && Objects.equals(this.mMmsProxyAddress, apnSetting.mMmsProxyAddress) && this.mMmsProxyPort == apnSetting.mMmsProxyPort && this.mProxyPort == apnSetting.mProxyPort && Objects.equals(this.mUser, apnSetting.mUser) && Objects.equals(this.mPassword, apnSetting.mPassword) && this.mAuthType == apnSetting.mAuthType && this.mApnTypeBitmask == apnSetting.mApnTypeBitmask && this.mProtocol == apnSetting.mProtocol && this.mRoamingProtocol == apnSetting.mRoamingProtocol && this.mCarrierEnabled == apnSetting.mCarrierEnabled && this.mProfileId == apnSetting.mProfileId && this.mPersistent == apnSetting.mPersistent && this.mMaxConns == apnSetting.mMaxConns && this.mWaitTime == apnSetting.mWaitTime && this.mMaxConnsTime == apnSetting.mMaxConnsTime && this.mMtuV4 == apnSetting.mMtuV4 && this.mMtuV6 == apnSetting.mMtuV6 && this.mMvnoType == apnSetting.mMvnoType && Objects.equals(this.mMvnoMatchData, apnSetting.mMvnoMatchData) && this.mNetworkTypeBitmask == apnSetting.mNetworkTypeBitmask && this.mLingeringNetworkTypeBitmask == apnSetting.mLingeringNetworkTypeBitmask && this.mApnSetId == apnSetting.mApnSetId && this.mCarrierId == apnSetting.mCarrierId && this.mSkip464Xlat == apnSetting.mSkip464Xlat && this.mAlwaysOn == apnSetting.mAlwaysOn && this.mInfrastructureBitmask == apnSetting.mInfrastructureBitmask && this.mEsimBootstrapProvisioning == apnSetting.mEsimBootstrapProvisioning;
    }

    public boolean equals(Object obj, boolean z) {
        if (!(obj instanceof ApnSetting)) {
            return false;
        }
        ApnSetting apnSetting = (ApnSetting) obj;
        return this.mEntryName.equals(apnSetting.mEntryName) && Objects.equals(this.mOperatorNumeric, apnSetting.mOperatorNumeric) && Objects.equals(this.mApnName, apnSetting.mApnName) && Objects.equals(this.mProxyAddress, apnSetting.mProxyAddress) && Objects.equals(this.mMmsc, apnSetting.mMmsc) && Objects.equals(this.mMmsProxyAddress, apnSetting.mMmsProxyAddress) && Objects.equals(Integer.valueOf(this.mMmsProxyPort), Integer.valueOf(apnSetting.mMmsProxyPort)) && Objects.equals(Integer.valueOf(this.mProxyPort), Integer.valueOf(apnSetting.mProxyPort)) && Objects.equals(this.mUser, apnSetting.mUser) && Objects.equals(this.mPassword, apnSetting.mPassword) && Objects.equals(Integer.valueOf(this.mAuthType), Integer.valueOf(apnSetting.mAuthType)) && Objects.equals(Integer.valueOf(this.mApnTypeBitmask), Integer.valueOf(apnSetting.mApnTypeBitmask)) && Objects.equals(Long.valueOf(this.mLingeringNetworkTypeBitmask), Long.valueOf(apnSetting.mLingeringNetworkTypeBitmask)) && (z || Objects.equals(Integer.valueOf(this.mProtocol), Integer.valueOf(apnSetting.mProtocol))) && ((!z || Objects.equals(Integer.valueOf(this.mRoamingProtocol), Integer.valueOf(apnSetting.mRoamingProtocol))) && Objects.equals(Boolean.valueOf(this.mCarrierEnabled), Boolean.valueOf(apnSetting.mCarrierEnabled)) && Objects.equals(Integer.valueOf(this.mProfileId), Integer.valueOf(apnSetting.mProfileId)) && Objects.equals(Boolean.valueOf(this.mPersistent), Boolean.valueOf(apnSetting.mPersistent)) && Objects.equals(Integer.valueOf(this.mMaxConns), Integer.valueOf(apnSetting.mMaxConns)) && Objects.equals(Integer.valueOf(this.mWaitTime), Integer.valueOf(apnSetting.mWaitTime)) && Objects.equals(Integer.valueOf(this.mMaxConnsTime), Integer.valueOf(apnSetting.mMaxConnsTime)) && Objects.equals(Integer.valueOf(this.mMtuV4), Integer.valueOf(apnSetting.mMtuV4)) && Objects.equals(Integer.valueOf(this.mMtuV6), Integer.valueOf(apnSetting.mMtuV6)) && Objects.equals(Integer.valueOf(this.mMvnoType), Integer.valueOf(apnSetting.mMvnoType)) && Objects.equals(this.mMvnoMatchData, apnSetting.mMvnoMatchData) && Objects.equals(Integer.valueOf(this.mApnSetId), Integer.valueOf(apnSetting.mApnSetId)) && Objects.equals(Integer.valueOf(this.mCarrierId), Integer.valueOf(apnSetting.mCarrierId)) && Objects.equals(Integer.valueOf(this.mSkip464Xlat), Integer.valueOf(apnSetting.mSkip464Xlat)) && Objects.equals(Boolean.valueOf(this.mAlwaysOn), Boolean.valueOf(apnSetting.mAlwaysOn)) && Objects.equals(Integer.valueOf(this.mInfrastructureBitmask), Integer.valueOf(apnSetting.mInfrastructureBitmask)) && Objects.equals(Boolean.valueOf(this.mEsimBootstrapProvisioning), Boolean.valueOf(apnSetting.mEsimBootstrapProvisioning)));
    }

    public boolean similar(ApnSetting apnSetting) {
        return !canHandleType(8) && !apnSetting.canHandleType(8) && Objects.equals(this.mApnName, apnSetting.mApnName) && xorEqualsString(this.mProxyAddress, apnSetting.mProxyAddress) && xorEqualsInt(this.mProxyPort, apnSetting.mProxyPort) && xorEquals(this.mMmsc, apnSetting.mMmsc) && xorEqualsString(this.mMmsProxyAddress, apnSetting.mMmsProxyAddress) && xorEqualsInt(this.mMmsProxyPort, apnSetting.mMmsProxyPort) && xorEqualsString(this.mUser, apnSetting.mUser) && xorEqualsString(this.mPassword, apnSetting.mPassword) && Objects.equals(Integer.valueOf(this.mAuthType), Integer.valueOf(apnSetting.mAuthType)) && Objects.equals(this.mOperatorNumeric, apnSetting.mOperatorNumeric) && Objects.equals(Integer.valueOf(this.mProtocol), Integer.valueOf(apnSetting.mProtocol)) && Objects.equals(Integer.valueOf(this.mRoamingProtocol), Integer.valueOf(apnSetting.mRoamingProtocol)) && mtuUnsetOrEquals(this.mMtuV4, apnSetting.mMtuV4) && mtuUnsetOrEquals(this.mMtuV6, apnSetting.mMtuV6) && Objects.equals(Boolean.valueOf(this.mCarrierEnabled), Boolean.valueOf(apnSetting.mCarrierEnabled)) && Objects.equals(Integer.valueOf(this.mNetworkTypeBitmask), Integer.valueOf(apnSetting.mNetworkTypeBitmask)) && Objects.equals(Long.valueOf(this.mLingeringNetworkTypeBitmask), Long.valueOf(apnSetting.mLingeringNetworkTypeBitmask)) && Objects.equals(Integer.valueOf(this.mProfileId), Integer.valueOf(apnSetting.mProfileId)) && Objects.equals(Boolean.valueOf(this.mPersistent), Boolean.valueOf(apnSetting.mPersistent)) && Objects.equals(Integer.valueOf(this.mApnSetId), Integer.valueOf(apnSetting.mApnSetId)) && Objects.equals(Integer.valueOf(this.mCarrierId), Integer.valueOf(apnSetting.mCarrierId)) && Objects.equals(Integer.valueOf(this.mSkip464Xlat), Integer.valueOf(apnSetting.mSkip464Xlat)) && Objects.equals(Boolean.valueOf(this.mAlwaysOn), Boolean.valueOf(apnSetting.mAlwaysOn)) && Objects.equals(Integer.valueOf(this.mInfrastructureBitmask), Integer.valueOf(apnSetting.mInfrastructureBitmask)) && Objects.equals(Boolean.valueOf(this.mEsimBootstrapProvisioning), Boolean.valueOf(apnSetting.mEsimBootstrapProvisioning));
    }

    private boolean xorEquals(Object obj, Object obj2) {
        return obj == null || obj2 == null || obj.equals(obj2);
    }

    private boolean xorEqualsString(String str, String str2) {
        return TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || str.equals(str2);
    }

    private String nullToEmpty(String str) {
        return str == null ? "" : str;
    }

    public ContentValues toContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Telephony.Carriers.NUMERIC, nullToEmpty(this.mOperatorNumeric));
        if (!TextUtils.isEmpty(this.mOperatorNumeric) && (this.mOperatorNumeric.length() == 5 || this.mOperatorNumeric.length() == 6)) {
            contentValues.put("mcc", this.mOperatorNumeric.substring(0, 3));
            contentValues.put("mnc", this.mOperatorNumeric.substring(3));
        }
        contentValues.put("name", nullToEmpty(this.mEntryName));
        contentValues.put("apn", nullToEmpty(this.mApnName));
        contentValues.put("proxy", nullToEmpty(this.mProxyAddress));
        contentValues.put("port", nullToEmpty(portToString(this.mProxyPort)));
        contentValues.put(Telephony.Carriers.MMSC, nullToEmpty(UriToString(this.mMmsc)));
        contentValues.put(Telephony.Carriers.MMSPORT, nullToEmpty(portToString(this.mMmsProxyPort)));
        contentValues.put(Telephony.Carriers.MMSPROXY, nullToEmpty(this.mMmsProxyAddress));
        contentValues.put("user", nullToEmpty(this.mUser));
        contentValues.put("password", nullToEmpty(this.mPassword));
        contentValues.put(Telephony.Carriers.AUTH_TYPE, Integer.valueOf(this.mAuthType));
        contentValues.put("type", nullToEmpty(getApnTypesStringFromBitmask(this.mApnTypeBitmask)));
        contentValues.put("protocol", getProtocolStringFromInt(this.mProtocol));
        contentValues.put(Telephony.Carriers.ROAMING_PROTOCOL, getProtocolStringFromInt(this.mRoamingProtocol));
        contentValues.put(Telephony.Carriers.CARRIER_ENABLED, Boolean.valueOf(this.mCarrierEnabled));
        contentValues.put(Telephony.Carriers.MVNO_TYPE, getMvnoTypeStringFromInt(this.mMvnoType));
        contentValues.put(Telephony.Carriers.MVNO_MATCH_DATA, nullToEmpty(this.mMvnoMatchData));
        contentValues.put(Telephony.Carriers.NETWORK_TYPE_BITMASK, Integer.valueOf(this.mNetworkTypeBitmask));
        contentValues.put(Telephony.Carriers.LINGERING_NETWORK_TYPE_BITMASK, Long.valueOf(this.mLingeringNetworkTypeBitmask));
        contentValues.put(Telephony.Carriers.MTU_V4, Integer.valueOf(this.mMtuV4));
        contentValues.put(Telephony.Carriers.MTU_V6, Integer.valueOf(this.mMtuV6));
        contentValues.put("carrier_id", Integer.valueOf(this.mCarrierId));
        contentValues.put(Telephony.Carriers.SKIP_464XLAT, Integer.valueOf(this.mSkip464Xlat));
        contentValues.put(Telephony.Carriers.ALWAYS_ON, Boolean.valueOf(this.mAlwaysOn));
        contentValues.put(Telephony.Carriers.INFRASTRUCTURE_BITMASK, Integer.valueOf(this.mInfrastructureBitmask));
        contentValues.put(Telephony.Carriers.ESIM_BOOTSTRAP_PROVISIONING, Boolean.valueOf(this.mEsimBootstrapProvisioning));
        return contentValues;
    }

    public List<Integer> getApnTypes() {
        ArrayList arrayList = new ArrayList();
        for (Integer num : APN_TYPE_INT_MAP.keySet()) {
            if ((this.mApnTypeBitmask & num.intValue()) == num.intValue()) {
                arrayList.add(num);
            }
        }
        return arrayList;
    }

    public static String getApnTypesStringFromBitmask(int i) {
        ArrayList arrayList = new ArrayList();
        for (Integer num : APN_TYPE_INT_MAP.keySet()) {
            if ((num.intValue() & i) == num.intValue()) {
                arrayList.add(APN_TYPE_INT_MAP.get(num));
            }
        }
        return TextUtils.join(",", arrayList);
    }

    public static int[] getApnTypesFromBitmask(final int i) {
        return APN_TYPE_INT_MAP.keySet().stream().filter(new Predicate() { // from class: android.telephony.data.ApnSetting$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ApnSetting.lambda$getApnTypesFromBitmask$0(i, (Integer) obj);
            }
        }).mapToInt(new PreferentialNetworkServiceConfig$$ExternalSyntheticLambda2()).toArray();
    }

    static /* synthetic */ boolean lambda$getApnTypesFromBitmask$0(int i, Integer num) {
        return (i & num.intValue()) == num.intValue();
    }

    @SystemApi
    public static String getApnTypeString(int i) {
        if (i == 255) {
            return "*";
        }
        String str = APN_TYPE_INT_MAP.get(Integer.valueOf(i));
        return str == null ? "" : str;
    }

    @SystemApi
    public static int getApnTypeInt(String str) {
        return APN_TYPE_STRING_MAP.getOrDefault(str.toLowerCase(Locale.ROOT), 0).intValue();
    }

    public static int getApnTypesBitmaskFromString(String str) {
        if (TextUtils.isEmpty(str)) {
            return 255;
        }
        int iIntValue = 0;
        for (String str2 : str.split(",")) {
            Integer num = APN_TYPE_STRING_MAP.get(str2.toLowerCase(Locale.ROOT));
            if (num != null) {
                iIntValue |= num.intValue();
            }
        }
        return iIntValue;
    }

    public static int getMvnoTypeIntFromString(String str) {
        if (!TextUtils.isEmpty(str)) {
            str = str.toLowerCase(Locale.ROOT);
        }
        Integer num = MVNO_TYPE_STRING_MAP.get(str);
        if (num == null) {
            return -1;
        }
        return num.intValue();
    }

    public static String getMvnoTypeStringFromInt(int i) {
        String str = MVNO_TYPE_INT_MAP.get(Integer.valueOf(i));
        return str == null ? "" : str;
    }

    public static int getProtocolIntFromString(String str) {
        Integer num = PROTOCOL_STRING_MAP.get(str);
        if (num == null) {
            return -1;
        }
        return num.intValue();
    }

    public static String getProtocolStringFromInt(int i) {
        String str = PROTOCOL_INT_MAP.get(Integer.valueOf(i));
        return str == null ? "" : str;
    }

    private static Uri UriFromString(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return Uri.parse(str);
    }

    private static String UriToString(Uri uri) {
        if (uri == null) {
            return null;
        }
        return uri.toString();
    }

    public static InetAddress inetAddressFromString(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            return InetAddress.getByName(str);
        } catch (UnknownHostException unused) {
            Log.e(LOG_TAG, "Can't parse InetAddress from string: unknown host.");
            return null;
        }
    }

    public static String inetAddressToString(InetAddress inetAddress) {
        if (inetAddress == null) {
            return null;
        }
        String string = inetAddress.toString();
        if (TextUtils.isEmpty(string)) {
            return null;
        }
        String strSubstring = string.substring(0, string.indexOf("/"));
        String strSubstring2 = string.substring(string.indexOf("/") + 1);
        if (TextUtils.isEmpty(strSubstring) && TextUtils.isEmpty(strSubstring2)) {
            return null;
        }
        return TextUtils.isEmpty(strSubstring) ? strSubstring2 : strSubstring;
    }

    private static int portFromString(String str) {
        if (TextUtils.isEmpty(str)) {
            return -1;
        }
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException unused) {
            Log.e(LOG_TAG, "Can't parse port from String");
            return -1;
        }
    }

    private static String portToString(int i) {
        if (i == -1) {
            return null;
        }
        return Integer.toString(i);
    }

    public boolean canSupportNetworkType(int i) {
        if (i != 16 || (this.mNetworkTypeBitmask & 3) == 0) {
            return ServiceState.bitmaskHasTech(this.mNetworkTypeBitmask, i);
        }
        return true;
    }

    public boolean canSupportLingeringNetworkType(int i) {
        long j = this.mLingeringNetworkTypeBitmask;
        if (j == 0) {
            return canSupportNetworkType(i);
        }
        if (i != 16 || (3 & j) == 0) {
            return ServiceState.bitmaskHasTech((int) j, i);
        }
        return true;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mId);
        parcel.writeString(this.mOperatorNumeric);
        parcel.writeString(this.mEntryName);
        parcel.writeString(this.mApnName);
        parcel.writeString(this.mProxyAddress);
        parcel.writeInt(this.mProxyPort);
        parcel.writeParcelable(this.mMmsc, i);
        parcel.writeString(this.mMmsProxyAddress);
        parcel.writeInt(this.mMmsProxyPort);
        parcel.writeString(this.mUser);
        parcel.writeString(this.mPassword);
        parcel.writeInt(this.mAuthType);
        parcel.writeInt(this.mApnTypeBitmask);
        parcel.writeInt(this.mProtocol);
        parcel.writeInt(this.mRoamingProtocol);
        parcel.writeBoolean(this.mCarrierEnabled);
        parcel.writeInt(this.mNetworkTypeBitmask);
        parcel.writeLong(this.mLingeringNetworkTypeBitmask);
        parcel.writeInt(this.mProfileId);
        parcel.writeBoolean(this.mPersistent);
        parcel.writeInt(this.mMaxConns);
        parcel.writeInt(this.mWaitTime);
        parcel.writeInt(this.mMaxConnsTime);
        parcel.writeInt(this.mMtuV4);
        parcel.writeInt(this.mMtuV6);
        parcel.writeInt(this.mMvnoType);
        parcel.writeString(this.mMvnoMatchData);
        parcel.writeInt(this.mApnSetId);
        parcel.writeInt(this.mCarrierId);
        parcel.writeInt(this.mSkip464Xlat);
        parcel.writeBoolean(this.mAlwaysOn);
        parcel.writeInt(this.mInfrastructureBitmask);
        parcel.writeBoolean(this.mEsimBootstrapProvisioning);
        parcel.writeInt(this.mEditedStatus);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ApnSetting readFromParcel(Parcel parcel) {
        return new Builder().setId(parcel.readInt()).setOperatorNumeric(parcel.readString()).setEntryName(parcel.readString()).setApnName(parcel.readString()).setProxyAddress(parcel.readString()).setProxyPort(parcel.readInt()).setMmsc((Uri) parcel.readParcelable(Uri.class.getClassLoader(), Uri.class)).setMmsProxyAddress(parcel.readString()).setMmsProxyPort(parcel.readInt()).setUser(parcel.readString()).setPassword(parcel.readString()).setAuthType(parcel.readInt()).setApnTypeBitmask(parcel.readInt()).setProtocol(parcel.readInt()).setRoamingProtocol(parcel.readInt()).setCarrierEnabled(parcel.readBoolean()).setNetworkTypeBitmask(parcel.readInt()).setLingeringNetworkTypeBitmask(parcel.readLong()).setProfileId(parcel.readInt()).setModemCognitive(parcel.readBoolean()).setMaxConns(parcel.readInt()).setWaitTime(parcel.readInt()).setMaxConnsTime(parcel.readInt()).setMtuV4(parcel.readInt()).setMtuV6(parcel.readInt()).setMvnoType(parcel.readInt()).setMvnoMatchData(parcel.readString()).setApnSetId(parcel.readInt()).setCarrierId(parcel.readInt()).setSkip464Xlat(parcel.readInt()).setAlwaysOn(parcel.readBoolean()).setInfrastructureBitmask(parcel.readInt()).setEsimBootstrapProvisioning(parcel.readBoolean()).setEditedStatus(parcel.readInt()).buildWithoutCheck();
    }

    public static class Builder {
        private boolean mAlwaysOn;
        private String mApnName;
        private int mApnSetId;
        private int mApnTypeBitmask;
        private boolean mCarrierEnabled;
        private String mEntryName;
        private boolean mEsimBootstrapProvisioning;
        private int mId;
        private long mLingeringNetworkTypeBitmask;
        private int mMaxConns;
        private int mMaxConnsTime;
        private String mMmsProxyAddress;
        private Uri mMmsc;
        private boolean mModemCognitive;
        private int mMtuV4;
        private int mMtuV6;
        private String mMvnoMatchData;
        private int mNetworkTypeBitmask;
        private String mOperatorNumeric;
        private String mPassword;
        private int mProfileId;
        private String mProxyAddress;
        private String mUser;
        private int mWaitTime;
        private int mProxyPort = -1;
        private int mMmsProxyPort = -1;
        private int mAuthType = -1;
        private int mProtocol = -1;
        private int mRoamingProtocol = -1;
        private int mMvnoType = -1;
        private int mCarrierId = -1;
        private int mSkip464Xlat = -1;
        private int mInfrastructureBitmask = 3;
        private int mEditedStatus = 0;

        public Builder setId(int i) {
            this.mId = i;
            return this;
        }

        public Builder setMtuV4(int i) {
            this.mMtuV4 = i;
            return this;
        }

        public Builder setMtuV6(int i) {
            this.mMtuV6 = i;
            return this;
        }

        public Builder setProfileId(int i) {
            this.mProfileId = i;
            return this;
        }

        public Builder setPersistent(boolean z) {
            return setModemCognitive(z);
        }

        public Builder setModemCognitive(boolean z) {
            this.mModemCognitive = z;
            return this;
        }

        public Builder setMaxConns(int i) {
            this.mMaxConns = i;
            return this;
        }

        public Builder setWaitTime(int i) {
            this.mWaitTime = i;
            return this;
        }

        public Builder setMaxConnsTime(int i) {
            this.mMaxConnsTime = i;
            return this;
        }

        public Builder setMvnoMatchData(String str) {
            this.mMvnoMatchData = str;
            return this;
        }

        public Builder setApnSetId(int i) {
            this.mApnSetId = i;
            return this;
        }

        public Builder setEntryName(String str) {
            this.mEntryName = str;
            return this;
        }

        public Builder setApnName(String str) {
            this.mApnName = str;
            return this;
        }

        @Deprecated
        public Builder setProxyAddress(InetAddress inetAddress) {
            this.mProxyAddress = ApnSetting.inetAddressToString(inetAddress);
            return this;
        }

        public Builder setProxyAddress(String str) {
            this.mProxyAddress = str;
            return this;
        }

        public Builder setProxyPort(int i) {
            this.mProxyPort = i;
            return this;
        }

        public Builder setMmsc(Uri uri) {
            this.mMmsc = uri;
            return this;
        }

        @Deprecated
        public Builder setMmsProxyAddress(InetAddress inetAddress) {
            this.mMmsProxyAddress = ApnSetting.inetAddressToString(inetAddress);
            return this;
        }

        public Builder setMmsProxyAddress(String str) {
            this.mMmsProxyAddress = str;
            return this;
        }

        public Builder setMmsProxyPort(int i) {
            this.mMmsProxyPort = i;
            return this;
        }

        public Builder setUser(String str) {
            this.mUser = str;
            return this;
        }

        public Builder setPassword(String str) {
            this.mPassword = str;
            return this;
        }

        public Builder setAuthType(int i) {
            this.mAuthType = i;
            return this;
        }

        public Builder setApnTypeBitmask(int i) {
            this.mApnTypeBitmask = i;
            return this;
        }

        public Builder setOperatorNumeric(String str) {
            this.mOperatorNumeric = str;
            return this;
        }

        public Builder setProtocol(int i) {
            this.mProtocol = i;
            return this;
        }

        public Builder setRoamingProtocol(int i) {
            this.mRoamingProtocol = i;
            return this;
        }

        public Builder setCarrierEnabled(boolean z) {
            this.mCarrierEnabled = z;
            return this;
        }

        public Builder setNetworkTypeBitmask(int i) {
            this.mNetworkTypeBitmask = i;
            return this;
        }

        public Builder setLingeringNetworkTypeBitmask(long j) {
            this.mLingeringNetworkTypeBitmask = j;
            return this;
        }

        public Builder setMvnoType(int i) {
            this.mMvnoType = i;
            return this;
        }

        public Builder setCarrierId(int i) {
            this.mCarrierId = i;
            return this;
        }

        public Builder setSkip464Xlat(int i) {
            this.mSkip464Xlat = i;
            return this;
        }

        public Builder setAlwaysOn(boolean z) {
            this.mAlwaysOn = z;
            return this;
        }

        public Builder setInfrastructureBitmask(int i) {
            this.mInfrastructureBitmask = i;
            return this;
        }

        public Builder setEsimBootstrapProvisioning(boolean z) {
            this.mEsimBootstrapProvisioning = z;
            return this;
        }

        public Builder setEditedStatus(int i) {
            this.mEditedStatus = i;
            return this;
        }

        public ApnSetting build() {
            if ((this.mApnTypeBitmask & AudioChannelLayout.INDEX_MASK_18) == 0 || TextUtils.isEmpty(this.mApnName) || TextUtils.isEmpty(this.mEntryName)) {
                return null;
            }
            if ((this.mApnTypeBitmask & 2) != 0 && !TextUtils.isEmpty(this.mMmsProxyAddress) && this.mMmsProxyAddress.startsWith(IntentFilter.SCHEME_HTTP)) {
                Log.wtf(ApnSetting.LOG_TAG, "mms proxy(" + this.mMmsProxyAddress + ") should be a hostname, not a url");
                this.mMmsProxyAddress = Uri.parse(this.mMmsProxyAddress).getHost();
            }
            return new ApnSetting(this);
        }

        public ApnSetting buildWithoutCheck() {
            return new ApnSetting(this);
        }
    }

    public boolean apnTypeBitmaskSame(int i) {
        return this.mApnTypeBitmask == i;
    }

    public boolean hasApnTypeExt(int i) {
        return hasApnType(i);
    }
}
