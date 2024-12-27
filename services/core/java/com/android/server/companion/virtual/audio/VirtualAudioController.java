package com.android.server.companion.virtual.audio;

import android.companion.virtual.audio.IAudioConfigChangedCallback;
import android.companion.virtual.audio.IAudioRoutingCallback;
import android.companion.virtualdevice.flags.Flags;
import android.content.AttributionSource;
import android.content.Context;
import android.media.AudioManager;
import android.media.AudioPlaybackConfiguration;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.util.ArraySet;
import android.util.Slog;
import com.android.modules.expresslog.Counter;
import com.android.server.companion.virtual.GenericWindowPolicyController;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class VirtualAudioController implements GenericWindowPolicyController.RunningAppsChangedListener {
    public final AudioPlaybackDetector mAudioPlaybackDetector;
    public final AudioRecordingDetector mAudioRecordingDetector;
    public IAudioConfigChangedCallback mConfigChangedCallback;
    public final Context mContext;
    public GenericWindowPolicyController mGenericWindowPolicyController;
    public IAudioRoutingCallback mRoutingCallback;
    public final Handler mHandler = new Handler(Looper.getMainLooper());
    public final VirtualAudioController$$ExternalSyntheticLambda0 mUpdateAudioRoutingRunnable = new Runnable() { // from class: com.android.server.companion.virtual.audio.VirtualAudioController$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            VirtualAudioController.this.notifyAppsNeedingAudioRoutingChanged();
        }
    };
    public final Object mLock = new Object();
    public final ArraySet mRunningAppUids = new ArraySet();
    public ArraySet mPlayingAppUids = new ArraySet();
    public final Object mCallbackLock = new Object();

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.server.companion.virtual.audio.VirtualAudioController$$ExternalSyntheticLambda0] */
    public VirtualAudioController(Context context, AttributionSource attributionSource) {
        this.mContext = context;
        this.mAudioPlaybackDetector = new AudioPlaybackDetector(context);
        this.mAudioRecordingDetector = new AudioRecordingDetector(context);
        if (Flags.metricsCollection()) {
            Counter.logIncrementWithUid("virtual_devices.value_virtual_audio_created_count", attributionSource.getUid());
        }
    }

    public static ArraySet findPlayingAppUids(ArraySet arraySet, List list) {
        ArraySet arraySet2 = new ArraySet();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            AudioPlaybackConfiguration audioPlaybackConfiguration = (AudioPlaybackConfiguration) it.next();
            if (arraySet.contains(Integer.valueOf(audioPlaybackConfiguration.getClientUid())) && audioPlaybackConfiguration.getPlayerState() == 2) {
                arraySet2.add(Integer.valueOf(audioPlaybackConfiguration.getClientUid()));
            }
        }
        return arraySet2;
    }

    public void addPlayingAppsForTesting(int i) {
        synchronized (this.mLock) {
            this.mPlayingAppUids.add(Integer.valueOf(i));
        }
    }

    public boolean hasPendingRunnable() {
        return this.mHandler.hasCallbacks(this.mUpdateAudioRoutingRunnable);
    }

    public final void notifyAppsNeedingAudioRoutingChanged() {
        int[] iArr;
        if (this.mHandler.hasCallbacks(this.mUpdateAudioRoutingRunnable)) {
            this.mHandler.removeCallbacks(this.mUpdateAudioRoutingRunnable);
        }
        synchronized (this.mLock) {
            try {
                iArr = new int[this.mRunningAppUids.size()];
                for (int i = 0; i < this.mRunningAppUids.size(); i++) {
                    iArr[i] = ((Integer) this.mRunningAppUids.valueAt(i)).intValue();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        synchronized (this.mCallbackLock) {
            IAudioRoutingCallback iAudioRoutingCallback = this.mRoutingCallback;
            if (iAudioRoutingCallback != null) {
                try {
                    iAudioRoutingCallback.onAppsNeedingAudioRoutingChanged(iArr);
                } catch (RemoteException e) {
                    Slog.e("VirtualAudioController", "RemoteException when calling updateReroutingApps", e);
                }
            }
        }
    }

    @Override // com.android.server.companion.virtual.GenericWindowPolicyController.RunningAppsChangedListener
    public final void onRunningAppsChanged(ArraySet arraySet) {
        synchronized (this.mLock) {
            try {
                if (this.mRunningAppUids.equals(arraySet)) {
                    return;
                }
                this.mRunningAppUids.clear();
                this.mRunningAppUids.addAll(arraySet);
                ArraySet arraySet2 = this.mPlayingAppUids;
                ArraySet findPlayingAppUids = findPlayingAppUids(this.mRunningAppUids, ((AudioManager) this.mContext.getSystemService(AudioManager.class)).getActivePlaybackConfigurations());
                this.mPlayingAppUids = findPlayingAppUids;
                if (!findPlayingAppUids.isEmpty()) {
                    Slog.i("VirtualAudioController", "Audio is playing, do not change rerouted apps");
                    return;
                }
                if (arraySet2.isEmpty()) {
                    notifyAppsNeedingAudioRoutingChanged();
                    return;
                }
                Slog.i("VirtualAudioController", "The last playing app removed, delay change rerouted apps");
                if (this.mHandler.hasCallbacks(this.mUpdateAudioRoutingRunnable)) {
                    this.mHandler.removeCallbacks(this.mUpdateAudioRoutingRunnable);
                }
                this.mHandler.postDelayed(this.mUpdateAudioRoutingRunnable, 2000L);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void startListening(GenericWindowPolicyController genericWindowPolicyController, IAudioRoutingCallback iAudioRoutingCallback, IAudioConfigChangedCallback iAudioConfigChangedCallback) {
        this.mGenericWindowPolicyController = genericWindowPolicyController;
        synchronized (genericWindowPolicyController.mGenericWindowPolicyControllerLock) {
            genericWindowPolicyController.mRunningAppsChangedListeners.add(this);
        }
        synchronized (this.mCallbackLock) {
            this.mRoutingCallback = iAudioRoutingCallback;
            this.mConfigChangedCallback = iAudioConfigChangedCallback;
        }
        synchronized (this.mLock) {
            this.mRunningAppUids.clear();
            this.mPlayingAppUids.clear();
        }
        if (iAudioConfigChangedCallback != null) {
            AudioPlaybackDetector audioPlaybackDetector = this.mAudioPlaybackDetector;
            audioPlaybackDetector.mAudioPlaybackCallback = this;
            audioPlaybackDetector.mAudioManager.registerAudioPlaybackCallback(audioPlaybackDetector, null);
            AudioRecordingDetector audioRecordingDetector = this.mAudioRecordingDetector;
            audioRecordingDetector.mAudioRecordingCallback = this;
            audioRecordingDetector.mAudioManager.registerAudioRecordingCallback(audioRecordingDetector, null);
        }
    }

    public final void stopListening() {
        if (this.mHandler.hasCallbacks(this.mUpdateAudioRoutingRunnable)) {
            this.mHandler.removeCallbacks(this.mUpdateAudioRoutingRunnable);
        }
        AudioPlaybackDetector audioPlaybackDetector = this.mAudioPlaybackDetector;
        if (audioPlaybackDetector.mAudioPlaybackCallback != null) {
            audioPlaybackDetector.mAudioPlaybackCallback = null;
            audioPlaybackDetector.mAudioManager.unregisterAudioPlaybackCallback(audioPlaybackDetector);
        }
        AudioRecordingDetector audioRecordingDetector = this.mAudioRecordingDetector;
        if (audioRecordingDetector.mAudioRecordingCallback != null) {
            audioRecordingDetector.mAudioRecordingCallback = null;
            audioRecordingDetector.mAudioManager.unregisterAudioRecordingCallback(audioRecordingDetector);
        }
        GenericWindowPolicyController genericWindowPolicyController = this.mGenericWindowPolicyController;
        if (genericWindowPolicyController != null) {
            genericWindowPolicyController.unregisterRunningAppsChangedListener(this);
            this.mGenericWindowPolicyController = null;
        }
        synchronized (this.mCallbackLock) {
            this.mRoutingCallback = null;
            this.mConfigChangedCallback = null;
        }
    }
}
