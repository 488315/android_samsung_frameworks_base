package com.android.server.accessibility.gestures;

import android.accessibilityservice.AccessibilityGestureEvent;
import android.content.Context;
import android.util.Slog;
import android.view.MotionEvent;
import com.android.server.accessibility.AccessibilityManagerService;
import com.android.server.accessibility.gestures.GestureMatcher;
import com.android.server.accessibility.gestures.TouchExplorer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class GestureManifold implements GestureMatcher.StateChangeListener {
    public final List mEvents;
    public final List mGestures;
    public final Listener mListener;
    public final List mMultiFingerGestures;
    public boolean mMultiFingerGesturesEnabled;
    public boolean mSendMotionEventsEnabled;
    public boolean mServiceHandlesDoubleTap;
    public final TouchState mState;
    public boolean mTwoFingerPassthroughEnabled;
    public final List mTwoFingerSwipes;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface Listener {
    }

    public GestureManifold(Context context, Listener listener, TouchState touchState) {
        ArrayList arrayList = new ArrayList();
        this.mGestures = arrayList;
        this.mServiceHandlesDoubleTap = false;
        this.mSendMotionEventsEnabled = false;
        ArrayList arrayList2 = new ArrayList();
        this.mMultiFingerGestures = arrayList2;
        ArrayList arrayList3 = new ArrayList();
        this.mTwoFingerSwipes = arrayList3;
        this.mEvents = new ArrayList();
        this.mListener = listener;
        this.mState = touchState;
        this.mMultiFingerGesturesEnabled = false;
        this.mTwoFingerPassthroughEnabled = false;
        arrayList.add(new MultiTap(context, 2, 17, this));
        arrayList.add(new MultiTapAndHold(context, 2, 18, this));
        arrayList.add(new SecondFingerMultiTap(context, this));
        arrayList.add(new Swipe(context, 1, 4, this));
        arrayList.add(new Swipe(context, 0, 3, this));
        arrayList.add(new Swipe(context, 2, 1, this));
        arrayList.add(new Swipe(context, 3, 2, this));
        arrayList.add(new Swipe(context, 0, 1, 5, this));
        arrayList.add(new Swipe(context, 0, 2, 9, this));
        arrayList.add(new Swipe(context, 0, 3, 10, this));
        arrayList.add(new Swipe(context, 1, 2, 11, this));
        arrayList.add(new Swipe(context, 1, 3, 12, this));
        arrayList.add(new Swipe(context, 1, 0, 6, this));
        arrayList.add(new Swipe(context, 3, 2, 8, this));
        arrayList.add(new Swipe(context, 3, 0, 15, this));
        arrayList.add(new Swipe(context, 3, 1, 16, this));
        arrayList.add(new Swipe(context, 2, 3, 7, this));
        arrayList.add(new Swipe(context, 2, 0, 13, this));
        arrayList.add(new Swipe(context, 2, 1, 14, this));
        arrayList2.add(new MultiFingerMultiTap(context, 2, 1, 19, this));
        arrayList2.add(new MultiFingerMultiTap(context, 2, 2, 20, this));
        arrayList2.add(new MultiFingerMultiTapAndHold(context, 2, 2, 40, this));
        arrayList2.add(new MultiFingerMultiTap(context, 2, 3, 21, this));
        arrayList2.add(new MultiFingerMultiTapAndHold(context, 2, 3, 43, this));
        arrayList2.add(new MultiFingerMultiTap(context, 3, 1, 22, this));
        arrayList2.add(new MultiFingerMultiTap(context, 3, 2, 23, this));
        arrayList2.add(new MultiFingerMultiTapAndHold(context, 3, 1, 44, this));
        arrayList2.add(new MultiFingerMultiTapAndHold(context, 3, 2, 41, this));
        arrayList2.add(new MultiFingerMultiTap(context, 3, 3, 24, this));
        arrayList2.add(new MultiFingerMultiTapAndHold(context, 3, 3, 45, this));
        arrayList2.add(new MultiFingerMultiTap(context, 3, 3, 24, this));
        arrayList2.add(new MultiFingerMultiTap(context, 4, 1, 37, this));
        arrayList2.add(new MultiFingerMultiTap(context, 4, 2, 38, this));
        arrayList2.add(new MultiFingerMultiTapAndHold(context, 4, 2, 42, this));
        arrayList2.add(new MultiFingerMultiTap(context, 4, 3, 39, this));
        arrayList3.add(new MultiFingerSwipe(context, 2, 3, 26, this));
        arrayList3.add(new MultiFingerSwipe(context, 2, 0, 27, this));
        arrayList3.add(new MultiFingerSwipe(context, 2, 1, 28, this));
        arrayList3.add(new MultiFingerSwipe(context, 2, 2, 25, this));
        arrayList2.addAll(arrayList3);
        arrayList2.add(new MultiFingerSwipe(context, 3, 3, 30, this));
        arrayList2.add(new MultiFingerSwipe(context, 3, 0, 31, this));
        arrayList2.add(new MultiFingerSwipe(context, 3, 1, 32, this));
        arrayList2.add(new MultiFingerSwipe(context, 3, 2, 29, this));
        arrayList2.add(new MultiFingerSwipe(context, 4, 3, 34, this));
        arrayList2.add(new MultiFingerSwipe(context, 4, 0, 35, this));
        arrayList2.add(new MultiFingerSwipe(context, 4, 1, 36, this));
        arrayList2.add(new MultiFingerSwipe(context, 4, 2, 33, this));
        arrayList2.add(new SemMultiFingerMultiTapAndHold(context, 2, 1, 1000, this));
    }

    public final void clear() {
        Iterator it = ((ArrayList) this.mGestures).iterator();
        while (it.hasNext()) {
            ((GestureMatcher) it.next()).clear();
        }
        if (this.mEvents != null) {
            while (((ArrayList) this.mEvents).size() > 0) {
                ((MotionEvent) ((ArrayList) this.mEvents).remove(0)).recycle();
            }
        }
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher.StateChangeListener
    public final void onStateChanged(int i, int i2, int i3, MotionEvent motionEvent, MotionEvent motionEvent2) {
        TouchState touchState = this.mState;
        Listener listener = this.mListener;
        if (i2 == 1 && touchState.mState != 5) {
            if (i != 17 && i != 18) {
                ((TouchExplorer) listener).onGestureStarted();
                return;
            } else {
                if (this.mServiceHandlesDoubleTap) {
                    ((TouchExplorer) listener).onGestureStarted();
                    return;
                }
                return;
            }
        }
        if (i2 == 2) {
            if (i != 17) {
                if (i != 18) {
                    ((TouchExplorer) listener).onGestureCompleted(new AccessibilityGestureEvent(i, motionEvent.getDisplayId(), this.mEvents));
                } else if (this.mServiceHandlesDoubleTap) {
                    ((TouchExplorer) listener).onGestureCompleted(new AccessibilityGestureEvent(i, motionEvent.getDisplayId(), this.mEvents));
                } else {
                    ((TouchExplorer) listener).onDoubleTapAndHold(motionEvent, motionEvent2, i3);
                }
            } else if (this.mServiceHandlesDoubleTap) {
                ((TouchExplorer) listener).onGestureCompleted(new AccessibilityGestureEvent(i, motionEvent.getDisplayId(), this.mEvents));
            } else {
                ((TouchExplorer) listener).onDoubleTap(motionEvent, motionEvent2, i3);
            }
            clear();
            return;
        }
        if (i2 == 3 && touchState.mState == 5) {
            Iterator it = ((ArrayList) this.mGestures).iterator();
            while (it.hasNext()) {
                if (((GestureMatcher) it.next()).mState == 1) {
                    return;
                }
            }
            if (TouchExplorer.DEBUG) {
                Slog.d("GestureManifold", "Cancelling.");
            }
            TouchExplorer touchExplorer = (TouchExplorer) listener;
            AccessibilityManagerService accessibilityManagerService = touchExplorer.mAms;
            if (accessibilityManagerService.mTraceManager.isA11yTracingEnabledForTypes(12288L)) {
                accessibilityManagerService.mTraceManager.logTrace("TouchExplorer.onGestureCancelled", 12288L, "event=" + motionEvent + ";rawEvent=" + motionEvent2 + ";policyFlags=" + i3);
            }
            TouchState touchState2 = touchExplorer.mState;
            if (touchState2.mState == 5) {
                boolean z = motionEvent.getActionMasked() == 1;
                touchExplorer.mAms.onTouchInteractionEnd();
                EventDispatcher eventDispatcher = touchExplorer.mDispatcher;
                eventDispatcher.sendAccessibilityEvent(524288);
                if (z) {
                    eventDispatcher.sendAccessibilityEvent(2097152);
                }
                TouchExplorer.ExitGestureDetectionModeDelayed exitGestureDetectionModeDelayed = touchExplorer.mExitGestureDetectionModeDelayed;
                TouchExplorer.this.mHandler.removeCallbacks(exitGestureDetectionModeDelayed);
                return;
            }
            if (!touchState2.isTouchExploring() || motionEvent.getActionMasked() != 2) {
                GestureManifold gestureManifold = touchExplorer.mGestureDetector;
                if (gestureManifold.mSendMotionEventsEnabled) {
                    touchExplorer.dispatchGesture(new AccessibilityGestureEvent(0, touchExplorer.mDisplayId, gestureManifold.mEvents));
                    return;
                }
                return;
            }
            int primaryPointerId = 1 << touchExplorer.mReceivedPointerTracker.getPrimaryPointerId();
            MotionEvent motionEvent3 = touchState2.mLastReceivedEvent;
            TouchExplorer.SendHoverExitDelayed sendHoverExitDelayed = touchExplorer.mSendHoverEnterAndMoveDelayed;
            sendHoverExitDelayed.addEvent(motionEvent, motionEvent3);
            if (sendHoverExitDelayed.isPending()) {
                sendHoverExitDelayed.run();
                sendHoverExitDelayed.cancel();
            }
            touchExplorer.mSendHoverExitDelayed.cancel();
            touchExplorer.mDispatcher.sendMotionEvent(7, primaryPointerId, i3, motionEvent, motionEvent);
        }
    }
}
