package android.telephony;

import android.annotation.SystemApi;
import android.content.Intent;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.Telephony;
import android.text.TextUtils;
import com.android.internal.telephony.DctConstants;
import com.android.internal.telephony.SemTelephonyUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/* loaded from: classes3.dex */
public class ServiceState implements Parcelable {
  public static final Parcelable.Creator<ServiceState> CREATOR =
      new Parcelable.Creator<ServiceState>() { // from class: android.telephony.ServiceState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ServiceState createFromParcel(Parcel in) {
          return new ServiceState(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ServiceState[] newArray(int size) {
          return new ServiceState[size];
        }
      };
  static final boolean DBG = false;
  public static final int DUPLEX_MODE_FDD = 1;
  public static final int DUPLEX_MODE_TDD = 2;
  public static final int DUPLEX_MODE_UNKNOWN = 0;
  private static final String EXTRA_SERVICE_STATE = "android.intent.extra.SERVICE_STATE";
  public static final int FREQUENCY_RANGE_COUNT = 5;
  public static final int FREQUENCY_RANGE_HIGH = 3;
  public static final int FREQUENCY_RANGE_LOW = 1;
  public static final int FREQUENCY_RANGE_MID = 2;
  public static final int FREQUENCY_RANGE_MMWAVE = 4;
  public static final int FREQUENCY_RANGE_UNKNOWN = 0;
  static final String LOG_TAG = "PHONE";
  public static final int MSIM_SUB_MODE_DSDA = 1;
  public static final int MSIM_SUB_MODE_DSDS = 0;
  private static final int NEXT_RIL_RADIO_TECHNOLOGY = 21;
  public static final int NR_5G_BEARER_STATUS_ALLOCATED = 1;
  public static final int NR_5G_BEARER_STATUS_MMW_ALLOCATED = 2;
  public static final int NR_5G_BEARER_STATUS_NOT_ALLOCATED = 0;
  public static final int OPTIONAL_RADIO_TECH_2G_DTM = 2;
  public static final int OPTIONAL_RADIO_TECH_DC = 1;
  public static final int OPTIONAL_RADIO_TECH_FIVE_G_EVO = 5;
  public static final int OPTIONAL_RADIO_TECH_FOUR_POINT_FIVE_G = 3;
  public static final int OPTIONAL_RADIO_TECH_FOUR_POINT_FIVE_G_PLUS = 4;
  public static final int OPTIONAL_RADIO_TECH_NONE = 0;
  public static final int REGISTRATION_TYPE_CELLULAR = 1;
  public static final int REGISTRATION_TYPE_NOCELLULAR = 2;
  public static final int REGISTRATION_TYPE_UNKNOWN = 0;
  public static final int RIL_FEMTOCELL_INDICATOR_LTE = 1;
  public static final int RIL_FEMTOCELL_INDICATOR_NONE = 0;
  public static final int RIL_RADIO_CDMA_TECHNOLOGY_BITMASK = 6392;
  public static final int RIL_RADIO_TECHNOLOGY_1xRTT = 6;
  public static final int RIL_RADIO_TECHNOLOGY_EDGE = 2;
  public static final int RIL_RADIO_TECHNOLOGY_EHRPD = 13;
  public static final int RIL_RADIO_TECHNOLOGY_EVDO_0 = 7;
  public static final int RIL_RADIO_TECHNOLOGY_EVDO_A = 8;
  public static final int RIL_RADIO_TECHNOLOGY_EVDO_B = 12;
  public static final int RIL_RADIO_TECHNOLOGY_GPRS = 1;
  public static final int RIL_RADIO_TECHNOLOGY_GSM = 16;
  public static final int RIL_RADIO_TECHNOLOGY_HSDPA = 9;
  public static final int RIL_RADIO_TECHNOLOGY_HSPA = 11;
  public static final int RIL_RADIO_TECHNOLOGY_HSPAP = 15;
  public static final int RIL_RADIO_TECHNOLOGY_HSUPA = 10;
  public static final int RIL_RADIO_TECHNOLOGY_IS95A = 4;
  public static final int RIL_RADIO_TECHNOLOGY_IS95B = 5;
  public static final int RIL_RADIO_TECHNOLOGY_IWLAN = 18;
  public static final int RIL_RADIO_TECHNOLOGY_LTE = 14;
  public static final int RIL_RADIO_TECHNOLOGY_LTE_CA = 19;
  public static final int RIL_RADIO_TECHNOLOGY_NR = 20;
  public static final int RIL_RADIO_TECHNOLOGY_TD_SCDMA = 17;
  public static final int RIL_RADIO_TECHNOLOGY_UMTS = 3;
  public static final int RIL_RADIO_TECHNOLOGY_UNKNOWN = 0;

  @SystemApi public static final int ROAMING_TYPE_DOMESTIC = 2;

  @SystemApi public static final int ROAMING_TYPE_INTERNATIONAL = 3;

  @SystemApi public static final int ROAMING_TYPE_NOT_ROAMING = 0;

  @SystemApi public static final int ROAMING_TYPE_UNKNOWN = 1;
  public static final int SEM_ROAMING_TYPE_DOMESTIC = 2;
  public static final int SEM_ROAMING_TYPE_INTERNATIONAL = 3;
  public static final int SEM_ROAMING_TYPE_NOT_ROAMING = 0;
  public static final int SEM_ROAMING_TYPE_UNKNOWN = 1;
  public static final int SNAPSHOT_STATUS_ACTIVATED = 1;
  public static final int SNAPSHOT_STATUS_DEACTIVATED = 0;
  public static final int STATE_EMERGENCY_ONLY = 2;
  public static final int STATE_IN_SERVICE = 0;
  public static final int STATE_OUT_OF_SERVICE = 1;
  public static final int STATE_POWER_OFF = 3;
  public static final int UNKNOWN_ID = -1;
  static final boolean VDBG = false;
  private int mArfcnRsrpBoost;
  private int mCdmaDefaultRoamingIndicator;
  private int mCdmaEriIconIndex;
  private int mCdmaEriIconMode;
  private int mCdmaRoamingIndicator;
  private int[] mCellBandwidths;
  private int mChannelNumber;
  private boolean mCssIndicator;
  private int mDataRegState;
  private int mFemtocellIndicator;
  private boolean mIsDataRoamingFromRegistration;
  private boolean mIsEmergencyOnly;
  private boolean mIsIwlanPreferred;
  private boolean mIsManualNetworkSelection;
  private boolean mIsPsOnlyReg;
  private boolean mIsSprDisplayRoam;
  private boolean mIsVoiceCallAvailable;
  private int mMsimSubmode;
  private int mNetworkId;
  private final List<NetworkRegistrationInfo> mNetworkRegistrationInfos;
  private int mNrFrequencyRange;
  private String mOperatorAlphaLong;
  private String mOperatorAlphaLongRaw;
  private String mOperatorAlphaShort;
  private String mOperatorAlphaShortRaw;
  private String mOperatorNumeric;
  private int mOptionalRadioTech;
  private int mSnapshotStatus;
  private int mSystemId;
  private int mVoiceRegState;
  private int mVoiceRegType;

  @Retention(RetentionPolicy.SOURCE)
  public @interface DuplexMode {}

  @Retention(RetentionPolicy.SOURCE)
  public @interface FrequencyRange {}

  @Retention(RetentionPolicy.SOURCE)
  public @interface RegState {}

  @Retention(RetentionPolicy.SOURCE)
  public @interface RilRadioTechnology {}

  @Retention(RetentionPolicy.SOURCE)
  public @interface RoamingType {}

  public static final String getRoamingLogString(int roamingType) {
    switch (roamingType) {
      case 0:
        return "home";
      case 1:
        return Telephony.Carriers.ROAMING;
      case 2:
        return "Domestic Roaming";
      case 3:
        return "International Roaming";
      default:
        return "UNKNOWN";
    }
  }

  public static ServiceState newFromBundle(Bundle m) {
    ServiceState ret = new ServiceState();
    ret.setFromNotifierBundle(m);
    return ret;
  }

  public ServiceState() {
    this.mVoiceRegState = 1;
    this.mDataRegState = 1;
    this.mCdmaEriIconIndex = 1;
    this.mCellBandwidths = new int[0];
    this.mArfcnRsrpBoost = 0;
    this.mNetworkRegistrationInfos = new ArrayList();
    this.mVoiceRegType = 0;
    this.mSnapshotStatus = 0;
    this.mIsPsOnlyReg = false;
    this.mFemtocellIndicator = 0;
    this.mIsSprDisplayRoam = false;
    this.mOptionalRadioTech = 0;
    this.mMsimSubmode = 0;
    this.mIsVoiceCallAvailable = false;
  }

  public ServiceState(ServiceState s) {
    this.mVoiceRegState = 1;
    this.mDataRegState = 1;
    this.mCdmaEriIconIndex = 1;
    this.mCellBandwidths = new int[0];
    this.mArfcnRsrpBoost = 0;
    this.mNetworkRegistrationInfos = new ArrayList();
    this.mVoiceRegType = 0;
    this.mSnapshotStatus = 0;
    this.mIsPsOnlyReg = false;
    this.mFemtocellIndicator = 0;
    this.mIsSprDisplayRoam = false;
    this.mOptionalRadioTech = 0;
    this.mMsimSubmode = 0;
    this.mIsVoiceCallAvailable = false;
    copyFrom(s);
  }

  protected void copyFrom(ServiceState s) {
    this.mVoiceRegState = s.mVoiceRegState;
    this.mDataRegState = s.mDataRegState;
    this.mOperatorAlphaLong = s.mOperatorAlphaLong;
    this.mOperatorAlphaShort = s.mOperatorAlphaShort;
    this.mOperatorNumeric = s.mOperatorNumeric;
    this.mIsManualNetworkSelection = s.mIsManualNetworkSelection;
    this.mCssIndicator = s.mCssIndicator;
    this.mNetworkId = s.mNetworkId;
    this.mSystemId = s.mSystemId;
    this.mCdmaRoamingIndicator = s.mCdmaRoamingIndicator;
    this.mCdmaDefaultRoamingIndicator = s.mCdmaDefaultRoamingIndicator;
    this.mCdmaEriIconIndex = s.mCdmaEriIconIndex;
    this.mCdmaEriIconMode = s.mCdmaEriIconMode;
    this.mIsEmergencyOnly = s.mIsEmergencyOnly;
    this.mChannelNumber = s.mChannelNumber;
    int[] iArr = s.mCellBandwidths;
    this.mCellBandwidths = iArr == null ? null : Arrays.copyOf(iArr, iArr.length);
    this.mArfcnRsrpBoost = s.mArfcnRsrpBoost;
    synchronized (this.mNetworkRegistrationInfos) {
      this.mNetworkRegistrationInfos.clear();
      for (NetworkRegistrationInfo nri : s.getNetworkRegistrationInfoList()) {
        this.mNetworkRegistrationInfos.add(new NetworkRegistrationInfo(nri));
      }
    }
    this.mNrFrequencyRange = s.mNrFrequencyRange;
    this.mOperatorAlphaLongRaw = s.mOperatorAlphaLongRaw;
    this.mOperatorAlphaShortRaw = s.mOperatorAlphaShortRaw;
    this.mIsDataRoamingFromRegistration = s.mIsDataRoamingFromRegistration;
    this.mIsIwlanPreferred = s.mIsIwlanPreferred;
    this.mVoiceRegType = s.mVoiceRegType;
    this.mSnapshotStatus = s.mSnapshotStatus;
    this.mIsPsOnlyReg = s.mIsPsOnlyReg;
    this.mFemtocellIndicator = s.mFemtocellIndicator;
    this.mIsSprDisplayRoam = s.mIsSprDisplayRoam;
    this.mOptionalRadioTech = s.mOptionalRadioTech;
    this.mMsimSubmode = s.mMsimSubmode;
    this.mIsVoiceCallAvailable = s.mIsVoiceCallAvailable;
  }

  @Deprecated
  public ServiceState(Parcel in) {
    boolean z;
    boolean z2;
    boolean z3;
    boolean z4;
    boolean z5;
    this.mVoiceRegState = 1;
    this.mDataRegState = 1;
    this.mCdmaEriIconIndex = 1;
    this.mCellBandwidths = new int[0];
    this.mArfcnRsrpBoost = 0;
    ArrayList arrayList = new ArrayList();
    this.mNetworkRegistrationInfos = arrayList;
    this.mVoiceRegType = 0;
    this.mSnapshotStatus = 0;
    this.mIsPsOnlyReg = false;
    this.mFemtocellIndicator = 0;
    this.mIsSprDisplayRoam = false;
    this.mOptionalRadioTech = 0;
    this.mMsimSubmode = 0;
    this.mIsVoiceCallAvailable = false;
    this.mVoiceRegState = in.readInt();
    this.mDataRegState = in.readInt();
    this.mOperatorAlphaLong = in.readString();
    this.mOperatorAlphaShort = in.readString();
    this.mOperatorNumeric = in.readString();
    if (in.readInt() != 0) {
      z = true;
    } else {
      z = false;
    }
    this.mIsManualNetworkSelection = z;
    if (in.readInt() != 0) {
      z2 = true;
    } else {
      z2 = false;
    }
    this.mCssIndicator = z2;
    this.mNetworkId = in.readInt();
    this.mSystemId = in.readInt();
    this.mCdmaRoamingIndicator = in.readInt();
    this.mCdmaDefaultRoamingIndicator = in.readInt();
    this.mCdmaEriIconIndex = in.readInt();
    this.mCdmaEriIconMode = in.readInt();
    if (in.readInt() != 0) {
      z3 = true;
    } else {
      z3 = false;
    }
    this.mIsEmergencyOnly = z3;
    this.mArfcnRsrpBoost = in.readInt();
    synchronized (arrayList) {
      in.readList(
          arrayList, NetworkRegistrationInfo.class.getClassLoader(), NetworkRegistrationInfo.class);
    }
    this.mChannelNumber = in.readInt();
    this.mCellBandwidths = in.createIntArray();
    this.mNrFrequencyRange = in.readInt();
    this.mOperatorAlphaLongRaw = in.readString();
    this.mOperatorAlphaShortRaw = in.readString();
    this.mIsDataRoamingFromRegistration = in.readBoolean();
    this.mIsIwlanPreferred = in.readBoolean();
    this.mVoiceRegType = in.readInt();
    this.mSnapshotStatus = in.readInt();
    if (in.readInt() != 0) {
      z4 = true;
    } else {
      z4 = false;
    }
    this.mIsPsOnlyReg = z4;
    this.mFemtocellIndicator = in.readInt();
    if (in.readInt() != 0) {
      z5 = true;
    } else {
      z5 = false;
    }
    this.mIsSprDisplayRoam = z5;
    this.mOptionalRadioTech = in.readInt();
    this.mMsimSubmode = in.readInt();
    this.mIsVoiceCallAvailable = in.readInt() != 0;
  }

  @Override // android.os.Parcelable
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeInt(this.mVoiceRegState);
    parcel.writeInt(this.mDataRegState);
    parcel.writeString(this.mOperatorAlphaLong);
    parcel.writeString(this.mOperatorAlphaShort);
    parcel.writeString(this.mOperatorNumeric);
    parcel.writeInt(this.mIsManualNetworkSelection ? 1 : 0);
    parcel.writeInt(this.mCssIndicator ? 1 : 0);
    parcel.writeInt(this.mNetworkId);
    parcel.writeInt(this.mSystemId);
    parcel.writeInt(this.mCdmaRoamingIndicator);
    parcel.writeInt(this.mCdmaDefaultRoamingIndicator);
    parcel.writeInt(this.mCdmaEriIconIndex);
    parcel.writeInt(this.mCdmaEriIconMode);
    parcel.writeInt(this.mIsEmergencyOnly ? 1 : 0);
    parcel.writeInt(this.mArfcnRsrpBoost);
    synchronized (this.mNetworkRegistrationInfos) {
      parcel.writeList(this.mNetworkRegistrationInfos);
    }
    parcel.writeInt(this.mChannelNumber);
    parcel.writeIntArray(this.mCellBandwidths);
    parcel.writeInt(this.mNrFrequencyRange);
    parcel.writeString(this.mOperatorAlphaLongRaw);
    parcel.writeString(this.mOperatorAlphaShortRaw);
    parcel.writeBoolean(this.mIsDataRoamingFromRegistration);
    parcel.writeBoolean(this.mIsIwlanPreferred);
    parcel.writeInt(this.mVoiceRegType);
    parcel.writeInt(this.mSnapshotStatus);
    parcel.writeInt(this.mIsPsOnlyReg ? 1 : 0);
    parcel.writeInt(this.mFemtocellIndicator);
    parcel.writeInt(this.mIsSprDisplayRoam ? 1 : 0);
    parcel.writeInt(this.mOptionalRadioTech);
    parcel.writeInt(this.mMsimSubmode);
    parcel.writeInt(this.mIsVoiceCallAvailable ? 1 : 0);
  }

