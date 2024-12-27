package com.android.server.accessibility;

import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.accessibility.AccessibilityEvent;

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

    default void onDestroy() {}

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
