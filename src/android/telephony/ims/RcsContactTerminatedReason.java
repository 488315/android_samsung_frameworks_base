package android.telephony.ims;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes4.dex */
public final class RcsContactTerminatedReason implements Parcelable {
    public static final Parcelable.Creator<RcsContactTerminatedReason> CREATOR = new Parcelable.Creator<RcsContactTerminatedReason>() { // from class: android.telephony.ims.RcsContactTerminatedReason.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RcsContactTerminatedReason createFromParcel(Parcel parcel) {
            return new RcsContactTerminatedReason(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RcsContactTerminatedReason[] newArray(int i) {
            return new RcsContactTerminatedReason[i];
        }
    };
    private final Uri mContactUri;
    private final String mReason;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public RcsContactTerminatedReason(Uri uri, String str) {
        this.mContactUri = uri;
        this.mReason = str;
    }

    private RcsContactTerminatedReason(Parcel parcel) {
        this.mContactUri = (Uri) parcel.readParcelable(Uri.class.getClassLoader(), Uri.class);
        this.mReason = parcel.readString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.mContactUri, i);
        parcel.writeString(this.mReason);
    }

    public Uri getContactUri() {
        return this.mContactUri;
    }

    public String getReason() {
        return this.mReason;
    }
}
