package com.android.server.companion.virtual.audio;

import android.companion.virtual.audio.IAudioConfigChangedCallback;
import android.companion.virtual.audio.IAudioRoutingCallback;
import android.content.Context;
import android.media.AudioManager;
import android.media.AudioPlaybackConfiguration;
import android.media.AudioRecordingConfiguration;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.util.ArraySet;
import android.util.Slog;
import com.android.server.companion.virtual.GenericWindowPolicyController;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public final class VirtualAudioController
    implements AudioPlaybackDetector.AudioPlaybackCallback,
        AudioRecordingDetector.AudioRecordingCallback,
        GenericWindowPolicyController.RunningAppsChangedListener {
  public final AudioPlaybackDetector mAudioPlaybackDetector;
  public final AudioRecordingDetector mAudioRecordingDetector;
  public IAudioConfigChangedCallback mConfigChangedCallback;
  public final Context mContext;
  public GenericWindowPolicyController mGenericWindowPolicyController;
  public IAudioRoutingCallback mRoutingCallback;
  public final Handler mHandler = new Handler(Looper.getMainLooper());
  public final Runnable mUpdateAudioRoutingRunnable = new Runnable() { // from class:
        // com.android.server.companion.virtual.audio.VirtualAudioController$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
          VirtualAudioController.this.notifyAppsNeedingAudioRoutingChanged();
        }
      };
  public final Object mLock = new Object();
  public final ArraySet mRunningAppUids = new ArraySet();
  public ArraySet mPlayingAppUids = new ArraySet();
  public final Object mCallbackLock = new Object();

  public VirtualAudioController(Context context) {
    this.mContext = context;
    this.mAudioPlaybackDetector = new AudioPlaybackDetector(context);
    this.mAudioRecordingDetector = new AudioRecordingDetector(context);
  }

  public void startListening(
      GenericWindowPolicyController genericWindowPolicyController,
      IAudioRoutingCallback iAudioRoutingCallback,
      IAudioConfigChangedCallback iAudioConfigChangedCallback) {
    this.mGenericWindowPolicyController = genericWindowPolicyController;
    genericWindowPolicyController.registerRunningAppsChangedListener(this);
    synchronized (this.mCallbackLock) {
      this.mRoutingCallback = iAudioRoutingCallback;
      this.mConfigChangedCallback = iAudioConfigChangedCallback;
    }
    synchronized (this.mLock) {
      this.mRunningAppUids.clear();
      this.mPlayingAppUids.clear();
    }
    if (iAudioConfigChangedCallback != null) {
      this.mAudioPlaybackDetector.register(this);
      this.mAudioRecordingDetector.register(this);
    }
  }

  public void stopListening() {
    if (this.mHandler.hasCallbacks(this.mUpdateAudioRoutingRunnable)) {
      this.mHandler.removeCallbacks(this.mUpdateAudioRoutingRunnable);
    }
    this.mAudioPlaybackDetector.unregister();
    this.mAudioRecordingDetector.unregister();
    GenericWindowPolicyController genericWindowPolicyController =
        this.mGenericWindowPolicyController;
    if (genericWindowPolicyController != null) {
      genericWindowPolicyController.unregisterRunningAppsChangedListener(this);
      this.mGenericWindowPolicyController = null;
    }
    synchronized (this.mCallbackLock) {
      this.mRoutingCallback = null;
      this.mConfigChangedCallback = null;
    }
  }

  @Override // com.android.server.companion.virtual.GenericWindowPolicyController.RunningAppsChangedListener
  public void onRunningAppsChanged(ArraySet arraySet) {
    synchronized (this.mLock) {
      if (this.mRunningAppUids.equals(arraySet)) {
        return;
      }
      this.mRunningAppUids.clear();
      this.mRunningAppUids.addAll(arraySet);
      ArraySet arraySet2 = this.mPlayingAppUids;
      ArraySet findPlayingAppUids =
          findPlayingAppUids(
              ((AudioManager) this.mContext.getSystemService(AudioManager.class))
                  .getActivePlaybackConfigurations(),
              this.mRunningAppUids);
      this.mPlayingAppUids = findPlayingAppUids;
      if (!findPlayingAppUids.isEmpty()) {
        Slog.i("VirtualAudioController", "Audio is playing, do not change rerouted apps");
        return;
      }
      if (!arraySet2.isEmpty()) {
        Slog.i(
            "VirtualAudioController", "The last playing app removed, delay change rerouted apps");
        if (this.mHandler.hasCallbacks(this.mUpdateAudioRoutingRunnable)) {
          this.mHandler.removeCallbacks(this.mUpdateAudioRoutingRunnable);
        }
        this.mHandler.postDelayed(this.mUpdateAudioRoutingRunnable, 2000L);
        return;
      }
      notifyAppsNeedingAudioRoutingChanged();
    }
  }

  @Override // com.android.server.companion.virtual.audio.AudioPlaybackDetector.AudioPlaybackCallback
  public void onPlaybackConfigChanged(List list) {
    List findPlaybackConfigurations;
    updatePlayingApplications(list);
    synchronized (this.mLock) {
      findPlaybackConfigurations = findPlaybackConfigurations(list, this.mRunningAppUids);
    }
    synchronized (this.mCallbackLock) {
      IAudioConfigChangedCallback iAudioConfigChangedCallback = this.mConfigChangedCallback;
      if (iAudioConfigChangedCallback != null) {
        try {
          iAudioConfigChangedCallback.onPlaybackConfigChanged(findPlaybackConfigurations);
        } catch (RemoteException e) {
          Slog.e(
              "VirtualAudioController", "RemoteException when calling onPlaybackConfigChanged", e);
        }
      }
    }
  }

  @Override // com.android.server.companion.virtual.audio.AudioRecordingDetector.AudioRecordingCallback
  public void onRecordingConfigChanged(List list) {
    List findRecordingConfigurations;
    synchronized (this.mLock) {
      findRecordingConfigurations = findRecordingConfigurations(list, this.mRunningAppUids);
    }
    synchronized (this.mCallbackLock) {
      IAudioConfigChangedCallback iAudioConfigChangedCallback = this.mConfigChangedCallback;
      if (iAudioConfigChangedCallback != null) {
        try {
          iAudioConfigChangedCallback.onRecordingConfigChanged(findRecordingConfigurations);
        } catch (RemoteException e) {
          Slog.e(
              "VirtualAudioController", "RemoteException when calling onRecordingConfigChanged", e);
        }
      }
    }
  }

  public final void updatePlayingApplications(List list) {
    synchronized (this.mLock) {
      ArraySet findPlayingAppUids = findPlayingAppUids(list, this.mRunningAppUids);
      if (this.mPlayingAppUids.equals(findPlayingAppUids)) {
        return;
      }
      this.mPlayingAppUids = findPlayingAppUids;
      notifyAppsNeedingAudioRoutingChanged();
    }
  }

  public final void notifyAppsNeedingAudioRoutingChanged() {
    int[] iArr;
    if (this.mHandler.hasCallbacks(this.mUpdateAudioRoutingRunnable)) {
      this.mHandler.removeCallbacks(this.mUpdateAudioRoutingRunnable);
    }
    synchronized (this.mLock) {
      iArr = new int[this.mRunningAppUids.size()];
      for (int i = 0; i < this.mRunningAppUids.size(); i++) {
        iArr[i] = ((Integer) this.mRunningAppUids.valueAt(i)).intValue();
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

  public static ArraySet findPlayingAppUids(List list, ArraySet arraySet) {
    ArraySet arraySet2 = new ArraySet();
    Iterator it = list.iterator();
    while (it.hasNext()) {
      AudioPlaybackConfiguration audioPlaybackConfiguration =
          (AudioPlaybackConfiguration) it.next();
      if (arraySet.contains(Integer.valueOf(audioPlaybackConfiguration.getClientUid()))
          && audioPlaybackConfiguration.getPlayerState() == 2) {
        arraySet2.add(Integer.valueOf(audioPlaybackConfiguration.getClientUid()));
      }
    }
    return arraySet2;
  }

  public static List findPlaybackConfigurations(List list, ArraySet arraySet) {
    ArrayList arrayList = new ArrayList();
    Iterator it = list.iterator();
    while (it.hasNext()) {
      AudioPlaybackConfiguration audioPlaybackConfiguration =
          (AudioPlaybackConfiguration) it.next();
      if (arraySet.contains(Integer.valueOf(audioPlaybackConfiguration.getClientUid()))) {
        arrayList.add(audioPlaybackConfiguration);
      }
    }
    return arrayList;
  }

  public static List findRecordingConfigurations(List list, ArraySet arraySet) {
    ArrayList arrayList = new ArrayList();
    Iterator it = list.iterator();
    while (it.hasNext()) {
      AudioRecordingConfiguration audioRecordingConfiguration =
          (AudioRecordingConfiguration) it.next();
      if (arraySet.contains(Integer.valueOf(audioRecordingConfiguration.getClientUid()))) {
        arrayList.add(audioRecordingConfiguration);
      }
    }
    return arrayList;
  }

  public boolean hasPendingRunnable() {
    return this.mHandler.hasCallbacks(this.mUpdateAudioRoutingRunnable);
  }

  public void addPlayingAppsForTesting(int i) {
    synchronized (this.mLock) {
      this.mPlayingAppUids.add(Integer.valueOf(i));
    }
  }
}
