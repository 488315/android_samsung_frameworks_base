package com.android.server.timezonedetector;

import android.util.ArraySet;

import java.util.function.Function;

public final class OrdinalGenerator {
    public final Function mCanonicalizationFunction;
    public final ArraySet mKnownIds = new ArraySet();

    public OrdinalGenerator(TimeZoneCanonicalizer timeZoneCanonicalizer) {
        this.mCanonicalizationFunction = timeZoneCanonicalizer;
    }

    public final int ordinal(Object obj) {
        Object apply = this.mCanonicalizationFunction.apply(obj);
        int indexOf = this.mKnownIds.indexOf(apply);
        if (indexOf >= 0) {
            return indexOf;
        }
        int size = this.mKnownIds.size();
        this.mKnownIds.add(apply);
        return size;
    }
}
