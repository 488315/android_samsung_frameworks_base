package android.app;

import android.graphics.drawable.Icon;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.NtpTrustedTime;

import com.android.internal.os.BackgroundThread;

import com.samsung.android.server.notification.NotificationHistoryImageProvider;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes.dex */
public final class NotificationHistory implements Parcelable {
    public static final Parcelable.Creator<NotificationHistory> CREATOR =
            new Parcelable.Creator<
                    NotificationHistory>() { // from class: android.app.NotificationHistory.2
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public NotificationHistory createFromParcel(Parcel source) {
                    return new NotificationHistory(source);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public NotificationHistory[] newArray(int size) {
                    return new NotificationHistory[size];
                }
            };
    private int mHistoryCount;
    private int mIndex;
    private List<HistoricalNotification> mNotificationsToWrite;
    private Parcel mParcel;
    private String[] mStringPool;
    private Set<String> mStringsToWrite;

    public static final class HistoricalNotification {
        private String mChannelId;
        private String mChannelName;
        private String mConversationId;
        private String mExtraTitle;
        private Icon mIcon;
        private boolean mIsChecked;
        private String mPackage;
        private long mPostedTimeMs;
        private String mSbnKey;
        private String mText;
        private String mTitle;
        private int mType;
        private int mUid;
        private Uri mUri;
        private int mUserId;
        private long mWhen;

        private HistoricalNotification() {}

        public String getPackage() {
            return this.mPackage;
        }

        public String getChannelName() {
            return this.mChannelName;
        }

        public String getChannelId() {
            return this.mChannelId;
        }

        public int getUid() {
            return this.mUid;
        }

        public int getUserId() {
            return this.mUserId;
        }

        public long getPostedTimeMs() {
            return this.mPostedTimeMs;
        }

        public String getTitle() {
            return this.mTitle;
        }

        public String getText() {
            return this.mText;
        }

        public Icon getIcon() {
            return this.mIcon;
        }

        public String getKey() {
            return this.mPackage
                    + NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER
                    + this.mUid
                    + NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER
                    + this.mPostedTimeMs;
        }

        public String getConversationId() {
            return this.mConversationId;
        }

        public String getSbnKey() {
            return this.mSbnKey;
        }

        public int getType() {
            return this.mType;
        }

        public boolean getChecked() {
            return this.mIsChecked;
        }

        public void setChecked(boolean checked) {
            this.mIsChecked = checked;
        }

        public Uri getUri() {
            return this.mUri;
        }

        public long getWhen() {
            return this.mWhen;
        }

        public String getExtraTitle() {
            return this.mExtraTitle;
        }

        public String toString() {
            SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss.SSSZ");
            String fixedName = (String) TextUtils.trimToLengthWithEllipsis(this.mChannelName, 6);
            String fixedId = (String) TextUtils.trimToLengthWithEllipsis(this.mChannelId, 6);
            return "HistoricalNotification{, key ="
                    + this.mSbnKey
                    + ", type ="
                    + this.mType
                    + ", mPostedTimeMs="
                    + this.mPostedTimeMs
                    + NavigationBarInflaterView.KEY_CODE_START
                    + dayTime.format(new Date(this.mPostedTimeMs))
                    + "), mIsChecked ="
                    + this.mIsChecked
                    + ", mUri ="
                    + (this.mUri != null ? this.mUri.toString() : null)
                    + ", mWhen="
                    + this.mWhen
                    + NavigationBarInflaterView.KEY_CODE_START
                    + dayTime.format(new Date(this.mWhen))
                    + "), mExtraTitle = "
                    + this.mExtraTitle
                    + ", mChannelName='"
                    + fixedName
                    + DateFormat.QUOTE
                    + ", mChannelId='"
                    + fixedId
                    + DateFormat.QUOTE
                    + ", mUserId="
                    + this.mUserId
                    + ", mUid="
                    + this.mUid
                    + ", mIcon="
                    + this.mIcon
                    + ", mConversationId="
                    + this.mConversationId
                    + '}';
        }

        public boolean equals(Object o) {
            boolean iconsAreSame;
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            HistoricalNotification that = (HistoricalNotification) o;
            if ((getIcon() == null && that.getIcon() == null)
                    || (getIcon() != null
                            && that.getIcon() != null
                            && getIcon().sameAs(that.getIcon()))) {
                iconsAreSame = true;
            } else {
                iconsAreSame = false;
            }
            if (getUid() == that.getUid()
                    && getUserId() == that.getUserId()
                    && getPostedTimeMs() == that.getPostedTimeMs()
                    && Objects.equals(getPackage(), that.getPackage())
                    && Objects.equals(getChannelName(), that.getChannelName())
                    && Objects.equals(getChannelId(), that.getChannelId())
                    && Objects.equals(getTitle(), that.getTitle())
                    && Objects.equals(getText(), that.getText())
                    && Objects.equals(getConversationId(), that.getConversationId())
                    && iconsAreSame) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(
                    getPackage(),
                    getChannelName(),
                    getChannelId(),
                    Integer.valueOf(getUid()),
                    Integer.valueOf(getUserId()),
                    Long.valueOf(getPostedTimeMs()),
                    getTitle(),
                    getText(),
                    getIcon(),
                    getConversationId());
        }

        public static final class Builder {
            private String mChannelId;
            private String mChannelName;
            private String mConversationId;
            private String mExtraTitle;
            private Icon mIcon;
            private boolean mIsChecked;
            private String mPackage;
            private long mPostedTimeMs;
            private String mSbnKey;
            private String mText;
            private String mTitle;
            private int mType;
            private int mUid;
            private Uri mUri;
            private int mUserId;
            private long mWhen;

            public Builder setPackage(String aPackage) {
                this.mPackage = aPackage;
                return this;
            }

            public Builder setChannelName(String channelName) {
                this.mChannelName = channelName;
                return this;
            }

            public Builder setChannelId(String channelId) {
                this.mChannelId = channelId;
                return this;
            }

            public Builder setUid(int uid) {
                this.mUid = uid;
                return this;
            }

            public Builder setUserId(int userId) {
                this.mUserId = userId;
                return this;
            }

            public Builder setPostedTimeMs(long postedTimeMs) {
                this.mPostedTimeMs = postedTimeMs;
                return this;
            }

            public Builder setTitle(String title) {
                this.mTitle = title;
                return this;
            }

            public Builder setText(String text) {
                this.mText = text;
                return this;
            }

            public Builder setIcon(Icon icon) {
                this.mIcon = icon;
                return this;
            }

            public Builder setConversationId(String conversationId) {
                this.mConversationId = conversationId;
                return this;
            }

            public Builder setSbnKey(String sbnKey) {
                this.mSbnKey = sbnKey;
                return this;
            }

            public Builder setType(int type) {
                this.mType = type;
                return this;
            }

            public Builder setChecked(boolean checked) {
                this.mIsChecked = checked;
                return this;
            }

            public Builder setUri(Uri uri) {
                this.mUri = uri;
                return this;
            }

            public Builder setWhen(long when) {
                this.mWhen = when;
                return this;
            }

            public Builder setExtraTitle(String extraTitle) {
                this.mExtraTitle = extraTitle;
                return this;
            }

            public HistoricalNotification build() {
                HistoricalNotification n = new HistoricalNotification();
                n.mPackage = this.mPackage;
                n.mChannelName = this.mChannelName;
                n.mChannelId = this.mChannelId;
                n.mUid = this.mUid;
                n.mUserId = this.mUserId;
                n.mPostedTimeMs = this.mPostedTimeMs;
                n.mTitle = this.mTitle;
                n.mText = this.mText;
                n.mIcon = this.mIcon;
                n.mConversationId = this.mConversationId;
                n.mSbnKey = this.mSbnKey;
                n.mType = this.mType;
                n.mIsChecked = this.mIsChecked;
                n.mUri = this.mUri;
                n.mWhen = this.mWhen;
                n.mExtraTitle = this.mExtraTitle;
                return n;
            }
        }
    }

    private NotificationHistory(Parcel in) {
        this.mNotificationsToWrite = new ArrayList();
        this.mStringsToWrite = new HashSet();
        this.mParcel = null;
        this.mIndex = 0;
        byte[] bytes = in.readBlob();
        Parcel data = Parcel.obtain();
        data.unmarshall(bytes, 0, bytes.length);
        data.setDataPosition(0);
        this.mHistoryCount = data.readInt();
        this.mIndex = data.readInt();
        if (this.mHistoryCount > 0) {
            this.mStringPool = data.createStringArray();
            int listByteLength = data.readInt();
            int positionInParcel = data.readInt();
            this.mParcel = Parcel.obtain();
            this.mParcel.setDataPosition(0);
            this.mParcel.appendFrom(data, data.dataPosition(), listByteLength);
            this.mParcel.setDataSize(this.mParcel.dataPosition());
            this.mParcel.setDataPosition(positionInParcel);
        }
    }

    public NotificationHistory() {
        this.mNotificationsToWrite = new ArrayList();
        this.mStringsToWrite = new HashSet();
        this.mParcel = null;
        this.mIndex = 0;
        this.mHistoryCount = 0;
    }

    public boolean hasNextNotification() {
        return this.mIndex < this.mHistoryCount;
    }

    public HistoricalNotification getNextNotification() {
        if (!hasNextNotification()) {
            return null;
        }
        HistoricalNotification n = readNotificationFromParcel(this.mParcel);
        this.mIndex++;
        if (!hasNextNotification()) {
            this.mParcel.recycle();
            this.mParcel = null;
        }
        return n;
    }

    public boolean updateNotificationToWrite(String key, boolean isPackage) {
        boolean updated = false;
        for (int i = this.mNotificationsToWrite.size() - 1; i >= 0; i--) {
            if (isPackage) {
                if (key.equals(this.mNotificationsToWrite.get(i).getPackage())) {
                    updated = true;
                    this.mNotificationsToWrite.get(i).setChecked(true);
                }
            } else if (key.equals(this.mNotificationsToWrite.get(i).getSbnKey())) {
                updated = true;
                this.mNotificationsToWrite.get(i).setChecked(true);
            }
        }
        return updated;
    }

    public void addNotificationsToWriteForPkgName(
            NotificationHistory notificationHistory, String pkgName) {
        for (HistoricalNotification hn : notificationHistory.getNotificationsToWrite()) {
            if (pkgName.equals(hn.getPackage())) {
                addNotificationToWrite(hn);
            }
        }
        Collections.sort(
                this.mNotificationsToWrite,
                new Comparator() { // from class:
                    // android.app.NotificationHistory$$ExternalSyntheticLambda3
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        return NotificationHistory.lambda$addNotificationsToWriteForPkgName$0(
                                (NotificationHistory.HistoricalNotification) obj,
                                (NotificationHistory.HistoricalNotification) obj2);
                    }
                });
        poolStringsFromNotifications();
    }

