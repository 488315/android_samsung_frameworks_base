package android.media.tv;

import android.annotation.NonNull;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public final class SignalingDataResponse extends BroadcastInfoResponse implements Parcelable {
    public static final Parcelable.Creator<SignalingDataResponse> CREATOR = new Parcelable.Creator<SignalingDataResponse>() { // from class: android.media.tv.SignalingDataResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SignalingDataResponse[] newArray(int i) {
            return new SignalingDataResponse[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SignalingDataResponse createFromParcel(Parcel parcel) {
            return new SignalingDataResponse(parcel);
        }
    };
    private static final int RESPONSE_TYPE = 9;
    private final List<SignalingDataInfo> mSignalingDataInfoList;
    private final List<String> mSignalingDataTypes;

    @Override // android.media.tv.BroadcastInfoResponse, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    static SignalingDataResponse createFromParcelBody(Parcel parcel) {
        return new SignalingDataResponse(parcel);
    }

    public SignalingDataResponse(int i, int i2, int i3, List<String> list, List<SignalingDataInfo> list2) {
        super(9, i, i2, i3);
        this.mSignalingDataTypes = list;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) list);
        this.mSignalingDataInfoList = list2;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) list2);
    }

    public List<String> getSignalingDataTypes() {
        return this.mSignalingDataTypes;
    }

    public List<SignalingDataInfo> getSignalingDataInfoList() {
        return this.mSignalingDataInfoList;
    }

    @Override // android.media.tv.BroadcastInfoResponse, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeStringList(this.mSignalingDataTypes);
        parcel.writeParcelableList(this.mSignalingDataInfoList, i);
    }

    SignalingDataResponse(Parcel parcel) {
        super(9, parcel);
        ArrayList arrayList = new ArrayList();
        parcel.readStringList(arrayList);
        ArrayList arrayList2 = new ArrayList();
        parcel.readParcelableList(arrayList2, SignalingDataInfo.class.getClassLoader());
        this.mSignalingDataTypes = arrayList;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) arrayList);
        this.mSignalingDataInfoList = arrayList2;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) arrayList2);
    }
}
