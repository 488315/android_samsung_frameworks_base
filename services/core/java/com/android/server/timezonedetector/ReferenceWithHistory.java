package com.android.server.timezonedetector;

import android.os.SystemClock;
import android.os.TimestampedValue;
import android.util.IndentingPrintWriter;
import java.time.Duration;
import java.util.ArrayDeque;
import java.util.Iterator;

/* loaded from: classes3.dex */
public final class ReferenceWithHistory {
    public final int mMaxHistorySize;
    public int mSetCount;
    public ArrayDeque mValues;

    public ReferenceWithHistory(int i) {
        if (i < 1) {
            throw new IllegalArgumentException("maxHistorySize < 1: " + i);
        }
        this.mMaxHistorySize = i;
    }

    public Object get() {
        ArrayDeque arrayDeque = this.mValues;
        if (arrayDeque == null || arrayDeque.isEmpty()) {
            return null;
        }
        return ((TimestampedValue) this.mValues.getFirst()).getValue();
    }

    public Object set(Object obj) {
        if (this.mValues == null) {
            this.mValues = new ArrayDeque(this.mMaxHistorySize);
        }
        if (this.mValues.size() >= this.mMaxHistorySize) {
            this.mValues.removeLast();
        }
        Object obj2 = get();
        this.mValues.addFirst(new TimestampedValue(SystemClock.elapsedRealtime(), obj));
        this.mSetCount++;
        return obj2;
    }

    public void dump(IndentingPrintWriter indentingPrintWriter) {
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
                indentingPrintWriter.print(Duration.ofMillis(timestampedValue.getReferenceTimeMillis()).toString());
                indentingPrintWriter.print(": ");
                indentingPrintWriter.println(timestampedValue.getValue());
                size++;
            }
        }
        indentingPrintWriter.flush();
    }

    public int getHistoryCount() {
        ArrayDeque arrayDeque = this.mValues;
        if (arrayDeque == null) {
            return 0;
        }
        return arrayDeque.size();
    }

    public String toString() {
        return String.valueOf(get());
    }
}
