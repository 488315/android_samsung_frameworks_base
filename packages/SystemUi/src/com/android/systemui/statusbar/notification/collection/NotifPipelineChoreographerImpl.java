package com.android.systemui.statusbar.notification.collection;

import android.view.Choreographer;
import com.android.systemui.util.ListenerSet;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.ExecutorImpl;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotifPipelineChoreographerImpl implements NotifPipelineChoreographer {
    public final DelayableExecutor executor;
    public boolean isScheduled;
    public ExecutorImpl.ExecutionToken timeoutSubscription;
    public final Choreographer viewChoreographer;
    public final ListenerSet listeners = new ListenerSet();
    public final NotifPipelineChoreographerImpl$frameCallback$1 frameCallback = new Choreographer.FrameCallback() { // from class: com.android.systemui.statusbar.notification.collection.NotifPipelineChoreographerImpl$frameCallback$1
        @Override // android.view.Choreographer.FrameCallback
        public final void doFrame(long j) {
            NotifPipelineChoreographerImpl notifPipelineChoreographerImpl = NotifPipelineChoreographerImpl.this;
            if (notifPipelineChoreographerImpl.isScheduled) {
                notifPipelineChoreographerImpl.isScheduled = false;
                ExecutorImpl.ExecutionToken executionToken = notifPipelineChoreographerImpl.timeoutSubscription;
                if (executionToken != null) {
                    executionToken.run();
                }
                Iterator it = NotifPipelineChoreographerImpl.this.listeners.iterator();
                while (it.hasNext()) {
                    ((Runnable) it.next()).run();
                }
            }
        }
    };

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.statusbar.notification.collection.NotifPipelineChoreographerImpl$frameCallback$1] */
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
            this.timeoutSubscription = this.executor.executeDelayed(100L, new Runnable() { // from class: com.android.systemui.statusbar.notification.collection.NotifPipelineChoreographerImpl$schedule$1
                @Override // java.lang.Runnable
                public final void run() {
                    NotifPipelineChoreographerImpl notifPipelineChoreographerImpl = NotifPipelineChoreographerImpl.this;
                    if (notifPipelineChoreographerImpl.isScheduled) {
                        notifPipelineChoreographerImpl.isScheduled = false;
                        notifPipelineChoreographerImpl.viewChoreographer.removeFrameCallback(notifPipelineChoreographerImpl.frameCallback);
                        Iterator it = notifPipelineChoreographerImpl.listeners.iterator();
                        while (it.hasNext()) {
                            ((Runnable) it.next()).run();
                        }
                    }
                }
            });
        }
    }
}
