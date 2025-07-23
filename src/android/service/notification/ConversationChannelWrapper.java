package android.service.notification;

import android.app.NotificationChannel;
import android.content.pm.ShortcutInfo;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class ConversationChannelWrapper implements Parcelable {
    public static final Parcelable.Creator<ConversationChannelWrapper> CREATOR = new Parcelable.Creator<ConversationChannelWrapper>() { // from class: android.service.notification.ConversationChannelWrapper.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ConversationChannelWrapper createFromParcel(Parcel parcel) {
            return new ConversationChannelWrapper(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ConversationChannelWrapper[] newArray(int i) {
            return new ConversationChannelWrapper[i];
        }
    };
    private CharSequence mGroupLabel;
    private NotificationChannel mNotificationChannel;
    private CharSequence mParentChannelLabel;
    private String mPkg;
    private ShortcutInfo mShortcutInfo;
    private int mUid;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ConversationChannelWrapper() {
    }

    protected ConversationChannelWrapper(Parcel parcel) {
        this.mNotificationChannel = (NotificationChannel) parcel.readParcelable(NotificationChannel.class.getClassLoader(), NotificationChannel.class);
        this.mGroupLabel = parcel.readCharSequence();
        this.mParentChannelLabel = parcel.readCharSequence();
        this.mShortcutInfo = (ShortcutInfo) parcel.readParcelable(ShortcutInfo.class.getClassLoader(), ShortcutInfo.class);
        this.mPkg = parcel.readStringNoHelper();
        this.mUid = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.mNotificationChannel, i);
        parcel.writeCharSequence(this.mGroupLabel);
        parcel.writeCharSequence(this.mParentChannelLabel);
        parcel.writeParcelable(this.mShortcutInfo, i);
        parcel.writeStringNoHelper(this.mPkg);
        parcel.writeInt(this.mUid);
    }

    public NotificationChannel getNotificationChannel() {
        return this.mNotificationChannel;
    }

    public void setNotificationChannel(NotificationChannel notificationChannel) {
        this.mNotificationChannel = notificationChannel;
    }

    public CharSequence getGroupLabel() {
        return this.mGroupLabel;
    }

    public void setGroupLabel(CharSequence charSequence) {
        this.mGroupLabel = charSequence;
    }

    public CharSequence getParentChannelLabel() {
        return this.mParentChannelLabel;
    }

    public void setParentChannelLabel(CharSequence charSequence) {
        this.mParentChannelLabel = charSequence;
    }

    public ShortcutInfo getShortcutInfo() {
        return this.mShortcutInfo;
    }

    public void setShortcutInfo(ShortcutInfo shortcutInfo) {
        this.mShortcutInfo = shortcutInfo;
    }

    public String getPkg() {
        return this.mPkg;
    }

    public void setPkg(String str) {
        this.mPkg = str;
    }

    public int getUid() {
        return this.mUid;
    }

    public void setUid(int i) {
        this.mUid = i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            ConversationChannelWrapper conversationChannelWrapper = (ConversationChannelWrapper) obj;
            if (Objects.equals(getNotificationChannel(), conversationChannelWrapper.getNotificationChannel()) && Objects.equals(getGroupLabel(), conversationChannelWrapper.getGroupLabel()) && Objects.equals(getParentChannelLabel(), conversationChannelWrapper.getParentChannelLabel()) && Objects.equals(getShortcutInfo(), conversationChannelWrapper.getShortcutInfo()) && Objects.equals(getPkg(), conversationChannelWrapper.getPkg()) && getUid() == conversationChannelWrapper.getUid()) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(getNotificationChannel(), getGroupLabel(), getParentChannelLabel(), getShortcutInfo(), getPkg(), Integer.valueOf(getUid()));
    }
}
