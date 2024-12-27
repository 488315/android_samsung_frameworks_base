package android.media.tv;

import android.annotation.NonNull;
import android.os.Parcel;
import android.os.Parcelable;

import com.android.internal.util.AnnotationValidations;

import java.util.ArrayList;
import java.util.List;

public final class SignalingDataResponse extends BroadcastInfoResponse implements Parcelable {
    public static final Parcelable.Creator<SignalingDataResponse> CREATOR =
            new Parcelable.Creator<
                    SignalingDataResponse>() { // from class:
                                               // android.media.tv.SignalingDataResponse.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SignalingDataResponse[] newArray(int size) {
                    return new SignalingDataResponse[size];
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SignalingDataResponse createFromParcel(Parcel in) {
                    return new SignalingDataResponse(in);
                }
            };
    private static final int RESPONSE_TYPE = 9;
    private final List<SignalingDataInfo> mSignalingDataInfoList;
    private final List<String> mSignalingDataTypes;

    static SignalingDataResponse createFromParcelBody(Parcel in) {
        return new SignalingDataResponse(in);
    }

    public SignalingDataResponse(
            int requestId,
            int sequence,
            int responseResult,
            List<String> signalingDataTypes,
            List<SignalingDataInfo> signalingDataInfoList) {
        super(9, requestId, sequence, responseResult);
        this.mSignalingDataTypes = signalingDataTypes;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mSignalingDataTypes);
        this.mSignalingDataInfoList = signalingDataInfoList;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class,
                (NonNull) null,
                (Object) this.mSignalingDataInfoList);
    }

    public List<String> getSignalingDataTypes() {
        return this.mSignalingDataTypes;
    }

    public List<SignalingDataInfo> getSignalingDataInfoList() {
        return this.mSignalingDataInfoList;
    }

    @Override // android.media.tv.BroadcastInfoResponse, android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeStringList(this.mSignalingDataTypes);
        dest.writeParcelableList(this.mSignalingDataInfoList, flags);
    }

    @Override // android.media.tv.BroadcastInfoResponse, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    SignalingDataResponse(Parcel in) {
        super(9, in);
        List<String> types = new ArrayList<>();
        in.readStringList(types);
        ArrayList arrayList = new ArrayList();
        in.readParcelableList(arrayList, SignalingDataInfo.class.getClassLoader());
        this.mSignalingDataTypes = types;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mSignalingDataTypes);
        this.mSignalingDataInfoList = arrayList;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class,
                (NonNull) null,
                (Object) this.mSignalingDataInfoList);
    }
}
