package android.speech.tts;

import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;

/* loaded from: classes3.dex */
class AudioPlaybackHandler {
    private static final boolean DBG = false;
    private static final String TAG = "TTS.AudioPlaybackHandler";
    private final LinkedBlockingQueue<PlaybackQueueItem> mQueue = new LinkedBlockingQueue<>();
    private volatile PlaybackQueueItem mCurrentWorkItem = null;
    private final Thread mHandlerThread = new Thread(new MessageLoop(), "TTS.AudioPlaybackThread");

    AudioPlaybackHandler() {
    }

    public void start() {
        this.mHandlerThread.start();
    }

    private void stop(PlaybackQueueItem playbackQueueItem) {
        if (playbackQueueItem == null) {
            return;
        }
        playbackQueueItem.stop(-2);
    }

    public void enqueue(PlaybackQueueItem playbackQueueItem) {
        try {
            this.mQueue.put(playbackQueueItem);
        } catch (InterruptedException unused) {
        }
    }

    public void stopForApp(Object obj) {
        removeWorkItemsFor(obj);
        PlaybackQueueItem playbackQueueItem = this.mCurrentWorkItem;
        if (playbackQueueItem == null || playbackQueueItem.getCallerIdentity() != obj) {
            return;
        }
        stop(playbackQueueItem);
    }

    public void stop() {
        removeAllMessages();
        stop(this.mCurrentWorkItem);
    }

    public boolean isSpeaking() {
        return (this.mQueue.peek() == null && this.mCurrentWorkItem == null) ? false : true;
    }

    public void quit() {
        removeAllMessages();
        stop(this.mCurrentWorkItem);
        this.mHandlerThread.interrupt();
    }

    private void removeAllMessages() {
        this.mQueue.clear();
    }

    private void removeWorkItemsFor(Object obj) {
        Iterator<PlaybackQueueItem> it = this.mQueue.iterator();
        while (it.hasNext()) {
            PlaybackQueueItem next = it.next();
            if (next.getCallerIdentity() == obj) {
                it.remove();
                stop(next);
            }
        }
    }

    private final class MessageLoop implements Runnable {
        private MessageLoop() {
        }

        @Override // java.lang.Runnable
        public void run() {
            while (true) {
                try {
                    PlaybackQueueItem playbackQueueItem = (PlaybackQueueItem) AudioPlaybackHandler.this.mQueue.take();
                    AudioPlaybackHandler.this.mCurrentWorkItem = playbackQueueItem;
                    playbackQueueItem.run();
                    AudioPlaybackHandler.this.mCurrentWorkItem = null;
                } catch (InterruptedException unused) {
                    return;
                }
            }
        }
    }
}
