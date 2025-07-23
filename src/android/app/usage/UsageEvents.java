package android.app.usage;

import android.annotation.SystemApi;
import android.content.res.Configuration;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.util.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes.dex */
public final class UsageEvents implements Parcelable {
    public static final Parcelable.Creator<UsageEvents> CREATOR = new Parcelable.Creator<UsageEvents>() { // from class: android.app.usage.UsageEvents.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UsageEvents createFromParcel(Parcel parcel) {
            return new UsageEvents(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UsageEvents[] newArray(int i) {
            return new UsageEvents[i];
        }
    };
    public static final int HIDE_LOCUS_EVENTS = 8;
    public static final int HIDE_SHORTCUT_EVENTS = 2;
    public static final String INSTANT_APP_CLASS_NAME = "android.instant_class";
    public static final String INSTANT_APP_PACKAGE_NAME = "android.instant_app";
    public static final String OBFUSCATED_NOTIFICATION_CHANNEL_ID = "unknown_channel_id";
    public static final int OBFUSCATE_INSTANT_APPS = 1;
    public static final int OBFUSCATE_NOTIFICATION_EVENTS = 4;
    public static final int SHOW_ALL_EVENT_DATA = 0;
    private static final String TAG = "UsageEvents";
    private int mEventCount;
    private List<Event> mEventsToWrite;
    private final boolean mIncludeTaskRoots;
    private int mIndex;
    private Parcel mParcel;
    private String[] mStringPool;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static final class Event {
        public static final int ACTIVITY_DESTROYED = 24;
        public static final int ACTIVITY_PAUSED = 2;
        public static final int ACTIVITY_RESUMED = 1;
        public static final int ACTIVITY_STOPPED = 23;
        public static final int APP_COMPONENT_USED = 31;
        public static final int CHOOSER_ACTION = 9;
        public static final int CONFIGURATION_CHANGE = 5;
        public static final int CONTINUE_PREVIOUS_DAY = 4;
        public static final int CONTINUING_FOREGROUND_SERVICE = 21;
        public static final String DEVICE_EVENT_PACKAGE_NAME = "android";
        public static final int DEVICE_SHUTDOWN = 26;
        public static final int DEVICE_STARTUP = 27;
        public static final int END_OF_DAY = 3;
        public static final int FLAG_IS_PACKAGE_INSTANT_APP = 1;
        public static final int FLUSH_TO_DISK = 25;
        public static final int FOREGROUND_SERVICE_START = 19;
        public static final int FOREGROUND_SERVICE_STOP = 20;
        public static final int KEYGUARD_HIDDEN = 18;
        public static final int KEYGUARD_SHOWN = 17;
        public static final int LOCUS_ID_SET = 30;
        public static final int MAX_EVENT_TYPE = 31;

        @Deprecated
        public static final int MOVE_TO_BACKGROUND = 2;

        @Deprecated
        public static final int MOVE_TO_FOREGROUND = 1;
        public static final int NONE = 0;

        @SystemApi
        public static final int NOTIFICATION_INTERRUPTION = 12;

        @SystemApi
        public static final int NOTIFICATION_SEEN = 10;
        public static final int ROLLOVER_FOREGROUND_SERVICE = 22;
        public static final int SCREEN_INTERACTIVE = 15;
        public static final int SCREEN_NON_INTERACTIVE = 16;
        public static final int SHORTCUT_INVOCATION = 8;

        @SystemApi
        public static final int SLICE_PINNED = 14;

        @SystemApi
        public static final int SLICE_PINNED_PRIV = 13;
        public static final int STANDBY_BUCKET_CHANGED = 11;

        @SystemApi
        public static final int SYSTEM_INTERACTION = 6;
        private static final int UNASSIGNED_TOKEN = -1;
        public static final int USER_INTERACTION = 7;
        public static final int USER_STOPPED = 29;
        public static final int USER_UNLOCKED = 28;
        public static final int VALID_FLAG_BITS = 1;
        public String mAction;
        public int mBucketAndReason;
        public String mClass;
        public Configuration mConfiguration;
        public String[] mContentAnnotations;
        public String mContentType;
        public int mEventType;
        public int mFlags;
        public long mInitTimeStamp;
        public int mInstanceId;
        public String mLocusId;
        public String mNotificationChannelId;
        public String mPackage;
        public String mShortcutId;
        public String mTaskRootClass;
        public String mTaskRootPackage;
        public long mTimeStamp;
        public int mPackageToken = -1;
        public int mClassToken = -1;
        public int mTaskRootPackageToken = -1;
        public int mTaskRootClassToken = -1;
        public int mShortcutIdToken = -1;
        public int mNotificationChannelIdToken = -1;
        public int mLocusIdToken = -1;
        public PersistableBundle mExtras = null;
        public UserInteractionEventExtrasToken mUserInteractionExtrasToken = null;

        @Retention(RetentionPolicy.SOURCE)
        public @interface EventFlags {
        }

        @Retention(RetentionPolicy.SOURCE)
        public @interface EventType {
        }

        public static class UserInteractionEventExtrasToken {
            public int mCategoryToken = -1;
            public int mActionToken = -1;
        }

        public Event() {
        }

        public Event(int i, long j) {
            this.mEventType = i;
            this.mTimeStamp = j;
            this.mInitTimeStamp = j;
        }

        public Event(Event event) {
            copyFrom(event);
        }

        public String getPackageName() {
            return this.mPackage;
        }

        @SystemApi
        public boolean isInstantApp() {
            return (this.mFlags & 1) == 1;
        }

        public String getClassName() {
            return this.mClass;
        }

        @SystemApi
        public int getInstanceId() {
            return this.mInstanceId;
        }

        @SystemApi
        public String getTaskRootPackageName() {
            return this.mTaskRootPackage;
        }

        @SystemApi
        public String getTaskRootClassName() {
            return this.mTaskRootClass;
        }

        public long getTimeStamp() {
            return this.mTimeStamp;
        }

        public int getEventType() {
            return this.mEventType;
        }

        public PersistableBundle getExtras() {
            PersistableBundle persistableBundle = this.mExtras;
            return persistableBundle == null ? PersistableBundle.EMPTY : persistableBundle;
        }

        public Configuration getConfiguration() {
            return this.mConfiguration;
        }

        public String getShortcutId() {
            return this.mShortcutId;
        }

        public int getAppStandbyBucket() {
            return (this.mBucketAndReason & (-65536)) >>> 16;
        }

        public int getStandbyReason() {
            return this.mBucketAndReason & 65535;
        }

        @SystemApi
        public String getNotificationChannelId() {
            return this.mNotificationChannelId;
        }

        public Event getObfuscatedIfInstantApp() {
            if (!isInstantApp()) {
                return this;
            }
            Event event = new Event(this);
            event.mPackage = UsageEvents.INSTANT_APP_PACKAGE_NAME;
            event.mClass = UsageEvents.INSTANT_APP_CLASS_NAME;
            return event;
        }

        public Event getObfuscatedNotificationEvent() {
            Event event = new Event(this);
            event.mNotificationChannelId = UsageEvents.OBFUSCATED_NOTIFICATION_CHANNEL_ID;
            return event;
        }

        public String getLocusId() {
            return this.mLocusId;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void copyFrom(Event event) {
            this.mPackage = event.mPackage;
            this.mClass = event.mClass;
            this.mInstanceId = event.mInstanceId;
            this.mTaskRootPackage = event.mTaskRootPackage;
            this.mTaskRootClass = event.mTaskRootClass;
            this.mTimeStamp = event.mTimeStamp;
            this.mEventType = event.mEventType;
            this.mConfiguration = event.mConfiguration;
            this.mShortcutId = event.mShortcutId;
            this.mAction = event.mAction;
            this.mContentType = event.mContentType;
            this.mContentAnnotations = event.mContentAnnotations;
            this.mFlags = event.mFlags;
            this.mBucketAndReason = event.mBucketAndReason;
            this.mNotificationChannelId = event.mNotificationChannelId;
            this.mLocusId = event.mLocusId;
            this.mExtras = event.mExtras;
        }
    }

    public UsageEvents(Parcel parcel) {
        this.mEventsToWrite = null;
        this.mParcel = null;
        this.mIndex = 0;
        if (Flags.useParceledList()) {
            readUsageEventsFromParcelWithParceledList(parcel);
        } else {
            readUsageEventsFromParcelWithBlob(parcel);
        }
        this.mIncludeTaskRoots = true;
    }

    private void readUsageEventsFromParcelWithParceledList(Parcel parcel) {
        this.mEventCount = parcel.readInt();
        this.mIndex = parcel.readInt();
        ParcelableUsageEventList parcelableUsageEventList = (ParcelableUsageEventList) parcel.readParcelable(getClass().getClassLoader(), ParcelableUsageEventList.class);
        if (parcelableUsageEventList != null) {
            this.mEventsToWrite = parcelableUsageEventList.getList();
        } else {
            this.mEventsToWrite = new ArrayList();
        }
        if (this.mEventCount != this.mEventsToWrite.size()) {
            Log.w(TAG, "Partial usage event list received: " + this.mEventCount + " != " + this.mEventsToWrite.size());
            this.mEventCount = this.mEventsToWrite.size();
        }
    }

    private void readUsageEventsFromParcelWithBlob(Parcel parcel) {
        byte[] readBlob = parcel.readBlob();
        Parcel obtain = Parcel.obtain();
        obtain.unmarshall(readBlob, 0, readBlob.length);
        obtain.setDataPosition(0);
        this.mEventCount = obtain.readInt();
        this.mIndex = obtain.readInt();
        if (this.mEventCount > 0) {
            this.mStringPool = obtain.createStringArray();
            int readInt = obtain.readInt();
            int readInt2 = obtain.readInt();
            Parcel obtain2 = Parcel.obtain();
            this.mParcel = obtain2;
            obtain2.setDataPosition(0);
            this.mParcel.appendFrom(obtain, obtain.dataPosition(), readInt);
            Parcel parcel2 = this.mParcel;
            parcel2.setDataSize(parcel2.dataPosition());
            this.mParcel.setDataPosition(readInt2);
        }
    }

    UsageEvents() {
        this.mEventsToWrite = null;
        this.mParcel = null;
        this.mIndex = 0;
        this.mEventCount = 0;
        this.mIncludeTaskRoots = true;
    }

    public UsageEvents(List<Event> list, String[] strArr) {
        this(list, strArr, false);
    }

    public UsageEvents(List<Event> list, String[] strArr, boolean z) {
        this.mEventsToWrite = null;
        this.mParcel = null;
        this.mIndex = 0;
        this.mStringPool = strArr;
        this.mEventCount = list.size();
        this.mEventsToWrite = list;
        this.mIncludeTaskRoots = z;
    }

    public boolean hasNextEvent() {
        return this.mIndex < this.mEventCount;
    }

    public boolean getNextEvent(Event event) {
        Parcel parcel;
        if (event == null) {
            throw new IllegalArgumentException("Given eventOut must not be null");
        }
        if (this.mIndex >= this.mEventCount) {
            return false;
        }
        if (Flags.useParceledList()) {
            return getNextEventFromParceledList(event);
        }
        Parcel parcel2 = this.mParcel;
        if (parcel2 != null) {
            readEventFromParcel(parcel2, event);
        } else {
            event.copyFrom(this.mEventsToWrite.get(this.mIndex));
        }
        int i = this.mIndex + 1;
        this.mIndex = i;
        if (i >= this.mEventCount && (parcel = this.mParcel) != null) {
            parcel.recycle();
            this.mParcel = null;
        }
        return true;
    }

    private boolean getNextEventFromParceledList(Event event) {
        event.copyFrom(this.mEventsToWrite.get(this.mIndex));
        this.mIndex++;
        return true;
    }

    public void resetToStart() {
        this.mIndex = 0;
        Parcel parcel = this.mParcel;
        if (parcel != null) {
            parcel.setDataPosition(0);
        }
    }

    private int findStringIndex(String str) {
        int binarySearch = Arrays.binarySearch(this.mStringPool, str);
        if (binarySearch >= 0) {
            return binarySearch;
        }
        throw new IllegalStateException("String '" + str + "' is not in the string pool");
    }

    private void writeEventToParcel(Event event, Parcel parcel, int i) {
        int i2 = -1;
        int findStringIndex = event.mPackage != null ? findStringIndex(event.mPackage) : -1;
        int findStringIndex2 = event.mClass != null ? findStringIndex(event.mClass) : -1;
        int findStringIndex3 = (!this.mIncludeTaskRoots || event.mTaskRootPackage == null) ? -1 : findStringIndex(event.mTaskRootPackage);
        if (this.mIncludeTaskRoots && event.mTaskRootClass != null) {
            i2 = findStringIndex(event.mTaskRootClass);
        }
        parcel.writeInt(findStringIndex);
        parcel.writeInt(findStringIndex2);
        parcel.writeInt(event.mInstanceId);
        parcel.writeInt(findStringIndex3);
        parcel.writeInt(i2);
        parcel.writeInt(event.mEventType);
        parcel.writeLong(event.mTimeStamp);
        int i3 = event.mEventType;
        if (i3 == 5) {
            event.mConfiguration.writeToParcel(parcel, i);
        } else if (i3 == 30) {
            parcel.writeString(event.mLocusId);
        } else if (i3 != 7) {
            if (i3 == 8) {
                parcel.writeString(event.mShortcutId);
            } else if (i3 == 9) {
                parcel.writeString(event.mAction);
                parcel.writeString(event.mContentType);
                parcel.writeStringArray(event.mContentAnnotations);
            } else if (i3 == 11) {
                parcel.writeInt(event.mBucketAndReason);
            } else if (i3 == 12) {
                parcel.writeString(event.mNotificationChannelId);
            }
        } else if (event.mExtras != null) {
            parcel.writeInt(1);
            parcel.writePersistableBundle(event.mExtras);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(event.mFlags);
    }

    private void readEventFromParcel(Parcel parcel, Event event) {
        int readInt = parcel.readInt();
        if (readInt >= 0) {
            event.mPackage = this.mStringPool[readInt];
        } else {
            event.mPackage = null;
        }
        int readInt2 = parcel.readInt();
        if (readInt2 >= 0) {
            event.mClass = this.mStringPool[readInt2];
        } else {
            event.mClass = null;
        }
        event.mInstanceId = parcel.readInt();
        int readInt3 = parcel.readInt();
        if (readInt3 >= 0) {
            event.mTaskRootPackage = this.mStringPool[readInt3];
        } else {
            event.mTaskRootPackage = null;
        }
        int readInt4 = parcel.readInt();
        if (readInt4 >= 0) {
            event.mTaskRootClass = this.mStringPool[readInt4];
        } else {
            event.mTaskRootClass = null;
        }
        event.mEventType = parcel.readInt();
        event.mTimeStamp = parcel.readLong();
        event.mConfiguration = null;
        event.mShortcutId = null;
        event.mAction = null;
        event.mContentType = null;
        event.mContentAnnotations = null;
        event.mNotificationChannelId = null;
        event.mLocusId = null;
        event.mExtras = null;
        int i = event.mEventType;
        if (i == 5) {
            event.mConfiguration = Configuration.CREATOR.createFromParcel(parcel);
        } else if (i == 30) {
            event.mLocusId = parcel.readString();
        } else if (i != 7) {
            if (i == 8) {
                event.mShortcutId = parcel.readString();
            } else if (i == 9) {
                event.mAction = parcel.readString();
                event.mContentType = parcel.readString();
                event.mContentAnnotations = parcel.readStringArray();
            } else if (i == 11) {
                event.mBucketAndReason = parcel.readInt();
            } else if (i == 12) {
                event.mNotificationChannelId = parcel.readString();
            }
        } else if (parcel.readInt() != 0) {
            event.mExtras = parcel.readPersistableBundle(getClass().getClassLoader());
        }
        event.mFlags = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        if (Flags.useParceledList()) {
            writeUsageEventsToParcelWithParceledList(parcel, i);
        } else {
            writeUsageEventsToParcelWithBlob(parcel, i);
        }
    }

    private void writeUsageEventsToParcelWithParceledList(Parcel parcel, int i) {
        parcel.writeInt(this.mEventCount);
        parcel.writeInt(this.mIndex);
        parcel.writeParcelable(new ParcelableUsageEventList(this.mEventsToWrite), i);
    }

    private void writeUsageEventsToParcelWithBlob(Parcel parcel, int i) {
        Parcel obtain = Parcel.obtain();
        obtain.writeInt(this.mEventCount);
        obtain.writeInt(this.mIndex);
        if (this.mEventCount > 0) {
            obtain.writeStringArray(this.mStringPool);
            if (this.mEventsToWrite != null) {
                obtain = Parcel.obtain();
                try {
                    obtain.setDataPosition(0);
                    for (int i2 = 0; i2 < this.mEventCount; i2++) {
                        writeEventToParcel(this.mEventsToWrite.get(i2), obtain, i);
                    }
                    int dataPosition = obtain.dataPosition();
                    obtain.writeInt(dataPosition);
                    obtain.writeInt(0);
                    obtain.appendFrom(obtain, 0, dataPosition);
                    obtain.recycle();
                } finally {
                    obtain.recycle();
                }
            } else {
                Parcel parcel2 = this.mParcel;
                if (parcel2 != null) {
                    obtain.writeInt(parcel2.dataSize());
                    obtain.writeInt(this.mParcel.dataPosition());
                    Parcel parcel3 = this.mParcel;
                    obtain.appendFrom(parcel3, 0, parcel3.dataSize());
                } else {
                    throw new IllegalStateException("Either mParcel or mEventsToWrite must not be null");
                }
            }
        }
        parcel.writeBlob(obtain.marshall());
    }
}
