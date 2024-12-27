package android.media;

import android.annotation.SystemApi;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;

import java.io.PrintWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class AudioPlaybackConfiguration implements Parcelable {
    private static final boolean DEBUG = false;
    public static final String EXTRA_PLAYER_EVENT_CHANNEL_MASK =
            "android.media.extra.PLAYER_EVENT_CHANNEL_MASK";
    public static final String EXTRA_PLAYER_EVENT_MUTE = "android.media.extra.PLAYER_EVENT_MUTE";
    public static final String EXTRA_PLAYER_EVENT_SAMPLE_RATE =
            "android.media.extra.PLAYER_EVENT_SAMPLE_RATE";
    public static final String EXTRA_PLAYER_EVENT_SPATIALIZED =
            "android.media.extra.PLAYER_EVENT_SPATIALIZED";

    @SystemApi public static final int MUTED_BY_APP_OPS = 8;

    @SystemApi public static final int MUTED_BY_CLIENT_VOLUME = 16;

    @SystemApi public static final int MUTED_BY_MASTER = 1;

    @SystemApi public static final int MUTED_BY_STREAM_MUTED = 4;

    @SystemApi public static final int MUTED_BY_STREAM_VOLUME = 2;

    @SystemApi public static final int MUTED_BY_VOLUME_SHAPER = 32;
    public static final int PLAYER_DEVICEID_INVALID = 0;
    public static final int PLAYER_PIID_INVALID = -1;

    @SystemApi public static final int PLAYER_STATE_IDLE = 1;

    @SystemApi public static final int PLAYER_STATE_PAUSED = 3;

    @SystemApi public static final int PLAYER_STATE_RELEASED = 0;

    @SystemApi public static final int PLAYER_STATE_STARTED = 2;

    @SystemApi public static final int PLAYER_STATE_STOPPED = 4;

    @SystemApi public static final int PLAYER_STATE_UNKNOWN = -1;

    @SystemApi public static final int PLAYER_TYPE_AAUDIO = 13;
    public static final int PLAYER_TYPE_EXTERNAL_PROXY = 15;
    public static final int PLAYER_TYPE_HW_SOURCE = 14;

    @SystemApi public static final int PLAYER_TYPE_JAM_AUDIOTRACK = 1;

    @SystemApi public static final int PLAYER_TYPE_JAM_MEDIAPLAYER = 2;

    @SystemApi public static final int PLAYER_TYPE_JAM_SOUNDPOOL = 3;

    @SystemApi public static final int PLAYER_TYPE_SLES_AUDIOPLAYER_BUFFERQUEUE = 11;

    @SystemApi public static final int PLAYER_TYPE_SLES_AUDIOPLAYER_URI_FD = 12;

    @SystemApi public static final int PLAYER_TYPE_UNKNOWN = -1;
    public static final int PLAYER_UPDATE_DEVICE_ID = 5;
    public static final int PLAYER_UPDATE_FORMAT = 8;
    public static final int PLAYER_UPDATE_MUTED = 7;
    public static final int PLAYER_UPDATE_PORT_ID = 6;
    public static final int PLAYER_UPID_INVALID = -1;
    public static final int SEM_PLAYER_STATE_IDLE = 1;
    public static final int SEM_PLAYER_STATE_PAUSED = 3;
    public static final int SEM_PLAYER_STATE_RELEASED = 0;
    public static final int SEM_PLAYER_STATE_STARTED = 2;
    public static final int SEM_PLAYER_STATE_STOPPED = 4;
    public static final int SEM_PLAYER_STATE_UNKNOWN = -1;
    public static final int SEM_PLAYER_TYPE_AAUDIO = 13;
    public static final int SEM_PLAYER_TYPE_EXTERNAL_PROXY = 15;
    public static final int SEM_PLAYER_TYPE_HW_SOURCE = 14;
    public static final int SEM_PLAYER_TYPE_JAM_AUDIOTRACK = 1;
    public static final int SEM_PLAYER_TYPE_JAM_MEDIAPLAYER = 2;
    public static final int SEM_PLAYER_TYPE_JAM_SOUNDPOOL = 3;
    public static final int SEM_PLAYER_TYPE_SLES_AUDIOPLAYER_BUFFERQUEUE = 11;
    public static final int SEM_PLAYER_TYPE_SLES_AUDIOPLAYER_URI_FD = 12;
    public static final int SEM_PLAYER_TYPE_UNKNOWN = -1;
    public static PlayerDeathMonitor sPlayerDeathMonitor;
    private int mClientPid;
    private int mClientUid;
    private int mDeviceId;
    private FormatInfo mFormatInfo;
    private IPlayerShell mIPlayerShell;
    private int mMutedState;
    private AudioAttributes mPlayerAttr;
    private final int mPlayerIId;
    private int mPlayerState;
    private int mPlayerType;
    private int mSessionId;
    private final Object mUpdateablePropLock;
    private static final String TAG = new String("AudioPlaybackConfiguration");
    public static final Parcelable.Creator<AudioPlaybackConfiguration> CREATOR =
            new Parcelable.Creator<AudioPlaybackConfiguration>() { // from class:
                // android.media.AudioPlaybackConfiguration.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public AudioPlaybackConfiguration createFromParcel(Parcel p) {
                    return new AudioPlaybackConfiguration(p);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public AudioPlaybackConfiguration[] newArray(int size) {
                    return new AudioPlaybackConfiguration[size];
                }
            };

    public interface PlayerDeathMonitor {
        void playerDeath(int i);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PlayerMuteEvent {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface PlayerState {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface PlayerType {}

    public static String playerStateToString(int state) {
        switch (state) {
            case -1:
                return "PLAYER_STATE_UNKNOWN";
            case 0:
                return "PLAYER_STATE_RELEASED";
            case 1:
                return "PLAYER_STATE_IDLE";
            case 2:
                return "PLAYER_STATE_STARTED";
            case 3:
                return "PLAYER_STATE_PAUSED";
            case 4:
                return "PLAYER_STATE_STOPPED";
            case 5:
                return "PLAYER_UPDATE_DEVICE_ID";
            case 6:
                return "PLAYER_UPDATE_PORT_ID";
            case 7:
                return "PLAYER_UPDATE_MUTED";
            case 8:
                return "PLAYER_UPDATE_FORMAT";
            default:
                return "invalid state " + state;
        }
    }

    private AudioPlaybackConfiguration(int piid) {
        this.mUpdateablePropLock = new Object();
        this.mPlayerIId = piid;
        this.mIPlayerShell = null;
    }

    public AudioPlaybackConfiguration(PlayerBase.PlayerIdCard pic, int piid, int uid, int pid) {
        this.mUpdateablePropLock = new Object();
        this.mPlayerIId = piid;
        this.mPlayerType = pic.mPlayerType;
        this.mClientUid = uid;
        this.mClientPid = pid;
        this.mMutedState = 0;
        this.mDeviceId = 0;
        this.mPlayerState = 1;
        this.mPlayerAttr = pic.mAttributes;
        if (sPlayerDeathMonitor != null && pic.mIPlayer != null) {
            this.mIPlayerShell = new IPlayerShell(this, pic.mIPlayer);
        } else {
            this.mIPlayerShell = null;
        }
        this.mSessionId = pic.mSessionId;
        this.mFormatInfo = FormatInfo.DEFAULT;
    }

    public void init() {
        synchronized (this) {
            if (this.mIPlayerShell != null) {
                this.mIPlayerShell.monitorDeath();
            }
        }
    }

    private void setUpdateableFields(
            int deviceId, int sessionId, int mutedState, FormatInfo format) {
        synchronized (this.mUpdateablePropLock) {
            this.mDeviceId = deviceId;
            this.mSessionId = sessionId;
            this.mMutedState = mutedState;
            this.mFormatInfo = format;
        }
    }

    public static AudioPlaybackConfiguration anonymizedCopy(AudioPlaybackConfiguration in) {
        AudioPlaybackConfiguration anonymCopy = new AudioPlaybackConfiguration(in.mPlayerIId);
        anonymCopy.mPlayerState = in.mPlayerState;
        AudioAttributes.Builder builder =
                new AudioAttributes.Builder()
                        .setContentType(in.mPlayerAttr.getContentType())
                        .setFlags(in.mPlayerAttr.getFlags())
                        .setAllowedCapturePolicy(
                                in.mPlayerAttr.getAllowedCapturePolicy() != 1 ? 3 : 1);
        if (AudioAttributes.isSystemUsage(in.mPlayerAttr.getSystemUsage())) {
            builder.setSystemUsage(in.mPlayerAttr.getSystemUsage());
        } else {
            builder.setUsage(in.mPlayerAttr.getUsage());
        }
        anonymCopy.mPlayerAttr = builder.build();
        anonymCopy.mPlayerType = -1;
        anonymCopy.mClientUid = -1;
        anonymCopy.mClientPid = -1;
        anonymCopy.mIPlayerShell = null;
        anonymCopy.setUpdateableFields(0, 0, 0, FormatInfo.DEFAULT);
        return anonymCopy;
    }

    public AudioAttributes getAudioAttributes() {
        return this.mPlayerAttr;
    }

    @SystemApi
    public int getClientUid() {
        return this.mClientUid;
    }

    @SystemApi
    public int getClientPid() {
        return this.mClientPid;
    }

    public AudioDeviceInfo getAudioDeviceInfo() {
        int deviceId;
        synchronized (this.mUpdateablePropLock) {
            deviceId = this.mDeviceId;
        }
        if (deviceId == 0) {
            return null;
        }
        return AudioManager.getDeviceForPortId(deviceId, 2);
    }

    @SystemApi
    public int getSessionId() {
        int i;
        synchronized (this.mUpdateablePropLock) {
            i = this.mSessionId;
        }
        return i;
    }

    @SystemApi
    public boolean isMuted() {
        boolean z;
        synchronized (this.mUpdateablePropLock) {
            z = this.mMutedState != 0;
        }
        return z;
    }

    @SystemApi
    public int getMutedBy() {
        int i;
        synchronized (this.mUpdateablePropLock) {
            i = this.mMutedState;
        }
        return i;
    }

    @SystemApi
    public int getPlayerType() {
        switch (this.mPlayerType) {
            case 14:
            case 15:
                return -1;
            default:
                return this.mPlayerType;
        }
    }

    @SystemApi
    public int getPlayerState() {
        return this.mPlayerState;
    }

    @SystemApi
    public int getPlayerInterfaceId() {
        return this.mPlayerIId;
    }

    @SystemApi
    public PlayerProxy getPlayerProxy() {
        IPlayerShell ips;
        synchronized (this) {
            ips = this.mIPlayerShell;
        }
        if (ips == null) {
            return null;
        }
        return new PlayerProxy(this);
    }

    @SystemApi
    public boolean isSpatialized() {
        boolean z;
        synchronized (this.mUpdateablePropLock) {
            z = this.mFormatInfo.mIsSpatialized;
        }
        return z;
    }

    @SystemApi
    public int getSampleRate() {
        int i;
        synchronized (this.mUpdateablePropLock) {
            i = this.mFormatInfo.mSampleRate;
        }
        return i;
    }

    @SystemApi
    public int getChannelMask() {
        int convertNativeChannelMaskToOutMask;
        synchronized (this.mUpdateablePropLock) {
            convertNativeChannelMaskToOutMask =
                    AudioFormat.convertNativeChannelMaskToOutMask(
                            this.mFormatInfo.mNativeChannelMask);
        }
        return convertNativeChannelMaskToOutMask;
    }

    IPlayer getIPlayer() {
        IPlayerShell ips;
        synchronized (this) {
            ips = this.mIPlayerShell;
        }
        if (ips == null) {
            return null;
        }
        return ips.getIPlayer();
    }

    public boolean handleAudioAttributesEvent(AudioAttributes attr) {
        boolean changed = !attr.equals(this.mPlayerAttr);
        this.mPlayerAttr = attr;
        return changed;
    }

    public boolean handleSessionIdEvent(int sessionId) {
        boolean changed;
        synchronized (this.mUpdateablePropLock) {
            changed = sessionId != this.mSessionId;
            this.mSessionId = sessionId;
        }
        return changed;
    }

    public boolean handleMutedEvent(int mutedState) {
        boolean changed;
        synchronized (this.mUpdateablePropLock) {
            changed = this.mMutedState != mutedState;
            this.mMutedState = mutedState;
        }
        return changed;
    }

    public boolean handleFormatEvent(FormatInfo fi) {
        boolean changed;
        synchronized (this.mUpdateablePropLock) {
            changed = !this.mFormatInfo.equals(fi);
            this.mFormatInfo = fi;
        }
        return changed;
    }

    public boolean handleStateEvent(int event, int deviceId) {
        boolean changed = false;
        synchronized (this.mUpdateablePropLock) {
            if (event != 5) {
                try {
                    changed = this.mPlayerState != event;
                    this.mPlayerState = event;
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (event == 2 || event == 5) {
                changed = changed || this.mDeviceId != deviceId;
                this.mDeviceId = deviceId;
            }
            if (changed && event == 0 && this.mIPlayerShell != null) {
                this.mIPlayerShell.release();
                this.mIPlayerShell = null;
            }
        }
        return changed;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void playerDied() {
        if (sPlayerDeathMonitor != null) {
            sPlayerDeathMonitor.playerDeath(this.mPlayerIId);
        }
    }

    private boolean isMuteAffectingActiveState() {
        return ((this.mMutedState & 16) == 0
                        && (this.mMutedState & 32) == 0
                        && (this.mMutedState & 8) == 0)
                ? false
                : true;
    }

    @SystemApi
    public boolean isActive() {
        switch (this.mPlayerState) {
            case 2:
                return !isMuteAffectingActiveState();
            default:
                return false;
        }
    }

    public void dump(PrintWriter pw) {
        pw.println("  " + this);
    }

    public int hashCode() {
        int hash;
        synchronized (this.mUpdateablePropLock) {
            hash =
                    Objects.hash(
                            Integer.valueOf(this.mPlayerIId),
                            Integer.valueOf(this.mDeviceId),
                            Integer.valueOf(this.mMutedState),
                            Integer.valueOf(this.mPlayerType),
                            Integer.valueOf(this.mClientUid),
                            Integer.valueOf(this.mClientPid),
                            Integer.valueOf(this.mSessionId));
        }
        return hash;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        IPlayerShell ips;
        synchronized (this.mUpdateablePropLock) {
            dest.writeInt(this.mPlayerIId);
            dest.writeInt(this.mDeviceId);
            dest.writeInt(this.mMutedState);
            dest.writeInt(this.mPlayerType);
            dest.writeInt(this.mClientUid);
            dest.writeInt(this.mClientPid);
            dest.writeInt(this.mPlayerState);
            this.mPlayerAttr.writeToParcel(dest, 0);
            synchronized (this) {
                ips = this.mIPlayerShell;
            }
            dest.writeStrongInterface(ips == null ? null : ips.getIPlayer());
            dest.writeInt(this.mSessionId);
            this.mFormatInfo.writeToParcel(dest, 0);
        }
    }

    private AudioPlaybackConfiguration(Parcel in) {
        this.mUpdateablePropLock = new Object();
        this.mPlayerIId = in.readInt();
        this.mDeviceId = in.readInt();
        this.mMutedState = in.readInt();
        this.mPlayerType = in.readInt();
        this.mClientUid = in.readInt();
        this.mClientPid = in.readInt();
        this.mPlayerState = in.readInt();
        this.mPlayerAttr = AudioAttributes.CREATOR.createFromParcel(in);
        IPlayer p = IPlayer.Stub.asInterface(in.readStrongBinder());
        this.mIPlayerShell = p != null ? new IPlayerShell(null, p) : null;
        this.mSessionId = in.readInt();
        this.mFormatInfo = FormatInfo.CREATOR.createFromParcel(in);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !(o instanceof AudioPlaybackConfiguration)) {
            return false;
        }
        AudioPlaybackConfiguration that = (AudioPlaybackConfiguration) o;
        if (this.mPlayerIId == that.mPlayerIId
                && this.mDeviceId == that.mDeviceId
                && this.mMutedState == that.mMutedState
                && this.mPlayerType == that.mPlayerType
                && this.mClientUid == that.mClientUid
                && this.mClientPid == that.mClientPid
                && this.mSessionId == that.mSessionId) {
            return true;
        }
        return false;
    }

    public String toString() {
        StringBuilder apcToString = new StringBuilder();
        synchronized (this.mUpdateablePropLock) {
            apcToString
                    .append("AudioPlaybackConfiguration piid:")
                    .append(this.mPlayerIId)
                    .append(" deviceId:")
                    .append(this.mDeviceId)
                    .append(" type:")
                    .append(toLogFriendlyPlayerType(this.mPlayerType))
                    .append(" u/pid:")
                    .append(this.mClientUid)
                    .append("/")
                    .append(this.mClientPid)
                    .append(" state:")
                    .append(toLogFriendlyPlayerState(this.mPlayerState))
                    .append(" attr:")
                    .append(this.mPlayerAttr)
                    .append(" sessionId:")
                    .append(this.mSessionId)
                    .append(" mutedState:");
            if (this.mMutedState == 0) {
                apcToString.append("none ");
            } else {
                if ((this.mMutedState & 1) != 0) {
                    apcToString.append("master ");
                }
                if ((this.mMutedState & 2) != 0) {
                    apcToString.append("streamVolume ");
                }
                if ((this.mMutedState & 4) != 0) {
                    apcToString.append("streamMute ");
                }
                if ((this.mMutedState & 8) != 0) {
                    apcToString.append("appOps ");
                }
                if ((this.mMutedState & 16) != 0) {
                    apcToString.append("clientVolume ");
                }
                if ((this.mMutedState & 32) != 0) {
                    apcToString.append("volumeShaper ");
                }
            }
            apcToString.append(" ").append(this.mFormatInfo);
        }
        return apcToString.toString();
    }

    static final class IPlayerShell implements IBinder.DeathRecipient {
        private volatile IPlayer mIPlayer;
        final AudioPlaybackConfiguration mMonitor;

        IPlayerShell(AudioPlaybackConfiguration monitor, IPlayer iplayer) {
            this.mMonitor = monitor;
            this.mIPlayer = iplayer;
        }

        synchronized void monitorDeath() {
            if (this.mIPlayer == null) {
                return;
            }
            try {
                this.mIPlayer.asBinder().linkToDeath(this, 0);
            } catch (RemoteException e) {
                if (this.mMonitor != null) {
                    Log.w(
                            AudioPlaybackConfiguration.TAG,
                            "Could not link to client death for piid=" + this.mMonitor.mPlayerIId,
                            e);
                } else {
                    Log.w(AudioPlaybackConfiguration.TAG, "Could not link to client death", e);
                }
            }
        }

        IPlayer getIPlayer() {
            return this.mIPlayer;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            if (this.mMonitor != null) {
                this.mMonitor.playerDied();
            }
        }

        synchronized void release() {
            if (this.mIPlayer == null) {
                return;
            }
            this.mIPlayer.asBinder().unlinkToDeath(this, 0);
            this.mIPlayer = null;
            Binder.flushPendingCommands();
        }
    }

    public static final class FormatInfo implements Parcelable {
        final boolean mIsSpatialized;
        final int mNativeChannelMask;
        final int mSampleRate;
        static final FormatInfo DEFAULT = new FormatInfo(false, 0, 0);
        public static final Parcelable.Creator<FormatInfo> CREATOR =
                new Parcelable.Creator<FormatInfo>() { // from class:
                    // android.media.AudioPlaybackConfiguration.FormatInfo.1
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public FormatInfo createFromParcel(Parcel p) {
                        return new FormatInfo(p);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public FormatInfo[] newArray(int size) {
                        return new FormatInfo[size];
                    }
                };

        public FormatInfo(boolean isSpatialized, int nativeChannelMask, int sampleRate) {
            this.mIsSpatialized = isSpatialized;
            this.mNativeChannelMask = nativeChannelMask;
            this.mSampleRate = sampleRate;
        }

        public String toString() {
            return "FormatInfo{isSpatialized="
                    + this.mIsSpatialized
                    + ", channelMask=0x"
                    + Integer.toHexString(this.mNativeChannelMask)
                    + ", sampleRate="
                    + this.mSampleRate
                    + '}';
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof FormatInfo)) {
                return false;
            }
            FormatInfo that = (FormatInfo) o;
            return this.mIsSpatialized == that.mIsSpatialized
                    && this.mNativeChannelMask == that.mNativeChannelMask
                    && this.mSampleRate == that.mSampleRate;
        }

        public int hashCode() {
            return Objects.hash(
                    Boolean.valueOf(this.mIsSpatialized),
                    Integer.valueOf(this.mNativeChannelMask),
                    Integer.valueOf(this.mSampleRate));
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeBoolean(this.mIsSpatialized);
            dest.writeInt(this.mNativeChannelMask);
            dest.writeInt(this.mSampleRate);
        }

        private FormatInfo(Parcel in) {
            this(in.readBoolean(), in.readInt(), in.readInt());
        }
    }

    public static String toLogFriendlyPlayerType(int type) {
        switch (type) {
            case -1:
                return "unknown";
            case 0:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            default:
                return "unknown player type " + type + " - FIXME";
            case 1:
                return "android.media.AudioTrack";
            case 2:
                return "android.media.MediaPlayer";
            case 3:
                return "android.media.SoundPool";
            case 11:
                return "OpenSL ES AudioPlayer (Buffer Queue)";
            case 12:
                return "OpenSL ES AudioPlayer (URI/FD)";
            case 13:
                return "AAudio";
            case 14:
                return "hardware source";
            case 15:
                return "external proxy";
        }
    }

    public static String toLogFriendlyPlayerState(int state) {
        switch (state) {
            case -1:
                return "unknown";
            case 0:
                return "released";
            case 1:
                return "idle";
            case 2:
                return "started";
            case 3:
                return "paused";
            case 4:
                return "stopped";
            case 5:
                return "device updated";
            case 6:
                return "port updated";
            case 7:
                return "muted updated";
            default:
                return "unknown player state - FIXME";
        }
    }

    public int semGetPlayerType() {
        return getPlayerType();
    }

    public int semGetClientUid() {
        return getClientUid();
    }

    public int semGetClientPid() {
        return getClientPid();
    }

    public int semGetPlayerState() {
        return getPlayerState();
    }
}
