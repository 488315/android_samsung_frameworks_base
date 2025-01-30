package com.android.systemui.qs.animator;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.provider.Settings;
import android.util.Property;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.qs.PagedTileLayout;
import com.android.systemui.qs.QSTileHost;
import com.android.systemui.qs.SecQSDetail;
import com.android.systemui.qs.SecQSPanel;
import com.android.systemui.qs.SecQSPanelController;
import com.android.systemui.qs.SecQuickQSPanel;
import com.android.systemui.qs.SecQuickStatusBarHeader;
import com.android.systemui.qs.TouchAnimator;
import com.android.systemui.qs.animator.SecQSFragmentAnimatorManager;
import com.android.systemui.qs.bar.BarController;
import com.android.systemui.plugins.qs.InterfaceC1922QS;
import com.android.systemui.shade.ShadeExpansionChangeEvent;
import com.android.systemui.shade.ShadeHeaderController;
import com.android.systemui.util.SettingsHelper;
import com.sec.ims.volte2.data.VolteConstants;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.BooleanSupplier;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class QsTransitionAnimator extends SecQSFragmentAnimatorBase implements SettingsHelper.OnChangedCallback {
    public static final Interpolator INTERPOLATOR = new PathInterpolator(0.37f, 0.3f, 0.14f, 1.34f);
    public final Context mContext;
    public final C20822 mDetailAnimListener;
    public SecQSDetail.C20715 mDetailCallback;
    public TouchAnimator mDetailCollapseAlphaAnimator;
    public AnimatorSet mDetailHideAnimSet;
    public AnimatorSet mDetailShowAnimSet;
    public SecQSFragmentAnimatorManager.C20831 mDetailStateCallback;
    public View mDetailView;
    public SecQuickStatusBarHeader mHeader;
    public View mHeaderDateButtonContainer;
    public final Lazy mLazyExpandAnimator;
    public View mPagedTileLayout;
    public final C20811 mPanelAnimListener;
    public boolean mPanelExpanded;
    public AnimatorSet mPanelHideAnimSetForDetail;
    public AnimatorSet mPanelShowAnimSetForDetail;
    public final SecQSPanelController mQSPanelController;
    public boolean mQsFullyExpanded;
    public SecQSPanel mQsPanel;
    public SecQuickQSPanel mQuickQsPanel;
    public final QsTransitionAnimator$$ExternalSyntheticLambda0 mUpdateAnimators;
    public final ArrayList mPanelContents = new ArrayList();
    public final ArrayList mDetailContents = new ArrayList();
    public final ArrayList mPanelShowAnimatorsForDetail = new ArrayList();
    public final ArrayList mPanelHideAnimatorsForDetail = new ArrayList();
    public final ArrayList mDetailShowAnimators = new ArrayList();
    public final ArrayList mDetailHideAnimators = new ArrayList();

    /* JADX WARN: Type inference failed for: r5v4, types: [com.android.systemui.qs.animator.QsTransitionAnimator$1] */
    /* JADX WARN: Type inference failed for: r5v5, types: [com.android.systemui.qs.animator.QsTransitionAnimator$2] */
    public QsTransitionAnimator(Context context, Lazy lazy, BarController barController, SecQSPanelController secQSPanelController, QSTileHost qSTileHost, ShadeHeaderController shadeHeaderController) {
        ((SettingsHelper) Dependency.get(SettingsHelper.class)).isAnimationRemoved();
        this.mUpdateAnimators = new QsTransitionAnimator$$ExternalSyntheticLambda0(this, 0);
        this.mPanelAnimListener = new Animator.AnimatorListener() { // from class: com.android.systemui.qs.animator.QsTransitionAnimator.1
            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                if (QsTransitionAnimator.this.isDetailVisible()) {
                    QsTransitionAnimator.this.mDetailStateCallback.setDetailClosing(false);
                    SecQSFragmentAnimatorManager.C20831 c20831 = QsTransitionAnimator.this.mDetailStateCallback;
                    c20831.getClass();
                    SecQSFragmentAnimatorManager.this.executeConsumer(new SecQSFragmentAnimatorManager$$ExternalSyntheticLambda1(false, 7));
                    QsTransitionAnimator.this.clearDetailView();
                    ((View) QsTransitionAnimator.this.mQSPanelController.mTileLayout).setVisibility(0);
                }
                QsTransitionAnimator qsTransitionAnimator = QsTransitionAnimator.this;
                if (qsTransitionAnimator.mQsPanel != null) {
                    SecQSPanelController secQSPanelController2 = qsTransitionAnimator.mQSPanelController;
                    if (secQSPanelController2.mExpandSettingsPanel) {
                        secQSPanelController2.mExpandSettingsPanel = false;
                    }
                }
                if (qsTransitionAnimator.mLazyExpandAnimator.get() != null) {
                    ((QsExpandAnimator) QsTransitionAnimator.this.mLazyExpandAnimator.get()).updateAnimators();
                }
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
            }
        };
        this.mDetailAnimListener = new Animator.AnimatorListener() { // from class: com.android.systemui.qs.animator.QsTransitionAnimator.2
            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                QsTransitionAnimator qsTransitionAnimator = QsTransitionAnimator.this;
                if (!qsTransitionAnimator.mPanelExpanded) {
                    qsTransitionAnimator.mDetailStateCallback.setDetailClosing(false);
                    QsTransitionAnimator.this.mDetailStateCallback.setDetailShowing(false);
                    return;
                }
                Iterator it = qsTransitionAnimator.mDetailContents.iterator();
                while (it.hasNext()) {
                    ((View) it.next()).setAlpha(1.0f);
                }
                SecQSDetail secQSDetail = SecQSDetail.this;
                if (secQSDetail.mDetailAdapter != null) {
                    secQSDetail.mQsPanelController.setGridContentVisibility(false);
                }
                secQSDetail.mAnimatingOpen = false;
                SecQSDetail.m1621$$Nest$mcheckPendingAnimations(secQSDetail);
                QsTransitionAnimator.this.mDetailStateCallback.setDetailOpening(false);
                QsTransitionAnimator.this.mDetailStateCallback.setDetailShowing(true);
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
            }
        };
        this.mContext = context;
        this.mLazyExpandAnimator = lazy;
        this.mQSPanelController = secQSPanelController;
        ((SettingsHelper) Dependency.get(SettingsHelper.class)).registerCallback(this, Settings.Global.getUriFor("animator_duration_scale"));
    }

    public static Animator makeTransitionAnimator(View view, int i, float f, boolean z, Interpolator interpolator) {
        PropertyValuesHolder[] propertyValuesHolderArr = new PropertyValuesHolder[3];
        propertyValuesHolderArr[0] = PropertyValuesHolder.ofFloat((Property<?, Float>) View.ALPHA, f);
        Property property = View.SCALE_X;
        float[] fArr = new float[1];
        fArr[0] = z ? 1.0f : 0.93f;
        propertyValuesHolderArr[1] = PropertyValuesHolder.ofFloat((Property<?, Float>) property, fArr);
        Property property2 = View.SCALE_Y;
        float[] fArr2 = new float[1];
        fArr2[0] = z ? 1.0f : 0.93f;
        propertyValuesHolderArr[2] = PropertyValuesHolder.ofFloat((Property<?, Float>) property2, fArr2);
        ObjectAnimator ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(view, propertyValuesHolderArr);
        ofPropertyValuesHolder.setDuration(i);
        ofPropertyValuesHolder.setStartDelay(0);
        ofPropertyValuesHolder.setInterpolator(interpolator);
        return ofPropertyValuesHolder;
    }

    public final void clearDetailView() {
        if (!isThereNoView()) {
            this.mDetailView.setAlpha(0.0f);
        }
        SecQSDetail secQSDetail = SecQSDetail.this;
        secQSDetail.mDetailContent.removeAllViews();
        secQSDetail.setVisibility(4);
        secQSDetail.handleShowingDetail(null);
        SecQSFragmentAnimatorManager.C20831 c20831 = this.mDetailStateCallback;
        c20831.getClass();
        SecQSFragmentAnimatorManager.this.executeConsumer(new SecQSFragmentAnimatorManager$$ExternalSyntheticLambda1(false, 7));
        this.mDetailStateCallback.setDetailOpening(false);
        this.mDetailStateCallback.setDetailShowing(false);
        this.mDetailStateCallback.setDetailClosing(false);
    }

    @Override // com.android.systemui.qs.animator.SecQSFragmentAnimatorBase
    public final void destroyQSViews() {
        clearDetailView();
        this.mPanelContents.clear();
        this.mDetailContents.clear();
        this.mQsPanel = null;
        this.mQuickQsPanel = null;
        this.mHeaderDateButtonContainer = null;
        this.mDetailView = null;
        this.mQs = null;
        ((SettingsHelper) Dependency.get(SettingsHelper.class)).unregisterCallback(this);
    }

    @Override // com.android.systemui.qs.animator.SecQSFragmentAnimatorBase, com.android.systemui.logging.PanelScreenShotLogger.LogProvider
    public final ArrayList gatherState() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("SecQSsTransitionAnimator ============================================= ");
        arrayList.add(" mPanelContents ");
        Iterator it = this.mPanelContents.iterator();
        while (it.hasNext()) {
            View view = (View) it.next();
            arrayList.add("  " + view.getClass().getSimpleName() + " : alpha = " + view.getAlpha() + " visibility = " + view.getVisibility());
        }
        arrayList.add(" mDetailContents ");
        Iterator it2 = this.mDetailContents.iterator();
        while (it2.hasNext()) {
            View view2 = (View) it2.next();
            arrayList.add("  " + view2.getClass().getSimpleName() + " : alpha = " + view2.getAlpha() + " visibility = " + view2.getVisibility());
        }
        arrayList.add("============================================================== ");
        return arrayList;
    }

    @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
    public final void onChanged(Uri uri) {
        if (uri.equals(Settings.Global.getUriFor("animator_duration_scale"))) {
            ((SettingsHelper) Dependency.get(SettingsHelper.class)).isAnimationRemoved();
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSFragmentAnimatorBase
    public final void onConfigurationChanged(Configuration configuration) {
        if (this.mThemeSeq == configuration.themeSeq && this.mAssetSeq == configuration.assetsSeq && this.mUIMode == configuration.uiMode) {
            return;
        }
        this.mQs.getView().post(new QsTransitionAnimator$$ExternalSyntheticLambda0(this, 1));
    }

    @Override // com.android.systemui.qs.animator.SecQSFragmentAnimatorBase
    public final void onPanelClosed() {
        if (isThereNoView()) {
            return;
        }
        clearDetailView();
        restorePanelView();
        SecQSPanel secQSPanel = this.mQsPanel;
        if (secQSPanel != null) {
            secQSPanel.post(this.mUpdateAnimators);
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSFragmentAnimatorBase, com.android.systemui.shade.ShadeExpansionListener
    public final void onPanelExpansionChanged(ShadeExpansionChangeEvent shadeExpansionChangeEvent) {
        TouchAnimator touchAnimator;
        BooleanSupplier booleanSupplier = this.mExpandImmediateSupplier;
        if (booleanSupplier != null && booleanSupplier.getAsBoolean()) {
            if ((this.mIsDetailShowing && !this.mIsDetailClosing) && (touchAnimator = this.mDetailCollapseAlphaAnimator) != null) {
                touchAnimator.setPosition(shadeExpansionChangeEvent.fraction);
            }
        }
        float f = shadeExpansionChangeEvent.fraction;
        if (f == 0.0f) {
            this.mDetailStateCallback.setDetailOpening(false);
            this.mDetailStateCallback.setDetailShowing(false);
            this.mDetailStateCallback.setDetailClosing(false);
        } else {
            if (f != 1.0f || this.mDetailView == null || isDetailVisible() || this.mDetailView.getVisibility() != 0) {
                return;
            }
            clearDetailView();
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSFragmentAnimatorBase
    public final void onStateChanged(int i) {
        this.mState = i;
        if (i == 1) {
            if (isDetailVisible()) {
                clearDetailView();
            }
            restorePanelView();
        }
    }

    public final void restorePanelView() {
        if (isThereNoView()) {
            return;
        }
        Iterator it = this.mPanelContents.iterator();
        while (it.hasNext()) {
            View view = (View) it.next();
            view.setAlpha(1.0f);
            view.setScaleX(1.0f);
            view.setScaleY(1.0f);
        }
        this.mPagedTileLayout.setVisibility(0);
    }

    @Override // com.android.systemui.qs.animator.SecQSFragmentAnimatorBase
    public final void setFullyExpanded(boolean z) {
        this.mQsFullyExpanded = z;
        if (z) {
            updateAnimators();
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSFragmentAnimatorBase
    public final void setQs(InterfaceC1922QS interfaceC1922QS) {
        if (interfaceC1922QS == null) {
            destroyQSViews();
        } else {
            this.mQs = interfaceC1922QS;
            this.mContext.getResources();
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSFragmentAnimatorBase
    public final void setQsExpanded(boolean z) {
        this.mQsExpanded = z;
        if (z || this.mPanelExpanded) {
            return;
        }
        restorePanelView();
    }

    @Override // com.android.systemui.qs.animator.SecQSFragmentAnimatorBase
    public final void updateAnimators() {
        Interpolator interpolator;
        if (isThereNoView()) {
            return;
        }
        boolean isThereNoView = isThereNoView();
        ArrayList arrayList = this.mDetailContents;
        ArrayList arrayList2 = this.mPanelContents;
        if (isThereNoView) {
            destroyQSViews();
        } else {
            arrayList2.clear();
            arrayList.clear();
            SecQuickStatusBarHeader secQuickStatusBarHeader = (SecQuickStatusBarHeader) this.mQs.getView().findViewById(R.id.header);
            this.mHeader = secQuickStatusBarHeader;
            this.mQuickQsPanel = (SecQuickQSPanel) secQuickStatusBarHeader.findViewById(R.id.quick_qs_panel);
            View findViewById = this.mHeader.findViewById(R.id.quick_qs_date_buttons);
            this.mHeaderDateButtonContainer = findViewById;
            arrayList2.add(findViewById);
            arrayList2.add(this.mQuickQsPanel);
            this.mQsPanel = (SecQSPanel) this.mQs.getView().findViewById(R.id.quick_settings_panel);
            for (int i = 0; i < this.mQsPanel.getChildCount(); i++) {
                arrayList2.add(this.mQsPanel.getChildAt(i));
            }
            this.mPagedTileLayout = this.mQsPanel.findViewById(R.id.qs_pager);
            View findViewById2 = this.mQs.getView().findViewById(R.id.qs_detail);
            this.mDetailView = findViewById2;
            arrayList.add(findViewById2);
        }
        this.mPanelHideAnimSetForDetail = new AnimatorSet();
        ArrayList arrayList3 = this.mPanelHideAnimatorsForDetail;
        arrayList3.clear();
        Iterator it = arrayList2.iterator();
        while (true) {
            boolean hasNext = it.hasNext();
            interpolator = INTERPOLATOR;
            boolean z = true;
            if (!hasNext) {
                break;
            }
            View view = (View) it.next();
            if (view != this.mQuickQsPanel) {
                z = false;
            }
            arrayList3.add(makeTransitionAnimator(view, 350, 0.0f, z, interpolator));
        }
        this.mPanelHideAnimSetForDetail.playTogether(arrayList3);
        this.mPanelShowAnimSetForDetail = new AnimatorSet();
        ArrayList arrayList4 = this.mPanelShowAnimatorsForDetail;
        arrayList4.clear();
        Iterator it2 = arrayList2.iterator();
        while (it2.hasNext()) {
            View view2 = (View) it2.next();
            if (this.mQsPanel == null || this.mQSPanelController.mExpandSettingsPanel || !this.mDetailTriggeredExpand || !(view2 instanceof PagedTileLayout)) {
                arrayList4.add(makeTransitionAnimator(view2, 350, 1.0f, true, interpolator));
            }
        }
        this.mPanelShowAnimSetForDetail.playTogether(arrayList4);
        this.mPanelShowAnimSetForDetail.addListener(this.mPanelAnimListener);
        this.mDetailShowAnimSet = new AnimatorSet();
        ArrayList arrayList5 = this.mDetailShowAnimators;
        arrayList5.clear();
        Iterator it3 = arrayList.iterator();
        while (it3.hasNext()) {
            arrayList5.add(makeTransitionAnimator((View) it3.next(), VolteConstants.ErrorCode.TEMPORARILY_UNAVAILABLE, 1.0f, true, interpolator));
        }
        this.mDetailShowAnimSet.playTogether(arrayList5);
        this.mDetailShowAnimSet.addListener(this.mDetailAnimListener);
        this.mDetailHideAnimSet = new AnimatorSet();
        ArrayList arrayList6 = this.mDetailHideAnimators;
        arrayList6.clear();
        Iterator it4 = arrayList.iterator();
        while (it4.hasNext()) {
            arrayList6.add(makeTransitionAnimator((View) it4.next(), 300, 0.0f, false, interpolator));
        }
        this.mDetailHideAnimSet.playTogether(arrayList6);
        TouchAnimator.Builder builder = new TouchAnimator.Builder();
        builder.addFloat(this.mDetailView, "alpha", 0.0f, 1.0f);
        builder.mStartDelay = 0.3f;
        this.mDetailCollapseAlphaAnimator = builder.build();
        this.mAnimatorsInitialiezed = true;
    }

    @Override // com.android.systemui.qs.animator.SecQSFragmentAnimatorBase
    public final void updatePanelExpanded(boolean z) {
        this.mPanelExpanded = z;
    }
}
