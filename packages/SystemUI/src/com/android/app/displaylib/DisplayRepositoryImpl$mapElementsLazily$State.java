package com.android.app.displaylib;

import java.util.Map;
import java.util.Set;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class DisplayRepositoryImpl$mapElementsLazily$State {
    public final Set previousSet;
    public final Set resultSet;
    public final Map valueMap;

    public DisplayRepositoryImpl$mapElementsLazily$State(Set<Object> set, Map<Object, Object> map, Set<Object> set2) {
        this.previousSet = set;
        this.valueMap = map;
        this.resultSet = set2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DisplayRepositoryImpl$mapElementsLazily$State)) {
            return false;
        }
        DisplayRepositoryImpl$mapElementsLazily$State displayRepositoryImpl$mapElementsLazily$State = (DisplayRepositoryImpl$mapElementsLazily$State) obj;
        return Intrinsics.areEqual(this.previousSet, displayRepositoryImpl$mapElementsLazily$State.previousSet) && Intrinsics.areEqual(this.valueMap, displayRepositoryImpl$mapElementsLazily$State.valueMap) && Intrinsics.areEqual(this.resultSet, displayRepositoryImpl$mapElementsLazily$State.resultSet);
    }

    public final int hashCode() {
        return this.resultSet.hashCode() + ((this.valueMap.hashCode() + (this.previousSet.hashCode() * 31)) * 31);
    }

    public final String toString() {
        return "State(previousSet=" + this.previousSet + ", valueMap=" + this.valueMap + ", resultSet=" + this.resultSet + ")";
    }
}
