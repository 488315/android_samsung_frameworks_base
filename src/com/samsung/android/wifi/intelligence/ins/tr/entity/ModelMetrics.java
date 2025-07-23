package com.samsung.android.wifi.intelligence.ins.tr.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public final class ModelMetrics implements Parcelable {
    public static final Parcelable.Creator<ModelMetrics> CREATOR = new Parcelable.Creator<ModelMetrics>() { // from class: com.samsung.android.wifi.intelligence.ins.tr.entity.ModelMetrics.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ModelMetrics createFromParcel(Parcel parcel) {
            return new ModelMetrics(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ModelMetrics[] newArray(int i) {
            return new ModelMetrics[i];
        }
    };
    public static final String TAG = "ModelMetrics";
    private float accuracy;
    private int epochs;
    private int ignoredDataPoints;
    private float loss;
    private float precision;
    private float recall;
    private int trainSize;
    private int valSize;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ModelMetrics() {
        this.trainSize = 0;
        this.valSize = 0;
        this.epochs = 0;
        this.loss = 0.0f;
        this.ignoredDataPoints = 0;
        this.accuracy = 0.0f;
        this.precision = 0.0f;
        this.recall = 0.0f;
    }

    public float getAccuracy() {
        return this.accuracy;
    }

    public void setAccuracy(float f) {
        this.accuracy = f;
    }

    public int getEpochs() {
        return this.epochs;
    }

    public void setEpochs(int i) {
        this.epochs = i;
    }

    public float getPrecision() {
        return this.precision;
    }

    public void setPrecision(float f) {
        this.precision = f;
    }

    public float getRecall() {
        return this.recall;
    }

    public void setRecall(float f) {
        this.recall = f;
    }

    public int getTrainSize() {
        return this.trainSize;
    }

    public void setTrainSize(int i) {
        this.trainSize = i;
    }

    public int getValSize() {
        return this.valSize;
    }

    public void setValSize(int i) {
        this.valSize = i;
    }

    public float getLoss() {
        return this.loss;
    }

    public void setLoss(float f) {
        this.loss = f;
    }

    public int getIgnoredDataPoints() {
        return this.ignoredDataPoints;
    }

    public void setIgnoredDataPoints(int i) {
        this.ignoredDataPoints = i;
    }

    public String toString() {
        return "ModelMetrics{accuracy=" + this.accuracy + ", trainSize=" + this.trainSize + ", valSize=" + this.valSize + ", epochs=" + this.epochs + ", loss=" + this.loss + ", ignoredDataPoints=" + this.ignoredDataPoints + ", precision=" + this.precision + ", recall=" + this.recall + '}';
    }

    public String toJsonString() {
        return "{\"accuracy\":" + this.accuracy + ", \"trainSize\":" + this.trainSize + ", \"valSize\":" + this.valSize + ", \"epochs\":" + this.epochs + ", \"loss\":" + this.loss + ", \"ignoredDataPoints\":" + this.ignoredDataPoints + ", \"precision\":" + this.precision + ", \"recall\":" + this.recall + "}";
    }

    public static ModelMetrics fromJsonString(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            ModelMetrics modelMetrics = new ModelMetrics();
            modelMetrics.setAccuracy((float) jSONObject.getDouble("accuracy"));
            modelMetrics.setTrainSize(jSONObject.getInt("trainSize"));
            modelMetrics.setValSize(jSONObject.getInt("valSize"));
            modelMetrics.setEpochs(jSONObject.getInt("epochs"));
            modelMetrics.setLoss((float) jSONObject.getDouble("loss"));
            modelMetrics.setIgnoredDataPoints(jSONObject.getInt("ignoredDataPoints"));
            modelMetrics.setPrecision((float) jSONObject.getDouble("precision"));
            modelMetrics.setRecall((float) jSONObject.getDouble("recall"));
            return modelMetrics;
        } catch (JSONException e) {
            Log.w(TAG, "Error parsing JSON string:" + e);
            return null;
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(this.accuracy);
        parcel.writeInt(this.epochs);
        parcel.writeInt(this.ignoredDataPoints);
        parcel.writeFloat(this.loss);
        parcel.writeFloat(this.precision);
        parcel.writeFloat(this.recall);
        parcel.writeInt(this.trainSize);
        parcel.writeInt(this.valSize);
    }

    private ModelMetrics(Parcel parcel) {
        this.accuracy = parcel.readFloat();
        this.epochs = parcel.readInt();
        this.ignoredDataPoints = parcel.readInt();
        this.loss = parcel.readFloat();
        this.precision = parcel.readFloat();
        this.recall = parcel.readFloat();
        this.trainSize = parcel.readInt();
        this.valSize = parcel.readInt();
    }
}
