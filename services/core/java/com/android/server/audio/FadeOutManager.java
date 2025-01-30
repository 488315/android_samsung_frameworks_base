package com.android.server.audio;

import android.media.AudioPlaybackConfiguration;
import android.media.VolumeShaper;
import android.util.Log;
import com.android.server.display.DisplayPowerController2;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;

/* loaded from: classes.dex */
public final class FadeOutManager {
  public static final int[] FADEABLE_USAGES;
  public static final VolumeShaper.Configuration FADEOUT_VSHAPE =
      new VolumeShaper.Configuration.Builder()
          .setId(2)
          .setCurve(
              new float[] {DisplayPowerController2.RATE_FROM_DOZE_TO_ON, 0.25f, 1.0f},
              new float[] {1.0f, 0.65f, DisplayPowerController2.RATE_FROM_DOZE_TO_ON})
          .setOptionFlags(2)
          .setDuration(2000)
          .build();
  public static final VolumeShaper.Operation PLAY_CREATE_IF_NEEDED;
  public static final VolumeShaper.Operation PLAY_SKIP_RAMP;
  public static final int[] UNFADEABLE_CONTENT_TYPES;
  public static final int[] UNFADEABLE_PLAYER_TYPES;
  public final HashMap mFadedApps = new HashMap();

  static {
    VolumeShaper.Operation build =
        new VolumeShaper.Operation.Builder(VolumeShaper.Operation.PLAY).createIfNeeded().build();
    PLAY_CREATE_IF_NEEDED = build;
    UNFADEABLE_PLAYER_TYPES = new int[] {13, 3};
    UNFADEABLE_CONTENT_TYPES = new int[] {1};
    FADEABLE_USAGES = new int[] {14, 1};
    PLAY_SKIP_RAMP = new VolumeShaper.Operation.Builder(build).setXOffset(1.0f).build();
  }

  public synchronized void unfadeOutUid(int i, HashMap hashMap) {
    Log.i("AudioService.FadeOutManager", "unfadeOutUid() uid:" + i);
    CurrentDeviceManager$$ExternalSyntheticThrowCCEIfNotNull0.m4m(
        this.mFadedApps.remove(Integer.valueOf(i)));
  }

  public synchronized void checkFade(AudioPlaybackConfiguration audioPlaybackConfiguration) {
    CurrentDeviceManager$$ExternalSyntheticThrowCCEIfNotNull0.m4m(
        this.mFadedApps.get(Integer.valueOf(audioPlaybackConfiguration.getClientUid())));
  }

  public synchronized void removeReleased(AudioPlaybackConfiguration audioPlaybackConfiguration) {
    CurrentDeviceManager$$ExternalSyntheticThrowCCEIfNotNull0.m4m(
        this.mFadedApps.get(Integer.valueOf(audioPlaybackConfiguration.getClientUid())));
  }

  public synchronized void dump(PrintWriter printWriter) {
    Iterator it = this.mFadedApps.values().iterator();
    if (it.hasNext()) {
      CurrentDeviceManager$$ExternalSyntheticThrowCCEIfNotNull0.m4m(it.next());
      throw null;
    }
  }
}
