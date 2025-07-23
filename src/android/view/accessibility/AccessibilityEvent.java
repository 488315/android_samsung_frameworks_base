package android.view.accessibility;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.util.BitUtils;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.IntFunction;

/* loaded from: classes4.dex */
public final class AccessibilityEvent extends AccessibilityRecord implements Parcelable {
    public static final int CONTENT_CHANGE_TYPE_CHECKED = 8192;
    public static final int CONTENT_CHANGE_TYPE_CONTENT_DESCRIPTION = 4;
    public static final int CONTENT_CHANGE_TYPE_CONTENT_INVALID = 1024;
    public static final int CONTENT_CHANGE_TYPE_DRAG_CANCELLED = 512;
    public static final int CONTENT_CHANGE_TYPE_DRAG_DROPPED = 256;
    public static final int CONTENT_CHANGE_TYPE_DRAG_STARTED = 128;
    public static final int CONTENT_CHANGE_TYPE_ENABLED = 4096;
    public static final int CONTENT_CHANGE_TYPE_ERROR = 2048;
    public static final int CONTENT_CHANGE_TYPE_EXPANDED = 16384;
    public static final int CONTENT_CHANGE_TYPE_PANE_APPEARED = 16;
    public static final int CONTENT_CHANGE_TYPE_PANE_DISAPPEARED = 32;
    public static final int CONTENT_CHANGE_TYPE_PANE_TITLE = 8;
    public static final int CONTENT_CHANGE_TYPE_STATE_DESCRIPTION = 64;
    public static final int CONTENT_CHANGE_TYPE_SUBTREE = 1;
    public static final int CONTENT_CHANGE_TYPE_SUPPLEMENTAL_DESCRIPTION = 32768;
    public static final int CONTENT_CHANGE_TYPE_TEXT = 2;
    public static final int CONTENT_CHANGE_TYPE_UNDEFINED = 0;
    public static final Parcelable.Creator<AccessibilityEvent> CREATOR;
    private static final boolean DEBUG;
    public static final boolean DEBUG_ORIGIN = false;
    public static final int INVALID_POSITION = -1;
    private static final String LOG_TAG = "AccessibilityEvent";

    @Deprecated
    public static final int MAX_TEXT_LENGTH = 500;
    public static final int SPEECH_STATE_LISTENING_END = 8;
    public static final int SPEECH_STATE_LISTENING_START = 4;
    public static final int SPEECH_STATE_SPEAKING_END = 2;
    public static final int SPEECH_STATE_SPEAKING_START = 1;
    public static final int TYPES_ALL_MASK = -1;

