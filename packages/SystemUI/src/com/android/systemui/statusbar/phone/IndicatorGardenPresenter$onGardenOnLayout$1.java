package com.android.systemui.statusbar.phone;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class IndicatorGardenPresenter$onGardenOnLayout$1 implements Runnable {
    public final /* synthetic */ IndicatorGarden $garden;
    public final /* synthetic */ IndicatorGardenPresenter this$0;

    public IndicatorGardenPresenter$onGardenOnLayout$1(IndicatorGardenPresenter indicatorGardenPresenter, IndicatorGarden indicatorGarden) {
        this.this$0 = indicatorGardenPresenter;
        this.$garden = indicatorGarden;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.this$0.updateGardenWithNewModel(this.$garden);
    }
}
