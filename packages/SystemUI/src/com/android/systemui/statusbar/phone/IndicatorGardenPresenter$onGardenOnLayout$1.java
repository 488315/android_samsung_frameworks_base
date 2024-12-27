package com.android.systemui.statusbar.phone;

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
