package com.android.systemui.qs;

import com.android.systemui.plugins.qs.DetailAdapter;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SecQSDetailController$handleShowingDetail$6$1 implements Runnable {
    public final /* synthetic */ DetailAdapter $this_run;
    public final /* synthetic */ SecQSDetailController this$0;

    public SecQSDetailController$handleShowingDetail$6$1(SecQSDetailController secQSDetailController, DetailAdapter detailAdapter) {
        this.this$0 = secQSDetailController;
        this.$this_run = detailAdapter;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.this$0.metricsLogger.hidden(this.$this_run.getMetricsCategory());
        this.$this_run.dismissListPopupWindow();
        this.this$0.detailAdapter = null;
    }
}
