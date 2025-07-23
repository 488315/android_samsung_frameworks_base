package android.telephony;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class ClosedSubscriberGroupInfo implements Parcelable {
    public static final Parcelable.Creator<ClosedSubscriberGroupInfo> CREATOR = new Parcelable.Creator<ClosedSubscriberGroupInfo>() { // from class: android.telephony.ClosedSubscriberGroupInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ClosedSubscriberGroupInfo createFromParcel(Parcel parcel) {
            return ClosedSubscriberGroupInfo.createFromParcelBody(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ClosedSubscriberGroupInfo[] newArray(int i) {
            return new ClosedSubscriberGroupInfo[i];
        }
    };
    private static final String TAG = "ClosedSubscriberGroupInfo";
    private final int mCsgIdentity;
    private final boolean mCsgIndicator;
    private final String mHomeNodebName;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ClosedSubscriberGroupInfo(boolean z, String str, int i) {
        this.mCsgIndicator = z;
        this.mHomeNodebName = str == null ? "" : str;
        this.mCsgIdentity = i;
    }

    public boolean getCsgIndicator() {
        return this.mCsgIndicator;
    }

    public String getHomeNodebName() {
        return this.mHomeNodebName;
    }

    public int getCsgIdentity() {
        return this.mCsgIdentity;
    }

    public int hashCode() {
        return Objects.hash(Boolean.valueOf(this.mCsgIndicator), this.mHomeNodebName, Integer.valueOf(this.mCsgIdentity));
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ClosedSubscriberGroupInfo)) {
            return false;
        }
        ClosedSubscriberGroupInfo closedSubscriberGroupInfo = (ClosedSubscriberGroupInfo) obj;
        return this.mCsgIndicator == closedSubscriberGroupInfo.mCsgIndicator && closedSubscriberGroupInfo.mHomeNodebName.equals(this.mHomeNodebName) && this.mCsgIdentity == closedSubscriberGroupInfo.mCsgIdentity;
    }

    public String toString() {
        return "ClosedSubscriberGroupInfo:{ mCsgIndicator = " + this.mCsgIndicator + " mHomeNodebName = " + this.mHomeNodebName + " mCsgIdentity = " + this.mCsgIdentity;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBoolean(this.mCsgIndicator);
        parcel.writeString(this.mHomeNodebName);
        parcel.writeInt(this.mCsgIdentity);
    }

    private ClosedSubscriberGroupInfo(Parcel parcel) {
        this(parcel.readBoolean(), parcel.readString(), parcel.readInt());
    }

    protected static ClosedSubscriberGroupInfo createFromParcelBody(Parcel parcel) {
        return new ClosedSubscriberGroupInfo(parcel);
    }
}
