package com.android.systemui.statusbar.phone;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
