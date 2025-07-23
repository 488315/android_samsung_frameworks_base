package android.os;

/* loaded from: classes3.dex */
public abstract class CountDownTimer {
    private static final int MSG = 1;
    private final long mCountdownInterval;
    private final long mMillisInFuture;
    private long mStopTimeInFuture;
    private boolean mCancelled = false;
    private Handler mHandler = new Handler() { // from class: android.os.CountDownTimer.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            long j;
            synchronized (CountDownTimer.this) {
                if (CountDownTimer.this.mCancelled) {
                    return;
                }
                long elapsedRealtime = CountDownTimer.this.mStopTimeInFuture - SystemClock.elapsedRealtime();
                long j2 = 0;
                if (elapsedRealtime <= 0) {
                    CountDownTimer.this.onFinish();
                } else {
                    long elapsedRealtime2 = SystemClock.elapsedRealtime();
                    CountDownTimer.this.onTick(elapsedRealtime);
                    long elapsedRealtime3 = SystemClock.elapsedRealtime() - elapsedRealtime2;
                    if (elapsedRealtime < CountDownTimer.this.mCountdownInterval) {
                        j = elapsedRealtime - elapsedRealtime3;
                        if (j < 0) {
                            sendMessageDelayed(obtainMessage(1), j2);
                        }
                    } else {
                        j = CountDownTimer.this.mCountdownInterval - elapsedRealtime3;
                        while (j < 0) {
                            j += CountDownTimer.this.mCountdownInterval;
                        }
                    }
                    j2 = j;
                    sendMessageDelayed(obtainMessage(1), j2);
                }
            }
        }
    };

    public abstract void onFinish();

    public abstract void onTick(long j);

    public CountDownTimer(long j, long j2) {
        this.mMillisInFuture = j;
        this.mCountdownInterval = j2;
    }

    public final synchronized void cancel() {
        this.mCancelled = true;
        this.mHandler.removeMessages(1);
    }

    public final synchronized CountDownTimer start() {
        this.mCancelled = false;
        if (this.mMillisInFuture <= 0) {
            onFinish();
            return this;
        }
        this.mStopTimeInFuture = SystemClock.elapsedRealtime() + this.mMillisInFuture;
        Handler handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(1));
        return this;
    }
}
