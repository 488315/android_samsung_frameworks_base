package com.android.systemui.qs;

import com.android.systemui.Dependency;
import com.android.systemui.qs.animator.SecQSImplAnimatorManager;
import com.android.systemui.qs.bar.BarController;
import com.android.systemui.qs.bar.BarController$$ExternalSyntheticLambda1;
import com.android.systemui.qs.bar.ColoredBGHelper;
import com.android.systemui.qs.buttons.QSButtonsContainerController;
import com.android.systemui.qs.customize.viewcontroller.QSCMainViewController;
import com.android.systemui.shade.PanelTransitionStateChangeEvent;
import com.android.systemui.shade.PanelTransitionStateListener;
import com.android.systemui.shade.SecPanelSplitHelper;
import com.android.systemui.shade.ShadeHeaderController;
import com.android.systemui.shade.data.repository.ShadeRepository;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SecQSImpl implements PanelTransitionStateListener, LockscreenShadeTransitionController.Callback {
    public BarController barController;
    public int barState;
    public final Lazy coloredBGHelper$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.qs.SecQSImpl$coloredBGHelper$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return (ColoredBGHelper) Dependency.sDependency.getDependencyInner(ColoredBGHelper.class);
        }
    });
    public final Lazy detailController$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.qs.SecQSImpl$detailController$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return (SecQSDetailController) Dependency.sDependency.getDependencyInner(SecQSDetailController.class);
        }
    });
    public final LockscreenShadeTransitionController lockscreenShadeTransitionController;
    public boolean panelListening;
    public final SecPanelSplitHelper panelSplitHelper;
    public int panelTransitionState;
    public QSButtonsContainerController qsButtonsContainerController;
    public final Function0 qsExpanded;
    public SecQSPanelController qsPanelController;
    public QSCMainViewController qscMainViewController;
    public SecQSImplAnimatorManager secQSImplAnimatorManager;
    public final Lazy shadeHeaderController$delegate;
    public final Lazy shadeRepository$delegate;
    public boolean stackScrollerOverscrolling;
    public final Runnable updateQsState;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public SecQSImpl(Function0 function0, SecPanelSplitHelper secPanelSplitHelper, Runnable runnable, LockscreenShadeTransitionController lockscreenShadeTransitionController) {
        this.qsExpanded = function0;
        this.panelSplitHelper = secPanelSplitHelper;
        this.updateQsState = runnable;
        this.lockscreenShadeTransitionController = lockscreenShadeTransitionController;
        LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.qs.SecQSImpl$headsUpManager$2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (HeadsUpManager) Dependency.sDependency.getDependencyInner(HeadsUpManager.class);
            }
        });
        this.shadeHeaderController$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.qs.SecQSImpl$shadeHeaderController$2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (ShadeHeaderController) Dependency.sDependency.getDependencyInner(ShadeHeaderController.class);
            }
        });
        this.shadeRepository$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.qs.SecQSImpl$shadeRepository$2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (ShadeRepository) Dependency.sDependency.getDependencyInner(ShadeRepository.class);
            }
        });
        this.barState = 1;
    }

    @Override // com.android.systemui.shade.PanelTransitionStateListener
    public final void onPanelTransitionStateChanged(PanelTransitionStateChangeEvent panelTransitionStateChangeEvent) {
        int i = this.panelTransitionState;
        int i2 = panelTransitionStateChangeEvent.state;
        if (i == i2) {
            return;
        }
        this.panelTransitionState = i2;
        this.updateQsState.run();
    }

    public final void setListening(boolean z, boolean z2) {
        BarController barController = this.barController;
        if (barController != null) {
            barController.mAllBarItems.forEach(new BarController$$ExternalSyntheticLambda1(z, 2));
        }
        if (this.panelListening == z) {
            return;
        }
        this.panelListening = z;
        QSButtonsContainerController qSButtonsContainerController = this.qsButtonsContainerController;
        if (qSButtonsContainerController != null) {
            qSButtonsContainerController.setListening(z, z2);
        }
        SecQSPanelController secQSPanelController = this.qsPanelController;
        if (secQSPanelController != null) {
            secQSPanelController.setListening(z && z2);
        }
    }

    @Override // com.android.systemui.statusbar.LockscreenShadeTransitionController.Callback
    public final void setTransitionToFullShadeAmount(float f) {
        int i = this.barState;
        Function0 function0 = this.qsExpanded;
        if (i != 1) {
            if (i != 2) {
                return;
            }
            setListening(true, ((Boolean) function0.invoke()).booleanValue());
        } else if (f == 0.0f && this.panelListening) {
            setListening(false, ((Boolean) function0.invoke()).booleanValue());
        } else {
            if (this.panelListening) {
                return;
            }
            setListening(true, ((Boolean) function0.invoke()).booleanValue());
        }
    }
}
