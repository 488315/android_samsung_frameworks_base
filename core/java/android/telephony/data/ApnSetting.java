package android.telephony.data;

import android.annotation.SystemApi;
import android.app.admin.PreferentialNetworkServiceConfig$$ExternalSyntheticLambda2;
import android.content.ContentValues;
import android.content.IntentFilter;
import android.database.Cursor;
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

public class ApnSetting implements Parcelable {
    private static final Map<Integer, String> APN_TYPE_INT_MAP;
    private static final Map<String, Integer> APN_TYPE_STRING_MAP = new ArrayMap();
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

    @SystemApi public static final String TYPE_ALL_STRING = "*";
    public static final int TYPE_BANDWIDTH = 262144;
    public static final int TYPE_BIP = 8192;

    @SystemApi public static final String TYPE_BIP_STRING = "bip";
    public static final int TYPE_CBS = 128;

    @SystemApi public static final String TYPE_CBS_STRING = "cbs";
    public static final int TYPE_DEFAULT = 17;

    @SystemApi public static final String TYPE_DEFAULT_STRING = "default";
    public static final int TYPE_DUN = 8;

    @SystemApi public static final String TYPE_DUN_STRING = "dun";
    public static final int TYPE_EMERGENCY = 512;

    @SystemApi public static final String TYPE_EMERGENCY_STRING = "emergency";
    public static final int TYPE_ENT1 = 65536;
    public static final String TYPE_ENT1_STRING = "ent1";
    public static final int TYPE_ENTERPRISE = 16384;

    @SystemApi public static final String TYPE_ENTERPRISE_STRING = "enterprise";
    public static final int TYPE_FOTA = 32;

    @SystemApi public static final String TYPE_FOTA_STRING = "fota";
    public static final int TYPE_HIPRI = 16;

    @SystemApi public static final String TYPE_HIPRI_STRING = "hipri";
    public static final int TYPE_IA = 256;

    @SystemApi public static final String TYPE_IA_STRING = "ia";
    public static final int TYPE_IMS = 64;

    @SystemApi public static final String TYPE_IMS_STRING = "ims";
    public static final int TYPE_LATENCY = 131072;
    public static final int TYPE_MCX = 1024;

    @SystemApi public static final String TYPE_MCX_STRING = "mcx";
    public static final int TYPE_MMS = 2;

    @SystemApi public static final String TYPE_MMS_STRING = "mms";
    public static final int TYPE_NONE = 0;
    public static final int TYPE_RCS = 32768;

    @SystemApi public static final String TYPE_RCS_STRING = "rcs";
    public static final int TYPE_SUPL = 4;

    @SystemApi public static final String TYPE_SUPL_STRING = "supl";
    private static final int TYPE_VENDOR = 32768;
    public static final int TYPE_VSIM = 4096;

    @SystemApi public static final String TYPE_VSIM_STRING = "vsim";
    public static final int TYPE_XCAP = 2048;

    @SystemApi public static final String TYPE_XCAP_STRING = "xcap";
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
    public @interface ApnType {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface ApnTypeString {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface AuthType {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface InfrastructureBitmask {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface MvnoType {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface ProtocolType {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface Skip464XlatStatus {}

