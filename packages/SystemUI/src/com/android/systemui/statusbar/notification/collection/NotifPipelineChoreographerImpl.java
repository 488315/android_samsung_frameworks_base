package com.android.systemui.statusbar.notification.collection;

import android.view.Choreographer;
import com.android.systemui.util.ListenerSet;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.util.Iterator;

public final class NotifPipelineChoreographerImpl implements NotifPipelineChoreographer {
    public final DelayableExecutor executor;
    public boolean isScheduled;
    public Runnable timeoutSubscription;
    public final Choreographer viewChoreographer;
    public final ListenerSet listeners = new ListenerSet();
    public final NotifPipelineChoreographerImpl$frameCallback$1 frameCallback = new Choreographer.FrameCallback() { // from class: com.android.systemui.statusbar.notification.collection.NotifPipelineChoreographerImpl$frameCallback$1
        @Override // android.view.Choreographer.FrameCallback
        public final void doFrame(long j) {
            NotifPipelineChoreographerImpl notifPipelineChoreographerImpl = NotifPipelineChoreographerImpl.this;
            if (notifPipelineChoreographerImpl.isScheduled) {
                notifPipelineChoreographerImpl.isScheduled = false;
                Runnable runnable = notifPipelineChoreographerImpl.timeoutSubscription;
                if (runnable != null) {
                    runnable.run();
                }
                Iterator<E> it = NotifPipelineChoreographerImpl.this.listeners.iterator();
                while (it.hasNext()) {
                    ((Runnable) it.next()).run();
                }
            }
        }
    };

    public NotifPipelineChoreographerImpl(Choreographer choreographer, DelayableExecutor delayableExecutor) {
        this.viewChoreographer = choreographer;
        this.executor = delayableExecutor;
    }

    public final void schedule() {
        if (this.isScheduled) {
            return;
        }
        this.isScheduled = true;
        this.viewChoreographer.postFrameCallback(this.frameCallback);
        if (this.isScheduled) {
            this.timeoutSubscription = this.executor.executeDelayed(new Runnable() { // from class: com.android.systemui.statusbar.notification.collection.NotifPipelineChoreographerImpl$schedule$1
                @Override // java.lang.Runnable
                public final void run() {
                    NotifPipelineChoreographerImpl notifPipelineChoreographerImpl = NotifPipelineChoreographerImpl.this;
                    if (notifPipelineChoreographerImpl.isScheduled) {
                        notifPipelineChoreographerImpl.isScheduled = false;
                        notifPipelineChoreographerImpl.viewChoreographer.removeFrameCallback(notifPipelineChoreographerImpl.frameCallback);
                        Iterator<E> it = notifPipelineChoreographerImpl.listeners.iterator();
                        while (it.hasNext()) {
                            ((Runnable) it.next()).run();
                        }
                    }
                }
            }, 100L);
        }
    }
}
