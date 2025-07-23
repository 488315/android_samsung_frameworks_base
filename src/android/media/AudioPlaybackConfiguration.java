package android.media;

import android.annotation.SystemApi;
import android.media.AudioAttributes;
import android.media.IPlayer;
import android.media.PlayerBase;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;
import java.io.PrintWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class AudioPlaybackConfiguration implements Parcelable {
    private static final boolean DEBUG = false;
    public static final String EXTRA_PLAYER_EVENT_CHANNEL_MASK = "android.media.extra.PLAYER_EVENT_CHANNEL_MASK";
    public static final String EXTRA_PLAYER_EVENT_MUTE = "android.media.extra.PLAYER_EVENT_MUTE";
    public static final String EXTRA_PLAYER_EVENT_SAMPLE_RATE = "android.media.extra.PLAYER_EVENT_SAMPLE_RATE";
    public static final String EXTRA_PLAYER_EVENT_SPATIALIZED = "android.media.extra.PLAYER_EVENT_SPATIALIZED";

    @SystemApi
    @Deprecated
    public static final int MUTED_BY_APP_OPS = 8;

    @SystemApi
    public static final int MUTED_BY_CLIENT_VOLUME = 16;

    @SystemApi
    public static final int MUTED_BY_MASTER = 1;

    @SystemApi
    public static final int MUTED_BY_OP_CONTROL_AUDIO = 128;

    @SystemApi
    public static final int MUTED_BY_OP_PLAY_AUDIO = 8;

    @SystemApi
    public static final int MUTED_BY_PORT_VOLUME = 64;

    @SystemApi
    public static final int MUTED_BY_STREAM_MUTED = 4;

    @SystemApi
    public static final int MUTED_BY_STREAM_VOLUME = 2;

    @SystemApi
    public static final int MUTED_BY_VOLUME_SHAPER = 32;
    public static final int PLAYER_DEVICEID_INVALID = 0;
    public static final int PLAYER_PIID_INVALID = -1;

    @SystemApi
    public static final int PLAYER_STATE_IDLE = 1;

    @SystemApi
    public static final int PLAYER_STATE_PAUSED = 3;

    @SystemApi
    public static final int PLAYER_STATE_RELEASED = 0;

    @SystemApi
    public static final int PLAYER_STATE_STARTED = 2;

    @SystemApi
    public static final int PLAYER_STATE_STOPPED = 4;

    @SystemApi
    public static final int PLAYER_STATE_UNKNOWN = -1;

    @SystemApi
    public static final int PLAYER_TYPE_AAUDIO = 13;
    public static final int PLAYER_TYPE_EXTERNAL_PROXY = 15;
    public static final int PLAYER_TYPE_HW_SOURCE = 14;

    @SystemApi
    public static final int PLAYER_TYPE_JAM_AUDIOTRACK = 1;

    @SystemApi
    public static final int PLAYER_TYPE_JAM_MEDIAPLAYER = 2;

    @SystemApi
    public static final int PLAYER_TYPE_JAM_SOUNDPOOL = 3;

    @SystemApi
    public static final int PLAYER_TYPE_SLES_AUDIOPLAYER_BUFFERQUEUE = 11;

    @SystemApi
    public static final int PLAYER_TYPE_SLES_AUDIOPLAYER_URI_FD = 12;

    @SystemApi
    public static final int PLAYER_TYPE_UNKNOWN = -1;
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
    private int[] mDeviceIds;
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
    public static final int[] PLAYER_DEVICEIDS_INVALID = new int[0];
    public static final Parcelable.Creator<AudioPlaybackConfiguration> CREATOR = new Parcelable.Creator<AudioPlaybackConfiguration>() { // from class: android.media.AudioPlaybackConfiguration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioPlaybackConfiguration createFromParcel(Parcel parcel) {
            return new AudioPlaybackConfiguration(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioPlaybackConfiguration[] newArray(int i) {
            return new AudioPlaybackConfiguration[i];
        }
    };

    public interface PlayerDeathMonitor {
        void playerDeath(int i);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PlayerMuteEvent {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PlayerState {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PlayerType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static String playerStateToString(int i) {
        switch (i) {
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
                return "invalid state " + i;
        }
    }

    private AudioPlaybackConfiguration(int i) {
        this.mUpdateablePropLock = new Object();
        this.mDeviceIds = PLAYER_DEVICEIDS_INVALID;
        this.mPlayerIId = i;
        this.mIPlayerShell = null;
    }

    public AudioPlaybackConfiguration(PlayerBase.PlayerIdCard playerIdCard, int i, int i2, int i3) {
        this.mUpdateablePropLock = new Object();
        int[] iArr = PLAYER_DEVICEIDS_INVALID;
        this.mDeviceIds = iArr;
        this.mPlayerIId = i;
        this.mPlayerType = playerIdCard.mPlayerType;
        this.mClientUid = i2;
        this.mClientPid = i3;
        this.mMutedState = 0;
        this.mDeviceIds = iArr;
        this.mPlayerState = 1;
        this.mPlayerAttr = playerIdCard.mAttributes;
        if (sPlayerDeathMonitor != null && playerIdCard.mIPlayer != null) {
            this.mIPlayerShell = new IPlayerShell(this, playerIdCard.mIPlayer);
        } else {
            this.mIPlayerShell = null;
        }
        this.mSessionId = playerIdCard.mSessionId;
        this.mFormatInfo = FormatInfo.DEFAULT;
    }

    public void init() {
        synchronized (this) {
            IPlayerShell iPlayerShell = this.mIPlayerShell;
            if (iPlayerShell != null) {
                iPlayerShell.monitorDeath();
            }
        }
    }

    private void setUpdateableFields(int[] iArr, int i, int i2, FormatInfo formatInfo) {
        synchronized (this.mUpdateablePropLock) {
            this.mDeviceIds = iArr;
            this.mSessionId = i;
            this.mMutedState = i2;
            this.mFormatInfo = formatInfo;
        }
    }

    public static AudioPlaybackConfiguration anonymizedCopy(AudioPlaybackConfiguration audioPlaybackConfiguration) {
        AudioPlaybackConfiguration audioPlaybackConfiguration2 = new AudioPlaybackConfiguration(audioPlaybackConfiguration.mPlayerIId);
        audioPlaybackConfiguration2.mPlayerState = audioPlaybackConfiguration.mPlayerState;
        AudioAttributes.Builder allowedCapturePolicy = new AudioAttributes.Builder().setContentType(audioPlaybackConfiguration.mPlayerAttr.getContentType()).setFlags(audioPlaybackConfiguration.mPlayerAttr.getFlags()).setAllowedCapturePolicy(audioPlaybackConfiguration.mPlayerAttr.getAllowedCapturePolicy() != 1 ? 3 : 1);
        if (AudioAttributes.isSystemUsage(audioPlaybackConfiguration.mPlayerAttr.getSystemUsage())) {
            allowedCapturePolicy.setSystemUsage(audioPlaybackConfiguration.mPlayerAttr.getSystemUsage());
        } else {
            allowedCapturePolicy.setUsage(audioPlaybackConfiguration.mPlayerAttr.getUsage());
        }
        audioPlaybackConfiguration2.mPlayerAttr = allowedCapturePolicy.build();
        audioPlaybackConfiguration2.mPlayerType = -1;
        audioPlaybackConfiguration2.mClientUid = -1;
        audioPlaybackConfiguration2.mClientPid = -1;
        audioPlaybackConfiguration2.mIPlayerShell = null;
        audioPlaybackConfiguration2.setUpdateableFields(new int[0], 0, 0, FormatInfo.DEFAULT);
        return audioPlaybackConfiguration2;
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

    @Deprecated
    public AudioDeviceInfo getAudioDeviceInfo() {
        int[] iArr;
        synchronized (this.mUpdateablePropLock) {
            iArr = this.mDeviceIds;
        }
        if (iArr.length == 0) {
            return null;
        }
        return AudioManager.getDeviceForPortId(iArr[0], 2);
    }

    @SystemApi
    public List<AudioDeviceInfo> getAudioDeviceInfos() {
        int[] iArr;
        ArrayList arrayList = new ArrayList();
        synchronized (this.mUpdateablePropLock) {
            iArr = this.mDeviceIds;
        }
        for (int i : iArr) {
            AudioDeviceInfo deviceForPortId = AudioManager.getDeviceForPortId(i, 2);
            if (deviceForPortId != null) {
                arrayList.add(deviceForPortId);
            }
        }
        return arrayList;
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
        int i = this.mPlayerType;
        if (i == 14 || i == 15) {
            return -1;
        }
        return i;
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
        IPlayerShell iPlayerShell;
        synchronized (this) {
            iPlayerShell = this.mIPlayerShell;
        }
        if (iPlayerShell == null) {
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
            convertNativeChannelMaskToOutMask = AudioFormat.convertNativeChannelMaskToOutMask(this.mFormatInfo.mNativeChannelMask);
        }
        return convertNativeChannelMaskToOutMask;
    }

    IPlayer getIPlayer() {
        IPlayerShell iPlayerShell;
        synchronized (this) {
            iPlayerShell = this.mIPlayerShell;
        }
        if (iPlayerShell == null) {
            return null;
        }
        return iPlayerShell.getIPlayer();
    }

    public boolean handleAudioAttributesEvent(AudioAttributes audioAttributes) {
        boolean z = !audioAttributes.equals(this.mPlayerAttr);
        this.mPlayerAttr = audioAttributes;
        return z;
    }

    public boolean handleSessionIdEvent(int i) {
        boolean z;
        synchronized (this.mUpdateablePropLock) {
            z = i != this.mSessionId;
            this.mSessionId = i;
        }
        return z;
    }

    public boolean handleMutedEvent(int i) {
        boolean z;
        synchronized (this.mUpdateablePropLock) {
            z = this.mMutedState != i;
            this.mMutedState = i;
        }
        return z;
    }

    public boolean handleFormatEvent(FormatInfo formatInfo) {
        boolean z;
        synchronized (this.mUpdateablePropLock) {
            z = !this.mFormatInfo.equals(formatInfo);
            this.mFormatInfo = formatInfo;
        }
        return z;
    }

    public boolean handleStateEvent(int i, int[] iArr) {
        boolean z;
        IPlayerShell iPlayerShell;
        synchronized (this.mUpdateablePropLock) {
            boolean z2 = true;
            if (i != 5) {
                try {
                    z = this.mPlayerState != i;
                    this.mPlayerState = i;
                } catch (Throwable th) {
                    throw th;
                }
            } else {
                z = false;
            }
            if (i == 2 || i == 5) {
                if (!z && Arrays.equals(this.mDeviceIds, iArr)) {
                    z2 = false;
                }
                this.mDeviceIds = iArr;
                z = z2;
            }
            if (z && i == 0 && (iPlayerShell = this.mIPlayerShell) != null) {
                iPlayerShell.release();
                this.mIPlayerShell = null;
            }
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void playerDied() {
        PlayerDeathMonitor playerDeathMonitor = sPlayerDeathMonitor;
        if (playerDeathMonitor != null) {
            playerDeathMonitor.playerDeath(this.mPlayerIId);
        }
    }

    private boolean isMuteAffectingActiveState() {
        int i = this.mMutedState;
        return ((i & 16) == 0 && (i & 32) == 0 && (i & 8) == 0) ? false : true;
    }

    @SystemApi
    public boolean isActive() {
        if (this.mPlayerState != 2) {
            return false;
        }
        return !isMuteAffectingActiveState();
    }

    public void dump(PrintWriter printWriter) {
        printWriter.println("  " + this);
    }

    public int hashCode() {
        int hash;
        synchronized (this.mUpdateablePropLock) {
            hash = Objects.hash(Integer.valueOf(this.mPlayerIId), Arrays.toString(this.mDeviceIds), Integer.valueOf(this.mMutedState), Integer.valueOf(this.mPlayerType), Integer.valueOf(this.mClientUid), Integer.valueOf(this.mClientPid), Integer.valueOf(this.mSessionId));
        }
        return hash;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        IPlayerShell iPlayerShell;
        synchronized (this.mUpdateablePropLock) {
            parcel.writeInt(this.mPlayerIId);
            parcel.writeIntArray(this.mDeviceIds);
            parcel.writeInt(this.mMutedState);
            parcel.writeInt(this.mPlayerType);
            parcel.writeInt(this.mClientUid);
            parcel.writeInt(this.mClientPid);
            parcel.writeInt(this.mPlayerState);
            this.mPlayerAttr.writeToParcel(parcel, 0);
            synchronized (this) {
                iPlayerShell = this.mIPlayerShell;
            }
            parcel.writeStrongInterface(iPlayerShell == null ? null : iPlayerShell.getIPlayer());
            parcel.writeInt(this.mSessionId);
            this.mFormatInfo.writeToParcel(parcel, 0);
        }
    }

    private AudioPlaybackConfiguration(Parcel parcel) {
        this.mUpdateablePropLock = new Object();
        this.mDeviceIds = PLAYER_DEVICEIDS_INVALID;
        this.mPlayerIId = parcel.readInt();
        this.mDeviceIds = new int[parcel.readInt()];
        int i = 0;
        while (true) {
            int[] iArr = this.mDeviceIds;
            if (i >= iArr.length) {
                break;
            }
            iArr[i] = parcel.readInt();
            i++;
        }
        this.mMutedState = parcel.readInt();
        this.mPlayerType = parcel.readInt();
        this.mClientUid = parcel.readInt();
        this.mClientPid = parcel.readInt();
        this.mPlayerState = parcel.readInt();
        this.mPlayerAttr = AudioAttributes.CREATOR.createFromParcel(parcel);
        IPlayer asInterface = IPlayer.Stub.asInterface(parcel.readStrongBinder());
        this.mIPlayerShell = asInterface != null ? new IPlayerShell(null, asInterface) : null;
        this.mSessionId = parcel.readInt();
        this.mFormatInfo = FormatInfo.CREATOR.createFromParcel(parcel);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && (obj instanceof AudioPlaybackConfiguration)) {
            AudioPlaybackConfiguration audioPlaybackConfiguration = (AudioPlaybackConfiguration) obj;
            if (this.mPlayerIId == audioPlaybackConfiguration.mPlayerIId && Arrays.equals(this.mDeviceIds, audioPlaybackConfiguration.mDeviceIds) && this.mMutedState == audioPlaybackConfiguration.mMutedState && this.mPlayerType == audioPlaybackConfiguration.mPlayerType && this.mClientUid == audioPlaybackConfiguration.mClientUid && this.mClientPid == audioPlaybackConfiguration.mClientPid && this.mSessionId == audioPlaybackConfiguration.mSessionId) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("AudioPlaybackConfiguration piid:");
        synchronized (this.mUpdateablePropLock) {
            sb.append(this.mPlayerIId);
            sb.append(" deviceIds:");
            sb.append(Arrays.toString(this.mDeviceIds));
            sb.append(" type:");
            sb.append(toLogFriendlyPlayerType(this.mPlayerType));
            sb.append(" u/pid:");
            sb.append(this.mClientUid);
            sb.append("/");
            sb.append(this.mClientPid);
            sb.append(" state:");
            sb.append(toLogFriendlyPlayerState(this.mPlayerState));
            sb.append(" attr:");
            sb.append(this.mPlayerAttr);
            sb.append(" sessionId:");
            sb.append(this.mSessionId);
            sb.append(" mutedState:");
            int i = this.mMutedState;
            if (i == 0) {
                sb.append("none ");
            } else {
                if ((i & 1) != 0) {
                    sb.append("master ");
                }
                if ((this.mMutedState & 2) != 0) {
                    sb.append("streamVolume ");
                }
                if ((this.mMutedState & 4) != 0) {
                    sb.append("streamMute ");
                }
                if ((this.mMutedState & 8) != 0) {
                    sb.append("opPlayAudio ");
                }
                if ((this.mMutedState & 16) != 0) {
                    sb.append("clientVolume ");
                }
                if ((this.mMutedState & 32) != 0) {
                    sb.append("volumeShaper ");
                }
                if ((this.mMutedState & 64) != 0) {
                    sb.append("portVolume ");
                }
                if ((this.mMutedState & 128) != 0) {
                    sb.append("opControlAudio ");
                }
            }
            sb.append(" ");
            sb.append(this.mFormatInfo);
        }
        return sb.toString();
    }

    static final class IPlayerShell implements IBinder.DeathRecipient {
        private volatile IPlayer mIPlayer;
        final AudioPlaybackConfiguration mMonitor;

        IPlayerShell(AudioPlaybackConfiguration audioPlaybackConfiguration, IPlayer iPlayer) {
            this.mMonitor = audioPlaybackConfiguration;
            this.mIPlayer = iPlayer;
        }

        synchronized void monitorDeath() {
            if (this.mIPlayer == null) {
                return;
            }
            try {
                this.mIPlayer.asBinder().linkToDeath(this, 0);
            } catch (RemoteException e) {
                if (this.mMonitor != null) {
                    Log.w(AudioPlaybackConfiguration.TAG, "Could not link to client death for piid=" + this.mMonitor.mPlayerIId, e);
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
            AudioPlaybackConfiguration audioPlaybackConfiguration = this.mMonitor;
            if (audioPlaybackConfiguration != null) {
                audioPlaybackConfiguration.playerDied();
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
        public static final Parcelable.Creator<FormatInfo> CREATOR = new Parcelable.Creator<FormatInfo>() { // from class: android.media.AudioPlaybackConfiguration.FormatInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public FormatInfo createFromParcel(Parcel parcel) {
                return new FormatInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public FormatInfo[] newArray(int i) {
                return new FormatInfo[i];
            }
        };

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public FormatInfo(boolean z, int i, int i2) {
            this.mIsSpatialized = z;
            this.mNativeChannelMask = i;
            this.mSampleRate = i2;
        }

        public String toString() {
            return "FormatInfo{isSpatialized=" + this.mIsSpatialized + ", channelMask=0x" + Integer.toHexString(this.mNativeChannelMask) + ", sampleRate=" + this.mSampleRate + '}';
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof FormatInfo)) {
                return false;
            }
            FormatInfo formatInfo = (FormatInfo) obj;
            return this.mIsSpatialized == formatInfo.mIsSpatialized && this.mNativeChannelMask == formatInfo.mNativeChannelMask && this.mSampleRate == formatInfo.mSampleRate;
        }

        public int hashCode() {
            return Objects.hash(Boolean.valueOf(this.mIsSpatialized), Integer.valueOf(this.mNativeChannelMask), Integer.valueOf(this.mSampleRate));
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeBoolean(this.mIsSpatialized);
            parcel.writeInt(this.mNativeChannelMask);
            parcel.writeInt(this.mSampleRate);
        }

        private FormatInfo(Parcel parcel) {
            this(parcel.readBoolean(), parcel.readInt(), parcel.readInt());
        }
    }

    public static String toLogFriendlyPlayerType(int i) {
        if (i == -1) {
            return "unknown";
        }
        if (i == 1) {
            return "android.media.AudioTrack";
        }
        if (i == 2) {
            return "android.media.MediaPlayer";
        }
        if (i == 3) {
            return "android.media.SoundPool";
        }
        switch (i) {
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
            default:
                return "unknown player type " + i + " - FIXME";
        }
    }

    public static String toLogFriendlyPlayerState(int i) {
        switch (i) {
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
