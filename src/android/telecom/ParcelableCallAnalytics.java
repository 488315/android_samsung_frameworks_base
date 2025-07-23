package android.telecom;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

@SystemApi
/* loaded from: classes4.dex */
public class ParcelableCallAnalytics implements Parcelable {
    public static final int CALLTYPE_INCOMING = 1;
    public static final int CALLTYPE_OUTGOING = 2;
    public static final int CALLTYPE_UNKNOWN = 0;

    @Deprecated
    public static final int CDMA_PHONE = 1;
    public static final Parcelable.Creator<ParcelableCallAnalytics> CREATOR = new Parcelable.Creator<ParcelableCallAnalytics>() { // from class: android.telecom.ParcelableCallAnalytics.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ParcelableCallAnalytics createFromParcel(Parcel parcel) {
            return new ParcelableCallAnalytics(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ParcelableCallAnalytics[] newArray(int i) {
            return new ParcelableCallAnalytics[i];
        }
    };
    public static final int GSM_PHONE = 2;
    public static final int IMS_PHONE = 4;
    public static final long MILLIS_IN_1_SECOND = 1000;
    public static final long MILLIS_IN_5_MINUTES = 300000;
    public static final int SIP_PHONE = 8;
    public static final int STILL_CONNECTED = -1;
    public static final int THIRD_PARTY_PHONE = 16;
    private final List<AnalyticsEvent> analyticsEvents;
    private final long callDurationMillis;
    private int callSource;
    private final int callTechnologies;
    private final int callTerminationCode;
    private final int callType;
    private final String connectionService;
    private final List<EventTiming> eventTimings;
    private final boolean isAdditionalCall;
    private final boolean isCreatedFromExistingConnection;
    private final boolean isEmergencyCall;
    private final boolean isInterrupted;
    private boolean isVideoCall;
    private final long startTimeMillis;
    private List<VideoEvent> videoEvents;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static final class VideoEvent implements Parcelable {
        public static final Parcelable.Creator<VideoEvent> CREATOR = new Parcelable.Creator<VideoEvent>() { // from class: android.telecom.ParcelableCallAnalytics.VideoEvent.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public VideoEvent createFromParcel(Parcel parcel) {
                return new VideoEvent(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public VideoEvent[] newArray(int i) {
                return new VideoEvent[i];
            }
        };
        public static final int RECEIVE_REMOTE_SESSION_MODIFY_REQUEST = 2;
        public static final int RECEIVE_REMOTE_SESSION_MODIFY_RESPONSE = 3;
        public static final int SEND_LOCAL_SESSION_MODIFY_REQUEST = 0;
        public static final int SEND_LOCAL_SESSION_MODIFY_RESPONSE = 1;
        private int mEventName;
        private long mTimeSinceLastEvent;
        private int mVideoState;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public VideoEvent(int i, long j, int i2) {
            this.mEventName = i;
            this.mTimeSinceLastEvent = j;
            this.mVideoState = i2;
        }

        VideoEvent(Parcel parcel) {
            this.mEventName = parcel.readInt();
            this.mTimeSinceLastEvent = parcel.readLong();
            this.mVideoState = parcel.readInt();
        }

        public int getEventName() {
            return this.mEventName;
        }

        public long getTimeSinceLastEvent() {
            return this.mTimeSinceLastEvent;
        }

        public int getVideoState() {
            return this.mVideoState;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mEventName);
            parcel.writeLong(this.mTimeSinceLastEvent);
            parcel.writeInt(this.mVideoState);
        }
    }

    public static final class AnalyticsEvent implements Parcelable {
        public static final int AUDIO_ROUTE_BT = 204;
        public static final int AUDIO_ROUTE_EARPIECE = 205;
        public static final int AUDIO_ROUTE_HEADSET = 206;
        public static final int AUDIO_ROUTE_SPEAKER = 207;
        public static final int BIND_CS = 5;
        public static final int BLOCK_CHECK_FINISHED = 105;
        public static final int BLOCK_CHECK_INITIATED = 104;
        public static final int CONFERENCE_WITH = 300;
        public static final Parcelable.Creator<AnalyticsEvent> CREATOR = new Parcelable.Creator<AnalyticsEvent>() { // from class: android.telecom.ParcelableCallAnalytics.AnalyticsEvent.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AnalyticsEvent createFromParcel(Parcel parcel) {
                return new AnalyticsEvent(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AnalyticsEvent[] newArray(int i) {
                return new AnalyticsEvent[i];
            }
        };
        public static final int CS_BOUND = 6;
        public static final int DIRECT_TO_VM_FINISHED = 103;
        public static final int DIRECT_TO_VM_INITIATED = 102;
        public static final int DND_CHECK_COMPLETED = 110;
        public static final int DND_CHECK_INITIATED = 109;
        public static final int FILTERING_COMPLETED = 107;
        public static final int FILTERING_INITIATED = 106;
        public static final int FILTERING_TIMED_OUT = 108;
        public static final int MUTE = 202;
        public static final int REMOTELY_HELD = 402;
        public static final int REMOTELY_UNHELD = 403;
        public static final int REQUEST_ACCEPT = 7;
        public static final int REQUEST_HOLD = 400;
        public static final int REQUEST_PULL = 500;
        public static final int REQUEST_REJECT = 8;
        public static final int REQUEST_UNHOLD = 401;
        public static final int SCREENING_COMPLETED = 101;
        public static final int SCREENING_SENT = 100;
        public static final int SET_ACTIVE = 1;
        public static final int SET_DIALING = 4;
        public static final int SET_DISCONNECTED = 2;
        public static final int SET_HOLD = 404;
        public static final int SET_PARENT = 302;
        public static final int SET_SELECT_PHONE_ACCOUNT = 0;
        public static final int SILENCE = 201;
        public static final int SKIP_RINGING = 200;
        public static final int SPLIT_CONFERENCE = 301;
        public static final int START_CONNECTION = 3;
        public static final int SWAP = 405;
        public static final int UNMUTE = 203;
        private int mEventName;
        private long mTimeSinceLastEvent;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public AnalyticsEvent(int i, long j) {
            this.mEventName = i;
            this.mTimeSinceLastEvent = j;
        }

        AnalyticsEvent(Parcel parcel) {
            this.mEventName = parcel.readInt();
            this.mTimeSinceLastEvent = parcel.readLong();
        }

        public int getEventName() {
            return this.mEventName;
        }

        public long getTimeSinceLastEvent() {
            return this.mTimeSinceLastEvent;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mEventName);
            parcel.writeLong(this.mTimeSinceLastEvent);
        }
    }

    public static final class EventTiming implements Parcelable {
        public static final int ACCEPT_TIMING = 0;
        public static final int BIND_CS_TIMING = 6;
        public static final int BLOCK_CHECK_FINISHED_TIMING = 9;
        public static final Parcelable.Creator<EventTiming> CREATOR = new Parcelable.Creator<EventTiming>() { // from class: android.telecom.ParcelableCallAnalytics.EventTiming.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public EventTiming createFromParcel(Parcel parcel) {
                return new EventTiming(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public EventTiming[] newArray(int i) {
                return new EventTiming[i];
            }
        };
        public static final int DIRECT_TO_VM_FINISHED_TIMING = 8;
        public static final int DISCONNECT_TIMING = 2;
        public static final int DND_PRE_CALL_PRE_CHECK_TIMING = 12;
        public static final int FILTERING_COMPLETED_TIMING = 10;
        public static final int FILTERING_TIMED_OUT_TIMING = 11;
        public static final int HOLD_TIMING = 3;
        public static final int INVALID = 999999;
        public static final int OUTGOING_TIME_TO_DIALING_TIMING = 5;
        public static final int REJECT_TIMING = 1;
        public static final int SCREENING_COMPLETED_TIMING = 7;
        public static final int START_CONNECTION_TO_REQUEST_DISCONNECT_TIMING = 12;
        public static final int UNHOLD_TIMING = 4;
        private int mName;
        private long mTime;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public EventTiming(int i, long j) {
            this.mName = i;
            this.mTime = j;
        }

        private EventTiming(Parcel parcel) {
            this.mName = parcel.readInt();
            this.mTime = parcel.readLong();
        }

        public int getName() {
            return this.mName;
        }

        public long getTime() {
            return this.mTime;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mName);
            parcel.writeLong(this.mTime);
        }
    }

    public ParcelableCallAnalytics(long j, long j2, int i, boolean z, boolean z2, int i2, int i3, boolean z3, String str, boolean z4, List<AnalyticsEvent> list, List<EventTiming> list2) {
        this.isVideoCall = false;
        this.callSource = 0;
        this.startTimeMillis = j;
        this.callDurationMillis = j2;
        this.callType = i;
        this.isAdditionalCall = z;
        this.isInterrupted = z2;
        this.callTechnologies = i2;
        this.callTerminationCode = i3;
        this.isEmergencyCall = z3;
        this.connectionService = str;
        this.isCreatedFromExistingConnection = z4;
        this.analyticsEvents = list;
        this.eventTimings = list2;
    }

    public ParcelableCallAnalytics(Parcel parcel) {
        this.isVideoCall = false;
        this.callSource = 0;
        this.startTimeMillis = parcel.readLong();
        this.callDurationMillis = parcel.readLong();
        this.callType = parcel.readInt();
        this.isAdditionalCall = readByteAsBoolean(parcel);
        this.isInterrupted = readByteAsBoolean(parcel);
        this.callTechnologies = parcel.readInt();
        this.callTerminationCode = parcel.readInt();
        this.isEmergencyCall = readByteAsBoolean(parcel);
        this.connectionService = parcel.readString();
        this.isCreatedFromExistingConnection = readByteAsBoolean(parcel);
        ArrayList arrayList = new ArrayList();
        this.analyticsEvents = arrayList;
        parcel.readTypedList(arrayList, AnalyticsEvent.CREATOR);
        ArrayList arrayList2 = new ArrayList();
        this.eventTimings = arrayList2;
        parcel.readTypedList(arrayList2, EventTiming.CREATOR);
        this.isVideoCall = readByteAsBoolean(parcel);
        ArrayList arrayList3 = new ArrayList();
        this.videoEvents = arrayList3;
        parcel.readTypedList(arrayList3, VideoEvent.CREATOR);
        this.callSource = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.startTimeMillis);
        parcel.writeLong(this.callDurationMillis);
        parcel.writeInt(this.callType);
        writeBooleanAsByte(parcel, this.isAdditionalCall);
        writeBooleanAsByte(parcel, this.isInterrupted);
        parcel.writeInt(this.callTechnologies);
        parcel.writeInt(this.callTerminationCode);
        writeBooleanAsByte(parcel, this.isEmergencyCall);
        parcel.writeString(this.connectionService);
        writeBooleanAsByte(parcel, this.isCreatedFromExistingConnection);
        parcel.writeTypedList(this.analyticsEvents);
        parcel.writeTypedList(this.eventTimings);
        writeBooleanAsByte(parcel, this.isVideoCall);
        parcel.writeTypedList(this.videoEvents);
        parcel.writeInt(this.callSource);
    }