  @Override // android.os.Parcelable
  public int describeContents() {
    return 0;
  }

  public int getState() {
    return getVoiceRegState();
  }

  public int getVoiceRegState() {
    return this.mVoiceRegState;
  }

  public int getDataRegState() {
    return this.mDataRegState;
  }

  public int getDataRegistrationState() {
    return getDataRegState();
  }

  public int getDuplexMode() {
    if (!isPsOnlyTech(getRilDataRadioTechnology())) {
      return 0;
    }
    int band = AccessNetworkUtils.getOperatingBandForEarfcn(this.mChannelNumber);
    return AccessNetworkUtils.getDuplexModeForEutranBand(band);
  }

  public int getChannelNumber() {
    return this.mChannelNumber;
  }

  public int[] getCellBandwidths() {
    int[] iArr = this.mCellBandwidths;
    return iArr == null ? new int[0] : iArr;
  }

  public boolean getRoaming() {
    return getVoiceRoaming() || getDataRoaming();
  }

  public boolean getVoiceRoaming() {
    return getVoiceRoamingType() != 0;
  }

  public int getVoiceRoamingType() {
    NetworkRegistrationInfo regState = getNetworkRegistrationInfo(1, 1);
    if (regState != null) {
      return regState.getRoamingType();
    }
    return 0;
  }

