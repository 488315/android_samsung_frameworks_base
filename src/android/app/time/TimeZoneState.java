package android.app.time;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.ShellCommand;
import java.io.PrintWriter;
import java.util.Objects;

@SystemApi
/* loaded from: classes.dex */
public final class TimeZoneState implements Parcelable {
    public static final Parcelable.Creator<TimeZoneState> CREATOR = new Parcelable.Creator<TimeZoneState>() { // from class: android.app.time.TimeZoneState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TimeZoneState createFromParcel(Parcel parcel) {
            return TimeZoneState.createFromParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TimeZoneState[] newArray(int i) {
            return new TimeZoneState[i];
        }
    };
    private final String mId;
    private final boolean mUserShouldConfirmId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public TimeZoneState(String str, boolean z) {
        this.mId = (String) Objects.requireNonNull(str);
        this.mUserShouldConfirmId = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static TimeZoneState createFromParcel(Parcel parcel) {
        return new TimeZoneState(parcel.readString8(), parcel.readBoolean());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString8(this.mId);
        parcel.writeBoolean(this.mUserShouldConfirmId);
    }

    public static TimeZoneState parseCommandLineArgs(ShellCommand shellCommand) {
        String str = null;
        Boolean bool = null;
        while (true) {
            String nextArg = shellCommand.getNextArg();
            if (nextArg == null) {
                if (str == null) {
                    throw new IllegalArgumentException("No zoneId specified.");
                }
                if (bool == null) {
                    throw new IllegalArgumentException("No userShouldConfirmId specified.");
                }
                return new TimeZoneState(str, bool.booleanValue());
            }
            nextArg.hashCode();
            if (nextArg.equals("--user_should_confirm_id")) {
                bool = Boolean.valueOf(Boolean.parseBoolean(shellCommand.getNextArgRequired()));
            } else if (nextArg.equals("--zone_id")) {
                str = shellCommand.getNextArgRequired();
            } else {
                throw new IllegalArgumentException("Unknown option: " + nextArg);
            }
        }
    }

    public static void printCommandLineOpts(PrintWriter printWriter) {
        printWriter.println("TimeZoneState options:");
        printWriter.println("  --zone_id {<Olson ID>}");
        printWriter.println("  --user_should_confirm_id {true|false}");
        printWriter.println();
        printWriter.println("See " + TimeZoneState.class.getName() + " for more information");
    }

    public String getId() {
        return this.mId;
    }

    public boolean getUserShouldConfirmId() {
        return this.mUserShouldConfirmId;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            TimeZoneState timeZoneState = (TimeZoneState) obj;
            if (Objects.equals(this.mId, timeZoneState.mId) && this.mUserShouldConfirmId == timeZoneState.mUserShouldConfirmId) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.mId, Boolean.valueOf(this.mUserShouldConfirmId));
    }

    public String toString() {
        return "TimeZoneState{mZoneId=" + this.mId + ", mUserShouldConfirmId=" + this.mUserShouldConfirmId + '}';
    }
}
