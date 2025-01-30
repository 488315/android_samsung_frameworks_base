package com.android.server.accessibility.autoaction.actiontype;

import android.R;
import android.content.Context;
import android.media.AudioManager;

/* loaded from: classes.dex */
public class SoundAction extends CornerActionType {
  public AudioManager mAudioManager;
  public Context mContext;
  public String mType;

  public SoundAction(Context context, String str) {
    this.mContext = context;
    this.mAudioManager = (AudioManager) context.getSystemService("audio");
    this.mType = str;
  }

  public static SoundAction createAction(Context context, String str) {
    return new SoundAction(context, str);
  }

  public static int getStringResId(String str) {
    str.hashCode();
    switch (str) {
      case "ringtone_volume_up":
        return R.string.adbwifi_active_notification_message;
      case "sound_vibrate_mute":
        return R.string.aerr_application_repeated;
      case "media_volume_down":
        return R.string.activity_chooser_view_see_all;
      case "media_volume_up":
        return R.string.activity_list_empty;
      case "ringtone_volume_down":
        return R.string.adb_debugging_notification_channel_tv;
      default:
        throw new IllegalArgumentException("Wrong Sound Action Type");
    }
  }

  /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
  @Override // com.android.server.accessibility.autoaction.actiontype.CornerActionType
  public void performCornerAction(int i) {
    char c;
    if (this.mAudioManager != null) {
      String str = this.mType;
      str.hashCode();
      switch (str.hashCode()) {
        case -1428385405:
          if (str.equals("ringtone_volume_up")) {
            c = 0;
            break;
          }
          c = 65535;
          break;
        case -417942503:
          if (str.equals("sound_vibrate_mute")) {
            c = 1;
            break;
          }
          c = 65535;
          break;
        case 452226764:
          if (str.equals("media_volume_down")) {
            c = 2;
            break;
          }
          c = 65535;
          break;
        case 1158011717:
          if (str.equals("media_volume_up")) {
            c = 3;
            break;
          }
          c = 65535;
          break;
        case 1710656906:
          if (str.equals("ringtone_volume_down")) {
            c = 4;
            break;
          }
          c = 65535;
          break;
        default:
          c = 65535;
          break;
      }
      switch (c) {
        case 0:
          this.mAudioManager.adjustStreamVolume(2, 0, 5);
          this.mAudioManager.adjustStreamVolume(2, 1, 33554432);
          return;
        case 1:
          int ringerModeInternal = this.mAudioManager.getRingerModeInternal() - 1;
          this.mAudioManager.setRingerModeInternal(
              ringerModeInternal >= 0 ? ringerModeInternal : 2);
          return;
        case 2:
          this.mAudioManager.adjustStreamVolume(3, 0, 1);
          this.mAudioManager.adjustStreamVolume(3, -1, 33554432);
          return;
        case 3:
          this.mAudioManager.adjustStreamVolume(3, 0, 1);
          this.mAudioManager.adjustStreamVolume(3, 1, 33554432);
          return;
        case 4:
          this.mAudioManager.adjustStreamVolume(2, 0, 5);
          this.mAudioManager.adjustStreamVolume(2, -1, 33554432);
          return;
        default:
          throw new IllegalArgumentException("Wrong Sound Action Type");
      }
    }
  }
}
