package com.android.wm.shell.freeform;

import android.animation.Animator;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class FreeformTaskTransitionHandler$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ArrayList f$0;

    public /* synthetic */ FreeformTaskTransitionHandler$$ExternalSyntheticLambda0(ArrayList arrayList, int i) {
        this.$r8$classId = i;
        this.f$0 = arrayList;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        ArrayList arrayList = this.f$0;
        switch (i) {
            case 0:
                int size = arrayList.size();
                int i2 = 0;
                while (i2 < size) {
                    Object obj = arrayList.get(i2);
                    i2++;
                    ((Animator) obj).end();
                }
                break;
            default:
                int size2 = arrayList.size();
                int i3 = 0;
                while (i3 < size2) {
                    Object obj2 = arrayList.get(i3);
                    i3++;
                    ((Animator) obj2).start();
                }
                break;
        }
    }
}