  public int semGetVoiceRoamingType() {
    return getVoiceRoamingType();
  }

  public boolean getDataRoaming() {
    return getDataRoamingType() != 0;
  }

  public void setDataRoamingFromRegistration(boolean dataRoaming) {
    this.mIsDataRoamingFromRegistration = dataRoaming;
  }

  public boolean getDataRoamingFromRegistration() {
    return this.mIsDataRoamingFromRegistration;
  }

  public int getDataRoamingType() {
    NetworkRegistrationInfo regState = getNetworkRegistrationInfo(2, 1);
    if (regState != null) {
      return regState.getRoamingType();
    }
    return 0;
  }

  public int semGetCurrentDataRoamingType() {
    return getDataRoamingType();
  }

  public boolean isEmergencyOnly() {
    return this.mIsEmergencyOnly;
  }

  public boolean semIsEmergencyOnly() {
    return isEmergencyOnly();
  }

  public int getCdmaRoamingIndicator() {
    return this.mCdmaRoamingIndicator;
  }

  public int getCdmaDefaultRoamingIndicator() {
    return this.mCdmaDefaultRoamingIndicator;
  }

  public int getCdmaEriIconIndex() {
    return this.mCdmaEriIconIndex;
  }

  public int getCdmaEriIconMode() {
    return this.mCdmaEriIconMode;
  }

  public String getOperatorAlphaLong() {
    return this.mOperatorAlphaLong;
  }

  public String getVoiceOperatorAlphaLong() {
    return this.mOperatorAlphaLong;
  }

  public String getOperatorAlphaShort() {
    return this.mOperatorAlphaShort;
  }

  public String getVoiceOperatorAlphaShort() {
    return this.mOperatorAlphaShort;
  }

  public String getDataOperatorAlphaShort() {
    return this.mOperatorAlphaShort;
  }

  public String getOperatorAlpha() {
    if (TextUtils.isEmpty(this.mOperatorAlphaLong)) {
      return this.mOperatorAlphaShort;
    }
    return this.mOperatorAlphaLong;
  }

  public String getOperatorNumeric() {
    return this.mOperatorNumeric;
  }

  public String getVoiceOperatorNumeric() {
    return this.mOperatorNumeric;
  }

  public String getDataOperatorNumeric() {
    return this.mOperatorNumeric;
  }

  public boolean getIsManualSelection() {
    return this.mIsManualNetworkSelection;
  }

  public int hashCode() {
    int hash;
    synchronized (this.mNetworkRegistrationInfos) {
      Object[] objArr = new Object[31];
      objArr[0] = Integer.valueOf(this.mVoiceRegState);
      objArr[1] = Integer.valueOf(this.mDataRegState);
      objArr[2] = Integer.valueOf(this.mChannelNumber);
      objArr[3] = Integer.valueOf(Arrays.hashCode(this.mCellBandwidths));
      objArr[4] = this.mOperatorAlphaLong;
      objArr[5] = this.mOperatorAlphaShort;
      objArr[6] = this.mOperatorNumeric;
      objArr[7] = Boolean.valueOf(this.mIsManualNetworkSelection);
      objArr[8] = Boolean.valueOf(this.mCssIndicator);
      objArr[9] = Integer.valueOf(this.mNetworkId);
      objArr[10] = Integer.valueOf(this.mSystemId);
      objArr[11] = Integer.valueOf(this.mCdmaRoamingIndicator);
      objArr[12] = Integer.valueOf(this.mCdmaDefaultRoamingIndicator);
      objArr[13] = Integer.valueOf(this.mCdmaEriIconIndex);
      objArr[14] = Integer.valueOf(this.mCdmaEriIconMode);
      objArr[15] = Integer.valueOf(this.mVoiceRegType);
      objArr[16] = Integer.valueOf(this.mSnapshotStatus);
      objArr[17] = Integer.valueOf(this.mIsPsOnlyReg ? 1 : 0);
      objArr[18] = Integer.valueOf(this.mFemtocellIndicator);
      objArr[19] = Integer.valueOf(this.mIsSprDisplayRoam ? 1 : 0);
      objArr[20] = Integer.valueOf(this.mOptionalRadioTech);
      objArr[21] = Integer.valueOf(this.mMsimSubmode);
      objArr[22] = Integer.valueOf(this.mIsVoiceCallAvailable ? 1 : 0);
      objArr[23] = Boolean.valueOf(this.mIsEmergencyOnly);
      objArr[24] = Integer.valueOf(this.mArfcnRsrpBoost);
      objArr[25] = this.mNetworkRegistrationInfos;
      objArr[26] = Integer.valueOf(this.mNrFrequencyRange);
      objArr[27] = this.mOperatorAlphaLongRaw;
      objArr[28] = this.mOperatorAlphaShortRaw;
      objArr[29] = Boolean.valueOf(this.mIsDataRoamingFromRegistration);
      objArr[30] = Boolean.valueOf(this.mIsIwlanPreferred);
      hash = Objects.hash(objArr);
    }
    return hash;
  }

