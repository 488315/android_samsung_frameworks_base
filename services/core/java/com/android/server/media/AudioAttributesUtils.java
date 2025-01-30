package com.android.server.media;

import android.media.AudioAttributes;
import android.media.AudioDeviceAttributes;

/* loaded from: classes2.dex */
public abstract class AudioAttributesUtils {
  public static final AudioAttributes ATTRIBUTES_MEDIA =
      new AudioAttributes.Builder().setUsage(1).build();

  public static int mapToMediaRouteType(AudioDeviceAttributes audioDeviceAttributes) {
    int type = audioDeviceAttributes.getType();
    if (type == 1 || type == 2) {
      return 2;
    }
    int i = 3;
    if (type != 3) {
      i = 4;
      if (type != 4) {
        i = 8;
        if (type != 8) {
          i = 9;
          if (type != 9) {
            i = 11;
            if (type != 11) {
              i = 13;
              if (type != 13) {
                if (type == 26) {
                  return 26;
                }
                if (type != 31) {
                  int i2 = 22;
                  if (type != 22) {
                    i2 = 23;
                    if (type != 23) {
                      return 0;
                    }
                  }
                  return i2;
                }
              }
            }
          }
        }
      }
    }
    return i;
  }

  public static boolean isDeviceOutputAttributes(AudioDeviceAttributes audioDeviceAttributes) {
    if (audioDeviceAttributes == null || audioDeviceAttributes.getRole() != 2) {
      return false;
    }
    int type = audioDeviceAttributes.getType();
    return type == 1
        || type == 2
        || type == 3
        || type == 4
        || type == 9
        || type == 11
        || type == 13
        || type == 22
        || type == 31;
  }

  public static boolean isBluetoothOutputAttributes(AudioDeviceAttributes audioDeviceAttributes) {
    if (audioDeviceAttributes == null || audioDeviceAttributes.getRole() != 2) {
      return false;
    }
    int type = audioDeviceAttributes.getType();
    return type == 8 || type == 23 || type == 26 || type == 27;
  }
}
