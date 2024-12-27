package android.speech;

public interface ModelDownloadListener {
    void onError(int i);

    void onProgress(int i);

    void onScheduled();

    void onSuccess();
}
