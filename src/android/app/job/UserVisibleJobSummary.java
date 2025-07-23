package android.app.job;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes.dex */
public class UserVisibleJobSummary implements Parcelable {
    public static final Parcelable.Creator<UserVisibleJobSummary> CREATOR = new Parcelable.Creator<UserVisibleJobSummary>() { // from class: android.app.job.UserVisibleJobSummary.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UserVisibleJobSummary createFromParcel(Parcel parcel) {
            return new UserVisibleJobSummary(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UserVisibleJobSummary[] newArray(int i) {
            return new UserVisibleJobSummary[i];
        }
    };
    private final String mCallingPackageName;
    private final int mCallingUid;
    private final int mJobId;
    private final String mNamespace;
    private final String mSourcePackageName;
    private final int mSourceUserId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public UserVisibleJobSummary(int i, String str, int i2, String str2, String str3, int i3) {
        this.mCallingUid = i;
        this.mCallingPackageName = str;
        this.mSourceUserId = i2;
        this.mSourcePackageName = str2;
        this.mNamespace = str3;
        this.mJobId = i3;
    }

    protected UserVisibleJobSummary(Parcel parcel) {
        this.mCallingUid = parcel.readInt();
        this.mCallingPackageName = parcel.readString();
        this.mSourceUserId = parcel.readInt();
        this.mSourcePackageName = parcel.readString();
        this.mNamespace = parcel.readString();
        this.mJobId = parcel.readInt();
    }

    public String getCallingPackageName() {
        return this.mCallingPackageName;
    }

    public int getCallingUid() {
        return this.mCallingUid;
    }

    public int getJobId() {
        return this.mJobId;
    }

    public String getNamespace() {
        return this.mNamespace;
    }

    public int getSourceUserId() {
        return this.mSourceUserId;
    }

    public String getSourcePackageName() {
        return this.mSourcePackageName;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof UserVisibleJobSummary)) {
            return false;
        }
        UserVisibleJobSummary userVisibleJobSummary = (UserVisibleJobSummary) obj;
        return this.mCallingUid == userVisibleJobSummary.mCallingUid && this.mCallingPackageName.equals(userVisibleJobSummary.mCallingPackageName) && this.mSourceUserId == userVisibleJobSummary.mSourceUserId && this.mSourcePackageName.equals(userVisibleJobSummary.mSourcePackageName) && Objects.equals(this.mNamespace, userVisibleJobSummary.mNamespace) && this.mJobId == userVisibleJobSummary.mJobId;
    }

    public int hashCode() {
        int hashCode = (((((this.mCallingUid * 31) + this.mCallingPackageName.hashCode()) * 31) + this.mSourceUserId) * 31) + this.mSourcePackageName.hashCode();
        String str = this.mNamespace;
        if (str != null) {
            hashCode = (hashCode * 31) + str.hashCode();
        }
        return (hashCode * 31) + this.mJobId;
    }

    public String toString() {
        return "UserVisibleJobSummary{callingUid=" + this.mCallingUid + ", callingPackageName='" + this.mCallingPackageName + "', sourceUserId=" + this.mSourceUserId + ", sourcePackageName='" + this.mSourcePackageName + "', namespace=" + this.mNamespace + ", jobId=" + this.mJobId + "}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mCallingUid);
        parcel.writeString(this.mCallingPackageName);
        parcel.writeInt(this.mSourceUserId);
        parcel.writeString(this.mSourcePackageName);
        parcel.writeString(this.mNamespace);
        parcel.writeInt(this.mJobId);
    }
}
