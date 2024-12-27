package com.android.server.accessibility;

import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.accessibility.AccessibilityEvent;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public interface EventStreamTransformation {
    default void clearEvents(int i) {
        EventStreamTransformation next = getNext();
        if (next != null) {
            next.clearEvents(i);
        }
    }

    EventStreamTransformation getNext();

    default void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        EventStreamTransformation next = getNext();
        if (next != null) {
            next.onAccessibilityEvent(accessibilityEvent);
        }
    }

    default void onDestroy() {
    }

    default void onKeyEvent(KeyEvent keyEvent, int i) {
        EventStreamTransformation next = getNext();
        if (next != null) {
            next.onKeyEvent(keyEvent, i);
        }
    }

    default void onMotionEvent(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
        EventStreamTransformation next = getNext();
        if (next != null) {
            next.onMotionEvent(motionEvent, motionEvent2, i);
        }
    }

    void setNext(EventStreamTransformation eventStreamTransformation);
}