    public void setIsVideoCall(boolean z) {
        this.isVideoCall = z;
    }

    public void setVideoEvents(List<VideoEvent> list) {
        this.videoEvents = list;
    }

    public void setCallSource(int i) {
        this.callSource = i;
    }

    public long getStartTimeMillis() {
        return this.startTimeMillis;
    }

    public long getCallDurationMillis() {
        return this.callDurationMillis;
    }

    public int getCallType() {
        return this.callType;
    }

    public boolean isAdditionalCall() {
        return this.isAdditionalCall;
    }

    public boolean isInterrupted() {
        return this.isInterrupted;
    }

    public int getCallTechnologies() {
        return this.callTechnologies;
    }

    public int getCallTerminationCode() {
        return this.callTerminationCode;
    }

    public boolean isEmergencyCall() {
        return this.isEmergencyCall;
    }

    public String getConnectionService() {
        return this.connectionService;
    }

    public boolean isCreatedFromExistingConnection() {
        return this.isCreatedFromExistingConnection;
    }

    public List<AnalyticsEvent> analyticsEvents() {
        return this.analyticsEvents;
    }

    public List<EventTiming> getEventTimings() {
        return this.eventTimings;
    }

    public boolean isVideoCall() {
        return this.isVideoCall;
    }

    public List<VideoEvent> getVideoEvents() {
        return this.videoEvents;
    }

    public int getCallSource() {
        return this.callSource;
    }

    private static void writeBooleanAsByte(Parcel parcel, boolean z) {
        parcel.writeByte(z ? (byte) 1 : (byte) 0);
    }

    private static boolean readByteAsBoolean(Parcel parcel) {
        return parcel.readByte() == 1;
    }
}
