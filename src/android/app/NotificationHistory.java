package android.app;

import android.app.NotificationHistory;
import android.graphics.drawable.Icon;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
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
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes.dex */
public final class NotificationHistory implements Parcelable {
    public static final Parcelable.Creator<NotificationHistory> CREATOR = new Parcelable.Creator<NotificationHistory>() { // from class: android.app.NotificationHistory.2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NotificationHistory createFromParcel(Parcel parcel) {
            return new NotificationHistory(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NotificationHistory[] newArray(int i) {
            return new NotificationHistory[i];
        }
    };
    private int mHistoryCount;
    private int mIndex;
    private List<HistoricalNotification> mNotificationsToWrite;
    private Parcel mParcel;
    private String[] mStringPool;
    private Set<String> mStringsToWrite;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

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

        private HistoricalNotification() {
        }

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
            return this.mPackage + NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER + this.mUid + NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER + this.mPostedTimeMs;
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

        public void setChecked(boolean z) {
            this.mIsChecked = z;
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
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss.SSSZ");
            String str = (String) TextUtils.trimToLengthWithEllipsis(this.mChannelName, 6);
            String str2 = (String) TextUtils.trimToLengthWithEllipsis(this.mChannelId, 6);
            StringBuilder sb = new StringBuilder("HistoricalNotification{, key =");
            sb.append(this.mSbnKey);
            sb.append(", type =");
            sb.append(this.mType);
            sb.append(", mPostedTimeMs=");
            sb.append(this.mPostedTimeMs);
            sb.append(NavigationBarInflaterView.KEY_CODE_START);
            sb.append(simpleDateFormat.format(new Date(this.mPostedTimeMs)));
            sb.append("), mIsChecked =");
            sb.append(this.mIsChecked);
            sb.append(", mUri =");
            Uri uri = this.mUri;
            sb.append(uri != null ? uri.toString() : null);
            sb.append(", mWhen=");
            sb.append(this.mWhen);
            sb.append(NavigationBarInflaterView.KEY_CODE_START);
            sb.append(simpleDateFormat.format(new Date(this.mWhen)));
            sb.append("), mExtraTitle = ");
            sb.append(this.mExtraTitle);
            sb.append(", mChannelName='");
            sb.append(str);
            sb.append("', mChannelId='");
            sb.append(str2);
            sb.append("', mUserId=");
            sb.append(this.mUserId);
            sb.append(", mUid=");
            sb.append(this.mUid);
            sb.append(", mIcon=");
            sb.append(this.mIcon);
            sb.append(", mConversationId=");
            sb.append(this.mConversationId);
            sb.append('}');
            return sb.toString();
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && getClass() == obj.getClass()) {
                HistoricalNotification historicalNotification = (HistoricalNotification) obj;
                boolean z = (getIcon() == null && historicalNotification.getIcon() == null) || !(getIcon() == null || historicalNotification.getIcon() == null || !getIcon().sameAs(historicalNotification.getIcon()));
                if (getUid() == historicalNotification.getUid() && getUserId() == historicalNotification.getUserId() && getPostedTimeMs() == historicalNotification.getPostedTimeMs() && Objects.equals(getPackage(), historicalNotification.getPackage()) && Objects.equals(getChannelName(), historicalNotification.getChannelName()) && Objects.equals(getChannelId(), historicalNotification.getChannelId()) && Objects.equals(getTitle(), historicalNotification.getTitle()) && Objects.equals(getText(), historicalNotification.getText()) && Objects.equals(getConversationId(), historicalNotification.getConversationId()) && z) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(getPackage(), getChannelName(), getChannelId(), Integer.valueOf(getUid()), Integer.valueOf(getUserId()), Long.valueOf(getPostedTimeMs()), getTitle(), getText(), getIcon(), getConversationId());
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

            public Builder setPackage(String str) {
                this.mPackage = str;
                return this;
            }

            public Builder setChannelName(String str) {
                this.mChannelName = str;
                return this;
            }

            public Builder setChannelId(String str) {
                this.mChannelId = str;
                return this;
            }

            public Builder setUid(int i) {
                this.mUid = i;
                return this;
            }

            public Builder setUserId(int i) {
                this.mUserId = i;
                return this;
            }

            public Builder setPostedTimeMs(long j) {
                this.mPostedTimeMs = j;
                return this;
            }

            public Builder setTitle(String str) {
                this.mTitle = str;
                return this;
            }

            public Builder setText(String str) {
                this.mText = str;
                return this;
            }

            public Builder setIcon(Icon icon) {
                this.mIcon = icon;
                return this;
            }

            public Builder setConversationId(String str) {
                this.mConversationId = str;
                return this;
            }

            public Builder setSbnKey(String str) {
                this.mSbnKey = str;
                return this;
            }

            public Builder setType(int i) {
                this.mType = i;
                return this;
            }

            public Builder setChecked(boolean z) {
                this.mIsChecked = z;
                return this;
            }

            public Builder setUri(Uri uri) {
                this.mUri = uri;
                return this;
            }

            public Builder setWhen(long j) {
                this.mWhen = j;
                return this;
            }

            public Builder setExtraTitle(String str) {
                this.mExtraTitle = str;
                return this;
            }

            public HistoricalNotification build() {
                HistoricalNotification historicalNotification = new HistoricalNotification();
                historicalNotification.mPackage = this.mPackage;
                historicalNotification.mChannelName = this.mChannelName;
                historicalNotification.mChannelId = this.mChannelId;
                historicalNotification.mUid = this.mUid;
                historicalNotification.mUserId = this.mUserId;
                historicalNotification.mPostedTimeMs = this.mPostedTimeMs;
                historicalNotification.mTitle = this.mTitle;
                historicalNotification.mText = this.mText;
                historicalNotification.mIcon = this.mIcon;
                historicalNotification.mConversationId = this.mConversationId;
                historicalNotification.mSbnKey = this.mSbnKey;
                historicalNotification.mType = this.mType;
                historicalNotification.mIsChecked = this.mIsChecked;
                historicalNotification.mUri = this.mUri;
                historicalNotification.mWhen = this.mWhen;
                historicalNotification.mExtraTitle = this.mExtraTitle;
                return historicalNotification;
            }
        }
    }

