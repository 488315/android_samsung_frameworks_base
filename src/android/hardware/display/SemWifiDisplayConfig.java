package android.hardware.display;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class SemWifiDisplayConfig implements Parcelable {
    public static final int CONNECTION_TYPE_AP = 2;
    public static final int CONNECTION_TYPE_P2P = 1;
    public static final int CONNECTION_TYPE_USB = 3;
    public static final Parcelable.Creator<SemWifiDisplayConfig> CREATOR = new Parcelable.Creator<SemWifiDisplayConfig>() { // from class: android.hardware.display.SemWifiDisplayConfig.2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemWifiDisplayConfig createFromParcel(Parcel parcel) {
            return new SemWifiDisplayConfig(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemWifiDisplayConfig[] newArray(int i) {
            return new SemWifiDisplayConfig[i];
        }
    };
    public static final int FLAG_INITIATE_MIRRORING = 32;
    public static final int FLAG_PIN_CONNECT = 8192;
    public static final int FLAG_SKIP_PIN_NUMBER_CONFIRM = 16384;
    public static final int MODE_DEX_ON_PC = 3;
    public static final int MODE_NORMAL_MIRRORING = 0;
    public static final int MODE_WATCH_CAMERA = 1;
    public static final int MODE_WIRELESS_DEX = 2;
    private static final String TAG = "SemWifiDisplayConfig";
    private int mConnectionType;
    private String mDeviceName;
    private int mFlags;
    private String mIpAddress;
    private int mMode;
    private String mP2pMacAddress;
    private String mPort;
    private HashMap<String, List<SemWifiDisplayParameter>> mPrameterGroups;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SemWifiDisplayConfig() {
        this.mConnectionType = 1;
        this.mFlags = 0;
        this.mMode = 0;
        this.mPrameterGroups = new HashMap<String, List<SemWifiDisplayParameter>>() { // from class: android.hardware.display.SemWifiDisplayConfig.1
            {
                put(SemWifiDisplayParameter.INIT_PARAMETER, new ArrayList());
                put(SemWifiDisplayParameter.GET_PARAMETER, new ArrayList());
                put(SemWifiDisplayParameter.SET_PARAMETER, new ArrayList());
            }
        };
    }

    private SemWifiDisplayConfig(String str) {
        this.mConnectionType = 1;
        this.mFlags = 0;
        this.mMode = 0;
        this.mPrameterGroups = new HashMap<String, List<SemWifiDisplayParameter>>() { // from class: android.hardware.display.SemWifiDisplayConfig.1
            {
                put(SemWifiDisplayParameter.INIT_PARAMETER, new ArrayList());
                put(SemWifiDisplayParameter.GET_PARAMETER, new ArrayList());
                put(SemWifiDisplayParameter.SET_PARAMETER, new ArrayList());
            }
        };
        this.mP2pMacAddress = str;
        this.mConnectionType = 1;
        this.mFlags = 0;
        this.mMode = 0;
        addParameter(SemWifiDisplayParameter.INIT_PARAMETER, new SemWifiDisplayParameter("connection-type", this.mConnectionType));
    }

    private SemWifiDisplayConfig(int i, String str, String str2, String str3, String str4) {
        this.mConnectionType = 1;
        this.mFlags = 0;
        this.mMode = 0;
        this.mPrameterGroups = new HashMap<String, List<SemWifiDisplayParameter>>() { // from class: android.hardware.display.SemWifiDisplayConfig.1
            {
                put(SemWifiDisplayParameter.INIT_PARAMETER, new ArrayList());
                put(SemWifiDisplayParameter.GET_PARAMETER, new ArrayList());
                put(SemWifiDisplayParameter.SET_PARAMETER, new ArrayList());
            }
        };
        this.mIpAddress = str;
        this.mPort = str2;
        this.mDeviceName = str3;
        this.mP2pMacAddress = str4;
        this.mConnectionType = i;
        this.mFlags = 0;
        this.mMode = 0;
        addParameter(SemWifiDisplayParameter.INIT_PARAMETER, new SemWifiDisplayParameter("connection-type", this.mConnectionType));
    }

    protected SemWifiDisplayConfig(Parcel parcel) {
        this.mConnectionType = 1;
        this.mFlags = 0;
        this.mMode = 0;
        this.mPrameterGroups = new HashMap<String, List<SemWifiDisplayParameter>>() { // from class: android.hardware.display.SemWifiDisplayConfig.1
            {
                put(SemWifiDisplayParameter.INIT_PARAMETER, new ArrayList());
                put(SemWifiDisplayParameter.GET_PARAMETER, new ArrayList());
                put(SemWifiDisplayParameter.SET_PARAMETER, new ArrayList());
            }
        };
        this.mP2pMacAddress = parcel.readString();
        this.mIpAddress = parcel.readString();
        this.mPort = parcel.readString();
        this.mDeviceName = parcel.readString();
        this.mConnectionType = parcel.readInt();
        this.mFlags = parcel.readInt();
        this.mMode = parcel.readInt();
        ArrayList arrayList = new ArrayList();
        parcel.readTypedList(arrayList, SemWifiDisplayParameter.CREATOR);
        ArrayList arrayList2 = new ArrayList();
        parcel.readTypedList(arrayList2, SemWifiDisplayParameter.CREATOR);
        ArrayList arrayList3 = new ArrayList();
        parcel.readTypedList(arrayList3, SemWifiDisplayParameter.CREATOR);
        this.mPrameterGroups.get(SemWifiDisplayParameter.INIT_PARAMETER).addAll(arrayList);
        this.mPrameterGroups.get(SemWifiDisplayParameter.GET_PARAMETER).addAll(arrayList2);
        this.mPrameterGroups.get(SemWifiDisplayParameter.SET_PARAMETER).addAll(arrayList3);
    }

    public void addFlags(int i) {
        this.mFlags = i | this.mFlags;
    }

    public void setMode(int i) {
        this.mMode = i;
        addParameter(SemWifiDisplayParameter.INIT_PARAMETER, new SemWifiDisplayParameter("mode", this.mMode));
    }

    public void addParameter(String str, SemWifiDisplayParameter semWifiDisplayParameter) {
        if (str == null || semWifiDisplayParameter == null) {
            return;
        }
        List<SemWifiDisplayParameter> list = this.mPrameterGroups.get(str);
        if (list.contains(semWifiDisplayParameter)) {
            return;
        }
        list.add(semWifiDisplayParameter);
    }

    public void addParameters(String str, List<SemWifiDisplayParameter> list) {
        if (str == null || list == null) {
            return;
        }
        List<SemWifiDisplayParameter> list2 = this.mPrameterGroups.get(str);
        for (SemWifiDisplayParameter semWifiDisplayParameter : list) {
            if (!list2.contains(semWifiDisplayParameter)) {
                list2.add(semWifiDisplayParameter);
            }
        }
    }

    public void removeParameter(String str, String str2) {
        if (str == null || str2 == null) {
            return;
        }
        List<SemWifiDisplayParameter> list = this.mPrameterGroups.get(str);
        for (SemWifiDisplayParameter semWifiDisplayParameter : list) {
            if (semWifiDisplayParameter.getKey().equals(str2)) {
                list.remove(semWifiDisplayParameter);
                return;
            }
        }
    }

    public List<SemWifiDisplayParameter> getParameters(String str) {
        return this.mPrameterGroups.get(str);
    }

    public SemWifiDisplayParameter getParameter(String str, String str2) {
        if (str == null || str2 == null) {
            return null;
        }
        for (SemWifiDisplayParameter semWifiDisplayParameter : this.mPrameterGroups.get(str)) {
            if (semWifiDisplayParameter.getKey().equals(str2)) {
                return semWifiDisplayParameter;
            }
        }
        return null;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public int getMode() {
        return this.mMode;
    }

    public String getP2pMacAddress() {
        return this.mP2pMacAddress;
    }

    public String getIpAddress() {
        return this.mIpAddress;
    }

    public String getDeviceName() {
        return this.mDeviceName;
    }

    public String getPort() {
        return this.mPort;
    }

    public int getConnectionType() {
        return this.mConnectionType;
    }

    public boolean isInitiateMirroringMode() {
        return (this.mFlags & 32) != 0;
    }

    public boolean isPinRequest() {
        return (this.mFlags & 8192) != 0;
    }

    public boolean isSkipPinNumberConfirm() {
        return (this.mFlags & 16384) != 0;
    }

    public JSONArray getJasonArrayParameters(String str) {
        JSONArray jSONArray = new JSONArray();
        for (SemWifiDisplayParameter semWifiDisplayParameter : this.mPrameterGroups.get(str)) {
            if (SemWifiDisplayParameter.INIT_PARAMETER.equals(str)) {
                try {
                    jSONArray.put(new JSONObject().put(semWifiDisplayParameter.getKey(), semWifiDisplayParameter.getValue()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                jSONArray.put(semWifiDisplayParameter.toString());
            }
        }
        return jSONArray;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mP2pMacAddress);
        parcel.writeString(this.mIpAddress);
        parcel.writeString(this.mPort);
        parcel.writeString(this.mDeviceName);
        parcel.writeInt(this.mConnectionType);
        parcel.writeInt(this.mFlags);
        parcel.writeInt(this.mMode);
        parcel.writeTypedList(this.mPrameterGroups.get(SemWifiDisplayParameter.INIT_PARAMETER));
        parcel.writeTypedList(this.mPrameterGroups.get(SemWifiDisplayParameter.GET_PARAMETER));
        parcel.writeTypedList(this.mPrameterGroups.get(SemWifiDisplayParameter.SET_PARAMETER));
    }

    public String toString() {
        String str = "SemWifiDisplayConfig:: p2pMacAddr = " + this.mP2pMacAddress;
        if (this.mConnectionType != 1) {
            str = str + ", ipAddr = " + this.mIpAddress + ", port = " + this.mPort + ", name = " + this.mDeviceName;
        }
        return str + ", connectionType = " + this.mConnectionType + ", flags = " + this.mFlags + ", mode = " + this.mMode + ", initParams = " + this.mPrameterGroups.get(SemWifiDisplayParameter.INIT_PARAMETER).toString() + ", getParams = " + this.mPrameterGroups.get(SemWifiDisplayParameter.GET_PARAMETER).toString() + ", setParams = " + this.mPrameterGroups.get(SemWifiDisplayParameter.SET_PARAMETER).toString();
    }

    public static final class Builder {
        private String mDeviceName;
        private String mIpAddress;
        private String mP2pMacAddress;
        private String mPort;
        private int mConnectionType = 1;
        private int mFlags = 0;
        private int mMode = 0;
        private HashMap<String, List<SemWifiDisplayParameter>> mPrameterGroups = new HashMap<String, List<SemWifiDisplayParameter>>() { // from class: android.hardware.display.SemWifiDisplayConfig.Builder.1
            {
                put(SemWifiDisplayParameter.INIT_PARAMETER, new ArrayList());
                put(SemWifiDisplayParameter.GET_PARAMETER, new ArrayList());
                put(SemWifiDisplayParameter.SET_PARAMETER, new ArrayList());
            }
        };

        public SemWifiDisplayConfig build() {
            SemWifiDisplayConfig semWifiDisplayConfig;
            if (this.mConnectionType == 1) {
                semWifiDisplayConfig = new SemWifiDisplayConfig(this.mP2pMacAddress);
            } else {
                semWifiDisplayConfig = new SemWifiDisplayConfig(this.mConnectionType, this.mIpAddress, this.mPort, this.mDeviceName, this.mP2pMacAddress);
            }
            semWifiDisplayConfig.addParameters(SemWifiDisplayParameter.INIT_PARAMETER, this.mPrameterGroups.get(SemWifiDisplayParameter.INIT_PARAMETER));
            semWifiDisplayConfig.addParameters(SemWifiDisplayParameter.GET_PARAMETER, this.mPrameterGroups.get(SemWifiDisplayParameter.GET_PARAMETER));
            semWifiDisplayConfig.addParameters(SemWifiDisplayParameter.SET_PARAMETER, this.mPrameterGroups.get(SemWifiDisplayParameter.SET_PARAMETER));
            semWifiDisplayConfig.setMode(this.mMode);
            semWifiDisplayConfig.addFlags(this.mFlags);
            return semWifiDisplayConfig;
        }

        public Builder setP2pConnection(String str) {
            this.mConnectionType = 1;
            this.mP2pMacAddress = str;
            return this;
        }

        public Builder setApConnection(String str, String str2, String str3, String str4) {
            this.mConnectionType = 2;
            this.mIpAddress = str;
            this.mPort = str2;
            this.mDeviceName = str3;
            this.mP2pMacAddress = str4;
            return this;
        }

        public Builder setUsbConnection(String str, String str2, String str3, String str4) {
            this.mConnectionType = 3;
            this.mIpAddress = str;
            this.mPort = str2;
            this.mDeviceName = str3;
            this.mP2pMacAddress = str4;
            return this;
        }

        public Builder addFlags(int i) {
            this.mFlags = i | this.mFlags;
            return this;
        }

        public Builder setMode(int i) {
            this.mMode = i;
            return this;
        }

        public Builder addParameter(String str, SemWifiDisplayParameter semWifiDisplayParameter) {
            if (str != null && semWifiDisplayParameter != null) {
                this.mPrameterGroups.get(str).add(semWifiDisplayParameter);
            }
            return this;
        }

        public Builder addParameters(String str, List<SemWifiDisplayParameter> list) {
            if (str != null && list != null) {
                this.mPrameterGroups.get(str).addAll(list);
            }
            return this;
        }
    }
}
