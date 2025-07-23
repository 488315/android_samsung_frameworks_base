package android.telephony;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class VendorConfigurationState implements Parcelable {
    public static final String CONFIG_CA_ENABLED = "CA_ENABLED";
    public static final String CONFIG_EGPRS_SUPPORT = "EGPRS_SUPPORT";
    public static final String CONFIG_FRAMEWORK_READY = "FW_READY";
    public static final String CONFIG_ISCTC = "IS_CTC";
    public static final String CONFIG_LTE_CS_CAPA = "LTE_CS_CAPA";
    public static final String CONFIG_MSIM_SUBMODE = "MSIM_SUBMODE";
    public static final String CONFIG_REJECT_INFO_FOR_SIM_TRANSFER = "REJECT_INFO_FOR_SIM_TRANSFER";
    public static final String CONFIG_SUPPORTED_NRCA = "SUPPORTED_NRCA";
    public static final String CONFIG_SUPPORTED_RAT = "SUPPORTED_RAT";
    public static final String CONFIG_SUPPORT_HYBRID_DSDA = "SUPPORT_HYBRID_DSDA";
    public static final String CONFIG_SUPPORT_LTE_CAPA_OPTION_C = "SUPPORT_LTE_CAPA_OPTION_C";
    public static final String CONFIG_SUPPORT_UAC_TYPE = "SUPPORT_UAC_TYPE";
    public static final String CONFIG_VOLTE_E911CALL = "VOLTE_911_CALL";
    public static final String CONFIG_WFC_DEFAULT_SPN = "WFC_DEFAULT_SPN";
    public static final Parcelable.Creator<VendorConfigurationState> CREATOR = new Parcelable.Creator<VendorConfigurationState>() { // from class: android.telephony.VendorConfigurationState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VendorConfigurationState createFromParcel(Parcel parcel) {
            return new VendorConfigurationState(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VendorConfigurationState[] newArray(int i) {
            return new VendorConfigurationState[i];
        }
    };
    private static final String LOG_TAG = "VendorConfigurationState";
    private Map<String, String> mDataMap;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public VendorConfigurationState() {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        this.mDataMap = linkedHashMap;
        linkedHashMap.put(CONFIG_FRAMEWORK_READY, "0");
        this.mDataMap.put(CONFIG_EGPRS_SUPPORT, "0");
        this.mDataMap.put(CONFIG_SUPPORTED_RAT, "0");
        this.mDataMap.put(CONFIG_ISCTC, "0");
        this.mDataMap.put(CONFIG_VOLTE_E911CALL, "0");
        this.mDataMap.put(CONFIG_CA_ENABLED, "0");
        this.mDataMap.put(CONFIG_LTE_CS_CAPA, "0");
        this.mDataMap.put(CONFIG_MSIM_SUBMODE, "0");
        this.mDataMap.put(CONFIG_SUPPORTED_NRCA, "0");
        this.mDataMap.put(CONFIG_SUPPORT_LTE_CAPA_OPTION_C, "0");
        this.mDataMap.put(CONFIG_REJECT_INFO_FOR_SIM_TRANSFER, "0");
        this.mDataMap.put(CONFIG_WFC_DEFAULT_SPN, "");
        this.mDataMap.put(CONFIG_SUPPORT_UAC_TYPE, "0");
        this.mDataMap.put(CONFIG_SUPPORT_HYBRID_DSDA, "0");
    }

    public VendorConfigurationState(VendorConfigurationState vendorConfigurationState) {
        this.mDataMap = new LinkedHashMap();
        copyFrom(vendorConfigurationState);
    }

    protected void copyFrom(VendorConfigurationState vendorConfigurationState) {
        this.mDataMap = new LinkedHashMap(vendorConfigurationState.mDataMap);
    }

    @Deprecated
    public VendorConfigurationState(Parcel parcel) {
        this();
        for (int i = 0; i < this.mDataMap.size(); i++) {
            this.mDataMap.put(parcel.readString(), parcel.readString());
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        for (Map.Entry<String, String> entry : this.mDataMap.entrySet()) {
            parcel.writeString(entry.getKey());
            parcel.writeString(entry.getValue());
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer(64);
        stringBuffer.append("Vendor Configuration state - ");
        for (Map.Entry<String, String> entry : this.mDataMap.entrySet()) {
            stringBuffer.append(entry.getKey());
            stringBuffer.append(": ");
            stringBuffer.append(entry.getValue());
            stringBuffer.append(", ");
        }
        stringBuffer.setLength(stringBuffer.length() - 2);
        return stringBuffer.toString();
    }

    public void setData(String str, String str2) {
        if (this.mDataMap.containsKey(str)) {
            this.mDataMap.put(str, str2);
            return;
        }
        com.android.telephony.Rlog.d(LOG_TAG, "setData - VendorConfiguration does not match: " + str);
    }

    public String getData(String str) {
        if (this.mDataMap.containsKey(str)) {
            return this.mDataMap.get(str);
        }
        com.android.telephony.Rlog.d(LOG_TAG, "getData - VendorConfiguration does not match: " + str);
        return "";
    }

    public boolean getDataAsBool(String str) {
        return "1".equals(getData(str));
    }

    public int getDataAsInt(String str) {
        try {
            return Integer.parseInt(getData(str));
        } catch (NumberFormatException unused) {
            return 0;
        }
    }
}
