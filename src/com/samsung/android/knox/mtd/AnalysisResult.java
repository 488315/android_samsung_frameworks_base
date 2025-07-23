package com.samsung.android.knox.mtd;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class AnalysisResult implements Parcelable {
    public static final Parcelable.Creator<AnalysisResult> CREATOR = new Parcelable.Creator<AnalysisResult>() { // from class: com.samsung.android.knox.mtd.AnalysisResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AnalysisResult createFromParcel(Parcel parcel) {
            return new AnalysisResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AnalysisResult[] newArray(int i) {
            return new AnalysisResult[i];
        }
    };
    double confidence;
    String givenInput;
    long inferenceTime;
    ResultCode resultCode;
    String shortUrlResolved;
    KnoxMtdErrorCode statusCode;
    String typoSquattingPredicted;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getShortUrlResolved() {
        return this.shortUrlResolved;
    }

    public void setShortUrlResolved(String str) {
        this.shortUrlResolved = str;
    }

    public String getTypoSquattingPredicted() {
        return this.typoSquattingPredicted;
    }

    public void setTypoSquattingPredicted(String str) {
        this.typoSquattingPredicted = str;
    }

    public void setGivenInput(String str) {
        this.givenInput = str;
    }

    public String getGivenInput() {
        return this.givenInput;
    }

    public void setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return this.resultCode;
    }

    public void setStatusCode(int i) {
        this.statusCode = KnoxMtdErrorCode.getCodeFromValue(i);
    }

    public KnoxMtdErrorCode getStatusCode() {
        return this.statusCode;
    }

    public void setConfidence(double d) {
        this.confidence = d;
    }

    public double getConfidence() {
        return this.confidence;
    }

    public void setInferenceTime(long j) {
        this.inferenceTime = j;
    }

    public long getInferenceTime() {
        return this.inferenceTime;
    }

    public AnalysisResult() {
        this.statusCode = KnoxMtdErrorCode.INTERNAL_ERROR;
        this.resultCode = ResultCode.UNANALYZED;
        this.confidence = -1.0d;
        this.inferenceTime = 0L;
        this.typoSquattingPredicted = "";
        this.shortUrlResolved = "";
    }

    protected AnalysisResult(Parcel parcel) {
        this.givenInput = parcel.readString();
        this.statusCode = KnoxMtdErrorCode.getCodeFromValue(parcel.readInt());
        this.resultCode = ResultCode.getCodeFromValue(parcel.readInt());
        this.confidence = parcel.readDouble();
        this.inferenceTime = parcel.readLong();
        this.typoSquattingPredicted = parcel.readString();
        this.shortUrlResolved = parcel.readString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.givenInput);
        parcel.writeInt(this.statusCode.getValue());
        parcel.writeInt(this.resultCode.getValue());
        parcel.writeDouble(this.confidence);
        parcel.writeLong(this.inferenceTime);
        parcel.writeString(this.typoSquattingPredicted);
        parcel.writeString(this.shortUrlResolved);
    }
}
