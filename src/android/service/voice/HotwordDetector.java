package android.service.voice;

import android.annotation.SystemApi;
import android.media.AudioFormat;
import android.os.ParcelFileDescriptor;
import android.os.PersistableBundle;
import android.os.SharedMemory;
import android.service.voice.AlwaysOnHotwordDetector;
import java.io.PrintWriter;

@SystemApi
/* loaded from: classes3.dex */
public interface HotwordDetector {
    public static final int DETECTOR_TYPE_NORMAL = 0;
    public static final int DETECTOR_TYPE_TRUSTED_HOTWORD_DSP = 1;
    public static final int DETECTOR_TYPE_TRUSTED_HOTWORD_SOFTWARE = 2;
    public static final int DETECTOR_TYPE_VISUAL_QUERY_DETECTOR = 3;

    boolean startRecognition();

    boolean startRecognition(ParcelFileDescriptor parcelFileDescriptor, AudioFormat audioFormat, PersistableBundle persistableBundle);

    boolean stopRecognition();

    void updateState(PersistableBundle persistableBundle, SharedMemory sharedMemory);

    default void destroy() {
        throw new UnsupportedOperationException("Not implemented. Must override in a subclass.");
    }

    default boolean isUsingSandboxedDetectionService() {
        throw new UnsupportedOperationException("Not implemented. Must override in a subclass.");
    }

    static String detectorTypeToString(int i) {
        if (i == 0) {
            return "normal";
        }
        if (i == 1) {
            return "trusted_hotword_dsp";
        }
        if (i == 2) {
            return "trusted_hotword_software";
        }
        if (i == 3) {
            return "visual_query_detector";
        }
        return Integer.toString(i);
    }

    default void dump(String str, PrintWriter printWriter) {
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

        default void onUnknownFailure(String str) {
            onError();
        }
    }
}
