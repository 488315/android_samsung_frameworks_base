package com.android.systemui.statusbar.pipeline.shared.ui.composable;

/* loaded from: classes3.dex */
public final class FiveBarsDimensions extends BarsDependentDimensions {
    public static final FiveBarsDimensions INSTANCE = new FiveBarsDimensions();

    /* JADX WARN: Illegal instructions before constructor call */
    private FiveBarsDimensions() {
        StackedMobileIconDimensions stackedMobileIconDimensions = StackedMobileIconDimensions.INSTANCE;
        stackedMobileIconDimensions.getClass();
        long j = StackedMobileIconDimensions.IconWidthFiveBarsSp;
        stackedMobileIconDimensions.getClass();
        long j2 = StackedMobileIconDimensions.HorizontalPaddingFiveBarsSp;
        stackedMobileIconDimensions.getClass();
        super(j, j2, StackedMobileIconDimensions.BarBaseHeightFiveBarsSp, null);
    }
}
