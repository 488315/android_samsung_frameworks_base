package com.android.server.wm;

import java.util.ArrayList;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class TaskDisplayArea$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ TaskDisplayArea$$ExternalSyntheticLambda0(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                ArrayList arrayList = (ArrayList) obj2;
                Task task = (Task) obj;
                if (task.isLeafTask() && task.isVisible()) {
                    arrayList.add(task);
                    break;
                }
                break;
            case 1:
                boolean[] zArr = (boolean[]) obj2;
                zArr[0] = ((Task) obj).needsZBoost() | zArr[0];
                break;
            default:
                int[] iArr = (int[]) obj2;
                iArr[0] = iArr[0] + 1;
                break;
        }
    }
}
