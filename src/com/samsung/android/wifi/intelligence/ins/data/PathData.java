package com.samsung.android.wifi.intelligence.ins.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes6.dex */
public final class PathData extends BaseData implements Parcelable {
    public static final Parcelable.Creator<PathData> CREATOR = new Parcelable.Creator<PathData>() { // from class: com.samsung.android.wifi.intelligence.ins.data.PathData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PathData createFromParcel(Parcel parcel) {
            return new PathData(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PathData[] newArray(int i) {
            return new PathData[i];
        }
    };
    public static final int INDEX_AIR = 4;
    public static final int INDEX_CM_LABEL = 6;
    public static final int INDEX_FLUSH_TIMESTAMP = 9;
    public static final int INDEX_LABEL = 7;
    public static final int INDEX_MAGNETIC_X = 1;
    public static final int INDEX_MAGNETIC_Y = 2;
    public static final int INDEX_MAGNETIC_Z = 3;
    public static final int INDEX_NO_MOVE = 8;
    public static final int INDEX_RSSI = 5;
    public static final int INDEX_TIMESTAMP = 0;
    private static final String TAG = "PathData";
    private final float air;
    private float mMagneticDelta;
    private final float mMagneticMagnitude;
    private float mSequenceMagneticDeltaSum;
    private final float magneticX;
    private final float magneticY;
    private final float magneticZ;
    private int noMove;
    private final float rssi;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public PathData(String str, float f, int i, int i2, float f2, float f3, float f4, float f5, int i3, String str2) {
        this.timestamp = str;
        this.air = f;
        this.cmLabel = i2;
        this.label = i;
        this.magneticX = f2;
        this.magneticY = f3;
        this.magneticZ = f4;
        this.rssi = f5;
        this.noMove = i3;
        this.flushTimestamp = str2;
        this.mMagneticMagnitude = (float) Math.sqrt((f2 * f2) + (f3 * f3) + (f4 * f4));
    }

    public PathData(String str, float f, int i, int i2, float f2, float f3, float f4, float f5) {
        this(str, f, i, i2, f2, f3, f4, f5, 0, "0");
    }

    private PathData(Parcel parcel) {
        this.magneticX = parcel.readFloat();
        this.magneticY = parcel.readFloat();
        this.magneticZ = parcel.readFloat();
        this.air = parcel.readFloat();
        this.rssi = parcel.readFloat();
        this.noMove = parcel.readInt();
        this.mMagneticMagnitude = parcel.readFloat();
        this.mMagneticDelta = parcel.readFloat();
        this.mSequenceMagneticDeltaSum = parcel.readFloat();
    }

    public float getAir() {
        return this.air;
    }

    public float getMagneticX() {
        return this.magneticX;
    }

    public float getMagneticY() {
        return this.magneticY;
    }

    public float getMagneticZ() {
        return this.magneticZ;
    }

    public float getRssi() {
        return this.rssi;
    }

    public float getMagneticDelta() {
        return this.mMagneticDelta;
    }

    public float getMagneticMagnitude() {
        return this.mMagneticMagnitude;
    }

    public float getSequenceMagneticDeltaSum() {
        return this.mSequenceMagneticDeltaSum;
    }

    public int getNoMove() {
        return this.noMove;
    }

    public void setMagneticDelta(float f) {
        this.mMagneticDelta = f;
    }

    public void setSequenceDeltaSum(float f) {
        this.mSequenceMagneticDeltaSum = f;
    }

    public void setNoMove(int i) {
        this.noMove = i;
    }

    @Override // com.samsung.android.wifi.intelligence.ins.data.BaseData
    public String getTimestamp() {
        return this.timestamp;
    }

    public static String getCsvHeader() {
        return "timestamp,magneticX,magneticY,magneticZ,air,rssi,cmLabel,label,noMove,flushTimestamp\n";
    }

    @Override // com.samsung.android.wifi.intelligence.ins.data.BaseData
    public String toCsvString() {
        return this.timestamp + "," + this.magneticX + "," + this.magneticY + "," + this.magneticZ + "," + this.air + "," + this.rssi + "," + this.cmLabel + "," + this.label + "," + this.noMove + "," + this.flushTimestamp + ShaderAssembler.NEWLINE;
    }

    public static PathData fromCsvString(String str) {
        List listAsList = Arrays.asList(str.split(","));
        if (listAsList.size() != 10) {
            Log.w(TAG, "Invalid CSV string: " + str);
            return null;
        }
        try {
            return new PathData((String) listAsList.get(0), Float.parseFloat((String) listAsList.get(4)), Integer.parseInt((String) listAsList.get(7)), Integer.parseInt((String) listAsList.get(6)), Float.parseFloat((String) listAsList.get(1)), Float.parseFloat((String) listAsList.get(2)), Float.parseFloat((String) listAsList.get(3)), Float.parseFloat((String) listAsList.get(5)), Integer.parseInt((String) listAsList.get(8)), (String) listAsList.get(9));
        } catch (Exception e) {
            Log.w(TAG, "Invalid CSV string: " + str);
            e.printStackTrace();
            return null;
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(this.magneticX);
        parcel.writeFloat(this.magneticY);
        parcel.writeFloat(this.magneticZ);
        parcel.writeFloat(this.air);
        parcel.writeFloat(this.rssi);
        parcel.writeInt(this.noMove);
        parcel.writeFloat(this.mMagneticMagnitude);
        parcel.writeFloat(this.mMagneticDelta);
        parcel.writeFloat(this.mSequenceMagneticDeltaSum);
    }
}
