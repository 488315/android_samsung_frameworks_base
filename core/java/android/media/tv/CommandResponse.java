package android.media.tv;

import android.os.Parcel;
import android.os.Parcelable;

public final class CommandResponse extends BroadcastInfoResponse implements Parcelable {
    public static final Parcelable.Creator<CommandResponse> CREATOR =
            new Parcelable.Creator<
                    CommandResponse>() { // from class: android.media.tv.CommandResponse.1
                @Override // android.os.Parcelable.Creator
                public CommandResponse createFromParcel(Parcel source) {
                    source.readInt();
                    return CommandResponse.createFromParcelBody(source);
                }

                @Override // android.os.Parcelable.Creator
                public CommandResponse[] newArray(int size) {
                    return new CommandResponse[size];
                }
            };
    private static final int RESPONSE_TYPE = 7;
    public static final String RESPONSE_TYPE_JSON = "json";
    public static final String RESPONSE_TYPE_XML = "xml";
    private final String mResponse;
    private final String mResponseType;

    static CommandResponse createFromParcelBody(Parcel in) {
        return new CommandResponse(in);
    }

    public CommandResponse(
            int requestId, int sequence, int responseResult, String response, String responseType) {
        super(7, requestId, sequence, responseResult);
        this.mResponse = response;
        this.mResponseType = responseType;
    }

    CommandResponse(Parcel source) {
        super(7, source);
        this.mResponse = source.readString();
        this.mResponseType = source.readString();
    }

    public String getResponse() {
        return this.mResponse;
    }

    public String getResponseType() {
        return this.mResponseType;
    }

    @Override // android.media.tv.BroadcastInfoResponse, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.media.tv.BroadcastInfoResponse, android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.mResponse);
        dest.writeString(this.mResponseType);
    }
}