    static /* synthetic */ int lambda$addNotificationsToWriteForPkgName$0(
            HistoricalNotification o1, HistoricalNotification o2) {
        return Long.compare(o1.getPostedTimeMs(), o2.getPostedTimeMs()) * (-1);
    }

    public void addNotificationsToWrite(
            NotificationHistory notificationHistory, String sbnkey, int maxNotifications) {
        for (HistoricalNotification hn : notificationHistory.getNotificationsToWrite()) {
            if (sbnkey.equals(hn.getSbnKey())) {
                addNotificationToWrite(hn);
            }
        }
        Collections.sort(
                this.mNotificationsToWrite,
                new Comparator() { // from class:
                    // android.app.NotificationHistory$$ExternalSyntheticLambda1
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        return NotificationHistory.lambda$addNotificationsToWrite$1(
                                (NotificationHistory.HistoricalNotification) obj,
                                (NotificationHistory.HistoricalNotification) obj2);
                    }
                });
        poolStringsFromNotifications();
    }

    static /* synthetic */ int lambda$addNotificationsToWrite$1(
            HistoricalNotification o1, HistoricalNotification o2) {
        return Long.compare(o1.getPostedTimeMs(), o2.getPostedTimeMs()) * (-1);
    }

    public boolean removeImageNotificationsFromWrite(String sbnkey, String text, Uri uri) {
        boolean removed = false;
        int i = 0;
        while (true) {
            if (i >= this.mNotificationsToWrite.size()) {
                break;
            }
            boolean sameUri = false;
            final HistoricalNotification hn = this.mNotificationsToWrite.get(i);
            if (uri != null && hn.getUri() != null && uri.equals(hn.getUri())) {
                sameUri = true;
            }
            if (!sbnkey.equals(hn.getSbnKey()) || !text.equals(hn.getText()) || sameUri) {
                i++;
            } else {
                removed = true;
                this.mNotificationsToWrite.remove(i);
                if (hn.getUri() != null) {
                    BackgroundThread.getHandler()
                            .postDelayed(
                                    new Runnable() { // from class:
                                        // android.app.NotificationHistory.1
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            NotificationHistoryImageProvider.getInstance()
                                                    .deleteRows(hn.getUri().toString());
                                        }
                                    },
                                    500L);
                }
            }
        }
        if (removed) {
            poolStringsFromNotifications();
        }
        return removed;
    }

    public void addNotificationsForDump(
            NotificationHistory notificationHistory, String packageName, int maxNotifications) {
        for (HistoricalNotification hn : notificationHistory.getNotificationsToWrite()) {
            if (packageName.equals(hn.getPackage())) {
                addNotificationToWrite(hn);
            }
        }
        Collections.sort(
                this.mNotificationsToWrite,
                new Comparator() { // from class:
                    // android.app.NotificationHistory$$ExternalSyntheticLambda0
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        return NotificationHistory.lambda$addNotificationsForDump$2(
                                (NotificationHistory.HistoricalNotification) obj,
                                (NotificationHistory.HistoricalNotification) obj2);
                    }
                });
        poolStringsFromNotifications();
    }

    static /* synthetic */ int lambda$addNotificationsForDump$2(
            HistoricalNotification o1, HistoricalNotification o2) {
        return Long.compare(o1.getPostedTimeMs(), o2.getPostedTimeMs()) * (-1);
    }

    public void addPooledStrings(List<String> strings) {
        this.mStringsToWrite.addAll(strings);
    }

    public void poolStringsFromNotifications() {
        this.mStringsToWrite.clear();
        for (int i = 0; i < this.mNotificationsToWrite.size(); i++) {
            HistoricalNotification notification = this.mNotificationsToWrite.get(i);
            this.mStringsToWrite.add(notification.getPackage());
            this.mStringsToWrite.add(notification.getChannelName());
            this.mStringsToWrite.add(notification.getChannelId());
            if (!TextUtils.isEmpty(notification.getConversationId())) {
                this.mStringsToWrite.add(notification.getConversationId());
            }
        }
    }

    public void addNotificationToWrite(HistoricalNotification notification) {
        if (notification == null) {
            return;
        }
        this.mNotificationsToWrite.add(notification);
        this.mHistoryCount++;
    }

    public void addNewNotificationToWrite(HistoricalNotification notification) {
        if (notification == null) {
            return;
        }
        this.mNotificationsToWrite.add(0, notification);
        this.mHistoryCount++;
    }

    public void addNotificationsToWrite(NotificationHistory notificationHistory) {
        for (HistoricalNotification hn : notificationHistory.getNotificationsToWrite()) {
            addNotificationToWrite(hn);
        }
        Collections.sort(
                this.mNotificationsToWrite,
                new Comparator() { // from class:
                    // android.app.NotificationHistory$$ExternalSyntheticLambda2
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        return NotificationHistory.lambda$addNotificationsToWrite$3(
                                (NotificationHistory.HistoricalNotification) obj,
                                (NotificationHistory.HistoricalNotification) obj2);
                    }
                });
        poolStringsFromNotifications();
    }

    static /* synthetic */ int lambda$addNotificationsToWrite$3(
            HistoricalNotification o1, HistoricalNotification o2) {
        return Long.compare(o1.getPostedTimeMs(), o2.getPostedTimeMs()) * (-1);
    }

    public void removeNotificationsFromWrite(String packageName) {
        for (int i = this.mNotificationsToWrite.size() - 1; i >= 0; i--) {
            if (packageName.equals(this.mNotificationsToWrite.get(i).getPackage())) {
                this.mNotificationsToWrite.remove(i);
            }
        }
        poolStringsFromNotifications();
    }

    public boolean removeNotificationFromWrite(String packageName, long postedTime) {
        boolean removed = false;
        for (int i = this.mNotificationsToWrite.size() - 1; i >= 0; i--) {
            HistoricalNotification hn = this.mNotificationsToWrite.get(i);
            if (packageName.equals(hn.getPackage()) && postedTime == hn.getPostedTimeMs()) {
                removed = true;
                this.mNotificationsToWrite.remove(i);
            }
        }
        if (removed) {
            poolStringsFromNotifications();
        }
        return removed;
    }

    public boolean removeConversationsFromWrite(String packageName, Set<String> conversationIds) {
        boolean removed = false;
        for (int i = this.mNotificationsToWrite.size() - 1; i >= 0; i--) {
            HistoricalNotification hn = this.mNotificationsToWrite.get(i);
            if (packageName.equals(hn.getPackage())
                    && hn.getConversationId() != null
                    && conversationIds.contains(hn.getConversationId())) {
                removed = true;
                this.mNotificationsToWrite.remove(i);
            }
        }
        if (removed) {
            poolStringsFromNotifications();
        }
        return removed;
    }

    public boolean removeChannelFromWrite(String packageName, String channelId) {
        boolean removed = false;
        for (int i = this.mNotificationsToWrite.size() - 1; i >= 0; i--) {
            HistoricalNotification hn = this.mNotificationsToWrite.get(i);
            if (packageName.equals(hn.getPackage())
                    && Objects.equals(channelId, hn.getChannelId())) {
                removed = true;
                this.mNotificationsToWrite.remove(i);
            }
        }
        if (removed) {
            poolStringsFromNotifications();
        }
        return removed;
    }

    public String[] getPooledStringsToWrite() {
        String[] stringsToWrite = (String[]) this.mStringsToWrite.toArray(new String[0]);
        Arrays.sort(stringsToWrite);
        return stringsToWrite;
    }

    public List<HistoricalNotification> getNotificationsToWrite() {
        return this.mNotificationsToWrite;
    }

    public int getHistoryCount() {
        return this.mHistoryCount;
    }

    private int findStringIndex(String str) {
        int index = Arrays.binarySearch(this.mStringPool, str);
        if (index < 0) {
            throw new IllegalStateException("String '" + str + "' is not in the string pool");
        }
        return index;
    }

    private void writeNotificationToParcel(
            HistoricalNotification notification, Parcel p, int flags) {
        int packageIndex;
        int channelNameIndex;
        int channelIdIndex;
        int conversationIdIndex;
        if (notification.mPackage != null) {
            packageIndex = findStringIndex(notification.mPackage);
        } else {
            packageIndex = -1;
        }
        if (notification.getChannelName() != null) {
            channelNameIndex = findStringIndex(notification.getChannelName());
        } else {
            channelNameIndex = -1;
        }
        if (notification.getChannelId() != null) {
            channelIdIndex = findStringIndex(notification.getChannelId());
        } else {
            channelIdIndex = -1;
        }
        if (!TextUtils.isEmpty(notification.getConversationId())) {
            conversationIdIndex = findStringIndex(notification.getConversationId());
        } else {
            conversationIdIndex = -1;
        }
        p.writeInt(packageIndex);
        p.writeInt(channelNameIndex);
        p.writeInt(channelIdIndex);
        p.writeInt(conversationIdIndex);
        p.writeInt(notification.getUid());
        p.writeInt(notification.getUserId());
        p.writeLong(notification.getPostedTimeMs());
        p.writeString(notification.getTitle());
        p.writeString(notification.getText());
        p.writeString(notification.getSbnKey());
        p.writeInt(notification.getType());
        p.writeBoolean(notification.getChecked());
        if (notification.getUri() != null) {
            p.writeString(notification.getUri().toString());
        } else {
            p.writeString(null);
        }
        p.writeLong(notification.getWhen());
        p.writeString(notification.getExtraTitle());
        p.writeBoolean(false);
    }

    private HistoricalNotification readNotificationFromParcel(Parcel p) {
        HistoricalNotification.Builder notificationOut = new HistoricalNotification.Builder();
        int packageIndex = p.readInt();
        if (packageIndex >= 0) {
            notificationOut.mPackage = this.mStringPool[packageIndex];
        } else {
            notificationOut.mPackage = null;
        }
        int channelNameIndex = p.readInt();
        if (channelNameIndex >= 0) {
            notificationOut.setChannelName(this.mStringPool[channelNameIndex]);
        } else {
            notificationOut.setChannelName(null);
        }
        int channelIdIndex = p.readInt();
        if (channelIdIndex >= 0) {
            notificationOut.setChannelId(this.mStringPool[channelIdIndex]);
        } else {
            notificationOut.setChannelId(null);
        }
        int conversationIdIndex = p.readInt();
        if (conversationIdIndex >= 0) {
            notificationOut.setConversationId(this.mStringPool[conversationIdIndex]);
        } else {
            notificationOut.setConversationId(null);
        }
        notificationOut.setUid(p.readInt());
        notificationOut.setUserId(p.readInt());
        notificationOut.setPostedTimeMs(p.readLong());
        notificationOut.setTitle(p.readString());
        notificationOut.setText(p.readString());
        notificationOut.setSbnKey(p.readString());
        notificationOut.setType(p.readInt());
        notificationOut.setChecked(p.readBoolean());
        String s = p.readString();
        notificationOut.setUri(s != null ? Uri.parse(s) : null);
        notificationOut.setWhen(p.readLong());
        notificationOut.setExtraTitle(p.readString());
        if (p.readBoolean()) {
            notificationOut.setIcon(Icon.CREATOR.createFromParcel(p));
        }
        return notificationOut.build();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        Parcel p = Parcel.obtain();
        p.writeInt(this.mHistoryCount);
        p.writeInt(this.mIndex);
        if (this.mHistoryCount > 0) {
            this.mStringPool = getPooledStringsToWrite();
            p.writeStringArray(this.mStringPool);
            if (!this.mNotificationsToWrite.isEmpty()) {
                p = Parcel.obtain();
                try {
                    p.setDataPosition(0);
                    for (int i = 0; i < this.mHistoryCount; i++) {
                        HistoricalNotification notification = this.mNotificationsToWrite.get(i);
                        writeNotificationToParcel(notification, p, flags);
                    }
                    int listByteLength = p.dataPosition();
                    p.writeInt(listByteLength);
                    p.writeInt(0);
                    p.appendFrom(p, 0, listByteLength);
                    p.recycle();
                } finally {
                    p.recycle();
                }
            } else if (this.mParcel != null) {
                p.writeInt(this.mParcel.dataSize());
                p.writeInt(this.mParcel.dataPosition());
                p.appendFrom(this.mParcel, 0, this.mParcel.dataSize());
            } else {
                throw new IllegalStateException(
                        "Either mParcel or mNotificationsToWrite must not be null");
            }
        }
        dest.writeBlob(p.marshall());
    }
}
