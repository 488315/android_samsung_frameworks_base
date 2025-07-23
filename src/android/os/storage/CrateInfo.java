package android.os.storage;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import android.text.TextUtils;
import com.android.internal.util.Preconditions;

/* loaded from: classes3.dex */
public final class CrateInfo implements Parcelable {
    public static final Parcelable.Creator<CrateInfo> CREATOR = new Parcelable.Creator<CrateInfo>() { // from class: android.os.storage.CrateInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CrateInfo createFromParcel(Parcel parcel) {
            CrateInfo crateInfo = new CrateInfo();
            crateInfo.readFromParcel(parcel);
            return crateInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CrateInfo[] newArray(int i) {
            return new CrateInfo[i];
        }
    };
    private static final String TAG = "CrateInfo";
    private long mExpiration;
    private String mId;
    private CharSequence mLabel;
    private String mPackageName;
    private int mUid;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private CrateInfo() {
        this.mExpiration = 0L;
    }

    public CrateInfo(CharSequence charSequence, long j) {
        Preconditions.checkStringNotEmpty(charSequence, "Label should not be either null or empty string");
        Preconditions.checkArgumentNonnegative(j, "Expiration should be non negative number");
        this.mLabel = charSequence;
        this.mExpiration = j;
    }

    public CrateInfo(CharSequence charSequence) {
        this(charSequence, 0L);
    }

    public CharSequence getLabel() {
        if (TextUtils.isEmpty(this.mLabel)) {
            return this.mId;
        }
        return this.mLabel;
    }

    public long getExpirationMillis() {
        return this.mExpiration;
    }

    public void setExpiration(long j) {
        Preconditions.checkArgumentNonnegative(j);
        this.mExpiration = j;
    }

    public int hashCode() {
        return super.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof CrateInfo) {
            CrateInfo crateInfo = (CrateInfo) obj;
            if (!TextUtils.isEmpty(this.mId) && TextUtils.equals(this.mId, crateInfo.mId)) {
                return true;
            }
        }
        return super.equals(obj);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        if (parcel == null) {
            return;
        }
        parcel.writeCharSequence(this.mLabel);
        parcel.writeLong(this.mExpiration);
        parcel.writeInt(this.mUid);
        parcel.writeString(this.mPackageName);
        parcel.writeString(this.mId);
    }

    public void readFromParcel(Parcel parcel) {
        if (parcel == null) {
            return;
        }
        this.mLabel = parcel.readCharSequence();
        this.mExpiration = parcel.readLong();
        this.mUid = parcel.readInt();
        this.mPackageName = parcel.readString();
        this.mId = parcel.readString();
    }

    public static CrateInfo copyFrom(int i, String str, String str2) {
        if (!UserHandle.isApp(i) || TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return null;
        }
        CrateInfo crateInfo = new CrateInfo(str2, 0L);
        crateInfo.mUid = i;
        crateInfo.mPackageName = str;
        crateInfo.mId = str2;
        return crateInfo;
    }
}