  public boolean equals(Object o) {
    boolean z = false;
    if (!(o instanceof ServiceState)) {
      return false;
    }
    ServiceState s = (ServiceState) o;
    synchronized (this.mNetworkRegistrationInfos) {
      if (this.mVoiceRegState == s.mVoiceRegState
          && this.mDataRegState == s.mDataRegState
          && this.mIsManualNetworkSelection == s.mIsManualNetworkSelection
          && this.mChannelNumber == s.mChannelNumber
          && Arrays.equals(this.mCellBandwidths, s.mCellBandwidths)
          && equalsHandlesNulls(this.mOperatorAlphaLong, s.mOperatorAlphaLong)
          && equalsHandlesNulls(this.mOperatorAlphaShort, s.mOperatorAlphaShort)
          && equalsHandlesNulls(this.mOperatorNumeric, s.mOperatorNumeric)
          && equalsHandlesNulls(
              Boolean.valueOf(this.mCssIndicator), Boolean.valueOf(s.mCssIndicator))
          && equalsHandlesNulls(Integer.valueOf(this.mNetworkId), Integer.valueOf(s.mNetworkId))
          && equalsHandlesNulls(Integer.valueOf(this.mSystemId), Integer.valueOf(s.mSystemId))
          && equalsHandlesNulls(
              Integer.valueOf(this.mCdmaRoamingIndicator), Integer.valueOf(s.mCdmaRoamingIndicator))
          && equalsHandlesNulls(
              Integer.valueOf(this.mCdmaDefaultRoamingIndicator),
              Integer.valueOf(s.mCdmaDefaultRoamingIndicator))
          && equalsHandlesNulls(
              Integer.valueOf(this.mVoiceRegType), Integer.valueOf(s.mVoiceRegType))
          && equalsHandlesNulls(
              Integer.valueOf(this.mSnapshotStatus), Integer.valueOf(s.mSnapshotStatus))
          && this.mIsPsOnlyReg == s.mIsPsOnlyReg
          && equalsHandlesNulls(
              Integer.valueOf(this.mFemtocellIndicator), Integer.valueOf(s.mFemtocellIndicator))
          && this.mIsSprDisplayRoam == s.mIsSprDisplayRoam
          && this.mOptionalRadioTech == s.mOptionalRadioTech
          && this.mMsimSubmode == s.mMsimSubmode
          && this.mIsVoiceCallAvailable == s.mIsVoiceCallAvailable
          && this.mIsEmergencyOnly == s.mIsEmergencyOnly
          && equalsHandlesNulls(this.mOperatorAlphaLongRaw, s.mOperatorAlphaLongRaw)
          && equalsHandlesNulls(this.mOperatorAlphaShortRaw, s.mOperatorAlphaShortRaw)
          && this.mNetworkRegistrationInfos.size() == s.mNetworkRegistrationInfos.size()
          && this.mNetworkRegistrationInfos.containsAll(s.mNetworkRegistrationInfos)
          && this.mNrFrequencyRange == s.mNrFrequencyRange
          && this.mIsDataRoamingFromRegistration == s.mIsDataRoamingFromRegistration
          && this.mIsIwlanPreferred == s.mIsIwlanPreferred) {
        z = true;
      }
    }
    return z;
  }

  public static String roamingTypeToString(int roamingType) {
    switch (roamingType) {
      case 0:
        return "NOT_ROAMING";
      case 1:
        return "UNKNOWN";
      case 2:
        return "DOMESTIC";
      case 3:
        return "INTERNATIONAL";
      default:
        return "Unknown roaming type " + roamingType;
    }
  }

  public static String rilRadioTechnologyToString(int rt) {
    switch (rt) {
      case 0:
        return "Unknown";
      case 1:
        return "GPRS";
      case 2:
        return "EDGE";
      case 3:
        return "UMTS";
      case 4:
        return "CDMA-IS95A";
      case 5:
        return "CDMA-IS95B";
      case 6:
        return "1xRTT";
      case 7:
        return "EvDo-rev.0";
      case 8:
        return "EvDo-rev.A";
      case 9:
        return "HSDPA";
      case 10:
        return "HSUPA";
      case 11:
        return "HSPA";
      case 12:
        return "EvDo-rev.B";
      case 13:
        return "eHRPD";
      case 14:
        return DctConstants.RAT_NAME_LTE;
      case 15:
        return "HSPAP";
      case 16:
        return "GSM";
      case 17:
        return "TD-SCDMA";
      case 18:
        return "IWLAN";
      case 19:
        return "LTE_CA";
      case 20:
        return "NR_SA";
      default:
        com.android.telephony.Rlog.m246w(LOG_TAG, "Unexpected radioTechnology=" + rt);
        return "Unexpected";
    }
  }

  public static String frequencyRangeToString(int range) {
    switch (range) {
      case 0:
        return "UNKNOWN";
      case 1:
        return "LOW";
      case 2:
        return "MID";
      case 3:
        return "HIGH";
      case 4:
        return "MMWAVE";
      default:
        return Integer.toString(range);
    }
  }

  public static String rilServiceStateToString(int serviceState) {
    switch (serviceState) {
      case 0:
        return "IN_SERVICE";
      case 1:
        return "OUT_OF_SERVICE";
      case 2:
        return "EMERGENCY_ONLY";
      case 3:
        return "POWER_OFF";
      default:
        return "UNKNOWN";
    }
  }

  public String toString() {
    String str;
    synchronized (this.mNetworkRegistrationInfos) {
      str =
          "{mVoiceRegState="
              + this.mVoiceRegState
              + (NavigationBarInflaterView.KEY_CODE_START
                  + rilServiceStateToString(this.mVoiceRegState)
                  + NavigationBarInflaterView.KEY_CODE_END)
              + ", mDataRegState="
              + this.mDataRegState
              + (NavigationBarInflaterView.KEY_CODE_START
                  + rilServiceStateToString(this.mDataRegState)
                  + NavigationBarInflaterView.KEY_CODE_END)
              + ", mChannelNumber="
              + this.mChannelNumber
              + ", duplexMode()="
              + getDuplexMode()
              + ", mCellBandwidths="
              + Arrays.toString(this.mCellBandwidths)
              + ", mOperatorAlphaLong="
              + this.mOperatorAlphaLong
              + ", mOperatorAlphaShort="
              + this.mOperatorAlphaShort
              + ", isManualNetworkSelection="
              + this.mIsManualNetworkSelection
              + (this.mIsManualNetworkSelection ? "(manual)" : "(automatic)")
              + ", getRilVoiceRadioTechnology="
              + getRilVoiceRadioTechnology()
              + (NavigationBarInflaterView.KEY_CODE_START
                  + rilRadioTechnologyToString(getRilVoiceRadioTechnology())
                  + NavigationBarInflaterView.KEY_CODE_END)
              + ", getRilDataRadioTechnology="
              + getRilDataRadioTechnology()
              + (NavigationBarInflaterView.KEY_CODE_START
                  + rilRadioTechnologyToString(getRilDataRadioTechnology())
                  + NavigationBarInflaterView.KEY_CODE_END)
              + ", mCssIndicator="
              + (this.mCssIndicator ? "supported" : "unsupported")
              + ", mNetworkId="
              + SemTelephonyUtils.maskPiiFromCellIdentity(this.mNetworkId)
              + ", mSystemId="
              + SemTelephonyUtils.maskPiiFromCellIdentity(this.mSystemId)
              + ", mCdmaRoamingIndicator="
              + this.mCdmaRoamingIndicator
              + ", mCdmaDefaultRoamingIndicator="
              + this.mCdmaDefaultRoamingIndicator
              + ", VoiceRegType="
              + this.mVoiceRegType
              + ", Snap="
              + this.mSnapshotStatus
              + ", PsOnly="
              + this.mIsPsOnlyReg
              + ", FemtocellInd="
              + this.mFemtocellIndicator
              + ", SprDisplayRoam="
              + this.mIsSprDisplayRoam
              + ", OptRadioTech="
              + this.mOptionalRadioTech
              + ", MsimSubmode="
              + this.mMsimSubmode
              + ", IsVoiceCallAvailable="
              + this.mIsVoiceCallAvailable
              + ", mIsEmergencyOnly="
              + this.mIsEmergencyOnly
              + ", isUsingCarrierAggregation="
              + isUsingCarrierAggregation()
              + ", mArfcnRsrpBoost="
              + this.mArfcnRsrpBoost
              + ", mNetworkRegistrationInfos="
              + this.mNetworkRegistrationInfos
              + ", mNrFrequencyRange="
              + (Build.IS_DEBUGGABLE ? this.mNrFrequencyRange : 0)
              + ", mOperatorAlphaLongRaw="
              + this.mOperatorAlphaLongRaw
              + ", mOperatorAlphaShortRaw="
              + this.mOperatorAlphaShortRaw
              + ", mIsDataRoamingFromRegistration="
              + this.mIsDataRoamingFromRegistration
              + ", mIsIwlanPreferred="
              + this.mIsIwlanPreferred
              + "}";
    }
    return str;
  }

