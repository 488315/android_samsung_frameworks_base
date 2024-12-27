package com.android.server.accessibility.magnification;

import com.android.server.accessibility.gestures.GestureMatcher;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class GesturesObserver implements GestureMatcher.StateChangeListener {
    public final Listener mListener;
    public final List mGestureMatchers = new ArrayList();
    public boolean mObserveStarted = false;
    public boolean mProcessMotionEvent = false;
    public int mCancelledMatcherSize = 0;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface Listener {}

    public GesturesObserver(Listener listener, GestureMatcher... gestureMatcherArr) {
        this.mListener = listener;
        for (GestureMatcher gestureMatcher : gestureMatcherArr) {
            gestureMatcher.mListener = this;
            ((ArrayList) this.mGestureMatchers).add(gestureMatcher);
        }
    }

    public final void clear$1() {
        Iterator it = ((ArrayList) this.mGestureMatchers).iterator();
        while (it.hasNext()) {
            ((GestureMatcher) it.next()).clear();
        }
        this.mCancelledMatcherSize = 0;
        this.mObserveStarted = false;
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0115  */
    /* JADX WARN: Removed duplicated region for block: B:25:? A[RETURN, SYNTHETIC] */
    @Override // com.android.server.accessibility.gestures.GestureMatcher.StateChangeListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onStateChanged(
            int r17, int r18, int r19, android.view.MotionEvent r20, android.view.MotionEvent r21) {
        /*
            Method dump skipped, instructions count: 338
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.accessibility.magnification.GesturesObserver.onStateChanged(int,"
                    + " int, int, android.view.MotionEvent, android.view.MotionEvent):void");
    }
}
