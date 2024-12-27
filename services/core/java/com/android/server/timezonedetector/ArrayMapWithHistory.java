package com.android.server.timezonedetector;

import android.util.ArrayMap;
import android.util.IndentingPrintWriter;
import android.util.Log;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;

import java.util.ArrayDeque;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class ArrayMapWithHistory {
    public ArrayMap mMap;

    public final void dump(IndentingPrintWriter indentingPrintWriter) {
        if (this.mMap == null) {
            indentingPrintWriter.println("{Empty}");
        } else {
            for (int i = 0; i < this.mMap.size(); i++) {
                StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i, "key idx: ", "=");
                m.append(this.mMap.keyAt(i));
                indentingPrintWriter.println(m.toString());
                ReferenceWithHistory referenceWithHistory =
                        (ReferenceWithHistory) this.mMap.valueAt(i);
                indentingPrintWriter.println("val idx: " + i + "=" + referenceWithHistory);
                indentingPrintWriter.increaseIndent();
                indentingPrintWriter.println("Historic values=[");
                indentingPrintWriter.increaseIndent();
                referenceWithHistory.dump(indentingPrintWriter);
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println("]");
                indentingPrintWriter.decreaseIndent();
            }
        }
        indentingPrintWriter.flush();
    }

    public final Object get(Object obj) {
        ReferenceWithHistory referenceWithHistory;
        ArrayMap arrayMap = this.mMap;
        if (arrayMap == null
                || (referenceWithHistory = (ReferenceWithHistory) arrayMap.get(obj)) == null) {
            return null;
        }
        ArrayDeque arrayDeque = referenceWithHistory.mValues;
        if ((arrayDeque == null ? 0 : arrayDeque.size()) == 0) {
            Log.w("ArrayMapWithHistory", "History for \"" + obj + "\" was unexpectedly empty");
        }
        return referenceWithHistory.get();
    }

    public int getHistoryCountForKeyForTests(Object obj) {
        ReferenceWithHistory referenceWithHistory;
        ArrayMap arrayMap = this.mMap;
        if (arrayMap == null
                || (referenceWithHistory = (ReferenceWithHistory) arrayMap.get(obj)) == null) {
            return 0;
        }
        ArrayDeque arrayDeque = referenceWithHistory.mValues;
        if ((arrayDeque == null ? 0 : arrayDeque.size()) != 0) {
            ArrayDeque arrayDeque2 = referenceWithHistory.mValues;
            if (arrayDeque2 == null) {
                return 0;
            }
            return arrayDeque2.size();
        }
        Log.w(
                "ArrayMapWithHistory",
                "getValuesSizeForKeyForTests(\"" + obj + "\") was unexpectedly empty");
        return 0;
    }

    public final void put(Object obj, Object obj2) {
        if (this.mMap == null) {
            this.mMap = new ArrayMap();
        }
        ReferenceWithHistory referenceWithHistory = (ReferenceWithHistory) this.mMap.get(obj);
        if (referenceWithHistory == null) {
            referenceWithHistory = new ReferenceWithHistory(10);
            this.mMap.put(obj, referenceWithHistory);
        } else {
            ArrayDeque arrayDeque = referenceWithHistory.mValues;
            if ((arrayDeque == null ? 0 : arrayDeque.size()) == 0) {
                Log.w("ArrayMapWithHistory", "History for \"" + obj + "\" was unexpectedly empty");
            }
        }
        referenceWithHistory.set(obj2);
    }

    public final String toString() {
        return "ArrayMapWithHistory{mHistorySize=10, mMap=" + this.mMap + '}';
    }

    public final Object valueAt(int i) {
        ArrayMap arrayMap = this.mMap;
        if (arrayMap == null) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        ReferenceWithHistory referenceWithHistory = (ReferenceWithHistory) arrayMap.valueAt(i);
        if (referenceWithHistory != null) {
            ArrayDeque arrayDeque = referenceWithHistory.mValues;
            if ((arrayDeque == null ? 0 : arrayDeque.size()) != 0) {
                return referenceWithHistory.get();
            }
        }
        Log.w("ArrayMapWithHistory", "valueAt(" + i + ") was unexpectedly null or empty");
        return null;
    }
}
