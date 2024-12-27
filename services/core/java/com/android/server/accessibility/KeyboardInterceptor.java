package com.android.server.accessibility;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Pools;
import android.util.Slog;
import android.view.KeyEvent;

import com.android.server.policy.PhoneWindowManager;
import com.android.server.policy.WindowManagerPolicy;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class KeyboardInterceptor extends BaseEventStreamTransformation
        implements Handler.Callback {
    public final AccessibilityManagerService mAms;
    public KeyEventHolder mEventQueueEnd;
    public KeyEventHolder mEventQueueStart;
    public final Handler mHandler = new Handler(this);
    public final WindowManagerPolicy mPolicy;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class KeyEventHolder {
        public static final Pools.SimplePool sPool = new Pools.SimplePool(32);
        public long dispatchTime;
        public KeyEvent event;
        public int policyFlags;
        public KeyEventHolder previous;
    }

    public KeyboardInterceptor(
            AccessibilityManagerService accessibilityManagerService,
            WindowManagerPolicy windowManagerPolicy) {
        this.mAms = accessibilityManagerService;
        this.mPolicy = windowManagerPolicy;
    }

    @Override // android.os.Handler.Callback
    public final boolean handleMessage(Message message) {
        if (message.what != 1) {
            Slog.e("KeyboardInterceptor", "Unexpected message type");
            return false;
        }
        long uptimeMillis = SystemClock.uptimeMillis();
        while (true) {
            KeyEventHolder keyEventHolder = this.mEventQueueEnd;
            if (keyEventHolder == null || keyEventHolder.dispatchTime > uptimeMillis) {
                break;
            }
            KeyEvent keyEvent = keyEventHolder.event;
            int i = keyEventHolder.policyFlags;
            int keyCode = keyEvent.getKeyCode();
            long interceptKeyBeforeDispatching =
                    (keyCode == 25 || keyCode == 24)
                            ? ((PhoneWindowManager) this.mPolicy)
                                    .interceptKeyBeforeDispatching(null, keyEvent, i)
                            : 0L;
            if (interceptKeyBeforeDispatching > 0) {
                this.mEventQueueEnd.dispatchTime = uptimeMillis + interceptKeyBeforeDispatching;
                break;
            }
            if (interceptKeyBeforeDispatching == 0) {
                AccessibilityManagerService accessibilityManagerService = this.mAms;
                KeyEventHolder keyEventHolder2 = this.mEventQueueEnd;
                accessibilityManagerService.notifyKeyEvent(
                        keyEventHolder2.event, keyEventHolder2.policyFlags);
            }
            KeyEventHolder keyEventHolder3 = this.mEventQueueEnd;
            this.mEventQueueEnd = keyEventHolder3.previous;
            keyEventHolder3.event.recycle();
            keyEventHolder3.event = null;
            keyEventHolder3.policyFlags = 0;
            keyEventHolder3.dispatchTime = 0L;
            keyEventHolder3.previous = null;
            KeyEventHolder.sPool.release(keyEventHolder3);
            if (this.mEventQueueEnd == null) {
                this.mEventQueueStart = null;
            }
        }
        if (this.mEventQueueStart != null
                && !this.mHandler.sendEmptyMessageAtTime(1, this.mEventQueueEnd.dispatchTime)) {
            Slog.e("KeyboardInterceptor", "Failed to schedule key event");
        }
        return true;
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public final void onKeyEvent(KeyEvent keyEvent, int i) {
        if (this.mAms.mTraceManager.isA11yTracingEnabledForTypes(4096L)) {
            this.mAms.mTraceManager.logTrace(
                    "KeyboardInterceptor.onKeyEvent",
                    4096L,
                    "event=" + keyEvent + ";policyFlags=" + i);
        }
        int keyCode = keyEvent.getKeyCode();
        long interceptKeyBeforeDispatching =
                (keyCode == 25 || keyCode == 24)
                        ? ((PhoneWindowManager) this.mPolicy)
                                .interceptKeyBeforeDispatching(null, keyEvent, i)
                        : 0L;
        if (interceptKeyBeforeDispatching < 0) {
            return;
        }
        if (interceptKeyBeforeDispatching <= 0 && this.mEventQueueStart == null) {
            this.mAms.notifyKeyEvent(keyEvent, i);
            return;
        }
        long uptimeMillis = SystemClock.uptimeMillis() + interceptKeyBeforeDispatching;
        if (this.mEventQueueStart != null) {
            KeyEventHolder keyEventHolder = (KeyEventHolder) KeyEventHolder.sPool.acquire();
            if (keyEventHolder == null) {
                keyEventHolder = new KeyEventHolder();
            }
            keyEventHolder.event = KeyEvent.obtain(keyEvent);
            keyEventHolder.policyFlags = i;
            keyEventHolder.dispatchTime = uptimeMillis;
            this.mEventQueueStart.previous = keyEventHolder;
            this.mEventQueueStart = keyEventHolder;
            return;
        }
        KeyEventHolder keyEventHolder2 = (KeyEventHolder) KeyEventHolder.sPool.acquire();
        if (keyEventHolder2 == null) {
            keyEventHolder2 = new KeyEventHolder();
        }
        keyEventHolder2.event = KeyEvent.obtain(keyEvent);
        keyEventHolder2.policyFlags = i;
        keyEventHolder2.dispatchTime = uptimeMillis;
        this.mEventQueueStart = keyEventHolder2;
        this.mEventQueueEnd = keyEventHolder2;
        if (this.mHandler.sendEmptyMessageAtTime(1, uptimeMillis)) {
            return;
        }
        Slog.e("KeyboardInterceptor", "Failed to schedule key event");
    }
}
