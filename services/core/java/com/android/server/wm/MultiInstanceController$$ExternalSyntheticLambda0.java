package com.android.server.wm;

import java.util.Comparator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class MultiInstanceController$$ExternalSyntheticLambda0
        implements Comparator {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ MultiInstanceController$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        Task task = (Task) obj;
        Task task2 = (Task) obj2;
        switch (this.$r8$classId) {
        }
        return Long.compare(task2.lastGainFocusTime, task.lastGainFocusTime);
    }
}
