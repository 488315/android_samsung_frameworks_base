package com.android.systemui.qs.bar;

import java.util.function.Supplier;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class QuickControlBar$$ExternalSyntheticLambda0 implements Supplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ QuickControlBar f$0;

    public /* synthetic */ QuickControlBar$$ExternalSyntheticLambda0(QuickControlBar quickControlBar, int i) {
        this.$r8$classId = i;
        this.f$0 = quickControlBar;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        int i = this.$r8$classId;
        QuickControlBar quickControlBar = this.f$0;
        switch (i) {
            case 0:
                return Boolean.valueOf(quickControlBar.mShowing);
            default:
                return Boolean.valueOf(quickControlBar.mQsExpanded);
        }
    }
}
