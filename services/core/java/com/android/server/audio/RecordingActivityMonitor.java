package com.android.server.audio;

import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AudioDeviceInfo;
import android.media.AudioFormat;
import android.media.AudioRecordingConfiguration;
import android.media.AudioSystem;
import android.media.IRecordingConfigDispatcher;
import android.media.MediaRecorder;
import android.media.audiofx.AudioEffect;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.utils.EventLogger;
import com.att.iqi.lib.BuildConfig;
import com.samsung.android.audio.Rune;
import com.samsung.android.game.SemGameManager;
import com.samsung.android.server.audio.AudioSettingsHelper;
import com.samsung.android.server.audio.MicModeManager;
import com.samsung.android.server.audio.RecordingPopupHelper;
import com.samsung.android.server.audio.utils.KaraokeUtils;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes.dex */
public final class RecordingActivityMonitor implements AudioSystem.AudioRecordingCallback {
    public static final EventLogger sEventLogger = new EventLogger(50, "recording activity received by AudioService");
    public AudioSettingsHelper mAudioSettingsHelper;
    public IRecordingEventChecker mChecker;
    public final Context mContext;
    public final PackageManager mPackMan;
    public ArrayList mClients = new ArrayList();
    public boolean mHasPublicClients = false;
    public AtomicInteger mLegacyRemoteSubmixRiid = new AtomicInteger(-1);
    public AtomicBoolean mLegacyRemoteSubmixActive = new AtomicBoolean(false);
    public List mRecordStates = new ArrayList();
    public Handler mAudioHandler = null;

    public interface IRecordingEventChecker {
        void notifyRecordingEvent(String str, int i);
    }

    public final class RecordingState {
        public AudioRecordingConfiguration mConfig;
        public final RecorderDeathHandler mDeathHandler;
        public boolean mIsActive;
        public final int mRiid;

        public RecordingState(int i, RecorderDeathHandler recorderDeathHandler) {
            this.mRiid = i;
            this.mDeathHandler = recorderDeathHandler;
        }

        public RecordingState(AudioRecordingConfiguration audioRecordingConfiguration) {
            this.mRiid = -1;
            this.mDeathHandler = null;
            this.mConfig = audioRecordingConfiguration;
        }

        public int getRiid() {
            return this.mRiid;
        }

        public int getPortId() {
            AudioRecordingConfiguration audioRecordingConfiguration = this.mConfig;
            if (audioRecordingConfiguration != null) {
                return audioRecordingConfiguration.getClientPortId();
            }
            return -1;
        }

        public AudioRecordingConfiguration getConfig() {
            return this.mConfig;
        }

        public boolean hasDeathHandler() {
            return this.mDeathHandler != null;
        }

        public boolean isActiveConfiguration() {
            return this.mIsActive && this.mConfig != null;
        }

        public void release() {
            RecorderDeathHandler recorderDeathHandler = this.mDeathHandler;
            if (recorderDeathHandler != null) {
                recorderDeathHandler.release();
            }
        }

        public boolean setActive(boolean z) {
            if (this.mIsActive == z) {
                return false;
            }
            this.mIsActive = z;
            return this.mConfig != null;
        }

        public boolean setConfig(AudioRecordingConfiguration audioRecordingConfiguration) {
            if (audioRecordingConfiguration.equals(this.mConfig)) {
                return false;
            }
            this.mConfig = audioRecordingConfiguration;
            return this.mIsActive;
        }

        public void dump(PrintWriter printWriter) {
            printWriter.println("riid " + this.mRiid + "; active? " + this.mIsActive);
            AudioRecordingConfiguration audioRecordingConfiguration = this.mConfig;
            if (audioRecordingConfiguration != null) {
                audioRecordingConfiguration.dump(printWriter);
            } else {
                printWriter.println("  no config");
            }
        }
    }

    public RecordingActivityMonitor(Context context) {
        RecMonitorClient.sMonitor = this;
        RecorderDeathHandler.sMonitor = this;
        this.mPackMan = context.getPackageManager();
        this.mContext = context;
        this.mAudioSettingsHelper = AudioSettingsHelper.getInstance(context);
    }

