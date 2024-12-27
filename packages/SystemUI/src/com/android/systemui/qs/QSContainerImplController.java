package com.android.systemui.qs;

import android.content.res.Configuration;
import android.view.MotionEvent;
import android.view.View;
import com.android.systemui.Flags;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.shade.ShadeHeaderController;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.ViewController;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class QSContainerImplController extends ViewController {
    public final ConfigurationController mConfigurationController;
    public final AnonymousClass1 mConfigurationListener;
    public final AnonymousClass2 mContainerTouchHandler;
    public final FalsingManager mFalsingManager;
    public final NonInterceptingScrollView mQSPanelContainer;
    public final SecQSPanelController mQsPanelController;
    public final SecQuickStatusBarHeaderController mQuickStatusBarHeaderController;

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.qs.QSContainerImplController$1] */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.qs.QSContainerImplController$2] */
    public QSContainerImplController(QSContainerImpl qSContainerImpl, SecQSPanelController secQSPanelController, SecQuickStatusBarHeaderController secQuickStatusBarHeaderController, ConfigurationController configurationController, FalsingManager falsingManager, ShadeHeaderController shadeHeaderController) {
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
        Flags.sceneContainer();
        qSContainerImpl.mShadeHeaderController = shadeHeaderController;
    }

    public final QSContainerImpl getView() {
        return (QSContainerImpl) this.mView;
    }

    @Override // com.android.systemui.util.ViewController
    public final void onInit() {
        this.mQuickStatusBarHeaderController.init();
        ((QSContainerImpl) this.mView).mSceneContainerEnabled = false;
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        ((QSContainerImpl) this.mView).updateResources(this.mQsPanelController, this.mQuickStatusBarHeaderController);
        ((ConfigurationControllerImpl) this.mConfigurationController).addCallback(this.mConfigurationListener);
        NonInterceptingScrollView nonInterceptingScrollView = this.mQSPanelContainer;
        if (nonInterceptingScrollView != null) {
            nonInterceptingScrollView.setOnTouchListener(this.mContainerTouchHandler);
        }
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
