package com.android.server.wm;

import java.util.function.Predicate;

public final /* synthetic */
class TaskFragmentOrganizerController$TaskFragmentOrganizerState$$ExternalSyntheticLambda0
        implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return !((ActivityRecord) obj).isEmbedded();
    }
}