    public void onRecordingConfigurationChanged(int i, int i2, int i3, int i4, int i5, int i6, boolean z, int[] iArr, AudioEffect.Descriptor[] descriptorArr, AudioEffect.Descriptor[] descriptorArr2, int i7, String str) {
        AudioDeviceInfo audioDevice;
        AudioRecordingConfiguration createRecordingConfiguration = createRecordingConfiguration(i3, i4, i5, iArr, i6, z, i7, descriptorArr, descriptorArr2);
        if (i5 == 8 && ((i == 0 || i == 2) && (audioDevice = createRecordingConfiguration.getAudioDevice()) != null && "0".equals(audioDevice.getAddress()))) {
            this.mLegacyRemoteSubmixRiid.set(i2);
            this.mLegacyRemoteSubmixActive.set(true);
        }
        if (MediaRecorder.isSystemOnlyAudioSource(i5) && MediaRecorder.isSemSystemOnlyAudioSource(i5)) {
            sEventLogger.enqueue(new RecordingEvent(i, i2, createRecordingConfiguration).printLog("AudioService.RecordingActivityMonitor"));
            return;
        }
        if (Rune.SEC_AUDIO_RECORDING_POPUP && i == 99) {
            Log.d("AudioService.RecordingActivityMonitor", "[RECORDING POPUP] onRecordingConfigurationChanged on event " + i);
            sEventLogger.enqueue(new RecordingEvent(i, i2, createRecordingConfiguration).printLog("AudioService.RecordingActivityMonitor"));
            AudioDeviceInfo audioDevice2 = createRecordingConfiguration.getAudioDevice();
            RecordingPopupHelper.notifyRecordingPopup(this.mAudioHandler, i3, i6, audioDevice2 != null ? audioDevice2.getType() : 0);
            return;
        }
        dispatchCallbacks(updateSnapshot(i, i2, createRecordingConfiguration));
    }

    public int trackRecorder(IBinder iBinder) {
        if (iBinder == null) {
            Log.e("AudioService.RecordingActivityMonitor", "trackRecorder called with null token");
            return -1;
        }
        int newAudioRecorderId = AudioSystem.newAudioRecorderId();
        RecorderDeathHandler recorderDeathHandler = new RecorderDeathHandler(newAudioRecorderId, iBinder);
        if (!recorderDeathHandler.init()) {
            return -1;
        }
        synchronized (this.mRecordStates) {
            this.mRecordStates.add(new RecordingState(newAudioRecorderId, recorderDeathHandler));
        }
        return newAudioRecorderId;
    }

    public void recorderEvent(int i, int i2) {
        if (this.mLegacyRemoteSubmixRiid.get() == i) {
            this.mLegacyRemoteSubmixActive.set(i2 == 0);
        }
        int i3 = i2 != 0 ? i2 == 1 ? 1 : -1 : 0;
        if (i == -1 || i3 == -1) {
            sEventLogger.enqueue(new RecordingEvent(i2, i, null).printLog("AudioService.RecordingActivityMonitor"));
        } else {
            dispatchCallbacks(updateSnapshot(i3, i, null));
        }
    }

    public void releaseRecorder(int i) {
        dispatchCallbacks(updateSnapshot(3, i, null));
    }

    public boolean isRecordingActiveForUid(int i) {
        synchronized (this.mRecordStates) {
            for (RecordingState recordingState : this.mRecordStates) {
                if (recordingState.isActiveConfiguration() && recordingState.getConfig().getClientUid() == i && !recordingState.getConfig().isClientSilenced()) {
                    return true;
                }
            }
            return false;
        }
    }

    public final void dispatchCallbacks(List list) {
        ArrayList arrayList;
        if (list == null) {
            return;
        }
        synchronized (this.mClients) {
            if (this.mHasPublicClients) {
                arrayList = anonymizeForPublicConsumption(list);
            } else {
                arrayList = new ArrayList();
            }
            Iterator it = this.mClients.iterator();
            while (it.hasNext()) {
                RecMonitorClient recMonitorClient = (RecMonitorClient) it.next();
                try {
                    if (recMonitorClient.mIsPrivileged) {
                        recMonitorClient.mDispatcherCb.dispatchRecordingConfigChange(list);
                    } else {
                        recMonitorClient.mDispatcherCb.dispatchRecordingConfigChange(arrayList);
                    }
                } catch (RemoteException e) {
                    Log.w("AudioService.RecordingActivityMonitor", "Could not call dispatchRecordingConfigChange() on client", e);
                }
            }
        }
    }

    public void dump(PrintWriter printWriter) {
        printWriter.println("\nRecordActivityMonitor dump time: " + DateFormat.getTimeInstance().format(new Date()));
        synchronized (this.mRecordStates) {
            Iterator it = this.mRecordStates.iterator();
            while (it.hasNext()) {
                ((RecordingState) it.next()).dump(printWriter);
            }
        }
        printWriter.println(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        sEventLogger.dump(printWriter);
    }

    public static ArrayList anonymizeForPublicConsumption(List list) {
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(AudioRecordingConfiguration.anonymizedCopy((AudioRecordingConfiguration) it.next()));
        }
        return arrayList;
    }