    @Deprecated
    public static final int TYPE_ANNOUNCEMENT = 16384;
    public static final int TYPE_ASSIST_READING_CONTEXT = 16777216;
    public static final int TYPE_GESTURE_DETECTION_END = 524288;
    public static final int TYPE_GESTURE_DETECTION_START = 262144;
    public static final int TYPE_NOTIFICATION_STATE_CHANGED = 64;
    public static final int TYPE_SPEECH_STATE_CHANGE = 33554432;
    public static final int TYPE_TOUCH_EXPLORATION_GESTURE_END = 1024;
    public static final int TYPE_TOUCH_EXPLORATION_GESTURE_START = 512;
    public static final int TYPE_TOUCH_INTERACTION_END = 2097152;
    public static final int TYPE_TOUCH_INTERACTION_START = 1048576;
    public static final int TYPE_VIEW_ACCESSIBILITY_FOCUSED = 32768;
    public static final int TYPE_VIEW_ACCESSIBILITY_FOCUS_CLEARED = 65536;
    public static final int TYPE_VIEW_CLICKED = 1;
    public static final int TYPE_VIEW_CONTEXT_CLICKED = 8388608;
    public static final int TYPE_VIEW_FOCUSED = 8;
    public static final int TYPE_VIEW_HOVER_ENTER = 128;
    public static final int TYPE_VIEW_HOVER_EXIT = 256;
    public static final int TYPE_VIEW_LONG_CLICKED = 2;
    public static final int TYPE_VIEW_SCROLLED = 4096;
    public static final int TYPE_VIEW_SELECTED = 4;
    public static final int TYPE_VIEW_TARGETED_BY_SCROLL = 67108864;
    public static final int TYPE_VIEW_TEXT_CHANGED = 16;
    public static final int TYPE_VIEW_TEXT_SELECTION_CHANGED = 8192;
    public static final int TYPE_VIEW_TEXT_TRAVERSED_AT_MOVEMENT_GRANULARITY = 131072;
    public static final int TYPE_WINDOWS_CHANGED = 4194304;
    public static final int TYPE_WINDOW_CONTENT_CHANGED = 2048;
    public static final int TYPE_WINDOW_STATE_CHANGED = 32;
    public static final int WINDOWS_CHANGE_ACCESSIBILITY_FOCUSED = 128;
    public static final int WINDOWS_CHANGE_ACTIVE = 32;
    public static final int WINDOWS_CHANGE_ADDED = 1;
    public static final int WINDOWS_CHANGE_BOUNDS = 8;
    public static final int WINDOWS_CHANGE_CHILDREN = 512;
    public static final int WINDOWS_CHANGE_FOCUSED = 64;
    public static final int WINDOWS_CHANGE_LAYER = 16;
    public static final int WINDOWS_CHANGE_PARENT = 256;
    public static final int WINDOWS_CHANGE_PIP = 1024;
    public static final int WINDOWS_CHANGE_REMOVED = 2;
    public static final int WINDOWS_CHANGE_TITLE = 4;
    int mAction;
    int mContentChangeTypes;
    private long mEventTime;
    private int mEventType;
    int mMovementGranularity;
    private CharSequence mPackageName;
    private ArrayList<AccessibilityRecord> mRecords;
    int mSpeechStateChangeTypes;
    int mWindowChangeTypes;
    public StackTraceElement[] originStackTrace = null;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ContentChangeTypes {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface EventType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SpeechStateChangeTypes {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface WindowsChangeTypes {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.view.accessibility.AccessibilityRecord
    @Deprecated
    public void recycle() {
    }

    static {
        DEBUG = Log.isLoggable(LOG_TAG, 3) && Build.IS_DEBUGGABLE;
        CREATOR = new Parcelable.Creator<AccessibilityEvent>() { // from class: android.view.accessibility.AccessibilityEvent.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AccessibilityEvent createFromParcel(Parcel parcel) {
                AccessibilityEvent accessibilityEvent = new AccessibilityEvent();
                accessibilityEvent.initFromParcel(parcel);
                return accessibilityEvent;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AccessibilityEvent[] newArray(int i) {
                return new AccessibilityEvent[i];
            }
        };
    }

    public AccessibilityEvent() {
    }

    public AccessibilityEvent(int i) {
        this.mEventType = i;
    }

    public AccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        init(accessibilityEvent);
    }

    void init(AccessibilityEvent accessibilityEvent) {
        super.init((AccessibilityRecord) accessibilityEvent);
        this.mEventType = accessibilityEvent.mEventType;
        this.mMovementGranularity = accessibilityEvent.mMovementGranularity;
        this.mAction = accessibilityEvent.mAction;
        this.mContentChangeTypes = accessibilityEvent.mContentChangeTypes;
        this.mSpeechStateChangeTypes = accessibilityEvent.mSpeechStateChangeTypes;
        this.mWindowChangeTypes = accessibilityEvent.mWindowChangeTypes;
        this.mEventTime = accessibilityEvent.mEventTime;
        this.mPackageName = accessibilityEvent.mPackageName;
        if (accessibilityEvent.mRecords != null) {
            this.mRecords = new ArrayList<>(accessibilityEvent.mRecords.size());
            Iterator<AccessibilityRecord> it = accessibilityEvent.mRecords.iterator();
            while (it.hasNext()) {
                this.mRecords.add(new AccessibilityRecord(it.next()));
            }
        }
    }

    @Override // android.view.accessibility.AccessibilityRecord
    public void setSealed(boolean z) {
        super.setSealed(z);
        ArrayList<AccessibilityRecord> arrayList = this.mRecords;
        if (arrayList != null) {
            Iterator<AccessibilityRecord> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().setSealed(z);
            }
        }
    }

    public int getRecordCount() {
        ArrayList<AccessibilityRecord> arrayList = this.mRecords;
        if (arrayList == null) {
            return 0;
        }
        return arrayList.size();
    }

    public void appendRecord(AccessibilityRecord accessibilityRecord) {
        enforceNotSealed();
        if (this.mRecords == null) {
            this.mRecords = new ArrayList<>();
        }
        this.mRecords.add(accessibilityRecord);
    }

    public AccessibilityRecord getRecord(int i) {
        ArrayList<AccessibilityRecord> arrayList = this.mRecords;
        if (arrayList == null) {
            throw new IndexOutOfBoundsException("Invalid index " + i + ", size is 0");
        }
        return arrayList.get(i);
    }

    public int getEventType() {
        return this.mEventType;
    }

    public int getContentChangeTypes() {
        return this.mContentChangeTypes;
    }

    private static String contentChangeTypesToString(int i) {
        return BitUtils.flagsToString(i, new IntFunction() { // from class: android.view.accessibility.AccessibilityEvent$$ExternalSyntheticLambda2
            @Override // java.util.function.IntFunction
            public final Object apply(int i2) {
                String singleContentChangeTypeToString;
                singleContentChangeTypeToString = AccessibilityEvent.singleContentChangeTypeToString(i2);
                return singleContentChangeTypeToString;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String singleContentChangeTypeToString(int i) {
        if (i == 0) {
            return "CONTENT_CHANGE_TYPE_UNDEFINED";
        }
        if (i == 1) {
            return "CONTENT_CHANGE_TYPE_SUBTREE";
        }
        if (i != 2) {
            switch (i) {
                case 4:
                    return "CONTENT_CHANGE_TYPE_CONTENT_DESCRIPTION";
                case 8:
                    return "CONTENT_CHANGE_TYPE_PANE_TITLE";
                case 16:
                    return "CONTENT_CHANGE_TYPE_PANE_APPEARED";
                case 32:
                    return "CONTENT_CHANGE_TYPE_PANE_DISAPPEARED";
                case 64:
                    return "CONTENT_CHANGE_TYPE_STATE_DESCRIPTION";
                case 128:
                    return "CONTENT_CHANGE_TYPE_DRAG_STARTED";
                case 256:
                    return "CONTENT_CHANGE_TYPE_DRAG_DROPPED";
                case 512:
                    return "CONTENT_CHANGE_TYPE_DRAG_CANCELLED";
                case 1024:
                    return "CONTENT_CHANGE_TYPE_CONTENT_INVALID";
                case 2048:
                    return "CONTENT_CHANGE_TYPE_ERROR";
                case 4096:
                    return "CONTENT_CHANGE_TYPE_ENABLED";
                default:
                    if (Flags.triStateChecked() && i == 8192) {
                        return "CONTENT_CHANGE_TYPE_CHECKED";
                    }
                    if (Flags.a11yExpansionStateApi() && i == 16384) {
                        return "CONTENT_CHANGE_TYPE_EXPANDED";
                    }
                    if (Flags.supplementalDescription() && i == 32768) {
                        return "CONTENT_CHANGE_TYPE_SUPPLEMENTAL_DESCRIPTION";
                    }
                    return Integer.toHexString(i);
            }
        }
        return "CONTENT_CHANGE_TYPE_TEXT";
    }

    public void setContentChangeTypes(int i) {
        enforceNotSealed();
        this.mContentChangeTypes = i;
    }

    @Override // android.view.accessibility.AccessibilityRecord
    public boolean isAccessibilityDataSensitive() {
        return super.isAccessibilityDataSensitive();
    }

    @Override // android.view.accessibility.AccessibilityRecord
    public void setAccessibilityDataSensitive(boolean z) {
        super.setAccessibilityDataSensitive(z);
    }

    public int getSpeechStateChangeTypes() {
        return this.mSpeechStateChangeTypes;
    }

    private static String speechStateChangeTypesToString(int i) {
        return BitUtils.flagsToString(i, new IntFunction() { // from class: android.view.accessibility.AccessibilityEvent$$ExternalSyntheticLambda0
            @Override // java.util.function.IntFunction
            public final Object apply(int i2) {
                String singleSpeechStateChangeTypeToString;
                singleSpeechStateChangeTypeToString = AccessibilityEvent.singleSpeechStateChangeTypeToString(i2);
                return singleSpeechStateChangeTypeToString;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String singleSpeechStateChangeTypeToString(int i) {
        if (i == 1) {
            return "SPEECH_STATE_SPEAKING_START";
        }
        if (i == 2) {
            return "SPEECH_STATE_SPEAKING_END";
        }
        if (i == 4) {
            return "SPEECH_STATE_LISTENING_START";
        }
        if (i == 8) {
            return "SPEECH_STATE_LISTENING_END";
        }
        return Integer.toHexString(i);
    }

    public void setSpeechStateChangeTypes(int i) {
        enforceNotSealed();
        this.mSpeechStateChangeTypes = i;
    }

    public int getWindowChanges() {
        return this.mWindowChangeTypes;
    }

    public void setWindowChanges(int i) {
        this.mWindowChangeTypes = i;
    }

    private static String windowChangeTypesToString(int i) {
        return BitUtils.flagsToString(i, new IntFunction() { // from class: android.view.accessibility.AccessibilityEvent$$ExternalSyntheticLambda1
            @Override // java.util.function.IntFunction
            public final Object apply(int i2) {
                String singleWindowChangeTypeToString;
                singleWindowChangeTypeToString = AccessibilityEvent.singleWindowChangeTypeToString(i2);
                return singleWindowChangeTypeToString;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String singleWindowChangeTypeToString(int i) {
        if (i == 1) {
            return "WINDOWS_CHANGE_ADDED";
        }
        if (i == 2) {
            return "WINDOWS_CHANGE_REMOVED";
        }
        if (i == 4) {
            return "WINDOWS_CHANGE_TITLE";
        }
        if (i == 8) {
            return "WINDOWS_CHANGE_BOUNDS";
        }
        if (i == 16) {
            return "WINDOWS_CHANGE_LAYER";
        }
        if (i == 32) {
            return "WINDOWS_CHANGE_ACTIVE";
        }
        if (i == 64) {
            return "WINDOWS_CHANGE_FOCUSED";
        }
        if (i == 128) {
            return "WINDOWS_CHANGE_ACCESSIBILITY_FOCUSED";
        }
        if (i == 256) {
            return "WINDOWS_CHANGE_PARENT";
        }
        if (i == 512) {
            return "WINDOWS_CHANGE_CHILDREN";
        }
        if (i == 1024) {
            return "WINDOWS_CHANGE_PIP";
        }
        return Integer.toHexString(i);
    }

    public void setEventType(int i) {
        enforceNotSealed();
        this.mEventType = i;
    }

    public long getEventTime() {
        return this.mEventTime;
    }

    public void setEventTime(long j) {
        enforceNotSealed();
        this.mEventTime = j;
    }

    public CharSequence getPackageName() {
        return this.mPackageName;
    }

    public void setPackageName(CharSequence charSequence) {
        enforceNotSealed();
        this.mPackageName = charSequence;
    }

    public void setMovementGranularity(int i) {
        enforceNotSealed();
        this.mMovementGranularity = i;
    }

    public int getMovementGranularity() {
        return this.mMovementGranularity;
    }

    public void setAction(int i) {
        enforceNotSealed();
        this.mAction = i;
    }

    public int getAction() {
        return this.mAction;
    }

    public static AccessibilityEvent obtainWindowsChangedEvent(int i, int i2, int i3) {
        AccessibilityEvent accessibilityEvent = new AccessibilityEvent(4194304);
        accessibilityEvent.setDisplayId(i);
        accessibilityEvent.setWindowId(i2);
        accessibilityEvent.setWindowChanges(i3);
        accessibilityEvent.setImportantForAccessibility(true);
        return accessibilityEvent;
    }

    @Deprecated
    public static AccessibilityEvent obtain(int i) {
        AccessibilityEvent accessibilityEvent = new AccessibilityEvent();
        accessibilityEvent.setEventType(i);
        return accessibilityEvent;
    }

    @Deprecated
    public static AccessibilityEvent obtain(AccessibilityEvent accessibilityEvent) {
        AccessibilityEvent accessibilityEvent2 = new AccessibilityEvent();
        accessibilityEvent2.init(accessibilityEvent);
        return accessibilityEvent2;
    }

    @Deprecated
    public static AccessibilityEvent obtain() {
        return new AccessibilityEvent();
    }

    @Override // android.view.accessibility.AccessibilityRecord
    protected void clear() {
        super.clear();
        this.mEventType = 0;
        this.mMovementGranularity = 0;
        this.mAction = 0;
        this.mContentChangeTypes = 0;
        this.mWindowChangeTypes = 0;
        this.mSpeechStateChangeTypes = 0;
        this.mPackageName = null;
        this.mEventTime = 0L;
        if (this.mRecords != null) {
            while (!this.mRecords.isEmpty()) {
                this.mRecords.remove(0);
            }
        }
    }

    public void initFromParcel(Parcel parcel) {
        this.mSealed = parcel.readInt() == 1;
        this.mEventType = parcel.readInt();
        this.mMovementGranularity = parcel.readInt();
        this.mAction = parcel.readInt();
        this.mContentChangeTypes = parcel.readInt();
        this.mWindowChangeTypes = parcel.readInt();
        this.mSpeechStateChangeTypes = parcel.readInt();
        this.mPackageName = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mEventTime = parcel.readLong();
        this.mConnectionId = parcel.readInt();
        readAccessibilityRecordFromParcel(this, parcel);
        int readInt = parcel.readInt();
        if (readInt > 0) {
            this.mRecords = new ArrayList<>(readInt);
            for (int i = 0; i < readInt; i++) {
                AccessibilityRecord accessibilityRecord = new AccessibilityRecord();
                readAccessibilityRecordFromParcel(accessibilityRecord, parcel);
                accessibilityRecord.mConnectionId = this.mConnectionId;
                this.mRecords.add(accessibilityRecord);
            }
        }
    }

    private void readAccessibilityRecordFromParcel(AccessibilityRecord accessibilityRecord, Parcel parcel) {
        accessibilityRecord.mBooleanProperties = parcel.readInt();
        accessibilityRecord.mCurrentItemIndex = parcel.readInt();
        accessibilityRecord.mItemCount = parcel.readInt();
        accessibilityRecord.mFromIndex = parcel.readInt();
        accessibilityRecord.mToIndex = parcel.readInt();
        accessibilityRecord.mScrollX = parcel.readInt();
        accessibilityRecord.mScrollY = parcel.readInt();
        accessibilityRecord.mScrollDeltaX = parcel.readInt();
        accessibilityRecord.mScrollDeltaY = parcel.readInt();
        accessibilityRecord.mMaxScrollX = parcel.readInt();
        accessibilityRecord.mMaxScrollY = parcel.readInt();
        accessibilityRecord.mAddedCount = parcel.readInt();
        accessibilityRecord.mRemovedCount = parcel.readInt();
        accessibilityRecord.mClassName = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        accessibilityRecord.mContentDescription = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        accessibilityRecord.mBeforeText = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        accessibilityRecord.mParcelableData = parcel.readParcelable(null);
        parcel.readList(accessibilityRecord.mText, null, CharSequence.class);
        accessibilityRecord.mSourceWindowId = parcel.readInt();
        accessibilityRecord.mSourceNodeId = parcel.readLong();
        accessibilityRecord.mSourceDisplayId = parcel.readInt();
        accessibilityRecord.mSealed = parcel.readInt() == 1;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(isSealed() ? 1 : 0);
        parcel.writeInt(this.mEventType);
        parcel.writeInt(this.mMovementGranularity);
        parcel.writeInt(this.mAction);
        parcel.writeInt(this.mContentChangeTypes);
        parcel.writeInt(this.mWindowChangeTypes);
        parcel.writeInt(this.mSpeechStateChangeTypes);
        TextUtils.writeToParcel(this.mPackageName, parcel, 0);
        parcel.writeLong(this.mEventTime);
        parcel.writeInt(this.mConnectionId);
        writeAccessibilityRecordToParcel(this, parcel, i);
        int recordCount = getRecordCount();
        parcel.writeInt(recordCount);
        for (int i2 = 0; i2 < recordCount; i2++) {
            writeAccessibilityRecordToParcel(this.mRecords.get(i2), parcel, i);
        }
    }

    private void writeAccessibilityRecordToParcel(AccessibilityRecord accessibilityRecord, Parcel parcel, int i) {
        parcel.writeInt(accessibilityRecord.mBooleanProperties);
        parcel.writeInt(accessibilityRecord.mCurrentItemIndex);
        parcel.writeInt(accessibilityRecord.mItemCount);
        parcel.writeInt(accessibilityRecord.mFromIndex);
        parcel.writeInt(accessibilityRecord.mToIndex);
        parcel.writeInt(accessibilityRecord.mScrollX);
        parcel.writeInt(accessibilityRecord.mScrollY);
        parcel.writeInt(accessibilityRecord.mScrollDeltaX);
        parcel.writeInt(accessibilityRecord.mScrollDeltaY);
        parcel.writeInt(accessibilityRecord.mMaxScrollX);
        parcel.writeInt(accessibilityRecord.mMaxScrollY);
        parcel.writeInt(accessibilityRecord.mAddedCount);
        parcel.writeInt(accessibilityRecord.mRemovedCount);
        TextUtils.writeToParcel(accessibilityRecord.mClassName, parcel, i);
        TextUtils.writeToParcel(accessibilityRecord.mContentDescription, parcel, i);
        TextUtils.writeToParcel(accessibilityRecord.mBeforeText, parcel, i);
        parcel.writeParcelable(accessibilityRecord.mParcelableData, i);
        parcel.writeList(accessibilityRecord.mText);
        parcel.writeInt(accessibilityRecord.mSourceWindowId);
        parcel.writeLong(accessibilityRecord.mSourceNodeId);
        parcel.writeInt(accessibilityRecord.mSourceDisplayId);
        parcel.writeInt(accessibilityRecord.mSealed ? 1 : 0);
    }

    @Override // android.view.accessibility.AccessibilityRecord
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("EventType: ");
        sb.append(eventTypeToString(this.mEventType));
        sb.append("; EventTime: ");
        sb.append(this.mEventTime);
        sb.append("; PackageName: ");
        sb.append(this.mPackageName);
        sb.append("; MovementGranularity: ");
        sb.append(this.mMovementGranularity);
        sb.append("; Action: ");
        sb.append(this.mAction);
        sb.append("; ContentChangeTypes: ");
        sb.append(contentChangeTypesToString(this.mContentChangeTypes));
        sb.append("; WindowChangeTypes: ");
        sb.append(windowChangeTypesToString(this.mWindowChangeTypes));
        super.appendTo(sb);
        boolean z = DEBUG;
        if (z) {
            sb.append(ShaderAssembler.NEWLINE);
            if (z) {
                sb.append("; SourceWindowId: 0x");
                sb.append(Long.toHexString(this.mSourceWindowId));
                sb.append("; SourceNodeId: 0x");
                sb.append(Long.toHexString(this.mSourceNodeId));
                sb.append("; SourceDisplayId: ");
                sb.append(this.mSourceDisplayId);
            }
            for (int i = 0; i < getRecordCount(); i++) {
                sb.append("  Record ");
                sb.append(i);
                sb.append(":");
                getRecord(i).appendTo(sb).append(ShaderAssembler.NEWLINE);
            }
        } else {
            sb.append("; recordCount: ");
            sb.append(getRecordCount());
        }
        return sb.toString();
    }

    public static String eventTypeToString(int i) {
        if (i == -1) {
            return "TYPES_ALL_MASK";
        }
        StringBuilder sb = new StringBuilder();
        int i2 = 0;
        while (i != 0) {
            int numberOfTrailingZeros = 1 << Integer.numberOfTrailingZeros(i);
            i &= ~numberOfTrailingZeros;
            if (i2 > 0) {
                sb.append(", ");
            }
            sb.append(singleEventTypeToString(numberOfTrailingZeros));
            i2++;
        }
        if (i2 > 1) {
            sb.insert(0, '[');
            sb.append(']');
        }
        return sb.toString();
    }

    private static String singleEventTypeToString(int i) {
        if (i == 1) {
            return "TYPE_VIEW_CLICKED";
        }
        if (i == 2) {
            return "TYPE_VIEW_LONG_CLICKED";
        }
        switch (i) {
            case 4:
                return "TYPE_VIEW_SELECTED";
            case 8:
                return "TYPE_VIEW_FOCUSED";
            case 16:
                return "TYPE_VIEW_TEXT_CHANGED";
            case 32:
                return "TYPE_WINDOW_STATE_CHANGED";
            case 64:
                return "TYPE_NOTIFICATION_STATE_CHANGED";
            case 128:
                return "TYPE_VIEW_HOVER_ENTER";
            case 256:
                return "TYPE_VIEW_HOVER_EXIT";
            case 512:
                return "TYPE_TOUCH_EXPLORATION_GESTURE_START";
            case 1024:
                return "TYPE_TOUCH_EXPLORATION_GESTURE_END";
            case 2048:
                return "TYPE_WINDOW_CONTENT_CHANGED";
            case 4096:
                return "TYPE_VIEW_SCROLLED";
            case 8192:
                return "TYPE_VIEW_TEXT_SELECTION_CHANGED";
            case 16384:
                return "TYPE_ANNOUNCEMENT";
            case 32768:
                return "TYPE_VIEW_ACCESSIBILITY_FOCUSED";
            case 65536:
                return "TYPE_VIEW_ACCESSIBILITY_FOCUS_CLEARED";
            case 131072:
                return "TYPE_VIEW_TEXT_TRAVERSED_AT_MOVEMENT_GRANULARITY";
            case 262144:
                return "TYPE_GESTURE_DETECTION_START";
            case 524288:
                return "TYPE_GESTURE_DETECTION_END";
            case 1048576:
                return "TYPE_TOUCH_INTERACTION_START";
            case 2097152:
                return "TYPE_TOUCH_INTERACTION_END";
            case 4194304:
                return "TYPE_WINDOWS_CHANGED";
            case 8388608:
                return "TYPE_VIEW_CONTEXT_CLICKED";
            case 16777216:
                return "TYPE_ASSIST_READING_CONTEXT";
            case 33554432:
                return "TYPE_SPEECH_STATE_CHANGE";
            case 67108864:
                return "TYPE_VIEW_TARGETED_BY_SCROLL";
            default:
                return Integer.toHexString(i);
        }
    }
}
