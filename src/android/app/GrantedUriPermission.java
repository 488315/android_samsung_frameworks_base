package android.app;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class GrantedUriPermission implements Parcelable {
    public static final Parcelable.Creator<GrantedUriPermission> CREATOR = new Parcelable.Creator<GrantedUriPermission>() { // from class: android.app.GrantedUriPermission.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GrantedUriPermission createFromParcel(Parcel parcel) {
            return new GrantedUriPermission(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GrantedUriPermission[] newArray(int i) {
            return new GrantedUriPermission[i];
        }
    };
    public final String packageName;
    public final Uri uri;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public GrantedUriPermission(Uri uri, String str) {
        this.uri = uri;
        this.packageName = str;
    }

    public String toString() {
        return this.packageName + ":" + this.uri;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.uri, i);
        parcel.writeString(this.packageName);
    }

    private GrantedUriPermission(Parcel parcel) {
        this.uri = (Uri) parcel.readParcelable(null, Uri.class);
        this.packageName = parcel.readString();
    }
}