    static {
        APN_TYPE_STRING_MAP.put("*", 255);
        APN_TYPE_STRING_MAP.put("default", 17);
        APN_TYPE_STRING_MAP.put("mms", 2);
        APN_TYPE_STRING_MAP.put("supl", 4);
        APN_TYPE_STRING_MAP.put("dun", 8);
        APN_TYPE_STRING_MAP.put("hipri", 16);
        APN_TYPE_STRING_MAP.put("fota", 32);
        APN_TYPE_STRING_MAP.put("ims", 64);
        APN_TYPE_STRING_MAP.put("cbs", 128);
        APN_TYPE_STRING_MAP.put("ia", 256);
        APN_TYPE_STRING_MAP.put("emergency", 512);
        APN_TYPE_STRING_MAP.put("mcx", 1024);
        APN_TYPE_STRING_MAP.put("xcap", 2048);
        APN_TYPE_STRING_MAP.put(TYPE_ENTERPRISE_STRING, 16384);
        APN_TYPE_STRING_MAP.put(TYPE_VSIM_STRING, 4096);
        APN_TYPE_STRING_MAP.put("bip", 8192);
        APN_TYPE_STRING_MAP.put("rcs", 32768);
        APN_TYPE_STRING_MAP.put("ent1", 65536);
        APN_TYPE_INT_MAP = new ArrayMap();
        APN_TYPE_INT_MAP.put(17, "default");
        APN_TYPE_INT_MAP.put(2, "mms");
        APN_TYPE_INT_MAP.put(4, "supl");
        APN_TYPE_INT_MAP.put(8, "dun");
        APN_TYPE_INT_MAP.put(16, "hipri");
        APN_TYPE_INT_MAP.put(32, "fota");
        APN_TYPE_INT_MAP.put(64, "ims");
        APN_TYPE_INT_MAP.put(128, "cbs");
        APN_TYPE_INT_MAP.put(256, "ia");
        APN_TYPE_INT_MAP.put(512, "emergency");
        APN_TYPE_INT_MAP.put(1024, "mcx");
        APN_TYPE_INT_MAP.put(2048, "xcap");
        APN_TYPE_INT_MAP.put(16384, TYPE_ENTERPRISE_STRING);
        APN_TYPE_INT_MAP.put(4096, TYPE_VSIM_STRING);
        APN_TYPE_INT_MAP.put(8192, "bip");
        APN_TYPE_INT_MAP.put(32768, "rcs");
        APN_TYPE_INT_MAP.put(65536, "ent1");
        PROTOCOL_STRING_MAP = new ArrayMap();
        PROTOCOL_STRING_MAP.put(CarrierConfigManager.Apn.PROTOCOL_IPV4, 0);
        PROTOCOL_STRING_MAP.put("IPV6", 1);
        PROTOCOL_STRING_MAP.put(CarrierConfigManager.Apn.PROTOCOL_IPV4V6, 2);
        PROTOCOL_STRING_MAP.put("PPP", 3);
        PROTOCOL_STRING_MAP.put("NON-IP", 4);
        PROTOCOL_STRING_MAP.put("UNSTRUCTURED", 5);
        PROTOCOL_INT_MAP = new ArrayMap();
        PROTOCOL_INT_MAP.put(0, CarrierConfigManager.Apn.PROTOCOL_IPV4);
        PROTOCOL_INT_MAP.put(1, "IPV6");
        PROTOCOL_INT_MAP.put(2, CarrierConfigManager.Apn.PROTOCOL_IPV4V6);
        PROTOCOL_INT_MAP.put(3, "PPP");
        PROTOCOL_INT_MAP.put(4, "NON-IP");
        PROTOCOL_INT_MAP.put(5, "UNSTRUCTURED");
        MVNO_TYPE_STRING_MAP = new ArrayMap();
        MVNO_TYPE_STRING_MAP.put(Telephony.CarrierId.All.SPN, 0);
        MVNO_TYPE_STRING_MAP.put("imsi", 1);
        MVNO_TYPE_STRING_MAP.put("gid", 2);
        MVNO_TYPE_STRING_MAP.put("iccid", 3);
        MVNO_TYPE_INT_MAP = new ArrayMap();
        MVNO_TYPE_INT_MAP.put(0, Telephony.CarrierId.All.SPN);
        MVNO_TYPE_INT_MAP.put(1, "imsi");
        MVNO_TYPE_INT_MAP.put(2, "gid");
        MVNO_TYPE_INT_MAP.put(3, "iccid");
        CREATOR =
                new Parcelable.Creator<
                        ApnSetting>() { // from class: android.telephony.data.ApnSetting.1
                    @Override // android.os.Parcelable.Creator
                    public ApnSetting createFromParcel(Parcel in) {
                        return ApnSetting.readFromParcel(in);
                    }

                    @Override // android.os.Parcelable.Creator
                    public ApnSetting[] newArray(int size) {
                        return new ApnSetting[size];
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

    public void setPermanentFailed(boolean permanentFailed) {
        this.mPermanentFailed = permanentFailed;
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

    public boolean isForInfrastructure(int infrastructures) {
        return (this.mInfrastructureBitmask & infrastructures) != 0;
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
        int apnTypesBitmask =
                getApnTypesBitmaskFromString(
                        cursor.getString(cursor.getColumnIndexOrThrow("type")));
        int networkTypeBitmask =
                cursor.getInt(
                        cursor.getColumnIndexOrThrow(Telephony.Carriers.NETWORK_TYPE_BITMASK));
        if (networkTypeBitmask == 0) {
            int bearerBitmask =
                    cursor.getInt(cursor.getColumnIndexOrThrow(Telephony.Carriers.BEARER_BITMASK));
            networkTypeBitmask =
                    ServiceState.convertBearerBitmaskToNetworkTypeBitmask(bearerBitmask);
        }
        int mtuV4 = cursor.getInt(cursor.getColumnIndexOrThrow(Telephony.Carriers.MTU_V4));
        if (mtuV4 == 0) {
            mtuV4 = cursor.getInt(cursor.getColumnIndexOrThrow("mtu"));
        }
        return new Builder()
                .setId(cursor.getInt(cursor.getColumnIndexOrThrow("_id")))
                .setOperatorNumeric(
                        cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Carriers.NUMERIC)))
                .setEntryName(cursor.getString(cursor.getColumnIndexOrThrow("name")))
                .setApnName(cursor.getString(cursor.getColumnIndexOrThrow("apn")))
                .setProxyAddress(cursor.getString(cursor.getColumnIndexOrThrow("proxy")))
                .setProxyPort(
                        portFromString(cursor.getString(cursor.getColumnIndexOrThrow("port"))))
                .setMmsc(
                        UriFromString(
                                cursor.getString(
                                        cursor.getColumnIndexOrThrow(Telephony.Carriers.MMSC))))
                .setMmsProxyAddress(
                        cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Carriers.MMSPROXY)))
                .setMmsProxyPort(
                        portFromString(
                                cursor.getString(
                                        cursor.getColumnIndexOrThrow(Telephony.Carriers.MMSPORT))))
                .setUser(cursor.getString(cursor.getColumnIndexOrThrow("user")))
                .setPassword(cursor.getString(cursor.getColumnIndexOrThrow("password")))
                .setAuthType(
                        cursor.getInt(cursor.getColumnIndexOrThrow(Telephony.Carriers.AUTH_TYPE)))
                .setApnTypeBitmask(apnTypesBitmask)
                .setProtocol(
                        getProtocolIntFromString(
                                cursor.getString(cursor.getColumnIndexOrThrow("protocol"))))
                .setRoamingProtocol(
                        getProtocolIntFromString(
                                cursor.getString(
                                        cursor.getColumnIndexOrThrow(
                                                Telephony.Carriers.ROAMING_PROTOCOL))))
                .setCarrierEnabled(
                        cursor.getInt(
                                        cursor.getColumnIndexOrThrow(
                                                Telephony.Carriers.CARRIER_ENABLED))
                                == 1)
                .setNetworkTypeBitmask(networkTypeBitmask)
                .setLingeringNetworkTypeBitmask(
                        cursor.getInt(
                                cursor.getColumnIndexOrThrow(
                                        Telephony.Carriers.LINGERING_NETWORK_TYPE_BITMASK)))
                .setProfileId(
                        cursor.getInt(cursor.getColumnIndexOrThrow(Telephony.Carriers.PROFILE_ID)))
                .setModemCognitive(
                        cursor.getInt(
                                        cursor.getColumnIndexOrThrow(
                                                Telephony.Carriers.MODEM_PERSIST))
                                == 1)
                .setMaxConns(
                        cursor.getInt(
                                cursor.getColumnIndexOrThrow(Telephony.Carriers.MAX_CONNECTIONS)))
                .setWaitTime(
                        cursor.getInt(
                                cursor.getColumnIndexOrThrow(Telephony.Carriers.WAIT_TIME_RETRY)))
                .setMaxConnsTime(
                        cursor.getInt(
                                cursor.getColumnIndexOrThrow(
                                        Telephony.Carriers.TIME_LIMIT_FOR_MAX_CONNECTIONS)))
                .setMtuV4(mtuV4)
                .setMtuV6(cursor.getInt(cursor.getColumnIndexOrThrow(Telephony.Carriers.MTU_V6)))
                .setMvnoType(
                        getMvnoTypeIntFromString(
                                cursor.getString(
                                        cursor.getColumnIndexOrThrow(
                                                Telephony.Carriers.MVNO_TYPE))))
                .setMvnoMatchData(
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(Telephony.Carriers.MVNO_MATCH_DATA)))
                .setApnSetId(
                        cursor.getInt(cursor.getColumnIndexOrThrow(Telephony.Carriers.APN_SET_ID)))
                .setCarrierId(cursor.getInt(cursor.getColumnIndexOrThrow("carrier_id")))
                .setSkip464Xlat(
                        cursor.getInt(
                                cursor.getColumnIndexOrThrow(Telephony.Carriers.SKIP_464XLAT)))
                .setAlwaysOn(
                        cursor.getInt(cursor.getColumnIndexOrThrow(Telephony.Carriers.ALWAYS_ON))
                                == 1)
                .setInfrastructureBitmask(
                        cursor.getInt(
                                cursor.getColumnIndexOrThrow(
                                        Telephony.Carriers.INFRASTRUCTURE_BITMASK)))
                .setEsimBootstrapProvisioning(
                        cursor.getInt(
                                        cursor.getColumnIndexOrThrow(
                                                Telephony.Carriers.ESIM_BOOTSTRAP_PROVISIONING))
                                == 1)
                .setEditedStatus(
                        cursor.getInt(
                                cursor.getColumnIndexOrThrow(Telephony.Carriers.EDITED_STATUS)))
                .buildWithoutCheck();
    }