  public String toSimpleString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Voice=[").append(rilServiceStateToString(this.mVoiceRegState));
    sb.append(" ").append(getRoamingLogString(getVoiceRoamingType()));
    sb.append(" ").append(rilRadioTechnologyToString(getRilVoiceRadioTechnology()));
    sb.append("] Data=[").append(rilServiceStateToString(this.mDataRegState));
    sb.append(" ").append(getRoamingLogString(getDataRoamingType()));
    sb.append(" ").append(rilRadioTechnologyToString(getRilDataRadioTechnology()));
    if (this.mIsPsOnlyReg) {
      sb.append(" PsOnly");
    }
    if (isUsingCarrierAggregation()) {
      sb.append(" CA");
    }
    if (this.mSnapshotStatus == 1) {
      sb.append(" Snap");
    }
    if (this.mIsIwlanPreferred) {
      sb.append(" WlanPref");
    }
    sb.append(NavigationBarInflaterView.SIZE_MOD_END);
    sb.append(" Op=[L:").append(this.mOperatorAlphaLong);
    sb.append(", S:").append(this.mOperatorAlphaShort);
    sb.append(", N:").append(this.mOperatorNumeric);
    sb.append(", LR:").append(this.mOperatorAlphaLongRaw);
    sb.append(", SR:")
        .append(this.mOperatorAlphaShortRaw)
        .append(NavigationBarInflaterView.SIZE_MOD_END);
    if (!SemTelephonyUtils.SHIP_BUILD) {
      if (this.mNetworkId != -1) {
        sb.append(" NID=").append(this.mNetworkId);
      }
      if (this.mSystemId != -1) {
        sb.append(" SID=").append(this.mSystemId);
      }
    }
    if (this.mCdmaRoamingIndicator != -1) {
      sb.append(" RoamInd=").append(this.mCdmaRoamingIndicator);
    }
    if (this.mCdmaDefaultRoamingIndicator != -1) {
      sb.append(" DefRoamInd=").append(this.mCdmaDefaultRoamingIndicator);
    }
    if (this.mCssIndicator) {
      sb.append(" CSS");
    }
    if (this.mIsManualNetworkSelection) {
      sb.append(" Manual");
    }
    if (this.mIsEmergencyOnly) {
      sb.append(" EmergOnly");
    }
    if (this.mOptionalRadioTech != 0) {
      sb.append(" OptRadioTech").append(this.mOptionalRadioTech);
    }
    if (!this.mIsVoiceCallAvailable) {
      sb.append(" VoiceCallNotAvailable");
    }
    if (this.mVoiceRegType != 0) {
      sb.append(" VoiceType=").append(this.mVoiceRegType);
    }
    if (this.mFemtocellIndicator != 0) {
      sb.append(" FemtoInd=").append(this.mFemtocellIndicator);
    }
    if (this.mIsSprDisplayRoam) {
      sb.append(" SprDisplayRoam");
    }
    if (this.mArfcnRsrpBoost != 0) {
      sb.append(" RsrpBoost=").append(this.mArfcnRsrpBoost);
    }
    if (this.mChannelNumber != -1) {
      sb.append(" Channel=").append(this.mChannelNumber);
    }
    if (getDuplexMode() != 0) {
      sb.append(" Duplex=").append(getDuplexMode());
    }
    sb.append(" CellBandwidths=").append(Arrays.toString(this.mCellBandwidths));
    if (this.mNrFrequencyRange != 0) {
      sb.append(" NrFreq=").append(this.mNrFrequencyRange);
    }
    sb.append(" NetRegiInfos=").append(this.mNetworkRegistrationInfos);
    sb.append(" MsimSubmode=").append(this.mMsimSubmode);
    return sb.toString();
  }

  private void init() {
    this.mVoiceRegState = 1;
    this.mDataRegState = 1;
    this.mChannelNumber = -1;
    this.mCellBandwidths = new int[0];
    this.mOperatorAlphaLong = null;
    this.mOperatorAlphaShort = null;
    this.mOperatorNumeric = null;
    this.mIsManualNetworkSelection = false;
    this.mCssIndicator = false;
    this.mNetworkId = -1;
    this.mSystemId = -1;
    this.mCdmaRoamingIndicator = -1;
    this.mCdmaDefaultRoamingIndicator = -1;
    this.mCdmaEriIconIndex = -1;
    this.mCdmaEriIconMode = -1;
    this.mIsEmergencyOnly = false;
    this.mArfcnRsrpBoost = 0;
    this.mNrFrequencyRange = 0;
    this.mVoiceRegType = 0;
    this.mSnapshotStatus = 0;
    this.mIsPsOnlyReg = false;
    this.mFemtocellIndicator = 0;
    this.mIsSprDisplayRoam = false;
    this.mOptionalRadioTech = 0;
    this.mMsimSubmode = 0;
    this.mIsVoiceCallAvailable = false;
    synchronized (this.mNetworkRegistrationInfos) {
      this.mNetworkRegistrationInfos.clear();
      addNetworkRegistrationInfo(
          new NetworkRegistrationInfo.Builder()
              .setDomain(1)
              .setTransportType(1)
              .setRegistrationState(4)
              .build());
      addNetworkRegistrationInfo(
          new NetworkRegistrationInfo.Builder()
              .setDomain(2)
              .setTransportType(1)
              .setRegistrationState(4)
              .build());
      addNetworkRegistrationInfo(
          new NetworkRegistrationInfo.Builder()
              .setDomain(2)
              .setTransportType(2)
              .setRegistrationState(4)
              .build());
    }
    this.mOperatorAlphaLongRaw = null;
    this.mOperatorAlphaShortRaw = null;
    this.mIsDataRoamingFromRegistration = false;
    this.mIsIwlanPreferred = false;
  }

  public void setStateOutOfService() {
    init();
  }

  public void setStateOff() {
    init();
    this.mVoiceRegState = 3;
    this.mDataRegState = 3;
  }

  public void setOutOfService(boolean powerOff) {
    init();
    if (powerOff) {
      this.mVoiceRegState = 3;
      this.mDataRegState = 3;
    }
  }

  public void setState(int state) {
    setVoiceRegState(state);
  }

  public void setVoiceRegState(int state) {
    this.mVoiceRegState = state;
  }

  public void setDataRegState(int state) {
    this.mDataRegState = state;
  }

  public void setCellBandwidths(int[] bandwidths) {
    this.mCellBandwidths = bandwidths;
  }

  public void setChannelNumber(int channelNumber) {
    this.mChannelNumber = channelNumber;
  }

  public void setRoaming(boolean roaming) {
    setVoiceRoaming(roaming);
    setDataRoaming(roaming);
  }

  public void setVoiceRoaming(boolean z) {
    setVoiceRoamingType(z ? 1 : 0);
  }

  public void setVoiceRoamingType(int type) {
    NetworkRegistrationInfo regInfo = getNetworkRegistrationInfo(1, 1);
    if (regInfo == null) {
      regInfo = new NetworkRegistrationInfo.Builder().setDomain(1).setTransportType(1).build();
    }
    regInfo.setRoamingType(type);
    addNetworkRegistrationInfo(regInfo);
  }

  public void setDataRoaming(boolean z) {
    setDataRoamingType(z ? 1 : 0);
  }

  public void setDataRoamingType(int type) {
    NetworkRegistrationInfo regInfo = getNetworkRegistrationInfo(2, 1);
    if (regInfo == null) {
      regInfo = new NetworkRegistrationInfo.Builder().setDomain(2).setTransportType(1).build();
    }
    regInfo.setRoamingType(type);
    addNetworkRegistrationInfo(regInfo);
  }

