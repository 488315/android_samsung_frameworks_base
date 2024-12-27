package android.speech;

public interface RecognitionSupportCallback {
    void onError(int i);

    void onSupportResult(RecognitionSupport recognitionSupport);
}