    public static ApnSetting makeApnSetting(ApnSetting apn) {
        return makeApnSetting(apn, false);
    }

    public static ApnSetting makeApnSetting(ApnSetting apn, boolean deleteEmergencyType) {
        Builder authType =
                new Builder()
                        .setId(apn.mId)
                        .setOperatorNumeric(apn.mOperatorNumeric)
                        .setEntryName(apn.mEntryName)
                        .setApnName(apn.mApnName)
                        .setProxyAddress(apn.mProxyAddress)
                        .setProxyPort(apn.mProxyPort)
                        .setMmsc(apn.mMmsc)
                        .setMmsProxyAddress(apn.mMmsProxyAddress)
                        .setMmsProxyPort(apn.mMmsProxyPort)
                        .setUser(apn.mUser)
                        .setPassword(apn.mPassword)
                        .setAuthType(apn.mAuthType);
        int i = apn.mApnTypeBitmask;
        if (deleteEmergencyType) {
            i &= -513;
        }
        return authType.setApnTypeBitmask(i)
                .setProtocol(apn.mProtocol)
                .setRoamingProtocol(apn.mRoamingProtocol)
                .setCarrierEnabled(apn.mCarrierEnabled)
                .setNetworkTypeBitmask(apn.mNetworkTypeBitmask)
                .setLingeringNetworkTypeBitmask(apn.mLingeringNetworkTypeBitmask)
                .setProfileId(apn.mProfileId)
                .setModemCognitive(apn.mPersistent)
                .setMaxConns(apn.mMaxConns)
                .setWaitTime(apn.mWaitTime)
                .setMaxConnsTime(apn.mMaxConnsTime)
                .setMtuV4(apn.mMtuV4)
                .setMtuV6(apn.mMtuV6)
                .setMvnoType(apn.mMvnoType)
                .setMvnoMatchData(apn.mMvnoMatchData)
                .setApnSetId(apn.mApnSetId)
                .setCarrierId(apn.mCarrierId)
                .setSkip464Xlat(apn.mSkip464Xlat)
                .setAlwaysOn(apn.mAlwaysOn)
                .setInfrastructureBitmask(apn.mInfrastructureBitmask)
                .setEsimBootstrapProvisioning(apn.mEsimBootstrapProvisioning)
                .setEditedStatus(apn.mEditedStatus)
                .buildWithoutCheck();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[ApnSetting] ")
                .append(this.mEntryName)
                .append(", ")
                .append(this.mId)
                .append(", ")
                .append(this.mOperatorNumeric)
                .append(", ")
                .append(this.mApnName)
                .append(", ")
                .append(this.mProxyAddress)
                .append(", ")
                .append(UriToString(this.mMmsc))
                .append(", ")
                .append(this.mMmsProxyAddress)
                .append(", ")
                .append(portToString(this.mMmsProxyPort))
                .append(", ")
                .append(portToString(this.mProxyPort))
                .append(", ")
                .append(this.mAuthType)
                .append(", ");
        String[] types = getApnTypesStringFromBitmask(this.mApnTypeBitmask).split(",");
        sb.append(TextUtils.join(" | ", types));
        sb.append(", ").append(PROTOCOL_INT_MAP.get(Integer.valueOf(this.mProtocol)));
        sb.append(", ").append(PROTOCOL_INT_MAP.get(Integer.valueOf(this.mRoamingProtocol)));
        sb.append(", ").append(this.mCarrierEnabled);
        sb.append(", ").append(this.mProfileId);
        sb.append(", ").append(this.mPersistent);
        sb.append(", ").append(this.mMaxConns);
        sb.append(", ").append(this.mWaitTime);
        sb.append(", ").append(this.mMaxConnsTime);
        sb.append(", ").append(this.mMtuV4);
        sb.append(", ").append(this.mMtuV6);
        sb.append(", ").append(MVNO_TYPE_INT_MAP.get(Integer.valueOf(this.mMvnoType)));
        sb.append(", ").append(this.mMvnoMatchData);
        sb.append(", ").append(this.mPermanentFailed);
        sb.append(", ")
                .append(
                        TelephonyManager.convertNetworkTypeBitmaskToString(
                                this.mNetworkTypeBitmask));
        sb.append(", ")
                .append(
                        TelephonyManager.convertNetworkTypeBitmaskToString(
                                this.mLingeringNetworkTypeBitmask));
        sb.append(", ").append(this.mApnSetId);
        sb.append(", ").append(this.mCarrierId);
        sb.append(", ").append(this.mSkip464Xlat);
        sb.append(", ").append(this.mAlwaysOn);
        sb.append(", ").append(this.mInfrastructureBitmask);
        sb.append(", ").append(Objects.hash(this.mUser, this.mPassword));
        sb.append(", ").append(this.mEsimBootstrapProvisioning);
        sb.append(", ").append(TelephonyUtils.apnEditedStatusToString(this.mEditedStatus));
        return sb.toString();
    }

