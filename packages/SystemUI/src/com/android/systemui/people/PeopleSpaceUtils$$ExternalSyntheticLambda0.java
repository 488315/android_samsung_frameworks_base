package com.android.systemui.people;

import com.android.systemui.people.widget.PeopleTileKey;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class PeopleSpaceUtils$$ExternalSyntheticLambda0 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        PeopleTileKey peopleTileKey = PeopleSpaceUtils.EMPTY_KEY;
        return ((Set) ((Map.Entry) obj).getValue()).stream();
    }
}
