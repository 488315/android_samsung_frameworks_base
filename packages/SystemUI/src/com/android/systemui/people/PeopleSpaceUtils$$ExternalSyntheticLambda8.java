package com.android.systemui.people;

import android.app.people.PeopleSpaceTile;
import com.android.systemui.people.widget.PeopleTileKey;
import java.util.Comparator;

/* loaded from: classes2.dex */
public final /* synthetic */ class PeopleSpaceUtils$$ExternalSyntheticLambda8 implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        PeopleTileKey peopleTileKey = PeopleSpaceUtils.EMPTY_KEY;
        return new Long(((PeopleSpaceTile) obj2).getLastInteractionTimestamp()).compareTo(new Long(((PeopleSpaceTile) obj).getLastInteractionTimestamp()));
    }
}