    public String toStringExceptID() {
        StringBuilder sb = new StringBuilder();
        sb.append("[ApnSetting] ")
                .append(this.mEntryName)
                .append(", ")
                .append(this.mOperatorNumeric)
                .append(", ")
                .append(this.mApnName)
                .append(", ")
                .append(this.mProxyAddress)
                .append(", ")
                .append(UriToString(this.mMmsc))
                .append(", ")
                .append(this.mMmsProxyAddress)
                .append(", ")
                .append(portToString(this.mMmsProxyPort))
                .append(", ")
                .append(portToString(this.mProxyPort))
                .append(", ")
                .append(this.mAuthType)
                .append(", ");
        String[] types = getApnTypesStringFromBitmask(this.mApnTypeBitmask).split(",");
        sb.append(TextUtils.join(" | ", types));
        sb.append(", ").append(PROTOCOL_INT_MAP.get(Integer.valueOf(this.mProtocol)));
        sb.append(", ").append(PROTOCOL_INT_MAP.get(Integer.valueOf(this.mRoamingProtocol)));
        sb.append(", ").append(this.mCarrierEnabled);
        sb.append(", ").append(this.mProfileId);
        sb.append(", ").append(this.mPersistent);
        sb.append(", ").append(this.mMaxConns);
        sb.append(", ").append(this.mWaitTime);
        sb.append(", ").append(this.mMaxConnsTime);
        sb.append(", ").append(this.mMtuV4);
        sb.append(", ").append(this.mMtuV6);
        sb.append(", ").append(MVNO_TYPE_INT_MAP.get(Integer.valueOf(this.mMvnoType)));
        sb.append(", ").append(this.mMvnoMatchData);
        sb.append(", ").append(this.mPermanentFailed);
        sb.append(", ")
                .append(
                        TelephonyManager.convertNetworkTypeBitmaskToString(
                                this.mNetworkTypeBitmask));
        sb.append(", ")
                .append(
                        TelephonyManager.convertNetworkTypeBitmaskToString(
                                this.mLingeringNetworkTypeBitmask));
        sb.append(", ").append(this.mApnSetId);
        sb.append(", ").append(this.mCarrierId);
        sb.append(", ").append(this.mSkip464Xlat);
        sb.append(", ").append(this.mAlwaysOn);
        sb.append(", ").append(this.mInfrastructureBitmask);
        sb.append(", ").append(Objects.hash(this.mUser, this.mPassword));
        sb.append(", ").append(this.mEsimBootstrapProvisioning);
        sb.append(", ").append(TelephonyUtils.apnEditedStatusToString(this.mEditedStatus));
        return sb.toString();
    }

    public boolean equalsExceptID(Object o) {
        if (!(o instanceof ApnSetting)) {
            return false;
        }
        ApnSetting obj = (ApnSetting) o;
        boolean ret = toStringExceptID().equals(obj.toStringExceptID());
        boolean areUsersEqual = defaultString(this.mUser).equals(defaultString(obj.mUser));
        boolean arePasswordsEqual =
                defaultString(this.mPassword).equals(defaultString(obj.mPassword));
        return ret && areUsersEqual && arePasswordsEqual;
    }

    public String defaultString(String str) {
        return str == null ? "" : str;
    }

    public boolean hasMvnoParams() {
        return (TextUtils.isEmpty(getMvnoTypeStringFromInt(this.mMvnoType))
                        || TextUtils.isEmpty(this.mMvnoMatchData))
                ? false
                : true;
    }

    private boolean hasApnType(int type) {
        return (this.mApnTypeBitmask & type) == type;
    }

    public boolean isEmergencyApn() {
        return hasApnType(512);
    }

    public boolean canHandleType(int type) {
        if (!this.mCarrierEnabled) {
            return false;
        }
        return hasApnType(type);
    }

    private boolean typeSameAny(ApnSetting first, ApnSetting second) {
        if ((first.mApnTypeBitmask & second.mApnTypeBitmask) != 0) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(
                this.mApnName,
                this.mProxyAddress,
                Integer.valueOf(this.mProxyPort),
                this.mMmsc,
                this.mMmsProxyAddress,
                Integer.valueOf(this.mMmsProxyPort),
                this.mUser,
                this.mPassword,
                Integer.valueOf(this.mAuthType),
                Integer.valueOf(this.mApnTypeBitmask),
                Integer.valueOf(this.mId),
                this.mOperatorNumeric,
                Integer.valueOf(this.mProtocol),
                Integer.valueOf(this.mRoamingProtocol),
                Integer.valueOf(this.mMtuV4),
                Integer.valueOf(this.mMtuV6),
                Boolean.valueOf(this.mCarrierEnabled),
                Integer.valueOf(this.mNetworkTypeBitmask),
                Long.valueOf(this.mLingeringNetworkTypeBitmask),
                Integer.valueOf(this.mProfileId),
                Boolean.valueOf(this.mPersistent),
                Integer.valueOf(this.mMaxConns),
                Integer.valueOf(this.mWaitTime),
                Integer.valueOf(this.mMaxConnsTime),
                Integer.valueOf(this.mMvnoType),
                this.mMvnoMatchData,
                Integer.valueOf(this.mApnSetId),
                Integer.valueOf(this.mCarrierId),
                Integer.valueOf(this.mSkip464Xlat),
                Boolean.valueOf(this.mAlwaysOn),
                Integer.valueOf(this.mInfrastructureBitmask),
                Boolean.valueOf(this.mEsimBootstrapProvisioning));
    }

    public boolean equals(Object o) {
        if (!(o instanceof ApnSetting)) {
            return false;
        }
        ApnSetting other = (ApnSetting) o;
        return this.mEntryName.equals(other.mEntryName)
                && this.mId == other.mId
                && Objects.equals(this.mOperatorNumeric, other.mOperatorNumeric)
                && Objects.equals(this.mApnName, other.mApnName)
                && Objects.equals(this.mProxyAddress, other.mProxyAddress)
                && Objects.equals(this.mMmsc, other.mMmsc)
                && Objects.equals(this.mMmsProxyAddress, other.mMmsProxyAddress)
                && this.mMmsProxyPort == other.mMmsProxyPort
                && this.mProxyPort == other.mProxyPort
                && Objects.equals(this.mUser, other.mUser)
                && Objects.equals(this.mPassword, other.mPassword)
                && this.mAuthType == other.mAuthType
                && this.mApnTypeBitmask == other.mApnTypeBitmask
                && this.mProtocol == other.mProtocol
                && this.mRoamingProtocol == other.mRoamingProtocol
                && this.mCarrierEnabled == other.mCarrierEnabled
                && this.mProfileId == other.mProfileId
                && this.mPersistent == other.mPersistent
                && this.mMaxConns == other.mMaxConns
                && this.mWaitTime == other.mWaitTime
                && this.mMaxConnsTime == other.mMaxConnsTime
                && this.mMtuV4 == other.mMtuV4
                && this.mMtuV6 == other.mMtuV6
                && this.mMvnoType == other.mMvnoType
                && Objects.equals(this.mMvnoMatchData, other.mMvnoMatchData)
                && this.mNetworkTypeBitmask == other.mNetworkTypeBitmask
                && this.mLingeringNetworkTypeBitmask == other.mLingeringNetworkTypeBitmask
                && this.mApnSetId == other.mApnSetId
                && this.mCarrierId == other.mCarrierId
                && this.mSkip464Xlat == other.mSkip464Xlat
                && this.mAlwaysOn == other.mAlwaysOn
                && this.mInfrastructureBitmask == other.mInfrastructureBitmask
                && this.mEsimBootstrapProvisioning == other.mEsimBootstrapProvisioning;
    }

