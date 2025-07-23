package android.service.notification;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class NotifyingApp implements Parcelable, Comparable<NotifyingApp> {
    public static final Parcelable.Creator<NotifyingApp> CREATOR = new Parcelable.Creator<NotifyingApp>() { // from class: android.service.notification.NotifyingApp.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NotifyingApp createFromParcel(Parcel parcel) {
            return new NotifyingApp(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NotifyingApp[] newArray(int i) {
            return new NotifyingApp[i];
        }
    };
    private long mLastNotified;
    private String mPkg;
    private int mUserId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public NotifyingApp() {
    }

    protected NotifyingApp(Parcel parcel) {
        this.mUserId = parcel.readInt();
        this.mPkg = parcel.readString();
        this.mLastNotified = parcel.readLong();
    }

    public int getUserId() {
        return this.mUserId;
    }

    public NotifyingApp setUserId(int i) {
        this.mUserId = i;
        return this;
    }

    public String getPackage() {
        return this.mPkg;
    }

    public NotifyingApp setPackage(String str) {
        this.mPkg = str;
        return this;
    }

    public long getLastNotified() {
        return this.mLastNotified;
    }

    public NotifyingApp setLastNotified(long j) {
        this.mLastNotified = j;
        return this;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mUserId);
        parcel.writeString(this.mPkg);
        parcel.writeLong(this.mLastNotified);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            NotifyingApp notifyingApp = (NotifyingApp) obj;
            if (getUserId() == notifyingApp.getUserId() && getLastNotified() == notifyingApp.getLastNotified() && Objects.equals(this.mPkg, notifyingApp.mPkg)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(getUserId()), this.mPkg, Long.valueOf(getLastNotified()));
    }

    @Override // java.lang.Comparable
    public int compareTo(NotifyingApp notifyingApp) {
        if (getLastNotified() == notifyingApp.getLastNotified()) {
            if (getUserId() == notifyingApp.getUserId()) {
                return getPackage().compareTo(notifyingApp.getPackage());
            }
            return Integer.compare(getUserId(), notifyingApp.getUserId());
        }
        return -Long.compare(getLastNotified(), notifyingApp.getLastNotified());
    }

    public String toString() {
        return "NotifyingApp{mUserId=" + this.mUserId + ", mPkg='" + this.mPkg + "', mLastNotified=" + this.mLastNotified + '}';
    }
}
