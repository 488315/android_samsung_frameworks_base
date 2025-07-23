package com.samsung.android.wifi.intelligence.ins.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes6.dex */
public final class NsmData extends BaseData implements Parcelable {
    public static final Parcelable.Creator<NsmData> CREATOR = new Parcelable.Creator<NsmData>() { // from class: com.samsung.android.wifi.intelligence.ins.data.NsmData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NsmData createFromParcel(Parcel parcel) {
            return new NsmData(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NsmData[] newArray(int i) {
            return new NsmData[i];
        }
    };
    private static final int INDEX_BSSID = 5;
    private static final int INDEX_CM_LABEL = 6;
    private static final int INDEX_FLUSH_TIMESTAMP = 8;
    private static final int INDEX_LABEL = 7;
    private static final int INDEX_LATENCY = 1;
    private static final int INDEX_RSSI = 2;
    private static final int INDEX_RX_SPEED = 3;
    private static final int INDEX_TIMESTAMP = 0;
    private static final int INDEX_TX_SPEED = 4;
    private static final String TAG = "NsmData";
    private final String BSSID;
    private float latency;
    private final OutageData outageData;
    private float rssi;
    private float rxLinkSpeedMbps;
    private float txSpeed;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public NsmData(String str, int i, int i2, float f, float f2, float f3, float f4, String str2, String str3, OutageData outageData) {
        this.timestamp = str;
        this.cmLabel = i2;
        this.label = i;
        this.latency = f;
        this.rssi = f2;
        this.rxLinkSpeedMbps = f3;
        this.txSpeed = f4;
        this.BSSID = str2;
        this.flushTimestamp = str3;
        this.outageData = outageData;
    }

    public NsmData(String str, int i, int i2, float f, float f2, float f3, float f4, String str2, OutageData outageData) {
        this(str, i, i2, f, f2, f3, f4, str2, "0", outageData);
    }

    public NsmData(String str, int i, int i2, float f, float f2, float f3, float f4, String str2) {
        this(str, i, i2, f, f2, f3, f4, str2, "0", null);
    }

    private NsmData(Parcel parcel) {
        this.txSpeed = parcel.readFloat();
        this.rssi = parcel.readFloat();
        this.latency = parcel.readFloat();
        this.rxLinkSpeedMbps = parcel.readFloat();
        this.BSSID = parcel.readString();
        this.outageData = (OutageData) parcel.readParcelable(OutageData.class.getClassLoader());
    }

    public float getLatency() {
        return this.latency;
    }

    public float getRssi() {
        return this.rssi;
    }

    public float getRxLinkSpeedMbps() {
        return this.rxLinkSpeedMbps;
    }

    public float getTxSpeed() {
        return this.txSpeed;
    }

    public String getBssid() {
        return this.BSSID;
    }

    public OutageData getOutageData() {
        return this.outageData;
    }

    public void setTxSpeed(float f) {
        this.txSpeed = f;
    }

    public void setRxLinkSpeedMbps(float f) {
        this.rxLinkSpeedMbps = f;
    }

    public void setLatency(float f) {
        this.latency = f;
    }

    public void setRssi(float f) {
        this.rssi = f;
    }

    public static String getCsvHeader() {
        return "timestamp,latency,rssi,rxLinkSpeedMbps,txSpeed,BSSID,cmLabel,label,flushTimestamp," + OutageData.getCsvHeader();
    }

    @Override // com.samsung.android.wifi.intelligence.ins.data.BaseData
    public String toCsvString() {
        return this.timestamp + "," + this.latency + "," + this.rssi + "," + this.rxLinkSpeedMbps + "," + this.txSpeed + "," + this.BSSID + "," + this.cmLabel + "," + this.label + "," + this.flushTimestamp + "," + this.outageData.toCsvString();
    }

    public static NsmData fromCsvString(String str) {
        List asList = Arrays.asList(str.split(","));
        if (asList.size() != 9 && asList.size() != 25) {
            Log.w(TAG, "Invalid CSV string: " + str);
            return null;
        }
        try {
            return new NsmData((String) asList.get(0), Integer.parseInt((String) asList.get(7)), Integer.parseInt((String) asList.get(6)), Float.parseFloat((String) asList.get(1)), Float.parseFloat((String) asList.get(2)), Float.parseFloat((String) asList.get(3)), Float.parseFloat((String) asList.get(4)), (String) asList.get(5), (String) asList.get(8), null);
        } catch (Exception e) {
            Log.w(TAG, "Invalid CSV string: " + str);
            e.printStackTrace();
            return null;
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(this.txSpeed);
        parcel.writeFloat(this.rssi);
        parcel.writeFloat(this.latency);
        parcel.writeFloat(this.rxLinkSpeedMbps);
        parcel.writeString(this.BSSID);
        parcel.writeParcelable(this.outageData, i);
    }
}