    public void initMonitor() {
        AudioSystem.setRecordingCallback(this);
    }

    public void onAudioServerDied() {
        List activeRecordingConfigurations;
        synchronized (this.mRecordStates) {
            Iterator it = this.mRecordStates.iterator();
            boolean z = false;
            while (it.hasNext()) {
                RecordingState recordingState = (RecordingState) it.next();
                if (!recordingState.hasDeathHandler()) {
                    if (recordingState.isActiveConfiguration()) {
                        sEventLogger.enqueue(new RecordingEvent(3, recordingState.getRiid(), recordingState.getConfig()));
                        z = true;
                    }
                    it.remove();
                }
            }
            activeRecordingConfigurations = z ? getActiveRecordingConfigurations(true) : null;
        }
        dispatchCallbacks(activeRecordingConfigurations);
    }

    public void registerRecordingCallback(IRecordingConfigDispatcher iRecordingConfigDispatcher, boolean z) {
        if (iRecordingConfigDispatcher == null) {
            return;
        }
        synchronized (this.mClients) {
            RecMonitorClient recMonitorClient = new RecMonitorClient(iRecordingConfigDispatcher, z);
            if (recMonitorClient.init()) {
                if (!z) {
                    this.mHasPublicClients = true;
                }
                this.mClients.add(recMonitorClient);
            }
        }
    }

    public void unregisterRecordingCallback(IRecordingConfigDispatcher iRecordingConfigDispatcher) {
        if (iRecordingConfigDispatcher == null) {
            return;
        }
        synchronized (this.mClients) {
            Iterator it = this.mClients.iterator();
            boolean z = false;
            while (it.hasNext()) {
                RecMonitorClient recMonitorClient = (RecMonitorClient) it.next();
                if (iRecordingConfigDispatcher.asBinder().equals(recMonitorClient.mDispatcherCb.asBinder())) {
                    recMonitorClient.release();
                    it.remove();
                } else if (!recMonitorClient.mIsPrivileged) {
                    z = true;
                }
            }
            this.mHasPublicClients = z;
        }
    }

    public List getActiveRecordingConfigurations(boolean z) {
        ArrayList arrayList = new ArrayList();
        synchronized (this.mRecordStates) {
            for (RecordingState recordingState : this.mRecordStates) {
                if (recordingState.isActiveConfiguration()) {
                    arrayList.add(recordingState.getConfig());
                }
            }
        }
        return !z ? anonymizeForPublicConsumption(arrayList) : arrayList;
    }

    public boolean isLegacyRemoteSubmixActive() {
        return this.mLegacyRemoteSubmixActive.get();
    }

    public final AudioRecordingConfiguration createRecordingConfiguration(int i, int i2, int i3, int[] iArr, int i4, boolean z, int i5, AudioEffect.Descriptor[] descriptorArr, AudioEffect.Descriptor[] descriptorArr2) {
        String str;
        AudioFormat build = new AudioFormat.Builder().setEncoding(iArr[0]).setChannelMask(iArr[1]).setSampleRate(iArr[2]).build();
        AudioFormat build2 = new AudioFormat.Builder().setEncoding(iArr[3]).setChannelMask(iArr[4]).setSampleRate(iArr[5]).build();
        int i6 = iArr[6];
        String[] packagesForUid = this.mPackMan.getPackagesForUid(i);
        if (packagesForUid == null || packagesForUid.length <= 0) {
            str = "";
        } else {
            str = i == 1000 ? "system" : packagesForUid[0];
        }
        return new AudioRecordingConfiguration(i, i2, i3, build, build2, i6, str, i4, z, i5, descriptorArr, descriptorArr2);
    }

