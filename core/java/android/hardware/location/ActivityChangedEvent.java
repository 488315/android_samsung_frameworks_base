package android.hardware.location;

import android.os.Parcel;
import android.os.Parcelable;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.List;

public class ActivityChangedEvent implements Parcelable {
    public static final Parcelable.Creator<ActivityChangedEvent> CREATOR =
            new Parcelable.Creator<ActivityChangedEvent>() { // from class:
                // android.hardware.location.ActivityChangedEvent.1
                @Override // android.os.Parcelable.Creator
                public ActivityChangedEvent createFromParcel(Parcel source) {
                    int activityRecognitionEventsLength = source.readInt();
                    ActivityRecognitionEvent[] activityRecognitionEvents =
                            new ActivityRecognitionEvent[activityRecognitionEventsLength];
                    source.readTypedArray(
                            activityRecognitionEvents, ActivityRecognitionEvent.CREATOR);
                    return new ActivityChangedEvent(activityRecognitionEvents);
                }

                @Override // android.os.Parcelable.Creator
                public ActivityChangedEvent[] newArray(int size) {
                    return new ActivityChangedEvent[size];
                }
            };
    private final List<ActivityRecognitionEvent> mActivityRecognitionEvents;

    public ActivityChangedEvent(ActivityRecognitionEvent[] activityRecognitionEvents) {
        if (activityRecognitionEvents == null) {
            throw new InvalidParameterException(
                    "Parameter 'activityRecognitionEvents' must not be null.");
        }
        this.mActivityRecognitionEvents = Arrays.asList(activityRecognitionEvents);
    }

    public Iterable<ActivityRecognitionEvent> getActivityRecognitionEvents() {
        return this.mActivityRecognitionEvents;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
        ActivityRecognitionEvent[] activityRecognitionEventArray =
                (ActivityRecognitionEvent[])
                        this.mActivityRecognitionEvents.toArray(new ActivityRecognitionEvent[0]);
        parcel.writeInt(activityRecognitionEventArray.length);
        parcel.writeTypedArray(activityRecognitionEventArray, flags);
    }

    public String toString() {
        StringBuilder builder = new StringBuilder("[ ActivityChangedEvent:");
        for (ActivityRecognitionEvent event : this.mActivityRecognitionEvents) {
            builder.append("\n    ");
            builder.append(event.toString());
        }
        builder.append("\n]");
        return builder.toString();
    }
}
