package com.samsung.android.allshare;

import android.os.Bundle;
import com.sec.android.allshare.iface.message.AllShareKey;

/* loaded from: classes5.dex */
public final class WHADeviceInfo {
  private Bundle mBundle;

  public static class ActionState {
    public static final int FAILED = -1;
    public static final int SUCCESS = 0;
  }

  public static class PlayState {
    public static final int CLIENT_STAND_BY = 100;
    public static final int PLAYING = 1;
    public static final int STOPPED = 0;
  }

  public static class WHAState {
    public static final int CLIENT = 3;
    public static final int CLIENT_CONNECTING = 2;
    public static final int DISABLED = 0;
    public static final int FREE = 1;
    public static final int MASTER = 4;

    /* renamed from: NA */
    public static final int f2954NA = -1;
  }

  WHADeviceInfo(Bundle bundle) {
    this.mBundle = null;
    this.mBundle = bundle;
  }

  public String getWhaName() {
    return this.mBundle.getString(AllShareKey.BUNDLE_STRING_WHA_FRIENDLY_NAME);
  }

  public int getWhaDeviceStatus() {
    return this.mBundle.getInt(AllShareKey.BUNDLE_STRING_WHA_DEVICE_STATUS);
  }

  public String getWhaPartyID() {
    return this.mBundle.getString(AllShareKey.BUNDLE_STRING_WHA_PARTY_ID);
  }

  public int getWhaPlayState() {
    return this.mBundle.getInt(AllShareKey.BUNDLE_STRING_WHA_PLAY_STATE);
  }

  public int getWhaVolume() {
    return this.mBundle.getInt(AllShareKey.BUNDLE_STRING_WHA_VOLUME);
  }
}
