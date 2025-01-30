package com.android.server.audio;

import android.media.AudioDeviceAttributes;
import android.util.Slog;
import com.android.internal.util.jobs.XmlUtils;
import java.util.UUID;

/* loaded from: classes.dex */
public abstract class UuidUtils {
  public static final UUID STANDALONE_UUID = new UUID(0, 0);

  public static UUID uuidFromAudioDeviceAttributes(AudioDeviceAttributes audioDeviceAttributes) {
    if (audioDeviceAttributes.getInternalType() != 128) {
      return null;
    }
    String replace =
        audioDeviceAttributes.getAddress().replace(XmlUtils.STRING_ARRAY_SEPARATOR, "");
    if (replace.length() != 12) {
      return null;
    }
    try {
      long longValue = Long.decode("0x" + replace).longValue() | 4779445104546938880L;
      Slog.i(
          "AudioService.UuidUtils",
          "uuidFromAudioDeviceAttributes lsb: " + Long.toHexString(longValue));
      return new UUID(0L, longValue);
    } catch (NumberFormatException unused) {
      return null;
    }
  }
}
