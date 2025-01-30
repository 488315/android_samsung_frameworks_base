package com.android.systemui.qs.animator;

import android.content.res.Configuration;
import com.android.systemui.logging.PanelScreenShotLogger;
import com.android.systemui.qs.dagger.QSFragmentComponent;
import com.android.systemui.plugins.qs.InterfaceC1922QS;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.shade.ShadeExpansionChangeEvent;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SecQSFragmentAnimatorManager extends SecQSFragmentAnimatorBase {
    public final ArrayList mList;
    public final SecQSFragmentAnimatorLogger mLogger;
    public int mOrientation;
    public boolean mQsFullyExpanded;
    public final ShadeExpansionStateManager mShadeExpansionStateManager;
    public final QsTransitionAnimator mTransitionAnimator;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.qs.animator.SecQSFragmentAnimatorManager$1 */
    public final class C20831 {
        public C20831() {
        }

        public final void setDetailClosing(boolean z) {
            SecQSFragmentAnimatorManager secQSFragmentAnimatorManager = SecQSFragmentAnimatorManager.this;
            secQSFragmentAnimatorManager.mIsDetailClosing = z;
            secQSFragmentAnimatorManager.executeConsumer(new SecQSFragmentAnimatorManager$$ExternalSyntheticLambda1(z, 5));
        }

        public final void setDetailOpening(boolean z) {
            SecQSFragmentAnimatorManager secQSFragmentAnimatorManager = SecQSFragmentAnimatorManager.this;
            secQSFragmentAnimatorManager.mIsDetailOpening = z;
            secQSFragmentAnimatorManager.executeConsumer(new SecQSFragmentAnimatorManager$$ExternalSyntheticLambda1(z, 4));
        }

        public final void setDetailShowing(boolean z) {
            SecQSFragmentAnimatorManager secQSFragmentAnimatorManager = SecQSFragmentAnimatorManager.this;
            secQSFragmentAnimatorManager.mIsDetailShowing = z;
            secQSFragmentAnimatorManager.executeConsumer(new SecQSFragmentAnimatorManager$$ExternalSyntheticLambda1(z, 6));
        }
    }

    public SecQSFragmentAnimatorManager(QSFragmentComponent qSFragmentComponent, ShadeExpansionStateManager shadeExpansionStateManager) {
        C20831 c20831 = new C20831();
        this.mOrientation = 1;
        this.mLogger = new SecQSFragmentAnimatorLogger();
        ArrayList arrayList = new ArrayList();
        this.mList = arrayList;
        arrayList.add(qSFragmentComponent.getQsExpandAnimator());
        arrayList.add(qSFragmentComponent.getQsOpenAnimator());
        QsTransitionAnimator qsTransitionAnimator = qSFragmentComponent.getQsTransitionAnimator();
        this.mTransitionAnimator = qsTransitionAnimator;
        qsTransitionAnimator.mDetailStateCallback = c20831;
        arrayList.add(qsTransitionAnimator);
        PanelScreenShotLogger.INSTANCE.addLogProvider("QSFragmentAnimator", this);
        this.mShadeExpansionStateManager = shadeExpansionStateManager;
        shadeExpansionStateManager.addExpansionListener(this);
    }

    @Override // com.android.systemui.qs.animator.SecQSFragmentAnimatorBase
    public final void destroyQSViews() {
        executeConsumer(new SecQSFragmentAnimatorManager$$ExternalSyntheticLambda0(2));
        synchronized (PanelScreenShotLogger.INSTANCE) {
            PanelScreenShotLogger.providers.remove("QSFragmentAnimator");
        }
        this.mShadeExpansionStateManager.expansionListeners.remove(this);
    }

    public final void executeConsumer(Consumer consumer) {
        this.mList.stream().filter(new SecQSFragmentAnimatorManager$$ExternalSyntheticLambda6(2)).forEach(consumer);
    }

    @Override // com.android.systemui.qs.animator.SecQSFragmentAnimatorBase, com.android.systemui.logging.PanelScreenShotLogger.LogProvider
    public final ArrayList gatherState() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("SecQSFragmentAnimatorManager ============================================= ");
        arrayList.add("  mQsExpanded = " + this.mQsExpanded + "  mQsFullyExpanded = " + this.mQsFullyExpanded + "  mPanelExpanded = " + this.mPanelExpanded);
        StringBuilder sb = new StringBuilder("  mIsExpanding = false  mState = ");
        sb.append(this.mState);
        sb.append("  mQsExpandImmediate = false mQsCollapsingWhilePanelClosing = false");
        arrayList.add(sb.toString());
        arrayList.add("============================================================== ");
        this.mList.stream().filter(new SecQSFragmentAnimatorManager$$ExternalSyntheticLambda6(0)).map(new SecQSFragmentAnimatorManager$$ExternalSyntheticLambda7()).filter(new SecQSFragmentAnimatorManager$$ExternalSyntheticLambda6(1)).forEach(new SecQSFragmentAnimatorManager$$ExternalSyntheticLambda3(arrayList, 3));
        return arrayList;
    }

    @Override // com.android.systemui.qs.animator.SecQSFragmentAnimatorBase
    public final void onConfigurationChanged(Configuration configuration) {
        if (this.mOrientation == configuration.orientation && this.mThemeSeq == configuration.themeSeq && this.mAssetSeq == configuration.assetsSeq && this.mUIMode == configuration.uiMode) {
            return;
        }
        executeConsumer(new SecQSFragmentAnimatorManager$$ExternalSyntheticLambda3(configuration, 2));
        this.mOrientation = configuration.orientation;
        this.mThemeSeq = configuration.themeSeq;
        this.mAssetSeq = configuration.assetsSeq;
        this.mUIMode = configuration.uiMode;
    }

    @Override // com.android.systemui.qs.animator.SecQSFragmentAnimatorBase
    public final void onPanelClosed() {
        executeConsumer(new SecQSFragmentAnimatorManager$$ExternalSyntheticLambda0(1));
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x004d, code lost:
    
        if ((r0 == 1.0f) != false) goto L30;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00b1, code lost:
    
        if ((r2 == 1.0f) != false) goto L58;
     */
    @Override // com.android.systemui.qs.animator.SecQSFragmentAnimatorBase, com.android.systemui.shade.ShadeExpansionListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onPanelExpansionChanged(ShadeExpansionChangeEvent shadeExpansionChangeEvent) {
        float f = shadeExpansionChangeEvent.fraction;
        SecQSFragmentAnimatorLogger secQSFragmentAnimatorLogger = this.mLogger;
        int i = 0;
        if (!(secQSFragmentAnimatorLogger.panelExpansionFraction == f)) {
            if (Math.abs(secQSFragmentAnimatorLogger.panelExpansionFractionLastLoggingValue - f) <= 0.1d) {
                float f2 = secQSFragmentAnimatorLogger.panelExpansionFraction;
                if (!(f2 == 0.0f)) {
                    if (!(f2 == 1.0f)) {
                        if (!(f == 0.0f)) {
                        }
                    }
                }
            }
            secQSFragmentAnimatorLogger.printLog("[panelExpansionFraction:" + new DecimalFormat("#.###").format(Float.valueOf(f)) + "]");
            secQSFragmentAnimatorLogger.panelExpansionFractionLastLoggingValue = f;
        }
        secQSFragmentAnimatorLogger.panelExpansionFraction = f;
        float f3 = secQSFragmentAnimatorLogger.panelExpansionDragDownPxAmount;
        float f4 = shadeExpansionChangeEvent.dragDownPxAmount;
        if (!(f3 == f4)) {
            if (Math.abs(secQSFragmentAnimatorLogger.panelExpansionDragDownPxAmountLastLoggingValue - f4) <= 0.1d) {
                float f5 = secQSFragmentAnimatorLogger.panelExpansionDragDownPxAmount;
                if (!(f5 == 0.0f)) {
                    if (!(f5 == 1.0f)) {
                        if (!(f4 == 0.0f)) {
                        }
                    }
                }
            }
            secQSFragmentAnimatorLogger.printLog("[panelExpansionDragDownPxAmount:" + new DecimalFormat("#.###").format(Float.valueOf(f4)) + "]");
            secQSFragmentAnimatorLogger.panelExpansionDragDownPxAmountLastLoggingValue = f4;
        }
        secQSFragmentAnimatorLogger.panelExpansionDragDownPxAmount = f4;
        executeConsumer(new SecQSFragmentAnimatorManager$$ExternalSyntheticLambda3(shadeExpansionChangeEvent, i));
        if (shadeExpansionChangeEvent.fraction == 0.0f) {
            if (this.mPanelExpanded) {
                updatePanelExpanded(false);
            }
        } else {
            if (this.mPanelExpanded) {
                return;
            }
            updatePanelExpanded(true);
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSFragmentAnimatorBase
    public final void onRtlChanged() {
        executeConsumer(new SecQSFragmentAnimatorManager$$ExternalSyntheticLambda0(0));
    }

    @Override // com.android.systemui.qs.animator.SecQSFragmentAnimatorBase
    public final void onStateChanged(final int i) {
        SecQSFragmentAnimatorLogger secQSFragmentAnimatorLogger = this.mLogger;
        if (secQSFragmentAnimatorLogger.barState != i) {
            secQSFragmentAnimatorLogger.printLog("[barState:" + i + "]");
        }
        secQSFragmentAnimatorLogger.barState = i;
        executeConsumer(new Consumer() { // from class: com.android.systemui.qs.animator.SecQSFragmentAnimatorManager$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((SecQSFragmentAnimatorBase) obj).onStateChanged(i);
            }
        });
    }

    @Override // com.android.systemui.qs.animator.SecQSFragmentAnimatorBase
    public final void setExpandImmediateSupplier(BooleanSupplier booleanSupplier) {
        this.mExpandImmediateSupplier = booleanSupplier;
        executeConsumer(new SecQSFragmentAnimatorManager$$ExternalSyntheticLambda3(booleanSupplier, 5));
    }

    @Override // com.android.systemui.qs.animator.SecQSFragmentAnimatorBase
    public final void setFancyClipping(final int i, final int i2, final int i3, final int i4, final int i5, final boolean z, final boolean z2) {
        executeConsumer(new Consumer() { // from class: com.android.systemui.qs.animator.SecQSFragmentAnimatorManager$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((SecQSFragmentAnimatorBase) obj).setFancyClipping(i, i2, i3, i4, i5, z, z2);
            }
        });
    }

    @Override // com.android.systemui.qs.animator.SecQSFragmentAnimatorBase
    public final void setFullyExpanded(boolean z) {
        if (this.mQsFullyExpanded == z) {
            return;
        }
        SecQSFragmentAnimatorLogger secQSFragmentAnimatorLogger = this.mLogger;
        if (secQSFragmentAnimatorLogger.qsFullyExpanded != z) {
            secQSFragmentAnimatorLogger.printLog("[qsFullyExpanded:" + z + "]");
        }
        secQSFragmentAnimatorLogger.qsFullyExpanded = z;
        executeConsumer(new SecQSFragmentAnimatorManager$$ExternalSyntheticLambda1(z, 0));
        this.mQsFullyExpanded = z;
    }

    @Override // com.android.systemui.qs.animator.SecQSFragmentAnimatorBase
    public final void setNotificationStackScrollerController(NotificationStackScrollLayoutController notificationStackScrollLayoutController) {
        this.mStackScrollerController = notificationStackScrollLayoutController;
        executeConsumer(new SecQSFragmentAnimatorManager$$ExternalSyntheticLambda3(notificationStackScrollLayoutController, 6));
    }

    @Override // com.android.systemui.qs.animator.SecQSFragmentAnimatorBase
    public final void setPanelViewController(NotificationPanelViewController notificationPanelViewController) {
        this.mPanelViewController = notificationPanelViewController;
        executeConsumer(new SecQSFragmentAnimatorManager$$ExternalSyntheticLambda3(notificationPanelViewController, 4));
    }

    @Override // com.android.systemui.qs.animator.SecQSFragmentAnimatorBase
    public final void setQs(InterfaceC1922QS interfaceC1922QS) {
        executeConsumer(new SecQSFragmentAnimatorManager$$ExternalSyntheticLambda3(interfaceC1922QS, 1));
        updateAnimators();
    }

    @Override // com.android.systemui.qs.animator.SecQSFragmentAnimatorBase
    public final void setQsExpanded(boolean z) {
        SecQSFragmentAnimatorLogger secQSFragmentAnimatorLogger = this.mLogger;
        if (secQSFragmentAnimatorLogger.qsExpanded != z) {
            secQSFragmentAnimatorLogger.printLog("[qsExpanded:" + z + "]");
        }
        secQSFragmentAnimatorLogger.qsExpanded = z;
        executeConsumer(new SecQSFragmentAnimatorManager$$ExternalSyntheticLambda1(z, 2));
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0046, code lost:
    
        if ((r9 == 1.0f) != false) goto L29;
     */
    @Override // com.android.systemui.qs.animator.SecQSFragmentAnimatorBase
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setQsExpansionPosition(final float f) {
        SecQSFragmentAnimatorLogger secQSFragmentAnimatorLogger = this.mLogger;
        if (!(secQSFragmentAnimatorLogger.qsExpansionPosition == f)) {
            if (Math.abs(secQSFragmentAnimatorLogger.qsExpansionPositionLastLoggingValue - f) <= 0.1d) {
                float f2 = secQSFragmentAnimatorLogger.qsExpansionPosition;
                if (!(f2 == 0.0f)) {
                    if (!(f2 == 1.0f)) {
                        if (!(f == 0.0f)) {
                        }
                    }
                }
            }
            secQSFragmentAnimatorLogger.printLog("[qsExpansionPosition:" + new DecimalFormat("#.###").format(Float.valueOf(f)) + "]");
            secQSFragmentAnimatorLogger.qsExpansionPositionLastLoggingValue = f;
        }
        secQSFragmentAnimatorLogger.qsExpansionPosition = f;
        executeConsumer(new Consumer() { // from class: com.android.systemui.qs.animator.SecQSFragmentAnimatorManager$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((SecQSFragmentAnimatorBase) obj).setQsExpansionPosition(f);
            }
        });
    }

    @Override // com.android.systemui.qs.animator.SecQSFragmentAnimatorBase
    public final void setStackScrollerOverscrolling(boolean z) {
        executeConsumer(new SecQSFragmentAnimatorManager$$ExternalSyntheticLambda1(z, 1));
    }

    @Override // com.android.systemui.qs.animator.SecQSFragmentAnimatorBase
    public final void updateAnimators() {
        executeConsumer(new SecQSFragmentAnimatorManager$$ExternalSyntheticLambda0(3));
    }

    @Override // com.android.systemui.qs.animator.SecQSFragmentAnimatorBase
    public final void updatePanelExpanded(boolean z) {
        this.mPanelExpanded = z;
        SecQSFragmentAnimatorLogger secQSFragmentAnimatorLogger = this.mLogger;
        if (secQSFragmentAnimatorLogger.panelExpanded != z) {
            secQSFragmentAnimatorLogger.printLog("[panelExpanded:" + z + "]");
        }
        secQSFragmentAnimatorLogger.panelExpanded = z;
        executeConsumer(new SecQSFragmentAnimatorManager$$ExternalSyntheticLambda1(z, 3));
    }
}