    public boolean equals(Object o, boolean isDataRoaming) {
        if (!(o instanceof ApnSetting)) {
            return false;
        }
        ApnSetting other = (ApnSetting) o;
        if (!this.mEntryName.equals(other.mEntryName)
                || !Objects.equals(this.mOperatorNumeric, other.mOperatorNumeric)
                || !Objects.equals(this.mApnName, other.mApnName)
                || !Objects.equals(this.mProxyAddress, other.mProxyAddress)
                || !Objects.equals(this.mMmsc, other.mMmsc)
                || !Objects.equals(this.mMmsProxyAddress, other.mMmsProxyAddress)
                || !Objects.equals(
                        Integer.valueOf(this.mMmsProxyPort), Integer.valueOf(other.mMmsProxyPort))
                || !Objects.equals(
                        Integer.valueOf(this.mProxyPort), Integer.valueOf(other.mProxyPort))
                || !Objects.equals(this.mUser, other.mUser)
                || !Objects.equals(this.mPassword, other.mPassword)
                || !Objects.equals(
                        Integer.valueOf(this.mAuthType), Integer.valueOf(other.mAuthType))
                || !Objects.equals(
                        Integer.valueOf(this.mApnTypeBitmask),
                        Integer.valueOf(other.mApnTypeBitmask))
                || !Objects.equals(
                        Long.valueOf(this.mLingeringNetworkTypeBitmask),
                        Long.valueOf(other.mLingeringNetworkTypeBitmask))) {
            return false;
        }
        if (isDataRoaming
                || Objects.equals(
                        Integer.valueOf(this.mProtocol), Integer.valueOf(other.mProtocol))) {
            return (!isDataRoaming
                            || Objects.equals(
                                    Integer.valueOf(this.mRoamingProtocol),
                                    Integer.valueOf(other.mRoamingProtocol)))
                    && Objects.equals(
                            Boolean.valueOf(this.mCarrierEnabled),
                            Boolean.valueOf(other.mCarrierEnabled))
                    && Objects.equals(
                            Integer.valueOf(this.mProfileId), Integer.valueOf(other.mProfileId))
                    && Objects.equals(
                            Boolean.valueOf(this.mPersistent), Boolean.valueOf(other.mPersistent))
                    && Objects.equals(
                            Integer.valueOf(this.mMaxConns), Integer.valueOf(other.mMaxConns))
                    && Objects.equals(
                            Integer.valueOf(this.mWaitTime), Integer.valueOf(other.mWaitTime))
                    && Objects.equals(
                            Integer.valueOf(this.mMaxConnsTime),
                            Integer.valueOf(other.mMaxConnsTime))
                    && Objects.equals(Integer.valueOf(this.mMtuV4), Integer.valueOf(other.mMtuV4))
                    && Objects.equals(Integer.valueOf(this.mMtuV6), Integer.valueOf(other.mMtuV6))
                    && Objects.equals(
                            Integer.valueOf(this.mMvnoType), Integer.valueOf(other.mMvnoType))
                    && Objects.equals(this.mMvnoMatchData, other.mMvnoMatchData)
                    && Objects.equals(
                            Integer.valueOf(this.mApnSetId), Integer.valueOf(other.mApnSetId))
                    && Objects.equals(
                            Integer.valueOf(this.mCarrierId), Integer.valueOf(other.mCarrierId))
                    && Objects.equals(
                            Integer.valueOf(this.mSkip464Xlat), Integer.valueOf(other.mSkip464Xlat))
                    && Objects.equals(
                            Boolean.valueOf(this.mAlwaysOn), Boolean.valueOf(other.mAlwaysOn))
                    && Objects.equals(
                            Integer.valueOf(this.mInfrastructureBitmask),
                            Integer.valueOf(other.mInfrastructureBitmask))
                    && Objects.equals(
                            Boolean.valueOf(this.mEsimBootstrapProvisioning),
                            Boolean.valueOf(other.mEsimBootstrapProvisioning));
        }
        return false;
    }

    public boolean similar(ApnSetting other) {
        return !canHandleType(8)
                && !other.canHandleType(8)
                && Objects.equals(this.mApnName, other.mApnName)
                && xorEqualsString(this.mProxyAddress, other.mProxyAddress)
                && xorEqualsInt(this.mProxyPort, other.mProxyPort)
                && xorEquals(this.mMmsc, other.mMmsc)
                && xorEqualsString(this.mMmsProxyAddress, other.mMmsProxyAddress)
                && xorEqualsInt(this.mMmsProxyPort, other.mMmsProxyPort)
                && xorEqualsString(this.mUser, other.mUser)
                && xorEqualsString(this.mPassword, other.mPassword)
                && Objects.equals(Integer.valueOf(this.mAuthType), Integer.valueOf(other.mAuthType))
                && Objects.equals(this.mOperatorNumeric, other.mOperatorNumeric)
                && Objects.equals(Integer.valueOf(this.mProtocol), Integer.valueOf(other.mProtocol))
                && Objects.equals(
                        Integer.valueOf(this.mRoamingProtocol),
                        Integer.valueOf(other.mRoamingProtocol))
                && mtuUnsetOrEquals(this.mMtuV4, other.mMtuV4)
                && mtuUnsetOrEquals(this.mMtuV6, other.mMtuV6)
                && Objects.equals(
                        Boolean.valueOf(this.mCarrierEnabled),
                        Boolean.valueOf(other.mCarrierEnabled))
                && Objects.equals(
                        Integer.valueOf(this.mNetworkTypeBitmask),
                        Integer.valueOf(other.mNetworkTypeBitmask))
                && Objects.equals(
                        Long.valueOf(this.mLingeringNetworkTypeBitmask),
                        Long.valueOf(other.mLingeringNetworkTypeBitmask))
                && Objects.equals(
                        Integer.valueOf(this.mProfileId), Integer.valueOf(other.mProfileId))
                && Objects.equals(
                        Boolean.valueOf(this.mPersistent), Boolean.valueOf(other.mPersistent))
                && Objects.equals(Integer.valueOf(this.mApnSetId), Integer.valueOf(other.mApnSetId))
                && Objects.equals(
                        Integer.valueOf(this.mCarrierId), Integer.valueOf(other.mCarrierId))
                && Objects.equals(
                        Integer.valueOf(this.mSkip464Xlat), Integer.valueOf(other.mSkip464Xlat))
                && Objects.equals(Boolean.valueOf(this.mAlwaysOn), Boolean.valueOf(other.mAlwaysOn))
                && Objects.equals(
                        Integer.valueOf(this.mInfrastructureBitmask),
                        Integer.valueOf(other.mInfrastructureBitmask))
                && Objects.equals(
                        Boolean.valueOf(this.mEsimBootstrapProvisioning),
                        Boolean.valueOf(other.mEsimBootstrapProvisioning));
    }

