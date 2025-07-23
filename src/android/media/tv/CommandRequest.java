package android.media.tv;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public final class CommandRequest extends BroadcastInfoRequest implements Parcelable {
    public static final String ARGUMENT_TYPE_JSON = "json";
    public static final String ARGUMENT_TYPE_XML = "xml";
    public static final Parcelable.Creator<CommandRequest> CREATOR = new Parcelable.Creator<CommandRequest>() { // from class: android.media.tv.CommandRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CommandRequest createFromParcel(Parcel parcel) {
            parcel.readInt();
            return CommandRequest.createFromParcelBody(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CommandRequest[] newArray(int i) {
            return new CommandRequest[i];
        }
    };
    private static final int REQUEST_TYPE = 7;
    private final String mArgumentType;
    private final String mArguments;
    private final String mName;
    private final String mNamespace;

    @Override // android.media.tv.BroadcastInfoRequest, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    static CommandRequest createFromParcelBody(Parcel parcel) {
        return new CommandRequest(parcel);
    }

    public CommandRequest(int i, int i2, String str, String str2, String str3, String str4) {
        super(7, i, i2);
        this.mNamespace = str;
        this.mName = str2;
        this.mArguments = str3;
        this.mArgumentType = str4;
    }

    CommandRequest(Parcel parcel) {
        super(7, parcel);
        this.mNamespace = parcel.readString();
        this.mName = parcel.readString();
        this.mArguments = parcel.readString();
        this.mArgumentType = parcel.readString();
    }

    public String getNamespace() {
        return this.mNamespace;
    }

    public String getName() {
        return this.mName;
    }

    public String getArguments() {
        return this.mArguments;
    }

    public String getArgumentType() {
        return this.mArgumentType;
    }

    @Override // android.media.tv.BroadcastInfoRequest, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.mNamespace);
        parcel.writeString(this.mName);
        parcel.writeString(this.mArguments);
        parcel.writeString(this.mArgumentType);
    }
}
