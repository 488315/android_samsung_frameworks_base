package com.android.server.timezonedetector;

import android.util.ArraySet;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/* loaded from: classes3.dex */
public class OrdinalGenerator {
    public final Function mCanonicalizationFunction;
    public final ArraySet mKnownIds = new ArraySet();

    public OrdinalGenerator(Function function) {
        Objects.requireNonNull(function);
        this.mCanonicalizationFunction = function;
    }

    public int ordinal(Object obj) {
        Object apply = this.mCanonicalizationFunction.apply(obj);
        int indexOf = this.mKnownIds.indexOf(apply);
        if (indexOf >= 0) {
            return indexOf;
        }
        int size = this.mKnownIds.size();
        this.mKnownIds.add(apply);
        return size;
    }

    public int[] ordinals(List list) {
        int size = list.size();
        int[] iArr = new int[size];
        for (int i = 0; i < size; i++) {
            iArr[i] = ordinal(list.get(i));
        }
        return iArr;
    }
}
