package com.android.server.audio;

import android.app.ActivityManager;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioDeviceAttributes;
import android.media.AudioPlaybackConfiguration;
import android.media.AudioSystem;
import android.media.FadeManagerConfiguration;
import android.media.IPlaybackConfigDispatcher;
import android.media.VolumeShaper;
import android.media.audiopolicy.Flags;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.util.Log;
import android.util.Slog;
import android.util.SparseIntArray;

import com.android.internal.util.ArrayUtils;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.NetworkScoreService$$ExternalSyntheticOutline0;
import com.android.server.VpnManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.utils.EventLogger;

import com.samsung.android.knoxguard.service.utils.Constants;
import com.samsung.android.server.audio.FrequentWorker;
import com.samsung.android.server.audio.GoodCatchManager;

import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class PlaybackActivityMonitor
        implements AudioPlaybackConfiguration.PlayerDeathMonitor, PlayerFocusEnforcer {
    public static final Set ACTIVE_CHECK_PLAYER_TYPES;
    public static final VolumeShaper.Configuration MUTE_AWAIT_CONNECTION_VSHAPE;
    public static final VolumeShaper.Operation PLAY_CREATE_IF_NEEDED;
    public static final VolumeShaper.Operation PLAY_SKIP_RAMP;
    public static final Set TAGS_TO_NOT_MUTE_IN_CALL;
    public static final int[] UNDUCKABLE_PLAYER_TYPES;
    public static final Set mGoodCatchSkipStream;
    public static final Set mGoodCatchSystemUiStream;
    public static final EventLogger sEventLogger;
    public final HashMap mAllowedCapturePolicies;
    public Handler mAudioHandler;
    public final AudioService mAudioService;
    public final ArrayList mBannedUids;
    public final Context mContext;
    public final ArrayList mDoNotLogPiidList;
    public final DuckingManager mDuckingManager;
    public final AnonymousClass1 mEventHandler;
    public final HandlerThread mEventThread;
    public final FadeOutManager mFadeOutManager;
    public long mLastUpdateTime;
    public final int mMaxAlarmVolume;
    public final Consumer mMuteAwaitConnectionTimeoutCb;
    public final ArrayList mMutedPlayers;
    public final ArrayList mMutedPlayersAwaitingConnection;
    public int[] mMutedUsagesAwaitingConnection;
    public final AnonymousClass2 mNotifierSoundAliveForDVFS;
    public final HashMap mPackageTimeMap;
    public final SparseIntArray mPortIdToPiid;
    public int mPrivilegedAlarmActiveCount;
    public int mSavedAlarmVolume;
    public static final VolumeShaper.Configuration DUCK_VSHAPE =
            new VolumeShaper.Configuration.Builder()
                    .setId(1)
                    .setCurve(
                            new float[] {FullScreenMagnificationGestureHandler.MAX_SCALE, 1.0f},
                            new float[] {1.0f, 0.2f})
                    .setOptionFlags(2)
                    .setDuration(
                            MediaFocusControl.getFocusRampTimeMs(
                                    new AudioAttributes.Builder().setUsage(5).build()))
                    .build();
    public static final VolumeShaper.Configuration DUCK_ID = new VolumeShaper.Configuration(1);
    public static final VolumeShaper.Configuration STRONG_DUCK_VSHAPE =
            new VolumeShaper.Configuration.Builder()
                    .setId(4)
                    .setCurve(
                            new float[] {FullScreenMagnificationGestureHandler.MAX_SCALE, 1.0f},
                            new float[] {1.0f, 0.017783f})
                    .setOptionFlags(2)
                    .setDuration(
                            MediaFocusControl.getFocusRampTimeMs(
                                    new AudioAttributes.Builder().setUsage(5).build()))
                    .build();
    public static final VolumeShaper.Configuration STRONG_DUCK_ID =
            new VolumeShaper.Configuration(4);
    public final ConcurrentLinkedQueue mClients = new ConcurrentLinkedQueue();
    public final Object mPlayerLock = new Object();
    public final HashMap mPlayers = new HashMap();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AudioAttrEvent extends EventLogger.Event {
        public final /* synthetic */ int $r8$classId = 0;
        public final Object mPlayerAttr;
        public final int mPlayerIId;

        public AudioAttrEvent(int i, AudioAttributes audioAttributes) {
            this.mPlayerIId = i;
            this.mPlayerAttr = audioAttributes;
        }

        public AudioAttrEvent(int i, AudioPlaybackConfiguration.FormatInfo formatInfo) {
            this.mPlayerIId = i;
            this.mPlayerAttr = formatInfo;
        }

        @Override // com.android.server.utils.EventLogger.Event
        public final String eventToString() {
            switch (this.$r8$classId) {
                case 0:
                    return new String(
                            "player piid:"
                                    + this.mPlayerIId
                                    + " new AudioAttributes:"
                                    + ((AudioAttributes) this.mPlayerAttr));
                default:
                    return new String(
                            "player piid:"
                                    + this.mPlayerIId
                                    + " format update:"
                                    + ((AudioPlaybackConfiguration.FormatInfo) this.mPlayerAttr));
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DuckEvent extends VolumeShaperEvent {
        public final boolean mUseStrongDuck;

        public DuckEvent(
                AudioPlaybackConfiguration audioPlaybackConfiguration,
                boolean z,
                boolean z2,
                VolumeShaper.Configuration configuration,
                VolumeShaper.Operation operation) {
            super(audioPlaybackConfiguration, z, configuration, operation);
            this.mUseStrongDuck = z2;
        }

        @Override // com.android.server.audio.PlaybackActivityMonitor.VolumeShaperEvent
        public final String getVSAction() {
            return this.mUseStrongDuck ? "ducking (strong)" : "ducking";
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DuckingManager {
        public final HashMap mDuckers = new HashMap();

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class DuckedApp {
            public final ArrayList mDuckedPlayers = new ArrayList();
            public final int mUid;
            public final boolean mUseStrongDuck;

            public DuckedApp(int i, boolean z) {
                this.mUid = i;
                this.mUseStrongDuck = z;
            }

            public final void addDuck(
                    AudioPlaybackConfiguration audioPlaybackConfiguration, boolean z) {
                Integer num = new Integer(audioPlaybackConfiguration.getPlayerInterfaceId());
                int intValue = num.intValue();
                if (this.mDuckedPlayers.contains(num)) {
                    Log.v("AS.PlaybackActivityMon", "player piid:" + intValue + " already ducked");
                    return;
                }
                try {
                    VolumeShaper.Configuration configuration =
                            this.mUseStrongDuck
                                    ? PlaybackActivityMonitor.STRONG_DUCK_VSHAPE
                                    : PlaybackActivityMonitor.DUCK_VSHAPE;
                    VolumeShaper.Operation operation =
                            z
                                    ? PlaybackActivityMonitor.PLAY_SKIP_RAMP
                                    : PlaybackActivityMonitor.PLAY_CREATE_IF_NEEDED;
                    EventLogger eventLogger = PlaybackActivityMonitor.sEventLogger;
                    DuckEvent duckEvent =
                            new DuckEvent(
                                    audioPlaybackConfiguration,
                                    z,
                                    this.mUseStrongDuck,
                                    configuration,
                                    operation);
                    duckEvent.printLog(0, "AS.PlaybackActivityMon");
                    eventLogger.enqueue(duckEvent);
                    audioPlaybackConfiguration
                            .getPlayerProxy()
                            .applyVolumeShaper(configuration, operation);
                    this.mDuckedPlayers.add(num);
                } catch (Exception e) {
                    StringBuilder m =
                            BatteryService$$ExternalSyntheticOutline0.m(
                                    intValue, "Error ducking player piid:", " uid:");
                    m.append(this.mUid);
                    Log.e("AS.PlaybackActivityMon", m.toString(), e);
                }
            }

            public final void dump(PrintWriter printWriter) {
                printWriter.print("\t uid:" + this.mUid + " piids:");
                Iterator it = this.mDuckedPlayers.iterator();
                while (it.hasNext()) {
                    printWriter.print(" " + ((Integer) it.next()).intValue());
                }
                printWriter.println("");
            }

            public final void removeUnduckAll(HashMap hashMap) {
                Iterator it = this.mDuckedPlayers.iterator();
                while (it.hasNext()) {
                    Integer num = (Integer) it.next();
                    int intValue = num.intValue();
                    AudioPlaybackConfiguration audioPlaybackConfiguration =
                            (AudioPlaybackConfiguration) hashMap.get(num);
                    int i = this.mUid;
                    if (audioPlaybackConfiguration != null) {
                        try {
                            EventLogger eventLogger = PlaybackActivityMonitor.sEventLogger;
                            EventLogger.StringEvent stringEvent =
                                    new EventLogger.StringEvent("unducking piid:" + intValue);
                            stringEvent.printLog(0, "AS.PlaybackActivityMon");
                            eventLogger.enqueue(stringEvent);
                            audioPlaybackConfiguration
                                    .getPlayerProxy()
                                    .applyVolumeShaper(
                                            this.mUseStrongDuck
                                                    ? PlaybackActivityMonitor.STRONG_DUCK_ID
                                                    : PlaybackActivityMonitor.DUCK_ID,
                                            VolumeShaper.Operation.REVERSE);
                        } catch (Exception e) {
                            Log.e(
                                    "AS.PlaybackActivityMon",
                                    ArrayUtils$$ExternalSyntheticOutline0.m(
                                            intValue, i, "Error unducking player piid:", " uid:"),
                                    e);
                        }
                    } else {
                        Log.v(
                                "AS.PlaybackActivityMon",
                                "Error unducking player piid:"
                                        + intValue
                                        + ", player not found for uid "
                                        + i);
                    }
                }
                this.mDuckedPlayers.clear();
            }
        }

        public final synchronized void checkDuck(
                AudioPlaybackConfiguration audioPlaybackConfiguration) {
            Log.v(
                    "AS.PlaybackActivityMon",
                    "DuckingManager: checkDuck() player piid:"
                            + audioPlaybackConfiguration.getPlayerInterfaceId()
                            + " uid:"
                            + audioPlaybackConfiguration.getClientUid());
            DuckedApp duckedApp =
                    (DuckedApp)
                            this.mDuckers.get(
                                    Integer.valueOf(audioPlaybackConfiguration.getClientUid()));
            if (duckedApp == null) {
                return;
            }
            Log.v(
                    "AS.PlaybackActivityMon",
                    "DuckingManager: checkDuck() player piid:" + this.mDuckers.toString() + " )");
            duckedApp.addDuck(audioPlaybackConfiguration, true);
        }

        public final synchronized void duckUid(int i, ArrayList arrayList, boolean z) {
            try {
                Log.v("AS.PlaybackActivityMon", "DuckingManager: duckUid() uid:" + i);
                if (!this.mDuckers.containsKey(Integer.valueOf(i))) {
                    this.mDuckers.put(Integer.valueOf(i), new DuckedApp(i, z));
                }
                DuckedApp duckedApp = (DuckedApp) this.mDuckers.get(Integer.valueOf(i));
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    duckedApp.addDuck((AudioPlaybackConfiguration) it.next(), false);
                }
            } catch (Throwable th) {
                throw th;
            }
        }

        public final synchronized void removeReleased(
                AudioPlaybackConfiguration audioPlaybackConfiguration) {
            int clientUid = audioPlaybackConfiguration.getClientUid();
            Log.v(
                    "AS.PlaybackActivityMon",
                    "DuckingManager: removedReleased() player piid: "
                            + audioPlaybackConfiguration.getPlayerInterfaceId()
                            + " uid:"
                            + clientUid);
            DuckedApp duckedApp = (DuckedApp) this.mDuckers.get(Integer.valueOf(clientUid));
            if (duckedApp == null) {
                return;
            }
            duckedApp.mDuckedPlayers.remove(
                    new Integer(audioPlaybackConfiguration.getPlayerInterfaceId()));
        }

        public final synchronized void unduckUid(int i, HashMap hashMap) {
            Log.v("AS.PlaybackActivityMon", "DuckingManager: unduckUid() uid:" + i);
            DuckedApp duckedApp = (DuckedApp) this.mDuckers.remove(Integer.valueOf(i));
            if (duckedApp == null) {
                return;
            }
            duckedApp.removeUnduckAll(hashMap);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class FadeInEvent extends VolumeShaperEvent {
        public final /* synthetic */ int $r8$classId;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public /* synthetic */ FadeInEvent(
                AudioPlaybackConfiguration audioPlaybackConfiguration,
                boolean z,
                VolumeShaper.Configuration configuration,
                VolumeShaper.Operation operation,
                int i) {
            super(audioPlaybackConfiguration, z, configuration, operation);
            this.$r8$classId = i;
        }

        @Override // com.android.server.audio.PlaybackActivityMonitor.VolumeShaperEvent
        public final String getVSAction() {
            switch (this.$r8$classId) {
                case 0:
                    return "fading in";
                default:
                    return "fading out";
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MuteAwaitConnectionEvent extends EventLogger.Event {
        public final int[] mUsagesToMute;

        public MuteAwaitConnectionEvent(int[] iArr) {
            this.mUsagesToMute = iArr;
        }

        @Override // com.android.server.utils.EventLogger.Event
        public final String eventToString() {
            return "muteAwaitConnection muting usages " + Arrays.toString(this.mUsagesToMute);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class NewPlayerEvent extends EventLogger.Event {
        public final String mClientPackageName;
        public final int mClientPid;
        public final int mClientUid;
        public final AudioAttributes mPlayerAttr;
        public final int mPlayerIId;
        public final int mPlayerType;
        public final int mSessionId;

        public NewPlayerEvent(AudioPlaybackConfiguration audioPlaybackConfiguration, String str) {
            this.mPlayerIId = audioPlaybackConfiguration.getPlayerInterfaceId();
            this.mPlayerType = audioPlaybackConfiguration.getPlayerType();
            this.mClientUid = audioPlaybackConfiguration.getClientUid();
            this.mClientPackageName = str;
            this.mClientPid = audioPlaybackConfiguration.getClientPid();
            this.mPlayerAttr = audioPlaybackConfiguration.getAudioAttributes();
            this.mSessionId = audioPlaybackConfiguration.getSessionId();
        }

        @Override // com.android.server.utils.EventLogger.Event
        public final String eventToString() {
            return new String(
                    "new player piid:"
                            + this.mPlayerIId
                            + " uid/pid:"
                            + this.mClientUid
                            + "/"
                            + this.mClientPid
                            + " package:"
                            + this.mClientPackageName
                            + " type:"
                            + AudioPlaybackConfiguration.toLogFriendlyPlayerType(this.mPlayerType)
                            + " attr:"
                            + this.mPlayerAttr
                            + " session:"
                            + this.mSessionId);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PlayMonitorClient implements IBinder.DeathRecipient {
        public static PlaybackActivityMonitor sListenerDeathMonitor;
        public final IPlaybackConfigDispatcher mDispatcherCb;
        public final boolean mIsPrivileged;
        public final int mPid;
        public final int mUid;
        public boolean mIsReleased = false;
        public int mErrorCount = 0;

        public PlayMonitorClient(
                IPlaybackConfigDispatcher iPlaybackConfigDispatcher, boolean z, int i, int i2) {
            this.mDispatcherCb = iPlaybackConfigDispatcher;
            this.mIsPrivileged = z;
            this.mUid = i;
            this.mPid = i2;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            Log.w("AS.PlaybackActivityMon", "client died");
            sListenerDeathMonitor.unregisterPlaybackCallback(this.mDispatcherCb);
        }

        public final void dispatchPlaybackConfigChange(List list, boolean z) {
            synchronized (this) {
                try {
                    if (this.mIsReleased) {
                        return;
                    }
                    try {
                        this.mDispatcherCb.dispatchPlaybackConfigChange(list, z);
                    } catch (RemoteException e) {
                        synchronized (this) {
                            this.mErrorCount++;
                            Log.e(
                                    "AS.PlaybackActivityMon",
                                    "Error ("
                                            + this.mErrorCount
                                            + ") trying to dispatch playback config change to "
                                            + this,
                                    e);
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final String toString() {
            boolean z;
            StringBuilder sb = new StringBuilder("PlayMonitorClient:");
            synchronized (this) {
                z = this.mIsPrivileged;
            }
            sb.append(z ? "S" : "P");
            sb.append(" uid:");
            sb.append(this.mUid);
            sb.append(" pid:");
            sb.append(this.mPid);
            return sb.toString();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PlayerEvent extends EventLogger.Event {
        public final int mEvent;
        public final int mEventValue;
        public final int mPlayerIId;

        public PlayerEvent(int i, int i2, int i3) {
            this.mPlayerIId = i;
            this.mEvent = i2;
            this.mEventValue = i3;
        }

        @Override // com.android.server.utils.EventLogger.Event
        public final String eventToString() {
            StringBuilder sb = new StringBuilder("player piid:");
            int i = this.mPlayerIId;
            sb.append(i);
            sb.append(" event:");
            int i2 = this.mEvent;
            sb.append(AudioPlaybackConfiguration.toLogFriendlyPlayerState(i2));
            int i3 = this.mEventValue;
            if (i2 == 5) {
                if (i3 != 0) {
                    sb.append(" deviceId:");
                    sb.append(i3);
                }
                return sb.toString();
            }
            if (i2 == 6) {
                return AudioPlaybackConfiguration.toLogFriendlyPlayerState(i2)
                        + " portId:"
                        + i3
                        + " mapped to player piid:"
                        + i;
            }
            if (i2 != 7) {
                return sb.toString();
            }
            sb.append(" source:");
            if (i3 <= 0) {
                sb.append("none ");
            } else {
                if ((i3 & 1) != 0) {
                    sb.append("masterMute ");
                }
                if ((i3 & 2) != 0) {
                    sb.append("streamVolume ");
                }
                if ((i3 & 4) != 0) {
                    sb.append("streamMute ");
                }
                if ((i3 & 8) != 0) {
                    sb.append("appOps ");
                }
                if ((i3 & 16) != 0) {
                    sb.append("clientVolume ");
                }
                if ((i3 & 32) != 0) {
                    sb.append("volumeShaper ");
                }
            }
            return sb.toString();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PlayerOpPlayAudioEvent extends EventLogger.Event {
        public final boolean mHasOp;
        public final int mPlayerIId;
        public final int mUid;

        public PlayerOpPlayAudioEvent(int i, int i2, boolean z) {
            this.mPlayerIId = i;
            this.mHasOp = z;
            this.mUid = i2;
        }

        @Override // com.android.server.utils.EventLogger.Event
        public final String eventToString() {
            return "player piid:"
                    + this.mPlayerIId
                    + " has OP_PLAY_AUDIO:"
                    + this.mHasOp
                    + " in uid:"
                    + this.mUid;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class VolumeShaperEvent extends EventLogger.Event {
        public final int mClientPid;
        public final int mClientUid;
        public final VolumeShaper.Configuration mConfig;
        public final VolumeShaper.Operation mOperation;
        public final AudioAttributes mPlayerAttr;
        public final int mPlayerIId;
        public final int mPlayerType;
        public final boolean mSkipRamp;

        public VolumeShaperEvent(
                AudioPlaybackConfiguration audioPlaybackConfiguration,
                boolean z,
                VolumeShaper.Configuration configuration,
                VolumeShaper.Operation operation) {
            this.mPlayerIId = audioPlaybackConfiguration.getPlayerInterfaceId();
            this.mSkipRamp = z;
            this.mClientUid = audioPlaybackConfiguration.getClientUid();
            this.mClientPid = audioPlaybackConfiguration.getClientPid();
            this.mPlayerAttr = audioPlaybackConfiguration.getAudioAttributes();
            this.mPlayerType = audioPlaybackConfiguration.getPlayerType();
            this.mConfig = configuration;
            this.mOperation = operation;
        }

        @Override // com.android.server.utils.EventLogger.Event
        public final String eventToString() {
            return getVSAction()
                    + " player piid:"
                    + this.mPlayerIId
                    + " uid/pid:"
                    + this.mClientUid
                    + "/"
                    + this.mClientPid
                    + " skip ramp:"
                    + this.mSkipRamp
                    + " player type:"
                    + AudioPlaybackConfiguration.toLogFriendlyPlayerType(this.mPlayerType)
                    + " attr:"
                    + this.mPlayerAttr
                    + " config:"
                    + this.mConfig
                    + " operation:"
                    + this.mOperation;
        }

        public abstract String getVSAction();
    }

    static {
        VolumeShaper.Operation build =
                new VolumeShaper.Operation.Builder(VolumeShaper.Operation.PLAY)
                        .createIfNeeded()
                        .build();
        PLAY_CREATE_IF_NEEDED = build;
        MUTE_AWAIT_CONNECTION_VSHAPE =
                new VolumeShaper.Configuration.Builder()
                        .setId(3)
                        .setCurve(
                                new float[] {FullScreenMagnificationGestureHandler.MAX_SCALE, 1.0f},
                                new float[] {1.0f, FullScreenMagnificationGestureHandler.MAX_SCALE})
                        .setOptionFlags(2)
                        .setDuration(100L)
                        .build();
        UNDUCKABLE_PLAYER_TYPES = new int[] {13, 3};
        PLAY_SKIP_RAMP = new VolumeShaper.Operation.Builder(build).setXOffset(1.0f).build();
        sEventLogger = new EventLogger(100, "playback activity as reported through PlayerBase");
        new ArrayDeque(50);
        HashSet hashSet = new HashSet(2);
        TAGS_TO_NOT_MUTE_IN_CALL = hashSet;
        hashSet.add("AUDIO_STREAM_RING");
        hashSet.add("NO_MUTE_IN_CALL");
        HashSet hashSet2 = new HashSet(2);
        ACTIVE_CHECK_PLAYER_TYPES = hashSet2;
        hashSet2.add(2);
        hashSet2.add(1);
        mGoodCatchSkipStream = new HashSet(Arrays.asList(1));
        mGoodCatchSystemUiStream = new HashSet(Arrays.asList(5));
    }

    /* JADX WARN: Type inference failed for: r0v15, types: [com.android.server.audio.PlaybackActivityMonitor$2] */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.android.server.audio.PlaybackActivityMonitor$1] */
    public PlaybackActivityMonitor(
            Context context,
            int i,
            AudioService$$ExternalSyntheticLambda16 audioService$$ExternalSyntheticLambda16,
            AudioService audioService) {
        new SparseIntArray();
        this.mPortIdToPiid = new SparseIntArray();
        this.mSavedAlarmVolume = -1;
        this.mPrivilegedAlarmActiveCount = 0;
        this.mFadeOutManager = new FadeOutManager();
        this.mBannedUids = new ArrayList();
        this.mDoNotLogPiidList = new ArrayList();
        this.mAllowedCapturePolicies = new HashMap();
        this.mMutedPlayers = new ArrayList();
        this.mDuckingManager = new DuckingManager();
        this.mMutedPlayersAwaitingConnection = new ArrayList();
        this.mMutedUsagesAwaitingConnection = null;
        this.mAudioHandler = null;
        this.mNotifierSoundAliveForDVFS =
                new FrequentWorker() { // from class:
                                       // com.android.server.audio.PlaybackActivityMonitor.2
                    {
                        this.mPeriodMs = 5000;
                        this.mCachedValue = null;
                    }

                    @Override // com.samsung.android.server.audio.FrequentWorker
                    public final Object func() {
                        Handler handler = PlaybackActivityMonitor.this.mAudioHandler;
                        if (handler == null) {
                            Log.w("AS.PlaybackActivityMon", "There is no audio handler");
                            return null;
                        }
                        handler.sendMessage(handler.obtainMessage(2762, 2, 0));
                        return null;
                    }
                };
        this.mPackageTimeMap = new HashMap();
        this.mLastUpdateTime = 0L;
        this.mContext = context;
        this.mMaxAlarmVolume = i;
        PlayMonitorClient.sListenerDeathMonitor = this;
        AudioPlaybackConfiguration.sPlayerDeathMonitor = this;
        this.mMuteAwaitConnectionTimeoutCb = audioService$$ExternalSyntheticLambda16;
        HandlerThread handlerThread = new HandlerThread("AS.PlaybackActivityMon");
        this.mEventThread = handlerThread;
        handlerThread.start();
        this.mEventHandler =
                new Handler(
                        this.mEventThread
                                .getLooper()) { // from class:
                                                // com.android.server.audio.PlaybackActivityMonitor.1
                    @Override // android.os.Handler
                    public final void handleMessage(Message message) {
                        AudioPlaybackConfiguration audioPlaybackConfiguration;
                        int i2 = message.what;
                        if (i2 == 1) {
                            PlaybackActivityMonitor.sEventLogger.enqueueAndLog(
                                    0,
                                    "Timeout for muting waiting for "
                                            + ((AudioDeviceAttributes) message.obj)
                                            + ", unmuting",
                                    "AS.PlaybackActivityMon");
                            synchronized (PlaybackActivityMonitor.this.mPlayerLock) {
                                PlaybackActivityMonitor.this.unmutePlayersExpectingDevice();
                            }
                            PlaybackActivityMonitor.this.mMuteAwaitConnectionTimeoutCb.accept(
                                    (AudioDeviceAttributes) message.obj);
                            return;
                        }
                        if (i2 == 2) {
                            PersistableBundle persistableBundle = (PersistableBundle) message.obj;
                            if (persistableBundle == null) {
                                Log.w(
                                        "AS.PlaybackActivityMon",
                                        "Received mute event with no extras");
                                return;
                            }
                            int i3 =
                                    persistableBundle.getInt(
                                            "android.media.extra.PLAYER_EVENT_MUTE");
                            synchronized (PlaybackActivityMonitor.this.mPlayerLock) {
                                try {
                                    int i4 = message.arg1;
                                    PlaybackActivityMonitor.sEventLogger.enqueue(
                                            new PlayerEvent(i4, 7, i3));
                                    AudioPlaybackConfiguration audioPlaybackConfiguration2 =
                                            (AudioPlaybackConfiguration)
                                                    PlaybackActivityMonitor.this.mPlayers.get(
                                                            Integer.valueOf(i4));
                                    if (audioPlaybackConfiguration2 != null
                                            && audioPlaybackConfiguration2.handleMutedEvent(i3)) {
                                        PlaybackActivityMonitor.this.dispatchPlaybackChange(false);
                                    }
                                } finally {
                                }
                            }
                            return;
                        }
                        if (i2 != 3) {
                            return;
                        }
                        PersistableBundle persistableBundle2 = (PersistableBundle) message.obj;
                        if (persistableBundle2 == null) {
                            Log.w("AS.PlaybackActivityMon", "Received format event with no extras");
                            return;
                        }
                        AudioPlaybackConfiguration.FormatInfo formatInfo =
                                new AudioPlaybackConfiguration.FormatInfo(
                                        persistableBundle2.getBoolean(
                                                "android.media.extra.PLAYER_EVENT_SPATIALIZED",
                                                false),
                                        persistableBundle2.getInt(
                                                "android.media.extra.PLAYER_EVENT_CHANNEL_MASK", 0),
                                        persistableBundle2.getInt(
                                                "android.media.extra.PLAYER_EVENT_SAMPLE_RATE", 0));
                        PlaybackActivityMonitor.sEventLogger.enqueue(
                                new AudioAttrEvent(message.arg1, formatInfo));
                        synchronized (PlaybackActivityMonitor.this.mPlayerLock) {
                            audioPlaybackConfiguration =
                                    (AudioPlaybackConfiguration)
                                            PlaybackActivityMonitor.this.mPlayers.get(
                                                    Integer.valueOf(message.arg1));
                        }
                        if (audioPlaybackConfiguration == null
                                || !audioPlaybackConfiguration.handleFormatEvent(formatInfo)) {
                            return;
                        }
                        PlaybackActivityMonitor.this.dispatchPlaybackChange(false);
                    }
                };
        this.mAudioService = audioService;
    }

    public static ArrayList anonymizeForPublicConsumption(List list) {
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            AudioPlaybackConfiguration audioPlaybackConfiguration =
                    (AudioPlaybackConfiguration) it.next();
            if (audioPlaybackConfiguration.isActive()) {
                arrayList.add(
                        AudioPlaybackConfiguration.anonymizedCopy(audioPlaybackConfiguration));
            }
        }
        return arrayList;
    }

    public static boolean checkBanPlayer(
            AudioPlaybackConfiguration audioPlaybackConfiguration, int i) {
        boolean z = audioPlaybackConfiguration.getClientUid() == i;
        if (z) {
            int playerInterfaceId = audioPlaybackConfiguration.getPlayerInterfaceId();
            try {
                Log.v(
                        "AS.PlaybackActivityMon",
                        "banning player " + playerInterfaceId + " uid:" + i);
                audioPlaybackConfiguration.getPlayerProxy().pause();
            } catch (Exception e) {
                Log.e(
                        "AS.PlaybackActivityMon",
                        ArrayUtils$$ExternalSyntheticOutline0.m(
                                playerInterfaceId, i, "error banning player ", " uid:"),
                        e);
            }
        }
        return z;
    }

    public static boolean checkConfigurationCaller(
            int i, AudioPlaybackConfiguration audioPlaybackConfiguration, int i2) {
        if (audioPlaybackConfiguration == null) {
            return false;
        }
        if (i2 == 0 || audioPlaybackConfiguration.getClientUid() == i2) {
            return true;
        }
        Log.e("AS.PlaybackActivityMon", "Forbidden operation from uid " + i2 + " for player " + i);
        return false;
    }

    public static boolean checkMuteStrategy(
            AudioPlaybackConfiguration audioPlaybackConfiguration, boolean z) {
        Iterator it = audioPlaybackConfiguration.getAudioAttributes().getTags().iterator();
        while (it.hasNext()) {
            if (((HashSet) TAGS_TO_NOT_MUTE_IN_CALL).contains((String) it.next())) {
                return false;
            }
        }
        if (!((HashSet) ACTIVE_CHECK_PLAYER_TYPES)
                        .contains(Integer.valueOf(audioPlaybackConfiguration.getPlayerType()))
                || audioPlaybackConfiguration.isActive()) {
            return z;
        }
        return false;
    }

    public final void cancelMuteAwaitConnection(String str) {
        sEventLogger.enqueueAndLog(
                0, "cancelMuteAwaitConnection() from:" + str, "AS.PlaybackActivityMon");
        synchronized (this.mPlayerLock) {
            removeMessages(1);
            unmutePlayersExpectingDevice();
        }
    }

    public final void checkVolumeForPrivilegedAlarm(
            AudioPlaybackConfiguration audioPlaybackConfiguration, int i) {
        if (i == 5) {
            return;
        }
        if ((i == 2 || audioPlaybackConfiguration.getPlayerState() == 2)
                && (audioPlaybackConfiguration.getAudioAttributes().getAllFlags() & 192) == 192
                && audioPlaybackConfiguration.getAudioAttributes().getUsage() == 4
                && this.mContext.checkPermission(
                                "android.permission.MODIFY_PHONE_STATE",
                                audioPlaybackConfiguration.getClientPid(),
                                audioPlaybackConfiguration.getClientUid())
                        == 0) {
            if (i == 2 && audioPlaybackConfiguration.getPlayerState() != 2) {
                int i2 = this.mPrivilegedAlarmActiveCount;
                this.mPrivilegedAlarmActiveCount = i2 + 1;
                if (i2 == 0) {
                    this.mSavedAlarmVolume = AudioSystem.getStreamVolumeIndex(4, 2);
                    AudioSystem.setStreamVolumeIndexAS(4, this.mMaxAlarmVolume, 2);
                    return;
                }
                return;
            }
            if (i == 2 || audioPlaybackConfiguration.getPlayerState() != 2) {
                return;
            }
            int i3 = this.mPrivilegedAlarmActiveCount - 1;
            this.mPrivilegedAlarmActiveCount = i3;
            if (i3 == 0 && AudioSystem.getStreamVolumeIndex(4, 2) == this.mMaxAlarmVolume) {
                AudioSystem.setStreamVolumeIndexAS(4, this.mSavedAlarmVolume, 2);
            }
        }
    }

    public final void clearTransientFadeManagerConfiguration() {
        FadeOutManager fadeOutManager = this.mFadeOutManager;
        synchronized (fadeOutManager.mLock) {
            FadeConfigurations fadeConfigurations = fadeOutManager.mFadeConfigurations;
            fadeConfigurations.getClass();
            if (Flags.enableFadeManagerConfiguration()) {
                synchronized (fadeConfigurations.mLock) {
                    fadeConfigurations.mTransientFadeManagerConfig = null;
                    fadeConfigurations.mActiveFadeManagerConfig =
                            fadeConfigurations.getActiveFadeMgrConfigLocked();
                }
            }
        }
    }

    public final void disableAudioForUid(int i, boolean z) {
        synchronized (this.mPlayerLock) {
            try {
                int indexOf = this.mBannedUids.indexOf(new Integer(i));
                if (indexOf >= 0) {
                    if (!z) {
                        sEventLogger.enqueue(new EventLogger.StringEvent("unbanning uid:" + i));
                        this.mBannedUids.remove(indexOf);
                    }
                } else if (z) {
                    Iterator it = this.mPlayers.values().iterator();
                    while (it.hasNext()) {
                        checkBanPlayer((AudioPlaybackConfiguration) it.next(), i);
                    }
                    sEventLogger.enqueue(new EventLogger.StringEvent("banning uid:" + i));
                    this.mBannedUids.add(new Integer(i));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void dispatchPlaybackChange(boolean z) {
        boolean z2;
        boolean z3;
        Log.v(
                "AS.PlaybackActivityMon",
                "dispatchPlaybackChange to " + this.mClients.size() + " clients");
        synchronized (this.mPlayerLock) {
            try {
                if (this.mPlayers.isEmpty()) {
                    return;
                }
                List list =
                        this.mPlayers.values().stream()
                                .sorted(
                                        Comparator.comparing(
                                                        new PlaybackActivityMonitor$$ExternalSyntheticLambda0())
                                                .reversed())
                                .toList();
                Iterator it = this.mClients.iterator();
                ArrayList arrayList = null;
                while (it.hasNext()) {
                    PlayMonitorClient playMonitorClient = (PlayMonitorClient) it.next();
                    synchronized (playMonitorClient) {
                        z2 = playMonitorClient.mErrorCount >= 5;
                    }
                    if (!z2) {
                        synchronized (playMonitorClient) {
                            z3 = playMonitorClient.mIsPrivileged;
                        }
                        if (z3) {
                            playMonitorClient.dispatchPlaybackConfigChange(list, z);
                        } else {
                            if (arrayList == null) {
                                arrayList = anonymizeForPublicConsumption(list);
                            }
                            playMonitorClient.dispatchPlaybackConfigChange(arrayList, false);
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.audio.PlayerFocusEnforcer
    public final boolean duckPlayers(
            FocusRequester focusRequester, FocusRequester focusRequester2, boolean z) {
        Log.v(
                "AS.PlaybackActivityMon",
                String.format(
                        "duckPlayers: uids winner=%d loser=%d",
                        Integer.valueOf(focusRequester.mCallingUid),
                        Integer.valueOf(focusRequester2.mCallingUid)));
        synchronized (this.mPlayerLock) {
            try {
                if (this.mPlayers.isEmpty()) {
                    return true;
                }
                Iterator it = this.mPlayers.values().iterator();
                ArrayList arrayList = new ArrayList();
                while (true) {
                    boolean z2 = false;
                    if (!it.hasNext()) {
                        DuckingManager duckingManager = this.mDuckingManager;
                        int i = focusRequester2.mCallingUid;
                        if (focusRequester.mFocusGainRequest == 3
                                && focusRequester.mAttributes.getUsage() == 16) {
                            z2 = true;
                        }
                        duckingManager.duckUid(i, arrayList, z2);
                        return true;
                    }
                    AudioPlaybackConfiguration audioPlaybackConfiguration =
                            (AudioPlaybackConfiguration) it.next();
                    if (!focusRequester.hasSameUid(audioPlaybackConfiguration.getClientUid())
                            && focusRequester2.hasSameUid(audioPlaybackConfiguration.getClientUid())
                            && audioPlaybackConfiguration.getPlayerState() == 2) {
                        if (!z
                                && audioPlaybackConfiguration.getAudioAttributes().getContentType()
                                        == 1) {
                            Log.v(
                                    "AS.PlaybackActivityMon",
                                    "not ducking player "
                                            + audioPlaybackConfiguration.getPlayerInterfaceId()
                                            + " uid:"
                                            + audioPlaybackConfiguration.getClientUid()
                                            + " pid:"
                                            + audioPlaybackConfiguration.getClientPid()
                                            + " - SPEECH");
                            return false;
                        }
                        if (ArrayUtils.contains(
                                UNDUCKABLE_PLAYER_TYPES,
                                audioPlaybackConfiguration.getPlayerType())) {
                            Log.v(
                                    "AS.PlaybackActivityMon",
                                    "not ducking player "
                                            + audioPlaybackConfiguration.getPlayerInterfaceId()
                                            + " uid:"
                                            + audioPlaybackConfiguration.getClientUid()
                                            + " pid:"
                                            + audioPlaybackConfiguration.getClientPid()
                                            + " due to type:"
                                            + AudioPlaybackConfiguration.toLogFriendlyPlayerType(
                                                    audioPlaybackConfiguration.getPlayerType()));
                            return false;
                        }
                        if (!z
                                && focusRequester.mAttributes.getUsage() == 12
                                && this.mAudioService.isIgnoreDucking()) {
                            Log.d(
                                    "AS.PlaybackActivityMon",
                                    "not duckPlayers -  ConnectedAndroidAuto : "
                                            + this.mAudioService.isConnectedAndroidAuto()
                                            + " , forceDuck : "
                                            + z
                                            + " , IgnoreDucking : "
                                            + this.mAudioService.isIgnoreDucking()
                                            + " , winner usage : "
                                            + focusRequester.mAttributes.getUsage()
                                            + " , loser usage : "
                                            + focusRequester2.mAttributes.getUsage());
                            return false;
                        }
                        arrayList.add(audioPlaybackConfiguration);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:83:0x004d, code lost:

       r5 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x01d7, code lost:

       throw r5;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void dump(java.io.PrintWriter r6) {
        /*
            Method dump skipped, instructions count: 472
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.audio.PlaybackActivityMonitor.dump(java.io.PrintWriter):void");
    }

    @Override // com.android.server.audio.PlayerFocusEnforcer
    public final boolean fadeOutPlayers(
            FocusRequester focusRequester, FocusRequester focusRequester2) {
        boolean isFadeable;
        Log.v(
                "AS.PlaybackActivityMon",
                "fadeOutPlayers: winner="
                        + focusRequester.mPackageName
                        + " loser="
                        + focusRequester2.mPackageName);
        synchronized (this.mPlayerLock) {
            try {
                if (this.mPlayers.isEmpty()) {
                    Log.v("AS.PlaybackActivityMon", "no players to fade out");
                    return false;
                }
                this.mFadeOutManager.getClass();
                if (focusRequester.mAttributes.getContentType() == 1) {
                    Slog.i("AS.FadeOutManager", "not fading out: new focus is for speech");
                } else {
                    if ((focusRequester2.mGrantFlags & 2) == 0) {
                        ArrayList arrayList = new ArrayList();
                        boolean z = false;
                        for (AudioPlaybackConfiguration audioPlaybackConfiguration :
                                this.mPlayers.values()) {
                            if (!focusRequester.hasSameUid(
                                            audioPlaybackConfiguration.getClientUid())
                                    && focusRequester2.hasSameUid(
                                            audioPlaybackConfiguration.getClientUid())
                                    && audioPlaybackConfiguration.getPlayerState() == 2) {
                                FadeOutManager fadeOutManager = this.mFadeOutManager;
                                synchronized (fadeOutManager.mLock) {
                                    isFadeable =
                                            fadeOutManager.mFadeConfigurations.isFadeable(
                                                    audioPlaybackConfiguration.getAudioAttributes(),
                                                    audioPlaybackConfiguration.getClientUid(),
                                                    audioPlaybackConfiguration.getPlayerType());
                                }
                                if (!isFadeable) {
                                    Log.v(
                                            "AS.PlaybackActivityMon",
                                            "not fading out player "
                                                    + audioPlaybackConfiguration
                                                            .getPlayerInterfaceId()
                                                    + " uid:"
                                                    + audioPlaybackConfiguration.getClientUid()
                                                    + " pid:"
                                                    + audioPlaybackConfiguration.getClientPid()
                                                    + " type:"
                                                    + AudioPlaybackConfiguration
                                                            .toLogFriendlyPlayerType(
                                                                    audioPlaybackConfiguration
                                                                            .getPlayerType())
                                                    + " attr:"
                                                    + audioPlaybackConfiguration
                                                            .getAudioAttributes());
                                    return false;
                                }
                                arrayList.add(audioPlaybackConfiguration);
                                z = true;
                            }
                        }
                        if (z) {
                            this.mFadeOutManager.fadeOutUid(focusRequester2.mCallingUid, arrayList);
                        }
                        return z;
                    }
                    Slog.i(
                            "AS.FadeOutManager",
                            "not fading out: loser has PAUSES_ON_DUCKABLE_LOSS");
                }
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.audio.PlayerFocusEnforcer
    public final void forgetUid(int i) {
        HashMap hashMap;
        synchronized (this.mPlayerLock) {
            hashMap = (HashMap) this.mPlayers.clone();
        }
        this.mFadeOutManager.unfadeOutUid(i, hashMap);
        this.mDuckingManager.unduckUid(i, hashMap);
    }

    public final List getActivePlaybackConfigurations(boolean z) {
        synchronized (this.mPlayerLock) {
            try {
                if (z) {
                    return new ArrayList(this.mPlayers.values());
                }
                return anonymizeForPublicConsumption(new ArrayList(this.mPlayers.values()));
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final HashMap getAllAllowedCapturePolicies() {
        HashMap hashMap;
        synchronized (this.mAllowedCapturePolicies) {
            hashMap = (HashMap) this.mAllowedCapturePolicies.clone();
        }
        return hashMap;
    }

    @Override // com.android.server.audio.PlayerFocusEnforcer
    public final long getFadeInDelayForOffendersMillis(AudioAttributes audioAttributes) {
        long fadeInDelayForOffenders;
        long j;
        FadeOutManager fadeOutManager = this.mFadeOutManager;
        synchronized (fadeOutManager.mLock) {
            FadeConfigurations fadeConfigurations = fadeOutManager.mFadeConfigurations;
            fadeConfigurations.getClass();
            if (Flags.enableFadeManagerConfiguration()) {
                synchronized (fadeConfigurations.mLock) {
                    fadeInDelayForOffenders =
                            fadeConfigurations
                                    .getUpdatedFadeManagerConfigLocked()
                                    .getFadeInDelayForOffenders();
                }
                j = fadeInDelayForOffenders;
            } else {
                j = 2000;
            }
        }
        return j;
    }

    @Override // com.android.server.audio.PlayerFocusEnforcer
    public final long getFadeOutDurationMillis(AudioAttributes audioAttributes) {
        long j;
        FadeOutManager fadeOutManager = this.mFadeOutManager;
        synchronized (fadeOutManager.mLock) {
            FadeConfigurations fadeConfigurations = fadeOutManager.mFadeConfigurations;
            j = 0;
            if (fadeConfigurations.isFadeable(audioAttributes, -1, -1)) {
                if (Flags.enableFadeManagerConfiguration()) {
                    synchronized (fadeConfigurations.mLock) {
                        try {
                            FadeManagerConfiguration updatedFadeManagerConfigLocked =
                                    fadeConfigurations.getUpdatedFadeManagerConfigLocked();
                            long fadeOutDurationForAudioAttributes =
                                    updatedFadeManagerConfigLocked
                                            .getFadeOutDurationForAudioAttributes(audioAttributes);
                            if (fadeOutDurationForAudioAttributes != 0) {
                                j = fadeOutDurationForAudioAttributes;
                            } else {
                                j =
                                        updatedFadeManagerConfigLocked.getFadeOutDurationForUsage(
                                                audioAttributes.getSystemUsage());
                            }
                        } finally {
                        }
                    }
                } else {
                    j = 2000;
                }
            }
        }
        return j;
    }

    public final String getPackageNameForPid(int i) {
        ActivityManager activityManager =
                (ActivityManager) this.mContext.getSystemService("activity");
        if (activityManager.getRunningAppProcesses() == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo :
                activityManager.getRunningAppProcesses()) {
            if (runningAppProcessInfo.pid == i) {
                return runningAppProcessInfo.processName;
            }
        }
        return null;
    }

    public final void ignorePlayerIId(int i) {
        synchronized (this.mPlayerLock) {
            this.mDoNotLogPiidList.add(Integer.valueOf(i));
        }
    }

    public final void maybeMutePlayerAwaitingConnection(
            AudioPlaybackConfiguration audioPlaybackConfiguration) {
        int[] iArr = this.mMutedUsagesAwaitingConnection;
        if (iArr == null) {
            return;
        }
        for (int i : iArr) {
            if (i == audioPlaybackConfiguration.getAudioAttributes().getUsage()) {
                try {
                    EventLogger eventLogger = sEventLogger;
                    EventLogger.StringEvent stringEvent =
                            new EventLogger.StringEvent(
                                    "awaiting connection: muting piid:"
                                            + audioPlaybackConfiguration.getPlayerInterfaceId()
                                            + " uid:"
                                            + audioPlaybackConfiguration.getClientUid());
                    stringEvent.printLog(0, "AS.PlaybackActivityMon");
                    eventLogger.enqueue(stringEvent);
                    audioPlaybackConfiguration
                            .getPlayerProxy()
                            .applyVolumeShaper(MUTE_AWAIT_CONNECTION_VSHAPE, PLAY_SKIP_RAMP);
                    this.mMutedPlayersAwaitingConnection.add(
                            Integer.valueOf(audioPlaybackConfiguration.getPlayerInterfaceId()));
                } catch (Exception e) {
                    Log.e(
                            "AS.PlaybackActivityMon",
                            "awaiting connection: error muting player "
                                    + audioPlaybackConfiguration.getPlayerInterfaceId(),
                            e);
                }
            }
        }
    }

    public final void muteAwaitConnection(
            int[] iArr, AudioDeviceAttributes audioDeviceAttributes, long j) {
        sEventLogger.enqueueAndLog(
                0,
                "muteAwaitConnection() dev:" + audioDeviceAttributes + " timeOutMs:" + j,
                "AS.PlaybackActivityMon");
        synchronized (this.mPlayerLock) {
            mutePlayersExpectingDevice(iArr);
            removeMessages(1);
            AnonymousClass1 anonymousClass1 = this.mEventHandler;
            anonymousClass1.sendMessageDelayed(
                    anonymousClass1.obtainMessage(1, audioDeviceAttributes), j);
        }
    }

    public final void mutePlayersExpectingDevice(int[] iArr) {
        sEventLogger.enqueue(new MuteAwaitConnectionEvent(iArr));
        this.mMutedUsagesAwaitingConnection = iArr;
        Iterator it = this.mPlayers.keySet().iterator();
        while (it.hasNext()) {
            AudioPlaybackConfiguration audioPlaybackConfiguration =
                    (AudioPlaybackConfiguration) this.mPlayers.get((Integer) it.next());
            if (audioPlaybackConfiguration != null) {
                maybeMutePlayerAwaitingConnection(audioPlaybackConfiguration);
            }
        }
    }

    @Override // com.android.server.audio.PlayerFocusEnforcer
    public final void mutePlayersForCall(int[] iArr) {
        boolean z;
        String str = new String("mutePlayersForCall: usages=");
        for (int i : iArr) {
            str = VpnManagerService$$ExternalSyntheticOutline0.m(i, str, " ");
        }
        Log.v("AS.PlaybackActivityMon", str);
        synchronized (this.mPlayerLock) {
            try {
                for (Integer num : this.mPlayers.keySet()) {
                    AudioPlaybackConfiguration audioPlaybackConfiguration =
                            (AudioPlaybackConfiguration) this.mPlayers.get(num);
                    if (audioPlaybackConfiguration != null) {
                        int usage = audioPlaybackConfiguration.getAudioAttributes().getUsage();
                        int length = iArr.length;
                        int i2 = 0;
                        while (true) {
                            if (i2 >= length) {
                                z = false;
                                break;
                            } else {
                                if (usage == iArr[i2]) {
                                    z = true;
                                    break;
                                }
                                i2++;
                            }
                        }
                        if (checkMuteStrategy(audioPlaybackConfiguration, z)) {
                            try {
                                EventLogger eventLogger = sEventLogger;
                                EventLogger.StringEvent stringEvent =
                                        new EventLogger.StringEvent(
                                                "call: muting piid:"
                                                        + num
                                                        + " uid:"
                                                        + audioPlaybackConfiguration
                                                                .getClientUid());
                                stringEvent.printLog(0, "AS.PlaybackActivityMon");
                                eventLogger.enqueue(stringEvent);
                                audioPlaybackConfiguration
                                        .getPlayerProxy()
                                        .setVolume(FullScreenMagnificationGestureHandler.MAX_SCALE);
                                this.mMutedPlayers.add(new Integer(num.intValue()));
                            } catch (Exception e) {
                                Log.e(
                                        "AS.PlaybackActivityMon",
                                        "call: error muting player " + num,
                                        e);
                            }
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void playerDeath(int i) {
        releasePlayer(i, 0);
    }

    public final void registerPlaybackCallback(
            IPlaybackConfigDispatcher iPlaybackConfigDispatcher, boolean z) {
        if (iPlaybackConfigDispatcher == null) {
            return;
        }
        PlayMonitorClient playMonitorClient =
                new PlayMonitorClient(
                        iPlaybackConfigDispatcher,
                        z,
                        Binder.getCallingUid(),
                        Binder.getCallingPid());
        synchronized (playMonitorClient) {
            if (playMonitorClient.mIsReleased) {
                return;
            }
            try {
                iPlaybackConfigDispatcher.asBinder().linkToDeath(playMonitorClient, 0);
                this.mClients.add(playMonitorClient);
            } catch (RemoteException e) {
                Log.w("AS.PlaybackActivityMon", "Could not link to client death", e);
            }
        }
    }

    public final void releasePlayer(int i, int i2) {
        boolean z;
        Log.v("AS.PlaybackActivityMon", "releasePlayer() for piid=" + i);
        if (i == -1) {
            NetworkScoreService$$ExternalSyntheticOutline0.m(
                    i, "Received releasePlayer with invalid piid: ", "AS.PlaybackActivityMon");
            sEventLogger.enqueue(
                    new EventLogger.StringEvent(
                            ArrayUtils$$ExternalSyntheticOutline0.m(
                                    i, i2, "releasePlayer with invalid piid:", ", uid:")));
            return;
        }
        synchronized (this.mPlayerLock) {
            try {
                AudioPlaybackConfiguration audioPlaybackConfiguration =
                        (AudioPlaybackConfiguration) this.mPlayers.get(new Integer(i));
                z = false;
                if (checkConfigurationCaller(i, audioPlaybackConfiguration, i2)) {
                    sEventLogger.enqueue(
                            new EventLogger.StringEvent(
                                    "releasing player piid:" + i + ", uid:" + i2));
                    this.mPlayers.remove(new Integer(i));
                    this.mDuckingManager.removeReleased(audioPlaybackConfiguration);
                    this.mFadeOutManager.removeReleased(audioPlaybackConfiguration);
                    this.mMutedPlayersAwaitingConnection.remove(Integer.valueOf(i));
                    checkVolumeForPrivilegedAlarm(audioPlaybackConfiguration, 0);
                    boolean handleStateEvent = audioPlaybackConfiguration.handleStateEvent(0, 0);
                    com.android.media.audio.Flags.portToPiidSimplification();
                    while (true) {
                        int indexOfValue = this.mPortIdToPiid.indexOfValue(i);
                        if (indexOfValue < 0) {
                            break;
                        } else {
                            this.mPortIdToPiid.removeAt(indexOfValue);
                        }
                    }
                    if (!handleStateEvent || !this.mDoNotLogPiidList.contains(Integer.valueOf(i))) {
                        z = handleStateEvent;
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (z) {
            dispatchPlaybackChange(true);
        }
    }

    @Override // com.android.server.audio.PlayerFocusEnforcer
    public final void restoreVShapedPlayers(FocusRequester focusRequester) {
        Log.v("AS.PlaybackActivityMon", "unduckPlayers: uids winner=" + focusRequester.mCallingUid);
        synchronized (this.mPlayerLock) {
            this.mDuckingManager.unduckUid(focusRequester.mCallingUid, this.mPlayers);
            this.mFadeOutManager.unfadeOutUid(focusRequester.mCallingUid, this.mPlayers);
        }
    }

    public final void setAllowedCapturePolicy(int i, int i2) {
        synchronized (this.mAllowedCapturePolicies) {
            try {
                if (i2 == 1) {
                    this.mAllowedCapturePolicies.remove(Integer.valueOf(i));
                    return;
                }
                this.mAllowedCapturePolicies.put(Integer.valueOf(i), Integer.valueOf(i2));
                synchronized (this.mPlayerLock) {
                    try {
                        for (AudioPlaybackConfiguration audioPlaybackConfiguration :
                                this.mPlayers.values()) {
                            if (audioPlaybackConfiguration.getClientUid() == i
                                    && audioPlaybackConfiguration
                                                    .getAudioAttributes()
                                                    .getAllowedCapturePolicy()
                                            < i2) {
                                audioPlaybackConfiguration.handleAudioAttributesEvent(
                                        new AudioAttributes.Builder(
                                                        audioPlaybackConfiguration
                                                                .getAudioAttributes())
                                                .setAllowedCapturePolicy(i2)
                                                .build());
                            }
                        }
                    } finally {
                    }
                }
            } finally {
            }
        }
    }

    public final void setTransientFadeManagerConfiguration(
            FadeManagerConfiguration fadeManagerConfiguration) {
        FadeOutManager fadeOutManager = this.mFadeOutManager;
        synchronized (fadeOutManager.mLock) {
            FadeConfigurations fadeConfigurations = fadeOutManager.mFadeConfigurations;
            fadeConfigurations.getClass();
            if (Flags.enableFadeManagerConfiguration()) {
                synchronized (fadeConfigurations.mLock) {
                    Objects.requireNonNull(
                            fadeManagerConfiguration,
                            "Transient FadeManagerConfiguration cannot be null");
                    fadeConfigurations.mTransientFadeManagerConfig = fadeManagerConfiguration;
                    fadeConfigurations.mActiveFadeManagerConfig =
                            fadeConfigurations.getActiveFadeMgrConfigLocked();
                }
            }
        }
    }

    @Override // com.android.server.audio.PlayerFocusEnforcer
    public final boolean shouldEnforceFade() {
        boolean isFadeEnabled;
        FadeConfigurations fadeConfigurations = this.mFadeOutManager.mFadeConfigurations;
        fadeConfigurations.getClass();
        if (!Flags.enableFadeManagerConfiguration()) {
            return true;
        }
        synchronized (fadeConfigurations.mLock) {
            isFadeEnabled = fadeConfigurations.getUpdatedFadeManagerConfigLocked().isFadeEnabled();
        }
        return isFadeEnabled;
    }

    public final void unmutePlayersExpectingDevice() {
        this.mMutedUsagesAwaitingConnection = null;
        Iterator it = this.mMutedPlayersAwaitingConnection.iterator();
        while (it.hasNext()) {
            Integer num = (Integer) it.next();
            int intValue = num.intValue();
            AudioPlaybackConfiguration audioPlaybackConfiguration =
                    (AudioPlaybackConfiguration) this.mPlayers.get(num);
            if (audioPlaybackConfiguration != null) {
                try {
                    EventLogger eventLogger = sEventLogger;
                    EventLogger.StringEvent stringEvent =
                            new EventLogger.StringEvent("unmuting piid:" + intValue);
                    stringEvent.printLog(0, "AS.PlaybackActivityMon");
                    eventLogger.enqueue(stringEvent);
                    audioPlaybackConfiguration
                            .getPlayerProxy()
                            .applyVolumeShaper(
                                    MUTE_AWAIT_CONNECTION_VSHAPE, VolumeShaper.Operation.REVERSE);
                } catch (Exception e) {
                    StringBuilder m =
                            BatteryService$$ExternalSyntheticOutline0.m(
                                    intValue, "Error unmuting player ", " uid:");
                    m.append(audioPlaybackConfiguration.getClientUid());
                    Log.e("AS.PlaybackActivityMon", m.toString(), e);
                }
            }
        }
        this.mMutedPlayersAwaitingConnection.clear();
    }

    @Override // com.android.server.audio.PlayerFocusEnforcer
    public final void unmutePlayersForCall() {
        Log.v("AS.PlaybackActivityMon", "unmutePlayersForCall()");
        synchronized (this.mPlayerLock) {
            try {
                if (this.mMutedPlayers.isEmpty()) {
                    return;
                }
                Iterator it = this.mMutedPlayers.iterator();
                while (it.hasNext()) {
                    Integer num = (Integer) it.next();
                    int intValue = num.intValue();
                    AudioPlaybackConfiguration audioPlaybackConfiguration =
                            (AudioPlaybackConfiguration) this.mPlayers.get(num);
                    if (audioPlaybackConfiguration != null) {
                        try {
                            EventLogger eventLogger = sEventLogger;
                            EventLogger.StringEvent stringEvent =
                                    new EventLogger.StringEvent("call: unmuting piid:" + intValue);
                            stringEvent.printLog(0, "AS.PlaybackActivityMon");
                            eventLogger.enqueue(stringEvent);
                            audioPlaybackConfiguration.getPlayerProxy().setVolume(1.0f);
                        } catch (Exception e) {
                            Log.e(
                                    "AS.PlaybackActivityMon",
                                    "call: error unmuting player "
                                            + intValue
                                            + " uid:"
                                            + audioPlaybackConfiguration.getClientUid(),
                                    e);
                        }
                    }
                }
                this.mMutedPlayers.clear();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void unregisterPlaybackCallback(
            IPlaybackConfigDispatcher iPlaybackConfigDispatcher) {
        boolean equals;
        if (iPlaybackConfigDispatcher == null) {
            return;
        }
        Iterator it = this.mClients.iterator();
        while (it.hasNext()) {
            PlayMonitorClient playMonitorClient = (PlayMonitorClient) it.next();
            synchronized (playMonitorClient) {
                equals =
                        iPlaybackConfigDispatcher
                                .asBinder()
                                .equals(playMonitorClient.mDispatcherCb.asBinder());
            }
            if (equals) {
                synchronized (playMonitorClient) {
                    playMonitorClient.mDispatcherCb.asBinder().unlinkToDeath(playMonitorClient, 0);
                    playMonitorClient.mIsReleased = true;
                }
                it.remove();
            }
        }
    }

    public final void updateGoodCatch(AudioPlaybackConfiguration audioPlaybackConfiguration) {
        String packageNameForPid;
        String str;
        int legacyStreamType =
                AudioAttributes.toLegacyStreamType(audioPlaybackConfiguration.getAudioAttributes());
        if (((HashSet) mGoodCatchSkipStream).contains(Integer.valueOf(legacyStreamType))) {
            return;
        }
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - this.mLastUpdateTime > 10000) {
            this.mPackageTimeMap.clear();
        }
        boolean z = this.mAudioService.getRingerModeInternal() == 2;
        int clientUid = audioPlaybackConfiguration.getClientUid();
        int clientPid = audioPlaybackConfiguration.getClientPid();
        if (clientUid == 1000) {
            packageNameForPid = getPackageNameForPid(clientPid);
        } else {
            String[] packagesForUid =
                    this.mContext.getPackageManager().getPackagesForUid(clientUid);
            if (packagesForUid == null || packagesForUid.length <= 0) {
                packageNameForPid = getPackageNameForPid(clientPid);
            } else if (packagesForUid.length == 1) {
                packageNameForPid = packagesForUid[0];
            } else {
                for (String str2 : packagesForUid) {
                    if (str2.equals(Constants.SYSTEMUI_PACKAGE_NAME)) {
                        str = str2;
                        break;
                    }
                }
                packageNameForPid = getPackageNameForPid(clientPid);
            }
        }
        str = packageNameForPid;
        if (!this.mPackageTimeMap.containsKey(str)
                || currentTimeMillis - ((Long) this.mPackageTimeMap.get(str)).longValue() > 10000) {
            if (Constants.SYSTEMUI_PACKAGE_NAME.equals(str)) {
                if (((HashSet) mGoodCatchSystemUiStream)
                        .contains(Integer.valueOf(legacyStreamType))) {
                    Log.d(
                            "AS.PlaybackActivityMon",
                            "updateGoodCatch systemui sound, so skip updateSoundPlayed");
                    return;
                }
            }
            if (!z && this.mAudioService.isStreamAffectedByRingerMode(legacyStreamType)) {
                Log.d(
                        "AS.PlaybackActivityMon",
                        "updateGoodCatch no ringerModeNormal, so skip updateSoundPlayed");
                return;
            }
            this.mLastUpdateTime = currentTimeMillis;
            this.mPackageTimeMap.put(str, Long.valueOf(currentTimeMillis));
            GoodCatchManager goodCatchManager = this.mAudioService.mGoodCatchManager;
            if (goodCatchManager != null) {
                goodCatchManager.mSemGoodCatchManager.update(
                        GoodCatchManager.SOUND_FUNC[3], str, legacyStreamType, "", "");
            }
        }
    }
}
