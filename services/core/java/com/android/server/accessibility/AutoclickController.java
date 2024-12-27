package com.android.server.accessibility;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.MotionEvent;

public final class AutoclickController extends BaseEventStreamTransformation {
    public ClickDelayObserver mClickDelayObserver;
    public ClickScheduler mClickScheduler;
    public final Context mContext;
    public final AccessibilityTraceManager mTrace;
    public final int mUserId;

    public final class ClickDelayObserver extends ContentObserver {
        public final Uri mAutoclickDelaySettingUri;
        public ClickScheduler mClickScheduler;
        public ContentResolver mContentResolver;
        public final int mUserId;

        public ClickDelayObserver(int i, Handler handler) {
            super(handler);
            this.mAutoclickDelaySettingUri =
                    Settings.Secure.getUriFor("accessibility_autoclick_delay");
            this.mUserId = i;
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            if (this.mAutoclickDelaySettingUri.equals(uri)) {
                this.mClickScheduler.mDelay =
                        Settings.Secure.getIntForUser(
                                this.mContentResolver,
                                "accessibility_autoclick_delay",
                                600,
                                this.mUserId);
            }
        }
    }

    public final class ClickScheduler implements Runnable {
        public boolean mActive;
        public final MotionEvent.PointerCoords mAnchorCoords;
        public int mDelay;
        public int mEventPolicyFlags;
        public final Handler mHandler;
        public MotionEvent mLastMotionEvent = null;
        public int mMetaState;
        public long mScheduledClickTime;
        public MotionEvent.PointerCoords[] mTempPointerCoords;
        public MotionEvent.PointerProperties[] mTempPointerProperties;

        public ClickScheduler(Handler handler) {
            this.mHandler = handler;
            resetInternalState();
            this.mDelay = 600;
            this.mAnchorCoords = new MotionEvent.PointerCoords();
        }

        public final void cancel() {
            if (this.mActive) {
                resetInternalState();
                this.mHandler.removeCallbacks(this);
            }
        }

        public final void resetInternalState() {
            this.mActive = false;
            MotionEvent motionEvent = this.mLastMotionEvent;
            if (motionEvent != null) {
                motionEvent.recycle();
                this.mLastMotionEvent = null;
            }
            this.mScheduledClickTime = -1L;
        }

        @Override // java.lang.Runnable
        public final void run() {
            long uptimeMillis = SystemClock.uptimeMillis();
            long j = this.mScheduledClickTime;
            if (uptimeMillis < j) {
                this.mHandler.postDelayed(this, j - uptimeMillis);
                return;
            }
            MotionEvent motionEvent = this.mLastMotionEvent;
            if (motionEvent != null && AutoclickController.this.mNext != null) {
                int actionIndex = motionEvent.getActionIndex();
                if (this.mTempPointerProperties == null) {
                    this.mTempPointerProperties =
                            new MotionEvent.PointerProperties[] {
                                new MotionEvent.PointerProperties()
                            };
                }
                this.mLastMotionEvent.getPointerProperties(
                        actionIndex, this.mTempPointerProperties[0]);
                if (this.mTempPointerCoords == null) {
                    this.mTempPointerCoords =
                            new MotionEvent.PointerCoords[] {new MotionEvent.PointerCoords()};
                }
                this.mLastMotionEvent.getPointerCoords(actionIndex, this.mTempPointerCoords[0]);
                long uptimeMillis2 = SystemClock.uptimeMillis();
                MotionEvent obtain =
                        MotionEvent.obtain(
                                uptimeMillis2,
                                uptimeMillis2,
                                0,
                                1,
                                this.mTempPointerProperties,
                                this.mTempPointerCoords,
                                this.mMetaState,
                                1,
                                1.0f,
                                1.0f,
                                this.mLastMotionEvent.getDeviceId(),
                                0,
                                this.mLastMotionEvent.getSource(),
                                this.mLastMotionEvent.getDisplayId(),
                                this.mLastMotionEvent.getFlags());
                MotionEvent obtain2 = MotionEvent.obtain(obtain);
                obtain2.setAction(11);
                obtain2.setActionButton(1);
                MotionEvent obtain3 = MotionEvent.obtain(obtain);
                obtain3.setAction(12);
                obtain3.setActionButton(1);
                obtain3.setButtonState(0);
                MotionEvent obtain4 = MotionEvent.obtain(obtain);
                obtain4.setAction(1);
                obtain4.setButtonState(0);
                AutoclickController.super.onMotionEvent(obtain, obtain, this.mEventPolicyFlags);
                obtain.recycle();
                AutoclickController.super.onMotionEvent(obtain2, obtain2, this.mEventPolicyFlags);
                obtain2.recycle();
                AutoclickController.super.onMotionEvent(obtain3, obtain3, this.mEventPolicyFlags);
                obtain3.recycle();
                AutoclickController.super.onMotionEvent(obtain4, obtain4, this.mEventPolicyFlags);
                obtain4.recycle();
            }
            resetInternalState();
        }

