package android.speech;

import android.os.Bundle;

/* loaded from: classes3.dex */
public interface RecognitionListener {
    void onBeginningOfSpeech();

    void onBufferReceived(byte[] bArr);

    default void onEndOfSegmentedSession() {
    }

    void onEndOfSpeech();

    void onError(int i);

    void onEvent(int i, Bundle bundle);

    default void onLanguageDetection(Bundle bundle) {
    }

    void onPartialResults(Bundle bundle);

    void onReadyForSpeech(Bundle bundle);

    void onResults(Bundle bundle);

    void onRmsChanged(float f);

    default void onSegmentResults(Bundle bundle) {
    }
}
