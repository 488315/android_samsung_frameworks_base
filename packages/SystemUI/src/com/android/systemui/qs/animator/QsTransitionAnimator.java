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
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.plugins.qs.QS;
import com.android.systemui.qs.SecQSDetailController;
import com.android.systemui.qs.SecQSPanel;
import com.android.systemui.qs.SecQSPanelController;
import com.android.systemui.qs.SecQuickQSPanel;
import com.android.systemui.qs.SecQuickStatusBarHeader;
import com.android.systemui.qs.TouchAnimator;
import com.android.systemui.qs.animator.SecQSImplAnimatorManager;
import com.android.systemui.qs.customize.viewcontroller.QSCMainViewController;
import com.android.systemui.shade.ShadeExpansionChangeEvent;
import com.android.systemui.shade.ShadeHeaderController;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.util.ConfigurationState;
import com.android.systemui.util.SettingsHelper;
import com.sec.ims.volte2.data.VolteConstants;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class QsTransitionAnimator extends SecQSImplAnimatorBase implements SettingsHelper.OnChangedCallback {
    public static final Interpolator INTERPOLATOR = new PathInterpolator(0.37f, 0.3f, 0.14f, 1.34f);
    public SecQSImplAnimatorManager.AnonymousClass2 mAnimStateCallback;
    public final QSCMainViewController mCustomizerMainController;
    public DetailCallback mDetailCallback;
    public TouchAnimator mDetailCollapseAnimator;
    public final SecQSDetailController mDetailController;
    public AnimatorSet mDetailHideAnimSet;
    public AnimatorSet mDetailShowAnimSet;
    public View mDetailView;
    public SecQuickStatusBarHeader mHeader;
    public View mHeaderDateButtonContainer;
    public final ViewGroup mHeaderIcons;
    public NotificationStackScrollLayout mNSSL;
    public final NotificationStackScrollLayoutController mNotificationStackScrollLayoutController;
    public boolean mPanelExpanded;
    public AnimatorSet mPanelHideAnimSet;
    public AnimatorSet mPanelShowAnimSet;
    public final View mPlmn;
    public final SecQSPanelController mQSPanelController;
    public SecQSPanel mQsPanel;
    public SecQuickQSPanel mQuickQsPanel;
    private final SettingsHelper mSettingsHelper;
    public final ConfigurationState mLastConfigurationState = new ConfigurationState(Arrays.asList(ConfigurationState.ConfigurationField.ASSET_SEQ, ConfigurationState.ConfigurationField.THEME_SEQ, ConfigurationState.ConfigurationField.UI_MODE));
    public final ArrayList mPanelContents = new ArrayList();
    public final ArrayList mDetailContents = new ArrayList();
    public final ArrayList mPanelShowAnimators = new ArrayList();
    public final ArrayList mPanelHideAnimators = new ArrayList();
    public final ArrayList mDetailShowAnimators = new ArrayList();
    public final ArrayList mDetailHideAnimators = new ArrayList();
    public final ArrayList mCarriers = new ArrayList();
    public final Handler mHandler = new Handler(Looper.getMainLooper());
    public final AnonymousClass1 mPanelAnimListener = new Animator.AnimatorListener() { // from class: com.android.systemui.qs.animator.QsTransitionAnimator.1
        public boolean isCanceled = false;

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationCancel(Animator animator) {
            this.isCanceled = true;
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            QsTransitionAnimator.this.getClass();
            if (SecQSImplAnimatorBase.isDetailVisible()) {
                QsTransitionAnimator.this.mAnimStateCallback.setDetailClosing(false);
                SecQSImplAnimatorManager.AnonymousClass2 anonymousClass2 = QsTransitionAnimator.this.mAnimStateCallback;
                anonymousClass2.getClass();
                SecQSImplAnimatorManager.this.executeConsumer(new SecQSImplAnimatorManager$$ExternalSyntheticLambda5());
                QsTransitionAnimator.this.clearDetailView();
                ((View) QsTransitionAnimator.this.mQSPanelController.mTileLayout).setVisibility(0);
            }
            if (!this.isCanceled) {
                QsTransitionAnimator.this.getClass();
                if (QsAnimatorState.isCustomizerShowing) {
                    QsTransitionAnimator.this.mAnimStateCallback.setDetailClosing(false);
                    QsTransitionAnimator.this.clearCustomizerView();
                }
            }
            QsTransitionAnimator qsTransitionAnimator = QsTransitionAnimator.this;
            if (qsTransitionAnimator.mQsPanel != null) {
                SecQSPanelController secQSPanelController = qsTransitionAnimator.mQSPanelController;
                if (secQSPanelController.mExpandSettingsPanel) {
                    secQSPanelController.mExpandSettingsPanel = false;
                }
            }
            this.isCanceled = false;
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationStart(Animator animator) {
            if (QsTransitionAnimator.this.mNotificationStackScrollLayoutController.mView.getVisibility() == 4) {
                QsTransitionAnimator.this.mNotificationStackScrollLayoutController.mView.setVisibility(0);
            }
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationRepeat(Animator animator) {
        }
    };
    public final AnonymousClass2 mDetailAnimListener = new Animator.AnimatorListener() { // from class: com.android.systemui.qs.animator.QsTransitionAnimator.2
        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            QsTransitionAnimator qsTransitionAnimator = QsTransitionAnimator.this;
            if (!qsTransitionAnimator.mPanelExpanded) {
                qsTransitionAnimator.mAnimStateCallback.setDetailClosing(false);
                QsTransitionAnimator.this.mAnimStateCallback.setDetailShowing(false);
                return;
            }
            Iterator it = qsTransitionAnimator.mDetailContents.iterator();
            while (it.hasNext()) {
                ((View) it.next()).setAlpha(1.0f);
            }
            QsTransitionAnimator.this.mDetailCallback.showDetailAnimEnd();
            QsTransitionAnimator.this.mAnimStateCallback.setDetailOpening(false);
            QsTransitionAnimator.this.mAnimStateCallback.setDetailShowing(true);
            if (QsAnimatorState.qsExpanded) {
                return;
            }
            QsTransitionAnimator.this.mNotificationStackScrollLayoutController.mView.setVisibility(4);
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface DetailCallback {
        void hideDetailAnimEnd();

        void showDetailAnimEnd();
    }

    /* JADX WARN: Type inference failed for: r4v10, types: [com.android.systemui.qs.animator.QsTransitionAnimator$1] */
    /* JADX WARN: Type inference failed for: r4v11, types: [com.android.systemui.qs.animator.QsTransitionAnimator$2] */
    public QsTransitionAnimator(Lazy lazy, SecQSPanelController secQSPanelController, NotificationStackScrollLayoutController notificationStackScrollLayoutController, QSCMainViewController qSCMainViewController, ShadeHeaderController shadeHeaderController, SecQSDetailController secQSDetailController) {
        this.mQSPanelController = secQSPanelController;
        this.mNotificationStackScrollLayoutController = notificationStackScrollLayoutController;
        SettingsHelper settingsHelper = (SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class);
        this.mSettingsHelper = settingsHelper;
        settingsHelper.registerCallback(this, Settings.Global.getUriFor(SettingsHelper.INDEX_GLOBAL_ANIMATOR_DURATION_SCALE));
        this.mCustomizerMainController = qSCMainViewController;
        this.mDetailController = secQSDetailController;
        this.mHeaderIcons = (ViewGroup) shadeHeaderController.header.requireViewById(R.id.split_shade_status_bar);
        this.mPlmn = shadeHeaderController.header.requireViewById(R.id.carrier_group);
    }

    public static Animator makeTransitionAnimator(View view, int i, float f, boolean z) {
        PropertyValuesHolder[] propertyValuesHolderArr = new PropertyValuesHolder[3];
        propertyValuesHolderArr[0] = PropertyValuesHolder.ofFloat((Property<?, Float>) View.ALPHA, f);
        propertyValuesHolderArr[1] = PropertyValuesHolder.ofFloat((Property<?, Float>) View.SCALE_X, z ? 1.0f : 0.93f);
        propertyValuesHolderArr[2] = PropertyValuesHolder.ofFloat((Property<?, Float>) View.SCALE_Y, z ? 1.0f : 0.93f);
        ObjectAnimator ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(view, propertyValuesHolderArr);
        ofPropertyValuesHolder.setDuration(i);
        ofPropertyValuesHolder.setStartDelay(0L);
        ofPropertyValuesHolder.setInterpolator(INTERPOLATOR);
        return ofPropertyValuesHolder;
    }

    public final void clearCustomizerView() {
        if (!isThereNoView()) {
            this.mCustomizerMainController.close();
        }
        SecQSImplAnimatorManager.AnonymousClass2 anonymousClass2 = this.mAnimStateCallback;
        if (anonymousClass2 != null) {
            SecQSImplAnimatorManager.this.executeConsumer(new SecQSImplAnimatorManager$$ExternalSyntheticLambda5());
            this.mAnimStateCallback.getClass();
            SecQSImplAnimatorManager.AnonymousClass2.setCustomizerShowing(false);
        }
    }

    public final void clearDetailView() {
        if (!isThereNoView()) {
            this.mDetailView.setAlpha(0.0f);
            this.mDetailView.setTranslationY(0.0f);
        }
        DetailCallback detailCallback = this.mDetailCallback;
        if (detailCallback != null) {
            detailCallback.hideDetailAnimEnd();
        }
        SecQSImplAnimatorManager.AnonymousClass2 anonymousClass2 = this.mAnimStateCallback;
        if (anonymousClass2 != null) {
            SecQSImplAnimatorManager.this.executeConsumer(new SecQSImplAnimatorManager$$ExternalSyntheticLambda5());
        }
        SecQSImplAnimatorManager.AnonymousClass2 anonymousClass22 = this.mAnimStateCallback;
        if (anonymousClass22 != null) {
            anonymousClass22.setDetailOpening(false);
        }
        SecQSImplAnimatorManager.AnonymousClass2 anonymousClass23 = this.mAnimStateCallback;
        if (anonymousClass23 != null) {
            anonymousClass23.setDetailShowing(false);
        }
        SecQSImplAnimatorManager.AnonymousClass2 anonymousClass24 = this.mAnimStateCallback;
        if (anonymousClass24 != null) {
            anonymousClass24.setDetailClosing(false);
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void destroyQSViews() {
        clearDetailView();
        clearCustomizerView();
        this.mPanelContents.clear();
        this.mDetailContents.clear();
        this.mCarriers.clear();
        this.mQsPanel = null;
        this.mQuickQsPanel = null;
        this.mDetailView = null;
        this.mQs = null;
        this.mSettingsHelper.unregisterCallback(this);
        this.mDetailCallback = null;
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase, com.android.systemui.logging.PanelScreenShotLogger.LogProvider
    public final ArrayList gatherState() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("QsTransitionAnimator ============================================= ");
        SecQSImplAnimatorBase.gatherStateOfViews(arrayList, " mPanelContents ", this.mPanelContents, false);
        SecQSImplAnimatorBase.gatherStateOfViews(arrayList, " mDetailContents ", this.mDetailContents, false);
        SecQSImplAnimatorBase.gatherStateOfViews(arrayList, " mCarriers ", this.mCarriers, false);
        arrayList.add("============================================================== ");
        return arrayList;
    }

    @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
    public final void onChanged(Uri uri) {
        if (uri.equals(Settings.Global.getUriFor(SettingsHelper.INDEX_GLOBAL_ANIMATOR_DURATION_SCALE))) {
            this.mSettingsHelper.isAnimationRemoved();
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void onConfigurationChanged(Configuration configuration) {
        ConfigurationState configurationState = this.mLastConfigurationState;
        if (configurationState.needToUpdate(configuration)) {
            this.mQs.getView().post(new Runnable() { // from class: com.android.systemui.qs.animator.QsTransitionAnimator$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    QsTransitionAnimator.this.updateAnimators();
                }
            });
            configurationState.update(configuration);
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void onPanelClosed() {
        if (isThereNoView()) {
            return;
        }
        clearDetailView();
        clearCustomizerView();
        restorePanelView();
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase, com.android.systemui.shade.ShadeExpansionListener
    public final void onPanelExpansionChanged(ShadeExpansionChangeEvent shadeExpansionChangeEvent) {
        TouchAnimator touchAnimator;
        boolean z = QsAnimatorState.isDetailShowing;
        float f = shadeExpansionChangeEvent.fraction;
        if (z && !QsAnimatorState.isDetailClosing && (touchAnimator = this.mDetailCollapseAnimator) != null) {
            touchAnimator.setPosition(f);
        }
        if (f < 1.0f) {
            this.mCustomizerMainController.setPosition(f);
        }
        if (f == 0.0f) {
            this.mAnimStateCallback.setDetailOpening(false);
            this.mAnimStateCallback.setDetailShowing(false);
            this.mAnimStateCallback.setDetailClosing(false);
        } else {
            if (f != 1.0f || this.mDetailView == null || SecQSImplAnimatorBase.isDetailVisible() || this.mDetailView.getVisibility() != 0) {
                return;
            }
            clearDetailView();
        }
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
        Iterator it = this.mPanelContents.iterator();
        while (it.hasNext()) {
            View view = (View) it.next();
            view.setAlpha(1.0f);
            view.setScaleX(1.0f);
            view.setScaleY(1.0f);
        }
        Iterator it2 = this.mCarriers.iterator();
        while (it2.hasNext()) {
            View view2 = (View) it2.next();
            view2.setAlpha(1.0f);
            view2.setScaleX(1.0f);
            view2.setScaleY(1.0f);
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void setQs(QS qs) {
        if (qs == null) {
            destroyQSViews();
        } else {
            this.mQs = qs;
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void setQsExpanded(boolean z) {
        QsAnimatorState.qsExpanded = z;
        if (z || this.mPanelExpanded) {
            return;
        }
        restorePanelView();
    }

    public final void showQsPanel(boolean z) {
        if (isThereNoView()) {
            return;
        }
        if (z) {
            this.mPanelHideAnimSet.cancel();
            this.mPanelShowAnimSet.start();
        } else {
            this.mPanelShowAnimSet.cancel();
            this.mPanelHideAnimSet.start();
        }
        this.mHeader.setDescendantFocusability(z ? 262144 : 393216);
    }

    public final void showQsPanelForCustomizer(final boolean z) {
        Handler handler = this.mHandler;
        handler.removeCallbacksAndMessages(null);
        makeTransitionAnimator(this.mHeaderIcons, 350, z ? 1.0f : 0.0f, true).start();
        handler.postDelayed(new Runnable() { // from class: com.android.systemui.qs.animator.QsTransitionAnimator$$ExternalSyntheticLambda0
            /* JADX WARN: Code restructure failed: missing block: B:4:0x000d, code lost:
            
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
                    com.android.systemui.qs.animator.QsTransitionAnimator r0 = com.android.systemui.qs.animator.QsTransitionAnimator.this
                    boolean r3 = r2
                    r1 = 0
                    if (r3 == 0) goto L10
                    r0.getClass()
                    int r3 = com.android.systemui.qs.animator.QsAnimatorState.state
                    r2 = 1
                    if (r3 == r2) goto L10
                    goto L11
                L10:
                    r2 = r1
                L11:
                    com.android.systemui.qs.SecQSPanelController r3 = r0.mQSPanelController
                    r3.setGridContentVisibility(r2)
                    android.view.ViewGroup r3 = r0.mHeaderIcons
                    if (r2 == 0) goto L1b
                    goto L1c
                L1b:
                    r1 = 4
                L1c:
                    r3.setVisibility(r1)
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.animator.QsTransitionAnimator$$ExternalSyntheticLambda0.run():void");
            }
        }, z ? 0L : 350L);
        showQsPanel(z);
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void updateAnimators() {
        if (isThereNoView()) {
            return;
        }
        if (isThereNoView()) {
            destroyQSViews();
        } else {
            this.mPanelContents.clear();
            this.mDetailContents.clear();
            this.mCarriers.clear();
            SecQuickStatusBarHeader header = getHeader();
            this.mHeader = header;
            if (header != null) {
                this.mQuickQsPanel = (SecQuickQSPanel) header.findViewById(R.id.quick_qs_panel);
                View findViewById = this.mHeader.findViewById(R.id.quick_qs_date_buttons);
                this.mHeaderDateButtonContainer = findViewById;
                this.mPanelContents.add(findViewById);
                this.mPanelContents.add(this.mQuickQsPanel);
            }
            SecQSPanel secQSPanel = (SecQSPanel) this.mQs.getView().findViewById(R.id.quick_settings_panel);
            this.mQsPanel = secQSPanel;
            this.mNSSL = this.mNotificationStackScrollLayoutController.mView;
            this.mPanelContents.add(secQSPanel);
            this.mPanelContents.add(this.mNSSL);
            View findViewById2 = this.mDetailController.view.findViewById(R.id.qs_detail);
            this.mDetailView = findViewById2;
            this.mDetailContents.add(findViewById2);
            this.mCarriers.add(this.mPlmn);
            this.mCarriers.add(this.mPlmn.findViewById(R.id.carrier1));
            this.mCarriers.add(this.mPlmn.findViewById(R.id.carrier2));
            this.mCarriers.add(this.mPlmn.findViewById(R.id.carrier3));
        }
        this.mPanelHideAnimSet = new AnimatorSet();
        this.mPanelHideAnimators.clear();
        Iterator it = this.mPanelContents.iterator();
        while (it.hasNext()) {
            View view = (View) it.next();
            this.mPanelHideAnimators.add(makeTransitionAnimator(view, 350, 0.0f, view == this.mQuickQsPanel));
        }
        Iterator it2 = this.mCarriers.iterator();
        while (it2.hasNext()) {
            this.mPanelHideAnimators.add(makeTransitionAnimator((View) it2.next(), 350, 0.0f, false));
        }
        this.mPanelHideAnimSet.playTogether(this.mPanelHideAnimators);
        this.mPanelShowAnimSet = new AnimatorSet();
        this.mPanelShowAnimators.clear();
        Iterator it3 = this.mPanelContents.iterator();
        while (it3.hasNext()) {
            this.mPanelShowAnimators.add(makeTransitionAnimator((View) it3.next(), 350, 1.0f, true));
        }
        Iterator it4 = this.mCarriers.iterator();
        while (it4.hasNext()) {
            this.mPanelShowAnimators.add(makeTransitionAnimator((View) it4.next(), 350, 1.0f, true));
        }
        this.mPanelShowAnimSet.playTogether(this.mPanelShowAnimators);
        this.mPanelShowAnimSet.addListener(this.mPanelAnimListener);
        this.mDetailShowAnimSet = new AnimatorSet();
        this.mDetailShowAnimators.clear();
        Iterator it5 = this.mDetailContents.iterator();
        while (it5.hasNext()) {
            this.mDetailShowAnimators.add(makeTransitionAnimator((View) it5.next(), VolteConstants.ErrorCode.TEMPORARILY_UNAVAILABLE, 1.0f, true));
        }
        this.mDetailShowAnimSet.playTogether(this.mDetailShowAnimators);
        this.mDetailShowAnimSet.addListener(this.mDetailAnimListener);
        this.mDetailHideAnimSet = new AnimatorSet();
        this.mDetailHideAnimators.clear();
        Iterator it6 = this.mDetailContents.iterator();
        while (it6.hasNext()) {
            this.mDetailHideAnimators.add(makeTransitionAnimator((View) it6.next(), 300, 0.0f, false));
        }
        this.mDetailHideAnimSet.playTogether(this.mDetailHideAnimators);
        TouchAnimator.Builder builder = new TouchAnimator.Builder();
        builder.addFloat(this.mDetailView, "alpha", 0.0f, 1.0f);
        builder.addFloat(this.mDetailView, "translationY", (-this.mHeader.getHeight()) * 0.2f, 0.0f);
        this.mDetailCollapseAnimator = builder.build();
        this.mAnimatorsInitialiezed = true;
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void updatePanelExpanded(boolean z) {
        this.mPanelExpanded = z;
    }
}
