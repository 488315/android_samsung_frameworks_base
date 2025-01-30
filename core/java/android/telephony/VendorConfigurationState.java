package android.telephony;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class VendorConfigurationState implements Parcelable {
  public static final String CONFIGURATION_CA_ENABLED = "CA_ENABLED";
  public static final String CONFIGURATION_EGPRS_SUPPORT = "EGPRS_SUPPORT";
  public static final String CONFIGURATION_FRAMEWORK_READY = "FW_READY";
  public static final String CONFIGURATION_ISCTC = "IS_CTC";
  public static final String CONFIGURATION_LTE_CS_CAPA = "LTE_CS_CAPA";
  public static final String CONFIGURATION_MSIM_SUBMODE = "MSIM_SUBMODE";
  public static final String CONFIGURATION_REJECT_INFO_FOR_SIM_TRANSFER =
      "REJECT_INFO_FOR_SIM_TRANSFER";
  public static final String CONFIGURATION_SUPPORTED_NRCA = "SUPPORTED_NRCA";
  public static final String CONFIGURATION_SUPPORTED_RAT = "SUPPORTED_RAT";
  public static final String CONFIGURATION_SUPPORT_LTE_CAPA_OPTION_C = "SUPPORT_LTE_CAPA_OPTION_C";
  public static final String CONFIGURATION_SUPPORT_UAC_TYPE = "SUPPORT_UAC_TYPE";
  public static final String CONFIGURATION_VOLTE_E911CALL = "VOLTE_911_CALL";
  public static final String CONFIGURATION_WFC_DEFAULT_SPN = "WFC_DEFAULT_SPN";
  public static final Parcelable.Creator<VendorConfigurationState> CREATOR =
      new Parcelable.Creator<
          VendorConfigurationState>() { // from class: android.telephony.VendorConfigurationState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VendorConfigurationState createFromParcel(Parcel in) {
          return new VendorConfigurationState(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VendorConfigurationState[] newArray(int size) {
          return new VendorConfigurationState[size];
        }
      };
  private static final String LOG_TAG = "VendorConfigurationState";
  private String mFrameWorkReady;
  private String mIsCaEnabled;
  private String mIsCtc;
  private String mLteCsCapa;
  private String mMsimSubmode;
  private int mPhoneId;
  private String mRejectInfoForSimTransfer;
  private String mSupportEgprs;
  private String mSupportLteCapaOptionC;
  private String mSupportUacType;
  private String mSupportedNrca;
  private String mSupportedRat;
  private String mVolte911Call;
  private String mWfcDefaultSpn;

  public VendorConfigurationState(int phoneId) {
    this.mPhoneId = phoneId;
    this.mFrameWorkReady = "0";
    this.mSupportEgprs = "0";
    this.mSupportedRat = "0";
    this.mIsCtc = "0";
    this.mVolte911Call = "0";
    this.mIsCaEnabled = "0";
    this.mLteCsCapa = "0";
    this.mMsimSubmode = "0";
    this.mSupportedNrca = "0";
    this.mSupportLteCapaOptionC = "0";
    this.mRejectInfoForSimTransfer = "0";
    this.mWfcDefaultSpn = "";
    this.mSupportUacType = "0";
  }

  public VendorConfigurationState(VendorConfigurationState s) {
    copyFrom(s);
  }

  protected void copyFrom(VendorConfigurationState s) {
    this.mFrameWorkReady = s.mFrameWorkReady;
    this.mSupportEgprs = s.mSupportEgprs;
    this.mSupportedRat = s.mSupportedRat;
    this.mIsCtc = s.mIsCtc;
    this.mVolte911Call = s.mVolte911Call;
    this.mIsCaEnabled = s.mIsCaEnabled;
    this.mLteCsCapa = s.mLteCsCapa;
    this.mMsimSubmode = s.mMsimSubmode;
    this.mSupportedNrca = s.mSupportedNrca;
    this.mSupportLteCapaOptionC = s.mSupportLteCapaOptionC;
    this.mRejectInfoForSimTransfer = s.mRejectInfoForSimTransfer;
    this.mWfcDefaultSpn = s.mWfcDefaultSpn;
    this.mSupportUacType = s.mSupportUacType;
  }

  @Deprecated
  public VendorConfigurationState(Parcel in) {
    this.mFrameWorkReady = in.readString();
    this.mSupportEgprs = in.readString();
    this.mSupportedRat = in.readString();
    this.mIsCtc = in.readString();
    this.mVolte911Call = in.readString();
    this.mIsCaEnabled = in.readString();
    this.mLteCsCapa = in.readString();
    this.mMsimSubmode = in.readString();
    this.mSupportedNrca = in.readString();
    this.mSupportLteCapaOptionC = in.readString();
    this.mRejectInfoForSimTransfer = in.readString();
    this.mWfcDefaultSpn = in.readString();
    this.mSupportUacType = in.readString();
  }

  @Override // android.os.Parcelable
  public void writeToParcel(Parcel out, int flags) {
    out.writeString(this.mFrameWorkReady);
    out.writeString(this.mSupportEgprs);
    out.writeString(this.mSupportedRat);
    out.writeString(this.mIsCtc);
    out.writeString(this.mVolte911Call);
    out.writeString(this.mIsCaEnabled);
    out.writeString(this.mLteCsCapa);
    out.writeString(this.mMsimSubmode);
    out.writeString(this.mSupportedNrca);
    out.writeString(this.mSupportLteCapaOptionC);
    out.writeString(this.mRejectInfoForSimTransfer);
    out.writeString(this.mWfcDefaultSpn);
    out.writeString(this.mSupportUacType);
  }

  @Override // android.os.Parcelable
  public int describeContents() {
    return 0;
  }

  public String toString() {
    StringBuffer sb = new StringBuffer(64);
    sb.append("Vendor Configuration state - ");
    sb.append("FrameWorkReady: ").append(this.mFrameWorkReady);
    sb.append(", SupportEgprs: ").append(this.mSupportEgprs);
    sb.append(", SupportedRat: ").append(this.mSupportedRat);
    sb.append(", IsCtc: ").append(this.mIsCtc);
    sb.append(", IsVolte911Call: ").append(this.mVolte911Call);
    sb.append(", IsCaEnabled: ").append(this.mIsCaEnabled);
    sb.append(", LteCsCapa: ").append(this.mLteCsCapa);
    sb.append(", MsimSubmode: ").append(this.mMsimSubmode);
    sb.append(", SupportedNrca: ").append(this.mSupportedNrca);
    sb.append(", SupportLteCapaOptionC: ").append(this.mSupportLteCapaOptionC);
    sb.append(", RejectInfoForSimTransfer: ").append(this.mRejectInfoForSimTransfer);
    sb.append(", WfcDefaultSpn: ").append(this.mWfcDefaultSpn);
    sb.append(", SupportUacType: ").append(this.mSupportUacType);
    return sb.toString();
  }

  public void setData(String name, String value) {
    if (CONFIGURATION_FRAMEWORK_READY.equals(name)) {
      if (!this.mFrameWorkReady.equals(value)) {
        setFrameWorkReady(value);
        return;
      }
      return;
    }
    if (CONFIGURATION_EGPRS_SUPPORT.equals(name)) {
      if (!this.mSupportEgprs.equals(value)) {
        setSupportEgprs(value);
        return;
      }
      return;
    }
    if (CONFIGURATION_SUPPORTED_RAT.equals(name)) {
      if (!this.mSupportedRat.equals(value)) {
        setSupportedRat(value);
        return;
      }
      return;
    }
    if (CONFIGURATION_ISCTC.equals(name)) {
      if (!this.mIsCtc.equals(value)) {
        setIsCtc(value);
        return;
      }
      return;
    }
    if (CONFIGURATION_VOLTE_E911CALL.equals(name)) {
      if (!this.mVolte911Call.equals(value)) {
        setVolte911Call(value);
        return;
      }
      return;
    }
    if (CONFIGURATION_CA_ENABLED.equals(name)) {
      if (!this.mIsCaEnabled.equals(value)) {
        setCaEnabled(value);
        return;
      }
      return;
    }
    if (CONFIGURATION_LTE_CS_CAPA.equals(name)) {
      if (!this.mLteCsCapa.equals(value)) {
        setLteCsCapa(value);
        return;
      }
      return;
    }
    if (CONFIGURATION_MSIM_SUBMODE.equals(name)) {
      if (!this.mMsimSubmode.equals(value)) {
        setMsimSubmode(value);
        return;
      }
      return;
    }
    if (CONFIGURATION_SUPPORTED_NRCA.equals(name)) {
      if (!this.mSupportedNrca.equals(value)) {
        setSupportedNrca(value);
        return;
      }
      return;
    }
    if (CONFIGURATION_SUPPORT_LTE_CAPA_OPTION_C.equals(name)) {
      if (!this.mSupportLteCapaOptionC.equals(value)) {
        setSupportLteCapaOptionC(value);
        return;
      }
      return;
    }
    if (CONFIGURATION_REJECT_INFO_FOR_SIM_TRANSFER.equals(name)) {
      if (!this.mRejectInfoForSimTransfer.equals(value)) {
        setRejectInfoForSimTransfer(value);
      }
    } else if (CONFIGURATION_WFC_DEFAULT_SPN.equals(name)) {
      if (!this.mWfcDefaultSpn.equals(value)) {
        setWfcDefaultSpn(value);
      }
    } else {
      if (CONFIGURATION_SUPPORT_UAC_TYPE.equals(name)) {
        if (!this.mSupportUacType.equals(value)) {
          setSupportUacType(value);
          return;
        }
        return;
      }
      Rlog.m77d(LOG_TAG, "VendorConfiguration does not match : " + name);
    }
  }

  public boolean isFrameWorkReady() {
    return "1".equals(this.mFrameWorkReady);
  }

  public void setFrameWorkReady(String ready) {
    this.mFrameWorkReady = ready;
  }

  public boolean getSupportEgprs() {
    return "1".equals(this.mSupportEgprs);
  }

  public void setSupportEgprs(String support) {
    this.mSupportEgprs = support;
  }

  public int getSupportedRat() {
    try {
      int rat = Integer.parseInt(this.mSupportedRat);
      return rat;
    } catch (NumberFormatException e) {
      return 0;
    }
  }

  public void setSupportedRat(String rat) {
    this.mSupportedRat = rat;
  }

  public boolean isCtc() {
    return "1".equals(this.mIsCtc);
  }

  public void setIsCtc(String isctc) {
    this.mIsCtc = isctc;
  }

  public boolean getVolte911Call() {
    return "1".equals(this.mVolte911Call);
  }

  public void setVolte911Call(String isvolte911call) {
    this.mVolte911Call = isvolte911call;
  }

  public boolean isCaEnabled() {
    return "1".equals(this.mIsCaEnabled);
  }

  public void setCaEnabled(String enabled) {
    this.mIsCaEnabled = enabled;
  }

  public int getLteCsCapa() {
    try {
      int capa = Integer.parseInt(this.mLteCsCapa);
      return capa;
    } catch (NumberFormatException e) {
      return 0;
    }
  }

  public void setLteCsCapa(String status) {
    this.mLteCsCapa = status;
  }

  public void setMsimSubmode(String mode) {
    this.mMsimSubmode = mode;
  }

  public int getMsimSubmode() {
    try {
      int mode = Integer.parseInt(this.mMsimSubmode);
      return mode;
    } catch (NumberFormatException e) {
      return 0;
    }
  }

  public void setSupportedNrca(String nrcaStatus) {
    this.mSupportedNrca = nrcaStatus;
  }

  public boolean getSupportedNrca() {
    return "1".equals(this.mSupportedNrca);
  }

  public void setSupportLteCapaOptionC(String lteCapaOptionC) {
    this.mSupportLteCapaOptionC = lteCapaOptionC;
  }

  public boolean isSupportLteCapaOptionC() {
    return "1".equals(this.mSupportLteCapaOptionC);
  }

  public void setWfcDefaultSpn(String wfcDefaultSpn) {
    this.mWfcDefaultSpn = wfcDefaultSpn;
  }

  public String getWfcDefaultSpn() {
    return this.mWfcDefaultSpn;
  }

  public void setRejectInfoForSimTransfer(String rejectInfo) {
    this.mRejectInfoForSimTransfer = rejectInfo;
  }

  public String getRejectInfoForSimTransfer() {
    return this.mRejectInfoForSimTransfer;
  }

  public void setSupportUacType(String uacType) {
    this.mSupportUacType = uacType;
  }

  public int getSupportUacType() {
    try {
      int uacType = Integer.parseInt(this.mSupportUacType);
      return uacType;
    } catch (NumberFormatException e) {
      return 0;
    }
  }
}
