package android.service.timezone;

import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.Duration;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class TimeZoneProviderEvent implements Parcelable {
    public static final Parcelable.Creator<TimeZoneProviderEvent> CREATOR = new Parcelable.Creator<TimeZoneProviderEvent>() { // from class: android.service.timezone.TimeZoneProviderEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TimeZoneProviderEvent createFromParcel(Parcel parcel) {
            return new TimeZoneProviderEvent(parcel.readInt(), parcel.readLong(), (TimeZoneProviderSuggestion) parcel.readParcelable(getClass().getClassLoader(), TimeZoneProviderSuggestion.class), parcel.readString8(), (TimeZoneProviderStatus) parcel.readParcelable(getClass().getClassLoader(), TimeZoneProviderStatus.class));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TimeZoneProviderEvent[] newArray(int i) {
            return new TimeZoneProviderEvent[i];
        }
    };
    public static final int EVENT_TYPE_PERMANENT_FAILURE = 1;
    public static final int EVENT_TYPE_SUGGESTION = 2;
    public static final int EVENT_TYPE_UNCERTAIN = 3;
    private final long mCreationElapsedMillis;
    private final String mFailureCause;
    private final TimeZoneProviderSuggestion mSuggestion;
    private final TimeZoneProviderStatus mTimeZoneProviderStatus;
    private final int mType;

    @Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface EventType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private TimeZoneProviderEvent(int i, long j, TimeZoneProviderSuggestion timeZoneProviderSuggestion, String str, TimeZoneProviderStatus timeZoneProviderStatus) {
        int iValidateEventType = validateEventType(i);
        this.mType = iValidateEventType;
        this.mCreationElapsedMillis = j;
        this.mSuggestion = timeZoneProviderSuggestion;
        this.mFailureCause = str;
        this.mTimeZoneProviderStatus = timeZoneProviderStatus;
        if (iValidateEventType != 1 || timeZoneProviderStatus == null) {
            return;
        }
        throw new IllegalArgumentException("Unexpected status: mType=" + iValidateEventType + ", mTimeZoneProviderStatus=" + timeZoneProviderStatus);
    }

    private static int validateEventType(int i) {
        if (i < 1 || i > 3) {
            throw new IllegalArgumentException(Integer.toString(i));
        }
        return i;
    }

    public static TimeZoneProviderEvent createSuggestionEvent(long j, TimeZoneProviderSuggestion timeZoneProviderSuggestion, TimeZoneProviderStatus timeZoneProviderStatus) {
        return new TimeZoneProviderEvent(2, j, (TimeZoneProviderSuggestion) Objects.requireNonNull(timeZoneProviderSuggestion), null, timeZoneProviderStatus);
    }

    public static TimeZoneProviderEvent createUncertainEvent(long j, TimeZoneProviderStatus timeZoneProviderStatus) {
        return new TimeZoneProviderEvent(3, j, null, null, timeZoneProviderStatus);
    }

    public static TimeZoneProviderEvent createPermanentFailureEvent(long j, String str) {
        return new TimeZoneProviderEvent(1, j, null, (String) Objects.requireNonNull(str), null);
    }

    public int getType() {
        return this.mType;
    }

    public long getCreationElapsedMillis() {
        return this.mCreationElapsedMillis;
    }

    public TimeZoneProviderSuggestion getSuggestion() {
        return this.mSuggestion;
    }

    public String getFailureCause() {
        return this.mFailureCause;
    }

    public TimeZoneProviderStatus getTimeZoneProviderStatus() {
        return this.mTimeZoneProviderStatus;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mType);
        parcel.writeLong(this.mCreationElapsedMillis);
        parcel.writeParcelable(this.mSuggestion, 0);
        parcel.writeString8(this.mFailureCause);
        parcel.writeParcelable(this.mTimeZoneProviderStatus, 0);
    }

    public String toString() {
        return "TimeZoneProviderEvent{mType=" + this.mType + ", mCreationElapsedMillis=" + Duration.ofMillis(this.mCreationElapsedMillis).toString() + ", mSuggestion=" + this.mSuggestion + ", mFailureCause=" + this.mFailureCause + ", mTimeZoneProviderStatus=" + this.mTimeZoneProviderStatus + '}';
    }

    public boolean isEquivalentTo(TimeZoneProviderEvent timeZoneProviderEvent) {
        int i;
        if (this == timeZoneProviderEvent) {
            return true;
        }
        if (timeZoneProviderEvent == null || (i = this.mType) != timeZoneProviderEvent.mType) {
            return false;
        }
        if (i == 2) {
            return this.mSuggestion.isEquivalentTo(timeZoneProviderEvent.mSuggestion) && Objects.equals(this.mTimeZoneProviderStatus, timeZoneProviderEvent.mTimeZoneProviderStatus);
        }
        return Objects.equals(this.mTimeZoneProviderStatus, timeZoneProviderEvent.mTimeZoneProviderStatus);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            TimeZoneProviderEvent timeZoneProviderEvent = (TimeZoneProviderEvent) obj;
            if (this.mType == timeZoneProviderEvent.mType && this.mCreationElapsedMillis == timeZoneProviderEvent.mCreationElapsedMillis && Objects.equals(this.mSuggestion, timeZoneProviderEvent.mSuggestion) && Objects.equals(this.mFailureCause, timeZoneProviderEvent.mFailureCause) && Objects.equals(this.mTimeZoneProviderStatus, timeZoneProviderEvent.mTimeZoneProviderStatus)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mType), Long.valueOf(this.mCreationElapsedMillis), this.mSuggestion, this.mFailureCause, this.mTimeZoneProviderStatus);
    }
}
