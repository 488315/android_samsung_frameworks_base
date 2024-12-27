package android.speech.tts;

import android.os.ConditionVariable;
import android.speech.tts.TextToSpeechService;

/* loaded from: classes3.dex */
class SilencePlaybackQueueItem extends PlaybackQueueItem {
    private final ConditionVariable mCondVar;
    private final long mSilenceDurationMs;

    SilencePlaybackQueueItem(TextToSpeechService.UtteranceProgressDispatcher dispatcher, Object callerIdentity, long silenceDurationMs) {
        super(dispatcher, callerIdentity);
        this.mCondVar = new ConditionVariable();
        this.mSilenceDurationMs = silenceDurationMs;
    }

    @Override // android.speech.tts.PlaybackQueueItem, java.lang.Runnable
    public void run() {
        getDispatcher().dispatchOnStart();
        boolean wasStopped = false;
        if (this.mSilenceDurationMs > 0) {
            wasStopped = this.mCondVar.block(this.mSilenceDurationMs);
        }
        if (wasStopped) {
            getDispatcher().dispatchOnStop();
        } else {
            getDispatcher().dispatchOnSuccess();
        }
    }

    @Override // android.speech.tts.PlaybackQueueItem
    void stop(int errorCode) {
        this.mCondVar.open();
    }
}
