package android.media.tv;

import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes3.dex */
public abstract class BroadcastInfoRequest implements Parcelable {
    public static final Parcelable.Creator<BroadcastInfoRequest> CREATOR = new Parcelable.Creator<BroadcastInfoRequest>() { // from class: android.media.tv.BroadcastInfoRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BroadcastInfoRequest createFromParcel(Parcel parcel) {
            int i = parcel.readInt();
            switch (i) {
                case 1:
                    return TsRequest.createFromParcelBody(parcel);
                case 2:
                    return TableRequest.createFromParcelBody(parcel);
                case 3:
                    return SectionRequest.createFromParcelBody(parcel);
                case 4:
                    return PesRequest.createFromParcelBody(parcel);
                case 5:
                    return StreamEventRequest.createFromParcelBody(parcel);
                case 6:
                    return DsmccRequest.createFromParcelBody(parcel);
                case 7:
                    return CommandRequest.createFromParcelBody(parcel);
                case 8:
                    return TimelineRequest.createFromParcelBody(parcel);
                case 9:
                    return SignalingDataRequest.createFromParcelBody(parcel);
                default:
                    throw new IllegalStateException("Unexpected broadcast info request type (value " + i + ") in parcel.");
            }
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BroadcastInfoRequest[] newArray(int i) {
            return new BroadcastInfoRequest[i];
        }
    };
    public static final int REQUEST_OPTION_AUTO_UPDATE = 1;
    public static final int REQUEST_OPTION_ONESHOT = 3;
    public static final int REQUEST_OPTION_ONEWAY = 2;
    public static final int REQUEST_OPTION_REPEAT = 0;
    private final int mOption;
    private final int mRequestId;
    private final int mType;

    @Retention(RetentionPolicy.SOURCE)
    public @interface RequestOption {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    BroadcastInfoRequest(int i, int i2, int i3) {
        this.mType = i;
        this.mRequestId = i2;
        this.mOption = i3;
    }

    BroadcastInfoRequest(int i, Parcel parcel) {
        this.mType = i;
        this.mRequestId = parcel.readInt();
        this.mOption = parcel.readInt();
    }

    public int getType() {
        return this.mType;
    }

    public int getRequestId() {
        return this.mRequestId;
    }

    public int getOption() {
        return this.mOption;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mType);
        parcel.writeInt(this.mRequestId);
        parcel.writeInt(this.mOption);
    }
}
