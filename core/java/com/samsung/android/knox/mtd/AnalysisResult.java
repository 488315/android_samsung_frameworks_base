package com.samsung.android.knox.mtd;

import android.os.Parcel;
import android.os.Parcelable;

public class AnalysisResult implements Parcelable {
    public static final Parcelable.Creator<AnalysisResult> CREATOR =
            new Parcelable.Creator<
                    AnalysisResult>() { // from class: com.samsung.android.knox.mtd.AnalysisResult.1
                @Override // android.os.Parcelable.Creator
                public AnalysisResult createFromParcel(Parcel in) {
                    return new AnalysisResult(in);
                }

                @Override // android.os.Parcelable.Creator
                public AnalysisResult[] newArray(int size) {
                    return new AnalysisResult[size];
                }
            };
    double confidence;
    String givenInput;
    long inferenceTime;
    ResultCode resultCode;
    String shortUrlResolved;
    KnoxMtdErrorCode statusCode;
    String typoSquattingPredicted;

    public String getShortUrlResolved() {
        return this.shortUrlResolved;
    }

    public void setShortUrlResolved(String shortUrlResolved) {
        this.shortUrlResolved = shortUrlResolved;
    }

    public String getTypoSquattingPredicted() {
        return this.typoSquattingPredicted;
    }

    public void setTypoSquattingPredicted(String typoSquattingPredicted) {
        this.typoSquattingPredicted = typoSquattingPredicted;
    }

    public void setGivenInput(String input) {
        this.givenInput = input;
    }

    public String getGivenInput() {
        return this.givenInput;
    }

    public void setResultCode(ResultCode code) {
        this.resultCode = code;
    }

    public ResultCode getResultCode() {
        return this.resultCode;
    }

    public void setStatusCode(int code) {
        this.statusCode = KnoxMtdErrorCode.getCodeFromValue(code);
    }

    public KnoxMtdErrorCode getStatusCode() {
        return this.statusCode;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    public double getConfidence() {
        return this.confidence;
    }

    public void setInferenceTime(long time) {
        this.inferenceTime = time;
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

    protected AnalysisResult(Parcel in) {
        this.givenInput = in.readString();
        this.statusCode = KnoxMtdErrorCode.getCodeFromValue(in.readInt());
        this.resultCode = ResultCode.getCodeFromValue(in.readInt());
        this.confidence = in.readDouble();
        this.inferenceTime = in.readLong();
        this.typoSquattingPredicted = in.readString();
        this.shortUrlResolved = in.readString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.givenInput);
        dest.writeInt(this.statusCode.getValue());
        dest.writeInt(this.resultCode.getValue());
        dest.writeDouble(this.confidence);
        dest.writeLong(this.inferenceTime);
        dest.writeString(this.typoSquattingPredicted);
        dest.writeString(this.shortUrlResolved);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
