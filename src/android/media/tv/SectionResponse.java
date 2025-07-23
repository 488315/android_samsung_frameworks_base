package android.media.tv;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public final class SectionResponse extends BroadcastInfoResponse implements Parcelable {
    public static final Parcelable.Creator<SectionResponse> CREATOR = new Parcelable.Creator<SectionResponse>() { // from class: android.media.tv.SectionResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SectionResponse createFromParcel(Parcel parcel) {
            parcel.readInt();
            return SectionResponse.createFromParcelBody(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SectionResponse[] newArray(int i) {
            return new SectionResponse[i];
        }
    };
    private static final int RESPONSE_TYPE = 3;
    private final Bundle mSessionData;
    private final int mSessionId;
    private final int mVersion;

    @Override // android.media.tv.BroadcastInfoResponse, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    static SectionResponse createFromParcelBody(Parcel parcel) {
        return new SectionResponse(parcel);
    }

    public SectionResponse(int i, int i2, int i3, int i4, int i5, Bundle bundle) {
        super(3, i, i2, i3);
        this.mSessionId = i4;
        this.mVersion = i5;
        this.mSessionData = bundle;
    }

    SectionResponse(Parcel parcel) {
        super(3, parcel);
        this.mSessionId = parcel.readInt();
        this.mVersion = parcel.readInt();
        this.mSessionData = parcel.readBundle();
    }

    public int getSessionId() {
        return this.mSessionId;
    }

    public int getVersion() {
        return this.mVersion;
    }

    public Bundle getSessionData() {
        return this.mSessionData;
    }

    @Override // android.media.tv.BroadcastInfoResponse, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.mSessionId);
        parcel.writeInt(this.mVersion);
        parcel.writeBundle(this.mSessionData);
    }
}
