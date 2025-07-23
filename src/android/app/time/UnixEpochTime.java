package android.app.time;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.ShellCommand;
import java.io.PrintWriter;
import java.util.Objects;

@SystemApi
/* loaded from: classes.dex */
public final class UnixEpochTime implements Parcelable {
    public static final Parcelable.Creator<UnixEpochTime> CREATOR = new Parcelable.Creator<UnixEpochTime>() { // from class: android.app.time.UnixEpochTime.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UnixEpochTime createFromParcel(Parcel parcel) {
            return new UnixEpochTime(parcel.readLong(), parcel.readLong());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UnixEpochTime[] newArray(int i) {
            return new UnixEpochTime[i];
        }
    };
    private final long mElapsedRealtimeMillis;
    private final long mUnixEpochTimeMillis;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public UnixEpochTime(long j, long j2) {
        this.mElapsedRealtimeMillis = j;
        this.mUnixEpochTimeMillis = j2;
    }

    public static UnixEpochTime parseCommandLineArgs(ShellCommand shellCommand) {
        Long l = null;
        Long l2 = null;
        while (true) {
            String nextArg = shellCommand.getNextArg();
            if (nextArg == null) {
                if (l == null) {
                    throw new IllegalArgumentException("No elapsedRealtimeMillis specified.");
                }
                if (l2 == null) {
                    throw new IllegalArgumentException("No unixEpochTimeMillis specified.");
                }
                return new UnixEpochTime(l.longValue(), l2.longValue());
            }
            nextArg.hashCode();
            if (nextArg.equals("--elapsed_realtime")) {
                l = Long.valueOf(Long.parseLong(shellCommand.getNextArgRequired()));
            } else if (nextArg.equals("--unix_epoch_time")) {
                l2 = Long.valueOf(Long.parseLong(shellCommand.getNextArgRequired()));
            } else {
                throw new IllegalArgumentException("Unknown option: " + nextArg);
            }
        }
    }

    public static void printCommandLineOpts(PrintWriter printWriter) {
        printWriter.println("UnixEpochTime options:\n");
        printWriter.println("  --elapsed_realtime <elapsed realtime millis>");
        printWriter.println("  --unix_epoch_time <Unix epoch time millis>");
        printWriter.println();
        printWriter.println("See " + UnixEpochTime.class.getName() + " for more information");
    }

    public long getElapsedRealtimeMillis() {
        return this.mElapsedRealtimeMillis;
    }

    public long getUnixEpochTimeMillis() {
        return this.mUnixEpochTimeMillis;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            UnixEpochTime unixEpochTime = (UnixEpochTime) obj;
            if (this.mElapsedRealtimeMillis == unixEpochTime.mElapsedRealtimeMillis && this.mUnixEpochTimeMillis == unixEpochTime.mUnixEpochTimeMillis) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Long.valueOf(this.mElapsedRealtimeMillis), Long.valueOf(this.mUnixEpochTimeMillis));
    }

    public String toString() {
        return "UnixEpochTime{mElapsedRealtimeMillis=" + this.mElapsedRealtimeMillis + ", mUnixEpochTimeMillis=" + this.mUnixEpochTimeMillis + '}';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.mElapsedRealtimeMillis);
        parcel.writeLong(this.mUnixEpochTimeMillis);
    }

    public UnixEpochTime at(long j) {
        return new UnixEpochTime(j, (j - this.mElapsedRealtimeMillis) + this.mUnixEpochTimeMillis);
    }

    public static long elapsedRealtimeDifference(UnixEpochTime unixEpochTime, UnixEpochTime unixEpochTime2) {
        return unixEpochTime.mElapsedRealtimeMillis - unixEpochTime2.mElapsedRealtimeMillis;
    }
}
