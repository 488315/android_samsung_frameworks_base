package android.speech.tts;


abstract class PlaybackQueueItem implements Runnable {
    private final Object mCallerIdentity;
    private final TextToSpeechService.UtteranceProgressDispatcher mDispatcher;

    @Override // java.lang.Runnable
    public abstract void run();

    abstract void stop(int i);

    PlaybackQueueItem(
            TextToSpeechService.UtteranceProgressDispatcher dispatcher, Object callerIdentity) {
        this.mDispatcher = dispatcher;
        this.mCallerIdentity = callerIdentity;
    }

    Object getCallerIdentity() {
        return this.mCallerIdentity;
    }

    protected TextToSpeechService.UtteranceProgressDispatcher getDispatcher() {
        return this.mDispatcher;
    }
}
