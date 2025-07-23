package android.media.tv;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public final class CommandResponse extends BroadcastInfoResponse implements Parcelable {
    public static final Parcelable.Creator<CommandResponse> CREATOR = new Parcelable.Creator<CommandResponse>() { // from class: android.media.tv.CommandResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CommandResponse createFromParcel(Parcel parcel) {
            parcel.readInt();
            return CommandResponse.createFromParcelBody(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CommandResponse[] newArray(int i) {
            return new CommandResponse[i];
        }
    };
    private static final int RESPONSE_TYPE = 7;
    public static final String RESPONSE_TYPE_JSON = "json";
    public static final String RESPONSE_TYPE_XML = "xml";
    private final String mResponse;
    private final String mResponseType;

    @Override // android.media.tv.BroadcastInfoResponse, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    static CommandResponse createFromParcelBody(Parcel parcel) {
        return new CommandResponse(parcel);
    }

    public CommandResponse(int i, int i2, int i3, String str, String str2) {
        super(7, i, i2, i3);
        this.mResponse = str;
        this.mResponseType = str2;
    }

    CommandResponse(Parcel parcel) {
        super(7, parcel);
        this.mResponse = parcel.readString();
        this.mResponseType = parcel.readString();
    }

    public String getResponse() {
        return this.mResponse;
    }

    public String getResponseType() {
        return this.mResponseType;
    }

    @Override // android.media.tv.BroadcastInfoResponse, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.mResponse);
        parcel.writeString(this.mResponseType);
    }
}
