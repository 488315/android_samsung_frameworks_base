package android.app.time;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.ShellCommand;
import java.io.PrintWriter;
import java.util.Objects;

@SystemApi
/* loaded from: classes.dex */
public final class TimeState implements Parcelable {
    public static final Parcelable.Creator<TimeState> CREATOR = new Parcelable.Creator<TimeState>() { // from class: android.app.time.TimeState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TimeState createFromParcel(Parcel parcel) {
            return TimeState.createFromParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TimeState[] newArray(int i) {
            return new TimeState[i];
        }
    };
    private final UnixEpochTime mUnixEpochTime;
    private final boolean mUserShouldConfirmTime;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public TimeState(UnixEpochTime unixEpochTime, boolean z) {
        this.mUnixEpochTime = (UnixEpochTime) Objects.requireNonNull(unixEpochTime);
        this.mUserShouldConfirmTime = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static TimeState createFromParcel(Parcel parcel) {
        return new TimeState((UnixEpochTime) parcel.readParcelable(null, UnixEpochTime.class), parcel.readBoolean());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.mUnixEpochTime, 0);
        parcel.writeBoolean(this.mUserShouldConfirmTime);
    }

    public static TimeState parseCommandLineArgs(ShellCommand shellCommand) {
        Long lValueOf = null;
        Long lValueOf2 = null;
        Boolean boolValueOf = null;
        while (true) {
            String nextArg = shellCommand.getNextArg();
            if (nextArg == null) {
                if (lValueOf == null) {
                    throw new IllegalArgumentException("No elapsedRealtimeMillis specified.");
                }
                if (lValueOf2 == null) {
                    throw new IllegalArgumentException("No unixEpochTimeMillis specified.");
                }
                if (boolValueOf == null) {
                    throw new IllegalArgumentException("No userShouldConfirmTime specified.");
                }
                return new TimeState(new UnixEpochTime(lValueOf.longValue(), lValueOf2.longValue()), boolValueOf.booleanValue());
            }
            nextArg.hashCode();
            switch (nextArg) {
                case "--elapsed_realtime":
                    lValueOf = Long.valueOf(Long.parseLong(shellCommand.getNextArgRequired()));
                    break;
                case "--unix_epoch_time":
                    lValueOf2 = Long.valueOf(Long.parseLong(shellCommand.getNextArgRequired()));
                    break;
                case "--user_should_confirm_time":
                    boolValueOf = Boolean.valueOf(Boolean.parseBoolean(shellCommand.getNextArgRequired()));
                    break;
                default:
                    throw new IllegalArgumentException("Unknown option: " + nextArg);
            }
        }
    }

    public static void printCommandLineOpts(PrintWriter printWriter) {
        printWriter.println("TimeState options:");
        printWriter.println("  --elapsed_realtime <elapsed realtime millis>");
        printWriter.println("  --unix_epoch_time <Unix epoch time millis>");
        printWriter.println("  --user_should_confirm_time {true|false}");
        printWriter.println();
        printWriter.println("See " + TimeState.class.getName() + " for more information");
    }

    public UnixEpochTime getUnixEpochTime() {
        return this.mUnixEpochTime;
    }

    public boolean getUserShouldConfirmTime() {
        return this.mUserShouldConfirmTime;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            TimeState timeState = (TimeState) obj;
            if (Objects.equals(this.mUnixEpochTime, timeState.mUnixEpochTime) && this.mUserShouldConfirmTime == timeState.mUserShouldConfirmTime) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.mUnixEpochTime, Boolean.valueOf(this.mUserShouldConfirmTime));
    }

    public String toString() {
        return "TimeState{mUnixEpochTime=" + this.mUnixEpochTime + ", mUserShouldConfirmTime=" + this.mUserShouldConfirmTime + '}';
    }
}