    private NotificationHistory(Parcel parcel) {
        this.mNotificationsToWrite = new ArrayList();
        this.mStringsToWrite = new HashSet();
        this.mParcel = null;
        this.mIndex = 0;
        byte[] blob = parcel.readBlob();
        Parcel parcelObtain = Parcel.obtain();
        parcelObtain.unmarshall(blob, 0, blob.length);
        parcelObtain.setDataPosition(0);
        this.mHistoryCount = parcelObtain.readInt();
        this.mIndex = parcelObtain.readInt();
        if (this.mHistoryCount > 0) {
            this.mStringPool = parcelObtain.createStringArray();
            int i = parcelObtain.readInt();
            int i2 = parcelObtain.readInt();
            Parcel parcelObtain2 = Parcel.obtain();
            this.mParcel = parcelObtain2;
            parcelObtain2.setDataPosition(0);
            this.mParcel.appendFrom(parcelObtain, parcelObtain.dataPosition(), i);
            Parcel parcel2 = this.mParcel;
            parcel2.setDataSize(parcel2.dataPosition());
            this.mParcel.setDataPosition(i2);
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
        HistoricalNotification notificationFromParcel = readNotificationFromParcel(this.mParcel);
        this.mIndex++;
        if (!hasNextNotification()) {
            this.mParcel.recycle();
            this.mParcel = null;
        }
        return notificationFromParcel;
    }

    public boolean updateNotificationToWrite(String str, boolean z) {
        boolean z2 = false;
        for (int size = this.mNotificationsToWrite.size() - 1; size >= 0; size--) {
            if (z) {
                if (str.equals(this.mNotificationsToWrite.get(size).getPackage())) {
                    this.mNotificationsToWrite.get(size).setChecked(true);
                    z2 = true;
                }
            } else if (str.equals(this.mNotificationsToWrite.get(size).getSbnKey())) {
                this.mNotificationsToWrite.get(size).setChecked(true);
                z2 = true;
            }
        }
        return z2;
    }

    public void addNotificationsToWriteForPkgName(NotificationHistory notificationHistory, String str, int i) {
        int i2 = 0;
        for (HistoricalNotification historicalNotification : notificationHistory.getNotificationsToWrite()) {
            if (str.equals(historicalNotification.getPackage()) && historicalNotification.getTitle().equals(historicalNotification.getExtraTitle())) {
                addNotificationToWrite(historicalNotification);
                i2++;
                if (i2 == i) {
                    break;
                }
            }
        }
        Collections.sort(this.mNotificationsToWrite, new Comparator() { // from class: android.app.NotificationHistory$$ExternalSyntheticLambda2
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return NotificationHistory.lambda$addNotificationsToWriteForPkgName$0((NotificationHistory.HistoricalNotification) obj, (NotificationHistory.HistoricalNotification) obj2);
            }
        });
        poolStringsFromNotifications();
    }

