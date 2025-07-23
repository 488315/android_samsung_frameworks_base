package android.speech.tts;

import android.os.ConditionVariable;
import android.speech.tts.TextToSpeechService;

/* loaded from: classes3.dex */
class SilencePlaybackQueueItem extends PlaybackQueueItem {
    private final ConditionVariable mCondVar;
    private final long mSilenceDurationMs;

    SilencePlaybackQueueItem(TextToSpeechService.UtteranceProgressDispatcher utteranceProgressDispatcher, Object obj, long j) {
        super(utteranceProgressDispatcher, obj);
        this.mCondVar = new ConditionVariable();
        this.mSilenceDurationMs = j;
    }

    @Override // android.speech.tts.PlaybackQueueItem, java.lang.Runnable
    public void run() {
        getDispatcher().dispatchOnStart();
        long j = this.mSilenceDurationMs;
        if (j > 0 ? this.mCondVar.block(j) : false) {
            getDispatcher().dispatchOnStop();
        } else {
            getDispatcher().dispatchOnSuccess();
        }
    }

    @Override // android.speech.tts.PlaybackQueueItem
    void stop(int i) {
        this.mCondVar.open();
    }
}