    private boolean xorEquals(Object first, Object second) {
        return first == null || second == null || first.equals(second);
    }

    private boolean xorEqualsString(String first, String second) {
        return TextUtils.isEmpty(first) || TextUtils.isEmpty(second) || first.equals(second);
    }

    private boolean xorEqualsInt(int first, int second) {
        return first == -1 || second == -1 || first == second;
    }

    private boolean mtuUnsetOrEquals(int first, int second) {
        return first <= 0 || second <= 0 || first == second;
    }

    private String nullToEmpty(String stringValue) {
        return stringValue == null ? "" : stringValue;
    }

    public ContentValues toContentValues() {
        ContentValues apnValue = new ContentValues();
        apnValue.put(Telephony.Carriers.NUMERIC, nullToEmpty(this.mOperatorNumeric));
        if (!TextUtils.isEmpty(this.mOperatorNumeric)
                && (this.mOperatorNumeric.length() == 5 || this.mOperatorNumeric.length() == 6)) {
            apnValue.put("mcc", this.mOperatorNumeric.substring(0, 3));
            apnValue.put("mnc", this.mOperatorNumeric.substring(3));
        }
        apnValue.put("name", nullToEmpty(this.mEntryName));
        apnValue.put("apn", nullToEmpty(this.mApnName));
        apnValue.put("proxy", nullToEmpty(this.mProxyAddress));
        apnValue.put("port", nullToEmpty(portToString(this.mProxyPort)));
        apnValue.put(Telephony.Carriers.MMSC, nullToEmpty(UriToString(this.mMmsc)));
        apnValue.put(Telephony.Carriers.MMSPORT, nullToEmpty(portToString(this.mMmsProxyPort)));
        apnValue.put(Telephony.Carriers.MMSPROXY, nullToEmpty(this.mMmsProxyAddress));
        apnValue.put("user", nullToEmpty(this.mUser));
        apnValue.put("password", nullToEmpty(this.mPassword));
        apnValue.put(Telephony.Carriers.AUTH_TYPE, Integer.valueOf(this.mAuthType));
        String apnType = getApnTypesStringFromBitmask(this.mApnTypeBitmask);
        apnValue.put("type", nullToEmpty(apnType));
        apnValue.put("protocol", getProtocolStringFromInt(this.mProtocol));
        apnValue.put(
                Telephony.Carriers.ROAMING_PROTOCOL,
                getProtocolStringFromInt(this.mRoamingProtocol));
        apnValue.put(Telephony.Carriers.CARRIER_ENABLED, Boolean.valueOf(this.mCarrierEnabled));
        apnValue.put(Telephony.Carriers.MVNO_TYPE, getMvnoTypeStringFromInt(this.mMvnoType));
        apnValue.put(Telephony.Carriers.MVNO_MATCH_DATA, nullToEmpty(this.mMvnoMatchData));
        apnValue.put(
                Telephony.Carriers.NETWORK_TYPE_BITMASK, Integer.valueOf(this.mNetworkTypeBitmask));
        apnValue.put(
                Telephony.Carriers.LINGERING_NETWORK_TYPE_BITMASK,
                Long.valueOf(this.mLingeringNetworkTypeBitmask));
        apnValue.put(Telephony.Carriers.MTU_V4, Integer.valueOf(this.mMtuV4));
        apnValue.put(Telephony.Carriers.MTU_V6, Integer.valueOf(this.mMtuV6));
        apnValue.put("carrier_id", Integer.valueOf(this.mCarrierId));
        apnValue.put(Telephony.Carriers.SKIP_464XLAT, Integer.valueOf(this.mSkip464Xlat));
        apnValue.put(Telephony.Carriers.ALWAYS_ON, Boolean.valueOf(this.mAlwaysOn));
        apnValue.put(
                Telephony.Carriers.INFRASTRUCTURE_BITMASK,
                Integer.valueOf(this.mInfrastructureBitmask));
        apnValue.put(
                Telephony.Carriers.ESIM_BOOTSTRAP_PROVISIONING,
                Boolean.valueOf(this.mEsimBootstrapProvisioning));
        return apnValue;
    }

    public List<Integer> getApnTypes() {
        List<Integer> types = new ArrayList<>();
        for (Integer type : APN_TYPE_INT_MAP.keySet()) {
            if ((this.mApnTypeBitmask & type.intValue()) == type.intValue()) {
                types.add(type);
            }
        }
        return types;
    }

    public static String getApnTypesStringFromBitmask(int apnTypeBitmask) {
        List<String> types = new ArrayList<>();
        for (Integer type : APN_TYPE_INT_MAP.keySet()) {
            if ((type.intValue() & apnTypeBitmask) == type.intValue()) {
                types.add(APN_TYPE_INT_MAP.get(type));
            }
        }
        return TextUtils.join(",", types);
    }

    public static int[] getApnTypesFromBitmask(final int apnTypeBitmask) {
        return APN_TYPE_INT_MAP.keySet().stream()
                .filter(
                        new Predicate() { // from class:
                                          // android.telephony.data.ApnSetting$$ExternalSyntheticLambda0
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                return ApnSetting.lambda$getApnTypesFromBitmask$0(
                                        apnTypeBitmask, (Integer) obj);
                            }
                        })
                .mapToInt(new PreferentialNetworkServiceConfig$$ExternalSyntheticLambda2())
                .toArray();
    }

    static /* synthetic */ boolean lambda$getApnTypesFromBitmask$0(
            int apnTypeBitmask, Integer type) {
        return (type.intValue() & apnTypeBitmask) == type.intValue();
    }

    @SystemApi
    public static String getApnTypeString(int apnType) {
        if (apnType == 255) {
            return "*";
        }
        String apnTypeString = APN_TYPE_INT_MAP.get(Integer.valueOf(apnType));
        return apnTypeString == null ? "" : apnTypeString;
    }

    @SystemApi
    public static int getApnTypeInt(String apnType) {
        return APN_TYPE_STRING_MAP.getOrDefault(apnType.toLowerCase(Locale.ROOT), 0).intValue();
    }

