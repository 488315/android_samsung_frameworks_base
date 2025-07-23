package com.android.systemui.qs;

import android.content.res.Configuration;
import android.view.MotionEvent;
import android.view.View;
import com.android.systemui.R;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.shade.ShadeHeaderController;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.ViewController;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class QSContainerImplController extends ViewController {
    public final ConfigurationController mConfigurationController;
    public final AnonymousClass1 mConfigurationListener;
    public final AnonymousClass2 mContainerTouchHandler;
    public final SecQSDetailController mDetailController;
    public final FalsingManager mFalsingManager;
    public final NonInterceptingScrollView mQSPanelContainer;
    public final SecQSPanelController mQsPanelController;
    public final SecQuickStatusBarHeaderController mQuickStatusBarHeaderController;

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.qs.QSContainerImplController$1] */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.qs.QSContainerImplController$2] */
    public QSContainerImplController(QSContainerImpl qSContainerImpl, SecQSPanelController secQSPanelController, SecQuickStatusBarHeaderController secQuickStatusBarHeaderController, ConfigurationController configurationController, FalsingManager falsingManager, ShadeHeaderController shadeHeaderController, SecQSDetailController secQSDetailController) {
        super(qSContainerImpl);
        this.mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.qs.QSContainerImplController.1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                QSContainerImplController qSContainerImplController = QSContainerImplController.this;
                ((QSContainerImpl) ((ViewController) qSContainerImplController).mView).updateResources(qSContainerImplController.mQsPanelController, qSContainerImplController.mQuickStatusBarHeaderController);
            }
        };
        this.mContainerTouchHandler = new View.OnTouchListener() { // from class: com.android.systemui.qs.QSContainerImplController.2
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getActionMasked() != 1) {
                    return false;
                }
                QSContainerImplController.this.mQSPanelContainer.getClass();
                return false;
            }
        };
        this.mQsPanelController = secQSPanelController;
        this.mQuickStatusBarHeaderController = secQuickStatusBarHeaderController;
        this.mConfigurationController = configurationController;
        this.mFalsingManager = falsingManager;
        this.mQSPanelContainer = ((QSContainerImpl) this.mView).mQSPanelContainer;
        int i = SceneContainerFlag.$r8$clinit;
        qSContainerImpl.mShadeHeaderController = shadeHeaderController;
        this.mDetailController = secQSDetailController;
    }

    public final QSContainerImpl getView() {
        return (QSContainerImpl) this.mView;
    }

    @Override // com.android.systemui.util.ViewController
    public final void onInit() {
        this.mQuickStatusBarHeaderController.init();
        ((QSContainerImpl) this.mView).getClass();
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        ((QSContainerImpl) this.mView).updateResources(this.mQsPanelController, this.mQuickStatusBarHeaderController);
        ((ConfigurationControllerImpl) this.mConfigurationController).addCallback(this.mConfigurationListener);
        NonInterceptingScrollView nonInterceptingScrollView = this.mQSPanelContainer;
        if (nonInterceptingScrollView != null) {
            nonInterceptingScrollView.setOnTouchListener(this.mContainerTouchHandler);
        }
        this.mDetailController.scrollView = ((QSContainerImpl) this.mView).findViewById(R.id.expanded_qs_scroll_view);
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        ((ConfigurationControllerImpl) this.mConfigurationController).removeCallback(this.mConfigurationListener);
        NonInterceptingScrollView nonInterceptingScrollView = this.mQSPanelContainer;
        if (nonInterceptingScrollView != null) {
            nonInterceptingScrollView.setOnTouchListener(null);
        }
    }
}
