package android.media.tv;

import android.annotation.NonNull;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public final class SignalingDataRequest extends BroadcastInfoRequest implements Parcelable {
    public static final Parcelable.Creator<SignalingDataRequest> CREATOR = new Parcelable.Creator<SignalingDataRequest>() { // from class: android.media.tv.SignalingDataRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SignalingDataRequest[] newArray(int i) {
            return new SignalingDataRequest[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SignalingDataRequest createFromParcel(Parcel parcel) {
            return new SignalingDataRequest(parcel);
        }
    };
    private static final int REQUEST_TYPE = 9;
    public static final int SIGNALING_DATA_NO_GROUP_ID = -1;
    public static final String SIGNALING_METADATA_AEAT = "AEAT";
    public static final String SIGNALING_METADATA_AEI = "AEI";
    public static final String SIGNALING_METADATA_APD = "APD";
    public static final String SIGNALING_METADATA_ASD = "ASD";
    public static final String SIGNALING_METADATA_ASPD = "ASPD";
    public static final String SIGNALING_METADATA_CAD = "CAD";
    public static final String SIGNALING_METADATA_CDT = "CDT";
    public static final String SIGNALING_METADATA_CRIT = "CRIT";
    public static final String SIGNALING_METADATA_DCIT = "DCIT";
    public static final String SIGNALING_METADATA_DWD = "DWD";
    public static final String SIGNALING_METADATA_EMSG = "EMSG";
    public static final String SIGNALING_METADATA_EVTI = "EVTI";
    public static final String SIGNALING_METADATA_HELD = "HELD";
    public static final String SIGNALING_METADATA_IED = "IED";
    public static final String SIGNALING_METADATA_MPD = "MPD";
    public static final String SIGNALING_METADATA_MPIT = "MPIT";
    public static final String SIGNALING_METADATA_MPT = "MPT";
    public static final String SIGNALING_METADATA_OSN = "OSN";
    public static final String SIGNALING_METADATA_PAT = "PAT";
    public static final String SIGNALING_METADATA_RDT = "RDT";
    public static final String SIGNALING_METADATA_RRT = "RRT";
    public static final String SIGNALING_METADATA_RSAT = "RSAT";
    public static final String SIGNALING_METADATA_SLT = "SLT";
    public static final String SIGNALING_METADATA_SMT = "SMT";
    public static final String SIGNALING_METADATA_SSD = "SSD";
    public static final String SIGNALING_METADATA_STSID = "STSID";
    public static final String SIGNALING_METADATA_STT = "STT";
    public static final String SIGNALING_METADATA_USBD = "USBD";
    public static final String SIGNALING_METADATA_USD = "USD";
    public static final String SIGNALING_METADATA_VSPD = "VSPD";
    private final int mGroup;
    private final List<String> mSignalingDataTypes;

    @Retention(RetentionPolicy.SOURCE)
    public @interface SignalingMetadata {
    }

    @Override // android.media.tv.BroadcastInfoRequest, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SignalingDataRequest(int i, int i2, int i3, List<String> list) {
        super(9, i, i2);
        this.mGroup = i3;
        this.mSignalingDataTypes = list;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) list);
    }

    static SignalingDataRequest createFromParcelBody(Parcel parcel) {
        return new SignalingDataRequest(parcel);
    }

    public int getGroup() {
        return this.mGroup;
    }

    public List<String> getSignalingDataTypes() {
        return this.mSignalingDataTypes;
    }

    @Override // android.media.tv.BroadcastInfoRequest, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.mGroup);
        parcel.writeStringList(this.mSignalingDataTypes);
    }

    SignalingDataRequest(Parcel parcel) {
        super(9, parcel);
        int i = parcel.readInt();
        ArrayList arrayList = new ArrayList();
        parcel.readStringList(arrayList);
        this.mGroup = i;
        this.mSignalingDataTypes = arrayList;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) arrayList);
    }
}
