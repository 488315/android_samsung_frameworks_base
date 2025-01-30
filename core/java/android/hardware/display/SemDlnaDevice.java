package android.hardware.display;

import android.p009os.Parcel;
import android.p009os.Parcelable;
import android.util.Log;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class SemDlnaDevice implements Parcelable {
    public static final int STATE_CONNECTED = 1;
    public static final int STATE_CONNECTING = 3;
    public static final int STATE_ERROR = 2;
    public static final int STATE_NOT_CONNECTED = 0;
    public static final int SUPPORT_TYPE_IMAGE = 2;
    public static final int SUPPORT_TYPE_MUSIC = 4;
    public static final int SUPPORT_TYPE_VIDEO = 1;
    private static final String TAG = "SemDlnaDevice";
    public static final int TYPE_IMAGE = 1;
    public static final int TYPE_MUSIC = 2;
    public static final int TYPE_VIDEO = 0;
    private int mConnectionState;
    private int mDlnaSupportTypes;
    private int mDlnaType;
    private String mIpAddress;
    private boolean mIsSwitchingDevice;
    private String mMacAddressFromARP;
    private String mNICType;
    private String mName;
    private String mP2pMacAddress;
    private String mURI;
    private String mUid;
    public static final SemDlnaDevice[] EMPTY_ARRAY = new SemDlnaDevice[0];
    public static final Parcelable.Creator<SemDlnaDevice> CREATOR = new Parcelable.Creator<SemDlnaDevice>() { // from class: android.hardware.display.SemDlnaDevice.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemDlnaDevice createFromParcel(Parcel in) {
            String deviceName = in.readString();
            String deviceIpAddress = in.readString();
            String deviceP2pMacAddress = in.readString();
            String deviceMacAddressFromARP = in.readString();
            String deviceNICType = in.readString();
            String uid = in.readString();
            int dlnaType = in.readInt();
            boolean isSwitchingDevice = in.readInt() != 0;
            String uri = in.readString();
            int dlnaSupportTypes = in.readInt();
            int connectionState = in.readInt();
            SemDlnaDevice dlnaDevice = new SemDlnaDevice(deviceName, deviceIpAddress, deviceP2pMacAddress, deviceMacAddressFromARP, deviceNICType, uid, dlnaType, isSwitchingDevice, uri);
            dlnaDevice.setDlnaSupportTypes(dlnaSupportTypes);
            dlnaDevice.setConnectionState(connectionState);
            return dlnaDevice;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemDlnaDevice[] newArray(int size) {
            return size == 0 ? SemDlnaDevice.EMPTY_ARRAY : new SemDlnaDevice[size];
        }
    };

    public SemDlnaDevice() {
        Log.m94d(TAG, "SemDlnaDevice " + toString());
        this.mConnectionState = 0;
    }

    public SemDlnaDevice(String uid, String deviceName, int dlnaType, boolean isSwitchingDevice) {
        this.mUid = uid;
        this.mName = deviceName;
        this.mDlnaType = dlnaType;
        this.mIsSwitchingDevice = isSwitchingDevice;
        this.mConnectionState = 0;
        Log.m94d(TAG, "SemDlnaDevice " + toString());
    }

    public SemDlnaDevice(String name, String ipAddress, String p2pMacAddress, String macAddressFromARP, String deviceNICType, String uid, int dlnaType, boolean isSwitchingDevice, String uri) {
        this.mName = name;
        this.mIpAddress = ipAddress;
        String str = p2pMacAddress != null ? p2pMacAddress : "";
        this.mP2pMacAddress = str;
        if (str.equals("") && ((macAddressFromARP == null || macAddressFromARP.equals("")) && !"".equals(ipAddress))) {
            this.mMacAddressFromARP = getMacAddrFromArpTable(ipAddress);
        } else {
            this.mMacAddressFromARP = macAddressFromARP != null ? macAddressFromARP : "";
        }
        this.mNICType = deviceNICType != null ? deviceNICType : "";
        this.mUid = uid;
        this.mDlnaType = dlnaType;
        this.mIsSwitchingDevice = isSwitchingDevice;
        this.mURI = uri;
        Log.m94d(TAG, "SemDlnaDevice " + toString());
    }

    public String getUid() {
        return this.mUid;
    }

    public String getDeviceName() {
        return this.mName;
    }

    public int getConnectionState() {
        return this.mConnectionState;
    }

    public String getIpAddress() {
        return this.mIpAddress;
    }

    public String getP2pMacAddress() {
        return this.mP2pMacAddress;
    }

    public String getMacAddressFromArp() {
        return this.mMacAddressFromARP;
    }

    public String getNetType() {
        return this.mNICType;
    }

    public int getDlnaType() {
        return this.mDlnaType;
    }

    public String getUri() {
        return this.mURI;
    }

    public boolean isSwitchingDevice() {
        return this.mIsSwitchingDevice;
    }

    public boolean isDlnaSupportType(int type) {
        switch (type) {
            case 0:
                if ((this.mDlnaSupportTypes & 1) != 0) {
                }
                break;
            case 1:
                if ((this.mDlnaSupportTypes & 2) != 0) {
                }
                break;
            case 2:
                if ((this.mDlnaSupportTypes & 4) != 0) {
                }
                break;
        }
        return true;
    }

    public int getDlnaSupportTypes() {
        return this.mDlnaSupportTypes;
    }

    public boolean isConnected() {
        return this.mConnectionState == 1;
    }

    public void setConnectionState(int connectionState) {
        this.mConnectionState = connectionState;
    }

    public void setPlayerType(int type) {
        this.mDlnaType = type;
    }

    public void setDlnaSupportTypes(int supportTypes) {
        this.mDlnaSupportTypes = supportTypes;
    }

    private static String getMacAddrFromArpTable(String ipAddr) {
        if (ipAddr == null) {
            return "";
        }
        BufferedReader br = null;
        String ipAddr2 = ipAddr.replace("/", "");
        try {
            try {
                BufferedReader br2 = new BufferedReader(new InputStreamReader(new FileInputStream("/proc/net/arp"), StandardCharsets.UTF_8));
                while (true) {
                    String line = br2.readLine();
                    if (line == null) {
                        br2.close();
                        try {
                            br2.close();
                        } catch (Exception e) {
                            Log.m96e(TAG, "getMacAddrFromArpTable br.close() IOE" + e.toString());
                        }
                        return "";
                    }
                    String[] splitted = line.split(" +");
                    if (splitted != null && splitted.length >= 4 && ipAddr2.equals(splitted[0])) {
                        String mac = splitted[3];
                        if (mac.matches("..:..:..:..:..:..")) {
                            String mac2 = mac.trim();
                            br2.close();
                            try {
                                br2.close();
                            } catch (Exception e2) {
                                Log.m96e(TAG, "getMacAddrFromArpTable br.close() IOE" + e2.toString());
                            }
                            return mac2;
                        }
                    }
                }
            } catch (Exception e3) {
                Log.m96e(TAG, "getMacAddrFromArpTable Exception" + e3.toString());
                if (0 != 0) {
                    try {
                        br.close();
                    } catch (Exception e4) {
                        Log.m96e(TAG, "getMacAddrFromArpTable br.close() IOE" + e4.toString());
                    }
                }
                return "";
            }
        } catch (Throwable th) {
            if (0 != 0) {
                try {
                    br.close();
                } catch (Exception e5) {
                    Log.m96e(TAG, "getMacAddrFromArpTable br.close() IOE" + e5.toString());
                }
            }
            throw th;
        }
    }

    public boolean equals(Object o) {
        return (o instanceof SemDlnaDevice) && equals((SemDlnaDevice) o);
    }

    public boolean equals(SemDlnaDevice other) {
        return other != null && this.mUid.equals(other.mUid) && this.mName.equals(other.mName) && this.mP2pMacAddress.equals(other.mP2pMacAddress) && Objects.equals(Integer.valueOf(this.mDlnaType), Integer.valueOf(other.mDlnaType));
    }

    public int hashCode() {
        return this.mUid.hashCode();
    }

    @Override // android.p009os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mName);
        parcel.writeString(this.mIpAddress);
        parcel.writeString(this.mP2pMacAddress);
        parcel.writeString(this.mMacAddressFromARP);
        parcel.writeString(this.mNICType);
        parcel.writeString(this.mUid);
        parcel.writeInt(this.mDlnaType);
        parcel.writeInt(this.mIsSwitchingDevice ? 1 : 0);
        parcel.writeString(this.mURI);
        parcel.writeInt(this.mDlnaSupportTypes);
        parcel.writeInt(this.mConnectionState);
    }

    @Override // android.p009os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String toString() {
        String result = "name: " + this.mName;
        return result + ", ip: " + this.mIpAddress + ", mac: " + this.mP2pMacAddress + ", macFromArp: " + this.mMacAddressFromARP + ", netType: " + this.mNICType + ", uid: " + this.mUid + ", dlnaType : " + this.mDlnaType + ", isSwitchingDevice : " + this.mIsSwitchingDevice + ", uri : " + this.mURI + ", dlnaType : " + this.mDlnaType + ", dlnaSupportTypes : " + this.mDlnaSupportTypes + ", connectionState : " + this.mConnectionState;
    }
}
