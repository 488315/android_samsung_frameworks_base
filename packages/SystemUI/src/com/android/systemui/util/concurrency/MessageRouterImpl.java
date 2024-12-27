package com.android.systemui.util.concurrency;

import com.android.systemui.util.concurrency.MessageRouter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MessageRouterImpl implements MessageRouter {
    private final DelayableExecutor mDelayableExecutor;
    private final Map<Integer, List<Runnable>> mIdMessageCancelers = new HashMap();
    private final Map<Class<Object>, List<Runnable>> mDataMessageCancelers = new HashMap();
    private final Map<Integer, List<MessageRouter.SimpleMessageListener>> mSimpleMessageListenerMap = new HashMap();
    private final Map<Class<?>, List<MessageRouter.DataMessageListener<Object>>> mDataMessageListenerMap = new HashMap();

    public MessageRouterImpl(DelayableExecutor delayableExecutor) {
        this.mDelayableExecutor = delayableExecutor;
    }

    private void addCanceler(int i, Runnable runnable) {
        synchronized (this.mIdMessageCancelers) {
            this.mIdMessageCancelers.putIfAbsent(Integer.valueOf(i), new ArrayList());
            this.mIdMessageCancelers.get(Integer.valueOf(i)).add(runnable);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: onMessage, reason: merged with bridge method [inline-methods] */
    public void lambda$sendMessageDelayed$0(int i) {
        synchronized (this.mSimpleMessageListenerMap) {
            try {
                if (this.mSimpleMessageListenerMap.containsKey(Integer.valueOf(i))) {
                    Iterator<MessageRouter.SimpleMessageListener> it = this.mSimpleMessageListenerMap.get(Integer.valueOf(i)).iterator();
                    while (it.hasNext()) {
                        it.next().onMessage(i);
                    }
                }
            } finally {
            }
        }
        synchronized (this.mIdMessageCancelers) {
            try {
                if (this.mIdMessageCancelers.containsKey(Integer.valueOf(i)) && !this.mIdMessageCancelers.get(Integer.valueOf(i)).isEmpty()) {
                    this.mIdMessageCancelers.get(Integer.valueOf(i)).remove(0);
                    if (this.mIdMessageCancelers.get(Integer.valueOf(i)).isEmpty()) {
                        this.mIdMessageCancelers.remove(Integer.valueOf(i));
                    }
                }
            } finally {
            }
        }
    }

    @Override // com.android.systemui.util.concurrency.MessageRouter
    public void cancelMessages(int i) {
        synchronized (this.mIdMessageCancelers) {
            try {
                if (this.mIdMessageCancelers.containsKey(Integer.valueOf(i))) {
                    Iterator<Runnable> it = this.mIdMessageCancelers.get(Integer.valueOf(i)).iterator();
                    while (it.hasNext()) {
                        it.next().run();
                    }
                    this.mIdMessageCancelers.remove(Integer.valueOf(i));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.systemui.util.concurrency.MessageRouter
    public void sendMessageDelayed(final int i, long j) {
        addCanceler(i, this.mDelayableExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.util.concurrency.MessageRouterImpl$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                MessageRouterImpl.this.lambda$sendMessageDelayed$0(i);
            }
        }, j));
    }

    @Override // com.android.systemui.util.concurrency.MessageRouter
    public void subscribeTo(int i, MessageRouter.SimpleMessageListener simpleMessageListener) {
        synchronized (this.mSimpleMessageListenerMap) {
            this.mSimpleMessageListenerMap.putIfAbsent(Integer.valueOf(i), new ArrayList());
            this.mSimpleMessageListenerMap.get(Integer.valueOf(i)).add(simpleMessageListener);
        }
    }

    @Override // com.android.systemui.util.concurrency.MessageRouter
    public void unsubscribeFrom(int i, MessageRouter.SimpleMessageListener simpleMessageListener) {
        synchronized (this.mSimpleMessageListenerMap) {
            try {
                if (this.mSimpleMessageListenerMap.containsKey(Integer.valueOf(i))) {
                    this.mSimpleMessageListenerMap.get(Integer.valueOf(i)).remove(simpleMessageListener);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.systemui.util.concurrency.MessageRouter
    public void sendMessageDelayed(final Object obj, long j) {
        addCanceler(obj.getClass(), this.mDelayableExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.util.concurrency.MessageRouterImpl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                MessageRouterImpl.this.lambda$sendMessageDelayed$1(obj);
            }
        }, j));
    }

    private void addCanceler(Class<Object> cls, Runnable runnable) {
        synchronized (this.mDataMessageCancelers) {
            this.mDataMessageCancelers.putIfAbsent(cls, new ArrayList());
            this.mDataMessageCancelers.get(cls).add(runnable);
        }
    }

    @Override // com.android.systemui.util.concurrency.MessageRouter
    public <T> void subscribeTo(Class<T> cls, MessageRouter.DataMessageListener<T> dataMessageListener) {
        synchronized (this.mDataMessageListenerMap) {
            this.mDataMessageListenerMap.putIfAbsent(cls, new ArrayList());
            this.mDataMessageListenerMap.get(cls).add(dataMessageListener);
        }
    }

    @Override // com.android.systemui.util.concurrency.MessageRouter
    public <T> void unsubscribeFrom(Class<T> cls, MessageRouter.DataMessageListener<T> dataMessageListener) {
        synchronized (this.mDataMessageListenerMap) {
            try {
                if (this.mDataMessageListenerMap.containsKey(cls)) {
                    this.mDataMessageListenerMap.get(cls).remove(dataMessageListener);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.systemui.util.concurrency.MessageRouter
    public <T> void cancelMessages(Class<T> cls) {
        synchronized (this.mDataMessageCancelers) {
            try {
                if (this.mDataMessageCancelers.containsKey(cls)) {
                    Iterator<Runnable> it = this.mDataMessageCancelers.get(cls).iterator();
                    while (it.hasNext()) {
                        it.next().run();
                    }
                    this.mDataMessageCancelers.remove(cls);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.systemui.util.concurrency.MessageRouter
    public void unsubscribeFrom(MessageRouter.SimpleMessageListener simpleMessageListener) {
        synchronized (this.mSimpleMessageListenerMap) {
            try {
                Iterator<Integer> it = this.mSimpleMessageListenerMap.keySet().iterator();
                while (it.hasNext()) {
                    this.mSimpleMessageListenerMap.get(it.next()).remove(simpleMessageListener);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: onMessage, reason: merged with bridge method [inline-methods] */
    public void lambda$sendMessageDelayed$1(Object obj) {
        synchronized (this.mDataMessageListenerMap) {
            try {
                if (this.mDataMessageListenerMap.containsKey(obj.getClass())) {
                    Iterator<MessageRouter.DataMessageListener<Object>> it = this.mDataMessageListenerMap.get(obj.getClass()).iterator();
                    while (it.hasNext()) {
                        it.next().onMessage(obj);
                    }
                }
            } finally {
            }
        }
        synchronized (this.mDataMessageCancelers) {
            try {
                if (this.mDataMessageCancelers.containsKey(obj.getClass()) && !this.mDataMessageCancelers.get(obj.getClass()).isEmpty()) {
                    this.mDataMessageCancelers.get(obj.getClass()).remove(0);
                    if (this.mDataMessageCancelers.get(obj.getClass()).isEmpty()) {
                        this.mDataMessageCancelers.remove(obj.getClass());
                    }
                }
            } finally {
            }
        }
    }

    @Override // com.android.systemui.util.concurrency.MessageRouter
    public <T> void unsubscribeFrom(MessageRouter.DataMessageListener<T> dataMessageListener) {
        synchronized (this.mDataMessageListenerMap) {
            try {
                Iterator<Class<?>> it = this.mDataMessageListenerMap.keySet().iterator();
                while (it.hasNext()) {
                    this.mDataMessageListenerMap.get(it.next()).remove(dataMessageListener);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
