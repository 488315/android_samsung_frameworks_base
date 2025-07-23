package android.media.tv;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public final class DsmccRequest extends BroadcastInfoRequest implements Parcelable {
    public static final Parcelable.Creator<DsmccRequest> CREATOR = new Parcelable.Creator<DsmccRequest>() { // from class: android.media.tv.DsmccRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DsmccRequest createFromParcel(Parcel parcel) {
            parcel.readInt();
            return DsmccRequest.createFromParcelBody(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DsmccRequest[] newArray(int i) {
            return new DsmccRequest[i];
        }
    };
    private static final int REQUEST_TYPE = 6;
    private final Uri mUri;

    @Override // android.media.tv.BroadcastInfoRequest, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    static DsmccRequest createFromParcelBody(Parcel parcel) {
        return new DsmccRequest(parcel);
    }

    public DsmccRequest(int i, int i2, Uri uri) {
        super(6, i, i2);
        this.mUri = uri;
    }

    DsmccRequest(Parcel parcel) {
        super(6, parcel);
        String readString = parcel.readString();
        this.mUri = readString == null ? null : Uri.parse(readString);
    }

    public Uri getUri() {
        return this.mUri;
    }

    @Override // android.media.tv.BroadcastInfoRequest, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        Uri uri = this.mUri;
        parcel.writeString(uri == null ? null : uri.toString());
    }
}
