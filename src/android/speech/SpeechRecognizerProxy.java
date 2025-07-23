package android.speech;

import android.content.ComponentName;
import android.content.Intent;
import android.util.CloseGuard;
import java.lang.ref.Reference;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
class SpeechRecognizerProxy extends SpeechRecognizer {
    private final CloseGuard mCloseGuard;
    private final SpeechRecognizer mDelegate;

    SpeechRecognizerProxy(SpeechRecognizer speechRecognizer) {
        CloseGuard closeGuard = new CloseGuard();
        this.mCloseGuard = closeGuard;
        this.mDelegate = speechRecognizer;
        closeGuard.open("SpeechRecognizer#destroy()");
    }

    @Override // android.speech.SpeechRecognizer
    public void setRecognitionListener(RecognitionListener recognitionListener) {
        this.mDelegate.setRecognitionListener(recognitionListener);
    }

    @Override // android.speech.SpeechRecognizer
    public void startListening(Intent intent) {
        this.mDelegate.startListening(intent);
    }

    @Override // android.speech.SpeechRecognizer
    public void stopListening() {
        this.mDelegate.stopListening();
    }

    @Override // android.speech.SpeechRecognizer
    public void cancel() {
        this.mDelegate.cancel();
    }

    @Override // android.speech.SpeechRecognizer
    public void destroy() {
        try {
            this.mCloseGuard.close();
            this.mDelegate.destroy();
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    @Override // android.speech.SpeechRecognizer
    public void checkRecognitionSupport(Intent intent, Executor executor, RecognitionSupportCallback recognitionSupportCallback) {
        this.mDelegate.checkRecognitionSupport(intent, executor, recognitionSupportCallback);
    }

    @Override // android.speech.SpeechRecognizer
    public void triggerModelDownload(Intent intent) {
        this.mDelegate.triggerModelDownload(intent);
    }

    @Override // android.speech.SpeechRecognizer
    public void triggerModelDownload(Intent intent, Executor executor, ModelDownloadListener modelDownloadListener) {
        this.mDelegate.triggerModelDownload(intent, executor, modelDownloadListener);
    }

    @Override // android.speech.SpeechRecognizer
    public void setTemporaryOnDeviceRecognizer(ComponentName componentName) {
        this.mDelegate.setTemporaryOnDeviceRecognizer(componentName);
    }

    protected void finalize() throws Throwable {
        try {
            this.mCloseGuard.warnIfOpen();
            destroy();
        } finally {
            super.finalize();
        }
    }
}
