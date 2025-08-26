package com.android.systemui.statusbar.pipeline.shared.ui.composable;

/* loaded from: classes3.dex */
public final class FourBarsDimensions extends BarsDependentDimensions {
    public static final FourBarsDimensions INSTANCE = new FourBarsDimensions();

    /* JADX WARN: Illegal instructions before constructor call */
    private FourBarsDimensions() {
        StackedMobileIconDimensions stackedMobileIconDimensions = StackedMobileIconDimensions.INSTANCE;
        stackedMobileIconDimensions.getClass();
        long j = StackedMobileIconDimensions.IconWidthFourBarsSp;
        stackedMobileIconDimensions.getClass();
        long j2 = StackedMobileIconDimensions.HorizontalPaddingFourBarsSp;
        stackedMobileIconDimensions.getClass();
        super(j, j2, StackedMobileIconDimensions.BarBaseHeightFourBarsSp, null);
    }
}
