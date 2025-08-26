package com.android.systemui.people;

import com.android.systemui.people.widget.PeopleTileKey;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/* loaded from: classes2.dex */
public final /* synthetic */ class PeopleSpaceUtils$$ExternalSyntheticLambda0 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        PeopleTileKey peopleTileKey = PeopleSpaceUtils.EMPTY_KEY;
        return ((Set) ((Map.Entry) obj).getValue()).stream();
    }
}