        public final String toString() {
            return "ClickScheduler: { active="
                    + this.mActive
                    + ", delay="
                    + this.mDelay
                    + ", scheduledClickTime="
                    + this.mScheduledClickTime
                    + ", anchor={x:"
                    + this.mAnchorCoords.x
                    + ", y:"
                    + this.mAnchorCoords.y
                    + "}, metastate="
                    + this.mMetaState
                    + ", policyFlags="
                    + this.mEventPolicyFlags
                    + ", lastMotionEvent="
                    + this.mLastMotionEvent
                    + " }";
        }
    }

    public AutoclickController(
            Context context, int i, AccessibilityTraceManager accessibilityTraceManager) {
        this.mTrace = accessibilityTraceManager;
        this.mContext = context;
        this.mUserId = i;
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public final void clearEvents(int i) {
        ClickScheduler clickScheduler;
        if (i == 8194 && (clickScheduler = this.mClickScheduler) != null) {
            clickScheduler.cancel();
        }
        super.clearEvents(i);
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public final void onDestroy() {
        ClickDelayObserver clickDelayObserver = this.mClickDelayObserver;
        if (clickDelayObserver != null) {
            ContentResolver contentResolver = clickDelayObserver.mContentResolver;
            if (contentResolver == null || clickDelayObserver.mClickScheduler == null) {
                throw new IllegalStateException("ClickDelayObserver not started.");
            }
            contentResolver.unregisterContentObserver(clickDelayObserver);
            this.mClickDelayObserver = null;
        }
        ClickScheduler clickScheduler = this.mClickScheduler;
        if (clickScheduler != null) {
            clickScheduler.cancel();
            this.mClickScheduler = null;
        }
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public final void onKeyEvent(KeyEvent keyEvent, int i) {
        AccessibilityTraceManager accessibilityTraceManager = this.mTrace;
        if (accessibilityTraceManager.isA11yTracingEnabledForTypes(4096L)) {
            accessibilityTraceManager.logTrace(
                    "AutoclickController.onKeyEvent",
                    4096L,
                    "event=" + keyEvent + ";policyFlags=" + i);
        }
        if (this.mClickScheduler != null) {
            if (KeyEvent.isModifierKey(keyEvent.getKeyCode())) {
                this.mClickScheduler.mMetaState = keyEvent.getMetaState();
            } else {
                this.mClickScheduler.cancel();
            }
        }
        super.onKeyEvent(keyEvent, i);
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x00ef  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00fc  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0109  */
    @Override // com.android.server.accessibility.EventStreamTransformation
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onMotionEvent(
            android.view.MotionEvent r10, android.view.MotionEvent r11, int r12) {
        /*
            Method dump skipped, instructions count: 320
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.accessibility.AutoclickController.onMotionEvent(android.view.MotionEvent,"
                    + " android.view.MotionEvent, int):void");
    }
}
