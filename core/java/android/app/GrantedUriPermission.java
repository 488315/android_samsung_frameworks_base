package android.app;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class GrantedUriPermission implements Parcelable {
    public static final Parcelable.Creator<GrantedUriPermission> CREATOR =
            new Parcelable.Creator<
                    GrantedUriPermission>() { // from class: android.app.GrantedUriPermission.1
                @Override // android.os.Parcelable.Creator
                public GrantedUriPermission createFromParcel(Parcel in) {
                    return new GrantedUriPermission(in);
                }

                @Override // android.os.Parcelable.Creator
                public GrantedUriPermission[] newArray(int size) {
                    return new GrantedUriPermission[size];
                }
            };
    public final String packageName;
    public final Uri uri;

    public GrantedUriPermission(Uri uri, String packageName) {
        this.uri = uri;
        this.packageName = packageName;
    }

    public String toString() {
        return this.packageName + ":" + this.uri;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeParcelable(this.uri, flags);
        out.writeString(this.packageName);
    }

    private GrantedUriPermission(Parcel in) {
        this.uri = (Uri) in.readParcelable(null, Uri.class);
        this.packageName = in.readString();
    }
}
