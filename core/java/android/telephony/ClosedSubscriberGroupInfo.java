package android.telephony;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public final class ClosedSubscriberGroupInfo implements Parcelable {
    public static final Parcelable.Creator<ClosedSubscriberGroupInfo> CREATOR =
            new Parcelable.Creator<
                    ClosedSubscriberGroupInfo>() { // from class:
                                                   // android.telephony.ClosedSubscriberGroupInfo.1
                @Override // android.os.Parcelable.Creator
                public ClosedSubscriberGroupInfo createFromParcel(Parcel in) {
                    return ClosedSubscriberGroupInfo.createFromParcelBody(in);
                }

                @Override // android.os.Parcelable.Creator
                public ClosedSubscriberGroupInfo[] newArray(int size) {
                    return new ClosedSubscriberGroupInfo[size];
                }
            };
    private static final String TAG = "ClosedSubscriberGroupInfo";
    private final int mCsgIdentity;
    private final boolean mCsgIndicator;
    private final String mHomeNodebName;

    public ClosedSubscriberGroupInfo(boolean csgIndicator, String homeNodebName, int csgIdentity) {
        this.mCsgIndicator = csgIndicator;
        this.mHomeNodebName = homeNodebName == null ? "" : homeNodebName;
        this.mCsgIdentity = csgIdentity;
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
        return Objects.hash(
                Boolean.valueOf(this.mCsgIndicator),
                this.mHomeNodebName,
                Integer.valueOf(this.mCsgIdentity));
    }

    public boolean equals(Object other) {
        if (!(other instanceof ClosedSubscriberGroupInfo)) {
            return false;
        }
        ClosedSubscriberGroupInfo o = (ClosedSubscriberGroupInfo) other;
        return this.mCsgIndicator == o.mCsgIndicator
                && o.mHomeNodebName.equals(this.mHomeNodebName)
                && this.mCsgIdentity == o.mCsgIdentity;
    }

    public String toString() {
        return "ClosedSubscriberGroupInfo:{ mCsgIndicator = "
                + this.mCsgIndicator
                + " mHomeNodebName = "
                + this.mHomeNodebName
                + " mCsgIdentity = "
                + this.mCsgIdentity;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int type) {
        dest.writeBoolean(this.mCsgIndicator);
        dest.writeString(this.mHomeNodebName);
        dest.writeInt(this.mCsgIdentity);
    }

    private ClosedSubscriberGroupInfo(Parcel in) {
        this(in.readBoolean(), in.readString(), in.readInt());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    protected static ClosedSubscriberGroupInfo createFromParcelBody(Parcel in) {
        return new ClosedSubscriberGroupInfo(in);
    }
}