  public void setEmergencyOnly(boolean emergencyOnly) {
    this.mIsEmergencyOnly = emergencyOnly;
  }

  public void setCdmaRoamingIndicator(int roaming) {
    this.mCdmaRoamingIndicator = roaming;
  }

  public void setCdmaDefaultRoamingIndicator(int roaming) {
    this.mCdmaDefaultRoamingIndicator = roaming;
  }

  public void setCdmaEriIconIndex(int index) {
    this.mCdmaEriIconIndex = index;
  }

  public void setCdmaEriIconMode(int mode) {
    this.mCdmaEriIconMode = mode;
  }

  public void setOperatorName(String longName, String shortName, String numeric) {
    this.mOperatorAlphaLong = longName;
    this.mOperatorAlphaShort = shortName;
    this.mOperatorNumeric = numeric;
  }

  public void setOperatorAlphaLong(String longName) {
    this.mOperatorAlphaLong = longName;
  }

  public void setIsManualSelection(boolean isManual) {
    this.mIsManualNetworkSelection = isManual;
  }

  private static boolean equalsHandlesNulls(Object a, Object b) {
    return a == null ? b == null : a.equals(b);
  }

  private void setFromNotifierBundle(Bundle m) {
    ServiceState ssFromBundle =
        (ServiceState) m.getParcelable(EXTRA_SERVICE_STATE, ServiceState.class);
    if (ssFromBundle != null) {
      copyFrom(ssFromBundle);
    }
  }

  public void fillInNotifierBundle(Bundle m) {
    m.putParcelable(EXTRA_SERVICE_STATE, this);
    m.putInt(Intent.EXTRA_VOICE_REG_STATE, this.mVoiceRegState);
    m.putInt(Intent.EXTRA_DATA_REG_STATE, this.mDataRegState);
    m.putInt(Intent.EXTRA_DATA_ROAMING_TYPE, getDataRoamingType());
    m.putInt(Intent.EXTRA_VOICE_ROAMING_TYPE, getVoiceRoamingType());
    m.putString(Intent.EXTRA_OPERATOR_ALPHA_LONG, this.mOperatorAlphaLong);
    m.putString(Intent.EXTRA_OPERATOR_ALPHA_SHORT, this.mOperatorAlphaShort);
    m.putString(Intent.EXTRA_OPERATOR_NUMERIC, this.mOperatorNumeric);
    m.putString(Intent.EXTRA_DATA_OPERATOR_ALPHA_LONG, this.mOperatorAlphaLong);
    m.putString(Intent.EXTRA_DATA_OPERATOR_ALPHA_SHORT, this.mOperatorAlphaShort);
    m.putString(Intent.EXTRA_DATA_OPERATOR_NUMERIC, this.mOperatorNumeric);
    m.putBoolean(Intent.EXTRA_MANUAL, this.mIsManualNetworkSelection);
    m.putInt(Intent.EXTRA_VOICE_RADIO_TECH, getRilVoiceRadioTechnology());
    m.putInt(Intent.EXTRA_DATA_RADIO_TECH, getRilDataRadioTechnology());
    m.putBoolean(Intent.EXTRA_CSS_INDICATOR, this.mCssIndicator);
    m.putInt("networkId", this.mNetworkId);
    m.putInt(Intent.EXTRA_SYSTEM_ID, this.mSystemId);
    m.putInt(Intent.EXTRA_CDMA_ROAMING_INDICATOR, this.mCdmaRoamingIndicator);
    m.putInt(Intent.EXTRA_CDMA_DEFAULT_ROAMING_INDICATOR, this.mCdmaDefaultRoamingIndicator);
    m.putBoolean(Intent.EXTRA_EMERGENCY_ONLY, this.mIsEmergencyOnly);
    m.putBoolean(Intent.EXTRA_IS_DATA_ROAMING_FROM_REGISTRATION, getDataRoamingFromRegistration());
    m.putBoolean(Intent.EXTRA_IS_USING_CARRIER_AGGREGATION, isUsingCarrierAggregation());
    m.putInt("ArfcnRsrpBoost", this.mArfcnRsrpBoost);
    m.putInt("ChannelNumber", this.mChannelNumber);
    m.putIntArray("CellBandwidths", this.mCellBandwidths);
    m.putInt("mNrFrequencyRange", this.mNrFrequencyRange);
    m.putString("operator-alpha-long-raw", this.mOperatorAlphaLongRaw);
    m.putString("operator-alpha-short-raw", this.mOperatorAlphaShortRaw);
    m.putInt("voiceRegType", this.mVoiceRegType);
    m.putInt("snapshotstatus", this.mSnapshotStatus);
    m.putBoolean("isPsOnlyReg", this.mIsPsOnlyReg);
    m.putInt("femtocellIndicator", this.mFemtocellIndicator);
    m.putBoolean("isSprDisplayRoam", this.mIsSprDisplayRoam);
    m.putInt("optionalRadioTech", this.mOptionalRadioTech);
    m.putInt("msimSubmode", this.mMsimSubmode);
    m.putBoolean("isVoiceCallAvailable", this.mIsVoiceCallAvailable);
  }

  public void setRilVoiceRadioTechnology(int rt) {
    com.android.telephony.Rlog.m240e(
        LOG_TAG,
        "ServiceState.setRilVoiceRadioTechnology() called. It's encouraged to use"
            + " addNetworkRegistrationInfo() instead *******");
    NetworkRegistrationInfo regInfo = getNetworkRegistrationInfo(1, 1);
    if (regInfo == null) {
      regInfo = new NetworkRegistrationInfo.Builder().setDomain(1).setTransportType(1).build();
    }
    regInfo.setAccessNetworkTechnology(rilRadioTechnologyToNetworkType(rt));
    addNetworkRegistrationInfo(regInfo);
  }

  public void setRilDataRadioTechnology(int rt) {
    com.android.telephony.Rlog.m240e(
        LOG_TAG,
        "ServiceState.setRilDataRadioTechnology() called. It's encouraged to use"
            + " addNetworkRegistrationInfo() instead *******");
    NetworkRegistrationInfo regInfo = getNetworkRegistrationInfo(2, 1);
    if (regInfo == null) {
      regInfo = new NetworkRegistrationInfo.Builder().setDomain(2).setTransportType(1).build();
    }
    regInfo.setAccessNetworkTechnology(rilRadioTechnologyToNetworkType(rt));
    addNetworkRegistrationInfo(regInfo);
  }

  public boolean isUsingCarrierAggregation() {
    int rat = getRilMobileDataRadioTechnology();
    if (rat != 14 && rat != 19 && getCellBandwidths().length > 1) {
      return true;
    }
    synchronized (this.mNetworkRegistrationInfos) {
      for (NetworkRegistrationInfo nri : this.mNetworkRegistrationInfos) {
        if (nri.isUsingCarrierAggregation()) {
          return true;
        }
      }
      return false;
    }
  }

  public int getNrFrequencyRange() {
    return this.mNrFrequencyRange;
  }

  public int getNrState() {
    NetworkRegistrationInfo regInfo = getNetworkRegistrationInfo(2, 1);
    if (regInfo == null) {
      return 0;
    }
    return regInfo.getNrState();
  }

  public void setNrFrequencyRange(int nrFrequencyRange) {
    this.mNrFrequencyRange = nrFrequencyRange;
  }

  public int getArfcnRsrpBoost() {
    return this.mArfcnRsrpBoost;
  }

  public void setArfcnRsrpBoost(int arfcnRsrpBoost) {
    this.mArfcnRsrpBoost = arfcnRsrpBoost;
  }

  public void setCssIndicator(int css) {
    this.mCssIndicator = css != 0;
  }

  public void setCdmaSystemAndNetworkId(int systemId, int networkId) {
    this.mSystemId = systemId;
    this.mNetworkId = networkId;
  }

