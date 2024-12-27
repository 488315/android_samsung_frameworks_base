package com.samsung.android.knoxguard.service;

import android.content.Context;
import android.os.Bundle;
import android.os.HandlerThread;
import android.os.Message;
import android.os.MessageQueue;
import android.util.Slog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class KGEventQueue {
    public static final String HANDLER_THREAD_NAME = "KG_EVENT_HANDLER_THREAD";
    public static final String TAG = "KG.KGEventQueue";
    public static KGEventQueue kgEventQueue;
    public KGEventHandler eventHandler;
    public HandlerThread eventHandlerThread;
    public MessageQueue.IdleHandler idleHandler;
    public final Object mEventLock = new Object();

    public static KGEventQueue getInstance() {
        if (kgEventQueue == null) {
            kgEventQueue = new KGEventQueue();
        }
        return kgEventQueue;
    }

    public final void addIdleHandler() {
        if (this.idleHandler == null) {
            this.idleHandler =
                    new MessageQueue
                            .IdleHandler() { // from class:
                                             // com.samsung.android.knoxguard.service.KGEventQueue.1
                        @Override // android.os.MessageQueue.IdleHandler
                        public final boolean queueIdle() {
                            Slog.d(KGEventQueue.TAG, "@queueIdle called");
                            KGEventQueue.this.stopThread();
                            return false;
                        }
                    };
            this.eventHandlerThread.getLooper().getQueue().addIdleHandler(this.idleHandler);
        }
    }

    public final void enqueueEvent(Context context, KGEventHandler.SystemEvent systemEvent) {
        enqueueEvent(context, systemEvent, null);
    }

    public final void enqueueEvent(
            Context context, KGEventHandler.SystemEvent systemEvent, Bundle bundle) {
        synchronized (this.mEventLock) {
            try {
                if (context == null || systemEvent == null) {
                    return;
                }
                startThread();
                Message message = new Message();
                message.what = systemEvent.ordinal();
                message.obj = context;
                message.setData(bundle);
                this.eventHandler.sendMessage(message);
                addIdleHandler();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void startThread() {
        if (this.eventHandlerThread == null) {
            HandlerThread handlerThread = new HandlerThread(HANDLER_THREAD_NAME);
            this.eventHandlerThread = handlerThread;
            handlerThread.start();
        }
        if (this.eventHandler == null) {
            this.eventHandler = new KGEventHandler(this.eventHandlerThread.getLooper());
        }
    }

    public final void stopThread() {
        synchronized (this.mEventLock) {
            this.eventHandlerThread.quitSafely();
            this.eventHandler = null;
            this.eventHandlerThread = null;
            this.idleHandler = null;
        }
    }
}
