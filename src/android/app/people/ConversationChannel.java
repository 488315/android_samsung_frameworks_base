package android.app.people;

import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.content.pm.ShortcutInfo;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public final class ConversationChannel implements Parcelable {
    public static final Parcelable.Creator<ConversationChannel> CREATOR = new Parcelable.Creator<ConversationChannel>() { // from class: android.app.people.ConversationChannel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ConversationChannel createFromParcel(Parcel parcel) {
            return new ConversationChannel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ConversationChannel[] newArray(int i) {
            return new ConversationChannel[i];
        }
    };
    private boolean mHasActiveNotifications;
    private boolean mHasBirthdayToday;
    private long mLastEventTimestamp;
    private NotificationChannel mNotificationChannel;
    private NotificationChannelGroup mNotificationChannelGroup;
    private ShortcutInfo mShortcutInfo;
    private List<ConversationStatus> mStatuses;
    private int mUid;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ConversationChannel(ShortcutInfo shortcutInfo, int i, NotificationChannel notificationChannel, NotificationChannelGroup notificationChannelGroup, long j, boolean z) {
        this.mShortcutInfo = shortcutInfo;
        this.mUid = i;
        this.mNotificationChannel = notificationChannel;
        this.mNotificationChannelGroup = notificationChannelGroup;
        this.mLastEventTimestamp = j;
        this.mHasActiveNotifications = z;
    }

    public ConversationChannel(ShortcutInfo shortcutInfo, int i, NotificationChannel notificationChannel, NotificationChannelGroup notificationChannelGroup, long j, boolean z, boolean z2, List<ConversationStatus> list) {
        this.mShortcutInfo = shortcutInfo;
        this.mUid = i;
        this.mNotificationChannel = notificationChannel;
        this.mNotificationChannelGroup = notificationChannelGroup;
        this.mLastEventTimestamp = j;
        this.mHasActiveNotifications = z;
        this.mHasBirthdayToday = z2;
        this.mStatuses = list;
    }

    public ConversationChannel(Parcel parcel) {
        this.mShortcutInfo = (ShortcutInfo) parcel.readParcelable(ShortcutInfo.class.getClassLoader(), ShortcutInfo.class);
        this.mUid = parcel.readInt();
        this.mNotificationChannel = (NotificationChannel) parcel.readParcelable(NotificationChannel.class.getClassLoader(), NotificationChannel.class);
        this.mNotificationChannelGroup = (NotificationChannelGroup) parcel.readParcelable(NotificationChannelGroup.class.getClassLoader(), NotificationChannelGroup.class);
        this.mLastEventTimestamp = parcel.readLong();
        this.mHasActiveNotifications = parcel.readBoolean();
        this.mHasBirthdayToday = parcel.readBoolean();
        ArrayList arrayList = new ArrayList();
        this.mStatuses = arrayList;
        parcel.readParcelableList(arrayList, ConversationStatus.class.getClassLoader(), ConversationStatus.class);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.mShortcutInfo, i);
        parcel.writeInt(this.mUid);
        parcel.writeParcelable(this.mNotificationChannel, i);
        parcel.writeParcelable(this.mNotificationChannelGroup, i);
        parcel.writeLong(this.mLastEventTimestamp);
        parcel.writeBoolean(this.mHasActiveNotifications);
        parcel.writeBoolean(this.mHasBirthdayToday);
        parcel.writeParcelableList(this.mStatuses, i);
    }

    public ShortcutInfo getShortcutInfo() {
        return this.mShortcutInfo;
    }

    public int getUid() {
        return this.mUid;
    }

    public NotificationChannel getNotificationChannel() {
        return this.mNotificationChannel;
    }

    public NotificationChannelGroup getNotificationChannelGroup() {
        return this.mNotificationChannelGroup;
    }

    public long getLastEventTimestamp() {
        return this.mLastEventTimestamp;
    }

    public boolean hasActiveNotifications() {
        return this.mHasActiveNotifications;
    }

    public boolean hasBirthdayToday() {
        return this.mHasBirthdayToday;
    }

    public List<ConversationStatus> getStatuses() {
        return this.mStatuses;
    }

    public String toString() {
        return "ConversationChannel{mShortcutInfo=" + this.mShortcutInfo + ", mUid=" + this.mUid + ", mNotificationChannel=" + this.mNotificationChannel + ", mNotificationChannelGroup=" + this.mNotificationChannelGroup + ", mLastEventTimestamp=" + this.mLastEventTimestamp + ", mHasActiveNotifications=" + this.mHasActiveNotifications + ", mHasBirthdayToday=" + this.mHasBirthdayToday + ", mStatuses=" + this.mStatuses + '}';
    }
}
