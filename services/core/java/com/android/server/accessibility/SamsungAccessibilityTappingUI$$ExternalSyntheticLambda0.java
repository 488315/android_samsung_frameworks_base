package com.android.server.accessibility;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class SamsungAccessibilityTappingUI$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SamsungAccessibilityTappingUI f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ SamsungAccessibilityTappingUI$$ExternalSyntheticLambda0(
            SamsungAccessibilityTappingUI samsungAccessibilityTappingUI, boolean z, int i) {
        this.$r8$classId = i;
        this.f$0 = samsungAccessibilityTappingUI;
        this.f$1 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                SamsungAccessibilityTappingUI samsungAccessibilityTappingUI = this.f$0;
                if (!this.f$1) {
                    samsungAccessibilityTappingUI.view.setVisibility(8);
                    break;
                } else {
                    samsungAccessibilityTappingUI.view.setVisibility(0);
                    break;
                }
            default:
                SamsungAccessibilityTappingUI samsungAccessibilityTappingUI2 = this.f$0;
                if (!this.f$1) {
                    samsungAccessibilityTappingUI2.mTapImageView.setVisibility(0);
                    samsungAccessibilityTappingUI2.mIgnoreImageView.setVisibility(8);
                    break;
                } else {
                    samsungAccessibilityTappingUI2.mTapImageView.setVisibility(8);
                    samsungAccessibilityTappingUI2.mIgnoreImageView.setVisibility(0);
                    break;
                }
        }
    }
}
