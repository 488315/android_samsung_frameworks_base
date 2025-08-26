package android.speech.tts;

import android.media.AudioTrack;
import android.speech.tts.TextToSpeechService;
import android.util.Log;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes3.dex */
final class SynthesisPlaybackQueueItem extends PlaybackQueueItem implements AudioTrack.OnPlaybackPositionUpdateListener {
    private static final boolean DBG = false;
    private static final long MAX_UNCONSUMED_AUDIO_MS = 500;
    private static final int NOT_RUN = 0;
    private static final int RUN_CALLED = 1;
    private static final int STOP_CALLED = 2;
    private static final String TAG = "TTS.SynthQueueItem";
    private final BlockingAudioTrack mAudioTrack;
    private final LinkedList<ListEntry> mDataBufferList;
    private volatile boolean mDone;
    private final Lock mListLock;
    private final AbstractEventLogger mLogger;
    private final Condition mNotFull;
    private final Condition mReadReady;
    private final AtomicInteger mRunState;
    private volatile int mStatusCode;
    private volatile boolean mStopped;
    private int mUnconsumedBytes;
    private ConcurrentLinkedQueue<ProgressMarker> markerList;

    @Override // android.media.AudioTrack.OnPlaybackPositionUpdateListener
    public void onPeriodicNotification(AudioTrack audioTrack) {
    }

    SynthesisPlaybackQueueItem(TextToSpeechService.AudioOutputParams audioOutputParams, int i, int i2, int i3, TextToSpeechService.UtteranceProgressDispatcher utteranceProgressDispatcher, Object obj, AbstractEventLogger abstractEventLogger) {
        super(utteranceProgressDispatcher, obj);
        ReentrantLock reentrantLock = new ReentrantLock();
        this.mListLock = reentrantLock;
        this.mReadReady = reentrantLock.newCondition();
        this.mNotFull = reentrantLock.newCondition();
        this.mDataBufferList = new LinkedList<>();
        this.markerList = new ConcurrentLinkedQueue<>();
        this.mRunState = new AtomicInteger(0);
        this.mUnconsumedBytes = 0;
        this.mStopped = false;
        this.mDone = false;
        this.mStatusCode = 0;
        this.mAudioTrack = new BlockingAudioTrack(audioOutputParams, i, i2, i3);
        this.mLogger = abstractEventLogger;
    }

    @Override // android.speech.tts.PlaybackQueueItem, java.lang.Runnable
    public void run() throws IllegalStateException, InterruptedException {
        if (this.mRunState.compareAndSet(0, 1)) {
            TextToSpeechService.UtteranceProgressDispatcher dispatcher = getDispatcher();
            dispatcher.dispatchOnStart();
            if (!this.mAudioTrack.init()) {
                dispatcher.dispatchOnError(-5);
                return;
            }
            this.mAudioTrack.setPlaybackPositionUpdateListener(this);
            updateMarker();
            while (true) {
                try {
                    byte[] bArrTake = take();
                    if (bArrTake == null) {
                        break;
                    }
                    this.mAudioTrack.write(bArrTake);
                    this.mLogger.onAudioDataWritten();
                } catch (InterruptedException unused) {
                }
            }
            this.mAudioTrack.waitAndRelease();
            dispatchEndStatus();
        }
    }

    private void dispatchEndStatus() {
        TextToSpeechService.UtteranceProgressDispatcher dispatcher = getDispatcher();
        if (this.mStatusCode == 0) {
            dispatcher.dispatchOnSuccess();
        } else if (this.mStatusCode == -2) {
            dispatcher.dispatchOnStop();
        } else {
            dispatcher.dispatchOnError(this.mStatusCode);
        }
        this.mLogger.onCompleted(this.mStatusCode);
    }

    @Override // android.speech.tts.PlaybackQueueItem
    void stop(int i) {
        try {
            this.mListLock.lock();
            this.mStopped = true;
            this.mStatusCode = i;
            this.mNotFull.signal();
            if (this.mRunState.getAndSet(2) == 0) {
                dispatchEndStatus();
                return;
            }
            this.mReadReady.signal();
            this.mListLock.unlock();
            this.mAudioTrack.stop();
        } finally {
            this.mListLock.unlock();
        }
    }

    void done() {
        try {
            this.mListLock.lock();
            this.mDone = true;
            this.mReadReady.signal();
            this.mNotFull.signal();
        } finally {
            this.mListLock.unlock();
        }
    }

    private class ProgressMarker {
        public final int end;
        public final int frames;
        public final int start;

        public ProgressMarker(SynthesisPlaybackQueueItem synthesisPlaybackQueueItem, int i, int i2, int i3) {
            this.frames = i;
            this.start = i2;
            this.end = i3;
        }
    }

    void updateMarker() {
        ProgressMarker progressMarkerPeek = this.markerList.peek();
        if (progressMarkerPeek != null) {
            this.mAudioTrack.setNotificationMarkerPosition(progressMarkerPeek.frames == 0 ? 1 : progressMarkerPeek.frames);
        }
    }

    void rangeStart(int i, int i2, int i3) {
        this.markerList.add(new ProgressMarker(this, i, i2, i3));
        updateMarker();
    }

    @Override // android.media.AudioTrack.OnPlaybackPositionUpdateListener
    public void onMarkerReached(AudioTrack audioTrack) {
        ProgressMarker progressMarkerPoll = this.markerList.poll();
        if (progressMarkerPoll == null) {
            Log.e(TAG, "onMarkerReached reached called but no marker in queue");
        } else {
            getDispatcher().dispatchOnRangeStart(progressMarkerPoll.start, progressMarkerPoll.end, progressMarkerPoll.frames);
            updateMarker();
        }
    }

    void put(byte[] bArr) throws InterruptedException {
        try {
            this.mListLock.lock();
            while (this.mAudioTrack.getAudioLengthMs(this.mUnconsumedBytes) > MAX_UNCONSUMED_AUDIO_MS && !this.mStopped) {
                this.mNotFull.await();
            }
            if (!this.mStopped) {
                this.mDataBufferList.add(new ListEntry(bArr));
                this.mUnconsumedBytes += bArr.length;
                this.mReadReady.signal();
            }
        } finally {
            this.mListLock.unlock();
        }
    }

    private byte[] take() throws InterruptedException {
        ListEntry listEntryPoll;
        try {
            this.mListLock.lock();
            while (this.mDataBufferList.size() == 0 && !this.mStopped && !this.mDone) {
                this.mReadReady.await();
            }
            if (!this.mStopped && (listEntryPoll = this.mDataBufferList.poll()) != null) {
                this.mUnconsumedBytes -= listEntryPoll.mBytes.length;
                this.mNotFull.signal();
                return listEntryPoll.mBytes;
            }
            return null;
        } finally {
            this.mListLock.unlock();
        }
    }

    static final class ListEntry {
        final byte[] mBytes;

        ListEntry(byte[] bArr) {
            this.mBytes = bArr;
        }
    }
}
