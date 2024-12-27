package com.android.server.wm;

import java.util.function.Consumer;

public final /* synthetic */ class TaskFragment$$ExternalSyntheticLambda8 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ TaskFragment$$ExternalSyntheticLambda8(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                int[] iArr = (int[]) obj2;
                if (!((ActivityRecord) obj).finishing) {
                    iArr[0] = iArr[0] + 1;
                    break;
                }
                break;
            default:
                TaskFragment taskFragment = (TaskFragment) obj2;
                TaskFragment taskFragment2 = (TaskFragment) obj;
                taskFragment.getClass();
                if (taskFragment2.mCompanionTaskFragment == taskFragment) {
                    taskFragment2.mCompanionTaskFragment = null;
                    break;
                }
                break;
        }
    }
}
