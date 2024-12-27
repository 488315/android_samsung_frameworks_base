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
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.audio.AudioService;
import com.android.server.utils.EventLogger;
import com.att.iqi.lib.BuildConfig;
import com.samsung.android.audio.Rune;
import com.samsung.android.game.SemGameManager;
import com.samsung.android.server.audio.AudioSettingsHelper;
import com.samsung.android.server.audio.MicModeManager;
import com.samsung.android.server.audio.RecordingPopupHelper;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public final class RecordingActivityMonitor implements AudioSystem.AudioRecordingCallback {
    public static final EventLogger sEventLogger = new EventLogger(50, "recording activity received by AudioService");
    public final AudioSettingsHelper mAudioSettingsHelper;
    public AudioService.AnonymousClass11 mChecker;
    public final Context mContext;
    public final PackageManager mPackMan;
    public final ArrayList mClients = new ArrayList();
    public boolean mHasPublicClients = false;
    public final AtomicInteger mLegacyRemoteSubmixRiid = new AtomicInteger(-1);
    public final AtomicBoolean mLegacyRemoteSubmixActive = new AtomicBoolean(false);
    public final List mRecordStates = new ArrayList();
    public Handler mAudioHandler = null;

    public final class RecMonitorClient implements IBinder.DeathRecipient {
        public static RecordingActivityMonitor sMonitor;
        public final IRecordingConfigDispatcher mDispatcherCb;
        public final boolean mIsPrivileged;

        public RecMonitorClient(IRecordingConfigDispatcher iRecordingConfigDispatcher, boolean z) {
            this.mDispatcherCb = iRecordingConfigDispatcher;
            this.mIsPrivileged = z;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            Log.w("AudioService.RecordingActivityMonitor", "client died");
            sMonitor.unregisterRecordingCallback(this.mDispatcherCb);
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
        public final void binderDied() {
            RecordingActivityMonitor recordingActivityMonitor = sMonitor;
            recordingActivityMonitor.dispatchCallbacks(recordingActivityMonitor.updateSnapshot(3, this.mRiid, null));
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

        @Override // com.android.server.utils.EventLogger.Event
        public final String eventToString() {
            StringBuilder sb = new StringBuilder("rec ");
            int i = this.mRecEvent;
            sb.append(i != 0 ? i != 1 ? i != 2 ? i != 3 ? i != 99 ? BinaryTransparencyService$$ExternalSyntheticOutline0.m(i, "unknown (", ")") : "popup" : BuildConfig.BUILD_TYPE : "update" : "stop" : "start");
            sb.append(" riid:");
            sb.append(this.mRIId);
            sb.append(" uid:");
            sb.append(this.mClientUid);
            sb.append(" session:");
            sb.append(this.mSession);
            sb.append(" src:");
            sb.append(MediaRecorder.toLogFriendlyAudioSource(this.mSource));
            sb.append(this.mSilenced ? " silenced" : " not silenced");
            String str = this.mPackName;
            sb.append(str == null ? "" : ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(" pack:", str));
            return sb.toString();
        }
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

        public final boolean isActiveConfiguration() {
            return this.mIsActive && this.mConfig != null;
        }
    }

    public RecordingActivityMonitor(Context context) {
        RecMonitorClient.sMonitor = this;
        RecorderDeathHandler.sMonitor = this;
        this.mPackMan = context.getPackageManager();
        this.mContext = context;
        this.mAudioSettingsHelper = AudioSettingsHelper.getInstance(context);
    }

    public final void dispatchCallbacks(List list) {
        ArrayList arrayList;
        if (list == null) {
            return;
        }
        synchronized (this.mClients) {
            try {
                if (this.mHasPublicClients) {
                    arrayList = new ArrayList();
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        arrayList.add(AudioRecordingConfiguration.anonymizedCopy((AudioRecordingConfiguration) it.next()));
                    }
                } else {
                    arrayList = new ArrayList();
                }
                Iterator it2 = this.mClients.iterator();
                while (it2.hasNext()) {
                    RecMonitorClient recMonitorClient = (RecMonitorClient) it2.next();
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
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void dump(PrintWriter printWriter) {
        printWriter.println("\nRecordActivityMonitor dump time: " + DateFormat.getTimeInstance().format(new Date()));
        synchronized (this.mRecordStates) {
            try {
                Iterator it = ((ArrayList) this.mRecordStates).iterator();
                while (it.hasNext()) {
                    RecordingState recordingState = (RecordingState) it.next();
                    StringBuilder sb = new StringBuilder("riid ");
                    sb.append(recordingState.mRiid);
                    sb.append("; active? ");
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(sb, recordingState.mIsActive, printWriter);
                    AudioRecordingConfiguration audioRecordingConfiguration = recordingState.mConfig;
                    if (audioRecordingConfiguration != null) {
                        audioRecordingConfiguration.dump(printWriter);
                    } else {
                        printWriter.println("  no config");
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        printWriter.println("\n");
        sEventLogger.dump(printWriter);
    }

    public final int findStateByPortId(int i) {
        synchronized (this.mRecordStates) {
            int i2 = 0;
            while (true) {
                try {
                    if (i2 >= ((ArrayList) this.mRecordStates).size()) {
                        return -1;
                    }
                    if (((RecordingState) ((ArrayList) this.mRecordStates).get(i2)).mDeathHandler == null) {
                        AudioRecordingConfiguration audioRecordingConfiguration = ((RecordingState) ((ArrayList) this.mRecordStates).get(i2)).mConfig;
                        if ((audioRecordingConfiguration != null ? audioRecordingConfiguration.getClientPortId() : -1) == i) {
                            return i2;
                        }
                    }
                    i2++;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final List getActiveRecordingConfigurations(boolean z) {
        ArrayList arrayList = new ArrayList();
        synchronized (this.mRecordStates) {
            try {
                Iterator it = ((ArrayList) this.mRecordStates).iterator();
                while (it.hasNext()) {
                    RecordingState recordingState = (RecordingState) it.next();
                    if (recordingState.isActiveConfiguration()) {
                        arrayList.add(recordingState.mConfig);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (z) {
            return arrayList;
        }
        ArrayList arrayList2 = new ArrayList();
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            arrayList2.add(AudioRecordingConfiguration.anonymizedCopy((AudioRecordingConfiguration) it2.next()));
        }
        return arrayList2;
    }

    public final boolean isRecordingActiveForUid(int i) {
        synchronized (this.mRecordStates) {
            try {
                Iterator it = ((ArrayList) this.mRecordStates).iterator();
                while (it.hasNext()) {
                    RecordingState recordingState = (RecordingState) it.next();
                    if (recordingState.isActiveConfiguration() && recordingState.mConfig.getClientUid() == i && !recordingState.mConfig.isClientSilenced()) {
                        return true;
                    }
                }
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onRecordingConfigurationChanged(int i, int i2, int i3, int i4, int i5, int i6, boolean z, int[] iArr, AudioEffect.Descriptor[] descriptorArr, AudioEffect.Descriptor[] descriptorArr2, int i7, String str) {
        int i8;
        AudioDeviceInfo audioDevice;
        AudioFormat build = new AudioFormat.Builder().setEncoding(iArr[0]).setChannelMask(iArr[1]).setSampleRate(iArr[2]).build();
        AudioFormat build2 = new AudioFormat.Builder().setEncoding(iArr[3]).setChannelMask(iArr[4]).setSampleRate(iArr[5]).build();
        int i9 = iArr[6];
        String[] packagesForUid = this.mPackMan.getPackagesForUid(i3);
        AudioRecordingConfiguration audioRecordingConfiguration = new AudioRecordingConfiguration(i3, i4, i5, build, build2, i9, (packagesForUid == null || packagesForUid.length <= 0) ? "" : packagesForUid[0], i6, z, i7, descriptorArr, descriptorArr2);
        if (i5 == 8 && ((i == 0 || i == 2) && (audioDevice = audioRecordingConfiguration.getAudioDevice()) != null && "0".equals(audioDevice.getAddress()))) {
            i8 = i2;
            this.mLegacyRemoteSubmixRiid.set(i8);
            this.mLegacyRemoteSubmixActive.set(true);
        } else {
            i8 = i2;
        }
        if (MediaRecorder.isSystemOnlyAudioSource(i5) && MediaRecorder.isSemSystemOnlyAudioSource(i5)) {
            EventLogger eventLogger = sEventLogger;
            RecordingEvent recordingEvent = new RecordingEvent(i, i8, audioRecordingConfiguration);
            recordingEvent.printLog(0, "AudioService.RecordingActivityMonitor");
            eventLogger.enqueue(recordingEvent);
            return;
        }
        if (!Rune.SEC_AUDIO_RECORDING_POPUP || i != 99) {
            dispatchCallbacks(updateSnapshot(i, i8, audioRecordingConfiguration));
            return;
        }
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "[RECORDING POPUP] onRecordingConfigurationChanged on event ", "AudioService.RecordingActivityMonitor");
        EventLogger eventLogger2 = sEventLogger;
        RecordingEvent recordingEvent2 = new RecordingEvent(i, i8, audioRecordingConfiguration);
        recordingEvent2.printLog(0, "AudioService.RecordingActivityMonitor");
        eventLogger2.enqueue(recordingEvent2);
        AudioDeviceInfo audioDevice2 = audioRecordingConfiguration.getAudioDevice();
        int type = audioDevice2 != null ? audioDevice2.getType() : 0;
        Handler handler = this.mAudioHandler;
        if (handler == null) {
            Log.w("AS.RecordingPopupHelper", "[RECORDING POPUP] There is no audio handler");
            return;
        }
        if (RecordingPopupHelper.sOldPortId != i6) {
            RecordingPopupHelper.sOldPortId = i6;
            long currentTimeMillis = System.currentTimeMillis();
            long j = currentTimeMillis - RecordingPopupHelper.sPreviousTime;
            RecordingPopupHelper.sPreviousTime = currentTimeMillis;
            StringBuilder m = ArrayUtils$$ExternalSyntheticOutline0.m(i3, i6, "[RECORDING POPUP] notifyRecordingPopup uid ", " portId ", " periodTime ");
            m.append(j);
            Log.d("AS.RecordingPopupHelper", m.toString());
            handler.removeMessages(2772);
            if (j > 4000) {
                handler.sendMessageDelayed(handler.obtainMessage(2772, i3, type), 1000L);
            } else {
                handler.sendMessageDelayed(handler.obtainMessage(2772, i3, type), 3000L);
            }
        }
    }

    public final void registerRecordingCallback(IRecordingConfigDispatcher iRecordingConfigDispatcher, boolean z) {
        if (iRecordingConfigDispatcher == null) {
            return;
        }
        synchronized (this.mClients) {
            try {
                RecMonitorClient recMonitorClient = new RecMonitorClient(iRecordingConfigDispatcher, z);
                try {
                    iRecordingConfigDispatcher.asBinder().linkToDeath(recMonitorClient, 0);
                    if (!z) {
                        this.mHasPublicClients = true;
                    }
                    this.mClients.add(recMonitorClient);
                } catch (RemoteException e) {
                    Log.w("AudioService.RecordingActivityMonitor", "Could not link to client death", e);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void unregisterRecordingCallback(IRecordingConfigDispatcher iRecordingConfigDispatcher) {
        if (iRecordingConfigDispatcher == null) {
            return;
        }
        synchronized (this.mClients) {
            try {
                Iterator it = this.mClients.iterator();
                boolean z = false;
                while (it.hasNext()) {
                    RecMonitorClient recMonitorClient = (RecMonitorClient) it.next();
                    if (iRecordingConfigDispatcher.asBinder().equals(recMonitorClient.mDispatcherCb.asBinder())) {
                        recMonitorClient.mDispatcherCb.asBinder().unlinkToDeath(recMonitorClient, 0);
                        it.remove();
                    } else if (!recMonitorClient.mIsPrivileged) {
                        z = true;
                    }
                }
                this.mHasPublicClients = z;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:86:0x0039, code lost:
    
        r10 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x012a, code lost:
    
        throw r10;
     */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00fb A[Catch: all -> 0x0039, TryCatch #1 {all -> 0x0039, all -> 0x0025, blocks: (B:6:0x0007, B:7:0x0009, B:22:0x0045, B:24:0x005d, B:25:0x0074, B:28:0x0076, B:35:0x008a, B:37:0x009a, B:38:0x009e, B:40:0x0112, B:41:0x0127, B:43:0x00af, B:45:0x00b7, B:46:0x00bc, B:47:0x00c5, B:50:0x00ce, B:51:0x00d3, B:54:0x00df, B:57:0x00e4, B:58:0x00d8, B:61:0x00ec, B:65:0x00fb, B:71:0x0105, B:73:0x00f2, B:83:0x002d, B:85:0x0030, B:10:0x000b, B:12:0x0015, B:16:0x0023, B:14:0x0027, B:77:0x002a), top: B:4:0x0005 }] */
    /* JADX WARN: Removed duplicated region for block: B:72:0x010f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.List updateSnapshot(int r11, int r12, android.media.AudioRecordingConfiguration r13) {
        /*
            Method dump skipped, instructions count: 299
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.audio.RecordingActivityMonitor.updateSnapshot(int, int, android.media.AudioRecordingConfiguration):java.util.List");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v0 */
    /* JADX WARN: Type inference failed for: r5v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r5v2 */
    public final void updateSoundAppPolicy(AudioRecordingConfiguration audioRecordingConfiguration, int i) {
        if (audioRecordingConfiguration == null) {
            return;
        }
        String clientPackageName = audioRecordingConfiguration.getClientPackageName();
        ?? r5 = (i == 0 || i == 2) ? 1 : 0;
        Log.v("AudioService.RecordingActivityMonitor", "updateSoundAppPolicy packageName = " + clientPackageName);
        if (Rune.SEC_AUDIO_KARAOKE_LISTENBACK && AudioServiceExt.mKaraokeListenbackEnabled == 1 && this.mAudioSettingsHelper.checkAppCategory(clientPackageName, "karaoke_listenback_allow")) {
            AudioSystem.setParameters("l_effect_listenback_key;state=" + ((int) r5));
        }
        AudioService.AnonymousClass11 anonymousClass11 = this.mChecker;
        if (anonymousClass11 != null && (i == 0 || i == 1 || i == 2 || i == 3)) {
            int audioSource = audioRecordingConfiguration.getAudioSource();
            int i2 = AudioService.BECOMING_NOISY_DELAY_MS;
            AudioService.this.sendBroadcastToSoundEventReceiver(128, audioSource, clientPackageName);
        }
        if (Rune.SEC_AUDIO_KARAOKE && this.mAudioSettingsHelper.checkAppCategory(clientPackageName, "karaoke_allow")) {
            AudioSystem.setParameters("l_karaoke_enable=" + ((boolean) r5));
            return;
        }
        if (Rune.SEC_AUDIO_GAMECHAT_SPEAKER_AEC) {
            try {
                if (SemGameManager.isGamePackage(clientPackageName)) {
                    AudioSystem.setParameters("l_game_chat_enable=" + ((boolean) r5));
                    if (Rune.SEC_AUDIO_MIC_MODE_FOR_QUICK_PANEL_UI) {
                        Log.i("AudioService.RecordingActivityMonitor", "updateSoundAppPolicy SEC_LOCAL_GAME_CHAT_ENABLE : " + ((boolean) r5));
                        MicModeManager.getInstance(this.mContext).updateState(32, r5);
                    }
                }
            } catch (IllegalStateException e) {
                Log.e("AudioService.RecordingActivityMonitor", "updateSnapshot", e);
            }
        }
    }
}