    public final List updateSnapshot(int i, int i2, AudioRecordingConfiguration audioRecordingConfiguration) {
        int findStateByPortId;
        synchronized (this.mRecordStates) {
            try {
                if (i2 != -1) {
                    findStateByPortId = findStateByRiid(i2);
                } else {
                    findStateByPortId = audioRecordingConfiguration != null ? findStateByPortId(audioRecordingConfiguration.getClientPortId()) : -1;
                }
                boolean z = false;
                List list = null;
                if (findStateByPortId == -1) {
                    if (i != 0 || audioRecordingConfiguration == null) {
                        if (audioRecordingConfiguration == null) {
                            Log.e("AudioService.RecordingActivityMonitor", String.format("Unexpected event %d for riid %d", Integer.valueOf(i), Integer.valueOf(i2)));
                        }
                        return null;
                    }
                    this.mRecordStates.add(new RecordingState(audioRecordingConfiguration));
                    findStateByPortId = this.mRecordStates.size() - 1;
                }
                RecordingState recordingState = (RecordingState) this.mRecordStates.get(findStateByPortId);
                if (i == 0) {
                    boolean active = recordingState.setActive(true);
                    if (audioRecordingConfiguration == null) {
                        z = active;
                    } else if (recordingState.setConfig(audioRecordingConfiguration) || active) {
                        z = true;
                    }
                } else if (i == 1) {
                    z = recordingState.setActive(false);
                    if (!recordingState.hasDeathHandler()) {
                        this.mRecordStates.remove(findStateByPortId);
                    }
                } else if (i == 2) {
                    z = recordingState.setConfig(audioRecordingConfiguration);
                } else if (i == 3) {
                    z = recordingState.isActiveConfiguration();
                    recordingState.release();
                    this.mRecordStates.remove(findStateByPortId);
                } else {
                    Log.e("AudioService.RecordingActivityMonitor", String.format("Unknown event %d for riid %d / portid %d", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(recordingState.getPortId())));
                }
                if (z) {
                    sEventLogger.enqueue(new RecordingEvent(i, i2, recordingState.getConfig()));
                    updateSoundAppPolicy(recordingState.getConfig(), i);
                    list = getActiveRecordingConfigurations(true);
                }
                return list;
            } finally {
            }
        }
    }

    public final int findStateByRiid(int i) {
        synchronized (this.mRecordStates) {
            for (int i2 = 0; i2 < this.mRecordStates.size(); i2++) {
                if (((RecordingState) this.mRecordStates.get(i2)).getRiid() == i) {
                    return i2;
                }
            }
            return -1;
        }
    }

    public final int findStateByPortId(int i) {
        synchronized (this.mRecordStates) {
            for (int i2 = 0; i2 < this.mRecordStates.size(); i2++) {
                if (!((RecordingState) this.mRecordStates.get(i2)).hasDeathHandler() && ((RecordingState) this.mRecordStates.get(i2)).getPortId() == i) {
                    return i2;
                }
            }
            return -1;
        }
    }

    public final class RecMonitorClient implements IBinder.DeathRecipient {
        public static RecordingActivityMonitor sMonitor;
        public final IRecordingConfigDispatcher mDispatcherCb;
        public final boolean mIsPrivileged;

        public RecMonitorClient(IRecordingConfigDispatcher iRecordingConfigDispatcher, boolean z) {
            this.mDispatcherCb = iRecordingConfigDispatcher;
            this.mIsPrivileged = z;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            Log.w("AudioService.RecordingActivityMonitor", "client died");
            sMonitor.unregisterRecordingCallback(this.mDispatcherCb);
        }

        public boolean init() {
            try {
                this.mDispatcherCb.asBinder().linkToDeath(this, 0);
                return true;
            } catch (RemoteException e) {
                Log.w("AudioService.RecordingActivityMonitor", "Could not link to client death", e);
                return false;
            }
        }

        public void release() {
            this.mDispatcherCb.asBinder().unlinkToDeath(this, 0);
        }
    }

    public final class RecorderDeathHandler implements IBinder.DeathRecipient {
        public static RecordingActivityMonitor sMonitor;
        public final IBinder mRecorderToken;
        public final int mRiid;

        public RecorderDeathHandler(int i, IBinder iBinder) {
            this.mRiid = i;
            this.mRecorderToken = iBinder;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            sMonitor.releaseRecorder(this.mRiid);
        }

        public boolean init() {
            try {
                this.mRecorderToken.linkToDeath(this, 0);
                return true;
            } catch (RemoteException e) {
                Log.w("AudioService.RecordingActivityMonitor", "Could not link to recorder death", e);
                return false;
            }
        }

        public void release() {
            this.mRecorderToken.unlinkToDeath(this, 0);
        }
    }

    public final class RecordingEvent extends EventLogger.Event {
        public final int mClientUid;
        public final String mPackName;
        public final int mRIId;
        public final int mRecEvent;
        public final int mSession;
        public final boolean mSilenced;
        public final int mSource;

        public RecordingEvent(int i, int i2, AudioRecordingConfiguration audioRecordingConfiguration) {
            this.mRecEvent = i;
            this.mRIId = i2;
            if (audioRecordingConfiguration != null) {
                this.mClientUid = audioRecordingConfiguration.getClientUid();
                this.mSession = audioRecordingConfiguration.getClientAudioSessionId();
                this.mSource = audioRecordingConfiguration.getClientAudioSource();
                this.mPackName = audioRecordingConfiguration.getClientPackageName();
                this.mSilenced = audioRecordingConfiguration.isClientSilenced();
                return;
            }
            this.mClientUid = -1;
            this.mSession = -1;
            this.mSource = -1;
            this.mPackName = null;
            this.mSilenced = false;
        }

