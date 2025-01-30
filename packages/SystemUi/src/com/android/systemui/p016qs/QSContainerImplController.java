package com.android.systemui.p016qs;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.shade.ShadeHeaderController;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.statusbar.policy.OnHeadsUpChangedListener;
import com.android.systemui.util.ViewController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class QSContainerImplController extends ViewController {
    public final ConfigurationController mConfigurationController;
    public final C20371 mConfigurationListener;
    public final ViewOnTouchListenerC20382 mContainerTouchHandler;
    public final FalsingManager mFalsingManager;
    public final C20393 mHUNListener;
    public final HeadsUpManager mHeadsUpManager;
    public final ViewOnLayoutChangeListenerC20404 mOnLayoutChangeListener;
    public final NonInterceptingScrollView mQSPanelContainer;
    public final SecQSPanelController mQsPanelController;
    public final SecQuickStatusBarHeaderController mQuickStatusBarHeaderController;
    public final ShadeHeaderController mShadeHeaderController;

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.qs.QSContainerImplController$1] */
    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.qs.QSContainerImplController$2] */
    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.qs.QSContainerImplController$3] */
    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.qs.QSContainerImplController$4] */
    public QSContainerImplController(QSContainerImpl qSContainerImpl, SecQSPanelController secQSPanelController, SecQuickStatusBarHeaderController secQuickStatusBarHeaderController, ConfigurationController configurationController, FalsingManager falsingManager, ShadeHeaderController shadeHeaderController, HeadsUpManager headsUpManager) {
        super(qSContainerImpl);
        this.mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.qs.QSContainerImplController.1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                boolean z = QpRune.QUICK_TABLET_BG;
                QSContainerImplController qSContainerImplController = QSContainerImplController.this;
                if (z) {
                    ((QSContainerImpl) qSContainerImplController.mView).updateTabletResources(qSContainerImplController.mShadeHeaderController);
                    return;
                }
                QSContainerImpl qSContainerImpl2 = (QSContainerImpl) qSContainerImplController.mView;
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) qSContainerImpl2.mQSPanelContainer.getLayoutParams();
                SecQSPanelResourcePicker secQSPanelResourcePicker = qSContainerImpl2.mResourcePicker;
                Context context = qSContainerImpl2.getContext();
                secQSPanelResourcePicker.getClass();
                Resources resources = context.getResources();
                layoutParams.topMargin = resources.getDimensionPixelSize(R.dimen.sec_qs_buttons_container_margin_bottom) + resources.getDimensionPixelSize(R.dimen.sec_qs_buttons_container_margin_top) + resources.getDimensionPixelSize(R.dimen.sec_qs_buttons_container_height);
            }
        };
        this.mContainerTouchHandler = new View.OnTouchListener() { // from class: com.android.systemui.qs.QSContainerImplController.2
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getActionMasked() != 1) {
                    return false;
                }
                QSContainerImplController qSContainerImplController = QSContainerImplController.this;
                if (!qSContainerImplController.mQSPanelContainer.mPreventingIntercept) {
                    return false;
                }
                qSContainerImplController.mFalsingManager.isFalseTouch(17);
                return false;
            }
        };
        this.mHUNListener = new OnHeadsUpChangedListener() { // from class: com.android.systemui.qs.QSContainerImplController.3
            @Override // com.android.systemui.statusbar.policy.OnHeadsUpChangedListener
            public final void onHeadsUpPinnedModeChanged(boolean z) {
                ((QSContainerImpl) QSContainerImplController.this.mView).mHeadsUpPinned = z;
            }
        };
        this.mOnLayoutChangeListener = new View.OnLayoutChangeListener() { // from class: com.android.systemui.qs.QSContainerImplController.4
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                QSContainerImplController qSContainerImplController = QSContainerImplController.this;
                ((QSContainerImpl) qSContainerImplController.mView).updateTabletResources(qSContainerImplController.mShadeHeaderController);
            }
        };
        this.mQsPanelController = secQSPanelController;
        this.mQuickStatusBarHeaderController = secQuickStatusBarHeaderController;
        this.mShadeHeaderController = shadeHeaderController;
        this.mConfigurationController = configurationController;
        this.mFalsingManager = falsingManager;
        this.mQSPanelContainer = ((QSContainerImpl) this.mView).mQSPanelContainer;
        this.mHeadsUpManager = headsUpManager;
    }

    @Override // com.android.systemui.util.ViewController
    public final void onInit() {
        this.mQuickStatusBarHeaderController.init();
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        boolean z = QpRune.QUICK_TABLET_BG;
        ShadeHeaderController shadeHeaderController = this.mShadeHeaderController;
        if (z) {
            ((QSContainerImpl) this.mView).updateTabletResources(shadeHeaderController);
        } else {
            QSContainerImpl qSContainerImpl = (QSContainerImpl) this.mView;
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) qSContainerImpl.mQSPanelContainer.getLayoutParams();
            SecQSPanelResourcePicker secQSPanelResourcePicker = qSContainerImpl.mResourcePicker;
            Context context = qSContainerImpl.getContext();
            secQSPanelResourcePicker.getClass();
            Resources resources = context.getResources();
            layoutParams.topMargin = resources.getDimensionPixelSize(R.dimen.sec_qs_buttons_container_margin_bottom) + resources.getDimensionPixelSize(R.dimen.sec_qs_buttons_container_margin_top) + resources.getDimensionPixelSize(R.dimen.sec_qs_buttons_container_height);
        }
        ((ConfigurationControllerImpl) this.mConfigurationController).addCallback(this.mConfigurationListener);
        this.mQSPanelContainer.setOnTouchListener(this.mContainerTouchHandler);
        this.mHeadsUpManager.addListener(this.mHUNListener);
        if (QpRune.QUICK_TABLET_TOP_MARGIN) {
            shadeHeaderController.header.addOnLayoutChangeListener(this.mOnLayoutChangeListener);
        }
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        ((ConfigurationControllerImpl) this.mConfigurationController).removeCallback(this.mConfigurationListener);
        this.mQSPanelContainer.setOnTouchListener(null);
        this.mHeadsUpManager.mListeners.remove(this.mHUNListener);
        if (QpRune.QUICK_TABLET_TOP_MARGIN) {
            this.mShadeHeaderController.header.removeOnLayoutChangeListener(this.mOnLayoutChangeListener);
        }
    }
}