    public static int getApnTypesBitmaskFromString(String types) {
        if (TextUtils.isEmpty(types)) {
            return 255;
        }
        int result = 0;
        for (String str : types.split(",")) {
            Integer type = APN_TYPE_STRING_MAP.get(str.toLowerCase(Locale.ROOT));
            if (type != null) {
                result |= type.intValue();
            }
        }
        return result;
    }

    public static int getMvnoTypeIntFromString(String mvnoType) {
        String mvnoTypeString =
                TextUtils.isEmpty(mvnoType) ? mvnoType : mvnoType.toLowerCase(Locale.ROOT);
        Integer mvnoTypeInt = MVNO_TYPE_STRING_MAP.get(mvnoTypeString);
        if (mvnoTypeInt == null) {
            return -1;
        }
        return mvnoTypeInt.intValue();
    }

    public static String getMvnoTypeStringFromInt(int mvnoType) {
        String mvnoTypeString = MVNO_TYPE_INT_MAP.get(Integer.valueOf(mvnoType));
        return mvnoTypeString == null ? "" : mvnoTypeString;
    }

    public static int getProtocolIntFromString(String protocol) {
        Integer protocolInt = PROTOCOL_STRING_MAP.get(protocol);
        if (protocolInt == null) {
            return -1;
        }
        return protocolInt.intValue();
    }

    public static String getProtocolStringFromInt(int protocol) {
        String protocolString = PROTOCOL_INT_MAP.get(Integer.valueOf(protocol));
        return protocolString == null ? "" : protocolString;
    }

    private static Uri UriFromString(String uri) {
        if (TextUtils.isEmpty(uri)) {
            return null;
        }
        return Uri.parse(uri);
    }

    private static String UriToString(Uri uri) {
        if (uri == null) {
            return null;
        }
        return uri.toString();
    }

    public static InetAddress inetAddressFromString(String inetAddress) {
        if (TextUtils.isEmpty(inetAddress)) {
            return null;
        }
        try {
            return InetAddress.getByName(inetAddress);
        } catch (UnknownHostException e) {
            Log.e(LOG_TAG, "Can't parse InetAddress from string: unknown host.");
            return null;
        }
    }

    public static String inetAddressToString(InetAddress inetAddress) {
        if (inetAddress == null) {
            return null;
        }
        String inetAddressString = inetAddress.toString();
        if (TextUtils.isEmpty(inetAddressString)) {
            return null;
        }
        String hostName = inetAddressString.substring(0, inetAddressString.indexOf("/"));
        String address = inetAddressString.substring(inetAddressString.indexOf("/") + 1);
        if (TextUtils.isEmpty(hostName) && TextUtils.isEmpty(address)) {
            return null;
        }
        return TextUtils.isEmpty(hostName) ? address : hostName;
    }

    private static int portFromString(String strPort) {
        if (TextUtils.isEmpty(strPort)) {
            return -1;
        }
        try {
            int port = Integer.parseInt(strPort);
            return port;
        } catch (NumberFormatException e) {
            Log.e(LOG_TAG, "Can't parse port from String");
            return -1;
        }
    }

    private static String portToString(int port) {
        if (port == -1) {
            return null;
        }
        return Integer.toString(port);
    }

    public boolean canSupportNetworkType(int networkType) {
        if (networkType == 16 && (this.mNetworkTypeBitmask & 3) != 0) {
            return true;
        }
        return ServiceState.bitmaskHasTech(this.mNetworkTypeBitmask, networkType);
    }

    public boolean canSupportLingeringNetworkType(int networkType) {
        if (this.mLingeringNetworkTypeBitmask == 0) {
            return canSupportNetworkType(networkType);
        }
        if (networkType == 16 && (this.mLingeringNetworkTypeBitmask & 3) != 0) {
            return true;
        }
        return ServiceState.bitmaskHasTech((int) this.mLingeringNetworkTypeBitmask, networkType);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mId);
        dest.writeString(this.mOperatorNumeric);
        dest.writeString(this.mEntryName);
        dest.writeString(this.mApnName);
        dest.writeString(this.mProxyAddress);
        dest.writeInt(this.mProxyPort);
        dest.writeParcelable(this.mMmsc, flags);
        dest.writeString(this.mMmsProxyAddress);
        dest.writeInt(this.mMmsProxyPort);
        dest.writeString(this.mUser);
        dest.writeString(this.mPassword);
        dest.writeInt(this.mAuthType);
        dest.writeInt(this.mApnTypeBitmask);
        dest.writeInt(this.mProtocol);
        dest.writeInt(this.mRoamingProtocol);
        dest.writeBoolean(this.mCarrierEnabled);
        dest.writeInt(this.mNetworkTypeBitmask);
        dest.writeLong(this.mLingeringNetworkTypeBitmask);
        dest.writeInt(this.mProfileId);
        dest.writeBoolean(this.mPersistent);
        dest.writeInt(this.mMaxConns);
        dest.writeInt(this.mWaitTime);
        dest.writeInt(this.mMaxConnsTime);
        dest.writeInt(this.mMtuV4);
        dest.writeInt(this.mMtuV6);
        dest.writeInt(this.mMvnoType);
        dest.writeString(this.mMvnoMatchData);
        dest.writeInt(this.mApnSetId);
        dest.writeInt(this.mCarrierId);
        dest.writeInt(this.mSkip464Xlat);
        dest.writeBoolean(this.mAlwaysOn);
        dest.writeInt(this.mInfrastructureBitmask);
        dest.writeBoolean(this.mEsimBootstrapProvisioning);
        dest.writeInt(this.mEditedStatus);
    }

