package com.android.server.wm;

import java.util.function.Predicate;

public final /* synthetic */ class TaskFragment$$ExternalSyntheticLambda6 implements Predicate {
    public final /* synthetic */ TaskFragment f$0;

    public /* synthetic */ TaskFragment$$ExternalSyntheticLambda6(TaskFragment taskFragment) {
        this.f$0 = taskFragment;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        TaskFragment taskFragment = this.f$0;
        return !taskFragment.isAllowedToEmbedActivityInTrustedMode(
                taskFragment.mTaskFragmentOrganizerUid, (ActivityRecord) obj);
    }
}
