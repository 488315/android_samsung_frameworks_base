package android.app.blob;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

/* loaded from: classes.dex */
public final class LeaseInfo implements Parcelable {
    public static final Parcelable.Creator<LeaseInfo> CREATOR = new Parcelable.Creator<LeaseInfo>() { // from class: android.app.blob.LeaseInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LeaseInfo createFromParcel(Parcel parcel) {
            return new LeaseInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LeaseInfo[] newArray(int i) {
            return new LeaseInfo[i];
        }
    };
    private final CharSequence mDescription;
    private final int mDescriptionResId;
    private final long mExpiryTimeMillis;
    private final String mPackageName;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public LeaseInfo(String str, long j, int i, CharSequence charSequence) {
        this.mPackageName = str;
        this.mExpiryTimeMillis = j;
        this.mDescriptionResId = i;
        this.mDescription = charSequence;
    }

    private LeaseInfo(Parcel parcel) {
        this.mPackageName = parcel.readString();
        this.mExpiryTimeMillis = parcel.readLong();
        this.mDescriptionResId = parcel.readInt();
        this.mDescription = parcel.readCharSequence();
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public long getExpiryTimeMillis() {
        return this.mExpiryTimeMillis;
    }

    public int getDescriptionResId() {
        return this.mDescriptionResId;
    }

    public CharSequence getDescription() {
        return this.mDescription;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mPackageName);
        parcel.writeLong(this.mExpiryTimeMillis);
        parcel.writeInt(this.mDescriptionResId);
        parcel.writeCharSequence(this.mDescription);
    }

    public String toString() {
        return "LeaseInfo {package: " + this.mPackageName + ",expiryMs: " + this.mExpiryTimeMillis + ",descriptionResId: " + this.mDescriptionResId + ",description: " + ((Object) this.mDescription) + ",}";
    }

    private String toShortString() {
        return this.mPackageName;
    }

    static String toShortString(List<LeaseInfo> list) {
        StringBuilder sb = new StringBuilder(NavigationBarInflaterView.SIZE_MOD_START);
        int size = list.size();
        for (int i = 0; i < size; i++) {
            sb.append(list.get(i).toShortString());
            sb.append(",");
        }
        sb.append(NavigationBarInflaterView.SIZE_MOD_END);
        return sb.toString();
    }
}
