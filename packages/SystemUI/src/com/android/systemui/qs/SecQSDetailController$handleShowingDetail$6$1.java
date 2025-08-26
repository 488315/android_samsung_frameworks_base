package com.android.systemui.qs;

import com.android.systemui.plugins.qs.DetailAdapter;

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
