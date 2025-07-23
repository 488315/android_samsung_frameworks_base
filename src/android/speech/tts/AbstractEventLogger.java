package android.speech.tts;

import android.os.SystemClock;

/* loaded from: classes3.dex */
abstract class AbstractEventLogger {
    protected final int mCallerPid;
    protected final int mCallerUid;
    protected final String mServiceApp;
    protected long mPlaybackStartTime = -1;
    private volatile long mRequestProcessingStartTime = -1;
    private volatile long mEngineStartTime = -1;
    private volatile long mEngineCompleteTime = -1;
    private boolean mLogWritten = false;
    protected final long mReceivedTime = SystemClock.elapsedRealtime();

    protected abstract void logFailure(int i);

    protected abstract void logSuccess(long j, long j2, long j3);

    AbstractEventLogger(int i, int i2, String str) {
        this.mCallerUid = i;
        this.mCallerPid = i2;
        this.mServiceApp = str;
    }

    public void onRequestProcessingStart() {
        this.mRequestProcessingStartTime = SystemClock.elapsedRealtime();
    }

    public void onEngineDataReceived() {
        if (this.mEngineStartTime == -1) {
            this.mEngineStartTime = SystemClock.elapsedRealtime();
        }
    }

    public void onEngineComplete() {
        this.mEngineCompleteTime = SystemClock.elapsedRealtime();
    }

    public void onAudioDataWritten() {
        if (this.mPlaybackStartTime == -1) {
            this.mPlaybackStartTime = SystemClock.elapsedRealtime();
        }
    }

    public void onCompleted(int i) {
        if (this.mLogWritten) {
            return;
        }
        this.mLogWritten = true;
        SystemClock.elapsedRealtime();
        if (i != 0 || this.mPlaybackStartTime == -1 || this.mEngineCompleteTime == -1) {
            logFailure(i);
        } else {
            logSuccess(this.mPlaybackStartTime - this.mReceivedTime, this.mEngineStartTime - this.mRequestProcessingStartTime, this.mEngineCompleteTime - this.mRequestProcessingStartTime);
        }
    }
}
