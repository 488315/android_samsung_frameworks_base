package android.speech;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;

import com.android.internal.R;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public class SpeechRecognizer {
    public static final String CONFIDENCE_SCORES = "confidence_scores";
    public static final String DETECTED_LANGUAGE = "detected_language";
    public static final int ERROR_AUDIO = 3;
    public static final int ERROR_CANNOT_CHECK_SUPPORT = 14;
    public static final int ERROR_CANNOT_LISTEN_TO_DOWNLOAD_EVENTS = 15;
    public static final int ERROR_CLIENT = 5;
    public static final int ERROR_INSUFFICIENT_PERMISSIONS = 9;
    public static final int ERROR_LANGUAGE_NOT_SUPPORTED = 12;
    public static final int ERROR_LANGUAGE_UNAVAILABLE = 13;
    public static final int ERROR_NETWORK = 2;
    public static final int ERROR_NETWORK_TIMEOUT = 1;
    public static final int ERROR_NO_MATCH = 7;
    public static final int ERROR_RECOGNIZER_BUSY = 8;
    public static final int ERROR_SERVER = 4;
    public static final int ERROR_SERVER_DISCONNECTED = 11;
    public static final int ERROR_SPEECH_TIMEOUT = 6;
    public static final int ERROR_TOO_MANY_REQUESTS = 10;
    public static final String LANGUAGE_DETECTION_CONFIDENCE_LEVEL =
            "language_detection_confidence_level";
    public static final int LANGUAGE_DETECTION_CONFIDENCE_LEVEL_CONFIDENT = 2;
    public static final int LANGUAGE_DETECTION_CONFIDENCE_LEVEL_HIGHLY_CONFIDENT = 3;
    public static final int LANGUAGE_DETECTION_CONFIDENCE_LEVEL_NOT_CONFIDENT = 1;
    public static final int LANGUAGE_DETECTION_CONFIDENCE_LEVEL_UNKNOWN = 0;
    public static final String LANGUAGE_SWITCH_RESULT = "language_switch_result";
    public static final int LANGUAGE_SWITCH_RESULT_FAILED = 2;
    public static final int LANGUAGE_SWITCH_RESULT_NOT_ATTEMPTED = 0;
    public static final int LANGUAGE_SWITCH_RESULT_SKIPPED_NO_MODEL = 3;
    public static final int LANGUAGE_SWITCH_RESULT_SUCCEEDED = 1;
    private static final int MSG_CANCEL = 3;
    private static final int MSG_CHANGE_LISTENER = 4;
    private static final int MSG_CHECK_RECOGNITION_SUPPORT = 6;
    private static final int MSG_SET_TEMPORARY_ON_DEVICE_COMPONENT = 5;
    private static final int MSG_START = 1;
    private static final int MSG_STOP = 2;
    private static final int MSG_TRIGGER_MODEL_DOWNLOAD = 7;
    public static final String RECOGNITION_PARTS = "recognition_parts";
    public static final String RESULTS_ALTERNATIVES = "results_alternatives";
    public static final String RESULTS_RECOGNITION = "results_recognition";
    public static final String TOP_LOCALE_ALTERNATIVES = "top_locale_alternatives";

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface LanguageDetectionConfidenceLevel {}

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface LanguageSwitchResult {}

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface RecognitionError {}

    SpeechRecognizer() {}

    public static boolean isRecognitionAvailable(Context context) {
        List<ResolveInfo> list =
                context.getPackageManager()
                        .queryIntentServices(new Intent(RecognitionService.SERVICE_INTERFACE), 0);
        return (list == null || list.size() == 0) ? false : true;
    }

    public static boolean isOnDeviceRecognitionAvailable(Context context) {
        ComponentName componentName =
                ComponentName.unflattenFromString(
                        context.getString(R.string.config_defaultOnDeviceSpeechRecognitionService));
        return componentName != null;
    }

    public static SpeechRecognizer createSpeechRecognizer(Context context) {
        return createSpeechRecognizer(context, null);
    }

    public static SpeechRecognizer createSpeechRecognizer(
            Context context, ComponentName serviceComponent) {
        if (context == null) {
            throw new IllegalArgumentException("Context cannot be null");
        }
        SpeechRecognizerImpl.checkIsCalledFromMainThread();
        return wrapWithProxy(new SpeechRecognizerImpl(context, serviceComponent));
    }

    public static SpeechRecognizer createOnDeviceSpeechRecognizer(Context context) {
        if (!isOnDeviceRecognitionAvailable(context)) {
            throw new UnsupportedOperationException("On-device recognition is not available");
        }
        return wrapWithProxy(SpeechRecognizerImpl.lenientlyCreateOnDeviceSpeechRecognizer(context));
    }

    private static SpeechRecognizer wrapWithProxy(SpeechRecognizer delegate) {
        return new SpeechRecognizerProxy(delegate);
    }

    public static SpeechRecognizer createOnDeviceTestingSpeechRecognizer(Context context) {
        return wrapWithProxy(SpeechRecognizerImpl.lenientlyCreateOnDeviceSpeechRecognizer(context));
    }

    public void setRecognitionListener(RecognitionListener listener) {
        throw new UnsupportedOperationException();
    }

    public void startListening(Intent recognizerIntent) {
        throw new UnsupportedOperationException();
    }

    public void stopListening() {
        throw new UnsupportedOperationException();
    }

    public void cancel() {
        throw new UnsupportedOperationException();
    }

    public void checkRecognitionSupport(
            Intent recognizerIntent,
            Executor executor,
            RecognitionSupportCallback supportListener) {
        throw new UnsupportedOperationException();
    }

    public void triggerModelDownload(Intent recognizerIntent) {
        throw new UnsupportedOperationException();
    }

    public void triggerModelDownload(
            Intent recognizerIntent, Executor executor, ModelDownloadListener listener) {
        throw new UnsupportedOperationException();
    }

    public void setTemporaryOnDeviceRecognizer(ComponentName componentName) {
        throw new UnsupportedOperationException();
    }

    public void destroy() {
        throw new UnsupportedOperationException();
    }
}
