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
    public static final Parcelable.Creator<UsageEvents> CREATOR =
            new Parcelable.Creator<UsageEvents>() { // from class: android.app.usage.UsageEvents.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public UsageEvents createFromParcel(Parcel source) {
                    return new UsageEvents(source);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public UsageEvents[] newArray(int size) {
                    return new UsageEvents[size];
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

        @Deprecated public static final int MOVE_TO_BACKGROUND = 2;

        @Deprecated public static final int MOVE_TO_FOREGROUND = 1;
        public static final int NONE = 0;

        @SystemApi public static final int NOTIFICATION_INTERRUPTION = 12;

        @SystemApi public static final int NOTIFICATION_SEEN = 10;
        public static final int ROLLOVER_FOREGROUND_SERVICE = 22;
        public static final int SCREEN_INTERACTIVE = 15;
        public static final int SCREEN_NON_INTERACTIVE = 16;
        public static final int SHORTCUT_INVOCATION = 8;

        @SystemApi public static final int SLICE_PINNED = 14;

        @SystemApi public static final int SLICE_PINNED_PRIV = 13;
        public static final int STANDBY_BUCKET_CHANGED = 11;

        @SystemApi public static final int SYSTEM_INTERACTION = 6;
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
        public @interface EventFlags {}

        @Retention(RetentionPolicy.SOURCE)
        public @interface EventType {}

        public static class UserInteractionEventExtrasToken {
            public int mCategoryToken = -1;
            public int mActionToken = -1;
        }

        public Event() {}

        public Event(int type, long timeStamp) {
            this.mEventType = type;
            this.mTimeStamp = timeStamp;
            this.mInitTimeStamp = timeStamp;
        }

        public Event(Event orig) {
            copyFrom(orig);
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
            return this.mExtras == null ? PersistableBundle.EMPTY : this.mExtras;
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
            Event ret = new Event(this);
            ret.mPackage = UsageEvents.INSTANT_APP_PACKAGE_NAME;
            ret.mClass = UsageEvents.INSTANT_APP_CLASS_NAME;
            return ret;
        }

        public Event getObfuscatedNotificationEvent() {
            Event ret = new Event(this);
            ret.mNotificationChannelId = UsageEvents.OBFUSCATED_NOTIFICATION_CHANNEL_ID;
            return ret;
        }

        public String getLocusId() {
            return this.mLocusId;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void copyFrom(Event orig) {
            this.mPackage = orig.mPackage;
            this.mClass = orig.mClass;
            this.mInstanceId = orig.mInstanceId;
            this.mTaskRootPackage = orig.mTaskRootPackage;
            this.mTaskRootClass = orig.mTaskRootClass;
            this.mTimeStamp = orig.mTimeStamp;
            this.mEventType = orig.mEventType;
            this.mConfiguration = orig.mConfiguration;
            this.mShortcutId = orig.mShortcutId;
            this.mAction = orig.mAction;
            this.mContentType = orig.mContentType;
            this.mContentAnnotations = orig.mContentAnnotations;
            this.mFlags = orig.mFlags;
            this.mBucketAndReason = orig.mBucketAndReason;
            this.mNotificationChannelId = orig.mNotificationChannelId;
            this.mLocusId = orig.mLocusId;
            this.mExtras = orig.mExtras;
        }
    }

    public UsageEvents(Parcel in) {
        this.mEventsToWrite = null;
        this.mParcel = null;
        this.mIndex = 0;
        if (Flags.useParceledList()) {
            readUsageEventsFromParcelWithParceledList(in);
        } else {
            readUsageEventsFromParcelWithBlob(in);
        }
        this.mIncludeTaskRoots = true;
    }

    private void readUsageEventsFromParcelWithParceledList(Parcel in) {
        this.mEventCount = in.readInt();
        this.mIndex = in.readInt();
        ParcelableUsageEventList slice =
                (ParcelableUsageEventList)
                        in.readParcelable(
                                getClass().getClassLoader(), ParcelableUsageEventList.class);
        if (slice != null) {
            this.mEventsToWrite = slice.getList();
        } else {
            this.mEventsToWrite = new ArrayList();
        }
        if (this.mEventCount != this.mEventsToWrite.size()) {
            Log.w(
                    TAG,
                    "Partial usage event list received: "
                            + this.mEventCount
                            + " != "
                            + this.mEventsToWrite.size());
            this.mEventCount = this.mEventsToWrite.size();
        }
    }

    private void readUsageEventsFromParcelWithBlob(Parcel in) {
        byte[] bytes = in.readBlob();
        Parcel data = Parcel.obtain();
        data.unmarshall(bytes, 0, bytes.length);
        data.setDataPosition(0);
        this.mEventCount = data.readInt();
        this.mIndex = data.readInt();
        if (this.mEventCount > 0) {
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

    UsageEvents() {
        this.mEventsToWrite = null;
        this.mParcel = null;
        this.mIndex = 0;
        this.mEventCount = 0;
        this.mIncludeTaskRoots = true;
    }

    public UsageEvents(List<Event> events, String[] stringPool) {
        this(events, stringPool, false);
    }

    public UsageEvents(List<Event> events, String[] stringPool, boolean includeTaskRoots) {
        this.mEventsToWrite = null;
        this.mParcel = null;
        this.mIndex = 0;
        this.mStringPool = stringPool;
        this.mEventCount = events.size();
        this.mEventsToWrite = events;
        this.mIncludeTaskRoots = includeTaskRoots;
    }

    public boolean hasNextEvent() {
        return this.mIndex < this.mEventCount;
    }

    public boolean getNextEvent(Event eventOut) {
        if (eventOut == null) {
            throw new IllegalArgumentException("Given eventOut must not be null");
        }
        if (this.mIndex >= this.mEventCount) {
            return false;
        }
        if (Flags.useParceledList()) {
            return getNextEventFromParceledList(eventOut);
        }
        if (this.mParcel != null) {
            readEventFromParcel(this.mParcel, eventOut);
        } else {
            eventOut.copyFrom(this.mEventsToWrite.get(this.mIndex));
        }
        this.mIndex++;
        if (this.mIndex >= this.mEventCount && this.mParcel != null) {
            this.mParcel.recycle();
            this.mParcel = null;
        }
        return true;
    }

    private boolean getNextEventFromParceledList(Event eventOut) {
        eventOut.copyFrom(this.mEventsToWrite.get(this.mIndex));
        this.mIndex++;
        return true;
    }

    public void resetToStart() {
        this.mIndex = 0;
        if (this.mParcel != null) {
            this.mParcel.setDataPosition(0);
        }
    }

    private int findStringIndex(String str) {
        int index = Arrays.binarySearch(this.mStringPool, str);
        if (index < 0) {
            throw new IllegalStateException("String '" + str + "' is not in the string pool");
        }
        return index;
    }

    private void writeEventToParcel(Event event, Parcel p, int flags) {
        int packageIndex;
        int classIndex;
        int taskRootPackageIndex;
        int taskRootClassIndex;
        if (event.mPackage != null) {
            packageIndex = findStringIndex(event.mPackage);
        } else {
            packageIndex = -1;
        }
        if (event.mClass != null) {
            classIndex = findStringIndex(event.mClass);
        } else {
            classIndex = -1;
        }
        if (this.mIncludeTaskRoots && event.mTaskRootPackage != null) {
            taskRootPackageIndex = findStringIndex(event.mTaskRootPackage);
        } else {
            taskRootPackageIndex = -1;
        }
        if (this.mIncludeTaskRoots && event.mTaskRootClass != null) {
            taskRootClassIndex = findStringIndex(event.mTaskRootClass);
        } else {
            taskRootClassIndex = -1;
        }
        p.writeInt(packageIndex);
        p.writeInt(classIndex);
        p.writeInt(event.mInstanceId);
        p.writeInt(taskRootPackageIndex);
        p.writeInt(taskRootClassIndex);
        p.writeInt(event.mEventType);
        p.writeLong(event.mTimeStamp);
        switch (event.mEventType) {
            case 5:
                event.mConfiguration.writeToParcel(p, flags);
                break;
            case 7:
                if (event.mExtras != null) {
                    p.writeInt(1);
                    p.writePersistableBundle(event.mExtras);
                    break;
                } else {
                    p.writeInt(0);
                    break;
                }
            case 8:
                p.writeString(event.mShortcutId);
                break;
            case 9:
                p.writeString(event.mAction);
                p.writeString(event.mContentType);
                p.writeStringArray(event.mContentAnnotations);
                break;
            case 11:
                p.writeInt(event.mBucketAndReason);
                break;
            case 12:
                p.writeString(event.mNotificationChannelId);
                break;
            case 30:
                p.writeString(event.mLocusId);
                break;
        }
        p.writeInt(event.mFlags);
    }

    private void readEventFromParcel(Parcel p, Event eventOut) {
        int packageIndex = p.readInt();
        if (packageIndex >= 0) {
            eventOut.mPackage = this.mStringPool[packageIndex];
        } else {
            eventOut.mPackage = null;
        }
        int classIndex = p.readInt();
        if (classIndex >= 0) {
            eventOut.mClass = this.mStringPool[classIndex];
        } else {
            eventOut.mClass = null;
        }
        eventOut.mInstanceId = p.readInt();
        int taskRootPackageIndex = p.readInt();
        if (taskRootPackageIndex >= 0) {
            eventOut.mTaskRootPackage = this.mStringPool[taskRootPackageIndex];
        } else {
            eventOut.mTaskRootPackage = null;
        }
        int taskRootClassIndex = p.readInt();
        if (taskRootClassIndex >= 0) {
            eventOut.mTaskRootClass = this.mStringPool[taskRootClassIndex];
        } else {
            eventOut.mTaskRootClass = null;
        }
        eventOut.mEventType = p.readInt();
        eventOut.mTimeStamp = p.readLong();
        eventOut.mConfiguration = null;
        eventOut.mShortcutId = null;
        eventOut.mAction = null;
        eventOut.mContentType = null;
        eventOut.mContentAnnotations = null;
        eventOut.mNotificationChannelId = null;
        eventOut.mLocusId = null;
        eventOut.mExtras = null;
        switch (eventOut.mEventType) {
            case 5:
                eventOut.mConfiguration = Configuration.CREATOR.createFromParcel(p);
                break;
            case 7:
                if (p.readInt() != 0) {
                    eventOut.mExtras = p.readPersistableBundle(getClass().getClassLoader());
                    break;
                }
                break;
            case 8:
                eventOut.mShortcutId = p.readString();
                break;
            case 9:
                eventOut.mAction = p.readString();
                eventOut.mContentType = p.readString();
                eventOut.mContentAnnotations = p.readStringArray();
                break;
            case 11:
                eventOut.mBucketAndReason = p.readInt();
                break;
            case 12:
                eventOut.mNotificationChannelId = p.readString();
                break;
            case 30:
                eventOut.mLocusId = p.readString();
                break;
        }
        eventOut.mFlags = p.readInt();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        if (Flags.useParceledList()) {
            writeUsageEventsToParcelWithParceledList(dest, flags);
        } else {
            writeUsageEventsToParcelWithBlob(dest, flags);
        }
    }

    private void writeUsageEventsToParcelWithParceledList(Parcel dest, int flags) {
        dest.writeInt(this.mEventCount);
        dest.writeInt(this.mIndex);
        dest.writeParcelable(new ParcelableUsageEventList(this.mEventsToWrite), flags);
    }

    private void writeUsageEventsToParcelWithBlob(Parcel dest, int flags) {
        Parcel p = Parcel.obtain();
        p.writeInt(this.mEventCount);
        p.writeInt(this.mIndex);
        if (this.mEventCount > 0) {
            p.writeStringArray(this.mStringPool);
            if (this.mEventsToWrite != null) {
                p = Parcel.obtain();
                try {
                    p.setDataPosition(0);
                    for (int i = 0; i < this.mEventCount; i++) {
                        Event event = this.mEventsToWrite.get(i);
                        writeEventToParcel(event, p, flags);
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
                        "Either mParcel or mEventsToWrite must not be null");
            }
        }
        dest.writeBlob(p.marshall());
    }
}
