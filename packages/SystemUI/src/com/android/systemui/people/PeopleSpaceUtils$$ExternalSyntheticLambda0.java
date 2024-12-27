package com.android.systemui.people;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class PeopleSpaceUtils$$ExternalSyntheticLambda0 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return ((Set) ((Map.Entry) obj).getValue()).stream();
    }
}