    static /* synthetic */ int lambda$addNotificationsToWriteForPkgName$0(HistoricalNotification historicalNotification, HistoricalNotification historicalNotification2) {
        return Long.compare(historicalNotification.getPostedTimeMs(), historicalNotification2.getPostedTimeMs()) * (-1);
    }

    public void addNotificationsToWrite(NotificationHistory notificationHistory, String str, int i) {
        int i2 = 0;
        for (HistoricalNotification historicalNotification : notificationHistory.getNotificationsToWrite()) {
            if (str.equals(historicalNotification.getSbnKey())) {
                addNotificationToWrite(historicalNotification);
                i2++;
                if (i2 == i) {
                    break;
                }
            }
        }
        Collections.sort(this.mNotificationsToWrite, new Comparator() { // from class: android.app.NotificationHistory$$ExternalSyntheticLambda1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return NotificationHistory.lambda$addNotificationsToWrite$1((NotificationHistory.HistoricalNotification) obj, (NotificationHistory.HistoricalNotification) obj2);
            }
        });
        poolStringsFromNotifications();
    }

    static /* synthetic */ int lambda$addNotificationsToWrite$1(HistoricalNotification historicalNotification, HistoricalNotification historicalNotification2) {
        return Long.compare(historicalNotification.getPostedTimeMs(), historicalNotification2.getPostedTimeMs()) * (-1);
    }

    public boolean removeImageNotificationsFromWrite(String str, String str2, Uri uri) {
        boolean z = false;
        int i = 0;
        while (true) {
            if (i >= this.mNotificationsToWrite.size()) {
                break;
            }
            final HistoricalNotification historicalNotification = this.mNotificationsToWrite.get(i);
            boolean z2 = (uri == null || historicalNotification.getUri() == null || !uri.equals(historicalNotification.getUri())) ? false : true;
            if (str.equals(historicalNotification.getSbnKey()) && str2.equals(historicalNotification.getText()) && !z2) {
                this.mNotificationsToWrite.remove(i);
                if (historicalNotification.getUri() != null) {
                    BackgroundThread.getHandler().postDelayed(new Runnable(this) { // from class: android.app.NotificationHistory.1
                        @Override // java.lang.Runnable
                        public void run() {
                            NotificationHistoryImageProvider.getInstance().deleteRows(historicalNotification.getUri().toString());
                        }
                    }, 500L);
                }
                z = true;
            } else {
                i++;
            }
        }
        if (z) {
            poolStringsFromNotifications();
        }
        return z;
    }

    public void addNotificationsForDump(NotificationHistory notificationHistory, String str, int i) {
        for (HistoricalNotification historicalNotification : notificationHistory.getNotificationsToWrite()) {
            if (str.equals(historicalNotification.getPackage())) {
                addNotificationToWrite(historicalNotification);
            }
        }
        Collections.sort(this.mNotificationsToWrite, new Comparator() { // from class: android.app.NotificationHistory$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return NotificationHistory.lambda$addNotificationsForDump$2((NotificationHistory.HistoricalNotification) obj, (NotificationHistory.HistoricalNotification) obj2);
            }
        });
        poolStringsFromNotifications();
    }

    static /* synthetic */ int lambda$addNotificationsForDump$2(HistoricalNotification historicalNotification, HistoricalNotification historicalNotification2) {
        return Long.compare(historicalNotification.getPostedTimeMs(), historicalNotification2.getPostedTimeMs()) * (-1);
    }

    public void addPooledStrings(List<String> list) {
        this.mStringsToWrite.addAll(list);
    }

    public void poolStringsFromNotifications() {
        this.mStringsToWrite.clear();
        for (int i = 0; i < this.mNotificationsToWrite.size(); i++) {
            HistoricalNotification historicalNotification = this.mNotificationsToWrite.get(i);
            this.mStringsToWrite.add(historicalNotification.getPackage());
            this.mStringsToWrite.add(historicalNotification.getChannelName());
            this.mStringsToWrite.add(historicalNotification.getChannelId());
            if (!TextUtils.isEmpty(historicalNotification.getConversationId())) {
                this.mStringsToWrite.add(historicalNotification.getConversationId());
            }
        }
    }

    public void addNotificationToWrite(HistoricalNotification historicalNotification) {
        if (historicalNotification == null) {
            return;
        }
        this.mNotificationsToWrite.add(historicalNotification);
        this.mHistoryCount++;
    }

    public void addNewNotificationToWrite(HistoricalNotification historicalNotification) {
        if (historicalNotification == null) {
            return;
        }
        this.mNotificationsToWrite.add(0, historicalNotification);
        this.mHistoryCount++;
    }

    public void addNotificationsToWrite(NotificationHistory notificationHistory) {
        Iterator<HistoricalNotification> it = notificationHistory.getNotificationsToWrite().iterator();
        while (it.hasNext()) {
            addNotificationToWrite(it.next());
        }
        Collections.sort(this.mNotificationsToWrite, new Comparator() { // from class: android.app.NotificationHistory$$ExternalSyntheticLambda3
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return NotificationHistory.lambda$addNotificationsToWrite$3((NotificationHistory.HistoricalNotification) obj, (NotificationHistory.HistoricalNotification) obj2);
            }
        });
        poolStringsFromNotifications();
    }

    static /* synthetic */ int lambda$addNotificationsToWrite$3(HistoricalNotification historicalNotification, HistoricalNotification historicalNotification2) {
        return Long.compare(historicalNotification.getPostedTimeMs(), historicalNotification2.getPostedTimeMs()) * (-1);
    }

    public void removeNotificationsFromWrite(String str) {
        for (int size = this.mNotificationsToWrite.size() - 1; size >= 0; size--) {
            if (str.equals(this.mNotificationsToWrite.get(size).getPackage())) {
                this.mNotificationsToWrite.remove(size);
            }
        }
        poolStringsFromNotifications();
    }

    public boolean removeNotificationFromWrite(String str, long j) {
        boolean z = false;
        for (int size = this.mNotificationsToWrite.size() - 1; size >= 0; size--) {
            HistoricalNotification historicalNotification = this.mNotificationsToWrite.get(size);
            if (str.equals(historicalNotification.getPackage()) && j == historicalNotification.getPostedTimeMs()) {
                this.mNotificationsToWrite.remove(size);
                z = true;
            }
        }
        if (z) {
            poolStringsFromNotifications();
        }
        return z;
    }

    public boolean removeConversationsFromWrite(String str, Set<String> set) {
        boolean z = false;
        for (int size = this.mNotificationsToWrite.size() - 1; size >= 0; size--) {
            HistoricalNotification historicalNotification = this.mNotificationsToWrite.get(size);
            if (str.equals(historicalNotification.getPackage()) && historicalNotification.getConversationId() != null && set.contains(historicalNotification.getConversationId())) {
                this.mNotificationsToWrite.remove(size);
                z = true;
            }
        }
        if (z) {
            poolStringsFromNotifications();
        }
        return z;
    }

    public boolean removeChannelFromWrite(String str, String str2) {
        boolean z = false;
        for (int size = this.mNotificationsToWrite.size() - 1; size >= 0; size--) {
            HistoricalNotification historicalNotification = this.mNotificationsToWrite.get(size);
            if (str.equals(historicalNotification.getPackage()) && Objects.equals(str2, historicalNotification.getChannelId())) {
                this.mNotificationsToWrite.remove(size);
                z = true;
            }
        }
        if (z) {
            poolStringsFromNotifications();
        }
        return z;
    }

    public String[] getPooledStringsToWrite() {
        String[] strArr = (String[]) this.mStringsToWrite.toArray(new String[0]);
        Arrays.sort(strArr);
        return strArr;
    }

    public List<HistoricalNotification> getNotificationsToWrite() {
        return this.mNotificationsToWrite;
    }

    public int getHistoryCount() {
        return this.mHistoryCount;
    }

    private int findStringIndex(String str) {
        int iBinarySearch = Arrays.binarySearch(this.mStringPool, str);
        if (iBinarySearch >= 0) {
            return iBinarySearch;
        }
        throw new IllegalStateException("String '" + str + "' is not in the string pool");
    }

    private void writeNotificationToParcel(HistoricalNotification historicalNotification, Parcel parcel, int i) {
        int iFindStringIndex = historicalNotification.mPackage != null ? findStringIndex(historicalNotification.mPackage) : -1;
        int iFindStringIndex2 = historicalNotification.getChannelName() != null ? findStringIndex(historicalNotification.getChannelName()) : -1;
        int iFindStringIndex3 = historicalNotification.getChannelId() != null ? findStringIndex(historicalNotification.getChannelId()) : -1;
        int iFindStringIndex4 = TextUtils.isEmpty(historicalNotification.getConversationId()) ? -1 : findStringIndex(historicalNotification.getConversationId());
        parcel.writeInt(iFindStringIndex);
        parcel.writeInt(iFindStringIndex2);
        parcel.writeInt(iFindStringIndex3);
        parcel.writeInt(iFindStringIndex4);
        parcel.writeInt(historicalNotification.getUid());
        parcel.writeInt(historicalNotification.getUserId());
        parcel.writeLong(historicalNotification.getPostedTimeMs());
        parcel.writeString(historicalNotification.getTitle());
        parcel.writeString(historicalNotification.getText());
        parcel.writeString(historicalNotification.getSbnKey());
        parcel.writeInt(historicalNotification.getType());
        parcel.writeBoolean(historicalNotification.getChecked());
        if (historicalNotification.getUri() != null) {
            parcel.writeString(historicalNotification.getUri().toString());
        } else {
            parcel.writeString(null);
        }
        parcel.writeLong(historicalNotification.getWhen());
        parcel.writeString(historicalNotification.getExtraTitle());
        parcel.writeBoolean(false);
    }

    private HistoricalNotification readNotificationFromParcel(Parcel parcel) {
        HistoricalNotification.Builder builder = new HistoricalNotification.Builder();
        int i = parcel.readInt();
        if (i >= 0) {
            builder.mPackage = this.mStringPool[i];
        } else {
            builder.mPackage = null;
        }
        int i2 = parcel.readInt();
        if (i2 >= 0) {
            builder.setChannelName(this.mStringPool[i2]);
        } else {
            builder.setChannelName(null);
        }
        int i3 = parcel.readInt();
        if (i3 >= 0) {
            builder.setChannelId(this.mStringPool[i3]);
        } else {
            builder.setChannelId(null);
        }
        int i4 = parcel.readInt();
        if (i4 >= 0) {
            builder.setConversationId(this.mStringPool[i4]);
        } else {
            builder.setConversationId(null);
        }
        builder.setUid(parcel.readInt());
        builder.setUserId(parcel.readInt());
        builder.setPostedTimeMs(parcel.readLong());
        builder.setTitle(parcel.readString());
        builder.setText(parcel.readString());
        builder.setSbnKey(parcel.readString());
        builder.setType(parcel.readInt());
        builder.setChecked(parcel.readBoolean());
        String string = parcel.readString();
        builder.setUri(string != null ? Uri.parse(string) : null);
        builder.setWhen(parcel.readLong());
        builder.setExtraTitle(parcel.readString());
        if (parcel.readBoolean()) {
            builder.setIcon(Icon.CREATOR.createFromParcel(parcel));
        }
        return builder.build();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        Parcel parcelObtain = Parcel.obtain();
        parcelObtain.writeInt(this.mHistoryCount);
        parcelObtain.writeInt(this.mIndex);
        if (this.mHistoryCount > 0) {
            String[] pooledStringsToWrite = getPooledStringsToWrite();
            this.mStringPool = pooledStringsToWrite;
            parcelObtain.writeStringArray(pooledStringsToWrite);
            if (!this.mNotificationsToWrite.isEmpty()) {
                parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.setDataPosition(0);
                    for (int i2 = 0; i2 < this.mHistoryCount; i2++) {
                        writeNotificationToParcel(this.mNotificationsToWrite.get(i2), parcelObtain, i);
                    }
                    int iDataPosition = parcelObtain.dataPosition();
                    parcelObtain.writeInt(iDataPosition);
                    parcelObtain.writeInt(0);
                    parcelObtain.appendFrom(parcelObtain, 0, iDataPosition);
                    parcelObtain.recycle();
                } finally {
                    parcelObtain.recycle();
                }
            } else {
                Parcel parcel2 = this.mParcel;
                if (parcel2 != null) {
                    parcelObtain.writeInt(parcel2.dataSize());
                    parcelObtain.writeInt(this.mParcel.dataPosition());
                    Parcel parcel3 = this.mParcel;
                    parcelObtain.appendFrom(parcel3, 0, parcel3.dataSize());
                } else {
                    throw new IllegalStateException("Either mParcel or mNotificationsToWrite must not be null");
                }
            }
        }
        parcel.writeBlob(parcelObtain.marshall());
    }
}
