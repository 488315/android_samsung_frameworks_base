package android.service.voice;

import android.annotation.SystemApi;
import android.media.AudioFormat;
import android.p009os.ParcelFileDescriptor;
import android.p009os.PersistableBundle;
import android.p009os.SharedMemory;
import java.io.PrintWriter;

@SystemApi
/* loaded from: classes3.dex */
public interface HotwordDetector {
  public static final int DETECTOR_TYPE_NORMAL = 0;
  public static final int DETECTOR_TYPE_TRUSTED_HOTWORD_DSP = 1;
  public static final int DETECTOR_TYPE_TRUSTED_HOTWORD_SOFTWARE = 2;
  public static final int DETECTOR_TYPE_VISUAL_QUERY_DETECTOR = 3;

  boolean startRecognition();

  boolean startRecognition(
      ParcelFileDescriptor parcelFileDescriptor,
      AudioFormat audioFormat,
      PersistableBundle persistableBundle);

  boolean stopRecognition();

  void updateState(PersistableBundle persistableBundle, SharedMemory sharedMemory);

  default void destroy() {
    throw new UnsupportedOperationException("Not implemented. Must override in a subclass.");
  }

  default boolean isUsingSandboxedDetectionService() {
    throw new UnsupportedOperationException("Not implemented. Must override in a subclass.");
  }

  static String detectorTypeToString(int detectorType) {
    switch (detectorType) {
      case 0:
        return "normal";
      case 1:
        return "trusted_hotword_dsp";
      case 2:
        return "trusted_hotword_software";
      case 3:
        return "visual_query_detector";
      default:
        return Integer.toString(detectorType);
    }
  }

  default void dump(String prefix, PrintWriter pw) {
    throw new UnsupportedOperationException("Not implemented. Must override in a subclass.");
  }

  public interface Callback {
    void onDetected(AlwaysOnHotwordDetector.EventPayload eventPayload);

    @Deprecated
    void onError();

    void onHotwordDetectionServiceInitialized(int i);

    void onHotwordDetectionServiceRestarted();

    void onRecognitionPaused();

    void onRecognitionResumed();

    void onRejected(HotwordRejectedResult hotwordRejectedResult);

    default void onFailure(HotwordDetectionServiceFailure hotwordDetectionServiceFailure) {
      onError();
    }

    default void onUnknownFailure(String errorMessage) {
      onError();
    }
  }
}
