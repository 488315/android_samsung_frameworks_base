package android.hardware.display;

import android.p009os.Parcel;
import android.p009os.Parcelable;
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
        public SemWifiDisplayConfig createFromParcel(Parcel in) {
            return new SemWifiDisplayConfig(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemWifiDisplayConfig[] newArray(int size) {
            return new SemWifiDisplayConfig[size];
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

    private SemWifiDisplayConfig(String p2pMacAddress) {
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
        this.mP2pMacAddress = p2pMacAddress;
        this.mConnectionType = 1;
        this.mFlags = 0;
        this.mMode = 0;
        addParameter(SemWifiDisplayParameter.INIT_PARAMETER, new SemWifiDisplayParameter("connection-type", this.mConnectionType));
    }

    private SemWifiDisplayConfig(int connetionType, String ipAddress, String port, String deviceName, String p2pMacAddress) {
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
        this.mIpAddress = ipAddress;
        this.mPort = port;
        this.mDeviceName = deviceName;
        this.mP2pMacAddress = p2pMacAddress;
        this.mConnectionType = connetionType;
        this.mFlags = 0;
        this.mMode = 0;
        addParameter(SemWifiDisplayParameter.INIT_PARAMETER, new SemWifiDisplayParameter("connection-type", this.mConnectionType));
    }

    protected SemWifiDisplayConfig(Parcel in) {
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
        this.mP2pMacAddress = in.readString();
        this.mIpAddress = in.readString();
        this.mPort = in.readString();
        this.mDeviceName = in.readString();
        this.mConnectionType = in.readInt();
        this.mFlags = in.readInt();
        this.mMode = in.readInt();
        ArrayList<SemWifiDisplayParameter> initParameters = new ArrayList<>();
        in.readTypedList(initParameters, SemWifiDisplayParameter.CREATOR);
        ArrayList<SemWifiDisplayParameter> getParameters = new ArrayList<>();
        in.readTypedList(getParameters, SemWifiDisplayParameter.CREATOR);
        ArrayList<SemWifiDisplayParameter> setParameters = new ArrayList<>();
        in.readTypedList(setParameters, SemWifiDisplayParameter.CREATOR);
        this.mPrameterGroups.get(SemWifiDisplayParameter.INIT_PARAMETER).addAll(initParameters);
        this.mPrameterGroups.get(SemWifiDisplayParameter.GET_PARAMETER).addAll(getParameters);
        this.mPrameterGroups.get(SemWifiDisplayParameter.SET_PARAMETER).addAll(setParameters);
    }

    public void addFlags(int flags) {
        this.mFlags |= flags;
    }

    public void setMode(int mode) {
        this.mMode = mode;
        addParameter(SemWifiDisplayParameter.INIT_PARAMETER, new SemWifiDisplayParameter("mode", this.mMode));
    }

    public void addParameter(String groupKey, SemWifiDisplayParameter parameter) {
        if (groupKey != null && parameter != null) {
            List<SemWifiDisplayParameter> values = this.mPrameterGroups.get(groupKey);
            if (!values.contains(parameter)) {
                values.add(parameter);
            }
        }
    }

    public void addParameters(String groupKey, List<SemWifiDisplayParameter> parameters) {
        if (groupKey != null && parameters != null) {
            List<SemWifiDisplayParameter> values = this.mPrameterGroups.get(groupKey);
            for (SemWifiDisplayParameter parameter : parameters) {
                if (!values.contains(parameter)) {
                    values.add(parameter);
                }
            }
        }
    }

    public void removeParameter(String groupKey, String key) {
        if (groupKey != null && key != null) {
            List<SemWifiDisplayParameter> wifiDisplayParameterList = this.mPrameterGroups.get(groupKey);
            for (SemWifiDisplayParameter parameter : wifiDisplayParameterList) {
                if (parameter.getKey().equals(key)) {
                    wifiDisplayParameterList.remove(parameter);
                    return;
                }
            }
        }
    }

    public List<SemWifiDisplayParameter> getParameters(String groupKey) {
        return this.mPrameterGroups.get(groupKey);
    }

    public SemWifiDisplayParameter getParameter(String groupKey, String key) {
        if (groupKey != null && key != null) {
            List<SemWifiDisplayParameter> wifiDisplayParameterList = this.mPrameterGroups.get(groupKey);
            for (SemWifiDisplayParameter parameter : wifiDisplayParameterList) {
                if (parameter.getKey().equals(key)) {
                    return parameter;
                }
            }
            return null;
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

    public JSONArray getJasonArrayParameters(String groupKey) {
        JSONArray jArray = new JSONArray();
        for (SemWifiDisplayParameter param : this.mPrameterGroups.get(groupKey)) {
            if (SemWifiDisplayParameter.INIT_PARAMETER.equals(groupKey)) {
                try {
                    jArray.put(new JSONObject().put(param.getKey(), param.getValue()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                jArray.put(param.toString());
            }
        }
        return jArray;
    }

    @Override // android.p009os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p009os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mP2pMacAddress);
        dest.writeString(this.mIpAddress);
        dest.writeString(this.mPort);
        dest.writeString(this.mDeviceName);
        dest.writeInt(this.mConnectionType);
        dest.writeInt(this.mFlags);
        dest.writeInt(this.mMode);
        dest.writeTypedList(this.mPrameterGroups.get(SemWifiDisplayParameter.INIT_PARAMETER));
        dest.writeTypedList(this.mPrameterGroups.get(SemWifiDisplayParameter.GET_PARAMETER));
        dest.writeTypedList(this.mPrameterGroups.get(SemWifiDisplayParameter.SET_PARAMETER));
    }

    public String toString() {
        String result = "SemWifiDisplayConfig:: p2pMacAddr = " + this.mP2pMacAddress;
        if (this.mConnectionType != 1) {
            result = result + ", ipAddr = " + this.mIpAddress + ", port = " + this.mPort + ", name = " + this.mDeviceName;
        }
        return result + ", connectionType = " + this.mConnectionType + ", flags = " + this.mFlags + ", mode = " + this.mMode + ", initParams = " + this.mPrameterGroups.get(SemWifiDisplayParameter.INIT_PARAMETER).toString() + ", getParams = " + this.mPrameterGroups.get(SemWifiDisplayParameter.GET_PARAMETER).toString() + ", setParams = " + this.mPrameterGroups.get(SemWifiDisplayParameter.SET_PARAMETER).toString();
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
            SemWifiDisplayConfig wifiDisplayConfig;
            if (this.mConnectionType == 1) {
                wifiDisplayConfig = new SemWifiDisplayConfig(this.mP2pMacAddress);
            } else {
                wifiDisplayConfig = new SemWifiDisplayConfig(this.mConnectionType, this.mIpAddress, this.mPort, this.mDeviceName, this.mP2pMacAddress);
            }
            wifiDisplayConfig.addParameters(SemWifiDisplayParameter.INIT_PARAMETER, this.mPrameterGroups.get(SemWifiDisplayParameter.INIT_PARAMETER));
            wifiDisplayConfig.addParameters(SemWifiDisplayParameter.GET_PARAMETER, this.mPrameterGroups.get(SemWifiDisplayParameter.GET_PARAMETER));
            wifiDisplayConfig.addParameters(SemWifiDisplayParameter.SET_PARAMETER, this.mPrameterGroups.get(SemWifiDisplayParameter.SET_PARAMETER));
            wifiDisplayConfig.setMode(this.mMode);
            wifiDisplayConfig.addFlags(this.mFlags);
            return wifiDisplayConfig;
        }

        public Builder setP2pConnection(String p2pMacAddress) {
            this.mConnectionType = 1;
            this.mP2pMacAddress = p2pMacAddress;
            return this;
        }

        public Builder setApConnection(String ipAddress, String port, String deviceName, String p2pMacAddress) {
            this.mConnectionType = 2;
            this.mIpAddress = ipAddress;
            this.mPort = port;
            this.mDeviceName = deviceName;
            this.mP2pMacAddress = p2pMacAddress;
            return this;
        }

        public Builder setUsbConnection(String ipAddress, String port, String deviceName, String p2pMacAddress) {
            this.mConnectionType = 3;
            this.mIpAddress = ipAddress;
            this.mPort = port;
            this.mDeviceName = deviceName;
            this.mP2pMacAddress = p2pMacAddress;
            return this;
        }

        public Builder addFlags(int flags) {
            this.mFlags |= flags;
            return this;
        }

        public Builder setMode(int mode) {
            this.mMode = mode;
            return this;
        }

        public Builder addParameter(String groupKey, SemWifiDisplayParameter parameter) {
            if (groupKey != null && parameter != null) {
                List<SemWifiDisplayParameter> values = this.mPrameterGroups.get(groupKey);
                values.add(parameter);
            }
            return this;
        }

        public Builder addParameters(String groupKey, List<SemWifiDisplayParameter> parameters) {
            if (groupKey != null && parameters != null) {
                List<SemWifiDisplayParameter> values = this.mPrameterGroups.get(groupKey);
                values.addAll(parameters);
            }
            return this;
        }
    }
}
