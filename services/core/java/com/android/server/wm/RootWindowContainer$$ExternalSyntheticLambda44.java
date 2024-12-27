package com.android.server.wm;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class RootWindowContainer$$ExternalSyntheticLambda44
        implements Consumer {
    public final /* synthetic */ boolean f$0;
    public final /* synthetic */ boolean[] f$1;
    public final /* synthetic */ boolean f$2;

    public /* synthetic */ RootWindowContainer$$ExternalSyntheticLambda44(
            boolean z, boolean[] zArr, boolean z2) {
        this.f$0 = z;
        this.f$1 = zArr;
        this.f$2 = z2;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        boolean z = this.f$0;
        boolean[] zArr = this.f$1;
        boolean z2 = this.f$2;
        Task task = (Task) obj;
        if (!z) {
            task.ensureActivitiesVisible(true, null);
        } else {
            zArr[0] = task.goToSleepIfPossible(z2) & zArr[0];
        }
    }
}
