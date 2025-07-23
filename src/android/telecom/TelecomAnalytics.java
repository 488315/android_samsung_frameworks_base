package android.telecom;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

@SystemApi
/* loaded from: classes4.dex */
public final class TelecomAnalytics implements Parcelable {
    public static final Parcelable.Creator<TelecomAnalytics> CREATOR = new Parcelable.Creator<TelecomAnalytics>() { // from class: android.telecom.TelecomAnalytics.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TelecomAnalytics createFromParcel(Parcel parcel) {
            return new TelecomAnalytics(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TelecomAnalytics[] newArray(int i) {
            return new TelecomAnalytics[i];
        }
    };
    private List<ParcelableCallAnalytics> mCallAnalytics;
    private List<SessionTiming> mSessionTimings;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static final class SessionTiming extends TimedEvent<Integer> implements Parcelable {
        public static final Parcelable.Creator<SessionTiming> CREATOR = new Parcelable.Creator<SessionTiming>() { // from class: android.telecom.TelecomAnalytics.SessionTiming.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SessionTiming createFromParcel(Parcel parcel) {
                return new SessionTiming(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SessionTiming[] newArray(int i) {
                return new SessionTiming[i];
            }
        };
        public static final int CSW_ADD_CONFERENCE_CALL = 108;
        public static final int CSW_HANDLE_CREATE_CONNECTION_COMPLETE = 100;
        public static final int CSW_REMOVE_CALL = 106;
        public static final int CSW_SET_ACTIVE = 101;
        public static final int CSW_SET_DIALING = 103;
        public static final int CSW_SET_DISCONNECTED = 104;
        public static final int CSW_SET_IS_CONFERENCED = 107;
        public static final int CSW_SET_ON_HOLD = 105;
        public static final int CSW_SET_RINGING = 102;
        public static final int ICA_ANSWER_CALL = 1;
        public static final int ICA_CONFERENCE = 8;
        public static final int ICA_DISCONNECT_CALL = 3;
        public static final int ICA_HOLD_CALL = 4;
        public static final int ICA_MUTE = 6;
        public static final int ICA_REJECT_CALL = 2;
        public static final int ICA_SET_AUDIO_ROUTE = 7;
        public static final int ICA_UNHOLD_CALL = 5;
        private int mId;
        private long mTime;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public SessionTiming(int i, long j) {
            this.mId = i;
            this.mTime = j;
        }

        private SessionTiming(Parcel parcel) {
            this.mId = parcel.readInt();
            this.mTime = parcel.readLong();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.telecom.TimedEvent
        public Integer getKey() {
            return Integer.valueOf(this.mId);
        }

        @Override // android.telecom.TimedEvent
        public long getTime() {
            return this.mTime;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mId);
            parcel.writeLong(this.mTime);
        }
    }

    public TelecomAnalytics(List<SessionTiming> list, List<ParcelableCallAnalytics> list2) {
        this.mSessionTimings = list;
        this.mCallAnalytics = list2;
    }

    private TelecomAnalytics(Parcel parcel) {
        ArrayList arrayList = new ArrayList();
        this.mSessionTimings = arrayList;
        parcel.readTypedList(arrayList, SessionTiming.CREATOR);
        ArrayList arrayList2 = new ArrayList();
        this.mCallAnalytics = arrayList2;
        parcel.readTypedList(arrayList2, ParcelableCallAnalytics.CREATOR);
    }

    public List<SessionTiming> getSessionTimings() {
        return this.mSessionTimings;
    }

    public List<ParcelableCallAnalytics> getCallAnalytics() {
        return this.mCallAnalytics;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.mSessionTimings);
        parcel.writeTypedList(this.mCallAnalytics);
    }
}
