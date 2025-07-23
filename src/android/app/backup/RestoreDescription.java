package android.app.backup;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
/* loaded from: classes.dex */
public class RestoreDescription implements Parcelable {
    public static final int TYPE_FULL_STREAM = 2;
    public static final int TYPE_KEY_VALUE = 1;
    private final int mDataType;
    private final String mPackageName;
    private static final String NO_MORE_PACKAGES_SENTINEL = "NO_MORE_PACKAGES";
    public static final RestoreDescription NO_MORE_PACKAGES = new RestoreDescription(NO_MORE_PACKAGES_SENTINEL, 0);
    public static final Parcelable.Creator<RestoreDescription> CREATOR = new Parcelable.Creator<RestoreDescription>() { // from class: android.app.backup.RestoreDescription.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RestoreDescription createFromParcel(Parcel parcel) {
            RestoreDescription restoreDescription = new RestoreDescription(parcel);
            return RestoreDescription.NO_MORE_PACKAGES_SENTINEL.equals(restoreDescription.mPackageName) ? RestoreDescription.NO_MORE_PACKAGES : restoreDescription;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RestoreDescription[] newArray(int i) {
            return new RestoreDescription[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("RestoreDescription{");
        sb.append(this.mPackageName);
        sb.append(" : ");
        sb.append(this.mDataType == 1 ? "KEY_VALUE" : "STREAM");
        sb.append('}');
        return sb.toString();
    }

    public RestoreDescription(String str, int i) {
        this.mPackageName = str;
        this.mDataType = i;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public int getDataType() {
        return this.mDataType;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mPackageName);
        parcel.writeInt(this.mDataType);
    }

    private RestoreDescription(Parcel parcel) {
        this.mPackageName = parcel.readString();
        this.mDataType = parcel.readInt();
    }
}
