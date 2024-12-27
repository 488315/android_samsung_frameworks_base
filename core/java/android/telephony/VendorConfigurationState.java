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
    public static final String CONFIG_SUPPORT_LTE_CAPA_OPTION_C = "SUPPORT_LTE_CAPA_OPTION_C";
    public static final String CONFIG_SUPPORT_UAC_TYPE = "SUPPORT_UAC_TYPE";
    public static final String CONFIG_VOLTE_E911CALL = "VOLTE_911_CALL";
    public static final String CONFIG_WFC_DEFAULT_SPN = "WFC_DEFAULT_SPN";
    public static final Parcelable.Creator<VendorConfigurationState> CREATOR =
            new Parcelable.Creator<
                    VendorConfigurationState>() { // from class:
                                                  // android.telephony.VendorConfigurationState.1
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
    private Map<String, String> mDataMap;

    public VendorConfigurationState() {
        this.mDataMap = new LinkedHashMap();
        this.mDataMap.put(CONFIG_FRAMEWORK_READY, "0");
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
    }

    public VendorConfigurationState(VendorConfigurationState s) {
        this.mDataMap = new LinkedHashMap();
        copyFrom(s);
    }

    protected void copyFrom(VendorConfigurationState s) {
        this.mDataMap = new LinkedHashMap(s.mDataMap);
    }

    @Deprecated
    public VendorConfigurationState(Parcel in) {
        this();
        for (int i = 0; i < this.mDataMap.size(); i++) {
            this.mDataMap.put(in.readString(), in.readString());
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        for (Map.Entry<String, String> entry : this.mDataMap.entrySet()) {
            out.writeString(entry.getKey());
            out.writeString(entry.getValue());
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(64);
        sb.append("Vendor Configuration state - ");
        for (Map.Entry<String, String> entry : this.mDataMap.entrySet()) {
            sb.append(entry.getKey());
            sb.append(": ");
            sb.append(entry.getValue());
            sb.append(", ");
        }
        sb.setLength(sb.length() - 2);
        return sb.toString();
    }

    public void setData(String name, String value) {
        if (this.mDataMap.containsKey(name)) {
            this.mDataMap.put(name, value);
        } else {
            com.android.telephony.Rlog.d(
                    LOG_TAG, "setData - VendorConfiguration does not match: " + name);
        }
    }

    public String getData(String name) {
        if (this.mDataMap.containsKey(name)) {
            return this.mDataMap.get(name);
        }
        com.android.telephony.Rlog.d(
                LOG_TAG, "getData - VendorConfiguration does not match: " + name);
        return "";
    }

    public boolean getDataAsBool(String name) {
        String strValue = getData(name);
        return "1".equals(strValue);
    }

    public int getDataAsInt(String name) {
        try {
            int numValue = Integer.parseInt(getData(name));
            return numValue;
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