        public static String recordEventToString(int i) {
            if (i == 0) {
                return "start";
            }
            if (i == 1) {
                return "stop";
            }
            if (i == 2) {
                return "update";
            }
            if (i == 3) {
                return BuildConfig.BUILD_TYPE;
            }
            if (i == 99) {
                return "popup";
            }
            return "unknown (" + i + ")";
        }

        @Override // com.android.server.utils.EventLogger.Event
        public String eventToString() {
            String str;
            StringBuilder sb = new StringBuilder("rec ");
            sb.append(recordEventToString(this.mRecEvent));
            sb.append(" riid:");
            sb.append(this.mRIId);
            sb.append(" uid:");
            sb.append(this.mClientUid);
            sb.append(" session:");
            sb.append(this.mSession);
            sb.append(" src:");
            sb.append(MediaRecorder.toLogFriendlyAudioSource(this.mSource));
            sb.append(this.mSilenced ? " silenced" : " not silenced");
            if (this.mPackName == null) {
                str = "";
            } else {
                str = " pack:" + this.mPackName;
            }
            sb.append(str);
            return sb.toString();
        }
    }

    public void setAudioHandler(Handler handler) {
        this.mAudioHandler = handler;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r3v2 */
    public void updateSoundAppPolicy(AudioRecordingConfiguration audioRecordingConfiguration, int i) {
        if (audioRecordingConfiguration == null) {
            return;
        }
        String clientPackageName = audioRecordingConfiguration.getClientPackageName();
        ?? r3 = (i == 0 || i == 2) ? 1 : 0;
        Log.v("AudioService.RecordingActivityMonitor", "updateSoundAppPolicy packageName = " + clientPackageName);
        if (Rune.SEC_AUDIO_KARAOKE_LISTENBACK && AudioServiceExt.mKaraokeListenbackEnabled == 1 && this.mAudioSettingsHelper.checkAppCategory(clientPackageName, "karaoke_listenback_allow")) {
            KaraokeUtils.setKaraokeListenback(r3);
        }
        IRecordingEventChecker iRecordingEventChecker = this.mChecker;
        if (iRecordingEventChecker != null && (i == 0 || i == 1 || i == 2 || i == 3)) {
            iRecordingEventChecker.notifyRecordingEvent(clientPackageName, audioRecordingConfiguration.getAudioSource());
        }
        if (Rune.SEC_AUDIO_KARAOKE && this.mAudioSettingsHelper.checkAppCategory(clientPackageName, "karaoke_allow")) {
            AudioSystem.setParameters("l_karaoke_enable=" + ((boolean) r3));
            return;
        }
        if (Rune.SEC_AUDIO_GAMECHAT_SPEAKER_AEC) {
            try {
                if (SemGameManager.isGamePackage(clientPackageName)) {
                    AudioSystem.setParameters("l_game_chat_enable=" + ((boolean) r3));
                    if (Rune.SEC_AUDIO_MIC_MODE_FOR_QUICK_PANEL_UI) {
                        Log.i("AudioService.RecordingActivityMonitor", "updateSoundAppPolicy SEC_LOCAL_GAME_CHAT_ENABLE : " + ((boolean) r3));
                        MicModeManager.getInstance(this.mContext).updateGameChatState(r3);
                    }
                }
            } catch (IllegalStateException e) {
                Log.e("AudioService.RecordingActivityMonitor", "updateSnapshot", e);
            }
        }
    }

    public boolean isOnlyKaraokeRecordingActive() {
        synchronized (this.mRecordStates) {
            int i = 0;
            int i2 = 0;
            for (RecordingState recordingState : this.mRecordStates) {
                if (recordingState.isActiveConfiguration()) {
                    String[] packagesForUid = this.mPackMan.getPackagesForUid(recordingState.getConfig().getClientUid());
                    if (packagesForUid != null && packagesForUid.length > 0) {
                        if (this.mAudioSettingsHelper.checkAppCategory(packagesForUid[0], "karaoke_listenback_allow")) {
                            i++;
                        } else {
                            i2++;
                        }
                    }
                }
            }
            return i > 0 && i2 == 0;
        }
    }

    public void setRecordingEventChecker(IRecordingEventChecker iRecordingEventChecker) {
        this.mChecker = iRecordingEventChecker;
    }
}
