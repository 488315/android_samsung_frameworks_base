package android.hardware.display;

import android.os.Parcel;
import android.os.Parcelable;
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
        public SemDlnaDevice createFromParcel(Parcel parcel) {
            String readString = parcel.readString();
            String readString2 = parcel.readString();
            String readString3 = parcel.readString();
            String readString4 = parcel.readString();
            String readString5 = parcel.readString();
            String readString6 = parcel.readString();
            int readInt = parcel.readInt();
            boolean z = parcel.readInt() != 0;
            String readString7 = parcel.readString();
            int readInt2 = parcel.readInt();
            int readInt3 = parcel.readInt();
            SemDlnaDevice semDlnaDevice = new SemDlnaDevice(readString, readString2, readString3, readString4, readString5, readString6, readInt, z, readString7);
            semDlnaDevice.setDlnaSupportTypes(readInt2);
            semDlnaDevice.setConnectionState(readInt3);
            return semDlnaDevice;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemDlnaDevice[] newArray(int i) {
            return i == 0 ? SemDlnaDevice.EMPTY_ARRAY : new SemDlnaDevice[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SemDlnaDevice() {
        Log.d(TAG, "SemDlnaDevice " + toString());
        this.mConnectionState = 0;
    }

    public SemDlnaDevice(String str, String str2, int i, boolean z) {
        this.mUid = str;
        this.mName = str2;
        this.mDlnaType = i;
        this.mIsSwitchingDevice = z;
        this.mConnectionState = 0;
        Log.d(TAG, "SemDlnaDevice " + toString());
    }

    public SemDlnaDevice(String str, String str2, String str3, String str4, String str5, String str6, int i, boolean z, String str7) {
        this.mName = str;
        this.mIpAddress = str2;
        str3 = str3 == null ? "" : str3;
        this.mP2pMacAddress = str3;
        if (str3.equals("") && ((str4 == null || str4.equals("")) && !"".equals(str2))) {
            this.mMacAddressFromARP = getMacAddrFromArpTable(str2);
        } else {
            this.mMacAddressFromARP = str4 == null ? "" : str4;
        }
        this.mNICType = str5 == null ? "" : str5;
        this.mUid = str6;
        this.mDlnaType = i;
        this.mIsSwitchingDevice = z;
        this.mURI = str7;
        Log.d(TAG, "SemDlnaDevice " + toString());
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

    public boolean isDlnaSupportType(int i) {
        return i != 0 ? i != 1 ? i == 2 && (this.mDlnaSupportTypes & 4) != 0 : (this.mDlnaSupportTypes & 2) != 0 : (this.mDlnaSupportTypes & 1) != 0;
    }

    public int getDlnaSupportTypes() {
        return this.mDlnaSupportTypes;
    }

    public boolean isConnected() {
        return this.mConnectionState == 1;
    }

    public void setConnectionState(int i) {
        this.mConnectionState = i;
    }

    public void setPlayerType(int i) {
        this.mDlnaType = i;
    }

    public void setDlnaSupportTypes(int i) {
        this.mDlnaSupportTypes = i;
    }

    private static String getMacAddrFromArpTable(String str) {
        if (str == null) {
            return "";
        }
        String replace = str.replace("/", "");
        BufferedReader bufferedReader = null;
        try {
            try {
                BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(new FileInputStream("/proc/net/arp"), StandardCharsets.UTF_8));
                while (true) {
                    try {
                        String readLine = bufferedReader2.readLine();
                        if (readLine == null) {
                            bufferedReader2.close();
                            try {
                                bufferedReader2.close();
                                return "";
                            } catch (Exception e) {
                                Log.e(TAG, "getMacAddrFromArpTable br.close() IOE" + e.toString());
                                return "";
                            }
                        }
                        String[] split = readLine.split(" +");
                        if (split != null && split.length >= 4 && replace.equals(split[0])) {
                            String str2 = split[3];
                            if (str2.matches("..:..:..:..:..:..")) {
                                String trim = str2.trim();
                                bufferedReader2.close();
                                try {
                                    bufferedReader2.close();
                                    return trim;
                                } catch (Exception e2) {
                                    Log.e(TAG, "getMacAddrFromArpTable br.close() IOE" + e2.toString());
                                    return trim;
                                }
                            }
                        }
                    } catch (Exception e3) {
                        e = e3;
                        bufferedReader = bufferedReader2;
                        Log.e(TAG, "getMacAddrFromArpTable Exception" + e.toString());
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (Exception e4) {
                                Log.e(TAG, "getMacAddrFromArpTable br.close() IOE" + e4.toString());
                            }
                        }
                        return "";
                    } catch (Throwable th) {
                        th = th;
                        bufferedReader = bufferedReader2;
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (Exception e5) {
                                Log.e(TAG, "getMacAddrFromArpTable br.close() IOE" + e5.toString());
                            }
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e6) {
            e = e6;
        }
    }

    public boolean equals(Object obj) {
        return (obj instanceof SemDlnaDevice) && equals((SemDlnaDevice) obj);
    }

    public boolean equals(SemDlnaDevice semDlnaDevice) {
        return semDlnaDevice != null && this.mUid.equals(semDlnaDevice.mUid) && this.mName.equals(semDlnaDevice.mName) && this.mP2pMacAddress.equals(semDlnaDevice.mP2pMacAddress) && Objects.equals(Integer.valueOf(this.mDlnaType), Integer.valueOf(semDlnaDevice.mDlnaType));
    }

    public int hashCode() {
        return this.mUid.hashCode();
    }

    @Override // android.os.Parcelable
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

    public String toString() {
        return ("name: " + this.mName) + ", ip: " + this.mIpAddress + ", mac: " + this.mP2pMacAddress + ", macFromArp: " + this.mMacAddressFromARP + ", netType: " + this.mNICType + ", uid: " + this.mUid + ", dlnaType : " + this.mDlnaType + ", isSwitchingDevice : " + this.mIsSwitchingDevice + ", uri : " + this.mURI + ", dlnaType : " + this.mDlnaType + ", dlnaSupportTypes : " + this.mDlnaSupportTypes + ", connectionState : " + this.mConnectionState;
    }
}
