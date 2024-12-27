package com.android.systemui.people;

import android.app.people.PeopleSpaceTile;
import java.util.Comparator;

public final /* synthetic */ class PeopleSpaceUtils$$ExternalSyntheticLambda8 implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        return new Long(((PeopleSpaceTile) obj2).getLastInteractionTimestamp()).compareTo(new Long(((PeopleSpaceTile) obj).getLastInteractionTimestamp()));
    }
}
