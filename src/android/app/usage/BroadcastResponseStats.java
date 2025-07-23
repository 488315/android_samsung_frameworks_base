package android.app.usage;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

@SystemApi
/* loaded from: classes.dex */
public final class BroadcastResponseStats implements Parcelable {
    public static final Parcelable.Creator<BroadcastResponseStats> CREATOR = new Parcelable.Creator<BroadcastResponseStats>() { // from class: android.app.usage.BroadcastResponseStats.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BroadcastResponseStats createFromParcel(Parcel parcel) {
            return new BroadcastResponseStats(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BroadcastResponseStats[] newArray(int i) {
            return new BroadcastResponseStats[i];
        }
    };
    private int mBroadcastsDispatchedCount;
    private final long mId;
    private int mNotificationsCancelledCount;
    private int mNotificationsPostedCount;
    private int mNotificationsUpdatedCount;
    private final String mPackageName;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public BroadcastResponseStats(String str, long j) {
        this.mPackageName = str;
        this.mId = j;
    }

    private BroadcastResponseStats(Parcel parcel) {
        this.mPackageName = parcel.readString8();
        this.mId = parcel.readLong();
        this.mBroadcastsDispatchedCount = parcel.readInt();
        this.mNotificationsPostedCount = parcel.readInt();
        this.mNotificationsUpdatedCount = parcel.readInt();
        this.mNotificationsCancelledCount = parcel.readInt();
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public long getId() {
        return this.mId;
    }

    public int getBroadcastsDispatchedCount() {
        return this.mBroadcastsDispatchedCount;
    }

    public int getNotificationsPostedCount() {
        return this.mNotificationsPostedCount;
    }

    public int getNotificationsUpdatedCount() {
        return this.mNotificationsUpdatedCount;
    }

    public int getNotificationsCancelledCount() {
        return this.mNotificationsCancelledCount;
    }

    public void incrementBroadcastsDispatchedCount(int i) {
        this.mBroadcastsDispatchedCount += i;
    }

    public void incrementNotificationsPostedCount(int i) {
        this.mNotificationsPostedCount += i;
    }

    public void incrementNotificationsUpdatedCount(int i) {
        this.mNotificationsUpdatedCount += i;
    }

    public void incrementNotificationsCancelledCount(int i) {
        this.mNotificationsCancelledCount += i;
    }

    public void addCounts(BroadcastResponseStats broadcastResponseStats) {
        incrementBroadcastsDispatchedCount(broadcastResponseStats.getBroadcastsDispatchedCount());
        incrementNotificationsPostedCount(broadcastResponseStats.getNotificationsPostedCount());
        incrementNotificationsUpdatedCount(broadcastResponseStats.getNotificationsUpdatedCount());
        incrementNotificationsCancelledCount(broadcastResponseStats.getNotificationsCancelledCount());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && (obj instanceof BroadcastResponseStats)) {
            BroadcastResponseStats broadcastResponseStats = (BroadcastResponseStats) obj;
            if (this.mBroadcastsDispatchedCount == broadcastResponseStats.mBroadcastsDispatchedCount && this.mNotificationsPostedCount == broadcastResponseStats.mNotificationsPostedCount && this.mNotificationsUpdatedCount == broadcastResponseStats.mNotificationsUpdatedCount && this.mNotificationsCancelledCount == broadcastResponseStats.mNotificationsCancelledCount && this.mId == broadcastResponseStats.mId && this.mPackageName.equals(broadcastResponseStats.mPackageName)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.mPackageName, Long.valueOf(this.mId), Integer.valueOf(this.mBroadcastsDispatchedCount), Integer.valueOf(this.mNotificationsPostedCount), Integer.valueOf(this.mNotificationsUpdatedCount), Integer.valueOf(this.mNotificationsCancelledCount));
    }

    public String toString() {
        return "stats {package=" + this.mPackageName + ",id=" + this.mId + ",broadcastsSent=" + this.mBroadcastsDispatchedCount + ",notificationsPosted=" + this.mNotificationsPostedCount + ",notificationsUpdated=" + this.mNotificationsUpdatedCount + ",notificationsCancelled=" + this.mNotificationsCancelledCount + "}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString8(this.mPackageName);
        parcel.writeLong(this.mId);
        parcel.writeInt(this.mBroadcastsDispatchedCount);
        parcel.writeInt(this.mNotificationsPostedCount);
        parcel.writeInt(this.mNotificationsUpdatedCount);
        parcel.writeInt(this.mNotificationsCancelledCount);
    }
}
