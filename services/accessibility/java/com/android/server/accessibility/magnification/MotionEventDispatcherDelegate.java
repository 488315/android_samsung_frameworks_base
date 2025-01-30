package com.android.server.accessibility.magnification;

import android.R;
import android.content.Context;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class MotionEventDispatcherDelegate {
    public static final boolean DBG = Log.isLoggable(MotionEventDispatcherDelegate.class.getSimpleName(), 3);
    public static final String TAG = "MotionEventDispatcherDelegate";
    public final EventDispatcher mEventDispatcher;
    public long mLastDelegatedDownEventTime;
    public final int mMultiTapMaxDelay;

    public interface EventDispatcher {
        void dispatchMotionEvent(MotionEvent motionEvent, MotionEvent motionEvent2, int i);
    }

    public MotionEventDispatcherDelegate(Context context, EventDispatcher eventDispatcher) {
        this.mEventDispatcher = eventDispatcher;
        this.mMultiTapMaxDelay = ViewConfiguration.getDoubleTapTimeout() + context.getResources().getInteger(R.integer.config_screen_rotation_total_90);
    }

    public void sendDelayedMotionEvents(List list, long j) {
        if (list == null) {
            return;
        }
        long min = Math.min(SystemClock.uptimeMillis() - j, this.mMultiTapMaxDelay);
        Iterator it = list.iterator();
        while (it.hasNext()) {
            MotionEventInfo motionEventInfo = (MotionEventInfo) it.next();
            MotionEvent motionEvent = motionEventInfo.mEvent;
            motionEvent.setDownTime(motionEvent.getDownTime() + min);
            dispatchMotionEvent(motionEventInfo.mEvent, motionEventInfo.mRawEvent, motionEventInfo.mPolicyFlags);
            motionEventInfo.recycle();
        }
    }

    public void dispatchMotionEvent(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
        if (motionEvent.getActionMasked() == 0) {
            this.mLastDelegatedDownEventTime = motionEvent.getDownTime();
            if (DBG) {
                Log.d(TAG, "dispatchMotionEvent mLastDelegatedDownEventTime time = " + this.mLastDelegatedDownEventTime);
            }
        }
        if (DBG) {
            Log.d(TAG, "dispatchMotionEvent original down time = " + motionEvent.getDownTime());
        }
        motionEvent.setDownTime(this.mLastDelegatedDownEventTime);
        this.mEventDispatcher.dispatchMotionEvent(motionEvent, motionEvent2, i);
    }
}
