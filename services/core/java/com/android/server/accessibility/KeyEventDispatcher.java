package com.android.server.accessibility;

import android.os.Binder;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pools;
import android.view.InputEventConsistencyVerifier;
import android.view.KeyEvent;

import com.android.server.HeapdumpWatcher$$ExternalSyntheticOutline0;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class KeyEventDispatcher implements Handler.Callback {
    public final Handler mHandlerToSendKeyEventsToInputFilter;
    public final Handler mKeyEventTimeoutHandler;
    public final Object mLock;
    public final int mMessageTypeForSendKeyEvent;
    public final Pools.Pool mPendingEventPool = new Pools.SimplePool(10);
    public final Map mPendingEventsMap = new ArrayMap();
    public final PowerManager mPowerManager;
    public final InputEventConsistencyVerifier mSentEventsVerifier;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface KeyEventFilter {
        boolean onKeyEvent(KeyEvent keyEvent, int i);
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PendingKeyEvent {
        public KeyEvent event;
        public boolean handled;
        public int policyFlags;
        public int referenceCount;
    }

    public KeyEventDispatcher(Handler handler, Object obj, PowerManager powerManager) {
        if (InputEventConsistencyVerifier.isInstrumentationEnabled()) {
            this.mSentEventsVerifier =
                    new InputEventConsistencyVerifier(this, 0, "KeyEventDispatcher");
        } else {
            this.mSentEventsVerifier = null;
        }
        this.mHandlerToSendKeyEventsToInputFilter = handler;
        this.mMessageTypeForSendKeyEvent = 8;
        this.mKeyEventTimeoutHandler = new Handler(handler.getLooper(), this);
        this.mLock = obj;
        this.mPowerManager = powerManager;
    }

    @Override // android.os.Handler.Callback
    public final boolean handleMessage(Message message) {
        if (message.what != 1) {
            HeapdumpWatcher$$ExternalSyntheticOutline0.m(
                    new StringBuilder("Unknown message: "), message.what, "KeyEventDispatcher");
            return false;
        }
        Log.i(
                "KeyEventDispatcher",
                "KeyEventDispatcher handleMessage cannot receive setOnKeyEventResult");
        PendingKeyEvent pendingKeyEvent = (PendingKeyEvent) message.obj;
        synchronized (this.mLock) {
            try {
                Iterator it = ((ArrayMap) this.mPendingEventsMap).values().iterator();
                while (it.hasNext()
                        && (!((ArrayList) it.next()).remove(pendingKeyEvent)
                                || !removeReferenceToPendingEventLocked(pendingKeyEvent))) {}
            } finally {
            }
        }
        return true;
    }

    public final boolean notifyKeyEventLocked(KeyEvent keyEvent, int i, List list) {
        Log.i("KeyEventDispatcher", "KeyEventDispatcher notifyKeyEventLocked");
        KeyEvent obtain = KeyEvent.obtain(keyEvent);
        PendingKeyEvent pendingKeyEvent = null;
        int i2 = 0;
        while (true) {
            ArrayList arrayList = (ArrayList) list;
            if (i2 >= arrayList.size()) {
                break;
            }
            KeyEventFilter keyEventFilter = (KeyEventFilter) arrayList.get(i2);
            if (keyEventFilter.onKeyEvent(obtain, obtain.getSequenceNumber())) {
                if (pendingKeyEvent == null) {
                    pendingKeyEvent = (PendingKeyEvent) this.mPendingEventPool.acquire();
                    if (pendingKeyEvent == null) {
                        pendingKeyEvent = new PendingKeyEvent();
                    }
                    pendingKeyEvent.event = obtain;
                    pendingKeyEvent.policyFlags = i;
                    pendingKeyEvent.referenceCount = 0;
                    pendingKeyEvent.handled = false;
                }
                ArrayList arrayList2 =
                        (ArrayList) ((ArrayMap) this.mPendingEventsMap).get(keyEventFilter);
                if (arrayList2 == null) {
                    arrayList2 = new ArrayList();
                    ((ArrayMap) this.mPendingEventsMap).put(keyEventFilter, arrayList2);
                }
                arrayList2.add(pendingKeyEvent);
                pendingKeyEvent.referenceCount++;
            }
            i2++;
        }
        if (pendingKeyEvent == null) {
            obtain.recycle();
            return false;
        }
        this.mKeyEventTimeoutHandler.sendMessageDelayed(
                this.mKeyEventTimeoutHandler.obtainMessage(1, pendingKeyEvent), 500L);
        return true;
    }

    public final boolean removeReferenceToPendingEventLocked(PendingKeyEvent pendingKeyEvent) {
        int i = pendingKeyEvent.referenceCount - 1;
        pendingKeyEvent.referenceCount = i;
        if (i > 0) {
            return false;
        }
        this.mKeyEventTimeoutHandler.removeMessages(1, pendingKeyEvent);
        if (pendingKeyEvent.handled) {
            pendingKeyEvent.event.recycle();
        } else {
            InputEventConsistencyVerifier inputEventConsistencyVerifier = this.mSentEventsVerifier;
            if (inputEventConsistencyVerifier != null) {
                inputEventConsistencyVerifier.onKeyEvent(pendingKeyEvent.event, 0);
            }
            this.mHandlerToSendKeyEventsToInputFilter
                    .obtainMessage(
                            this.mMessageTypeForSendKeyEvent,
                            pendingKeyEvent.policyFlags | 1073741824,
                            0,
                            pendingKeyEvent.event)
                    .sendToTarget();
        }
        this.mPendingEventPool.release(pendingKeyEvent);
        return true;
    }

    public final void setOnKeyEventResult(KeyEventFilter keyEventFilter, boolean z, int i) {
        PendingKeyEvent pendingKeyEvent;
        Log.i("KeyEventDispatcher", "KeyEventDispatcher setOnKeyEventResult");
        synchronized (this.mLock) {
            try {
                List list = (List) ((ArrayMap) this.mPendingEventsMap).get(keyEventFilter);
                int i2 = 0;
                while (true) {
                    if (i2 >= list.size()) {
                        pendingKeyEvent = null;
                        break;
                    }
                    pendingKeyEvent = (PendingKeyEvent) list.get(i2);
                    if (pendingKeyEvent.event.getSequenceNumber() == i) {
                        list.remove(pendingKeyEvent);
                        break;
                    }
                    i2++;
                }
                if (pendingKeyEvent != null) {
                    if (z && !pendingKeyEvent.handled) {
                        pendingKeyEvent.handled = z;
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            this.mPowerManager.userActivity(
                                    pendingKeyEvent.event.getEventTime(), 3, 0);
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        } catch (Throwable th) {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            throw th;
                        }
                    }
                    removeReferenceToPendingEventLocked(pendingKeyEvent);
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }
}
