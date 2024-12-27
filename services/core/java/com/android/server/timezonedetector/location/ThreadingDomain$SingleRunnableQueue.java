package com.android.server.timezonedetector.location;

public final class ThreadingDomain$SingleRunnableQueue {
    public long mDelayMillis;
    public boolean mIsQueued;
    public final /* synthetic */ HandlerThreadingDomain this$0;

    public ThreadingDomain$SingleRunnableQueue(HandlerThreadingDomain handlerThreadingDomain) {
        this.this$0 = handlerThreadingDomain;
    }

    public final void cancel() {
        HandlerThreadingDomain handlerThreadingDomain = this.this$0;
        handlerThreadingDomain.assertCurrentThread();
        if (this.mIsQueued) {
            handlerThreadingDomain.mHandler.removeCallbacksAndMessages(this);
        }
        this.mIsQueued = false;
        this.mDelayMillis = -1L;
    }
}
