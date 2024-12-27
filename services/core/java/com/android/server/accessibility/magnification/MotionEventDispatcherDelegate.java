package com.android.server.accessibility.magnification;

import android.R;
import android.content.Context;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class MotionEventDispatcherDelegate {
    public static final boolean DBG = Log.isLoggable("MotionEventDispatcherDelegate", 3);
    public final WindowMagnificationGestureHandler$$ExternalSyntheticLambda1 mEventDispatcher;
    public long mLastDelegatedDownEventTime;
    public final int mMultiTapMaxDelay;

    public MotionEventDispatcherDelegate(
            Context context,
            WindowMagnificationGestureHandler$$ExternalSyntheticLambda1
                    windowMagnificationGestureHandler$$ExternalSyntheticLambda1) {
        this.mEventDispatcher = windowMagnificationGestureHandler$$ExternalSyntheticLambda1;
        this.mMultiTapMaxDelay =
                context.getResources().getInteger(R.integer.config_zen_repeat_callers_threshold)
                        + ViewConfiguration.getDoubleTapTimeout();
    }

    public final void dispatchMotionEvent(
            MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
        int actionMasked = motionEvent.getActionMasked();
        boolean z = DBG;
        if (actionMasked == 0) {
            this.mLastDelegatedDownEventTime = motionEvent.getDownTime();
            if (z) {
                Log.d(
                        "MotionEventDispatcherDelegate",
                        "dispatchMotionEvent mLastDelegatedDownEventTime time = "
                                + this.mLastDelegatedDownEventTime);
            }
        }
        if (z) {
            Log.d(
                    "MotionEventDispatcherDelegate",
                    "dispatchMotionEvent original down time = " + motionEvent.getDownTime());
        }
        motionEvent.setDownTime(this.mLastDelegatedDownEventTime);
        this.mEventDispatcher.f$0.dispatchTransformedEvent(motionEvent, motionEvent2, i);
    }

    public final void sendDelayedMotionEvents(long j, List list) {
        if (list == null) {
            return;
        }
        long min = Math.min(SystemClock.uptimeMillis() - j, this.mMultiTapMaxDelay);
        Iterator it = list.iterator();
        while (it.hasNext()) {
            MotionEventInfo motionEventInfo = (MotionEventInfo) it.next();
            MotionEvent motionEvent = motionEventInfo.mEvent;
            motionEvent.setDownTime(motionEvent.getDownTime() + min);
            dispatchMotionEvent(
                    motionEventInfo.mEvent,
                    motionEventInfo.mRawEvent,
                    motionEventInfo.mPolicyFlags);
            MotionEvent motionEvent2 = motionEventInfo.mEvent;
            if (motionEvent2 != null) {
                motionEvent2.recycle();
            }
            motionEventInfo.mEvent = null;
            MotionEvent motionEvent3 = motionEventInfo.mRawEvent;
            if (motionEvent3 != null) {
                motionEvent3.recycle();
            }
            motionEventInfo.mRawEvent = null;
        }
    }
}
