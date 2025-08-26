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
import com.android.systemui.shade.SecPanelFoldHelper;
import com.android.systemui.shade.SecPanelSplitHelper;
import com.android.systemui.shade.ShadeHeaderController;
import com.android.systemui.shade.data.repository.ShadeRepository;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class SecQSImpl implements PanelTransitionStateListener, LockscreenShadeTransitionController.Callback {
    public static final /* synthetic */ int $r8$clinit = 0;
    public BarController barController;
    public int barState = 1;
    public final Lazy coloredBGHelper$delegate;
    public final Lazy detailController$delegate;
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

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
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
        final int i = 0;
        this.coloredBGHelper$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.qs.SecQSImpl$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i) {
                    case 0:
                        int i2 = SecQSImpl.$r8$clinit;
                        return (ColoredBGHelper) Dependency.sDependency.getDependencyInner(ColoredBGHelper.class);
                    case 1:
                        int i3 = SecQSImpl.$r8$clinit;
                        return (SecQSDetailController) Dependency.sDependency.getDependencyInner(SecQSDetailController.class);
                    case 2:
                        int i4 = SecQSImpl.$r8$clinit;
                        return (ShadeHeaderController) Dependency.sDependency.getDependencyInner(ShadeHeaderController.class);
                    default:
                        int i5 = SecQSImpl.$r8$clinit;
                        return (ShadeRepository) Dependency.sDependency.getDependencyInner(ShadeRepository.class);
                }
            }
        });
        final int i2 = 1;
        this.detailController$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.qs.SecQSImpl$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        int i22 = SecQSImpl.$r8$clinit;
                        return (ColoredBGHelper) Dependency.sDependency.getDependencyInner(ColoredBGHelper.class);
                    case 1:
                        int i3 = SecQSImpl.$r8$clinit;
                        return (SecQSDetailController) Dependency.sDependency.getDependencyInner(SecQSDetailController.class);
                    case 2:
                        int i4 = SecQSImpl.$r8$clinit;
                        return (ShadeHeaderController) Dependency.sDependency.getDependencyInner(ShadeHeaderController.class);
                    default:
                        int i5 = SecQSImpl.$r8$clinit;
                        return (ShadeRepository) Dependency.sDependency.getDependencyInner(ShadeRepository.class);
                }
            }
        });
        final int i3 = 2;
        this.shadeHeaderController$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.qs.SecQSImpl$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i3) {
                    case 0:
                        int i22 = SecQSImpl.$r8$clinit;
                        return (ColoredBGHelper) Dependency.sDependency.getDependencyInner(ColoredBGHelper.class);
                    case 1:
                        int i32 = SecQSImpl.$r8$clinit;
                        return (SecQSDetailController) Dependency.sDependency.getDependencyInner(SecQSDetailController.class);
                    case 2:
                        int i4 = SecQSImpl.$r8$clinit;
                        return (ShadeHeaderController) Dependency.sDependency.getDependencyInner(ShadeHeaderController.class);
                    default:
                        int i5 = SecQSImpl.$r8$clinit;
                        return (ShadeRepository) Dependency.sDependency.getDependencyInner(ShadeRepository.class);
                }
            }
        });
        final int i4 = 3;
        this.shadeRepository$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.qs.SecQSImpl$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i4) {
                    case 0:
                        int i22 = SecQSImpl.$r8$clinit;
                        return (ColoredBGHelper) Dependency.sDependency.getDependencyInner(ColoredBGHelper.class);
                    case 1:
                        int i32 = SecQSImpl.$r8$clinit;
                        return (SecQSDetailController) Dependency.sDependency.getDependencyInner(SecQSDetailController.class);
                    case 2:
                        int i42 = SecQSImpl.$r8$clinit;
                        return (ShadeHeaderController) Dependency.sDependency.getDependencyInner(ShadeHeaderController.class);
                    default:
                        int i5 = SecQSImpl.$r8$clinit;
                        return (ShadeRepository) Dependency.sDependency.getDependencyInner(ShadeRepository.class);
                }
            }
        });
        new SecPanelFoldHelper();
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
        this.panelListening = z;
        QSButtonsContainerController qSButtonsContainerController = this.qsButtonsContainerController;
        if (qSButtonsContainerController != null) {
            qSButtonsContainerController.setListening(z, z2);
        }
        SecQSPanelController secQSPanelController = this.qsPanelController;
        if (secQSPanelController != null) {
            secQSPanelController.setListening(z && z2);
        }
        BarController barController = this.barController;
        if (barController != null) {
            barController.mAllBarItems.forEach(new BarController$$ExternalSyntheticLambda1(z, 2));
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
