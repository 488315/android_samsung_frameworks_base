package com.android.systemui.accessibility.floatingmenu;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class AccessibilityFloatingMenuView$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AccessibilityFloatingMenuView f$0;

    public /* synthetic */ AccessibilityFloatingMenuView$$ExternalSyntheticLambda1(AccessibilityFloatingMenuView accessibilityFloatingMenuView, int i) {
        this.$r8$classId = i;
        this.f$0 = accessibilityFloatingMenuView;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        AccessibilityFloatingMenuView accessibilityFloatingMenuView = this.f$0;
        switch (i) {
            case 0:
                accessibilityFloatingMenuView.mFadeOutAnimator.start();
                break;
            default:
                accessibilityFloatingMenuView.setAlpha(1.0f);
                break;
        }
    }
}
