package android.media.tv;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public final class StreamEventRequest extends BroadcastInfoRequest implements Parcelable {
    public static final Parcelable.Creator<StreamEventRequest> CREATOR = new Parcelable.Creator<StreamEventRequest>() { // from class: android.media.tv.StreamEventRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StreamEventRequest createFromParcel(Parcel parcel) {
            parcel.readInt();
            return StreamEventRequest.createFromParcelBody(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StreamEventRequest[] newArray(int i) {
            return new StreamEventRequest[i];
        }
    };
    private static final int REQUEST_TYPE = 5;
    private final String mEventName;
    private final Uri mTargetUri;

    @Override // android.media.tv.BroadcastInfoRequest, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    static StreamEventRequest createFromParcelBody(Parcel parcel) {
        return new StreamEventRequest(parcel);
    }

    public StreamEventRequest(int i, int i2, Uri uri, String str) {
        super(5, i, i2);
        this.mTargetUri = uri;
        this.mEventName = str;
    }

    StreamEventRequest(Parcel parcel) {
        super(5, parcel);
        String string = parcel.readString();
        this.mTargetUri = string == null ? null : Uri.parse(string);
        this.mEventName = parcel.readString();
    }

    public Uri getTargetUri() {
        return this.mTargetUri;
    }

    public String getEventName() {
        return this.mEventName;
    }

    @Override // android.media.tv.BroadcastInfoRequest, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        Uri uri = this.mTargetUri;
        parcel.writeString(uri == null ? null : uri.toString());
        parcel.writeString(this.mEventName);
    }
}