    public static ApnSetting readFromParcel(Parcel in) {
        return new Builder()
                .setId(in.readInt())
                .setOperatorNumeric(in.readString())
                .setEntryName(in.readString())
                .setApnName(in.readString())
                .setProxyAddress(in.readString())
                .setProxyPort(in.readInt())
                .setMmsc((Uri) in.readParcelable(Uri.class.getClassLoader(), Uri.class))
                .setMmsProxyAddress(in.readString())
                .setMmsProxyPort(in.readInt())
                .setUser(in.readString())
                .setPassword(in.readString())
                .setAuthType(in.readInt())
                .setApnTypeBitmask(in.readInt())
                .setProtocol(in.readInt())
                .setRoamingProtocol(in.readInt())
                .setCarrierEnabled(in.readBoolean())
                .setNetworkTypeBitmask(in.readInt())
                .setLingeringNetworkTypeBitmask(in.readLong())
                .setProfileId(in.readInt())
                .setModemCognitive(in.readBoolean())
                .setMaxConns(in.readInt())
                .setWaitTime(in.readInt())
                .setMaxConnsTime(in.readInt())
                .setMtuV4(in.readInt())
                .setMtuV6(in.readInt())
                .setMvnoType(in.readInt())
                .setMvnoMatchData(in.readString())
                .setApnSetId(in.readInt())
                .setCarrierId(in.readInt())
                .setSkip464Xlat(in.readInt())
                .setAlwaysOn(in.readBoolean())
                .setInfrastructureBitmask(in.readInt())
                .setEsimBootstrapProvisioning(in.readBoolean())
                .setEditedStatus(in.readInt())
                .buildWithoutCheck();
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

        public Builder setId(int id) {
            this.mId = id;
            return this;
        }

        public Builder setMtuV4(int mtuV4) {
            this.mMtuV4 = mtuV4;
            return this;
        }

        public Builder setMtuV6(int mtuV6) {
            this.mMtuV6 = mtuV6;
            return this;
        }

        public Builder setProfileId(int profileId) {
            this.mProfileId = profileId;
            return this;
        }

        public Builder setPersistent(boolean isPersistent) {
            return setModemCognitive(isPersistent);
        }

        public Builder setModemCognitive(boolean modemCognitive) {
            this.mModemCognitive = modemCognitive;
            return this;
        }

        public Builder setMaxConns(int maxConns) {
            this.mMaxConns = maxConns;
            return this;
        }

        public Builder setWaitTime(int waitTime) {
            this.mWaitTime = waitTime;
            return this;
        }

        public Builder setMaxConnsTime(int maxConnsTime) {
            this.mMaxConnsTime = maxConnsTime;
            return this;
        }

        public Builder setMvnoMatchData(String mvnoMatchData) {
            this.mMvnoMatchData = mvnoMatchData;
            return this;
        }

        public Builder setApnSetId(int apnSetId) {
            this.mApnSetId = apnSetId;
            return this;
        }

        public Builder setEntryName(String entryName) {
            this.mEntryName = entryName;
            return this;
        }

        public Builder setApnName(String apnName) {
            this.mApnName = apnName;
            return this;
        }

        @Deprecated
        public Builder setProxyAddress(InetAddress proxy) {
            this.mProxyAddress = ApnSetting.inetAddressToString(proxy);
            return this;
        }

        public Builder setProxyAddress(String proxy) {
            this.mProxyAddress = proxy;
            return this;
        }

        public Builder setProxyPort(int port) {
            this.mProxyPort = port;
            return this;
        }

        public Builder setMmsc(Uri mmsc) {
            this.mMmsc = mmsc;
            return this;
        }

        @Deprecated
        public Builder setMmsProxyAddress(InetAddress mmsProxy) {
            this.mMmsProxyAddress = ApnSetting.inetAddressToString(mmsProxy);
            return this;
        }

        public Builder setMmsProxyAddress(String mmsProxy) {
            this.mMmsProxyAddress = mmsProxy;
            return this;
        }

        public Builder setMmsProxyPort(int mmsPort) {
            this.mMmsProxyPort = mmsPort;
            return this;
        }

        public Builder setUser(String user) {
            this.mUser = user;
            return this;
        }

        public Builder setPassword(String password) {
            this.mPassword = password;
            return this;
        }

        public Builder setAuthType(int authType) {
            this.mAuthType = authType;
            return this;
        }

        public Builder setApnTypeBitmask(int apnTypeBitmask) {
            this.mApnTypeBitmask = apnTypeBitmask;
            return this;
        }

        public Builder setOperatorNumeric(String operatorNumeric) {
            this.mOperatorNumeric = operatorNumeric;
            return this;
        }

        public Builder setProtocol(int protocol) {
            this.mProtocol = protocol;
            return this;
        }

        public Builder setRoamingProtocol(int roamingProtocol) {
            this.mRoamingProtocol = roamingProtocol;
            return this;
        }

        public Builder setCarrierEnabled(boolean carrierEnabled) {
            this.mCarrierEnabled = carrierEnabled;
            return this;
        }

        public Builder setNetworkTypeBitmask(int networkTypeBitmask) {
            this.mNetworkTypeBitmask = networkTypeBitmask;
            return this;
        }

        public Builder setLingeringNetworkTypeBitmask(long lingeringNetworkTypeBitmask) {
            this.mLingeringNetworkTypeBitmask = lingeringNetworkTypeBitmask;
            return this;
        }

        public Builder setMvnoType(int mvnoType) {
            this.mMvnoType = mvnoType;
            return this;
        }

        public Builder setCarrierId(int carrierId) {
            this.mCarrierId = carrierId;
            return this;
        }

        public Builder setSkip464Xlat(int skip464xlat) {
            this.mSkip464Xlat = skip464xlat;
            return this;
        }

        public Builder setAlwaysOn(boolean alwaysOn) {
            this.mAlwaysOn = alwaysOn;
            return this;
        }

        public Builder setInfrastructureBitmask(int infrastructureBitmask) {
            this.mInfrastructureBitmask = infrastructureBitmask;
            return this;
        }

        public Builder setEsimBootstrapProvisioning(boolean esimBootstrapProvisioning) {
            this.mEsimBootstrapProvisioning = esimBootstrapProvisioning;
            return this;
        }

        public Builder setEditedStatus(int editedStatus) {
            this.mEditedStatus = editedStatus;
            return this;
        }

        public ApnSetting build() {
            if ((this.mApnTypeBitmask & 65535) == 0
                    || TextUtils.isEmpty(this.mApnName)
                    || TextUtils.isEmpty(this.mEntryName)) {
                return null;
            }
            if ((this.mApnTypeBitmask & 2) != 0
                    && !TextUtils.isEmpty(this.mMmsProxyAddress)
                    && this.mMmsProxyAddress.startsWith(IntentFilter.SCHEME_HTTP)) {
                Log.wtf(
                        ApnSetting.LOG_TAG,
                        "mms proxy(" + this.mMmsProxyAddress + ") should be a hostname, not a url");
                Uri mMmsProxyAddressUri = Uri.parse(this.mMmsProxyAddress);
                this.mMmsProxyAddress = mMmsProxyAddressUri.getHost();
            }
            return new ApnSetting(this);
        }

        public ApnSetting buildWithoutCheck() {
            return new ApnSetting(this);
        }
    }

    public boolean apnTypeBitmaskSame(int apnTypeBitmask) {
        return this.mApnTypeBitmask == apnTypeBitmask;
    }

    public boolean hasApnTypeExt(int type) {
        return hasApnType(type);
    }
}
