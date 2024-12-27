package com.android.systemui.people;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public final /* synthetic */ class PeopleSpaceUtils$$ExternalSyntheticLambda0 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return ((Set) ((Map.Entry) obj).getValue()).stream();
    }
}
