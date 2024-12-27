package com.android.server.wm;

import com.android.window.flags.Flags;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class TransitionController$$ExternalSyntheticLambda2 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Transition f$0;

    public /* synthetic */ TransitionController$$ExternalSyntheticLambda2(Transition transition, int i) {
        this.$r8$classId = i;
        this.f$0 = transition;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Transition transition = this.f$0;
        switch (i) {
            case 0:
                Task task = (Task) obj;
                if (task.isVisible()) {
                    transition.collect(task, false);
                    if (CoreRune.MW_EMBED_ACTIVITY_ANIMATION && task.inFreeformWindowingMode()) {
                        ArrayList arrayList = new ArrayList();
                        for (int size = task.mChildren.size() - 1; size >= 0; size--) {
                            WindowContainer windowContainer = (WindowContainer) task.mChildren.get(size);
                            if (windowContainer.asTaskFragment() != null && windowContainer.isEmbedded()) {
                                arrayList.add((TaskFragment) windowContainer);
                            }
                        }
                        if (!arrayList.isEmpty()) {
                        }
                    }
                    if (CoreRune.FW_SHELL_TRANSITION_BUG_FIX && Flags.removePrepareSurfaceInPlacement()) {
                        task.forAllActivities(new TransitionController$$ExternalSyntheticLambda2(transition, 1));
                        break;
                    }
                }
                break;
            default:
                ActivityRecord activityRecord = (ActivityRecord) obj;
                if (activityRecord.isVisibleRequested() && activityRecord.mVisible) {
                    transition.collect(activityRecord, false);
                    break;
                }
                break;
        }
    }
}
