package com.android.server.timezonedetector;

import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.os.SystemClock;
import android.os.TimestampedValue;
import android.util.IndentingPrintWriter;

import java.time.Duration;
import java.util.ArrayDeque;
import java.util.Iterator;

public final class ReferenceWithHistory {
    public final int mMaxHistorySize;
    public int mSetCount;
    public ArrayDeque mValues;

    public ReferenceWithHistory(int i) {
        if (i < 1) {
            throw new IllegalArgumentException(
                    VibrationParam$1$$ExternalSyntheticOutline0.m(i, "maxHistorySize < 1: "));
        }
        this.mMaxHistorySize = i;
    }

    public final void dump(IndentingPrintWriter indentingPrintWriter) {
        ArrayDeque arrayDeque = this.mValues;
        if (arrayDeque == null) {
            indentingPrintWriter.println("{Empty}");
        } else {
            int size = this.mSetCount - arrayDeque.size();
            Iterator descendingIterator = this.mValues.descendingIterator();
            while (descendingIterator.hasNext()) {
                TimestampedValue timestampedValue = (TimestampedValue) descendingIterator.next();
                indentingPrintWriter.print(size);
                indentingPrintWriter.print("@");
                indentingPrintWriter.print(
                        Duration.ofMillis(timestampedValue.getReferenceTimeMillis()).toString());
                indentingPrintWriter.print(": ");
                indentingPrintWriter.println(timestampedValue.getValue());
                size++;
            }
        }
        indentingPrintWriter.flush();
    }

    public final Object get() {
        ArrayDeque arrayDeque = this.mValues;
        if (arrayDeque == null || arrayDeque.isEmpty()) {
            return null;
        }
        return ((TimestampedValue) this.mValues.getFirst()).getValue();
    }

    public final Object set(Object obj) {
        ArrayDeque arrayDeque = this.mValues;
        int i = this.mMaxHistorySize;
        if (arrayDeque == null) {
            this.mValues = new ArrayDeque(i);
        }
        if (this.mValues.size() >= i) {
            this.mValues.removeLast();
        }
        Object obj2 = get();
        this.mValues.addFirst(new TimestampedValue(SystemClock.elapsedRealtime(), obj));
        this.mSetCount++;
        return obj2;
    }

    public final String toString() {
        return String.valueOf(get());
    }
}
