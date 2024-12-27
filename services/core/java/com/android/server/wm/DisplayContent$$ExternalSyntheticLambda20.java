package com.android.server.wm;

import android.graphics.Rect;
import java.util.Set;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class DisplayContent$$ExternalSyntheticLambda20 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ DisplayContent$$ExternalSyntheticLambda20(int i, Object obj) {
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
                iArr[0] = iArr[0] + 1;
                break;
            case 1:
                ((Set) obj2).add((Rect) obj);
                break;
            default:
                boolean[] zArr = (boolean[]) obj2;
                ActivityRecord activityRecord = (ActivityRecord) obj;
                if (activityRecord.mImeInsetsFrozenUntilStartInput && activityRecord.isVisibleRequested()) {
                    activityRecord.mImeInsetsFrozenUntilStartInput = false;
                    zArr[0] = true;
                    break;
                }
                break;
        }
    }
}
