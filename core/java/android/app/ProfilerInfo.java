package android.app;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import java.io.IOException;
import java.util.Objects;

/* loaded from: classes.dex */
public class ProfilerInfo implements Parcelable {
    public static final int CLOCK_TYPE_DEFAULT = 0;
    public static final int CLOCK_TYPE_DUAL = 272;
    public static final int CLOCK_TYPE_THREAD_CPU = 256;
    public static final int CLOCK_TYPE_WALL = 16;
    public static final Parcelable.Creator<ProfilerInfo> CREATOR = new Parcelable.Creator<ProfilerInfo>() { // from class: android.app.ProfilerInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ProfilerInfo createFromParcel(Parcel in) {
            return new ProfilerInfo(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ProfilerInfo[] newArray(int size) {
            return new ProfilerInfo[size];
        }
    };
    public static final int OUTPUT_VERSION_DEFAULT = 1;
    private static final String TAG = "ProfilerInfo";
    public static final int TRACE_FORMAT_VERSION_SHIFT = 1;
    public final String agent;
    public final boolean attachAgentDuringBind;
    public final boolean autoStopProfiler;
    public final int clockType;
    public ParcelFileDescriptor profileFd;
    public final String profileFile;
    public final int profilerOutputVersion;
    public final int samplingInterval;
    public final boolean streamingOutput;

    public ProfilerInfo(String filename, ParcelFileDescriptor fd, int interval, boolean autoStop, boolean streaming, String agent, boolean attachAgentDuringBind, int clockType, int profilerOutputVersion) {
        this.profileFile = filename;
        this.profileFd = fd;
        this.samplingInterval = interval;
        this.autoStopProfiler = autoStop;
        this.streamingOutput = streaming;
        this.clockType = clockType;
        this.agent = agent;
        this.attachAgentDuringBind = attachAgentDuringBind;
        this.profilerOutputVersion = profilerOutputVersion;
    }

    public ProfilerInfo(ProfilerInfo in) {
        this.profileFile = in.profileFile;
        this.profileFd = in.profileFd;
        this.samplingInterval = in.samplingInterval;
        this.autoStopProfiler = in.autoStopProfiler;
        this.streamingOutput = in.streamingOutput;
        this.agent = in.agent;
        this.attachAgentDuringBind = in.attachAgentDuringBind;
        this.clockType = in.clockType;
        this.profilerOutputVersion = in.profilerOutputVersion;
    }

    public static int getClockTypeFromString(String type) {
        if ("thread-cpu".equals(type)) {
            return 256;
        }
        if ("wall".equals(type)) {
            return 16;
        }
        if ("dual".equals(type)) {
            return 272;
        }
        return 0;
    }

    public static int getFlagsForOutputVersion(int version) {
        if (version != 1 || version != 2) {
            version = 1;
        }
        return (version - 1) << 1;
    }

    public ProfilerInfo setAgent(String agent, boolean attachAgentDuringBind) {
        return new ProfilerInfo(this.profileFile, this.profileFd, this.samplingInterval, this.autoStopProfiler, this.streamingOutput, agent, attachAgentDuringBind, this.clockType, this.profilerOutputVersion);
    }

    public void closeFd() {
        if (this.profileFd != null) {
            try {
                this.profileFd.close();
            } catch (IOException e) {
                Slog.w(TAG, "Failure closing profile fd", e);
            }
            this.profileFd = null;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        if (this.profileFd != null) {
            return this.profileFd.describeContents();
        }
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.profileFile);
        if (this.profileFd != null) {
            parcel.writeInt(1);
            this.profileFd.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.samplingInterval);
        parcel.writeInt(this.autoStopProfiler ? 1 : 0);
        parcel.writeInt(this.streamingOutput ? 1 : 0);
        parcel.writeString(this.agent);
        parcel.writeBoolean(this.attachAgentDuringBind);
        parcel.writeInt(this.clockType);
        parcel.writeInt(this.profilerOutputVersion);
    }

    public void dumpDebug(ProtoOutputStream proto, long fieldId) {
        long token = proto.start(fieldId);
        proto.write(1138166333441L, this.profileFile);
        if (this.profileFd != null) {
            proto.write(1120986464258L, this.profileFd.getFd());
        }
        proto.write(1120986464259L, this.samplingInterval);
        proto.write(1133871366148L, this.autoStopProfiler);
        proto.write(1133871366149L, this.streamingOutput);
        proto.write(1138166333446L, this.agent);
        proto.write(1120986464263L, this.clockType);
        proto.write(1120986464264L, this.profilerOutputVersion);
        proto.end(token);
    }

    private ProfilerInfo(Parcel in) {
        this.profileFile = in.readString();
        this.profileFd = in.readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel(in) : null;
        this.samplingInterval = in.readInt();
        this.autoStopProfiler = in.readInt() != 0;
        this.streamingOutput = in.readInt() != 0;
        this.agent = in.readString();
        this.attachAgentDuringBind = in.readBoolean();
        this.clockType = in.readInt();
        this.profilerOutputVersion = in.readInt();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProfilerInfo other = (ProfilerInfo) o;
        if (Objects.equals(this.profileFile, other.profileFile) && this.autoStopProfiler == other.autoStopProfiler && this.samplingInterval == other.samplingInterval && this.streamingOutput == other.streamingOutput && Objects.equals(this.agent, other.agent) && this.clockType == other.clockType && this.profilerOutputVersion == other.profilerOutputVersion) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (((((((((((((17 * 31) + Objects.hashCode(this.profileFile)) * 31) + this.samplingInterval) * 31) + (this.autoStopProfiler ? 1 : 0)) * 31) + (this.streamingOutput ? 1 : 0)) * 31) + Objects.hashCode(this.agent)) * 31) + this.clockType) * 31) + this.profilerOutputVersion;
    }
}
