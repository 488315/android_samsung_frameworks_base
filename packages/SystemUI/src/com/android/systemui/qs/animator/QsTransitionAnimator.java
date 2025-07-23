package com.android.systemui.qs.animator;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.plugins.qs.QS;
import com.android.systemui.qs.QSImpl;
import com.android.systemui.qs.SecQSPanelController;
import com.android.systemui.qs.TouchAnimator;
import com.android.systemui.qs.animator.QSAnimViewProvider;
import com.android.systemui.qs.animator.QsTransitionAnimator;
import com.android.systemui.qs.animator.SecQSImplAnimatorManager;
import com.android.systemui.qs.customize.viewcontroller.QSCMainViewController;
import com.android.systemui.shade.PanelPopOverManager;
import com.android.systemui.shade.SecPanelSplitHelper;
import com.android.systemui.shade.ShadeExpansionChangeEvent;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManager;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl;
import com.android.systemui.statusbar.notification.headsup.OnHeadsUpChangedListener;
import com.android.systemui.util.ConfigurationState;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import com.android.systemui.util.SettingsHelper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class QsTransitionAnimator extends SecQSImplAnimatorBase implements SettingsHelper.OnChangedCallback {
    public static final Interpolator INTERPOLATOR;
    public SecQSImplAnimatorManager.AnonymousClass2 animStateCallback;
    public final QsTransitionAnimator$detailAnimListener$1 detailAnimListener;
    public DetailCallback detailCallback;
    public TouchAnimator detailCollapseAnimator;
    public final ArrayList detailContents;
    public AnimatorSet detailHideAnimSet;
    public final ArrayList detailHideAnimators;
    public AnimatorSet detailShowAnimSet;
    public final ArrayList detailShowAnimators;
    public QSAnimView detailView;
    public final Handler handler;
    public QSAnimView headerIcons;
    public QSAnimView headerView;
    public final HeadsUpManager headsUpManager;
    public boolean inPinnedMode;
    public final ConfigurationState lastConfigurationState = new ConfigurationState(Arrays.asList(ConfigurationState.ConfigurationField.ASSET_SEQ, ConfigurationState.ConfigurationField.THEME_SEQ, ConfigurationState.ConfigurationField.UI_MODE));
    public QSAnimView nssl;
    public final QsTransitionAnimator$onHeadsUpChangedListener$1 onHeadsUpChangedListener;
    public final QsTransitionAnimator$panelAnimListener$1 panelAnimListener;
    public final ArrayList panelContents;
    public AnimatorSet panelHideAnimSet;
    public final ArrayList panelHideAnimators;
    public final PanelPopOverManager panelPopOverManager;
    public AnimatorSet panelShowAnimSet;
    public final ArrayList panelShowAnimators;
    public QSAnimView plmn;
    public QSAnimView qsPanel;
    public final SecQSPanelController qsPanelController;
    public final QSCMainViewController qscMainViewController;
    public QSAnimView quickQsPanel;
    private final SettingsHelper settingsHelper;
    public final QSAnimViewProvider viewProvider;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface DetailCallback {
        void hideDetailAnimEnd();

        void showDetailAnimEnd();
    }

    static {
        new Companion(null);
        INTERPOLATOR = new PathInterpolator(0.37f, 0.3f, 0.14f, 1.34f);
    }

    /* JADX WARN: Type inference failed for: r5v12, types: [com.android.systemui.qs.animator.QsTransitionAnimator$onHeadsUpChangedListener$1] */
    /* JADX WARN: Type inference failed for: r5v13, types: [com.android.systemui.qs.animator.QsTransitionAnimator$panelAnimListener$1] */
    /* JADX WARN: Type inference failed for: r5v14, types: [com.android.systemui.qs.animator.QsTransitionAnimator$detailAnimListener$1] */
    public QsTransitionAnimator(SecQSPanelController secQSPanelController, HeadsUpManager headsUpManager, QSAnimViewProvider qSAnimViewProvider, QSCMainViewController qSCMainViewController, PanelPopOverManager panelPopOverManager) {
        this.qsPanelController = secQSPanelController;
        this.headsUpManager = headsUpManager;
        this.viewProvider = qSAnimViewProvider;
        this.qscMainViewController = qSCMainViewController;
        this.panelPopOverManager = panelPopOverManager;
        SettingsHelper settingsHelper = (SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class);
        this.settingsHelper = settingsHelper;
        this.panelContents = new ArrayList();
        this.detailContents = new ArrayList();
        this.panelShowAnimators = new ArrayList();
        this.panelHideAnimators = new ArrayList();
        this.detailShowAnimators = new ArrayList();
        this.detailHideAnimators = new ArrayList();
        this.handler = new Handler(Looper.getMainLooper());
        this.onHeadsUpChangedListener = new OnHeadsUpChangedListener() { // from class: com.android.systemui.qs.animator.QsTransitionAnimator$onHeadsUpChangedListener$1
            @Override // com.android.systemui.statusbar.notification.headsup.OnHeadsUpChangedListener
            public final void onHeadsUpPinnedModeChanged(boolean z) {
                EmergencyButtonController$$ExternalSyntheticOutline0.m("onHeadsUpPinnedModeChanged: ", "QsTransitionAnimator", z);
                QsTransitionAnimator.this.inPinnedMode = z;
            }
        };
        this.panelAnimListener = new Animator.AnimatorListener() { // from class: com.android.systemui.qs.animator.QsTransitionAnimator$panelAnimListener$1
            public boolean isCanceled;

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                this.isCanceled = true;
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                QsTransitionAnimator.this.getClass();
                if (SecQSImplAnimatorBase.isDetailVisible()) {
                    if (QsTransitionAnimator.this.animStateCallback != null) {
                        SecQSImplAnimatorManager.AnonymousClass2.setDetailClosing(false);
                        SecQSImplAnimatorManager.AnonymousClass2 anonymousClass2 = QsTransitionAnimator.this.animStateCallback;
                        if (anonymousClass2 != null) {
                            SecQSImplAnimatorManager.this.executeConsumer(new SecQSImplAnimatorManager$$ExternalSyntheticLambda6(6));
                        }
                    }
                    QsTransitionAnimator.this.clearDetailView();
                    ((View) QsTransitionAnimator.this.qsPanelController.mTileLayout).setVisibility(0);
                }
                if (!this.isCanceled) {
                    QsTransitionAnimator.this.getClass();
                    if (QsAnimatorState.isCustomizerShowing) {
                        if (QsTransitionAnimator.this.animStateCallback != null) {
                            SecQSImplAnimatorManager.AnonymousClass2.setDetailClosing(false);
                        }
                        QsTransitionAnimator.this.clearCustomizerView();
                    }
                }
                this.isCanceled = false;
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                QSAnimView qSAnimView = QsTransitionAnimator.this.viewProvider.get(QSAnimViewProvider.ViewType.NSSL);
                SecPanelSplitHelper.Companion.getClass();
                if (SecPanelSplitHelper.isEnabled || qSAnimView == null || qSAnimView.getVisibility() != 4) {
                    return;
                }
                qSAnimView.setVisibility(0);
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationRepeat(Animator animator) {
            }
        };
        this.detailAnimListener = new Animator.AnimatorListener() { // from class: com.android.systemui.qs.animator.QsTransitionAnimator$detailAnimListener$1
            public boolean isCanceled;

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                this.isCanceled = true;
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                QSAnimView qSAnimView;
                if (QsAnimatorState.panelExpanded) {
                    ArrayList arrayList = QsTransitionAnimator.this.detailContents;
                    int size = arrayList.size();
                    int i = 0;
                    while (i < size) {
                        Object obj = arrayList.get(i);
                        i++;
                        QSAnimView qSAnimView2 = (QSAnimView) obj;
                        if (qSAnimView2 != null) {
                            qSAnimView2.setAlpha(1.0f);
                        }
                    }
                    QsTransitionAnimator.DetailCallback detailCallback = QsTransitionAnimator.this.detailCallback;
                    if (detailCallback != null) {
                        detailCallback.showDetailAnimEnd();
                    }
                    if (QsTransitionAnimator.this.animStateCallback != null) {
                        SecQSImplAnimatorManager.AnonymousClass2.setDetailOpening(false);
                        QsAnimatorState.setDetailShowing(true);
                    }
                    SecPanelSplitHelper.Companion.getClass();
                    if (!SecPanelSplitHelper.isEnabled && !QsAnimatorState.qsExpanded && !this.isCanceled && (qSAnimView = QsTransitionAnimator.this.nssl) != null) {
                        qSAnimView.setVisibility(4);
                    }
                } else if (QsTransitionAnimator.this.animStateCallback != null) {
                    SecQSImplAnimatorManager.AnonymousClass2.setDetailClosing(false);
                    QsAnimatorState.setDetailShowing(false);
                }
                this.isCanceled = false;
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
            }
        };
        settingsHelper.registerCallback(this, Settings.Global.getUriFor(SettingsHelper.INDEX_GLOBAL_ANIMATOR_DURATION_SCALE));
        this.headerIcons = qSAnimViewProvider.get(QSAnimViewProvider.ViewType.SHADE_HEADER);
        this.plmn = qSAnimViewProvider.get(QSAnimViewProvider.ViewType.SHADE_HEADER_PLMN);
        this.inPinnedMode = ((HeadsUpManagerImpl) headsUpManager).mHasPinnedNotification;
    }

    public final void clearCustomizerView() {
        if (!isThereNoView()) {
            this.qscMainViewController.close();
        }
        SecQSImplAnimatorManager.AnonymousClass2 anonymousClass2 = this.animStateCallback;
        if (anonymousClass2 != null) {
            SecQSImplAnimatorManager.this.executeConsumer(new SecQSImplAnimatorManager$$ExternalSyntheticLambda6(6));
            SecQSImplAnimatorManager.AnonymousClass2.setCustomizerShowing(false);
        }
    }

    public final void clearDetailView() {
        QSAnimView qSAnimView;
        if (!isThereNoView() && (qSAnimView = this.detailView) != null) {
            qSAnimView.setAlpha(0.0f);
            qSAnimView.setTranslationY(0.0f);
        }
        DetailCallback detailCallback = this.detailCallback;
        if (detailCallback != null) {
            detailCallback.hideDetailAnimEnd();
        }
        SecQSImplAnimatorManager.AnonymousClass2 anonymousClass2 = this.animStateCallback;
        if (anonymousClass2 != null) {
            SecQSImplAnimatorManager.this.executeConsumer(new SecQSImplAnimatorManager$$ExternalSyntheticLambda6(6));
            SecQSImplAnimatorManager.AnonymousClass2.setDetailOpening(false);
            QsAnimatorState.setDetailShowing(false);
            SecQSImplAnimatorManager.AnonymousClass2.setDetailClosing(false);
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void destroyQSViews() {
        clearDetailView();
        clearCustomizerView();
        this.panelContents.clear();
        this.detailContents.clear();
        this.headerView = null;
        this.qsPanel = null;
        this.nssl = null;
        this.quickQsPanel = null;
        this.detailView = null;
        this.headerIcons = null;
        this.plmn = null;
        this.mQs = null;
        this.settingsHelper.unregisterCallback(this);
        this.detailCallback = null;
        ((HeadsUpManagerImpl) this.headsUpManager).removeListener(this.onHeadsUpChangedListener);
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase, com.android.systemui.logging.PanelScreenShotLogger.LogProvider
    public final ArrayList gatherState() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("QsTransitionAnimator ============================================= ");
        SecQSImplAnimatorBase.gatherStateOfAnimViews(arrayList, this.panelContents, " panelContents ");
        SecQSImplAnimatorBase.gatherStateOfAnimViews(arrayList, this.detailContents, " detailContents ");
        arrayList.add("============================================================== ");
        return arrayList;
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final boolean isThereNoView() {
        return super.isThereNoView() || this.inPinnedMode;
    }

    public final Animator makeTransitionAnimator(QSAnimView qSAnimView, long j, float f, boolean z) {
        PropertyValuesHolder[] propertyValuesHolderArr = new PropertyValuesHolder[3];
        propertyValuesHolderArr[0] = PropertyValuesHolder.ofFloat("alpha", f);
        float f2 = this.SCALE_DOWN_RATIO;
        propertyValuesHolderArr[1] = PropertyValuesHolder.ofFloat("scaleX", z ? 1.0f : f2);
        if (z) {
            f2 = 1.0f;
        }
        propertyValuesHolderArr[2] = PropertyValuesHolder.ofFloat("scaleY", f2);
        ObjectAnimator ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(qSAnimView, propertyValuesHolderArr);
        ofPropertyValuesHolder.setDuration(j);
        ofPropertyValuesHolder.setStartDelay(0L);
        ofPropertyValuesHolder.setInterpolator(INTERPOLATOR);
        return ofPropertyValuesHolder;
    }

    @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
    public final void onChanged(Uri uri) {
        if (Intrinsics.areEqual(uri, Settings.Global.getUriFor(SettingsHelper.INDEX_GLOBAL_ANIMATOR_DURATION_SCALE))) {
            this.settingsHelper.isAnimationRemoved();
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void onConfigurationChanged(Configuration configuration) {
        ConfigurationState configurationState = this.lastConfigurationState;
        if (configurationState.needToUpdate(configuration)) {
            this.mQs.getView().post(new Runnable() { // from class: com.android.systemui.qs.animator.QsTransitionAnimator$onConfigurationChanged$1
                @Override // java.lang.Runnable
                public final void run() {
                    QsTransitionAnimator.this.updateAnimators();
                }
            });
            configurationState.update(configuration);
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void onPanelClosed$1() {
        if (isThereNoView()) {
            return;
        }
        clearDetailView();
        clearCustomizerView();
        restorePanelView();
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase, com.android.systemui.shade.ShadeExpansionListener
    public final void onPanelExpansionChanged(ShadeExpansionChangeEvent shadeExpansionChangeEvent) {
        QSAnimView qSAnimView;
        TouchAnimator touchAnimator;
        boolean z = QsAnimatorState.isDetailShowing;
        float f = shadeExpansionChangeEvent.fraction;
        if (z && !QsAnimatorState.isDetailClosing && (touchAnimator = this.detailCollapseAnimator) != null) {
            touchAnimator.setPosition(f);
        }
        if (f < 1.0f) {
            this.qscMainViewController.setPosition$2(f);
        }
        if (f == 0.0f) {
            if (this.animStateCallback != null) {
                SecQSImplAnimatorManager.AnonymousClass2.setDetailOpening(false);
                QsAnimatorState.setDetailShowing(false);
                SecQSImplAnimatorManager.AnonymousClass2.setDetailClosing(false);
                return;
            }
            return;
        }
        if (f != 1.0f || SecQSImplAnimatorBase.isDetailVisible() || (qSAnimView = this.detailView) == null || qSAnimView.getVisibility() != 0) {
            return;
        }
        clearDetailView();
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void onStateChanged(int i) {
        QsAnimatorState.state = i;
        if (i == 1) {
            if (SecQSImplAnimatorBase.isDetailVisible()) {
                clearDetailView();
            }
            if (QsAnimatorState.isCustomizerShowing) {
                clearCustomizerView();
            }
            restorePanelView();
        }
    }

    public final void restorePanelView() {
        if (isThereNoView()) {
            return;
        }
        Iterator it = this.panelContents.iterator();
        while (it.hasNext()) {
            QSAnimView qSAnimView = (QSAnimView) it.next();
            if (qSAnimView != null) {
                qSAnimView.setAlpha(1.0f);
                qSAnimView.setScaleX(1.0f);
                qSAnimView.setScaleY(1.0f);
            }
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void setQs(QS qs) {
        if (qs == null) {
            destroyQSViews();
        } else {
            this.mQs = (QSImpl) qs;
            ((HeadsUpManagerImpl) this.headsUpManager).addListener(this.onHeadsUpChangedListener);
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void setQsExpanded(boolean z) {
        QsAnimatorState.qsExpanded = z;
        if (z || QsAnimatorState.panelExpanded) {
            return;
        }
        restorePanelView();
    }

    public final void showQsPanel(boolean z) {
        if (isThereNoView()) {
            return;
        }
        PanelPopOverManager panelPopOverManager = this.panelPopOverManager;
        if (z) {
            panelPopOverManager.transitionBlurAnim(false);
            AnimatorSet animatorSet = this.panelHideAnimSet;
            if (animatorSet != null) {
                animatorSet.cancel();
            }
            AnimatorSet animatorSet2 = this.panelShowAnimSet;
            if (animatorSet2 != null) {
                animatorSet2.cancel();
                animatorSet2.start();
            }
        } else {
            panelPopOverManager.transitionBlurAnim(true);
            AnimatorSet animatorSet3 = this.panelShowAnimSet;
            if (animatorSet3 != null) {
                animatorSet3.cancel();
            }
            AnimatorSet animatorSet4 = this.panelHideAnimSet;
            if (animatorSet4 != null) {
                animatorSet4.cancel();
                animatorSet4.start();
            }
        }
        QSAnimView qSAnimView = this.headerView;
        View view = qSAnimView != null ? qSAnimView.getView() : null;
        ViewGroup viewGroup = view instanceof ViewGroup ? (ViewGroup) view : null;
        if (viewGroup != null) {
            viewGroup.setDescendantFocusability(z ? 262144 : 393216);
        }
    }

    public final void showQsPanelForCustomizer(final boolean z) {
        Handler handler = this.handler;
        handler.removeCallbacksAndMessages(null);
        makeTransitionAnimator(this.headerIcons, 350L, z ? 1.0f : 0.0f, true).start();
        handler.postDelayed(new Runnable() { // from class: com.android.systemui.qs.animator.QsTransitionAnimator$showQsPanelForCustomizer$1
            /* JADX WARN: Code restructure failed: missing block: B:4:0x0008, code lost:
            
                if (com.android.systemui.qs.animator.QsAnimatorState.state != 1) goto L8;
             */
            @Override // java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void run() {
                /*
                    r3 = this;
                    boolean r0 = r1
                    r1 = 0
                    if (r0 == 0) goto Lb
                    int r0 = com.android.systemui.qs.animator.QsAnimatorState.state
                    r2 = 1
                    if (r0 == r2) goto Lb
                    goto Lc
                Lb:
                    r2 = r1
                Lc:
                    com.android.systemui.qs.animator.QsTransitionAnimator r0 = r2
                    com.android.systemui.qs.SecQSPanelController r0 = r0.qsPanelController
                    r0.setGridContentVisibility(r2)
                    com.android.systemui.qs.animator.QsTransitionAnimator r3 = r2
                    com.android.systemui.qs.animator.QSAnimView r3 = r3.headerIcons
                    if (r3 == 0) goto L20
                    if (r2 == 0) goto L1c
                    goto L1d
                L1c:
                    r1 = 4
                L1d:
                    r3.setVisibility(r1)
                L20:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.animator.QsTransitionAnimator$showQsPanelForCustomizer$1.run():void");
            }
        }, z ? 0L : 350L);
        showQsPanel(z);
    }

    public final void transitionDetail(boolean z) {
        if (isThereNoView() || !this.mAnimatorsInitialiezed) {
            return;
        }
        if (!z) {
            if (this.animStateCallback != null) {
                QsAnimatorState.setDetailShowing(false);
                SecQSImplAnimatorManager.AnonymousClass2.setDetailClosing(true);
            }
            if (!QsAnimatorState.panelExpanded) {
                if (isThereNoView()) {
                    return;
                }
                AnimatorSet animatorSet = this.panelHideAnimSet;
                if (animatorSet != null) {
                    animatorSet.cancel();
                }
                AnimatorSet animatorSet2 = this.panelShowAnimSet;
                if (animatorSet2 != null) {
                    animatorSet2.cancel();
                }
                Iterator it = this.panelContents.iterator();
                while (it.hasNext()) {
                    ObjectAnimator ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder((QSAnimView) it.next(), PropertyValuesHolder.ofFloat("alpha", 1.0f), PropertyValuesHolder.ofFloat("scaleX", 1.0f), PropertyValuesHolder.ofFloat("scaleY", 1.0f));
                    ofPropertyValuesHolder.setDuration(100L);
                    ofPropertyValuesHolder.setInterpolator(INTERPOLATOR);
                    ofPropertyValuesHolder.setStartDelay(0L);
                    ofPropertyValuesHolder.start();
                }
                ArrayList arrayList = this.detailContents;
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    ObjectAnimator ofPropertyValuesHolder2 = ObjectAnimator.ofPropertyValuesHolder((QSAnimView) obj, PropertyValuesHolder.ofFloat("alpha", 0.0f));
                    ofPropertyValuesHolder2.setDuration(50L);
                    ofPropertyValuesHolder2.setInterpolator(INTERPOLATOR);
                    ofPropertyValuesHolder2.setStartDelay(0L);
                    ofPropertyValuesHolder2.start();
                }
                DetailCallback detailCallback = this.detailCallback;
                if (detailCallback != null) {
                    detailCallback.hideDetailAnimEnd();
                }
                if (this.animStateCallback != null) {
                    SecQSImplAnimatorManager.AnonymousClass2.setDetailClosing(false);
                    return;
                }
                return;
            }
        } else if (this.animStateCallback != null) {
            SecQSImplAnimatorManager.AnonymousClass2.setDetailOpening(true);
        }
        showQsPanel(!z);
        if (isThereNoView()) {
            return;
        }
        if (!z) {
            AnimatorSet animatorSet3 = this.detailShowAnimSet;
            if (animatorSet3 != null) {
                animatorSet3.cancel();
            }
            AnimatorSet animatorSet4 = this.detailHideAnimSet;
            if (animatorSet4 != null) {
                animatorSet4.start();
                return;
            }
            return;
        }
        QSAnimView qSAnimView = this.detailView;
        if (qSAnimView != null) {
            qSAnimView.setAlpha(0.0f);
            float f = this.SCALE_DOWN_RATIO;
            qSAnimView.setScaleX(f);
            qSAnimView.setScaleY(f);
        }
        AnimatorSet animatorSet5 = this.detailHideAnimSet;
        if (animatorSet5 != null) {
            animatorSet5.cancel();
        }
        AnimatorSet animatorSet6 = this.detailShowAnimSet;
        if (animatorSet6 != null) {
            animatorSet6.start();
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void updateAnimators() {
        View view;
        if (isThereNoView()) {
            return;
        }
        if (isThereNoView()) {
            destroyQSViews();
        } else {
            this.panelContents.clear();
            this.detailContents.clear();
            QSAnimViewProvider.ViewType viewType = QSAnimViewProvider.ViewType.QS_HEADER;
            QSAnimViewProvider qSAnimViewProvider = this.viewProvider;
            QSAnimView qSAnimView = qSAnimViewProvider.get(viewType);
            if (qSAnimView != null) {
                this.quickQsPanel = qSAnimViewProvider.get(QSAnimViewProvider.ViewType.QS_HEADER_QQS);
                this.panelContents.add(qSAnimViewProvider.get(QSAnimViewProvider.ViewType.QQS_HEADER_BUTTON_CONTAINER));
                this.panelContents.add(this.quickQsPanel);
            } else {
                qSAnimView = null;
            }
            this.headerView = qSAnimView;
            this.qsPanel = qSAnimViewProvider.get(QSAnimViewProvider.ViewType.ROOT_VIEW);
            this.nssl = qSAnimViewProvider.get(QSAnimViewProvider.ViewType.NSSL);
            this.panelContents.add(this.qsPanel);
            this.panelContents.add(this.nssl);
            this.panelContents.add(this.plmn);
            QSAnimView qSAnimView2 = qSAnimViewProvider.get(QSAnimViewProvider.ViewType.DETAIL);
            this.detailView = qSAnimView2;
            this.detailContents.add(qSAnimView2);
        }
        this.panelHideAnimSet = new AnimatorSet();
        this.panelHideAnimators.clear();
        ArrayList arrayList = this.panelContents;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            QSAnimView qSAnimView3 = (QSAnimView) obj;
            QsTransitionAnimator qsTransitionAnimator = this;
            this.panelHideAnimators.add(qsTransitionAnimator.makeTransitionAnimator(qSAnimView3, 350L, 0.0f, qSAnimView3 == this.quickQsPanel || ((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isTablet()));
            this = qsTransitionAnimator;
        }
        QsTransitionAnimator qsTransitionAnimator2 = this;
        AnimatorSet animatorSet = qsTransitionAnimator2.panelHideAnimSet;
        if (animatorSet != null) {
            animatorSet.playTogether(qsTransitionAnimator2.panelHideAnimators);
        }
        qsTransitionAnimator2.panelShowAnimSet = new AnimatorSet();
        qsTransitionAnimator2.panelShowAnimators.clear();
        ArrayList arrayList2 = qsTransitionAnimator2.panelContents;
        int size2 = arrayList2.size();
        int i2 = 0;
        while (i2 < size2) {
            Object obj2 = arrayList2.get(i2);
            i2++;
            qsTransitionAnimator2.panelShowAnimators.add(qsTransitionAnimator2.makeTransitionAnimator((QSAnimView) obj2, 350L, 1.0f, true));
        }
        AnimatorSet animatorSet2 = qsTransitionAnimator2.panelShowAnimSet;
        if (animatorSet2 != null) {
            animatorSet2.playTogether(qsTransitionAnimator2.panelShowAnimators);
            animatorSet2.addListener(qsTransitionAnimator2.panelAnimListener);
        }
        qsTransitionAnimator2.detailShowAnimSet = new AnimatorSet();
        qsTransitionAnimator2.detailShowAnimators.clear();
        ArrayList arrayList3 = qsTransitionAnimator2.detailContents;
        int size3 = arrayList3.size();
        int i3 = 0;
        while (i3 < size3) {
            Object obj3 = arrayList3.get(i3);
            i3++;
            qsTransitionAnimator2.detailShowAnimators.add(qsTransitionAnimator2.makeTransitionAnimator((QSAnimView) obj3, 480L, 1.0f, true));
        }
        AnimatorSet animatorSet3 = qsTransitionAnimator2.detailShowAnimSet;
        if (animatorSet3 != null) {
            animatorSet3.playTogether(qsTransitionAnimator2.detailShowAnimators);
            animatorSet3.addListener(qsTransitionAnimator2.detailAnimListener);
        }
        qsTransitionAnimator2.detailHideAnimSet = new AnimatorSet();
        qsTransitionAnimator2.detailHideAnimators.clear();
        ArrayList arrayList4 = qsTransitionAnimator2.detailContents;
        int size4 = arrayList4.size();
        int i4 = 0;
        while (i4 < size4) {
            Object obj4 = arrayList4.get(i4);
            i4++;
            qsTransitionAnimator2.detailHideAnimators.add(qsTransitionAnimator2.makeTransitionAnimator((QSAnimView) obj4, 300L, 0.0f, false));
        }
        AnimatorSet animatorSet4 = qsTransitionAnimator2.detailHideAnimSet;
        if (animatorSet4 != null) {
            animatorSet4.playTogether(qsTransitionAnimator2.detailHideAnimators);
        }
        TouchAnimator.Builder builder = new TouchAnimator.Builder();
        builder.addFloat(qsTransitionAnimator2.detailView, "alpha", 0.0f, 1.0f);
        QSAnimView qSAnimView4 = qsTransitionAnimator2.detailView;
        QSAnimView qSAnimView5 = qsTransitionAnimator2.headerView;
        builder.addFloat(qSAnimView4, "translationY", (-((qSAnimView5 == null || (view = qSAnimView5.getView()) == null) ? 0 : view.getHeight())) * 0.2f, 0.0f);
        qsTransitionAnimator2.detailCollapseAnimator = builder.build();
        qsTransitionAnimator2.mAnimatorsInitialiezed = true;
    }
}
