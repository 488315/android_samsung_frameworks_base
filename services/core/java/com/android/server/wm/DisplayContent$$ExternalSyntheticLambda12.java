package com.android.server.wm;

import java.util.ArrayList;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class DisplayContent$$ExternalSyntheticLambda12 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int[] f$0;
    public final /* synthetic */ ArrayList f$1;

    public /* synthetic */ DisplayContent$$ExternalSyntheticLambda12(int[] iArr, ArrayList arrayList, int i) {
        this.$r8$classId = i;
        this.f$0 = iArr;
        this.f$1 = arrayList;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                int[] iArr = this.f$0;
                ArrayList arrayList = this.f$1;
                Task task = (Task) obj;
                for (int i : iArr) {
                    if (!task.mCreatedByOrganizer && task.getWindowingMode() == i && task.isActivityTypeStandardOrUndefined()) {
                        arrayList.add(task);
                    }
                }
                break;
            default:
                int[] iArr2 = this.f$0;
                ArrayList arrayList2 = this.f$1;
                Task task2 = (Task) obj;
                for (int i2 : iArr2) {
                    if (task2.mCreatedByOrganizer) {
                        for (int childCount = task2.getChildCount() - 1; childCount >= 0; childCount--) {
                            Task task3 = (Task) task2.getChildAt(childCount);
                            if (task3.getActivityType() == i2) {
                                arrayList2.add(task3);
                            }
                        }
                    } else if (task2.getActivityType() == i2) {
                        arrayList2.add(task2);
                    }
                }
                break;
        }
    }
}
