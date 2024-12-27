package android.media;

import android.app.ActivityThread;
import android.companion.virtual.VirtualDeviceManager;
import android.content.Context;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.text.TextUtils;
import android.util.Log;

import com.android.internal.app.IAppOpsCallback;
import com.android.internal.app.IAppOpsService;

import java.lang.ref.WeakReference;
import java.util.Objects;

public abstract class PlayerBase {
    private static final boolean DEBUG = false;
    private static final boolean DEBUG_APP_OPS = false;
    private static final String TAG = "PlayerBase";
    private static IAudioService sService;
    private IAppOpsService mAppOps;
    private IAppOpsCallback mAppOpsCallback;
    protected AudioAttributes mAttributes;
    private int mDeviceId;
    private final int mImplType;
    private int mState;
    protected float mLeftVolume = 1.0f;
    protected float mRightVolume = 1.0f;
    protected float mAuxEffectSendLevel = 0.0f;
    private final Object mLock = new Object();
    private boolean mHasAppOpsPlayAudio = true;
    protected int mPlayerIId = -1;
    private int mStartDelayMs = 0;
    private float mPanMultiplierL = 1.0f;
    private float mPanMultiplierR = 1.0f;
    private float mVolMultiplier = 1.0f;

    abstract int playerApplyVolumeShaper(
            VolumeShaper.Configuration configuration, VolumeShaper.Operation operation);

    abstract VolumeShaper.State playerGetVolumeShaperState(int i);

    abstract void playerPause();

    abstract int playerSetAuxEffectSendLevel(boolean z, float f);

    abstract void playerSetVolume(boolean z, float f, float f2);

    abstract void playerStart();

    abstract void playerStop();

    PlayerBase(AudioAttributes attr, int implType) {
        if (attr == null) {
            throw new IllegalArgumentException("Illegal null AudioAttributes");
        }
        this.mAttributes = attr;
        this.mImplType = implType;
        this.mState = 1;
    }

    public int getPlayerIId() {
        int i;
        synchronized (this.mLock) {
            i = this.mPlayerIId;
        }
        return i;
    }

    protected void baseRegisterPlayer(int sessionId) {
        try {
            this.mPlayerIId =
                    getService()
                            .trackPlayer(
                                    new PlayerIdCard(
                                            this.mImplType,
                                            this.mAttributes,
                                            new IPlayerWrapper(this),
                                            sessionId));
        } catch (RemoteException e) {
            Log.e(TAG, "Error talking to audio service, player will not be tracked", e);
        }
    }

    void baseUpdateAudioAttributes(AudioAttributes attr) {
        if (attr == null) {
            throw new IllegalArgumentException("Illegal null AudioAttributes");
        }
        try {
            getService().playerAttributes(this.mPlayerIId, attr);
        } catch (RemoteException e) {
            Log.e(TAG, "Error talking to audio service, audio attributes will not be updated", e);
        }
        synchronized (this.mLock) {
            this.mAttributes = attr;
        }
    }

    void baseUpdateSessionId(int sessionId) {
        try {
            getService().playerSessionId(this.mPlayerIId, sessionId);
        } catch (RemoteException e) {
            Log.e(TAG, "Error talking to audio service, the session ID will not be updated", e);
        }
    }

    void baseUpdateDeviceId(AudioDeviceInfo deviceInfo) {
        int piid;
        int deviceId = 0;
        if (deviceInfo != null) {
            deviceId = deviceInfo.getId();
        }
        synchronized (this.mLock) {
            piid = this.mPlayerIId;
            this.mDeviceId = deviceId;
        }
        try {
            getService().playerEvent(piid, 5, deviceId);
        } catch (RemoteException e) {
            Log.e(
                    TAG,
                    "Error talking to audio service, "
                            + deviceId
                            + " device id will not be tracked for piid="
                            + piid,
                    e);
        }
    }

    private void updateState(int state, int deviceId) {
        int piid;
        synchronized (this.mLock) {
            this.mState = state;
            piid = this.mPlayerIId;
            this.mDeviceId = deviceId;
        }
        try {
            getService().playerEvent(piid, state, deviceId);
        } catch (RemoteException e) {
            Log.e(
                    TAG,
                    "Error talking to audio service, "
                            + AudioPlaybackConfiguration.toLogFriendlyPlayerState(state)
                            + " state will not be tracked for piid="
                            + piid,
                    e);
        }
    }

