package com.samsung.android.wifi.intelligence.ins.inf.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public final class ResultInfer implements Parcelable {
    public static final Parcelable.Creator<ResultInfer> CREATOR = new Parcelable.Creator<ResultInfer>() { // from class: com.samsung.android.wifi.intelligence.ins.inf.entity.ResultInfer.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ResultInfer createFromParcel(Parcel parcel) {
            return new ResultInfer(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ResultInfer[] newArray(int i) {
            return new ResultInfer[i];
        }
    };
    public static final String TAG = "ResultInfer";
    private final int label;
    private final float nsmProb;
    private final float nsmThreshold;
    private final float pathProb;
    private final float pathThreshold;
    private final boolean sufficientData;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ResultInfer(float f, float f2, float f3, float f4, boolean z, int i) {
        this.nsmProb = f;
        this.pathProb = f2;
        this.nsmThreshold = f3;
        this.pathThreshold = f4;
        this.sufficientData = z;
        this.label = i;
    }

    private ResultInfer(Parcel parcel) {
        this.nsmProb = parcel.readFloat();
        this.pathProb = parcel.readFloat();
        this.nsmThreshold = parcel.readFloat();
        this.pathThreshold = parcel.readFloat();
        this.sufficientData = parcel.readByte() != 0;
        this.label = parcel.readInt();
    }

    public int getLabel() {
        return this.label;
    }

    public float getNsmProb() {
        return this.nsmProb;
    }

    public float getPathProb() {
        return this.pathProb;
    }

    public float getNsmThreshold() {
        return this.nsmThreshold;
    }

    public float getPathThreshold() {
        return this.pathThreshold;
    }

    public boolean isSufficientData() {
        return this.sufficientData;
    }

    public String toJsonString() {
        return "{\"nsm_prob\": " + this.nsmProb + ", \"path_prob\": " + this.pathProb + ", \"nsm_threshold\": " + this.nsmThreshold + ", \"path_threshold\": " + this.pathThreshold + ", \"sufficient_data\": " + this.sufficientData + ", \"label\": " + this.label + "}";
    }

    public static ResultInfer fromJsonString(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            return new ResultInfer((float) jSONObject.getDouble("nsm_prob"), (float) jSONObject.getDouble("path_prob"), (float) jSONObject.getDouble("nsm_threshold"), (float) jSONObject.getDouble("path_threshold"), jSONObject.getBoolean("sufficient_data"), jSONObject.getInt("label"));
        } catch (JSONException e) {
            Log.w(TAG, "Cannot parse Json String:" + e);
            return null;
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(this.nsmProb);
        parcel.writeFloat(this.pathProb);
        parcel.writeFloat(this.nsmThreshold);
        parcel.writeFloat(this.pathThreshold);
        parcel.writeByte(this.sufficientData ? (byte) 1 : (byte) 0);
        parcel.writeInt(this.label);
    }
}