  public int getRilVoiceRadioTechnology() {
    NetworkRegistrationInfo wwanRegInfo = getNetworkRegistrationInfo(1, 1);
    if (wwanRegInfo != null) {
      return networkTypeToRilRadioTechnology(wwanRegInfo.getAccessNetworkTechnology());
    }
    return 0;
  }

  public int getRilDataRadioTechnology() {
    return networkTypeToRilRadioTechnology(getDataNetworkType());
  }

  public static int rilRadioTechnologyToNetworkType(int rat) {
    switch (rat) {
      case 1:
        return 1;
      case 2:
        return 2;
      case 3:
        return 3;
      case 4:
      case 5:
        return 4;
      case 6:
        return 7;
      case 7:
        return 5;
      case 8:
        return 6;
      case 9:
        return 8;
      case 10:
        return 9;
      case 11:
        return 10;
      case 12:
        return 12;
      case 13:
        return 14;
      case 14:
        return 13;
      case 15:
        return 15;
      case 16:
        return 16;
      case 17:
        return 17;
      case 18:
        return 18;
      case 19:
        return 19;
      case 20:
        return 20;
      default:
        return 0;
    }
  }

  public static int rilRadioTechnologyToAccessNetworkType(int rt) {
    switch (rt) {
      case 1:
      case 2:
      case 16:
        return 1;
      case 3:
      case 9:
      case 10:
      case 11:
      case 15:
      case 17:
        return 2;
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
      case 12:
      case 13:
        return 4;
      case 14:
      case 19:
        return 3;
      case 18:
        return 5;
      case 20:
        return 6;
      default:
        return 0;
    }
  }

  public static int networkTypeToRilRadioTechnology(int networkType) {
    switch (networkType) {
      case 1:
        return 1;
      case 2:
        return 2;
      case 3:
        return 3;
      case 4:
        return 4;
      case 5:
        return 7;
      case 6:
        return 8;
      case 7:
        return 6;
      case 8:
        return 9;
      case 9:
        return 10;
      case 10:
        return 11;
      case 11:
      default:
        return 0;
      case 12:
        return 12;
      case 13:
        return 14;
      case 14:
        return 13;
      case 15:
        return 15;
      case 16:
        return 16;
      case 17:
        return 17;
      case 18:
        return 18;
      case 19:
        return 19;
      case 20:
        return 20;
    }
  }

  public int getDataNetworkType() {
    NetworkRegistrationInfo iwlanRegInfo = getNetworkRegistrationInfo(2, 2);
    NetworkRegistrationInfo wwanRegInfo = getNetworkRegistrationInfo(2, 1);
    if (iwlanRegInfo == null || !iwlanRegInfo.isInService()) {
      if (wwanRegInfo != null) {
        return wwanRegInfo.getAccessNetworkTechnology();
      }
      return 0;
    }
    if (!wwanRegInfo.isInService() || this.mIsIwlanPreferred) {
      return iwlanRegInfo.getAccessNetworkTechnology();
    }
    return wwanRegInfo.getAccessNetworkTechnology();
  }

  public int getVoiceNetworkType() {
    NetworkRegistrationInfo regState = getNetworkRegistrationInfo(1, 1);
    if (regState != null) {
      return regState.getAccessNetworkTechnology();
    }
    return 0;
  }

  public int getCssIndicator() {
    return this.mCssIndicator ? 1 : 0;
  }

  public int getCdmaNetworkId() {
    return this.mNetworkId;
  }

  public int getCdmaSystemId() {
    return this.mSystemId;
  }

  public static boolean isGsm(int radioTechnology) {
    return radioTechnology == 1
        || radioTechnology == 2
        || radioTechnology == 3
        || radioTechnology == 9
        || radioTechnology == 10
        || radioTechnology == 11
        || radioTechnology == 14
        || radioTechnology == 15
        || radioTechnology == 16
        || radioTechnology == 17
        || radioTechnology == 18
        || radioTechnology == 19
        || radioTechnology == 20;
  }

  public static boolean isCdma(int radioTechnology) {
    return radioTechnology == 4
        || radioTechnology == 5
        || radioTechnology == 6
        || radioTechnology == 7
        || radioTechnology == 8
        || radioTechnology == 12
        || radioTechnology == 13;
  }

  public static boolean isPsOnlyTech(int radioTechnology) {
    return radioTechnology == 14 || radioTechnology == 19 || radioTechnology == 20;
  }

  public static boolean bearerBitmapHasCdma(int networkTypeBitmask) {
    return (convertNetworkTypeBitmaskToBearerBitmask(networkTypeBitmask)
            & RIL_RADIO_CDMA_TECHNOLOGY_BITMASK)
        != 0;
  }

  public static boolean bitmaskHasTech(int bearerBitmask, int radioTech) {
    if (bearerBitmask == 0) {
      return true;
    }
    if (radioTech >= 1 && ((1 << (radioTech - 1)) & bearerBitmask) != 0) {
      return true;
    }
    return false;
  }

  public static int getBitmaskForTech(int radioTech) {
    if (radioTech >= 1) {
      return 1 << (radioTech - 1);
    }
    return 0;
  }

  public static int getBitmaskFromString(String bearerList) {
    String[] bearers = bearerList.split("\\|");
    int bearerBitmask = 0;
    for (String bearer : bearers) {
      try {
        int bearerInt = Integer.parseInt(bearer.trim());
        if (bearerInt == 0) {
          return 0;
        }
        bearerBitmask |= getBitmaskForTech(bearerInt);
      } catch (NumberFormatException e) {
        return 0;
      }
    }
    return bearerBitmask;
  }

  public static int convertNetworkTypeBitmaskToBearerBitmask(int networkTypeBitmask) {
    if (networkTypeBitmask == 0) {
      return 0;
    }
    int bearerBitmask = 0;
    for (int bearerInt = 0; bearerInt < 21; bearerInt++) {
      if (bitmaskHasTech(networkTypeBitmask, rilRadioTechnologyToNetworkType(bearerInt))) {
        bearerBitmask |= getBitmaskForTech(bearerInt);
      }
    }
    return bearerBitmask;
  }

  public static int convertBearerBitmaskToNetworkTypeBitmask(int bearerBitmask) {
    if (bearerBitmask == 0) {
      return 0;
    }
    int networkTypeBitmask = 0;
    for (int bearerInt = 0; bearerInt < 21; bearerInt++) {
      if (bitmaskHasTech(bearerBitmask, bearerInt)) {
        networkTypeBitmask |= getBitmaskForTech(rilRadioTechnologyToNetworkType(bearerInt));
      }
    }
    return networkTypeBitmask;
  }

  public static ServiceState mergeServiceStates(ServiceState baseSs, ServiceState voiceSs) {
    if (voiceSs.mVoiceRegState != 0) {
      return baseSs;
    }
    ServiceState newSs = new ServiceState(baseSs);
    newSs.mVoiceRegState = voiceSs.mVoiceRegState;
    newSs.mIsEmergencyOnly = false;
    return newSs;
  }

  public List<NetworkRegistrationInfo> getNetworkRegistrationInfoList() {
    List<NetworkRegistrationInfo> newList;
    synchronized (this.mNetworkRegistrationInfos) {
      newList = new ArrayList<>();
      for (NetworkRegistrationInfo nri : this.mNetworkRegistrationInfos) {
        newList.add(new NetworkRegistrationInfo(nri));
      }
    }
    return newList;
  }

  @SystemApi
  public List<NetworkRegistrationInfo> getNetworkRegistrationInfoListForTransportType(
      int transportType) {
    List<NetworkRegistrationInfo> list = new ArrayList<>();
    synchronized (this.mNetworkRegistrationInfos) {
      for (NetworkRegistrationInfo networkRegistrationInfo : this.mNetworkRegistrationInfos) {
        if (networkRegistrationInfo.getTransportType() == transportType) {
          list.add(new NetworkRegistrationInfo(networkRegistrationInfo));
        }
      }
    }
    return list;
  }