    void baseStart(int deviceId) {
        updateState(2, deviceId);
    }

    void baseSetStartDelayMs(int delayMs) {
        synchronized (this.mLock) {
            this.mStartDelayMs = Math.max(delayMs, 0);
        }
    }

    protected int getStartDelayMs() {
        int i;
        synchronized (this.mLock) {
            i = this.mStartDelayMs;
        }
        return i;
    }

    void basePause() {
        updateState(3, 0);
    }

    void baseStop() {
        updateState(4, 0);
    }

    void baseSetPan(float pan) {
        float p = Math.min(Math.max(-1.0f, pan), 1.0f);
        synchronized (this.mLock) {
            if (p >= 0.0f) {
                this.mPanMultiplierL = 1.0f - p;
                this.mPanMultiplierR = 1.0f;
            } else {
                this.mPanMultiplierL = 1.0f;
                this.mPanMultiplierR = 1.0f + p;
            }
        }
        updatePlayerVolume();
    }

    private void updatePlayerVolume() {
        float finalLeftVol;
        float finalRightVol;
        synchronized (this.mLock) {
            finalLeftVol = this.mVolMultiplier * this.mLeftVolume * this.mPanMultiplierL;
            finalRightVol = this.mVolMultiplier * this.mRightVolume * this.mPanMultiplierR;
        }
        playerSetVolume(false, finalLeftVol, finalRightVol);
    }

    void setVolumeMultiplier(float vol) {
        synchronized (this.mLock) {
            this.mVolMultiplier = vol;
        }
        updatePlayerVolume();
    }

    void baseSetVolume(float leftVolume, float rightVolume) {
        synchronized (this.mLock) {
            this.mLeftVolume = leftVolume;
            this.mRightVolume = rightVolume;
        }
        updatePlayerVolume();
    }

    int baseSetAuxEffectSendLevel(float level) {
        synchronized (this.mLock) {
            this.mAuxEffectSendLevel = level;
        }
        return playerSetAuxEffectSendLevel(false, level);
    }

    void baseRelease() {
        boolean releasePlayer = false;
        synchronized (this.mLock) {
            if (this.mState != 0) {
                releasePlayer = true;
                this.mState = 0;
            }
        }
        if (releasePlayer) {
            try {
                getService().releasePlayer(this.mPlayerIId);
            } catch (RemoteException e) {
                Log.e(TAG, "Error talking to audio service, the player will still be tracked", e);
            }
        }
        try {
            if (this.mAppOps != null) {
                this.mAppOps.stopWatchingMode(this.mAppOpsCallback);
            }
        } catch (Exception e2) {
        }
    }

    private static IAudioService getService() {
        if (sService != null) {
            return sService;
        }
        IBinder b = ServiceManager.getService("audio");
        sService = IAudioService.Stub.asInterface(b);
        return sService;
    }

    public void setStartDelayMs(int delayMs) {
        baseSetStartDelayMs(delayMs);
    }

    private static class IPlayerWrapper extends IPlayer.Stub {
        private final WeakReference<PlayerBase> mWeakPB;

        public IPlayerWrapper(PlayerBase pb) {
            this.mWeakPB = new WeakReference<>(pb);
        }

        @Override // android.media.IPlayer
        public void start() {
            PlayerBase pb = this.mWeakPB.get();
            if (pb != null) {
                pb.playerStart();
            }
        }

        @Override // android.media.IPlayer
        public void pause() {
            PlayerBase pb = this.mWeakPB.get();
            if (pb != null) {
                pb.playerPause();
            }
        }

        @Override // android.media.IPlayer
        public void stop() {
            PlayerBase pb = this.mWeakPB.get();
            if (pb != null) {
                pb.playerStop();
            }
        }

        @Override // android.media.IPlayer
        public void setVolume(float vol) {
            PlayerBase pb = this.mWeakPB.get();
            if (pb != null) {
                pb.setVolumeMultiplier(vol);
            }
        }

        @Override // android.media.IPlayer
        public void setPan(float pan) {
            PlayerBase pb = this.mWeakPB.get();
            if (pb != null) {
                pb.baseSetPan(pan);
            }
        }

