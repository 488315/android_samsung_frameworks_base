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
        public ProfilerInfo createFromParcel(Parcel parcel) {
            return new ProfilerInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ProfilerInfo[] newArray(int i) {
            return new ProfilerInfo[i];
        }
    };
    public static final int OUTPUT_VERSION_DEFAULT = 1;
    public static final int PROFILE_TYPE_LOW_OVERHEAD = 1;
    public static final int PROFILE_TYPE_REGULAR = 0;
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

    public static int getFlagsForOutputVersion(int i) {
        if (i != 1 || i != 2) {
            i = 1;
        }
        return (i - 1) << 1;
    }

    public ProfilerInfo(String str, ParcelFileDescriptor parcelFileDescriptor, int i, boolean z, boolean z2, String str2, boolean z3, int i2, int i3) {
        this.profileFile = str;
        this.profileFd = parcelFileDescriptor;
        this.samplingInterval = i;
        this.autoStopProfiler = z;
        this.streamingOutput = z2;
        this.clockType = i2;
        this.agent = str2;
        this.attachAgentDuringBind = z3;
        this.profilerOutputVersion = i3;
    }

    public ProfilerInfo(ProfilerInfo profilerInfo) {
        this.profileFile = profilerInfo.profileFile;
        this.profileFd = profilerInfo.profileFd;
        this.samplingInterval = profilerInfo.samplingInterval;
        this.autoStopProfiler = profilerInfo.autoStopProfiler;
        this.streamingOutput = profilerInfo.streamingOutput;
        this.agent = profilerInfo.agent;
        this.attachAgentDuringBind = profilerInfo.attachAgentDuringBind;
        this.clockType = profilerInfo.clockType;
        this.profilerOutputVersion = profilerInfo.profilerOutputVersion;
    }

    public static int getClockTypeFromString(String str) {
        if ("thread-cpu".equals(str)) {
            return 256;
        }
        if ("wall".equals(str)) {
            return 16;
        }
        return "dual".equals(str) ? 272 : 0;
    }

    public ProfilerInfo setAgent(String str, boolean z) {
        return new ProfilerInfo(this.profileFile, this.profileFd, this.samplingInterval, this.autoStopProfiler, this.streamingOutput, str, z, this.clockType, this.profilerOutputVersion);
    }

    public void closeFd() {
        ParcelFileDescriptor parcelFileDescriptor = this.profileFd;
        if (parcelFileDescriptor != null) {
            try {
                parcelFileDescriptor.close();
            } catch (IOException e) {
                Slog.w(TAG, "Failure closing profile fd", e);
            }
            this.profileFd = null;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        ParcelFileDescriptor parcelFileDescriptor = this.profileFd;
        if (parcelFileDescriptor != null) {
            return parcelFileDescriptor.describeContents();
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

    public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1138166333441L, this.profileFile);
        ParcelFileDescriptor parcelFileDescriptor = this.profileFd;
        if (parcelFileDescriptor != null) {
            protoOutputStream.write(1120986464258L, parcelFileDescriptor.getFd());
        }
        protoOutputStream.write(1120986464259L, this.samplingInterval);
        protoOutputStream.write(1133871366148L, this.autoStopProfiler);
        protoOutputStream.write(1133871366149L, this.streamingOutput);
        protoOutputStream.write(1138166333446L, this.agent);
        protoOutputStream.write(1120986464263L, this.clockType);
        protoOutputStream.write(1120986464264L, this.profilerOutputVersion);
        protoOutputStream.end(start);
    }

    private ProfilerInfo(Parcel parcel) {
        this.profileFile = parcel.readString();
        this.profileFd = parcel.readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel(parcel) : null;
        this.samplingInterval = parcel.readInt();
        this.autoStopProfiler = parcel.readInt() != 0;
        this.streamingOutput = parcel.readInt() != 0;
        this.agent = parcel.readString();
        this.attachAgentDuringBind = parcel.readBoolean();
        this.clockType = parcel.readInt();
        this.profilerOutputVersion = parcel.readInt();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            ProfilerInfo profilerInfo = (ProfilerInfo) obj;
            if (Objects.equals(this.profileFile, profilerInfo.profileFile) && this.autoStopProfiler == profilerInfo.autoStopProfiler && this.samplingInterval == profilerInfo.samplingInterval && this.streamingOutput == profilerInfo.streamingOutput && Objects.equals(this.agent, profilerInfo.agent) && this.clockType == profilerInfo.clockType && this.profilerOutputVersion == profilerInfo.profilerOutputVersion) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((((((((((((527 + Objects.hashCode(this.profileFile)) * 31) + this.samplingInterval) * 31) + (this.autoStopProfiler ? 1 : 0)) * 31) + (this.streamingOutput ? 1 : 0)) * 31) + Objects.hashCode(this.agent)) * 31) + this.clockType) * 31) + this.profilerOutputVersion;
    }
}
