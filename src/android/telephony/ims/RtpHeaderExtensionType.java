package android.telephony.ims;

import android.annotation.SystemApi;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

@SystemApi
/* loaded from: classes4.dex */
public final class RtpHeaderExtensionType implements Parcelable {
    public static final Parcelable.Creator<RtpHeaderExtensionType> CREATOR = new Parcelable.Creator<RtpHeaderExtensionType>() { // from class: android.telephony.ims.RtpHeaderExtensionType.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RtpHeaderExtensionType createFromParcel(Parcel parcel) {
            return new RtpHeaderExtensionType(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RtpHeaderExtensionType[] newArray(int i) {
            return new RtpHeaderExtensionType[i];
        }
    };
    private int mLocalIdentifier;
    private Uri mUri;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public RtpHeaderExtensionType(int i, Uri uri) {
        if (i < 1 || i > 13) {
            throw new IllegalArgumentException("localIdentifier must be in range 1-14");
        }
        if (uri == null) {
            throw new NullPointerException("uri is required.");
        }
        this.mLocalIdentifier = i;
        this.mUri = uri;
    }

    private RtpHeaderExtensionType(Parcel parcel) {
        this.mLocalIdentifier = parcel.readInt();
        this.mUri = (Uri) parcel.readParcelable(Uri.class.getClassLoader(), Uri.class);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mLocalIdentifier);
        parcel.writeParcelable(this.mUri, i);
    }

    public int getLocalIdentifier() {
        return this.mLocalIdentifier;
    }

    public Uri getUri() {
        return this.mUri;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            RtpHeaderExtensionType rtpHeaderExtensionType = (RtpHeaderExtensionType) obj;
            if (this.mLocalIdentifier == rtpHeaderExtensionType.mLocalIdentifier && this.mUri.equals(rtpHeaderExtensionType.mUri)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mLocalIdentifier), this.mUri);
    }

    public String toString() {
        return "RtpHeaderExtensionType{mLocalIdentifier=" + this.mLocalIdentifier + ", mUri=" + this.mUri + "}";
    }
}