        @Override // android.media.IPlayer
        public void setStartDelayMs(int delayMs) {
            PlayerBase pb = this.mWeakPB.get();
            if (pb != null) {
                pb.baseSetStartDelayMs(delayMs);
            }
        }

        @Override // android.media.IPlayer
        public void applyVolumeShaper(
                VolumeShaperConfiguration configuration, VolumeShaperOperation operation) {
            PlayerBase pb = this.mWeakPB.get();
            if (pb != null) {
                pb.playerApplyVolumeShaper(
                        VolumeShaper.Configuration.fromParcelable(configuration),
                        VolumeShaper.Operation.fromParcelable(operation));
            }
        }
    }

    public static class PlayerIdCard implements Parcelable {
        public static final int AUDIO_ATTRIBUTES_DEFINED = 1;
        public static final int AUDIO_ATTRIBUTES_NONE = 0;
        public static final Parcelable.Creator<PlayerIdCard> CREATOR =
                new Parcelable.Creator<
                        PlayerIdCard>() { // from class: android.media.PlayerBase.PlayerIdCard.1
                    @Override // android.os.Parcelable.Creator
                    public PlayerIdCard createFromParcel(Parcel p) {
                        return new PlayerIdCard(p);
                    }

                    @Override // android.os.Parcelable.Creator
                    public PlayerIdCard[] newArray(int size) {
                        return new PlayerIdCard[size];
                    }
                };
        public final AudioAttributes mAttributes;
        public final IPlayer mIPlayer;
        public final int mPlayerType;
        public final int mSessionId;

        PlayerIdCard(int type, AudioAttributes attr, IPlayer iplayer, int sessionId) {
            this.mPlayerType = type;
            this.mAttributes = attr;
            this.mIPlayer = iplayer;
            this.mSessionId = sessionId;
        }

        public int hashCode() {
            return Objects.hash(
                    Integer.valueOf(this.mPlayerType), Integer.valueOf(this.mSessionId));
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.mPlayerType);
            this.mAttributes.writeToParcel(dest, 0);
            dest.writeStrongBinder(this.mIPlayer == null ? null : this.mIPlayer.asBinder());
            dest.writeInt(this.mSessionId);
        }

        private PlayerIdCard(Parcel in) {
            this.mPlayerType = in.readInt();
            this.mAttributes = AudioAttributes.CREATOR.createFromParcel(in);
            IBinder b = in.readStrongBinder();
            this.mIPlayer = b == null ? null : IPlayer.Stub.asInterface(b);
            this.mSessionId = in.readInt();
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || !(o instanceof PlayerIdCard)) {
                return false;
            }
            PlayerIdCard that = (PlayerIdCard) o;
            if (this.mPlayerType == that.mPlayerType
                    && this.mAttributes.equals(that.mAttributes)
                    && this.mSessionId == that.mSessionId) {
                return true;
            }
            return false;
        }
    }

    public static void deprecateStreamTypeForPlayback(
            int streamType, String className, String opName) throws IllegalArgumentException {
        if (streamType == 10) {
            throw new IllegalArgumentException(
                    "Use of STREAM_ACCESSIBILITY is reserved for volume control");
        }
        Log.w(
                className,
                "Use of stream types is deprecated for operations other than volume control");
        Log.w(
                className,
                "See the documentation of "
                        + opName
                        + " for what to use instead with android.media.AudioAttributes to qualify"
                        + " your playback use case");
    }

    protected String getCurrentOpPackageName() {
        return TextUtils.emptyIfNull(ActivityThread.currentOpPackageName());
    }

    protected static int resolvePlaybackSessionId(Context context, int requestedSessionId) {
        int deviceId;
        VirtualDeviceManager vdm;
        if (requestedSessionId != 0) {
            return requestedSessionId;
        }
        if (context == null
                || (deviceId = context.getDeviceId()) == 0
                || (vdm =
                                (VirtualDeviceManager)
                                        context.getSystemService(VirtualDeviceManager.class))
                        == null
                || vdm.getDevicePolicy(deviceId, 1) == 0) {
            return 0;
        }
        return vdm.getAudioPlaybackSessionId(deviceId);
    }

    public float getVolMultiplier() {
        return this.mVolMultiplier;
    }
}