  @SystemApi
  public List<NetworkRegistrationInfo> getNetworkRegistrationInfoListForDomain(int domain) {
    List<NetworkRegistrationInfo> list = new ArrayList<>();
    synchronized (this.mNetworkRegistrationInfos) {
      for (NetworkRegistrationInfo networkRegistrationInfo : this.mNetworkRegistrationInfos) {
        if ((networkRegistrationInfo.getDomain() & domain) != 0) {
          list.add(new NetworkRegistrationInfo(networkRegistrationInfo));
        }
      }
    }
    return list;
  }

  @SystemApi
  public NetworkRegistrationInfo getNetworkRegistrationInfo(int domain, int transportType) {
    synchronized (this.mNetworkRegistrationInfos) {
      for (NetworkRegistrationInfo networkRegistrationInfo : this.mNetworkRegistrationInfos) {
        if (networkRegistrationInfo.getTransportType() == transportType
            && (networkRegistrationInfo.getDomain() & domain) != 0) {
          return new NetworkRegistrationInfo(networkRegistrationInfo);
        }
      }
      return null;
    }
  }

  public void addNetworkRegistrationInfo(NetworkRegistrationInfo nri) {
    if (nri == null) {
      return;
    }
    synchronized (this.mNetworkRegistrationInfos) {
      int i = 0;
      while (true) {
        if (i >= this.mNetworkRegistrationInfos.size()) {
          break;
        }
        NetworkRegistrationInfo curRegState = this.mNetworkRegistrationInfos.get(i);
        if (curRegState.getTransportType() != nri.getTransportType()
            || curRegState.getDomain() != nri.getDomain()) {
          i++;
        } else {
          this.mNetworkRegistrationInfos.remove(i);
          break;
        }
      }
      this.mNetworkRegistrationInfos.add(new NetworkRegistrationInfo(nri));
    }
  }

  public ServiceState createLocationInfoSanitizedCopy(boolean removeCoarseLocation) {
    ServiceState state = new ServiceState(this);
    synchronized (state.mNetworkRegistrationInfos) {
      List<NetworkRegistrationInfo> networkRegistrationInfos =
          (List)
              state.mNetworkRegistrationInfos.stream()
                  .map(
                      new Function() { // from class:
                                       // android.telephony.ServiceState$$ExternalSyntheticLambda0
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                          return ((NetworkRegistrationInfo) obj).sanitizeLocationInfo();
                        }
                      })
                  .collect(Collectors.toList());
      state.mNetworkRegistrationInfos.clear();
      state.mNetworkRegistrationInfos.addAll(networkRegistrationInfos);
    }
    if (!removeCoarseLocation) {
      return state;
    }
    state.mOperatorAlphaLong = null;
    state.mOperatorAlphaShort = null;
    state.mOperatorNumeric = null;
    state.mSystemId = -1;
    state.mNetworkId = -1;
    return state;
  }

  public void setOperatorAlphaLongRaw(String operatorAlphaLong) {
    this.mOperatorAlphaLongRaw = operatorAlphaLong;
  }

  public String getOperatorAlphaLongRaw() {
    return this.mOperatorAlphaLongRaw;
  }

  public void setOperatorAlphaShortRaw(String operatorAlphaShort) {
    this.mOperatorAlphaShortRaw = operatorAlphaShort;
  }

  public String getOperatorAlphaShortRaw() {
    return this.mOperatorAlphaShortRaw;
  }

  public void setIwlanPreferred(boolean isIwlanPreferred) {
    this.mIsIwlanPreferred = isIwlanPreferred;
  }

  public boolean isIwlanPreferred() {
    return this.mIsIwlanPreferred;
  }

  public boolean isSearching() {
    NetworkRegistrationInfo psRegState = getNetworkRegistrationInfo(2, 1);
    if (psRegState != null && psRegState.getRegistrationState() == 2) {
      return true;
    }
    NetworkRegistrationInfo csRegState = getNetworkRegistrationInfo(1, 1);
    return csRegState != null && csRegState.getRegistrationState() == 2;
  }

  public static boolean isFrequencyRangeValid(int frequencyRange) {
    if (frequencyRange == 1 || frequencyRange == 2 || frequencyRange == 3 || frequencyRange == 4) {
      return true;
    }
    return false;
  }

  public boolean isUsingNonTerrestrialNetwork() {
    synchronized (this.mNetworkRegistrationInfos) {
      for (NetworkRegistrationInfo nri : this.mNetworkRegistrationInfos) {
        if (nri.isNonTerrestrialNetwork()) {
          return true;
        }
      }
      return false;
    }
  }

  public int getVoiceRegType() {
    return this.mVoiceRegType;
  }

  public void setVoiceRegType(int voiceRegType) {
    this.mVoiceRegType = voiceRegType;
  }

  public boolean canCellularVoiceService() {
    return this.mVoiceRegType != 2;
  }

  public int getSnapshotStatus() {
    return this.mSnapshotStatus;
  }

  public void setSnapshotStatus(int snapshotStatus) {
    this.mSnapshotStatus = snapshotStatus;
  }

  public int getMobileDataRegState() {
    NetworkRegistrationInfo nri = getNetworkRegistrationInfo(2, 1);
    if (nri == null) {
      return 1;
    }
    switch (nri.getNetworkRegistrationState()) {
      case 1:
      case 5:
        break;
    }
    return 1;
  }

  public int getMobileDataRoamingType() {
    return getDataRoamingType();
  }

  public boolean getMobileDataRoaming() {
    return getDataRoamingType() != 0;
  }

  public int getRilMobileDataRadioTechnology() {
    NetworkRegistrationInfo nri = getNetworkRegistrationInfo(2, 1);
    if (nri != null) {
      return networkTypeToRilRadioTechnology(nri.getAccessNetworkTechnology());
    }
    return 0;
  }

  public boolean isPsOnlyReg() {
    return this.mIsPsOnlyReg;
  }

  public void setPsOnlyReg(boolean psOnlyReg) {
    this.mIsPsOnlyReg = psOnlyReg;
  }

  public int getFemtocellIndicator() {
    return this.mFemtocellIndicator;
  }

  public void setFemtocellIndicator(int femtocellIndicator) {
    this.mFemtocellIndicator = femtocellIndicator;
  }

  public boolean getSprDisplayRoam() {
    return this.mIsSprDisplayRoam;
  }

  public void setSprDisplayRoam(boolean sprDisplayRoam) {
    this.mIsSprDisplayRoam = sprDisplayRoam;
  }

  public int getOptionalRadioTech() {
    return this.mOptionalRadioTech;
  }

  public void setOptionalRadioTech(int optTech) {
    this.mOptionalRadioTech = optTech;
  }

  public void setMsimSubmode(int submode) {
    this.mMsimSubmode = submode;
  }

  public int getMsimSubmode() {
    return this.mMsimSubmode;
  }

  public boolean isVoiceCallAvailable() {
    return this.mIsVoiceCallAvailable;
  }

  public void setVoiceCallAvailable(boolean voiceAvailable) {
    this.mIsVoiceCallAvailable = voiceAvailable;
  }

  public boolean semIsOnlyPsRegistered() {
    return isPsOnlyReg();
  }

  public int getEndcStatus() {
    DataSpecificRegistrationInfo dataSpecificInfo;
    NetworkRegistrationInfo networkRegistrationInfo = getNetworkRegistrationInfo(2, 1);
    if (networkRegistrationInfo == null
        || (dataSpecificInfo = networkRegistrationInfo.getDataSpecificInfo()) == null) {
      return 0;
    }
    return dataSpecificInfo.isEnDcAvailable ? 1 : 0;
  }

  public int getRestrictDcnrStatus() {
    DataSpecificRegistrationInfo dataSpecificInfo;
    NetworkRegistrationInfo networkRegistrationInfo = getNetworkRegistrationInfo(2, 1);
    if (networkRegistrationInfo == null
        || (dataSpecificInfo = networkRegistrationInfo.getDataSpecificInfo()) == null) {
      return 0;
    }
    return dataSpecificInfo.isDcNrRestricted ? 1 : 0;
  }
}
