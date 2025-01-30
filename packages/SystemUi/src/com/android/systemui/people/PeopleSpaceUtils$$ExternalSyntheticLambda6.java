package com.android.systemui.people;

import android.app.people.PeopleSpaceTile;
import java.util.Comparator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class PeopleSpaceUtils$$ExternalSyntheticLambda6 implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        return new Long(((PeopleSpaceTile) obj2).getLastInteractionTimestamp()).compareTo(new Long(((PeopleSpaceTile) obj).getLastInteractionTimestamp()));
    }
}
