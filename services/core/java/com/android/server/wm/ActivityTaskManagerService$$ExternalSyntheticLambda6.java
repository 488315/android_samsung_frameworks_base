package com.android.server.wm;

import com.samsung.android.rune.CoreRune;

import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class ActivityTaskManagerService$$ExternalSyntheticLambda6
        implements Predicate {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ ActivityTaskManagerService$$ExternalSyntheticLambda6(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        Task task = (Task) obj;
        switch (this.$r8$classId) {
            case 0:
                return task.isLeafTask()
                        && task.isTopActivityFocusable()
                        && !task.isFreeformForceHidden()
                        && !(CoreRune.MW_CAPTION_SHELL_FREEFORM_PINNING
                                && task.isFreeformPinned()
                                && task.isMinimized());
            default:
                return task.isActivityTypeStandard();
        }
    }
}
