package com.android.systemui.qs.animator;

import android.content.Context;
import android.util.Log;
import android.view.View;
import androidx.appcompat.widget.MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.picker3.widget.SeslColorSpectrumView$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.plugins.qs.QS;
import com.android.systemui.qs.QSImpl;
import com.android.systemui.qs.TouchAnimator;
import com.android.systemui.qs.animator.QSAnimViewProvider;
import com.android.systemui.qs.bar.domain.interactor.BarOrderInteractor;
import com.android.systemui.samsung.quicksetting.SecQSPanelComposeAdapter;
import com.android.systemui.shade.PanelSlideEventHandler;
import com.android.systemui.shade.PanelTransitionStateChangeEvent;
import com.android.systemui.shade.SecPanelSplitHelper;
import com.android.systemui.shade.ShadeExpansionChangeEvent;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManager;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl;
import com.android.systemui.statusbar.notification.headsup.OnHeadsUpChangedListener;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import java.util.ArrayList;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class PanelSplitOpenAnimator extends SecQSImplAnimatorBase {
    public final BarOrderInteractor barOrderInteractor;
    public QSAnimView blurParent;
    public QSAnimView blurView;
    public TouchAnimator blurViewAnimator;
    public TouchAnimator buttonAnimator;
    public QSAnimView buttonContainer;
    public TouchAnimator clockDateAnimator;
    public QSAnimView clockDateContainer;
    public TouchAnimator containerViewAnimator;
    public final Context context;
    public QSAnimView header;
    public TouchAnimator headerAnimator;
    public final HeadsUpManager headsUpManager;
    public boolean inPinnedMode;
    public QSAnimView largeShadowView;
    public QSAnimView lessBlurView;
    public float openPosition;
    public float overExpansionAmount;
    public final float overExpansionMaxAmount;
    public TouchAnimator panelBarAnimator;
    public final SecQsUiDisplayModeInteractor secQsUiDisplayModeInteractor;
    public QSAnimView smallShadowView;
    public final QSAnimViewProvider viewProvider;
    public final PanelSplitOpenAnimator$onHeadsUpChangedListener$1 onHeadsUpChangedListener = new OnHeadsUpChangedListener() { // from class: com.android.systemui.qs.animator.PanelSplitOpenAnimator$onHeadsUpChangedListener$1
        @Override // com.android.systemui.statusbar.notification.headsup.OnHeadsUpChangedListener
        public final void onHeadsUpPinnedModeChanged(boolean z) {
            EmergencyButtonController$$ExternalSyntheticOutline0.m("onHeadsUpPinnedModeChanged: ", "PanelSplitOpenAnimator", z);
            PanelSplitOpenAnimator.this.inPinnedMode = z;
            SecPanelSplitHelper.Companion.getClass();
        }
    };
    public final ArrayList panelBarViews = new ArrayList();
    public final ArrayList overExpansionBarAnimators = new ArrayList();
    public final float barYDiff = 300.0f;
    public final float buttonYDiff = 50.0f;
    public float xDiff = 100.0f;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.systemui.qs.animator.PanelSplitOpenAnimator$onHeadsUpChangedListener$1] */
    public PanelSplitOpenAnimator(Context context, QSAnimViewProvider qSAnimViewProvider, BarOrderInteractor barOrderInteractor, HeadsUpManager headsUpManager, SecQSPanelComposeAdapter secQSPanelComposeAdapter, SecQsUiDisplayModeInteractor secQsUiDisplayModeInteractor) {
        this.context = context;
        this.viewProvider = qSAnimViewProvider;
        this.barOrderInteractor = barOrderInteractor;
        this.headsUpManager = headsUpManager;
        this.secQsUiDisplayModeInteractor = secQsUiDisplayModeInteractor;
        this.overExpansionMaxAmount = context.getResources().getDimension(R.dimen.panel_overshoot_amount) * 1.5f;
        this.inPinnedMode = ((HeadsUpManagerImpl) headsUpManager).mHasPinnedNotification;
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void clearAnimationState() {
        View view;
        Log.d("PanelSplitOpenAnimator", "clearAnimationState");
        if (isThereNoView()) {
            return;
        }
        QSAnimView qSAnimView = this.header;
        if (qSAnimView != null) {
            qSAnimView.setAlpha(QsAnimatorState.state == 1 ? 0.0f : 1.0f);
            qSAnimView.setTranslationY(0.0f);
        }
        QSAnimView qSAnimView2 = this.clockDateContainer;
        if (qSAnimView2 != null) {
            qSAnimView2.setAlpha(1.0f);
            qSAnimView2.setTranslationX(0.0f);
            qSAnimView2.setTranslationY(0.0f);
            qSAnimView2.setScaleX(1.0f);
            qSAnimView2.setScaleY(1.0f);
        }
        QSAnimView qSAnimView3 = this.buttonContainer;
        if (qSAnimView3 != null) {
            qSAnimView3.setAlpha(1.0f);
            qSAnimView3.setTranslationX(0.0f);
            qSAnimView3.setTranslationY(0.0f);
            qSAnimView3.setScaleX(1.0f);
            qSAnimView3.setScaleY(1.0f);
        }
        QSImpl qSImpl = this.mQs;
        if (qSImpl != null && (view = qSImpl.getView()) != null) {
            view.setAlpha(1.0f);
            view.setTranslationY(0.0f);
        }
        ArrayList arrayList = this.panelBarViews;
        int size = arrayList.size();
        int i = 0;
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            QSAnimView qSAnimView4 = (QSAnimView) obj;
            qSAnimView4.setAlpha(1.0f);
            qSAnimView4.setScaleX(1.0f);
            qSAnimView4.setScaleY(1.0f);
        }
        this.panelBarViews.clear();
        QSAnimView qSAnimView5 = this.blurParent;
        if (qSAnimView5 != null) {
            qSAnimView5.setScaleX(1.0f);
            qSAnimView5.setScaleY(1.0f);
            qSAnimView5.setTranslationY(0.0f);
        }
        QSAnimView qSAnimView6 = this.largeShadowView;
        if (qSAnimView6 != null) {
            qSAnimView6.setScaleX(1.0f);
            qSAnimView6.setScaleY(1.0f);
            qSAnimView6.setTranslationY(0.0f);
        }
        QSAnimView qSAnimView7 = this.smallShadowView;
        if (qSAnimView7 != null) {
            qSAnimView7.setScaleX(1.0f);
            qSAnimView7.setScaleY(1.0f);
            qSAnimView7.setTranslationY(0.0f);
        }
        ArrayList bars = getBars();
        int size2 = bars.size();
        while (i < size2) {
            Object obj2 = bars.get(i);
            i++;
            ((View) obj2).setTranslationY(0.0f);
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void destroyQSViews() {
        ((HeadsUpManagerImpl) this.headsUpManager).removeListener(this.onHeadsUpChangedListener);
        this.header = null;
        this.clockDateContainer = null;
        this.buttonContainer = null;
        this.blurView = null;
        this.lessBlurView = null;
        this.largeShadowView = null;
        this.smallShadowView = null;
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase, com.android.systemui.logging.PanelScreenShotLogger.LogProvider
    public final ArrayList gatherState() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("PanelSplitOpenAnimator ============================================= ");
        SecQSImplAnimatorBase.gatherStateOfAnimViews(arrayList, this.panelBarViews, "");
        arrayList.add("============================================================== ");
        return arrayList;
    }

    public final ArrayList getBars() {
        boolean isTablet = this.secQsUiDisplayModeInteractor.isTablet();
        BarOrderInteractor barOrderInteractor = this.barOrderInteractor;
        return isTablet ? barOrderInteractor.getBarViewsByOrder() : this.context.getResources().getConfiguration().orientation == 2 ? barOrderInteractor.landscapeBars : barOrderInteractor.getBarViewsByOrder();
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final boolean isThereNoView() {
        if (super.isThereNoView()) {
            return true;
        }
        SecPanelSplitHelper.Companion.getClass();
        return !SecPanelSplitHelper.isEnabled || QsAnimatorState.isDetailShowing || this.inPinnedMode;
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void onPanelClosed$1() {
        updateAnimators();
        clearAnimationState();
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase, com.android.systemui.shade.ShadeExpansionListener
    public final void onPanelExpansionChanged(ShadeExpansionChangeEvent shadeExpansionChangeEvent) {
        if (isThereNoView()) {
            return;
        }
        int i = QsAnimatorState.state;
        StringBuilder sb = new StringBuilder("onPanelExpansionChanged : ");
        float f = shadeExpansionChangeEvent.fraction;
        sb.append(f);
        sb.append(" state : ");
        sb.append(i);
        Log.d("PanelSplitOpenAnimator", sb.toString());
        if (QsAnimatorState.state != 1) {
            setPosition$1(f);
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void onPanelTransitionStateChanged(PanelTransitionStateChangeEvent panelTransitionStateChangeEvent) {
        int i = this.mPanelState;
        int i2 = panelTransitionStateChangeEvent.state;
        if (i != i2) {
            this.mPanelState = i2;
        }
        boolean z = this.mPanelSplitEnabled;
        boolean z2 = panelTransitionStateChangeEvent.enabled;
        if (z != z2) {
            this.mPanelSplitEnabled = z2;
            if (z2) {
                updateAnimators();
            }
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void onStateChanged(int i) {
        QsAnimatorState.state = i;
        if (i == 1) {
            clearAnimationState();
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void onUserSwitched(int i) {
        this.mUserChanged = true;
        SecPanelSplitHelper.Companion.getClass();
        if (SecPanelSplitHelper.isEnabled) {
            setPosition$1(1.0f);
            QSAnimView qSAnimView = this.header;
            if (qSAnimView != null) {
                qSAnimView.setAlpha(0.0f);
            }
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void setOverDragAmount(float f) {
        setOverScrollAmount(f);
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void setOverScrollAmount(float f) {
        if (isThereNoView()) {
            return;
        }
        Log.d("PanelSplitOpenAnimator", "setOverScrollAmount : " + f);
        this.overExpansionAmount = f;
        float f2 = f / this.overExpansionMaxAmount;
        ArrayList arrayList = this.overExpansionBarAnimators;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((TouchAnimator) obj).setPosition(f2);
        }
    }

    public final void setPosition$1(float f) {
        boolean z = this.mUserChanged;
        this.mUserChanged = false;
        boolean isThereNoView = isThereNoView();
        boolean z2 = QsAnimatorState.isSliding;
        float f2 = this.overExpansionAmount;
        StringBuilder sb = new StringBuilder("setPosition : ");
        sb.append(f);
        sb.append(" // ");
        sb.append(!z);
        sb.append(" ");
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sb, isThereNoView, " ", z2, " ");
        SeslColorSpectrumView$$ExternalSyntheticOutline0.m(f2, "PanelSplitOpenAnimator", sb);
        if (!z && (isThereNoView() || QsAnimatorState.isSliding || QsAnimatorState.isDetailShowing || QsAnimatorState.isDetailPopupShowing || (this.openPosition == 1.0f && this.overExpansionAmount > 0.0f))) {
            Log.d("PanelSplitOpenAnimator", "setPosition : " + f + ", forced return!!");
            return;
        }
        this.openPosition = f;
        TouchAnimator touchAnimator = this.headerAnimator;
        if (touchAnimator != null) {
            touchAnimator.setPosition(f);
        }
        TouchAnimator touchAnimator2 = this.clockDateAnimator;
        if (touchAnimator2 != null) {
            touchAnimator2.setPosition(f);
        }
        TouchAnimator touchAnimator3 = this.buttonAnimator;
        if (touchAnimator3 != null) {
            touchAnimator3.setPosition(f);
        }
        TouchAnimator touchAnimator4 = this.blurViewAnimator;
        if (touchAnimator4 != null) {
            touchAnimator4.setPosition(f);
        }
        TouchAnimator touchAnimator5 = this.containerViewAnimator;
        if (touchAnimator5 != null) {
            touchAnimator5.setPosition(f);
        }
        TouchAnimator touchAnimator6 = this.panelBarAnimator;
        if (touchAnimator6 != null) {
            touchAnimator6.setPosition(f);
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void setQs(QS qs) {
        if (qs == null) {
            destroyQSViews();
            return;
        }
        this.mQs = (QSImpl) qs;
        QSAnimViewProvider.ViewType viewType = QSAnimViewProvider.ViewType.SHADE_HEADER;
        QSAnimViewProvider qSAnimViewProvider = this.viewProvider;
        this.header = qSAnimViewProvider.get(viewType);
        this.clockDateContainer = qSAnimViewProvider.get(QSAnimViewProvider.ViewType.QS_HEADER_CLOCK_DATE_PARENT);
        this.buttonContainer = qSAnimViewProvider.get(QSAnimViewProvider.ViewType.QS_HEADER_BUTTON_CONTAINER);
        this.blurParent = qSAnimViewProvider.get(QSAnimViewProvider.ViewType.BLUR_PARENT);
        this.blurView = qSAnimViewProvider.get(QSAnimViewProvider.ViewType.BLUR_VIEW);
        this.lessBlurView = qSAnimViewProvider.get(QSAnimViewProvider.ViewType.LESS_BLUR_VIEW);
        this.largeShadowView = qSAnimViewProvider.get(QSAnimViewProvider.ViewType.LARGE_SHADOW_VIEW);
        this.smallShadowView = qSAnimViewProvider.get(QSAnimViewProvider.ViewType.SMALL_SHADOW_VIEW);
        QSImpl qSImpl = this.mQs;
        if (qSImpl != null) {
            qSImpl.getView();
        }
        updateAnimators();
        ((HeadsUpManagerImpl) this.headsUpManager).addListener(this.onHeadsUpChangedListener);
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void setTransitionToFullShadeAmount(float f) {
        if (!isThereNoView() && QsAnimatorState.state == 1) {
            Log.d("PanelSplitOpenAnimator", "################ setTransitionToFullShadeAmount : " + f);
            setPosition$1(f);
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void slide(float f, float f2, PanelSlideEventHandler.Direction direction, int i) {
        if (QsAnimatorState.state != 1 || this.openPosition == 1.0f) {
            return;
        }
        this.openPosition = 1.0f;
        TouchAnimator touchAnimator = this.containerViewAnimator;
        if (touchAnimator != null) {
            touchAnimator.setPosition(1.0f);
        }
        TouchAnimator touchAnimator2 = this.panelBarAnimator;
        if (touchAnimator2 != null) {
            touchAnimator2.setPosition(1.0f);
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void updateAnimators() {
        float f;
        View view;
        if (super.isThereNoView()) {
            return;
        }
        SecPanelSplitHelper.Companion.getClass();
        if (!SecPanelSplitHelper.isEnabled || QsAnimatorState.isDetailShowing) {
            return;
        }
        Log.d("PanelSplitOpenAnimator", "updateAnimators");
        SecQsUiDisplayModeInteractor secQsUiDisplayModeInteractor = this.secQsUiDisplayModeInteractor;
        this.xDiff = secQsUiDisplayModeInteractor.isTablet() ? 0.0f : 100.0f;
        float f2 = secQsUiDisplayModeInteractor.isTablet() ? 0.5f : 0.0f;
        boolean isTablet = secQsUiDisplayModeInteractor.isTablet();
        float f3 = this.buttonYDiff;
        float f4 = this.barYDiff;
        float f5 = isTablet ? 0.0f : f4 - f3;
        QSAnimView qSAnimView = this.header;
        if (qSAnimView != null) {
            TouchAnimator.Builder builder = new TouchAnimator.Builder();
            builder.addFloat(qSAnimView, "alpha", 0.0f, 1.0f);
            builder.addFloat(qSAnimView, "translationY", -f3, 0.0f);
            this.headerAnimator = builder.build();
        }
        QSAnimView qSAnimView2 = this.clockDateContainer;
        if (qSAnimView2 != null) {
            TouchAnimator.Builder builder2 = new TouchAnimator.Builder();
            f = 1.0f;
            builder2.addFloat(qSAnimView2, "alpha", 0.0f, 1.0f);
            builder2.addFloat(qSAnimView2, "translationX", MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0.m(this.context) == 1 ? this.xDiff : -this.xDiff, 0.0f);
            builder2.addFloat(qSAnimView2, "translationY", f5, 0.0f);
            builder2.addFloat(qSAnimView2, "scaleX", 0.8f, 1.0f);
            builder2.addFloat(qSAnimView2, "scaleY", 0.8f, 1.0f);
            builder2.mStartDelay = f2;
            this.clockDateAnimator = builder2.build();
        } else {
            f = 1.0f;
        }
        QSAnimView qSAnimView3 = this.buttonContainer;
        if (qSAnimView3 != null) {
            TouchAnimator.Builder builder3 = new TouchAnimator.Builder();
            builder3.addFloat(qSAnimView3, "alpha", 0.0f, 1.0f);
            builder3.addFloat(qSAnimView3, "translationX", MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0.m(this.context) == 1 ? -this.xDiff : this.xDiff, 0.0f);
            builder3.addFloat(qSAnimView3, "translationY", f5, 0.0f);
            builder3.addFloat(qSAnimView3, "scaleX", 0.8f, 1.0f);
            builder3.addFloat(qSAnimView3, "scaleY", 0.8f, 1.0f);
            builder3.mStartDelay = f2;
            this.buttonAnimator = builder3.build();
        }
        QSImpl qSImpl = this.mQs;
        if (qSImpl != null && (view = qSImpl.getView()) != null) {
            TouchAnimator.Builder builder4 = new TouchAnimator.Builder();
            builder4.addFloat(view, "translationY", -f4, 0.0f);
            this.containerViewAnimator = builder4.build();
        }
        QSAnimView qSAnimView4 = this.blurView;
        float f6 = this.SCALE_DOWN_RATIO;
        if (qSAnimView4 != null && this.largeShadowView != null && this.smallShadowView != null && this.lessBlurView != null) {
            QSAnimView qSAnimView5 = this.blurParent;
            if (qSAnimView5 != null) {
                qSAnimView5.setPivotY(0.0f);
            }
            QSAnimView qSAnimView6 = this.blurParent;
            if (qSAnimView6 != null) {
                qSAnimView6.setPivotX((qSAnimView6.getView() != null ? r7.getWidth() : 2) / 2.0f);
            }
            QSAnimView qSAnimView7 = this.largeShadowView;
            if (qSAnimView7 != null) {
                qSAnimView7.setPivotY(0.0f);
            }
            QSAnimView qSAnimView8 = this.largeShadowView;
            if (qSAnimView8 != null) {
                qSAnimView8.setPivotX((qSAnimView8.getView() != null ? r7.getWidth() : 2) / 2.0f);
            }
            QSAnimView qSAnimView9 = this.smallShadowView;
            if (qSAnimView9 != null) {
                qSAnimView9.setPivotY(0.0f);
            }
            QSAnimView qSAnimView10 = this.smallShadowView;
            if (qSAnimView10 != null) {
                qSAnimView10.setPivotX((qSAnimView10.getView() != null ? r7.getWidth() : 2) / 2.0f);
            }
            TouchAnimator.Builder builder5 = new TouchAnimator.Builder();
            builder5.addFloat(this.blurParent, "scaleX", f6, f);
            builder5.addFloat(this.blurParent, "scaleY", f6, f);
            builder5.addFloat(this.blurParent, "translationY", -50.0f, 0.0f);
            builder5.addFloat(this.largeShadowView, "translationY", -50.0f, 0.0f);
            builder5.addFloat(this.largeShadowView, "scaleX", f6, f);
            builder5.addFloat(this.largeShadowView, "scaleY", f6, f);
            builder5.addFloat(this.smallShadowView, "translationY", -50.0f, 0.0f);
            builder5.addFloat(this.smallShadowView, "scaleX", f6, f);
            builder5.addFloat(this.smallShadowView, "scaleY", f6, f);
            builder5.mStartDelay = 0.5f;
            this.blurViewAnimator = builder5.build();
        }
        this.panelBarViews.clear();
        for (QSAnimView qSAnimView11 : this.viewProvider.getBars()) {
            if (qSAnimView11 != null) {
                this.panelBarViews.add(qSAnimView11);
            }
        }
        TouchAnimator.Builder builder6 = new TouchAnimator.Builder();
        ArrayList arrayList = this.panelBarViews;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            QSAnimView qSAnimView12 = (QSAnimView) obj;
            builder6.addFloat(qSAnimView12, "alpha", 0.0f, 1.0f);
            builder6.addFloat(qSAnimView12, "scaleX", f6, f);
            builder6.addFloat(qSAnimView12, "scaleY", f6, f);
            builder6.mStartDelay = 0.5f;
            builder6.build();
        }
        this.panelBarAnimator = builder6.build();
        this.overExpansionBarAnimators.clear();
        ArrayList bars = getBars();
        int size2 = bars.size();
        int i2 = 0;
        int i3 = 0;
        while (i3 < size2) {
            Object obj2 = bars.get(i3);
            i3++;
            View findViewWithTag = ((View) obj2).findViewWithTag("expand_anim");
            if (findViewWithTag != null) {
                if (findViewWithTag.getHeight() <= 0 && !(findViewWithTag instanceof ConstraintLayout)) {
                    findViewWithTag = null;
                }
                if (findViewWithTag != null) {
                    i2++;
                    int i4 = i2 * 5;
                    TouchAnimator.Builder builder7 = new TouchAnimator.Builder();
                    builder7.addFloat(findViewWithTag, "translationY", 0.0f, ((i4 * i2) - i4) + 20);
                    this.overExpansionBarAnimators.add(builder7.build());
                }
            }
        }
    }
}
