package com.android.systemui.statusbar.phone;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
