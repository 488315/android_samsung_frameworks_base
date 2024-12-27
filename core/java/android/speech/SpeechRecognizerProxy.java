package android.speech;

import android.content.ComponentName;
import android.content.Intent;
import android.util.CloseGuard;
import java.lang.ref.Reference;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
class SpeechRecognizerProxy extends SpeechRecognizer {
    private final CloseGuard mCloseGuard = new CloseGuard();
    private final SpeechRecognizer mDelegate;

    SpeechRecognizerProxy(SpeechRecognizer delegate) {
        this.mDelegate = delegate;
        this.mCloseGuard.open("SpeechRecognizer#destroy()");
    }

    @Override // android.speech.SpeechRecognizer
    public void setRecognitionListener(RecognitionListener listener) {
        this.mDelegate.setRecognitionListener(listener);
    }

    @Override // android.speech.SpeechRecognizer
    public void startListening(Intent recognizerIntent) {
        this.mDelegate.startListening(recognizerIntent);
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
    public void checkRecognitionSupport(Intent recognizerIntent, Executor executor, RecognitionSupportCallback supportListener) {
        this.mDelegate.checkRecognitionSupport(recognizerIntent, executor, supportListener);
    }

    @Override // android.speech.SpeechRecognizer
    public void triggerModelDownload(Intent recognizerIntent) {
        this.mDelegate.triggerModelDownload(recognizerIntent);
    }

    @Override // android.speech.SpeechRecognizer
    public void triggerModelDownload(Intent recognizerIntent, Executor executor, ModelDownloadListener listener) {
        this.mDelegate.triggerModelDownload(recognizerIntent, executor, listener);
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
