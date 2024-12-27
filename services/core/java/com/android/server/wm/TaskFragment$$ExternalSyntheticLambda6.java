package com.android.server.wm;

import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class TaskFragment$$ExternalSyntheticLambda6 implements Predicate {
    public final /* synthetic */ TaskFragment f$0;

    public /* synthetic */ TaskFragment$$ExternalSyntheticLambda6(TaskFragment taskFragment) {
        this.f$0 = taskFragment;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        TaskFragment taskFragment = this.f$0;
        return !taskFragment.isAllowedToEmbedActivityInTrustedMode(taskFragment.mTaskFragmentOrganizerUid, (ActivityRecord) obj);
    }
}
