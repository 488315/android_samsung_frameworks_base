package android.media.tv;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public final class StreamEventRequest extends BroadcastInfoRequest implements Parcelable {
    public static final Parcelable.Creator<StreamEventRequest> CREATOR = new Parcelable.Creator<StreamEventRequest>() { // from class: android.media.tv.StreamEventRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StreamEventRequest createFromParcel(Parcel source) {
            source.readInt();
            return StreamEventRequest.createFromParcelBody(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StreamEventRequest[] newArray(int size) {
            return new StreamEventRequest[size];
        }
    };
    private static final int REQUEST_TYPE = 5;
    private final String mEventName;
    private final Uri mTargetUri;

    static StreamEventRequest createFromParcelBody(Parcel in) {
        return new StreamEventRequest(in);
    }

    public StreamEventRequest(int requestId, int option, Uri targetUri, String eventName) {
        super(5, requestId, option);
        this.mTargetUri = targetUri;
        this.mEventName = eventName;
    }

    StreamEventRequest(Parcel source) {
        super(5, source);
        String uriString = source.readString();
        this.mTargetUri = uriString == null ? null : Uri.parse(uriString);
        this.mEventName = source.readString();
    }

    public Uri getTargetUri() {
        return this.mTargetUri;
    }

    public String getEventName() {
        return this.mEventName;
    }

    @Override // android.media.tv.BroadcastInfoRequest, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.media.tv.BroadcastInfoRequest, android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        String uriString = this.mTargetUri == null ? null : this.mTargetUri.toString();
        dest.writeString(uriString);
        dest.writeString(this.mEventName);
    }
}
