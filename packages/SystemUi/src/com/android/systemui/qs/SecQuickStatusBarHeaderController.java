package com.android.systemui.qs;

import android.view.View;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.shade.ShadeHeaderController;
import com.android.systemui.statusbar.phone.StatusBarIconController;
import com.android.systemui.util.ViewController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SecQuickStatusBarHeaderController extends ViewController {
    public boolean mListening;
    public final SecQuickQSPanelController mQuickQSPanelController;
    public final ShadeHeaderController mShadeHeaderController;

    public SecQuickStatusBarHeaderController(SecQuickStatusBarHeader secQuickStatusBarHeader, StatusBarIconController statusBarIconController, DemoModeController demoModeController, SecQuickQSPanelController secQuickQSPanelController, FeatureFlags featureFlags, StatusBarIconController.TintedIconManager.Factory factory, ShadeHeaderController shadeHeaderController) {
        super(secQuickStatusBarHeader);
        this.mQuickQSPanelController = secQuickQSPanelController;
        this.mShadeHeaderController = shadeHeaderController;
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        View view = this.mView;
        ((SecQuickStatusBarHeader) view).setPadding(((SecQuickStatusBarHeader) view).getPaddingLeft(), QpRune.QUICK_TABLET_TOP_MARGIN ? this.mShadeHeaderController.getViewHeight() - ((SecQuickStatusBarHeader) this.mView).getResources().getDimensionPixelSize(R.dimen.shade_header_bottom_margin_tablet) : ((SecQuickStatusBarHeader) this.mView).getPaddingTop(), ((SecQuickStatusBarHeader) this.mView).getPaddingRight(), ((SecQuickStatusBarHeader) this.mView).getPaddingBottom());
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        if (this.mListening) {
            this.mListening = false;
            this.mQuickQSPanelController.setListening(false);
        }
    }

    @Override // com.android.systemui.util.ViewController
    public final void onInit() {
    }
}
