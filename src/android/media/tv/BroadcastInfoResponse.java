package android.media.tv;

import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes3.dex */
public abstract class BroadcastInfoResponse implements Parcelable {
    public static final Parcelable.Creator<BroadcastInfoResponse> CREATOR = new Parcelable.Creator<BroadcastInfoResponse>() { // from class: android.media.tv.BroadcastInfoResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BroadcastInfoResponse createFromParcel(Parcel parcel) {
            int readInt = parcel.readInt();
            switch (readInt) {
                case 1:
                    return TsResponse.createFromParcelBody(parcel);
                case 2:
                    return TableResponse.createFromParcelBody(parcel);
                case 3:
                    return SectionResponse.createFromParcelBody(parcel);
                case 4:
                    return PesResponse.createFromParcelBody(parcel);
                case 5:
                    return StreamEventResponse.createFromParcelBody(parcel);
                case 6:
                    return DsmccResponse.createFromParcelBody(parcel);
                case 7:
                    return CommandResponse.createFromParcelBody(parcel);
                case 8:
                    return TimelineResponse.createFromParcelBody(parcel);
                case 9:
                    return SignalingDataResponse.createFromParcelBody(parcel);
                default:
                    throw new IllegalStateException("Unexpected broadcast info response type (value " + readInt + ") in parcel.");
            }
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BroadcastInfoResponse[] newArray(int i) {
            return new BroadcastInfoResponse[i];
        }
    };
    public static final int RESPONSE_RESULT_CANCEL = 3;
    public static final int RESPONSE_RESULT_ERROR = 1;
    public static final int RESPONSE_RESULT_OK = 2;
    private final int mRequestId;
    private final int mResponseResult;
    private final int mSequence;
    private final int mType;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ResponseResult {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    BroadcastInfoResponse(int i, int i2, int i3, int i4) {
        this.mType = i;
        this.mRequestId = i2;
        this.mSequence = i3;
        this.mResponseResult = i4;
    }

    BroadcastInfoResponse(int i, Parcel parcel) {
        this.mType = i;
        this.mRequestId = parcel.readInt();
        this.mSequence = parcel.readInt();
        this.mResponseResult = parcel.readInt();
    }

    public int getType() {
        return this.mType;
    }

    public int getRequestId() {
        return this.mRequestId;
    }

    public int getSequence() {
        return this.mSequence;
    }

    public int getResponseResult() {
        return this.mResponseResult;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mType);
        parcel.writeInt(this.mRequestId);
        parcel.writeInt(this.mSequence);
        parcel.writeInt(this.mResponseResult);
    }
}
