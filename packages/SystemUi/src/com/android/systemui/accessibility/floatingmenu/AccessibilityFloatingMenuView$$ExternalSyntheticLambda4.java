package com.android.systemui.accessibility.floatingmenu;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class AccessibilityFloatingMenuView$$ExternalSyntheticLambda4 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AccessibilityFloatingMenuView f$0;

    public /* synthetic */ AccessibilityFloatingMenuView$$ExternalSyntheticLambda4(AccessibilityFloatingMenuView accessibilityFloatingMenuView, int i) {
        this.$r8$classId = i;
        this.f$0 = accessibilityFloatingMenuView;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.mFadeOutAnimator.start();
                break;
            default:
                this.f$0.setAlpha(1.0f);
                break;
        }
    }
}
