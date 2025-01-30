package com.android.systemui.util.concurrency;

import com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda11;
import com.android.systemui.util.concurrency.ExecutorImpl;
import com.android.systemui.util.concurrency.MessageRouter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class MessageRouterImpl implements MessageRouter {
    public final DelayableExecutor mDelayableExecutor;
    public final Map mIdMessageCancelers = new HashMap();
    public final Map mDataMessageCancelers = new HashMap();
    public final Map mSimpleMessageListenerMap = new HashMap();
    public final Map mDataMessageListenerMap = new HashMap();

    public MessageRouterImpl(DelayableExecutor delayableExecutor) {
        this.mDelayableExecutor = delayableExecutor;
    }

    public final void cancelMessages(int i) {
        synchronized (this.mIdMessageCancelers) {
            if (((HashMap) this.mIdMessageCancelers).containsKey(Integer.valueOf(i))) {
                Iterator it = ((List) ((HashMap) this.mIdMessageCancelers).get(Integer.valueOf(i))).iterator();
                while (it.hasNext()) {
                    ((Runnable) it.next()).run();
                }
                ((HashMap) this.mIdMessageCancelers).remove(Integer.valueOf(i));
            }
        }
    }

    public final void sendMessageDelayed(final int i, long j) {
        ExecutorImpl.ExecutionToken executeDelayed = this.mDelayableExecutor.executeDelayed(j, new Runnable() { // from class: com.android.systemui.util.concurrency.MessageRouterImpl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                MessageRouterImpl messageRouterImpl = MessageRouterImpl.this;
                int i2 = i;
                synchronized (messageRouterImpl.mSimpleMessageListenerMap) {
                    if (((HashMap) messageRouterImpl.mSimpleMessageListenerMap).containsKey(Integer.valueOf(i2))) {
                        Iterator it = ((List) ((HashMap) messageRouterImpl.mSimpleMessageListenerMap).get(Integer.valueOf(i2))).iterator();
                        while (it.hasNext()) {
                            ((MessageRouter.SimpleMessageListener) it.next()).onMessage();
                        }
                    }
                }
                synchronized (messageRouterImpl.mIdMessageCancelers) {
                    if (((HashMap) messageRouterImpl.mIdMessageCancelers).containsKey(Integer.valueOf(i2))) {
                        if (!((List) ((HashMap) messageRouterImpl.mIdMessageCancelers).get(Integer.valueOf(i2))).isEmpty()) {
                            ((List) ((HashMap) messageRouterImpl.mIdMessageCancelers).get(Integer.valueOf(i2))).remove(0);
                            if (((List) ((HashMap) messageRouterImpl.mIdMessageCancelers).get(Integer.valueOf(i2))).isEmpty()) {
                                ((HashMap) messageRouterImpl.mIdMessageCancelers).remove(Integer.valueOf(i2));
                            }
                        }
                    }
                }
            }
        });
        synchronized (this.mIdMessageCancelers) {
            ((HashMap) this.mIdMessageCancelers).putIfAbsent(Integer.valueOf(i), new ArrayList());
            ((List) ((HashMap) this.mIdMessageCancelers).get(Integer.valueOf(i))).add(executeDelayed);
        }
    }

    public final void subscribeTo(int i, MessageRouter.SimpleMessageListener simpleMessageListener) {
        synchronized (this.mSimpleMessageListenerMap) {
            ((HashMap) this.mSimpleMessageListenerMap).putIfAbsent(Integer.valueOf(i), new ArrayList());
            ((List) ((HashMap) this.mSimpleMessageListenerMap).get(Integer.valueOf(i))).add(simpleMessageListener);
        }
    }

    public final void subscribeTo(Class cls, CentralSurfacesImpl$$ExternalSyntheticLambda11 centralSurfacesImpl$$ExternalSyntheticLambda11) {
        synchronized (this.mDataMessageListenerMap) {
            ((HashMap) this.mDataMessageListenerMap).putIfAbsent(cls, new ArrayList());
            ((List) ((HashMap) this.mDataMessageListenerMap).get(cls)).add(centralSurfacesImpl$$ExternalSyntheticLambda11);
        }
    }
}
