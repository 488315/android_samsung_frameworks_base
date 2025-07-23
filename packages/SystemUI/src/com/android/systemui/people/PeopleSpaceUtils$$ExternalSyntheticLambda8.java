package com.android.systemui.people;

import android.app.people.PeopleSpaceTile;
import com.android.systemui.people.widget.PeopleTileKey;
import java.util.Comparator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class PeopleSpaceUtils$$ExternalSyntheticLambda8 implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        PeopleTileKey peopleTileKey = PeopleSpaceUtils.EMPTY_KEY;
        return new Long(((PeopleSpaceTile) obj2).getLastInteractionTimestamp()).compareTo(new Long(((PeopleSpaceTile) obj).getLastInteractionTimestamp()));
    }
}
