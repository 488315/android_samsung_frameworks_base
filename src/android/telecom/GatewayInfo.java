package android.telecom;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/* loaded from: classes4.dex */
public class GatewayInfo implements Parcelable {
    public static final Parcelable.Creator<GatewayInfo> CREATOR = new Parcelable.Creator<GatewayInfo>() { // from class: android.telecom.GatewayInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GatewayInfo createFromParcel(Parcel parcel) {
            return new GatewayInfo(parcel.readString(), Uri.CREATOR.createFromParcel(parcel), Uri.CREATOR.createFromParcel(parcel));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GatewayInfo[] newArray(int i) {
            return new GatewayInfo[i];
        }
    };
    private final Uri mGatewayAddress;
    private final String mGatewayProviderPackageName;
    private final Uri mOriginalAddress;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public GatewayInfo(String str, Uri uri, Uri uri2) {
        this.mGatewayProviderPackageName = str;
        this.mGatewayAddress = uri;
        this.mOriginalAddress = uri2;
    }

    public String getGatewayProviderPackageName() {
        return this.mGatewayProviderPackageName;
    }

    public Uri getGatewayAddress() {
        return this.mGatewayAddress;
    }

    public Uri getOriginalAddress() {
        return this.mOriginalAddress;
    }

    public boolean isEmpty() {
        return TextUtils.isEmpty(this.mGatewayProviderPackageName) || this.mGatewayAddress == null;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mGatewayProviderPackageName);
        Uri.writeToParcel(parcel, this.mGatewayAddress);
        Uri.writeToParcel(parcel, this.mOriginalAddress);
    }
}
